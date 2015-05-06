package com.larrainvial.logviwer.event;

import com.larrainvial.trading.emp.Event;

import java.io.BufferedReader;
import java.io.FileInputStream;


public class ReadLogEvent extends Event {

    public String nameAlgo;
    public String typeMarket;
    public FileInputStream inputStream;


    public ReadLogEvent(Object source, String nameAlgo, String typeMarket, FileInputStream inputStream) {
        super(source);
        this.nameAlgo = nameAlgo;
        this.typeMarket = typeMarket;
        this.inputStream = inputStream;

    }
}
