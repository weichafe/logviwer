package com.larrainvial.logviwer.event.copyfilexbog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.trading.emp.Event;

public class CopyFileEvent extends Event {

    public Algo algo;

    public CopyFileEvent(Algo algo) {
        super(algo);
        this.algo = algo;
    }
}
