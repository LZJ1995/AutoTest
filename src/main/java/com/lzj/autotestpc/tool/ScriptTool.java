package com.lzj.autotestpc.tool;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;
import com.lzj.autotestpc.bean.DeviceInfo;
import com.lzj.autotestpc.servce.ScriptService;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     * 截图
     * @param device 设备信息
     * @return
     */
    public String getScreenShot(IDevice device) {
        String filepath= null;
        RawImage rawScreen =null;
        if (device!=null){
        try {
            rawScreen =device.getScreenshot();
            System.out.println(device.getScreenshot());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (AdbCommandRejectedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (rawScreen!=null){
            BufferedImage image = null;
            Boolean landscape = false;
            int width2 = landscape ? rawScreen.height : rawScreen.width;
            int height2 = landscape ? rawScreen.width : rawScreen.height;
            if (image == null) {
                image = new BufferedImage(width2, height2,
                        BufferedImage.TYPE_INT_RGB);
            } else {
                if (image.getHeight() != height2 || image.getWidth() != width2) {
                    image = new BufferedImage(width2, height2,
                            BufferedImage.TYPE_INT_RGB);
                }
            }
            int index = 0;
            int indexInc = rawScreen.bpp >> 3;
            for (int y = 0; y < rawScreen.height; y++) {
                for (int x = 0; x < rawScreen.width; x++, index += indexInc) {
                    int value = rawScreen.getARGB(index);
                    if (landscape)
                        image.setRGB(y, rawScreen.width - x - 1, value);
                    else
                        image.setRGB(x, y, value);
                }
            }
            try {
                filepath="E:\\idaeWorkSpace\\IDEworkspace\\AutoTestPc\\src\\main\\resources\\screenshot\\"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg";
                ImageIO.write((RenderedImage) image, "PNG", new File(filepath));
                return filepath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
        return null;
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