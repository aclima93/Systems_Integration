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
    public MusicUploadResult uploadMusic(Music music, User user) {
    	try {
    		music.setUploader(user);
    		em.merge(music);
    		user.getMusics().add(music);
    		em.merge(user);
    		return MusicUploadResult.Success;
    	} catch(Exception e) {
    		return MusicUploadResult.Error;
    	}
    }
    
    @Override
    public MusicEditResult changeMusicTitle(Music music, User user, String title) {
    	try {
    		Query query = em.createQuery("* from Music m, User u WHERE u.email=:u AND m.path=:m AND u.id = m.uploader_id");
    		query.setParameter("u", user.getEmail());
    		query.setParameter("m", music.getPath());
    		if(query.getResultList().isEmpty()) {
    			return MusicEditResult.Unauthorized;
    		}
    		music.setTitle(title);
    		em.merge(music);
    		return MusicEditResult.Success;
    	} catch(Exception e) {
    		return MusicEditResult.Error;
    	}
    }

	@Override
	public MusicEditResult changeMusicArtist(Music music, User user, String artist) {
		try {
    		Query query = em.createQuery("* from Music m, User u WHERE u.email=:u AND m.path=:m AND u.id = m.uploader_id");
    		query.setParameter("u", user.getEmail());
    		query.setParameter("m", music.getPath());
    		if(query.getResultList().isEmpty()) {
    			return MusicEditResult.Unauthorized;
    		}
    		music.setArtist(artist);
    		//em.persist(music);
    		return MusicEditResult.Success;
    	} catch(Exception e) {
    		return MusicEditResult.Error;
    	}
	}

	@Override
	public MusicEditResult changeMusicAlbum(Music music, User user, String album) {
		try {
    		Query query = em.createQuery("* from Music m, User u WHERE u.email=:u AND m.path=:m AND u.id = m.uploader_id");
    		query.setParameter("u", user.getEmail());
    		query.setParameter("m", music.getPath());
    		if(query.getResultList().isEmpty()) {
    			return MusicEditResult.Unauthorized;
    		}
    		music.setAlbum(album);
    		//em.persist(music);
    		return MusicEditResult.Success;
    	} catch(Exception e) {
    		return MusicEditResult.Error;
    	}
	}

	@Override
	public MusicEditResult changeMusicYear(Music music, User user, String year) {
		try {
    		Query query = em.createQuery("* from Music m, User u WHERE u.email=:u AND m.path=:m AND u.id = m.uploader_id");
    		query.setParameter("u", user.getEmail());
    		query.setParameter("m", music.getPath());
    		if(query.getResultList().isEmpty()) {
    			return MusicEditResult.Unauthorized;
    		}
    		music.setYear(year);
    		//em.persist(music);
    		return MusicEditResult.Success;
    	} catch(Exception e) {
    		return MusicEditResult.Error;
    	}
	}

	@Override
	public MusicDeleteResult deleteMusic(Music music, User user) {
		try {
			Query query = em.createQuery("* from Music m, User u WHERE u.id=:u AND m.id=:m AND u.id = m.uploader_id");
    		query.setParameter("u", user.getId());
    		query.setParameter("m", music.getId());
    		if(query.getResultList().isEmpty()) {
    			return MusicDeleteResult.Unauthorized;
    		}
    		em.remove(em.find(Music.class, music.getId()));
    		return MusicDeleteResult.Success;
		} catch(Exception e) {
			return MusicDeleteResult.Error;
		}
	}

	@Override
	public List<Music> getAllMusic() {
		try {
			Query query = em.createQuery("* from Music");
			@SuppressWarnings("unchecked")
			List<Music> resultList = query.getResultList();
			return resultList;
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
