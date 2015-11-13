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
import userEJB.UserDeleteResult;
import userEJB.UserEditResult;

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
		if(action!=null && action.compareToIgnoreCase("logout")==0) {
			request.getSession().removeAttribute("user");
			request.getRequestDispatcher("logout.jsp").forward(request, response);
			return;
		} else if(request.getSession().getAttribute("user") == null) {
			if(action == null) {
				request.getSession().setAttribute("user", null);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else if(action.compareToIgnoreCase("login")==0) {
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				User result = this.user.login(email, password);
				if(result != null) {
					request.getSession().setAttribute("user", result);
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("user", result);
					request.getSession().setAttribute("error", "Error logging in");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}
			} else if (action.compareToIgnoreCase("register")==0){
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				UserRegisterResult result = this.user.register(name, email, password);
				if(result == UserRegisterResult.Success) {
					request.getSession().setAttribute("message", "User registered successfully");
					request.getRequestDispatcher("register.jsp").forward(request, response);
				} else if(result == UserRegisterResult.EmailAlreadyUsed) {
					request.getSession().setAttribute("error", "There is already an user with that e-mail address.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("error", "Something went wrong.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}
			} else {
				request.getSession().setAttribute("error", "Something went wrong.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} else {
			User currentUser = (User)request.getSession().getAttribute("user");
			if(action == null) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else if(action.compareToIgnoreCase("index") == 0) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else if(action.compareToIgnoreCase("logout") == 0) {
				request.getSession().removeAttribute("user");
				request.getRequestDispatcher("logout.jsp").forward(request, response);
			} else if(action.compareToIgnoreCase("userMenu") == 0) {
				request.getSession().setAttribute("message", "Select an option.");
				request.getRequestDispatcher("account.jsp").forward(request, response);
			} else if(action.compareToIgnoreCase("changeName") == 0) {
				String newName = request.getParameter("name");
				User result = this.user.changeUserName(currentUser, newName);
				if(result == null) {
					request.getSession().setAttribute("error", "Error changing user name");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("user", result);
					request.getSession().setAttribute("message", "User name updated successfully.");
					request.getRequestDispatcher("account.jsp").forward(request, response);
				}
			} else if(action.compareToIgnoreCase("changeEmail") == 0) {
				String newEmail = request.getParameter("email");
				User result = this.user.changeUserEmail(currentUser, newEmail);
				if(result == null) {
					request.getSession().setAttribute("error", "Error changing user email");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				} else if(result.getEmail().compareTo(currentUser.getEmail()) == 0){
					request.getSession().setAttribute("message", "That e-mail is already in use.");
					request.getRequestDispatcher("account.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("user", result);
					request.getSession().setAttribute("message", "User e-mail updated successfully.");
					request.getRequestDispatcher("account.jsp").forward(request, response);
				}
			} else if(action.compareToIgnoreCase("changePassword") == 0) {
				String newPassword = request.getParameter("password");
				User result = this.user.changeUserPassword(currentUser, newPassword);
				if(result == null) {
					request.getSession().setAttribute("error", "Error changing user password");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("user", result);
					request.getSession().setAttribute("message", "User password updated successfully.");
					request.getRequestDispatcher("account.jsp").forward(request, response);
				}
			} else if(action.compareToIgnoreCase("deleteUser") == 0) {
				request.getRequestDispatcher("deleteUser.jsp").forward(request, response);
			} else if(action.compareToIgnoreCase("deleteUserConfirm") == 0) {
				UserDeleteResult result = this.user.deleteUser(currentUser);
				if(result == UserDeleteResult.Success) {
					request.getSession().removeAttribute("user");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				} else if(result == UserDeleteResult.Error) {
					request.getSession().setAttribute("error", "Something went wrong.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}
			}
		}
	}

}
