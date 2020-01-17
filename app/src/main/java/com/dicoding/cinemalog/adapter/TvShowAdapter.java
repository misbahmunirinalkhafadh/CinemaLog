package com.dicoding.cinemalog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.cinemalog.R;
import com.dicoding.cinemalog.model.TvShow;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {

    private ArrayList<TvShow> mData = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<TvShow> items) {
        mData.clear();
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
    public void onBindViewHolder(@NonNull final TvViewHolder holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        RatingBar ratingBar;
        TextView tvTitle, tvYear, tvDesc, tvRatting;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.iv_poster);
            ratingBar = itemView.findViewById(R.id.rattingBar);
            tvRatting = itemView.findViewById(R.id.tv_ratting);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvDesc = itemView.findViewById(R.id.tv_description);
        }
    }


    public interface OnItemClickCallback {
        void onItemClicked(TvShow data);
    }
}
