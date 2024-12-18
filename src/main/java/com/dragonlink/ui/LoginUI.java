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
import java.io.*;
import java.util.Properties;

public class LoginUI extends Application {
    private final AuthController authController = new AuthController();
    private final Properties properties = new Properties();

    private CheckBox rememberPasswordCheckBox;
    private CheckBox autoLoginCheckBox;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label errorMessage;

    private final String CONFIG_FILE = "config.properties";

    @Override
    public void start(Stage primaryStage) {
        loadProperties(); // 加载配置文件

        primaryStage.setTitle("DragonLink 登录");
        primaryStage.setResizable(false);

        // 设置背景图片
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("/images/background.png").toExternalForm(), 350, 500, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);

        // 输入框和标签
        usernameField = new TextField();
        usernameField.setPromptText("请输入账号");
        usernameField.setPrefWidth(240);

        passwordField = new PasswordField();
        passwordField.setPromptText("请输入密码");
        passwordField.setPrefWidth(240);

        rememberPasswordCheckBox = new CheckBox("记住密码");
        autoLoginCheckBox = new CheckBox("自动登录");

        // 自动填充已保存的信息
        if (properties.getProperty("username") != null) {
            usernameField.setText(properties.getProperty("username"));
        }
        if (properties.getProperty("password") != null) {
            passwordField.setText(properties.getProperty("password"));
            rememberPasswordCheckBox.setSelected(true);
        }
        if (Boolean.parseBoolean(properties.getProperty("autoLogin"))) {
            autoLoginCheckBox.setSelected(true);
            handleLogin(primaryStage); // 自动登录
        }

        // 登录按钮
        Button loginButton = new Button("登录");
        loginButton.setDefaultButton(true);
        loginButton.setPrefWidth(240);
        loginButton.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setOnAction(e -> handleLogin(primaryStage));

        // 错误消息提示
        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        // 注册和找回密码按钮
        Hyperlink registerLink = new Hyperlink("注册账号");
        Hyperlink forgotPasswordLink = new Hyperlink("找回密码");

        HBox linkBox = new HBox(20, registerLink, forgotPasswordLink);
        linkBox.setAlignment(Pos.CENTER);

        // 添加注册事件跳转到 RegisterUI
        registerLink.setOnAction(e -> openRegisterUI(primaryStage));

        // 找回密码链接点击事件
        forgotPasswordLink.setOnAction(e -> openForgotPasswordUI(primaryStage));

        // 布局设置
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20, 50, 20, 50));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(
                usernameField,
                passwordField,
                errorMessage,
                rememberPasswordCheckBox,
                autoLoginCheckBox,
                loginButton,
                linkBox
        );

        // 将背景图片添加到主布局
        StackPane root = new StackPane();
        root.setBackground(background);
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 350, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * 打开注册界面
     */
    private void openRegisterUI(Stage primaryStage) {
        RegisterUI registerUI = new RegisterUI();
        try {
            registerUI.start(new Stage()); // 打开新窗口显示注册界面
            primaryStage.close(); // 关闭当前登录窗口
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 打开找回密码界面
     */
    private void openForgotPasswordUI(Stage primaryStage) {
        ForgotPasswordUI forgotPasswordUI = new ForgotPasswordUI();
        try {
            forgotPasswordUI.start(new Stage()); // 打开新窗口显示找回密码界面
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 处理登录逻辑
     */
    private void handleLogin(Stage primaryStage) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("账号或密码不能为空！");
            return;
        }

        // 调用 AuthController 验证登录
        String result = authController.login(username, password);
        if (result.equals("登录成功！")) {
            errorMessage.setText("");
            saveProperties(username, password); // 保存登录状态和配置信息
            showMainUI(primaryStage); // 跳转到主界面
        } else {
            errorMessage.setText(result);
        }
    }

    /**
     * 加载配置文件
     */
    private void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("配置文件不存在，使用默认设置。");
        }
    }

    /**
     * 保存登录配置信息
     */
    private void saveProperties(String username, String password) {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.setProperty("username", username);
            if (rememberPasswordCheckBox.isSelected()) {
                properties.setProperty("password", password);
            } else {
                properties.remove("password");
            }
            properties.setProperty("autoLogin", String.valueOf(autoLoginCheckBox.isSelected()));
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到主界面
     */
    private void showMainUI(Stage primaryStage) {
        Label welcomeLabel = new Label("欢迎来到 DragonLink 主界面！");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #2E8B57;");

        VBox layout = new VBox(20, welcomeLabel);
        layout.setAlignment(Pos.CENTER);
        Scene mainScene = new Scene(layout, 400, 300);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("DragonLink 主界面");
    }



    public static void main(String[] args) {
        launch(args);
    }
}

