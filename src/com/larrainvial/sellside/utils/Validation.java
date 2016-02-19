package com.larrainvial.sellside.utils;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.sellside.event.send.OrderCancelRejectEvent;
import com.larrainvial.sellside.event.send.RejectedEvent;
import com.larrainvial.trading.emp.Controller;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.NewOrderSingle;
import quickfix.fix44.OrderCancelReplaceRequest;
import quickfix.fix44.OrderCancelRequest;

public class Validation {

    public synchronized boolean validateNewOrderSingleFromBuySide(NewOrderSingle newOrderSingle) throws Exception {


        for (int i=1; i <= newOrderSingle.getGroups(NoPartyIDs.FIELD).size(); i++) {

            if (newOrderSingle.getGroup(i, NoPartyIDs.FIELD).getString(PartyID.FIELD).equals(Constants.Brokers.XPUS)) {

                if (!newOrderSingle.isSetMaxFloor()){
                    Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("Reject XPUS!! Max Floor is necesary")));
                    return false;
                }

                if (!Repository.UUID.containsKey(newOrderSingle.getHeader().getString(SenderSubID.FIELD))){
                    Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("Reject XPUS!! Invalid user 50=?")));
                    return false;
                }

                if (newOrderSingle.getSide().valueEquals(Side.SELL_SHORT)) {

                    if (!newOrderSingle.getString(5700).equals(Constants.Brokers.XPUS)) {
                        Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("Reject XPUS 5700")));
                        return false;
                    }

                    if(newOrderSingle.getLocateReqd().valueEquals(true)){
                        Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("Reject XPUS 114")));
                        return false;
                    }

                }



            }

            if (newOrderSingle.getGroup(i, NoPartyIDs.FIELD).getString(PartyID.FIELD).equals(Constants.Brokers.IB)) {

                if (!newOrderSingle.isSetCurrency()){
                    Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("Reject IB")));
                    return false;
                }

                if (!newOrderSingle.isSetMaxFloor()){
                    Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("Reject IB Max Floor")));
                    return false;
                }
            }

            if (newOrderSingle.getPrice().getValue() == 100000d){
                Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("Rechazado Forzado")));
                return false;
            }

        }


        if (newOrderSingle.isSetPrice() && !newOrderSingle.getOrdType().valueEquals(OrdType.MARKET)){

            if (newOrderSingle.getPrice().getValue() <= 0) {
                Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("44=0 (Precio en cero)")));
                return false;
            }
        }

        if (newOrderSingle.isSetOrderQty()){

            if (newOrderSingle.getOrderQty().getValue() <= 0){
                Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new Text("38=0 Cantidad en cero")));
                return false;
            }

        }

        return true;

    }


    public synchronized boolean validateOrderCancelReplaceRequestFromBuySide(OrderCancelReplaceRequest orderCancelReplaceRequest) throws Exception{


        ExecutionReport workOrders;

        if (orderCancelReplaceRequest.getPrice().getValue() == 100000d) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected Forzado")));
            return false;
        }

        if (orderCancelReplaceRequest.isSetSymbol()){
            if (orderCancelReplaceRequest.getSymbol().valueEquals("PFAVAL")) {
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected Forzado")));
                return false;
            }
        }


        for (int i=1; i <= orderCancelReplaceRequest.getGroups(NoPartyIDs.FIELD).size(); i++) {

            if (orderCancelReplaceRequest.getGroup(i, NoPartyIDs.FIELD).getString(PartyID.FIELD).equals(Constants.Brokers.XPUS)){

                if (!orderCancelReplaceRequest.getHeader().getString(TargetSubID.FIELD).equals(Constants.Brokers.XPUS)){
                    Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected XPUS")));
                    return false;
                }

                if (orderCancelReplaceRequest.getSide().valueEquals(Side.SELL_SHORT)) {

                    if (!orderCancelReplaceRequest.getString(5700).equals(Constants.Brokers.XPUS)) {
                        Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected XPUS")));
                        return false;
                    }

                    if (!orderCancelReplaceRequest.isSetMaxFloor()) {
                        Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected XPUS Max FLoor")));
                        return false;
                    }

                    if (orderCancelReplaceRequest.getLocateReqd().valueEquals(true)) {
                        Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected XPUS")));
                        return false;
                    }

                }

                if (!Repository.UUID.containsKey(orderCancelReplaceRequest.getHeader().getString(SenderSubID.FIELD))) {
                    Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected XPUS")));
                    return false;
                }

            }

            if(orderCancelReplaceRequest.getGroup(i, NoPartyIDs.FIELD).getString(PartyID.FIELD).equals(Constants.Brokers.IB)){

                if (!orderCancelReplaceRequest.isSetCurrency()) {
                    Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected IB")));
                    return false;
                }

                if (!orderCancelReplaceRequest.isSetMaxFloor()) {
                    Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Rejected IB MAX FLoor")));
                    return false;
                }

            }

        }


        if (orderCancelReplaceRequest.getSide().valueEquals(Side.BUY)) {

            if (!Repository.executionWorkOrderBuy.containsKey(orderCancelReplaceRequest.getOrigClOrdID().getValue())) {
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text(Repository.buySide.getPropertiesString("Replace01"))));
                return false;
            }

            workOrders = Repository.executionWorkOrderBuy.get(orderCancelReplaceRequest.getOrigClOrdID().getValue()).workOrders;

        } else {

            if (!Repository.executionWorkOrderSell.containsKey(orderCancelReplaceRequest.getOrigClOrdID().getValue())){
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text(Repository.buySide.getPropertiesString("Replace01"))));
                return false;
            }

            workOrders = Repository.executionWorkOrderSell.get(orderCancelReplaceRequest.getOrigClOrdID().getValue()).workOrders;
        }


        if (workOrders.getClOrdID().valueEquals(orderCancelReplaceRequest.getClOrdID().getValue())) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text(Repository.buySide.getPropertiesString("Replace02"))));
            return  false;
        }

        if (workOrders.getOrdStatus().valueEquals(OrdStatus.FILLED)) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text(Repository.buySide.getPropertiesString("Replace05"))));
            return false;
        }

        if (workOrders.getOrdStatus().valueEquals(OrdStatus.REJECTED)) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text(Repository.buySide.getPropertiesString("Cancel10"))));
            return false;
        }

        if(workOrders.getOrdStatus().valueEquals(OrdStatus.CANCELED)){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text(Repository.buySide.getPropertiesString("Replace07"))));
            return false;
        }

        if(workOrders.getOrdStatus().valueEquals(OrdStatus.DONE_FOR_DAY)) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text(Repository.buySide.getPropertiesString("Replace16"))));
            return false;
        }

        if (orderCancelReplaceRequest.getOrderQty().getValue() < workOrders.getCumQty().getValue()) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text(Repository.buySide.getPropertiesString("Replace13"))));
            return false;
        }

        if (workOrders.isSetSecurityType() && !orderCancelReplaceRequest.isSetSecurityType()) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new Text("Requiere Tag Missing 167=?")));
            return false;
        }

        return true;
    }


    public synchronized boolean validateOrdenCancelRequestFromBuySide(OrderCancelRequest orderCancelRequest) throws Exception {

        ExecutionReport workOrders;

        if (orderCancelRequest.isSetOrderQty()){
            if (orderCancelRequest.getOrderQty().getValue() == 100000d) {
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new Text("Rejected Forzado")));
                return false;
            }
        }


        if (orderCancelRequest.getSide().valueEquals(Side.BUY)) {
            if (!Repository.executionWorkOrderBuy.containsKey(orderCancelRequest.getOrigClOrdID().getValue())) {
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new Text(Repository.buySide.getPropertiesString("Replace01"))));
                return false;
            }

            workOrders = Repository.executionWorkOrderBuy.get(orderCancelRequest.getOrigClOrdID().getValue()).workOrders;

        } else {

            if (!Repository.executionWorkOrderSell.containsKey(orderCancelRequest.getOrigClOrdID().getValue())){
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new Text(Repository.buySide.getPropertiesString("Replace01"))));
                return false;
            }

            workOrders = Repository.executionWorkOrderSell.get(orderCancelRequest.getOrigClOrdID().getValue()).workOrders;
        }

        if (workOrders.getClOrdID().valueEquals(orderCancelRequest.getClOrdID().getValue())) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new Text("Orden not Exist")));
            return false;
        }

        if (workOrders.getOrdStatus().valueEquals(OrdStatus.FILLED)) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new Text("Orden Status FILLED")));
            return false;
        }

        if (workOrders.getOrdStatus().valueEquals(OrdStatus.REJECTED)) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new Text("Orden Status REJECTED")));
            return false;
        }


        if (workOrders.getOrdStatus().valueEquals(OrdStatus.CANCELED)) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new Text("Orden Status CANCELED")));
            return false;
        }


        return true;
    }

}
