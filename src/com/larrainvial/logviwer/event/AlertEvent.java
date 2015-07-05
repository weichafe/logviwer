package com.larrainvial.logviwer.event;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;

public class AlertEvent extends Event {

    public ModelMarketData modelMarketData;
    public ModelRoutingData modelRoutingData;
    public String execType;
    public Algo algo;

    public AlertEvent(Object source, ModelMarketData modelMarketData) {
        super(source);
        this.algo = (Algo) source;
        this.modelMarketData = modelMarketData;
        this.execType = modelMarketData.messageByType;
    }

    public AlertEvent(Object source, ModelRoutingData modelRoutingData) {
        super(source);
        this.algo = (Algo) source;
        this.modelRoutingData = modelRoutingData;
        this.execType = modelRoutingData.execType;

    }

}
