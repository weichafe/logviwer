package com.larrainvial.process;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.larrainvial.logviwer.controller.algos.LastPriceController;
import com.larrainvial.logviwer.controller.process.ProccesController;
import com.larrainvial.logviwer.model.ModelProcess;
import com.larrainvial.process.ssh.Connection;
import com.larrainvial.process.util.PropertiesFile;
import com.larrainvial.process.vo.ServerVO;
import com.larrainvial.logviwer.MainLogViwer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;

public class MainProcess {

    private static Logger logger = Logger.getLogger(MainProcess.class.getName());

    public TextArea textAreaWebOrb = new TextArea();
    public TextArea textAreaCore = new TextArea();

    public ServerVO serverCore = new ServerVO();
    public ServerVO serverWeborb = new ServerVO();

    public Session sessionServerCore;
    public Session sessionServerWebOrb;

    public AnchorPane ventanaPrincipal;
    public VBox general = new VBox();

    public static String textCore = "Console Core " + "\n";
    public static String textWebOrb = "Console WebOrb " + "\n";

    public VBox weborbVbox = new VBox();

    public Double prefWidth;
    public Double prefHeight;

    public FXMLLoader proccessFXMLLoader = new FXMLLoader();
    public TableView<ModelProcess> tableView = null;
    public FXMLLoader loader;

    public MainProcess(Stage primaryStage, String properties){

        try {

            logger.info("Kill Pocess OK");

            general.getStyleClass().add("generalKillProcess");

            loader = new FXMLLoader(MainLogViwer.class.getResource("view/process/Process.fxml"));
            proccessFXMLLoader.setLocation(MainLogViwer.class.getResource("view/process/PanelProccesView.fxml"));
            proccessFXMLLoader.load();


            ProccesController modelProcess = proccessFXMLLoader.getController();
            tableView = modelProcess.getType();


            this.prefWidth = primaryStage.getWidth();
            this.prefHeight = primaryStage.getHeight();
            this.process(primaryStage, properties);

        } catch (Exception e){
            logger.error(Level.SEVERE);
            e.printStackTrace();
        }
    }


