package com.paco.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paco.java.subway.NodeLink;
import com.paco.java.subway.Node;


/**
 * Servlet implementation class NodeCreatorServlet : This servlet is used to create a Subway map
 * 2 Nodes at a time.  The information necessary to operate the servlet are :
 * FirstName : A <code>String</code> of the name of the first Node
 * SecondName : A <code>String</code> of the name of the second Node
 * NodeDistance : An <code>Integer</code> of the distance between those 2 Nodes
 *  
 * @author Pascal
 */
@WebServlet(description = "This servlet creates the Subway map 2 nodes at a time...", urlPatterns = { "/NodeCreatorServlet" })
public class NodeCreatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodeCreatorServlet() {
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
		//Extract first name from the line 
		String sFirstName = request.getParameter("FirstName");//sLine.substring(0, iIndex);
		Node oFirst = new Node(sFirstName);
		//If AlphaNode doesn't exist then we set oFirst as AlphaNode
		//Extract second Name from the line
		String sSecondName = request.getParameter("SecondName");
		Node oSecond = new Node(sSecondName);
		//Extract the distance from the line
		int iNodeDistance = Integer.valueOf(request.getParameter("NodeDistance"));
		NodeLink oLinker = new NodeLink(oFirst, oSecond, iNodeDistance);
		//Sharing the Node
	    PrintWriter out = response.getWriter();
	    String sTest = Node.ExtractSubwayMap();
	    out.println(sTest);
	}
}
