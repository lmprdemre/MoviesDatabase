package com.example.android.moviesdatabase.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.example.android.moviesdatabase.Adapter.MovieAdapter;
import com.example.android.moviesdatabase.Model.Movie;
import com.example.android.moviesdatabase.Model.MoviesResponse;
import com.example.android.moviesdatabase.R;
import com.example.android.moviesdatabase.Service.RequestInterface;
import com.example.android.moviesdatabase.Service.ServiceGenerator;
import com.example.android.moviesdatabase.Ui.BottomNavigationViewHelper;
import com.example.android.moviesdatabase.Ui.DividerItemDecoration;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemListener{

    RecyclerView recyclerView;

    // Insert moviedb.org Api key
    private static final String API_KEY = "5cb6e5d62f03ebda9db5adf7ed9a354a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // to make for fullscreen and remove status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.movies_recyclerview_Id);
        //For divider between recyclerview list items
        recyclerView.addItemDecoration(new DividerItemDecoration(this,R.drawable.divider));

        RequestInterface requestInterface = ServiceGenerator.createService(RequestInterface.class);
        Call<MoviesResponse> call = requestInterface.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                List<Movie> movies = (List<Movie>) response.body().getResults();
                MovieAdapter movieAdapter = new MovieAdapter(getBaseContext(),movies);
                recyclerView.setAdapter(movieAdapter);

                // Necessary for click  Recyclerview items
                movieAdapter.setItemListener(MainActivity.this);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Fail oldun evlat",Toast.LENGTH_LONG).show();

            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.ic_backarrow:
                        break;

                    case R.id.ic_popular:
                        Intent i1 = new Intent(MainActivity.this,PopularMovie.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_nowplaying:
                        Intent i2 = new Intent(MainActivity.this,NowPlayingMovie.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_upcoming:
                        Intent i3 = new Intent(MainActivity.this,UpComingMovie.class);
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
        Intent i = new Intent(MainActivity.this,Popup.class);
        i.putExtra("movieId",id);
        startActivity(i);
    }

    // For change font in this activity
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
