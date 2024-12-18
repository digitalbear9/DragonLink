package com.dragonlink.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ChatUI extends Application {

    private VBox chatBox; // 聊天消息容器
    private ScrollPane chatScrollPane;
    private TextField messageInputField;
    private Button sendButton, fileButton;
    private ListView<String> memberListView; // 群成员列表
    private Label announcementLabel; // 群公告
    private String currentUser = "我"; // 当前登录用户
    private String chatTarget = "2023级软工3班"; // 聊天对象名称

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(chatTarget + " - DragonLink 聊天");

        // 顶部群公告栏和聊天标题
        VBox titleArea = createTitleArea();

        // 聊天窗口
        chatBox = new VBox(10);
        chatBox.setPadding(new Insets(10));
        chatBox.setStyle("-fx-background-color: #F9F9F9;");

        chatScrollPane = new ScrollPane(chatBox);
        chatScrollPane.setFitToWidth(true);
        chatScrollPane.setStyle("-fx-background: #F9F9F9;");

        // 消息输入框和按钮区域
        HBox inputArea = createInputArea(primaryStage);

        // 群成员列表
        memberListView = createMemberListView();

        // 主界面布局
        BorderPane root = new BorderPane();
        root.setTop(titleArea);
        root.setCenter(chatScrollPane);
        root.setBottom(inputArea);
        root.setRight(memberListView);

        // 设置场景
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建顶部区域，包含群公告和标题
     */
    private VBox createTitleArea() {
        Label chatTitle = new Label(chatTarget);
        chatTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        announcementLabel = new Label("群公告：今晚8点实验报告讨论，请准时参加！");
        announcementLabel.setStyle("-fx-background-color: #FFD700; -fx-padding: 5;");

        Button editAnnouncementButton = new Button("修改公告");
        editAnnouncementButton.setStyle("-fx-background-color: #87CEFA; -fx-text-fill: white;");
        editAnnouncementButton.setOnAction(e -> editAnnouncement());

        HBox titleBar = new HBox(10, chatTitle, editAnnouncementButton);
        titleBar.setPadding(new Insets(10));
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setStyle("-fx-background-color: #87CEFA;");

        VBox titleArea = new VBox(announcementLabel, titleBar);
        titleArea.setSpacing(5);
        return titleArea;
    }

    /**
     * 修改群公告
     */
    private void editAnnouncement() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("修改群公告");
        dialog.setHeaderText("请输入新的群公告内容：");
        dialog.setContentText("公告：");

        dialog.showAndWait().ifPresent(newAnnouncement -> {
            announcementLabel.setText("群公告：" + newAnnouncement);
        });
    }

    /**
     * 创建消息输入区域
     */
    private HBox createInputArea(Stage stage) {
        messageInputField = new TextField();
        messageInputField.setPromptText("输入消息...");
        messageInputField.setPrefHeight(40);

        sendButton = new Button("发送(S)");
        sendButton.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white;");
        sendButton.setPrefHeight(40);
        sendButton.setOnAction(e -> sendMessage());

        fileButton = new Button("发送文件");
        fileButton.setStyle("-fx-background-color: #32CD32; -fx-text-fill: white;");
        fileButton.setPrefHeight(40);
        fileButton.setOnAction(e -> sendFile(stage));

        HBox inputArea = new HBox(10, messageInputField, sendButton, fileButton);
        inputArea.setPadding(new Insets(10));
        inputArea.setAlignment(Pos.CENTER);
        inputArea.setStyle("-fx-background-color: #F0F8FF;");

        return inputArea;
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        String messageText = messageInputField.getText().trim();
        if (!messageText.isEmpty()) {
            addMessage(currentUser, messageText, true);
            messageInputField.clear();
            simulateReply(); // 模拟回复
        }
    }

    /**
     * 发送文件
     */
    private void sendFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String fileInfo = "文件: " + file.getName() + " (" + file.length() / 1024 + " KB)";
            addFileMessage(fileInfo);
        }
    }

    /**
     * 添加文件消息带传输进度
     */
    private void addFileMessage(String fileInfo) {
        Label fileLabel = new Label(fileInfo);
        fileLabel.setStyle("-fx-font-size: 14px;");

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);

        HBox fileMessage = new HBox(10, fileLabel, progressBar);
        fileMessage.setAlignment(Pos.CENTER_LEFT);
        chatBox.getChildren().add(fileMessage);

        // 模拟文件传输进度
        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                final double progress = i / 100.0;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.setProgress(progress);
            }
        }).start();
    }

    /**
     * 添加消息到聊天窗口
     */
    private void addMessage(String sender, String messageText, boolean isCurrentUser) {
        HBox messageBox = new HBox(10);
        messageBox.setPadding(new Insets(5));

        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/images/avatar.png")));
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);

        Text messageContent = new Text(messageText);
        messageContent.setStyle("-fx-font-size: 14px;");
        Label timestamp = new Label(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        timestamp.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");

        VBox messageContainer = new VBox(messageContent, timestamp);
        messageContainer.setSpacing(5);

        if (isCurrentUser) {
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            messageContainer.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white; -fx-padding: 10;");
            messageBox.getChildren().addAll(messageContainer, avatar);
        } else {
            messageBox.setAlignment(Pos.CENTER_LEFT);
            messageContainer.setStyle("-fx-background-color: #E0E0E0; -fx-padding: 10;");
            messageBox.getChildren().addAll(avatar, messageContainer);
        }

        chatBox.getChildren().add(messageBox);
        chatScrollPane.setVvalue(1.0);
    }

    /**
     * 创建群成员列表
     */
    private ListView<String> createMemberListView() {
        ListView<String> listView = new ListView<>();
        listView.setPrefWidth(200);
        listView.setStyle("-fx-background-color: #F0F8FF;");
        listView.getItems().addAll(Arrays.asList("班长 江钰镕", "学习委员 蔡旭文", "心理委员 吴嘉胜", "自律委员 郭佳恩"));
        return listView;
    }

    private void simulateReply() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                addMessage("105 黄福涛", "收到！", false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

