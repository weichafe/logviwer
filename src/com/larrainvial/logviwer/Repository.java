package com.larrainvial.logviwer;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.HashMap;

public class  Repository {

    public static TabPane tabPanePrincipalTabPanel;
    public static FXMLLoader rootLayout_Loader = new FXMLLoader();
    public static FXMLLoader principalTabPanel_Loader = new FXMLLoader();
    public static HashMap<String, Algo> strategy = new HashMap<String, Algo>();
    public static Stage primaryStage;
    public static Stage dialogStage;

}