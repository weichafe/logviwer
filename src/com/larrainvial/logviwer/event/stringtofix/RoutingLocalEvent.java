package com.larrainvial.logviwer.event.stringtofix;


import com.larrainvial.trading.emp.Event;

public class RoutingLocalEvent extends Event {

    public String lineFromLog;
    public String nameAlgo;
    public String typeMarket;

    public RoutingLocalEvent(Object source, String lineFromLog, String nameAlgo, String typeMarket) {
        super(source);
        this.lineFromLog = lineFromLog;
        this.nameAlgo = nameAlgo;
        this.typeMarket = typeMarket;
    }

}
