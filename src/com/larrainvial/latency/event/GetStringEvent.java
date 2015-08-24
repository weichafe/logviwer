package com.larrainvial.latency.event;

import com.larrainvial.latency.Algo;
import com.larrainvial.trading.emp.Event;

public class GetStringEvent extends Event {

    public Algo algo;
    public String getLineString;

    public GetStringEvent(Algo algo, String getLineString) {
        super(algo);
        this.algo = algo;
        this.getLineString = getLineString;
    }
}
