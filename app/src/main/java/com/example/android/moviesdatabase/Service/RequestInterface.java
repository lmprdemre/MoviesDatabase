package com.example.android.moviesdatabase.Service;

import com.example.android.moviesdatabase.Model.Movie;
import com.example.android.moviesdatabase.Model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by emres on 24.07.2017.
 */

public interface RequestInterface {
    //https://developers.themoviedb.org/3/movies/get-top-rated-movies
    @GET("movie/top_rated/")
    Call<MoviesResponse> getTopRatedMovies (@Query("api_key") String apiKey);

    //https://developers.themoviedb.org/3/movies/get-popular-movies
    @GET("movie/popular/")
    Call<MoviesResponse> getPopularMovies (@Query("api_key") String apiKey);

    //https://developers.themoviedb.org/3/movies/get-now-playing
    @GET("movie/now_playing/")
    Call<MoviesResponse> getNowPlayingMovies (@Query("api_key") String apiKey);

    //https://developers.themoviedb.org/3/movies/get-upcoming
    @GET("movie/upcoming/")
    Call<MoviesResponse> getUpComingMovies (@Query("api_key") String apiKey);

    //https://developers.themoviedb.org/3/movies/get-movie-details
    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetail (@Path("movie_id") int movie_id, @Query("api_key") String apiKey);

}
