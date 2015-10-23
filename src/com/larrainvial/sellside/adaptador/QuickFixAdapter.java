package com.larrainvial.sellside.adaptador;

import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.sellside.Repository;
import com.larrainvial.sellside.event.receievd.ReceivedNewOrderSingleEvent;
import com.larrainvial.sellside.event.receievd.ReceivedOrderCancelReplaceRequestEvent;
import com.larrainvial.sellside.event.receievd.ReceivedOrderCancelRequestEvent;
import com.larrainvial.trading.emp.Controller;
import org.apache.log4j.Logger;
import quickfix.*;
import quickfix.Message;
import quickfix.fix44.*;
import quickfix.fix44.MessageCracker;

import java.net.URL;
import java.util.logging.Level;

public final class QuickFixAdapter extends MessageCracker implements Application {

    public SocketAcceptor socketAcceptor;
    private SessionSettings sessionSettings;
    private static Logger logger = Logger.getLogger(QuickFixAdapter.class.getName());

    public QuickFixAdapter(String quickFixIniFile) {

        try {

            sessionSettings = new SessionSettings(quickFixIniFile);
            FileStoreFactory fileStoreFactory = new FileStoreFactory(sessionSettings);
            com.larrainvial.trading.utils.quickfix.FileLogFactory fileLogFactory = new com.larrainvial.trading.utils.quickfix.FileLogFactory(sessionSettings);

            DefaultMessageFactory defaultMessageFactory = new DefaultMessageFactory();
            socketAcceptor = new SocketAcceptor(this, fileStoreFactory, sessionSettings, fileLogFactory, defaultMessageFactory);
            Repository.socketAcceptor = this.socketAcceptor;
            Repository.socketAcceptor.start();

            Repository.socketAcceptPort = sessionSettings.getString(Repository.sessionID, "SocketAcceptPort");
            Repository.senderCompID = sessionSettings.getString(Repository.sessionID, "SenderCompID");
            Repository.targetCompID = sessionSettings.getString(Repository.sessionID, "TargetCompID");

            Notifier.INSTANCE.notifySuccess("Success", "Sell Side");

        } catch (Exception e) {
            logger.error(Level.SEVERE, e);
            e.printStackTrace();
        }
    }

    public void onCreate(SessionID sessionID) {

        try {

            Repository.sessionID = sessionID;
            Repository.socketAcceptPort = sessionSettings.getString(Repository.sessionID, "SocketAcceptPort");
            Repository.senderCompID = sessionSettings.getString(Repository.sessionID, "SenderCompID");
            Repository.targetCompID = sessionSettings.getString(Repository.sessionID, "TargetCompID");

        } catch (Exception e) {
            logger.error(e);
        }
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
            logger.error(unsupportedMessageType);
        }
    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        // nothing
    }

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {

        try {
            crack(message, sessionID);
        } catch (UnsupportedMessageType unsupportedMessageType) {
            logger.error(unsupportedMessageType);
        }
    }

    public void onMessage(ExecutionReport executionReport, SessionID sessionID) {

    }

    public void onMessage(OrderCancelReject orderCancelReject, SessionID sessionID) {

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

    public static void strop() {

        try {

            Repository.socketAcceptor.stop();

        } catch (Exception e){
            logger.error(Level.SEVERE, e);
            e.printStackTrace();
        }
    }

    public static void start() {

        try {

            Repository.socketAcceptor.start();

        } catch (Exception e){
            logger.error(Level.SEVERE, e);
            e.printStackTrace();
        }
    }


}

