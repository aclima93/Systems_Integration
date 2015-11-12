package userEJB;

import javax.ejb.Remote;

import jpa.User;

@Remote
public interface UserBeanRemote {
	public UserRegisterResult register(String name, String email, String password);
	public User login(String email, String password);
	public UserEditResult changeUserName(User user, String name);
	public UserEditResult changeUserEmail(User user, String email);
	public UserEditResult changeUserPassword(User user, String password);
	public UserDeleteResult deleteUser(User user);
}
