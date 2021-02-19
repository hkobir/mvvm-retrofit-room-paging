package com.example.mvvm_retrofit_room_paging.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.mvvm_retrofit_room_paging.Dao.MoviesDao;
import com.example.mvvm_retrofit_room_paging.Models.Movie;
import com.example.mvvm_retrofit_room_paging.Models.MoviesDetail;


@Database(entities = {Movie.class, MoviesDetail.class}, version = 1)

public abstract class MoviesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Movies_list";

    public abstract MoviesDao moviesDao();


    private static volatile MoviesDatabase INSTANCE = null;  //save instance into memory after closed

    public static MoviesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, MoviesDatabase.class,
                            DATABASE_NAME)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsynTask(INSTANCE);
        }
    };

    static class PopulateAsynTask extends AsyncTask<Void, Void, Void> {
        private MoviesDao moviesDao;

        private PopulateAsynTask(MoviesDatabase moviesDatabase) {
            moviesDao = moviesDatabase.moviesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            moviesDao.deleteAll();   //first time delete all from main thread
            return null;
        }
    }
}
