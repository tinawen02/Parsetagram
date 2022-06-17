package com.example.parsetagram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parsetagram.R;
import com.example.parsetagram.models.Comment;
import com.example.parsetagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class ComposeCommentActivity extends AppCompatActivity {

    private Post post;
    private EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));
        etBody = findViewById(R.id.etBody);

        // Posts a new comment when a user clicks a button
        postComment(findViewById(R.id.btSave));

    }

    private void postComment(Button btSave) {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Post the new comment to parse
                String body = etBody.getText().toString();

                Comment comment = new Comment();
                comment.setAuthor(ParseUser.getCurrentUser());
                comment.setBody(body);
                comment.setPost(post);

                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            return;
                        }
                        finish();
                    }
                });

            }
        });
    }
}