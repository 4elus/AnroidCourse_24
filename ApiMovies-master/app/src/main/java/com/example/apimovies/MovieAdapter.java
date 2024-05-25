package com.example.apimovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.RecycleViewHolder>  {
    private ArrayList<Movies> arrayList;
    private Context context;
    public MovieAdapter( Context context, ArrayList<Movies> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public MovieAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view, parent, false);
        return new RecycleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.RecycleViewHolder holder, int position) {
        Movies currentMovie = arrayList.get(position);

        String title = currentMovie.getTitle();
        String year = currentMovie.getYear();
        String poster = currentMovie.getPoster();
        String type = currentMovie.getType();

        holder.titleView.setText(title);
        holder.yearView.setText(year);
        holder.typeView.setText(type);

        Picasso.get().load(poster).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Detail_activity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;
        public TextView yearView;
        public TextView typeView;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poster);
            titleView = itemView.findViewById(R.id.titleS);
            yearView = itemView.findViewById(R.id.year);
            typeView = itemView.findViewById(R.id.type);
        }
    }
}