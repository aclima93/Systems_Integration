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
			em.getTransaction().begin();
			em.persist(new User(name, email, password));
			em.getTransaction().commit();
			return UserRegisterResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
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
	
	public UserEditResult changeUserName(User user, String name) {
		try {
			em.getTransaction().begin();
			user.setName(name);
			em.getTransaction().commit();
			return UserEditResult.Success;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return UserEditResult.Error;
		}
	}
	
	public UserEditResult changeUserEmail(User user, String email) {
		try {
			Query query = em.createQuery("from User u where u.email = :email");
			query.setParameter("email", email);
			@SuppressWarnings("unchecked")
			List<User> result = query.getResultList();
			if(!result.isEmpty()) {
				return UserEditResult.EmailAlreadyUsed;
			} else {
				em.getTransaction().begin();
				user.setEmail(email);
				em.getTransaction().commit();
				return UserEditResult.Success;
			}
		} catch(Exception e) {
			em.getTransaction().rollback();
			return UserEditResult.Error;
		}
	}
	
	public UserEditResult changeUserPassword(User user, String password) {
		try {
			em.getTransaction().begin();
			user.setPassword(password);
			em.getTransaction().commit();
			return UserEditResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
			return UserEditResult.Error;
		}
	}
	
	public UserDeleteResult deleteUser(User user) {
		try {
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
			return UserDeleteResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
			return UserDeleteResult.Error;
		}
	}
	
}
