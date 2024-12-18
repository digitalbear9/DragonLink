package com.dragonlink.model;
import java.time.LocalDateTime;

public class OfflineMessage {
    private int offlineMessageID;
    private int userID;
    private int messageID;
    private LocalDateTime timestamp;

    public int getOfflineMessageID() {
        return offlineMessageID;
    }

    public void setOfflineMessageID(int offlineMessageID) {
        this.offlineMessageID = offlineMessageID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

