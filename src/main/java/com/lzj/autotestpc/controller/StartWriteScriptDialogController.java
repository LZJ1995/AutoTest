package com.lzj.autotestpc.controller;

import com.lzj.autotestpc.base.AutoTestContext;
import com.lzj.autotestpc.bean.DeviceInfo;
import com.lzj.autotestpc.servce.ScriptService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartWriteScriptDialogController implements Initializable {
    @FXML
    private Label selectPackge;
    private static final Log log = LogFactory.getLog(HomePageController.class);
    private ScriptService sciptServicer;
    @FXML
    private Label startActvityShow;
    private ArrayList<String> packgeData;
    private DeviceInfo bean;
    private boolean isStratScript=false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AutoTestContext.controllers.put(this.getClass().getName(), this);
        System.out.println(this.getClass().getName());
        try {
            showPackge(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refresh(Event event){
        try {
            packgeData.clear();
            showPackge(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示package
     * @param isAll 是否所有包名
     * @throws IOException
     */
    public void showPackge(boolean isAll) throws IOException {
        sciptServicer = new ScriptService();
        bean=new DeviceInfo();
        if (isAll) {
            packgeData = sciptServicer.getAllPackges();
        } else {
            packgeData = sciptServicer.getPackge();
        }
        if (packgeData != null) {
            log.info("packgecount ：" + packgeData.size());
            for (int i = 1; i < packgeData.size(); i++) {
                if (i%2 ==1) {
                    selectPackge.setText(packgeData.get(i-1));
                    bean.setAppPackage(packgeData.get(i-1));
                }
                    startActvityShow.setText(packgeData.get(i));
                    bean.setAppActivity(packgeData.get(i));
            }
        }else {
            selectPackge.setText("没有相关包信息");
            startActvityShow.setText("没有应用启动界面");
        }
    }

    public DeviceInfo getBean() {
        return bean;
    }
    public boolean getIsStratScript() {
        return isStratScript;
    }

    @FXML
    public void startWritTest(Event event){
        isStratScript=true;
    }
}
