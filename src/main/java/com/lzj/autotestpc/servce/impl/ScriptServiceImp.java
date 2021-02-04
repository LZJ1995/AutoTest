package com.lzj.autotestpc.servce.impl;

import com.android.ddmlib.IDevice;
import com.lzj.autotestpc.bean.DeviceInfo;

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
     * 执行脚本
     */
    public void runSriptTest();
}
