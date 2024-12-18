package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    /**
     * 测试 App 的 main 方法是否能够正常启动。
     */
    @Test
    public void testAppMain() {
        try {
            App.main(new String[]{}); // 调用 App 的 main 方法
            assertTrue(true, "App 启动成功。");
        } catch (Exception e) {
            fail("App 启动失败: " + e.getMessage());
        }
    }

    /**
     * 测试 getWelcomeMessage 方法是否返回预期结果。
     */
    @Test
    public void testWelcomeMessage() {
        String expected = "Welcome to DragonLink!";
        String actual = App.getWelcomeMessage();
        assertEquals(expected, actual, "欢迎消息应与预期一致。");
    }
}
