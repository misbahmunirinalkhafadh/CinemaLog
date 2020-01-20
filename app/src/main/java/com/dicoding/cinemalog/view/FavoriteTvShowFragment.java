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
import com.dicoding.cinemalog.adapter.FavTvShowAdapter;
import com.dicoding.cinemalog.db.FavTvShowHelper;
import com.dicoding.cinemalog.helper.FavTvShowMappingHelper;
import com.dicoding.cinemalog.model.FavoriteTvShow;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment implements LoadFavTvshowCallback {

    private ProgressBar progressBar;
    private RecyclerView rvFavTvShow;
    private FavTvShowAdapter adapter;
    private FavTvShowHelper favTvShowHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_favorite_tvshow, container, false);

        progressBar = fragmentView.findViewById(R.id.progressBar);
        rvFavTvShow = fragmentView.findViewById(R.id.rv_favtvshow);
        rvFavTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavTvShow.setHasFixedSize(true);
        adapter = new FavTvShowAdapter(getActivity());
        rvFavTvShow.setAdapter(adapter);

        favTvShowHelper = FavTvShowHelper.getInstance(getActivity());
        favTvShowHelper.open();

        new LoadFavTvshowAsync(favTvShowHelper, this).execute();
        if (savedInstanceState == null) {
            new LoadFavTvshowAsync(favTvShowHelper, this).execute();
        } else {
            ArrayList<FavoriteTvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
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
        Snackbar.make(rvFavTvShow, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favTvShowHelper.close();
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<FavoriteTvShow> favoriteTvShows) {
        progressBar.setVisibility(View.INVISIBLE);
        if (favoriteTvShows.size() > 0) {
            adapter.setmData(favoriteTvShows);
        } else {
            adapter.setmData(new ArrayList<FavoriteTvShow>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadFavTvshowAsync extends AsyncTask<Void, Void, ArrayList<FavoriteTvShow>> {
        private final WeakReference<FavTvShowHelper> weakNoteHelper;
        private final WeakReference<LoadFavTvshowCallback> weakCallback;

        public LoadFavTvshowAsync(FavTvShowHelper favTvShowHelper, LoadFavTvshowCallback callback) {
            weakNoteHelper = new WeakReference<>(favTvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<FavoriteTvShow> doInBackground(Void... voids) {
            Cursor cursor = weakNoteHelper.get().queryAll();
            return FavTvShowMappingHelper.mapCursorToArrayList(cursor);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteTvShow> favoriteTvShows) {
            super.onPostExecute(favoriteTvShows);
            weakCallback.get().postExecute(favoriteTvShows);
        }
    }
}
