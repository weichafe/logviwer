package com.larrainvial.logviwer.event;


import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;

public class SendToViewEvent extends Event {

    public String nameAlgo;
    public String typeMarket;
    public ModelMarketData modelMarketData;
    public ModelRoutingData modelRoutingData;
    public boolean marketData = false;

    public SendToViewEvent(Object source, String nameAlgo, String typeMarket, ModelMarketData modelMarketData) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
        this.modelMarketData = modelMarketData;
    }

    public SendToViewEvent(Object source, String nameAlgo, String typeMarket, ModelRoutingData modelRoutingData) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
        this.modelRoutingData = modelRoutingData;
        this.marketData = true;
    }

}
