/**
 * 
 */
package com.paco.java.subway.test;


import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.paco.java.subway.Intersection;
import com.paco.java.subway.Node;
import com.paco.java.subway.NodeLink;


/**
 * @author Pascal
 *
 */
public class NodeLinkTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//Extract first name from the line 
		_Node1 = new Node("Node1");
		//Extact second Name from the line
		_Node2 = new Node("Node2");
		
		//Extract first name from the line 
		_Node3 = new Node("Node3");
		//Extact second Name from the line
		_Node4 = new Node("Node4");

		//Extract first name from the line 
		_Node5 = new Node("Node5");
		//Extact second Name from the line
		_Node6 = new Node("Node6");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Node.IOriginNode = null;
	}

	
	/**
	 * Test method for {@link pascal.java.subway.node.Node#FindNode(pascal.java.subway.node.Node, pascal.java.subway.node.Node, int)}.
	 */
	@Test
	public void testFindNode() {
		_Link1 = new NodeLink(_Node1, _Node2, 5);
		_Link2 = new NodeLink(_Node2, _Node3, 7);
		_Link2.setActiveNode(null);
		Node oNode = new Node("Node2");
		Node oRes = Node.FindNode(oNode);
		Node oOri = _Link2.getNextNode();
		if (oRes != oOri)
		{
			fail("Didn't find Node2");			
		}
	}
	
	
	/**
	 * Test method for {@link pascal.java.subway.node.NodeLink#NodeLink(pascal.java.subway.node.Node, pascal.java.subway.node.Node, int)}.
	 */
	@Test
	public void testNodeLink() {
		_Link1 = new NodeLink(_Node1, _Node2, 5);
		_Link2 = new NodeLink(_Node2, _Node3, 10);
		_Link4 = new NodeLink(_Node2, _Node4, 8);	
		_Link3 = new NodeLink(_Node6, _Node1, 7);
		//Extract the distance from the line
		if(!_Link2.getFirstNode().getNodeName().equals(Node.IOriginNode.getNodeName() ) )
		{
			fail("Didn't load Node1");
		}
		_Node2 = _Link1.getSecondNode();
		if ((_Node2 instanceof Intersection) == false)
		{
			fail("Didn't xform Node2 into an Intersection");			
		}
	}
	

	private Node _Node1;
	private Node _Node2;
	private Node _Node3;
	private Node _Node4;
	private Node _Node5;
	private Node _Node6;
	private NodeLink _Link1;
	private NodeLink _Link2;
	private NodeLink _Link3;
	private NodeLink _Link4;

}
