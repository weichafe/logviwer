package com.larrainvial.sellside.listener.send;

import com.larrainvial.sellside.Repository;
import com.larrainvial.sellside.event.send.ExecutionReportEvent;
import com.larrainvial.sellside.event.send.TradeEvent;
import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import com.larrainvial.trading.utils.IDGenerator;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import java.util.Map;

public class TradeListener implements Listener {

    private Orders receivedOrder;
    private Orders repositoryOrders;

    @Override
    public void eventOccurred(Event event) {

        try {

            TradeEvent ev = (TradeEvent) event;

            receivedOrder = ev.orders;

            if(receivedOrder.workOrders.getOrdStatus().valueEquals(OrdStatus.FILLED)) return;


            if (receivedOrder.workOrders.getSide().valueEquals(Side.BUY)) {

                for (Map.Entry<String, Orders> e: Repository.executionWorkOrderSell.entrySet()) {

                    repositoryOrders = Repository.executionWorkOrderSell.get(e.getKey());

                    if(repositoryOrders.workOrders.getOrdStatus().valueEquals(OrdStatus.FILLED)) continue;
                    if(repositoryOrders.workOrders.getOrdStatus().valueEquals(OrdStatus.CANCELED)) continue;
                    if(repositoryOrders.workOrders.getOrdStatus().valueEquals(OrdStatus.REJECTED)) continue;

                    if (receivedOrder.workOrders.getPrice().getValue() >= repositoryOrders.workOrders.getPrice().getValue() &&
                            receivedOrder.workOrders.getSymbol().valueEquals(repositoryOrders.workOrders.getSymbol().getValue())) {

                        this.privateFillOrder(receivedOrder, repositoryOrders);
                    }

                }


            } else {

                for (Map.Entry<String, Orders> e: Repository.executionWorkOrderBuy.entrySet()) {

                    repositoryOrders = Repository.executionWorkOrderBuy.get(e.getKey());

                    if(repositoryOrders.workOrders.getOrdStatus().valueEquals(OrdStatus.FILLED)) continue;
                    if(repositoryOrders.workOrders.getOrdStatus().valueEquals(OrdStatus.CANCELED)) continue;
                    if(repositoryOrders.workOrders.getOrdStatus().valueEquals(OrdStatus.REJECTED)) continue;

                    if (receivedOrder.workOrders.getPrice().getValue() <= repositoryOrders.workOrders.getPrice().getValue() &&
                            receivedOrder.workOrders.getSymbol().valueEquals(repositoryOrders.workOrders.getSymbol().getValue())) {

                        this.privateFillOrder(receivedOrder, repositoryOrders);
                    }

                }



            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     private void privateFillOrder(Orders receivedOrder, Orders repositoryOrders) throws Exception {

         if (receivedOrder.workOrders.getOrderQty().getValue() >= repositoryOrders.workOrders.getOrderQty().getValue()){

             this.saveTrade(receivedOrder, repositoryOrders.workOrders.getPrice().getValue(), repositoryOrders.workOrders.getOrderQty().getValue());
             this.saveTrade(repositoryOrders, repositoryOrders.workOrders.getPrice().getValue(), repositoryOrders.workOrders.getOrderQty().getValue());

             Controller.dispatchEvent(new ExecutionReportEvent(this, receivedOrder, new ExecType(ExecType.TRADE)));
             Controller.dispatchEvent(new ExecutionReportEvent(this, repositoryOrders, new ExecType(ExecType.TRADE)));
         }

         else if (receivedOrder.workOrders.getOrderQty().getValue() == repositoryOrders.workOrders.getOrderQty().getValue())  {

             this.saveTrade(receivedOrder, repositoryOrders.workOrders.getPrice().getValue(), repositoryOrders.workOrders.getOrderQty().getValue());
             this.saveTrade(repositoryOrders, receivedOrder.workOrders.getPrice().getValue(), receivedOrder.workOrders.getOrderQty().getValue());

             Controller.dispatchEvent(new ExecutionReportEvent(this, receivedOrder, new ExecType(ExecType.TRADE)));
             Controller.dispatchEvent(new ExecutionReportEvent(this, repositoryOrders, new ExecType(ExecType.TRADE)));

         } else if (receivedOrder.workOrders.getOrderQty().getValue() <= repositoryOrders.workOrders.getOrderQty().getValue()) {

             this.saveTrade(repositoryOrders, receivedOrder.workOrders.getPrice().getValue(), receivedOrder.workOrders.getOrderQty().getValue());
             this.saveTrade(receivedOrder,    receivedOrder.workOrders.getPrice().getValue(), receivedOrder.workOrders.getOrderQty().getValue());

             Controller.dispatchEvent(new ExecutionReportEvent(this, receivedOrder, new ExecType(ExecType.TRADE)));
             Controller.dispatchEvent(new ExecutionReportEvent(this, repositoryOrders, new ExecType(ExecType.TRADE)));
         }


     }


    public synchronized void saveTrade(Orders orders, double lastprice, double lastQty ) throws Exception {

        ExecutionReport workOrders = orders.workOrders;

        workOrders.set(new LastPx(lastprice));
        workOrders.set(new LastQty(lastQty));

        workOrders.set(new ExecID(IDGenerator.getID()));

        orders.pXq = (workOrders.getLastPx().getValue() * workOrders.getLastQty().getValue()) + orders.pXq;
        orders.cumQtyLocal = (workOrders.getLastQty().getValue() + orders.cumQtyLocal);
        orders.workOrders.set(new LeavesQty(orders.workOrders.getOrderQty().getValue() - orders.cumQtyLocal));

        workOrders.set(new CumQty(orders.cumQtyLocal));
        workOrders.set(new AvgPx((workOrders.getCumQty().getValue() == 0d) ? 0d : orders.pXq / workOrders.getCumQty().getValue()));

        if (workOrders.getLeavesQty().getValue() == 0d) {
            workOrders.set(new OrdStatus(OrdStatus.FILLED));
        }

        if (workOrders.getLeavesQty().getValue() == workOrders.getOrderQty().getValue()) {
            workOrders.set(new OrdStatus(OrdStatus.NEW));
        }

        if (workOrders.getCumQty().getValue() > 0d && workOrders.getCumQty().getValue() < workOrders.getOrderQty().getValue()) {
            workOrders.set(new OrdStatus(OrdStatus.PARTIALLY_FILLED));
        }

    }


}
