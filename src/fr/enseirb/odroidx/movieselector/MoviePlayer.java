package fr.enseirb.odroidx.movieselector;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MoviePlayer extends Activity {
	
	private String ip = "http://collocmarcel.free.fr";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null) {
			ip = savedInstanceState.getString("serverIP");
		}
//		else {
//			Toast.makeText(this, "Impossible to get server IP", Toast.LENGTH_LONG).show();
//			finish();
//		}
		
		setContentView(R.layout.main);
		ArrayList<Movie> movies = ContainerData.getMovies(ip);
		ListMovieAdapter listMovieAdapter = new ListMovieAdapter(this, movies);
		ListView m = ((ListView)findViewById(R.id.listMovies));
		m.setAdapter(listMovieAdapter);
		m.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				
				Intent vlcLaunch = new Intent();
				
				Movie selectedMovie = (Movie) parent.getItemAtPosition(position); 
				String selectedMovieName = selectedMovie.getTitle();
//				vlcLaunch.putExtra("useVLC", true);
//				vlcLaunch.putExtra("URL", ip + "/VOD/" + selectedMovieName +".mpd");
				System.out.println("intent launched, URL = " + ip + "/VOD/" + selectedMovieName +".mpd");
				
//				startActivity(vlcLaunch);
			}
		});
	}
}