package com.larrainvial.process;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.process.model.ModelProcess;
import com.larrainvial.process.ssh.Connection;
import com.larrainvial.process.util.PropertiesFile;
import com.larrainvial.process.vo.ServerVO;
import com.larrainvial.logviwer.MainLogViwer;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;

public class MainProcess {

    private static Logger logger = Logger.getLogger(MainProcess.class.getName());
    public final String CORE = "core";
    public final String WEB_ORB = "weborb";
    public String TEXT_CORE = "Console Core \n";
    public String TEXT_WEBORB = "Console WebOrb \n";

    public TextArea textAreaWebOrb = new TextArea();
    public TextArea textAreaCore = new TextArea();

    public ServerVO serverCore = new ServerVO();
    public ServerVO serverWeborb = new ServerVO();

    public Session sessionServerCore;
    public Session sessionServerWebOrb;

    public AnchorPane ventanaPrincipal;
    public VBox general = new VBox();
    public Stage principalStage;

    public ObservableList<ModelProcess> dataList = FXCollections.observableArrayList();

    public Double prefWidth;
    public Double prefHeight;
    public FXMLLoader loader;

    public TableView<ModelProcess> tableView = new TableView<>();
    public TableColumn<ModelProcess, String> name = new TableColumn<>("Name");
    public TableColumn<ModelProcess, String> processName = new TableColumn<>("Process Name");
    public TableColumn<ModelProcess, String> pathbin = new TableColumn<>("Path Bin");
    public TableColumn<ModelProcess, String> comentary = new TableColumn<>("Comentary");
    public TableColumn<ModelProcess, ModelProcess> core = new TableColumn<ModelProcess, ModelProcess>("Core");
    public ChannelExec channelExec;

    public MainProcess(Stage primaryStage, String properties) {

        try {

            logger.info("Kill Pocess OK");

            general.getStyleClass().add("generalKillProcess");
            loader = new FXMLLoader(MainLogViwer.class.getResource("view/process/Process.fxml"));

            name.setMinWidth(200);
            name.setCellValueFactory(new PropertyValueFactory("name"));

            processName.setMinWidth(250);
            processName.setCellValueFactory(new PropertyValueFactory("processName"));

            pathbin.setMinWidth(520);
            pathbin.setCellValueFactory(new PropertyValueFactory("pathbin"));

            comentary.setMinWidth(300);
            comentary.setCellValueFactory(new PropertyValueFactory("comentary"));

            core.setMinWidth(220);

            this.prefWidth = primaryStage.getWidth();
            this.prefHeight = primaryStage.getHeight();

            ventanaPrincipal = (AnchorPane) loader.load();

            this.process(primaryStage, properties);


        } catch (Exception e) {
            logger.error(Level.SEVERE);
            e.printStackTrace();
        }
    }

