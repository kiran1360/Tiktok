package com.my.tiktok.model;

public class FollowModel {
    private String followBy;
    private  long followAt;

    public FollowModel() {
    }

    public String getFollowBy() {
        return followBy;
    }

    public void setFollowBy(String followBy) {
        this.followBy = followBy;
    }

    public long getFollowAt() {
        return followAt;
    }

    public void setFollowAt(long followAt) {
        this.followAt = followAt;
    }
}
