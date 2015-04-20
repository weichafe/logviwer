package com.larrainvial.logviwer.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ModelRoutingData {

    public final StringProperty hour;
    public final StringProperty year;
    public final StringProperty messageByType;
    public final StringProperty symbol;

    public final StringProperty orderID;
    public final StringProperty secondaryClOrdID;
    public final StringProperty clOrdID;
    public final StringProperty origClOrdID;
    public final StringProperty clOrdLinkID;
    public final StringProperty execID;
    public final StringProperty execRefID;
    public final StringProperty execType;
    public final StringProperty ordStatus;
    public final StringProperty account;
    public final StringProperty side;

    public final StringProperty currency;
    public final StringProperty effectiveTime;
    public final StringProperty expireTime;
    public final StringProperty exDestination;
    public final StringProperty securityExchange;

    public final DoubleProperty price;
    public final DoubleProperty lastQty;
    public final DoubleProperty lastPx;
    public final DoubleProperty cumQty;
    public final DoubleProperty avgPx;
    public final DoubleProperty leavesQty;
    public final DoubleProperty maxFloor;



    public ModelRoutingData(String hour, String year, String messageByType, String orderID, String secondaryClOrdID, String clOrdID, String origClOrdID,
                            String clOrdLinkID, String execID, String execRefID, String execType, String ordStatus, String account, String side,
                            Double price, String currency, String effectiveTime, String expireTime, Double lastQty,
                            Double lastPx, Double cumQty, Double avgPx, Double leavesQty, Double maxFloor, String symbol,
                            String exDestination, String securityExchange) {

        this.year = new SimpleStringProperty(year);
        this.messageByType = new SimpleStringProperty(messageByType);
        this.symbol = new SimpleStringProperty(symbol);
        this.hour = new SimpleStringProperty(hour);
        this.orderID = new SimpleStringProperty(orderID);
        this.secondaryClOrdID = new SimpleStringProperty(secondaryClOrdID);
        this.clOrdID = new SimpleStringProperty(clOrdID);
        this.origClOrdID = new SimpleStringProperty(origClOrdID);
        this.clOrdLinkID = new SimpleStringProperty(clOrdLinkID);
        this.execID = new SimpleStringProperty(execID);
        this.execRefID = new SimpleStringProperty(execRefID);
        this.execType = new SimpleStringProperty(execType);
        this.ordStatus = new SimpleStringProperty(ordStatus);
        this.account = new SimpleStringProperty(account);
        this.side = new SimpleStringProperty(side);
        this.effectiveTime = new SimpleStringProperty(effectiveTime);
        this.currency = new SimpleStringProperty(currency);
        this.expireTime = new SimpleStringProperty(expireTime);
        this.exDestination = new SimpleStringProperty(exDestination);
        this.securityExchange = new SimpleStringProperty(securityExchange);
        this.price = new SimpleDoubleProperty(price);
        this.lastQty = new SimpleDoubleProperty(lastQty);
        this.lastPx = new SimpleDoubleProperty(lastPx);
        this.cumQty = new SimpleDoubleProperty(cumQty);
        this.avgPx = new SimpleDoubleProperty(avgPx);
        this.leavesQty = new SimpleDoubleProperty(leavesQty);
        this.maxFloor = new SimpleDoubleProperty(maxFloor);
    }
}
