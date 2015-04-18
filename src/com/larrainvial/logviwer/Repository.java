package com.larrainvial.logviwer;

import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class  Repository {

    public static final String DOLAR = "DOLAR";
    public static final String MKD_NYSE = "NYSE";
    public static final String NameAdrArbitrageXSGO = "ADRArbitrageXSGO";
    public static String fileDolar = "C:\\logfix.log";

    public static FXMLLoader rootLayout_Loader = new FXMLLoader();
    public static FXMLLoader principalTabPanel_Loader = new FXMLLoader();
    public static TabPane tabPanePrincipalTabPanel;


    public static FXMLLoader adrArbitrageXSGO_DOLAR_LOADER = new FXMLLoader();
    public static ObservableList<ModelMarketData> adrArbitrageXSGO_MKD_DOLAR = FXCollections.observableArrayList();

    public static  FXMLLoader adrArbitrageXSGO_NYSE_LOADER = new FXMLLoader();
    public static ObservableList<ModelMarketData> adrArbitrageXSGO_MKD_NYSE = FXCollections.observableArrayList();







}
