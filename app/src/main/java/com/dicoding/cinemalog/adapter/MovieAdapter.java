package com.dicoding.cinemalog.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.dicoding.cinemalog.model.Movie;
import com.dicoding.cinemalog.view.DetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mData = new ArrayList<>();
    private Activity activity;

    public MovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Movie> getListMovie() {
        return mData;
    }

    public void setData(ArrayList<Movie> items) {
        if (items.size() > 0) {
            this.mData.clear();
        }
        this.mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Method Create, Update, Delete
     *
     * @param movie
     */
    public void addItem(Movie movie) {
        this.mData.add(movie);
        notifyItemInserted(mData.size() - 1);
    }

    public void updateItem(int position, Movie movie) {
        this.mData.set(position, movie);
        notifyItemChanged(position, movie);
    }

    public void removeItem(int position) {
        this.mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_movie, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        Movie movie = mData.get(position);

        String url_poster = "https://image.tmdb.org/t/p/w185";
        Picasso.with(holder.itemView.getContext()).load(url_poster + movie.getPoster()).into(holder.imgPoster);

        // Set Ratting
        Float rateStar = Float.parseFloat(movie.getRating());
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        holder.ratingBar.setRating(rateStar / 2);
        if (movie.getRating().equals("0")) {
            holder.tvRatting.setText("0");
        } else {
            holder.tvRatting.setText(decimalFormat.format(rateStar));
        }

        //Set Release Date
        final SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            Date release = input.parse(movie.getYear());                 // parse input
            holder.tvYear.setText(output.format(release));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvTitle.setText(movie.getTitle());
        holder.tvDesc.setText(movie.getDesc());
        holder.cvMovie.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailMovieActivity.EXTRA_CINEMA, mData.get(position));
                activity.startActivityForResult(intent, DetailMovieActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        RatingBar ratingBar;
        CardView cvMovie;
        TextView tvTitle, tvYear, tvDesc, tvRatting;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.iv_poster);
            ratingBar = itemView.findViewById(R.id.rattingBar);
            tvRatting = itemView.findViewById(R.id.tv_ratting);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvDesc = itemView.findViewById(R.id.tv_description);
            cvMovie = itemView.findViewById(R.id.cv_item_movie);
        }
    }
}
