package com.dicoding.cinemalog.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.cinemalog.BuildConfig;
import com.dicoding.cinemalog.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setListMovie(String bahasa) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItem = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=" + bahasa;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movies = new Movie();
                        movies.setId(movie.getInt("id"));
                        movies.setRating(movie.getString("vote_average"));
                        movies.setTitle(movie.getString("title"));
                        movies.setYear(movie.getString("release_date"));
                        movies.setDesc(movie.getString("overview"));
                        movies.setPoster(movie.getString("poster_path"));

                        listItem.add(movies);
                    }
                    listMovies.postValue(listItem);
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

    public LiveData<ArrayList<Movie>> getListMovies() {
        return listMovies;
    }
}
