package com.example.parsetagram.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_BODY = "body";
    public static final String KEY_POST = "post";
    public static final String KEY_AUTHOR = "author";

    public String getBody() {
        return getString(KEY_BODY);
    }
    public void setBody(String body) {
        put(KEY_BODY,body);
    }

    public Post getPost() {
        return (Post) getParseObject(KEY_POST);
    }
    public void setPost(Post post) {
        put(KEY_POST,post);
    }

    public ParseUser getAuthor() {
        return getParseUser(KEY_AUTHOR);
    }
    public void setAuthor(ParseUser user) {
        put(KEY_AUTHOR,user);
    }
}
