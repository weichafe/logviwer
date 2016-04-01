package com.larrainvial.logviwer.event.copyfilexbog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class CopyMkdAdrlEvent extends Event {

    public Algo algo;

    public CopyMkdAdrlEvent(Algo algo) {
        super(algo);
        this.algo = algo;
    }

}
