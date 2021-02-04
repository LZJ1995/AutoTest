package com.lzj.autotestpc.controller;


import com.android.ddmlib.IDevice;
import com.lzj.autotestpc.base.AutoTestContext;
import com.lzj.autotestpc.bean.DeviceInfo;
import com.lzj.autotestpc.bean.ScriptStepBean;
import com.lzj.autotestpc.constant.DeviceInfoList;
import com.lzj.autotestpc.constant.PageConstant;
import com.lzj.autotestpc.servce.AppiumOperationService;
import com.lzj.autotestpc.servce.impl.AppiumOperationServiceImp;
import com.lzj.autotestpc.tool.ScriptTool;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
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
import java.util.*;

public class HomePageController implements Initializable {
    public static final String TAG = "HomePageController :";
    private static final Log log = LogFactory.getLog(HomePageController.class);
    //主窗体
    private Stage stage;

    //脚本树
    @FXML
    private TreeView treeView;
    //脚本工具类
    private ScriptTool util;

    @FXML
    private TableView deviceTable;
    @FXML
    private TableView scriptView;
    @FXML
    private TableView ScirptOperation;

    @FXML
    private TableColumn<DeviceInfo, String> deviceName;
    @FXML
    private TableColumn<DeviceInfo, String> deviceState;
    @FXML
    private TableColumn<DeviceInfo, String> deviceVersion;
    @FXML
    private TableColumn OperationName;

    //脚本步骤
    @FXML
    private TableColumn numberColumn, actionColumn, attributeColumn, valueColumn, remarkColumn;

    @FXML
    private TextArea showRow;
    @FXML
    private TextArea demoEditor;
    //编辑代码显示行数
    private int row = 1;

    //截图窗口
    private Popup srceenPopup = new Popup();
    //设备信息
    private IDevice device;


    private AppiumOperationServiceImp appiumOperationService;

    private StartWriteScriptDialogController scriptDialogController;
    //拖拽传输类型
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    public void initialize(URL location, ResourceBundle resources) {
        AutoTestContext.controllers.put(this.getClass().getName(), this);
        init();
    }

