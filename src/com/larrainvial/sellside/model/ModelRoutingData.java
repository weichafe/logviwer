package com.larrainvial.sellside.model;

import com.larrainvial.logviwer.model.ModelPositions;

import java.io.Serializable;

public class ModelRoutingData {

    public String hour;
    public String year;
    public String messageByType;
    public String symbol;

    public String orderID;
    public String clOrdID;
    public String origClOrdID;
    public String clOrdLinkID;
    public String execID;
    public String execType;
    public String ordStatus;
    public String account;
    public String side;

    public String effectiveTime;
    public String text;
    public String expireTime;
    public String exDestination;
    public String securityExchange;

    public Double price;
    public Double orderQty;
    public Double lastQty;
    public Double lastPx;
    public Double cumQty;
    public Double avgPx;
    public Double leavesQty;
    public Double maxFloor;

}