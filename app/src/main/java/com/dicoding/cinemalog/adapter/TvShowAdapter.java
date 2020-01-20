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
import com.dicoding.cinemalog.model.TvShow;
import com.dicoding.cinemalog.view.DetailTvShowActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {

    private ArrayList<TvShow> mData = new ArrayList<>();
    private Activity activity;

    public TvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(ArrayList<TvShow> items) {
        if (items.size() > 0) {
            this.mData.clear();
        }
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_tvshow, viewGroup, false);
        return new TvViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvViewHolder holder, final int position) {
        TvShow tvShow = mData.get(position);

        String url_poster = "https://image.tmdb.org/t/p/w185";
        Picasso.with(holder.itemView.getContext()).load(url_poster + tvShow.getPoster()).into(holder.imgPoster);

        // set Ratting
        Float rateStar = Float.parseFloat(tvShow.getRating());
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        holder.ratingBar.setRating(rateStar / 2);
        if (tvShow.getRating().equals("0")) {
            holder.tvRatting.setText("0");
        } else {
            holder.tvRatting.setText(decimalFormat.format(rateStar));
        }

        //Set Release Date
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        try {
            Date release = input.parse(tvShow.getRelease());                 // parse input
            holder.tvYear.setText(output.format(release));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvTitle.setText(tvShow.getName());
        holder.tvDesc.setText(tvShow.getDesc());
        holder.cvTvShow.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailTvShowActivity.EXTRA_CINEMA, mData.get(position));
                activity.startActivityForResult(intent, DetailTvShowActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        RatingBar ratingBar;
        CardView cvTvShow;
        TextView tvTitle, tvYear, tvDesc, tvRatting;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.iv_poster);
            ratingBar = itemView.findViewById(R.id.rattingBar);
            tvRatting = itemView.findViewById(R.id.tv_ratting);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvDesc = itemView.findViewById(R.id.tv_description);
            cvTvShow = itemView.findViewById(R.id.cv_item_tvshow);
        }
    }
}
