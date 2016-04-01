package com.larrainvial.logviwer.event.copyfilexbog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class CopyRoutingLocalEvent extends Event {

    public Algo algo;

    public CopyRoutingLocalEvent(Algo algo) {
        super(algo);
        this.algo = algo;
    }

}
