package com.larrainvial.logviwer;

import com.larrainvial.logviwer.controller.algos.LastPriceController;
import com.larrainvial.logviwer.controller.algos.PanelPositionsController;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.event.readlog.*;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.stringtofix.*;
import com.larrainvial.logviwer.listener.AlertListener;
import com.larrainvial.logviwer.listener.readlog.*;
import com.larrainvial.logviwer.listener.sendtoview.LastPriceListener;
import com.larrainvial.logviwer.listener.sendtoview.PositionViewListener;
import com.larrainvial.logviwer.listener.stringtofix.*;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Dialog;
import com.larrainvial.logviwer.utils.SwitchButton;
import com.larrainvial.sellside.MainSellSide;
import com.larrainvial.sellside.controller.SellSideController;
import com.larrainvial.trading.emp.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.net.Inet4Address;
import java.util.*;

public class Algo {

    public String nameAlgo;
    public String mkdDolar;
    public String mkdLocal;
    public String mkdAdr;
    public String routingLocal;
    public String routingAdr;
    public double time;

    public FXMLLoader panelPositionsLoader = new FXMLLoader();
    public FXMLLoader sellSideLoader = new FXMLLoader();
    public FXMLLoader lastPriceLoader = new FXMLLoader();

    public ObservableList<ModelPositions> positionsMasterList = FXCollections.observableArrayList();
    public Map<String,ModelPositions> positionsMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelPositions>());
    public HashMap<String, Integer> positions = new HashMap<String, Integer>();
    public int countPositions = 0;

    public ObservableList<ModelMarketData> lastPriceMasterList = FXCollections.observableArrayList();
    public Map<String,ModelMarketData> lastPriceMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelMarketData>());
    public HashMap<String, Integer> lastPrice = new HashMap<String, Integer>();
    public int countLastPrice = 0;

    public TableView<ModelPositions> panelPositionsTableView;
    public TableView<ModelMarketData> lastPriceTableView;
    public TableView<ModelRoutingData> sellsideTableView;

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

    public LastPriceListener lastPriceListener;
    public PositionViewListener positionViewListener;
    public DolarListener dolarListener;
    public MarketDataAdrListener marketDataAdrListener;
    public MarketDataLocalListener marketDataLocalListener;
    public RoutingAdrListener routingAdrListener;
    public RoutingLocalListener routingLocalListener;
    public AlertListener alertListener;


    public Algo(Element elem, int tab) {

        try {

            this.nameAlgo = elem.getElementsByTagName("nameAlgo").item(0).getChildNodes().item(0).getNodeValue();
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

            panelPositionsLoader.setLocation(MainLogViwer.class.getResource("view/algos/PanelPositionsView.fxml"));
            lastPriceLoader.setLocation(MainLogViwer.class.getResource("view/algos/LastPriceView.fxml"));

            AnchorPane anchorPane = new AnchorPane();
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
            positionViewListener = new PositionViewListener(this);

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

            Controller.addEventListener(PositionViewEvent.class, positionViewListener);

            Repository.strategy.put(this.nameAlgo, this);
            start(booleanDolar, booleanMLocal, booleanMAdr, booleanRLocal, booleanRAdr);

        } catch (Exception e){
            Dialog.exception(e);
        }
    }


    public Algo(int tab) {

        try {

            this.nameAlgo = "Sell Side";
            this.time = 1d;

            SwitchButton switchButtonDolar = new SwitchButton("Strart", this);
            Button switchBtn1 = switchButtonDolar.returnButton();
            switchBtn1.setLayoutX(30);
            switchBtn1.setLayoutY(35);

            SwitchButton switchButtonAlert = new SwitchButton("Alert", this);
            Button switchBtn6 = switchButtonAlert.returnButton();
            switchBtn6.setLayoutX(160);
            switchBtn6.setLayoutY(35);

            Label ip = new Label("IP Server : " + Inet4Address.getLocalHost().getHostAddress());
            ip.setLayoutX(30);
            ip.setLayoutY(80);

            Label port = new Label("Port : 6060");
            port.setLayoutX(30);
            port.setLayoutY(100);

            Label sender = new Label("Sender : LOGVIEWER");
            sender.setLayoutX(170);
            sender.setLayoutY(80);

            Label target = new Label("Target : CLIENT");
            target.setLayoutX(170);
            target.setLayoutY(100);



            sellSideLoader.setLocation(MainSellSide.class.getResource("view/SellSide.fxml"));

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) sellSideLoader.load());
            anchorPane.getChildren().add(switchBtn1);
            anchorPane.getChildren().add(switchBtn6);
            anchorPane.getChildren().add(ip);
            anchorPane.getChildren().add(sender);
            anchorPane.getChildren().add(target);
            anchorPane.getChildren().add(port);

            ScrollPane scrollBar = new ScrollPane();
            scrollBar.prefWidthProperty().bind(anchorPane.widthProperty());
            scrollBar.prefHeightProperty().bind(anchorPane.heightProperty());
            scrollBar.setContent(anchorPane);
            scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(scrollBar);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(this.nameAlgo);

            SellSideController sellsideLoader = sellSideLoader.getController();
            sellsideTableView = sellsideLoader.getType();

            Repository.strategy.put(this.nameAlgo, this);

        } catch (Exception e){
            e.printStackTrace();
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
                        Dialog.exception(e);
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