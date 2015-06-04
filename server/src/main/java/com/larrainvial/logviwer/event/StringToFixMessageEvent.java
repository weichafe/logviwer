package com.larrainvial.logviwer.event;


import com.larrainvial.trading.emp.Event;

public class StringToFixMessageEvent extends Event {

    public String lineFromLog;
    public String nameAlgo;
    public String typeMarket;

    public StringToFixMessageEvent(Object source, String lineFromLog, String nameAlgo, String typeMarket) {
        super(source);
        this.lineFromLog = lineFromLog;
        this.nameAlgo = nameAlgo;
        this.typeMarket = typeMarket;
    }
}
