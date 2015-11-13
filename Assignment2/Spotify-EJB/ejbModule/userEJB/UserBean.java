package userEJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import jpa.User;

/**
 * Session Bean implementation class MainBean
 */
@Stateless
public class UserBean implements UserBeanRemote {
	@PersistenceContext()
	EntityManager em;
	
	private static Logger logger = Logger.getLogger(UserBean.class);
	

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
				logger.info("Client attempted to create an account with an already used e-mail");
				return UserRegisterResult.EmailAlreadyUsed;
			}
			em.merge(new User(name, email, password));
			logger.info("New user registered");
			return UserRegisterResult.Success;
		} catch(Exception e) {
			logger.error("Error registering user");
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
	    		logger.info("Attempt to login with an unregistered user");
	    		return null;
	    	}
	    	User u = result.get(0);
    		if(u.getPassword().compareTo(password) == 0) {
    			logger.info("User "+u.getName()+" logged in");
    			return u;
    		} else {
    			logger.info("User "+u.getName()+" used a wrong password");
    			return null;
    		}
		} catch(Exception e) {
			logger.error("Error logging in");
			return null;
		}
    }
	
	public User changeUserName(User user, String name) {
		try {
			User newUser = em.find(User.class, user.getId());
			newUser.setName(name);
			logger.info("User "+user.getName()+" changed name to "+name);
			return newUser;
		} catch (Exception e) {
			logger.error("Error changing user name");
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
				logger.info("User "+user.getName()+" tried to change e-mail address, but it's already used");
				return user;
			} else {
				User newUser = em.find(User.class, user.getId());
				newUser.setEmail(email);
				logger.info("User "+user.getName()+" changed e-mail address to "+email);
				return newUser;
			}
		} catch(Exception e) {
			logger.error("Error changing user e-mail address");
			return null;
		}
	}
	
	public User changeUserPassword(User user, String password) {
		try {
			User newUser = em.find(User.class, user.getId());
			newUser.setPassword(password);
			logger.info("User "+user.getName()+" changed password");
			return newUser;
		} catch (Exception e) {
			logger.error("Error changing user password");
			return null;
		}
	}
	
	public UserDeleteResult deleteUser(User user) {
		try {
			User newUser = em.find(User.class, user.getId());
			em.remove(newUser);
			logger.info("Deleted user "+user.getEmail());
			return UserDeleteResult.Success;
		} catch(Exception e) {
			logger.error("Error deleting "+user.getEmail()+" user");
			return UserDeleteResult.Error;
		}
	}
	
}
