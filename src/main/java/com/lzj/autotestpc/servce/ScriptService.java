package com.lzj.autotestpc.servce;

import com.lzj.autotestpc.bean.StartDeviceBean;
import com.lzj.autotestpc.controller.HomePageController;
import com.lzj.autotestpc.servce.impl.ScriptServiceImp;
import com.lzj.autotestpc.tool.ScriptTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 脚本service
 */
public class ScriptService implements ScriptServiceImp {
    private static final Log log = LogFactory.getLog(HomePageController.class);
    private ScriptTool util;
    private HomePageController homePageController;

    /***
     * 获取所有包名
     * @return
     */
    @Override
    public ArrayList<String> getAllPackges()  {
        util = new ScriptTool();
        Process process = util.cmdAdb("adb shell pm list packages");
        BufferedReader read = null;
        String lin = null;
        ArrayList<String> packgeData = null;
        if (process != null) {
            packgeData = new ArrayList<>();
            read = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                lin = read.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (lin != null) {
                packgeData.add(lin);
                log.info("packge :" + lin);
            }
        }
        return packgeData;
    }

    /***
     * 获取当前运行包名
     * @return
     */
    @Override
    public ArrayList<String> getPackge() {
        util = new ScriptTool();
        Process process = util.cmdAdb("cmd /c adb shell dumpsys window | findstr mCurrentFocus");
        BufferedReader read = null;
        String lin = null;
        ArrayList<String> appInfo = null;
        if (process != null) {
            appInfo = new ArrayList<>();
            read = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                lin = read.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (lin != null) {
                System.out.println(lin);
                lin=lin.trim();
                String[] splitString=lin.split(" ",0);
                splitString=splitString[2].split("/",0);
                appInfo.add(splitString[0]);
                splitString=splitString[1].split("}",0);
                appInfo.add(splitString[0]);

            }
                log.info("packge :" + lin);
        }
        return appInfo;
    }

    @Override
    public void getDeviceInfo(ArrayList<StartDeviceBean> startDeviceInfos) {

    }


    @Override
    public void runSriptTest() {

    }

}
