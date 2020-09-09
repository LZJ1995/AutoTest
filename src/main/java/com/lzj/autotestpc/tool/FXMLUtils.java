package com.lzj.autotestpc.tool;

import com.lzj.autotestpc.controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FXMLUtils {


    /**
     * 读取FXML文件并返回Controlle
     *
     * @param stage
     * @param fxml
     * @param isFull
     * @return
     * @throws Exception
     */
    public Initializable replaceSceneContent(Stage stage, String fxml, boolean isFull) throws Exception {
        Scene scene;
        FXMLLoader loader = new FXMLLoader();
        InputStream in = this.getClass().getResourceAsStream(fxml);
        loader.setLocation(this.getClass().getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

        if (isFull) {
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            scene = new Scene(page, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
            stage.close();
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());
            stage.show();
        } else {
            scene = new Scene(page);
        }
        stage.setScene(scene);
        return (Initializable) loader.getController();
    }

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

    public ArrayList<String> getAllDvice() {

        ArrayList<String> listDevice = null;
        ArrayList<String> deviceInfo = null;
        Process process;
        BufferedReader reader;
        String line = null;
        String deviceType;


        listDevice = new ArrayList<String>();
        deviceInfo=new ArrayList<String>();
        //设置adb.exe存放路径，如果设置了环境变量，直接输入adb即可
        String adbPath = "adb";
        //执行adb device操作，查看pc当前连接手机或模拟器设备列表
        //注意：一定要先配置好sdk环境变量，否则无法直接执行adb命令
        try {
            process = cmdAdb(adbPath + " devices -l");
            if (process != null) {
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 1) {
                        listDevice.add(line);
                    }
                }
                if (!listDevice.contains("daemon started successfully")) {
                    if (listDevice != null && listDevice.size() > 1) {
                        if (!listDevice.contains("device")) {
                            for (int i = 0; i < listDevice.size(); i++) {
                                for (int j = 0; j < listDevice.get(i).split(" ").length; j++) {
                                    //获取手机设备连接状态，目前状态有：device(正常)、offline、unauthorized
                                    deviceType = listDevice.get(i).split(" ")[j];
                                    //判断当前设备状态是否正常
                                    if (deviceType.equals("device")||deviceType.equals("offline")) {
                                        //获取设备序列号
                                        String device_sn = listDevice.get(i).split(" ")[0];
                                        System.out.println(listDevice.size()+"!!!!!"+listDevice.get(i));
                                        System.out.println(i+"设备序列号："+device_sn);
                                        //获取设备名称
                                        String device_name = listDevice.get(i).split("device:")[1].split(" ")[0];
                                        System.out.println("当前设备名称为:" + device_name);
                                        String shell = adbPath + " -s " + device_sn + " shell getprop ro.build.version.release";
                                        process = cmdAdb(shell);
                                        if (process != null) {
                                            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                            line = null;
                                            while ((line = reader.readLine())!= null) {
                                                if (!line.equals("")) {
                                                    deviceInfo.add(device_name);
                                                    deviceInfo.add("Andorid:" + line);
                                                    System.out.println(line);
                                                }
                                            }
                                        }
                                        deviceInfo.add(deviceType);
                                        deviceInfo.add(device_sn);
                                    }
                                }
                            }
                        } else {
                            System.out.println("当前设备列表中，没有device类型连接设备，请检查！");
                        }
                    } else {
                        System.out.println("当前设备列表没有连接的设备，请检查！");
                    }
                } else {
                    getAllDvice();
                }
            } else {
                System.out.println("当前执行adb命令异常，请检查adb环境！");
            }
        } catch (IOException e) {
            System.err.println("IOException" + e.getMessage());
            return null;
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
            System.out.println(adbCmd);
            process = runtime.exec(adbCmd);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("执行命令:" + adbCmd + "出错啦！");
            return null;
        }
        return process;
    }
}
