package com.larrainvial.logviwer.event.stringtofix;


import com.larrainvial.trading.emp.Event;

public class MarketDataLocalEvent extends Event {

    public String lineFromLog;
    public String nameAlgo;
    public String typeMarket;

    public MarketDataLocalEvent(Object source, String lineFromLog, String nameAlgo, String typeMarket) {
        super(source);
        this.lineFromLog = lineFromLog;
        this.nameAlgo = nameAlgo;
        this.typeMarket = typeMarket;
    }

}
