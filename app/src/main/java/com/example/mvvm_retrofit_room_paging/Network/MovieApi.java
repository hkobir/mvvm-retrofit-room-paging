package com.example.mvvm_retrofit_room_paging.Network;

import androidx.paging.PagedList;


import com.example.mvvm_retrofit_room_paging.Models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {

    @GET("/movies.php")
    Call<List<Movie>> getAllMovies();
}
