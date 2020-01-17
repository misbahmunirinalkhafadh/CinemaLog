package com.dicoding.cinemalog.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.cinemalog.BuildConfig;
import com.dicoding.cinemalog.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<TvShow>> listTv = new MutableLiveData<>();

    public void setListTv(String bahasa) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language="+ bahasa;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvshow = list.getJSONObject(i);
                        TvShow tvShows = new TvShow();
                        tvShows.setRating(tvshow.getString("vote_average"));
                        tvShows.setName(tvshow.getString("name"));
                        tvShows.setRelease(tvshow.getString("first_air_date"));
                        tvShows.setDesc(tvshow.getString("overview"));
                        tvShows.setPoster(tvshow.getString("poster_path"));

                        listItem.add(tvShows);
                    }
                    listTv.postValue(listItem);
                    Log.i("Result", result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

    }
    public LiveData<ArrayList<TvShow>> getListTvShow() {
        return listTv;
    }
}
