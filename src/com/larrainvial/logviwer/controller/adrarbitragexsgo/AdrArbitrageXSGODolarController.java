package com.larrainvial.logviwer.controller.adrarbitragexsgo;

import com.larrainvial.logviwer.MainApp;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

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

    public void setComponentToPrincipalPanel() {

        try {

            Repository.adrArbitrageXSGO_DOLAR_LOADER.setLocation(MainApp.class.getResource("view/ARDArbitrage_XSGO_MKD_DOLAR.fxml"));
            AnchorPane adrArbitrageXSGO_DOLAR_LOADER = (AnchorPane) Repository.adrArbitrageXSGO_DOLAR_LOADER.load();

            Repository.adrArbitrageXSGO_NYSE_LOADER.setLocation(MainApp.class.getResource("view/ARDArbitrage_XSGO_MKD_NYSE.fxml"));
            AnchorPane adrArbitrageXSGO_MKD_NYSE = (AnchorPane) Repository.adrArbitrageXSGO_NYSE_LOADER.load();

            Repository.tabPanePrincipalTabPanel.getTabs().get(0).setContent(adrArbitrageXSGO_DOLAR_LOADER);
            Repository.tabPanePrincipalTabPanel.getTabs().get(0).setContent(adrArbitrageXSGO_MKD_NYSE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public TableView<ModelMarketData> getModelDolar() {
        return adrArbitrageXSGODolar;
    }

}
