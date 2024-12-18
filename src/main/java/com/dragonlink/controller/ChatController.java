package com.dragonlink.controller;

import com.dragonlink.model.Message;
import com.dragonlink.service.GroupService;
import com.dragonlink.service.MessageService;

import java.util.List;

public class ChatController {
    private final MessageService messageService = new MessageService();
    private final GroupService groupService = new GroupService();

    /**
     * 发送私聊消息
     *
     * @param senderID   发送者ID
     * @param receiverID 接收者ID
     * @param content    消息内容
     * @return 发送结果
     */
    public String sendPrivateMessage(int senderID, int receiverID, String content) {
        boolean success = messageService.sendPrivateMessage(senderID, receiverID, content);
        return success ? "私聊消息发送成功！" : "私聊消息发送失败，请稍后重试。";
    }

    /**
     * 发送群聊消息
     *
     * @param senderID 发送者ID
     * @param groupID  群组ID
     * @param content  消息内容
     * @return 发送结果
     */
    public String sendGroupMessage(int senderID, int groupID, String content) {
        if (!groupService.isUserInGroup(groupID, senderID)) {
            return "发送失败，您不在该群组中。";
        }

        boolean success = messageService.sendGroupMessage(senderID, groupID, content);
        return success ? "群聊消息发送成功！" : "群聊消息发送失败，请稍后重试。";
    }

    /**
     * 获取用户接收的私聊消息
     *
     * @param receiverID 接收者ID
     * @return 消息列表
     */
    public List<Message> getPrivateMessages(int receiverID) {
        return messageService.getPrivateMessages(receiverID);
    }

    /**
     * 获取群组的消息记录
     *
     * @param groupID 群组ID
     * @return 消息列表
     */
    public List<Message> getGroupMessages(int groupID) {
        return messageService.getGroupMessages(groupID);
    }

    /**
     * 标记消息为已读
     *
     * @param messageID 消息ID
     * @return 标记结果
     */
    public String markMessageAsRead(int messageID) {
        boolean success = messageService.markMessageAsRead(messageID);
        return success ? "消息标记为已读成功！" : "标记消息失败，请稍后重试。";
    }

    /**
     * 删除消息
     *
     * @param messageID 消息ID
     * @return 删除结果
     */
    public String deleteMessage(int messageID) {
        boolean success = messageService.deleteMessage(messageID);
        return success ? "消息删除成功！" : "消息删除失败，请稍后重试。";
    }
}

