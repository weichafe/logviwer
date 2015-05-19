package com.larrainvial.logviwer.controller.adrarbitragexsgo;


import com.larrainvial.logviwer.model.ModelRoutingData;
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

public class RoutingLocalController {

    @FXML
    private TextField filterFieldSymbol;

    @FXML
    private TextField filterFieldClOrdID;

    @FXML
    private TextField filterFieldOrderID;

    @FXML
    private TextField filterFieldOrigClOrdID;

    @FXML
    private TextField filterFieldExecType;

    @FXML
    private TextField filterFieldOrdStatus;

    @FXML
    private TextField filterFieldAccount;

    @FXML
    private TextField filterFieldSide;

    @FXML
    private TextField filterFieldClOrdLinkID;

    @FXML
    private TableView<ModelRoutingData> routing_xsgo;

    @FXML
    private TableColumn<ModelRoutingData, String> year;

    @FXML
    private TableColumn<ModelRoutingData, String> hour;

    @FXML
    private TableColumn<ModelRoutingData, String> messageByType;

    @FXML
    private TableColumn<ModelRoutingData, String> symbol;

    @FXML
    private TableColumn<ModelRoutingData, String> orderID;

    @FXML
    private TableColumn<ModelRoutingData, String> clOrdID;

    @FXML
    private TableColumn<ModelRoutingData, String> origClOrdID;

    @FXML
    private TableColumn<ModelRoutingData, String> execID;

    @FXML
    private TableColumn<ModelRoutingData, String> execType;

    @FXML
    private TableColumn<ModelRoutingData, String> ordStatus;

    @FXML
    private TableColumn<ModelRoutingData, String> account;

    @FXML
    private TableColumn<ModelRoutingData, String> side;

    @FXML
    private TableColumn<ModelRoutingData, String> clOrdLinkID;

    @FXML
    private TableColumn<ModelRoutingData, String> effectiveTime;

    @FXML
    private TableColumn<ModelRoutingData, String> expireTime;

    @FXML
    private TableColumn<ModelRoutingData, String> text;

    @FXML
    private TableColumn<ModelRoutingData, String> exDestination;

    @FXML
    private TableColumn<ModelRoutingData, String> securityExchange;

    @FXML
    private TableColumn<ModelRoutingData, String> price;

    @FXML
    private TableColumn<ModelRoutingData, String> lastQty;

    @FXML
    private TableColumn<ModelRoutingData, String> lastPx;

    @FXML
    private TableColumn<ModelRoutingData, String> cumQty;

    @FXML
    private TableColumn<ModelRoutingData, String> avgPx;

    @FXML
    private TableColumn<ModelRoutingData, String> leavesQty;

    @FXML
    private TableColumn<ModelRoutingData, String> maxFloor;


    public ObservableList<ModelRoutingData> masterData = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDataSymbol = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDataSide = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDataAccount = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDataOrdStatus = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDataExecType = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDataClOrdLinkID = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDatadOrigClOrdID = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDataClOrdID = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> filteredDataOrderID = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> auxFilterData;
    public TextField auxFilterField;


    private boolean filter = false;

