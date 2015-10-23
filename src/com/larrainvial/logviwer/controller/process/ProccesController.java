package com.larrainvial.logviwer.controller.process;

import com.larrainvial.logviwer.model.ModelProcess;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;


public class ProccesController {

    @FXML
    private TableView<ModelProcess> process;

    @FXML
    private TableColumn<ModelProcess, String> name;

    @FXML
    private TableColumn<ModelProcess, String> nameprocess;

    @FXML
    private TableColumn<ModelProcess, String> pathbin;

    @FXML
    public TableColumn<ModelProcess, String> comentary;

    @FXML
    private TableColumn<ModelProcess, String> core;

    @FXML
    private synchronized void initialize() throws Exception {

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
        nameprocess.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().processName));
        pathbin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().pathbin));
        comentary.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().comentary));
        //core.setCellValueFactory(cellData2 -> new SimpleBooleanProperty(cellData2.getValue().core));
    }

    @FXML
    private void refreshTableView() throws Exception {

        name.setVisible(false);
        nameprocess.setVisible(true);
        pathbin.setVisible(true);
        comentary.setVisible(true);
        core.setVisible(true);
    }


    public TableView<ModelProcess> getType() {
        return process;
    }

    public void getCore(){

        core.setCellFactory(new Callback<TableColumn<ModelProcess, String>, TableCell<ModelProcess, String>>() {
            @Override public TableCell<ModelProcess, String> call(TableColumn<ModelProcess, String> btnCol) {
                final Button button = new Button();
                button.setMinWidth(130);
                TableCell<ModelProcess, String> cell = new TableCell<ModelProcess, String>() {
                    @Override public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setGraphic(null);
                        } else {
                            button.setText("Do something");
                            setGraphic(button);
                        }

                    }
                };

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent event) {
                        //ModelProcess currentItem = cell.getItem();
                        //do something with current item...
                    }
                });

                return cell ;
            }
        });

        
    }

}
