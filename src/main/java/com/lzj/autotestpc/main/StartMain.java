package com.lzj.autotestpc.main;

import com.lzj.autotestpc.adb.DeviceManager;
import com.lzj.autotestpc.base.AppiumBase;
import com.lzj.autotestpc.controller.MainController;
import javafx.application.Application;

import java.io.IOException;

public class StartMain {
    public static  void main(String[]args) throws IOException {
//        AppiumBase.startAppiumService();
        DeviceManager.getInstance().startDeviceManager();
        Application.launch(MainController.class,args);
    }
}
