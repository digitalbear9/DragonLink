package com.dragonlink.controller;

import com.dragonlink.model.FileInfo;
import com.dragonlink.service.FileService;

import java.util.List;

public class FileController {
    private final FileService fileService = new FileService();

    /**
     * 上传文件
     *
     * @param senderID   发送者ID
     * @param receiverID 接收者ID（私聊时提供）
     * @param groupID    群组ID（群聊时提供）
     * @param filename   文件名
     * @param filepath   文件路径
     * @return 操作结果
     */
    public String uploadFile(int senderID, Integer receiverID, Integer groupID, String filename, String filepath) {
        boolean success = fileService.uploadFile(senderID, receiverID, groupID, filename, filepath);
        return success ? "文件上传成功！" : "文件上传失败，请稍后重试。";
    }

    /**
     * 下载文件
     *
     * @param fileID 文件ID
     * @return 文件路径，如果文件不存在则返回提示
     */
    public String downloadFile(int fileID) {
        String filePath = fileService.downloadFile(fileID);
        return filePath != null ? "文件路径：" + filePath : "文件不存在或无法下载。";
    }

    /**
     * 获取用户发送的文件列表
     *
     * @param senderID 发送者ID
     * @return 文件列表
     */
    public List<FileInfo> getFilesSentByUser(int senderID) {
        return fileService.getFilesSentByUser(senderID);
    }

    /**
     * 获取用户接收的文件列表
     *
     * @param receiverID 接收者ID
     * @return 文件列表
     */
    public List<FileInfo> getFilesReceivedByUser(int receiverID) {
        return fileService.getFilesReceivedByUser(receiverID);
    }

    /**
     * 获取群组内的文件列表
     *
     * @param groupID 群组ID
     * @return 文件列表
     */
    public List<FileInfo> getFilesInGroup(int groupID) {
        return fileService.getFilesInGroup(groupID);
    }

    /**
     * 删除文件记录
     *
     * @param fileID 文件ID
     * @return 删除结果
     */
    public String deleteFile(int fileID) {
        boolean success = fileService.deleteFile(fileID);
        return success ? "文件删除成功！" : "文件删除失败，请稍后重试。";
    }
}

