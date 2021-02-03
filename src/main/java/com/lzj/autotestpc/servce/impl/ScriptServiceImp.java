package com.lzj.autotestpc.servce.impl;

import com.lzj.autotestpc.bean.StartDeviceBean;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 编写脚本接口
 */
public interface ScriptServiceImp {
    /**
     * 获取所有包名
     * @return
     * @throws IOException
     */
    public ArrayList<String> getAllPackges();
    /**
     * 获取当前包名
     * @return
     */
    public ArrayList<String> getPackge();

    /**
     * 获取用户选择运行的设备信息
     */
   public void getDeviceInfo(ArrayList<StartDeviceBean> startDeviceInfos);

    /**
     * 执行脚本
     */
    public void runSriptTest();
}
