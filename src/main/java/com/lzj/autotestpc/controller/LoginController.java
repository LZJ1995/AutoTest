package com.lzj.autotestpc.controller;


import com.lzj.autotestpc.tool.FXMLUtils;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

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
    private HomePageController home;
    private FXMLUtils utils;

    public void init() {
        stage = (Stage) loginLayout.getScene().getWindow();
        utils = new FXMLUtils();
    }

    public void submintLogin(ActionEvent event) throws Exception {
        HomePageController controller = (HomePageController) utils.replaceSceneContent(stage, "/fxml/homePage.fxml", true);
        controller.init();
        controller.loadTreeMenu("");
        controller.loadContent();
    }

    public void exit(MouseEvent event) {
        stage.close();
    }

    public void hide(MouseEvent event) {
        stage.toBack();
        stage.setIconified(true);
    }

    public void initialize(URL location, ResourceBundle resources) {
    }
}
