package com.dicoding.cinemalog.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dicoding.cinemalog.R;
import com.dicoding.cinemalog.db.FavMovieHelper;
import com.dicoding.cinemalog.model.FavoriteMovie;
import com.dicoding.cinemalog.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.dicoding.cinemalog.db.FavMovieContract.FavMovieColumns.DESCPRIPTION;
import static com.dicoding.cinemalog.db.FavMovieContract.FavMovieColumns.FAVORITE;
import static com.dicoding.cinemalog.db.FavMovieContract.FavMovieColumns.MOVIE_ID;
import static com.dicoding.cinemalog.db.FavMovieContract.FavMovieColumns.POSTER;
import static com.dicoding.cinemalog.db.FavMovieContract.FavMovieColumns.RATING;
import static com.dicoding.cinemalog.db.FavMovieContract.FavMovieColumns.TITLE;
import static com.dicoding.cinemalog.db.FavMovieContract.FavMovieColumns.YEAR;

public class DetailMovieActivity extends AppCompatActivity {

    int movieId, favorite;
    String poster, title, date, desc, rate;
    TextView tvTitle, tvYear, tvDesc, tvRating;
    RatingBar ratingBar;
    ImageView imgPoster;
    Button btnBack;
    ToggleButton tbFavorite;
    ScrollView svContent;
    SharedPreferences sharedPreferences;
    Movie movie;
    Context context = this;
    private boolean isEdit = false;
    private int position;
    private ProgressBar progressBar;
    private FavoriteMovie favoriteMovie;
    private FavMovieHelper favMovieHelper;

    public static final String EXTRA_CINEMA = "extra_cinema";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        //hide action bar
        getSupportActionBar().hide();

        initComponent();

        showLoading(true);

        movie = getIntent().getParcelableExtra(EXTRA_CINEMA);
        movieId = movie.getId();
        poster = "https://image.tmdb.org/t/p/w500" + movie.getPoster();
        title = movie.getTitle();
        date = movie.getYear();
        desc = movie.getDesc();
        rate = movie.getRating();

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        try {
            Date release = input.parse(date);                 // parse input
            tvYear.setText(output.format(release));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set Ratting
        Float rateStar = Float.parseFloat(rate);
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        ratingBar.setRating(rateStar / 2);
        if (rate.equals("0")) {
            tvRating.setText("0");
        } else {
            tvRating.setText(decimalFormat.format(rateStar));
        }

        tvTitle.setText(title);
        tvDesc.setText(desc);
        Picasso.with(context).load(poster).into(imgPoster, new Callback() {
            @Override
            public void onSuccess() {
                showLoading(false);
            }

            @Override
            public void onError() {

            }
        });

        // favorite
        favMovieHelper = new FavMovieHelper(getApplicationContext());
        if (favoriteMovie != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            Log.i("ADA", "" + position);
            isEdit = true;
        } else {
            favoriteMovie = new FavoriteMovie();
            Log.i("KOSONG", "" + position);
        }

        setFavorite();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setFavorite() {
        boolean fav = (favoriteMovie.getFavorite() == 1);
        Log.v("MASUK", " = " + fav);
        sharedPreferences = getSharedPreferences("favorite", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        tbFavorite.setChecked(fav);
        tbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                favorite = isChecked ? 1 : 0;
                saveItem();

                Intent intent = new Intent();
                intent.putExtra(EXTRA_CINEMA, favoriteMovie);

                ContentValues values = new ContentValues();
                values.put(MOVIE_ID, movieId);
                values.put(FAVORITE, favorite);
                values.put(TITLE, title);
                values.put(POSTER, poster);
                values.put(YEAR, date);
                values.put(RATING, rate);
                values.put(DESCPRIPTION, desc);

                if (isChecked) {
                    Log.v("Favorite", " = " + favoriteMovie.getFavorite());
                    long result = favMovieHelper.insert(values);
                    Log.v("Insert ", values.toString());
                    if (result > 0) {
                        favoriteMovie.setId((int) result);
                        setResult(RESULT_ADD, intent);
                        tbFavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
                        editor.putBoolean("SaveFavorite", true);

                        Toast.makeText(getApplicationContext(), "SUCCESS add to Favorite", Toast.LENGTH_SHORT).show();
                        Log.d("BEHASIL", "Favorite ");
                    } else {
                        Toast.makeText(getApplicationContext(), "FAILED add to Favorite", Toast.LENGTH_SHORT).show();
                        Log.e("GAGAL", "Favorite");
                    }
                } else {
                    Log.v("Favorite", " = " + favorite);
                    long result = favMovieHelper.deleteById(String.valueOf(favoriteMovie.getMovieId()));
                    if (result > 0) {
                        setResult(RESULT_DELETE, intent);
                        tbFavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_black_24dp));
                        editor.putBoolean("SaveFavorite", false);

                        Toast.makeText(getApplicationContext(), "REMOVE from Favorite", Toast.LENGTH_SHORT).show();
                        Log.d("BEHASIL", "Hapus Favorite");
                    } else {
                        Toast.makeText(getApplicationContext(), "FAILED to remove from Favorite", Toast.LENGTH_SHORT).show();
                        Log.e("GAGAL", "Hapus Favorite " + result);
                    }
                    editor.apply();
                }
            }
        });
    }

    private void saveItem() {
        favoriteMovie.setMovieId(movieId);
        favoriteMovie.setFavorite(favorite);
        favoriteMovie.setTitle(title);
        favoriteMovie.setPoster(poster);
        favoriteMovie.setYear(date);
        favoriteMovie.setRating(rate);
        favoriteMovie.setDesc(desc);
    }

    private void initComponent() {
        btnBack = findViewById(R.id.ic_back);
        tbFavorite = findViewById(R.id.tb_favorite);
        imgPoster = findViewById(R.id.iv_poster);
        ratingBar = findViewById(R.id.rattingBar);
        tvRating = findViewById(R.id.tv_ratting);
        tvTitle = findViewById(R.id.tv_title);
        tvYear = findViewById(R.id.tv_year);
        tvDesc = findViewById(R.id.tv_description);
        svContent = findViewById(R.id.sv_content);
        progressBar = findViewById(R.id.progressBar);
    }

    /**
     * progres loader
     *
     * @param state
     */
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            svContent.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            svContent.setVisibility(View.VISIBLE);
        }

    }
}
