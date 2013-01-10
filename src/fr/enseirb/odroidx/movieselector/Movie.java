package fr.enseirb.odroidx.movieselector;

public class Movie {
	private long id;
	private String title;
	private String link;
	
	public Movie(long id, String title, String link) {
		super();
		this.id = id;
		this.title = title;
		this.setLink(link);
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
