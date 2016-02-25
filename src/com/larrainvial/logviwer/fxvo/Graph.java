package com.larrainvial.logviwer.fxvo;


import com.larrainvial.logviwer.Algo;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Graph {

    public static void newLineChart(Algo algo){

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Cantidad de Solicitudes");
        yAxis.setLabel("Milesegundos");

        algo.lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        algo.lineChart.setPrefWidth(735);

        algo.adrRouting = new XYChart.Series();
        algo.adrRouting.setName("ADR Routing");

        algo.localRouting = new XYChart.Series();
        algo.localRouting.setName("Local Routing");

        algo.lineChart.getData().addAll(algo.adrRouting, algo.localRouting);

    }
}
