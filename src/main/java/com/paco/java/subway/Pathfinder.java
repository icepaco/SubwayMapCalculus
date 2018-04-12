package com.paco.java.subway;


import java.util.ArrayList;
import java.util.List;

import com.paco.java.subway.virtual.INodeLinker;


/**
 * This class is the main class to compute the optimal distance between any 2 Nodes of a Subway Map.
 * This class should be fired after a Subway Map is setup...
 * 
 * @author Pascal
 */
public class Pathfinder {
	public static List<NodeLink> LSTOriginPath = new ArrayList<NodeLink>();
	/**
	 * Function FindOptimalPath : This is the main method of the class.  It will take find the
	 * shortest path between 2 nodes.
	 * First we find the intersection around both Nodes (if it exists).  Then we simply pathfind
	 * The shortest way between those 2 Intersection.
	 * Note : If there are less than 2 Intersections in the path, then it means that it will be
	 *        straight path and therefore there will be only 1 answer...
	 */
	public void FindOptimalPath() {
		INodeLinker oLinker = new NodeLinker(this.startNode, null,0);
		Intersection oFirstCross = null;
		Intersection oSecondCross = null;		
		int iDistanceX1 = 0;
		int iDistanceX2 = 0;
		Node oCurrent = this.startNode;
		//If IOrigin is not an Intersection then we have a straight path between the 2 nodes
		if ( !(Node.IOriginNode instanceof Intersection) )
		{
			NodeLink oLink =oCurrent.oFirstLink;
			NodeLink oFirst = oLink;
			oCurrent.setActiveLink(oLink);
			oLink.setActiveNode(oCurrent);
			oLinker = new NodeLinker(this.startNode, oCurrent, 0);
			oLinker = oCurrent.ComputeNodeDistance(oLinker, this.destinationNode);
			if(!oLinker.getSecondNode().getNodeName().equals(this.destinationNode.getNodeName()))
			{
				//Reset the Linker
				oLinker.setDistance(0);
				oCurrent = this.startNode;
				oCurrent.setActiveLink(oFirst);
				oLinker = oCurrent.ComputeNodeDistance(oLinker, destinationNode);
			}			
			totalDistance = oLinker.getDistance();
		}
		else
		{
			//Get Intersection from Start Node :
			oLinker = oCurrent.ComputeNodeDistance(oLinker, this.destinationNode);
			INodeLinker oLinker2 = new NodeLinker(this.destinationNode, null,0);
			oLinker2 = destinationNode.ComputeNodeDistance(oLinker2, this.startNode);
			totalDistance = oLinker.ComputeOptimalDistance(oLinker2);
		}
		
	}
	
	/**
	 * Setter of startNode
	 */
	public void setStartNode(Node startNode) { 
		 this.startNode = startNode; 
	}
	/**
	 * Setter of destinationNode
	 */
	public void setDestinationNode(Node destinationNode) { 
		 this.destinationNode = destinationNode; 
	}
	/**
	 * Getter of totalDistance
	 */
	public Integer getTotalDistance() {
	 	 return totalDistance; 
	}

	/**
	 * Starting point to compute the shortest pathway between this node and the 'Destination' node...
	 */
	private Node startNode;
	/**
	 * End point to compute the shortest pathway between this node and the 'Start' node...
	 */
	private Node destinationNode;
	/**
	 * Natural number (> 0) measuring the smallest total distance 
	 */
	private Integer totalDistance;
	/**
	 * List of path between Start Node and Destination Node
	 */
	private List<NodeLink> lstDestinationPath;
	
}