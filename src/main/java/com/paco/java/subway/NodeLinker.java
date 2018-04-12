package com.paco.java.subway;

import com.paco.java.subway.virtual.INodeLinker;

/**
 * This class is a shadow class of the NodeLink class. It is used to strictly carry the
 * distance between 2 Nodes and doesn't handle the linking of the 2 Nodes.  Therefore
 * instance of this class can be used to compute the distance of any 2 Nodes...
 * This class implements the INodeLinker Interface
 * 
 * @author Pascal
 */
public class NodeLinker implements INodeLinker {

	public NodeLinker()
	{
		//Nothing to do
	}
	/**
	 * This constructor associates the parameters of the NodeLinker instance
	 * 
	 * @param oNode1 A non <code>null</code> Node (or subtype)
	 * 
	 * @param oNode2 A non <code>null</code> Node (or subtype)
	 * 
	 * @param iDistance A positive integer that measure the distance between the 2 Node
	 */
	public NodeLinker(Node oNode1, Node oNode2, int iDistance)
	{
		_oNode1 = oNode1;
		_oNode2 = oNode2;
		_iDistance = iDistance;
	}
	public Node getFirsttNode()
	{
		return _oNode1;
	}
	public Node getSecondNode()
	{
		return _oNode2;
	}
	public int getDistance()
	{
		return _iDistance;
	}
	public void setSecondNode(Node oNode)
	{
		_oNode2 = oNode;
	}
	public void setDistance(int iDistance)
	{
		_iDistance = iDistance;
	}
	public void setAlternateLink(INodeLinker newPath)
	{
		//NOTHINGTODO
	}
	public INodeLinker getAlternatePath()
	{
		return null;
	}
	public int ComputeOptimalDistance(INodeLinker oLink)
	{
		int iDistance=0;
		if(oLink instanceof NodeLinkTable)
			iDistance = ComputeOptimalDistance( (NodeLinkTable) oLink);
		else
			iDistance = ComputeOptimalDistance( (NodeLinker) oLink);
		return iDistance;
	}
	/**
	 * This method is called by it's sister method INodeLinker This method handles the case of a
	 * NodeLinker to NodeLinker in order to compute the Distance that exists between 2 of (possible 4)
	 * Nodes in play.  
	 * 
	 * @param oLink The other link of NodeLinker type
	 * 
	 * @return A positive Integer that measures the distance between the Start and the Destination
	 * Node
	 */
	protected int ComputeOptimalDistance(NodeLinker oLink)
	{
		int iDistance =0;
		if(getSecondNode().getNodeName().equals(oLink.getFirsttNode().getNodeName()))
			iDistance = getDistance();
		else if(getSecondNode().getNodeName().equals(oLink.getSecondNode().getNodeName()))
			iDistance = getDistance()+oLink.getDistance();		
		else
		{
			Pathfinder.LSTOriginPath.clear();
			Intersection oCross1 = (Intersection) getSecondNode();
			Intersection oCross2 = (Intersection) oLink.getSecondNode();
			INodeLinker oXLink = oCross1.SearchXNode((INodeLinker) oLink, oCross2);
			iDistance = oXLink.getDistance()+getDistance();
		}
		return iDistance;
	}
	/**
	 * This method is called by it's sister method INodeLinker This method handles the case of a
	 * NodeLinker to NodeLinkTable in order to compute the Distance that exists between 2 of (possible 4)
	 * Nodes in play.  
	 * 
	 * @param oLink The other link of NodeLinkTable type
	 * 
	 * @return A positive Integer that measures the distance between the Start and the Destination
	 * Node
	 */
	protected int ComputeOptimalDistance(NodeLinkTable oLink)
	{		
		int iDistance = Integer.MAX_VALUE;
		int iCurDistance = 0;
			INodeLinker oCourant1;
			Intersection oCross2 = (Intersection) getSecondNode();
			while ( (oCourant1 = oLink.getAlternatePath() ) != null)
			{
				iCurDistance = oCourant1.getDistance();
				Pathfinder.LSTOriginPath.clear();				
				Intersection oX1 = (Intersection) oCourant1.getSecondNode();
				INodeLinker oFinal = oX1.SearchXNode((INodeLinker) oLink, oCross2);
				iCurDistance = oFinal.getDistance()+getDistance();
				if(iDistance > iCurDistance)
					iDistance = iCurDistance;
			}
		return iDistance;
	}
	private Node _oNode1;
	private Node _oNode2;
	private int _iDistance;
}
