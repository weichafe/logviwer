package com.larrainvial.logviwer.controller.algos;

import com.larrainvial.logviwer.model.ModelMarketData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MarketDataDolarController {

    @FXML
    private TableView<ModelMarketData> dolar;

    @FXML
    private TextField filterField;

    @FXML
    private TextField filterType;

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

    private String items;

    private boolean filter = false;

    private ObservableList<ModelMarketData> auxFilterData;

    private TextField auxFilterField;

    public ObservableList<ModelMarketData> masterData = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> filteredDataSymbol = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> filteredDatafilterType = FXCollections.observableArrayList();

    public MarketDataDolarController(){

        items = "";

        masterData.addListener(new ListChangeListener<ModelMarketData>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends ModelMarketData> change) {

                updateFilteredData(auxFilterData, auxFilterField);
            }
        });
    }

    @FXML
    private synchronized void initialize() throws Exception {

        messageByType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessageByType()));
        symbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSymbol()));
        hour.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHour()));
        anio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear()));
        buyQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getBuyQty().toString()));
        buyPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getBuyPx().toString()));
        sellQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSellQty().toString()));
        sellPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSellPx().toString()));
        closePx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getClosePx().toString()));


        messageByType.setCellFactory(column -> {
            return new TableCell<ModelMarketData, String>() {

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null || empty) {
                            setText("");

                        } else {

                            items = item;

                            if (item.equals("V")) {
                                setText(item);

                            } else if (item.equals("W")) {
                                setText(item);

                            } else if (item.equals("X")) {
                                setText(item);

                            } else if (item.equals("5")) {
                                setTextFill(Color.RED);
                                //setStyle();
                                setText(item);

                            } else if (item.equals("1")) {
                                setTextFill(Color.AQUA);
                                setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
                                setText(item);

                            } else if (item.equals("A")) {
                                setTextFill(Color.RED);
                                setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
                                setText(item);

                            } else {
                                setText(item);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        });


        dolar.setItems(filteredDataSymbol);

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


    private boolean matchesFilter(ModelMarketData dolar, TextField filterField) {

        String filterString = filterField.getText();

        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();


        if(filterField.getId().equals("filterField")){
            if (dolar.getSymbol().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterType")){
            if (dolar.getMessageByType().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        return false;
    }


    private void reapplyTableSortOrder(ObservableList<ModelMarketData> filterData) {

        dolar.setItems(filterData);
    }

}
