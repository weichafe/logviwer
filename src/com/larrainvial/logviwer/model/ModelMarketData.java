package com.larrainvial.logviwer.model;

public class ModelMarketData {

    public String symbol;
    public String hour;
    public String year;
    public String messageByType;
    public Double buyPx;
    public Double buyQty;
    public Double sellPx;
    public Double sellQty;
    public Double closePx;
    public double tradeAmount;


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
        this.tradeAmount = 0d;
    }

}
