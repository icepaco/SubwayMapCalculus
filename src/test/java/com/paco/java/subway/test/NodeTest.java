package com.paco.java.subway.test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.paco.java.subway.Node;
import com.paco.java.subway.NodeLink;
import com.paco.java.subway.NodeLinker;
import com.paco.java.subway.Pathfinder;

class NodeTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		Node.IOriginNode = null;		
	}

	@Test
	void testFindNode() {
		Node oTest = Node.FindNode(null);
		if(oTest != null)
			fail("Not processing empty node system");
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
		String sTest = Node.ExtractSubwayMap();
		if (!sTest.equals("Node1-5-Node2-10-Node3|") )
			fail("Wrong path");
		
		_Link2 = new NodeLink(_Node6, _Node1, 7);
				
		sTest = Node.ExtractSubwayMap();
		if (!sTest.equals("Node1-5-Node2-10-Node3|Node1-7-Node6|") )
			fail("Wrong path");
		Node oNodeStart = Node.FindNode(_Node6);
		Node oNodeDest = Node.FindNode(_Node3);
		Pathfinder oPathF = new Pathfinder();
		oPathF.setStartNode(oNodeStart);
		oPathF.setDestinationNode(oNodeDest);
		oPathF.FindOptimalPath();
		int iDistance = oPathF.getTotalDistance();
		if (iDistance != 22)
			fail("Wrong distance");
		
		_Link4 = new NodeLink(_Node2, _Node4, 17);	
		sTest = Node.ExtractSubwayMap();
		if (!sTest.equals("\n+Node2-17-Node4|\n+Node2-10-Node3|\n+Node2-5-Node1-7-Node6|") )
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
