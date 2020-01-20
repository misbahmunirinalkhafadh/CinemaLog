package com.dicoding.cinemalog.db;

import android.provider.BaseColumns;

public class FavMovieContract {
    public static String TABLE_FAVORITE_MOVIE = "fav_movie";

    private FavMovieContract() {
    }

    public static final class FavMovieColumns implements BaseColumns {
        public static String MOVIE_ID = "movieId";
        public static String FAVORITE = "favorite";
        public static String TITLE = "title";
        public static String YEAR = "year";
        public static String RATING = "rating";
        public static String DESCRIPTION = "desc";
        public static String POSTER = "poster";
    }
}

