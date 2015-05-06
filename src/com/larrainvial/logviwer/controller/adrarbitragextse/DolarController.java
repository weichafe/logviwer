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

public class DolarController {

    public DolarController(){

        masterData.addListener(new ListChangeListener<ModelMarketData>() {
            @Override
            public void onChanged(Change<? extends ModelMarketData> change) {

                updateFilteredData();
            }
        });

    }

    @FXML
    private TextField filterField;

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

    private boolean filter = false;


    public ObservableList<ModelMarketData> masterData = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> filteredData = FXCollections.observableArrayList();

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

        // Must re-sort table after items changed
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

        //ArrayList<TableColumn<ModelMarketData, ?>> sortOrder = new ArrayList<>(dolar.getSortOrder());
        //dolar.getSortOrder().clear();
        //dolar.getSortOrder().addAll(sortOrder);
        dolar.setItems(filteredData);
    }

}
