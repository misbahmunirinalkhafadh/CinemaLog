package com.dicoding.cinemalog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteMovie implements Parcelable {
    private int id;
    private int movieId;
    private int favorite;
    private String poster;
    private String rating;
    private String title;
    private String year;
    private String desc;

    private FavoriteMovie(Parcel in) {
        id = in.readInt();
        movieId = in.readInt();
        favorite = in.readInt();
        poster = in.readString();
        rating = in.readString();
        title = in.readString();
        year = in.readString();
        desc = in.readString();
    }

    public static final Creator<FavoriteMovie> CREATOR = new Creator<FavoriteMovie>() {
        @Override
        public FavoriteMovie createFromParcel(Parcel in) {
            return new FavoriteMovie(in);
        }

        @Override
        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(movieId);
        dest.writeInt(favorite);
        dest.writeString(poster);
        dest.writeString(rating);
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(desc);
    }

    /**
     * Hasil Setter Getter
     * @return
     */
    public FavoriteMovie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
