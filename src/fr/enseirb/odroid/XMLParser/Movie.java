package fr.enseirb.odroid.XMLParser;

public class Movie {
	private long id;
	private String title;
	
	public Movie(long id, String title, String link, String pubDate, String creator, String description) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public Movie() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
