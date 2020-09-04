package com.lzj.autotestpc.controller;

import com.lzj.autotestpc.main.startApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private AnchorPane Layout;
    private Stage stage;
    @FXML
    private AnchorPane title;
    @FXML
    private Label hide;
    @FXML
    private Label exit;
    @FXML
    private TreeView treeView;

    @FXML
    private ListView centenView;

    public void init() {
        stage = (Stage) Layout.getScene().getWindow();
        title.setPrefWidth(stage.getWidth());
        hide.setLayoutX(stage.getWidth() - 100);
        exit.setLayoutX(stage.getWidth() - 50);
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

    public void loadContent() {
        String[] data1 = {"北欧ins简约铁艺客厅卧室墙上装饰壁挂创意家居墙面摆件收纳壁饰", "（大、中、小）",
                "https://detail.1688.com/offer/602531155196.html?spm=a2629j.8910595.autotrace-newchannel_fx_selloffer.48.369325b2ExGoQZ&sk=consign", "12.9", "10", "25.0", "广东广州", "13"};
        ArrayList datalist = new ArrayList<String[]>();
        for (int i = 0; i < 100; i++) {
            datalist.add(data1);
        }
        ObservableList<Object> strList;
        ArrayList hbox = new ArrayList<Object>();
        for (int i = 0; i < datalist.size(); i++) {
            String[] items = (String[]) datalist.get(i);
            HBox hBox = new HBox();
            for (int x=0;x<items.length;x++) {
                TextField textview = new TextField(items[x]);
                if(x>=3){
                    textview.setPrefWidth(80);
                }else {
                    textview.setPrefWidth(199);
                }
                textview.setEditable(false);
                textview.setId("cententTextView");
                textview.setPrefHeight(30);
                textview.setAlignment(Pos.CENTER);
                hBox.setPrefWidth(1000);
                hBox.getChildren().addAll(textview);
            }
            hbox.add(hBox);
        }
        strList = FXCollections.observableArrayList(hbox);
        centenView.setItems(strList);
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
