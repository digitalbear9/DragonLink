package com.dragonlink.service;

import com.dragonlink.dao.MessageDAO;
import com.dragonlink.dao.OfflineMessageDAO;
import com.dragonlink.model.Message;

import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO = new MessageDAO();
    private final OfflineMessageDAO offlineMessageDAO = new OfflineMessageDAO();

    /**
     * 发送消息（单聊）
     *
     * @param senderID   发送者ID
     * @param receiverID 接收者ID
     * @param content    消息内容
     * @return true: 发送成功, false: 发送失败
     */
    public boolean sendPrivateMessage(int senderID, int receiverID, String content) {
        Message message = new Message();
        message.setSenderID(senderID);
        message.setReceiverID(receiverID);
        message.setContent(content);
        message.setIsRead(false); // 默认消息未读

        boolean success = messageDAO.addMessage(message);

        // 如果接收者不在线，将消息存储为离线消息
        if (success && !isUserOnline(receiverID)) {
            offlineMessageDAO.addOfflineMessage(receiverID, message.getMessageID());
        }
        return success;
    }

    /**
     * 发送群消息
     *
     * @param senderID 发送者ID
     * @param groupID  群组ID
     * @param content  消息内容
     * @return true: 发送成功, false: 发送失败
     */
    public boolean sendGroupMessage(int senderID, int groupID, String content) {
        Message message = new Message();
        message.setSenderID(senderID);
        message.setGroupID(groupID);
        message.setContent(content);
        message.setIsRead(false); // 群消息默认未读

        return messageDAO.addMessage(message);
    }

    /**
     * 获取用户接收到的私聊消息
     *
     * @param receiverID 接收者ID
     * @return 消息列表
     */
    public List<Message> getPrivateMessages(int receiverID) {
        return messageDAO.getMessagesByReceiverID(receiverID);
    }

    /**
     * 获取群组的消息
     *
     * @param groupID 群组ID
     * @return 消息列表
     */
    public List<Message> getGroupMessages(int groupID) {
        return messageDAO.getMessagesByGroupID(groupID);
    }

    /**
     * 标记消息为已读
     *
     * @param messageID 消息ID
     * @return true: 标记成功, false: 标记失败
     */
    public boolean markMessageAsRead(int messageID) {
        return messageDAO.markMessageAsRead(messageID);
    }

    /**
     * 删除消息
     *
     * @param messageID 消息ID
     * @return true: 删除成功, false: 删除失败
     */
    public boolean deleteMessage(int messageID) {
        return messageDAO.deleteMessage(messageID);
    }

    /**
     * 检查用户是否在线
     *
     * @param userID 用户ID
     * @return true: 在线, false: 离线
     */
    private boolean isUserOnline(int userID) {
        // TODO: 此处需要实现用户在线状态的实际检查逻辑
        // 假设从某个在线用户列表中检查
        return false; // 示例返回：用户默认离线
    }
}

