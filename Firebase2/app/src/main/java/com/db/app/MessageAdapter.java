package com.db.app;

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
                    .inflate(R.layout.message_item, parent, false);
        }

        ImageView photoImageView = convertView.findViewById(R.id.imageView);
        TextView nameView = convertView.findViewById(R.id.nameView);
        TextView textView = convertView.findViewById(R.id.textView);

        Message message = getItem(position);

        boolean isText = message.getImageurl() == null;
        if(isText){
            textView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            textView.setText(message.getText());
        }else{
            textView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getImageurl())
                    .into(photoImageView);
        }
        nameView.setText(message.getText());
        return super.getView(position, convertView, parent);
    }
}
