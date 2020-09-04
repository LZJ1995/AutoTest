package com.lzj.autotestpc.tool;

import com.lzj.autotestpc.controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class FXMLUtils {

    /**
     * 读取FXML文件并返回Controlle
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
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());
        } else {
            scene = new Scene(page);
        }
        stage.setScene(scene);
        return (Initializable) loader.getController();
    }
   public Object doGet(String URL){
//       CloseableHttpClient
        return null;
   }
   public  Object doPost(){

        return null;
   }
}
