package com.example.beetel.moviesapp.data.api;


import com.example.beetel.moviesapp.data.model.MovieResultListModel;
import com.example.beetel.moviesapp.data.model.MovieReviewListModel;
import com.example.beetel.moviesapp.data.model.MovieVideoListModel;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by beetel on 12/03/2016.
 */
public interface MovieApi {


    @GET("discover/movie")
    Observable<MovieResultListModel> lodeMoviesRx(@Query("sort_by") String sort, @Query("api_key") String apikey);

    @GET("movie/{id}/videos")
    Observable<MovieVideoListModel> getMovieTrailerRx(@Path("id") String id, @Query("api_key") String apikey);

    @GET("movie/{id}/reviews")
    Observable<MovieReviewListModel> getMovieReviewRx(@Path("id") String id, @Query("api_key") String apikey);

}

