package musicEJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import jpa.Music;
import jpa.User;
import userEJB.UserBean;

/**
 * Session Bean implementation class MusicBean
 */
@Stateless
public class MusicBean implements MusicBeanRemote {
	@PersistenceContext()
	EntityManager em;
	
	private static Logger logger = Logger.getLogger(UserBean.class);
	
    /**
     * Default constructor. 
     */
    public MusicBean() {
    }
    
    @Override
    public MusicUploadResult uploadMusic(String title, String artist, String album, String year, User user) {
    	try {
    		Music music = new Music(title, artist, album, year, "");
    		music.setUploader(user);
    		em.merge(music);
    		logger.info("New song "+title+" uploaded by "+user.getName());
    		return MusicUploadResult.Success;
    	} catch(Exception e) {
    		logger.error("Error uploading music");
    		return MusicUploadResult.Error;
    	}
    }
    
    @Override
    public Music changeMusicTitle(int id, String title) {
    	try {
    		Music music = em.find(Music.class, id);
    		String old = music.getTitle();
    		music.setTitle(title);
    		em.merge(music);
    		logger.info("Changed name of song "+old+" to "+title);
    		return music;
    	} catch(Exception e) {
    		logger.error("Error changing title of song "+id);
    		return null;
    	}
    }

    @Override
    public Music changeMusicArtist(int id, String artist) {
    	try {
    		Music music = em.find(Music.class, id);
    		String old = music.getTitle();
    		music.setArtist(artist);
    		em.merge(music);
    		logger.info("Changed artist of song "+old+" to "+artist);
    		return music;
    	} catch(Exception e) {
    		logger.error("Error changing artist of song "+id);
    		return null;
    	}
    }
    
    @Override
    public Music changeMusicAlbum(int id, String album) {
    	try {
    		Music music = em.find(Music.class, id);
    		String old = music.getTitle();
    		music.setAlbum(album);
    		em.merge(music);
    		logger.info("Changed album of song "+old+" to "+album);
    		return music;
    	} catch(Exception e) {
    		logger.error("Error changing album of song "+id);
    		return null;
    	}
    }
    
    @Override
    public Music changeMusicYear(int id, String year) {
    	try {
    		Music music = em.find(Music.class, id);
    		String old = music.getTitle();
    		music.setYear(year);
    		em.merge(music);
    		logger.info("Changed year of song "+old+" to "+year);
    		return music;
    	} catch(Exception e) {
    		logger.error("Error changing year of song "+id);
    		return null;
    	}
    }

	@Override
	public MusicDeleteResult deleteMusic(int id) {
		try {
			Music music = em.find(Music.class, id);
			String old = music.getTitle();
    		em.remove(em.find(Music.class, id));
    		logger.info("Successfully deleted song "+old);
    		return MusicDeleteResult.Success;
		} catch(Exception e) {
			logger.error("Error deleting song "+id);
			return MusicDeleteResult.Error;
		}
	}

	@Override
	public List<Music> getAllMusic() {
		try {
			Query query = em.createQuery("from Music");
			@SuppressWarnings("unchecked")
			List<Music> resultList = query.getResultList();
			logger.info("Listing all music");
			return resultList;
		} catch(Exception e) {
			logger.error("Error listing music");
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
			logger.info("Listing all music for user "+user.getName());
			return result;
		} catch(Exception e) {
			logger.error("Error listing music for user "+user.getName());
			return null;
		}
	}
	
	@Override
	public Music getSongByID(int id) {
		try {
			Music result = em.find(Music.class, id);
			logger.info("Retrieving song "+result.getTitle());
			return result;
		} catch(Exception e) {
			logger.error("Error retrieving song for id "+id);
			return null;
		}
	}

	@Override
	public List<Music> searchMusic(MusicSearchParameter parameter, String value) {
		try {
			Query query = null;
			switch(parameter) {
			case Title:
				query = em.createQuery("from Music m WHERE m.title LIKE CONCAT('%',:m,'%')");
				query.setParameter("m", value);
				@SuppressWarnings("unchecked")
				List<Music> resultTitle = query.getResultList();
				logger.info("Listing music based on title "+value);
				return resultTitle;
			case Artist:
				query = em.createQuery("from Music m WHERE m.artist LIKE CONCAT('%',:m,'%')");
				query.setParameter("m", value);
				@SuppressWarnings("unchecked")
				List<Music> resultArtist = query.getResultList();
				logger.info("Listing music based on artist "+value);
				return resultArtist;
			case Album:
				query = em.createQuery("from Music m WHERE m.album LIKE CONCAT('%',:m,'%')");
				query.setParameter("m", value);
				@SuppressWarnings("unchecked")
				List<Music> resultAlbum = query.getResultList();
				logger.info("Listing music based on album "+value);
				return resultAlbum;
			case Year:
				query = em.createQuery("from Music m WHERE m.year LIKE CONCAT('%',:m,'%')");
				query.setParameter("m", value);
				@SuppressWarnings("unchecked")
				List<Music> resultYear = query.getResultList();
				logger.info("Listing music based on year "+value);
				return resultYear;
			default:
				logger.error("Illegal parameter found while searching music");
				return null;
			}
		} catch(Exception e) {
			logger.error("Error searching music");
			return null;
		}
	}
}
