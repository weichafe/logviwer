package com.larrainvial.logviwer.fxvo;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.vo.LatencyVO;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public class Dialog {

    public static synchronized void latency(LatencyVO latencyVO) {

        try {

            String text = "Name Algo : " + latencyVO.nameAlgo + "\n";
            text += "ClOrdID: " + latencyVO.clOrdID + "\n";
            text += "Routing: " + latencyVO.routing + "\n";
            text += "Latency: " + latencyVO.timeLatency + " mm" + "\n";

            final String finalText = text;

            Platform.runLater(new Runnable() {
                public void run() {
                    Notifier.INSTANCE.notifyWarning("Latency", finalText);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
            Helper.printerLog(e.toString());
            Notifier.INSTANCE.notifyError("Error", e.toString());

        }
    }


    public static synchronized void about() {

        try {

            Platform.runLater(new Runnable() {

                public void run() {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("LOG VIEWER LARRAINVIAL");
                    alert.setContentText("Author: Etrading\n");
                    alert.showAndWait();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Helper.printerLog(e.toString());
            Notifier.INSTANCE.notifyError("Error", e.toString());

        }
    }

}
