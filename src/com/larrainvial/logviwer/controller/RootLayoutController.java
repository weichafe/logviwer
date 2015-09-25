package com.larrainvial.logviwer.controller;

import com.larrainvial.killprocess.MainKillProcess;
import com.larrainvial.logviwer.MainLogViwer;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.fxvo.Dialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RootLayoutController {


    private MainLogViwer mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainLogViwer mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
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

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(Repository.primaryStage);

        if (file != null) {

            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    @FXML
    private void santiago() {

        try {

            new MainKillProcess().santiago(Repository.primaryStage);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void colombia() {

        try {

            new MainKillProcess().santiago(Repository.primaryStage);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void lima() {

        try {

            new MainKillProcess().santiago(Repository.primaryStage);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @FXML
    private void editStrategy() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainLogViwer.class.getResource("view/StrategyEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Repository.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            StrategyEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //controller.setStrategy(person);

            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void handleAbout() {
        Dialog.about();
    }


    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
