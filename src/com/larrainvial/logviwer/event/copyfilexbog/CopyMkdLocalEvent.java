package com.larrainvial.logviwer.event.copyfilexbog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class CopyMkdLocalEvent extends Event {

    public Algo algo;

    public CopyMkdLocalEvent(Algo algo) {
        super(algo);
        this.algo = algo;
    }
}
