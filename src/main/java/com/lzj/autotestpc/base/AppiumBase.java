package com.lzj.autotestpc.base;

import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.IOException;
import java.net.URL;

public class AppiumBase {

    private static AppiumDriverLocalService service;


    public static void startAppiumService () throws IOException {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }


    public static void stopAppiumService () {
        if (service != null) {
            service.stop();
        }
    }
    public URL getServerURL(){
        return service.getUrl();
    }
}
