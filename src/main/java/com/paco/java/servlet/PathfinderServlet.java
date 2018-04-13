package com.paco.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paco.java.subway.Node;
import com.paco.java.subway.Pathfinder;

/**
 * Servlet implementation class PathfinderServlet This servlet is called when the user wants
 * to compute the optimal distance between 2 nodes.
 * Note : This servlet's call must follow a successfull map build through calls from NodeCreatorServlet
 * 
 * @author Pascal
 *
 */
@WebServlet(description = "This servlet will take 2 parameters : NodeStart and NodeDestination.  Then it will calculate the optimal path between those 2 nodes", urlPatterns = { "/PathfinderServlet" })
public class PathfinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PathfinderServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Extract Start point.  This operation should only be done once
		String sStart = request.getParameter("StartNode");
		Node oStart = new Node(sStart);
		oStart = Node.FindNode(oStart);
		//Extract and setup the Destination node
		String sDestination = request.getParameter("DestinationNode");
		Node oDestination = new Node(sDestination);
		oDestination  = Node.FindNode(oDestination);
		//Computing optimal pathways
		Pathfinder oPath = new Pathfinder();
		oPath.setStartNode(oStart);
		oPath.setDestinationNode(oDestination);
		oPath.FindOptimalPath();
	    PrintWriter out = response.getWriter();
	    out.println("Distance between:"+sStart+" and "+sDestination+" is:"+oPath.getTotalDistance());
	}
	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		super.destroy();
		Node.IOriginNode = null;
	}
	
}
