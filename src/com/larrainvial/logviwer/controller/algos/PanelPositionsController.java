package com.larrainvial.logviwer.controller.algos;

import com.larrainvial.logviwer.model.ModelPositions;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;


public class PanelPositionsController {

    @FXML
    private TableView<ModelPositions> positionTable;

    @FXML
    private TableColumn<ModelPositions, String> symbolLocal;

    @FXML
    private TableColumn<ModelPositions, String> differenceInflow;

    @FXML
    private TableColumn<ModelPositions, String> differenceFlowback;

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

    private DecimalFormat numFormat = new DecimalFormat("###,###.##");

    private boolean validator = false;


    @FXML
    private void initialize() throws Exception {

        symbolLocal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().symbolLocal));
        symbolAdr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().symbolAdr));

        qtyBuyLocal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().qtyBuyLocal.toString()));
        qtyBuyAdr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().qtyBuyAdr.toString()));

        qtySellLocal.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().qtySellLocal.toString()));
        qtySellAdr.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().qtySellAdr.toString()));
        qtySellLocalRatio.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().qtySellLocalRatio.toString()));
        qtyBuyLocalRatio.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().qtyBuyLocalRatio.toString()));
        differenceFlowback.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().differenceflowback.toString()));
        differenceInflow.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().differenceInflow.toString()));

        ratio.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().ratio.toString()));


        differenceInflow.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null || empty) {
                            setText("");
                        } else {
                            setText(numFormat.format(Double.valueOf(item)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        });

        differenceFlowback.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText("");
                    } else {
                        setText(numFormat.format(Double.valueOf(item)));
                    }

                }
            };
        });

        qtyBuyLocal.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null || empty) {
                            setText("");
                        } else {
                            setTextFill(Color.GREEN);
                            setText(numFormat.format(Double.valueOf(item)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        });

        qtySellAdr.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null || empty) {
                            setText("");
                        } else {
                            setTextFill(Color.RED);
                            setText(numFormat.format(Double.valueOf(item)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        });



        qtyBuyAdr.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null || empty) {
                            setText("");
                        }else {
                            setTextFill(Color.GREEN);
                            setText(numFormat.format(Double.valueOf(item)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        });

        qtySellLocal.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null || empty) {
                            setText("");
                        }else {
                            setTextFill(Color.RED);
                            setText(numFormat.format(Double.valueOf(item)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        });

        qtyBuyLocalRatio.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null || empty) {
                            setText("");
                        }else {
                            setTextFill(Color.GREEN);
                            setText(numFormat.format(Double.valueOf(item)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        });

        qtySellLocalRatio.setCellFactory(column -> {
            return new TableCell<ModelPositions, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null || empty) {
                            setText("");
                        } else {
                            setTextFill(Color.RED);
                            setText(numFormat.format(Double.valueOf(item)));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
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

    public void formatingData(){

    }


}