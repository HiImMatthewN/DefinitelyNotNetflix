package com.example.pinayflix.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.model.datamodel.trailer.Trailer;
import com.example.pinayflix.model.datamodel.tvshow.Season;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.model.datamodel.tvshow.TVShowResult;
import com.example.pinayflix.network.TVShowService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TVShowRepository {

    private TVShowService tvShowService;

    private MutableLiveData<List<TVShow>> popularTvShowsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TVShow>> upcomingTvShowsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TVShow>> nowPlayingTvShowsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TVShow>> mysteryTvShowsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TVShow>> romanceTvShowsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TVShow>> documentaryTvShowsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailerTvShowLiveData = new MutableLiveData<>();

    private MutableLiveData<List<TVShow>> requestNewTvShows = new MutableLiveData<>();

    //LiveData for TvShowDetails
    private MutableLiveData<TVShow> tvShowDetailsLiveData = new MutableLiveData<>();

    private MutableLiveData<Season> tvShowSeasonLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TVShow>> recoTvShowLiveData = new MutableLiveData<>();

    private MutableLiveData<List<Review>> tvShowReviewsLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String TAG = "TVShowRepository";


    @Inject
    public TVShowRepository(TVShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    public void requestPopularTvShows(int page) {

        Disposable disposable = tvShowService.getPopularTVShow(page).subscribeOn(Schedulers.io())
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    popularTvShowsLiveData.postValue(tvShowResult.getTvShows());
                });
        compositeDisposable.add(disposable);
    }

    public void requestUpcomingTvShows(int page) {

        Disposable disposable = tvShowService.getAiringToday(page).subscribeOn(Schedulers.io())
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    upcomingTvShowsLiveData.postValue(tvShowResult.getTvShows());
                });
        compositeDisposable.add(disposable);
    }

    public void requestNowPlayingTvShows(int page) {

        Disposable disposable = tvShowService.getOnTheAirTVShow(page)


                .subscribeOn(Schedulers.io())
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    nowPlayingTvShowsLiveData.postValue(tvShowResult.getTvShows());
                });
        compositeDisposable.add(disposable);
    }

    public void requestMysteryTvShows(String genre, int page, String firstAirDate, int requiredVoteCount) {
        Disposable disposable = tvShowService.getTvShowByGenre(genre, page, firstAirDate)
                .map(TVShowResult::getTvShows)
                .flatMap(Observable::fromIterable)
                .filter(tvShow -> tvShow.getVoteCount() >= requiredVoteCount)
                .subscribeOn(Schedulers.io())
                .toList()
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    mysteryTvShowsLiveData.postValue(tvShowResult);
                });
        compositeDisposable.add(disposable);
    }

    public void requestRomanceTvShows(String genre, int page, String firstAirDate, int requiredVoteCount) {
        Disposable disposable = tvShowService.getTvShowByGenre(genre, page, firstAirDate)
                .map(TVShowResult::getTvShows)
                .flatMap(Observable::fromIterable)
                .filter(tvShow -> tvShow.getVoteCount() >= requiredVoteCount)
                .subscribeOn(Schedulers.io())
                .toList()
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    romanceTvShowsLiveData.postValue(tvShowResult);
                });
        compositeDisposable.add(disposable);
    }

    public void requestDocumentaryTvShows(String genre, int page, String firstAirDate, int requiredVoteCount) {
        Disposable disposable = tvShowService.getTvShowByGenre(genre, page, firstAirDate)
                .map(TVShowResult::getTvShows)
                .flatMap(Observable::fromIterable)
                .filter(tvShow -> tvShow.getVoteCount() >= requiredVoteCount)
                .subscribeOn(Schedulers.io())
                .toList()
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    documentaryTvShowsLiveData.postValue(tvShowResult);
                });
        compositeDisposable.add(disposable);
    }

    public void requestTvShowTrailer(int tvShowId) {
        Disposable disposable = tvShowService.getTrailer(tvShowId).subscribeOn(Schedulers.io())
                .subscribe(trailerResult -> {
                    if (trailerResult == null) return;
                    trailerTvShowLiveData.postValue(trailerResult.getTrailers());
                });
        compositeDisposable.add(disposable);

    }

    public void requestNewPopularTvShows(int page) {

        Disposable disposable = tvShowService.getPopularTVShow(page).subscribeOn(Schedulers.io())
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    requestNewTvShows.postValue(tvShowResult.getTvShows());
                });
        compositeDisposable.add(disposable);
    }

    public void requestNewUpcomingTvShows(int page) {

        Disposable disposable = tvShowService.getAiringToday(page).subscribeOn(Schedulers.io())
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    requestNewTvShows.postValue(tvShowResult.getTvShows());
                });
        compositeDisposable.add(disposable);
    }

    public void requestNewTvShowsByGenre(String genre, int page, String firstAirDate, int requiredVoteCount) {
        Disposable disposable = tvShowService.getTvShowByGenre(genre, page, firstAirDate)
                .map(TVShowResult::getTvShows)
                .flatMap(Observable::fromIterable)
                .filter(tvShow -> tvShow.getVoteCount() >= requiredVoteCount)
                .subscribeOn(Schedulers.io())
                .toList()
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    requestNewTvShows.postValue(tvShowResult);
                });
        compositeDisposable.add(disposable);
    }

    public void requestTvShowDetails(int tvShowId) {
        Disposable disposable = tvShowService.getTvShowDetails(tvShowId).subscribeOn(Schedulers.io())
                .subscribe(tvShow -> {
                    if (tvShow == null) return;
                    tvShowDetailsLiveData.postValue(tvShow);
                });
        compositeDisposable.add(disposable);
    }

    public void requestTvShowSeason(int tvShowId, int seasonNum) {
        Disposable disposable = tvShowService.getSeason(tvShowId, seasonNum).subscribeOn(Schedulers.io())
                .subscribe(tvShow -> {
                    if (tvShow == null) return;
                    tvShowSeasonLiveData.postValue(tvShow);
                });
        compositeDisposable.add(disposable);

    }

    public void requestRecos(int tvId) {
        Log.d(TAG, "requestRecos: Requesting TV Show ");
        Disposable disposable = tvShowService.getRecommendations(tvId).subscribeOn(Schedulers.io())
                .subscribe(tvShowResult -> {
                    if (tvShowResult == null) return;
                    recoTvShowLiveData.postValue(tvShowResult.getTvShows());
                });
        compositeDisposable.add(disposable);
    }

    public void requestTvShowReviews(int tvShowId) {
        Disposable disposable = tvShowService.getTvShowReviews(tvShowId).subscribeOn(Schedulers.io())
                .subscribe(tvReviews -> {
                    if (tvReviews == null) return;
                    tvShowReviewsLiveData.postValue(tvReviews.getReviews());
                });
        compositeDisposable.add(disposable);
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

    public LiveData<TVShow> getTVShowDetails() {
        return tvShowDetailsLiveData;
    }

    public LiveData<Season> getTVShowSeasonLiveData() {
        return tvShowSeasonLiveData;
    }

    public LiveData<List<TVShow>> getRecommendationsLiveData() {
        return recoTvShowLiveData;
    }

    public LiveData<List<Review>> getTvShowReview() {
        return tvShowReviewsLiveData;
    }
}
