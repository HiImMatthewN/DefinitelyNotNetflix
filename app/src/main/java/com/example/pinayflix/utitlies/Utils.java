package com.example.pinayflix.utitlies;

import com.example.pinayflix.model.datamodel.Genre;

import java.util.ArrayList;

public class Utils {
    public static final String POSTER_PATH ="https://image.tmdb.org/t/p/w342";
    public static final String BACKDROP_PATH = "https://image.tmdb.org/t/p/w1280";
    public static final String HIGHLIGHTED_IMAGE_PATH = "https://image.tmdb.org/t/p/w500";

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


}
