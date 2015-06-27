package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.MainLogViwer;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.controller.adrarbitragexsgo.*;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.event.readlog.*;
import com.larrainvial.logviwer.event.sendtoview.*;
import com.larrainvial.logviwer.event.stringtofix.*;
import com.larrainvial.logviwer.listener.AlertListener;
import com.larrainvial.logviwer.listener.SendToViewListener;
import com.larrainvial.logviwer.listener.readlog.*;
import com.larrainvial.logviwer.listener.sendtoview.*;
import com.larrainvial.logviwer.listener.stringtofix.*;
import com.larrainvial.trading.emp.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Control {

    public static void initialize(){

        Controller.addEventListener(SendToViewEvent.class, new SendToViewListener());
        Controller.addEventListener(AlertEvent.class, new AlertListener());

        Controller.addEventListener(ReadFromDolarEvent.class, new ReadFromDolarListener());
        Controller.addEventListener(ReadLogMkdAdrEvent.class, new ReadLogMkdAdrListener());
        Controller.addEventListener(ReadLogMkdLocalEvent.class, new ReadLogMkdLocalListener());
        Controller.addEventListener(ReadlogRoutingAdrEvent.class, new ReadlogRoutingAdrListener());
        Controller.addEventListener(ReadLogRoutingLocalEvent.class, new ReadLogRoutingLocalListener());

        Controller.addEventListener(DolarEvent.class, new DolarListener());
        Controller.addEventListener(MarketDataADREvent.class, new MarketDataAdrListener());
        Controller.addEventListener(MarketDataLocalEvent.class, new MarketDataLocalListener());
        Controller.addEventListener(RoutingAdrEvent.class, new RoutingAdrListener());
        Controller.addEventListener(RoutingLocalEvent.class, new RoutingLocalListener());

        Controller.addEventListener(DolarViewEvent.class, new DolarViewListener());
        Controller.addEventListener(MarketDataAdrViewEvent.class, new MarketDataAdrViewListener());
        Controller.addEventListener(MarketDataLocalViewEvent.class, new MarketDataLocalViewListener());
        Controller.addEventListener(PositionViewEvent.class, new PositionViewListener());
        Controller.addEventListener(RoutingAdrViewEvent.class, new RoutingAdrViewListener());
        Controller.addEventListener(RoutingLocalViewEvent.class, new RoutingLocalViewListener());

    }


    public  static void initializaAll() throws InterruptedException {

        //initializeSellSide(0);
        //initializeSellSide(1);
        initializeAdrArbitrageXSGO(2);
        initializeAdrArbitrageXTSE(3);
        initializeAdrArbitrageXBOG(4);

    }



    private static void initializeSellSide(int tab){

        try {

            Algo algo = new Algo();

            algo.nameAlgo =("Sell Sides");
            algo.routing_local =("ROUTING_LOCAL");
            algo.time = (1);

            Date fechaActual = new Date();
            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            String location = "C:\\workspaceGit\\logviwer\\logs\\log\\";
            String routing_nyse = "FIX.4.4-LVSSG_ARB-LVARB.messages_";
            String log = ".log";

            algo.file_routing_adr =(new File(location + routing_nyse + year + log));
            algo.fileReader(false, false, false, false, true);

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.time));
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(33);

            Label labelMinTimer = new Label("0 s");
            labelMinTimer.setTextFill(Color.web("#0076a3"));
            labelMinTimer.setLayoutX(28);
            labelMinTimer.setLayoutY(50);

            Label labelMaxTimer = new Label("10 s");
            labelMaxTimer.setTextFill(Color.web("#0076a3"));
            labelMaxTimer.setLayoutX(140);
            labelMaxTimer.setLayoutY(50);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.time = (new_val.doubleValue());
                }
            });

            SwitchButton switchButtonRouting_Adr = new SwitchButton("Routing", algo);
            Button switchBtn5 = switchButtonRouting_Adr.returnButton();
            switchBtn5.setLayoutX(190);
            switchBtn5.setLayoutY(30);


            SwitchButton switchButtonAlert = new SwitchButton("Alert", algo);
            Button switchBtn6 = switchButtonAlert.returnButton();
            switchBtn6.setLayoutX(320);
            switchBtn6.setLayoutY(30);

            Label labelIP = new Label(Helper.getIp());
            labelIP.setTextFill(Color.web("#0076a3"));
            labelIP.setLayoutX(500);
            labelIP.setLayoutY(30);


            algo.routing_adr_loader.setLocation(MainLogViwer.class.getResource("view/sellside/RoutingAdrView.fxml"));

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.routing_adr_loader.load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);
            anchorPane.getChildren().add(labelIP);
            anchorPane.getChildren().add(labelMinTimer);
            anchorPane.getChildren().add(labelMaxTimer);


            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.nameAlgo);

            com.larrainvial.logviwer.controller.sellside.RoutingAdrController getSellside_loader = algo.routing_adr_loader.getController();
            algo.routing_adr_tableView =(getSellside_loader.getType());
            algo.routingAdrMasterList = (getSellside_loader.masterData);

            Repository.strategy.put(algo.nameAlgo, algo);
            algo.iniziale(false, false, false, false, true);

        } catch (Exception e){
            Helper.exception(e);
        }

    }

    private static void initializeAdrArbitrageXSGO(int tab){

        try {

            Algo algo = new Algo();

            algo.nameAlgo =("ADRArbitrage XSGO");
            algo.mkd_dolar =("MKD_DOLAR");
            algo.mkd_adr =("MKD_NYSE");
            algo.mkd_local =("MKD_XSGO");
            algo.routing_adr = ("ROUTING_ADR");
            algo.routing_local =("ROUTING_LOCAL");
            algo.time = (1);

            Date fechaActual = new Date();

            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            //String location = "W:\\ADRArbitrageXSGOIB\\log\\";
            String location = "src\\resources\\log\\AdrArbitrageIB\\";
            String mkd_dolar = "FIX.4.4-ALGOARBADR5-MDFHBLP.messages_";
            String mkd_nyse = "FIX.4.4-ARBv3_EQUITY_NYS_BCS-MAMA_NYSE.messages_";
            String mkd_local = "FIX.4.4-MKDATACL2-MKDATAFHBCS2.messages_";
            String routing_local = "FIX.4.4-ARDARB_XSGO_IB-AMGTOBCS.messages_";
            String routing_nyse = "FIX.4.4-LVBSG-ADR_ARBITRAGE_IB_XNYS.messages_";
            String log = ".log";

            algo.file_mkd_dolar =(new File(location + mkd_dolar + year + log));
            algo.file_mkd_local =(new File(location + mkd_local + year + log));
            algo.file_mkd_adr = (new File(location + mkd_nyse + year + log));
            algo.file_routing_local =(new File(location + routing_local + year + log));
            algo.file_routing_adr =(new File(location + routing_nyse + year + log));

            algo.fileReader(true, true, true, true, true);

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.time));
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.time = (new_val.doubleValue());
                }
            });


            SwitchButton switchButtonDolar = new SwitchButton("Dolar", algo);
            Button switchBtn1 = switchButtonDolar.returnButton();
            switchBtn1.setLayoutX(177);
            switchBtn1.setLayoutY(10);

            SwitchButton switchButtonMkd_nyse = new SwitchButton("MKD ADR", algo);
            Button switchBtn2 = switchButtonMkd_nyse.returnButton();
            switchBtn2.setLayoutX(295);
            switchBtn2.setLayoutY(10);

            SwitchButton switchButtonMkd_Local = new SwitchButton("MKD Local", algo);
            Button switchBtn3 = switchButtonMkd_Local.returnButton();
            switchBtn3.setLayoutX(415);
            switchBtn3.setLayoutY(10);

            SwitchButton switchButtonRouting_Local = new SwitchButton("Routing Local", algo);
            Button switchBtn4 = switchButtonRouting_Local.returnButton();
            switchBtn4.setLayoutX(535);
            switchBtn4.setLayoutY(10);

            SwitchButton switchButtonRouting_Adr = new SwitchButton("Routing ADR", algo);
            Button switchBtn5 = switchButtonRouting_Adr.returnButton();
            switchBtn5.setLayoutX(655);
            switchBtn5.setLayoutY(10);

            SwitchButton switchButtonAlert = new SwitchButton("Alert", algo);
            Button switchBtn6 = switchButtonAlert.returnButton();
            switchBtn6.setLayoutX(775);
            switchBtn6.setLayoutY(10);


            algo.mkd_dolar_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexsgo/MarketDataDolarView.fxml"));
            algo.mkd_adr_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexsgo/MarketDataAdrView.fxml"));
            algo.mkd_local_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexsgo/MarketDataLocalView.fxml"));
            algo.routing_adr_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexsgo/RoutingAdrView.fxml"));
            algo.routing_local_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexsgo/RoutingLocalView.fxml"));
            algo.panel_positions_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexsgo/PanelPositionsView.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.mkd_dolar_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.mkd_adr_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.mkd_local_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.routing_adr_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.routing_local_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.panel_positions_loader.load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn2);
            anchorPane.getChildren().add(switchBtn3);
            anchorPane.getChildren().add(switchBtn4);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefSize(1000, 800);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setContent(anchorPane);


            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.nameAlgo);

            MarketDataDolarController getMkd_dolar_loader = algo.mkd_dolar_loader.getController();
            algo.mkd_dolar_tableView = (getMkd_dolar_loader.getType());
            algo.dolarMasterList =(getMkd_dolar_loader.masterData);

            MarketDataAdrController getMkd_adr_loader = algo.mkd_adr_loader.getController();
            algo.mkd_adr_tableView = (getMkd_adr_loader.getType());
            algo.mkdAdrMasterList = (getMkd_adr_loader.masterData);

            MarketDataLocalController getMkd_local_loader = algo.mkd_local_loader.getController();
            algo.mkd_local_tableView = getMkd_local_loader.getType();
            algo.mkdLocalMasterList = getMkd_local_loader.masterData;

            RoutingAdrController routing_adr_loader = algo.routing_adr_loader.getController();
            algo.routing_adr_tableView =(routing_adr_loader.getType());
            algo.routingAdrMasterList = (routing_adr_loader.masterData);

            RoutingLocalController routing_local_loader = algo.routing_local_loader.getController();
            algo.routing_local_tableView = (routing_local_loader.getType());
            algo.routingLocalMasterList =(routing_local_loader.masterData);

            PanelPositionsController panel_local_loader = algo.panel_positions_loader.getController();
            algo.panel_positions_tableView = (panel_local_loader.getType());

            Repository.strategy.put(algo.nameAlgo, algo);

            algo.iniziale(true, true, true, true, true);


        } catch (Exception e){
            Helper.exception(e);
        }
    }

    private static void initializeAdrArbitrageXTSE(int tab){

        try {

            Algo algo = new Algo();

            algo.nameAlgo =("ADRArbitrage XTSE");
            algo.mkd_dolar =("MKD_DOLAR");
            algo.mkd_adr =("MKD_NYSE");
            algo.mkd_local =("MKD_XSGO");
            algo.routing_adr = ("ROUTING_ADR");
            algo.routing_local =("ROUTING_LOCAL");
            algo.time = (1);

            Date fechaActual = new Date();

            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            //String location = "W:\\ADRArbitrageXTSEBeta\\connection\\log\\";
            String location = "src\\resources\\log\\AdrArbitrageXTSE\\";
            String mkd_dolar = "FIX.4.4-LVMDG-BLODPENNA7.messages_";
            String mkd_nyse = "FIX.4.4-LVMDG-BLODPENNA6.messages_";
            String mkd_local = "FIX.4.4-BOGCURNCY-LVMDG.messages_";
            String routing_local = "FIX.4.4-ADRARB_XTSE_TOBVC-AMGTOBVC.messages_";
            String routing_nyse = "FIX.4.4-LVBSG-CL04.messages_";
            String log = ".log";


            algo.file_mkd_dolar =(new File(location + mkd_dolar + year + log));
            algo.file_mkd_local =(new File(location + mkd_local + year + log));
            algo.file_mkd_adr = (new File(location + mkd_nyse + year + log));
            algo.file_routing_local =(new File(location + routing_local + year + log));
            algo.file_routing_adr =(new File(location + routing_nyse + year + log));

            algo.fileReader(true, true, true, true, true);

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.time));
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.time = (new_val.doubleValue());
                }
            });

            SwitchButton switchButtonDolar = new SwitchButton("Dolar", algo);
            Button switchBtn1 = switchButtonDolar.returnButton();
            switchBtn1.setLayoutX(177);
            switchBtn1.setLayoutY(10);

            SwitchButton switchButtonMkd_nyse = new SwitchButton("MKD ADR", algo);
            Button switchBtn2 = switchButtonMkd_nyse.returnButton();
            switchBtn2.setLayoutX(295);
            switchBtn2.setLayoutY(10);

            SwitchButton switchButtonMkd_Local = new SwitchButton("MKD Local", algo);
            Button switchBtn3 = switchButtonMkd_Local.returnButton();
            switchBtn3.setLayoutX(415);
            switchBtn3.setLayoutY(10);

            SwitchButton switchButtonRouting_Local = new SwitchButton("Routing Local", algo);
            Button switchBtn4 = switchButtonRouting_Local.returnButton();
            switchBtn4.setLayoutX(535);
            switchBtn4.setLayoutY(10);

            SwitchButton switchButtonRouting_Adr = new SwitchButton("Routing ADR", algo);
            Button switchBtn5 = switchButtonRouting_Adr.returnButton();
            switchBtn5.setLayoutX(655);
            switchBtn5.setLayoutY(10);

            SwitchButton switchButtonAlert = new SwitchButton("Alert", algo);
            Button switchBtn6 = switchButtonAlert.returnButton();
            switchBtn6.setLayoutX(775);
            switchBtn6.setLayoutY(10);


            algo.mkd_dolar_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragextse/MarketDataDolarView.fxml"));
            algo.mkd_adr_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragextse/MarketDataAdrView.fxml"));
            algo.mkd_local_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragextse/MarketDataLocalView.fxml"));
            algo.routing_adr_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragextse/RoutingAdrView.fxml"));
            algo.routing_local_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragextse/RoutingLocalView.fxml"));
            algo.panel_positions_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragextse/PanelPositionsView.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.mkd_dolar_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.mkd_adr_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.mkd_local_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.routing_adr_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.routing_local_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.panel_positions_loader.load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn2);
            anchorPane.getChildren().add(switchBtn3);
            anchorPane.getChildren().add(switchBtn4);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);

            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.nameAlgo);


            com.larrainvial.logviwer.controller.adrarbitragextse.MarketDataDolarController getMkd_dolar_loader = algo.mkd_dolar_loader.getController();
            algo.mkd_dolar_tableView = (getMkd_dolar_loader.getType());
            algo.dolarMasterList =(getMkd_dolar_loader.masterData);



            com.larrainvial.logviwer.controller.adrarbitragextse.MarketDataAdrController getMkd_adr_loader = algo.mkd_adr_loader.getController();
            algo.mkd_adr_tableView = (getMkd_adr_loader.getType());
            algo.mkdAdrMasterList = (getMkd_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragextse.MarketDataLocalController getMkd_local_loader = algo.mkd_local_loader.getController();
            algo.mkd_local_tableView = (getMkd_local_loader.getType());
            algo.mkdLocalMasterList = (getMkd_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragextse.RoutingAdrController routing_adr_loader = algo.routing_adr_loader.getController();
            algo.routing_adr_tableView =(routing_adr_loader.getType());
            algo.routingAdrMasterList = (routing_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragextse.RoutingLocalController routing_local_loader = algo.routing_local_loader.getController();
            algo.routing_local_tableView = (routing_local_loader.getType());
            algo.routingLocalMasterList =(routing_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragextse.PanelPositionsController panel_local_loader = algo.panel_positions_loader.getController();
            algo.panel_positions_tableView = (panel_local_loader.getType());

            Repository.strategy.put(algo.nameAlgo, algo);

            algo.iniziale(true, true, true, true, true);


        }catch (Exception e){
            Helper.exception(e);
        }
    }

    private static void initializeAdrArbitrageXBOG(int tab){

        try {

            Algo algo = new Algo();

            algo.nameAlgo =("ADRArbitrage XBOG");
            algo.mkd_dolar =("MKD_DOLAR");
            algo.mkd_adr =("MKD_NYSE");
            algo.mkd_local =("MKD_XSGO");
            algo.routing_adr = ("ROUTING_ADR");
            algo.routing_local =("ROUTING_LOCAL");
            algo.time = (1);

            Date fechaActual = new Date();

            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            //String location = "W:\\ADRArbitrageXBOGBeta\\connection\\log\\";
            String location = "src\\resources\\log\\AdrArbitrageXBOG\\";
            String mkd_dolar = "FIX.4.4-LVMDG-BLODPENNA5.messages_";
            String mkd_nyse = "FIX.4.4-ARBv7_EQUITY_NYS_BCS-MAMA_NYSE.messages_";
            String mkd_local = "FIX.4.4-STOPLOSSBEC-LVMDG.messages_";
            String routing_local = "FIX.4.4-ADRARBXBOG-AMGTOBVC.messages_";
            String routing_nyse = "FIX.4.4-LVBSG-FROMADRXBOG_XNYSE_BETA.messages_";
            String log = ".log";


            algo.file_mkd_dolar =(new File(location + mkd_dolar + year + log));
            algo.file_mkd_local =(new File(location + mkd_local + year + log));
            algo.file_mkd_adr = (new File(location + mkd_nyse + year + log));
            algo.file_routing_local =(new File(location + routing_local + year + log));
            algo.file_routing_adr =(new File(location + routing_nyse + year + log));

            algo.fileReader(true, true, true, true, true);

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.time));
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.time = (new_val.doubleValue());
                }
            });

            SwitchButton switchButtonDolar = new SwitchButton("Dolar", algo);
            Button switchBtn1 = switchButtonDolar.returnButton();
            switchBtn1.setLayoutX(177);
            switchBtn1.setLayoutY(10);

            SwitchButton switchButtonMkd_nyse = new SwitchButton("MKD ADR", algo);
            Button switchBtn2 = switchButtonMkd_nyse.returnButton();
            switchBtn2.setLayoutX(295);
            switchBtn2.setLayoutY(10);

            SwitchButton switchButtonMkd_Local = new SwitchButton("MKD Local", algo);
            Button switchBtn3 = switchButtonMkd_Local.returnButton();
            switchBtn3.setLayoutX(415);
            switchBtn3.setLayoutY(10);

            SwitchButton switchButtonRouting_Local = new SwitchButton("Routing Local", algo);
            Button switchBtn4 = switchButtonRouting_Local.returnButton();
            switchBtn4.setLayoutX(535);
            switchBtn4.setLayoutY(10);

            SwitchButton switchButtonRouting_Adr = new SwitchButton("Routing ADR", algo);
            Button switchBtn5 = switchButtonRouting_Adr.returnButton();
            switchBtn5.setLayoutX(655);
            switchBtn5.setLayoutY(10);

            SwitchButton switchButtonAlert = new SwitchButton("Alert", algo);
            Button switchBtn6 = switchButtonAlert.returnButton();
            switchBtn6.setLayoutX(775);
            switchBtn6.setLayoutY(10);


            algo.mkd_dolar_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexbog/MarketDataDolarView.fxml"));
            algo.mkd_adr_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexbog/MarketDataAdrView.fxml"));
            algo.mkd_local_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexbog/MarketDataLocalView.fxml"));
            algo.routing_adr_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexbog/RoutingAdrView.fxml"));
            algo.routing_local_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexbog/RoutingLocalView.fxml"));
            algo.panel_positions_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragexbog/PanelPositionsView.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.mkd_dolar_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.mkd_adr_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.mkd_local_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.routing_adr_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.routing_local_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.panel_positions_loader.load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn2);
            anchorPane.getChildren().add(switchBtn3);
            anchorPane.getChildren().add(switchBtn4);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);

            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.nameAlgo);

            com.larrainvial.logviwer.controller.adrarbitragexbog.MarketDataDolarController getMkd_dolar_loader = algo.mkd_dolar_loader.getController();
            algo.mkd_dolar_tableView = (getMkd_dolar_loader.getType());
            algo.dolarMasterList =(getMkd_dolar_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.MarketDataAdrController getMkd_adr_loader = algo.mkd_adr_loader.getController();
            algo.mkd_adr_tableView = (getMkd_adr_loader.getType());
            algo.mkdAdrMasterList = (getMkd_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.MarketDataLocalController getMkd_local_loader = algo.mkd_local_loader.getController();
            algo.mkd_local_tableView = (getMkd_local_loader.getType());
            algo.mkdLocalMasterList = (getMkd_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.RoutingAdrController routing_adr_loader = algo.routing_adr_loader.getController();
            algo.routing_adr_tableView =(routing_adr_loader.getType());
            algo.routingAdrMasterList = (routing_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.RoutingLocalController routing_local_loader = algo.routing_local_loader.getController();
            algo.routing_local_tableView = (routing_local_loader.getType());
            algo.routingLocalMasterList =(routing_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragexbog.PanelPositionsController panel_local_loader = algo.panel_positions_loader.getController();
            algo.panel_positions_tableView = (panel_local_loader.getType());

            Repository.strategy.put(algo.nameAlgo, algo);

            algo.iniziale(true, true, true, true, true);


        }catch (Exception e){
            Helper.exception(e);
        }
    }

    private static void initializeAdrArbitrageTest(int tab){

        try {

            Algo algo = new Algo();

            algo.nameAlgo =("ADRArbitrage TEST");
            algo.mkd_dolar =("MKD_DOLAR");
            algo.mkd_adr =("MKD_NYSE");
            algo.mkd_local =("MKD_XSGO");
            algo.routing_adr = ("ROUTING_ADR");
            algo.routing_local =("ROUTING_LOCAL");
            algo.time = (1);

            Date fechaActual = new Date();

            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            //String location = "W:\\ADRArbitrageXBOGBeta\\connection\\log\\";
            String location = "C:\\workpaceAplicationRun\\ADRArbitrage\\quickfix\\log\\";
            String mkd_dolar = "FIX.4.4-LVMDG-BLORAHUE.messages_";
            String mkd_nyse = "FIX.4.4-ARBv10_EQUITY_NYS_BCS-MAMA_NYSE.messages_";
            String mkd_local = "FIX.4.4-LVTRADEBASKET-FHMDXSGO.messages_";
            String routing_local = "FIX.4.4-TEST2-ORDERROUTER.messages_";
            String routing_nyse = "FIX.4.4-TEST3-ORDERROUTER.messages_";
            String log = ".log";


            algo.file_mkd_dolar =(new File(location + mkd_dolar + year + log));
            algo.file_mkd_local =(new File(location + mkd_local + year + log));
            algo.file_mkd_adr = (new File(location + mkd_nyse + year + log));
            algo.file_routing_local =(new File(location + routing_local + year + log));
            algo.file_routing_adr =(new File(location + routing_nyse + year + log));

            algo.fileReader(true, true, true, true, true);

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.time));
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.time = (new_val.doubleValue());
                }
            });

            SwitchButton switchButtonDolar = new SwitchButton("Dolar", algo);
            Button switchBtn1 = switchButtonDolar.returnButton();
            switchBtn1.setLayoutX(177);
            switchBtn1.setLayoutY(10);

            SwitchButton switchButtonMkd_nyse = new SwitchButton("MKD ADR", algo);
            Button switchBtn2 = switchButtonMkd_nyse.returnButton();
            switchBtn2.setLayoutX(295);
            switchBtn2.setLayoutY(10);

            SwitchButton switchButtonMkd_Local = new SwitchButton("MKD Local", algo);
            Button switchBtn3 = switchButtonMkd_Local.returnButton();
            switchBtn3.setLayoutX(415);
            switchBtn3.setLayoutY(10);

            SwitchButton switchButtonRouting_Local = new SwitchButton("Routing Local", algo);
            Button switchBtn4 = switchButtonRouting_Local.returnButton();
            switchBtn4.setLayoutX(535);
            switchBtn4.setLayoutY(10);

            SwitchButton switchButtonRouting_Adr = new SwitchButton("Routing ADR", algo);
            Button switchBtn5 = switchButtonRouting_Adr.returnButton();
            switchBtn5.setLayoutX(655);
            switchBtn5.setLayoutY(10);

            SwitchButton switchButtonAlert = new SwitchButton("Alert", algo);
            Button switchBtn6 = switchButtonAlert.returnButton();
            switchBtn6.setLayoutX(775);
            switchBtn6.setLayoutY(10);


            algo.mkd_dolar_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragetest/MarketDataDolarView.fxml"));
            algo.mkd_adr_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragetest/MarketDataAdrView.fxml"));
            algo.mkd_local_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragetest/MarketDataLocalView.fxml"));
            algo.routing_adr_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragetest/RoutingAdrView.fxml"));
            algo.routing_local_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragetest/RoutingLocalView.fxml"));
            algo.panel_positions_loader.setLocation(MainLogViwer.class.getResource("view/adrarbitragetest/PanelPositionsView.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.mkd_dolar_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.mkd_adr_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.mkd_local_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.routing_adr_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.routing_local_loader.load());
            anchorPane.getChildren().add((AnchorPane) algo.panel_positions_loader.load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn2);
            anchorPane.getChildren().add(switchBtn3);
            anchorPane.getChildren().add(switchBtn4);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);

            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.nameAlgo);

            com.larrainvial.logviwer.controller.adrarbitragetest.MarketDataDolarController getMkd_dolar_loader = algo.mkd_dolar_loader.getController();
            algo.mkd_dolar_tableView = (getMkd_dolar_loader.getType());
            algo.dolarMasterList =(getMkd_dolar_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.MarketDataAdrController getMkd_adr_loader = algo.mkd_adr_loader.getController();
            algo.mkd_adr_tableView = (getMkd_adr_loader.getType());
            algo.mkdAdrMasterList = (getMkd_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.MarketDataLocalController getMkd_local_loader = algo.mkd_local_loader.getController();
            algo.mkd_local_tableView = (getMkd_local_loader.getType());
            algo.mkdLocalMasterList = (getMkd_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.RoutingAdrController routing_adr_loader = algo.routing_adr_loader.getController();
            algo.routing_adr_tableView =(routing_adr_loader.getType());
            algo.routingAdrMasterList = (routing_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.RoutingLocalController routing_local_loader = algo.routing_local_loader.getController();
            algo.routing_local_tableView = (routing_local_loader.getType());
            algo.routingLocalMasterList =(routing_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.PanelPositionsController panel_local_loader = algo.panel_positions_loader.getController();
            algo.panel_positions_tableView = (panel_local_loader.getType());

            Repository.strategy.put(algo.nameAlgo, algo);

            algo.iniziale(true, true, true, true, true);


        }catch (Exception e){
            Helper.exception(e);
        }
    }

}