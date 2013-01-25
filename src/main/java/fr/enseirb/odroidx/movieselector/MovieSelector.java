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

import java.util.ArrayList;
import java.util.List;

import fr.enseirb.odroidx.movieselector.ParseXMLTask.ParseXMLTaskListenner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MovieSelector extends Activity implements ParseXMLTaskListenner {
	
	private String ip;
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private ListMovieAdapter listMovieAdapter;
	private STBRemoteControlCommunication stbrcc;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		stbrcc = new STBRemoteControlCommunication(this);
		
		/* gets back IP adress of the server from the home application */
		Intent receivedIntent = getIntent();
		if(receivedIntent != null && receivedIntent.getStringExtra("serverIP") != null) {
			ip = receivedIntent.getStringExtra("serverIP");
		} else {
			/* close the app if there is no IP */
			Toast.makeText(this, "Impossible to get server IP, check your settings in the home", Toast.LENGTH_LONG).show();
			finish();
		}
		
		/* View generation */
		setContentView(R.layout.main);
		new ParseXMLTask(this).execute(ip);
		listMovieAdapter = new ListMovieAdapter(this, movies);
		ListView m = ((ListView)findViewById(R.id.listMovies));
		m.setAdapter(listMovieAdapter);
		m.setOnItemClickListener(new OnItemClickListener() {
			/* action when you click on the film you want to play */
			/* launch our VLC here */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {		
				Intent vlcLaunch = getPackageManager().getLaunchIntentForPackage("org.videolan.vlc");
				Movie selectedMovie = (Movie) parent.getItemAtPosition(position); 
				String selectedMovieLink = selectedMovie.getLink();
				vlcLaunch.putExtra("isVodFile", true);
				vlcLaunch.putExtra("URL",selectedMovieLink);
				startActivity(vlcLaunch);
			}
		});
	}

	
	@Override
	protected void onStart() {
		stbrcc.doBindService();
		super.onStart();
	}

	@Override
	protected void onStop() {
		stbrcc.doUnbindService();
		super.onStop();
	}
	
	@Override
	public void parseSucceed(List<Movie> movies) {
		this.movies.addAll(movies);
		listMovieAdapter.notifyDataSetChanged();
	}

	@Override
	public void parseFailed(String errorMessage) {
		Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
	}
}
