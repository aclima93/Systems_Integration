package playlistEJB;

import java.util.List;

import javax.ejb.Remote;

import jpa.Music;
import jpa.Playlist;
import jpa.User;

@Remote
public interface PlaylistBeanRemote {
	PlaylistCreateResult createPlaylist(User user, String name);
	PlaylistEditResult changePlaylistName(int id, String name);
	List<Playlist> listPlaylists(PlaylistSortOrder order);
	List<Music> listMusicOnPlaylist(int id);
	PlaylistDeleteResult deletePlaylist(int id, User user);
	Playlist getPlaylistById(int id);
}
