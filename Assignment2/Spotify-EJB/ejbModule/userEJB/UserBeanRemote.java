package userEJB;

import javax.ejb.Remote;

import jpa.User;

@Remote
public interface UserBeanRemote {
	public boolean isLoggedIn();
	public UserRegisterResult register(User user);
	public UserLoginResult login(String email, String password);
	public UserLogoutResult logout();
	public UserEditResult changeUserName(String name);
	public UserEditResult changeUserEmail(String email);
	public UserEditResult changeUserPassword(String password);
	public UserDeleteResult deleteUser();
}
