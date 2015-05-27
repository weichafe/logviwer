package com.larrainvial.sellside.utils;

import com.larrainvial.sellside.Repository;
import com.larrainvial.sellside.event.send.OrderCancelRejectEvent;
import com.larrainvial.sellside.event.send.RejectedEvent;
import com.larrainvial.trading.emp.Controller;
import quickfix.field.OrdStatus;
import quickfix.field.OrdType;
import quickfix.field.Side;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.NewOrderSingle;
import quickfix.fix44.OrderCancelReplaceRequest;
import quickfix.fix44.OrderCancelRequest;

public class Validation {

    public synchronized boolean validateNewOrderSingleFromBuySide(NewOrderSingle newOrderSingle) throws Exception {

        if (newOrderSingle.isSetPrice() && !newOrderSingle.getOrdType().valueEquals(OrdType.MARKET)){
            if(newOrderSingle.getPrice().getValue() <= 0){
                Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new String[] {"99", "Your request can't be processed, because the Price is zero" }));
                return false;
            }
        }

        if(newOrderSingle.isSetOrderQty()){
            if (newOrderSingle.getOrderQty().getValue() <= 0){
                Controller.dispatchEvent(new RejectedEvent(this, newOrderSingle, new String[] { "99", "Your request can't be processed, because the OrderQty is zero" }));
                return false;
            }

        }

        return true;

    }


    public synchronized boolean validateOrderCancelReplaceRequestFromBuySide(OrderCancelReplaceRequest orderCancelReplaceRequest) throws Exception{


        ExecutionReport workOrders;

        if(orderCancelReplaceRequest.getSide().valueEquals(Side.BUY)){

            if (!Repository.executionWorkOrderBuy.containsKey(orderCancelReplaceRequest.getOrigClOrdID().getValue())){
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new String[]{"1", Repository.buySide.getPropertiesString("Replace01")}, "1"));
                return false;
            }

            workOrders = Repository.executionWorkOrderBuy.get(orderCancelReplaceRequest.getOrigClOrdID().getValue()).workOrders;

        } else {

            if (!Repository.executionWorkOrderSell.containsKey(orderCancelReplaceRequest.getOrigClOrdID().getValue())){
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new String[]{"1", Repository.buySide.getPropertiesString("Replace01")}, "1"));
                return false;
            }

            workOrders = Repository.executionWorkOrderSell.get(orderCancelReplaceRequest.getOrigClOrdID().getValue()).workOrders;
        }



        if (workOrders.getClOrdID().valueEquals(orderCancelReplaceRequest.getClOrdID().getValue())){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new String[]{"6", Repository.buySide.getPropertiesString("Replace02")}, "1"));
            return  false;
        }

        if (workOrders.getOrdStatus().valueEquals(OrdStatus.FILLED)){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new String[]{"99", Repository.buySide.getPropertiesString("Replace05")}, "1"));
            return false;
        }

        if (workOrders.getOrdStatus().valueEquals(OrdStatus.REJECTED)){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new String[]{"99", Repository.buySide.getPropertiesString("Cancel10")}, "1"));
            return false;
        }

        if(workOrders.getOrdStatus().valueEquals(OrdStatus.CANCELED)){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new String[]{"99", Repository.buySide.getPropertiesString("Replace07")}, "1"));
            return false;
        }

        if(workOrders.getOrdStatus().valueEquals(OrdStatus.DONE_FOR_DAY)){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new String[]{"99", Repository.buySide.getPropertiesString("Replace16")}, "1"));
            return false;
        }

        if (orderCancelReplaceRequest.getOrderQty().getValue() < workOrders.getCumQty().getValue()) {
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelReplaceRequest, new String[]{"99", Repository.buySide.getPropertiesString("Replace13")}, "1"));
            return false;
        }

        return true;
    }


    public synchronized boolean validateOrdenCancelRequestFromBuySide(OrderCancelRequest orderCancelRequest) throws Exception {

        ExecutionReport workOrders;

        if(orderCancelRequest.getSide().valueEquals(Side.BUY)){

            if (!Repository.executionWorkOrderBuy.containsKey(orderCancelRequest.getOrigClOrdID().getValue())){
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new String[]{"1", Repository.buySide.getPropertiesString("Replace01")}, "1"));
                return false;
            }

            workOrders = Repository.executionWorkOrderBuy.get(orderCancelRequest.getOrigClOrdID().getValue()).workOrders;

        } else {

            if (!Repository.executionWorkOrderSell.containsKey(orderCancelRequest.getOrigClOrdID().getValue())){
                Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new String[]{"1", Repository.buySide.getPropertiesString("Replace01")}, "1"));
                return false;
            }

            workOrders = Repository.executionWorkOrderSell.get(orderCancelRequest.getOrigClOrdID().getValue()).workOrders;
        }

        if (workOrders.getClOrdID().valueEquals(orderCancelRequest.getClOrdID().getValue())){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new String[]{"99", Repository.buySide.getPropertiesString("Cancel02")}, "1"));
            return false;
        }

        if (workOrders.getOrdStatus().valueEquals(OrdStatus.FILLED)){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new String[]{"99", Repository.buySide.getPropertiesString("Cancel07")}, "1"));
            return false;
        }

        if (workOrders.getOrdStatus().valueEquals(OrdStatus.REJECTED)){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new String[]{"99", Repository.buySide.getPropertiesString("Cancel10")}, "1"));
            return false;
        }


        if (workOrders.getOrdStatus().valueEquals(OrdStatus.CANCELED)){
            Controller.dispatchEvent(new OrderCancelRejectEvent(this, orderCancelRequest, new String[]{"99", Repository.buySide.getPropertiesString("Cancel08")}, "1"));
            return false;
        }


        return true;
    }

}
