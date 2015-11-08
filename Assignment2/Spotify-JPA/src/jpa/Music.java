package jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Music
 *
 */
@Entity
public class Music implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	/**
	 * The {@link Playlist}s to which this {@link Music} belongs
	 */
	@ManyToMany(mappedBy="songs")
	private List<Playlist> playlists;
	/**
	 * {@link User} who uploaded this {@link Music}
	 */
	@ManyToOne(optional=true)
	private User uploader;
	private String title;
	private String artist;
	private String album;
	private String year;
	private String path;

	public Music() {
		super();
	}

	/**
	 * @param title
	 * @param artist
	 * @param album
	 * @param year
	 * @param path
	 */
	public Music(String title, String artist, String album, String year, String path) {
		super();
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
		this.path = path;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
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
	 * @return the uploader
	 */
	public User getUploader() {
		return uploader;
	}

	/**
	 * @param uploader the uploader to set
	 */
	public void setUploader(User uploader) {
		this.uploader = uploader;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   
	
}
