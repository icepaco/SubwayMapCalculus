/**
 * 
 */
package com.paco.java.subway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.paco.java.subway.virtual.INodeLinker;

/**
 * This class is a specialization of the NodeLinker class.  This class will be called when
 * we are looking for optimized distance of a Node class that is linked to 2 Node
 * 
 * @author Pascal
 */
public class NodeLinkTable extends NodeLinker implements INodeLinker {

	public NodeLinkTable() {
		//Nothing to do
	}

	/**
	 * Repeats the same operation as the NodeLinker constructor but also add the linker to
	 * a list
	 * 
	 * @param oNode1 A non <code>null</code> Node (or subtype)
	 * 
	 * @param oNode2 A non <code>null</code> Node (or subtype)
	 * 
	 * @param iDistance A positive integer that measure the distance between the 2 Node
	 */
	public NodeLinkTable(Node oNode1, Node oNode2, int iDistance) {
		super(oNode1, oNode2, iDistance);
		NodeLinker oLn = new NodeLinker(oNode1, oNode2, iDistance);
		lstLinkTable.add(oLn);
	}
	public void setAlternateLink(INodeLinker newPath)
	{
		lstLinkTable.add(newPath);
	}
	public INodeLinker getAlternatePath()
	{
		INodeLinker oNext = null;
		if(iIndex >= lstLinkTable.size() )
		{
			iIndex = 0;
		}
		else
		{
			oNext = lstLinkTable.get(iIndex++);
		}
		return oNext;
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
	@Override
	protected int ComputeOptimalDistance(NodeLinker oLink)
	{
		int iFinalDistance =Integer.MAX_VALUE;
			INodeLinker oCourant1;
			Intersection oCross2 = (Intersection) oLink.getSecondNode();
			while ( (oCourant1 = getAlternatePath() ) != null)
			{
				int iDistance = oCourant1.getDistance();
				Pathfinder.LSTOriginPath.clear();
				Intersection oX1 = (Intersection) oCourant1.getSecondNode();
				INodeLinker oFinal = oX1.SearchXNode(oCourant1, oCross2);
				iDistance = oFinal.getDistance()+oLink.getDistance();
				if(iFinalDistance > iDistance)
					iFinalDistance = iDistance;
			}
		return iFinalDistance;
	}
	@Override
	protected int ComputeOptimalDistance(NodeLinkTable oLink)
	{
		int iDistance = Integer.MAX_VALUE;
		int iDistanceX1 = 0;
		int iDistanceX2 = 0;
		int iFinalDistanceX2 = Integer.MAX_VALUE;
		INodeLinker oCourant1;
		INodeLinker oCourant2;
			while ( (oCourant1 = getAlternatePath() ) != null)
			{
				iFinalDistanceX2 = Integer.MAX_VALUE;
				iDistanceX1 = oCourant1.getDistance();
				Intersection oX1 = (Intersection) oCourant1.getSecondNode();
				while ( (oCourant2 = oLink.getAlternatePath() ) != null)
				{
					iDistanceX2 = oCourant2.getDistance();
					Intersection oX2 = (Intersection) oCourant2.getSecondNode();
					oX1.SearchXNode(oLink, oX2);
					if (iFinalDistanceX2 > iDistanceX2)
						iFinalDistanceX2 = iDistanceX2;
				}
				if (iDistance > iDistanceX1 + iFinalDistanceX2)
					iDistance = iDistanceX1+iFinalDistanceX2;
			}
		return iDistance;
	}
	
	private List<INodeLinker> lstLinkTable = new ArrayList<INodeLinker>();
	private int iIndex=0;
}
