package fr.enseirb.odroidx.movieselector;

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
	
	public static ArrayList<Movie> getMovies(String ip){
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
//			url = new URL(ip + "/vod.xml");
			url = new URL("http://collocmarcel.free.fr/vod.xml");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		DefaultHandler handler = new ParserXMLHandler();
		try {
			parser.parse(url.openConnection().getInputStream(), handler);
			
			movies = ((ParserXMLHandler) handler).getData();
					
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return movies;
	}
}
