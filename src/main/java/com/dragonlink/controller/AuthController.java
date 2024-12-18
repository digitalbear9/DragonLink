package com.dragonlink.controller;

import com.dragonlink.dao.UserDAO;
import com.dragonlink.service.EmailService;
import com.dragonlink.service.PasswordResetService;
import com.dragonlink.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class AuthController {
    private final UserService userService = new UserService();
    private final PasswordResetService passwordResetService = new PasswordResetService();
    private final EmailService emailService = new EmailService();

    private final UserDAO userDAO = new UserDAO();
    private final Map<String, String> verificationStore = new HashMap<>(); // 模拟验证码存储
    private String verifiedPhoneNumber = null; // 记录已验证的手机号



    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 注册结果
     */
    public String register(String username, String password, String nickname) {
        if (userService.isUserExists(username)) {
            return "用户名已存在，请选择其他用户名。";
        }

        boolean success = userService.register(username, password, nickname);
        return success ? "注册成功！" : "注册失败，请稍后重试。";
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    public String login(String username, String password) {
        boolean success = userService.login(username, password);
        return success ? "登录成功！" : "用户名或密码错误。";
    }

    /**
     * 用户登出
     *
     * @param userID 用户ID
     * @return 登出结果
     */
    public String logout(int userID) {
        boolean success = userService.updateUserStatus(userID, "offline");
        return success ? "登出成功！" : "登出失败，请稍后重试。";
    }

    /**
     * 找回密码
     *
     * @param username 用户名
     * @return 找回密码结果
     */
    public String forgotPassword(String username) {
        if (!userService.isUserExists(username)) {
            return "用户名不存在。";
        }

        // 生成密码重置令牌
        String resetToken = passwordResetService.generatePasswordResetToken(username);
        if (resetToken == null) {
            return "生成密码重置令牌失败，请稍后重试。";
        }

        // 发送密码重置邮件
        String emailSubject = "DragonLink 密码重置请求";
        String emailContent = "请点击以下链接重置您的密码：\n" +
                "http://example.com/reset-password?token=" + resetToken;

        boolean emailSent = emailService.sendSimpleEmail(username, emailSubject, emailContent);
        return emailSent ? "密码重置邮件已发送，请检查您的邮箱。" : "邮件发送失败，请稍后重试。";
    }

    /**
     * 重置密码
     *
     * @param resetToken 重置令牌
     * @param newPassword 新密码
     * @return 重置密码结果
     */
    public String resetPassword(String resetToken, String newPassword) {
        boolean success = passwordResetService.resetPassword(resetToken, newPassword);
        return success ? "密码重置成功！" : "密码重置失败，请检查重置令牌是否有效。";
    }

    public boolean sendVerificationCode(String phoneNumber) {
        // 模拟发送验证码逻辑
        System.out.println("向手机号 " + phoneNumber + " 发送验证码...");
        return true; // 假设发送成功
    }

    public String resetPassword(String phoneNumber, String verificationCode, String newPassword) {
        // 模拟验证验证码和重置密码逻辑
        if (verificationCode.equals("1234")) {
            System.out.println("手机号 " + phoneNumber + " 密码已重置为：" + newPassword);
            return "密码重置成功！";
        } else {
            return "验证码错误，请重新输入！";
        }
    }
}

//package com.dragonlink.controller;
//
//import com.dragonlink.dao.UserDAO;
//import com.dragonlink.service.EmailService;
//import com.dragonlink.service.PasswordResetService;
//import com.dragonlink.service.UserService;
//import com.dragonlink.util.EncryptionUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class AuthController {
//    private final UserService userService = new UserService();
//    private final PasswordResetService passwordResetService = new PasswordResetService();
//    private final EmailService emailService = new EmailService();
//
//    private final UserDAO userDAO = new UserDAO();
//    private final Map<String, String> verificationStore = new HashMap<>(); // 验证码存储
//    private String verifiedPhoneNumber = null; // 记录已验证的手机号
//
//    /**
//     * 用户注册
//     *
//     * @param username 用户名
//     * @param password 密码
//     * @param nickname 昵称
//     * @return 注册结果
//     */
//    public String register(String username, String password, String nickname) {
//        if (userService.isUserExists(username)) {
//            return "用户名已存在，请选择其他用户名。";
//        }
//
//        boolean success = userService.register(username, password, nickname);
//        return success ? "注册成功！" : "注册失败，请稍后重试。";
//    }
//
//    /**
//     * 用户登录
//     *
//     * @param username 用户名
//     * @param password 密码
//     * @return 登录结果
//     */
//    public String login(String username, String password) {
//        boolean success = userService.login(username, password);
//        return success ? "登录成功！" : "用户名或密码错误。";
//    }
//
//    /**
//     * 用户登出
//     *
//     * @param userID 用户ID
//     * @return 登出结果
//     */
//    public String logout(int userID) {
//        boolean success = userService.updateUserStatus(userID, "offline");
//        return success ? "登出成功！" : "登出失败，请稍后重试。";
//    }
//
//    /**
//     * 找回密码 - 发送密码重置邮件
//     *
//     * @param username 用户名
//     * @return 找回密码结果
//     */
//    public String forgotPassword(String username) {
//        if (!userService.isUserExists(username)) {
//            return "用户名不存在。";
//        }
//
//        // 生成密码重置令牌
//        String resetToken = passwordResetService.generatePasswordResetToken(username);
//        if (resetToken == null) {
//            return "生成密码重置令牌失败，请稍后重试。";
//        }
//
//        // 发送密码重置邮件
//        String emailSubject = "DragonLink 密码重置请求";
//        String emailContent = "请点击以下链接重置您的密码：\n" +
//                "http://example.com/reset-password?token=" + resetToken;
//
//        boolean emailSent = emailService.sendSimpleEmail(username, emailSubject, emailContent);
//        return emailSent ? "密码重置邮件已发送，请检查您的邮箱。" : "邮件发送失败，请稍后重试。";
//    }
//
//    /**
//     * 重置密码
//     *
//     * @param resetToken 重置令牌
//     * @param newPassword 新密码
//     * @return 重置密码结果
//     */
//    public String resetPassword(String resetToken, String newPassword) {
//        boolean success = passwordResetService.resetPassword(resetToken, newPassword);
//        return success ? "密码重置成功！" : "密码重置失败，请检查重置令牌是否有效。";
//    }
//
//    /**
//     * 发送手机验证码
//     *
//     * @param phoneNumber 手机号
//     * @return 是否发送成功
//     */
//    public boolean sendVerificationCode(String phoneNumber) {
//        String code = String.valueOf((int) (Math.random() * 9000 + 1000)); // 生成4位随机验证码
//        System.out.println("发送验证码到手机号 " + phoneNumber + "，验证码为：" + code);
//
//        // 将验证码存储到 Map 中，模拟发送和存储逻辑
//        verificationStore.put(phoneNumber, code);
//        return true; // 假设发送成功
//    }
//
//    /**
//     * 验证手机验证码
//     *
//     * @param phoneNumber    手机号
//     * @param verificationCode 用户输入的验证码
//     * @return 验证结果
//     */
//    public boolean verifyCode(String phoneNumber, String verificationCode) {
//        if (verificationStore.containsKey(phoneNumber) &&
//                verificationStore.get(phoneNumber).equals(verificationCode)) {
//            verifiedPhoneNumber = phoneNumber; // 记录已验证的手机号
//            verificationStore.remove(phoneNumber); // 验证通过后删除验证码
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 重置密码（通过手机号验证）
//     *
//     * @param phoneNumber 手机号
//     * @param newPassword 新密码
//     * @return 重置密码结果
//     */
//    public String resetPassword(String phoneNumber, String newPassword) {
//        if (verifiedPhoneNumber == null || !verifiedPhoneNumber.equals(phoneNumber)) {
//            return "请先完成验证码验证！";
//        }
//
//        // 对新密码加密
//        String hashedPassword = EncryptionUtil.hashPassword(newPassword);
//
//        // 调用 DAO 更新密码
//        boolean success = userDAO.updatePasswordByPhoneNumber(phoneNumber, hashedPassword);
//        if (success) {
//            verifiedPhoneNumber = null; // 重置验证状态
//            return "密码重置成功！";
//        } else {
//            return "密码重置失败，请稍后重试。";
//        }
//    }
//}
