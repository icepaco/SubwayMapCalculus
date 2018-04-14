/**
 * 
 */
package com.paco.java.subway.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.paco.java.subway.Intersection;
import com.paco.java.subway.Node;
import com.paco.java.subway.NodeLink;


public class IntersectionTest {

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
		//Extact second Name from the line
		_Node4 = new Node("Node4");

		//Extract first name from the line 
		_Node5 = new Node("Node5");
		_Node3 = new Node("Node3");
		//Extact second Name from the line
		_Node6 = new Node("Node6");
		_Node7 = new Node("Node7");
		_Link1 = new NodeLink(_Node1, _Node2, 5);		
		_Link3 = new NodeLink(_Node2, _Node3, 10);	
		_Link4 = new NodeLink(_Node2, _Node4, 17);	
		
		_Link2 = new NodeLink(_Node6, _Node1, 7);
		_Link5 = new NodeLink(_Node3, _Node5, 3);
		_Link6 = new NodeLink(_Node4, _Node7, 2);
		_Link7 = new NodeLink(_Node4, _Node6, 12);
		_Link8 = new NodeLink(_Node4, _Node5, 2);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Node.IOriginNode = null;
	}

	/**
	 * Test method for {@link pascal.java.subway.node.Intersection#SearchForNode(pascal.java.subway.node.Node)}.
	 */
	@Test
	public void testSearchForNode() {
		Node oNode = Node.FindNode(_Node6);
		_Link2.setActiveNode(_Node1);
		Node oRes = _Link2.getNextNode();
		if( !(oRes.getNodeName().equals(oNode.getNodeName() ) ) )
		{
			fail("Can't find Node6");
		}
	}

	/**
	 * Test method for {@link pascal.java.subway.node.Intersection#Intersection(pascal.java.subway.node.Node, pascal.java.subway.node.NodeLink)}.
	 */
	@Test
	public void testIntersection() {
		_Link3.setActiveNode(null);
		_Link6.setActiveNode(null);
		Intersection oIn1 = (Intersection) _Link3.getNextNode();
		Intersection oIn2 = (Intersection) _Link6.getNextNode();
		int iDi = oIn1.CrossroadsDistance(oIn2);
		if(iDi != 15)
			fail("Wrong distance");
	}
	@Test
	public void testExtractMap() {
		String sTest = Node.ExtractSubwayMap();
		if (!sTest.equals("\n+Node2-17-Node4院\n+Node4-12-Node6-7-Node1-5-Node2院\n+Node2-10-Node3-3-Node5-2-Node4院\n+Node4-2-Node7|") )
			fail("Wrong path");
		Node oNode = Node.FindNode(_Node6);
		Node oOtherNode = new Node("Node8");
		NodeLink oLink = new NodeLink(oNode, oOtherNode,12);
		sTest = Node.ExtractSubwayMap();
		if (!sTest.equals("\n+Node2-17-Node4院\n+Node4-12-Node6院\n+Node6-12-Node8|\n+Node6-7-Node1-5-Node2院\n+Node2-10-Node3-3-Node5-2-Node4院\n+Node4-2-Node7|") )
			fail("Wrong path");				
	}
	private Node _Node1;
	private Node _Node2;
	private Node _Node3;
	private Node _Node4;
	private Node _Node5;
	private Node _Node6;
	private Node _Node7;
	private NodeLink _Link1;
	private NodeLink _Link2;
	private NodeLink _Link3;
	private NodeLink _Link4;
	private NodeLink _Link5;
	private NodeLink _Link6;
	private NodeLink _Link7;
	private NodeLink _Link8;
}
