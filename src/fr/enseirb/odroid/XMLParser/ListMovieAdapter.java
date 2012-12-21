package fr.enseirb.odroid.XMLParser;

import java.util.ArrayList;

import fr.enseirb.odroid.XMLParser.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListMovieAdapter extends BaseAdapter {

	private ArrayList<Movie> movies;
	
	// * Le LayoutInflater permet de parser un layout XML et de 
	// * te transcoder en IHM Android. Pour respecter l'interface 
	// * BaseAdapter 
	private LayoutInflater inflater;
	
	public ListMovieAdapter(Context context,ArrayList<Movie> movies) {
		inflater = LayoutInflater.from(context);
		this.movies = movies;
	}
	
	// * il nous faut specifier la methode "count()". 
	// * Cette methode permet de connaitre le nombre d'items presents 
	// * dans la liste. Dans notre cas, il faut donc renvoyer le nombre
	// * de personnes contenus dans "mListP".
	
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

	
	// * Le parametre "convertView" permet de recycler les elements 
	// * de notre liste. En effet, l'operation pour convertir un layout 
	// * XML en IHM standard est tres couteuse pour la plateforme Android. 
	// * On nous propose ici de reutiliser des occurences creees qui ne sont 
	// * plus affichees. Donc si ce parametre est "null" alors, il faut "inflater" 
	// * notre layout XML, sinon on le reutilise
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