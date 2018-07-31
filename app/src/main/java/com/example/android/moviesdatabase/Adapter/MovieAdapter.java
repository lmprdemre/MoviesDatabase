package com.example.android.moviesdatabase.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.moviesdatabase.Model.Movie;
import com.example.android.moviesdatabase.Model.MoviesResponse;
import com.example.android.moviesdatabase.R;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by emres on 24.07.2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movieArrayList;
    ItemListener itemListener;


    public MovieAdapter(Context context, List<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_movie,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        /*
        holder.title.setText(movieArrayList.get(position).getTitle());
        holder.data.setText(movieArrayList.get(position).getReleaseDate());
        holder.description.setText(movieArrayList.get(position).getOverview());
        holder.rating.setText(movieArrayList.get(position).getVoteAverage().toString());
        */

        // Necessary for click  Recyclerview items
        Movie movie = movieArrayList.get(position);
        holder.bindItem(movie);
    }

    @Override
    public int getItemCount() {

        return movieArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,data,description,rating;
        ImageView ratingImage,posterImage;
        Movie movie;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_id);
            data = (TextView) itemView.findViewById(R.id.subtitle_id);
            description = (TextView) itemView.findViewById(R.id.description_id);
            rating = (TextView) itemView.findViewById(R.id.rating_id);
            ratingImage = (ImageView) itemView.findViewById(R.id.rating_image_id);
            posterImage = (ImageView) itemView.findViewById(R.id.posterId);

            // Necessary for click  Recyclerview items
            itemView.setOnClickListener(this);
        }

        // Necessary for click  Recyclerview items
        public void bindItem (Movie movie){
            this.movie=movie;
            title.setText(movie.getTitle());
            data.setText(movie.getReleaseDate());
            description.setText(movie.getOverview());
            rating.setText(movie.getVoteAverage().toString());
            Glide.with(context).load(movie.getPosterPath()).into(posterImage);
        }

        @Override
        public void onClick(View v) {
            if(itemListener != null){
                itemListener.onItemClick(movie);
            }
        }
    }

    // Necessary for click  Recyclerview items
    public interface ItemListener{
        public void onItemClick(Movie movie);
    }

    // Necessary for click  Recyclerview items
    public void setItemListener(ItemListener itemListener){
        this.itemListener=itemListener;
    }
}
