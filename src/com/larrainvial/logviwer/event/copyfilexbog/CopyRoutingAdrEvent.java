package com.larrainvial.logviwer.event.copyfilexbog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class CopyRoutingAdrEvent extends Event {

    public Algo algo;

    public CopyRoutingAdrEvent(Algo algo) {
        super(algo);
        this.algo = algo;
    }
}
