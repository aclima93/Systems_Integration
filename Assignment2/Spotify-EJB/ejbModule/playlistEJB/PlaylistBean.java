package playlistEJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import jpa.Music;
import jpa.Playlist;
import jpa.User;
import userEJB.UserBean;

/**
 * Session Bean implementation class PlaylistBean
 */
@Stateless
public class PlaylistBean implements PlaylistBeanRemote {
	@PersistenceContext()
	EntityManager em;
	
	private static Logger logger = Logger.getLogger(UserBean.class);

    /**
     * Default constructor. 
     */
    public PlaylistBean() {
    }

	@Override
	public PlaylistCreateResult createPlaylist(User user, String name) {
		try {
			Query query = em.createQuery("from Playlist p where p.name=:p");
			query.setParameter("p", name);
			@SuppressWarnings("unchecked")
			List<Playlist> result = query.getResultList();
			if(!result.isEmpty()) {
				logger.info("User "+user.getName()+" attempted to create playlist "+name+", but it already exists");
				return PlaylistCreateResult.PlaylistAlreadyExists;
			}
			Playlist playlist = new Playlist(name);
			playlist.setCreator(user);
			em.merge(playlist);
			logger.info("Playlist "+name+" created by user "+user.getName());
			return PlaylistCreateResult.Success;
		} catch(Exception e) {
			logger.error("Error creating playlist");
			return PlaylistCreateResult.Error;
		}
	}

	@Override
	public PlaylistEditResult changePlaylistName(int id, String name) {
		try {
			Playlist playlist = em.find(Playlist.class, id);
			String old = playlist.getName();
			playlist.setName(name);
			logger.info("Changed playlist name "+old+" to "+name);
			return PlaylistEditResult.Success;
		} catch(Exception e) {
			logger.error("Error changing playlist name");
			return PlaylistEditResult.Error;
		}
	}

	@Override
	public List<Playlist> listPlaylists(PlaylistSortOrder order) {
		try {
			switch(order) {
			case Descending:
				Query querydesc = em.createQuery("from Playlist order by name desc");
				@SuppressWarnings("unchecked")
				List<Playlist> resultdesc = querydesc.getResultList();
				logger.info("Listing all playlists in descending order");
				return resultdesc;
			default:
				Query queryasc = em.createQuery("from Playlist order by name asc");
				@SuppressWarnings("unchecked")
				List<Playlist> resultasc = queryasc.getResultList();
				logger.info("Listing all playlists in ascending order");
				return resultasc;
			}
		} catch(Exception e) {
			logger.error("Error listing playlists");
			return null;
		}
	}
	
	@Override
	public List<Playlist> getMyPlaylists(User user) {
		try {
			Query query = em.createQuery("from Playlist p where p.creator=:u");
			query.setParameter("u", user);
			@SuppressWarnings("unchecked")
			List<Playlist> result = query.getResultList();
			logger.info("Listing personal playlists for user "+user.getName());
			return result;
		} catch(Exception e) {
			logger.error("Error listing personal playlists for user "+user.getName());
			return null;
		}
	}

	@Override
	public List<Music> listMusicOnPlaylist(int id) {
		try {
			Playlist playlist = em.find(Playlist.class, id);
			Query query = em.createQuery("select m from Music m join m.playlists p WHERE p.id=:p");
			query.setParameter("p", id);
			@SuppressWarnings("unchecked")
			List<Music> result = query.getResultList();
			logger.info("Listing all songs on playlist "+playlist.getName());
			return result;
		} catch(Exception e) {
			logger.error("Error listing musics on playlist "+id);
			return null;
		}
	}

	@Override
	public PlaylistDeleteResult deletePlaylist(int id, User user) {
		try {
			Playlist playlist = em.find(Playlist.class, id);
			Query query = em.createQuery("from Playlist p WHERE p.id=:p AND p.creator=:u");
    		query.setParameter("u", user);
    		query.setParameter("p", id);
    		if(query.getResultList().isEmpty()) {
    			logger.info("User "+user.getName()+" tried to delete playlist "+playlist.getName() + ", but the operation was denied");
    			return PlaylistDeleteResult.Unauthorized;
    		}
    		em.remove(em.find(Playlist.class, id));
    		logger.info("Deleted playlist "+playlist.getName());
    		return PlaylistDeleteResult.Success;
		} catch(Exception e) {
			logger.error("Error deleting playlist "+id);
			return PlaylistDeleteResult.Error;
		}
	}

	@Override
	public Playlist getPlaylistById(int id) {
		try {
			logger.info("Retrieving playlist with id "+id);
			return em.find(Playlist.class, id);
		} catch(Exception e) {
			logger.error("Error retrieving playlist "+id);
			return null;
		}
	}

	@Override
	public PlaylistEditResult addSongToPlaylist(int playlistId, int songId) {
		try {
			Playlist playlist = em.find(Playlist.class, playlistId);
			Music song = em.find(Music.class, songId);
			playlist.getSongs().add(song);
			logger.info("Adding song "+song.getTitle()+"to playlist "+playlist.getName());
			return PlaylistEditResult.Success;
		} catch(Exception e) {
			logger.error("Error adding song to playlist "+playlistId+" "+songId);
			return PlaylistEditResult.Error;
		}
	}

	@Override
	public PlaylistEditResult deleteSongFromPlaylist(int playlistId, int songId) {
		try {
			Playlist playlist = em.find(Playlist.class, playlistId);
			Music music = em.find(Music.class, songId);
			playlist.getSongs().remove(music);
			logger.info("Deleting song "+music.getTitle()+"from playlist "+playlist.getName());
			return PlaylistEditResult.Success;
		} catch(Exception e) {
			logger.error("Error deleting song from playlist "+playlistId+" "+songId);
			return PlaylistEditResult.Error;
		}
	}

}
