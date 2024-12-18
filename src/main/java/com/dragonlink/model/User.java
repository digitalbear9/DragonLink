package com.dragonlink.model;

import java.time.LocalDateTime;
import java.util.Date;

public class User {
    private int userID;
    private String username;
    private String passwordHash;
    private String nickname;
    private String status;
    private LocalDateTime createdTime;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdAt) {
        this.createdTime = createdAt;
    }
}
