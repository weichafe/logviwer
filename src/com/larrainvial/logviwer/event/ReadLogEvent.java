package com.larrainvial.logviwer.event;

import com.larrainvial.trading.emp.Event;

import java.io.BufferedReader;


public class ReadLogEvent extends Event {

    public String nameAlgo;
    public String typeMarket;
    public BufferedReader bufferedReader;


    public ReadLogEvent(Object source, String nameAlgo, String typeMarket, BufferedReader bufferedReader) {
        super(source);
        this.nameAlgo = nameAlgo;
        this.typeMarket = typeMarket;
        this.bufferedReader = bufferedReader;

    }
}
