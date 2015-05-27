package com.larrainvial.sellside.event.receievd;

import com.larrainvial.trading.emp.Event;
import quickfix.fix44.OrderCancelRequest;


public class ReceivedOrderCancelRequestEvent extends Event {

    public OrderCancelRequest orderCancelRequest;

    public ReceivedOrderCancelRequestEvent(Object source, OrderCancelRequest orderCancelRequest) {
        super(source);
        this.orderCancelRequest = orderCancelRequest;
    }
}
