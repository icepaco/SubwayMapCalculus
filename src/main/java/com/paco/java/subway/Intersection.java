package com.paco.java.subway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.paco.java.subway.virtual.INodeLinker;

/**
 * class Interaction : This is a specialization of the Node class.  This specialization represents an
 * intersection holding multiple (more than 2) links. From a functional perspective, this is the more
 * stable of the Node class because the behavior of this (Intersection) Node doesn't change depending
 * on the context...
 * 
 * @author Pascal
 */
public class Intersection extends Node {

	/**
	 * This constructor  transform a regular Node into an Intersection.  Doing so, means creating 
	 * IntersecLink type links between this Node and LeafNode or other Intersection type Node
	 * 
	 * @param oOldNode A non <code>null</code> Node containing all the original information of the Node
	 * 
	 * @param oLink The link that needs to be added to oOldNode in order to transform it into an
	 * Intersection type Node...
	 */
	public Intersection(Node oOldNode, NodeLink oLink) {
		super(oOldNode.getNodeName());
		oLink.setActiveNode(oOldNode);
		lstPaths.add(oLink);
		oLink.ReplaceNode(this);
		UpdateLink(oLink, null);
		oLink = oOldNode.getNextLink();
		oLink.setActiveNode(oOldNode);
		lstPaths.add(oLink);
		oLink.ReplaceNode(this);
		UpdateLink(oLink, null);
		oOldNode.setActiveLink(oLink);
		oLink = oOldNode.getNextLink();
		oLink.setActiveNode(oOldNode);
		lstPaths.add(oLink);
		oLink.ReplaceNode(this);
		UpdateLink(oLink, null);
		if ( !(Node.IOriginNode instanceof Intersection) )
		{
			if (Node.IOriginNode != oOldNode)
			{
				OriginNode oNode = (OriginNode) Node.IOriginNode;
				oNode.RemodelNode();			
			}
			Node.IOriginNode = this;			
		}
	}
	@Override
	public Node AddNextLink(NodeLink oLink)
	{
		lstPaths.add(oLink);
		return this;
	}
	/**
	 * This method creates a specialize NodeLink called IntersecLink which links an Intersection class
	 * with either another Intersection class or with a NodeLeaf class
	 * 
	 * @param oLink a non <code>null</code> IntersecLink link which establishes links between Intersection
	 * and NodeLeaf Node types 
	 */
	public void AddXRoad(IntersecLink oLink)
	{
		ListIterator<IntersecLink> iterLink = lstCrossroad.listIterator();
		IntersecLink oLn;
		oLink.setActiveNode(this);
		Intersection oInter = (Intersection) oLink.getNextNode();
		while (iterLink.hasNext() )
		{
			oLn = (IntersecLink) iterLink.next();
			//If a double exists, we update to the optimal path
			if (oLn.ContainsNode(oInter.getNodeName()) )
			{
				if(oLn.getIDistance() > oLink.getIDistance())
				{
					oLn = oLink;
					iterLink.remove();
				}
				else
					oLink = null;
			}
		}
		if (oLink != null)
			lstCrossroad.add(oLink);
	}
	/**
	 * This method calculates the distance an Intersection Node and this Node
	 * 
	 * @param oInter A non <code>null</code> Intersection Node
	 * 
	 * @return Positive Integer measuring the distance between the 2 Intersection Node...
	 */
	public int CrossroadsDistance(Intersection oInter)
	{
		int iDist = 0;
		for (IntersecLink oLn : lstCrossroad)
		{
			if(oLn.ContainsNode(oInter.getNodeName()))
			{
				iDist = oLn.getIDistance();
			}
		}
		return iDist;
	}
	/**
	 * This method will search for the Next Intersection in the IntersecLink list
	 * 
	 * @return A non <code>null</code> Intersection Node that is hte next one in the list
	 * of Intersection
	 */
	public IntersecLink getNextIntersection()
	{
		IntersecLink oXRoad = null;
		if (iXCounter >= lstCrossroad.size())
			iXCounter = 0;
		else
			oXRoad = lstCrossroad.get(iXCounter++);	
		return  oXRoad;
	}
	/**
	 * This method will try to link 2 Intersection type nodes together
	 * while keeping the smallest path in <code>static</codeo> object Pathfinder.RESULT
	 * 
	 * @param oLinker The link between the 2 Intersection Nodes we are considering
	 * @param oXNode The Intersection node we are looking for
	 * @return an update of the <code>NodeLinker</code> object oLinker
	 */
	public INodeLinker SearchXNode(INodeLinker oLinker, Intersection oXNode)
	{
		IntersecLink oXLink;
		Intersection oNXNode = this;
		INodeLinker oNxLinker = null;
		INodeLinker oOptimalLink = new NodeLinker(oLinker.getFirsttNode(), oLinker.getSecondNode(), 
				Integer.MAX_VALUE);
		if (this.getNodeName().equals(oXNode.getNodeName()))
			return oLinker;
		while ( (oXLink = this.getNextIntersection() ) != null )
		{
			oNxLinker = new NodeLinker(oLinker.getFirsttNode(), oLinker.getSecondNode(),
					oLinker.getDistance() );
			if(!Pathfinder.LSTOriginPath.contains(oXLink))
			{
				Pathfinder.LSTOriginPath.add(oXLink);	
				oXLink.setActiveNode(this);
				oNXNode = (Intersection) oXLink.getNextNode();
				oNxLinker.setSecondNode(oNXNode);
				oNxLinker.setDistance(oNxLinker.getDistance() + oXLink.getIDistance());
				oNxLinker = oNXNode.SearchXNode(oNxLinker, oXNode);
				if(oOptimalLink.getDistance() > oNxLinker.getDistance() )
					oOptimalLink.setDistance(oNxLinker.getDistance() );
			}
		}
			return oOptimalLink;
	}		
	@Override
	public INodeLinker ComputeNodeDistance(INodeLinker oLinker, Node oDestination)
	{
		//Once we hit an intersection the Path searching is done for ComputeNodeDistance
		return oLinker;
	}
	@Override
	protected Node SearchForNode(Node oNode)
	{
		Node oCurrentNode = this;
		if (oCurrentNode.getNodeName().equals(oNode.getNodeName() ) )
			return oCurrentNode;
		Node.LSTVisited.add(this);
		for (NodeLink oLink : lstPaths)
		{
			oLink.setActiveNode(this);
			Node oCurNode = oLink.getNextNode();
			if(Node.LSTVisited.contains(oCurNode))
			{
				oCurrentNode = null;
			}
			else
			{
				oCurNode.setActiveLink(oLink);
				oCurrentNode= oCurNode.SearchForNode(oNode);				
			}
			//We only interrupt the loop if we have a value... Otherwise we go through
			if (oCurrentNode != null)
			{
				return oCurrentNode;
			}
		}
		return null;
	}	
	@Override
	protected StringBuilder ExtractLink(StringBuilder sbResult)
	{
			StringBuilder sbLine;
			for (NodeLink oLink : lstPaths)
			{
					if( (oLink.getClass().equals(NodeLink.class) ) )
					{	
						if(!NodeLink.LSTExtracted.contains(oLink) )
						{						
							oLink.setActiveNode(this);
							sbLine = new StringBuilder();
							sbLine.append("\n+");
							sbLine.append(this.getNodeName());
							sbLine.append(oLink.getLinkString() );
							NodeLink.LSTExtracted.add(oLink);
							Node oNxNode = oLink.getNextNode();
							oNxNode.setActiveLink(oLink);
							NodeLink.LSTPathways.add(sbLine);
							sbLine = oNxNode.ExtractLink(sbLine);
						}
						else
						{
							if(sbResult.lastIndexOf("|") <( sbResult.length()-1) )
								sbResult.append("°|");
						}						
					}
			}
		return sbResult;
	}
	/**
	 * This function will iterate through both links of the old NOde. 
	 * If a Node is a Leaf Node, then the old intersection of that Node will be updated
	 * and linked to this intersection.  Also the LeafNode Intersection NodeLink will now point
	 * to this new Intersection and not the old one...
	 */
	private void UpdateLink(NodeLink oLink, Node oNext)
	{
		Node oNode;
		IntersecLink oNewL;		
		if(oNext == null)
			oLink.setActiveNode(this);
		else
			oLink.setActiveNode(oNext);
		oNode = oLink.getNextNode();
		IntersecLink oPath = new IntersecLink(this, oNode, oLink.getIDistance());
		//Don't Interlink a XNode with itself
		if(oNode instanceof Intersection)
		{
			if (!(getNodeName().equals(oNode.getNodeName() ) ) )
			{
			oNewL = new IntersecLink(this, oNode, oLink.getIDistance());			
			AddXRoad(oNewL);
			Intersection oIn = (Intersection) oNode;
			oIn.AddXRoad(oNewL);
			}
		}
		else if(oNode instanceof LeafNode)
		{
			LeafNode oLeaf = (LeafNode) oNode;
			oLeaf.setIntersec(oPath);
			lstPaths.add(oPath);
		}
		else
		{
			oNode.setActiveLink(oLink);
			oLink = oNode.getNextLink();
			oPath.UpdateDistance(oLink.getIDistance());
			UpdateLink(oPath, oNode);
		}		
	}
	
private List<IntersecLink> lstCrossroad = new ArrayList<IntersecLink>();
private List<NodeLink> lstPaths = new ArrayList<NodeLink>();
private int iXCounter = 0;
}
