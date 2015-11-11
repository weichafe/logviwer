package com.larrainvial.sellside.listener.send;


import com.larrainvial.sellside.event.send.ExecutionReportEvent;
import com.larrainvial.sellside.utils.Configuration;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.Session;

public class ExecutionReportListener implements Listener {

    @Override
    public void eventOccurred(Event event) {

        try {

            ExecutionReportEvent ev = (ExecutionReportEvent)event;

            Session.sendToTarget(ev.workOrdersToBuySide, Configuration.Buyside.SenderCompID, Configuration.Buyside.TargetCompID);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
