package com.alisherzhanibek.newnewfirebase;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private ArrayList<User> users;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(int position);
    }
    public void setOnUserClickListener(OnUserClickListener listener) {
        this.listener = listener;
    }
    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_item, viewGroup, false);
        UserViewHolder viewHolder = new UserViewHolder(view, listener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder userViewHolder, int i) {
        User currentUser = users.get(i);
        userViewHolder.avatarImageView.setImageResource(currentUser.getAvatarMockUpResource());
        userViewHolder.userNameTextView.setText(currentUser.getName());
    }
    @Override
    public int getItemCount() {
        return users.size();
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarImageView;
        public TextView userNameTextView;
        public UserViewHolder(@NonNull View itemView, OnUserClickListener listener) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(itemView.getContext(), ChatActivity.class);
//                    intent.putExtra("recipientUserId",  );
//                    intent.putExtra("recipientUserName", userArrayList.get(itemView).getName());
//                    intent.putExtra("userName", userName);
//                    itemView.getContext().startActivity(intent);
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onUserClick(position);
                        }
                    }
                }
            });
        }
    }
}

