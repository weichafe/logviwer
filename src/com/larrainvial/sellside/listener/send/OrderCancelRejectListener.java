package com.larrainvial.sellside.listener.send;

import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.sellside.event.send.OrderCancelRejectEvent;
import com.larrainvial.sellside.utils.Configuration;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import com.larrainvial.trading.utils.IDGenerator;
import quickfix.field.*;
import quickfix.fix44.OrderCancelReject;

import java.util.Date;

public class OrderCancelRejectListener implements Listener {

    @Override
    public void eventOccurred(Event event) {

        try {

            OrderCancelRejectEvent ev = (OrderCancelRejectEvent) event;

            OrderCancelReject orderCancelReject = new OrderCancelReject();
            orderCancelReject.set(new ClOrdID(ev.message.getString(ClOrdID.FIELD)));
            orderCancelReject.set(new OrigClOrdID(ev.message.getString(OrigClOrdID.FIELD)));

            orderCancelReject.set(ev.message.isSetField(OrderID.FIELD) ? new OrderID(ev.message.getString(OrderID.FIELD)) : new OrderID(IDGenerator.getID()));



            orderCancelReject.set(new OrdStatus(OrdStatus.REJECTED));
            orderCancelReject.set(new CxlRejReason(0));
            orderCancelReject.set(ev.text);
            orderCancelReject.set(new CxlRejResponseTo('1'));
            orderCancelReject.set(new TransactTime(new Date()));

            orderCancelReject.set(ev.message.isSetField(Account.FIELD) ?
                    new Account(ev.message.getString(Account.FIELD)) : new Account(Constants.NONE));

            if (ev.message.isSetField(SecondaryClOrdID.FIELD)){
                orderCancelReject.set(new SecondaryClOrdID(ev.message.getString(SecondaryClOrdID.FIELD)));
            }

            if (ev.message.isSetField(ClOrdLinkID.FIELD)) {
                orderCancelReject.set(new ClOrdLinkID(ev.message.getString(ClOrdLinkID.FIELD)));
            }

            quickfix.Session.sendToTarget(orderCancelReject, Configuration.Buyside.SenderCompID, Configuration.Buyside.TargetCompID);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
