package com.dicoding.cinemalog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 26;
    public static final String DATABASE_NAME = "CinemaLog.db";

    /**
     * Statement for table fav_movie
     */
    private static final String SQL_CREATE_TABLE_FAV_MOVIE = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            FavMovieContract.TABLE_NAME,
            FavMovieContract.FavMovieColumns.MOVIE_ID,
            FavMovieContract.FavMovieColumns.FAVORITE,
            FavMovieContract.FavMovieColumns.TITLE,
            FavMovieContract.FavMovieColumns.YEAR,
            FavMovieContract.FavMovieColumns.RATING,
            FavMovieContract.FavMovieColumns.DESCRIPTION,
            FavMovieContract.FavMovieColumns.POSTER
    );

    /**
     * Statement for table fav_tvshow
     */
//    private static final String SQL_CREATE_TABLE_FAV_TVSHOW = String.format("CREATE TABLE %s"
//                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL)",
//            FavTvShowContract.FavTvShowColumns.TABLE_NAME,
//            FavTvShowContract.FavTvShowColumns._ID,
//            FavTvShowContract.FavTvShowColumns.TITLE,
//            FavTvShowContract.FavTvShowColumns.RELEASE,
//            FavTvShowContract.FavTvShowColumns.DESCRIPTION,
//            FavTvShowContract.FavTvShowColumns.RATING,
//            FavTvShowContract.FavTvShowColumns.POSTER
//    );
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAV_MOVIE);
//        db.execSQL(SQL_CREATE_TABLE_FAV_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavMovieContract.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + FavTvShowContract.FavTvShowColumns.TABLE_NAME);
        onCreate(db);
    }
}
