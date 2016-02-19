package com.larrainvial.logviwer.menu.edit;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.Start;
import com.larrainvial.logviwer.controller.EditStrategyController;
import com.larrainvial.logviwer.controller.algos.PanelPositionsController;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelXml;
import com.larrainvial.logviwer.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.util.Map;
import java.util.logging.Level;

public class EditStrategy {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private FXMLLoader loader = new FXMLLoader();
    public TableView<ModelXml> editStrategy;

    public EditStrategy(Stage primaryStage){

        try {

            Double prefWidth = primaryStage.getWidth();
            Double prefHeight = primaryStage.getHeight();

            VBox general = new VBox();
            general.getStyleClass().add("generalEditStrategy");

            HBox anchorpanel = new HBox();
            anchorpanel.getStyleClass().add("tableviewEditStrategy");

            loader.setLocation(Start.class.getResource("view/editstrategy.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            anchorPane.getStyleClass().add("tableviewEditStrategy");
            general.getChildren().addAll(anchorPane);

            EditStrategyController editStrategyController = loader.getController();
            editStrategy = editStrategyController.getType();

            HBox tableview = new HBox();
            tableview.getStyleClass().add("tableviewEditStrategy");
            tableview.getChildren().add(editStrategy);
            general.getChildren().add(tableview);

            ScrollPane scrollBar = new ScrollPane();
            scrollBar.setContent(general);

            for (Map.Entry<String, Algo> entry : Repository.strategy.entrySet()) {
                if(entry.getKey().equals(Constants.SELL_SIDE)) continue;
                editStrategy.getItems().add(Repository.strategy.get(entry.getKey()).modelXml);
            }


            Stage editStrategy = new Stage();
            editStrategy.initOwner(primaryStage);

            Scene scene = new Scene(scrollBar, prefHeight, prefWidth);
            editStrategy.setScene(scene);
            editStrategy.show();

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }
    }


    public void fillGrill(){

    }


}
