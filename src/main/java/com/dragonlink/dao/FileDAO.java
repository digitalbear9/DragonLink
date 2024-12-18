package com.dragonlink.dao;
import com.dragonlink.model.FileInfo;
import com.dragonlink.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {

    // 添加新文件记录
    public boolean insertFile(FileInfo file) {
        String sql = "INSERT INTO Files (SenderID, ReceiverID, GroupID, Filename, Filepath) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, file.getSenderID());
            if (file.getReceiverID() != null) {
                stmt.setInt(2, file.getReceiverID());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            if (file.getGroupID() != null) {
                stmt.setInt(3, file.getGroupID());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setString(4, file.getFilename());
            stmt.setString(5, file.getFilepath());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据文件ID删除文件记录
    public boolean deleteFile(int fileID) {
        String sql = "DELETE FROM Files WHERE FileID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fileID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据文件ID查询文件信息
    public FileInfo getFileByID(int fileID) {
        String sql = "SELECT * FROM Files WHERE FileID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fileID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToFileInfo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //文件名称和内容查询文件
    private List<FileInfo> getFilesByField(String fieldName, int fieldValue) {
        List<FileInfo> files = new ArrayList<>();
        String sql = "SELECT * FROM Files WHERE " + fieldName + " = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fieldValue);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileID(rs.getInt("FileID"));
                fileInfo.setSenderID(rs.getInt("SenderID"));
                fileInfo.setReceiverID((Integer) rs.getObject("ReceiverID"));
                fileInfo.setGroupID((Integer) rs.getObject("GroupID"));
                fileInfo.setFilename(rs.getString("Filename"));
                fileInfo.setFilepath(rs.getString("Filepath"));
                fileInfo.setTimestamp(rs.getTimestamp("Timestamp").toLocalDateTime());
                files.add(fileInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }
    //根据群组ID查询文件
    public List<FileInfo> getFilesByGroupID(int groupID) {
        return getFilesByField("GroupID", groupID);
    }

    // 查询某个用户发送的所有文件
    public List<FileInfo> getFilesBySenderID(int senderID) {
        String sql = "SELECT * FROM Files WHERE SenderID = ?";
        List<FileInfo> files = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, senderID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                files.add(mapResultSetToFileInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }

    // 查询发送给某个用户的文件
    public List<FileInfo> getFilesByReceiverID(int receiverID) {
        String sql = "SELECT * FROM Files WHERE ReceiverID = ?";
        List<FileInfo> files = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, receiverID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                files.add(mapResultSetToFileInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }

    // 更新文件路径
    public boolean updateFilePath(int fileID, String newFilePath) {
        String sql = "UPDATE Files SET Filepath = ? WHERE FileID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newFilePath);
            stmt.setInt(2, fileID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 映射 ResultSet 到 FileInfo 对象
    private FileInfo mapResultSetToFileInfo(ResultSet rs) throws SQLException {
        FileInfo file = new FileInfo();
        file.setFileID(rs.getInt("FileID"));
        file.setSenderID(rs.getInt("SenderID"));
        file.setReceiverID(rs.getObject("ReceiverID") != null ? rs.getInt("ReceiverID") : null);
        file.setGroupID(rs.getObject("GroupID") != null ? rs.getInt("GroupID") : null);
        file.setFilename(rs.getString("Filename"));
        file.setFilepath(rs.getString("Filepath"));
        file.setTimestamp(rs.getTimestamp("Timestamp").toLocalDateTime());
        return file;
    }
}

