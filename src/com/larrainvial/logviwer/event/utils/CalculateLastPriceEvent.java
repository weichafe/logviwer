package com.larrainvial.logviwer.event.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Event;


public class CalculateLastPriceEvent extends Event {

    public Algo algo;
    public ModelMarketData modelMarketData;
    public String type_Market;

    public CalculateLastPriceEvent(Object source, ModelMarketData modelMarketData, String type_Market) {
        super(source);
        this.algo = (Algo) source;
        this.modelMarketData = modelMarketData;
        this.type_Market = type_Market;
    }
}
