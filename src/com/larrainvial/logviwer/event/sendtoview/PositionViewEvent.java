package com.larrainvial.logviwer.event.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;

public class PositionViewEvent extends Event {

    public Algo algo;
    public ModelRoutingData modelRoutingData;

    public PositionViewEvent(Object source, ModelRoutingData modelRoutingData) {
        super(source);
        this.algo = (Algo) source;
        this.modelRoutingData = modelRoutingData;

    }
}
