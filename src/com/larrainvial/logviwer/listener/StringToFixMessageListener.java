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
import quickfix.fix44.*;


public class StringToFixMessageListener implements Listener {

    private DataDictionary dictionary;
    private String symbol;
    private String mesage;
    private Message messageFix;

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


            mesage = ev.lineFromLog;
            String[] date = mesage.split("8=")[0].split("-");
            messageFix = stringToFix(mesage.split("FIX.4.4" + "\u0001")[1]);
            if(mesage.equals("")) return;

            MsgType msgType = Message.identifyType(mesage);


            if(msgType.valueEquals(NewOrderSingle.MSGTYPE)){

                ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), messageFix.getGroups(146).get(0).toString(), 0d, 0d, 0d, 0d, 0d);
                Controller.dispatchEvent(new RoutingMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix, modelMarketData));

            } else if(msgType.valueEquals(MarketDataSnapshotFullRefresh.MSGTYPE)){

                ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), messageFix.getString(Symbol.FIELD),  0d, 0d, 0d, 0d, 0d);
                Controller.dispatchEvent(new MarketDataMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix , modelMarketData));

            } else if(msgType.valueEquals(MarketDataIncrementalRefresh.MSGTYPE)){

                ModelMarketData modelMarketData;

                if(messageFix.getGroup(1, new RFQRequest.NoRelatedSym()).isSetField(55))
                    modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), messageFix.getGroup(1, new RFQRequest.NoRelatedSym()).getString(55),  0d, 0d, 0d, 0d, 0d);
                else
                    modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), messageFix.getGroup(1, new MarketDataIncrementalRefresh.NoMDEntries()).getString(55),  0d, 0d, 0d, 0d, 0d);

                Controller.dispatchEvent(new MarketDataMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix , modelMarketData));


            } else if(msgType.valueEquals(MarketDataRequest.MSGTYPE)){

                ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), messageFix.getGroup(1, new RFQRequest.NoRelatedSym()).getString(55),  0d, 0d, 0d, 0d, 0d);
                Controller.dispatchEvent(new MarketDataMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix , modelMarketData));

            }else {

                if(msgType.valueEquals(News.MSGTYPE)) return;

                ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), "",  0d, 0d, 0d, 0d, 0d);
                Controller.dispatchEvent(new MarketDataMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix , modelMarketData));
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(messageFix);

        }

    }


    public Message stringToFix(String fixMsg){

        Message fixMessage = new Message();

        try {
            fixMessage.fromString(fixMsg, dictionary, false);

        }catch (Exception e){
            e.printStackTrace();
        }

        return fixMessage;
    }




}
