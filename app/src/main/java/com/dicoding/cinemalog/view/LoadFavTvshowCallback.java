package com.dicoding.cinemalog.view;

import com.dicoding.cinemalog.model.FavoriteTvShow;

import java.util.ArrayList;

interface LoadFavTvshowCallback {
    void preExecute();

    void postExecute(ArrayList<FavoriteTvShow> favoriteMovies);
}
