package com.larrainvial.logviwer.event;


import com.larrainvial.trading.emp.Event;

public class SendToViewEvent extends Event {

    public String nameAlgo;
    public String typeMarket;

    public SendToViewEvent(Object source, String nameAlgo, String typeMarket) {
        super(source);
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
    }

}
