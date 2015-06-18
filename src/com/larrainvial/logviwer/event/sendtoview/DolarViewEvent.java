package com.larrainvial.logviwer.event.sendtoview;


import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Event;

public class DolarViewEvent  extends Event {

    public String nameAlgo;
    public String typeMarket;
    public ModelMarketData modelMarketData;

    public DolarViewEvent(Object source, String nameAlgo, String typeMarket, ModelMarketData modelMarketData) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
        this.modelMarketData = modelMarketData;
    }

}
