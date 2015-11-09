package playlistEJB;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Music;
import jpa.Playlist;
import jpa.User;
import musicEJB.MusicDeleteResult;

/**
 * Session Bean implementation class PlaylistBean
 */
@Stateful
public class PlaylistBean implements PlaylistBeanRemote {
	@PersistenceContext(name="Spotify")
	EntityManager em;

    /**
     * Default constructor. 
     */
    public PlaylistBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public PlaylistCreateResult createPlaylist(User user, String name) {
		try {
			Query query = em.createQuery("from Playlist p where p.name=:p");
			query.setParameter("p", name);
			@SuppressWarnings("unchecked")
			List<Playlist> result = query.getResultList();
			if(!result.isEmpty()) {
				return PlaylistCreateResult.PlaylistAlreadyExists;
			}
			Playlist playlist = new Playlist(name);
			playlist.setCreator(user);
			em.getTransaction().begin();
			em.persist(playlist);
			em.getTransaction().commit();
			return PlaylistCreateResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
			return PlaylistCreateResult.Error;
		}
	}

	@Override
	public PlaylistEditResult changePlaylistName(Playlist playlist, String name) {
		try {
			em.getTransaction().begin();
			playlist.setName(name);
			em.persist(playlist);
			em.getTransaction().commit();
			return PlaylistEditResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
			return PlaylistEditResult.Error;
		}
	}

	@Override
	public List<Playlist> listPlaylists(PlaylistSortOrder order) {
		Query query = em.createQuery("from Playlist order by name :o");
		switch(order) {
		case Ascending:
			query.setParameter("o", "asc");
			break;
		case Descending:
			query.setParameter("o", "desc");
			break;
		default:
			query.setParameter("o",	"asc");
			break;
		}
		@SuppressWarnings("unchecked")
		List<Playlist> result = query.getResultList();
		return result;
	}

	@Override
	public List<Music> listMusicOnPlaylist(Playlist playlist) {
		Query query = em.createQuery("m.* from Music m, Playlist_Music pm where m.id = pm.songs_id and pm.playlists_id = :p");
		query.setParameter("p", playlist.getId());
		@SuppressWarnings("unchecked")
		List<Music> result = query.getResultList();
		return result;
	}

	@Override
	public PlaylistDeleteResult deletePlaylist(Playlist playlist, User user) {
		try {
			Query query = em.createQuery("* from Playlist p, User u WHERE u.id=:u AND p.id=:p AND u.id = p.creator_id");
    		query.setParameter("u", user.getId());
    		query.setParameter("p", playlist.getId());
    		if(query.getResultList().isEmpty()) {
    			return PlaylistDeleteResult.Unauthorized;
    		}
    		em.getTransaction().begin();
    		em.remove(em.find(Playlist.class, playlist.getId()));
    		em.getTransaction().commit();
    		return PlaylistDeleteResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
			return PlaylistDeleteResult.Error;
		}
	}

}
