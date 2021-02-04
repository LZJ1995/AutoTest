package com.lzj.autotestpc.servce;

import com.lzj.autotestpc.base.AppiumElementBase;
import com.lzj.autotestpc.bean.DeviceInfo;
import com.lzj.autotestpc.servce.impl.AppiumOperationServiceImp;
import com.lzj.autotestpc.tool.AppiumTool;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * 控件操作层
 */
public class AppiumOperationService implements AppiumOperationServiceImp {
    private AppiumTool tool;
    private AndroidDriver driver;
    private AppiumElementBase base;

    public  AppiumOperationService(){
        if (tool==null){
            tool = new AppiumTool();
        }else if (base==null){
            base=new AppiumElementBase();
        }
    }
   @Override
    public AndroidDriver<AndroidElement> connectDevice(ArrayList<DeviceInfo> startDeviceInfos) {
       AndroidDriver<AndroidElement> driver=null;
       if (startDeviceInfos!=null&&startDeviceInfos.size()>0){
           for (int i=0;i<startDeviceInfos.size();i++){
               DeviceInfo bean=startDeviceInfos.get(i);
//               driver=tool.connectDevice(bean.getDeviceSN(),"Android",bean.getDeviceVersion(),bean.getAppPackage(),bean.getAppActivity());
               try {
                   tool.connectDevice();
               } catch (MalformedURLException e) {
                   e.printStackTrace();
               }
           }
       }

        return driver;
   }

    @Override
    public void findByIdClick(String id, int waitTime) {
            base.waitForElementPresent(driver, By.xpath(".//*[@id='"+id+"']"),waitTime).click();

    }

    @Override
    public void findElementByXpathClick(String xPath) {
        base.waitForElementPresent(driver,By.xpath(xPath),10).click();
    }

    @Override
    public boolean assertElement(String xPath) {
        return base.isElementPresent(driver,By.xpath(xPath));
    }
}
