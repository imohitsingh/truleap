package com.example.eventadminapp1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvEmail;

        public UserViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvEmail = v.findViewById(R.id.tvEmail);
        }
    }

    public UserAdapter(List<User> users) {
        userList = users;
    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate (R.layout.user_item, parent, false);

        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
