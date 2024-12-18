package com.dragonlink.model;
import java.time.LocalDateTime;

public class PasswordReset {
    private int resetID;
    private int userID;
    private String resetToken;
    private LocalDateTime expiration;
    private boolean isUsed;
    private LocalDateTime createdTime;

    public int getResetID() {
        return resetID;
    }

    public void setResetID(int resetID) {
        this.resetID = resetID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
