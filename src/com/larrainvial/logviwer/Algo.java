package com.larrainvial.logviwer;

import com.larrainvial.logviwer.controller.algos.LastPriceController;
import com.larrainvial.logviwer.controller.algos.PanelPositionsController;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.event.readlog.*;
import com.larrainvial.logviwer.event.utils.CalculateLastPriceEvent;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.stringtofix.*;
import com.larrainvial.logviwer.event.utils.CalculatePositionsEvent;
import com.larrainvial.logviwer.fxvo.Graph;
import com.larrainvial.logviwer.listener.util.AlertListener;
import com.larrainvial.logviwer.listener.readlog.*;
import com.larrainvial.logviwer.listener.util.CalculateLastPriceListener;
import com.larrainvial.logviwer.listener.sendtoview.LastPriceListener;
import com.larrainvial.logviwer.listener.sendtoview.PositionViewListener;
import com.larrainvial.logviwer.listener.stringtofix.*;
import com.larrainvial.logviwer.listener.util.CalculatePositionsListener;
import com.larrainvial.logviwer.model.Dolar;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.fxvo.SwitchButton;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.vo.LatencyVO;
import com.larrainvial.sellside.MainSellSide;
import com.larrainvial.sellside.controller.SellSideController;
import com.larrainvial.trading.emp.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileInputStream;
import java.net.Inet4Address;
import java.util.*;

public class Algo {

    private static Logger logger = Logger.getLogger(Algo.class.getName());

    public String nameAlgo;
    public String mkdDolar;
    public String mkdLocal;
    public String mkdAdr;
    public String routingLocal;
    public String routingAdr;
    public double time;
    public int countQtyOrderLocal = 0;

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
    public CalculatePositionsListener calculatePositionsListener;

    public LastPriceListener lastPriceListener;
    public PositionViewListener positionViewListener;
    public DolarListener dolarListener;
    public MarketDataAdrListener marketDataAdrListener;
    public MarketDataLocalListener marketDataLocalListener;
    public RoutingAdrListener routingAdrListener;
    public RoutingLocalListener routingLocalListener;
    public CalculateLastPriceListener calculateLastPriceListener;
    public AlertListener alertListener;

    //Button Options
    public Button buttonAlert;
    public Button buttonRoutingADR;
    public Button buttonRoutingLocal;
    public Button buttonMKDLocal;
    public Button buttonMDKDAdr;
    public Button buttonDolar;
    public Button graphButton;

    //GRAPH
    public XYChart.Series adrRouting;
    public XYChart.Series localRouting;
    public LineChart<Number,Number> lineChart;
    public boolean graphEnable  = false;

    //LATENCY
    public Map<String, LatencyVO> latencyADR = Collections.synchronizedMap(new HashMap<String, LatencyVO>());
    public Map<String, LatencyVO> latencyLocal = Collections.synchronizedMap(new HashMap<String, LatencyVO>());

    //count line file
    public int countDolar = 0;
    public int countMkdLocal = 0;
    public int countMdkAdr = 0;
    public int countRoutingLocal = 0;
    public int countRoutingAdr = 0;



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

            panelPositionsLoader.setLocation(MainLogViwer.class.getResource("view/algos/PanelPositionsView.fxml"));
            lastPriceLoader.setLocation(MainLogViwer.class.getResource("view/algos/LastPriceView.fxml"));

            fileMkdDolar = new File(location + mkd_dolar + Repository.year + ".log");
            fileMkdLocal = new File(location + mkd_local + Repository.year + ".log");
            fileMkdAdr = new File(location + mkd_nyse + Repository.year + ".log");
            fileRoutingLocal = new File(location + routing_local + Repository.year + ".log");
            fileRoutingAdr = new File(location + routing_nyse + Repository.year + ".log");

            this.fileReader(booleanDolar, booleanMLocal, booleanMAdr, booleanRLocal, booleanRAdr);

            HBox grill = new HBox();
            grill.getChildren().add((AnchorPane) lastPriceLoader.load());
            grill.getChildren().add((AnchorPane) panelPositionsLoader.load());


            HBox options = new HBox();
            options.setPrefHeight(100);

            options.setSpacing(20);
            options.setPadding(new Insets(40, 40, 20, 20));

            VBox general = new VBox();
            general.getChildren().addAll(options, grill);

            Slider opacityLevel = new Slider(1, 10, Double.valueOf(time));

            opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    time = new_val.doubleValue();
                }
            });

            VBox hBoxopacityLevel = new VBox();
            hBoxopacityLevel.setSpacing(10);
            hBoxopacityLevel.getChildren().add(opacityLevel);
            options.getChildren().add(hBoxopacityLevel);

            SwitchButton switchButtonAlert = new SwitchButton("Alert", this);
            Button switchBtn6 = switchButtonAlert.returnButton();
            this.buttonAlert =  switchBtn6;

            SwitchButton graphSwith = new SwitchButton("Graph", this);
            Button switchBtn7 = graphSwith.returnButton();
            this.graphButton = switchBtn7;

            VBox vBoxgraph = new VBox();
            vBoxgraph.getChildren().add(graphSwith);
            options.getChildren().add(vBoxgraph);

            VBox vBox = new VBox();
            vBox.getChildren().add(switchButtonAlert);
            options.getChildren().add(vBox);

            SwitchButton switchButtonDolar = new SwitchButton("Dolar", this);
            Button switchBtn1 = switchButtonDolar.returnButton();
            this.buttonDolar = switchBtn1;

            VBox ButtonDolar = new VBox();
            ButtonDolar.getChildren().add(switchButtonDolar);
            options.getChildren().add(ButtonDolar);

            SwitchButton switchButtonMkd_nyse = new SwitchButton("MKD ADR", this);
            Button switchBtn2 = switchButtonMkd_nyse.returnButton();
            this.buttonMDKDAdr = switchBtn2;

            VBox HBoxButtonMkd = new VBox();
            HBoxButtonMkd.getChildren().add(switchButtonMkd_nyse);
            options.getChildren().add(HBoxButtonMkd);

            SwitchButton switchButtonMkd_Local = new SwitchButton("MKD Local", this);
            Button switchBtn3 = switchButtonMkd_Local.returnButton();
            this.buttonMKDLocal = switchBtn3;

            VBox HBoswitchButtonMkd_Local = new VBox();
            HBoswitchButtonMkd_Local.getChildren().add(switchButtonMkd_Local);
            options.getChildren().add(HBoswitchButtonMkd_Local);

            SwitchButton switchButtonRouting_Local = new SwitchButton("Routing Local", this);
            Button switchBtn4 = switchButtonRouting_Local.returnButton();
            this.buttonRoutingLocal = switchBtn4;

            VBox HBosRouting_Local = new VBox();
            HBosRouting_Local.getChildren().add(switchButtonRouting_Local);
            options.getChildren().add(HBosRouting_Local);

            SwitchButton switchButtonRouting_Adr = new SwitchButton("Routing ADR", this);
            Button switchBtn5 = switchButtonRouting_Adr.returnButton();
            this.buttonRoutingADR = switchBtn5;

            VBox HBosRouting_Adr = new VBox();
            HBosRouting_Adr.getChildren().add(switchButtonRouting_Adr);
            options.getChildren().add(HBosRouting_Adr);




            if (nameAlgo.startsWith("ADR")){

                Label labelCofx = new Label("COFX VAR ");
                labelCofx.setTranslateY(3);
                labelCofx.setStyle("-fx-font-weight: bold");

                TextField cofxvar = new TextField();
                cofxvar.setPrefWidth(60);

                Label labelCAD = new Label("CAD VAR ");
                labelCAD.setTranslateY(3);
                labelCAD.setStyle("-fx-font-weight: bold");

                TextField cadvar = new TextField();
                cadvar.setPrefWidth(60);

                Label labelCLP = new Label("CLP VAR ");
                labelCLP.setTranslateY(3);
                labelCLP.setStyle("-fx-font-weight: bold");

                TextField clpvar = new TextField();
                clpvar.setPrefWidth(60);

                HBox variacion = new HBox();
                variacion.setSpacing(10);
                variacion.setPadding(new Insets(0, 0, 0, 80));
                variacion.getChildren().addAll(labelCAD, cadvar, labelCofx, cofxvar, labelCLP, clpvar);
                options.getChildren().add(variacion);


                HBox submit = new HBox();
                Button save = new Button("Save");
                save.setPrefWidth(80);
                submit.getChildren().add(save);

                options.getChildren().add(submit);

                save.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent e) {
                        if (cofxvar.getText() != null){
                            if (Helper.isNumber(cofxvar.getText())) {
                                Dolar.setVARIACION_COFX(Double.valueOf(cofxvar.getText()));
                                Notifier.INSTANCE.notifySuccess("COFX Index", "Variacion: " + cofxvar.getText());
                                logger.info(cofxvar.getText());
                            }

                        }

                        if (cadvar.getText() != null) {
                            if (Helper.isNumber(cadvar.getText())) {
                                Dolar.setVARIACION_CAD(Double.valueOf(cadvar.getText()));
                                Notifier.INSTANCE.notifySuccess("CAD Curncy", "Variacion: " + cadvar.getText());
                                logger.info(Dolar.VARIACION_CAD);
                            }
                        }

                        if (clpvar.getText() != null) {
                            if (Helper.isNumber(clpvar.getText())) {
                                Dolar.setVARIACION_CLP(Double.valueOf(clpvar.getText()));
                                Notifier.INSTANCE.notifySuccess("CLP", "Variacion: " + clpvar.getText());
                                logger.info(Dolar.VARIACION_CLP);
                            }
                        }
                    }
                });

            }

            Graph.newLineChart(this);

            HBox graph = new HBox();
            graph.setPrefHeight(400);
            graph.getChildren().add(lineChart);
            general.getChildren().add(graph);

            ScrollPane scrollBar = new ScrollPane();
            scrollBar.prefHeightProperty().bind(general.heightProperty());
            scrollBar.prefHeightProperty().bind(general.heightProperty());
            scrollBar.setContent(general);
            scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            Repository.tabPanePrincipalTabPanel.getTabs().get(tab+1).setContent(scrollBar);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab+1).setText(this.nameAlgo);

            PanelPositionsController panelLocalLoader = panelPositionsLoader.getController();
            panelPositionsTableView = panelLocalLoader.getType();

            LastPriceController lastPriceLocal = this.lastPriceLoader.getController();
            lastPriceTableView = lastPriceLocal.getType();

            readFromDolarListener = new ReadFromDolarListener(this);
            readLogMkdAdrListener = new ReadLogMkdAdrListener(this);
            readLogMkdLocalListener = new ReadLogMkdLocalListener(this);
            readlogRoutingAdrListener = new ReadlogRoutingAdrListener(this);
            readLogRoutingLocalListener = new ReadLogRoutingLocalListener(this);
            calculateLastPriceListener = new CalculateLastPriceListener(this);

            dolarListener =  new DolarListener(this);
            marketDataAdrListener = new MarketDataAdrListener(this);
            marketDataLocalListener = new MarketDataLocalListener(this);
            routingAdrListener = new RoutingAdrListener(this);
            routingLocalListener = new RoutingLocalListener(this);
            alertListener = new AlertListener(this);
            calculatePositionsListener = new CalculatePositionsListener(this);
            lastPriceListener = new LastPriceListener(this);
            positionViewListener = new PositionViewListener(this);

            Controller.addEventListener(AlertEvent.class, alertListener);
            Controller.addEventListener(ReadFromDolarEvent.class, readFromDolarListener);
            Controller.addEventListener(ReadLogMkdAdrEvent.class, readLogMkdAdrListener);
            Controller.addEventListener(ReadLogMkdLocalEvent.class, readLogMkdLocalListener);
            Controller.addEventListener(ReadlogRoutingAdrEvent.class, readlogRoutingAdrListener);
            Controller.addEventListener(ReadLogRoutingLocalEvent.class, readLogRoutingLocalListener);
            Controller.addEventListener(CalculateLastPriceEvent.class, calculateLastPriceListener);
            Controller.addEventListener(CalculatePositionsEvent.class, calculatePositionsListener);

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
            e.printStackTrace();
            Helper.printerLog(e.toString());
            Notifier.INSTANCE.notifyError("Error", e.toString());
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

            Label port = new Label("Port : " + com.larrainvial.sellside.Repository.socketAcceptPort );
            port.setLayoutX(30);
            port.setLayoutY(100);

            Label sender = new Label("Sender : " + com.larrainvial.sellside.Repository.senderCompID);
            sender.setLayoutX(170);
            sender.setLayoutY(80);

            Label target = new Label("Target : " + com.larrainvial.sellside.Repository.targetCompID);
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
            Helper.printerLog(e.toString());
            Notifier.INSTANCE.notifyError("Error", e.toString());
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
                        e.printStackTrace();
                        Helper.printerLog(e.toString());
                        Notifier.INSTANCE.notifyError("Error", e.toString());
                    }
                }

                Algo algo = Repository.strategy.get(nameAlgo);

                if (mkdDolarToggle && dolar) {
                    //Controller.dispatchEvent(new ReadFromDolarEvent(algo));
                }

                if (mkdAdrToggle && mAdr) {
                    //Controller.dispatchEvent(new ReadLogMkdAdrEvent(algo));
                }

                if (mkdLocalToggle && mLocal) {
                    //Controller.dispatchEvent(new ReadLogMkdLocalEvent(algo));
                }

                if (routingLocalToggle && rLocal) {
                    //Controller.dispatchEvent(new ReadLogRoutingLocalEvent(algo));
                }

                if (routingAdrToggle && rAdr) {
                    //Controller.dispatchEvent(new ReadlogRoutingAdrEvent(algo));
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


