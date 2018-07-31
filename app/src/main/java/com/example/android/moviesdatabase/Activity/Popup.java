package com.example.android.moviesdatabase.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviesdatabase.Model.Movie;
import com.example.android.moviesdatabase.Model.MoviesResponse;
import com.example.android.moviesdatabase.R;
import com.example.android.moviesdatabase.Service.RequestInterface;
import com.example.android.moviesdatabase.Service.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by emres on 3.08.2017.
 */

public class Popup extends AppCompatActivity {

    TextView popMessage;
    Movie movie;

    // Insert moviedb.org Api key
    private static final String API_KEY = "5cb6e5d62f03ebda9db5adf7ed9a354a";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // to make for fullscreen and remove status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.widthPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.9));


        int idMovies = getIntent().getIntExtra("movieId",0);

        RequestInterface requestInterface = ServiceGenerator.createService(RequestInterface.class);
        Call<Movie> call = requestInterface.getMovieDetail(idMovies,API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movies = response.body();
                popMessage = (TextView)findViewById(R.id.popTextId);
                popMessage.setText(movies.getOverview());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(Popup.this, "Fail oldun evlat", Toast.LENGTH_LONG).show();
            }
        });

    }

    // For change font in this activity
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
