package musicEJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Music;
import jpa.User;

/**
 * Session Bean implementation class MusicBean
 */
@Stateless
public class MusicBean implements MusicBeanRemote {
	@PersistenceContext()
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public MusicBean() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public MusicUploadResult uploadMusic(String title, String artist, String album, String year, User user) {
    	try {
    		Music music = new Music(title, artist, album, year, "");
    		music.setUploader(user);
    		em.merge(music);
    		return MusicUploadResult.Success;
    	} catch(Exception e) {
    		return MusicUploadResult.Error;
    	}
    }
    
    @Override
    public Music changeMusicTitle(int id, String title) {
    	try {
    		Music music = em.find(Music.class, id);
    		music.setTitle(title);
    		em.merge(music);
    		return music;
    	} catch(Exception e) {
    		return null;
    	}
    }

    @Override
    public Music changeMusicArtist(int id, String artist) {
    	try {
    		Music music = em.find(Music.class, id);
    		music.setArtist(artist);
    		em.merge(music);
    		return music;
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    @Override
    public Music changeMusicAlbum(int id, String album) {
    	try {
    		Music music = em.find(Music.class, id);
    		music.setAlbum(album);
    		em.merge(music);
    		return music;
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    @Override
    public Music changeMusicYear(int id, String year) {
    	try {
    		Music music = em.find(Music.class, id);
    		music.setYear(year);
    		em.merge(music);
    		return music;
    	} catch(Exception e) {
    		return null;
    	}
    }

	@Override
	public MusicDeleteResult deleteMusic(int id) {
		try {
    		em.remove(em.find(Music.class, id));
    		return MusicDeleteResult.Success;
		} catch(Exception e) {
			return MusicDeleteResult.Error;
		}
	}

	@Override
	public List<Music> getAllMusic() {
		try {
			Query query = em.createQuery("from Music");
			@SuppressWarnings("unchecked")
			List<Music> resultList = query.getResultList();
			return resultList;
		} catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Music> listSongsByUser(User user) {
		try {
			Query query = em.createQuery("from Music m where m.uploader=:u");
			query.setParameter("u", user);
			@SuppressWarnings("unchecked")
			List<Music> result = query.getResultList();
			return result;
		} catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public Music getSongByID(int id) {
		try {
			Music result = em.find(Music.class, id);
			return result;
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Music> searchMusic(MusicSearchParameter parameter, String value) {
		try {
			Query query = null;
			switch(parameter) {
			case Title:
				query = em.createQuery("from Music m WHERE m.title LIKE %:m%");
				break;
			case Artist:
				query = em.createQuery("from Music m WHERE m.artist LIKE %:m%");
				break;
			case Album:
				query = em.createQuery("from Music m WHERE m.album LIKE %:m%");
				break;
			case Year:
				query = em.createQuery("from Music m WHERE m.year=:m");
				break;
			default:
				return null;
			}
			query.setParameter("m", value);
			@SuppressWarnings("unchecked")
			List<Music> resultList = query.getResultList();
			return resultList;
		} catch(Exception e) {
			return null;
		}
	}
}
