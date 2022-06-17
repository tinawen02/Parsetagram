package com.example.parsetagram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.parsetagram.EndlessRecyclerViewScrollListener;
import com.example.parsetagram.models.Post;
import com.example.parsetagram.adapters.PostsAdapter;
import com.example.parsetagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private List<Post> allPosts;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Lookup the swipe container view
        swipeContainer = findViewById(R.id.swipeContainer);

        // Allows the user to refresh their timeline to update feed time and posts
        allowRefreshing(swipeContainer);

        rvPosts = findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();

        // Initialize the array that will hold posts and create a PostsAdapter
        adapter = new PostsAdapter(this, allPosts);

        // Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        // Set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        // Configure Recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(linearLayoutManager);
        rvPosts.setAdapter(adapter);

        // Query posts from Parsetagram
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

    private void allowRefreshing(SwipeRefreshLayout swipeContainer) {

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
    private void queryPosts(Date time) {
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
                adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
    }

}