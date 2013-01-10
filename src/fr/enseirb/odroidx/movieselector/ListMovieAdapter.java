package fr.enseirb.odroidx.movieselector;

import java.util.ArrayList;

import fr.enseirb.odroidx.movieselector.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListMovieAdapter extends BaseAdapter {

	private ArrayList<Movie> movies;
	
	private LayoutInflater inflater;
	
	public ListMovieAdapter(Context context,ArrayList<Movie> movies) {
		inflater = LayoutInflater.from(context);
		this.movies = movies;
	}
	
	@Override
	public int getCount() {
		return movies.size();
	}
	
	@Override
	public Object getItem(int index) {
		return movies.get(index);
	}

	@Override
	public long getItemId(int index) {
		return this.movies.get(index).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		MovieView fv;		
		
		if (convertView == null) {
			fv = new MovieView();
			convertView = inflater.inflate(R.layout.movie_view, null);

			fv.title = (TextView)convertView.findViewById(R.id.title);
			convertView.setTag(fv);

		} else {
			fv = (MovieView) convertView.getTag();
		}						
		fv.title.setText(movies.get(position).getTitle());
		
		return convertView;
	}
}