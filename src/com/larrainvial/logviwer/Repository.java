package com.larrainvial.logviwer;

import com.larrainvial.logviwer.utils.FileRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

import java.util.HashMap;

public class  Repository {

    public static TabPane tabPanePrincipalTabPanel;
    public static FXMLLoader rootLayout_Loader = new FXMLLoader();
    public static FXMLLoader principalTabPanel_Loader = new FXMLLoader();
    public static HashMap<String, Algo> strategy = new HashMap<String, Algo>();

    public static FileRepository workOrdersByOrdIDFile;

}