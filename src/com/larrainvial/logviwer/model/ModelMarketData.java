package com.larrainvial.logviwer.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelMarketData {

    private final StringProperty symbol;
    private final StringProperty hour;
    private final StringProperty year;
    private final StringProperty messageByType;
    private final DoubleProperty buyPx;
    private final DoubleProperty buyQty;
    private final DoubleProperty sellPx;
    private final DoubleProperty sellQty;
    private final DoubleProperty closePx;


    public ModelMarketData(String anio, String hour, String messageByType, String symbol) {

        this.year = new SimpleStringProperty(anio);
        this.messageByType = new SimpleStringProperty(messageByType);
        this.symbol = new SimpleStringProperty(symbol);
        this.hour = new SimpleStringProperty(hour);
        this.buyPx = new SimpleDoubleProperty(0);
        this.buyQty = new SimpleDoubleProperty(0);
        this.sellPx = new SimpleDoubleProperty(0);
        this.sellQty = new SimpleDoubleProperty(0);
        this.closePx = new SimpleDoubleProperty(0);
    }

    public StringProperty getSymbol() {
        return symbol;
    }

    public StringProperty symbolProperty() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    public StringProperty getHour() {
        return hour;
    }

    public StringProperty hourProperty() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour.set(hour);
    }

    public StringProperty getYear() {
        return year;
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public StringProperty getMessageByType() {
        return messageByType;
    }

    public StringProperty messageByTypeProperty() {
        return messageByType;
    }

    public void setMessageByType(String messageByType) {
        this.messageByType.set(messageByType);
    }

    public DoubleProperty getBuyPx() {
        return buyPx;
    }

    public DoubleProperty buyPxProperty() {
        return buyPx;
    }

    public void setBuyPx(double buyPx) {
        this.buyPx.set(buyPx);
    }

    public DoubleProperty getBuyQty() {
        return buyQty;
    }

    public DoubleProperty buyQtyProperty() {
        return buyQty;
    }

    public void setBuyQty(double buyQty) {
        this.buyQty.set(buyQty);
    }

    public DoubleProperty getSellPx() {
        return sellPx;
    }

    public DoubleProperty sellPxProperty() {
        return sellPx;
    }

    public void setSellPx(double sellPx) {
        this.sellPx.set(sellPx);
    }

    public DoubleProperty getSellQty() {
        return sellQty;
    }

    public DoubleProperty sellQtyProperty() {
        return sellQty;
    }

    public void setSellQty(double sellQty) {
        this.sellQty.set(sellQty);
    }

    public DoubleProperty getClosePx() {
        return closePx;
    }

    public DoubleProperty closePxProperty() {
        return closePx;
    }

    public void setClosePx(double closePx) {
        this.closePx.set(closePx);
    }
}
