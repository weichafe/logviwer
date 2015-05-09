package com.larrainvial.logviwer.listener;

import com.javtech.javatoolkit.fix.FixConstants;
import com.javtech.javatoolkit.message.Attribute;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.field.MsgType;
import quickfix.fix44.Message;

import java.util.*;


public class StringToFixMessageListener implements Listener {

    private String[] date;
    private MsgType typeOfMessage;
    private ModelRoutingData modelRoutingData;
    private ModelMarketData modelMarketData;

    @Override
    public void eventOccurred(Event event) {

        try {

            StringToFixMessageEvent ev = (StringToFixMessageEvent) event;

            if(ev.lineFromLog.equals("")) return;

            typeOfMessage = Message.identifyType(ev.lineFromLog);
            date = ev.lineFromLog.split("8=")[0].split("-");

            if(ev.typeMarket.startsWith("ROUTING")){

                this.routing(ev.lineFromLog);
                Controller.dispatchEvent(new SendToViewEvent(this, ev.nameAlgo, ev.typeMarket, modelRoutingData));


            } else {

                this.marketData(ev.lineFromLog);
                Controller.dispatchEvent(new SendToViewEvent(this, ev.nameAlgo, ev.typeMarket, modelMarketData));
            }



        } catch (Exception e){
            e.printStackTrace();

        } finally {
            return;
        }

    }


