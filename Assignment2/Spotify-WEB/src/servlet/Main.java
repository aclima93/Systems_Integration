package servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpa.Music;
import jpa.Playlist;
import jpa.User;
import musicEJB.MusicBeanRemote;
import musicEJB.MusicDeleteResult;
import musicEJB.MusicUploadResult;
import playlistEJB.PlaylistBeanRemote;
import playlistEJB.PlaylistCreateResult;
import playlistEJB.PlaylistDeleteResult;
import playlistEJB.PlaylistEditResult;
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
			} else if(action.compareToIgnoreCase("listMusicInPlaylist") == 0) {
				// Playlist view
				int playlist = Integer.parseInt(request.getParameter("playlist"));
				request.getSession().removeAttribute("list");
				List<Music> result = this.playlist.listMusicOnPlaylist(playlist);
				request.getSession().setAttribute("list", result);
				request.getSession().setAttribute("playlist", this.playlist.getPlaylistById(playlist));
				request.getRequestDispatcher("playlistView.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("changePlaylistName") == 0) {
				// Change playlist name
				int playlist = Integer.parseInt(request.getParameter("playlist"));
				String name = request.getParameter("name");
				request.getSession().removeAttribute("message");
				PlaylistEditResult result = this.playlist.changePlaylistName(playlist, name);
				
				if(result == PlaylistEditResult.Success) {
					// Successfully changed playlist name
					request.getSession().setAttribute("message", "Successfully changed playlist name.");
					request.getSession().setAttribute("playlist", this.playlist.getPlaylistById(playlist));
					request.getRequestDispatcher("playlistView.jsp").forward(request, response);
					
				} else {
					// Error changing playlist name
					request.getSession().setAttribute("error", "Error changing playlist name.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("musicMenu") == 0) {
				// Music menu
				request.getRequestDispatcher("music.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("listAllSongs") == 0) {
				// List all songs
				request.setAttribute("list", this.music.getAllMusic());
				request.getRequestDispatcher("listAllSongs.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("listMySongs") == 0) {
				// List songs uploaded by current user
				request.setAttribute("list", this.music.listSongsByUser(currentUser));
				request.getRequestDispatcher("listMySongs.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("addNewMusic") == 0) {
				// User wants to add a new song
				request.getSession().removeAttribute("message");
				request.getRequestDispatcher("newMusic.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("verifyNewSong") == 0) {
				// Check if song is "addable"
				String title = request.getParameter("title");
				String artist = request.getParameter("artist");
				String album = request.getParameter("album");
				String year = request.getParameter("year");
				MusicUploadResult result = this.music.uploadMusic(title, artist, album, year, currentUser);
				
				if(result == MusicUploadResult.Success) {
					// Successfully uploaded
					request.getSession().setAttribute("message", "Successfully added song.");
					request.getRequestDispatcher("newMusic.jsp").forward(request, response);
					
				} else {
					// Error uploading
					request.getSession().setAttribute("Error", "Something went wrong while uploading that song.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("editSongInfo") == 0) {
				// User wants to change something on the song
				int id = Integer.parseInt(request.getParameter("song"));
				Music song = this.music.getSongByID(id);
				request.getSession().setAttribute("song", song);
				request.getRequestDispatcher("editSongInfo.jsp").forward(request, response);
				
			} else if(action.compareToIgnoreCase("editSongTitle") == 0) {
				// User wants to change song title
				Music song = (Music)request.getSession().getAttribute("song");
				int id = song.getId();
				Music result = this.music.changeMusicTitle(id, request.getParameter("title"));
				if (result == null) {
					// Error editing song
					request.getSession().setAttribute("error", "Error changing song title.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else {
					// Success
					request.getSession().setAttribute("song", result);
					request.getSession().setAttribute("message", "Successfully changed song title.");
					request.getRequestDispatcher("editSongInfo.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("editSongArtist") == 0) {
				// User wants to change song artist
				Music song = (Music)request.getSession().getAttribute("song");
				int id = song.getId();
				Music result = this.music.changeMusicArtist(id, request.getParameter("artist"));
				if (result == null) {
					// Error editing song
					request.getSession().setAttribute("error", "Error changing song artist.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else {
					// Success
					request.getSession().setAttribute("song", result);
					request.getSession().setAttribute("message", "Successfully changed song artist.");
					request.getRequestDispatcher("editSongInfo.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("editSongAlbum") == 0) {
				// User wants to change song album
				Music song = (Music)request.getSession().getAttribute("song");
				int id = song.getId();
				Music result = this.music.changeMusicAlbum(id, request.getParameter("album"));
				if (result == null) {
					// Error editing song
					request.getSession().setAttribute("error", "Error changing song album.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else {
					// Success
					request.getSession().setAttribute("song", result);
					request.getSession().setAttribute("message", "Successfully changed song album.");
					request.getRequestDispatcher("editSongInfo.jsp").forward(request, response);
					
				}	
			} else if(action.compareToIgnoreCase("editSongYear") == 0) {
				// User wants to change song year
				Music song = (Music)request.getSession().getAttribute("song");
				int id = song.getId();
				Music result = this.music.changeMusicYear(id, request.getParameter("year"));
				if(result == null) {
					// Error editing song
					request.getSession().setAttribute("error", "Error changing song year.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else {
					// Success
					request.getSession().setAttribute("song", result);
					request.getSession().setAttribute("message", "Successfully changed song year.");
					request.getRequestDispatcher("editSongInfo.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("deleteSong") == 0) {
				// User wants to delete one of his songs
				Music song = (Music)request.getSession().getAttribute("song");
				int id = song.getId();
				MusicDeleteResult result = this.music.deleteMusic(id);
				if(result == null) {
					// Error deleting song
					request.getSession().setAttribute("error", "Error deleting song");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					
				} else {
					// Success
					request.setAttribute("list", this.music.listSongsByUser(currentUser));
					request.getSession().setAttribute("message", "Successfully deleted song");
					request.getRequestDispatcher("listMySongs.jsp").forward(request, response);
					
				}
			} else if(action.compareToIgnoreCase("searchSongs") == 0) {
				// Search with criteria
				
			}
		}
	}

}
