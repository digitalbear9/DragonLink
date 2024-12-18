package com.dragonlink.ui;

import com.dragonlink.controller.AuthController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ForgotPasswordUI extends Application {

    private final AuthController authController = new AuthController();

    private TextField phoneNumberField;
    private TextField verificationCodeField;
    private PasswordField newPasswordField;
    private Button sendCodeButton;
    private Button resetPasswordButton;
    private Label errorMessage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("找回密码 - DragonLink");
        primaryStage.setResizable(false);

        // 顶部标题
        Label titleLabel = new Label("找回密码");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        Label subtitleLabel = new Label("请输入您的手机号，获取验证码重置密码");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        VBox titleBox = new VBox(5, titleLabel, subtitleLabel);
        titleBox.setAlignment(Pos.CENTER);

        // 手机号输入框
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("请输入手机号");
        phoneNumberField.setPrefWidth(300);

        // 验证码输入框
        verificationCodeField = new TextField();
        verificationCodeField.setPromptText("请输入验证码");
        verificationCodeField.setPrefWidth(180);

        sendCodeButton = new Button("发送验证码");
        sendCodeButton.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white;");
        sendCodeButton.setOnAction(e -> handleSendCode());

        HBox verificationBox = new HBox(10, verificationCodeField, sendCodeButton);
        verificationBox.setAlignment(Pos.CENTER);

        // 新密码输入框
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("请输入新密码");
        newPasswordField.setPrefWidth(300);

        // 错误消息提示
        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        // 重置密码按钮
        resetPasswordButton = new Button("重置密码");
        resetPasswordButton.setPrefWidth(300);
        resetPasswordButton.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white; -fx-font-size: 14px;");
        resetPasswordButton.setOnAction(e -> handleResetPassword());

        // 布局设置
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(30, 50, 30, 50));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(
                titleBox,
                phoneNumberField,
                verificationBox,
                newPasswordField,
                errorMessage,
                resetPasswordButton
        );

        // 设置背景
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("/images/background.png").toExternalForm(), 400, 500, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImage));
        root.getChildren().add(mainLayout);

        // 设置场景
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 处理发送验证码逻辑
     */
    private void handleSendCode() {
        String phoneNumber = phoneNumberField.getText();

        if (phoneNumber.isEmpty()) {
            errorMessage.setText("请输入手机号！");
            return;
        }

        // 调用 AuthController 发送验证码
        boolean success = authController.sendVerificationCode(phoneNumber);
        if (success) {
            errorMessage.setStyle("-fx-text-fill: green;");
            errorMessage.setText("验证码已发送，请查看手机！");
        } else {
            errorMessage.setStyle("-fx-text-fill: red;");
            errorMessage.setText("验证码发送失败，请重试！");
        }
    }

    /**
     * 处理重置密码逻辑
     */
    private void handleResetPassword() {
        String phoneNumber = phoneNumberField.getText();
        String verificationCode = verificationCodeField.getText();
        String newPassword = newPasswordField.getText();

        if (phoneNumber.isEmpty() || verificationCode.isEmpty() || newPassword.isEmpty()) {
            errorMessage.setText("所有字段不能为空！");
            return;
        }

        // 调用 AuthController 重置密码
        String result = authController.resetPassword(phoneNumber, verificationCode, newPassword);
        if (result.equals("密码重置成功！")) {
            errorMessage.setStyle("-fx-text-fill: green;");
            errorMessage.setText("密码重置成功，请返回登录！");
        } else {
            errorMessage.setStyle("-fx-text-fill: red;");
            errorMessage.setText(result);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



//package com.dragonlink.ui;
//
//import com.dragonlink.controller.AuthController;
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//
//public class ForgotPasswordUI extends Application {
//
//    private final AuthController authController = new AuthController();
//    private TextField phoneNumberField;
//    private TextField verificationCodeField;
//    private PasswordField newPasswordField;
//    private Button sendCodeButton;
//    private Button verifyCodeButton;
//    private Button resetPasswordButton;
//    private Label errorMessage;
//
//    private boolean isVerified = false; // 标志验证码是否已通过验证
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("找回密码 - DragonLink");
//
//        // 顶部标题
//        Label titleLabel = new Label("找回密码");
//        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");
//
//        // 手机号输入框
//        phoneNumberField = new TextField();
//        phoneNumberField.setPromptText("请输入手机号");
//        phoneNumberField.setPrefWidth(300);
//
//        // 验证码输入框
//        verificationCodeField = new TextField();
//        verificationCodeField.setPromptText("请输入验证码");
//        verificationCodeField.setPrefWidth(180);
//
//        sendCodeButton = new Button("发送验证码");
//        sendCodeButton.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white;");
//        sendCodeButton.setOnAction(e -> handleSendCode());
//
//        verifyCodeButton = new Button("验证验证码");
//        verifyCodeButton.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white;");
//        verifyCodeButton.setOnAction(e -> handleVerifyCode());
//
//        HBox verificationBox = new HBox(10, verificationCodeField, sendCodeButton, verifyCodeButton);
//        verificationBox.setAlignment(Pos.CENTER);
//
//        // 新密码输入框
//        newPasswordField = new PasswordField();
//        newPasswordField.setPromptText("请输入新密码");
//        newPasswordField.setPrefWidth(300);
//        newPasswordField.setDisable(true); // 初始状态不可用
//
//        resetPasswordButton = new Button("重置密码");
//        resetPasswordButton.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white;");
//        resetPasswordButton.setDisable(true); // 初始状态不可用
//        resetPasswordButton.setOnAction(e -> handleResetPassword());
//
//        // 错误消息提示
//        errorMessage = new Label();
//        errorMessage.setStyle("-fx-text-fill: red;");
//
//        // 布局设置
//        VBox mainLayout = new VBox(15, titleLabel, phoneNumberField, verificationBox, newPasswordField,
//                resetPasswordButton, errorMessage);
//        mainLayout.setPadding(new Insets(30, 50, 30, 50));
//        mainLayout.setAlignment(Pos.CENTER);
//
//        Scene scene = new Scene(mainLayout, 400, 500);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    /**
//     * 发送验证码
//     */
//    private void handleSendCode() {
//        String phoneNumber = phoneNumberField.getText();
//        if (phoneNumber.isEmpty()) {
//            errorMessage.setText("请输入手机号！");
//            return;
//        }
//
//        boolean success = authController.sendVerificationCode(phoneNumber);
//        if (success) {
//            errorMessage.setStyle("-fx-text-fill: green;");
//            errorMessage.setText("验证码已发送！");
//        } else {
//            errorMessage.setStyle("-fx-text-fill: red;");
//            errorMessage.setText("验证码发送失败，请重试！");
//        }
//    }
//
//    /**
//     * 验证验证码
//     */
//    private void handleVerifyCode() {
//        String phoneNumber = phoneNumberField.getText(); // 获取手机号
//        String verificationCode = verificationCodeField.getText(); // 获取验证码
//
//        if (phoneNumber.isEmpty() || verificationCode.isEmpty()) {
//            errorMessage.setText("请输入手机号和验证码！");
//            return;
//        }
//
//        // 调用 AuthController 验证验证码
//        boolean isVerified = authController.verifyCode(phoneNumber, verificationCode);
//
//        if (isVerified) {
//            errorMessage.setStyle("-fx-text-fill: green;");
//            errorMessage.setText("验证码验证成功，请输入新密码。");
//            newPasswordField.setDisable(false); // 启用新密码输入框
//            resetPasswordButton.setDisable(false); // 启用重置密码按钮
//        } else {
//            errorMessage.setStyle("-fx-text-fill: red;");
//            errorMessage.setText("验证码错误，请重新输入！");
//        }
//    }
//
//    /**
//     * 重置密码
//     */
//    private void handleResetPassword() {
//        String phoneNumber = phoneNumberField.getText(); // 获取手机号
//        String newPassword = newPasswordField.getText(); // 获取新密码
//
//        if (phoneNumber.isEmpty() || newPassword.isEmpty()) {
//            errorMessage.setText("手机号和新密码不能为空！");
//            return;
//        }
//
//        // 调用 AuthController 重置密码
//        String result = authController.resetPassword(phoneNumber, newPassword);
//
//        if (result.equals("密码重置成功！")) {
//            errorMessage.setStyle("-fx-text-fill: green;");
//            errorMessage.setText("密码重置成功，请返回登录。");
//        } else {
//            errorMessage.setStyle("-fx-text-fill: red;");
//            errorMessage.setText(result);
//        }
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
