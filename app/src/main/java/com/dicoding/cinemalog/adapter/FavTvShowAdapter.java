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
import com.dicoding.cinemalog.model.FavoriteTvShow;
import com.dicoding.cinemalog.view.DetailFavTvShowActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FavTvShowAdapter extends RecyclerView.Adapter<FavTvShowAdapter.TvShowViewHolder> {

    private ArrayList<FavoriteTvShow> mData = new ArrayList<>();
    private Activity activity;
    Context context;

    public FavTvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavoriteTvShow> getmData() {
        return mData;
    }

    public void setmData(ArrayList<FavoriteTvShow> mData) {
        if (mData.size() > 0) {
            this.mData.clear();
        }
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    /**
     * Method Create, Update, Delete
     *
     * @param favoriteTvShow
     */
    public void addItem(FavoriteTvShow favoriteTvShow) {
        this.mData.add(favoriteTvShow);
        notifyItemInserted(mData.size() - 1);
    }

    public void updateItem(int position, FavoriteTvShow favoriteTvShow) {
        this.mData.set(position, favoriteTvShow);
        notifyItemChanged(position, favoriteTvShow);
    }

    public void removeItem(int position) {
        this.mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_favorite_tvshow, viewGroup, false);
        return new TvShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder holder, final int position) {
        final FavoriteTvShow favoriteTvShow = mData.get(position);

        String poster = "https://image.tmdb.org/t/p/w185" + favoriteTvShow.getDesc();
        String title = favoriteTvShow.getPoster();
        String date = favoriteTvShow.getRating();
        String rate = favoriteTvShow.getName();
        String desc = favoriteTvShow.getRelease();
        Log.v("POSTER", poster);
        Log.v("NAME", title);
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
        if (favoriteTvShow.getRating().equals("0")) {
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
        holder.cvFavTvShow.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailFavTvShowActivity.class);
                intent.putExtra(DetailFavTvShowActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailFavTvShowActivity.EXTRA_CINEMA, mData.get(position));
                activity.startActivityForResult(intent, DetailFavTvShowActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        CardView cvFavTvShow;
        ImageView imgPoster;
        RatingBar ratingBar;
        TextView tvTitle, tvYear, tvDesc, tvRatting;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            cvFavTvShow = itemView.findViewById(R.id.cv_item_fav_tvshow);
            imgPoster = itemView.findViewById(R.id.iv_poster);
            ratingBar = itemView.findViewById(R.id.rattingBar);
            tvRatting = itemView.findViewById(R.id.tv_ratting);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvDesc = itemView.findViewById(R.id.tv_description);
        }
    }
}
