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
    public Double tradeAmount;


    public ModelMarketData(String anio, String hour, String messageByType) {

        this.year = anio;
        this.messageByType = messageByType;
        this.hour =  hour;
        this.symbol =  "";
        this.buyPx = 0.0;
        this.buyQty =  0.0;
        this.sellPx =  0.0;
        this.sellQty =  0.0;
        this.closePx =  0.0;
        this.tradeAmount = 0.0;
    }

}
