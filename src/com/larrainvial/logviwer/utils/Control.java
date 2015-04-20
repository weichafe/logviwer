package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.event.*;
import com.larrainvial.logviwer.listener.*;
import com.larrainvial.trading.emp.Controller;

public class Control {

    public static void initialize(){

        Controller.addEventListener(ReadLogEvent.class, new ReadLogListener());
        Controller.addEventListener(StringToFixMessageEvent.class, new StringToFixMessageListener());
        Controller.addEventListener(SendToViewEvent.class, new SendToViewListener());
        Controller.addEventListener(RoutingMessageEvent.class, new RoutingMessageListener());
        Controller.addEventListener(MarketDataMessageEvent.class, new MarketDataMessageListener());


    }

}
