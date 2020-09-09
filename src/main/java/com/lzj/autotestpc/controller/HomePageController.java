package com.lzj.autotestpc.controller;

import com.lzj.autotestpc.main.startApplication;
import com.lzj.autotestpc.tool.FXMLUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private AnchorPane layout;
    private Stage stage;
    @FXML
    private AnchorPane title;
    @FXML
    private AnchorPane anchorBar;
    @FXML
    private HBox hboxBar;
    @FXML
    private Label hide;
    @FXML
    private Label exit;
    @FXML
    private TreeView treeView;
    private FXMLUtils util;
    @FXML
    private VBox deviceList;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(FXMLUtils utils) {
        this.util = utils;
        showDevices();
        loadTreeMenu("");
        stage = (Stage) layout.getScene().getWindow();
        title.setPrefWidth(stage.getWidth());
        hide.setLayoutX(stage.getWidth() - 100);
        exit.setLayoutX(stage.getWidth() - 50);
        anchorBar.setPrefWidth(stage.getWidth());
        hboxBar.setPrefWidth(stage.getWidth());
    }

    public void loadTreeMenu(String data) {
        String[] data2 = {"根目录1", "根目录2", "根目录3", "根目录4", "根目录5"};
        String[] data1 = {"张三", "李四", "王五", "效力", "大脑i"};
        TreeItem<String> muneItem;
        TreeItem<String> viewItem = new TreeItem<String>("");
        ;
        int i = 0;
        for (String dataview : data2) {
            muneItem = new TreeItem<String>(dataview);
            for (String dataitem : data1) {
                muneItem.getChildren().addAll(new TreeItem<String>(dataitem));
            }
            viewItem.getChildren().addAll(muneItem);
        }
        treeView.setRoot(viewItem);
        treeView.setShowRoot(false);
    }

    public void showDevices() {
        ArrayList<String> devices = util.getAllDvice();
        HBox hBox = new HBox();
        hBox.setId("shouDevice");
        if(devices!=null&&devices.size()>1) {
            Label deviceName = new Label(devices.get(0));
            deviceName.setId("deviceName");
            if (devices.get(2).equals("device")) {
                Label devicesType = new Label("在线");
                hBox.getChildren().addAll(deviceName, devicesType);
            }
        }else {
            Label deviceError = new Label("没有设备连接！");
            deviceError.setId("deviceError");
            hBox.getChildren().addAll(deviceError);
        }
        deviceList.getChildren().addAll(hBox);
    }

    public void exit(MouseEvent event) {
        stage.close();
    }

    public void hide(MouseEvent event) {
        stage.toBack();
        stage.setIconified(true);
    }
}
