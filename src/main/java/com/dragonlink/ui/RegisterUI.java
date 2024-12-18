package com.dragonlink.ui;

import com.dragonlink.controller.AuthController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegisterUI extends Application {

    private final AuthController authController = new AuthController();
    private TextField nicknameField;
    private PasswordField passwordField;
    private TextField phoneNumberField;
    private CheckBox termsCheckBox;
    private Label errorMessage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("注册 - DragonLink");

        // 顶部标题
        Label titleLabel = new Label("欢迎注册 DragonLink");
        titleLabel.setFont(new Font("Arial", 24));

        Label subtitleLabel = new Label("每天，乐在沟通。");
        subtitleLabel.setFont(new Font("Arial", 14));
        subtitleLabel.setStyle("-fx-text-fill: gray;");

        VBox titleBox = new VBox(5, titleLabel, subtitleLabel);
        titleBox.setAlignment(Pos.CENTER);

        // 输入框
        nicknameField = new TextField();
        nicknameField.setPromptText("昵称");
        nicknameField.setPrefWidth(300);

        passwordField = new PasswordField();
        passwordField.setPromptText("密码");
        passwordField.setPrefWidth(300);

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("手机号");
        phoneNumberField.setPrefWidth(300);

        termsCheckBox = new CheckBox("我已阅读并同意 服务协议 和 隐私保护指引");

        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button registerButton = new Button("立即注册");
        registerButton.setPrefWidth(300);
        registerButton.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white; -fx-font-size: 14px;");
        registerButton.setOnAction(e -> handleRegister(primaryStage));

        VBox mainLayout = new VBox(15, titleBox, nicknameField, passwordField, phoneNumberField,
                termsCheckBox, errorMessage, registerButton);
        mainLayout.setPadding(new Insets(30, 50, 30, 50));
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 处理注册逻辑
     */
    private void handleRegister(Stage primaryStage) {
        String nickname = nicknameField.getText();
        String password = passwordField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (nickname.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            errorMessage.setText("昵称、密码和手机号不能为空！");
            return;
        }
        if (!termsCheckBox.isSelected()) {
            errorMessage.setText("请同意服务协议和隐私保护指引。");
            return;
        }

        String result = authController.register(phoneNumber, password, nickname);
        if (result.equals("注册成功！")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "注册成功！请返回登录页面。", ButtonType.OK);
            alert.showAndWait();
            openLoginUI(primaryStage); // 注册成功后返回登录页面
        } else {
            errorMessage.setText(result);
        }
    }

    /**
     * 打开登录界面
     */
    private void openLoginUI(Stage primaryStage) {
        LoginUI loginUI = new LoginUI();
        try {
            loginUI.start(new Stage()); // 打开新的登录界面
            primaryStage.close(); // 关闭当前注册窗口
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
