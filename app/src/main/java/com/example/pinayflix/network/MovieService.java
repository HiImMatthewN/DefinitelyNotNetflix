package com.example.pinayflix.network;

import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.movie.MovieDetails;
import com.example.pinayflix.model.datamodel.movie.MovieResult;
import com.example.pinayflix.model.datamodel.review.ReviewResult;
import com.example.pinayflix.model.datamodel.trailer.TrailerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Call<MovieResult> getPopularMovies(@Query("page") String page);

    @GET("movie/upcoming")
    Call<MovieResult> getUpComingMovies(@Query("page") String page);

    @GET("movie/now_playing")
    Call<MovieResult> getNowPlayingMovies(@Query("page") String page);

    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") int id);

    @GET("movie/latest")
    Call<Movie> getLatestMovie();

    @GET("discover/movie")
    Call<MovieResult> getMoviesByGenre(@Query("with_genres") String genre, @Query("page") int page,
                                       @Query("primary_release_date.gte") String release,
                                       @Query("vote_count.gte") int voteCount);
    @GET("movie/{id}/videos")
    Call<TrailerResult> getTrailer(@Path("id")int movieId);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResult> getMovieReviews(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}/recommendations")
    Call<MovieResult> getRecommendations(@Path("movie_id")int movieId);

}
