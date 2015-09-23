package com.larrainvial.logviwer.event.utils;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;

public class AlertEvent extends Event {

    public ModelMarketData modelMarketData;
    public ModelRoutingData modelRoutingData;
    public String execType;
    public String messageByType;
    public String typeMarket;
    public Algo algo;
    public String text;

    public AlertEvent(Object source, ModelMarketData modelMarketData, String typeMarket) {
        super(source);
        this.algo = (Algo) source;
        this.modelMarketData = modelMarketData;
        this.messageByType = modelMarketData.messageByType;
        this.typeMarket = typeMarket;
        this.text = modelMarketData.text;
    }

    public AlertEvent(Object source, ModelRoutingData modelRoutingData, String typeMarket) {
        super(source);
        this.algo = (Algo) source;
        this.modelRoutingData = modelRoutingData;
        this.execType = modelRoutingData.execType;
        this.messageByType = modelRoutingData.messageByType;
        this.typeMarket = typeMarket;
        this.text = modelRoutingData.text;

    }

}
