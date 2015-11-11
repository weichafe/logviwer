package com.larrainvial.logviwer.controller;

import com.larrainvial.logviwer.utils.About;
import com.larrainvial.process.MainProcess;
import com.larrainvial.logviwer.MainLogViwer;
import com.larrainvial.logviwer.Repository;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class RootLayoutController {


    private MainLogViwer mainApp;

    public void setMainApp(MainLogViwer mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(Repository.primaryStage);

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }


    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }


    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(Repository.primaryStage);

        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }


    @FXML
    private void Marmolejo() {

        try {

            String properties = "KillProcessMarmolejo.properties";
            new MainProcess(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    @FXML
    private void Antuco() {

        try {

            String properties = "KillProcessAntuco.properties";
            new MainProcess(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Everest() {

        try {

            String properties = "KillProcessEverest.properties";
            new MainProcess(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Catedral() {

        try {

            String properties = "KillProcessCatedral.properties";
            new MainProcess(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Esmeralda() {

        try {

            String properties = "KillProcessEsmeralda.properties";
            new MainProcess(Repository.primaryStage, properties);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void Desconsuelo() {

        try {

            String properties = "KillProcessDesconsuelo.properties";
            new MainProcess(Repository.primaryStage, properties);

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
