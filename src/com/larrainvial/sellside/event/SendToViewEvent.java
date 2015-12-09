package com.larrainvial.sellside.event;

import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;
import quickfix.fix44.Message;


public class SendToViewEvent extends Event {

    public ModelRoutingData modelRoutingData;


    public SendToViewEvent(Object source, Message message, String typeMessage) {
        super(source);

        try {

            modelRoutingData = new ModelRoutingData();

            modelRoutingData.received = typeMessage;
            modelRoutingData.messageByType = message.getHeader().getString(35);
            modelRoutingData.symbol = message.getString(55);
            modelRoutingData.clOrdID = message.getString(11);

            if (message.isSetField(37)){
                modelRoutingData.orderID = message.getString(37);
            }

            if (message.isSetField(41)){
                modelRoutingData.origClOrdID = message.getString(41);
            }

            if (message.isSetField(583)){
                modelRoutingData.clOrdLinkID = message.getString(583);
            }

            if (message.isSetField(17)){
                modelRoutingData.execID = message.getString(17);
            }

            if (message.isSetField(150)){
                modelRoutingData.execType = message.getString(150);
            }

            if (message.isSetField(39)){
                modelRoutingData.ordStatus = message.getString(39);
            }

            if (message.isSetField(1)){
                modelRoutingData.account = message.getString(1);
            }

            if (message.isSetField(54)){
                modelRoutingData.side = message.getString(54);
            }

            if (message.isSetField(58)){
                modelRoutingData.text = message.getString(58);
            }

            if (message.isSetField(32)){
                modelRoutingData.lastQty = message.getDouble(32);
            }

            if (message.isSetField(31)){
                modelRoutingData.lastPx = message.getDouble(31);
            }

            if (message.isSetField(14)){
                modelRoutingData.cumQty = message.getDouble(14);
            }

            if (message.isSetField(6)){
                modelRoutingData.avgPx = message.getDouble(6);
            }

            if (message.isSetField(151)){
                modelRoutingData.leavesQty = message.getDouble(151);
            }

            if (message.isSetField(111)){
                modelRoutingData.maxFloor = message.getDouble(111);
            }

            if (message.isSetField(100)){
                modelRoutingData.exDestination = message.getString(100);
            }

            if (message.isSetField(207)){
                modelRoutingData.securityExchange = message.getString(207);
            }

            modelRoutingData.price = message.getDouble(44);
            modelRoutingData.orderQty = message.getDouble(38);

            //modelRoutingData.effectiveTime;
            //modelRoutingData.expireTime;


        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
