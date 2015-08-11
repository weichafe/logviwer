package com.larrainvial.logviwer.utils;


import com.larrainvial.logviwer.Repository;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public class Dialog {

    public static synchronized void about() {

        try {

            Platform.runLater(new Runnable() {

                public void run() {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("LOG VIEWER LARRAINVIAL");
                    alert.setContentText("Author: Etrading\n");
                    alert.showAndWait();
                }
            });

        } catch (Exception ex) {
            exception(ex);

        }
    }

    public static synchronized void exception(Exception e) {

        try {

            Platform.runLater(new Runnable() {

                public void run() {

                    if (!Repository.exception) return;

                    Alert alertException = new Alert(Alert.AlertType.ERROR);
                    alertException.setTitle("Exception Dialog");
                    alertException.setHeaderText("Look, an Exception Dialog");
                    alertException.setContentText(e.toString());

                    Exception ex = new FileNotFoundException(e.toString());

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String exceptionText = sw.toString();

                    Label label = new Label("The exception stacktrace was:");

                    TextArea textArea = new TextArea(exceptionText);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);

                    textArea.setMaxWidth(Double.MAX_VALUE);
                    textArea.setMaxHeight(Double.MAX_VALUE);
                    GridPane.setVgrow(textArea, Priority.ALWAYS);
                    GridPane.setHgrow(textArea, Priority.ALWAYS);

                    GridPane expContent = new GridPane();
                    expContent.setMaxWidth(Double.MAX_VALUE);
                    expContent.add(label, 0, 0);
                    expContent.add(textArea, 0, 1);

                    alertException.getDialogPane().setExpandableContent(expContent);

                    Repository.exception = false;
                    Optional<ButtonType> result = alertException.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        Repository.exception = true;
                    }


                }
            });

            e.printStackTrace();

        }catch (Exception ex){
            ex.printStackTrace();

        }
    }

    public static synchronized void alert(String headerText, String contentText1){

        Platform.runLater(new Runnable() {
            public void run() {

                if(!Repository.alertBloolean) return;

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setHeaderText(headerText);
                alert.setContentText(contentText1);

                Repository.alertBloolean = false;
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK){
                    Repository.alertBloolean = true;
                }
            }
        });
    }
}
