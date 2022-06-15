package com.example.parsetagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parsetagram.R;
import com.example.parsetagram.models.Comment;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    public ArrayList<Comment> mComments = new ArrayList<>();

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);

        // Inflate custom layout
        View commentView = inflator.inflate(R.layout.item_comment, parent, false);

        // Return a new holder instance
        return new ViewHolder(commentView);
    }

    // Populates data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.tvBody.setText(comment.getBody());
        holder.tvAuthor.setText(comment.getAuthor().getUsername());
    }

    @Override
    public int getItemCount() { return mComments.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor;
        TextView tvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }

        public void bind(Comment comment) {
            tvAuthor.setText(comment.getAuthor().getUsername());
            tvBody.setText(comment.getBody());
        }

    }
}
