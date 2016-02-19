package com.larrainvial.logviwer.model;


public class ModelPositions {

    public String localSymbol;
    public String adrSymbol;

    public Double inflowLocal = 0d;
    public Double inflowAdr = 0d;
    public Double leaveInflow = 0d;

    public Double flowbackLocal = 0d;
    public Double flobackSell = 0d;
    public Double leaveFlowback = 0d;

    public Long differenceInflow = 0l;
    public Long differenceflowback = 0l;

    public Double localBuy = 0d;
    public Double localSell = 0d;

    public Double ratio = 1d;
    public int positions = 1;

}