    /**
     * 初始化数据
     */
    public void init() {
        util = new ScriptTool();
        //实时刷新数据
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> showDevices());
            }
        }, 100, 500);
        //脚本操作列表
        showScriptOperation();
        //所有监听加载
        allEvent();
        //加载脚本树
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
     * 二级菜单点击事件
     */
    // 截图
    public void screenShot() {
        String srceenPath = null;
        if (!srceenPopup.isShowing()) {
            srceenPath = util.getScreenShot(device);
            if (srceenPath != null) {
                addSrceenPopup(srceenPath);
            } else {
                ScriptTool.showConfirmDialog(Alert.AlertType.ERROR, "请选择设备进行截图！", "温馨提示!");
            }
        } else {
            srceenPopup.hide();
            File scree = new File(srceenPath);
            scree.delete();
        }
    }

    //编写脚本
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


    //调试脚本，调试功能获取运行设备信
    public void debugScript() {
        appiumOperationService = new AppiumOperationService();
        ObservableList index = deviceTable.getSelectionModel().getSelectedIndices();
        if (index.size() > 0) {
            ArrayList<DeviceInfo> startDeviceInfos = new ArrayList<>();
            for (int i = 0; i < index.size(); i++) {
                DeviceInfo bean = (DeviceInfo) deviceTable.getSelectionModel().getSelectedItems().get(i);
            }
//            appiumOperationService.connectDevice(startDeviceInfos);
        } else {
            ScriptTool.showConfirmDialog(Alert.AlertType.ERROR, "请选择设备进行调试！", "温馨提示!");
        }
    }


    // 保存脚本
    public void saveScript() {
        scriptDialogController = (StartWriteScriptDialogController) AutoTestContext.controllers.get("com.lzj.autotestpc.controller.StartWriteScriptDialogController");
        if (scriptDialogController != null) {
            if (scriptDialogController.getIsStratScript()) {
                DeviceInfo bean = scriptDialogController.getBean();
                String AppPackage = bean.getAppPackage();
                String AppActivity = bean.getAppActivity();

            }
        }

    }


    /**
     * 脚本树显示
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
        Map<String, DeviceInfo> devices = DeviceInfoList.getdeviceNames();
        Set<String> iterator = devices.keySet();
        ObservableList<DeviceInfo> data = FXCollections.observableArrayList();
        if (devices.size() > 0) {
            device = devices.get(iterator.iterator().next()).getDevice();
            for (String key : iterator) {
                DeviceInfo info = devices.get(key);
                data.add(info);
            }
        }
        deviceName.setCellValueFactory(new PropertyValueFactory<>("deviceSN"));
        deviceVersion.setCellValueFactory(new PropertyValueFactory<>("deviceVersion"));
        deviceState.setCellValueFactory(new PropertyValueFactory<>("deviceState"));
        deviceTable.setItems(data);
    }


    /**
     * 显示脚本操作列表
     */
    public void showScriptOperation() {
        ObservableList<ScriptStepBean> showScriptOperation = FXCollections.observableArrayList();
        for (int i = 0; i < 10; i++) {
            ScriptStepBean bean = new ScriptStepBean();
            bean.setAction("获取id控件并点击" + i);
            bean.setAttribute("text" + i);
            showScriptOperation.add(bean);
        }
        OperationName.setCellValueFactory(new PropertyValueFactory<ScriptStepBean, String>("action"));
        ScirptOperation.setItems(showScriptOperation);
    }


    /***
     * 视图界面右键菜单显示
     */
    @FXML
    public void showMune(MouseEvent event) {
        if (event.isPopupTrigger()) {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("删除脚本");
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
                        int selectedIndex = scriptView.getSelectionModel().getFocusedIndex();
                        //删除脚本
                        if (selectedIndex >= 0) {
                            scriptView.getItems().remove(selectedIndex);
                        } else {
                            ScriptTool.showConfirmDialog(Alert.AlertType.ERROR, "请选择脚本删除！", "温馨提示!");
                        }
                        break;
                    case PageConstant.EDITOR_ASSERT:
                        break;
                }
            });
        }
    }

    /**
     * 所有的控件设置的监听事件
     */
    public void allEvent() {
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
        //脚本列表拖拽操作步静态，开始拖拽监听
        ScirptOperation.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ScriptStepBean bean = (ScriptStepBean) ScirptOperation.getSelectionModel().getSelectedItem();
                Dragboard db = ScirptOperation.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.put(SERIALIZED_MIME_TYPE, bean);
                db.setContent(content);
                event.consume();
            }
        });
        //脚本列表拖拽操作步静态，拖拽过程中监听
        scriptView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            }
        });
        //显示脚本数据列表
        ObservableList<ScriptStepBean> scriptDta = FXCollections.observableArrayList();
        //脚本列表拖拽操作步静态，拖拽结束后监听
        scriptView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    ScriptStepBean bean = (ScriptStepBean) db.getContent(SERIALIZED_MIME_TYPE);
                    scriptDta.add(bean);
                    //设置序列号
                    numberColumn.setCellFactory((col) -> {
                        TableCell<ScriptStepBean, String> cell = new TableCell<ScriptStepBean, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                this.setText(null);
                                this.setGraphic(null);
                                if (!empty) {
                                    int rowIndex = this.getIndex() + 1;
                                    this.setText(String.valueOf(rowIndex));
                                }
                            }
                        };
                        return cell;
                    });

                    actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
                    valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
                    attributeColumn.setCellValueFactory(new PropertyValueFactory<>("attribute"));
                    remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
                    scriptView.setEditable(true);
                    remarkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                    valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                    scriptView.setItems(scriptDta);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
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


    /**
     * 添加截图显示弹窗
     *
     * @param srceenPath 截图路径
     */
    private void addSrceenPopup(String srceenPath) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        HBox titleHbox = new HBox();
        titleHbox.setPrefWidth(1300);
        titleHbox.setPrefWidth(100);
        titleHbox.setAlignment(Pos.CENTER_RIGHT);
        titleHbox.setStyle("-fx-background-color:white;-fx-border-color: #95978e");
        Label regionView = new Label("区域");
        regionView.setPrefWidth(50);
        regionView.setPrefHeight(40);
        Label regionPoint = new Label("[7777,2222]");
        regionPoint.setPrefWidth(80);
        regionPoint.setPrefHeight(40);
        Label assertion = new Label("插入断言");
        assertion.setPrefWidth(80);
        assertion.setPrefHeight(40);
        Label exitScreen = new Label("退出");
        exitScreen.setPrefWidth(40);
        exitScreen.setPrefHeight(40);
        titleHbox.getChildren().addAll(regionView, regionPoint, assertion, exitScreen);
        exitScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                srceenPopup.hide();
                File scree = new File(srceenPath);
                scree.delete();
            }
        });
        HBox contextHbox = new HBox();
        contextHbox.setPrefWidth(1300);
        contextHbox.setPrefHeight(500);
        contextHbox.setAlignment(Pos.CENTER);
        contextHbox.setStyle("-fx-background-color:#cdc6c6;");
        ImageView showScreen = new ImageView(new Image("file:" + srceenPath));
        showScreen.setFitHeight(600);
        showScreen.setFitWidth(1200);
        showScreen.setPreserveRatio(true);
        contextHbox.getChildren().add(showScreen);
        vbox.getChildren().addAll(titleHbox, contextHbox);
        srceenPopup.getContent().addAll(vbox);
        srceenPopup.show(AutoTestContext.stageManagerTool.getStage("homePage"));
    }


}