    public void process(Stage primaryStage, String properties) {

        try {

            ventanaPrincipal = (AnchorPane) loader.load();

            general.getChildren().addAll(tableView);

            this.getProperties(properties);

            this.connectServerWebOrb();
            this.connectServerCore();


            this.setButtonStrategy();
            this.consoles();

            ventanaPrincipal.getChildren().add(general);


            ScrollPane scrollBar = new ScrollPane();
            scrollBar.setFitToHeight(true);
            scrollBar.setFitToWidth(true);
            scrollBar.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            scrollBar.setContent(ventanaPrincipal);

            Stage santiago = new Stage();
            santiago.initOwner(primaryStage);

            Scene scene = new Scene(scrollBar,prefHeight, prefWidth);
            santiago.setScene(scene);
            santiago.show();

            santiago.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    sessionServerCore.disconnect();
                    sessionServerWebOrb.disconnect();
                    textCore = "";
                    textWebOrb = "";
                }
            });


        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }

    }

    public void consoles(){

        HBox consoleCore = new HBox();
        consoleCore.getStyleClass().add("hboxConsole");
        consoleCore.prefWidthProperty().bind(ventanaPrincipal.widthProperty());

        textAreaCore.setPrefHeight(240);
        textAreaCore.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
        consoleCore.getChildren().addAll(textAreaCore);
        general.getChildren().add(consoleCore);

        HBox consoleWeborb = new HBox();
        consoleWeborb.getStyleClass().add("hboxConsole");
        consoleWeborb.prefWidthProperty().bind(ventanaPrincipal.widthProperty());

        textAreaWebOrb.setPrefHeight(240);
        textAreaWebOrb.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
        consoleWeborb.getChildren().addAll(textAreaWebOrb);
        general.getChildren().add(consoleWeborb);

    }

    public void printConsole(String aux, Boolean core){

        if (core) {
            textCore += aux + "\n";
            textAreaCore.setText(textCore);

        } else {
            textWebOrb += aux + "\n";
            textAreaWebOrb.setText(textWebOrb);
        }

    }

    public void setButtonStrategy(){

        for (Map.Entry<String, ModelProcess> e : Repository.coreStrategy.entrySet()) {

            ModelProcess core = Repository.coreStrategy.get(e.getKey());

            Button strategyCore = new Button();
            strategyCore.setId(core.name);
            strategyCore.setText(core.name);
            strategyCore.setTooltip(new Tooltip(core.comentary));

            Boolean verifyProcces = verifyProccesCoreLinux(core);

            if (verifyProcces) {
                strategyCore.getStyleClass().removeAll("processLinuxDown");
                strategyCore.getStyleClass().add("processLinuxUP");

            } else {
                strategyCore.getStyleClass().removeAll("processLinuxUP");
                strategyCore.getStyleClass().add("processLinuxDown");
            }

            strategyCore.setPrefWidth(140);

            strategyCore.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    Button clickedBtn = (Button) t.getSource();
                    setCommandtoServerCore(clickedBtn);

                }
            });

            //Aqui
        }


        for (Map.Entry<String, ModelProcess> e : Repository.weborbStrategy.entrySet()) {

            ModelProcess weborb = Repository.weborbStrategy.get(e.getKey());

            Button strategyWebOrb = new Button();
            strategyWebOrb.setPrefWidth(140);
            strategyWebOrb.setId(weborb.name);
            strategyWebOrb.setText(weborb.name);
            strategyWebOrb.setTooltip(new Tooltip(weborb.comentary));

            if (verifyProcceswebOrbLinux(weborb)) {
                strategyWebOrb.getStyleClass().removeAll("processLinuxDown");
                strategyWebOrb.getStyleClass().add("processLinuxUP");

            } else {
                strategyWebOrb.getStyleClass().removeAll("processLinuxUP");
                strategyWebOrb.getStyleClass().add("processLinuxDown");
            }

            weborbVbox.getChildren().add(strategyWebOrb);

            strategyWebOrb.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    Button clickedBtn = (Button) t.getSource();
                    setCommandtoServerWebOrb(clickedBtn);

                }
            });


        }


        //Aqui

    }

    public Boolean verifyProcceswebOrbLinux(ModelProcess core){

        String console;

        try {

            ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("ps -fea | grep " + core.processName);
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((console = reader.readLine()) != null) {

                if (console.contains("-Dname=" + core.processName)) {
                    final String aux = console;
                    printConsole(aux, false);
                    core.proccesUp = true;
                    channelExec.disconnect();
                    return true;
                }
            }

            core.proccesUp = false;
            channelExec.disconnect();

        } catch (Exception e){
            logger.error(e);
        }



        return false;

    }


    public Boolean verifyProccesCoreLinux(ModelProcess core){

        String console;

        try {

            ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("ps -fea | grep " + core.processName);
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((console = reader.readLine()) != null) {

                if (console.contains("-Dname="+core.processName)) {
                    final String aux = console;
                    printConsole(aux, true);
                    core.proccesUp = true;
                    channelExec.disconnect();
                    return true;
                }
            }

            core.proccesUp = false;
            channelExec.disconnect();

        } catch (Exception e){
            logger.error(e);
        }

        return false;

    }


    public void getProperties(String properties){

        Repository.killProcess = new PropertiesFile(com.larrainvial.logviwer.Repository.locationPath + properties);

        serverCore.url = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.core.url");
        serverCore.usuario = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.core.usuario");
        serverCore.pass = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.core.pass");

        serverWeborb.url = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.weborb.url");
        serverWeborb.usuario = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.weborb.usuario");
        serverWeborb.pass = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.weborb.pass");

        int count = 1;

        for (Map.Entry<?, ?> entry : com.larrainvial.process.Repository.killProcess.properties.entrySet()) {

            String key = (String) entry.getKey();

            if (key.indexOf(".core.name") > -1) {

                ModelProcess strategy = new ModelProcess();
                String name = key.replace(".core.name", "");

                strategy.name = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.name");
                strategy.processName = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.processname");
                strategy.pathbin = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.pathbin");
                strategy.comentary = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.comentario");

                tableView.getItems().add(strategy);
                Repository.coreStrategy.put(strategy.name, strategy);

                ProccesController modelProcess = proccessFXMLLoader.getController();
                modelProcess.getCore();


            }

/*
            if (key.indexOf(".weborb.name") > -1) {

                ModelProcess strategy = new ModelProcess();
                String name = key.replace(".weborb.name", "");

                strategy.name = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.name");
                strategy.processName = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.processname");
                strategy.pathbin = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.pathbin");
                strategy.comentary = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.comentario");

                Repository.weborbStrategy.put(strategy.name, strategy);
                tableView.getItems().add(strategy);


                Callback<TableColumn<ModelProcess, String>, TableCell<ModelProcess, String>> cellFactory =
                        new Callback<TableColumn<ModelProcess, String>, TableCell<ModelProcess, String>>() {

                            @Override
                            public TableCell call( final TableColumn<ModelProcess, String> param){

                                final TableCell<ModelProcess, String> cell = new TableCell<ModelProcess, String>(){

                                    final Button btn = new Button();

                                    @Override
                                    public void updateItem(String item, boolean empty ) {
                                        super.updateItem(item, empty);

                                        if (empty) {
                                            setGraphic(null);
                                            setText(null);

                                        } else {

                                            btn.setId(strategy.name);
                                            btn.setText(strategy.name);
                                            btn.setTooltip(new Tooltip(strategy.comentary));
                                            btn.setPrefWidth(140);

                                            Boolean verifyProcces = verifyProccesCoreLinux(strategy);

                                            if (verifyProcces) {
                                                btn.getStyleClass().removeAll("processLinuxDown");
                                                btn.getStyleClass().add("processLinuxUP");

                                            } else {
                                                btn.getStyleClass().removeAll("processLinuxUP");
                                                btn.getStyleClass().add("processLinuxDown");
                                            }



                                            btn.setOnAction(new EventHandler<ActionEvent>() {

                                                @Override
                                                public void handle(ActionEvent t) {
                                                    Button clickedBtn = (Button) t.getSource();
                                                    setCommandtoServerWebOrb(clickedBtn);

                                                }
                                            });

                                            setGraphic(btn);
                                            setText(null);
                                        }
                                    }
                                };
                                return cell;
                            }
                        };

                weborb.setCellFactory(cellFactory);

            }
*/
        }



    }


    public void connectServerWebOrb(){

        try {

            Connection connection  = new Connection();
            sessionServerWebOrb = connection.connectServer(serverCore);
            sessionServerWebOrb.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setCommandtoServerWebOrb(Button clickedBtn){

        try {

            if (!Repository.weborbStrategy.containsKey(clickedBtn.getId())) return;

            ModelProcess weborb = Repository.weborbStrategy.get(clickedBtn.getId());


            if (weborb.proccesUp){

                ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand("kill -9 $(ps aux | grep " + weborb.processName + " | awk '{print $2}')");
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;

                while ((linea = reader.readLine()) != null) {
                    printConsole(linea, false);
                }

                weborb.proccesUp = false;
                channelExec.disconnect();

                if (verifyProcceswebOrbLinux(weborb)){
                    clickedBtn.getStyleClass().removeAll("processLinuxDown");
                    clickedBtn.getStyleClass().add("processLinuxUP");
                } else {
                    clickedBtn.getStyleClass().removeAll("processLinuxUP");
                    clickedBtn.getStyleClass().add("processLinuxDown");
                }


            } else {

                ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand(weborb.pathbin);
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;

                while ((linea = reader.readLine()) != null) {
                    printConsole(linea, false);
                }

                channelExec.disconnect();

                if (verifyProcceswebOrbLinux(weborb)){
                    clickedBtn.getStyleClass().removeAll("processLinuxDown");
                    clickedBtn.getStyleClass().add("processLinuxUP");
                } else {
                    clickedBtn.getStyleClass().removeAll("processLinuxUP");
                    clickedBtn.getStyleClass().add("processLinuxDown");
                }

            }

        } catch (Exception e){
            logger.error(e);
        }

    }


    public void setCommandtoServerCore(Button clickedBtn){

        try {

            if (!Repository.coreStrategy.containsKey(clickedBtn.getId())) return;

            ModelProcess core = Repository.coreStrategy.get(clickedBtn.getId());


            if (core.proccesUp){

                ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand("kill -9 $(ps aux | grep " + core.processName + " | awk '{print $2}')");
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;
                int index = 0;

                while ((linea = reader.readLine()) != null) {
                    printConsole(linea, true);
                }



                core.proccesUp = false;
                channelExec.disconnect();

                if (verifyProccesCoreLinux(core)) {
                    clickedBtn.getStyleClass().removeAll("processLinuxDown");
                    clickedBtn.getStyleClass().add("processLinuxUP");
                } else {
                    clickedBtn.getStyleClass().removeAll("processLinuxUP");
                    clickedBtn.getStyleClass().add("processLinuxDown");
                }


            } else {

                ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");


                InputStream in = channelExec.getInputStream();

                channelExec.setCommand(core.pathbin);
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;

                while ((linea = reader.readLine()) != null) {
                    printConsole(linea, true);
                }


                channelExec.disconnect();

                if (verifyProccesCoreLinux(core)){
                    clickedBtn.getStyleClass().removeAll("processLinuxDown");
                    clickedBtn.getStyleClass().add("processLinuxUP");
                } else {
                    clickedBtn.getStyleClass().removeAll("processLinuxUP");
                    clickedBtn.getStyleClass().add("processLinuxDown");
                }

            }

        } catch (Exception e){
            logger.error(e);
        }

    }

    public void connectServerCore(){

        try {

            Connection connection  = new Connection();
            sessionServerCore = connection.connectServer(serverCore);
            sessionServerCore.connect();

        } catch (Exception e) {
            logger.error(e);
        }
    }


    private class ButtonCell extends TableCell<ModelProcess, Boolean> {

        final Button cellButton = new Button();

        ButtonCell(ModelProcess strategy){

            cellButton.setId(strategy.name);
            cellButton.setText(strategy.name);
            cellButton.setTooltip(new Tooltip(strategy.comentary));
            cellButton.setPrefWidth(140);

            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    Button clickedBtn = (Button) t.getSource();
                    setCommandtoServerWebOrb(clickedBtn);
                }
            });
        }


        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

}
