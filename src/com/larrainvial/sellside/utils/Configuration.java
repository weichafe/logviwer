package com.larrainvial.sellside.utils;

import com.larrainvial.sellside.Repository;
import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.trading.utils.IDGenerator;
import quickfix.StringField;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.Message;

public class Configuration {

    public static class Buyside {

        public static final String SenderCompID = Repository.buySide.getPropertiesString("Buyside.SenderCompID");
        public static final String TargetCompID = Repository.buySide.getPropertiesString("Buyside.TargetCompID");
    }

    public synchronized static void sendXpusBroker(Message menssage, ExecutionReport workOrders) throws Exception {

        menssage.getHeader().setString(TargetSubID.FIELD, Repository.XPUS);
        menssage.setDouble(MaxFloor.FIELD, 100d);

        if (menssage.getString(Side.FIELD).equals("5")) {
            menssage.setField(new StringField(5700, "XPUS"));
            menssage.setBoolean(114, false);
        }

        if (Repository.UUID.containsKey(workOrders.getHeader().getString(TargetSubID.FIELD))){
            menssage.getHeader().setString(SenderSubID.FIELD, Repository.UUID.get(workOrders.getHeader().getString(TargetSubID.FIELD)));

        } else {
            menssage.getHeader().setString(SenderSubID.FIELD, workOrders.getHeader().getString(TargetSubID.FIELD));
        }

    }


    public synchronized static void sendFCSTONEBroker(Message menssage, ExecutionReport workOrders) throws Exception {

        menssage.getHeader().setString(SenderSubID.FIELD, menssage.getString(Account.FIELD));
        menssage.getHeader().setString(TargetSubID.FIELD, "FCST");
        menssage.setDouble(MaxFloor.FIELD, 100d);

        if (workOrders.getSecurityExchange().valueEquals("US")) {
            menssage.setField(new StringField(6201, "CUSTOM1"));
            menssage.getHeader().setString(DeliverToCompID.FIELD, "FCHB");
            menssage.setString(SecurityExchange.FIELD, "US");
            menssage.setField(new StringField(ExDestination.FIELD, "US"));

        } else {
            menssage.setString(SecurityExchange.FIELD, "CN");
            menssage.setField(new StringField(ExDestination.FIELD, "CN"));
        }

    }

    public synchronized static void sendIBroker(Message menssage, ExecutionReport workOrders) throws Exception {

        menssage.setField(new StringField(Currency.FIELD, "USD"));
        menssage.setDouble(MaxFloor.FIELD, 100d);
    }




}
