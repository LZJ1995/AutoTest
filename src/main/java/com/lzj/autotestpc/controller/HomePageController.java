package com.lzj.autotestpc.controller;


import com.lzj.autotestpc.base.AutoTestContext;
import com.lzj.autotestpc.bean.StartDeviceBean;
import com.lzj.autotestpc.constant.PageConstant;
import com.lzj.autotestpc.servce.AppiumOperationService;
import com.lzj.autotestpc.servce.impl.AppiumOperationServiceImp;
import com.lzj.autotestpc.tool.ScriptTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    public static final String TAG = "HomePageController :";
    private static final Log log = LogFactory.getLog(HomePageController.class);
    private Stage stage;

    @FXML
    private TreeView treeView;
    private ScriptTool util;

    @FXML
    private Label deviceName;

    @FXML
    private ListView<HBox> deviceList;


    @FXML
    private TextArea showRow;
    @FXML
    private TextArea demoEditor;
    private int row = 1;

    @FXML
    private TableView scriptView;

    private Popup srceenPopup=new Popup();

    private ArrayList<StartDeviceBean> devices;

    private AppiumOperationServiceImp appiumOperationService;

    private StartWriteScriptDialogController scriptDialogController;

    /***
     * 初始化信息
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        AutoTestContext.controllers.put(this.getClass().getName(), this);
        util = new ScriptTool();
        showDevices();
        keyevent();
        loadTreeMenu("");
    }

    /**
     * 一级菜单点击事件
     */
    public void actionMenu(ActionEvent event) {
        MenuItem item = (MenuItem) event.getTarget();
        switch (item.getText()) {
            case PageConstant.PROJECT_OPEN:
                log.info(TAG + "click" + PageConstant.PROJECT_OPEN);
                try {
                    //待打包后需重新定义路径
                    Desktop.getDesktop().open(new File(this.getClass().getResource("").getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("打开当前所在位置窗口失败！路径:" + new File(this.getClass().getResource("").getPath()));
                }
                break;
            case PageConstant.PROJECT_NEW:
                log.info(TAG + "click" + PageConstant.PROJECT_NEW);
                break;
            case PageConstant.PROJECT_UPATE:
                log.info(TAG + "click" + PageConstant.PROJECT_UPATE);
                break;
            case PageConstant.PROJECT_SETTING:
                log.info(TAG + "click" + PageConstant.PROJECT_SETTING);
                break;
            case PageConstant.PROJECT_EXIT:
                log.info(TAG + "click" + PageConstant.PROJECT_EXIT);
                break;
            case PageConstant.SCRIPT_NEW:
                log.info(TAG + "click" + PageConstant.SCRIPT_NEW);
                break;
            case PageConstant.SCRIPT_UPATE:
                log.info(TAG + "click" + PageConstant.SCRIPT_UPATE);
                break;
            case PageConstant.SCRIPT_START:
                log.info(TAG + "click" + PageConstant.SCRIPT_START);
                break;
            case PageConstant.SCRIPT_CLEAR:
                log.info(TAG + "click" + PageConstant.SCRIPT_CLEAR);
                break;
            case PageConstant.SCRIPT_SEARCH:
                log.info(TAG + "click" + PageConstant.SCRIPT_SEARCH);
                break;
            case PageConstant.TEST_NEW:
                log.info(TAG + "click" + PageConstant.TEST_NEW);
                break;
            case PageConstant.TEST_DEBUG:
                log.info(TAG + "click" + PageConstant.TEST_DEBUG);
                break;
            case PageConstant.TEST_SAVE:
                log.info(TAG + "click" + PageConstant.TEST_SAVE);
                break;
            case PageConstant.TEST_SAVEAS:
                log.info(TAG + "click" + PageConstant.TEST_SAVEAS);
                break;
            case PageConstant.TEST_REPORT:
                log.info(TAG + "click" + PageConstant.TEST_REPORT);
                break;
            case PageConstant.DEVICE_RESET:
                log.info(TAG + "click" + PageConstant.DEVICE_RESET);
                break;
            case PageConstant.PLUG_UIAUTOMATORVIEW:
                log.info(TAG + "click" + PageConstant.PLUG_UIAUTOMATORVIEW);
                break;
            case PageConstant.PLUG_MONKEY:
                log.info(TAG + "click" + PageConstant.PLUG_MONKEY);
                break;
            case PageConstant.HLEAP_EXPLAIN:
                log.info(TAG + "click" + PageConstant.HLEAP_EXPLAIN);
                break;
            case PageConstant.HLEAP_ABOUT:
                log.info(TAG + "click" + PageConstant.HLEAP_ABOUT);
                break;
            default:
                log.info(TAG + "菜单栏出错");
        }
    }
    /**
     * 脚本列表显示
     */
    public void loadTreeMenu(String data) {
        String[] data2 = {"根目录1", "根目录2", "根目录3", "根目录4", "根目录5"};
        String[] data1 = {"张三", "李四", "王五", "效力", "大脑i"};
        TreeItem<String> muneItem;
        TreeItem<String> viewItem = new TreeItem<String>();
        for (String dataview : data2) {
            muneItem = new TreeItem<>(dataview);
            for (String dataitem : data1) {
                muneItem.getChildren().addAll(new TreeItem<String>(dataitem));
            }
            viewItem.getChildren().addAll(muneItem);
        }
        treeView.setRoot(viewItem);
        treeView.setShowRoot(false);
    }
    /**
     * 显示已连接设备信息
     */
    public void showDevices() {
        devices = util.getAllDvice();
        ArrayList<HBox> hboxList = new ArrayList<>();
        if (devices != null && devices.size() > 0) {
            for (int index = 0; index < devices.size(); index++) {
                HBox hBox = new HBox();
                hBox.setId("showDevice");
                log.info(TAG + "showDevices() " + "device count:" + devices.size());
                Label deviceName = new Label(devices.get(index).getDeviceSN());
                deviceName.setId("deviceName");
                if (devices.get(index).getDeviceType().equals("device")) {
                    Label devicesType = new Label("在线");
                    hBox.getChildren().addAll(deviceName, devicesType);
                }
                hboxList.add(hBox);
            }
        } else {
            HBox hBox = new HBox();
            Label deviceError = new Label("没有设备连接！");
            deviceError.setId("deviceError");
            hBox.getChildren().addAll(deviceError);
            log.info(TAG + "showDevices()" + "NoDevices");
            hboxList.add(hBox);
        }

        deviceList.setItems(FXCollections.observableList(hboxList));
    }

    /***
     * 截图
     * screenshot
     */
    public void screenShot(){
        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);
        HBox titleHbox=new HBox();
        titleHbox.setPrefWidth(1300);
        titleHbox.setPrefWidth(100);
        titleHbox.setAlignment(Pos.CENTER_RIGHT);
        titleHbox.setStyle("-fx-background-color:white;-fx-border-color: #95978e");
        Label regionView=new Label("区域");
        regionView.setPrefWidth(50);
        regionView.setPrefHeight(40);
        Label regionPoint=new Label("[7777,2222]");
        regionPoint.setPrefWidth(80);
        regionPoint.setPrefHeight(40);
        Label exitScreen=new Label("插入断言");
        exitScreen.setPrefWidth(80);
        exitScreen.setPrefHeight(40);
        titleHbox.getChildren().addAll(regionView,regionPoint,exitScreen);
        String url=HomePageController.class.getResource("/screenshot/11111.png").toString();
        HBox contextHbox=new HBox();
        contextHbox.setPrefWidth(1300);
        contextHbox.setPrefHeight(500);
        contextHbox.setAlignment(Pos.CENTER);
        contextHbox.setStyle("-fx-background-color:#cdc6c6;");
        ImageView showScreen=new ImageView(new Image(url));
        showScreen.setFitHeight(600);
        showScreen.setFitWidth(1200);
        showScreen.setPreserveRatio(true);
        contextHbox.getChildren().add(showScreen);
        vbox.getChildren().addAll(titleHbox,contextHbox);
        srceenPopup.getContent().addAll(vbox);
        srceenPopup.setAutoHide(true);
        if (!srceenPopup.isShowing()){
            srceenPopup.show(AutoTestContext.stageManagerTool.getStage("homePage"));
        }else {
            srceenPopup.hide();
        }

    }
    /**
     * 编写脚本
     *
     * @throws Exception
     */
    public void writeScript() throws Exception {
        if (ScriptTool.showConfirmDialog(Alert.AlertType.CONFIRMATION, "确定开始编写脚本？", "温馨提示").equals("YES")) {
            if (stage != null) {
                stage.close();
            }
            stage = AutoTestContext.stageFactory.createStage(300, 400, "温馨提示", "startWriteScriptDialog.fxml");
            stage.show();
            stage.setOnCloseRequest(event -> stage.close());
        }
    }

    /**
     * 调试脚本
     * 调试功能获取运行设备信息
     */
    public void debugScript() {
        appiumOperationService = new AppiumOperationService();
        ObservableList index = deviceList.getSelectionModel().getSelectedIndices();
        if (index.size() > 0) {
            ArrayList<StartDeviceBean> startDeviceInfos = new ArrayList<>();
            for (int i = 0; i < index.size(); i++) {
                Label item = (Label) deviceList.getSelectionModel().getSelectedItem().getChildren().get(i);
                if (!item.getId().equals("deviceError")) {
                    StartDeviceBean bean = devices.get(i);
                    startDeviceInfos.add(bean);
                } else {
                    ScriptTool.showConfirmDialog(Alert.AlertType.ERROR, "请选择设备进行调试！", "温馨提示!");
                }
            }
     appiumOperationService.connectDevice(startDeviceInfos);
        } else {
            ScriptTool.showConfirmDialog(Alert.AlertType.ERROR, "请选择设备进行调试！", "温馨提示!");
        }
    }

    /**
     * 保存脚本
     */
    public void saveScript() {
        scriptDialogController = (StartWriteScriptDialogController) AutoTestContext.controllers.get("com.lzj.autotestpc.controller.StartWriteScriptDialogController");
        if (scriptDialogController != null) {
            if (scriptDialogController.getIsStratScript()) {
                StartDeviceBean bean = scriptDialogController.getBean();
                String AppPackage = bean.getAppPackage();
                String AppActivity = bean.getAppActivity();

            }
        }

    }

    /**
     * 键盘和鼠标监听事件
     */
    public void keyevent() {
        //按键按压
        demoEditor.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
        });
        //鼠标滚动监听
        demoEditor.addEventFilter(ScrollEvent.SCROLL, event -> showRow.setScrollTop(demoEditor.getScrollTop()));
        // KeyReleased   按键释放
        demoEditor.addEventFilter(KeyEvent.KEY_RELEASED,
                event -> {
                    setShowRow();
                    if (event.getCode().toString().equals("TAB")) {
                        demoEditor.getCaretPosition();
                    }
           /* if (event.getCode().toString().equals("ENTER")) {

            }
            if (event.getCode().toString().equals("BACK_SPACE")) {

            }*/
                });
