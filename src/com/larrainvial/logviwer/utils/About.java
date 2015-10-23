package com.larrainvial.logviwer.utils;


import com.larrainvial.logviwer.MainLogViwer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class About {

    public AnchorPane ventanaPrincipal;
    public VBox general = new VBox();
    private static Logger logger = Logger.getLogger(About.class.getName());

    public About(Stage primaryStage){

        try {


            FXMLLoader loader = new FXMLLoader(MainLogViwer.class.getResource("view/about.fxml"));
            ventanaPrincipal = (AnchorPane) loader.load();
            ventanaPrincipal.setPrefWidth(300);
            ventanaPrincipal.setPrefHeight(200);

            general.prefWidthProperty().bind(ventanaPrincipal.widthProperty());
            ventanaPrincipal.getChildren().addAll(general);

            HBox text = new HBox();
            text.setAlignment(Pos.CENTER);

            String texto = "\n\n\n";
            texto += "\tEtrading " + "\n";
            texto += "Gerencia de Sitemas " + "\n";
            texto += "Larrainvial 2015 " + "\n";
            texto += "Version terrible chora  1.0.0.3 " + "\n";

            Label label1 = new Label(texto);
            text.getChildren().addAll(label1);
            general.getChildren().addAll(text);

            Stage santiago = new Stage();
            santiago.initOwner(primaryStage);

            Scene scene = new Scene(ventanaPrincipal);
            santiago.setScene(scene);
            santiago.show();

        } catch (Exception e){
            logger.error(e);
        }


    }
}
