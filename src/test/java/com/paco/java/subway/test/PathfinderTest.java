/**
 * 
 */
package com.paco.java.subway.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.paco.java.subway.Node;
import com.paco.java.subway.NodeLink;
import com.paco.java.subway.NodeLinker;
import com.paco.java.subway.Pathfinder;

/**
 * @author Pascal
 *
 */
class PathfinderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
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
		_Link2 = new NodeLink(_Node6, _Node1, 7);
		_Link4 = new NodeLink(_Node2, _Node4, 17);	
		_Link5 = new NodeLink(_Node3, _Node5, 3);
		_Link6 = new NodeLink(_Node4, _Node7, 2);
		_Link7 = new NodeLink(_Node4, _Node6, 12);
		_Link8 = new NodeLink(_Node4, _Node5, 2);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		Node.IOriginNode = null;
	}

	/**
	 * Test method for {@link com.paco.java.subway.Pathfinder#FindOptimalPath()}.
	 */
	@Test
	void testFindOptimalPath() {
		Pathfinder oPathF = new Pathfinder();
		Node oNodeStart = Node.FindNode(_Node3);
		Node oNodeDest = Node.FindNode(_Node7);
		oPathF.setStartNode(oNodeStart);
		oPathF.setDestinationNode(oNodeDest);
		oPathF.FindOptimalPath();
		int iDistance = oPathF.getTotalDistance();
		if (iDistance != 7)
			fail("Wrong distance");
		oNodeStart = Node.FindNode(_Node6);
		oNodeDest = Node.FindNode(_Node7);
		oPathF.setStartNode(oNodeStart);
		oPathF.setDestinationNode(oNodeDest);
		oPathF.FindOptimalPath();
		iDistance = oPathF.getTotalDistance();
		if (iDistance != 14)
			fail("Wrong distance");
		Node oNewNode = new Node("Node8");
		NodeLink oNewLink = new NodeLink(_Node6, oNewNode,4);
		oNodeStart = Node.FindNode(_Node3);
		oNodeDest = Node.FindNode(oNewNode);
		oPathF.setStartNode(oNodeStart);
		oPathF.setDestinationNode(oNodeDest);
		oPathF.FindOptimalPath();
		iDistance = oPathF.getTotalDistance();
		if (iDistance != 21)
			fail("Wrong distance");
		oNodeStart = Node.FindNode(oNewNode);
		oNodeDest = Node.FindNode(_Node3);
		oPathF.setStartNode(oNodeStart);
		oPathF.setDestinationNode(oNodeDest);
		oPathF.FindOptimalPath();
		iDistance = oPathF.getTotalDistance();
		if (iDistance != 21)
			fail("Wrong distance");
		oNodeStart = Node.FindNode(oNewNode);
		oNodeDest = Node.FindNode(_Node7);
		oPathF.setStartNode(oNodeStart);
		oPathF.setDestinationNode(oNodeDest);
		oPathF.FindOptimalPath();
		iDistance = oPathF.getTotalDistance();
		if (iDistance != 18)
			fail("Wrong distance");		
		oNodeStart = Node.FindNode(_Node1);
		oNodeDest = Node.FindNode(_Node5);
		oPathF.setStartNode(oNodeStart);
		oPathF.setDestinationNode(oNodeDest);
		oPathF.FindOptimalPath();
		iDistance = oPathF.getTotalDistance();
		if (iDistance != 18)
			fail("Wrong distance");		
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
