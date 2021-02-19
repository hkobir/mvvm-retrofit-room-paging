package com.example.mvvm_retrofit_room_paging.Respository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.mvvm_retrofit_room_paging.Database.MoviesDatabase;
import com.example.mvvm_retrofit_room_paging.Models.MoviesDetail;

import java.util.List;

public class MoviesDetailRepository {

    private MoviesDatabase moviesDatabase;
    private LiveData<List<MoviesDetail>> getAllMoviesDetail;

    public MoviesDetailRepository(Application application) {
        moviesDatabase = MoviesDatabase.getInstance(application);
        getAllMoviesDetail = moviesDatabase.moviesDao().getAllMoviesDetails();
    }

    public LiveData<List<MoviesDetail>> getAllMovieDetails() {
        return getAllMoviesDetail;
    }
}
