package com.larrainvial.logviwer.model;

import java.io.Serializable;

public class ModelMarketData implements Serializable {

    private static final long serialVersionUID = 666L;

    public String symbol;
    public String hour;
    public String year;
    public String messageByType;
    public Double buyPx;
    public Double buyQty;
    public Double sellPx;
    public Double sellQty;
    public Double closePx;
    public Double composite;


    public ModelMarketData(String anio, String hour, String messageByType) {

        this.year = anio;
        this.messageByType = messageByType;
        this.hour =  hour;
        this.symbol =  "";
        this.buyPx =  0d;
        this.buyQty =  0d;
        this.sellPx =  0d;
        this.sellQty =  0d;
        this.closePx =  0d;
        this.composite = 0d;
    }

    public ModelMarketData() {

    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMessageByType(String messageByType) {
        this.messageByType = messageByType;
    }

    public void setBuyPx(Double buyPx) {
        this.buyPx = buyPx;
    }

    public void setBuyQty(Double buyQty) {
        this.buyQty = buyQty;
    }

    public void setSellPx(Double sellPx) {
        this.sellPx = sellPx;
    }

    public void setSellQty(Double sellQty) {
        this.sellQty = sellQty;
    }

    public void setClosePx(Double closePx) {
        this.closePx = closePx;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getHour() {
        return hour;
    }

    public String getYear() {
        return year;
    }

    public String getMessageByType() {
        return messageByType;
    }

    public Double getBuyPx() {
        return buyPx;
    }

    public Double getBuyQty() {
        return buyQty;
    }

    public Double getSellPx() {
        return sellPx;
    }

    public Double getSellQty() {
        return sellQty;
    }

    public Double getClosePx() {
        return closePx;
    }

    public Double getComposite() {
        return composite;
    }

    public void setComposite(Double composite) {
        this.composite = composite;
    }
}
