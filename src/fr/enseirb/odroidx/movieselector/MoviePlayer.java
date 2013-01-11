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

public class MoviePlayer extends Activity {
	
	private String ip = "";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    STBRemoteControlCommunication stbrcc = new STBRemoteControlCommunication(this);
	    stbrcc.doBindService();
		
		if(savedInstanceState != null) {
			ip = savedInstanceState.getString("serverIP");
		}
		else {
			Toast.makeText(this, "Impossible to get server IP", Toast.LENGTH_LONG).show();
			finish();
		}
		
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
				String selectedMovieTitle = selectedMovie.getTitle();
				String selectedMovieLink = selectedMovie.getLink();
				vlcLaunch.putExtra("useVLC", true);
				vlcLaunch.putExtra("URL", selectedMovieLink);
//				System.out.println("intent launched, URL = " + selectedMovieLink);
				
				startActivity(vlcLaunch);
			}
		});
	}
}