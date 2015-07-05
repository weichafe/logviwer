package com.larrainvial.logviwer.utils;

public class Control {

    public static void initialize(){


        //Controller.addEventListener(AlertEvent.class, new AlertListener());

        //Controller.addEventListener(ReadFromDolarEvent.class, new ReadFromDolarListener());
        //Controller.addEventListener(ReadLogMkdAdrEvent.class, new ReadLogMkdAdrListener());
        //Controller.addEventListener(ReadLogMkdLocalEvent.class, new ReadLogMkdLocalListener());
        //Controller.addEventListener(ReadlogRoutingAdrEvent.class, new ReadlogRoutingAdrListener());
        //Controller.addEventListener(ReadLogRoutingLocalEvent.class, new ReadLogRoutingLocalListener());

        //Controller.addEventListener(DolarEvent.class, new DolarListener());
        //Controller.addEventListener(MarketDataADREvent.class, new MarketDataAdrListener());
        //Controller.addEventListener(MarketDataLocalEvent.class, new MarketDataLocalListener());
        //Controller.addEventListener(RoutingAdrEvent.class, new RoutingAdrListener());
//        Controller.addEventListener(RoutingLocalEvent.class, new RoutingLocalListener());


        //Controller.addEventListener(DolarViewEvent.class, new DolarViewListener());
        //Controller.addEventListener(MarketDataAdrViewEvent.class, new MarketDataAdrViewListener());
        //Controller.addEventListener(MarketDataLocalViewEvent.class, new MarketDataLocalViewListener());
        //Controller.addEventListener(PositionViewEvent.class, new PositionViewListener());
        //Controller.addEventListener(RoutingAdrViewEvent.class, new RoutingAdrViewListener());
//        Controller.addEventListener(RoutingLocalViewEvent.class, new RoutingLocalViewListener());



    }


    public  static void initializaAll() throws InterruptedException {

        //initializeSellSide(0);
        //initializeSellSide(1);

    }

    private static void initializeSellSide(int tab){

        try {
/*
            Algo algo = new Algo();

            algo.nameAlgo =("Sell Sides");
            algo.routingLocal =("ROUTING_LOCAL");
            algo.time = (1);

            Date fechaActual = new Date();
            DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
            String year = formatoFecha.format(fechaActual).replace("/", "");

            String location = "C:\\workspaceGit\\logviwer\\logs\\log\\";
            String routing_nyse = "FIX.4.4-LVSSG_ARB-LVARB.messages_";
            String log = ".log";

            algo.fileRoutingAdr =(new File(location + routing_nyse + year + log));
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


            algo.routingAdrLoader.setLocation(MainLogViwer.class.getResource("view/sellside/RoutingAdrView.fxml"));

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add((AnchorPane) algo.routingAdrLoader.load());
            anchorPane.getChildren().add(opacityLevel);
            anchorPane.getChildren().add(switchBtn5);
            anchorPane.getChildren().add(switchBtn6);
            anchorPane.getChildren().add(labelIP);
            anchorPane.getChildren().add(labelMinTimer);
            anchorPane.getChildren().add(labelMaxTimer);


            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setContent(anchorPane);
            Repository.tabPanePrincipalTabPanel.getTabs().get(tab).setText(algo.nameAlgo);

            com.larrainvial.logviwer.controller.sellside.RoutingAdrController getSellside_loader = algo.routingAdrLoader.getController();
            algo.routingAdrTableView =(getSellside_loader.getType());
            algo.routingAdrMasterList = (getSellside_loader.masterData);

            Repository.strategy.put(algo.nameAlgo, algo);
            algo.iniziale(false, false, false, false, true);
            */

        } catch (Exception e){
            Helper.exception(e);
        }

    }



}