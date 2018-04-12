/**
 * 
 */
package com.paco.java.subway;

/**
 * This class specializes the NodeLink class in order to focus exclusively on Intersection Links.
 * Those links are exclusively between Intersection Node and / or to NodeLeaf type node
 * 
 * @author Pascal
 */
public class IntersecLink extends NodeLink {

	/**
	 * This constructor is the same as the NodeLink constructor
	 * 
	 * @param oFirst
	 * @param oSecond
	 * @param iNodeDistance
	 */
	public IntersecLink(Node oFirst, Node oSecond, int iNodeDistance) {
		iDistance = iNodeDistance;
		oFirstNode = oFirst;
		oSecondNode = oSecond;
	}
	/**
	 * This function simply allows to add a distance segment between 2 Intersctions or between this
	 * Intersection and a LeafNode.  This becomes necessary when new Nodes are added... 
	 * 
	 * @param iDistanceAdd
	 */
	public void UpdateDistance(int iDistanceAdd)
	{
		iDistance += iDistanceAdd;
	}
}
