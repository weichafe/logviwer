package com.larrainvial.logviwer.listener.alert;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.utils.Sound;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class AlertListener extends Thread {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    public ModelMarketData modelMarketData;
    public ModelRoutingData modelRoutingData;
    public String execType;
    public String messageByType;
    public String typeMarket;
    public String text;
    public String hour;

    public AlertListener(Object source, ModelMarketData modelMarketData, String typeMarket) {
        this.algo = (Algo) source;
        this.modelMarketData = modelMarketData;
        this.messageByType = modelMarketData.messageByType;
        this.typeMarket = typeMarket;
        this.text = modelMarketData.text;
        this.hour = modelMarketData.hour;
    }

    public AlertListener(Object source, ModelRoutingData modelRoutingData, String typeMarket) {
        this.algo = (Algo) source;
        this.modelRoutingData = modelRoutingData;
        this.execType = modelRoutingData.execType;
        this.messageByType = modelRoutingData.messageByType;
        this.typeMarket = typeMarket;
        this.text = modelRoutingData.text;
        this.hour = modelRoutingData.hour;
    }


    @Override
    public synchronized void run() {

        try {

            if (!algo.nameAlgo.equals(algo.nameAlgo) || Helper.validateTime(hour)) return;

            if (messageByType.equals("8") && execType.equals("8")) {

                Notifier.INSTANCE.notifyError(algo.nameAlgo, modelRoutingData.text + " " + typeMarket + "\n" + "Rejected, 35=8 ;150 = 8 !");
                Helper.printerLog(algo.nameAlgo + " Rejected, 35=8 ;150 = 8 ! " + modelRoutingData.text + " " + typeMarket);
                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("9") && modelRoutingData.text.equals("RMG Reject: routing failure")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Rejected, check log files!\n");
                stringBuffer.append(modelRoutingData.text + "\n");
                stringBuffer.append(typeMarket + "\n");

                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " Rejected, check log files! " + modelRoutingData.text + " " + typeMarket);
                if (algo.isAlert()) new Sound();


            } else if (messageByType.equals("A")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Rejected, check log files! " + "\n");
                stringBuffer.append(algo.nameAlgo + "\n");
                stringBuffer.append(typeMarket + "\n");

                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("1")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("TestRequest, check log files! " + "\n");
                stringBuffer.append(typeMarket + "\n");

                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("5")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Logout, check log files! " + "\n");
                stringBuffer.append("ev.typeMarket" + "\n");

                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " Logout, check log files! " + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("3")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Protocol, check log files! " + "\n");
                stringBuffer.append(typeMarket + "\n");

                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " Protocol, check log files! " + text + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("8") && execType.equals("8")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Reject, check log files! " + "\n");
                stringBuffer.append(modelRoutingData.text + "\n");

                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " Reject, check log files! " + modelRoutingData.text + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            }


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }


    }

}
