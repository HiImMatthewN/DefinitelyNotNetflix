package com.example.pinayflix.network;

import com.example.pinayflix.model.datamodel.review.ReviewResult;
import com.example.pinayflix.model.datamodel.trailer.TrailerResult;
import com.example.pinayflix.model.datamodel.tvshow.Season;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.model.datamodel.tvshow.TVShowResult;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TVShowService {

    @GET("tv/popular")
    Single<TVShowResult> getPopularTVShow(@Query("page") int page);

    @GET("tv/on_the_air")
    Single<TVShowResult> getOnTheAirTVShow(@Query("page") int page);

    @GET("tv/airing_today")
    Single<TVShowResult> getAiringToday(@Query("page") int page);

    @GET("discover/tv")
    Single<TVShowResult> getTvShowByGenre(@Query("with_genres") String genre, @Query("page") int page,
                                       @Query("first_air_date.gte") String release,
                                       @Query("vote_count.gte") int voteCount);

    @GET("tv/{id}")
    Single<TVShow> getTvShowDetails(@Path("id") int id);
    @GET("tv/{id}/videos")
    Single<TrailerResult> getTrailer(@Path("id")int tvShowId);

    @GET("tv/{tv_id}/season/{season_number}")
    Single<Season> getSeason(@Path("tv_id") int tvId,@Path("season_number") int seasonNum);

    @GET("tv/{tv_id}/reviews")
    Single<ReviewResult> getTvShowReviews(@Path("tv_id") int tvShowId);

    @GET("tv/{tv_id}/recommendations")
    Single<TVShowResult> getRecommendations(@Path("tv_id")int tvId);

}
