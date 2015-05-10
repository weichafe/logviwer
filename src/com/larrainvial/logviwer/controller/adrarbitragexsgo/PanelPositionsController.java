package com.larrainvial.logviwer.controller.adrarbitragexsgo;

import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import com.larrainvial.logviwer.model.ModelPositions;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PanelPositionsController {

    @FXML
    private TableView<ModelPositions> positionTable;

    @FXML
    private TableColumn<ModelPositions, String> symbolLocal;

    @FXML
    private TableColumn<ModelPositions, String> symbolAdr;

    @FXML
    private TableColumn<ModelPositions, String> qtyBuyLocal;

    @FXML
    private TableColumn<ModelPositions, String> qtyBuyAdr;

    @FXML
    private TableColumn<ModelPositions, String> qtySellLocal;

    @FXML
    private TableColumn<ModelPositions, String> qtySellAdr;

    @FXML
    private TableColumn<ModelPositions, String> qtySellLocalRatio;

    @FXML
    private TableColumn<ModelPositions, String> qtyBuyLocalRatio;

    @FXML
    private TableColumn<ModelPositions, String> ratio;

    @FXML
    private void initialize() {

        symbolLocal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSymbolLocal()));
        symbolAdr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSymbolAdr()));
        qtyBuyLocal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQtyBuyLocal().toString()));
        qtyBuyAdr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQtyBuyAdr().toString()));

        qtySellLocal.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getQtySellLocal().toString()));
        qtySellAdr.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getQtySellAdr().toString()));
        qtySellLocalRatio.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getQtySellLocalRatio().toString()));
        qtyBuyLocalRatio.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getQtyBuyLocalRatio().toString()));

        ratio.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getRatio().toString()));
    }

    @FXML
    private void refreshTableView() {

        positionTable.setVisible(true);
        symbolLocal.setVisible(true);
        symbolAdr.setVisible(true);
        qtyBuyLocal.setVisible(true);
        qtyBuyAdr.setVisible(true);
        qtySellLocal.setVisible(true);
        qtySellAdr.setVisible(true);
        qtySellLocalRatio.setVisible(true);
        qtyBuyLocalRatio.setVisible(true);
        ratio.setVisible(true);

    }

    public TableView<ModelPositions> getType() {

        return positionTable;
    }


}
