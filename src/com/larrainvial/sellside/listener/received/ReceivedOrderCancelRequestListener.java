package com.larrainvial.sellside.listener.received;


import com.larrainvial.sellside.Repository;
import com.larrainvial.sellside.event.receievd.ReceivedOrderCancelRequestEvent;
import com.larrainvial.sellside.event.send.ExecutionReportEvent;
import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.sellside.utils.Validation;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import com.larrainvial.trading.utils.IDGenerator;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.OrderCancelRequest;

import java.util.Date;

public class ReceivedOrderCancelRequestListener implements Listener {

    private OrderCancelRequest orderCancelRequest;
    private Orders orders;
    private ExecType execType;
    private Validation validator;
    private LastPx lastPx;
    private LastQty lastQty;
    private OrdStatus ordStatus;

    public ReceivedOrderCancelRequestListener(){
        execType =  new ExecType(ExecType.CANCELED);
        lastPx = new LastPx(0d);
        lastQty = new LastQty(0d);
        validator = new Validation();
    }

    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            ReceivedOrderCancelRequestEvent ev = (ReceivedOrderCancelRequestEvent) event;

            orderCancelRequest = ev.orderCancelRequest;

            if (!validator.validateOrdenCancelRequestFromBuySide(orderCancelRequest)) return;

            if (orderCancelRequest.getSide().valueEquals(Side.BUY)){

                orders = Repository.executionWorkOrderBuy.get(orderCancelRequest.getOrigClOrdID().getValue());
                this.setValuesWorkOrders(orders.workOrders);

                Controller.dispatchEvent(new ExecutionReportEvent(this, orders, execType));

                Repository.executionWorkOrderBuy.remove(orderCancelRequest.getOrigClOrdID().getValue(), orders);

            } else {

                orders = Repository.executionWorkOrderSell.get(orderCancelRequest.getOrigClOrdID().getValue());
                this.setValuesWorkOrders(orders.workOrders);

                Controller.dispatchEvent(new ExecutionReportEvent(this, orders, execType));

                Repository.executionWorkOrderSell.put(orderCancelRequest.getClOrdID().getValue(), orders);
                Repository.executionWorkOrderSell.put(orders.workOrders.getOrderID().getValue(), orders);

                Repository.executionWorkOrderSell.remove(orderCancelRequest.getOrigClOrdID().getValue(), orders);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void setValuesWorkOrders(ExecutionReport workOrders) throws Exception {

        workOrders.set(lastPx);
        workOrders.set(lastQty);
        workOrders.set(orderCancelRequest.getClOrdID());
        workOrders.set(orderCancelRequest.getOrigClOrdID());
        workOrders.set(new ExecID(IDGenerator.getID()));
        workOrders.set(new TransactTime(new Date()));
        workOrders.set(new OrdStatus(OrdStatus.CANCELED));

    }
}
