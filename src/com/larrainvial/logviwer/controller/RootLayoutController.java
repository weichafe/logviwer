package com.larrainvial.logviwer.controller;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.menu.About.About;
import com.larrainvial.logviwer.menu.Process.Process;
import javafx.fxml.FXML;

public class RootLayoutController {


    @FXML
    private void handleNew() {
    }

    @FXML
    private void handleOpen() {
    }


    @FXML
    private void handleSave() {
    }


    @FXML
    private void handleSaveAs() {
    }


    @FXML
    private void Marmolejo() {

        try {

            String properties = "KillProcessMarmolejo.properties";
            new Process(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    @FXML
    private void Antuco() {

        try {

            String properties = "KillProcessAntuco.properties";
            new Process(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Everest() {

        try {

            String properties = "KillProcessEverest.properties";
            new Process(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Catedral() {

        try {

            String properties = "KillProcessCatedral.properties";
            new Process(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Esmeralda() {

        try {

            String properties = "KillProcessEsmeralda.properties";
            new Process(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Desconsuelo() {

        try {

            String properties = "KillProcessDesconsuelo.properties";
            new Process(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    private void lima() {

        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    @FXML
    private void editStrategy() {

        try {


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    private void handleAbout() {
        new About(Repository.primaryStage);
    }


    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
