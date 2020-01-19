package com.dicoding.cinemalog.view;


import android.content.Context;
import android.content.Intent;
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

import com.dicoding.cinemalog.adapter.TvShowAdapter;
import com.dicoding.cinemalog.model.TvShow;
import com.dicoding.cinemalog.R;
import com.dicoding.cinemalog.viewmodel.TvShowViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private TvShowAdapter adapter;
    private TvShowViewModel tvShowViewModel;
    private ProgressBar progressBar;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_tv_show, container, false);

        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("languangeKey", Context.MODE_PRIVATE);
        String bahasa = (sharedPreferences.getString("keyLang", ""));

        RecyclerView rvListTvShow = fragmentView.findViewById(R.id.rv_tvshow);
        progressBar = fragmentView.findViewById(R.id.progressBar);

        rvListTvShow.setHasFixedSize(true);
        rvListTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TvShowAdapter();
        adapter.notifyDataSetChanged();
        rvListTvShow.setAdapter(adapter);

        tvShowViewModel = ViewModelProviders.of(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);
        tvShowViewModel.setListTv(bahasa);

        showLoading(true);
        showRecyclerList();

        return fragmentView;
    }

    /**
     * menampilkan list data
     */
    private void showRecyclerList() {
        tvShowViewModel.getListTvShow().observe(Objects.requireNonNull(getActivity()), new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(ArrayList<TvShow> tvShows) {
                if (tvShows != null) {
                    adapter.setData(tvShows);
                    showLoading(false);
                }
            }
        });

        adapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow data) {
                Intent goToDetail = new Intent(getActivity(), DetailTvShowActivity.class);
                goToDetail.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, data);
                Objects.requireNonNull(getActivity()).startActivity(goToDetail);
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
