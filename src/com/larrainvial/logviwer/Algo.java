package com.larrainvial.logviwer;

import com.larrainvial.logviwer.controller.SellSideController;
import com.larrainvial.logviwer.controller.algos.HardDiskController;
import com.larrainvial.logviwer.controller.algos.LastPriceController;
import com.larrainvial.logviwer.controller.algos.MMBCSController;
import com.larrainvial.logviwer.controller.algos.PanelPositionsController;
import com.larrainvial.logviwer.event.readlog.*;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.stringtofix.*;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.event.utils.CalculateLastPriceEvent;
import com.larrainvial.logviwer.event.utils.CalculatePositionsEvent;
import com.larrainvial.logviwer.fxvo.Graph;
import com.larrainvial.logviwer.fxvo.SwitchButton;
import com.larrainvial.logviwer.listener.alert.AlertListener;
import com.larrainvial.logviwer.listener.alert.AlertMarketMakerBCSListener;
import com.larrainvial.logviwer.listener.calculate.CalculateLastPriceListener;
import com.larrainvial.logviwer.listener.calculate.CalculatePositionsListener;
import com.larrainvial.logviwer.listener.readlog.*;
import com.larrainvial.logviwer.listener.sendtoview.LastPriceListener;
import com.larrainvial.logviwer.listener.sendtoview.PositionViewListener;
import com.larrainvial.logviwer.listener.stringtofix.*;
import com.larrainvial.logviwer.model.*;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.ExportExcel;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.sellside.MainSellSide;
import com.larrainvial.trading.emp.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileInputStream;
import java.net.Inet4Address;
import java.util.*;
import java.util.logging.Level;


public class Algo {

    private static Logger logger = Logger.getLogger(Algo.class.getName());

    public int countQtyOrderLocal;
    public int countLastPrice;
    public int countPositions;
    public int countMMBCC;

    public int countDolar;
    public int countMkdLocal;
    public int countMdkAdr;
    public int countRoutingLocal;
    public int countRoutingAdr;

    public boolean validateDolar = true;
    public boolean validateMKDLocal = true;
    public boolean validateMKDADR = true;
    public boolean validateRoutingLocal = true;
    public boolean validateRoutingADR = true;

    public String nameAlgo;

    public FXMLLoader panelPositionsLoader = new FXMLLoader();
    public FXMLLoader lastPriceLoader = new FXMLLoader();
    public FXMLLoader panelMMBCS = new FXMLLoader();
    public FXMLLoader panelHardDisk = new FXMLLoader();

