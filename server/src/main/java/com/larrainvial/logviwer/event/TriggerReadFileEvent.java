package com.larrainvial.logviwer.event;


import com.larrainvial.trading.emp.Event;

import java.io.FileInputStream;
import java.util.ArrayList;

public class TriggerReadFileEvent  extends Event {

    public String nameAlgo;
    public ArrayList<String> typeMarket;
    public ArrayList<FileInputStream> typeFile;

    public TriggerReadFileEvent(Object source, String nameAlgo, ArrayList<String> typeMarket, ArrayList<FileInputStream> typeFile) {
        super(source);
        this.nameAlgo = nameAlgo;
        this.typeMarket = typeMarket;
        this.typeFile = typeFile;
    }
}
