package com.larrainvial.sellside.orders;

import quickfix.fix44.ExecutionReport;

import java.io.Serializable;

public class Orders {

    public ExecutionReport workOrders = new ExecutionReport();

    public Double pXq = 0d;
    public Double cumQtyLocal = 0d;


}
