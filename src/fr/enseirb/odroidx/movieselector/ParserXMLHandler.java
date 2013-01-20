package fr.enseirb.odroidx.movieselector;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ParserXMLHandler extends DefaultHandler {

	/* tags you want to inspect */
	private final String VOD = "vod";
	private final String MOVIE = "movie";

	private ArrayList<Movie> movies;

	/* characterise if the parser is parsing into a VOD tag */
	private boolean inItem;

	private Movie currentMovie;
	private String currentTitle = null;
	private String currentLink = null;

	/* buffer to store the parsed strings */
	private StringBuffer buffer;

	@Override
	public void processingInstruction(String target, String data) throws SAXException {		
		super.processingInstruction(target, data);
	}

	public ParserXMLHandler() {
		super();		
	}

	/* pre-processing, at the beginning of the document*/
	/* here, allocation of the arrayList where the movies objects will be stored */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		movies = new ArrayList<Movie>();
	}

	/* Method where you can access the attributes of a tag */
	/* here, getting back of the link to reach a movie if you are in a MOVIE tag, just marks that you enter into a VOD */
	@Override
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		buffer = new StringBuffer();		

		if (localName.equalsIgnoreCase(VOD)){			
			inItem = true;
		}
		else if (localName.equalsIgnoreCase(MOVIE)){	
			this.currentLink = attributes.getValue("link");
		}
	}

	/* Method where you can access the value of a tag */
	/* here, storage in the movie object, leaves the VOD zone if required*/
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {		

		if (localName.equalsIgnoreCase(MOVIE)){
			if(inItem){
				this.currentMovie = new Movie();
				this.currentMovie.setLink(currentLink);
				this.currentMovie.setTitle(currentTitle);
				movies.add(currentMovie);
				buffer = null;
			}
		}
		if (localName.equalsIgnoreCase(VOD)){		
			inItem = false;
		}
	}

	/* method called between startElement and endElement, reading the title of the current movie */
	public void characters(char[] ch,int start, int length)	throws SAXException{		
		String lecture = new String(ch,start,length);
		if(buffer != null) {
			this.currentTitle = lecture;
		}
	}

	public ArrayList<Movie> getData(){
		return movies;
	}
}