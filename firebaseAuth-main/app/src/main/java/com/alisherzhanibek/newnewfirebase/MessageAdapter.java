package com.alisherzhanibek.newnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(Context context, int resource, ArrayList<Message> arrayList) {
        super(context, resource, arrayList);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater()
                    .inflate(R.layout.message_item, null);
        }

        ImageView photoImageView = convertView.findViewById(R.id.imageView);
        TextView nameView = convertView.findViewById(R.id.nameView);
        TextView textView = convertView.findViewById(R.id.textView);
        ImageView sendImage = convertView.findViewById(R.id.sendImgView);


        //
        if(sendImage == null){
            sendImage.setVisibility(View.VISIBLE);
        }else{
            sendImage.setVisibility(View.GONE);
        }

        Message message = getItem(position);

        boolean isText = message.getImageurl() == null;
        if(isText){
            textView.setVisibility(View.VISIBLE);
            textView.setText(message.getText());
        }else{
            textView.setVisibility(View.GONE);
//            photoImageView.setVisibility(View.VISIBLE);
            sendImage.setVisibility(View.VISIBLE);
//            Glide.with(photoImageView.getContext())
//                    .load(message.getImageurl())
//                    .into(photoImageView);
            Glide.with(sendImage.getContext())
                    .load(message.getSendIMG())
                    .into(sendImage);
        }
        nameView.setText(message.getName());
        return convertView;
    }
}