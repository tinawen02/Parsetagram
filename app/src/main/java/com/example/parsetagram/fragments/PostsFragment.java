package com.example.parsetagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parsetagram.R;
import com.example.parsetagram.adapters.PostsAdapter;
import com.example.parsetagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostsFragment extends Fragment {

   private RecyclerView rvPosts;
   protected PostsAdapter adapter;
   protected List<Post> allPosts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);

        // Steps to use the recycler view:
        // Create layout for one row in the list
        // Create the adapter
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        // Create the data source

        // Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        // Set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        // Query the posts
        queryPosts(null);


    }

    // Gets all of the posts from Parse
    protected void queryPosts(Date time) {
        // Specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        // Include data referred by user key
        query.include(Post.KEY_USER);

        // Allows for user to vies more than 20 posts
        if(time != null) {
            query.whereLessThan(Post.KEY_CREATED_AT, time);
        }

        // Limit query to latest 20 items
        query.setLimit(20);

        // Order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");


        // Start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // Check for errors
                if (e != null) {
                    return;
                }

                // Save received posts to list and notify adapter of new data
                //adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }
}