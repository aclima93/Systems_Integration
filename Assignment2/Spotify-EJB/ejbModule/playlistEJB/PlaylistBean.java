package playlistEJB;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
