package com.larrainvial.logviwer.utils;

import com.javtech.javatoolkit.fix.FixConstants;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.model.ModelRoutingData;
import org.apache.log4j.Logger;
import quickfix.field.MsgType;
import quickfix.fix44.Message;

import java.util.Map;
import java.util.logging.Level;

public class StringToRoutingData {

    private ModelRoutingData modelRoutingData;
    private static Logger logger = Logger.getLogger(CalculatePositions.class.getName());

    public ModelRoutingData routing(String message){

        try {

            MsgType typeOfMessage = Message.identifyType(message);
            String[] date = message.split("8=")[0].split("-");

            modelRoutingData = new ModelRoutingData(date[0], date[1], typeOfMessage.getValue());
            modelRoutingData.messageByType = typeOfMessage.getValue();

            Map<Object, Object> messageMap = new Helper().getFixMessageParties(message);

            if(typeOfMessage.getValue().equals("5") || typeOfMessage.getValue().equals("A") || typeOfMessage.getValue().equals("1")){
                return modelRoutingData;
            }

            modelRoutingData.clOrdID = messageMap.containsKey(FixConstants.ClOrdID) ? messageMap.get(FixConstants.ClOrdID).toString().trim() : "";
            modelRoutingData.side = messageMap.containsKey(FixConstants.Side) ? Helper.side(messageMap.get(FixConstants.Side).toString()) : "";
            modelRoutingData.symbol = messageMap.containsKey(FixConstants.Symbol) ? messageMap.get(FixConstants.Symbol).toString() : "";
            modelRoutingData.account = messageMap.containsKey(FixConstants.Account) ? messageMap.get(FixConstants.Account).toString() : "";
            modelRoutingData.execType = messageMap.containsKey(FixConstants.ExecType) ? Helper.execType(messageMap.get(FixConstants.ExecType).toString()) : "";
            modelRoutingData.orderID = messageMap.containsKey(FixConstants.OrderID) ? messageMap.get(FixConstants.OrderID).toString() : "";
            modelRoutingData.execID = messageMap.containsKey(FixConstants.ExecID) ? messageMap.get(FixConstants.ExecID).toString() : "";
            modelRoutingData.ordStatus = messageMap.containsKey(FixConstants.OrdStatus) ? Helper.orderStatus(messageMap.get(FixConstants.OrdStatus).toString()) : "";
            modelRoutingData.origClOrdID = messageMap.containsKey(FixConstants.OrigClOrdID) ? messageMap.get(FixConstants.OrigClOrdID).toString() : "";
            modelRoutingData.clOrdLinkID = messageMap.containsKey(FixConstants.SecondaryClOrdID) ? messageMap.get(FixConstants.SecondaryClOrdID).toString() : "";
            modelRoutingData.leavesQty = messageMap.containsKey(FixConstants.LeavesQty) ? Double.valueOf(messageMap.get(FixConstants.LeavesQty).toString()) : 0d;
            modelRoutingData.cumQty = messageMap.containsKey(FixConstants.CumQty) ? Double.valueOf(messageMap.get(FixConstants.CumQty).toString()) : 0d;
            modelRoutingData.price = messageMap.containsKey(FixConstants.Price) ? Double.valueOf(messageMap.get(FixConstants.Price).toString()) : 0d;
            modelRoutingData.orderQty = messageMap.containsKey(FixConstants.OrderQty) ? Double.valueOf(messageMap.get(FixConstants.OrderQty).toString()) : 0d;
            modelRoutingData.lastQty = messageMap.containsKey(FixConstants.LastQty) ? Double.valueOf(messageMap.get(FixConstants.LastQty).toString().replace(".0", "")) : 0d;
            modelRoutingData.lastPx = messageMap.containsKey(FixConstants.LastPx) ? Double.valueOf(messageMap.get(FixConstants.LastPx).toString()) : 0d;
            modelRoutingData.avgPx = messageMap.containsKey(FixConstants.AvgPx) ? Double.valueOf(messageMap.get(FixConstants.AvgPx).toString()) : 0d;
            modelRoutingData.exDestination = messageMap.containsKey(FixConstants.ExDestination) ? messageMap.get(FixConstants.ExDestination).toString() : "";
            modelRoutingData.securityExchange = messageMap.containsKey(FixConstants.SecurityExchange) ? messageMap.get(FixConstants.SecurityExchange).toString() : "";
            modelRoutingData.text = messageMap.containsKey(FixConstants.Text) ? messageMap.get(FixConstants.Text).toString() : "";

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }


        return modelRoutingData;

    }
}
