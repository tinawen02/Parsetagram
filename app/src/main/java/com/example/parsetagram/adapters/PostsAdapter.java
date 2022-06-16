package com.example.parsetagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parsetagram.models.Post;
import com.example.parsetagram.R;
import com.example.parsetagram.activities.PostDetailsActivity;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvTimeStamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvUsername.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            } else {
                ivImage.setVisibility(View.GONE);
            }

            String test = post.getUser().getUsername();

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostDetailsActivity.class);
                    // serialize the movie using parceler, use its short name as a key
                    intent.putExtra("post", Parcels.wrap(post));
                    intent.putExtra("test",test);
                    // show the activity
                    context.startActivity(intent);
                }
            });

            // Calculate relative date
            Date createdAt = post.getCreatedAt();
            String timeAgo = Post.calculateTimeAgo(createdAt);
            tvTimeStamp.setText(timeAgo);
        }

       /* @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post movie = posts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }

        }*/
    }

    // Clean all posts of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of Posts
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
