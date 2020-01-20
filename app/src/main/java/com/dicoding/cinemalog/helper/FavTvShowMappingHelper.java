package com.dicoding.cinemalog.helper;

import android.database.Cursor;

import com.dicoding.cinemalog.db.FavTvShowContract;
import com.dicoding.cinemalog.model.FavoriteTvShow;

import java.util.ArrayList;

public class FavTvShowMappingHelper {
    public static ArrayList<FavoriteTvShow> mapCursorToArrayList(Cursor favTvshowCursor) {
        ArrayList<FavoriteTvShow> favTvshowList = new ArrayList<>();
        while (favTvshowCursor.moveToNext()) {
            int tvShowId = favTvshowCursor.getInt(favTvshowCursor.getColumnIndexOrThrow(FavTvShowContract.FavTvShowColumns.TVSHOW_ID));
            int favorite = favTvshowCursor.getInt(favTvshowCursor.getColumnIndexOrThrow(FavTvShowContract.FavTvShowColumns.FAVORITE));
            String name = favTvshowCursor.getString(favTvshowCursor.getColumnIndexOrThrow(FavTvShowContract.FavTvShowColumns.NAME));
            String release = favTvshowCursor.getString(favTvshowCursor.getColumnIndexOrThrow(FavTvShowContract.FavTvShowColumns.RELEASE));
            String rating = favTvshowCursor.getString(favTvshowCursor.getColumnIndexOrThrow(FavTvShowContract.FavTvShowColumns.RATING));
            String description = favTvshowCursor.getString(favTvshowCursor.getColumnIndexOrThrow(FavTvShowContract.FavTvShowColumns.DESCRIPTION));
            String poster = favTvshowCursor.getString(favTvshowCursor.getColumnIndexOrThrow(FavTvShowContract.FavTvShowColumns.POSTER));
            favTvshowList.add(new FavoriteTvShow(tvShowId, favorite, name, release, rating, description, poster));
        }
        return favTvshowList;
    }
}
