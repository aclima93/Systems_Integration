package servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Playlist;
import jpa.User;
import musicEJB.MusicBeanRemote;
import playlistEJB.PlaylistBeanRemote;
import playlistEJB.PlaylistCreateResult;
import playlistEJB.PlaylistDeleteResult;
import playlistEJB.PlaylistSortOrder;
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
		if(request.getSession().getAttribute("user") == null) {
			// User not logged in
			if(action == null) {
				// First access, must login
				request.getSession().removeAttribute("user");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("login")==0) {
				// Login
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				User result = this.user.login(email, password);
				
				if(result != null) {
					// Successful login
					request.getSession().setAttribute("user", result);
					request.getRequestDispatcher("index.jsp").forward(request, response);
					
				} else {
					// Error logging in, maybe wrong password
					request.getSession().setAttribute("user", result);
					request.getSession().setAttribute("error", "Error logging in");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				}
			} else if (action.compareToIgnoreCase("register")==0){
				// Register
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				UserRegisterResult result = this.user.register(name, email, password);
				
				if(result == UserRegisterResult.Success) {
					// Successful register, user can now login
					request.getSession().setAttribute("message", "User registered successfully");
					request.getRequestDispatcher("register.jsp").forward(request, response);
					
				} else if(result == UserRegisterResult.EmailAlreadyUsed) {
					// The email is already associated to some account, can't use it
					request.getSession().setAttribute("error", "There is already an user with that e-mail address.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else {
					// Something bad happened
					request.getSession().setAttribute("error", "Something went wrong.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				}
			} else {
				// Random access?
				request.getSession().removeAttribute("user");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				
			}
		} else {
			// User logged in
			User currentUser = (User)request.getSession().getAttribute("user");
			if(action == null) {
				// Shouldn't happend, but user is logged in, so go to main menu
				request.getRequestDispatcher("index.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("index") == 0) {
				// Main menu
				request.getRequestDispatcher("index.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("logout") == 0) {
				// Logout
				request.getSession().removeAttribute("user");
				request.getRequestDispatcher("logout.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("userMenu") == 0) {
				// User wants to change account settings
				request.getSession().setAttribute("message", "Select an option.");
				request.getRequestDispatcher("account.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("changeName") == 0) {
				// User wants to change user name
				String newName = request.getParameter("name");
				User result = this.user.changeUserName(currentUser, newName);
				
				if(result == null) {
					// Error changing name
					request.getSession().setAttribute("error", "Error changing user name");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else {
					// Successfully changed name
					request.getSession().setAttribute("user", result);
					request.getSession().setAttribute("message", "User name updated successfully.");
					request.getRequestDispatcher("account.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("changeEmail") == 0) {
				// User wants to change e-mail address
				String newEmail = request.getParameter("email");
				User result = this.user.changeUserEmail(currentUser, newEmail);
				
				if(result == null) {
					// Error changing e-mail
					request.getSession().setAttribute("error", "Error changing user email");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else if(result.getEmail().compareTo(currentUser.getEmail()) == 0){
					// E-mail address already in use
					request.getSession().setAttribute("message", "That e-mail is already in use.");
					request.getRequestDispatcher("account.jsp").forward(request, response);
					
				} else {
					// Successfully changed e-mail address
					request.getSession().setAttribute("user", result);
					request.getSession().setAttribute("message", "User e-mail updated successfully.");
					request.getRequestDispatcher("account.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("changePassword") == 0) {
				// User wants to change password
				String newPassword = request.getParameter("password");
				User result = this.user.changeUserPassword(currentUser, newPassword);
				
				if(result == null) {
					// Error changing password
					request.getSession().setAttribute("error", "Error changing user password");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else {
					// Successfully changed password
					request.getSession().setAttribute("user", result);
					request.getSession().setAttribute("message", "User password updated successfully.");
					request.getRequestDispatcher("account.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("deleteUser") == 0) {
				// User wants to delete account, redirect to confirmation page
				request.getRequestDispatcher("deleteUser.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("deleteUserConfirm") == 0) {
				// Confirm if user wants to delete account
				UserDeleteResult result = this.user.deleteUser(currentUser);
				
				if(result == UserDeleteResult.Success) {
					// Successfully deleted user account
					request.getSession().removeAttribute("user");
					request.getRequestDispatcher("login.jsp").forward(request, response);
					
				} else if(result == UserDeleteResult.Error) {
					// Error deleting account
					request.getSession().setAttribute("error", "Something went wrong.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("playlistMenu") == 0) {
				// User wants to access playlist menu
				String order = request.getParameter("order");
				PlaylistSortOrder sortOrder = null;
				
				if(order == null || order.compareToIgnoreCase("asc") == 0) {
					sortOrder = PlaylistSortOrder.Ascending;
				} else {
					sortOrder = PlaylistSortOrder.Descending;
				}
				
				List<Playlist> result = this.playlist.listPlaylists(sortOrder);
				request.getSession().removeAttribute("message");
				request.getSession().setAttribute("list", result);
				request.getRequestDispatcher("playlist.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("createPlaylist") == 0) {
				// User wants to create new playlist				
				String name = request.getParameter("name");
				PlaylistCreateResult result = this.playlist.createPlaylist(currentUser, name);
				
				if(result == PlaylistCreateResult.Success) {
					// Playlist successfully created
					String order = request.getParameter("order");
					PlaylistSortOrder sortOrder = null;
					
					if(order == null || order.compareToIgnoreCase("asc") == 0) {
						sortOrder = PlaylistSortOrder.Ascending;
					} else {
						sortOrder = PlaylistSortOrder.Descending;
					}
					
					List<Playlist> list = this.playlist.listPlaylists(sortOrder);
					request.getSession().setAttribute("list", list);
					
					request.getSession().setAttribute("message", "Playlist successfully created");
					request.getRequestDispatcher("playlist.jsp").forward(request, response);
					
				} else if(result == PlaylistCreateResult.PlaylistAlreadyExists) {
					// Playlist already exists
					String order = request.getParameter("order");
					PlaylistSortOrder sortOrder = null;
					
					if(order == null || order.compareToIgnoreCase("asc") == 0) {
						sortOrder = PlaylistSortOrder.Ascending;
					} else {
						sortOrder = PlaylistSortOrder.Descending;
					}
					
					List<Playlist> list = this.playlist.listPlaylists(sortOrder);
					request.getSession().setAttribute("list", list);
					
					request.getSession().setAttribute("message", "Playlist already exists");
					request.getRequestDispatcher("playlist.jsp").forward(request, response);
					
				} else {
					// Error of some sort
					request.getSession().setAttribute("error", "Something went wrong.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				}			
			} else if(action.compareToIgnoreCase("deletePlaylist") == 0) {
				// User wants to delete playlist, redirect
				request.getSession().setAttribute("playlist", request.getParameter("playlist"));
				request.getRequestDispatcher("deletePlaylist.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("deletePlaylistConfirm") == 0) {
				// Let's delete that playlist
				int playlist = Integer.parseInt((String)request.getSession().getAttribute("playlist"));
				PlaylistDeleteResult result = this.playlist.deletePlaylist(playlist, currentUser);
				if(result == PlaylistDeleteResult.Success) {
					// Successfully deleted playlist
					String order = request.getParameter("order");
					PlaylistSortOrder sortOrder = null;
					
					if(order == null || order.compareToIgnoreCase("asc") == 0) {
						sortOrder = PlaylistSortOrder.Ascending;
					} else {
						sortOrder = PlaylistSortOrder.Descending;
					}
					
					List<Playlist> list = this.playlist.listPlaylists(sortOrder);
					request.getSession().setAttribute("message", "Playlist deleted successfully");
					request.getSession().setAttribute("list", list);
					request.getRequestDispatcher("playlist.jsp").forward(request, response);
					
				} else if(result == PlaylistDeleteResult.Unauthorized) {
					// Oops, can't do it
					String order = request.getParameter("order");
					PlaylistSortOrder sortOrder = null;
					
					if(order == null || order.compareToIgnoreCase("asc") == 0) {
						sortOrder = PlaylistSortOrder.Ascending;
					} else {
						sortOrder = PlaylistSortOrder.Descending;
					}
					
					List<Playlist> list = this.playlist.listPlaylists(sortOrder);
					request.getSession().setAttribute("message", "You don't have authorization to delete that playlist");
					request.getSession().setAttribute("list", list);
					request.getRequestDispatcher("playlist.jsp").forward(request, response);
				} else {
					// Oops
					request.getSession().setAttribute("error", "Something went wrong.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}
			}
		}
	}

}
