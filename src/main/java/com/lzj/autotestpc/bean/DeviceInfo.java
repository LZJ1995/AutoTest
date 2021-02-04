package com.lzj.autotestpc.bean;

import com.android.ddmlib.IDevice;

public class DeviceInfo {

    private String appPackage;
    private String appActivity;
    private String deviceVersion;
    private String deviceSN;
    private String deviceModel;
    private String deviceState;
    private IDevice device;

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
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

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public IDevice getDevice() {
        return device;
    }

    public void setDevice(IDevice device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "appPackage='" + appPackage + '\'' +
                ", appActivity='" + appActivity + '\'' +
                ", deviceVersion='" + deviceVersion + '\'' +
                ", deviceSN='" + deviceSN + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceState='" + deviceState + '\'' +
                ", device=" + device +
                '}';
    }
}
