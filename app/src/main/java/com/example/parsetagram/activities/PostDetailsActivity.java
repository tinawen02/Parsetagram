package com.example.parsetagram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parsetagram.adapters.CommentsAdapter;
import com.example.parsetagram.models.Comment;
import com.example.parsetagram.models.Post;
import com.example.parsetagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.Date;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

    private TextView tvUsernamePost;
    private ImageView ivPostImagePost;
    private TextView tvDescriptionPost;
    private TextView tvTimeStampPost;
    private ImageButton ibCommentPost;
    private ImageButton ibHeartPost;
    private ImageButton ibSharePost;
    private TextView tvLikesPost;
    private TextView tvUsernameDescriptionPost;
    private RecyclerView rvComments;
    private CommentsAdapter adapter;

    private Post post;

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshComments();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUsernamePost = findViewById(R.id.tvUsernamePost);
        ivPostImagePost = findViewById(R.id.ivPostImagePost);
        tvDescriptionPost = findViewById(R.id.tvDescriptionPost);
        tvTimeStampPost = findViewById(R.id.tvTimeStampPost);
        ibCommentPost = findViewById(R.id.ibCommentPost);
        ibHeartPost = findViewById(R.id.ibHeartPost);
        ibSharePost = findViewById(R.id.ibSharePost);
        tvUsernameDescriptionPost = findViewById(R.id.tvUsernameDescriptionPost);
        rvComments = findViewById(R.id.rvComments);
        tvLikesPost = findViewById(R.id.tvLikesPost);

        adapter = new CommentsAdapter();

        // Unwrap the post passed in via intent, using its simple name as a key
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));
        String test = getIntent().getStringExtra("test");

        // Set all views
        tvUsernamePost.setText(test);
        tvDescriptionPost.setText(post.getDescription());
        tvLikesPost.setText(post.getLikesCount());
        tvUsernameDescriptionPost.setText(test);
        tvUsernamePost.setText(test);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);

        Glide.with(this).load(post.getImage().getUrl())
                .into(ivPostImagePost);

        // Updates relative time
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvTimeStampPost.setText(timeAgo);

        // Allows a user to comment
        userComment(ibCommentPost);

        // Refreshes the comments
        refreshComments();

        // Allows a user to like
        userLikes(ibHeartPost);

    }

    private void userLikes(ImageButton ibHeartPost) {
        ibHeartPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post.isLikedByCurrentUser()) {
                    // unlike
                    post.unlike();
                    ibHeartPost.setBackground(getDrawable(R.drawable.ufi_heart));
                } else {
                    // like
                    post.like();
                    ibHeartPost.setBackground(getDrawable(R.drawable.ic_heart_active));

                }
                tvLikesPost.setText(post.getLikesCount());
            }
        });
    }

    private void userComment(ImageButton ibCommentPost) {
        ibCommentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostDetailsActivity.this, ComposeCommentActivity.class);
                i.putExtra("post", Parcels.wrap(post));
                startActivity(i);
                finish();
            }
        });
    }

    void refreshComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery("Comment");
        query.whereEqualTo(Comment.KEY_POST, post);
        query.orderByDescending("createdAt");
        query.include(Comment.KEY_AUTHOR);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if (e != null) {
                    return;
                }
                adapter.mComments.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }
}