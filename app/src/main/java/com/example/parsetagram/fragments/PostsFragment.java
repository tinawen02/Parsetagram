package com.example.parsetagram.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.parsetagram.EndlessRecyclerViewScrollListener;
import com.example.parsetagram.R;
import com.example.parsetagram.adapters.PostsAdapter;
import com.example.parsetagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostsFragment extends Fragment {

   private RecyclerView rvPosts;
   private SwipeRefreshLayout swipeContainer;
   private EndlessRecyclerViewScrollListener scrollListener;
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

        // Allows the user to refresh their timeline to update feed time and post
        allowRefreshing(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);

        // Query the posts
        queryPosts(null);

        // Used to allow endless scrolling
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggers when new data is appended to the list
                queryPosts(allPosts.get(0).getCreatedAt());
            }
        };

        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);

    }

    protected void allowRefreshing(View view) {
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                adapter.clear();
                queryPosts(null);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
                swipeContainer.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });

    }
}