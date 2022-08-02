package com.my.tiktok.model;

public class CommentModel {
    private String postBody;
    private String postedBy;
    private long postedAt;

    public CommentModel(String postBody, String postedBy, long postedAt) {
        this.postBody = postBody;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
    }

    public CommentModel() {
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }
}
