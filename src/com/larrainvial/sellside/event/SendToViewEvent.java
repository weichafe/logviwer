package com.larrainvial.sellside.event;

import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Event;
import quickfix.field.*;
import quickfix.fix44.Message;


public class SendToViewEvent extends Event {

    public ModelRoutingData modelRoutingData;


    public SendToViewEvent(Object source, Message message, String typeMessage) {

        super(source);

        try {

            modelRoutingData = new ModelRoutingData();

            modelRoutingData.received = typeMessage;
            modelRoutingData.messageByType = message.getHeader().getString(35);
            modelRoutingData.symbol = message.isSetField(Symbol.FIELD) ? message.getString(Symbol.FIELD) : "";
            modelRoutingData.clOrdID = message.getString(ClOrdID.FIELD);

            if (message.isSetField(OrderID.FIELD)){
                modelRoutingData.orderID = message.getString(OrderID.FIELD);
            }

            if (message.isSetField(OrigClOrdID.FIELD)){
                modelRoutingData.origClOrdID = message.getString(OrigClOrdID.FIELD);
            }

            if (message.isSetField(ClOrdLinkID.FIELD)){
                modelRoutingData.clOrdLinkID = message.getString(ClOrdLinkID.FIELD);
            }

            if (message.isSetField(ExecID.FIELD)){
                modelRoutingData.execID = message.getString(ExecID.FIELD);
            }

            if (message.isSetField(ExecType.FIELD)){
                modelRoutingData.execType = message.getString(ExecType.FIELD);
            }

            if (message.isSetField(OrdStatus.FIELD)){
                modelRoutingData.ordStatus = message.getString(OrdStatus.FIELD);
            }

            if (message.isSetField(Account.FIELD)){
                modelRoutingData.account = message.getString(Account.FIELD);
            }

            if (message.isSetField(Side.FIELD)){
                modelRoutingData.side = message.getString(Side.FIELD);
            }

            if (message.isSetField(Text.FIELD)){
                modelRoutingData.text = message.getString(Text.FIELD);
            }

            if (message.isSetField(LastQty.FIELD)){
                modelRoutingData.lastQty = message.getDouble(LastQty.FIELD);
            }

            if (message.isSetField(LastPx.FIELD)){
                modelRoutingData.lastPx = message.getDouble(LastPx.FIELD);
            }

            if (message.isSetField(CumQty.FIELD)){
                modelRoutingData.cumQty = message.getDouble(CumQty.FIELD);
            }

            if (message.isSetField(AvgPx.FIELD)){
                modelRoutingData.avgPx = message.getDouble(AvgPx.FIELD);
            }

            if (message.isSetField(LeavesQty.FIELD)){
                modelRoutingData.leavesQty = message.getDouble(LeavesQty.FIELD);
            }

            if (message.isSetField(MaxFloor.FIELD)){
                modelRoutingData.maxFloor = message.getDouble(MaxFloor.FIELD);
            }

            if (message.isSetField(ExDestination.FIELD)){
                modelRoutingData.exDestination = message.getString(ExDestination.FIELD);
            }

            if (message.isSetField(SecurityExchange.FIELD)){
                modelRoutingData.securityExchange = message.getString(SecurityExchange.FIELD);
            }

            if (message.isSetField(Price.FIELD)){
                modelRoutingData.price = message.getDouble(Price.FIELD);
            }

            modelRoutingData.orderQty = message.isSetField(OrderQty.FIELD) ? message.getDouble(OrderQty.FIELD) : 0d;

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
