package com.lzj.autotestpc.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.functions.ExpectedCondition;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AppiumElementBase {
    public Date data;

    /**
     * 判断元素是否存在
     * 参数： androiddriver By的对象
     * return 布尔值
     **/

    public boolean isElementPresent(AndroidDriver driver, By by) {
        try {
            if (driver == null) {
//                Log.info("driver 对象为空...请检查代码....");
            }
            driver.findElement(by);
//            Log.info("在当前页面找到元素："+by.toString());
            return true;
        } catch (NoSuchElementException e) {
            //e.printStackTrace();
//            Log.error("在当前页面找不到该元素："+by.toString());
            return false;
        }
    }

    /**
     * 等待元素出现 10s超时，找不到返回null 自定义方法等待
     * 参数： androiddriver By的对象 ，等待时间
     * 找到返回true
     **/
    public AndroidElement waitForElementPresent(AndroidDriver driver, By by, int waitSec) {
        AndroidElement webElement = null;
        for (int sec = 0; ; sec++) {
            if (sec >= waitSec) {
                try {
                    throw new NoSuchElementException("超过" + waitSec + "s元素未找到：" + by.toString());
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
                break;
            }
            if (isElementPresent(driver, by)) {
                   webElement = (AndroidElement) driver.findElement(by);
                break;
            }
//                Log.info("继续尝试查找：" + by.toString());
//                ThreadSleepMethod.threadSleep(1000);
        }
        return webElement;
    }

    /**
     * 查找元素 显性等待
     * 参数：driver 对象,等待时间,by对象
     * return  webelment对象
     **/
    public WebElement WebElementWait(AndroidDriver driver , int waittime, final By by){
        WebDriverWait wait = new WebDriverWait(driver, waittime);
        WebElement element = wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(by);
            }});
        return element;
    }

    /**
     * 滚动到指定元素位置
     * @param driver
     * @param by
     * @return
     */

    public AndroidElement scrollToElement(AndroidDriver driver,By by){
        Actions action=new Actions(driver);
        AndroidElement element= (AndroidElement) driver.findElement(by);
        action.moveToElement(element);
        return element;
    }

    /**
     * 获取当前测试时间
     * 参数：无
     * return  返回当前日期 指定格式
     **/
    private   String getTime(){
        SimpleDateFormat data = new SimpleDateFormat("yyyyMMddhhmm");
        return data.format(new Date());
    }

    /**
     * 截图（路径写死）
     * 参数：driver 对象
     * return  无
     **/
    public void Screenshot(TakesScreenshot drivername,String strName ){
        String filename=getTime()+strName + ".jpg";
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(scrFile,new File("d://FTP//AutoTest"+"\\"+filename));
        } catch (IOException e) {
//            Log.error("保存失败");
            e.printStackTrace();
        }
        finally {
//            Log.info("Screen shot finished, path in "
//                    +"d://ftp");
        }
    }

    /**
     * 截图（可自定义路径）
     * 参数：截图的图片的名字，和pathName 文件路径
     * return  无
     **/
    public void Screenshot(TakesScreenshot drivername, String pathName, String strName){
        String filename=getTime()+strName + ".jpg";
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(scrFile,new File(pathName+"\\"+filename));
        } catch (IOException e) {
//            Log.error("保存失败....");
            e.printStackTrace();
        }
        finally {
//            Log.info("Screen shot finished, path in "
//                    + pathName);
        }
    }

    /***
     *
     * 截取截图
     */

    public String TakeScreen(String picturePath,double startX,double startY,double endX,double endY){
        String takePicture=null;

        return takePicture;
    }

}





