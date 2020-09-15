package com.lzj.autotestpc.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoEditorController implements Initializable {
    @FXML
    private TextArea showRow;
    @FXML
    private TextArea demoEditor;
    private int row = 1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyevent();
    }


    public void keyevent() {
        //按键按压
        demoEditor.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            }
        });
        //滑动监听
        demoEditor.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                showRow.setScrollTop(demoEditor.getScrollTop());
            }
        });
        // KeyReleased   按键释放
        demoEditor.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                StringBuffer s = new StringBuffer(demoEditor.getText());
                setShowRow();

                if (event.getCode().toString().equals("TAB")) {
                    demoEditor.getCaretPosition();
                }
                if (event.getCode().toString().equals("ENTER")) {

                }
                if (event.getCode().toString().equals("BACK_SPACE")) {

                }
            }
        });
//        KeyTyped   按键类型
        demoEditor.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

            }
        });
    }

    /**
     * 显示行数
     */
    public void setShowRow() {
        StringBuffer rowContent = new StringBuffer();
        row = demoEditor.getText().replace("", " ").split("\n").length;
        for (int i = 0; i < row; i++) {
            rowContent.append((i + 1) + "\n");
        }
        showRow.setText(rowContent.toString());
        showRow.setScrollTop(demoEditor.getScrollTop());
    }
}
