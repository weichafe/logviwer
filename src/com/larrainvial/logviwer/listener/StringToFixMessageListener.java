package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.event.MarketDataMessageEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.logviwer.event.RoutingMessageEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.DataDictionary;
import quickfix.field.*;
import quickfix.fix42.MarketDataSnapshotFullRefresh;
import quickfix.fix44.Message;
import quickfix.fix44.NewOrderSingle;


public class StringToFixMessageListener implements Listener {

    private  DataDictionary dictionary;
    private  String symbol;

    public StringToFixMessageListener() {

        try {
            dictionary = new DataDictionary("resources/FIX44.xml");
            symbol = "";

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void eventOccurred(Event event) {

        try {

            StringToFixMessageEvent ev = (StringToFixMessageEvent) event;

            String mesage = ev.lineFromLog;

            Message messageFix = stringToFix(mesage);
            MsgType msgType = Message.identifyType(mesage);

            String[] date = mesage.split("8=")[0].split("-");

            if(msgType.valueEquals(NewOrderSingle.MSGTYPE)){
                ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), messageFix.getString(Symbol.FIELD),  0d, 0d, 0d, 0d, 0d);
                Controller.dispatchEvent(new RoutingMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix, modelMarketData));

            } else if(msgType.valueEquals(MarketDataSnapshotFullRefresh.MSGTYPE)){
                ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), messageFix.getString(Symbol.FIELD),  0d, 0d, 0d, 0d, 0d);
                Controller.dispatchEvent(new MarketDataMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix , modelMarketData));

            }else {
                //ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), messageFix.getString(Symbol.FIELD),  0d, 0d, 0d, 0d, 0d);
                //Controller.dispatchEvent(new MarketDataMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix , modelMarketData));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public Message stringToFix(String fixMsg){

        Message fixMessage = new Message();

        try {
            fixMessage.fromString(fixMsg.split("FIX.4.4" + "\u0001")[1], dictionary, false);
        }catch (Exception e){
            e.printStackTrace();
        }

        return fixMessage;
    }




}
