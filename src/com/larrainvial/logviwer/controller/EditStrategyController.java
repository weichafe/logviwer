package com.larrainvial.logviwer.controller;

import com.larrainvial.logviwer.model.ModelXml;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class EditStrategyController {

    @FXML
    private TableView<ModelXml> editStrategy;

    @FXML
    private TableColumn<ModelXml, String> nameAlgo;

    @FXML
    private TableColumn<ModelXml, String> direccion;

    @FXML
    private TableColumn<ModelXml, String> mkd_dolar;

    @FXML
    public TableColumn<ModelXml, String> mkd_nyse;

    @FXML
    private TableColumn<ModelXml, String> mkd_local;

    @FXML
    private TableColumn<ModelXml, String> routing_local;

    @FXML
    private TableColumn<ModelXml, String> routing_nyse;

    @FXML
    private TableColumn<ModelXml, Boolean> booleanDolar;

    @FXML
    private TableColumn<ModelXml, Boolean> booleanMLocal;

    @FXML
    private TableColumn<ModelXml, Boolean> booleanMAdr;

    @FXML
    private TableColumn<ModelXml, Boolean> booleanRLocal;

    @FXML
    private TableColumn<ModelXml, Boolean> booleanRAdr;


    @FXML
    private synchronized void initialize() throws Exception {

        nameAlgo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nameAlgo));
        direccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().location));

        mkd_dolar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().mkd_dolar));
        mkd_nyse.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().mkd_nyse));
        mkd_local.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().mkd_local));
        routing_local.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().routing_local));
        routing_nyse.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().routing_nyse));

        booleanDolar.setCellValueFactory(cellData2 -> new SimpleBooleanProperty(cellData2.getValue().booleanDolar));
        booleanMLocal.setCellValueFactory(cellData2 -> new SimpleBooleanProperty(cellData2.getValue().booleanMLocal));
        booleanMAdr.setCellValueFactory(cellData2 -> new SimpleBooleanProperty(cellData2.getValue().booleanMAdr));
        booleanRLocal.setCellValueFactory(cellData2 -> new SimpleBooleanProperty(cellData2.getValue().booleanRLocal));
        booleanRAdr.setCellValueFactory(cellData2 -> new SimpleBooleanProperty(cellData2.getValue().booleanRAdr));

    }

    @FXML
    private synchronized void refreshTableView() {

        nameAlgo.setVisible(true);
        direccion.setVisible(true);
        mkd_dolar.setVisible(true);
        mkd_nyse.setVisible(true);
        mkd_local.setVisible(true);
        routing_local.setVisible(true);
        routing_nyse.setVisible(true);
        booleanDolar.setVisible(true);
        booleanMLocal.setVisible(true);
        booleanMAdr.setVisible(true);
        booleanRLocal.setVisible(true);
        booleanRAdr.setVisible(true);
    }

    public TableView<ModelXml> getType() {
        return editStrategy;
    }


}
