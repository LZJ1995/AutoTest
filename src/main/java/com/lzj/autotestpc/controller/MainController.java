package com.lzj.autotestpc.controller;


import com.lzj.autotestpc.base.AutoTestContext;
import com.lzj.autotestpc.tool.ScriptTool;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainController extends Application {

    private Stage stage;
    private ScriptTool utils;

    public void start(Stage stage) throws Exception {
        Stage logStage= AutoTestContext.stageFactory.createStage(600,400,"欢迎登录自动化平台","Login.fxml");
        logStage.show();
    }
}
