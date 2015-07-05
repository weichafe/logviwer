package com.larrainvial.logviwer.event.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Event;

public class MarketDataAdrViewEvent  extends Event {

    public Algo algo;
    public ModelMarketData modelMarketData;

    public MarketDataAdrViewEvent(Object source, ModelMarketData modelMarketData) {
        super(source);
        this.algo = (Algo) source;
        this.modelMarketData = modelMarketData;
    }

}
