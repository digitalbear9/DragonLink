package com.dragonlink.server;

import com.dragonlink.dao.UserDAO;
import com.dragonlink.util.GsonUtil;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ConcurrentHashMap<String, ClientHandler> onlineClients;
    private BufferedReader reader;
    private PrintWriter writer;

    private String username; // 当前连接的用户名
    private final UserDAO userDAO = new UserDAO(); // DAO，用于用户验证和数据处理

    public ClientHandler(Socket clientSocket, ConcurrentHashMap<String, ClientHandler> onlineClients) {
        this.clientSocket = clientSocket;
        this.onlineClients = onlineClients;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);

            // 登录处理
            if (!handleLogin()) {
                return; // 登录失败时，直接结束连接
            }

            // 监听客户端消息
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from " + username + ": " + message);
                handleMessage(message); // 处理消息
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            disconnect(); // 客户端下线处理
        }
    }

    private boolean handleLogin() throws IOException {
        writer.println("Welcome to ChatServer! Please login.");
        String loginRequest = reader.readLine(); // 假设客户端发送 JSON 格式的登录信息

        JsonObject loginJson = GsonUtil.getGson().fromJson(loginRequest, JsonObject.class);
        String type = loginJson.get("type").getAsString();

        if ("login".equals(type)) {
            String username = loginJson.get("username").getAsString();
            String password = loginJson.get("password").getAsString();

            // 验证用户身份
            if (userDAO.login(username, password)) {
                this.username = username;
                onlineClients.put(username, this); // 添加到在线用户列表
                writer.println("Login successful!");
                broadcastSystemMessage(username + " has joined the chat.");
                return true;
            } else {
                writer.println("Invalid username or password. Connection closed.");
                clientSocket.close();
                return false;
            }
        } else {
            writer.println("Invalid request type. Connection closed.");
            clientSocket.close();
            return false;
        }
    }

    private void handleMessage(String message) {
        JsonObject messageJson = GsonUtil.getGson().fromJson(message, JsonObject.class);
        String type = messageJson.get("type").getAsString();

        switch (type) {
            case "private":
                handlePrivateMessage(messageJson);
                break;
            case "group":
                handleGroupMessage(messageJson);
                break;
            case "logout":
                disconnect();
                break;
            default:
                writer.println("Unknown message type.");
        }
    }

    private void handlePrivateMessage(JsonObject messageJson) {
        String targetUsername = messageJson.get("target").getAsString();
        String content = messageJson.get("content").getAsString();

        ClientHandler targetHandler = onlineClients.get(targetUsername);
        if (targetHandler != null) {
            JsonObject response = new JsonObject();
            response.addProperty("type", "private");
            response.addProperty("sender", username);
            response.addProperty("content", content);
            targetHandler.writer.println(response.toString());
        } else {
            writer.println("User " + targetUsername + " is not online.");
        }
    }

    private void handleGroupMessage(JsonObject messageJson) {
        String groupName = messageJson.get("group").getAsString();
        String content = messageJson.get("content").getAsString();

        // TODO: Implement group message handling
        broadcastSystemMessage("Group chat feature is not yet implemented.");
    }

    private void broadcastSystemMessage(String message) {
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("type", "system");
        systemMessage.addProperty("content", message);

        for (ClientHandler handler : onlineClients.values()) {
            handler.writer.println(systemMessage.toString());
        }
    }

    private void disconnect() {
        if (username != null) {
            onlineClients.remove(username);
            broadcastSystemMessage(username + " has left the chat.");
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

