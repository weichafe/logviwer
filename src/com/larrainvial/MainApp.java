package com.larrainvial;


import com.larrainvial.logviwer.MainLogViwer;
import com.larrainvial.sellside.MainSellSide;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //LogViwer
        new MainLogViwer().start(primaryStage);

        //SellSide
        //new MainSellSide().sellside();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
