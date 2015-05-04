package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.event.MarketDataMessageEvent;
import com.larrainvial.logviwer.event.RoutingMessageEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.DataDictionary;
import quickfix.FieldNotFound;
import quickfix.field.*;
import quickfix.fix44.MarketDataIncrementalRefresh;
import quickfix.fix44.MarketDataSnapshotFullRefresh;
import quickfix.fix44.Message;


public class StringToFixMessageListener implements Listener {

    private DataDictionary dictionary;
    private Message messageFix;
    private quickfix.fix44.MarketDataSnapshotFullRefresh.NoMDEntries group;

    public StringToFixMessageListener() {

        try {

            dictionary = new DataDictionary("resources/FIX44.xml");
            group = new quickfix.fix44.MarketDataSnapshotFullRefresh.NoMDEntries();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void eventOccurred(Event event) {

        try {

            StringToFixMessageEvent ev = (StringToFixMessageEvent) event;

            if(ev.lineFromLog.equals("")) return;

            String[] date = ev.lineFromLog.split("8=")[0].split("-");
            messageFix = stringToFix(ev.lineFromLog.split("FIX.4.4" + "\u0001")[1]);


            if(ev.typeMarket.startsWith("ROUTING")){

                ModelRoutingData modelRoutingData = new ModelRoutingData(date[0], date[1], Message.identifyType(ev.lineFromLog).getValue(), this.getSymbolRouting(messageFix));
                Controller.dispatchEvent(new RoutingMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix, modelRoutingData));

            } else {

                ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], Message.identifyType(ev.lineFromLog).getValue(), this.getSymbolMKD(messageFix));
                Controller.dispatchEvent(new MarketDataMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix , modelMarketData));
            }

        } catch (Exception e){
            e.printStackTrace();
            System.out.println(messageFix);

        }

    }

    public String getSymbolRouting(Message message) {

        try {

            for (int i = 0; i < messageFix.getGroups(NoMDEntries.FIELD).size(); i++) {
                message.getGroup(i + 1, group);
                return group.getString(Symbol.FIELD);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(message);
        }

        return "";
    }


    public String getSymbolMKD(Message message) {

        try {

            for (int i = 0; i < messageFix.getGroups(NoMDEntries.FIELD).size(); i++) {
                message.getGroup(i + 1, group);
                return group.getString(Symbol.FIELD);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(message);
        }

        return "";
    }


    private Message stringToFix(String fixMsg){

        Message fixMessage = new Message();

        try {
            fixMessage.fromString(fixMsg, dictionary, false);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(fixMsg);
        }

        return fixMessage;
    }


}