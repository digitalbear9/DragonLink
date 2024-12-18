package com.dragonlink.dao;

import com.dragonlink.model.OfflineMessage;
import com.dragonlink.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfflineMessageDAO {

    // 添加离线消息
    public boolean addOfflineMessage(int userID, int messageID) {
        String sql = "INSERT INTO OfflineMessages (UserID, MessageID) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, messageID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取用户的所有离线消息
    public List<OfflineMessage> getOfflineMessagesByUserID(int userID) {
        List<OfflineMessage> offlineMessages = new ArrayList<>();
        String sql = "SELECT * FROM OfflineMessages WHERE UserID = ? ORDER BY Timestamp ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OfflineMessage offlineMessage = new OfflineMessage();
                offlineMessage.setOfflineMessageID(rs.getInt("OfflineMessagID"));
                offlineMessage.setUserID(rs.getInt("UserID"));
                offlineMessage.setMessageID(rs.getInt("MessageID"));
                offlineMessage.setTimestamp(rs.getTimestamp("Timestamp").toLocalDateTime());
                offlineMessages.add(offlineMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offlineMessages;
    }

    // 删除离线消息
    public boolean deleteOfflineMessage(int offlineMessageID) {
        String sql = "DELETE FROM OfflineMessages WHERE OfflineMessagID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offlineMessageID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
