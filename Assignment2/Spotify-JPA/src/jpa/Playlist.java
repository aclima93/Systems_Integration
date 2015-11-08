package jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Playlist
 *
 */
@Entity
public class Playlist implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	/**
	 * {@link Music}s on this {@link Playlist} 
	 */
	@ManyToMany
	private List<Music> songs;
	/**
	 * {@link User} that created this {@link Playlist}
	 */
	@ManyToOne(optional=true)
	private User creator;
	private String name;

	public Playlist() {
		super();
	}

	/**
	 * @param name
	 */
	public Playlist(String name) {
		super();
		this.name = name;
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
	 * @return the songs
	 */
	public List<Music> getSongs() {
		return songs;
	}

	/**
	 * @param songs the songs to set
	 */
	public void setSongs(List<Music> songs) {
		this.songs = songs;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
   
}
