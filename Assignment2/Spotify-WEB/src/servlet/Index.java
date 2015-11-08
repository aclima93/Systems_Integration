package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.MainBeanRemote;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	MainBeanRemote mainBeanRemote;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		if(request.getParameter("email")!=null && request.getParameter("password")!=null) {
			if(this.mainBeanRemote.login(request.getParameter("email"), request.getParameter("password"))) {
				out.println("<h1>Successfully logged in as "+request.getParameter("email")+"!</h1>");
			} else {
				out.println("<h1>Login failed</h1>");
				out.println("<h2>"+request.getParameter("email")+"</h2>");
				out.println("<h2>"+request.getParameter("password")+"</h2>");
			}
		} else {
			out.println("<h1>Oops! Something went wrong...</h1>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
