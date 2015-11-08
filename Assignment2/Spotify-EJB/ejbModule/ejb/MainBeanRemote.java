package ejb;

import javax.ejb.Remote;

@Remote
public interface MainBeanRemote {
	public boolean isLoggedIn();
	public boolean login(String email, String password);
}
