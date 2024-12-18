package com.dragonlink.service;

import com.dragonlink.dao.UserDAO;
import com.dragonlink.model.User;
import com.dragonlink.util.EncryptionUtil;

import java.security.NoSuchAlgorithmException;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    /**
     * 用户注册
     *
     * @param username  用户名
     * @param password  原始密码
     * @param nickname  昵称
     * @return true: 注册成功, false: 注册失败
     */
    public boolean register(String username, String password, String nickname) {
        try {
            // 加密用户密码
            String hashedPassword = EncryptionUtil.hashPassword(password);

            // 创建 User 对象
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(hashedPassword);
            user.setNickname(nickname);

            // 插入到数据库
            return userDAO.addUser(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户登录
     *
     * @param username  用户名
     * @param password  原始密码
     * @return true: 登录成功, false: 登录失败
     */
    public boolean login(String username, String password) {
        return userDAO.login(username, password);
    }

    /**
     * 更新用户状态（在线/离线）
     *
     * @param userID  用户ID
     * @param status  用户状态（如 "online" 或 "offline"）
     * @return true: 更新成功, false: 更新失败
     */
    public boolean updateUserStatus(int userID, String status) {
        return userDAO.updateUserStatus(userID, status);
    }

    /**
     * 根据用户名查找用户信息
     *
     * @param username 用户名
     * @return User 对象，或 null 如果用户不存在
     */
    public User findUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    /**
     * 修改用户密码
     *
     * @param username    用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return true: 修改成功, false: 修改失败
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // 验证旧密码是否正确
        if (!userDAO.login(username, oldPassword)) {
            System.out.println("旧密码错误，无法修改密码！");
            return false;
        }

        try {
            // 加密新密码
            String newHashedPassword = EncryptionUtil.hashPassword(newPassword);

            // 更新数据库中的密码
            return userDAO.updatePassword(username, newHashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除用户
     *
     * @param username 用户名
     * @return true: 删除成功, false: 删除失败
     */
    public boolean deleteUser(String username) {
        return userDAO.deleteUser(username);
    }

    /**
     * 检查用户是否存在
     *
     * @param username 用户名
     * @return true: 用户存在, false: 用户不存在
     */
    public boolean isUserExists(String username) {
        return userDAO.getUserByUsername(username) != null;
    }
}
