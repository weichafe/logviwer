package com.larrainvial.logviwer.event.stringtofix;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class RoutingLocalEvent extends Event {

    public Algo algo;
    public String lineFromLog;

    public RoutingLocalEvent(Object source, String lineFromLog) {
        super(source);
        this.algo = (Algo) source;
        this.lineFromLog = lineFromLog;
    }

}
