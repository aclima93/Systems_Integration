package musicEJB;

import java.util.List;

import javax.ejb.Remote;

import jpa.Music;
import jpa.User;

@Remote
public interface MusicBeanRemote {
	public MusicUploadResult uploadMusic(Music music, User user);
	public MusicEditResult changeMusicTitle(Music music, User user, String title);
	public MusicEditResult changeMusicArtist(Music music, User user, String artist);
	public MusicEditResult changeMusicAlbum(Music music, User user, String album);
	public MusicEditResult changeMusicYear(Music music, User user, String year);
	public MusicDeleteResult deleteMusic(Music music, User user);
	public List<Music> getAllMusic();
	public List<Music> searchMusic(MusicSearchParameter parameter, String value);
}
