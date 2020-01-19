package com.dicoding.cinemalog.view;

import com.dicoding.cinemalog.model.FavoriteMovie;

import java.util.ArrayList;

interface LoadFavMovieCallback {
    void preExecute();

    void postExecute(ArrayList<FavoriteMovie> favoriteMovies);
}

