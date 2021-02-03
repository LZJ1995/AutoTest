package com.lzj.autotestpc.tool;

import com.lzj.autotestpc.bean.StartDeviceBean;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Optional;

public class ScriptTool {
    private static final Log log = LogFactory.getLog(ScriptTool.class);
    private static ImageView icon = new ImageView();
    private static String buttonSelected = "CANCEL";

    public Object doGet(String url) {
//       CloseableHttpClient
        return null;
    }
    /**
     * 连接后台服务器，post请求
     *
     * @param url  请求地址
     * @param json 发送json数据
     * @return 得到服务器返回数据
     * @throws IOException
     */
    public String doPost(String url, String json) throws IOException {
        String result = null;
        String jsonDate = URLEncoder.encode(json, "UTF-8");
        //创建连接池
        PoolingHttpClientConnectionManager cManager = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        cManager.setMaxTotal(50);
        //设置每个主机地址的并发数
        cManager.setDefaultMaxPerRoute(50);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        //从连接池获取连接
        // 创建httpClient实例
        httpClient = HttpClients.custom().setConnectionManager(cManager).build();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(35000).setConnectTimeout(35000).setSocketTimeout(60000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
        httpPost.setEntity(new StringEntity(jsonDate));
        try {
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
        return result;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public ArrayList<StartDeviceBean> getAllDvice() {
        Process process = null;
        BufferedReader reader = null;
        String line = null;
        StartDeviceBean bean;
        ArrayList<String> listDevice = new ArrayList<String>();
        ArrayList<StartDeviceBean> deviceInfo = new ArrayList<StartDeviceBean>();
        //设置adb.exe存放路径，如果设置了环境变量，直接输入adb即可
        String adbPath = "adb";
        //执行adb device操作，查看pc当前连接手机或模拟器设备列表
        //注意：一定要先配置好sdk环境变量，否则无法直接执行adb命令
        try {
            process = cmdAdb(adbPath + " devices -l");
            if (process != null) {
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    if (!line.equals("List of devices attached") && !line.equals("")) {
                        listDevice.add(line);
                    }
                }
                if (!listDevice.contains("daemon started successfully")) {
                    if (listDevice != null && listDevice.size() > 0) {
                        if (!listDevice.contains("device")) {
                            for (int i = 0; i < listDevice.size(); i++) {
                                bean = new StartDeviceBean();
                                //设备序列号
                                bean.setDeviceSN(listDevice.get(i).split(" ", 2)[0]);
                                //获取手机设备连接状态，目前状态有：device(正常)、offline、unauthorized
                                String type = listDevice.get(i).split(" p")[0];
                                type = type.split(" ")[type.split(" ").length - 1];
                                bean.setDeviceType(type);
                                //获取手机类型
                                bean.setDeviceModel(listDevice.get(i).split("model:")[1].split(" ")[0]);
                                //获取手机安卓版本
                                String shell = adbPath + " -s " + bean.getDeviceSN() + " shell getprop ro.build.version.release";
                                process = cmdAdb(shell);
                                if (process != null) {
                                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                    if ((reader.readLine()) != null) {
                                        bean.setDeviceVersion(reader.readLine());
                                    }
                                }
                                deviceInfo.add(bean);
                            }
                        } else {
                            log.info("当前设备列表中，没有device类型连接设备，请检查！");
                        }
                    } else {
                        log.info("当前设备列表没有连接的设备，请检查！");
                    }
                } else {
                    getAllDvice();
                }
            } else {
                log.info("当前执行adb命令异常，请检查adb环境！");
            }
        } catch (IOException e) {
            log.error("IOException" + e.getMessage());
            return null;
        } finally {
            closeStream(reader);
            if (process != null) {
                process.destroy();
            }
        }

        return deviceInfo;
    }

    /**
     * 执行ADB命令
     *
     * @param adbCmd adb命令
     * @return
     */
    public Process cmdAdb(String adbCmd) {
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            log.info("cmdAdb() " + adbCmd);
            process = runtime.exec(adbCmd);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("cmdAdb() 参数" + adbCmd);
            return null;
        }
        return process;
    }

    public static String showConfirmDialog(Alert.AlertType type, String massge, String title) {
        Alert alert = null;
        if (type.equals(Alert.AlertType.CONFIRMATION)) {
            alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(massge);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                buttonSelected = "YES";
            } else {
                buttonSelected = "NO";
            }
        } else if (type.equals(Alert.AlertType.ERROR)) {
            alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(massge);
            alert.show();
        }

        return buttonSelected;
    }

    /**
     * 关闭流
     *
     * @param stream
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}