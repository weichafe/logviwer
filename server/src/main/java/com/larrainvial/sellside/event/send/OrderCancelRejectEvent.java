package com.larrainvial.sellside.event.send;

import com.larrainvial.trading.emp.Event;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.Message;
import quickfix.fix44.OrderCancelReplaceRequest;
import quickfix.fix44.OrderCancelRequest;

public class OrderCancelRejectEvent  extends Event {

    public String[] cxlRejReason;
    public Message message;
    public String cxlRejResponseTo;


    public OrderCancelRejectEvent(Object source, OrderCancelRequest orderCancelRequest, String[] cxlRejReason, String cxlRejResponseTo) {

        super(source);
        this.cxlRejReason = cxlRejReason;
        this.message = orderCancelRequest;
        this.cxlRejResponseTo = cxlRejResponseTo;
    }

    public OrderCancelRejectEvent(Object source, OrderCancelReplaceRequest orderCancelReplaceRequest, String[] cxlRejReason, String cxlRejResponseTo) {

        super(source);
        this.cxlRejReason = cxlRejReason;
        this.message = orderCancelReplaceRequest;
        this.cxlRejResponseTo = cxlRejResponseTo;
    }

    public OrderCancelRejectEvent(Object source, OrderCancelRequest orderCancelRequest, String[] cancelDMA01s, ExecutionReport workOrders) {
        super(source);
        this.cxlRejReason = cancelDMA01s;
        this.message = orderCancelRequest;
        this.cxlRejResponseTo = cxlRejResponseTo;

    }
}

