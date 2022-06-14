package com.example.parsetagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class PostDetailsActivity extends AppCompatActivity {

    private TextView tvUsernamePost;
    private ImageView ivPostImagePost;
    private TextView tvDescriptionPost;
    private TextView tvTimeStampPost;

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUsernamePost = findViewById(R.id.tvUsernamePost);
        ivPostImagePost = findViewById(R.id.ivPostImagePost);
        tvDescriptionPost = findViewById(R.id.tvDescriptionPost);
        tvTimeStampPost = findViewById(R.id.tvTimeStampPost);

        // Updates relative time
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvTimeStampPost.setText(timeAgo);
    }

}