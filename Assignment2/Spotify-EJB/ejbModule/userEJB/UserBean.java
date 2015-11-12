package userEJB;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jpa.User;

/**
 * Session Bean implementation class MainBean
 */
@Stateful
public class UserBean implements UserBeanRemote {
	EntityManagerFactory emf;
	EntityManager em;
	private boolean loggedIn;
	private User currentUser;
	

    /**
     * Default constructor. 
     */
    public UserBean() {
        this.loggedIn = false;
        this.currentUser = null;
        this.emf = Persistence.createEntityManagerFactory("Spotify");
        this.em = this.emf.createEntityManager();
    }
    
    /**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public UserRegisterResult register(User user) {
		try {
			Query query = em.createQuery("from User u where u.email = :email");
			query.setParameter("email", user.getEmail());
			@SuppressWarnings("unchecked")
			List<User> result = query.getResultList();
			if(!result.isEmpty()) {
				return UserRegisterResult.EmailAlreadyUsed;
			}
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			return UserRegisterResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
			return UserRegisterResult.Error;
		}
	}

	public UserLoginResult login(String email, String password) {
		try {
	    	Query query = em.createQuery("from User u where u.email = :email");
	    	query.setParameter("email",	email);
	    	@SuppressWarnings("unchecked")
	    	List<User> result = query.getResultList();
	    	if(result.isEmpty()) {
	    		return UserLoginResult.UserDoesNotExist;
	    	}
	    	for(User u : result) {
	    		if(u.getPassword().compareTo(password) == 0) {
	    			this.loggedIn = true;
	    			this.currentUser = u;
	    			return UserLoginResult.Success;
	    		} else {
	    			return UserLoginResult.WrongPassword;
	    		}
	    	}
	    	return UserLoginResult.Error;
		} catch(Exception e) {
			e.printStackTrace();
			return UserLoginResult.Error;
		}
    }
	
	public UserLogoutResult logout() {
		try {
			this.currentUser = null;
			this.loggedIn = false;
			return UserLogoutResult.Success;
		} catch(Exception e) {
			return UserLogoutResult.Error;
		}
	}
	
	public UserEditResult changeUserName(String name) {
		try {
			em.getTransaction().begin();
			this.currentUser.setName(name);
			em.persist(this.currentUser);
			em.getTransaction().commit();
			return UserEditResult.Success;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return UserEditResult.Error;
		}
	}
	
	public UserEditResult changeUserEmail(String email) {
		try {
			Query query = em.createQuery("from User u where u.email = :email");
			query.setParameter("email", email);
			@SuppressWarnings("unchecked")
			List<User> result = query.getResultList();
			if(!result.isEmpty()) {
				return UserEditResult.EmailAlreadyUsed;
			} else {
				em.getTransaction().begin();
				this.currentUser.setEmail(email);
				em.persist(this.currentUser);
				em.getTransaction().commit();
				return UserEditResult.Success;
			}
		} catch(Exception e) {
			em.getTransaction().rollback();
			return UserEditResult.Error;
		}
	}
	
	public UserEditResult changeUserPassword(String password) {
		try {
			em.getTransaction().begin();
			this.currentUser.setPassword(password);
			em.persist(this.currentUser);
			em.getTransaction().commit();
			return UserEditResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
			return UserEditResult.Error;
		}
	}
	
	public UserDeleteResult deleteUser() {
		try {
			em.getTransaction().begin();
			em.remove(em.find(User.class, this.currentUser.getId()));
			em.getTransaction().commit();
			this.loggedIn = false;
			this.currentUser = null;
			return UserDeleteResult.Success;
		} catch(Exception e) {
			em.getTransaction().rollback();
			return UserDeleteResult.Error;
		}
	}
	
}
