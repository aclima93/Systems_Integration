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
	public PlaylistEditResult changePlaylistName(int id, String name) {
		try {
			Playlist playlist = em.find(Playlist.class, id);
			playlist.setName(name);
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
	public List<Playlist> getMyPlaylists(User user) {
		try {
			Query query = em.createQuery("from Playlist p where p.creator=:u");
			query.setParameter("u", user);
			@SuppressWarnings("unchecked")
			List<Playlist> result = query.getResultList();
			return result;
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Music> listMusicOnPlaylist(int id) {
		try {
			Query query = em.createQuery("select m from Music m join m.playlists p WHERE p.id=:p");
			query.setParameter("p", id);
			@SuppressWarnings("unchecked")
			List<Music> result = query.getResultList();
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
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

	@Override
	public Playlist getPlaylistById(int id) {
		return em.find(Playlist.class, id);
	}

	@Override
	public PlaylistEditResult addSongToPlaylist(int playlistId, int songId) {
		try {
		Playlist playlist = em.find(Playlist.class, playlistId);
		Music song = em.find(Music.class, songId);
		playlist.getSongs().add(song);
		return PlaylistEditResult.Success;
		} catch(Exception e) {
			return PlaylistEditResult.Error;
		}
	}

	@Override
	public PlaylistEditResult deleteSongFromPlaylist(int playlistId, int songId) {
		try {
			Playlist playlist = em.find(Playlist.class, playlistId);
			Music music = em.find(Music.class, songId);
			playlist.getSongs().remove(music);
			return PlaylistEditResult.Success;
		} catch(Exception e) {
			return PlaylistEditResult.Error;
		}
	}

}
