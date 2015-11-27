package com.larrainvial.logviwer.listener.alert;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.utils.Sound;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;

public class AlertListener implements Listener {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AlertListener(Algo algo) {
        this.algo = algo;
    }

    @Override
    public void eventOccurred(Event event) {

        try {
            
            AlertEvent ev = (AlertEvent) event;

            if (!ev.algo.nameAlgo.equals(algo.nameAlgo) || validateTime(ev.hour)) return;

            if (ev.messageByType.equals("8") && ev.execType.equals("8")) {

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, ev.modelRoutingData.text + " " + ev.typeMarket + "\n" + "Rejected, 35=8 ;150 = 8 !");
                Helper.printerLog(algo.nameAlgo + " Rejected, 35=8 ;150 = 8 ! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                if (algo.isAlert()) new Sound();

            } else if (ev.messageByType.equals("9") && ev.modelRoutingData.text.equals("RMG Reject: routing failure")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Rejected, check log files!\n");
                stringBuffer.append(ev.modelRoutingData.text + "\n");
                stringBuffer.append(ev.typeMarket + "\n");

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " Rejected, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                if (algo.isAlert()) new Sound();


            } else if (ev.messageByType.equals("A")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Rejected, check log files! " + "\n");
                stringBuffer.append(ev.algo.nameAlgo + "\n");
                stringBuffer.append(ev.typeMarket + "\n");

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + ev.typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (ev.messageByType.equals("1")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("TestRequest, check log files! " + "\n");
                stringBuffer.append(ev.typeMarket + "\n");

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + ev.typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (ev.messageByType.equals("5")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Logout, check log files! " + "\n");
                stringBuffer.append("ev.typeMarket" + "\n");

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " Logout, check log files! " + " " + ev.typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (ev.messageByType.equals("3")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Protocol, check log files! " + "\n");
                stringBuffer.append(ev.typeMarket + "\n");

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " Protocol, check log files! " + ev.text + " " + ev.typeMarket);

                if (algo.isAlert()) new Sound();

            } else if (ev.messageByType.equals("8") && ev.execType.equals("8")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Reject, check log files! " + "\n");
                stringBuffer.append(ev.modelRoutingData.text + "\n");

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, stringBuffer.toString());
                Helper.printerLog(algo.nameAlgo + " Reject, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);

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
            ex.printStackTrace();
        }

        return null;

    }



}
