package com.dragonlink.model;
import java.time.LocalDateTime;

public class Friend {
    private int friendID;
    private int userID;
    private int friendUserID;
    private String status;
    private LocalDateTime createTime;
    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFriendUserID() {
        return friendUserID;
    }

    public void setFriendUserID(int friendUserID) {
        this.friendUserID = friendUserID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}

