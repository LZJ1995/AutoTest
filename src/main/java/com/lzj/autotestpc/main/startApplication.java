package com.lzj.autotestpc.main;


import com.lzj.autotestpc.controller.LoginController;
import com.lzj.autotestpc.tool.FXMLUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class startApplication extends Application {

    private Stage stage;
    private FXMLUtils utils;

    public startApplication() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        utils = new FXMLUtils();
        LoginController loginFxml = (LoginController) utils.replaceSceneContent(stage, "/fxml/Login.fxml", false);
        loginFxml.init();
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}
