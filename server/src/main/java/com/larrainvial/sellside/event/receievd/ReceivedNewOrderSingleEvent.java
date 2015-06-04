package com.larrainvial.sellside.event.receievd;

import com.larrainvial.trading.emp.Event;
import quickfix.fix44.NewOrderSingle;


public class ReceivedNewOrderSingleEvent extends Event {

    public NewOrderSingle newOrderSingle;

    public ReceivedNewOrderSingleEvent(Object source, NewOrderSingle newOrderSingle) {
        super(source);
        this.newOrderSingle = newOrderSingle;

    }

}
