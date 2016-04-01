package com.larrainvial.logviwer.event.copyfilexbog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class CopyDolarEvent extends Event {

    public Algo algo;

    public CopyDolarEvent(Algo algo) {
        super(algo);
        this.algo = algo;
    }
}
