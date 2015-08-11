package com.larrainvial.sellside.adaptador;

import com.larrainvial.sellside.Repository;
import com.larrainvial.sellside.event.receievd.ReceivedNewOrderSingleEvent;
import com.larrainvial.sellside.event.receievd.ReceivedOrderCancelReplaceRequestEvent;
import com.larrainvial.sellside.event.receievd.ReceivedOrderCancelRequestEvent;
import com.larrainvial.trading.emp.Controller;
import quickfix.*;
import quickfix.Message;
import quickfix.fix44.*;
import quickfix.fix44.MessageCracker;

import java.net.URL;

public final class QuickFixAdapter extends MessageCracker implements Application {

    public SocketAcceptor socketAcceptor;

    public QuickFixAdapter(URL quickFixIniFile) {

        try {

            SessionSettings sessionSettings = new SessionSettings(quickFixIniFile.openStream());
            FileStoreFactory fileStoreFactory = new FileStoreFactory(sessionSettings);
            FileLogFactory fileLogFactory = new FileLogFactory(sessionSettings);
            DefaultMessageFactory defaultMessageFactory = new DefaultMessageFactory();
            this.socketAcceptor = new SocketAcceptor(this, fileStoreFactory, sessionSettings, fileLogFactory, defaultMessageFactory);
            Repository.socketAcceptor = this.socketAcceptor;
            Repository.socketAcceptor.start();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void onCreate(SessionID sessionID) {
        Repository.sessionID = sessionID;
    }

    public void onLogon(SessionID sessionID) {
        // nothing
    }

    public void onLogout(SessionID sessionID) {
        // nothing
    }

    public void toAdmin(Message message, SessionID sessionID) {
        // nothing
    }

    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {

        try {
            crack(message, sessionID);
        } catch (UnsupportedMessageType unsupportedMessageType) {
            unsupportedMessageType.printStackTrace();
        }
    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        // nothing
    }

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {

        try {
            crack(message, sessionID);
        } catch (UnsupportedMessageType unsupportedMessageType) {
            unsupportedMessageType.printStackTrace();
        }
    }

    public void onMessage(ExecutionReport executionReport, SessionID sessionID) {

       //Controller.dispatchEvent(new ReceivedExecutionReportFromSellSideEvent(this, executionReport));

    }

    public void onMessage(OrderCancelReject orderCancelReject, SessionID sessionID) {

        //Controller.dispatchEvent(new ReceivedOrderCancelRejectBlotterEvent(this, orderCancelReject));

    }

    public void onMessage(NewOrderSingle newOrderSingle, SessionID sessionID) throws FieldNotFound {

        Controller.dispatchEvent(new ReceivedNewOrderSingleEvent(this, newOrderSingle));

    }

    public void onMessage(OrderCancelReplaceRequest orderCancelReplaceRequest, SessionID sessionID) throws FieldNotFound {

        Controller.dispatchEvent(new ReceivedOrderCancelReplaceRequestEvent(this, orderCancelReplaceRequest));

    }

    public void onMessage(OrderCancelRequest orderCancelRequest, SessionID sessionID) throws FieldNotFound {

        Controller.dispatchEvent(new ReceivedOrderCancelRequestEvent(this, orderCancelRequest));

    }

    public static void strop() throws ConfigError {
        Repository.socketAcceptor.stop();
    }

    public static void start() throws ConfigError {
        Repository.socketAcceptor.stop();
    }


}

