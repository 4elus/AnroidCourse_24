package com.example.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    // отображать элемент который выбрали
    public void setSelectedItem(String selectedItem) {
        TextView view = getView().findViewById(R.id.detailsText);
        view.setText(selectedItem);
    }
    public void setSendPrice(int price) {
        TextView priceView = getView().findViewById(R.id.price);
        priceView.setText(price + "$");
    }
    public void setSendDesc(String description) {
        TextView descView = getView().findViewById(R.id.desc);
        descView.setText(description);
    }

    public void setSendImg(int img) {
        ImageView imgView = getView().findViewById(R.id.img);
        imgView.setImageResource(img);
    }
}