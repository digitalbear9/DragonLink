package com.dragonlink.service;

import com.dragonlink.dao.GroupDAO;
import com.dragonlink.dao.GroupMemberDAO;
import com.dragonlink.model.Group;
import com.dragonlink.model.GroupMember;

import java.util.List;

public class GroupService {
    private final GroupDAO groupDAO = new GroupDAO();
    private final GroupMemberDAO groupMemberDAO = new GroupMemberDAO();

    /**
     * 创建群组
     *
     * @param groupName 群组名称
     * @param ownerID   群主ID
     * @return true: 创建成功, false: 创建失败
     */
    public boolean createGroup(String groupName, int ownerID) {
        return groupDAO.createGroup(groupName, ownerID);
    }

    /**
     * 获取所有群组
     *
     * @return 群组列表
     */
    public List<Group> getAllGroups() {
        return groupDAO.getAllGroups();
    }

    /**
     * 根据群组ID获取群组信息
     *
     * @param groupID 群组ID
     * @return 群组对象，或 null 如果群组不存在
     */
    public Group getGroupByID(int groupID) {
        return groupDAO.getGroupByID(groupID);
    }

    /**
     * 删除群组
     *
     * @param groupID 群组ID
     * @return true: 删除成功, false: 删除失败
     */
    public boolean deleteGroup(int groupID) {
        // 删除群组前，先删除所有群成员
        if (!groupMemberDAO.deleteAllMembersByGroupID(groupID)) {
            System.out.println("删除群组成员失败！");
            return false;
        }

        // 删除群组
        return groupDAO.deleteGroup(groupID);
    }

    /**
     * 添加群成员
     *
     * @param groupID 群组ID
     * @param userID  用户ID
     * @return true: 添加成功, false: 添加失败
     */
    public boolean addGroupMember(int groupID, int userID) {
        return groupMemberDAO.addGroupMember(groupID, userID);
    }

    /**
     * 删除群成员
     *
     * @param groupID 群组ID
     * @param userID  用户ID
     * @return true: 删除成功, false: 删除失败
     */
    public boolean removeGroupMember(int groupID, int userID) {
        return groupMemberDAO.removeGroupMember(groupID, userID);
    }

    /**
     * 获取群组所有成员
     *
     * @param groupID 群组ID
     * @return 群成员列表
     */
    public List<GroupMember> getGroupMembers(int groupID) {
        return groupMemberDAO.getGroupMembers(groupID);
    }

    /**
     * 检查用户是否在群组中
     *
     * @param groupID 群组ID
     * @param userID  用户ID
     * @return true: 用户在群组中, false: 用户不在群组中
     */
    public boolean isUserInGroup(int groupID, int userID) {
        List<GroupMember> members = groupMemberDAO.getGroupMembers(groupID);
        return members.stream().anyMatch(member -> member.getUserID() == userID);
    }
}

