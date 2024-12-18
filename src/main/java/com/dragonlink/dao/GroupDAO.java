package com.dragonlink.dao;

import com.dragonlink.model.Group;
import com.dragonlink.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {

    // 创建群组
    public boolean createGroup(String groupName, int ownerID) {
        String sql = "INSERT INTO Groups (Groupname, OwnerID) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, groupName);
            stmt.setInt(2, ownerID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取所有群组
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM Groups";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Group group = new Group();
                group.setGroupID(rs.getInt("GroupID"));
                group.setGroupName(rs.getString("Groupname"));
                group.setOwnerID(rs.getInt("OwnerID"));
                group.setCreatedTime(rs.getTimestamp("Createdtime").toLocalDateTime());
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    // 根据ID获取群组
    public Group getGroupByID(int groupID) {
        String sql = "SELECT * FROM Groups WHERE GroupID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Group group = new Group();
                group.setGroupID(rs.getInt("GroupID"));
                group.setGroupName(rs.getString("Groupname"));
                group.setOwnerID(rs.getInt("OwnerID"));
                group.setCreatedTime(rs.getTimestamp("Createdtime").toLocalDateTime());
                return group;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 删除群组
    public boolean deleteGroup(int groupID) {
        String sql = "DELETE FROM Groups WHERE GroupID = ?";
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

