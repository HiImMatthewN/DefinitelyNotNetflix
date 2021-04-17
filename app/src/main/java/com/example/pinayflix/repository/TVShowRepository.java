package com.example.pinayflix.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pinayflix.model.datamodel.trailer.Trailer;
import com.example.pinayflix.model.datamodel.trailer.TrailerResult;
import com.example.pinayflix.model.datamodel.tvshow.Season;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.model.datamodel.tvshow.TVShowDetails;
import com.example.pinayflix.model.datamodel.tvshow.TVShowResult;
import com.example.pinayflix.network.TVShowService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowRepository {

    private TVShowService tvShowService;

    private MutableLiveData<List<TVShow>> popularTvShowsLiveData;
    private MutableLiveData<List<TVShow>> upcomingTvShowsLiveData;
    private MutableLiveData<List<TVShow>> nowPlayingTvShowsLiveData;
    private MutableLiveData<List<TVShow>> mysteryTvShowsLiveData;
    private MutableLiveData<List<TVShow>> romanceTvShowsLiveData;
    private MutableLiveData<List<TVShow>> documentaryTvShowsLiveData;
    private MutableLiveData<List<Trailer>> trailerTvShowLiveData;

    private MutableLiveData<List<TVShow>> requestNewTvShows;

    //LiveData for TvShowDetails
    private MutableLiveData<TVShowDetails> tvShowDetailsLiveData;

    private MutableLiveData<Season> tvShowSeasonLiveData;


    private String TAG = "TVShowRepository";


    @Inject
    public TVShowRepository(TVShowService tvShowService) {
        this.tvShowService = tvShowService;

        popularTvShowsLiveData = new MutableLiveData<>();
        upcomingTvShowsLiveData = new MutableLiveData<>();
        nowPlayingTvShowsLiveData = new MutableLiveData<>();
        mysteryTvShowsLiveData = new MutableLiveData<>();
        romanceTvShowsLiveData = new MutableLiveData<>();
        documentaryTvShowsLiveData = new MutableLiveData<>();
        trailerTvShowLiveData = new MutableLiveData<>();
        requestNewTvShows = new MutableLiveData<>();
        tvShowDetailsLiveData = new MutableLiveData<>();
        tvShowSeasonLiveData = new MutableLiveData<>();

    }


    public void requestPopularTvShows(int page) {

        tvShowService.getPopularTVShow(page).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    popularTvShowsLiveData.postValue(response.body().getTvShows());
                Log.d(TAG, "onResponse: GET Popular TV Show Success");
            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {
                Log.d(TAG, "onResponse: GET Popular TV Show Failed" + t.getMessage());

            }
        });

    }

    public void requestUpcomingTvShows(int page) {

        tvShowService.getAiringToday(page).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    upcomingTvShowsLiveData.postValue(response.body().getTvShows());
                Log.d(TAG, "onResponse: GET Upcoming TV Show Success");
            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {
                Log.d(TAG, "onResponse: GET Popular TV Show Failed" + t.getMessage());

            }
        });

    }

    public void requestNowPlayingTvShows(int page) {

        tvShowService.getOnTheAirTVShow(page).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    nowPlayingTvShowsLiveData.postValue(response.body().getTvShows());
                Log.d(TAG, "onResponse: GET Now Playing TV Show Success");
            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {
                Log.d(TAG, "onResponse: GET Popular TV Show Failed" + t.getMessage());

            }
        });
    }

    public void requestMysteryTvShows(String genre, int page, String firstAirDate, int requiredVoteCount) {
        tvShowService.getTvShowByGenre(genre, page, firstAirDate, requiredVoteCount).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mysteryTvShowsLiveData.postValue(response.body().getTvShows());
                    Log.d(TAG, "onResponse: Mystery size " + response.body().getTvShows().size());
                    Log.d(TAG, "onResponse: GET Mystery TV Show Success");

                }

            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {
                Log.d(TAG, "onResponse: GET Horror TV Show Failed" + t.getMessage());

            }
        });
    }

    public void requestRomanceTvShows(String genre, int page, String firstAirDate, int requiredVoteCount) {
        tvShowService.getTvShowByGenre(genre, page, firstAirDate, requiredVoteCount).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    romanceTvShowsLiveData.postValue(response.body().getTvShows());
                    Log.d(TAG, "onResponse: GET Romance TV Show Success");

                }

            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {
                Log.d(TAG, "onResponse: GET Romance TV Show Failed" + t.getMessage());

            }
        });
    }

    public void requestDocumentaryTvShows(String genre, int page, String firstAirDate, int requiredVoteCount) {
        tvShowService.getTvShowByGenre(genre, page, firstAirDate, requiredVoteCount).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    documentaryTvShowsLiveData.postValue(response.body().getTvShows());
                    Log.d(TAG, "onResponse: GET Documentary TV Show Success");
                }
            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {
                Log.d(TAG, "onResponse: GET Documentary TV Show Failed" + t.getMessage());

            }
        });
    }

    public void requestTvShowTrailer(int tvShowId) {
        tvShowService.getTrailer(tvShowId).enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    trailerTvShowLiveData.postValue(response.body().getTrailers());
                    Log.d(TAG, "onResponse: GET TV Show Trailer Success");
                }
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {
                Log.d(TAG, "onResponse: GET TV Show Trailer Failed" + t.getMessage());

            }
        });


    }

    public void requestNewPopularTvShows(int page) {

        tvShowService.getPopularTVShow(page).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    requestNewTvShows.postValue(response.body().getTvShows());
            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {

            }
        });

    }

    public void requestNewUpcomingTvShows(int page) {

        tvShowService.getAiringToday(page).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    requestNewTvShows.postValue(response.body().getTvShows());
            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {

            }
        });

    }

    public void requestNewTvShowsByGenre(String genre, int page, String firstAirDate, int requiredVoteCount) {
        tvShowService.getTvShowByGenre(genre, page, firstAirDate, requiredVoteCount).enqueue(new Callback<TVShowResult>() {
            @Override
            public void onResponse(Call<TVShowResult> call, Response<TVShowResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    requestNewTvShows.postValue(response.body().getTvShows());
                }
            }

            @Override
            public void onFailure(Call<TVShowResult> call, Throwable t) {


            }
        });
    }

    public void requestTvShowDetails(int tvShowId) {
        tvShowService.getTvShowDetails(tvShowId).enqueue(new Callback<TVShowDetails>() {
            @Override
            public void onResponse(Call<TVShowDetails> call, Response<TVShowDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvShowDetailsLiveData.postValue(response.body());
                    Log.d(TAG, "onResponse: GET TVShowDetails success");
                }
            }

            @Override
            public void onFailure(Call<TVShowDetails> call, Throwable t) {
                Log.d(TAG, "onFailure: GET TVShowDetails failed " + t.getMessage());
            }
        });
    }

    public void requestTvShowSeason(int tvShowId, int seasonNum){
        tvShowService.getSeason(tvShowId,seasonNum).enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvShowSeasonLiveData.postValue(response.body());
                    Log.d(TAG, "onResponse: GET TV Season success");

                }

            }

            @Override
            public void onFailure(Call<Season> call, Throwable t) {
                Log.d(TAG, "onResponse: GET TV Season failed " + t.getMessage());

            }
        });

    }



    public LiveData<List<TVShow>> getPopularTvShowsLiveData() {
        return popularTvShowsLiveData;
    }

    public LiveData<List<TVShow>> getUpComingTvShowsLiveData() {
        return upcomingTvShowsLiveData;
    }

    public LiveData<List<TVShow>> getNowPlayingTvShowsLiveData() {
        return nowPlayingTvShowsLiveData;
    }

    public LiveData<List<TVShow>> getMysteryTvShowsLiveData() {
        return mysteryTvShowsLiveData;
    }

    public LiveData<List<TVShow>> getRomanceTvShowsLiveData() {
        return romanceTvShowsLiveData;
    }

    public LiveData<List<TVShow>> getDocumentaryTvShowsLiveData() {
        return documentaryTvShowsLiveData;
    }

    public LiveData<List<Trailer>> getTvShowTrailerLiveData() {
        return trailerTvShowLiveData;
    }

    public LiveData<List<TVShow>> getNewTVShowsLiveData() {
        return requestNewTvShows;
    }

    public LiveData<TVShowDetails> getTVShowDetails() {
        return tvShowDetailsLiveData;
    }

    public LiveData<Season> getTVShowSeasonLiveData(){
        return tvShowSeasonLiveData;
    }
}
