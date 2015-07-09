package com.larrainvial.logviwer;

import com.larrainvial.logviwer.controller.algos.*;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.event.readlog.*;
import com.larrainvial.logviwer.event.sendtoview.*;
import com.larrainvial.logviwer.event.stringtofix.*;
import com.larrainvial.logviwer.listener.AlertListener;
import com.larrainvial.logviwer.listener.readlog.*;
import com.larrainvial.logviwer.listener.sendtoview.*;
import com.larrainvial.logviwer.listener.stringtofix.*;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.SwitchButton;
import com.larrainvial.trading.emp.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Algo {

    public String nameAlgo;
    public String mkdDolar;
    public String mkdLocal;
    public String mkdAdr;
    public String routingLocal;
    public String routingAdr;
    public double time;

    public FXMLLoader mkdDolarLoader = new FXMLLoader();
    public FXMLLoader mkdLocalLoader = new FXMLLoader();
    public FXMLLoader mkdAdrLoader = new FXMLLoader();
    public FXMLLoader routingAdrLoader = new FXMLLoader();
    public FXMLLoader routingLocalLoader = new FXMLLoader();
    public FXMLLoader panelPositionsLoader = new FXMLLoader();
    public FXMLLoader lastPriceLoader = new FXMLLoader();

    public ObservableList<ModelMarketData> dolarMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelMarketData> dolarFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> mkdAdrMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelMarketData> mkdAdrFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> mkdLocalMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelMarketData> mkdLocalFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> routingAdrMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelRoutingData> routingAdrFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> routingLocalMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelRoutingData> routingLocalFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> routingBlotterMasterLsit = FXCollections.observableArrayList();
    public ObservableList<ModelRoutingData> routingBlotterFilterLsit = FXCollections.observableArrayList();

    public ObservableList<ModelPositions> positionsMasterList = FXCollections.observableArrayList();
    public Map<String,ModelPositions> positionsMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelPositions>());
    public HashMap<String, Integer> positions = new HashMap<String, Integer>();
    public int countPositions = 0;

    public ObservableList<ModelMarketData> lastPriceMasterList = FXCollections.observableArrayList();
    public Map<String,ModelMarketData> lastPriceMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelMarketData>());
    public HashMap<String, Integer> lastPrice = new HashMap<String, Integer>();
    public int countLastPrice = 0;

    public TableView<ModelMarketData> mkdDolarTableView;
    public TableView<ModelMarketData> mkdAdrTableView;
    public TableView<ModelMarketData> mkdLocalTableView;
    public TableView<ModelRoutingData> routingAdrTableView;
    public TableView<ModelRoutingData> routingLocalTableView;
    public TableView<ModelPositions> panelPositionsTableView;
    public TableView<ModelMarketData> lastPriceTableView;

    public boolean mkdDolarToggle = false;
    public boolean mkdLocalToggle = false;
    public boolean mkdAdrToggle = false;
    public boolean routingLocalToggle = false;
    public boolean routingAdrToggle = false;
    public boolean alert = false;

    public TimerTask timerTask;

    public File fileMkdDolar;
    public File fileMkdLocal;
    public File fileMkdAdr;
    public File fileRoutingLocal;
    public File fileRoutingAdr;

    public FileInputStream inputStreamMkdDolar;
    public FileInputStream inputStreamMkdLocal;
    public FileInputStream inputStreamMkdAdr;
    public FileInputStream inputStreamRoutingLocal;
    public FileInputStream inputStreamRoutingAdr;

    public ReadFromDolarListener readFromDolarListener;
    public ReadLogMkdAdrListener readLogMkdAdrListener;
    public ReadLogMkdLocalListener readLogMkdLocalListener;
    public ReadlogRoutingAdrListener readlogRoutingAdrListener;
    public ReadLogRoutingLocalListener readLogRoutingLocalListener;
    public DolarViewListener dolarViewListener;
    public LastPriceListener lastPriceListener;
    public MarketDataAdrViewListener marketDataAdrViewListener;
    public MarketDataLocalViewListener marketDataLocalViewListener;
    public PositionViewListener positionViewListener;
    public RoutingAdrViewListener routingAdrViewListener;
    public RoutingLocalViewListener routingLocalViewListener;
    public DolarListener dolarListener;
    public MarketDataAdrListener marketDataAdrListener;
    public MarketDataLocalListener marketDataLocalListener;
    public RoutingAdrListener routingAdrListener;
    public RoutingLocalListener routingLocalListener;
    public AlertListener alertListener;


    public Algo(Element elem, int tab) {

        try {

            this.nameAlgo = elem.getElementsByTagName("nameAlgo").item(0).getChildNodes().item(0).getNodeValue();
            System.out.println(this.nameAlgo);
            this.mkdDolar = elem.getElementsByTagName("mkdDolar").item(0).getChildNodes().item(0).getNodeValue();
            this.mkdLocal =  elem.getElementsByTagName("mkdLocal").item(0).getChildNodes().item(0).getNodeValue();
            this.mkdAdr = elem.getElementsByTagName("mkdAdr").item(0).getChildNodes().item(0).getNodeValue();
            this.routingLocal = elem.getElementsByTagName("routingLocal").item(0).getChildNodes().item(0).getNodeValue();
            this.routingAdr = elem.getElementsByTagName("routingAdr").item(0).getChildNodes().item(0).getNodeValue();
            this.time = Double.valueOf(elem.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue());

            String location = elem.getElementsByTagName("location").item(0).getChildNodes().item(0).getNodeValue();
            String mkd_dolar = elem.getElementsByTagName("mkd_dolar").item(0).getChildNodes().item(0).getNodeValue();
            String mkd_nyse = elem.getElementsByTagName("mkd_nyse").item(0).getChildNodes().item(0).getNodeValue();
            String mkd_local = elem.getElementsByTagName("mkd_local").item(0).getChildNodes().item(0).getNodeValue();
            String routing_local = elem.getElementsByTagName("routing_local").item(0).getChildNodes().item(0).getNodeValue();
            String routing_nyse = elem.getElementsByTagName("routing_nyse").item(0).getChildNodes().item(0).getNodeValue();

            boolean booleanDolar = Boolean.valueOf(elem.getElementsByTagName("booleanDolar").item(0).getChildNodes().item(0).getNodeValue());
            boolean booleanMLocal = Boolean.valueOf(elem.getElementsByTagName("booleanMLocal").item(0).getChildNodes().item(0).getNodeValue());
            boolean booleanMAdr = Boolean.valueOf(elem.getElementsByTagName("booleanMAdr").item(0).getChildNodes().item(0).getNodeValue());
            boolean booleanRLocal = Boolean.valueOf(elem.getElementsByTagName("booleanRLocal").item(0).getChildNodes().item(0).getNodeValue());
            boolean booleanRAdr = Boolean.valueOf(elem.getElementsByTagName("booleanRAdr").item(0).getChildNodes().item(0).getNodeValue());

            fileMkdDolar = new File(location + mkd_dolar + Repository.year + ".log");
            fileMkdLocal = new File(location + mkd_local + Repository.year + ".log");
            fileMkdAdr = new File(location + mkd_nyse + Repository.year + ".log");
            fileRoutingLocal = new File(location + routing_local + Repository.year + ".log");
            fileRoutingAdr = new File(location + routing_nyse + Repository.year + ".log");

            this.fileReader(booleanDolar, booleanMLocal, booleanMAdr, booleanRLocal, booleanRAdr);

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(time));
            opacityLevel.setLayoutX(25);
            opacityLevel.setLayoutY(37);

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    time = new_val.doubleValue();
                }
            });

            SwitchButton switchButtonDolar = new SwitchButton("Dolar", this);
            Button switchBtn1 = switchButtonDolar.returnButton();
            switchBtn1.setLayoutX(177);
            switchBtn1.setLayoutY(35);

            SwitchButton switchButtonMkd_nyse = new SwitchButton("MKD ADR", this);
            Button switchBtn2 = switchButtonMkd_nyse.returnButton();
            switchBtn2.setLayoutX(305);
            switchBtn2.setLayoutY(35);

            SwitchButton switchButtonMkd_Local = new SwitchButton("MKD Local", this);
            Button switchBtn3 = switchButtonMkd_Local.returnButton();
            switchBtn3.setLayoutX(435);
            switchBtn3.setLayoutY(35);

            SwitchButton switchButtonRouting_Local = new SwitchButton("Routing Local", this);
            Button switchBtn4 = switchButtonRouting_Local.returnButton();
            switchBtn4.setLayoutX(565);
            switchBtn4.setLayoutY(35);

            SwitchButton switchButtonRouting_Adr = new SwitchButton("Routing ADR", this);
            Button switchBtn5 = switchButtonRouting_Adr.returnButton();
            switchBtn5.setLayoutX(695);
            switchBtn5.setLayoutY(35);

            SwitchButton switchButtonAlert = new SwitchButton("Alert", this);
            Button switchBtn6 = switchButtonAlert.returnButton();
            switchBtn6.setLayoutX(825);
            switchBtn6.setLayoutY(35);

            mkdDolarLoader.setLocation(MainLogViwer.class.getResource("view/algos/MarketDataDolarView.fxml"));
            mkdAdrLoader.setLocation(MainLogViwer.class.getResource("view/algos/MarketDataAdrView.fxml"));
            mkdLocalLoader.setLocation(MainLogViwer.class.getResource("view/algos/MarketDataLocalView.fxml"));
            routingAdrLoader.setLocation(MainLogViwer.class.getResource("view/algos/RoutingAdrView.fxml"));
            routingLocalLoader.setLocation(MainLogViwer.class.getResource("view/algos/RoutingLocalView.fxml"));
            panelPositionsLoader.setLocation(MainLogViwer.class.getResource("view/algos/PanelPositionsView.fxml"));
            lastPriceLoader.setLocation(MainLogViwer.class.getResource("view/algos/LastPriceView.fxml"));

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) mkdDolarLoader.load());
            anchorPane.getChildren().add((AnchorPane) mkdAdrLoader.load());
            anchorPane.getChildren().add((AnchorPane) mkdLocalLoader.load());
            anchorPane.getChildren().add((AnchorPane) routingAdrLoader.load());
            anchorPane.getChildren().add((AnchorPane) routingLocalLoader.load());
            anchorPane.getChildren().add((AnchorPane) panelPositionsLoader.load());
            anchorPane.getChildren().add((AnchorPane) lastPriceLoader.load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn2);
            anchorPane.getChildren().add(switchBtn3);
            anchorPane.getChildren().add(switchBtn4);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);

            ScrollPane scrollBar = new ScrollPane();
            scrollBar.prefWidthProperty().bind(anchorPane.widthProperty());
            scrollBar.prefHeightProperty().bind(anchorPane.heightProperty());
            scrollBar.setContent(anchorPane);
            scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            Repository.tabPanePrincipalTabPanel.getTabs().get(tab+2).setContent(scrollBar);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab+2).setText(this.nameAlgo);

            MarketDataDolarController mkdDolarLoader = this.mkdDolarLoader.getController();
            mkdDolarTableView = (mkdDolarLoader.getType());
            dolarMasterList = (mkdDolarLoader.masterData);

            MarketDataAdrController mkdAdrLoader = this.mkdAdrLoader.getController();
            mkdAdrTableView = mkdAdrLoader.getType();
            mkdAdrMasterList = mkdAdrLoader.masterData;

            MarketDataLocalController mkdLocalLoader = this.mkdLocalLoader.getController();
            mkdLocalTableView = mkdLocalLoader.getType();
            mkdLocalMasterList = mkdLocalLoader.masterData;

            RoutingAdrController routingAdrLoader = this.routingAdrLoader.getController();
            routingAdrTableView = routingAdrLoader.getType();
            routingAdrMasterList = routingAdrLoader.masterData;

            RoutingLocalController routingLocalLoader = this.routingLocalLoader.getController();
            routingLocalTableView = routingLocalLoader.getType();
            routingLocalMasterList = routingLocalLoader.masterData;

            PanelPositionsController panelLocalLoader = panelPositionsLoader.getController();
            panelPositionsTableView = panelLocalLoader.getType();

            LastPriceController lastPriceLocal = this.lastPriceLoader.getController();
            lastPriceTableView = lastPriceLocal.getType();

            readFromDolarListener = new ReadFromDolarListener(this);
            readLogMkdAdrListener = new ReadLogMkdAdrListener(this);
            readLogMkdLocalListener = new ReadLogMkdLocalListener(this);
            readlogRoutingAdrListener = new ReadlogRoutingAdrListener(this);
            readLogRoutingLocalListener = new ReadLogRoutingLocalListener(this);

            dolarListener =  new DolarListener(this);
            marketDataAdrListener = new MarketDataAdrListener(this);
            marketDataLocalListener = new MarketDataLocalListener(this);
            routingAdrListener = new RoutingAdrListener(this);
            routingLocalListener = new RoutingLocalListener(this);
            alertListener = new AlertListener(this);

            lastPriceListener = new LastPriceListener(this);
            dolarViewListener = new DolarViewListener(this);
            marketDataAdrViewListener = new MarketDataAdrViewListener(this);
            marketDataLocalViewListener = new MarketDataLocalViewListener(this);
            positionViewListener = new PositionViewListener(this);
            routingAdrViewListener = new RoutingAdrViewListener(this);
            routingLocalViewListener = new RoutingLocalViewListener(this);

            Controller.addEventListener(AlertEvent.class, alertListener);
            Controller.addEventListener(ReadFromDolarEvent.class, readFromDolarListener);
            Controller.addEventListener(ReadLogMkdAdrEvent.class, readLogMkdAdrListener);
            Controller.addEventListener(ReadLogMkdLocalEvent.class, readLogMkdLocalListener);
            Controller.addEventListener(ReadlogRoutingAdrEvent.class, readlogRoutingAdrListener);
            Controller.addEventListener(ReadLogRoutingLocalEvent.class, readLogRoutingLocalListener);

            Controller.addEventListener(LastPriceEvent.class, lastPriceListener);

            Controller.addEventListener(DolarEvent.class, dolarListener);
            Controller.addEventListener(MarketDataADREvent.class, marketDataAdrListener);
            Controller.addEventListener(MarketDataLocalEvent.class, marketDataLocalListener);
            Controller.addEventListener(RoutingAdrEvent.class, routingAdrListener);
            Controller.addEventListener(RoutingLocalEvent.class, routingLocalListener);

            Controller.addEventListener(DolarViewEvent.class, dolarViewListener);
            Controller.addEventListener(MarketDataAdrViewEvent.class, marketDataAdrViewListener);
            Controller.addEventListener(MarketDataLocalViewEvent.class, marketDataLocalViewListener);
            Controller.addEventListener(PositionViewEvent.class, positionViewListener);
            Controller.addEventListener(RoutingAdrViewEvent.class, routingAdrViewListener);
            Controller.addEventListener(RoutingLocalViewEvent.class, routingLocalViewListener);

            Repository.strategy.put(this.nameAlgo, this);
            start(booleanDolar, booleanMLocal, booleanMAdr, booleanRLocal, booleanRAdr);

        } catch (Exception e){
            Helper.exception(e);
        }
    }


    public void fileReader(boolean dolar, boolean mLocal, boolean mAdr, boolean rLocal, boolean rAdr) throws Exception {

        if(dolar)  inputStreamMkdDolar = new FileInputStream(fileMkdDolar);
        if(mLocal) inputStreamMkdLocal = new FileInputStream(fileMkdLocal);
        if(mAdr)   inputStreamMkdAdr = new FileInputStream(fileMkdAdr);
        if(rLocal) inputStreamRoutingLocal = new FileInputStream(fileRoutingLocal);
        if(rAdr)   inputStreamRoutingAdr = new FileInputStream(fileRoutingAdr);
    }

    public void start(boolean dolar, boolean mLocal, boolean mAdr, boolean rLocal, boolean rAdr) throws Exception {

        final double finalTimer_initial = this.time;
        stopTimer();

        timerTask = new TimerTask(){

            public void run() {

                if(finalTimer_initial != time) {

                    try {
                        start(dolar, mLocal, mAdr, rLocal, rAdr);
                    } catch (Exception e) {
                        Helper.exception(e);
                    }
                }

                Algo algo = Repository.strategy.get(nameAlgo);

                if (mkdDolarToggle && dolar) {
                    Controller.dispatchEvent(new ReadFromDolarEvent(algo));
                }

                if (mkdAdrToggle && mAdr) {
                    Controller.dispatchEvent(new ReadLogMkdAdrEvent(algo));
                }

                if (mkdLocalToggle && mLocal) {
                    Controller.dispatchEvent(new ReadLogMkdLocalEvent(algo));
                }

                if (routingLocalToggle && rLocal) {
                    Controller.dispatchEvent(new ReadLogRoutingLocalEvent(algo));
                }

                if (routingAdrToggle && rAdr) {
                    Controller.dispatchEvent(new ReadlogRoutingAdrEvent(algo));

                }

            }

        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1, (int) this.time * 1000);

    }

    public void stopTimer() {

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

}