package com.larrainvial.logviwer.controller.adrarbitragexsgo;

import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdrArbitrageXSGODolarController {

    @FXML
    private TableView<ModelMarketData> adrArbitrageXSGODolar;

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

        symbol.setCellValueFactory(cellData -> cellData.getValue().symbol);
        messageByType.setCellValueFactory(cellData -> cellData.getValue().messageByType);
        hour.setCellValueFactory(cellData -> cellData.getValue().hour);
        anio.setCellValueFactory(cellData -> cellData.getValue().anio);

        buyQty.setCellValueFactory(cellData2 -> cellData2.getValue().buyQty.asString());
        buyPx.setCellValueFactory(cellData2 -> cellData2.getValue().buyPx.asString());

        sellQty.setCellValueFactory(cellData2 -> cellData2.getValue().sellQty.asString());
        sellPx.setCellValueFactory(cellData2 -> cellData2.getValue().sellPx.asString());

        closePx.setCellValueFactory(cellData2 -> cellData2.getValue().closePx.asString());

    }

    @FXML
    private void refreshTableView() {

        adrArbitrageXSGODolar.setVisible(true);
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


    public TableView<ModelMarketData> getModelDolar() {

        return adrArbitrageXSGODolar;
    }

}
