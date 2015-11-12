package servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import musicEJB.MusicBean;
import musicEJB.MusicBeanRemote;
import playlistEJB.PlaylistBean;
import playlistEJB.PlaylistBeanRemote;
import userEJB.UserLoginResult;
import userEJB.UserBean;
import userEJB.UserBeanRemote;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserBeanRemote user;
	private MusicBeanRemote music;
	private PlaylistBeanRemote playlist;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        this.user = new UserBean();
        this.music = new MusicBean();
        this.playlist = new PlaylistBean();
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
		if(!this.user.isLoggedIn()) {
			if(action == null) {
				request.setAttribute("loggedIn", "false");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else if(action.compareToIgnoreCase("login")==0) {
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				UserLoginResult result = this.user.login(email, password);
				if(result == UserLoginResult.Success) {
					response.getWriter().append("Successful login");
				} else if(result == UserLoginResult.UserDoesNotExist) {
					response.getWriter().append("That user doesn't exist");
				} else if(result == UserLoginResult.WrongPassword) {
					response.getWriter().append("Wrong password");
				} else {
					response.getWriter().append("General Error");
				}
				return;
			} else if (action.compareToIgnoreCase("register")==0){
				request.setAttribute("action", "register");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} else {
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
	}

}
