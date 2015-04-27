package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.event.RoutingMessageEvent;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.field.*;
import quickfix.fix44.Message;


public class RoutingMessageListener implements Listener {

    public ModelRoutingData modelRoutingData;

    @Override
    public void eventOccurred(Event event){

        try {

            RoutingMessageEvent ev = (RoutingMessageEvent) event;

            modelRoutingData = ev.modelRoutingData;

            this.setModelRoutingData(ev.messageFix);
            Controller.dispatchEvent(new SendToViewEvent(this, ev.nameAlgo, ev.typeMarket, modelRoutingData));


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setModelRoutingData(Message messageFix){

        try {

            if (messageFix.isSetField(ClOrdID.FIELD)) {
                modelRoutingData.setClOrdID(messageFix.getString(ClOrdID.FIELD));
            }

            if (messageFix.isSetField(OrigClOrdID.FIELD)) {
                modelRoutingData.setOrigClOrdID(messageFix.getString(OrigClOrdID.FIELD));
            }

            if (messageFix.isSetField(OrderID.FIELD)) {
                modelRoutingData.setOrderID(messageFix.getString(OrderID.FIELD));
            }

            if (messageFix.isSetField(ClOrdLinkID.FIELD)) {
                modelRoutingData.setClOrdLinkID(messageFix.getString(ClOrdLinkID.FIELD));
            }

            if (messageFix.isSetField(ExecID.FIELD)) {
                modelRoutingData.setExecID(messageFix.getString(ExecID.FIELD));
            }

            if (messageFix.isSetField(ExecType.FIELD)) {
                modelRoutingData.setExecType(this.execType(messageFix.getString(ExecType.FIELD)));
            }

            if (messageFix.isSetField(OrdStatus.FIELD)) {
                modelRoutingData.setOrdStatus(messageFix.getString(OrdStatus.FIELD));
            }

            if (messageFix.isSetField(Account.FIELD)) {
                modelRoutingData.setAccount(messageFix.getString(Account.FIELD));
            }

            if (messageFix.isSetField(Side.FIELD)) {
                modelRoutingData.setSide(messageFix.getString(Side.FIELD));
            }

            if (messageFix.isSetField(EffectiveTime.FIELD)) {
                modelRoutingData.setEffectiveTime(messageFix.getString(EffectiveTime.FIELD));
            }

            if (messageFix.isSetField(ExpireTime.FIELD)) {
                modelRoutingData.setExpireTime(messageFix.getString(ExpireTime.FIELD));
            }

            if (messageFix.isSetField(ExDestination.FIELD)) {
                modelRoutingData.setExDestination(messageFix.getString(ExDestination.FIELD));
            }

            if (messageFix.isSetField(SecurityExchange.FIELD)) {
                modelRoutingData.setSecurityExchange(messageFix.getString(SecurityExchange.FIELD));
            }

            if (messageFix.isSetField(Price.FIELD)) {
                modelRoutingData.setPrice(messageFix.getDouble(Price.FIELD));
            }

            if (messageFix.isSetField(LastQty.FIELD)) {
                modelRoutingData.setLastQty(messageFix.getDouble(LastQty.FIELD));
            }

            if (messageFix.isSetField(LastPx.FIELD)) {
                modelRoutingData.setLastPx(messageFix.getDouble(LastPx.FIELD));
            }

            if (messageFix.isSetField(CumQty.FIELD)) {
                modelRoutingData.setCumQty(messageFix.getDouble(CumQty.FIELD));
            }

            if (messageFix.isSetField(AvgPx.FIELD)) {
                modelRoutingData.setAvgPx(messageFix.getDouble(AvgPx.FIELD));
            }

            if (messageFix.isSetField(LeavesQty.FIELD)) {
                modelRoutingData.setLeavesQty(messageFix.getDouble(LeavesQty.FIELD));
            }

            if (messageFix.isSetField(MaxFloor.FIELD)) {
                modelRoutingData.setMaxFloor(messageFix.getDouble(MaxFloor.FIELD));
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String execType(String string){
        if(string.equals("0")) return "NEW";
        else if(string.equals("1")) return "PARTIAL FILL";
        else if(string.equals("B")) return "CALCULATED";
        else if(string.equals("4")) return "CANCELED";
        else if(string.equals("3")) return "DONE FOR DAY";
        else if(string.equals("C")) return "EXPIRED";
        else if(string.equals("I")) return "ORDER STATUS";
        else if(string.equals("6")) return "PENDING CANCEL";
        else if(string.equals("A")) return "PENDING NEW";
        else if(string.equals("2")) return "FILL";
        else if(string.equals("8")) return "REJECTED";
        else if(string.equals("G")) return "TRADE CORRECT";
        else if(string.equals("D")) return "RESTATED";
        else if(string.equals("5")) return "REMPLACED";
        else if(string.equals("E")) return "PENDING REPLACE";
        else  return string;
    }

}