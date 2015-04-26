package com.larrainvial.logviwer.controller.adrarbitragexsgo;


import com.larrainvial.logviwer.model.ModelRoutingData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class XsgoRoutingController {

    @FXML
    private TableView<ModelRoutingData> routing_xsgo;

    @FXML
    private TableColumn<ModelRoutingData, String> year;

    @FXML
    private TableColumn<ModelRoutingData, String> hour;

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
    private TableColumn<ModelRoutingData, String> execRefID;

    @FXML
    private TableColumn<ModelRoutingData, String> execType;

    @FXML
    private TableColumn<ModelRoutingData, String> ordStatus;

    @FXML
    private TableColumn<ModelRoutingData, String> account;

    @FXML
    private TableColumn<ModelRoutingData, String> side;

    @FXML
    private TableColumn<ModelRoutingData, String> currency;

    @FXML
    private TableColumn<ModelRoutingData, String> clOrdLinkID;

    @FXML
    private TableColumn<ModelRoutingData, String> effectiveTime;

    @FXML
    private TableColumn<ModelRoutingData, String> expireTime;

    @FXML
    private TableColumn<ModelRoutingData, String> exDestination;

    @FXML
    private TableColumn<ModelRoutingData, String> securityExchange;

    @FXML
    private TableColumn<ModelRoutingData, String> price;

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
    private void initialize() {

        symbol.setCellValueFactory(cellData -> cellData.getValue().getSymbol());
        messageByType.setCellValueFactory(cellData -> cellData.getValue().getMessageByType());
        hour.setCellValueFactory(cellData -> cellData.getValue().getHour());
        year.setCellValueFactory(cellData -> cellData.getValue().getYear());

        orderID.setCellValueFactory(cellData2 -> cellData2.getValue().getOrderID());
        clOrdID.setCellValueFactory(cellData2 -> cellData2.getValue().getClOrdID());
        origClOrdID.setCellValueFactory(cellData2 -> cellData2.getValue().getOrigClOrdID());
        clOrdLinkID.setCellValueFactory(cellData2 -> cellData2.getValue().getClOrdLinkID());

        execID.setCellValueFactory(cellData2 -> cellData2.getValue().getExecID());
        execRefID.setCellValueFactory(cellData2 -> cellData2.getValue().getExecRefID());
        execType.setCellValueFactory(cellData2 -> cellData2.getValue().getExecType());
        ordStatus.setCellValueFactory(cellData2 -> cellData2.getValue().getOrdStatus());
        account.setCellValueFactory(cellData2 -> cellData2.getValue().getAccount());
        side.setCellValueFactory(cellData2 -> cellData2.getValue().getSide());

        currency.setCellValueFactory(cellData2 -> cellData2.getValue().getCurrency());
        effectiveTime.setCellValueFactory(cellData2 -> cellData2.getValue().getEffectiveTime());
        expireTime.setCellValueFactory(cellData2 -> cellData2.getValue().getExpireTime());
        exDestination.setCellValueFactory(cellData2 -> cellData2.getValue().getExDestination());
        securityExchange.setCellValueFactory(cellData2 -> cellData2.getValue().getSecurityExchange());

        price.setCellValueFactory(cellData2 -> cellData2.getValue().getPrice().asString());
        lastQty.setCellValueFactory(cellData2 -> cellData2.getValue().getLastQty().asString());
        lastPx.setCellValueFactory(cellData2 -> cellData2.getValue().getLastPx().asString());
        cumQty.setCellValueFactory(cellData2 -> cellData2.getValue().getCumQty().asString());
        avgPx.setCellValueFactory(cellData2 -> cellData2.getValue().getAvgPx().asString());
        leavesQty.setCellValueFactory(cellData2 -> cellData2.getValue().getLeavesQty().asString());
        maxFloor.setCellValueFactory(cellData2 -> cellData2.getValue().getMaxFloor().asString());

    }

    @FXML
    private void refreshTableView() {

        routing_xsgo.setVisible(true);
        hour.setVisible(true);
        year.setVisible(true);
        messageByType.setVisible(true);
        symbol.setVisible(true);
        orderID.setVisible(true);
        clOrdID.setVisible(true);
        origClOrdID.setVisible(true);
        clOrdLinkID.setVisible(true);

        execID.setVisible(true);
        execRefID.setVisible(true);
        execType.setVisible(true);
        ordStatus.setVisible(true);
        account.setVisible(true);
        side.setVisible(true);

        currency.setVisible(true);
        effectiveTime.setVisible(true);
        expireTime.setVisible(true);
        exDestination.setVisible(true);
        securityExchange.setVisible(true);

        securityExchange.setVisible(true);
        price.setVisible(true);
        lastQty.setVisible(true);
        lastPx.setVisible(true);
        cumQty.setVisible(true);
        avgPx.setVisible(true);
        leavesQty.setVisible(true);
        maxFloor.setVisible(true);

    }

    public TableView<ModelRoutingData> getType() {
        return routing_xsgo;
    }




}
