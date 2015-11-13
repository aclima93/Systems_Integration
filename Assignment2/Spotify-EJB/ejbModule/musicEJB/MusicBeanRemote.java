package musicEJB;

import java.util.List;

import javax.ejb.Remote;

import jpa.Music;
import jpa.User;

@Remote
public interface MusicBeanRemote {
	public MusicUploadResult uploadMusic(String title, String artist, String album, String year, User user);
	public Music changeMusicTitle(int id, String title);
	public Music changeMusicArtist(int id, String artist);
	public Music changeMusicAlbum(int id, String album);
	public Music changeMusicYear(int id, String year);
	public MusicDeleteResult deleteMusic(int id);
	public List<Music> getAllMusic();
	public List<Music> listSongsByUser(User user);
	public Music getSongByID(int id);
	public List<Music> searchMusic(MusicSearchParameter parameter, String value);
}
