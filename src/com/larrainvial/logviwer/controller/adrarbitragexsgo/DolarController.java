package com.larrainvial.logviwer.controller.adrarbitragexsgo;

import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DolarController {

    @FXML
    private TableView<ModelMarketData> dolar;

    @FXML
    private TableColumn<ModelMarketData, String> anio;

    @FXML
    private TableColumn<ModelMarketData, String> hour;

    @FXML
    private TableColumn<ModelMarketData, String> messageByType;

    @FXML
    private TableColumn<ModelMarketData, String> symbol;

    @FXML
    private TableColumn<ModelMarketData, String> buyPx;

    @FXML
    private TableColumn<ModelMarketData, String> buyQty;

    @FXML
    private TableColumn<ModelMarketData, String> sellPx;

    @FXML
    private TableColumn<ModelMarketData, String> sellQty;

    @FXML
    private TableColumn<ModelMarketData, String> closePx;

    @FXML
    private void initialize() {

        symbol.setCellValueFactory(cellData -> cellData.getValue().getSymbol());
        messageByType.setCellValueFactory(cellData -> cellData.getValue().getMessageByType());
        hour.setCellValueFactory(cellData -> cellData.getValue().getHour());
        anio.setCellValueFactory(cellData -> cellData.getValue().getYear());

        buyQty.setCellValueFactory(cellData -> cellData.getValue().getBuyQty().asString());
        buyPx.setCellValueFactory(cellData -> cellData.getValue().getBuyPx().asString());

        sellQty.setCellValueFactory(cellData -> cellData.getValue().getSellQty().asString());
        sellPx.setCellValueFactory(cellData -> cellData.getValue().getSellPx().asString());

        closePx.setCellValueFactory(cellData -> cellData.getValue().getClosePx().asString());

    }

    @FXML
    private void refreshTableView() throws Exception{

        dolar.setVisible(true);
        anio.setVisible(true);
        messageByType.setVisible(true);
        symbol.setVisible(true);
        hour.setVisible(true);
        buyPx.setVisible(true);
        buyQty.setVisible(true);
        sellPx.setVisible(true);
        sellQty.setVisible(true);
        closePx.setVisible(true);

    }

    public TableView<ModelMarketData> getDolar() {
        return dolar;
    }

}
