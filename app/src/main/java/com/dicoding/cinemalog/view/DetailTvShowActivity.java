package com.dicoding.cinemalog.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.dicoding.cinemalog.db.FavTvShowHelper;
import com.dicoding.cinemalog.model.FavoriteTvShow;
import com.dicoding.cinemalog.model.TvShow;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.dicoding.cinemalog.db.FavTvShowContract.FavTvShowColumns.DESCRIPTION;
import static com.dicoding.cinemalog.db.FavTvShowContract.FavTvShowColumns.FAVORITE;
import static com.dicoding.cinemalog.db.FavTvShowContract.FavTvShowColumns.NAME;
import static com.dicoding.cinemalog.db.FavTvShowContract.FavTvShowColumns.POSTER;
import static com.dicoding.cinemalog.db.FavTvShowContract.FavTvShowColumns.RATING;
import static com.dicoding.cinemalog.db.FavTvShowContract.FavTvShowColumns.RELEASE;
import static com.dicoding.cinemalog.db.FavTvShowContract.FavTvShowColumns.TVSHOW_ID;

public class DetailTvShowActivity extends AppCompatActivity {

    int tvShowId, favorite;
    String poster, name, date, desc, rate;
    TextView tvTitle, tvYear, tvDesc, tvRating;
    RatingBar ratingBar;
    ImageView imgPoster;
    Button btnBack;
    ToggleButton tbFavorite;
    ScrollView svContent;
    Context context = this;
    private int position;
    private ProgressBar progressBar;
    private FavoriteTvShow favoriteTvShow;
    private FavTvShowHelper favTvShowHelper;

    public static final String EXTRA_CINEMA = "extra_tvshow";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        //hide action bar
        getSupportActionBar().hide();

        initComponent();

        showLoading(true);

        favTvShowHelper = new FavTvShowHelper(getApplicationContext());

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_CINEMA);
        tvShowId = tvShow.getId();
        poster = tvShow.getPoster();
        name = tvShow.getName();
        date = tvShow.getRelease();
        desc = tvShow.getDesc();
        rate = tvShow.getRating();

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
        Float rateStar = Float.parseFloat(rate);
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        ratingBar.setRating(rateStar / 2);
        if (rate.equals("0")) {
            tvRating.setText("0");
        } else {
            tvRating.setText(decimalFormat.format(rateStar));
        }

        tvTitle.setText(name);
        tvDesc.setText(desc);
        String url_poster = "https://image.tmdb.org/t/p/w500";
        Picasso.with(context).load(url_poster + poster).into(imgPoster, new Callback() {
            @Override
            public void onSuccess() {
                showLoading(false);
            }

            @Override
            public void onError() {

            }
        });

        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        favoriteTvShow = new FavoriteTvShow();
        // Get Status Favorite
        Cursor cursor = favTvShowHelper.queryById(String.valueOf(tvShowId));
        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndex("favorite"));
                Log.i("FAVORITE", "STATUS " + data);
                if (data.equals("1")) {
                    tbFavorite.setChecked(true);
                } else {
                    tbFavorite.setChecked(false);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        setFavorite();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setFavorite() {
        tbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                favorite = isChecked ? 1 : 0;
                saveItem();

                Intent intent = new Intent();
                intent.putExtra(EXTRA_CINEMA, favoriteTvShow);
                intent.putExtra(EXTRA_POSITION, position);

                ContentValues values = new ContentValues();
                values.put(TVSHOW_ID, tvShowId);
                values.put(FAVORITE, favorite);
                values.put(NAME, name);
                values.put(RELEASE, date);
                values.put(RATING, rate);
                values.put(DESCRIPTION, desc);
                values.put(POSTER, poster);

                if (isChecked) {
                    long result = favTvShowHelper.insert(values);
                    if (result > 0) {
                        favoriteTvShow.setTvShowId((int) result);
                        setResult(RESULT_ADD, intent);
                        tbFavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));

                        Toast.makeText(getApplicationContext(), "SUCCESS add to Favorite", Toast.LENGTH_SHORT).show();
                        Log.d("BEHASIL", "Favorite ");
                    } else {
                        Toast.makeText(getApplicationContext(), "FAILED add to Favorite", Toast.LENGTH_SHORT).show();
                        Log.e("GAGAL", "Favorite");
                    }
                } else {
                    long result = favTvShowHelper.deleteById(String.valueOf(favoriteTvShow.getTvShowId()));
                    if (result > 0) {
                        setResult(RESULT_DELETE, intent);
                        tbFavorite.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_black_24dp));

                        Toast.makeText(getApplicationContext(), "REMOVE from Favorite", Toast.LENGTH_SHORT).show();
                        Log.d("BEHASIL", "Hapus Favorite");
                    } else {
                        Toast.makeText(getApplicationContext(), "FAILED to remove from Favorite", Toast.LENGTH_SHORT).show();
                        Log.e("GAGAL", "Hapus Favorite " + result);
                    }
                }
            }
        });
    }

    private void saveItem() {
        favoriteTvShow.setTvShowId(tvShowId);
        favoriteTvShow.setFavorite(favorite);
        favoriteTvShow.setName(name);
        favoriteTvShow.setPoster(poster);
        favoriteTvShow.setRelease(date);
        favoriteTvShow.setRating(rate);
        favoriteTvShow.setDesc(desc);
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
