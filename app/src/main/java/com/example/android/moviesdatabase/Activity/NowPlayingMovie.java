package com.example.android.moviesdatabase.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.android.moviesdatabase.Adapter.MovieAdapter;
import com.example.android.moviesdatabase.Model.Movie;
import com.example.android.moviesdatabase.Model.MoviesResponse;
import com.example.android.moviesdatabase.R;
import com.example.android.moviesdatabase.Service.RequestInterface;
import com.example.android.moviesdatabase.Service.ServiceGenerator;
import com.example.android.moviesdatabase.Ui.DividerItemDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by emres on 28.07.2017.
 */

public class NowPlayingMovie extends AppCompatActivity implements MovieAdapter.ItemListener {

    RecyclerView recyclerViewNowPlayingMovie;

    // Insert moviedb.org Api key here
    private static final String API_KEY = "5cb6e5d62f03ebda9db5adf7ed9a354a";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // to make for fullscreen and remove status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nowplaying_movie);

        recyclerViewNowPlayingMovie = (RecyclerView)findViewById(R.id.nowplayingmovies_recyclerview_Id);
        //For divider between recyclerview list items
        recyclerViewNowPlayingMovie.addItemDecoration(new DividerItemDecoration(this,R.drawable.divider));
        RequestInterface requestInterface = ServiceGenerator.createService(RequestInterface.class);

        Call<MoviesResponse> call = requestInterface.getNowPlayingMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie> moviesNowPlaying = response.body().getResults();
                MovieAdapter movieAdapter = new MovieAdapter(getBaseContext(),moviesNowPlaying);
                recyclerViewNowPlayingMovie.setAdapter(movieAdapter);

                // Necessary for click  Recyclerview items
                movieAdapter.setItemListener(NowPlayingMovie.this);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Fail oldun evlat",Toast.LENGTH_LONG).show();
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.ic_backarrow:
                        Intent i1 = new Intent(NowPlayingMovie.this,MainActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_popular:
                        Intent i2 = new Intent(NowPlayingMovie.this,PopularMovie.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_nowplaying:
                        break;

                    case R.id.ic_upcoming:
                        Intent i3 = new Intent(NowPlayingMovie.this,UpComingMovie.class);
                        startActivity(i3);
                        break;
                }

                return false;
            }
        });

    }

    // Operations when clicking the recyclerview list items
    @Override
    public void onItemClick(Movie movie) {
        // Toast.makeText(MainActivity.this, movie.getOverview() + "YOK ARTIK LBJ", Toast.LENGTH_LONG).show();
        int id = movie.getId();
        Intent i = new Intent(NowPlayingMovie.this,Popup.class);
        i.putExtra("movieId",id);
        startActivity(i);
    }

    // For change font in this activity
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
