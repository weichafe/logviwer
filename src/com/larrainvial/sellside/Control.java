package com.larrainvial.sellside;

import com.larrainvial.sellside.event.receievd.ReceivedNewOrderSingleEvent;
import com.larrainvial.sellside.event.receievd.ReceivedOrderCancelReplaceRequestEvent;
import com.larrainvial.sellside.event.receievd.ReceivedOrderCancelRequestEvent;
import com.larrainvial.sellside.event.send.ExecutionReportEvent;
import com.larrainvial.sellside.event.send.OrderCancelRejectEvent;
import com.larrainvial.sellside.event.send.TradeEvent;
import com.larrainvial.sellside.listener.received.ReceivedNewOrderSingleListener;
import com.larrainvial.sellside.listener.received.ReceivedOrderCancelReplaceRequestListener;
import com.larrainvial.sellside.listener.received.ReceivedOrderCancelRequestListener;
import com.larrainvial.sellside.listener.send.ExecutionReportListener;
import com.larrainvial.sellside.listener.send.OrderCancelRejectListener;
import com.larrainvial.sellside.listener.send.TradeListener;
import com.larrainvial.trading.emp.Controller;

public class Control {

    public static void initialize(){

        Controller.addEventListener(ReceivedNewOrderSingleEvent.class, new ReceivedNewOrderSingleListener());
        Controller.addEventListener(ReceivedOrderCancelReplaceRequestEvent.class, new ReceivedOrderCancelReplaceRequestListener());
        Controller.addEventListener(ReceivedOrderCancelRequestEvent.class, new ReceivedOrderCancelRequestListener());

        Controller.addEventListener(ExecutionReportEvent.class, new ExecutionReportListener());
        Controller.addEventListener(OrderCancelRejectEvent.class, new OrderCancelRejectListener());
        Controller.addEventListener(TradeEvent.class, new TradeListener());

    }
}
