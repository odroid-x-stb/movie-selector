package fr.enseirb.odroid.XMLParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

public class ContainerData {	
	
	static public Context context;
	
	public static ArrayList<Movie> getFeeds(){
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parser = null;
		ArrayList<Movie> movies = null;
		try {
			parser = fabrique.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		URL url = null;
		try {
			url = new URL("http://collocmarcel.free.fr/vod.xml");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		/* 
		 * Le handler sera gestionnaire du fichier XML c'est a dire que c'est lui qui sera charge
		 * des operations de parsing. On vera cette classe en details ci apres.
		*/
		DefaultHandler handler = new ParserXMLHandler();
		try {
			parser.parse(url.openConnection().getInputStream(), handler);
			
			// On recupere la liste des films
			movies = ((ParserXMLHandler) handler).getData();
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return movies;
	}
}
