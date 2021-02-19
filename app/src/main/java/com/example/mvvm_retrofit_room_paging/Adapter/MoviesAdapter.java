package com.example.mvvm_retrofit_room_paging.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.mvvm_retrofit_room_paging.Listener.Listener;
import com.example.mvvm_retrofit_room_paging.Models.Movie;
import com.example.mvvm_retrofit_room_paging.R;

public class MoviesAdapter extends PagedListAdapter<Movie, MoviesAdapter.MoviesViewHolder> {

    private Context context;
    private static Listener listener;

    public MoviesAdapter(Context context, Listener listener) {
        super(itemCallback);
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie movie = getItem(position);
        holder.name.setText(movie.getMovie_name());
        holder.rating.setText("rating : " + movie.getRating());
        Glide.with(context)
                .load(movie.getPoster())
                .into(holder.poster);
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView name, rating;
        ImageView poster;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            poster = itemView.findViewById(R.id.poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickItemListener(getAdapterPosition());
                }
            });
        }
    }

    private static DiffUtil.ItemCallback<Movie> itemCallback = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getMovieDb_id() == newItem.getMovieDb_id();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };
}
