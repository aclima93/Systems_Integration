package playlistEJB;

import java.util.List;

import javax.ejb.Remote;

import jpa.Music;
import jpa.Playlist;
import jpa.User;

@Remote
public interface PlaylistBeanRemote {
	PlaylistCreateResult createPlaylist(User user, String name);
	PlaylistEditResult changePlaylistName(Playlist playlist, String name);
	List<Playlist> listPlaylists(PlaylistSortOrder order);
	List<Music> listMusicOnPlaylist(Playlist playlist);
	PlaylistDeleteResult deletePlaylist(Playlist playlist, User user);
}
