package com.larrainvial.killprocess.market;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.larrainvial.killprocess.Repository;
import com.larrainvial.killprocess.ssh.Connection;
import com.larrainvial.killprocess.util.PropertiesFile;
import com.larrainvial.killprocess.vo.CoreVO;
import com.larrainvial.killprocess.vo.ServerVO;
import com.larrainvial.logviwer.MainLogViwer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class Santiago {

    private static Logger logger = Logger.getLogger(Santiago.class.getName());

    public Button serverCoreButton = new Button();
    public SimpleBooleanProperty serverCoreButtonOn = new SimpleBooleanProperty(true);

    public TextArea textAreaWebOrb = new TextArea();
    public TextArea textAreaCore = new TextArea();

    public Button weborbCoreButton = new Button();
    public SimpleBooleanProperty weborbCoreButtonOn = new SimpleBooleanProperty(true);

    public SimpleBooleanProperty coreKillProcess = new SimpleBooleanProperty(true);
    public SimpleBooleanProperty webOrbKillProcess = new SimpleBooleanProperty(true);

    public ServerVO serverCore = new ServerVO();
    public ServerVO serverWeborb = new ServerVO();

    public Session sessionServerCore;
    public Session sessionServerWebOrb;

    public AnchorPane ventanaPrincipal;
    public VBox general;


    public void killProcess(Stage primaryStage) {

        try {

            this.getProperties();  //obtiene las conexion a los servidores weborb y core

            logger.info("Kill Pocess OK");

            FXMLLoader loader = new FXMLLoader(MainLogViwer.class.getResource("view/KillProcess.fxml"));
            ventanaPrincipal = (AnchorPane) loader.load();
            ventanaPrincipal.setPrefWidth(700);
            ventanaPrincipal.setPrefHeight(800);

            general = new VBox();
            general.prefWidthProperty().bind(ventanaPrincipal.widthProperty());

            HBox head = new HBox();
            head.setPrefHeight(50);
            head.setSpacing(40);
            head.setAlignment(Pos.CENTER);
            head.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");
            head.getChildren().addAll(serverCoreButton, weborbCoreButton);
            general.getChildren().add(head);


            this.setButtonStrategy(); // se crean los botones de los algos configuracion en el properties
            this.consoleCore();
            this.consoleWbOrb();

            ventanaPrincipal.getChildren().addAll(general);

            Stage santiago = new Stage();
            santiago.initOwner(primaryStage);

            Scene scene = new Scene(ventanaPrincipal);
            santiago.setScene(scene);
            santiago.show();

        } catch (Exception e) {
            logger.error(e);
        }


    }

    public void consoleWbOrb(){

        HBox consoleWeborb = new HBox();
        consoleWeborb.setAlignment(Pos.CENTER);
        consoleWeborb.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
        consoleWeborb.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");

        textAreaWebOrb.setPrefHeight(300);
        textAreaWebOrb.setText("HOLAAAAA");
        textAreaWebOrb.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
        textAreaWebOrb.setStyle("-fx-highlight-fill: #ffffff; -fx-highlight-text-fill: #000000; -fx-text-fill: #ffffff; -fx-background-color: #000000;");
        consoleWeborb.getChildren().addAll(textAreaWebOrb);
        general.getChildren().add(consoleWeborb);

    }

    public void consoleCore() {

        HBox consoleCore = new HBox();
        consoleCore.setAlignment(Pos.CENTER);
        consoleCore.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
        consoleCore.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");

        textAreaCore.setPrefHeight(300);
        textAreaCore.setText("");
        textAreaCore.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
        textAreaCore.setStyle("-fx-highlight-fill: #ffffff; -fx-highlight-text-fill: #000000; -fx-text-fill: #ffffff; -fx-background-color: #000000;");
        consoleCore.getChildren().addAll(textAreaCore);
        general.getChildren().add(consoleCore);

    }

    public void printConsolaCore(String aux){
        textAreaCore.setText(aux);
    }

    public void printConsolawebOrb(String aux){
        textAreaWebOrb.setText(aux);
    }

    public void setButtonStrategy(){


        HBox strategyHbox = new HBox();
        strategyHbox.setAlignment(Pos.CENTER);
        strategyHbox.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");

        VBox coreVbox = new VBox();
        coreVbox.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");

        VBox weborbVbox = new VBox();
        weborbVbox.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");


        for (Map.Entry<String, CoreVO> e : Repository.coreStrategy.entrySet()) {

            CoreVO core = Repository.coreStrategy.get(e.getKey());

            HBox auxCore = new HBox();
            auxCore.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");
            auxCore.setAlignment(Pos.CENTER);

            Button strategyCore = new Button();
            strategyCore.setId(core.name);
            strategyCore.setText(core.name);

            Boolean verifyProcces = verifyProccesCoreLinux(core);

            if (verifyProcces){
                strategyCore.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
            } else {
                strategyCore.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
            }

            strategyCore.setPrefWidth(180);
            auxCore.getChildren().add(strategyCore);
            coreVbox.getChildren().add(auxCore);


            strategyCore.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    Button clickedBtn = (Button) t.getSource();
                    setCommandtoServerCore(clickedBtn);

                }
            });

        }


        for (Map.Entry<String, CoreVO> e : Repository.weborbStrategy.entrySet()) {

            CoreVO weborb = Repository.weborbStrategy.get(e.getKey());

            HBox auxCore = new HBox();
            auxCore.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");
            auxCore.setAlignment(Pos.CENTER);

            Button strategyWebOrb = new Button();
            strategyWebOrb.setId(weborb.name);
            strategyWebOrb.setText(weborb.name);

            Boolean verifyProcces = verifyProcceswebOrbLinux(weborb);

            if (verifyProcces){
                strategyWebOrb.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
            } else {
                strategyWebOrb.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
            }

            strategyWebOrb.setPrefWidth(180);
            auxCore.getChildren().add(strategyWebOrb);
            weborbVbox.getChildren().add(auxCore);

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

            connectServerWebOrb();

            ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("ps -fea | grep " + core.nameprocess);
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((console = reader.readLine()) != null) {

                if (console.contains("-Dname="+core.nameprocess)) {
                    final String aux = console;
                    printConsolawebOrb(aux);
                    core.proccesUp = true;
                    channelExec.disconnect();
                    return true;
                }
            }

            channelExec.disconnect();

        } catch (Exception e){
            logger.error(e);
        }

        return false;

    }


    public Boolean verifyProccesCoreLinux(CoreVO core){

        String console;

        try {

            connectServerCore();

            ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("ps -fea | grep " + core.nameprocess);
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((console = reader.readLine()) != null) {

                if (console.contains("-Dname="+core.nameprocess)) {
                    final String aux = console;
                    printConsolaCore(aux);
                    core.proccesUp = true;
                    channelExec.disconnect();
                    return true;
                }
            }

            channelExec.disconnect();

        } catch (Exception e){
            logger.error(e);
        }

        return false;

    }


    public void getProperties(){

        Repository.killProcess = new PropertiesFile("C:\\Program Files (x86)\\LarrainVial\\Logviewer\\Resources\\KillProcessSantiago.properties");

        serverCore.url = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString("servidor.core.url");
        serverCore.usuario = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString("servidor.core.usuario");
        serverCore.pass = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString("servidor.core.pass");

        serverWeborb.url = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString("servidor.weborb.url");
        serverWeborb.usuario = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString("servidor.weborb.usuario");
        serverWeborb.pass = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString("servidor.weborb.pass");


        for (Map.Entry<?, ?> entry : com.larrainvial.killprocess.Repository.killProcess.properties.entrySet()) {

            String key = (String) entry.getKey();

            if (key.indexOf(".core.name") > -1) {

                CoreVO core = new CoreVO();
                String name = key.replace(".core.name", "");

                core.name = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString(name + ".core.name");
                core.nameprocess = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString(name + ".core.processname");
                core.pathbin = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString(name + ".core.pathbin");

                Repository.coreStrategy.put(core.name, core);
            }

            if (key.indexOf(".weborb.name") > -1) {

                CoreVO core = new CoreVO();
                String name = key.replace(".weborb.name", "");

                core.name = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString(name + ".weborb.name");
                core.nameprocess = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString(name + ".weborb.processname");
                core.pathbin = com.larrainvial.killprocess.Repository.killProcess.getPropertiesString(name + ".weborb.pathbin");

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

    public void disconnectServerWebOrb(){
        if(sessionServerWebOrb != null) sessionServerWebOrb.disconnect();
    }

    public void setCommandtoServerWebOrb(Button clickedBtn){

        try {

            if (!Repository.weborbStrategy.containsKey(clickedBtn.getId())) return;

            CoreVO core = Repository.weborbStrategy.get(clickedBtn.getId());


            if (core.proccesUp){

                ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand("kill -9 $(ps aux | grep " + core.nameprocess + " | awk '{print $2}')");
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;
                int index = 0;

                while ((linea = reader.readLine()) != null) {
                    printConsolawebOrb(linea);
                }

                core.proccesUp = false;
                verifyProcceswebOrbLinux(core);

                clickedBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");

            } else {

                ChannelExec channelExec = (ChannelExec) sessionServerWebOrb.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand(core.pathbin);
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;
                int index = 0;

                while ((linea = reader.readLine()) != null) {
                    printConsolawebOrb(linea);
                }

                verifyProcceswebOrbLinux(core);

                clickedBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
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
                    printConsolaCore(linea);
                }

                core.proccesUp = false;
                verifyProccesCoreLinux(core);

                clickedBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");

            } else {

                ChannelExec channelExec = (ChannelExec) sessionServerCore.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand(core.pathbin);
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;
                int index = 0;

                while ((linea = reader.readLine()) != null) {
                    printConsolaCore(linea);
                }

                verifyProccesCoreLinux(core);
                clickedBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
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



    public void disconnectServerCore(){
        if(sessionServerCore != null) sessionServerCore.disconnect();
    }

}
