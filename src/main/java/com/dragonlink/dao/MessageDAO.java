package com.dragonlink.dao;

import com.dragonlink.model.Message;
import com.dragonlink.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    // 插入新消息
    public boolean addMessage(Message message) {
        String sql = "INSERT INTO Messages (SenderID, ReceiverID, GroupID, Content, Isread) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, message.getSenderID());
            stmt.setObject(2, message.getReceiverID(), Types.INTEGER);
            stmt.setObject(3, message.getGroupID(), Types.INTEGER);
            stmt.setString(4, message.getContent());
            stmt.setBoolean(5, message.getIsRead());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据接收者ID获取消息列表（个人聊天）
    public List<Message> getMessagesByReceiverID(int receiverID) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Messages WHERE ReceiverID = ? ORDER BY Timestamp ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, receiverID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setMessageID(rs.getInt("MessageID"));
                message.setSenderID(rs.getInt("SenderID"));
                message.setReceiverID(rs.getInt("ReceiverID"));
                message.setContent(rs.getString("Content"));
                message.setTimestamp(rs.getTimestamp("Timestamp").toLocalDateTime());
                message.setIsRead(rs.getBoolean("Isread"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    // 根据群组ID获取消息列表（群聊）
    public List<Message> getMessagesByGroupID(int groupID) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Messages WHERE GroupID = ? ORDER BY Timestamp ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setMessageID(rs.getInt("MessageID"));
                message.setSenderID(rs.getInt("SenderID"));
                message.setGroupID(rs.getInt("GroupID"));
                message.setContent(rs.getString("Content"));
                message.setTimestamp(rs.getTimestamp("Timestamp").toLocalDateTime());
                message.setIsRead(rs.getBoolean("Isread"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    // 更新消息为已读
    public boolean markMessageAsRead(int messageID) {
        String sql = "UPDATE Messages SET Isread = 1 WHERE MessageID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, messageID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除消息
    public boolean deleteMessage(int messageID) {
        String sql = "DELETE FROM Messages WHERE MessageID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, messageID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
