package com.larrainvial.logviwer.event;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;

public class AlertEvent extends Event {

    public String nameAlgo;
    public String typeMarket;
    public ModelMarketData modelMarketData;
    public ModelRoutingData modelRoutingData;
    public String lineFromLog;

    public AlertEvent(Object source, String nameAlgo, String typeMarket, ModelMarketData modelMarketData, String lineFromLog) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
        this.modelMarketData = modelMarketData;
        this.lineFromLog = lineFromLog;
    }

    public AlertEvent(Object source, String nameAlgo, String typeMarket, ModelRoutingData modelRoutingData, String lineFromLog) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
        this.modelRoutingData = modelRoutingData;
        this.lineFromLog = lineFromLog;
    }

}
