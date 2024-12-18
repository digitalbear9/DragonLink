package com.dragonlink.model;
import java.time.LocalDateTime;

public class GroupMember {
    private int groupMemberID;
    private int groupID;
    private int userID;
    private LocalDateTime joinedTime;

    public int getGroupMemberID() {
        return groupMemberID;
    }

    public void setGroupMemberID(int groupMemberID) {
        this.groupMemberID = groupMemberID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDateTime getJoinedTime() {
        return joinedTime;
    }
    public void setJoinedTime(LocalDateTime joinedTime) {
        this.joinedTime = joinedTime;
    }
}
