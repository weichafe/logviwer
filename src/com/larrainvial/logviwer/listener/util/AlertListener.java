package com.larrainvial.logviwer.listener.util;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.utils.Sound;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;

public class AlertListener implements Runnable {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    public ModelRoutingData modelRoutingData;
    public ModelMarketData modelMarketData;
    public String execType;
    public String messageByType;
    public String typeMarket;
    public String text;
    public String hour;

    public AlertListener(Algo algo, ModelRoutingData modelRoutingData, String typeMarket) {
        this.algo = algo;
        this.modelRoutingData = modelRoutingData;
        this.execType = modelRoutingData.execType;
        this.messageByType = modelRoutingData.messageByType;
        this.typeMarket = typeMarket;
        this.text = modelRoutingData.text;
        this.hour = modelRoutingData.hour;
    }

    public AlertListener(Algo algo, ModelMarketData modelMarketData, String typeMarket) {
        this.algo = algo;
        this.modelMarketData = modelMarketData;
        this.messageByType = modelMarketData.messageByType;
        this.typeMarket = typeMarket;
        this.text = modelMarketData.text;
        this.hour = modelMarketData.hour;
    }



    @Override
    public void run() {

        try {

            if (!this.algo.nameAlgo.equals(algo.nameAlgo) || validateTime(hour)) return;

            if (messageByType.equals("8") && execType.equals("8")) {

                Notifier.INSTANCE.notifyError(algo.nameAlgo, modelRoutingData.text + " " + typeMarket + "\n" + "Rejected, 35=8 ;150 = 8 !");
                Helper.printerLog(algo.nameAlgo + " Rejected, 35=8 ;150 = 8 ! " + modelRoutingData.text + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("9") && modelRoutingData.text.equals("RMG Reject: routing failure")) {

                String text = "Rejected, check log files!" + "\n";
                text += modelRoutingData.text + "\n";
                text += typeMarket + "\n";

                Notifier.INSTANCE.notifyError(algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " Rejected, check log files! " + modelRoutingData.text + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("A")) {

                String text = "Rejected, check log files! " + "\n";
                text += algo.nameAlgo + "\n";
                text += typeMarket + "\n";

                Notifier.INSTANCE.notifyError(algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("1")) {

                String text = "TestRequest, check log files! " + "\n";
                text += typeMarket + "\n";

                Notifier.INSTANCE.notifyError(algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("5")) {

                String text = "Logout, check log files! " + "\n";
                text += typeMarket + "\n";

                Notifier.INSTANCE.notifyError(algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " Logout, check log files! " + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("3")) {

                String text = "Protocol, check log files! " + "\n";
                text += typeMarket + "\n";

                Notifier.INSTANCE.notifyError(algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " Protocol, check log files! " + modelRoutingData.text + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (messageByType.equals("8") && modelRoutingData.execType.equals("8")) {

                String text = "Reject, check log files! " + "\n";
                text += modelRoutingData.text + "\n";
                text += typeMarket + "\n";

                Notifier.INSTANCE.notifyError(algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " Reject, check log files! " + modelRoutingData.text + " " + typeMarket);

                if (algo.isAlert()) new Sound();

            }


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }


    }

    public Boolean validateTime(String time){

        try {

            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH:mm:ss");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

            SimpleDateFormat dateFormatLocal = new SimpleDateFormat("HH:mm:ss");
            Date datePC = dateFormatLocal.parse(dateFormatGmt.format(new Date()));

            Calendar newDatePC = Calendar.getInstance();
            newDatePC.setTime(datePC);
            newDatePC.set(Calendar.MINUTE, newDatePC.get(Calendar.MINUTE) - 5);
            datePC = newDatePC.getTime();

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date dateLog = formatter.parse(time);

            return (datePC.before(dateLog)) ? false : true;

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }

        return null;

    }



}
