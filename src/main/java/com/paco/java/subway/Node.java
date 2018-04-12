package com.paco.java.subway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.paco.java.subway.virtual.INodeLinker;

/**
 * class Node : This class is the main element / pivot of the application.  The Node represents both a virtual
 * class and a real class.  A Node contains 2 links which connects to other Nodes
 * 
 * @author Pascal
 */
public class Node {

	public static Node IOriginNode = null;
	public static List<Node> LSTVisited = new ArrayList<Node>();
	/**
	 * This constructor just sets the Name of the node.  If this constructor is called for the
	 * first time then we also set the IOrigin as an IOriginNode type...
	 * 
	 * @param sNodeName A non empty String representing the name of the Node
	 */
	public Node(String sNodeName)
	{
		sName = sNodeName;
		if (Node.IOriginNode == null)
		{
			IOriginNode = this;
			IOriginNode = new OriginNode(sNodeName);
		}		
	}
	/**
	 * This function will try to find oNode 
	 * amongst the chained list of Node and NodeLinks. If oNode is found then the instance 
	 * of the chained list will be returned. Otherwise <code>null</code> shall be returned.
	 * This function is polymorphic and recursive for each instance of the polymorph
	 * making it very complex
	 * 
	 * @param oNode : a non <code>null</code> of type <code>INode</code> that is the object being
	 * 					searched.  This value shouldn't change during the entire process for a given
	 * 					call...
	 * @param oCurrentNode : a non <code>null</code> of type <code>INode</code> that represents 
	 * 						where we are in the chain
	 * @return		: A possibly <code>null</code> value of type <code>INode</code>.  Represents
	 * 					if the Node being searched does exists in the chain...
	 */
	public static Node FindNode(Node oNode)
	{
		if(IOriginNode == null)
			return null;
		LSTVisited.clear();
		LSTVisited.add(IOriginNode);
		Node oCurrentNode = IOriginNode;
		if (oCurrentNode.getNodeName().equals(oNode.getNodeName() ) )
			return oCurrentNode;
		else
			return oCurrentNode.SearchForNode(oNode);
	}
	/**
	 * This method will go around the entire map recorded Link by Link and will export all the
	 * link info in String format.
	 * 
	 * @return A String containing a exported map of the SubwayMap
	 */
	public static String ExtractSubwayMap()
	{
		String sFinalPath = "";
		NodeLink.LSTExtracted.clear();
		NodeLink.LSTPathways.clear();
		StringBuilder sbPath = new StringBuilder();
		sbPath = IOriginNode.ExtractLink(sbPath);
		for (Iterator<StringBuilder> oFinal = NodeLink.LSTPathways.iterator();oFinal.hasNext(); )
		{
			sFinalPath += oFinal.next().toString();						
		}
		return sbPath.toString()+sFinalPath;
	}
	
