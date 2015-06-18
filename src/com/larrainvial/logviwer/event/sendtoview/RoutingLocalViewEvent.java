package com.larrainvial.logviwer.event.sendtoview;


import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;

public class RoutingLocalViewEvent extends Event {

    public String nameAlgo;
    public String typeMarket;
    public ModelRoutingData modelRoutingData;

    public RoutingLocalViewEvent(Object source, String nameAlgo, String typeMarket, ModelRoutingData modelRoutingData) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
        this.modelRoutingData = modelRoutingData;

    }

}
