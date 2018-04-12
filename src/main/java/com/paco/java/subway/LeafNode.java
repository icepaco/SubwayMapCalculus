/**
 * 
 */
package com.paco.java.subway;

import com.paco.java.subway.virtual.INodeLinker;

/**
 * This class is a specialization of the Node class.  It represents the end of a branch on the tree
 * 
 * @author Pascal
 */
public class LeafNode extends Node {

	/**
	 * Just a call to the super constructor
	 * 
	 * @param sNodeName Name of the Node
	 */
	public LeafNode(String sNodeName) {
		super(sNodeName);
	}
	/**
	 * property Intersec : Represents the Link between this leaf and the connecting Intersection
	 * Node
	 */
	public IntersecLink getIntersec()
	{
		return oIntersec;
	}
	public void setIntersec(IntersecLink oLn)
	{
		oIntersec = oLn;
	}
	@Override
	public Node AddNextLink(NodeLink oLink)
	{
		Node oFinal = this;
		//If FirstLink is null then we update it. Otherwise this becomes a Node
		if(oFirstLink == null)
		{
			oFirstLink = oLink;	
		}
		else
		{
			oLink.setActiveNode(this);
			Node oNod = oLink.getNextNode();
			IntersecLink oIn = null;
			if(oNod instanceof LeafNode)
			{
				LeafNode oLeaf = (LeafNode) oNod;
				//Verifying if we have a new LeafNode
				if( (oIn =oLeaf.getIntersec() ) != null)
				{
					//Otherwise we have 2 Leaf colliding.  Need to create the Crossroads
					oIn.setActiveNode(oLeaf);
					Intersection oMilan = (Intersection) oIn.getNextNode();
					oIntersec.setActiveNode(this);
					Intersection oInter = (Intersection) oIntersec.getNextNode();
					//Don't Interlink a XNode with itself
					if (!(oInter.getNodeName().equals(oMilan.getNodeName() ) ) )
					{
					oIntersec.UpdateDistance(oIn.getIDistance() );
					IntersecLink oNewLink = new IntersecLink(oInter, oMilan, oIntersec.getIDistance());
					oInter.AddXRoad(oNewLink);
					oMilan.AddXRoad(oNewLink);
					}
				}
				else
				{
					if(oIntersec != null)
					{
						oIntersec.UpdateDistance(oLink.getIDistance() );
						//Xferring the Intersec
						oLeaf.setIntersec(oIntersec);						
					}
				}
			}
			else if(oNod instanceof Intersection && oIntersec != null)
			{
				Intersection oMilan = (Intersection) oNod;
				oIntersec.setActiveNode(this);
				Intersection oInter = (Intersection) oIntersec.getNextNode();
				//Don't Interlink a XNode with itself
				if (!(oInter.getNodeName().equals(oMilan.getNodeName() ) ) )
				{
				oIntersec.UpdateDistance(oLink.getIDistance() );
				IntersecLink oNewLink = new IntersecLink(oInter, oMilan, oIntersec.getIDistance());
				oInter.AddXRoad(oNewLink);
				oMilan.AddXRoad(oNewLink);
				}
			}
			oFinal= new Node(this.getNodeName());
			oFinal.AddNextLink(oFirstLink);
			oFinal.AddNextLink(oLink);
			oFirstLink.ReplaceNode(oFinal);
			oLink.ReplaceNode(oFinal);
			
		}
		return oFinal;
	}
	@Override
	public NodeLink getNextLink()
	{
		return null;
	}
	@Override
	public INodeLinker ComputeNodeDistance(INodeLinker oLinker, Node oDestination)
	{
		NodeLink oLink = this.oFirstLink;
		if(this.getNodeName().equals(oDestination.getNodeName()))
			return oLinker;
		if(oLinker.getDistance() == 0)
		{
			INodeLinker oNodeLn = null;
			oLink.setActiveNode(this);
			Node oNextNode = oLink.getNextNode();
			oNextNode.setActiveLink(oLink);
			int iDistance = oLinker.getDistance()+oLink.getIDistance();
			oNodeLn = new NodeLinker(oLinker.getFirsttNode(), oNextNode, iDistance);
			oNodeLn = oNextNode.ComputeNodeDistance(oNodeLn, oDestination);
			return oNodeLn;
		}
		int iDistance = oIntersec.getIDistance() -  oLinker.getDistance();
		oIntersec.setActiveNode(this);
		oLinker.setSecondNode(oIntersec.getNextNode() );
		oLinker.setDistance(iDistance);
		return oLinker;
	}
	@Override
	protected Node SearchForNode(Node oNode)
	{
		Node oCurrentNode = this;
		if (oCurrentNode.getNodeName().equals(oNode.getNodeName() ) )
			return oCurrentNode;
		else
			return null;
	}
	@Override
	protected StringBuilder ExtractLink(StringBuilder sbResult)
	{
		if(sbResult.lastIndexOf("|") <( sbResult.length()-1) )
			sbResult.append("|");		
		return sbResult;
	}
	private IntersecLink oIntersec = null;
	
}
