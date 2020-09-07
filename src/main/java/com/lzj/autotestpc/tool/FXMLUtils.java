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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

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

    public String doPost(String url ,String json) throws IOException {
        String result = null;
      String jsonDate=  URLEncoder.encode(json,"UTF-8");
        //创建连接池
        PoolingHttpClientConnectionManager cManager=new PoolingHttpClientConnectionManager();
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
        httpPost.setHeader(HTTP.CONTENT_TYPE,"application/json");
        httpPost.setEntity(new StringEntity(jsonDate));
        try {
            response=httpClient.execute(httpPost);
            result=  EntityUtils.toString(response.getEntity(),"UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response!=null){
                response.close();
            }
            httpClient.close();
        }
        return result;
    }
}
