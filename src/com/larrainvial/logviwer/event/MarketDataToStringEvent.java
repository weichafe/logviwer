package com.larrainvial.logviwer.event;


import com.larrainvial.trading.emp.Event;

public class MarketDataToStringEvent extends Event {

    public String lineFromLog;
    public String nameAlgo;
    public String typeMarket;

    public MarketDataToStringEvent(Object source, String lineFromLog, String nameAlgo, String typeMarket) {
        super(source);
        this.lineFromLog = lineFromLog;
        this.nameAlgo = nameAlgo;
        this.typeMarket = typeMarket;
    }
}
