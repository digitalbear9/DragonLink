package org.example;

import com.dragonlink.ui.LoginUI;
import javafx.application.Application;

/**
 * 程序的主入口类
 */
public class App {
    /**
     * 主方法：程序入口，启动 LoginUI 界面
     */
    public static void main(String[] args) {
        System.out.println("DragonLink 应用程序启动中...");
        Application.launch(LoginUI.class, args); // 启动登录界面
    }

    /**
     * 返回欢迎消息
     * @return 欢迎消息字符串
     */
    public static String getWelcomeMessage() {
        return "Welcome to DragonLink!";
    }
}