//        KeyTyped   按键类型
        demoEditor.addEventFilter(KeyEvent.KEY_TYPED, event -> {

        });
    }

    /**
     * 代码编辑界面显示行数
     */
    public void setShowRow() {
        StringBuilder rowContent = new StringBuilder();
        row = demoEditor.getText().replace("", " ").split("\n").length;
        for (int i = 0; i < row; i++) {
            rowContent.append(i + 1).append("\n");
        }
        showRow.setText(rowContent.toString());
        showRow.setScrollTop(demoEditor.getScrollTop());
    }

    /***
     * 视图界面右键菜单显示
     *
     */
    @FXML
    public void showMune(MouseEvent event) {
        if (event.isPopupTrigger()) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("添加脚本");
            MenuItem editItem1 = new MenuItem("添加文字断言");
            MenuItem editItem2 = new MenuItem("添加延迟");
            MenuItem editItem3 = new MenuItem("保存");
            MenuItem editItem4 = new MenuItem("调试");
            contextMenu.getItems().addAll(editItem, editItem1, editItem2, editItem3, editItem4);
            scriptView.setContextMenu(contextMenu);
            contextMenu.setOnAction(event12 -> {
                MenuItem item = (MenuItem) event12.getTarget();
                switch (item.getText()) {
                    case PageConstant.EDITOR_CREATE:
                        //新增脚本
                        try {
                            if (stage != null) {
                                stage.close();
                            }
                            stage = AutoTestContext.stageFactory.createStage(400, 600, "插入脚本", "addScript.fxml");
                            stage.show();
                            stage.setOnCloseRequest(event1 -> stage.close());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case PageConstant.EDITOR_ASSERT:
                        break;
                }
            });
        }
    }


}

