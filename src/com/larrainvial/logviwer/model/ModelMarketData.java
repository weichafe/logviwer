package com.larrainvial.logviwer.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelMarketData {

    public final StringProperty symbol;
    public final StringProperty hour;
    public final StringProperty anio;
    public final StringProperty messageByType;

    public final DoubleProperty buyPx;
    public final DoubleProperty buyQty;

    public final DoubleProperty sellPx;
    public final DoubleProperty sellQty;

    public final DoubleProperty closePx;

    /**
     * Constructor with some initial data

     * @param messageByType
     * @param symbol
       @param hour
     * @param buyPx
     * @param buyQty
     * @param sellPx
     * @param sellQty
     * @param closePx
     */

    public ModelMarketData(String anio, String hour, String messageByType, String symbol, Double buyPx, Double buyQty, Double sellPx,
                           Double sellQty, Double closePx) {

        this.anio = new SimpleStringProperty(anio);
        this.messageByType = new SimpleStringProperty(messageByType);
        this.symbol = new SimpleStringProperty(symbol);
        this.hour = new SimpleStringProperty(hour);
        this.buyPx = new SimpleDoubleProperty(buyPx);
        this.buyQty = new SimpleDoubleProperty(buyQty);
        this.sellPx = new SimpleDoubleProperty(sellPx);
        this.sellQty = new SimpleDoubleProperty(sellQty);
        this.closePx = new SimpleDoubleProperty(closePx);
    }



}
