package com.larrainvial.logviwer.controller.algos;

import com.larrainvial.logviwer.model.ModelMMBCS;
import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MMBCSController {

    @FXML
    private TableView<ModelMMBCS> mmbcs;

    @FXML
    private TableColumn<ModelMMBCS, String> symbol;

    @FXML
    private TableColumn<ModelMMBCS, String> price;

    @FXML
    private TableColumn<ModelMMBCS, String> orderQty;

    @FXML
    public TableColumn<ModelMMBCS, String> leavesQty;

    @FXML
    public TableColumn<ModelMMBCS, String> side;

    @FXML
    private TableColumn<ModelMMBCS, String> cumQty;

    @FXML
    private TableColumn<ModelMMBCS, String> lastPx;

    @FXML
    private TableColumn<ModelMMBCS, String> lastQty;

    public ObservableList<ModelMMBCS> masterData = FXCollections.observableArrayList();

    @FXML
    private synchronized void initialize() throws Exception {

        symbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().symbol));
        price.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().price.toString()));
        orderQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().orderQty.toString()));
        leavesQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().leavesQty.toString()));
        side.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().side.toString()));
        cumQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().cumQty.toString()));
        lastPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().lastPx.toString()));
        lastQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(String.valueOf(cellData2.getValue().lastQty.toString())));
    }


    @FXML
    private void refreshTableView() throws Exception {

        symbol.setVisible(false);
        price.setVisible(true);
        orderQty.setVisible(true);
        leavesQty.setVisible(true);
        side.setVisible(true);
        cumQty.setVisible(true);
        lastPx.setVisible(true);
        lastQty.setVisible(true);
    }

    public TableView<ModelMMBCS> getType() {
        return mmbcs;
    }


}
