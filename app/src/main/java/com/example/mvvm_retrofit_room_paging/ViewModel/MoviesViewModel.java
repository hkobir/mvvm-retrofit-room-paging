package com.example.mvvm_retrofit_room_paging.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mvvm_retrofit_room_paging.Dao.MoviesDao;
import com.example.mvvm_retrofit_room_paging.Database.MoviesDatabase;
import com.example.mvvm_retrofit_room_paging.Models.Movie;


public class MoviesViewModel extends AndroidViewModel {

    private MoviesDao moviesDao;

    public final LiveData<PagedList<Movie>> pagedListLiveData;

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        moviesDao = MoviesDatabase.getInstance(application).moviesDao();
        pagedListLiveData = new LivePagedListBuilder<>(
                moviesDao.getAllMovies(), 10
        ).build();


    }


}
