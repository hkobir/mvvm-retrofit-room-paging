package com.example.mvvm_retrofit_room_paging.Models;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movies", indices = @Index(value = {"movie_id"}, unique = true))
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int movieDb_id;

    @SerializedName("id")
    private int movie_id;

    @SerializedName("name")
    private String movie_name;

    @SerializedName("image")
    private String poster;

    @SerializedName("rating")
    private float rating;

    public Movie(int movie_id, String movie_name, String poster, float rating) {
        this.movie_id = movie_id;
        this.movie_name = movie_name;
        this.poster = poster;
        this.rating = rating;
    }


    public int getMovieDb_id() {
        return movieDb_id;
    }

    public void setMovieDb_id(int movieDb_id) {
        this.movieDb_id = movieDb_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + movieDb_id +
                ", movie_id=" + movie_id +
                ", movie_name='" + movie_name + '\'' +
                ", poster='" + poster + '\'' +
                ", rating=" + rating +
                '}';
    }
}
