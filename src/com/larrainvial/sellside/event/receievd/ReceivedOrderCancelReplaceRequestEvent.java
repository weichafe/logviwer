package com.larrainvial.sellside.event.receievd;

import com.larrainvial.trading.emp.Event;
import quickfix.fix44.OrderCancelReplaceRequest;


public class ReceivedOrderCancelReplaceRequestEvent extends Event {

    public OrderCancelReplaceRequest orderCancelReplaceRequest;

    public ReceivedOrderCancelReplaceRequestEvent(Object source, OrderCancelReplaceRequest orderCancelReplaceRequest) {
        super(source);
        this.orderCancelReplaceRequest = orderCancelReplaceRequest;
    }
}
