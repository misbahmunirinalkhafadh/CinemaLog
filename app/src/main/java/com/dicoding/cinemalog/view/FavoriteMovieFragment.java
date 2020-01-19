package com.dicoding.cinemalog.view;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.cinemalog.R;
import com.dicoding.cinemalog.adapter.FavMovieAdapter;
import com.dicoding.cinemalog.db.FavMovieHelper;
import com.dicoding.cinemalog.helper.FavMovieMappingHelper;
import com.dicoding.cinemalog.model.FavoriteMovie;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadFavMovieCallback {

    private ProgressBar progressBar;
    private RecyclerView rvFavMovies;
    private FavMovieAdapter adapter;
    private FavMovieHelper favMovieHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_favorite_movie, container, false);

        progressBar = fragmentView.findViewById(R.id.progressBar);
        rvFavMovies = fragmentView.findViewById(R.id.rv_favmovie);
        rvFavMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavMovies.setHasFixedSize(true);
        adapter = new FavMovieAdapter(getActivity());
        rvFavMovies.setAdapter(adapter);

        favMovieHelper = FavMovieHelper.getInstance(getActivity());
        favMovieHelper.open();

        new LoadFavMovieAsync(favMovieHelper, this).execute();
        if (savedInstanceState == null) {
            new LoadFavMovieAsync(favMovieHelper, this).execute();
        } else {
            ArrayList<FavoriteMovie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setmData(list);
            }
        }

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getmData());
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavMovies, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favMovieHelper.close();
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<FavoriteMovie> favoriteMovies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (favoriteMovies.size() > 0) {
            adapter.setmData(favoriteMovies);
        } else {
            adapter.setmData(new ArrayList<FavoriteMovie>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }


    private static class LoadFavMovieAsync extends AsyncTask<Void, Void, ArrayList<FavoriteMovie>> {
        private final WeakReference<FavMovieHelper> weakNoteHelper;
        private final WeakReference<LoadFavMovieCallback> weakCallback;

        public LoadFavMovieAsync(FavMovieHelper favMovieHelper, LoadFavMovieCallback callback) {
            weakNoteHelper = new WeakReference<>(favMovieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<FavoriteMovie> doInBackground(Void... voids) {
            Cursor cursor = weakNoteHelper.get().queryAll();
            return FavMovieMappingHelper.mapCursorToArrayList(cursor);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteMovie> favoriteMovies) {
            super.onPostExecute(favoriteMovies);
            weakCallback.get().postExecute(favoriteMovies);
        }
    }
//
//    interface LoadFavMovieCallback {
//        void preExecute();
//
//        void postExecute(ArrayList<FavoriteMovie> favoriteMovies);
//    }
}
