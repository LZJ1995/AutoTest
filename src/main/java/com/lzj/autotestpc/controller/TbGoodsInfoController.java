package com.lzj.autotestpc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TbGoodsInfoController implements Initializable {

    @FXML
    private ListView centenView;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
     loadContent();
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
            for (int x = 0; x < items.length; x++) {

                if (x >= 3 || x == 1) {
                    Label label = new Label(items[x]);
                    label.setPrefWidth(90);
                    label.setPrefHeight(30);
                    label.setAlignment(Pos.CENTER);
                    hBox.setPrefWidth(1000);
                    hBox.getChildren().addAll(label);
                } else {
                    TextField textview = new TextField(items[x]);
                    textview.setPrefWidth(248.5);
                    textview.setEditable(false);
                    textview.setId("cententTextView");
                    textview.setPrefHeight(30);
                    textview.setAlignment(Pos.CENTER);
                    hBox.setPrefWidth(1000);
                    hBox.getChildren().addAll(textview);
                }
            }
            hbox.add(hBox);
        }
        strList = FXCollections.observableArrayList(hbox);
         centenView.setItems(strList);
    }

}
