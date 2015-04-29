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
import quickfix.field.MsgType;
import quickfix.field.Symbol;
import quickfix.fix44.Message;


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
            if(mesage.equals("")) return;

            String[] date = mesage.split("8=")[0].split("-");
            messageFix = stringToFix(mesage.split("FIX.4.4" + "\u0001")[1]);



            MsgType msgType = Message.identifyType(mesage);


            if(ev.typeMarket.startsWith("ROUTING")){

                ModelRoutingData modelRoutingData = new ModelRoutingData(date[0], date[1], msgType.getValue(), "", "", "", "", "", "", "", "", "", "", "","","","", 0d,0d,0d,0d,0d,0d,0d);
                Controller.dispatchEvent(new RoutingMessageEvent(this, ev.nameAlgo, ev.typeMarket, messageFix, modelRoutingData));

            } else {

                String symbol = (messageFix.isSetField(Symbol.FIELD) ? messageFix.getString(Symbol.FIELD) : "");
                ModelMarketData modelMarketData = new ModelMarketData(date[0], date[1], msgType.getValue(), symbol,  0d, 0d, 0d, 0d, 0d);
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