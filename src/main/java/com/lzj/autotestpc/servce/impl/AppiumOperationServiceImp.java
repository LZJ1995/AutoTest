package com.lzj.autotestpc.servce.impl;

import com.lzj.autotestpc.bean.StartDeviceBean;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.ArrayList;

/**
 *
 * 操作控件接口
 */
public interface AppiumOperationServiceImp {
    /**
     * 连接设备
     *
     * @return
     */
    public AndroidDriver<AndroidElement> connectDevice(ArrayList<StartDeviceBean>startDeviceInfos);

    /**
     * 根据className定位元素并点击
     * @param className
     */
    public void findByIdClick(String className, int waitTime);

    /**
     * 通过xpath定位元素并点击
     * @param xPath
     */
    public void findElementByXpathClick(String xPath);

    /**
     *元素断言
     */
    public boolean assertElement(String xPath);


}
