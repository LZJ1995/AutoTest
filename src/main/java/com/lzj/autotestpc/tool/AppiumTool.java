package com.lzj.autotestpc.tool;

import com.lzj.autotestpc.base.AppiumBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;

public class AppiumTool extends AppiumBase {
    private AndroidDriver<AndroidElement> driver;

//Appium连接
    public AndroidDriver<AndroidElement>  connectDevice(String deviceName, String platformName, String platformVersion, String appPackage, String appActivity) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
       /* //系统版本
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,platformVersion);
        //设备名字
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,deviceName);
        //操作系统配置
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,platformName);
        //启动app activity
        desiredCapabilities.setCapability("appActivity",appActivity);
        //启动app 包名
        desiredCapabilities.setCapability("appPackage",appPackage);
        driver =new AndroidDriver<AndroidElement>(getServiceUrl(),desiredCapabilities);*/
        //系统版本
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"9");
        //设备名字
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"1234567");
        //操作系统配置
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        //启动app activity
        desiredCapabilities.setCapability("appActivity",".MainActivity");
        //启动app 包名
        desiredCapabilities.setCapability("appPackage","com.ts.app.settings");
        driver =new AndroidDriver<AndroidElement>(getServerURL(),desiredCapabilities);
        System.out.println(driver);
       return driver;
    }
    //关闭Appium连接
    public void appiumQuit(){
        driver.quit();
    }
    public void connectDevice() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //系统版本
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"9");
        //设备名字
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"1234567");
        //操作系统配置
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        //启动app activity
        desiredCapabilities.setCapability("appActivity",".MainActivity");
        //启动app 包名
        desiredCapabilities.setCapability("appPackage","com.ts.app.settings");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");

        driver =new AndroidDriver(getServerURL(),desiredCapabilities);
        System.out.println(driver);
    }
}
