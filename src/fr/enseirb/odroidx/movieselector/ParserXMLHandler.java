package fr.enseirb.odroidx.movieselector;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ParserXMLHandler extends DefaultHandler {

	private final String VOD = "vod";
	private final String MOVIE = "movie";

	private ArrayList<Movie> movies;

	private boolean inItem;

	private Movie currentMovie;
	private String currentTitle = null;
	private String currentLink = null;

	private StringBuffer buffer;

	@Override
	public void processingInstruction(String target, String data) throws SAXException {		
		super.processingInstruction(target, data);
	}

	public ParserXMLHandler() {
		super();		
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		movies = new ArrayList<Movie>();
	}

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