package ejb;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.User;

/**
 * Session Bean implementation class MainBean
 */
@Stateful
public class MainBean implements MainBeanRemote {
	@PersistenceContext(name="Spotify")
	EntityManager em;
	private boolean loggedIn;

    /**
     * Default constructor. 
     */
    public MainBean() {
        this.loggedIn = false;
    }
    
    /**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public boolean login(String email, String password) {
    	Query query = em.createQuery("from User u where u.email = :email");
    	query.setParameter("email",	email);
    	System.out.println(query);
    	@SuppressWarnings("unchecked")
    	List<User> result = query.getResultList();
    	if(result.isEmpty()) {
    		System.out.println("Não há users");
    		return false;
    	}
    	for(User u : result) {
    		System.out.println("User encontrado. name: "+u.getName()+", email: "+u.getEmail()+", password: "+u.getPassword());
    		if(u.getPassword().compareTo(password) == 0) {
    			return true;
    		}
    	}
    	return false;
    }

}
