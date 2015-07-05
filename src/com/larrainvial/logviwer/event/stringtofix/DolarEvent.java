package com.larrainvial.logviwer.event.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class DolarEvent extends Event {

    public String lineFromLog;
    public Algo algo;

    public DolarEvent(Object source, String lineFromLog) {
        super(source);
        this.algo = (Algo) source;
        this.lineFromLog = lineFromLog;
    }

}
