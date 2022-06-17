package com.example.parsetagram.fragments;

import com.example.parsetagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

public class ProfileFragment extends PostsFragment{

    @Override
    protected void queryPosts(Date time) {
        // Specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        // Include data referred by user key
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());

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
