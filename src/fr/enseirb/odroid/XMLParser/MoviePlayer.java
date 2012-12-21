package fr.enseirb.odroid.XMLParser;

import java.util.ArrayList;

import fr.enseirb.odroid.XMLParser.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MoviePlayer extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ArrayList<Movie> movies = ContainerData.getFeeds();
		for (Movie movie : movies) {
			Log.e("movieSelector",movie.toString());
		}
		ListMovieAdapter lfa = new ListMovieAdapter(this, movies);
		((ListView)findViewById(R.id.listFeed)).setAdapter(lfa);

	}
}