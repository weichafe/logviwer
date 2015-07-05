package com.larrainvial.logviwer.event.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

import java.io.FileInputStream;

public class ReadLogMkdLocalEvent extends Event {

    public Algo algo;

    public ReadLogMkdLocalEvent(Object source) {
        super(source);
        this.algo = (Algo) source;
    }
}
