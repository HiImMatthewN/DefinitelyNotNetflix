package com.example.pinayflix.network;

import com.example.pinayflix.model.datamodel.trailer.TrailerResult;
import com.example.pinayflix.model.datamodel.tvshow.Season;
import com.example.pinayflix.model.datamodel.tvshow.TVShowDetails;
import com.example.pinayflix.model.datamodel.tvshow.TVShowResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TVShowService {

    @GET("tv/popular")
    Call<TVShowResult> getPopularTVShow(@Query("page") int page);

    @GET("tv/on_the_air")
    Call<TVShowResult> getOnTheAirTVShow(@Query("page") int page);

    @GET("tv/airing_today")
    Call<TVShowResult> getAiringToday(@Query("page") int page);

    @GET("discover/tv")
    Call<TVShowResult> getTvShowByGenre(@Query("with_genres") String genre, @Query("page") int page,
                                       @Query("first_air_date.gte") String release,
                                       @Query("vote_count.gte") int voteCount);

    @GET("tv/{id}")
    Call<TVShowDetails> getTvShowDetails(@Path("id") int id);
    @GET("tv/{id}/videos")
    Call<TrailerResult> getTrailer(@Path("id")int tvShowId);

    @GET("tv/{tv_id}/season/{season_number}")
    Call<Season> getSeason(@Path("tv_id") int tvId,@Path("season_number") int seasonNum);

}
