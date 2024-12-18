package com.dragonlink.dao;

import com.dragonlink.model.Friend;
import com.dragonlink.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendDAO {

    // 添加好友关系
    public boolean addFriend(int userID, int friendUserID, String status) {
        String sql = "INSERT INTO Friends (UserID, FriendUserID, Status) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, friendUserID);
            stmt.setString(3, status);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 更新好友状态
    public boolean updateFriendStatus(int userID, int friendUserID, String status) {
        String sql = "UPDATE Friends SET Status = ? WHERE UserID = ? AND FriendUserID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, userID);
            stmt.setInt(3, friendUserID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取好友列表
    public List<Friend> getFriendsByUserID(int userID) {
        List<Friend> friends = new ArrayList<>();
        String sql = "SELECT * FROM Friends WHERE UserID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Friend friend = new Friend();
                friend.setFriendID(rs.getInt("FriendID"));
                friend.setUserID(rs.getInt("UserID"));
                friend.setFriendUserID(rs.getInt("FriendUserID"));
                friend.setStatus(rs.getString("Status"));
                friend.setCreateTime(rs.getTimestamp("Createtime").toLocalDateTime());
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    // 删除好友关系
    public boolean deleteFriend(int userID, int friendUserID) {
        String sql = "DELETE FROM Friends WHERE UserID = ? AND FriendUserID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, friendUserID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //判断是否为好友关系
    public boolean isFriend(int userID, int friendUserID) {
        String sql = "SELECT * FROM Friends WHERE UserID = ? AND FriendUserID = ? AND Status = 'accepted'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, friendUserID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
