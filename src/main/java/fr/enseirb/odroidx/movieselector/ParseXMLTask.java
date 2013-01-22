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
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.AsyncTask;
import android.util.Log;

public class ParseXMLTask extends AsyncTask<String, Void, Boolean> {	
	
	private static final String TAG = ParseXMLTask.class.getSimpleName();
	
	public interface ParseXMLTaskListenner {
		public void parseSucceed(List<Movie> movies);
		public void parseFailed(String errorMessage);
	}
	
	private ParseXMLTaskListenner listenner;
	private List<Movie> movies = new ArrayList<Movie>();
	private String errorMessage;

	public ParseXMLTask(ParseXMLTaskListenner listenner) {
		this.listenner = listenner;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		String ip = params[0];
		String addr = "http://" + ip + ":8080/dash-manager/vod";
		Log.d(TAG, "Try to parse xml at: " + addr);
		DefaultHandler handler = new ParserXMLHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(addr, handler);
			movies = ((ParserXMLHandler) handler).getData();
			return true;
		} catch (SAXException e) {
			e.printStackTrace();
			errorMessage = "Impossible to parse vod.xml";
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			errorMessage = "Connection to server impossible";
			return false;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			errorMessage = "Parser initialization error";
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			listenner.parseSucceed(movies);
		} else {
			listenner.parseFailed(errorMessage);
		}
	}
}