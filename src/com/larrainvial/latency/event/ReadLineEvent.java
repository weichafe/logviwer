package com.larrainvial.latency.event;

import com.larrainvial.latency.Algo;
import com.larrainvial.trading.emp.Event;

public class ReadLineEvent extends Event {

    public Algo algo;
    public String nameFile;

    public ReadLineEvent(Algo algo, String nameFile) {
        super(algo);
        this.algo = algo;
        this.nameFile = nameFile;
    }
}
