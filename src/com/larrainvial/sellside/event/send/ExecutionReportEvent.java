package com.larrainvial.sellside.event.send;


import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.trading.emp.Event;
import quickfix.field.ExecType;
import quickfix.fix44.ExecutionReport;

public class ExecutionReportEvent extends Event {

    public ExecutionReport workOrdersToBuySide;

    public ExecutionReportEvent(Object source, Orders orders, ExecType execType) {

        super(source);
        workOrdersToBuySide = (ExecutionReport) orders.workOrders.clone();
        workOrdersToBuySide.set(execType);
    }
}
