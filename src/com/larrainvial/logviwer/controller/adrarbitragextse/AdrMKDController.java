package com.larrainvial.logviwer.controller.adrarbitragextse;

import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdrMKDController {

    @FXML
    private TextField filterField;

    @FXML
    private TableView<ModelMarketData> mkd_nyse;

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

    public ObservableList<ModelMarketData> masterData = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> filteredData = FXCollections.observableArrayList();

    private boolean filter = false;


    public AdrMKDController(){

        masterData.addListener(new ListChangeListener<ModelMarketData>() {
            @Override
            public void onChanged(Change<? extends ModelMarketData> change) {
                updateFilteredData();
            }
        });

    }

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

        mkd_nyse.setItems(filteredData);

        filterField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData();
            }
        });

    }

    @FXML
    private void refreshTableView() {

        mkd_nyse.setVisible(true);
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


    public TableView<ModelMarketData> getType() {

        return mkd_nyse;
    }

    private void updateFilteredData() {

        if(!filter) return;

        filteredData.clear();

        for (ModelMarketData p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        reapplyTableSortOrder();
    }

    private boolean matchesFilter(ModelMarketData mkdAdr) {

        String filterString = filterField.getText();

        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (mkdAdr.getSymbol().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false;
    }

    private void reapplyTableSortOrder() {
        mkd_nyse.setItems(filteredData);
    }

}
