package com.larrainvial.process.market;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.larrainvial.process.Repository;
import com.larrainvial.process.ssh.Connection;
import com.larrainvial.process.util.PropertiesFile;
import com.larrainvial.process.vo.CoreVO;
import com.larrainvial.process.vo.ServerVO;
import com.larrainvial.logviwer.MainLogViwer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class ConnectServer {

    private static Logger logger = Logger.getLogger(ConnectServer.class.getName());

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

    public HBox strategyHbox = new HBox();
    public VBox coreVbox = new VBox();
    public VBox weborbVbox = new VBox();


    public ConnectServer(Stage primaryStage, String properties){

        this.getProperties(properties);
        this.connectServerWebOrb();
        this.connectServerCore();
        this.killProcess(primaryStage);

    }


    public void killProcess(Stage primaryStage) {

        try {

            logger.info("Kill Pocess OK");

            FXMLLoader loader = new FXMLLoader(MainLogViwer.class.getResource("view/KillProcess.fxml"));
            ventanaPrincipal = (AnchorPane) loader.load();
            ventanaPrincipal.setPrefWidth(700);
            ventanaPrincipal.setPrefHeight(800);

            general.prefWidthProperty().bind(ventanaPrincipal.widthProperty());

            strategyHbox.getStyleClass().add("hboxMenuKill");
            coreVbox.getStyleClass().add("hboxMenuKillCore");
            weborbVbox.getStyleClass().add("hboxMenuKillwebOrb");

            this.setButtonStrategy();
            this.consoles();

            ventanaPrincipal.getChildren().addAll(general);

            Stage santiago = new Stage();
            santiago.initOwner(primaryStage);

            Scene scene = new Scene(ventanaPrincipal);
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

        for (Map.Entry<String, CoreVO> e : Repository.coreStrategy.entrySet()) {

            CoreVO core = Repository.coreStrategy.get(e.getKey());

            Button strategyCore = new Button();
            strategyCore.setId(core.name);
            strategyCore.setText(core.name);
            strategyCore.setTooltip(new Tooltip(core.commentario));

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

            coreVbox.getChildren().addAll(strategyCore);
        }


        for (Map.Entry<String, CoreVO> e : Repository.weborbStrategy.entrySet()) {

            CoreVO weborb = Repository.weborbStrategy.get(e.getKey());

            Button strategyWebOrb = new Button();
            strategyWebOrb.setPrefWidth(140);
            strategyWebOrb.setId(weborb.name);
            strategyWebOrb.setText(weborb.name);
            strategyWebOrb.setTooltip(new Tooltip(weborb.commentario));

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

        strategyHbox.getChildren().addAll(coreVbox, weborbVbox);
        general.getChildren().add(strategyHbox);

    }

    public Boolean verifyProcceswebOrbLinux(CoreVO core){

        String console;

        try {

            ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("ps -fea | grep " + core.nameprocess);
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((console = reader.readLine()) != null) {

                if (console.contains("-Dname=" + core.nameprocess)) {
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


    public Boolean verifyProccesCoreLinux(CoreVO core){

        String console;

        try {

            ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("ps -fea | grep " + core.nameprocess);
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((console = reader.readLine()) != null) {

                if (console.contains("-Dname="+core.nameprocess)) {
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


        for (Map.Entry<?, ?> entry : com.larrainvial.process.Repository.killProcess.properties.entrySet()) {

            String key = (String) entry.getKey();

            if (key.indexOf(".core.name") > -1) {

                CoreVO core = new CoreVO();
                String name = key.replace(".core.name", "");

                core.name = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.name");
                core.nameprocess = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.processname");
                core.pathbin = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.pathbin");
                core.commentario = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".core.comentario");

                Repository.coreStrategy.put(core.name, core);
            }

            if (key.indexOf(".weborb.name") > -1) {

                CoreVO core = new CoreVO();
                String name = key.replace(".weborb.name", "");

                core.name = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.name");
                core.nameprocess = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.processname");
                core.pathbin = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.pathbin");
                core.commentario = com.larrainvial.process.Repository.killProcess.getPropertiesString(name + ".weborb.comentario");

                Repository.weborbStrategy.put(core.name, core);
            }

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

            CoreVO weborb = Repository.weborbStrategy.get(clickedBtn.getId());


            if (weborb.proccesUp){

                ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand("kill -9 $(ps aux | grep " + weborb.nameprocess + " | awk '{print $2}')");
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

            CoreVO core = Repository.coreStrategy.get(clickedBtn.getId());


            if (core.proccesUp){

                ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand("kill -9 $(ps aux | grep " + core.nameprocess + " | awk '{print $2}')");
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

}
