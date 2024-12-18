package com.dragonlink.dao;

import com.dragonlink.model.PasswordReset;
import com.dragonlink.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordResetDAO {

    // 添加密码重置记录
    public boolean addPasswordReset(PasswordReset passwordReset) {
        String sql = "INSERT INTO PasswordResets (UserID, Resettoken, Expiration, Isused) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, passwordReset.getUserID());
            stmt.setString(2, passwordReset.getResetToken());
            stmt.setTimestamp(3, Timestamp.valueOf(passwordReset.getExpiration()));
            stmt.setBoolean(4, passwordReset.getIsUsed());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据token获取密码重置记录
    public PasswordReset getPasswordResetByToken(String token) {
        String sql = "SELECT * FROM PasswordResets WHERE Resettoken = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PasswordReset reset = new PasswordReset();
                reset.setResetID(rs.getInt("ResetID"));
                reset.setUserID(rs.getInt("UserID"));
                reset.setResetToken(rs.getString("Resettoken"));
                reset.setExpiration(rs.getTimestamp("Expiration").toLocalDateTime());
                reset.setIsUsed(rs.getBoolean("Isused"));
                reset.setCreatedTime(rs.getTimestamp("Createdtime").toLocalDateTime());
                return reset;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新密码重置记录为已使用
    public boolean markPasswordResetAsUsed(int resetID) {
        String sql = "UPDATE PasswordResets SET Isused = 1 WHERE ResetID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, resetID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除过期的密码重置记录
    public boolean deleteExpiredResets() {
        String sql = "DELETE FROM PasswordResets WHERE Expiration < GETDATE()";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
