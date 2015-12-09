package com.larrainvial.sellside.listener.received;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.sellside.event.receievd.ReceivedOrderCancelReplaceRequestEvent;
import com.larrainvial.sellside.event.send.ExecutionReportEvent;
import com.larrainvial.sellside.event.send.TradeEvent;
import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.sellside.utils.Validation;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import com.larrainvial.trading.utils.IDGenerator;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.OrderCancelReplaceRequest;
import java.util.Date;

public class ReceivedOrderCancelReplaceRequestListener implements Listener {

    private OrderCancelReplaceRequest orderCancelReplaceRequest;
    private Orders orders;
    private Validation validator;
    private ExecType execType;

    public ReceivedOrderCancelReplaceRequestListener(){

        validator = new Validation();
        execType =  new ExecType(ExecType.REPLACE);
    }


    @Override
    public void eventOccurred(Event event) {

        try {

            ReceivedOrderCancelReplaceRequestEvent ev = (ReceivedOrderCancelReplaceRequestEvent) event;

            orderCancelReplaceRequest = ev.orderCancelReplaceRequest;

            if (!validator.validateOrderCancelReplaceRequestFromBuySide(orderCancelReplaceRequest)) return;

            if (orderCancelReplaceRequest.getSide().valueEquals(Side.BUY)) {

                orders = Repository.executionWorkOrderBuy.get(orderCancelReplaceRequest.getOrigClOrdID().getValue());
                this.setValuesWorkOrders(orders.workOrders);

                Controller.dispatchEvent(new ExecutionReportEvent(this, orders, execType));

                Repository.executionWorkOrderBuy.put(orderCancelReplaceRequest.getClOrdID().getValue(), orders);
                Repository.executionWorkOrderBuy.remove(orderCancelReplaceRequest.getOrigClOrdID().getValue(), orders);

                Controller.dispatchEvent(new TradeEvent(this, orders));

            } else {

                orders = Repository.executionWorkOrderSell.get(orderCancelReplaceRequest.getOrigClOrdID().getValue());
                this.setValuesWorkOrders(orders.workOrders);

                Controller.dispatchEvent(new ExecutionReportEvent(this, orders, execType));

                Repository.executionWorkOrderSell.put(orderCancelReplaceRequest.getClOrdID().getValue(), orders);
                Repository.executionWorkOrderSell.remove(orderCancelReplaceRequest.getOrigClOrdID().getValue(), orders);

                Controller.dispatchEvent(new TradeEvent(this, orders));

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void setValuesWorkOrders(ExecutionReport workOrders) throws Exception {

        try {

            workOrders.set(orderCancelReplaceRequest.getClOrdID());
            workOrders.set(orderCancelReplaceRequest.getOrigClOrdID());
            workOrders.set(new ExecID(IDGenerator.getID()));
            workOrders.set(new TransactTime(new Date()));
            workOrders.set(orderCancelReplaceRequest.getPrice());
            workOrders.set(orderCancelReplaceRequest.getOrderQty());
            workOrders.set(new OrdStatus(OrdStatus.NEW));

            if (workOrders.getLeavesQty().getValue() == 0d) {
                workOrders.set(new OrdStatus(OrdStatus.FILLED));

            } else if (workOrders.getLeavesQty().getValue() == workOrders.getOrderQty().getValue()) {
                workOrders.set(new OrdStatus(OrdStatus.NEW));

            } else if (workOrders.getCumQty().getValue() > 0d && workOrders.getCumQty().getValue() < workOrders.getOrderQty().getValue()) {
                workOrders.set(new OrdStatus(OrdStatus.PARTIALLY_FILLED));
            }

            orders.workOrders.set(new LeavesQty(orders.workOrders.getOrderQty().getValue() - orders.cumQtyLocal));

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
