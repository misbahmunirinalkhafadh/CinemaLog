package com.dicoding.cinemalog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteTvShow implements Parcelable {
    private int tvShowId;
    private int favorite;
    private String poster;
    private String rating;
    private String name;
    private String release;
    private String desc;

    private FavoriteTvShow(Parcel in) {
        tvShowId = in.readInt();
        favorite = in.readInt();
        poster = in.readString();
        rating = in.readString();
        name = in.readString();
        release = in.readString();
        desc = in.readString();
    }

    public static final Creator<FavoriteTvShow> CREATOR = new Creator<FavoriteTvShow>() {
        @Override
        public FavoriteTvShow createFromParcel(Parcel in) {
            return new FavoriteTvShow(in);
        }

        @Override
        public FavoriteTvShow[] newArray(int size) {
            return new FavoriteTvShow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tvShowId);
        dest.writeInt(favorite);
        dest.writeString(poster);
        dest.writeString(rating);
        dest.writeString(name);
        dest.writeString(release);
        dest.writeString(desc);
    }

    public FavoriteTvShow(int tvShowId, int favorite, String poster, String rating, String name, String release, String desc) {
        this.tvShowId = tvShowId;
        this.favorite = favorite;
        this.poster = poster;
        this.rating = rating;
        this.name = name;
        this.release = release;
        this.desc = desc;
    }

    /**
     * Hasil Setter Getter
     * @return
     */


    public FavoriteTvShow() {
    }

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
