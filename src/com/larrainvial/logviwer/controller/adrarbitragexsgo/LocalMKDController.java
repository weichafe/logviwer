package com.larrainvial.logviwer.controller.adrarbitragexsgo;

import com.larrainvial.logviwer.model.ModelMarketData;
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

public class LocalMKDController {


    @FXML
    private TextField filterField;

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

    public ObservableList<ModelMarketData> masterData = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> filteredData = FXCollections.observableArrayList();

    private boolean filter = false;

    public LocalMKDController(){

        masterData.addListener(new ListChangeListener<ModelMarketData>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends ModelMarketData> change) {

                updateFilteredData();
            }
        });

    }

    @FXML
    private void initialize() {

        symbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSymbol()));
        messageByType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessageByType()));
        hour.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHour()));
        anio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear()));

        buyQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getBuyQty().toString()));
        buyPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getBuyPx().toString()));

        sellQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSellQty().toString()));
        sellPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSellPx().toString()));

        closePx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getClosePx().toString()));
        mkd_xsgo.setItems(filteredData);

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


    public javafx.scene.control.TableView<ModelMarketData> getType() {

        return mkd_xsgo;
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

    private boolean matchesFilter(ModelMarketData mkdLocal) {

        String filterString = filterField.getText();

        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (mkdLocal.getSymbol().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;

        }

        return false;
    }

    private void reapplyTableSortOrder() {

        mkd_xsgo.setItems(filteredData);
    }
}
