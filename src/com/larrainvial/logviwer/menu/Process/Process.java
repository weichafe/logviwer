package com.larrainvial.logviwer.menu.Process;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.process.Thread.PrintConsole;
import com.larrainvial.logviwer.model.ModelProcess;
import com.larrainvial.logviwer.process.SSH.Connection;
import com.larrainvial.logviwer.process.vo.ServerVO;
import com.larrainvial.logviwer.Start;
import com.larrainvial.logviwer.utils.PropertiesFile;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
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
import java.io.*;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class Process {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    public final String CORE = "core";
    public final String WEB_ORB = "weborb";
    public String TEXT_CORE = "Console Core \n";
    public String TEXT_WEBORB = "Console WebOrb \n";

    public TextArea textAreaWebOrb = new TextArea();
    public TextArea textAreaCore = new TextArea();

    public Session sessionServerCore;
    public Session sessionServerWebOrb;
    public ChannelExec channelExec;
    public Channel channel;

    public AnchorPane ventanaPrincipal;
    public VBox general = new VBox();
    public Stage principalStage;

    public ObservableList<ModelProcess> dataList = FXCollections.observableArrayList();

    public Double prefWidth;
    public Double prefHeight;
    public FXMLLoader loader;
    public TimerTask timerTask;

    public TableView<ModelProcess> tableView = new TableView<>();
    public TableColumn<ModelProcess, String> name = new TableColumn<>("Name");
    public TableColumn<ModelProcess, String> processName = new TableColumn<>("Process Name");
    public TableColumn<ModelProcess, String> pathbin = new TableColumn<>("Path Bin");
    public TableColumn<ModelProcess, String> comentary = new TableColumn<>("Comentary");
    public TableColumn<ModelProcess, ModelProcess> core = new TableColumn<ModelProcess, ModelProcess>("Core");

    public Process(Stage primaryStage, String properties) {

        try {

            logger.info("Kill Pocess OK");

            textAreaCore.scrollTopProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue,
                                    Object newValue) {
                    textAreaCore.setScrollTop(Double.MAX_VALUE);

                }
            });

            textAreaWebOrb.scrollTopProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue,
                                    Object newValue) {
                    textAreaWebOrb.setScrollTop(Double.MAX_VALUE);

                }
            });

            general.getStyleClass().add("generalKillProcess");
            loader = new FXMLLoader(Start.class.getResource("view/process/Process.fxml"));

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

        } catch (Exception ex) {
            logger.error(Level.SEVERE);
            ex.printStackTrace();
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

            Scene scene = new Scene(scrollBar, prefHeight, prefWidth);
            principalStage.setScene(scene);
            principalStage.show();

            principalStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    sessionServerCore.disconnect();
                    sessionServerWebOrb.disconnect();
                    timerTask.cancel();
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
                                modelProcess.setButton(button);
                                setGraphic(button);

                                if (modelProcess.algo.equals(CORE)) {
                                    buttonStyle(modelProcess, sessionServerCore, false);

                                } else if (modelProcess.algo.equals(WEB_ORB)) {
                                    buttonStyle(modelProcess, sessionServerWebOrb, false);
                                }

                                button.setOnAction(new EventHandler<ActionEvent>() {

                                    @Override
                                    public void handle(ActionEvent t) {
                                        Button clickedBtn = (Button) t.getSource();

                                        if (modelProcess.algo.equals(CORE)) {
                                            setCommandToServer(clickedBtn, sessionServerCore);
                                        } else {
                                            setCommandToServer(clickedBtn, sessionServerWebOrb);
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

            verifyProcessCron();

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }


    public void getProperties(String properties) throws Exception {

        Repository.killProcess = new PropertiesFile(Repository.locationPath + properties);

        ServerVO serverCore = new ServerVO();
        serverCore.url = Repository.killProcess.getPropertiesString("servidor.core.url");
        serverCore.usuario = Repository.killProcess.getPropertiesString("servidor.core.usuario");
        serverCore.pass = Repository.killProcess.getPropertiesString("servidor.core.pass");

        ServerVO serverWeborb = new ServerVO();
        serverWeborb.url = Repository.killProcess.getPropertiesString("servidor.weborb.url");
        serverWeborb.usuario = Repository.killProcess.getPropertiesString("servidor.weborb.usuario");
        serverWeborb.pass = Repository.killProcess.getPropertiesString("servidor.weborb.pass");

        sessionServerCore = this.connectServer(serverCore);
        sessionServerWebOrb = this.connectServer(serverWeborb);


        for (Map.Entry<?, ?> entry : Repository.killProcess.properties.entrySet()) {

            String key = (String) entry.getKey();
            ModelProcess strategy = new ModelProcess();

            if (key.indexOf(".core.name") > -1) {

                String name = key.replace(".core.name", "");
                strategy.setName(Repository.killProcess.getPropertiesString(name + ".core.name"));
                strategy.setProcessName(Repository.killProcess.getPropertiesString(name + ".core.processname"));
                strategy.setPathbin(Repository.killProcess.getPropertiesString(name + ".core.pathbin"));
                strategy.setComentary(Repository.killProcess.getPropertiesString(name + ".core.comentario"));
                strategy.algo = CORE;

                Repository.coreStrategy.put(strategy.getName(), strategy);
                dataList.add(strategy);
            }

            if (key.indexOf(".weborb.name") > -1) {

                String name = key.replace(".weborb.name", "");
                strategy.setName(Repository.killProcess.getPropertiesString(name + ".weborb.name"));
                strategy.setProcessName(Repository.killProcess.getPropertiesString(name + ".weborb.processname"));
                strategy.setPathbin(Repository.killProcess.getPropertiesString(name + ".weborb.pathbin"));
                strategy.setComentary(Repository.killProcess.getPropertiesString(name + ".weborb.comentario"));
                strategy.algo = WEB_ORB;

                Repository.coreStrategy.put(strategy.getName(), strategy);
                dataList.add(strategy);

            }

        }

    }

    public Boolean verifyProcess(ModelProcess core, Session sesion, boolean print) {

        try {

            channelExec = (ChannelExec) sesion.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("ps -fea | grep " + core.getProcessName());
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String console = null;

            while ((console = reader.readLine()) != null) {

                if (console.contains("-Dname="+core.getProcessName())) {
                    printConsole(core, console, print);
                    core.setProccesUp(true);
                    channelExec.disconnect();
                    return true;
                }
            }

            core.setProccesUp(false);

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

        return false;
    }


    public void setCommandToServer(Button clickedBtn, Session session){

        try {

            if (!Repository.coreStrategy.containsKey(clickedBtn.getId())) return;

            ModelProcess modelProcess = Repository.coreStrategy.get(clickedBtn.getId());

            channel = session.openChannel("shell");
            OutputStream outputStream = channel.getOutputStream();
            PrintStream commander = new PrintStream(outputStream, true);
            channel.setOutputStream(System.out, true);
            channel.connect();

            String command = (modelProcess.proccesUp) ? "kill -9 $(ps aux | grep " + modelProcess.getProcessName() + " | awk '{print $2}')"
                    : modelProcess.getPathbin();

            commander.println(command);
            verifyProcess(modelProcess, session, false);

            InputStream outputstream = channel.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(outputstream));

            Thread thread;

            if(modelProcess.algo.equals(CORE)){
                thread = new Thread(new PrintConsole(br, textAreaCore, true));
            } else {
                thread = new Thread(new PrintConsole(br, textAreaWebOrb, true));
            }

            thread.start();


        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }

    public Session connectServer(ServerVO serverVO){

        Session session = null;

        try {

            Connection connection  = new Connection();
            session = connection.connectServer(serverVO);
            session.connect();

            if (session.isConnected()){
                Notifier.INSTANCE.notifyInfo(serverVO.url, "Connection Established");

            } else {
                Notifier.INSTANCE.notifyError(serverVO.url, "Check Settings");
            }

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            Notifier.INSTANCE.notifyError(serverVO.url, "Check Settings");
            principalStage.close();
        }

        return session;
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


    public void printConsole(ModelProcess modelProcess, String aux, boolean print) throws Exception {

        try {

            if (!print) return;

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    if (modelProcess.algo.equals(CORE)) {
                        TEXT_CORE += aux + "\n";
                        textAreaCore.setText(TEXT_CORE);

                    } else {
                        TEXT_WEBORB += aux + "\n";
                        textAreaWebOrb.setText(TEXT_WEBORB);
                    }

                }

            });

        } catch (Exception ex){
            ex.printStackTrace();
            Helper.printerLog(ex.toString());
        }

    }


    public void buttonStyle(ModelProcess core, Session sesion, boolean print) {

        try {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (verifyProcess(core, sesion, print)){
                        core.button.getStyleClass().removeAll("processLinuxDown");
                        core.button.getStyleClass().add("processLinuxUP");
                    } else {
                        core.button.getStyleClass().removeAll("processLinuxUP");
                        core.button.getStyleClass().add("processLinuxDown");
                    }
                }

            });

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void verifyProcessCron(){

        timerTask = new TimerTask(){

            public void run() {

                    try {

                        for (Map.Entry<?, ?> entry : Repository.coreStrategy.entrySet()) {

                            ModelProcess modelProcess = Repository.coreStrategy.get((String) entry.getKey());

                            if (modelProcess.algo.equals(CORE)){
                                buttonStyle(modelProcess, sessionServerCore, false);
                            } else {
                                buttonStyle(modelProcess, sessionServerWebOrb, false);
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Helper.printerLog(ex.toString());
                    }
                }

        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1, 2000);

    }


}
