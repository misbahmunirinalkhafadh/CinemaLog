package com.dicoding.cinemalog.view;

import android.content.Context;
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
import com.dicoding.cinemalog.model.TvShow;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailTvShowActivity extends AppCompatActivity {

    int movieId;
    String poster, title, date, desc, rate;
    TextView tvTitle, tvYear, tvDesc, tvRating;
    RatingBar ratingBar;
    ImageView imgPoster;
    Button btnBack;
    ToggleButton tbFavorite;
    ScrollView svContent;

    SharedPreferences sharedPreferences;
    Context context = this;
    private ProgressBar progressBar;

    public static final String EXTRA_TVSHOW = "extra_tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        //hide action bar
        getSupportActionBar().hide();

        initComponent();

        showLoading(true);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        String poster = "https://image.tmdb.org/t/p/w500" + tvShow.getPoster();
        String name = tvShow.getName();
        String date = tvShow.getRelease();
        String desc = tvShow.getDesc();

        //Set release date format
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        try {
            Date release = input.parse(date);                 // parse input
            tvYear.setText(output.format(release));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set Ratting
        Float rateStar = Float.parseFloat(tvShow.getRating());
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        ratingBar.setRating(rateStar / 2);
        if (tvShow.getRating().equals("0")) {
            tvRating.setText("0");
        } else {
            tvRating.setText(decimalFormat.format(rateStar));
        }

        tvTitle.setText(name);
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

        setFavorite();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setFavorite() {
        sharedPreferences = getSharedPreferences("favorite", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        tbFavorite.setChecked(sharedPreferences.getBoolean("SaveFavorite", false));
        tbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    tbFavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
                    editor.putBoolean("SaveFavorite", true);

                    Toast.makeText(getApplicationContext(), "SUCCESS add to Favorite", Toast.LENGTH_SHORT).show();
                    Log.d("BEHASIL", "Favorite ");

                } else {

                    tbFavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_black_24dp));
                    editor.putBoolean("SaveFavorite", false);

                    Toast.makeText(getApplicationContext(), "REMOVE from Favorite", Toast.LENGTH_SHORT).show();
                    Log.d("BEHASIL", "Hapus Favorite");

                }
                editor.apply();

            }
        });
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
