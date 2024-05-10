package com.example.db_task;

import android.content.Intent;
import android.graphics.Color;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Data.DbHandler;
import Model.doList;

public class MyRecycleViewAdapter  extends RecyclerView.Adapter<MyRecycleViewAdapter.RecycleViewHolder>{
    private ArrayList<doList> arrayList;

    public MyRecycleViewAdapter(ArrayList<doList> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyRecycleViewAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewAdapter.RecycleViewHolder holder, int position) {
        doList DoList = arrayList.get(position);

        holder.title.setText(DoList.getTitle());
        holder.description.setText(DoList.getDescription());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHandler dbHandler = new DbHandler(v.getContext());
                doList doList = dbHandler.getList(DoList.getId());
                dbHandler.deleteList(doList);

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailActivity.class);
                intent.putExtra("id", DoList.getId());
                v.getContext().startActivity(intent);

            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check = holder.checkBox.isChecked();
                int id = DoList.getId();

                DbHandler dbHandler = new DbHandler(view.getContext());
                doList doList = dbHandler.getList(id);
                if(check){

                    doList.setCheck(1);

                    dbHandler.updateListCheckBox(doList);

                    Log.i("Info", "title " + DoList.getTitle() + " desc: " + DoList.getTitle() + " status " + DoList.getCheck());
                    holder.description.setTextColor(Color.rgb(0, 255, 0));
                }else{
                    holder.description.setTextColor(Color.rgb(0, 0, 255));

                    doList.setCheck(0);
                    dbHandler.updateListCheckBox(doList);
                    Log.i("Info", "title " + DoList.getTitle() + " desc: " + DoList.getTitle() + " status " + DoList.getCheck());
                }
//                Toast.makeText(view.getContext(), "id " + id, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public FloatingActionButton deleteBtn;
        public CheckBox checkBox;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
