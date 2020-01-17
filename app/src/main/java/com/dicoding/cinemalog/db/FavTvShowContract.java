package com.dicoding.cinemalog.db;

import android.provider.BaseColumns;

public class FavTvShowContract {
    public FavTvShowContract() {
    }

    public static final class FavTvShowColumns implements BaseColumns {
        public static String TABLE_NAME = "fav_tvshow";
        public static String TITLE = "title";
        public static String RELEASE = "release";
        public static String RATING = "rating";
        public static String DESCPRIPTION = "desc";
        public static String POSTER = "poster";

    }

}
