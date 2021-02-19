package com.example.mvvm_retrofit_room_paging.Network;



import com.example.mvvm_retrofit_room_paging.Models.MoviesDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DetailsApi {

    @GET("movies-detail.php")
    Call<List<MoviesDetail>> getAllMoviesDetails();
}
