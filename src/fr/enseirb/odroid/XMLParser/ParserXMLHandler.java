package fr.enseirb.odroid.XMLParser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ParserXMLHandler extends DefaultHandler {

	// nom des tags XML
	private final String VOD = "vod";
	private final String MOVIE = "movie";

	// liste de film	
	private ArrayList<Movie> movies;

	// temoin indiquant si nous sommes dans un item
	private boolean inItem;

	// Film courant
	private Movie currentMovie;

	// Buffer contenant les donnees d'un tag XML
	private StringBuffer buffer;

	@Override
	public void processingInstruction(String target, String data) throws SAXException {		
		super.processingInstruction(target, data);
	}

	public ParserXMLHandler() {
		super();		
	}

	// * Cette methode est appelee par le parser une et une seule  
	// * fois au demarrage de l'analyse de votre flux xml. 
	// * Elle est appelee avant toutes les autres methodes de l'interface,  
	// * a l'exception unique, evidemment, de la methode setDocumentLocator. 
	// * Cet evenement devrait vous permettre d'initialiser tout ce qui doit 
	// * l'etre avant le debut du parcours du document.

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		movies = new ArrayList<Movie>();
	}

	/* 
	 * Fonction declenchee lorsque le parser trouve un tag XML
	 * C'est cette methode que nous allons utiliser pour instancier un nouveau feed
	 */
	@Override
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		buffer = new StringBuffer();		

		if (localName.equalsIgnoreCase(VOD)){			
			//this.currentMovie = new Movie();
			inItem = true;
		}
	}


	// * Fonction etant declenchee lorsque le parser a parse 	
	// * l'interieur de la balise XML La methode characters  
	// * a donc fait son ouvrage et tous les caracteres inclus 
	// * dans la balise en cours sont copies dans le buffer 
	// * On peut donc tranquillement les recuperer pour completer
	// * notre objet currentFeed

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {		

		if (localName.equalsIgnoreCase(MOVIE)){
			if(inItem){
				buffer = null;
			}
		}
		if (localName.equalsIgnoreCase(VOD)){		
			inItem = false;
		}
	}

	// * Tout ce qui est dans l'arborescence mais n'est pas partie  
	// * integrante d'un tag, declenche la levee de cet evenement.  
	// * En general, cet evenement est donc leve tout simplement 
	// * par la presence de texte entre la balise d'ouverture et 
	// * la balise de fermeture

	public void characters(char[] ch,int start, int length)	throws SAXException{		
		String lecture = new String(ch,start,length);
		if(buffer != null) {
			this.currentMovie = new Movie();
			this.currentMovie.setTitle(lecture);				
			movies.add(currentMovie);
		}
	}

	public ArrayList<Movie> getData(){
		return movies;
	}
}
