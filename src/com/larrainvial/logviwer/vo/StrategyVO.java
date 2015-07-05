package com.larrainvial.logviwer.vo;


public class StrategyVO {

    public String nameAlgo;
    public String mkdDolar;
    public String mkdLocal;
    public String mkdAdr;
    public String routingLocal;
    public String routingAdr;
    public double time;

    public StrategyVO(String nameAlgo, String mkdDolar, String mkdLocal, String mkdAdr, String routingLocal, String routingAdr, double time){

        this.nameAlgo = nameAlgo;
        this.mkdDolar = mkdDolar;
        this.mkdLocal = mkdLocal;
        this.mkdAdr = mkdAdr;
        this.routingLocal = routingLocal;
        this.routingAdr = routingAdr;
        this.time = time;

    }


}
