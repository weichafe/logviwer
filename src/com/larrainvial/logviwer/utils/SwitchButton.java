package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.sellside.adaptador.QuickFixAdapter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import quickfix.ConfigError;

public class SwitchButton extends Label {

    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);
    private Button switchBtn;

    public SwitchButton(String nameType, Algo algo) {

        switchBtn = new Button();
        switchBtn.setId(nameType);
        switchBtn.setPrefWidth(120);


        switchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                switchedOn.set(!switchedOn.get());
            }
        });

        setGraphic(switchBtn);

        switchedOn.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                if (t) {

                    if (switchBtn.getId().equals("Dolar")) {
                        algo.mkdDolarToggle = true;
                    }

                    if (switchBtn.getId().equals("MKD ADR")) {
                        algo.mkdAdrToggle = true;
                    }

                    if (switchBtn.getId().equals("MKD Local")) {
                        algo.mkdLocalToggle = true;
                    }

                    if (switchBtn.getId().equals("Routing Local")) {
                        algo.routingLocalToggle = true;
                    }

                    if (switchBtn.getId().equals("Routing ADR")) {
                        algo.routingAdrToggle = true;
                    }

                    if (switchBtn.getId().equals("Routing")) {

                        try {

                            algo.routingAdrToggle = true;
                            QuickFixAdapter.start();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    if (switchBtn.getId().equals("Alert")) {
                        algo.setAlert(false);

                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);

                    } else {

                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.RIGHT);

                    }

                } else {

                    if (switchBtn.getId().equals("Dolar")) {
                        algo.mkdDolarToggle = false;
                    }

                    if (switchBtn.getId().equals("MKD ADR")) {
                        algo.mkdAdrToggle = false;
                    }

                    if (switchBtn.getId().equals("MKD Local")) {
                        algo.mkdLocalToggle = false;
                    }

                    if (switchBtn.getId().equals("Routing Local")) {
                        algo.routingLocalToggle = false;
                    }

                    if (switchBtn.getId().equals("Routing ADR")) {
                        algo.routingAdrToggle = false;
                    }

                    if (switchBtn.getId().equals("Routing")) {

                        algo.routingAdrToggle = false;

                        try {
                            QuickFixAdapter.strop();
                        } catch (ConfigError configError) {
                            configError.printStackTrace();
                        }

                    }

                    if (switchBtn.getId().equals("Alert")) {

                        algo.setAlert(true);
                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.RIGHT);

                    } else {

                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);

                    }

                }
            }
        });

        switchedOn.set(false);

    }

    public Button returnButton (){

        return switchBtn;
    }


}
