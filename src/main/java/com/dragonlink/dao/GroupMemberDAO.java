package com.dragonlink.dao;

import com.dragonlink.model.GroupMember;
import com.dragonlink.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberDAO {

    // 添加群成员
    public boolean addGroupMember(int groupID, int userID) {
        String sql = "INSERT INTO GroupMembers (GroupID, UserID) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupID);
            stmt.setInt(2, userID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除群成员
    public boolean removeGroupMember(int groupID, int userID) {
        String sql = "DELETE FROM GroupMembers WHERE GroupID = ? AND UserID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupID);
            stmt.setInt(2, userID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取群组所有成员
    public List<GroupMember> getGroupMembers(int groupID) {
        List<GroupMember> members = new ArrayList<>();
        String sql = "SELECT * FROM GroupMembers WHERE GroupID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GroupMember member = new GroupMember();
                member.setGroupMemberID(rs.getInt("GroupmemeberID"));
                member.setGroupID(rs.getInt("GroupID"));
                member.setUserID(rs.getInt("UserID"));
                member.setJoinedTime(rs.getTimestamp("Joinedtime").toLocalDateTime());
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    //根据群组ID删除所有群组成员
    public boolean deleteAllMembersByGroupID(int groupID) {
        String sql = "DELETE FROM GroupMembers WHERE GroupID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