	/**
	 * Property NodeName : Represents the name of the Node
	 * @return
	 */
	public String getNodeName()
	{
		return sName;
	}
	/**
	 * This function integrate a link within a Node but will also redefin the Node if need be
	 * If the Node has more than 2 Links, it automatically becomes an Intersection Node.
	 * If the NOde only has 1 Link it becomes a Leaf Node (Exception is the first IOriginNode)
	 * @param oLink : The Link that needs to be added to the Node
	 * @return : The Node either as a normal NOde or as an INtersection
	 */
	public Node AddNextLink(NodeLink oLink)
	{
		Node oFirstNode = this;
		if(oFirstLink == null)
		{
			oFirstLink = oLink;
		}
		else if (oSecondLink == null)
		{
			oSecondLink = oLink;			
		}
		//If The Node already has 2 links then we need to transform that node into a Intersection
		else
		{	
				oFirstNode = new Intersection(oFirstNode, oLink);
		}
		return oFirstNode;
	}
	/**
	 * NextLink property : Represents the active Link of this Node
	 * @return
	 */
	public NodeLink getNextLink()
	{
		if (oFirstLink == oActiveLink)
			return oSecondLink;
		else
			return oFirstLink;
	}
	/**
	 * property ActiveLink : Represent the link that is being used in this Node.  This property
	 * will be used by the NextLink function in order not to use the same link over and over... 
	 */
	public void setActiveLink(NodeLink oLink)
	{
		oActiveLink = oLink;		
	}
	public NodeLink getActiveLink()
	{
		return oActiveLink;
	}
	/**
	 * This method will follow a trail of Nodes until we either
	 * ends up on a Crossroad or find the oDestination node. 
	 * @param oLinker : a non <code>null</code> NodeLinker object
	 * @param oDestination : a non <code>null</code> Node object representing the Node that 
	 * is being searched
	 * @return : a NodeLinker object linking the start Node with the current Node
	 */
	public INodeLinker ComputeNodeDistance(INodeLinker oLinker, Node oDestination)
	{
		INodeLinker oNodeLn = null;
		INodeLinker oLinkN = null;
		NodeLink oLink = this.getNextLink();
		if(this.getNodeName().equals(oDestination.getNodeName()))
		{
			oLinker.setDistance(oLinker.getDistance()+oLink.getIDistance() );
			oLinker.setSecondNode(this);
			return oLinker;
		}
		oLink.setActiveNode(this);
		Node oNextNode = oLink.getNextNode();
		oNextNode.setActiveLink(oLink);
		int iDistance = oLinker.getDistance()+oLink.getIDistance();
		oNodeLn = new NodeLinker(oLinker.getFirsttNode(), oNextNode, iDistance);
		oNodeLn = oNextNode.ComputeNodeDistance(oNodeLn, oDestination);
		if(oLinker.getDistance() ==0)
		{
			this.setActiveLink(oLink);
			oLink = this.getNextLink();
			oLink.setActiveNode(this);
			oNextNode = oLink.getNextNode();
			oNextNode.setActiveLink(oLink);
			oLinkN = new NodeLinker(oLinker.getFirsttNode(), oNextNode, oLink.getIDistance() );
			oLinkN = oNextNode.ComputeNodeDistance(oLinkN, oDestination);
			if(!oNodeLn.getSecondNode().getNodeName().equals(oLinkN.getSecondNode().getNodeName() ) )
			{
				//Create a NodeLinker class
				oNodeLn = new NodeLinkTable(oNodeLn.getFirsttNode(), oNodeLn.getSecondNode(), oNodeLn.getDistance());
				oNodeLn.setAlternateLink(oLinkN);
			}				
		}
		return oNodeLn;
	}
	/**
	 * This function uses a  recursive pattern in order to search for a particular Node 
	 * @param oNode the non null node we are looking for
	 * @return Node target or null if not found...
	 */
	protected Node SearchForNode(Node oNode)
	{
		NodeLink oNextLink;
		Node oCurrentNode = this;
		if (oCurrentNode.getNodeName().equals(oNode.getNodeName() ) )
			return oCurrentNode;
		LSTVisited.add(this);
		oNextLink = oCurrentNode.getNextLink();
		oNextLink.setActiveNode(oCurrentNode);
		oCurrentNode = oNextLink.getNextNode();	
		oCurrentNode.setActiveLink(oNextLink);
		return oCurrentNode.SearchForNode(oNode);
	}
	/**
	 * This (recursive) function is used to get around the entire map link by link in
	 * order to create a String version of the map
	 * 
	 * @param sbResult A <code>StringBuilder</code> object containing the String version of the
	 * map so far
	 * 
	 * @return An updated <code>StringBuilder</code> object of the map (String version)
	 */
	protected StringBuilder ExtractLink(StringBuilder sbResult)
	{
		NodeLink oLink = this.getNextLink();
		if (!NodeLink.LSTExtracted.contains(oLink) )
		{
			oLink.setActiveNode(this);
			NodeLink.LSTExtracted.add(oLink);			
			sbResult.append(oLink.getLinkString() );
			Node oNxtNode = oLink.getNextNode();
			oNxtNode.setActiveLink(oLink);
			sbResult = oNxtNode.ExtractLink(sbResult);
		}
		else
		{
			if(sbResult.lastIndexOf("|") <( sbResult.length()-1) )
				sbResult.append("°|");
		}
		return sbResult;
	}
	protected NodeLink oFirstLink = null;
	protected NodeLink oActiveLink = null;
	protected NodeLink oSecondLink = null;
	private String sName;
}
