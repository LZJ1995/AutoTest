package com.lzj.autotestpc.controller;


import com.lzj.autotestpc.base.AutoTestContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private AutoTestContext context;

    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField pfPassWord;
    @FXML
    private Button bt_Login;
    @FXML
    private AnchorPane loginLayout;
    private Stage stage;
    private String userName;
    private String password;

    public void initialize(URL location, ResourceBundle resources) {
        AutoTestContext.controllers.put(this.getClass().getName(), this);
    }

    public void submintLogin(ActionEvent event) throws Exception {
        Stage pageStage = AutoTestContext.stageFactory.createStage(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100, Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100, "欢迎登录自动化平台", "homePage.fxml");
        AutoTestContext.stageManagerTool.jump("Login","homePage");
        pageStage.show();


    }
}
