/**
 * Copyright (C) 2012 Alexis Ferriere <aferriere@enseirb-matmeca.fr>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import android.widget.Toast;

public class ContainerData {	

	static public Context context;

	/* method which returns the list of films */
	public static ArrayList<Movie> getMovies(String ip, Context context){
		/* use SAX to parse XML file on the server  */
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

		URL url;
		try {
			/* open the link to the XML file, thanks to the IP adress given in parameter, if possible */
			url = new URL("http://" + ip + ":8080/dash-manager/vod");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Toast.makeText(context, "Malformed URL or unreachable XML", Toast.LENGTH_LONG).show();
			return null;
		}

		/* launches the parsing to get back the list of movies */
		DefaultHandler handler = new ParserXMLHandler();
		try {
			parser.parse(url.openConnection().getInputStream(), handler);
			movies = ((ParserXMLHandler) handler).getData();
		} catch (SAXException e) {
			e.printStackTrace();
			Toast.makeText(context, "Impossible to parse vod.xml", Toast.LENGTH_LONG).show();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, "Connection to server impossible", Toast.LENGTH_LONG).show();
			return null;
		}
		return movies;
	}
}