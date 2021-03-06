package com.dicoding.cinemalog.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.cinemalog.CustomOnItemClickListener;
import com.dicoding.cinemalog.R;
import com.dicoding.cinemalog.model.FavoriteMovie;
import com.dicoding.cinemalog.view.DetailFavMovieActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.MovieViewHolder> {

    private ArrayList<FavoriteMovie> mData = new ArrayList<>();
    private Activity activity;
    Context context;

    public FavMovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavoriteMovie> getmData() {
        return mData;
    }

    public void setmData(ArrayList<FavoriteMovie> mData) {
        if (mData.size() > 0) {
            this.mData.clear();
        }
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_favorite_movie, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        final FavoriteMovie favoriteMovie = mData.get(position);

        String poster = "https://image.tmdb.org/t/p/w185" + favoriteMovie.getDesc();
        String title = favoriteMovie.getRating();
        String date = favoriteMovie.getPoster();
        String rate = favoriteMovie.getTitle();
        String desc = favoriteMovie.getYear();
        Log.v("POSTER", poster);
        Log.v("TITLE", title);
        Log.v("YEAR", date);
        Log.v("RATING", rate);
        Log.v("DESC", desc);

        try {
            Picasso.with(holder.itemView.getContext()).load(poster).into(holder.imgPoster);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Load Poster", "GAGAL"+ e.toString());
        }

        // Set Ratting
        Float rateStar = Float.parseFloat(rate);
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        holder.ratingBar.setRating(rateStar / 2);
        if (favoriteMovie.getRating().equals("0")) {
            holder.tvRatting.setText("0");
        } else {
            holder.tvRatting.setText(decimalFormat.format(rateStar));
        }

        //Set Release Date
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            Date release = input.parse(date);                 // parse input
            holder.tvYear.setText(output.format(release));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvTitle.setText(title);
        holder.tvDesc.setText(desc);
        holder.cvFavMovie.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailFavMovieActivity.class);
                intent.putExtra(DetailFavMovieActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailFavMovieActivity.EXTRA_CINEMA, mData.get(position));
                activity.startActivityForResult(intent, DetailFavMovieActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cvFavMovie;
        ImageView imgPoster;
        RatingBar ratingBar;
        TextView tvTitle, tvYear, tvDesc, tvRatting;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            cvFavMovie = itemView.findViewById(R.id.cv_item_fav_movie);
            imgPoster = itemView.findViewById(R.id.iv_poster);
            ratingBar = itemView.findViewById(R.id.rattingBar);
            tvRatting = itemView.findViewById(R.id.tv_ratting);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvDesc = itemView.findViewById(R.id.tv_description);
        }
    }
}
