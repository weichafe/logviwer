package com.larrainvial;


import com.larrainvial.logviwer.MainLogViwer;
import com.larrainvial.sellside.MainAppSellSide;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        //SellSide
        MainAppSellSide.sellside();

        //LogViwer
        new MainLogViwer().start(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }


}
