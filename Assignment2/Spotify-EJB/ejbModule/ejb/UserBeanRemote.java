package ejb;

import javax.ejb.Remote;

import jpa.User;

@Remote
public interface UserBeanRemote {
	public boolean isLoggedIn();
	public RegisterResult register(User user);
	public LoginResult login(String email, String password);
	public LogoutResult logout();
	public UserEditResult changeUserName(String name);
	public UserEditResult changeUserEmail(String email);
	public UserEditResult changeUserPassword(String password);
	public UserDeleteResult deleteUser();
}
