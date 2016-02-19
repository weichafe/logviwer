package com.larrainvial.sellside.event.send;

import com.larrainvial.trading.emp.Event;
import quickfix.field.Text;
import quickfix.fix44.Message;

public class RejectedEvent extends Event {

    public Message message;
    public Text text;

    public RejectedEvent(Object source, Message message, Text text) {
        super(source);
        this.message = message;
        this.text = text;
    }


}
