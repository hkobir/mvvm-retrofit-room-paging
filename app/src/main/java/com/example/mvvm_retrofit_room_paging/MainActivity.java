package com.example.mvvm_retrofit_room_paging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mvvm_retrofit_room_paging.Adapter.MoviesAdapter;
import com.example.mvvm_retrofit_room_paging.Listener.Listener;
import com.example.mvvm_retrofit_room_paging.Models.Movie;
import com.example.mvvm_retrofit_room_paging.Network.MovieApi;
import com.example.mvvm_retrofit_room_paging.Respository.MoviesRepository;
import com.example.mvvm_retrofit_room_paging.ViewModel.MoviesViewModel;
import com.example.mvvm_retrofit_room_paging.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Listener {
    private ActivityMainBinding binding;
    private static final String URL_DATA = "http://www.codingwithjks.tech/movies.php/";
    private MoviesViewModel moviesViewModel;
    private MoviesAdapter moviesAdapter;
    private MoviesRepository moviesRepository;
    private PagedList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        moviesRepository=new MoviesRepository(getApplication());
        binding.movieRecyclerview.setItemAnimator(new DefaultItemAnimator());
        moviesAdapter=new MoviesAdapter(this,this::onClickItemListener);
        binding.movieRecyclerview.setHasFixedSize(true);
        binding.movieRecyclerview.setLayoutManager(new GridLayoutManager(this,2));

        moviesViewModel=new ViewModelProvider(this).get(MoviesViewModel.class);
        moviesViewModel.pagedListLiveData.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> moviesPagedList) {
                moviesAdapter.submitList(moviesPagedList);
                binding.movieRecyclerview.setAdapter(moviesAdapter);
                movies=moviesPagedList;
            }
        });
        saveDataIntoDatabase();
    }
    private void saveDataIntoDatabase() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URL_DATA)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieApi api=retrofit.create(MovieApi.class);
        Call<List<Movie>> call=api.getAllMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if(response.isSuccessful())
                {
                    Log.d("main", "onResponse: "+response.body());
                    moviesRepository.Insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d("main", "onFailure: "+t.getMessage());
            }
        });

    }

    @Override
    public void onClickItemListener(int position) {
        Intent intent=new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("id",movies.get(position).getMovie_id());
        startActivity(intent);
    }
}