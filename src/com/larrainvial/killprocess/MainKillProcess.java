package com.larrainvial.killprocess;

import com.jcraft.jsch.Session;
import com.larrainvial.killprocess.ssh.Connection;
import com.larrainvial.killprocess.util.PropertiesFile;
import com.larrainvial.killprocess.vo.CoreVO;
import com.larrainvial.killprocess.vo.ServerVO;
import com.larrainvial.logviwer.*;
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
import java.util.Map;

public class MainKillProcess {

    private static Logger logger = Logger.getLogger(MainKillProcess.class.getName());

    public Button serverCoreButton = new Button();
    public SimpleBooleanProperty serverCoreButtonOn = new SimpleBooleanProperty(true);

    public Button weborbCoreButton = new Button();
    public SimpleBooleanProperty weborbCoreButtonOn = new SimpleBooleanProperty(true);

    public ServerVO serverCore = new ServerVO();
    public ServerVO serverWeborb = new ServerVO();

    public Session sessionServerCore;
    public Session sessionServerWebOrb;



    public void santiago(Stage primaryStage) {

        try {

            this.getProperties();
            this.serverCore();
            this.serverWebOrb();

            logger.info("Kill Pocess OK");

            FXMLLoader loader = new FXMLLoader(MainLogViwer.class.getResource("view/KillProcess.fxml"));
            AnchorPane ventanaPrincipal = (AnchorPane) loader.load();
            ventanaPrincipal.setPrefWidth(700);
            ventanaPrincipal.setPrefHeight(800);


            VBox general = new VBox();
            general.prefWidthProperty().bind(ventanaPrincipal.widthProperty());


            HBox main = new HBox();
            main.setPrefHeight(50);
            main.setSpacing(40);
            main.setAlignment(Pos.CENTER);
            main.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");
            main.getChildren().addAll(serverCoreButton, weborbCoreButton);
            general.getChildren().add(main);


            HBox consoleCore = new HBox();
            consoleCore.setPrefHeight(50);
            consoleCore.setSpacing(40);
            consoleCore.setAlignment(Pos.BOTTOM_CENTER);
            consoleCore.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
            consoleCore.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");
            TextArea textArea = new TextArea();
            textArea.setPrefRowCount(2);
            textArea.setText("Hello\nworld!");
            consoleCore.getChildren().add(textArea);
            general.getChildren().add(consoleCore);


            HBox consoleWeborb = new HBox();
            consoleWeborb.setPrefHeight(50);
            consoleWeborb.setSpacing(40);
            consoleWeborb.setAlignment(Pos.CENTER);
            consoleWeborb.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
            consoleWeborb.setStyle("-fx-content-display: top; -fx-border-insets: 2 0 0 0; -fx-border-color: -fx-text-box-border; -fx-border-width: 2; -fx-padding: 18 5 5 5;");
            TextArea textArea2 = new TextArea();
            textArea2.setPrefRowCount(2);
            textArea2.setText("Hello\nworld!");
            consoleWeborb.getChildren().addAll(textArea2);
            general.getChildren().add(consoleWeborb);

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


    public void getProperties(){

        Repository.killProcess = new PropertiesFile("C:\\Program Files (x86)\\LarrainVial\\Logviewer\\Resources\\KillProcessSantiago.properties");

        serverCore.url = Repository.killProcess.getPropertiesString("servidor.core.url");
        serverCore.usuario = Repository.killProcess.getPropertiesString("servidor.core.usuario");
        serverCore.pass = Repository.killProcess.getPropertiesString("servidor.core.pass");

        serverWeborb.url = Repository.killProcess.getPropertiesString("servidor.weborb.url");
        serverWeborb.usuario = Repository.killProcess.getPropertiesString("servidor.weborb.usuario");
        serverWeborb.pass = Repository.killProcess.getPropertiesString("servidor.weborb.pass");


        for (Map.Entry<?, ?> entry : Repository.killProcess.properties.entrySet()) {

            String key = (String) entry.getKey();

            if (key.indexOf(".core.name") > -1) {

                CoreVO core = new CoreVO();
                String name = key.replace(".core.name", "");

                core.name = Repository.killProcess.getPropertiesString(name + ".core.name");
                core.nameprocess = Repository.killProcess.getPropertiesString(name + ".core.processname");
                core.pathbin = Repository.killProcess.getPropertiesString(name + ".core.pathbin");

                Repository.coreStrategy.put(core.name, core);
            }

            if (key.indexOf(".weborb.name") > -1) {

                CoreVO core = new CoreVO();
                String name = key.replace(".weborb.name", "");

                core.name = Repository.killProcess.getPropertiesString(name + ".weborb.name");
                core.nameprocess = Repository.killProcess.getPropertiesString(name + ".weborb.processname");
                core.pathbin = Repository.killProcess.getPropertiesString(name + ".weborb.pathbin");

                Repository.weborbStrategy.put(core.name, core);
            }


        }

    }


    public void serverCore(){

        serverCoreButton.setId("ServerCore");
        serverCoreButton.setText("Connect Server Core");
        serverCoreButton.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
        serverCoreButton.setPrefWidth(180);


        serverCoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                serverCoreButtonOn.set(!serverCoreButtonOn.get());
            }
        });

        serverCoreButtonOn.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                if (t) {
                    serverCoreButton.setText("Disconnect Server Core");
                    serverCoreButton.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                    disconnectServerCore();

                } else {
                    serverCoreButton.setText("Connect Server Core");
                    serverCoreButton.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                    connectServerCore();
                }

            }

        });

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

    public void connectServerCore(){

        try {

            Connection connection  = new Connection();
            sessionServerCore = connection.connectServer(serverCore);
            sessionServerCore.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectServerCore(){
        if(sessionServerCore != null) sessionServerCore.disconnect();
    }


    public void serverWebOrb(){

        weborbCoreButton.setId("ServerWeborb");
        weborbCoreButton.setText("Connect Server WebOrb");
        weborbCoreButton.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
        weborbCoreButton.setPrefWidth(180);


        weborbCoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                weborbCoreButtonOn.set(!weborbCoreButtonOn.get());
            }
        });

        weborbCoreButtonOn.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                if (t) {
                    disconnectServerWebOrb();
                    weborbCoreButton.setText("Disconnect Server Core");
                    weborbCoreButton.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");

                } else {
                    connectServerWebOrb();
                    weborbCoreButton.setText("Connect Server Core");
                    weborbCoreButton.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                }

            }

        });

    }

}
