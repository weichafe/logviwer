package com.larrainvial.logviwer.fxvo;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.sellside.adaptador.QuickFixAdapter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

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
                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.RIGHT);
                    }

                    if (switchBtn.getId().equals("MKD ADR")) {
                        algo.mkdAdrToggle = true;
                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.RIGHT);
                    }

                    if (switchBtn.getId().equals("MKD Local")) {
                        algo.mkdLocalToggle = true;
                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.RIGHT);
                    }

                    if (switchBtn.getId().equals("Routing Local")) {
                        algo.routingLocalToggle = true;
                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.RIGHT);
                    }

                    if (switchBtn.getId().equals("Routing ADR")) {
                        algo.routingAdrToggle = true;
                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.RIGHT);
                    }


                    if (switchBtn.getId().equals("Alert")) {
                        algo.setAlert(false);

                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);

                    }

                    if (switchBtn.getId().equals("Graph")) {
                        algo.graphEnable = false;

                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);

                    }

                    if (switchBtn.getId().equals("Strart")) {
                        switchBtn.setText("Sell Side OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);
                        new QuickFixAdapter(com.larrainvial.logviwer.Repository.locationPath + Repository.nameFileQuickFix);


                    }

                    if (switchBtn.getId().equals("Reset")) {

                        switchBtn.setText(nameType + " Orders");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);

                        Notifier.INSTANCE.notifyWarning("Sell Side", "All orders were deleted");
                    }


                } else {

                    if (switchBtn.getId().equals("Dolar")) {
                        algo.mkdDolarToggle = false;
                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);
                    }

                    if (switchBtn.getId().equals("MKD ADR")) {
                        algo.mkdAdrToggle = false;
                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);
                    }

                    if (switchBtn.getId().equals("MKD Local")) {
                        algo.mkdLocalToggle = false;
                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);
                    }

                    if (switchBtn.getId().equals("Routing Local")) {
                        algo.routingLocalToggle = false;
                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);
                    }

                    if (switchBtn.getId().equals("Routing ADR")) {
                        algo.routingAdrToggle = false;
                        switchBtn.setText(nameType + " OFF");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);
                    }

                    if (switchBtn.getId().equals("Alert")) {
                        algo.setAlert(true);
                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.RIGHT);

                    }


                    if (switchBtn.getId().equals("Graph")) {
                        algo.graphEnable = true;
                        switchBtn.setText(nameType + " ON");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);

                    }

                    if (switchBtn.getId().equals("Strart")) {
                        switchBtn.setText("Sell Side Start");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(255,0,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: red; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);
                        QuickFixAdapter.strop();
                    }

                    if (switchBtn.getId().equals("Reset")) {

                        switchBtn.setText(nameType + " Orders");
                        switchBtn.setStyle("-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,255,0,0.5) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #10bd22; -fx-font-weight: bold;");
                        switchBtn.setContentDisplay(ContentDisplay.LEFT);
                        Notifier.INSTANCE.notifyWarning("Sell Side", "All orders were deleted");
                        Repository.deleteOrder();
                    }

                }
            }
        });

        switchedOn.set(false);

    }

    public Button returnButton(){
        return switchBtn;
    }


}
