package com.larrainvial.sellside.listener.send;

import com.larrainvial.sellside.event.send.RejectedEvent;
import com.larrainvial.sellside.utils.Configuration;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import com.larrainvial.trading.utils.IDGenerator;
import org.apache.log4j.Logger;
import quickfix.StringField;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import java.util.Date;
import java.util.logging.Level;

public class RejectedListener implements Listener {

    private ExecutionReport rejected;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public RejectedListener(){

        rejected = new ExecutionReport();
        rejected.set(new OrderID(IDGenerator.getID()));
        rejected.set(new ExecID(IDGenerator.getID()));
        rejected.set(new ExecType(ExecType.REJECTED));
        rejected.set(new OrdStatus(OrdStatus.REJECTED));
        rejected.set(new LastPx(0d));
        rejected.set(new LastQty(0d));
        rejected.set(new CumQty(0d));
        rejected.set(new AvgPx(0d));
        rejected.set(new LeavesQty(0d));
    }

    @Override
    public void eventOccurred(Event event) {

        try {

            RejectedEvent ev = (RejectedEvent) event;

            rejected.set(new OrdRejReason(Integer.valueOf(ev.ordRejReason[0])));

            rejected.set(new TransactTime(new Date()));

            String required = "";

            if (ev.message.isSetField(ClOrdID.FIELD)){
                rejected.set(new ClOrdID(ev.message.getString(ClOrdID.FIELD)));

            } else{
                required = " required tag missing: 11=?";
            }

            if (ev.message.isSetField(Symbol.FIELD)){
                rejected.set(new Symbol(ev.message.getString(Symbol.FIELD)));

            }else{
                required = " required tag missing: 55=?";
            }

            if (ev.message.isSetField(Side.FIELD)){
                rejected.set(new Side(ev.message.getChar(Side.FIELD)));

            } else{
                required = " required tag missing: 54=?";
            }

            if (ev.message.isSetField(OrdType.FIELD)){
                rejected.set(new OrdType(ev.message.getChar(OrdType.FIELD)));

            } else{
                required = " required tag missing: 40=?";
            }

            if (ev.message.isSetField(OrderQty.FIELD)){
                rejected.set(new OrderQty(ev.message.getDouble(OrderQty.FIELD)));

            } else{
                required = " required tag missing: 38=?";
            }

            if (ev.message.isSetField(Price.FIELD)){
                rejected.set(new Price(ev.message.getDouble(Price.FIELD)));

            } else{
                required = " required tag missing: 44=?";
            }

            if (ev.message.isSetField(TimeInForce.FIELD)){
                rejected.set(new TimeInForce(ev.message.getChar(TimeInForce.FIELD)));

            } else{
                required = " required tag missing: 59=?";
            }

            if (ev.message.isSetField(ExDestination.FIELD)){
                rejected.setField(new StringField(ExDestination.FIELD, ev.message.getString(ExDestination.FIELD)));

            } else{
                required = " required tag missing: 100=?";
            }

            if (ev.message.isSetField(HandlInst.FIELD)){
                rejected.set(new HandlInst(ev.message.getChar(HandlInst.FIELD)));

            } else{
                required = " required tag missing: 21=?";
            }

            if (ev.message.isSetField(SettlType.FIELD)){
                rejected.set(new SettlType(ev.message.getString(SettlType.FIELD)));

            } else{
                required = " required tag missing: 63=?";
            }

            if (ev.message.isSetField(ClOrdLinkID.FIELD)){
                rejected.set(new ClOrdLinkID(ev.message.getString(ClOrdLinkID.FIELD)));
            }

            rejected.set(new Text(" LogViwer SellSide: " + ev.ordRejReason[1] + required));

            quickfix.Session.sendToTarget(rejected, Configuration.Buyside.SenderCompID, Configuration.Buyside.TargetCompID);

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }

    }

}
