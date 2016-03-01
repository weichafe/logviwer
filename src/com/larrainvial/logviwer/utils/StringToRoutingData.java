package com.larrainvial.logviwer.utils;

import com.javtech.javatoolkit.fix.FixConstants;
import com.larrainvial.logviwer.model.ModelRoutingData;
import org.apache.log4j.Logger;
import quickfix.field.MsgType;
import quickfix.fix44.*;

import java.util.Map;
import java.util.logging.Level;

public class StringToRoutingData {

    private ModelRoutingData modelRoutingData;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ModelRoutingData routing(String message){

        try {

            MsgType typeOfMessage = Message.identifyType(message);
            String[] date = message.split("8=")[0].split("-");

            modelRoutingData = new ModelRoutingData(date[0], date[1], typeOfMessage.getValue());
            modelRoutingData.messageByType = typeOfMessage.getValue();

            Map<Object, Object> messageMap = new Helper().getFixMessageParties(message);

            if (typeOfMessage.getValue().equals(Logout.MSGTYPE)
                    || typeOfMessage.getValue().equals(Logon.MSGTYPE)
                    || typeOfMessage.getValue().equals(SequenceReset.MSGTYPE)
                    || typeOfMessage.getValue().equals(TestRequest.MSGTYPE)
                    || typeOfMessage.getValue().equals(Heartbeat.MSGTYPE)
                    || typeOfMessage.getValue().equals(OrderCancelReject.MSGTYPE)
                    || typeOfMessage.getValue().equals(ResendRequest.MSGTYPE)) {

                return modelRoutingData;
            }

            modelRoutingData.clOrdID = messageMap.containsKey(FixConstants.ClOrdID) ? messageMap.get(FixConstants.ClOrdID).toString().trim() : Constants.EMPTY;
            modelRoutingData.side = messageMap.containsKey(FixConstants.Side) ? Helper.side(messageMap.get(FixConstants.Side).toString()) : Constants.EMPTY;
            modelRoutingData.symbol = messageMap.containsKey(FixConstants.Symbol) ? messageMap.get(FixConstants.Symbol).toString() : messageMap.get(FixConstants.SecurityID).toString();
            modelRoutingData.account = messageMap.containsKey(FixConstants.Account) ? messageMap.get(FixConstants.Account).toString() : Constants.EMPTY;
            modelRoutingData.execType = messageMap.containsKey(FixConstants.ExecType) ? Helper.execType(messageMap.get(FixConstants.ExecType).toString()) : Constants.EMPTY;
            modelRoutingData.orderID = messageMap.containsKey(FixConstants.OrderID) ? messageMap.get(FixConstants.OrderID).toString() : Constants.EMPTY;
            modelRoutingData.execID = messageMap.containsKey(FixConstants.ExecID) ? messageMap.get(FixConstants.ExecID).toString() : Constants.EMPTY;
            modelRoutingData.ordStatus = messageMap.containsKey(FixConstants.OrdStatus) ? Helper.orderStatus(messageMap.get(FixConstants.OrdStatus).toString()) : Constants.EMPTY;
            modelRoutingData.origClOrdID = messageMap.containsKey(FixConstants.OrigClOrdID) ? messageMap.get(FixConstants.OrigClOrdID).toString() : Constants.EMPTY;
            modelRoutingData.clOrdLinkID = messageMap.containsKey(FixConstants.SecondaryClOrdID) ? messageMap.get(FixConstants.SecondaryClOrdID).toString() : Constants.EMPTY;
            modelRoutingData.leavesQty = messageMap.containsKey(FixConstants.LeavesQty) ? Double.valueOf(messageMap.get(FixConstants.LeavesQty).toString()) : Constants.CERO;
            modelRoutingData.cumQty = messageMap.containsKey(FixConstants.CumQty) ? Double.valueOf(messageMap.get(FixConstants.CumQty).toString()) : Constants.CERO;
            modelRoutingData.price = messageMap.containsKey(FixConstants.Price) ? Double.valueOf(messageMap.get(FixConstants.Price).toString()) : Constants.CERO;
            modelRoutingData.orderQty = messageMap.containsKey(FixConstants.OrderQty) ? Double.valueOf(messageMap.get(FixConstants.OrderQty).toString()) : Constants.CERO;
            modelRoutingData.lastQty = messageMap.containsKey(FixConstants.LastQty) ? Double.valueOf(messageMap.get(FixConstants.LastQty).toString().replace(".0", Constants.EMPTY)) : Constants.CERO;
            modelRoutingData.lastPx = messageMap.containsKey(FixConstants.LastPx) ? Double.valueOf(messageMap.get(FixConstants.LastPx).toString()) : Constants.CERO;
            modelRoutingData.avgPx = messageMap.containsKey(FixConstants.AvgPx) ? Double.valueOf(messageMap.get(FixConstants.AvgPx).toString()) : Constants.CERO;
            modelRoutingData.exDestination = messageMap.containsKey(FixConstants.ExDestination) ? messageMap.get(FixConstants.ExDestination).toString() : Constants.EMPTY;
            modelRoutingData.securityExchange = messageMap.containsKey(FixConstants.SecurityExchange) ? messageMap.get(FixConstants.SecurityExchange).toString() : Constants.EMPTY;
            modelRoutingData.text = messageMap.containsKey(FixConstants.Text) ? messageMap.get(FixConstants.Text).toString() : "";

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }


        return modelRoutingData;

    }
}
