package com.larrainvial.logviwer.controller;

import com.larrainvial.logviwer.model.ModelRoutingData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SellSideController {

    @FXML
    private TableView<ModelRoutingData> sellside;

    @FXML
    private TableColumn<ModelRoutingData, String> received;

    @FXML
    private TableColumn<ModelRoutingData, String> messageByType;

    @FXML
    private TableColumn<ModelRoutingData, String> symbol;

    @FXML
    private TableColumn<ModelRoutingData, String> orderID;

    @FXML
    private TableColumn<ModelRoutingData, String> clOrdID;

    @FXML
    private TableColumn<ModelRoutingData, String> origClOrdID;

    @FXML
    private TableColumn<ModelRoutingData, String> execID;

    @FXML
    private TableColumn<ModelRoutingData, String> execType;

    @FXML
    private TableColumn<ModelRoutingData, String> ordStatus;

    @FXML
    private TableColumn<ModelRoutingData, String> account;

    @FXML
    private TableColumn<ModelRoutingData, String> side;

    @FXML
    private TableColumn<ModelRoutingData, String> clOrdLinkID;

    @FXML
    private TableColumn<ModelRoutingData, String> effectiveTime;

    @FXML
    private TableColumn<ModelRoutingData, String> expireTime;

    @FXML
    private TableColumn<ModelRoutingData, String> text;

    @FXML
    private TableColumn<ModelRoutingData, String> exDestination;

    @FXML
    private TableColumn<ModelRoutingData, String> securityExchange;

    @FXML
    private TableColumn<ModelRoutingData, String> price;

    @FXML
    private TableColumn<ModelRoutingData, String> orderQty;

    @FXML
    private TableColumn<ModelRoutingData, String> lastQty;

    @FXML
    private TableColumn<ModelRoutingData, String> lastPx;

    @FXML
    private TableColumn<ModelRoutingData, String> cumQty;

    @FXML
    private TableColumn<ModelRoutingData, String> avgPx;

    @FXML
    private TableColumn<ModelRoutingData, String> leavesQty;

    @FXML
    private TableColumn<ModelRoutingData, String> maxFloor;


    @FXML
    private void refreshTableView() {

        sellside.setVisible(true);
        messageByType.setVisible(true);
        symbol.setVisible(true);
        orderID.setVisible(true);
        clOrdID.setVisible(true);
        origClOrdID.setVisible(true);
        clOrdLinkID.setVisible(true);

        execID.setVisible(true);
        execType.setVisible(true);
        ordStatus.setVisible(true);
        account.setVisible(true);
        side.setVisible(true);

        effectiveTime.setVisible(true);
        expireTime.setVisible(true);
        text.setVisible(true);
        exDestination.setVisible(true);
        securityExchange.setVisible(true);

        securityExchange.setVisible(true);
        price.setVisible(true);
        orderQty.setVisible(true);
        lastQty.setVisible(true);
        lastPx.setVisible(true);
        cumQty.setVisible(true);
        avgPx.setVisible(true);
        leavesQty.setVisible(true);
        maxFloor.setVisible(true);

    }

    @FXML
    private void initialize() throws Exception {

        symbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSymbol()));
        messageByType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessageByType()));

        orderID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getOrderID()));
        clOrdID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getClOrdID()));
        origClOrdID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getOrigClOrdID()));
        clOrdLinkID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getClOrdLinkID()));

        execID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getExecID()));
        execType.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getExecType()));
        ordStatus.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getOrdStatus()));
        account.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getAccount()));
        side.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSide()));

        effectiveTime.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getEffectiveTime()));
        expireTime.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getExpireTime()));
        exDestination.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getExDestination()));
        securityExchange.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSecurityExchange()));
        text.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getText()));

        price.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getPrice().toString()));
        orderQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getOrderQty().toString()));
        lastQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getLastQty().toString()));
        lastPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getLastPx().toString()));
        cumQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getCumQty().toString()));
        avgPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getAvgPx().toString()));
        leavesQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getLeavesQty().toString()));
        maxFloor.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getMaxFloor().toString()));
        received.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getReceived().toString()));

    }

    public TableView<ModelRoutingData> getType() {

        return sellside;
    }

}
