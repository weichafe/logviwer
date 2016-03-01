package com.larrainvial.logviwer.controller.algos;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.utils.Constants;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.text.DecimalFormat;


public class PanelPositionsController {

    @FXML
    private TableView<ModelPositions> positionTable;


    @FXML
    private TableColumn<ModelPositions, String> localSymbol;

    @FXML
    private TableColumn<ModelPositions, String> adrSymbol;


    @FXML
    private TableColumn<ModelPositions, String> inflowLocal;

    @FXML
    private TableColumn<ModelPositions, String> inflowAdr;

    @FXML
    private TableColumn<ModelPositions, String> leaveInflow;

    @FXML
    private TableColumn<ModelPositions, String> leaveFlowback;


    @FXML
    private TableColumn<ModelPositions, String> flobackSell;

    @FXML
    private TableColumn<ModelPositions, String> localSell;

    @FXML
    private TableColumn<ModelPositions, String> localBuy;

    @FXML
    private TableColumn<ModelPositions, String> flowbackLocal;


    @FXML
    private TableColumn<ModelPositions, String> differenceInflow;

    @FXML
    private TableColumn<ModelPositions, String> differenceFlowback;

    @FXML
    private TableColumn<ModelPositions, String> ratio;

    @FXML
    private void initialize() throws Exception {

        localSymbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().localSymbol));
        adrSymbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().adrSymbol));
        localBuy.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().localBuy.toString()));
        flobackSell.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().flobackSell.toString()));
        localSell.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().localSell.toString()));
        inflowAdr.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().inflowAdr.toString()));
        flowbackLocal.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().flowbackLocal.toString()));
        inflowLocal.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().inflowLocal.toString()));
        differenceFlowback.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().differenceflowback.toString()));
        differenceInflow.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().differenceInflow.toString()));
        leaveInflow.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().leaveInflow.toString()));
        leaveFlowback.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().leaveFlowback.toString()));

        leaveFlowback.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        leaveInflow.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        ratio.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().ratio.toString()));


        differenceInflow.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                        if (item != null && (Math.abs(Double.valueOf(item)) >= Constants.DIFFERENCE_QTY)) {
                            setStyle("-fx-text-fill: #ffc208; -fx-font-weight: bold;");
                        } else {
                            setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        differenceFlowback.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                        if (item != null && (Math.abs(Double.valueOf(item)) >= Constants.DIFFERENCE_QTY)) {
                            setStyle("-fx-text-fill: #ffc208; -fx-font-weight: bold;");
                        } else {
                            setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        localBuy.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        inflowAdr.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setStyle("-fx-text-fill: #10bd22;");
                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });



        flobackSell.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setStyle("-fx-text-fill: #fa1f18;");
                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        localSell.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        inflowLocal.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setStyle("-fx-text-fill: #10bd22;");
                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        flowbackLocal.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setStyle("-fx-text-fill: #fa1f18;");
                        setText((item == null) ? "" : Repository.formatNumber.format(Double.valueOf(item)));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

    }

    @FXML
    private synchronized void refreshTableView() {

        positionTable.setVisible(true);
        differenceFlowback.setVisible(true);
        differenceInflow.setVisible(true);
        localSymbol.setVisible(true);
        adrSymbol.setVisible(true);
        localBuy.setVisible(true);
        flobackSell.setVisible(true);
        localSell.setVisible(true);
        inflowAdr.setVisible(true);
        flowbackLocal.setVisible(true);
        inflowLocal.setVisible(true);
        ratio.setVisible(true);
    }

    public TableView<ModelPositions> getType() {

        return positionTable;
    }

}