    public void process(Stage primaryStage, String properties) {

        try {

            this.getProperties(properties);

            tableView.setItems(dataList);
            tableView.getColumns().addAll(name, processName, pathbin, comentary, core);

            general.getChildren().add(tableView);

            this.consoles();

            ventanaPrincipal.getChildren().add(general);

            ScrollPane scrollBar = new ScrollPane();
            scrollBar.setFitToHeight(true);
            scrollBar.setFitToWidth(true);
            scrollBar.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scrollBar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scrollBar.setContent(ventanaPrincipal);

            principalStage = new Stage();
            principalStage.initOwner(primaryStage);
            principalStage.setTitle(serverCore.url);

            Scene scene = new Scene(scrollBar, prefHeight, prefWidth);
            principalStage.setScene(scene);
            principalStage.show();

            principalStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    sessionServerCore.disconnect();
                    sessionServerWebOrb.disconnect();
                }
            });

            core.setCellValueFactory(new Callback<CellDataFeatures<ModelProcess, ModelProcess>, ObservableValue<ModelProcess>>() {
                @Override
                public ObservableValue<ModelProcess> call(CellDataFeatures<ModelProcess, ModelProcess> features) {
                    return new ReadOnlyObjectWrapper(features.getValue());
                }
            });

            core.setCellFactory(new Callback<TableColumn<ModelProcess, ModelProcess>, TableCell<ModelProcess, ModelProcess>>() {
                @Override
                public TableCell<ModelProcess, ModelProcess> call(TableColumn<ModelProcess, ModelProcess> btnCol) {
                    return new TableCell<ModelProcess, ModelProcess>() {
                        final ImageView buttonGraphic = new ImageView();

                        final Button button = new Button();{
                            button.setGraphic(buttonGraphic);
                            button.setMinWidth(130);
                        }

                        @Override
                        public void updateItem(final ModelProcess modelProcess, boolean empty) {
                            super.updateItem(modelProcess, empty);

                            if (modelProcess != null) {
                                button.setText(modelProcess.getName());
                                button.setId(modelProcess.getName());
                                setGraphic(button);

                                if (modelProcess.algo.equals(CORE)) {
                                    buttonStyle(modelProcess, sessionServerCore, button);

                                } else if (modelProcess.algo.equals(WEB_ORB)) {
                                    buttonStyle(modelProcess, sessionServerCore, button);
                                }

                                button.setOnAction(new EventHandler<ActionEvent>() {

                                    @Override
                                    public void handle(ActionEvent t) {
                                        Button clickedBtn = (Button) t.getSource();

                                        if (modelProcess.algo.equals(CORE)) {
                                            setCommandtoServerCore(clickedBtn);
                                        } else {
                                            setCommandtoServerWebOrb(clickedBtn);
                                        }

                                    }

                                });

                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                }
            });

        } catch (Exception e) {
            logger.error(Level.SEVERE, e);
            e.printStackTrace();
        }

    }


    public void getProperties(String properties) throws Exception {

        Repository.killProcess = new PropertiesFile(com.larrainvial.logviwer.Repository.locationPath + properties);

        serverCore.url = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.core.url");
        serverCore.usuario = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.core.usuario");
        serverCore.pass = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.core.pass");

        serverWeborb.url = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.weborb.url");
        serverWeborb.usuario = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.weborb.usuario");
        serverWeborb.pass = com.larrainvial.process.Repository.killProcess.getPropertiesString("servidor.weborb.pass");


        for (Map.Entry<?, ?> entry : com.larrainvial.process.Repository.killProcess.properties.entrySet()) {

            String key = (String) entry.getKey();
            ModelProcess strategy = new ModelProcess();

            if (key.indexOf(".core.name") > -1) {

                String name = key.replace(".core.name", "");
                strategy.setName(com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.name"));
                strategy.setProcessName(com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.processname"));
                strategy.setPathbin(com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.pathbin"));
                strategy.setComentary(com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.comentario"));
                strategy.algo = CORE;

                Repository.coreStrategy.put(strategy.getName(), strategy);
                dataList.add(strategy);
            }

            if (key.indexOf(".weborb.name") > -1) {

                String name = key.replace(".weborb.name", "");
                strategy.setName(com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.name"));
                strategy.setProcessName(com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.processname"));
                strategy.setPathbin(com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.pathbin"));
                strategy.setComentary(com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.comentario"));
                strategy.algo = WEB_ORB;

                Repository.weborbStrategy.put(strategy.getName(), strategy);
                dataList.add(strategy);

            }

        }

        this.connectServerWebOrb();
        this.connectServerCore();

    }

    public Boolean verifyProcess(ModelProcess core, Session sesion) {

        try {

            channelExec = (ChannelExec) sesion.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("ps -fea | grep " + core.getProcessName());
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String console = null;

            while ((console = reader.readLine()) != null) {

                if (console.contains("-Dname="+core.getProcessName())) {
                    final String aux = console;
                    printConsole(aux, true);
                    core.setProccesUp(true);
                    channelExec.disconnect();
                    return true;
                }
            }

            core.setProccesUp(false);


        } catch (Exception e){
            logger.error(Level.SEVERE, e);
            e.printStackTrace();
        }

        return false;
    }


    public void setCommandtoServerWebOrb(Button clickedBtn){

        try {

            if (!Repository.weborbStrategy.containsKey(clickedBtn.getId())) return;

            ModelProcess weborb = Repository.weborbStrategy.get(clickedBtn.getId());

            if (weborb.proccesUp){

                channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand("kill -9 $(ps aux | grep " + weborb.getProcessName() + " | awk '{print $2}')");
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea;

                while ((linea = reader.readLine()) != null) {
                    printConsole(linea, false);
                }

                weborb.proccesUp = false;

                buttonStyle(weborb, sessionServerWebOrb, clickedBtn);
                channelExec.disconnect();

            } else {

                ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand(weborb.getPathbin());
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea;

                while ((linea = reader.readLine()) != null) {
                    printConsole(linea, false);
                }

                buttonStyle(weborb, sessionServerWebOrb, clickedBtn);
                channelExec.disconnect();
            }

        } catch (Exception e){
            logger.error(Level.SEVERE, e);
            e.printStackTrace();
        }

    }


    public void setCommandtoServerCore(Button clickedBtn) {

        try {

            if (!Repository.coreStrategy.containsKey(clickedBtn.getId())) return;

            ModelProcess core = Repository.coreStrategy.get(clickedBtn.getId());

            if (core.proccesUp){

                ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand("kill -9 $(ps aux | grep " + core.getProcessName() + " | awk '{print $2}')");
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;

                while ((linea = reader.readLine()) != null) {
                    printConsole(linea, true);
                }

                buttonStyle(core, sessionServerCore, clickedBtn);
                channelExec.disconnect();
                core.setProccesUp(false);

            } else {

                ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand(core.getPathbin());
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea;

                while ((linea = reader.readLine()) != null) {
                    printConsole(linea, true);
                }

                buttonStyle(core, sessionServerCore, clickedBtn);
                channelExec.disconnect();

            }

        } catch (Exception e){
            logger.error(Level.SEVERE, e);
        }

    }

    public void connectServerCore(){

        try {

            Connection connection  = new Connection();
            sessionServerCore = connection.connectServer(serverCore);
            sessionServerCore.connect();

            if (sessionServerCore.isConnected()){
                Notifier.INSTANCE.notifyInfo(serverCore.url, "Connection Established");

            } else {
                Notifier.INSTANCE.notifyError(serverCore.url, "Check Settings");
            }

        } catch (Exception e) {
            logger.error(Level.SEVERE, e);
            Notifier.INSTANCE.notifyError(serverCore.url, "Check Settings");
            principalStage.close();
        }
    }

    public void connectServerWebOrb(){

        try {

            Connection connection  = new Connection();
            sessionServerWebOrb = connection.connectServer(serverWeborb);
            sessionServerWebOrb.connect();

            if (sessionServerWebOrb.isConnected()){
                Notifier.INSTANCE.notifyInfo(serverWeborb.url, "Connection Established");

            } else {
                Notifier.INSTANCE.notifyError(serverWeborb.url, "Check Settings");
            }

        } catch (Exception e) {
            logger.error(Level.SEVERE, e);
            Notifier.INSTANCE.notifyError(serverWeborb.url, "Check Settings");
            principalStage.close();
        }
    }


    public void consoles() throws Exception {

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

    public void printConsole(String aux, Boolean core) throws Exception {

        if (core) {
            TEXT_CORE += aux + "\n";
            textAreaCore.setText(TEXT_CORE);

        } else {
            TEXT_WEBORB += aux + "\n";
            textAreaWebOrb.setText(TEXT_WEBORB);
        }

    }

    public void buttonStyle(ModelProcess core, Session sesion, Button button){

        if (verifyProcess(core, sesion)){
            button.getStyleClass().removeAll("processLinuxDown");
            button.getStyleClass().add("processLinuxUP");
        } else {
            button.getStyleClass().removeAll("processLinuxUP");
            button.getStyleClass().add("processLinuxDown");
        }

    }




}
