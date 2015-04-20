package com.larrainvial.logviwer.event;

import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Event;
import quickfix.fix44.Message;


public class MarketDataMessageEvent  extends Event {

    public String typeMarket;
    public String nameAlgo;
    public Message messageFix;
    public ModelMarketData modelMarketData;

    public MarketDataMessageEvent(Object source, String nameAlgo, String typeMarket, Message messageFix, ModelMarketData modelMarketData) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
        this.messageFix = messageFix;
        this.modelMarketData = modelMarketData;

    }
}
