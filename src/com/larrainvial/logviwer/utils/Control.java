package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.MainApp;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.controller.adrarbitragexsgo.*;
import com.larrainvial.logviwer.event.*;
import com.larrainvial.logviwer.listener.*;
import com.larrainvial.trading.emp.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

public class Control {

    public static void initialize(){

        Controller.addEventListener(StringToFixMessageEvent.class, new StringToFixMessageListener());
        Controller.addEventListener(SendToViewEvent.class, new SendToViewListener());
        Controller.addEventListener(RoutingMessageEvent.class, new RoutingMessageListener());
        Controller.addEventListener(MarketDataMessageEvent.class, new MarketDataMessageListener());
    }


    public  static void initializaAll(){

        initializeAdrArbitrageXSGO();
    }


    private static void initializeAdrArbitrageXSGO(){

        try {

            final Algo algo = new Algo();

            algo.setNameAlgo("ADRArbitrageXSGO");
            algo.setMkd_dolar("MKD_DOLAR");
            algo.setMkd_adr("MKD_NYSE");
            algo.setMkd_local("MKD_XSGO");
            algo.setRouting_adr("ROUTING_ADR");
            algo.setRouting_local("ROUTING_LOCAL");
            algo.setTime(1);
            algo.setMkd_dolar_file("C:\\Reporte\\Dolar.log");
            algo.setMkd_local_file("C:\\Reporte\\MKD LOCAL.log");
            algo.setMkd_adr_file("C:\\Reporte\\MKD NYSE.log");
            algo.setRouting_adr_file("C:\\Reporte\\ROUTING NYSE.log");
            algo.setRouting_local_file("C:\\Reporte\\routing XSGO.log");

            Repository.strategy.put(algo.getNameAlgo(), algo);

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.getTime()));

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
                }
            });

            algo.getMkd_dolar_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MKD_DOLAR.fxml"));
            algo.getMkd_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MKD_NYSE.fxml"));
            algo.getMkd_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MKD_XSGO.fxml"));
            algo.getRouting_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/ROUTING_ADR.fxml"));
            algo.getRouting_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/ROUTING_XSGO.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_dolar_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_local_loader().load());
            anchorPane.getChildren().add(opacityLevel);

            Repository.tabPanePrincipalTabPanel.getTabs().get(0).setContent(anchorPane);

            DolarController controller = algo.getMkd_dolar_loader().getController();
            algo.setMkd_dolar_tableView(controller.getType());

            NyseMKDController getMkd_adr_loader = algo.getMkd_adr_loader().getController();
            algo.setMkd_adr_tableView(getMkd_adr_loader.getType());

            XsgoMKDController getMkd_local_loader = algo.getMkd_local_loader().getController();
            algo.setMkd_local_tableView(getMkd_local_loader.getType());

            NyseRoutingController routing_adr_loader = algo.getRouting_adr_loader().getController();
            algo.setRouting_adr_tableView(routing_adr_loader.getType());

            XsgoRoutingController routing_local_loader = algo.getRouting_local_loader().getController();
            algo.setRouting_local_tableView(routing_local_loader.getType());

            algo.iniziale();


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}