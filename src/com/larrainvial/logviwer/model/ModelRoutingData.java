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
    public final StringProperty clOrdID;
    public final StringProperty origClOrdID;
    public final StringProperty clOrdLinkID;
    public final StringProperty execID;
    public final StringProperty execType;
    public final StringProperty ordStatus;
    public final StringProperty account;
    public final StringProperty side;

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



    public ModelRoutingData(String year, String hour, String messageByType, String execType, String symbol, String orderID, String clOrdID, String origClOrdID,
                            String clOrdLinkID, String execID, String ordStatus, String account, String side,
                            String exDestination, String securityExchange, String effectiveTime, String expireTime, Double lastQty,
                            Double lastPx, Double cumQty, Double avgPx, Double leavesQty, Double maxFloor, Double price) {

        this.year = new SimpleStringProperty(year);
        this.messageByType = new SimpleStringProperty(messageByType);
        this.symbol = new SimpleStringProperty(symbol);
        this.hour = new SimpleStringProperty(hour);
        this.orderID = new SimpleStringProperty(orderID);
        this.clOrdID = new SimpleStringProperty(clOrdID);
        this.origClOrdID = new SimpleStringProperty(origClOrdID);
        this.clOrdLinkID = new SimpleStringProperty(clOrdLinkID);
        this.execID = new SimpleStringProperty(execID);
        this.execType = new SimpleStringProperty(execType);
        this.ordStatus = new SimpleStringProperty(ordStatus);
        this.account = new SimpleStringProperty(account);
        this.side = new SimpleStringProperty(side);
        this.effectiveTime = new SimpleStringProperty(effectiveTime);
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


    public StringProperty getHour() {
        return hour;
    }

    public StringProperty hourProperty() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour.set(hour);
    }

    public StringProperty getYear() {
        return year;
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public StringProperty getMessageByType() {
        return messageByType;
    }

    public StringProperty messageByTypeProperty() {
        return messageByType;
    }

    public void setMessageByType(String messageByType) {
        this.messageByType.set(messageByType);
    }

    public StringProperty getSymbol() {
        return symbol;
    }

    public StringProperty symbolProperty() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    public StringProperty getOrderID() {
        return orderID;
    }

    public StringProperty orderIDProperty() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID.set(orderID);
    }

    public StringProperty getClOrdID() {
        return clOrdID;
    }

    public StringProperty clOrdIDProperty() {
        return clOrdID;
    }

    public void setClOrdID(String clOrdID) {
        this.clOrdID.set(clOrdID);
    }

    public StringProperty getOrigClOrdID() {
        return origClOrdID;
    }

    public StringProperty origClOrdIDProperty() {
        return origClOrdID;
    }

    public void setOrigClOrdID(String origClOrdID) {
        this.origClOrdID.set(origClOrdID);
    }

    public StringProperty getClOrdLinkID() {
        return clOrdLinkID;
    }

    public StringProperty clOrdLinkIDProperty() {
        return clOrdLinkID;
    }

    public void setClOrdLinkID(String clOrdLinkID) {
        this.clOrdLinkID.set(clOrdLinkID);
    }

    public StringProperty getExecID() {
        return execID;
    }

    public StringProperty execIDProperty() {
        return execID;
    }

    public void setExecID(String execID) {
        this.execID.set(execID);
    }

    public StringProperty getExecType() {
        return execType;
    }

    public StringProperty execTypeProperty() {
        return execType;
    }

    public void setExecType(String execType) {
        this.execType.set(execType);
    }

    public StringProperty getOrdStatus() {
        return ordStatus;
    }

    public StringProperty ordStatusProperty() {
        return ordStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus.set(ordStatus);
    }

    public StringProperty getAccount() {
        return account;
    }

    public StringProperty accountProperty() {
        return account;
    }

    public void setAccount(String account) {
        this.account.set(account);
    }

    public StringProperty getSide() {
        return side;
    }

    public StringProperty sideProperty() {
        return side;
    }

    public void setSide(String side) {
        this.side.set(side);
    }

    public StringProperty getEffectiveTime() {
        return effectiveTime;
    }

    public StringProperty effectiveTimeProperty() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime.set(effectiveTime);
    }

    public StringProperty getExpireTime() {
        return expireTime;
    }

    public StringProperty expireTimeProperty() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime.set(expireTime);
    }

    public StringProperty getExDestination() {
        return exDestination;
    }

    public StringProperty exDestinationProperty() {
        return exDestination;
    }

    public void setExDestination(String exDestination) {
        this.exDestination.set(exDestination);
    }

    public StringProperty getSecurityExchange() {
        return securityExchange;
    }

    public StringProperty securityExchangeProperty() {
        return securityExchange;
    }

    public void setSecurityExchange(String securityExchange) {
        this.securityExchange.set(securityExchange);
    }

    public DoubleProperty getPrice() {
        return price;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty getLastQty() {
        return lastQty;
    }

    public DoubleProperty lastQtyProperty() {
        return lastQty;
    }

    public void setLastQty(double lastQty) {
        this.lastQty.set(lastQty);
    }

    public DoubleProperty getLastPx() {
        return lastPx;
    }

    public DoubleProperty lastPxProperty() {
        return lastPx;
    }

    public void setLastPx(double lastPx) {
        this.lastPx.set(lastPx);
    }

    public DoubleProperty getCumQty() {
        return cumQty;
    }

    public DoubleProperty cumQtyProperty() {
        return cumQty;
    }

    public void setCumQty(double cumQty) {
        this.cumQty.set(cumQty);
    }

    public DoubleProperty getAvgPx() {
        return avgPx;
    }

    public DoubleProperty avgPxProperty() {
        return avgPx;
    }

    public void setAvgPx(double avgPx) {
        this.avgPx.set(avgPx);
    }

    public DoubleProperty getLeavesQty() {
        return leavesQty;
    }

    public DoubleProperty leavesQtyProperty() {
        return leavesQty;
    }

    public void setLeavesQty(double leavesQty) {
        this.leavesQty.set(leavesQty);
    }

    public DoubleProperty getMaxFloor() {
        return maxFloor;
    }

    public DoubleProperty maxFloorProperty() {
        return maxFloor;
    }

    public void setMaxFloor(double maxFloor) {
        this.maxFloor.set(maxFloor);
    }
}