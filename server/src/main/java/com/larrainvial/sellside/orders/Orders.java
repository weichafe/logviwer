package com.larrainvial.sellside.orders;

import quickfix.fix44.ExecutionReport;

import java.io.Serializable;

public class Orders implements Serializable {

    private static final long serialVersionUID = 666L;

    public ExecutionReport workOrders = new ExecutionReport();

    public Double pXq = 0d;
    public Double cumQtyLocal = 0d;


}