    private void marketData(String message) {

        try {

            ArrayList<Map> mDEntryType;

            modelMarketData = new ModelMarketData(date[0], date[1], typeOfMessage.getValue());

            Map<Object, Object> messageMap = getFixMessageAttributeFull(message);

            modelMarketData.symbol = messageMap.containsKey(FixConstants.Symbol) ? messageMap.get(FixConstants.Symbol).toString() : "";


            if(typeOfMessage.getValue().equals("5") || typeOfMessage.getValue().equals("A") || typeOfMessage.getValue().equals("1")){
                return;
            }

            if(typeOfMessage.getValue().equals("0") ){
                return;
            }

            mDEntryType = messageMap.containsKey(FixConstants.NoMDEntries) ? (ArrayList<Map>) messageMap.get(FixConstants.NoMDEntries) : null;

            if (messageMap.containsKey(FixConstants.NoMDEntryTypes)) {
                return;
            }

            for (Map map : mDEntryType) {

                if (map.get(FixConstants.MDEntryType).equals("0")) {

                    if (map.get(FixConstants.MDEntrySize).toString() != null) {
                        modelMarketData.buyQty = Double.valueOf(map.get(FixConstants.MDEntrySize).toString());
                    }

                    if (map.get(FixConstants.MDEntryPx).toString() != null) {
                        modelMarketData.buyPx = Double.valueOf(map.get(FixConstants.MDEntryPx).toString());
                    }
                }

                if (map.get(FixConstants.MDEntryType).equals("1")) {

                    if (map.get(FixConstants.MDEntrySize).toString() != null) {
                        modelMarketData.sellQty = Double.valueOf(map.get(FixConstants.MDEntrySize).toString());
                    }

                    if (map.get(FixConstants.MDEntryPx).toString() != null) {
                        modelMarketData.sellPx = Double.valueOf(map.get(FixConstants.MDEntryPx).toString());
                    }

                }

                if (map.get(FixConstants.MDEntryType).equals("5")) {

                    if (map.get(FixConstants.MDEntryPx).toString() != null) {
                        modelMarketData.closePx = Double.valueOf(map.get(FixConstants.MDEntryPx).toString());
                    }
                }

                if (map.get(FixConstants.MDEntryType).equals("D")) {
                    if (map.get(FixConstants.MDEntrySize).toString() != null) {
                        //modelMarketData.s = Double.valueOf(map.get(FixConstants.MDEntrySize).toString());
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(message);

        }  finally {
            return ;
        }
    }

    private void routing(String message){

        modelRoutingData = new ModelRoutingData(date[0], date[1], typeOfMessage.getValue());

        Map<Object, Object> messageMap = this.getFixMessageParties(message);

        modelRoutingData.clOrdID = messageMap.containsKey(FixConstants.ClOrdID) ? messageMap.get(FixConstants.ClOrdID).toString().trim() : "";
        modelRoutingData.side = messageMap.containsKey(FixConstants.Side) ? messageMap.get(FixConstants.Side).toString() : "";
        modelRoutingData.symbol = messageMap.containsKey(FixConstants.Symbol) ? messageMap.get(FixConstants.Symbol).toString() : "";
        modelRoutingData.account = messageMap.containsKey(FixConstants.Account) ? messageMap.get(FixConstants.Account).toString() : "";
        modelRoutingData.execType = messageMap.containsKey(FixConstants.ExecType) ? messageMap.get(FixConstants.ExecType).toString() : "";
        modelRoutingData.orderID = messageMap.containsKey(FixConstants.OrderID) ? messageMap.get(FixConstants.OrderID).toString() : "";
        modelRoutingData.execID = messageMap.containsKey(FixConstants.ExecID) ? messageMap.get(FixConstants.ExecID).toString() : "";
        modelRoutingData.ordStatus = messageMap.containsKey(FixConstants.OrdStatus) ? messageMap.get(FixConstants.OrdStatus).toString() : "";
        modelRoutingData.origClOrdID = messageMap.containsKey(FixConstants.OrigClOrdID) ? messageMap.get(FixConstants.OrigClOrdID).toString() : "";
        modelRoutingData.clOrdLinkID = messageMap.containsKey(FixConstants.SecondaryClOrdID) ? messageMap.get(FixConstants.SecondaryClOrdID).toString() : "";
        modelRoutingData.leavesQty = messageMap.containsKey(FixConstants.LeavesQty) ? Double.valueOf(messageMap.get(FixConstants.LeavesQty).toString()) : 0d;
        modelRoutingData.cumQty = messageMap.containsKey(FixConstants.CumQty) ? Double.valueOf(messageMap.get(FixConstants.CumQty).toString()) : 0d;
        modelRoutingData.price = messageMap.containsKey(FixConstants.Price) ? Double.valueOf(messageMap.get(FixConstants.Price).toString()) : 0d;
        modelRoutingData.lastQty = messageMap.containsKey(FixConstants.LastQty) ? Double.valueOf(messageMap.get(FixConstants.LastQty).toString().replace(".0", "")) : 0d;
        modelRoutingData.lastPx = messageMap.containsKey(FixConstants.LastPx) ? Double.valueOf(messageMap.get(FixConstants.LastPx).toString()) : 0d;
        modelRoutingData.avgPx = messageMap.containsKey(FixConstants.AvgPx) ? Double.valueOf(messageMap.get(FixConstants.AvgPx).toString()) : 0d;
        modelRoutingData.exDestination = messageMap.containsKey(FixConstants.ExDestination) ? messageMap.get(FixConstants.ExDestination).toString() : "";
        modelRoutingData.securityExchange = messageMap.containsKey(FixConstants.SecurityExchange) ? messageMap.get(FixConstants.SecurityExchange).toString() : "";
    }



    public static Map<Object, Object> getFixMessageParties(String fixMessageString) {

        try {

            Map<Object, Object> orderedFixMessage = new HashMap<Object, Object>();
            Map<Object, Object> mdParties = null;
            List<Map> parties = new ArrayList<Map>();

            Integer noPartyIDs = -1;
            Integer countId = 0;

            HashMap attributes = getHashWithAttribute();

            String[] valuesFixMessage = fixMessageString.split("\1");
            for (String tag : valuesFixMessage) {

                String[] value = tag.split("=");

                Attribute attribute = (Attribute) attributes.get(value[0]);

                if (attribute != null) {

                    if (attribute.equals(FixConstants.NoPartyIDs)) noPartyIDs = Integer.valueOf(value[1]);

                    if (attribute.equals(FixConstants.PartyID)) {

                        mdParties = new HashMap<Object, Object>();
                        mdParties.put(attribute, value[1]);
                        parties.add(mdParties);

                        countId++;
                        continue;

                    } else if (countId <= noPartyIDs
                            && attribute.equals(FixConstants.PartyID)
                            || attribute.equals(FixConstants.PartyIDSource)
                            || attribute.equals(FixConstants.PartyRole)) {

                        if (mdParties != null) mdParties.put(attribute, value[1]);

                    } else {

                        orderedFixMessage.put(attribute, value[1]);
                    }

                }
            }

            if (!parties.isEmpty())
                orderedFixMessage.put(attributes.get(String.valueOf(FixConstants.NoPartyIDs.getId())), parties);

            return orderedFixMessage;

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return null;
    }

    public static HashMap getHashWithAttribute() {

        HashMap attributes = new HashMap();

        for (Object object : FixConstants.m_attributes.getAllAttributes()) {
            Attribute attribute = (Attribute) object;
            attributes.put(String.valueOf(attribute.getId()), attribute);
        }

        return attributes;
    }

    public Map<Object, Object> getFixMessageAttributeFull(String fixMessageString)throws Exception {

            Map<Object, Object> orderedFixMessage = new HashMap<Object, Object>();

            Map<Object, Object> mdEntries = null;
            List<Map> entries = new ArrayList<Map>();

            Integer noMDEntries = -1;
            Integer countId = 0;

            HashMap attributes = getHashWithAttribute();

            String[] valuesFixMessage = fixMessageString.split("\1");

            for(String tag : valuesFixMessage){

                String[] value = tag.split("=");

                Attribute attribute = (value[0].equals("1020")) ? (Attribute) attributes.get(String.valueOf(FixConstants.MDEntrySize.getId())) : (Attribute) attributes.get(value[0]);

                if(attribute != null){

                    if(attribute.equals(FixConstants.NoMDEntries)) noMDEntries = Integer.valueOf(value[1]);

                    if(attribute.equals(FixConstants.MDEntryType)){

                        mdEntries = new HashMap<Object, Object>();

                        mdEntries.put(attribute, value[1]);

                        entries.add(mdEntries);

                        countId++;
                        continue;

                    }else if(countId <= noMDEntries){

                        if(mdEntries != null) mdEntries.put(attribute, value[1]);
                        orderedFixMessage.put(attribute, value[1]);

                    }else{

                        orderedFixMessage.put(attribute, value[1]);
                    }
                }
            }

            if(!entries.isEmpty()) orderedFixMessage.put(attributes.get(String.valueOf(FixConstants.NoMDEntries.getId())), entries);
                return orderedFixMessage;




    }


}