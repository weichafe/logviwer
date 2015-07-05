package com.larrainvial.logviwer.model;


public class ModelPositions {

    public String symbolLocal;
    public String symbolAdr;
    public Double qtyBuyLocal = 0d;
    public Double qtyBuyAdr = 0d;
    public Double qtySellLocal = 0d;
    public Double qtySellAdr = 0d;
    public Double qtySellLocalRatio = 0d;
    public Double qtyBuyLocalRatio = 0d;
    public Double ratio = 1d;
    public int positions = 1;

    public String getSymbolLocal() {
        return symbolLocal;
    }

    public void setSymbolLocal(String symbolLocal) {
        this.symbolLocal = symbolLocal;
    }

    public String getSymbolAdr() {
        return symbolAdr;
    }

    public void setSymbolAdr(String symbolAdr) {
        this.symbolAdr = symbolAdr;
    }

    public Double getQtyBuyLocal() {
        return qtyBuyLocal;
    }

    public void setQtyBuyLocal(Double qtyBuyLocal) {
        this.qtyBuyLocal = qtyBuyLocal;
    }

    public Double getQtyBuyAdr() {
        return qtyBuyAdr;
    }

    public void setQtyBuyAdr(Double qtyBuyAdr) {
        this.qtyBuyAdr = qtyBuyAdr;
    }

    public Double getQtySellLocal() {
        return qtySellLocal;
    }

    public void setQtySellLocal(Double qtySellLocal) {
        this.qtySellLocal = qtySellLocal;
    }

    public Double getQtySellAdr() {
        return qtySellAdr;
    }

    public void setQtySellAdr(Double qtySellAdr) {
        this.qtySellAdr = qtySellAdr;
    }

    public Double getQtySellLocalRatio() {
        return qtySellLocalRatio;
    }

    public void setQtySellLocalRatio(Double qtySellLocalRatio) {
        this.qtySellLocalRatio = qtySellLocalRatio;
    }

    public Double getQtyBuyLocalRatio() {
        return qtyBuyLocalRatio;
    }

    public void setQtyBuyLocalRatio(Double qtyBuyLocalRatio) {
        this.qtyBuyLocalRatio = qtyBuyLocalRatio;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
}
