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
		if (movies == null)
			return 0;
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