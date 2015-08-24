package com.larrainvial.latency.event;

import com.larrainvial.latency.Algo;
import com.larrainvial.trading.emp.Event;

public class GetDirectoryEvent extends Event {

    public Algo algo;

    public GetDirectoryEvent(Algo algo) {
        super(algo);
        this.algo = algo;
    }
}
