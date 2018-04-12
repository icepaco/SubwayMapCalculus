/**
 * 
 */
package com.paco.java.subway;

import com.paco.java.subway.virtual.INodeLinker;

/**
 * This class specializes the LeafNode. It represents the Node where a lot of operations
 * starts...
 * 
 * @author Pascal
 *
 */
public class OriginNode extends LeafNode {

	/**
	 * Same as Node constructor
	 * 
	 * @param sNodeName Name of the Node
	 */
	public OriginNode(String sNodeName) {
		super(sNodeName);
		Node.IOriginNode = this;
	}
	/**
	 * This special function only operates on OriginNode types.  It is called once the origin pointer
	 * is moved from the Node.  This function will remove the OriginNode class and replace it with either
	 * a Node or a NodeLeaf class
	 */
	public void RemodelNode()
	{
		if(this.oFirstLink != null && this.oSecondLink != null)
		{
			//This is now a normal Node
			Node NouvNode = new Node(this.getNodeName() );
			this.oFirstLink.ReplaceNode(NouvNode);
			this.oSecondLink.ReplaceNode(NouvNode);
			NouvNode.oFirstLink = this.oFirstLink;
			NouvNode.oSecondLink = this.oSecondLink;
		}
		else if (this.oFirstLink != null)
		{
			Node NouvNode = new LeafNode(getNodeName() );
			oFirstLink.ReplaceNode(NouvNode);
			NouvNode.oFirstLink = oFirstLink;
		}
		else if (this.oSecondLink != null)
		{
			Node NouvNode = new LeafNode(getNodeName() );
			oSecondLink.ReplaceNode(NouvNode);
			NouvNode.oFirstLink = oSecondLink;
		}
		
	}

	
	@Override
	public NodeLink getNextLink()
	{
		if (oFirstLink == oActiveLink)
		{			
			if(bPass)
			{
				bPass = false;
				return oSecondLink;
			}
			else
				return null;
		}
		else
		{
			oActiveLink = oFirstLink;
			return oFirstLink;		
		}
	}
	@Override
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
				Node.IOriginNode = oFirstNode;
		}
		return oFirstNode;
	}
	@Override
	public void setActiveLink(NodeLink oLink)
	{
		if(oLink == oFirstLink)
			bPass = true;
		oActiveLink = oLink;
	}
	@Override
	public INodeLinker ComputeNodeDistance(INodeLinker oLinker, Node oDestination)
	{
		INodeLinker oNodeLn = null;
		INodeLinker oLinkN = null;
		NodeLink oLink = null;
		if(this.getNodeName().equals(oDestination.getNodeName()))
		{
			oLinker.setDistance(oLinker.getDistance()+oLink.getIDistance() );
			oLinker.setSecondNode(this);
			return oLinker;
		}
		if(oLinker.getDistance() == 0)
		{
			oLink = oFirstLink;
			oLink.setActiveNode(this);
			Node oNextNode = oLink.getNextNode();
			oNextNode.setActiveLink(oLink);
			int iDistance = oLinker.getDistance()+oLink.getIDistance();
			oNodeLn = new NodeLinker(oLinker.getFirsttNode(), oNextNode, iDistance);
			oNodeLn = oNextNode.ComputeNodeDistance(oNodeLn, oDestination);
			if (oSecondLink != null )
			{
				oLink = oSecondLink;
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
		}
		else if (oFirstLink != null && oSecondLink != null)
		{
			oLink = getNextLink();
			Node oNextNode = oLink.getNextNode();
			oNextNode.setActiveLink(oLink);
			int iDistance = oLinker.getDistance()+oLink.getIDistance();
			oNodeLn = new NodeLinker(oLinker.getFirsttNode(), oNextNode, iDistance);
			oNodeLn = oNextNode.ComputeNodeDistance(oNodeLn, oDestination);
		}
		return oNodeLn;
	}
	@Override
	protected Node SearchForNode(Node oNode)
	{
		Node oFinalNode=null;
		NodeLink oNextLink;
		Node oCurrentNode = this;
		if (oCurrentNode.getNodeName().equals(oNode.getNodeName() ) )
			return oCurrentNode;
		LSTVisited.add(this);
		if (oCurrentNode.oFirstLink != null)
		{
			oNextLink = oCurrentNode.oFirstLink;
			oNextLink.setActiveNode(oCurrentNode);
			oCurrentNode = oNextLink.getNextNode();	
			oCurrentNode.setActiveLink(oNextLink);	
			oFinalNode = oCurrentNode.SearchForNode(oNode);
		}
		if(oFinalNode == null && this.oSecondLink != null)
		{
			oNextLink = this.oSecondLink;
			oNextLink.setActiveNode(this);
			oCurrentNode = oNextLink.getNextNode();	
			oCurrentNode.setActiveLink(oNextLink);	
			oFinalNode = oCurrentNode.SearchForNode(oNode);			
		}
		return oFinalNode;
	}	
	@Override
	protected StringBuilder ExtractLink(StringBuilder sbResult)
	{
		NodeLink oLink = this.oFirstLink;
		if (oLink != null )
		{
			oLink.setActiveNode(this);
			NodeLink.LSTExtracted.add(oLink);			
			sbResult.append(this.getNodeName());
			sbResult.append(oLink.getLinkString() );
			Node oNxtNode = oLink.getNextNode();
			oNxtNode.setActiveLink(oLink);			
			sbResult = oNxtNode.ExtractLink(sbResult);
		}
			oLink = this.oSecondLink;
			if(oLink != null)
			{
				oLink.setActiveNode(this);
				NodeLink.LSTExtracted.add(oLink);				
				sbResult.append(this.getNodeName());
				sbResult.append(oLink.getLinkString() );
				Node oNxtNode = oLink.getNextNode();
				oNxtNode.setActiveLink(oLink);				
				sbResult = oNxtNode.ExtractLink(sbResult);				
			}
		return sbResult;
	}
	
	private boolean bPass = true;
}
