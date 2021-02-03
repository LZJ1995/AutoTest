package com.lzj.autotestpc.bean;

/**
 * 脚本生成属性封装类
 */
public class ScriptGenerationBean {

    //设备名称
    private String deviceName;
    //设备名称
    private String platformName;
    //设备版本
    private String platformVersion;
    //启动应用包名
    private String appPackage;
    //启动应用Activity
    private String appActivity;
    //脚本名称
    private String scriptName;
    //脚本ID(用例ID)
    private String scriptId;
    //查找控件条件
    private String idOrText;
    //查找控件最大时间
    private int outTime;
    //方法名
    private String methodName;


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppActivity() {
        return appActivity;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public String getIdOrText() {
        return idOrText;
    }

    public void setIdOrText(String idOrText) {
        this.idOrText = idOrText;
    }

    public int getOutTime() {
        return outTime;
    }

    public void setOutTime(int outTime) {
        this.outTime = outTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
