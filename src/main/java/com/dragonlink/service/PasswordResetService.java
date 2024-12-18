package com.dragonlink.service;

import com.dragonlink.dao.PasswordResetDAO;
import com.dragonlink.dao.UserDAO;
import com.dragonlink.model.PasswordReset;
import com.dragonlink.util.EncryptionUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PasswordResetService {
    private final PasswordResetDAO passwordResetDAO = new PasswordResetDAO();
    private final UserDAO userDAO = new UserDAO();

    /**
     * 生成密码重置令牌
     *
     * @param username 用户名
     * @return 生成的重置令牌，或 null 如果用户不存在
     */
    public String generatePasswordResetToken(String username) {
        // 验证用户是否存在
        if (!userDAO.isUsernameExists(username)) {
            System.out.println("用户不存在！");
            return null;
        }

        // 生成随机重置令牌
        String resetToken = UUID.randomUUID().toString();

        // 设置令牌过期时间为1小时后
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);

        // 获取用户ID
        int userID = userDAO.getUserByUsername(username).getUserID();

        // 创建 PasswordReset 对象
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUserID(userID);
        passwordReset.setResetToken(resetToken);
        passwordReset.setExpiration(expirationTime);
        passwordReset.setIsUsed(false);

        // 插入到数据库
        if (passwordResetDAO.addPasswordReset(passwordReset)) {
            System.out.println("密码重置令牌生成成功！");
            return resetToken;
        } else {
            System.out.println("生成密码重置令牌失败！");
            return null;
        }
    }

    /**
     * 验证密码重置令牌的有效性
     *
     * @param resetToken 重置令牌
     * @return true: 有效, false: 无效或已过期
     */
    public boolean validatePasswordResetToken(String resetToken) {
        PasswordReset passwordReset = passwordResetDAO.getPasswordResetByToken(resetToken);

        if (passwordReset == null) {
            System.out.println("无效的重置令牌！");
            return false;
        }

        // 检查是否过期或已使用
        LocalDateTime expirationTime = passwordReset.getExpiration();

        if (LocalDateTime.now().isAfter(expirationTime) || passwordReset.getIsUsed()) {
            System.out.println("重置令牌已过期或已使用！");
            return false;
        }

        return true;
    }

    /**
     * 使用密码重置令牌修改用户密码
     *
     * @param resetToken  重置令牌
     * @param newPassword 新密码
     * @return true: 修改成功, false: 修改失败
     */
    public boolean resetPassword(String resetToken, String newPassword) {
        // 验证令牌
        if (!validatePasswordResetToken(resetToken)) {
            return false;
        }

        // 获取用户ID
        PasswordReset passwordReset = passwordResetDAO.getPasswordResetByToken(resetToken);
        int userID = passwordReset.getUserID();

        try {
            // 加密新密码
            String hashedPassword = EncryptionUtil.hashPassword(newPassword);

            // 更新用户密码
            boolean passwordUpdated = userDAO.updatePasswordByUserID(userID, hashedPassword);
            if (!passwordUpdated) {
                System.out.println("更新密码失败！");
                return false;
            }

            // 标记重置记录为已使用
            return passwordResetDAO.markPasswordResetAsUsed(passwordReset.getResetID());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 清理过期的密码重置记录
     *
     * @return 清理的记录数
     */
    public boolean cleanExpiredResets() {
        return passwordResetDAO.deleteExpiredResets();
    }
}
