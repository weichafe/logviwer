package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.listener.MarketDataToStringListener;
import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.MarketDataToStringEvent;
import com.larrainvial.logviwer.listener.ReadLogListener;
import com.larrainvial.logviwer.listener.SendToViewListener;
import com.larrainvial.trading.emp.Controller;

public class Control {

    public static void initialize(){

        Controller.addEventListener(ReadLogEvent.class, new ReadLogListener());
        Controller.addEventListener(MarketDataToStringEvent.class, new MarketDataToStringListener());
        Controller.addEventListener(SendToViewEvent.class, new SendToViewListener());
    }

}
