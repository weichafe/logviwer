package com.larrainvial.logviwer.controller.algos;

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

public class MarketDataAdrController {

    @FXML
    private TextField filterField;

    @FXML
    private TextField filterType;

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

    public ObservableList<ModelMarketData> filteredDataSymbol = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> filteredDatafilterType = FXCollections.observableArrayList();

    private boolean filter = false;

    private ObservableList<ModelMarketData> auxFilterData;

    private TextField auxFilterField;

    public MarketDataAdrController(){

        masterData.addListener(new ListChangeListener<ModelMarketData>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends ModelMarketData> change) {

                updateFilteredData(auxFilterData, auxFilterField);
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

        mkd_nyse.setItems(filteredDataSymbol);

        filterField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDatafilterType, filterField);
            }
        });

        filterType.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataSymbol, filterType);
            }
        });


    }

    @FXML
    private synchronized void refreshTableView() {

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

    private void updateFilteredData(ObservableList<ModelMarketData> filterData, TextField filterField) {

        if(!filter) return;

        auxFilterData = filterData;
        auxFilterField = filterField;

        filterData.clear();

        for (ModelMarketData p : masterData) {
            if (matchesFilter(p, filterField)) {
                filterData.add(p);
            }
        }

        reapplyTableSortOrder(filterData);
    }

    private boolean matchesFilter(ModelMarketData mkdAdr, TextField filterField) {

        String filterString = filterField.getText();

        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();


        if(filterField.getId().equals("filterField")){
            if (mkdAdr.getSymbol().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterType")){
            if (mkdAdr.getMessageByType().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        return false;
    }

    private void reapplyTableSortOrder(ObservableList<ModelMarketData> filterData) {

        mkd_nyse.setItems(filterData);
    }

}
