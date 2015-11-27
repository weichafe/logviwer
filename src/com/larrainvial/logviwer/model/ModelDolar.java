package com.larrainvial.logviwer.model;


import com.larrainvial.logviwer.Repository;

public class ModelDolar {

    public static String CLP;
    public static String CAD;
    public static String COFX;
    public static Double VARIACION_CLP;
    public static Double VARIACION_CAD;
    public static Double VARIACION_COFX;

    public static void setVARIACION_CLP(Double VARIACION_CLP) {
        ModelDolar.VARIACION_CLP = VARIACION_CLP;
        Repository.logviewer.setPropertiesString("VARIACION_CLP", String.valueOf(VARIACION_CLP));
    }

    public static void setVARIACION_CAD(Double VARIACION_CAD) {

        ModelDolar.VARIACION_CAD = VARIACION_CAD;
        Repository.logviewer.setPropertiesString("VARIACION_CAD", String.valueOf(VARIACION_CAD));
    }

    public static void setVARIACION_COFX(Double VARIACION_COFX) {
        ModelDolar.VARIACION_COFX = VARIACION_COFX;
        Repository.logviewer.setPropertiesString("VARIACION_COFX", String.valueOf(VARIACION_COFX));
    }
}
