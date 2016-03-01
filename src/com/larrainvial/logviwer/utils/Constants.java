package com.larrainvial.logviwer.utils;


public class Constants {

    public static final String SELL_SIDE = "Sell Side";
    public static final String REJECTED = "Rejected";
    public static final String TRADE = "Trade";
    public static final String LOCAL = "LOCAL";
    public static final String ADR = "ADR";
    public static final String EMPTY = "";
    public static final String BUY = "Buy";
    public static final String SELL = "Sell";
    public static final String SELL_SHORT = "Sell Short";
    public static final String RECEIVED = "Received";
    public static final String SEND = "Send";
    public static final String ORDERS_DELETED = "Orders Deleted";
    public static final Double DIFFERENCE_QTY = 1200d;
    public static final Double CERO = 0d;
    public static final String INFO = "info: para forzar un rechazo  35 = G 44=100.000 y 35 = F 38=100.000 ";
    public static final String NONE = "NONE";
    public static final String LN = "\n";
    public static final String LN2 = "\n\n";
    public static final String TAB = "\t";

    public class Rejected {

        public static final String XLIM = "XLIM";
        public static final String XPUS = "XPUS";
        public static final String IB = "IB";

    }


    public class Brokers {

        public static final String XLIM = "XLIM";
        public static final String XPUS = "XPUS";
        public static final String IB = "IB";

    }

    public class MarketMakerBCS {
        public static final double PXQminBcs = 13000000d;
        public static final String NAME = "Market Maker BCS";
    }

    public class TypeMarket {

        public static final String DOLAR = "DOLAR";
        public static final String ROUTING_LOCAL = "ROUTING LOCAL";
        public static final String MKD_ADR = "MKD ADR";
        public static final String MKD_LOCAL = "MKD LOCAL";
        public static final String ROUTING_ADR = "ROUTING ADR";

    }

    public class LastPrice {

        public static final String CLOSE_CERO_DOLAR = "CLOSE DOLAR CERO";
        public static final String BUY_CERO_DOLAR = "BUY DOLAR CERO";
        public static final String SELL_CERO_DOLAR = "SELL DOLAR CERO";
        public static final String DOLAR_PX = "DOLAR PX : ";
        public static final String VARIACION = "VARIACION DEL DOLAR";
    }


    public class NameAlgo {
        public static final String MarketMakerBCS = "Market Maker BCS";

    }


}
