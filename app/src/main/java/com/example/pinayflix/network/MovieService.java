package com.example.pinayflix.network;

import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.movie.MovieResult;
import com.example.pinayflix.model.datamodel.review.ReviewResult;
import com.example.pinayflix.model.datamodel.trailer.TrailerResult;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Single<MovieResult> getPopularMovies(@Query("page") String page);

    @GET("movie/upcoming")
    Single<MovieResult> getUpComingMovies(@Query("page") String page);

    @GET("movie/now_playing")
    Single<MovieResult> getNowPlayingMovies(@Query("page") String page);

    @GET("movie/{id}")
    Single<Movie> getMovieDetails(@Path("id") int id);

    @GET("movie/latest")
    Single<Movie> getLatestMovie();

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByGenre(@Query("with_genres") String genre, @Query("page") int page,
                                             @Query("primary_release_date.gte") String release);
    @GET("movie/{id}/videos")
    Single<TrailerResult> getTrailer(@Path("id")int movieId);

    @GET("movie/{movie_id}/reviews")
    Single<ReviewResult> getMovieReviews(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}/recommendations")
    Single<MovieResult> getRecommendations(@Path("movie_id")int movieId);

}
