package playlistEJB;

import java.util.List;

import javax.ejb.Remote;

import jpa.Music;
import jpa.Playlist;
import jpa.User;

@Remote
public interface PlaylistBeanRemote {
	public PlaylistCreateResult createPlaylist(User user, String name);
	public PlaylistEditResult changePlaylistName(int id, String name);
	public List<Playlist> listPlaylists(PlaylistSortOrder order);
	public List<Playlist> getMyPlaylists(User user);
	public List<Music> listMusicOnPlaylist(int id);
	public PlaylistDeleteResult deletePlaylist(int id, User user);
	public Playlist getPlaylistById(int id);
	public PlaylistEditResult addSongToPlaylist(int playlistId, int songId);
	public PlaylistEditResult deleteSongFromPlaylist(int playlistId, int songId);
}
