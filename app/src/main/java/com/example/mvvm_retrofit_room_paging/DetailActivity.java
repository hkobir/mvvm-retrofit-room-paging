package com.example.mvvm_retrofit_room_paging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.mvvm_retrofit_room_paging.Models.MoviesDetail;
import com.example.mvvm_retrofit_room_paging.Network.DetailsApi;
import com.example.mvvm_retrofit_room_paging.Respository.MoviesRepository;
import com.example.mvvm_retrofit_room_paging.ViewModel.MoviesDetailViewModel;
import com.example.mvvm_retrofit_room_paging.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    public static final String DATA_URL="http://www.codingwithjks.tech/movies-detail.php/";
    private static final String TAG ="main" ;
    private MoviesRepository repository;
    private int position;
    private List<MoviesDetail> moviesDetails;
    private MoviesDetailViewModel moviesDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        repository=new MoviesRepository(getApplication());
        moviesDetails=new ArrayList<>();
        moviesDetailViewModel=new ViewModelProvider(this).get(MoviesDetailViewModel.class);
        getMoviesDetails();
        setData();
    }
    private void setData() {
        Bundle bundle=getIntent().getExtras();

        if(bundle !=null)
        {
            position=bundle.getInt("position",0);
            moviesDetailViewModel.getAllMoviesDetail().observe(this, new Observer<List<MoviesDetail>>() {
                @Override
                public void onChanged(List<MoviesDetail> moviesDetailList) {
                    try {
                        Glide.with(getApplicationContext())
                                .load(moviesDetailList.get(position).getPoster_name())
                                .into(binding.imageDetail);
                        binding.nameDetail.setText(moviesDetailList.get(position).getMovies_name());
                        binding.ratingDetail.setText("" + moviesDetailList.get(position).getMovie_rating());
                        binding.descriptionDetail.setText(moviesDetailList.get(position).getMovie_description());
                    }
                    catch (Throwable t){
                    }

                    Log.d(TAG, "onChanged: "+moviesDetailList);
                }
            });

        }
    }

    private void getMoviesDetails() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(DATA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DetailsApi api=retrofit.create(DetailsApi.class);
        Call<List<MoviesDetail>> call=api.getAllMoviesDetails();
        call.enqueue(new Callback<List<MoviesDetail>>() {
            @Override
            public void onResponse(Call<List<MoviesDetail>> call, Response<List<MoviesDetail>> response) {
                if(response.isSuccessful())
                {
                    repository.insertMoviesDetail(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MoviesDetail>> call, Throwable t) {
                Log.d("main", "onFailure: "+t.getMessage());
            }
        });
    }
}