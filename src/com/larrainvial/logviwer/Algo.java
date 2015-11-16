package com.larrainvial.logviwer;

import com.jcraft.jsch.Session;
import com.larrainvial.logviwer.controller.algos.LastPriceController;
import com.larrainvial.logviwer.controller.algos.PanelPositionsController;
import com.larrainvial.logviwer.fxvo.Graph;
import com.larrainvial.logviwer.listener.readlog.*;
import com.larrainvial.logviwer.model.Dolar;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.fxvo.SwitchButton;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.vo.LatencyVO;
import com.larrainvial.logviwer.vo.XmlVO;
import com.larrainvial.process.ssh.Connection;
import com.larrainvial.sellside.MainSellSide;
import com.larrainvial.sellside.controller.SellSideController;
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
import java.io.*;
import java.net.Inet4Address;
import java.util.*;
import java.util.logging.Level;

public class Algo {

    private Logger logger = Logger.getLogger(this.getClass().getName());

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

    public Map<String, ModelPositions> positionsMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelPositions>());
    public Map<String, Integer> positions = Collections.synchronizedMap(new HashMap<String, Integer>());
    public int countPositions = 0;

    public Map<String, ModelMarketData> lastPriceMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelMarketData>());
    public Map<String, Integer> lastPrice = Collections.synchronizedMap(new HashMap<String, Integer>());
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
    public LineChart<Number, Number> lineChart;
    public boolean graphEnable = false;

    //LATENCY
    public Map<String, LatencyVO> latencyADR = Collections.synchronizedMap(new HashMap<String, LatencyVO>());
    public Map<String, LatencyVO> latencyLocal = Collections.synchronizedMap(new HashMap<String, LatencyVO>());

    //count line file
    public int countDolar = 0;
    public int countMkdLocal = 0;
    public int countMdkAdr = 0;
    public int countRoutingLocal = 0;
    public int countRoutingAdr = 0;

    //coneccion server
    public static XmlVO xmlVO;
    public static Session session;


    public Algo(Element elem, int tab) {

        try {

            synchronized (this) {

                this.xmlVO = new XmlVO();

                if (elem.getElementsByTagName("sshValidator").item(0).getChildNodes().item(0).getNodeValue().equals("true")) {

                    xmlVO.nameServer = elem.getElementsByTagName("sshServer").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.userServer = elem.getElementsByTagName("sshUser").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.passServer = elem.getElementsByTagName("sshPass").item(0).getChildNodes().item(0).getNodeValue();

                    xmlVO.location = elem.getElementsByTagName("location").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.mkd_dolar = elem.getElementsByTagName("mkd_dolar").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.mkd_nyse = elem.getElementsByTagName("mkd_nyse").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.mkd_local = elem.getElementsByTagName("mkd_local").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.routing_local = elem.getElementsByTagName("routing_local").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.routing_nyse = elem.getElementsByTagName("routing_nyse").item(0).getChildNodes().item(0).getNodeValue();

                    this.nameAlgo = elem.getElementsByTagName("nameAlgo").item(0).getChildNodes().item(0).getNodeValue();
                    this.mkdDolar = elem.getElementsByTagName("mkdDolar").item(0).getChildNodes().item(0).getNodeValue();
                    this.mkdLocal = elem.getElementsByTagName("mkdLocal").item(0).getChildNodes().item(0).getNodeValue();
                    this.mkdAdr = elem.getElementsByTagName("mkdAdr").item(0).getChildNodes().item(0).getNodeValue();
                    this.routingLocal = elem.getElementsByTagName("routingLocal").item(0).getChildNodes().item(0).getNodeValue();
                    this.routingAdr = elem.getElementsByTagName("routingAdr").item(0).getChildNodes().item(0).getNodeValue();
                    this.time = Double.valueOf(elem.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue());

                    xmlVO.booleanDolar = Boolean.valueOf(elem.getElementsByTagName("booleanDolar").item(0).getChildNodes().item(0).getNodeValue());
                    xmlVO.booleanMLocal = Boolean.valueOf(elem.getElementsByTagName("booleanMLocal").item(0).getChildNodes().item(0).getNodeValue());
                    xmlVO.booleanMAdr = Boolean.valueOf(elem.getElementsByTagName("booleanMAdr").item(0).getChildNodes().item(0).getNodeValue());
                    xmlVO.booleanRLocal = Boolean.valueOf(elem.getElementsByTagName("booleanRLocal").item(0).getChildNodes().item(0).getNodeValue());
                    xmlVO.booleanRAdr = Boolean.valueOf(elem.getElementsByTagName("booleanRAdr").item(0).getChildNodes().item(0).getNodeValue());

                    Connection connection = new Connection();
                    this.session = connection.connectServer(xmlVO);
                    this.session.connect();


                } else {

                    xmlVO.location = elem.getElementsByTagName("location").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.mkd_dolar = elem.getElementsByTagName("mkd_dolar").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.mkd_nyse = elem.getElementsByTagName("mkd_nyse").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.mkd_local = elem.getElementsByTagName("mkd_local").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.routing_local = elem.getElementsByTagName("routing_local").item(0).getChildNodes().item(0).getNodeValue();
                    xmlVO.routing_nyse = elem.getElementsByTagName("routing_nyse").item(0).getChildNodes().item(0).getNodeValue();

                    this.nameAlgo = elem.getElementsByTagName("nameAlgo").item(0).getChildNodes().item(0).getNodeValue();
                    this.mkdDolar = elem.getElementsByTagName("mkdDolar").item(0).getChildNodes().item(0).getNodeValue();
                    this.mkdLocal = elem.getElementsByTagName("mkdLocal").item(0).getChildNodes().item(0).getNodeValue();
                    this.mkdAdr = elem.getElementsByTagName("mkdAdr").item(0).getChildNodes().item(0).getNodeValue();
                    this.routingLocal = elem.getElementsByTagName("routingLocal").item(0).getChildNodes().item(0).getNodeValue();
                    this.routingAdr = elem.getElementsByTagName("routingAdr").item(0).getChildNodes().item(0).getNodeValue();
                    this.time = Double.valueOf(elem.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue());

                    xmlVO.booleanDolar = Boolean.valueOf(elem.getElementsByTagName("booleanDolar").item(0).getChildNodes().item(0).getNodeValue());
                    xmlVO.booleanMLocal = Boolean.valueOf(elem.getElementsByTagName("booleanMLocal").item(0).getChildNodes().item(0).getNodeValue());
                    xmlVO.booleanMAdr = Boolean.valueOf(elem.getElementsByTagName("booleanMAdr").item(0).getChildNodes().item(0).getNodeValue());
                    xmlVO.booleanRLocal = Boolean.valueOf(elem.getElementsByTagName("booleanRLocal").item(0).getChildNodes().item(0).getNodeValue());
                    xmlVO.booleanRAdr = Boolean.valueOf(elem.getElementsByTagName("booleanRAdr").item(0).getChildNodes().item(0).getNodeValue());

                    fileMkdDolar = new File(xmlVO.location + xmlVO.mkd_dolar + Repository.year + ".log");
                    fileMkdLocal = new File(xmlVO.location + xmlVO.mkd_local + Repository.year + ".log");
                    fileMkdAdr = new File(xmlVO.location + xmlVO.mkd_nyse + Repository.year + ".log");
                    fileRoutingLocal = new File(xmlVO.location + xmlVO.routing_local + Repository.year + ".log");
                    fileRoutingAdr = new File(xmlVO.location + xmlVO.routing_nyse + Repository.year + ".log");

                    this.fileReader();

                }

                panelPositionsLoader.setLocation(MainLogViwer.class.getResource("view/algos/PanelPositionsView.fxml"));
                lastPriceLoader.setLocation(MainLogViwer.class.getResource("view/algos/LastPriceView.fxml"));

                HBox grill = new HBox();
                grill.getStyleClass().add("grillStrategy");
                grill.getChildren().add((AnchorPane) lastPriceLoader.load());
                grill.getChildren().add((AnchorPane) panelPositionsLoader.load());

                HBox grillLastPricePositions = new HBox();
                grillLastPricePositions.getChildren().addAll(grill);

                HBox options = new HBox();
                options.getStyleClass().add("options");

                SwitchButton switchButtonAlert = new SwitchButton("Alert", this);
                Button switchBtn6 = switchButtonAlert.returnButton();
                this.buttonAlert = switchBtn6;

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


                if (nameAlgo.startsWith("ADR")) {

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
                    variacion.setPadding(new Insets(0, 0, 0, 0));
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
                            if (cofxvar.getText() != null) {
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
                graph.getStyleClass().add("hboxGraph");
                graph.getChildren().add(lineChart);

                VBox general = new VBox();
                general.getStyleClass().add("hboxGeneral");
                general.getChildren().addAll(options, grillLastPricePositions, graph);


                ScrollPane scrollBar = new ScrollPane();
                scrollBar.prefHeightProperty().bind(general.heightProperty());
                scrollBar.prefWidthProperty().bind(general.widthProperty());
                scrollBar.setContent(general);
                scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


                Tab tabAlgo = new Tab();
                tabAlgo.setText(this.nameAlgo);
                Repository.tabPanePrincipalTabPanel.getTabs().add(tabAlgo);
                Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(scrollBar);


                PanelPositionsController panelLocalLoader = panelPositionsLoader.getController();
                panelPositionsTableView = panelLocalLoader.getType();

                LastPriceController lastPriceLocal = this.lastPriceLoader.getController();
                lastPriceTableView = lastPriceLocal.getType();

                Repository.strategy.put(this.nameAlgo, this);
                this.fileReader();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Helper.printerLog(ex.toString());
            Notifier.INSTANCE.notifyError("Error", ex.toString());
        }
    }


    public Algo(int tab) {

        try {

            this.nameAlgo = "Sell Side";
            this.time = 1d;

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

            Label port = new Label("Port : " + com.larrainvial.sellside.Repository.socketAcceptPort);
            port.setLayoutX(30);
            port.setLayoutY(100);

            Label sender = new Label("Sender : " + com.larrainvial.sellside.Repository.senderCompID);
            sender.setLayoutX(170);
            sender.setLayoutY(80);

            Label target = new Label("Target : " + com.larrainvial.sellside.Repository.targetCompID);
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


            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(scrollBar);

            SellSideController sellsideLoader = sellSideLoader.getController();
            sellsideTableView = sellsideLoader.getType();

            Repository.strategy.put(this.nameAlgo, this);

        } catch (Exception ex) {
            ex.printStackTrace();
            Helper.printerLog(ex.toString());
            Notifier.INSTANCE.notifyError("Error", ex.toString());
        }


    }

    public synchronized void fileReader() {

        try {

            if (xmlVO.booleanDolar) {
                inputStreamMkdDolar = new FileInputStream(fileMkdDolar);
            }
            if (xmlVO.booleanMLocal) {
                inputStreamMkdLocal = new FileInputStream(fileMkdLocal);
            }
            if (xmlVO.booleanMAdr) {
                inputStreamMkdAdr = new FileInputStream(fileMkdAdr);
            }
            if (xmlVO.booleanRLocal) {
                inputStreamRoutingLocal = new FileInputStream(fileRoutingLocal);
            }
            if (xmlVO.booleanRAdr) {
                inputStreamRoutingAdr = new FileInputStream(fileRoutingAdr);
            }

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }

    }


    public synchronized void run() {

        Algo algo = Repository.strategy.get(nameAlgo);

        TimerTask timerTask = new TimerTask() {

            public void run() {

                synchronized (algo) {

                    try {

                        if (algo.mkdDolarToggle && xmlVO.booleanDolar) {
                            new Thread(new ReadFromDolarListener(algo)).start();
                        }

                        if (algo.mkdAdrToggle && xmlVO.booleanMAdr) {
                            new Thread(new ReadLogMkdAdrListener(algo)).start();
                        }

                        if (algo.mkdLocalToggle && xmlVO.booleanMLocal) {
                            new Thread(new ReadLogMkdLocalListener(algo)).start();
                        }

                        if (algo.routingLocalToggle && xmlVO.booleanRLocal) {
                            new Thread(new ReadLogRoutingLocalListener(algo)).start();
                        }

                        if (algo.routingAdrToggle && xmlVO.booleanRAdr) {
                            new Thread(new ReadlogRoutingAdrListener(algo)).start();
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println(algo.nameAlgo);
                        System.out.println(algo.mkdDolarToggle);
                        System.out.println(xmlVO.booleanDolar);
                    }

                }

            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1l, 300);



    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

}


