package com.larrainvial.sellside.utils;


import com.larrainvial.logviwer.Repository;
import com.larrainvial.sellside.event.send.TradeEvent;
import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.trading.emp.Controller;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;

import java.util.Date;

public class CreateOrden {

    public CreateOrden (String txtSide , String qty, String price, String symbol) throws Exception {


        Orders orders = new Orders();
        orders.workOrders = this.saveWorkOrder(txtSide, qty, price, symbol);

        if (orders.workOrders.getSide().valueEquals(Side.BUY)) {
            Repository.executionWorkOrderBuy.put(orders.workOrders.getString(ClOrdID.FIELD), orders);

        } else if (orders.workOrders.getSide().valueEquals(Side.SELL) || orders.workOrders.getSide().valueEquals(Side.SELL_SHORT)) {
            Repository.executionWorkOrderSell.put(orders.workOrders.getString(ClOrdID.FIELD), orders);
        }

        Controller.dispatchEvent(new TradeEvent(this, orders));

    }



    public synchronized ExecutionReport saveWorkOrder(String txtSide , String qty, String price, String symbol) throws Exception {

        ExecutionReport executionReport = new ExecutionReport();

        executionReport.set(new OrdStatus(OrdStatus.NEW));
        executionReport.set(new CumQty(0d));
        executionReport.set(new LastQty(0d));
        executionReport.set(new LastPx(0d));
        executionReport.set(new AvgPx(0d));
        executionReport.set(new ExecID(Repository.getID()));
        executionReport.set(new OrderID(Repository.getID()));
        executionReport.set(new TransactTime(new Date()));
        executionReport.set(new OrderQty(Double.valueOf(qty)));
        executionReport.set(new ClOrdID(Repository.getID()));
        executionReport.set(new Side(txtSide.charAt(0)));
        executionReport.set(new Symbol(symbol));


        executionReport.set(new Account("INPUT"));
        executionReport.setField(ExDestination.FIELD, new ExDestination("XSGO"));

        executionReport.set(new OrdType(OrdType.LIMIT));
        executionReport.set(new HandlInst(HandlInst.AUTOMATED_EXECUTION_ORDER_PRIVATE));
        executionReport.set(new TimeInForce(TimeInForce.DAY));
        executionReport.set(new SecurityExchange("XSGO"));
        executionReport.set(new SettlType(SettlType.T_PLUS_3));
        executionReport.set(new LeavesQty(Double.valueOf(qty)));
        executionReport.set(new Price(Double.valueOf(price)));

        return executionReport;
    }

}
