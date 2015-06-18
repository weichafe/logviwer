package com.larrainvial.logviwer.controller.adrarbitragexbog;

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

public class MarketDataDolarController {


    public MarketDataDolarController(){

        masterData.addListener(new ListChangeListener<ModelMarketData>() {
            @Override
            public void onChanged(Change<? extends ModelMarketData> change) {

                updateFilteredData();
            }
        });
    }


    @FXML
    public TextField filterField;

    @FXML
    private TableView<ModelMarketData> dolar;

    @FXML
    private TableColumn<ModelMarketData, String> anio;

    @FXML
    private TableColumn<ModelMarketData, String> hour;

    @FXML
    private TableColumn<ModelMarketData, String> messageByType;

    @FXML
    public TableColumn<ModelMarketData, String> symbol;

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

    private boolean filter = false;

    public ObservableList<ModelMarketData> masterData = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> filteredData = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws Exception {

        symbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSymbol()));
        messageByType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessageByType()));
        hour.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHour()));
        anio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear()));

        buyQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getBuyQty().toString()));
        buyPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getBuyPx().toString()));

        sellQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSellQty().toString()));
        sellPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSellPx().toString()));

        closePx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getClosePx().toString()));


        dolar.setItems(filteredData);

        filterField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                filter = true;
                updateFilteredData();
            }
        });

    }

    @FXML
    private void refreshTableView() throws Exception {

        dolar.setVisible(false);
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

        return dolar;
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


    private boolean matchesFilter(ModelMarketData dolar) {

        String filterString = filterField.getText();

        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (dolar.getSymbol().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;

        }

        return false;
    }


    private void reapplyTableSortOrder() {

        dolar.setItems(filteredData);
    }

}