    public Map<String, ModelPositions> positionsMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelPositions>());
    public HashMap<String, Integer> positions = new HashMap<String, Integer>();

    public Map<String, ModelMMBCS> mmBCSMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelMMBCS>());
    public HashMap<String, Integer> mmBCE = new HashMap<String, Integer>();

    public Map<String, ModelMarketData> lastPriceMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelMarketData>());
    public HashMap<String, Integer> lastPrice = new HashMap<String, Integer>();
    public Map<String, ModelLatency> latencyADR = Collections.synchronizedMap(new HashMap<String, ModelLatency>());
    public Map<String, ModelLatency> latencyLocal = Collections.synchronizedMap(new HashMap<String, ModelLatency>());

    public TableView<ModelPositions> panelPositionsTableView;
    public TableView<ModelMMBCS> panelMMBCSTableView;
    public TableView<ModelMarketData> lastPriceTableView;
    public TableView<ModelRoutingData> sellsideTableView = new TableView<ModelRoutingData>();

    public boolean mkdDolarToggle;
    public boolean mkdLocalToggle;
    public boolean mkdAdrToggle;
    public boolean routingLocalToggle;
    public boolean routingAdrToggle;
    public boolean alert;

    public TimerTask timerTask;

    public File fileMkdDolar;
    public File fileMkdLocal;
    public File fileMkdAdr;
    public File fileRoutingLocal;
    public File fileRoutingAdr;

    public FileInputStream inputStreamDolar;
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
    public AlertMarketMakerBCSListener alertMarketMakerBCSListener;

    public Button buttonAlert;
    public Button buttonRoutingADR;
    public Button buttonRoutingLocal;
    public Button buttonMKDLocal;
    public Button buttonMDKDAdr;
    public Button buttonDolar;
    public Button graphButton;

    public XYChart.Series adrRouting;
    public XYChart.Series localRouting;
    public LineChart<Number, Number> lineChart;
    public boolean graphEnable;

    public ModelXml modelXml;


    public Algo(Element elem, Stage primaryStage) {

        try {

            modelXml = new ModelXml();

            this.readXML(elem);
            this.fileReader(modelXml);
            this.nameAlgo = modelXml.nameAlgo;

            panelPositionsLoader.setLocation(Start.class.getResource("view/algos/PanelPositionsView.fxml"));
            lastPriceLoader.setLocation(Start.class.getResource("view/algos/LastPriceView.fxml"));
            panelMMBCS.setLocation(Start.class.getResource("view/algos/PanelMMBCS.fxml"));
            panelHardDisk.setLocation(Start.class.getResource("view/algos/HardDisk.fxml"));

            VBox optionsPanel = new VBox();
            optionsPanel.getStyleClass().add("optionsPanel");

            this.setOptionsPanel(optionsPanel);
            this.inputButtonReadLog(optionsPanel);

            HBox positionsPanel = new HBox();
            positionsPanel.getStyleClass().add("positionsPanel");

            positionsPanel.getChildren().add((modelXml.nameAlgo.equals(Constants.NameAlgo.MarketMakerBCS)) ?
                    (AnchorPane) panelMMBCS.load() : (AnchorPane) panelPositionsLoader.load());

            VBox lastPricePanel = new VBox();
            lastPricePanel.getStyleClass().add("lastPricePanel");
            lastPricePanel.getChildren().addAll((AnchorPane) lastPriceLoader.load());

            Graph.newLineChart(this);

            VBox graphPanel = new VBox();
            graphPanel.getStyleClass().add("graphPanel");
            graphPanel.getChildren().addAll(lineChart);

            VBox hardDiskPanel = new VBox();
            hardDiskPanel.getStyleClass().add("hardDiskPanel");
            hardDiskPanel.getChildren().add(panelHardDisk.load());

            HBox layout = new HBox();
            layout.setPrefWidth(primaryStage.getWidth());
            layout.setPrefHeight(primaryStage.getHeight());
            layout.getStyleClass().add("layoutStyle");
            layout.getChildren().addAll(optionsPanel, positionsPanel, lastPricePanel, graphPanel, hardDiskPanel);

            ScrollPane scrollBar = new ScrollPane();
            scrollBar.prefHeightProperty().bind(layout.maxHeightProperty());
            scrollBar.prefWidthProperty().bind(layout.maxWidthProperty());
            scrollBar.setContent(layout);
            scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            Repository.tabPanePrincipalTabPanel.getTabs().add(new Tab(modelXml.nameAlgo));
            Repository.tabPanePrincipalTabPanel.getTabs().get(Repository.tabPanePrincipalTabPanel.getTabs().size() - 1).setContent(scrollBar);

            LastPriceController lastPriceLocal = this.lastPriceLoader.getController();
            lastPriceTableView = lastPriceLocal.getType();

            HardDiskController hardDiskController = this.panelHardDisk.getController();

            Repository.hardDiskTableView = hardDiskController.getType();
            Repository.hardDiskTableView.getItems().addAll(Repository.listServerHardDisk);

            if (modelXml.nameAlgo.equals(Constants.NameAlgo.MarketMakerBCS)) {

                MMBCSController mMBCSController = this.panelMMBCS.getController();
                panelMMBCSTableView = mMBCSController.getType();

            } else {

                PanelPositionsController panelLocalLoader = this.panelPositionsLoader.getController();
                panelPositionsTableView = panelLocalLoader.getType();

                MenuItem positions = new MenuItem("Export to Excel");
                positions.setId(modelXml.nameAlgo);
                positions.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ExportExcel exportExcel = new ExportExcel();
                        exportExcel.positions(Repository.strategy.get(positions.getId()));
                    }
                });

                ContextMenu menuPositions = new ContextMenu();
                menuPositions.getItems().addAll(positions);
                panelPositionsTableView.setContextMenu(menuPositions);
            }


            MenuItem lastPrice = new MenuItem("Export to Excel");
            lastPrice.setId(modelXml.nameAlgo);

            lastPrice.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ExportExcel exportExcel = new ExportExcel();
                    exportExcel.lastPrice(Repository.strategy.get(lastPrice.getId()));
                }
            });


            ContextMenu menulastPrice = new ContextMenu();
            menulastPrice.getItems().addAll(lastPrice);

            lastPriceTableView.setContextMenu(menulastPrice);

            readFromDolarListener = new ReadFromDolarListener(this);
            readLogMkdAdrListener = new ReadLogMkdAdrListener(this);
            readLogMkdLocalListener = new ReadLogMkdLocalListener(this);
            readlogRoutingAdrListener = new ReadlogRoutingAdrListener(this);
            readLogRoutingLocalListener = new ReadLogRoutingLocalListener(this);
            calculateLastPriceListener = new CalculateLastPriceListener(this);

            dolarListener = new DolarListener(this);
            marketDataAdrListener = new MarketDataAdrListener(this);
            marketDataLocalListener = new MarketDataLocalListener(this);
            routingAdrListener = new RoutingAdrListener(this);
            routingLocalListener = new RoutingLocalListener(this);
            alertListener = new AlertListener(this);
            alertMarketMakerBCSListener = new AlertMarketMakerBCSListener(this);
            calculatePositionsListener = new CalculatePositionsListener(this);
            lastPriceListener = new LastPriceListener(this);
            positionViewListener = new PositionViewListener(this);

            Controller.addEventListener(AlertEvent.class, alertListener);
            Controller.addEventListener(AlertEvent.class, alertMarketMakerBCSListener);
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

            Repository.strategy.put(modelXml.nameAlgo, this);

            start(modelXml);

            primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                    layout.setPrefWidth(newSceneWidth.doubleValue());
                    positionsPanel.setPrefWidth(newSceneWidth.doubleValue());
                    optionsPanel.setPrefWidth(primaryStage.getWidth() * 0.3);

                }
            });

            primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                    layout.setPrefHeight(newSceneHeight.doubleValue());
                    positionsPanel.setPrefWidth(newSceneHeight.doubleValue());
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(modelXml.nameAlgo);
            logger.error(Level.SEVERE, ex);
            Notifier.INSTANCE.notifyError("Error " + modelXml.nameAlgo, ex.toString());
        }
    }


    public Algo() {

        try {

            modelXml = new ModelXml();
            modelXml.nameAlgo = "Sell Side";

            FXMLLoader sellSideLoader = new FXMLLoader();
            sellSideLoader.setLocation(MainSellSide.class.getResource("view/SellSide.fxml"));

            SwitchButton switchButtonReset = new SwitchButton("Reset", this);
            Button switchBtn2 = switchButtonReset.returnButton();
            switchBtn2.setLayoutX(30);
            switchBtn2.setLayoutY(35);

            SwitchButton switchButtonDolar = new SwitchButton("Strart", this);
            Button switchBtn1 = switchButtonDolar.returnButton();
            switchBtn1.setLayoutX(30);
            switchBtn1.setLayoutY(35);

            SwitchButton switchButtonAlert = new SwitchButton("Alert", this);
            Button switchBtn6 = switchButtonAlert.returnButton();
            switchBtn6.setLayoutX(160);
            switchBtn6.setLayoutY(35);

            HBox buttons = new HBox();
            buttons.getStyleClass().add("buttonSellSide");
            buttons.getChildren().addAll(switchBtn2, switchBtn1, switchBtn6);

            HBox consoleSellSide = new HBox();
            consoleSellSide.getStyleClass().add("consoleSellSide");
            consoleSellSide.getChildren().add((AnchorPane) sellSideLoader.load());

            Label ip = new Label("IP Server : " + Inet4Address.getLocalHost().getHostAddress());
            ip.setLayoutX(30);
            ip.setLayoutY(80);

            Label port = new Label("Port : " + Repository.socketAcceptPort);
            port.setLayoutX(30);
            port.setLayoutY(100);

            Label sender = new Label("Sender : " + Repository.senderCompID);
            sender.setLayoutX(170);
            sender.setLayoutY(80);

            Label target = new Label("Target : " + Repository.targetCompID);
            target.setLayoutX(170);
            target.setLayoutY(100);

            Label type = new Label("Type : Acceptor");
            type.setLayoutX(170);
            type.setLayoutY(100);

            HBox label = new HBox();
            label.getStyleClass().add("labelSellSide");
            label.getChildren().addAll(ip, port, sender, target, type);

            VBox general = new VBox();
            general.getStyleClass().add("generalSellSide");
            general.getChildren().addAll(buttons, consoleSellSide, label);

            ScrollPane scrollBar = new ScrollPane();
            scrollBar.prefWidthProperty().bind(general.widthProperty());
            scrollBar.prefHeightProperty().bind(general.heightProperty());
            scrollBar.setContent(general);
            scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            Repository.tabPanePrincipalTabPanel.getTabs().get(0).setContent(scrollBar);

            SellSideController sellsideLoader = sellSideLoader.getController();
            sellsideTableView = sellsideLoader.getType();

            Repository.strategy.put(modelXml.nameAlgo, this);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(Level.SEVERE, ex);
            Notifier.INSTANCE.notifyError("Error", ex.toString());
        }


    }


    public void readXML(Element elem) {

        try {

            modelXml.location = elem.getElementsByTagName("location").item(0).getChildNodes().item(0).getNodeValue();
            modelXml.nameAlgo = elem.getElementsByTagName("nameAlgo").item(0).getChildNodes().item(0).getNodeValue();

            modelXml.mkd_dolar = elem.getElementsByTagName("mkd_dolar").item(0).getChildNodes().item(0).getNodeValue();
            modelXml.mkd_nyse = elem.getElementsByTagName("mkd_nyse").item(0).getChildNodes().item(0).getNodeValue();
            modelXml.mkd_local = elem.getElementsByTagName("mkd_local").item(0).getChildNodes().item(0).getNodeValue();
            modelXml.routing_local = elem.getElementsByTagName("routing_local").item(0).getChildNodes().item(0).getNodeValue();
            modelXml.routing_nyse = elem.getElementsByTagName("routing_nyse").item(0).getChildNodes().item(0).getNodeValue();

            modelXml.booleanDolar = Boolean.valueOf(elem.getElementsByTagName("booleanDolar").item(0).getChildNodes().item(0).getNodeValue());
            modelXml.booleanMLocal = Boolean.valueOf(elem.getElementsByTagName("booleanMLocal").item(0).getChildNodes().item(0).getNodeValue());
            modelXml.booleanMAdr = Boolean.valueOf(elem.getElementsByTagName("booleanMAdr").item(0).getChildNodes().item(0).getNodeValue());
            modelXml.booleanRLocal = Boolean.valueOf(elem.getElementsByTagName("booleanRLocal").item(0).getChildNodes().item(0).getNodeValue());
            modelXml.booleanRAdr = Boolean.valueOf(elem.getElementsByTagName("booleanRAdr").item(0).getChildNodes().item(0).getNodeValue());

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }


    public void fileReader(ModelXml xmlVO) throws Exception {

        if (xmlVO.booleanDolar) {
            fileMkdDolar = new File(modelXml.location + modelXml.mkd_dolar + Repository.year + ".log");
            inputStreamDolar = new FileInputStream(fileMkdDolar);
        }

        if (xmlVO.booleanMLocal) {
            fileMkdLocal = new File(modelXml.location + modelXml.mkd_local + Repository.year + ".log");
            inputStreamMkdLocal = new FileInputStream(fileMkdLocal);
        }

        if (xmlVO.booleanMAdr) {
            fileMkdAdr = new File(modelXml.location + modelXml.mkd_nyse + Repository.year + ".log");
            inputStreamMkdAdr = new FileInputStream(fileMkdAdr);
        }

        if (xmlVO.booleanRLocal) {
            fileRoutingLocal = new File(modelXml.location + modelXml.routing_local + Repository.year + ".log");
            inputStreamRoutingLocal = new FileInputStream(fileRoutingLocal);
        }

        if (xmlVO.booleanRAdr) {
            fileRoutingAdr = new File(modelXml.location + modelXml.routing_nyse + Repository.year + ".log");
            inputStreamRoutingAdr = new FileInputStream(fileRoutingAdr);
        }

    }


    public synchronized void start(ModelXml xmlVO) {

        try {

            timerTask = new TimerTask() {

                public void run() {

                    Algo algo = Repository.strategy.get(xmlVO.nameAlgo);

                    if (mkdDolarToggle && xmlVO.booleanDolar && validateDolar) {
                        validateDolar = false;
                        Controller.dispatchEvent(new ReadFromDolarEvent(algo));
                    }

                    if (mkdAdrToggle && xmlVO.booleanMAdr && validateMKDADR) {
                        validateMKDADR = false;
                        Controller.dispatchEvent(new ReadLogMkdAdrEvent(algo));
                    }

                    if (mkdLocalToggle && xmlVO.booleanMLocal && validateMKDLocal) {
                        validateMKDLocal = false;
                        Controller.dispatchEvent(new ReadLogMkdLocalEvent(algo));
                    }

                    if (routingLocalToggle && xmlVO.booleanRLocal && validateRoutingLocal) {
                        validateRoutingLocal = false;
                        Controller.dispatchEvent(new ReadLogRoutingLocalEvent(algo));
                    }

                    if (routingAdrToggle && xmlVO.booleanRAdr && validateRoutingADR) {
                        validateRoutingADR = false;
                        Controller.dispatchEvent(new ReadlogRoutingAdrEvent(algo));
                    }

                }

            };

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 250, 1);

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }

    public void inputButtonReadLog(VBox optionsPanel) throws Exception {

        if (modelXml.booleanDolar.equals(true)) {
            SwitchButton switchButtonDolar = new SwitchButton("Dolar", this);
            Button switchBtn1 = switchButtonDolar.returnButton();
            this.buttonDolar = switchBtn1;

            VBox ButtonDolar = new VBox();
            ButtonDolar.getChildren().add(switchButtonDolar);
            optionsPanel.getChildren().add(ButtonDolar);
        }

        if (modelXml.booleanMLocal.equals(true)) {
            SwitchButton switchButtonMkd_Local = new SwitchButton("MKD Local", this);
            Button switchBtn3 = switchButtonMkd_Local.returnButton();
            this.buttonMKDLocal = switchBtn3;

            VBox HBoswitchButtonMkd_Local = new VBox();
            HBoswitchButtonMkd_Local.getChildren().add(switchButtonMkd_Local);
            optionsPanel.getChildren().add(HBoswitchButtonMkd_Local);
        }

        if (modelXml.booleanMAdr.equals(true)) {
            SwitchButton switchButtonMkd_nyse = new SwitchButton("MKD ADR", this);
            Button switchBtn2 = switchButtonMkd_nyse.returnButton();
            this.buttonMDKDAdr = switchBtn2;

            VBox HBoxButtonMkd = new VBox();
            HBoxButtonMkd.getChildren().add(switchButtonMkd_nyse);
            optionsPanel.getChildren().add(HBoxButtonMkd);

        }

        if (modelXml.booleanRLocal.equals(true)) {
            SwitchButton switchButtonRouting_Local = new SwitchButton("Routing Local", this);
            Button switchBtn4 = switchButtonRouting_Local.returnButton();
            this.buttonRoutingLocal = switchBtn4;

            VBox HBosRouting_Local = new VBox();
            HBosRouting_Local.getChildren().add(switchButtonRouting_Local);
            optionsPanel.getChildren().add(HBosRouting_Local);

        }

        if (modelXml.booleanRAdr.equals(true)) {
            SwitchButton switchButtonRouting_Adr = new SwitchButton("Routing ADR", this);
            Button switchBtn5 = switchButtonRouting_Adr.returnButton();
            this.buttonRoutingADR = switchBtn5;

            VBox HBosRouting_Adr = new VBox();
            HBosRouting_Adr.getChildren().add(switchButtonRouting_Adr);
            optionsPanel.getChildren().add(HBosRouting_Adr);
        }


    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }


    public void setOptionsPanel(VBox optionsPanel){

        SwitchButton switchButtonAlert = new SwitchButton("Alert", this);
        Button switchBtn6 = switchButtonAlert.returnButton();
        this.buttonAlert = switchBtn6;

        SwitchButton graphSwith = new SwitchButton("Graph", this);
        Button switchBtn7 = graphSwith.returnButton();
        this.graphButton = switchBtn7;

        VBox vBoxgraph = new VBox();
        vBoxgraph.getChildren().add(graphSwith);
        optionsPanel.getChildren().add(vBoxgraph);

        VBox vBox = new VBox();
        vBox.getChildren().add(switchButtonAlert);
        optionsPanel.getChildren().add(vBox);

    }


}


