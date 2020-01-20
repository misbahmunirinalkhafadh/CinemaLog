package com.dicoding.cinemalog.db;

import android.provider.BaseColumns;

public class FavTvShowContract {
    public static String TABLE_FAVORITE_TVSHOW = "fav_tvshow";

    public FavTvShowContract() {
    }

    public static final class FavTvShowColumns implements BaseColumns {
        public static String TVSHOW_ID = "tvShowId";
        public static String FAVORITE = "favorite";
        public static String NAME = "name";
        public static String RELEASE = "release";
        public static String RATING = "rating";
        public static String DESCRIPTION = "desc";
        public static String POSTER = "poster";
    }
}
