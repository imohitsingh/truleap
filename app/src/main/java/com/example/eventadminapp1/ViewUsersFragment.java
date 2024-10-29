package com.example.eventadminapp1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewUsersFragment extends Fragment {

    private RecyclerView      rvUsers;
    private UserAdapter       userAdapter;
    private List<User>        userList;
    private FirebaseFirestore db;

    public ViewUsersFragment() {
        // Required empty public constructor
    }

    public static ViewUsersFragment newInstance() {
        return new ViewUsersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_users, container, false);
        rvUsers = view.findViewById(R.id.rvUsers);
        rvUsers.setLayoutManager(new LinearLayoutManager (getContext()));

        userList = new ArrayList<> ();
        userAdapter = new UserAdapter(userList);
        rvUsers.setAdapter(userAdapter);

        db = FirebaseFirestore.getInstance();

        fetchUsers();

        return view;
    }

    private void fetchUsers() {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            userList.add(user);
                            Log.d("Firestore", "User fetched: " + user.getName());
                        }
                        userAdapter.notifyDataSetChanged();
                        Log.d("Firestore", "Total users fetched: " + userList.size());
                    } else {
                        Log.e("Firestore", "Error getting users.", task.getException());
                        Toast.makeText(getContext(), "Error getting users.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
