package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.MainApp;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

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
        //initializeAdrArbitrageTest();
        //initializeAdrArbitrageTest();
    }



    private static  void initializeSellSide(int tab){

        try {

            Algo algo = new Algo();

            algo.setNameAlgo("Sell Sides");
            algo.setRouting_adr("ROUTING_ADR");
            algo.setRouting_local("ROUTING_LOCAL");

            Date fechaActual = new Date();
            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            String location = "src\\resources\\Quickfix\\log\\";
            String routing_nyse = "FIX.4.4-LVSSG_ARB-LVARB.messages_";
            String log = ".log";

            algo.setFile_routing_adr(new File(location + routing_nyse + year + log));
            algo.fileReader();

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.getTime()));
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
                }
            });

            SwitchButton switchButtonDolar = new SwitchButton("Sell Side", algo);
            Button switchBtn1 = switchButtonDolar.returnButton();
            switchBtn1.setLayoutX(177);
            switchBtn1.setLayoutY(10);

            algo.getRouting_local_loader().setLocation(MainApp.class.getResource("view/sellside/RoutingAdrView.fxml"));

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_dolar_loader().load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(anchorPane);


            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.getNameAlgo());

            //RoutingAdrController getMkd_dolar_loader = algo.getMkd_dolar_loader().getController();
            //algo.setMkd_dolar_tableView(getMkd_dolar_loader.getType());
            //algo.setDolarMasterList(getMkd_dolar_loader.masterData);

            Repository.strategy.put(algo.getNameAlgo(), algo);
            algo.iniziale();

        } catch (Exception e){
            Helper.exception(e);
        }

    }

    private static void initializeAdrArbitrageXSGO(int tab){

        try {

            Algo algo = new Algo();

            algo.setNameAlgo("ADRArbitrage XSGO");
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
            //String location = "src\\resources\\log\\AdrArbitrageIB\\";
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
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
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
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.getNameAlgo());

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


        } catch (Exception e){
            Helper.exception(e);
        }
    }

    private static void initializeAdrArbitrageXTSE(int tab){

        try {

            Algo algo = new Algo();

            algo.setNameAlgo("ADRArbitrage XTSE");
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
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
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
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn2);
            anchorPane.getChildren().add(switchBtn3);
            anchorPane.getChildren().add(switchBtn4);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);

            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.getNameAlgo());


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
            Helper.exception(e);
        }
    }

    private static void initializeAdrArbitrageXBOG(int tab){

        try {

            Algo algo = new Algo();

            algo.setNameAlgo("ADRArbitrage XBOG");
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
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
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
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn2);
            anchorPane.getChildren().add(switchBtn3);
            anchorPane.getChildren().add(switchBtn4);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);

            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.getNameAlgo());

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
            Helper.exception(e);
        }
    }

    private static void initializeAdrArbitrageTest(int tab){

        try {

            Algo algo = new Algo();

            algo.setNameAlgo("ADRArbitrage TEST");
            algo.setMkd_dolar("MKD_DOLAR");
            algo.setMkd_adr("MKD_NYSE");
            algo.setMkd_local("MKD_XSGO");
            algo.setRouting_adr("ROUTING_ADR");
            algo.setRouting_local("ROUTING_LOCAL");
            algo.setTime(1);

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


            algo.setFile_mkd_dolar(new File(location + mkd_dolar + year + log));
            algo.setFile_mkd_local(new File(location + mkd_local + year + log));
            algo.setFile_mkd_adr(new File(location + mkd_nyse + year + log));
            algo.setFile_routing_local(new File(location + routing_local + year + log));
            algo.setFile_routing_adr(new File(location + routing_nyse + year + log));

            algo.fileReader();

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(algo.getTime()));
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(13);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    algo.setTime(new_val.doubleValue());
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


            algo.getMkd_dolar_loader().setLocation(MainApp.class.getResource("view/adrarbitragetest/MarketDataDolarView.fxml"));
            algo.getMkd_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragetest/MarketDataAdrView.fxml"));
            algo.getMkd_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragetest/MarketDataLocalView.fxml"));
            algo.getRouting_adr_loader().setLocation(MainApp.class.getResource("view/adrarbitragetest/RoutingAdrView.fxml"));
            algo.getRouting_local_loader().setLocation(MainApp.class.getResource("view/adrarbitragetest/RoutingLocalView.fxml"));
            algo.getPanel_positions_loader().setLocation(MainApp.class.getResource("view/adrarbitragetest/PanelPositionsView.fxml"));


            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_dolar_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getMkd_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_adr_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getRouting_local_loader().load());
            anchorPane.getChildren().add((AnchorPane) algo.getPanel_positions_loader().load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn2);
            anchorPane.getChildren().add(switchBtn3);
            anchorPane.getChildren().add(switchBtn4);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);

            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.getNameAlgo());

            com.larrainvial.logviwer.controller.adrarbitragetest.MarketDataDolarController getMkd_dolar_loader = algo.getMkd_dolar_loader().getController();
            algo.setMkd_dolar_tableView(getMkd_dolar_loader.getType());
            algo.setDolarMasterList(getMkd_dolar_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.MarketDataAdrController getMkd_adr_loader = algo.getMkd_adr_loader().getController();
            algo.setMkd_adr_tableView(getMkd_adr_loader.getType());
            algo.setMkdAdrMasterList(getMkd_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.MarketDataLocalController getMkd_local_loader = algo.getMkd_local_loader().getController();
            algo.setMkd_local_tableView(getMkd_local_loader.getType());
            algo.setMkdLocalMasterList(getMkd_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.RoutingAdrController routing_adr_loader = algo.getRouting_adr_loader().getController();
            algo.setRouting_adr_tableView(routing_adr_loader.getType());
            algo.setRoutingAdrMasterList(routing_adr_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.RoutingLocalController routing_local_loader = algo.getRouting_local_loader().getController();
            algo.setRouting_local_tableView(routing_local_loader.getType());
            algo.setRoutingLocalMasterList(routing_local_loader.masterData);

            com.larrainvial.logviwer.controller.adrarbitragetest.PanelPositionsController panel_local_loader = algo.getPanel_positions_loader().getController();
            algo.setPanel_positions_tableView(panel_local_loader.getType());

            Repository.strategy.put(algo.getNameAlgo(), algo);

            algo.iniziale();


        }catch (Exception e){
            Helper.exception(e);
        }
    }

}