package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.MainApp;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.controller.adrarbitragexsgo.*;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.logviwer.listener.AlertListener;
import com.larrainvial.logviwer.listener.ReadLogListener;
import com.larrainvial.logviwer.listener.SendToViewListener;
import com.larrainvial.logviwer.listener.StringToFixMessageListener;
import com.larrainvial.trading.emp.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Control {

    public static void initialize(){

        Controller.addEventListener(StringToFixMessageEvent.class, new StringToFixMessageListener());
        Controller.addEventListener(SendToViewEvent.class, new SendToViewListener());
        Controller.addEventListener(ReadLogEvent.class, new ReadLogListener());
        Controller.addEventListener(AlertEvent.class, new AlertListener());
    }


    public  static void initializaAll() throws InterruptedException {

        initializeAdrArbitrageXSGO();
        //initializeAdrArbitrageXTSE();
        initializeAdrArbitrageXBOG();
    }


    private static void initializeAdrArbitrageXSGO(){

        try {

            Algo algo = new Algo();

            algo.setNameAlgo("ADRArbitrageXSGO");
            algo.setMkd_dolar("MKD_DOLAR");
            algo.setMkd_adr("MKD_NYSE");
            algo.setMkd_local("MKD_XSGO");
            algo.setRouting_adr("ROUTING_ADR");
            algo.setRouting_local("ROUTING_LOCAL");
            algo.setTime(1);

            Date fechaActual = new Date();

            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            String location = "W:\\ADRArbitrageXSGOIB\\log\\";
            //String location = "log\\AdrArbitrageIB\\";
            String mkd_dolar = "FIX.4.4-ALGOARBADR5-MDFHBLP.messages_";
            String mkd_nyse = "FIX.4.4-ARBv3_EQUITY_NYS_BCS-MAMA_NYSE.messages_";
            String mkd_local = "FIX.4.4-MKDATACL2-MKDATAFHBCS2.messages_";
            String routing_local = "FIX.4.4-ARDARB_XSGO_IB-AMGTOBCS.messages_";
            String routing_nyse = "FIX.4.4-LVBSG-ADR_ARBITRAGE_IB_XNYS.messages_";
            String log = ".log";


            algo.setFile_mkd_dolar(new File(location + mkd_dolar + year + log));
            algo.setFile_mkd_local(new File(location + mkd_local + year + log));
            algo.setFile_mkd_adr(new File(location + mkd_nyse + year + log));
            algo.setFile_routing_local(new File(location + routing_local + year + log));
            algo.setFile_routing_adr(new File(location + routing_nyse + year + log));

            algo.fileReader();



            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.getTime()));

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
                }
            });


            algo.getMkd_dolar_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MarketDataDolarView.fxml"));
            algo.getMkd_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MarketDataAdrView.fxml"));
            algo.getMkd_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/MarketDataLocalView.fxml"));
            algo.getRouting_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/RoutingAdrView.fxml"));
            algo.getRouting_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/RoutingLocalView.fxml"));
            algo.getPanel_positions_loader().setLocation(MainApp.class.getResource("view/adrarbitragexsgo/PanelPositionsView.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_dolar_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getPanel_positions_loader().load());
            anchorPane.getChildren().add(opacityLevel);

            Repository.tabPanePrincipalTabPanel.getTabs().get(0).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(0).setText(algo.getNameAlgo());

            MarketDataDolarController getMkd_dolar_loader = algo.getMkd_dolar_loader().getController();
            algo.setMkd_dolar_tableView(getMkd_dolar_loader.getType());
            algo.setDolarMasterList(getMkd_dolar_loader.masterData);

            MarketDataAdrController getMkd_adr_loader = algo.getMkd_adr_loader().getController();
            algo.setMkd_adr_tableView(getMkd_adr_loader.getType());
            algo.setMkdAdrMasterList(getMkd_adr_loader.masterData);

            MarketDataLocalController getMkd_local_loader = algo.getMkd_local_loader().getController();
            algo.setMkd_local_tableView(getMkd_local_loader.getType());
            algo.setMkdLocalMasterList(getMkd_local_loader.masterData);

            RoutingAdrController routing_adr_loader = algo.getRouting_adr_loader().getController();
            algo.setRouting_adr_tableView(routing_adr_loader.getType());
            algo.setRoutingAdrMasterList(routing_adr_loader.masterData);

            RoutingLocalController routing_local_loader = algo.getRouting_local_loader().getController();
            algo.setRouting_local_tableView(routing_local_loader.getType());
            algo.setRoutingLocalMasterList(routing_local_loader.masterData);

            PanelPositionsController panel_local_loader = algo.getPanel_positions_loader().getController();
            algo.setPanel_positions_tableView(panel_local_loader.getType());



            Repository.strategy.put(algo.getNameAlgo(), algo);

            algo.iniziale();


        }catch (Exception e){
            //new Algo().exception(e);
        }
    }

    private static void initializeAdrArbitrageXTSE(){

        try {

            Algo algo = new Algo();

            algo.setNameAlgo("ADRArbitrageXTSE");
            algo.setMkd_dolar("MKD_DOLAR");
            algo.setMkd_adr("MKD_NYSE");
            algo.setMkd_local("MKD_XSGO");
            algo.setRouting_adr("ROUTING_ADR");
            algo.setRouting_local("ROUTING_LOCAL");
            algo.setTime(1);

            Date fechaActual = new Date();

            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            String location = "W:\\ADRArbitrageXTSEBeta\\connection\\log\\";
            //String location = "log\\AdrArbitrageIB\\";
            String mkd_dolar = "FIX.4.4-LVMDG-BLODPENNA7.messages_";
            String mkd_nyse = "FIX.4.4-LVMDG-BLODPENNA6.messages_";
            String mkd_local = "FIX.4.4-BOGCURNCY-LVMDG.messages_";
            String routing_local = "FIX.4.4-ADRARB_XTSE_TOBVC-AMGTOBVC.messages_";
            String routing_nyse = "FIX.4.4-LVBSG-CL04.messages_";
            String log = ".log";


            algo.setFile_mkd_dolar(new File(location + mkd_dolar + year + log));
            algo.setFile_mkd_local(new File(location + mkd_local + year + log));
            algo.setFile_mkd_adr(new File(location + mkd_nyse + year + log));
            algo.setFile_routing_local(new File(location + routing_local + year + log));
            algo.setFile_routing_adr(new File(location + routing_nyse + year + log));

            algo.fileReader();

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.getTime()));

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
                }
            });


            algo.getMkd_dolar_loader().setLocation(MainApp.class.getResource("view/adrarbitragextse/MarketDataDolarView.fxml"));
            algo.getMkd_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragextse/MarketDataAdrView.fxml"));
            algo.getMkd_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragextse/MarketDataLocalView.fxml"));
            algo.getRouting_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragextse/RoutingAdrView.fxml"));
            algo.getRouting_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragextse/RoutingLocalView.fxml"));
            algo.getPanel_positions_loader().setLocation(MainApp.class.getResource("view/adrarbitragextse/PanelPositionsView.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_dolar_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getPanel_positions_loader().load());
            anchorPane.getChildren().add(opacityLevel);

            Repository.tabPanePrincipalTabPanel.getTabs().get(1).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(1).setText(algo.getNameAlgo());


            com.larrainvial.logviwer.controller.adrarbitragextse.MarketDataDolarController getMkd_dolar_loader = algo.getMkd_dolar_loader().getController();
            algo.setMkd_dolar_tableView(getMkd_dolar_loader.getType());
            algo.setDolarMasterList(getMkd_dolar_loader.masterData);


            com.larrainvial.logviwer.controller.adrarbitragextse.MarketDataAdrController getMkd_adr_loader = algo.getMkd_adr_loader().getController();
            algo.setMkd_adr_tableView(getMkd_adr_loader.getType());
            algo.setMkdAdrMasterList(getMkd_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragextse.MarketDataLocalController getMkd_local_loader = algo.getMkd_local_loader().getController();
            algo.setMkd_local_tableView(getMkd_local_loader.getType());
            algo.setMkdLocalMasterList(getMkd_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragextse.RoutingAdrController routing_adr_loader = algo.getRouting_adr_loader().getController();
            algo.setRouting_adr_tableView(routing_adr_loader.getType());
            algo.setRoutingAdrMasterList(routing_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragextse.RoutingLocalController routing_local_loader = algo.getRouting_local_loader().getController();
            algo.setRouting_local_tableView(routing_local_loader.getType());
            algo.setRoutingLocalMasterList(routing_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragextse.PanelPositionsController panel_local_loader = algo.getPanel_positions_loader().getController();
            algo.setPanel_positions_tableView(panel_local_loader.getType());

            Repository.strategy.put(algo.getNameAlgo(), algo);

            algo.iniziale();


        }catch (Exception e){
            //new Algo().exception(e);
        }
    }

    private static void initializeAdrArbitrageXBOG(){

        try {

            Algo algo = new Algo();

            algo.setNameAlgo("ADRArbitrageXBOG");
            algo.setMkd_dolar("MKD_DOLAR");
            algo.setMkd_adr("MKD_NYSE");
            algo.setMkd_local("MKD_XSGO");
            algo.setRouting_adr("ROUTING_ADR");
            algo.setRouting_local("ROUTING_LOCAL");
            algo.setTime(1);

            Date fechaActual = new Date();

            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            String location = "W:\\ADRArbitrageXBOGBeta\\connection\\log\\";
            //String location = "log\\AdrArbitrageIB\\";
            String mkd_dolar = "FIX.4.4-LVMDG-BLODPENNA5.messages_";
            String mkd_nyse = "FIX.4.4-ARBv7_EQUITY_NYS_BCS-MAMA_NYSE.messages_";
            String mkd_local = "FIX.4.4-STOPLOSSBEC-LVMDG.messages_";
            String routing_local = "FIX.4.4-ADRARBXBOG-AMGTOBVC.messages_";
            String routing_nyse = "FIX.4.4-LVBSG-FROMADRXBOG_XNYSE_BETA.messages_";
            String log = ".log";


            algo.setFile_mkd_dolar(new File(location + mkd_dolar + year + log));
            algo.setFile_mkd_local(new File(location + mkd_local + year + log));
            algo.setFile_mkd_adr(new File(location + mkd_nyse + year + log));
            algo.setFile_routing_local(new File(location + routing_local + year + log));
            algo.setFile_routing_adr(new File(location + routing_nyse + year + log));

            algo.fileReader();

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.getTime()));

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
                }
            });


            algo.getMkd_dolar_loader().setLocation(MainApp.class.getResource("view/adrarbitragexbog/MarketDataDolarView.fxml"));
            algo.getMkd_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragexbog/MarketDataAdrView.fxml"));
            algo.getMkd_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragexbog/MarketDataLocalView.fxml"));
            algo.getRouting_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragexbog/RoutingAdrView.fxml"));
            algo.getRouting_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragexbog/RoutingLocalView.fxml"));
            algo.getPanel_positions_loader().setLocation(MainApp.class.getResource("view/adrarbitragexbog/PanelPositionsView.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_dolar_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getPanel_positions_loader().load());
            anchorPane.getChildren().add(opacityLevel);

            Repository.tabPanePrincipalTabPanel.getTabs().get(2).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(2).setText(algo.getNameAlgo());

            com.larrainvial.logviwer.controller.adrarbitragexbog.MarketDataDolarController getMkd_dolar_loader = algo.getMkd_dolar_loader().getController();
            algo.setMkd_dolar_tableView(getMkd_dolar_loader.getType());
            algo.setDolarMasterList(getMkd_dolar_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.MarketDataAdrController getMkd_adr_loader = algo.getMkd_adr_loader().getController();
            algo.setMkd_adr_tableView(getMkd_adr_loader.getType());
            algo.setMkdAdrMasterList(getMkd_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.MarketDataLocalController getMkd_local_loader = algo.getMkd_local_loader().getController();
            algo.setMkd_local_tableView(getMkd_local_loader.getType());
            algo.setMkdLocalMasterList(getMkd_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.RoutingAdrController routing_adr_loader = algo.getRouting_adr_loader().getController();
            algo.setRouting_adr_tableView(routing_adr_loader.getType());
            algo.setRoutingAdrMasterList(routing_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.RoutingLocalController routing_local_loader = algo.getRouting_local_loader().getController();
            algo.setRouting_local_tableView(routing_local_loader.getType());
            algo.setRoutingLocalMasterList(routing_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.PanelPositionsController panel_local_loader = algo.getPanel_positions_loader().getController();
            algo.setPanel_positions_tableView(panel_local_loader.getType());

            Repository.strategy.put(algo.getNameAlgo(), algo);

            algo.iniziale();


        }catch (Exception e){
            //new Algo().exception(e);
        }
    }

}