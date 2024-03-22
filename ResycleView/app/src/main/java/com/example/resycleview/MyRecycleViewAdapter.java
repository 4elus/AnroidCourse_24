package com.example.resycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecycleViewAdapter extends  RecyclerView.Adapter<MyRecycleViewAdapter.RecycleViewHolder>{

    private ArrayList<MyRecycleView> arrayList;

    // c Main activity в этот класс
    public MyRecycleViewAdapter(ArrayList<MyRecycleView> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view,
                parent, false);
        return new RecycleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        MyRecycleView myRecycleView = arrayList.get(position);
        holder.imageView.setImageResource(myRecycleView.getImage());
        holder.textView.setText(myRecycleView.getTitle());
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

        }
    }
}
