package com.dicoding.cinemalog.helper;

import android.database.Cursor;

import com.dicoding.cinemalog.db.FavMovieContract;
import com.dicoding.cinemalog.model.FavoriteMovie;

import java.util.ArrayList;

public class FavMovieMappingHelper {
    public static ArrayList<FavoriteMovie> mapCursorToArrayList(Cursor favMovieCursor) {
        ArrayList<FavoriteMovie> favMovieList = new ArrayList<>();
        while (favMovieCursor.moveToNext()) {
            int movieId = favMovieCursor.getInt(favMovieCursor.getColumnIndexOrThrow(FavMovieContract.FavMovieColumns.MOVIE_ID));
            int favorite = favMovieCursor.getInt(favMovieCursor.getColumnIndexOrThrow(FavMovieContract.FavMovieColumns.FAVORITE));
            String title = favMovieCursor.getString(favMovieCursor.getColumnIndexOrThrow(FavMovieContract.FavMovieColumns.TITLE));
            String date = favMovieCursor.getString(favMovieCursor.getColumnIndexOrThrow(FavMovieContract.FavMovieColumns.YEAR));
            String rating = favMovieCursor.getString(favMovieCursor.getColumnIndexOrThrow(FavMovieContract.FavMovieColumns.RATING));
            String description = favMovieCursor.getString(favMovieCursor.getColumnIndexOrThrow(FavMovieContract.FavMovieColumns.DESCRIPTION));
            String poster = favMovieCursor.getString(favMovieCursor.getColumnIndexOrThrow(FavMovieContract.FavMovieColumns.POSTER));
            favMovieList.add(new FavoriteMovie(movieId, favorite, date, title, rating,description, poster));
        }
        return favMovieList;
    }
}
