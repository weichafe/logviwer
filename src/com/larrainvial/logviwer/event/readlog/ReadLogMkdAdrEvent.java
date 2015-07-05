package com.larrainvial.logviwer.event.readlog;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

import java.io.FileInputStream;

public class ReadLogMkdAdrEvent extends Event {

    public Algo algo;

    public ReadLogMkdAdrEvent(Object source) {
        super(source);
        this.algo = (Algo) source;
    }
}
