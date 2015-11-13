package playlistEJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Music;
import jpa.Playlist;
import jpa.User;

/**
 * Session Bean implementation class PlaylistBean
 */
@Stateless
public class PlaylistBean implements PlaylistBeanRemote {
	@PersistenceContext()
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
			em.merge(playlist);
			return PlaylistCreateResult.Success;
		} catch(Exception e) {
			return PlaylistCreateResult.Error;
		}
	}

	@Override
	public PlaylistEditResult changePlaylistName(Playlist playlist, String name) {
		try {
			playlist.setName(name);
			//em.persist(playlist);
			return PlaylistEditResult.Success;
		} catch(Exception e) {
			return PlaylistEditResult.Error;
		}
	}

	@Override
	public List<Playlist> listPlaylists(PlaylistSortOrder order) {
		switch(order) {
		case Descending:
			Query querydesc = em.createQuery("from Playlist order by name desc");
			@SuppressWarnings("unchecked")
			List<Playlist> resultdesc = querydesc.getResultList();
			return resultdesc;
		default:
			Query queryasc = em.createQuery("from Playlist order by name asc");
			@SuppressWarnings("unchecked")
			List<Playlist> resultasc = queryasc.getResultList();
			return resultasc;
		}
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
	public PlaylistDeleteResult deletePlaylist(int id, User user) {
		try {
			Query query = em.createQuery("from Playlist p WHERE p.id=:p AND p.creator=:u");
    		query.setParameter("u", user);
    		query.setParameter("p", id);
    		if(query.getResultList().isEmpty()) {
    			return PlaylistDeleteResult.Unauthorized;
    		}
    		em.remove(em.find(Playlist.class, id));
    		return PlaylistDeleteResult.Success;
		} catch(Exception e) {
			return PlaylistDeleteResult.Error;
		}
	}

}
