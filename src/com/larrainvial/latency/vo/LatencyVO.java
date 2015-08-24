package com.larrainvial.latency.vo;

import com.larrainvial.latency.utils.Helper;

import java.io.Serializable;

public class LatencyVO implements Serializable{

    public String engine;
    public String nameFile;
    public long transactionTimeStart;
    public long transactionTimeEnd;
    public long timeLatency;
    public String clOrdID;
    public String msgType;
    public String symbol;
    public String side;
    public String transactionTimeStartStringFormat;
    public String transactionTimeEndStringFormat;


    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getEngine() {
        return engine;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setTransactionTimeStart(long transactionTimeStart) {
        this.transactionTimeStart = transactionTimeStart;
    }

    public long getTransactionTimeStart() {
        return transactionTimeStart;
    }

    public void setTransactionTimeEnd(long transactionTimeEnd) {
        this.transactionTimeEnd = transactionTimeEnd;
    }

    public long getTransactionTimeEnd() {
        return transactionTimeEnd;
    }

    public void setTimeLatency(long timeLatency) {
        this.timeLatency = timeLatency;
    }

    public long getTimeLatency() {
        return timeLatency;
    }

    public void setClOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    public String getClOrdID() {
        return clOrdID;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSide() {
        return side;
    }

    public void setTransactionTimeStartStringFormat(String transactionTimeStartStringFormat) {
        this.transactionTimeStartStringFormat = transactionTimeStartStringFormat;
    }

    public String getTransactionTimeStartStringFormat() {
        return Helper.getTimestampStringFormat(this.transactionTimeStart);
    }


    public void setTransactionTimeEndStringFormat(String transactionTimeEndStringFormat) {
        this.transactionTimeEndStringFormat = transactionTimeEndStringFormat;
    }

    public String getTransactionTimeEndStringFormat() {
        return Helper.getTimestampStringFormat(this.transactionTimeEnd);
    }

}

