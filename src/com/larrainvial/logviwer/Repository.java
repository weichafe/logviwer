package com.larrainvial.logviwer;

import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;

public class  Repository {

    public static final String DOLAR = "DOLAR";
    public static final String MKD_NYSE = "NYSE";
    public static final String NameAdrArbitrageXSGO = "ADRArbitrageXSGO";
    public static double timeSlider_adr_arbitrage_xsgo = 1d;

    public static String file_adrArbitrageXsgo_mkd_dolar = "C:\\Reporte\\MKD LIMA.log";
    public static String file_adrArbitrageXsgo_mkd_nyse = "C:\\Reporte\\MKD NYSE.log";
    public static String file_adrArbitrageXsgo_mkd_xsgo = "C:\\Reporte\\MKD NYSE.log";
    public static String file_adrArbitrageXsgo_routing_nyse = "C:\\Reporte\\MKD NYSE.log";
    public static String file_adrArbitrageXsgo_routing_xsgo= "C:\\Reporte\\MKD NYSE.log";


    public static HashMap<String, Double> hashMap_timeSlider = new HashMap<String, Double>();


    public static TabPane tabPanePrincipalTabPanel;
    public static FXMLLoader rootLayout_Loader = new FXMLLoader();
    public static FXMLLoader principalTabPanel_Loader = new FXMLLoader();

    public static FXMLLoader adrArbitrageXSGO_MKD_DOLAR_LOADER = new FXMLLoader();
    public static ObservableList<ModelMarketData> adrArbitrageXSGO_MKD_DOLAR = FXCollections.observableArrayList();

    public static  FXMLLoader adrArbitrageXSGO_MKD_NYSE_LOADER = new FXMLLoader();
    public static ObservableList<ModelMarketData> adrArbitrageXSGO_MKD_NYSE = FXCollections.observableArrayList();

    public static  FXMLLoader adrArbitrageXSGO_MKD_XSGO_LOADER = new FXMLLoader();
    public static ObservableList<ModelMarketData> adrArbitrageXSGO_MKD_XSGO = FXCollections.observableArrayList();

    public static  FXMLLoader adrArbitrageXSGO_ROUTING_XSGO_LOADER = new FXMLLoader();
    public static ObservableList<ModelRoutingData> adrArbitrageXSGO_ROUTING_XSGO = FXCollections.observableArrayList();

    public static  FXMLLoader adrArbitrageXSGO_ROUTING_NYSE_LOADER = new FXMLLoader();
    public static ObservableList<ModelRoutingData> adrArbitrageXSGO_ROUTING_NYSE = FXCollections.observableArrayList();


    public static void initializaAll(){
        initializeAdrArbitrageXSGO();
    }


    private static void initializeAdrArbitrageXSGO(){

        try {

            AnchorPane anchorPane = new AnchorPane();
            hashMap_timeSlider.put(NameAdrArbitrageXSGO, timeSlider_adr_arbitrage_xsgo);


            Slider opacityLevel = new Slider(0, 10, timeSlider_adr_arbitrage_xsgo);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    hashMap_timeSlider.put(NameAdrArbitrageXSGO, new_val.doubleValue());
                }
            });


            Repository.adrArbitrageXSGO_MKD_DOLAR_LOADER.setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MKD_DOLAR.fxml"));
            AnchorPane adrArbitrageXSGO_MKD_DOLAR_AnchoPanel = (AnchorPane) Repository.adrArbitrageXSGO_MKD_DOLAR_LOADER.load();

            Repository.adrArbitrageXSGO_MKD_NYSE_LOADER.setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MKD_NYSE.fxml"));
            AnchorPane adrArbitrageXSGO_MKD_NYSE_AnchoPanel = (AnchorPane) Repository.adrArbitrageXSGO_MKD_NYSE_LOADER.load();

            Repository.adrArbitrageXSGO_MKD_XSGO_LOADER.setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MKD_XSGO.fxml"));
            AnchorPane adrArbitrageXSGO_MKD_XSGO_AnchoPanel = (AnchorPane) Repository.adrArbitrageXSGO_MKD_XSGO_LOADER.load();

            Repository.adrArbitrageXSGO_ROUTING_XSGO_LOADER.setLocation(MainApp.class.getResource("view/adrarbitragexsgo/ROUTING_ADR.fxml"));
            AnchorPane adrArbitrageXSGO_ROUTING_NYSE_AnchoPanel = (AnchorPane) Repository.adrArbitrageXSGO_ROUTING_XSGO_LOADER.load();

            Repository.adrArbitrageXSGO_ROUTING_NYSE_LOADER.setLocation(MainApp.class.getResource("view/adrarbitragexsgo/ROUTING_XSGO.fxml"));
            AnchorPane adrArbitrageXSGO_ROUTING_XSGO_AnchoPanel = (AnchorPane) Repository.adrArbitrageXSGO_ROUTING_NYSE_LOADER.load();

            anchorPane.getChildren().add(adrArbitrageXSGO_MKD_DOLAR_AnchoPanel);
            anchorPane.getChildren().add(adrArbitrageXSGO_MKD_NYSE_AnchoPanel);
            anchorPane.getChildren().add(adrArbitrageXSGO_MKD_XSGO_AnchoPanel);
            anchorPane.getChildren().add(adrArbitrageXSGO_ROUTING_NYSE_AnchoPanel);
            anchorPane.getChildren().add(adrArbitrageXSGO_ROUTING_XSGO_AnchoPanel);
            //anchorPane.getChildren().add(opacityLevel);

            Repository.tabPanePrincipalTabPanel.getTabs().get(0).setContent(anchorPane);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
