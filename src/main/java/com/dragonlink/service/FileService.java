package com.dragonlink.service;

import com.dragonlink.dao.FileDAO;
import com.dragonlink.model.FileInfo;

import java.util.List;

public class FileService {
    private final FileDAO fileDAO = new FileDAO();

    /**
     * 上传文件记录到数据库
     *
     * @param senderID   发送者ID
     * @param receiverID 接收者ID（私聊时提供）
     * @param groupID    群组ID（群聊时提供）
     * @param filename   文件名
     * @param filepath   文件路径
     * @return true: 上传记录成功, false: 失败
     */
    public boolean uploadFile(int senderID, Integer receiverID, Integer groupID, String filename, String filepath) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setSenderID(senderID);
        fileInfo.setReceiverID(receiverID);
        fileInfo.setGroupID(groupID);
        fileInfo.setFilename(filename);
        fileInfo.setFilepath(filepath);

        return fileDAO.insertFile(fileInfo);
    }

    /**
     * 根据文件ID获取文件信息
     *
     * @param fileID 文件ID
     * @return 文件信息对象，如果不存在则返回 null
     */
    public FileInfo getFileByID(int fileID) {
        return fileDAO.getFileByID(fileID);
    }

    /**
     * 获取用户发送的文件列表
     *
     * @param senderID 发送者ID
     * @return 文件列表
     */
    public List<FileInfo> getFilesSentByUser(int senderID) {
        return fileDAO.getFilesBySenderID(senderID);
    }

    /**
     * 获取用户接收的文件列表
     *
     * @param receiverID 接收者ID
     * @return 文件列表
     */
    public List<FileInfo> getFilesReceivedByUser(int receiverID) {
        return fileDAO.getFilesByReceiverID(receiverID);
    }

    /**
     * 获取群组内的文件列表
     *
     * @param groupID 群组ID
     * @return 文件列表
     */
    public List<FileInfo> getFilesInGroup(int groupID) {
        return fileDAO.getFilesByGroupID(groupID);
    }

    /**
     * 删除文件记录
     *
     * @param fileID 文件ID
     * @return true: 删除成功, false: 删除失败
     */
    public boolean deleteFile(int fileID) {
        return fileDAO.deleteFile(fileID);
    }

    /**
     * 获取文件路径用于下载
     *
     * @param fileID 文件ID
     * @return 文件路径，如果文件不存在则返回 null
     */
    public String downloadFile(int fileID) {
        FileInfo fileInfo = fileDAO.getFileByID(fileID);
        return fileInfo != null ? fileInfo.getFilepath() : null;
    }
}

