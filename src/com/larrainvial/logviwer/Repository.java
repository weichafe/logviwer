package com.larrainvial.logviwer;

import com.larrainvial.logviwer.utils.PropertiesFile;
import com.larrainvial.logviwer.model.ModelProcess;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class  Repository {

    public static Stage primaryStage;
    public static Scene scene;
    public static TabPane tabPanePrincipalTabPanel;
    public static FXMLLoader rootLayout_Loader = new FXMLLoader();
    public static FXMLLoader principalTabPanel_Loader = new FXMLLoader();
    public static HashMap<String, Algo> strategy = new HashMap<String, Algo>();
    public static PropertiesFile logviewer;
    public static String locationPath;
    public static PropertiesFile killProcess;
    public static boolean exception = true;
    public static LinkedHashMap<String, ModelProcess> coreStrategy = new LinkedHashMap<String, ModelProcess>();
    public static String year = new SimpleDateFormat("yyyy/MM/dd").format(new Date()).replace("/", "");

}