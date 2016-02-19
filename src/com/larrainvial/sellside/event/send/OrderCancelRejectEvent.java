package com.larrainvial.sellside.event.send;

import com.larrainvial.trading.emp.Event;
import quickfix.field.Text;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.Message;
import quickfix.fix44.OrderCancelReplaceRequest;
import quickfix.fix44.OrderCancelRequest;

public class OrderCancelRejectEvent  extends Event {

    public Message message;
    public Text text;

    public OrderCancelRejectEvent(Object source, OrderCancelRequest orderCancelRequest, Text text) {
        super(source);
        this.message = orderCancelRequest;
        this.text = text;
    }

    public OrderCancelRejectEvent(Object source, OrderCancelReplaceRequest orderCancelReplaceRequest, Text text) {
        super(source);
        this.message = orderCancelReplaceRequest;
        this.text = text;
    }

}

