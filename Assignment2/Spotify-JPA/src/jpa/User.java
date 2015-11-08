package jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	/**
	 * {@link Playlist}s created by this {@link User}
	 */
	@OneToMany(mappedBy="creator")
	private List<Playlist> playlists;
	/**
	 * {@link Music}s uploaded by this {@link User}
	 */
	@OneToMany(mappedBy="uploader")
	private List<Music> musics;
	private String name;
	private String email;
	private String password;

	public User() {
		super();
	}

	/**
	 * @param name
	 * @param email
	 * @param password
	 */
	public User(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the playlists
	 */
	public List<Playlist> getPlaylists() {
		return playlists;
	}

	/**
	 * @param playlists the playlists to set
	 */
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	/**
	 * @return the musics
	 */
	public List<Music> getMusics() {
		return musics;
	}

	/**
	 * @param musics the musics to set
	 */
	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
