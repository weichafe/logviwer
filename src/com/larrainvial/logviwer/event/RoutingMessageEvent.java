package com.larrainvial.logviwer.event;

import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;
import quickfix.fix44.Message;


public class RoutingMessageEvent  extends Event {

    public String typeMarket;
    public String nameAlgo;
    public Message messageFix;
    public ModelRoutingData modelRoutingData;

    public RoutingMessageEvent(Object source, String nameAlgo, String typeMarket, Message messageFix, ModelRoutingData modelRoutingData) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
        this.messageFix = messageFix;
        this.modelRoutingData = modelRoutingData;

    }
}
