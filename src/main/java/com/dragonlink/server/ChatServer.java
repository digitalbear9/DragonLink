package com.dragonlink.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 12345; // 服务器监听端口
    private static final int THREAD_POOL_SIZE = 50; // 线程池大小

    // 用于保存在线用户的客户端处理器
    private static final ConcurrentHashMap<String, ClientHandler> onlineClients = new ConcurrentHashMap<>();

    // 线程池管理客户端连接
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void main(String[] args) {
        System.out.println("Chat Server is starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // 等待客户端连接
                System.out.println("New client connected from: " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, onlineClients);
                threadPool.execute(clientHandler); // 将客户端处理器交给线程池管理
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown(); // 停止线程池
        }
    }
}
