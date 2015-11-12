package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.User;
import musicEJB.MusicBeanRemote;
import playlistEJB.PlaylistBeanRemote;
import userEJB.UserRegisterResult;
import userEJB.UserBeanRemote;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserBeanRemote user;
	@EJB
	private MusicBeanRemote music;
	@EJB
	private PlaylistBeanRemote playlist;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(request.getSession().getAttribute("user") == null) {
			if(action == null) {
				request.setAttribute("user", null);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else if(action.compareToIgnoreCase("login")==0) {
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				User result = this.user.login(email, password);
				if(result != null) {
					request.setAttribute("user", result);
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} else {
					request.setAttribute("user", result);
					request.setAttribute("error", "Error logging in");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}
				return;
			} else if (action.compareToIgnoreCase("register")==0){
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				UserRegisterResult result = this.user.register(name, email, password);
				if(result == UserRegisterResult.Success) {
					response.getWriter().append("Successful register. You are now logged in.");
				} else if(result == UserRegisterResult.EmailAlreadyUsed) {
					response.getWriter().append("There is already an user with that e-mail address.");
				} else {
					response.getWriter().append("General Error.");
				}
			}
		} else {
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
	}

}
