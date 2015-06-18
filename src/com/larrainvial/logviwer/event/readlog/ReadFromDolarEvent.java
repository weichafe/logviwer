package com.larrainvial.logviwer.event.readlog;


import com.larrainvial.trading.emp.Event;

import java.io.FileInputStream;

public class ReadFromDolarEvent extends Event {

    public String nameAlgo;
    public String typeMarket;
    public FileInputStream inputStream;


    public ReadFromDolarEvent(Object source, String nameAlgo, String typeMarket, FileInputStream inputStream) {
        super(source);
        this.nameAlgo = nameAlgo;
        this.typeMarket = typeMarket;
        this.inputStream = inputStream;

    }

}
