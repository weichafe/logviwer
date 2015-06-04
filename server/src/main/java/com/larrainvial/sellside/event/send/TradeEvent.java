package com.larrainvial.sellside.event.send;


import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.trading.emp.Event;

public class TradeEvent extends Event {

    public Orders orders;

    public TradeEvent(Object source, Orders orders) {
        super(source);
        this.orders = orders;

    }
}
