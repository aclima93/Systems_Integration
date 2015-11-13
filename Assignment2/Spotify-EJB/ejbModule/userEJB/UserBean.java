package userEJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.User;

/**
 * Session Bean implementation class MainBean
 */
@Stateless
public class UserBean implements UserBeanRemote {
	@PersistenceContext()
	EntityManager em;
	

    /**
     * Default constructor. 
     */
    public UserBean() {
    }

	public UserRegisterResult register(String name, String email, String password) {
		try {
			Query query = em.createQuery("from User u where u.email = :email");
			query.setParameter("email", email);
			@SuppressWarnings("unchecked")
			List<User> result = query.getResultList();
			if(!result.isEmpty()) {
				return UserRegisterResult.EmailAlreadyUsed;
			}
			em.merge(new User(name, email, password));
			return UserRegisterResult.Success;
		} catch(Exception e) {
			return UserRegisterResult.Error;
		}
	}

	public User login(String email, String password) {
		try {
	    	Query query = em.createQuery("from User u where u.email = :email");
	    	query.setParameter("email",	email);
	    	@SuppressWarnings("unchecked")
	    	List<User> result = query.getResultList();
	    	if(result.isEmpty() || result.size() > 1) {
	    		return null;
	    	}
	    	User u = result.get(0);
    		if(u.getPassword().compareTo(password) == 0) {
    			return u;
    		} else {
    			return null;
    		}
		} catch(Exception e) {
			return null;
		}
    }
	
	public User changeUserName(User user, String name) {
		try {
			User newUser = em.find(User.class, user.getId());
			newUser.setName(name);
			return newUser;
		} catch (Exception e) {
			return null;
		}
	}
	
	public User changeUserEmail(User user, String email) {
		try {
			Query query = em.createQuery("from User u where u.email = :email");
			query.setParameter("email", email);
			@SuppressWarnings("unchecked")
			List<User> result = query.getResultList();
			if(!result.isEmpty()) {
				return user;
			} else {
				User newUser = em.find(User.class, user.getId());
				newUser.setEmail(email);
				return newUser;
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	public User changeUserPassword(User user, String password) {
		try {
			User newUser = em.find(User.class, user.getId());
			newUser.setPassword(password);
			return newUser;
		} catch (Exception e) {
			return null;
		}
	}
	
	public UserDeleteResult deleteUser(User user) {
		try {
			User newUser = em.find(User.class, user.getId());
			em.remove(newUser);
			return UserDeleteResult.Success;
		} catch(Exception e) {
			return UserDeleteResult.Error;
		}
	}
	
}
