package com.larrainvial.logviwer.controller.adrarbitragexsgo;

import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class XsgoMKDController {

    @FXML
    private TableView<ModelMarketData> mkd_xsgo;

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

        buyQty.setCellValueFactory(cellData2 -> cellData2.getValue().getBuyQty().asString());
        buyPx.setCellValueFactory(cellData2 -> cellData2.getValue().getBuyPx().asString());

        sellQty.setCellValueFactory(cellData2 -> cellData2.getValue().getSellQty().asString());
        sellPx.setCellValueFactory(cellData2 -> cellData2.getValue().getSellPx().asString());

        closePx.setCellValueFactory(cellData2 -> cellData2.getValue().getClosePx().asString());

    }

    @FXML
    private void refreshTableView() {

        mkd_xsgo.setVisible(true);
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


    public javafx.scene.control.TableView<ModelMarketData> getMkdXsgo() {
        return mkd_xsgo;
    }
}
