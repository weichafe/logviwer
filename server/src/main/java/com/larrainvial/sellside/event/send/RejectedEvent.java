package com.larrainvial.sellside.event.send;

import com.larrainvial.trading.emp.Event;
import quickfix.fix44.Message;

public class RejectedEvent extends Event {

    public Message message;
    public String[] ordRejReason;

    public RejectedEvent(Object source, Message message, String[] ordRejReason) {
        super(source);
        this.message = message;
        this.ordRejReason = ordRejReason;
    }


}
