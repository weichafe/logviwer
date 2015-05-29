package com.larrainvial.sellside.listener.received;

import com.larrainvial.sellside.Repository;
import com.larrainvial.sellside.event.receievd.ReceivedNewOrderSingleEvent;
import com.larrainvial.sellside.event.send.ExecutionReportEvent;
import com.larrainvial.sellside.event.send.TradeEvent;
import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.sellside.utils.Validation;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import com.larrainvial.trading.utils.IDGenerator;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.NewOrderSingle;

import java.util.Date;

public class ReceivedNewOrderSingleListener implements Listener {

    private NewOrderSingle newOrderSingle;
    private Validation validator;

    public ReceivedNewOrderSingleListener(){
        validator = new Validation();
    }

    @Override
    public void eventOccurred(Event event) {

        try {

            ReceivedNewOrderSingleEvent ev = (ReceivedNewOrderSingleEvent) event;

            newOrderSingle = ev.newOrderSingle;

            if(!validator.validateNewOrderSingleFromBuySide(newOrderSingle)) return;

            Orders orders = new Orders();
            orders.workOrders = this.saveWorkOrder(newOrderSingle);

            Controller.dispatchEvent(new ExecutionReportEvent(this, orders, new ExecType(ExecType.NEW)));


            if (orders.workOrders.getSide().valueEquals(Side.BUY)) {
                Repository.executionWorkOrderBuy.put(orders.workOrders.getString(ClOrdID.FIELD), orders);

            } else if (orders.workOrders.getSide().valueEquals(Side.SELL) || orders.workOrders.getSide().valueEquals(Side.SELL_SHORT)) {
                Repository.executionWorkOrderSell.put(orders.workOrders.getString(ClOrdID.FIELD), orders);
            }

            Controller.dispatchEvent(new TradeEvent(this, orders));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public synchronized ExecutionReport saveWorkOrder(NewOrderSingle newOrderSingle) throws Exception {

        ExecutionReport executionReport = new ExecutionReport();

        executionReport.set(new OrdStatus(OrdStatus.NEW));
        executionReport.set(new CumQty(0d));
        executionReport.set(new LastQty(0d));
        executionReport.set(new LastPx(0d));
        executionReport.set(new AvgPx(0d));
        executionReport.set(new ExecID(IDGenerator.getID()));
        executionReport.set(new OrderID(IDGenerator.getID()));
        executionReport.set(new TransactTime(new Date()));
        executionReport.set(newOrderSingle.getOrderQty());
        executionReport.set(newOrderSingle.getClOrdID());
        executionReport.set(newOrderSingle.getSide());
        executionReport.set(newOrderSingle.getSymbol());
        executionReport.set((newOrderSingle.isSetAccount()) ? newOrderSingle.getAccount() : new Account("NONE"));
        executionReport.setField(ExDestination.FIELD, new ExDestination(newOrderSingle.getExDestination().getValue()));
        executionReport.set(newOrderSingle.getOrdType());
        executionReport.set(newOrderSingle.getHandlInst());
        executionReport.set(newOrderSingle.getTimeInForce());
        executionReport.set(newOrderSingle.getSecurityExchange());
        executionReport.set(newOrderSingle.getSettlType());
        executionReport.set(new LeavesQty(newOrderSingle.getOrderQty().getValue()));

        if(newOrderSingle.isSetMaxFloor()){
            executionReport.set(newOrderSingle.getMaxFloor());
        }

        if(newOrderSingle.isSetTargetStrategy()){
            executionReport.set(newOrderSingle.getTargetStrategy());
        }

        if (newOrderSingle.isSetClOrdLinkID()) {
            executionReport.set(newOrderSingle.getClOrdLinkID());
        }

        if (!newOrderSingle.getOrdType().valueEquals(OrdType.MARKET)) {
            executionReport.set(newOrderSingle.getPrice());
        }

        if (newOrderSingle.isSetText()) {
            executionReport.set(newOrderSingle.getText());
        }

        if(newOrderSingle.isSetEffectiveTime()){
            executionReport.set(newOrderSingle.getEffectiveTime());
        }

        if(newOrderSingle.isSetExpireTime()){
            executionReport.set(newOrderSingle.getExpireTime());
        }

        if(newOrderSingle.getHeader().isSetField(DeliverToCompID.FIELD)){
            executionReport.getHeader().setString(OnBehalfOfCompID.FIELD, newOrderSingle.getHeader().getString(DeliverToCompID.FIELD));
        }

        if(newOrderSingle.getHeader().isSetField(OnBehalfOfCompID.FIELD)){
            executionReport.getHeader().setString(DeliverToCompID.FIELD, newOrderSingle.getHeader().getString(OnBehalfOfCompID.FIELD));
        }

        if(newOrderSingle.getHeader().isSetField(SenderSubID.FIELD)){
            executionReport.getHeader().setString(TargetSubID.FIELD, newOrderSingle.getHeader().getString(SenderSubID.FIELD));
        }

        if(newOrderSingle.getHeader().isSetField(TargetSubID.FIELD)){
            executionReport.getHeader().setString(SenderSubID.FIELD, newOrderSingle.getHeader().getString(TargetSubID.FIELD));
        }


        for(int i=1; i <= newOrderSingle.getGroups(NoPartyIDs.FIELD).size(); i++){
            executionReport.addGroup(newOrderSingle.getGroup(i, NoPartyIDs.FIELD));
        }


        for(int i=1; i <= newOrderSingle.getGroups(NoStrategyParameters.FIELD).size(); i++){
            executionReport.addGroup(newOrderSingle.getGroup(i, NoStrategyParameters.FIELD));
        }


        executionReport.set(new Text("New " + (executionReport.getSide().valueEquals(Side.BUY) ? "Buy " : "Sell ") + executionReport.getSymbol().getValue() + " " + executionReport.getDouble(OrderQty.FIELD) + " @ " + (executionReport.isSetField(Price.FIELD) ? executionReport.getDouble(Price.FIELD) : "MARKET")));

        return executionReport;
    }
}
