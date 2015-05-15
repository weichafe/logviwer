package com.larrainvial.logviwer.event;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class AlertEvent extends Event {

    public String lineFromLog;
    public Algo algo;

    public AlertEvent(Object source, String lineFromLog, Algo algo) {
        super(source);
        this.lineFromLog = lineFromLog;
        this.algo = algo;

    }

}
