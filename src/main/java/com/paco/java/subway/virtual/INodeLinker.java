package com.paco.java.subway.virtual;

import com.paco.java.subway.Node;
import com.paco.java.subway.NodeLinkTable;
import com.paco.java.subway.NodeLinker;

/**
 * @author Pascal
 *
 *Interface used for the NodeLinker and NodeLinkTable classes.  
 */
public interface INodeLinker {
	//Getters
	public Node getFirsttNode();
	public Node getSecondNode();
	public int getDistance();
	public INodeLinker getAlternatePath();	
	//Setters
	public void setSecondNode(Node oNode);
	public void setDistance(int iDistance);	
	/**
	 * This function allows the addition of a new Path that will need to be computed and compared
	 * with the chosen path and any other alternate paths
	 * @param newPath A non <code>null</code> INodeLinker object
	 */
	public void setAlternateLink(INodeLinker newPath);	
	/**
	 * This method will obtain the optimal (shortest) path between itself and the 2 node
	 * obtained in the parameter link
	 * @param oLink A non <code>null</code> INodeLinker instance
	 * @return A positive number corresponding to the distance between the elements
	 */
	public int ComputeOptimalDistance(INodeLinker oLink);
}
