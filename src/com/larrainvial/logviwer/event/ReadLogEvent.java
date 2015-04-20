package com.larrainvial.logviwer.event;

import com.larrainvial.trading.emp.Event;

public class ReadLogEvent extends Event {

    public String namefile;
    public String typeMarket;
    public String nameAlgo;

    public ReadLogEvent(Object source, String nameAlgo, String typeMarket, String namefile) {

        super(source);
        this.namefile = namefile;
        this.typeMarket = typeMarket;
        this.nameAlgo = nameAlgo;
    }
}
