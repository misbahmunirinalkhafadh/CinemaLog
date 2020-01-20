package com.dicoding.cinemalog.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.cinemalog.R;
import com.dicoding.cinemalog.adapter.MovieAdapter;
import com.dicoding.cinemalog.model.Movie;
import com.dicoding.cinemalog.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private MovieAdapter adapter;
    private MovieViewModel movieViewModel;
    private ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_movie, container, false);

        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("languangeKey", Context.MODE_PRIVATE);
        String bahasa = (sharedPreferences.getString("keyLang", ""));

        RecyclerView rvMovies = fragmentView.findViewById(R.id.rv_movie);
        progressBar = fragmentView.findViewById(R.id.progressBar);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();
        rvMovies.setAdapter(adapter);

        movieViewModel = ViewModelProviders.of(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.setListMovie(bahasa);

        showLoading(true);
        showRecyclerList();

        return fragmentView;
    }

    /**
     * menampilkan list
     */
    private void showRecyclerList() {
        movieViewModel.getListMovies().observe(Objects.requireNonNull(getActivity()), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null) {
                    adapter.setData(movies);
                    showLoading(false);
                }
            }
        });
    }

    /**
     * progres loader
     *
     * @param state
     */
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