    public RoutingLocalController(){

        masterData.addListener(new ListChangeListener<ModelRoutingData>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends ModelRoutingData> change) {
                updateFilteredData(auxFilterData, auxFilterField);
            }
        });

    }

    @FXML
    private void refreshTableView() {

        routing_xsgo.setVisible(true);
        hour.setVisible(true);
        year.setVisible(true);
        messageByType.setVisible(true);
        symbol.setVisible(true);
        orderID.setVisible(true);
        clOrdID.setVisible(true);
        origClOrdID.setVisible(true);
        clOrdLinkID.setVisible(true);

        execID.setVisible(true);
        execType.setVisible(true);
        ordStatus.setVisible(true);
        account.setVisible(true);
        side.setVisible(true);

        effectiveTime.setVisible(true);
        text.setVisible(true);
        expireTime.setVisible(true);
        exDestination.setVisible(true);
        securityExchange.setVisible(true);

        securityExchange.setVisible(true);
        price.setVisible(true);
        lastQty.setVisible(true);
        lastPx.setVisible(true);
        cumQty.setVisible(true);
        avgPx.setVisible(true);
        leavesQty.setVisible(true);
        maxFloor.setVisible(true);

    }

    @FXML
    private void initialize() throws Exception {

        symbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSymbol()));
        messageByType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessageByType()));
        hour.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHour()));
        year.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear()));

        orderID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getOrderID()));
        clOrdID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getClOrdID()));
        origClOrdID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getOrigClOrdID()));
        clOrdLinkID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getClOrdLinkID()));

        execID.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getExecID()));
        execType.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getExecType()));
        ordStatus.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getOrdStatus()));
        account.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getAccount()));
        side.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSide()));

        effectiveTime.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getEffectiveTime()));
        expireTime.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getExpireTime()));
        exDestination.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getExDestination()));
        securityExchange.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getSecurityExchange()));
        text.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getText()));

        price.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getPrice().toString()));
        lastQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getLastQty().toString()));
        lastPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getLastPx().toString()));
        cumQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getCumQty().toString()));
        avgPx.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getAvgPx().toString()));
        leavesQty.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getLeavesQty().toString()));
        maxFloor.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().getMaxFloor().toString()));

        routing_xsgo.setItems(filteredDataSymbol);

        filterFieldSymbol.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataSymbol, filterFieldSymbol);
            }
        });

        filterFieldClOrdID.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataClOrdID, filterFieldClOrdID);
            }
        });

        filterFieldOrderID.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataOrderID, filterFieldOrderID);
            }
        });

        filterFieldOrigClOrdID.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDatadOrigClOrdID, filterFieldOrigClOrdID);
            }
        });


        filterFieldExecType.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataExecType, filterFieldExecType);
            }
        });

        filterFieldOrdStatus.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataOrdStatus, filterFieldOrdStatus);
            }
        });

        filterFieldAccount.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataAccount, filterFieldAccount);
            }
        });

        filterFieldSide.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataSide, filterFieldSide);
            }
        });

        filterFieldClOrdLinkID.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filter = true;
                updateFilteredData(filteredDataClOrdLinkID, filterFieldClOrdLinkID);
            }
        });


    }



    public TableView<ModelRoutingData> getType() {

        return routing_xsgo;
    }

    private void updateFilteredData(ObservableList<ModelRoutingData> filterData, TextField filterField) {

        if(!filter) return;

        auxFilterData = filterData;
        auxFilterField = filterField;

        filterData.clear();

        for (ModelRoutingData p : masterData) {
            if (matchesFilter(p, filterField)) {
                filterData.add(p);
            }
        }

        reapplyTableSortOrder(filterData);
    }

    private boolean matchesFilter(ModelRoutingData routingAdr, TextField filterField) {

        String filterString = filterField.getText();

        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();


        if(filterField.getId().equals("filterFieldClOrdLinkID")){
            if (routingAdr.getClOrdLinkID().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterFieldSide")){
            if (routingAdr.getSide().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterFieldAccount")){
            if (routingAdr.getAccount().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterFieldOrdStatus")){
            if (routingAdr.getOrdStatus().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterFieldExecType")){
            if (routingAdr.getExecType().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterFieldOrigClOrdID")){
            if (routingAdr.getOrigClOrdID().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterFieldOrderID")){
            if (routingAdr.getOrderID().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterFieldSymbol")){
            if (routingAdr.getSymbol().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        if(filterField.getId().equals("filterFieldClOrdID")){
            if (routingAdr.getClOrdID().toString().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                return true;
            }
        }

        return false;
    }

    private void reapplyTableSortOrder(ObservableList<ModelRoutingData> filterData) {

        routing_xsgo.setItems(filterData);
    }
}