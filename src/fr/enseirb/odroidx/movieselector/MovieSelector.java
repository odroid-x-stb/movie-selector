package fr.enseirb.odroidx.movieselector;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MovieSelector extends Activity {
	
	private String ip;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent receivedIntent = getIntent();
		if(receivedIntent != null) {
			ip = receivedIntent.getStringExtra("serverIP");
		}
		else {
			Toast.makeText(this, "Impossible to get server IP", Toast.LENGTH_LONG).show();
			finish();
		}
		
		setContentView(R.layout.main);
		ArrayList<Movie> movies = ContainerData.getMovies(ip, getApplicationContext());
		if (movies == null)
			finish();
		ListMovieAdapter listMovieAdapter = new ListMovieAdapter(this, movies);
		ListView m = ((ListView)findViewById(R.id.listMovies));
		m.setAdapter(listMovieAdapter);
		m.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				
				Intent vlcLaunch = getPackageManager().getLaunchIntentForPackage("org.videolan.vlc");
				
				Movie selectedMovie = (Movie) parent.getItemAtPosition(position); 
				String selectedMovieName = selectedMovie.getTitle();
				vlcLaunch.putExtra("isVodFile", true);
				vlcLaunch.putExtra("URL","http://"+ ip + "/VOD/" + selectedMovieName +".mpd");
				System.out.println("intent launched, URL = " + ip + "/VOD/" + selectedMovieName +".mpd");
				
				startActivity(vlcLaunch);
			}
		});
	}
}
