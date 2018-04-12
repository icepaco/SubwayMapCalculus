package com.paco.java.subway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a link between 2 Node. The class also contains the distance between
 * the 2 Nodes
 * 
 * @author Pascal
 */
public class NodeLink {
	public static List<NodeLink> LSTExtracted = new ArrayList<NodeLink>();
	public static List<StringBuilder> LSTPathways = new ArrayList<StringBuilder>();
	/**
	 * Default super constructor
	 */
	public NodeLink()
	{
		//Nothing to do
	}
	
	/**
	 * This constructor is used to create an association between 2 Node
	 * 
	 * @param oFirst A non <code>null</code> Node (or sub-type)
	 * 
	 * @param oSecond A non <code>null</code> Node (or sub-type)
	 * 
	 * @param iNodeDistance The distance between the 2 Node
	 */
	public NodeLink(Node oFirst, Node oSecond, int iNodeDistance)
	{
		iDistance = iNodeDistance;
		oFirstNode = oFirst;
		oSecondNode = oSecond;
		Node oNode = Node.FindNode(oFirstNode);
		if(oNode != null)
		{
			oFirstNode = oNode;
		}
		else
		{
			oFirstNode = new LeafNode(oFirstNode.getNodeName());
		}
		oNode = Node.FindNode(oSecondNode);
		if(oNode != null)
		{
			oSecondNode = oNode;
		}
		else
		{
			oSecondNode = new LeafNode(oSecondNode.getNodeName());
		}
		oFirstNode = oFirstNode.AddNextLink(this);			
		oSecondNode = oSecondNode.AddNextLink(this);			
	}
	/**
	 * Comparaison function that returns true if oNode is contained withint this NodeLink
	 * @param oNode : A non <code>null</code> node name to compare
	 * @return 		: True if this node name exists in this Link
	 */
	public boolean ContainsNode(String sNodeName)
	{
		Node oNode = null;
		if(sNodeName == oFirstNode.getNodeName() )
			oNode = oFirstNode;
		else if (sNodeName == oSecondNode.getNodeName() )
			oNode = oSecondNode;
		return oNode != null;
	}
	/**
	 * This method will replace the node of a Link.  Name comparaison is used to replace the
	 * correct Node
	 * @param oNewNode A non <code>null</code> Node that will replace one of the 2 node of this link.
	 */
	public void ReplaceNode(Node oNewNode)
	{
		if(oNewNode.getNodeName() == oFirstNode.getNodeName())
		{
			oFirstNode = oNewNode;
		}
		else
		{
			oSecondNode = oNewNode;
		}
	}
	/**
	 * Getter of firstNode
	 */
	public Node getFirstNode() {
	 	 return oFirstNode; 
	}
	/**
	 * Getter of secondNode
	 */
	public Node getSecondNode() {
	 	 return oSecondNode; 
	}
	/**
	 * Getter of iDistance
	 */
	public Integer getIDistance() {
	 	 return iDistance; 
	}
	
	/**
	 * Setter of ActiveNode
	 */
	public void setActiveNode(Node activeNode) { 
		 oActiveNode = activeNode; 
	}
	/**
	 * property NextNode : Represent the next non <code>null</code> node of that link.
	 * This assumes that the <code>activeNode</code> property was set...
	 */
	public Node getNextNode()
	{
		if(oActiveNode != null && oActiveNode.getNodeName() == oFirstNode.getNodeName())
			return oSecondNode;
		else
			return oFirstNode;
	}
	/**
	 * This method will export a Link in a String format
	 * 
	 * @return A String representing the NodeLink
	 */
	public String getLinkString()
	{
		StringBuilder sbLine = new StringBuilder();
		sbLine.append('-');
		sbLine.append(iDistance);
		sbLine.append('-');
		if(this.oActiveNode == this.oFirstNode)
			sbLine.append(oSecondNode.getNodeName());
		else
			sbLine.append(oFirstNode.getNodeName());
		return sbLine.toString();
	}
	/**
	 * 
	 */
	protected Integer iDistance;
	/**
	 * 
	 */
	protected Node oFirstNode;
	/**
	 * 
	 */
	protected Node oSecondNode;
	/**
	 * 
	 */
	private Node oActiveNode;
}