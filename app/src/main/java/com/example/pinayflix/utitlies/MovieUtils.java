package com.example.pinayflix.utitlies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.pinayflix.model.datamodel.Genre;

import java.util.ArrayList;

public class MovieUtils {
    public static String getGenreNameFromId(int id) {
        for (Genre genre : getList()) {
            if (id == genre.getId())
                return genre.getName();

        }
        return "";

    }

    private static ArrayList<Genre> getList() {
        ArrayList<Genre> genres = new ArrayList<>();
        genres.add(new Genre(28, "Action"));
        genres.add(new Genre(12, "Adventure"));
        genres.add(new Genre(16, "Animation"));
        genres.add(new Genre(35, "Comedy"));
        genres.add(new Genre(80, "Crime"));
        genres.add(new Genre(99, "Documentary"));
        genres.add(new Genre(18, "Drama"));
        genres.add(new Genre(10751, "Family"));
        genres.add(new Genre(14, "Fantasy"));
        genres.add(new Genre(36, "History"));
        genres.add(new Genre(27, "Horror"));
        genres.add(new Genre(10402, "Music"));
        genres.add(new Genre(9648, "Mystery"));
        genres.add(new Genre(10749, "Romance"));
        genres.add(new Genre(878, "Science Fiction"));
        genres.add(new Genre(10770, "TV Movie"));
        genres.add(new Genre(53, "Thriller"));
        genres.add(new Genre(10752, "War"));
        genres.add(new Genre(37, "Western"));

        return genres;

    }

    public static <T> void observeOnce(final LiveData<T> liveData, final Observer<T> observer) {
        liveData.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
                liveData.removeObserver(this);
                observer.onChanged(t);
            }
        });
    }
}
