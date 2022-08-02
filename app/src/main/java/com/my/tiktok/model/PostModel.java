package com.my.tiktok.model;

public class PostModel {
    private String desc , video , videId, followingBy ,followingAt;
    private int likes;
    private int commentCount;
    private int viCount;

    public PostModel(String desc, String video, String followingBy, String followingAt) {
        this.desc = desc;
        this.video = video;
        this.followingBy = followingBy;
        this.followingAt = followingAt;
    }

    public PostModel() {

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideId() {
        return videId;
    }

    public void setVideId(String videId) {
        this.videId = videId;
    }

    public String getFollowingBy() {
        return followingBy;
    }

    public void setFollowingBy(String followingBy) {
        this.followingBy = followingBy;
    }

    public String getFollowingAt() {
        return followingAt;
    }

    public void setFollowingAt(String followingAt) {
        this.followingAt = followingAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViCount() {
        return viCount;
    }

    public void setViCount(int viCount) {
        this.viCount = viCount;
    }
}
