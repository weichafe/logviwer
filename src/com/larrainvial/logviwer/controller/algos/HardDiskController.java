package com.larrainvial.logviwer.controller.algos;

import com.larrainvial.logviwer.model.ModelHardDisk;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.utils.Constants;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.text.DecimalFormat;

public class HardDiskController {

    @FXML
    private TableView<ModelHardDisk> hardDisk;

    @FXML
    private TableColumn<ModelHardDisk, String> hostname;

    @FXML
    private TableColumn<ModelHardDisk, String> tamano;

    @FXML
    private TableColumn<ModelHardDisk, String> usados;

    @FXML
    public TableColumn<ModelHardDisk, String> disponible;

    @FXML
    private TableColumn<ModelHardDisk, String> uso;

    @FXML
    private TableColumn<ModelHardDisk, String> montado;

    private DecimalFormat formatNumber = new DecimalFormat( "#,###,###,##0" );
    private DecimalFormat formatNumberDecimal = new DecimalFormat( "#,###,###,##0.00" );


    @FXML
    private synchronized void initialize() throws Exception {

        tamano.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tamano));
        usados.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usados));
        disponible.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().disponible.toString()));
        uso.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().uso.toString()));
        montado.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().montado.toString()));
        hostname.setCellValueFactory(cellData2 -> new SimpleStringProperty(cellData2.getValue().hostname.toString()));

        hostname.setCellFactory(column -> {
            return new TableCell<ModelHardDisk, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        setText(item);
                        setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        montado.setCellFactory(column -> {
            return new TableCell<ModelHardDisk, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {
                        setText(item);
                        setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });


        uso.setCellFactory(column -> {
            return new TableCell<ModelHardDisk, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        if (item == null) return;

                        setText(item);

                        if(Integer.valueOf(item.replace("%","")) >= 90){
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                        } else if (Integer.valueOf(item.replace("%","")) >= 70)  {
                            setStyle("-fx-text-fill: #ffc208; -fx-font-weight: bold;");
                        } else {
                            setStyle("-fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        disponible.setCellFactory(column -> {
            return new TableCell<ModelHardDisk, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {
                        setText(item);
                        setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });


        usados.setCellFactory(column -> {
            return new TableCell<ModelHardDisk, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {
                        setText(item);
                        setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

        tamano.setCellFactory(column -> {
            return new TableCell<ModelHardDisk, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {
                        setText(item);
                        setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            };
        });

    }

    @FXML
    private void refreshTableView() throws Exception {

        hostname.setVisible(false);
        tamano.setVisible(true);
        usados.setVisible(true);
        disponible.setVisible(true);
        uso.setVisible(true);
        montado.setVisible(true);


    }


    public TableView<ModelHardDisk> getType() {
        return hardDisk;
    }

}
