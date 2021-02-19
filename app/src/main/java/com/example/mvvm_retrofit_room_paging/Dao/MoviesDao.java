package com.example.mvvm_retrofit_room_paging.Dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.mvvm_retrofit_room_paging.Models.Movie;
import com.example.mvvm_retrofit_room_paging.Models.MoviesDetail;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //replace if has duplicate data
    void insert(List<Movie> moviesList);  //add data to room from from retrofit

    @Query("DELETE FROM movies")
    void deleteAll();

    @Query("SELECT * FROM movies ORDER BY movieDb_id ASC")
    DataSource.Factory<Integer,Movie> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert_movies_details(List<MoviesDetail> moviesDetailList);

    @Query("SELECT * FROM movies_detail")
    LiveData<List<MoviesDetail>> getAllMoviesDetails();
}
