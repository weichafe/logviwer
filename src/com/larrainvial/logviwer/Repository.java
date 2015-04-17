package com.larrainvial.logviwer;

import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

public class  Repository {

    public static final String DOLAR = "DOLAR";
    public static final String MKD_NYSE = "NYSE";
    public static final String NameAdrArbitrageXSGO = "ADRArbitrageXSGO";
    public static String fileDolar = "C:\\logfix.log";

    public static ObservableList<ModelMarketData> adrArbitrageXSGO_DOLAR = FXCollections.observableArrayList();
    public static  FXMLLoader adrArbitrageXSGO_DOLAR_LOADER = new FXMLLoader();





}
