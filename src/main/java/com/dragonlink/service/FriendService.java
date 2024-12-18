package com.dragonlink.service;

import com.dragonlink.dao.FriendDAO;
import com.dragonlink.model.Friend;

import java.util.List;

public class FriendService {
    private final FriendDAO friendDAO = new FriendDAO();

    /**
     * 发送好友请求
     *
     * @param userID       发送请求的用户ID
     * @param friendUserID 接收请求的用户ID
     * @return true: 请求发送成功, false: 请求发送失败
     */
    public boolean sendFriendRequest(int userID, int friendUserID) {
        // 检查是否已经是好友
        if (friendDAO.isFriend(userID, friendUserID)) {
            System.out.println("已经是好友，无法重复添加！");
            return false;
        }
        // 发送好友请求，状态设置为 "pending"
        return friendDAO.addFriend(userID, friendUserID, "pending");
    }

    /**
     * 接受好友请求
     *
     * @param userID       接收请求的用户ID
     * @param friendUserID 请求发起方的用户ID
     * @return true: 接受成功, false: 接受失败
     */
    public boolean acceptFriendRequest(int userID, int friendUserID) {
        // 更新好友状态为 "accepted"
        boolean updated = friendDAO.updateFriendStatus(friendUserID, userID, "accepted");
        if (updated) {
            // 双向添加好友关系
            return friendDAO.addFriend(userID, friendUserID, "accepted");
        }
        return false;
    }

    /**
     * 拒绝好友请求
     *
     * @param userID       接收请求的用户ID
     * @param friendUserID 请求发起方的用户ID
     * @return true: 拒绝成功, false: 拒绝失败
     */
    public boolean rejectFriendRequest(int userID, int friendUserID) {
        // 删除请求记录
        return friendDAO.deleteFriend(friendUserID, userID);
    }

    /**
     * 删除好友
     *
     * @param userID       用户ID
     * @param friendUserID 好友的用户ID
     * @return true: 删除成功, false: 删除失败
     */
    public boolean removeFriend(int userID, int friendUserID) {
        // 双向删除好友关系
        boolean deleted = friendDAO.deleteFriend(userID, friendUserID);
        if (deleted) {
            return friendDAO.deleteFriend(friendUserID, userID);
        }
        return false;
    }

    /**
     * 获取用户的好友列表
     *
     * @param userID 用户ID
     * @return 好友列表
     */
    public List<Friend> getFriendList(int userID) {
        return friendDAO.getFriendsByUserID(userID);
    }

    /**
     * 检查两个用户是否是好友
     *
     * @param userID       用户ID
     * @param friendUserID 好友的用户ID
     * @return true: 是好友, false: 不是好友
     */
    public boolean isFriend(int userID, int friendUserID) {
        return friendDAO.isFriend(userID, friendUserID);
    }
}

