package com.larrainvial.logviwer.event.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;

public class RoutingLocalViewEvent extends Event {

    public Algo algo;
    public ModelRoutingData modelRoutingData;

    public RoutingLocalViewEvent(Object source, ModelRoutingData modelRoutingData) {
        super(source);
        this.algo = (Algo) source;
        this.modelRoutingData = modelRoutingData;

    }

}
