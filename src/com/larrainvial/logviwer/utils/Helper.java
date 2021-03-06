package com.larrainvial.logviwer.utils;

import com.javtech.javatoolkit.fix.FixConstants;
import com.javtech.javatoolkit.message.Attribute;
import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.model.ModelLatency;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

public class Helper {

    private static Logger logger = Logger.getLogger(Helper.class.getName());

    public void createStrategy() {

        try {

            File xmlFile = new File(Repository.locationPath + "strategy.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(xmlFile);

            Repository.nodeList = document.getDocumentElement().getChildNodes();

            for (int i = 0; i < Repository.nodeList.getLength(); i++) {

                Node node = Repository.nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    new Algo((Element) node);

                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
            logger.error(Level.SEVERE, ex);
        }

    }

    public static Boolean isNumber(String numero){
        try {
            Double.parseDouble(numero);
            return true;

        } catch (NumberFormatException nfe){
            return false;
        }

    }

    public synchronized static String adrToLocal(String symbolLocal){

        //XBOG
        if (symbolLocal.equals("AVAL")) return "PFAVAL";
        if (symbolLocal.equals("AVH")) return "PFAVH";
        if (symbolLocal.equals("CIB")) return "PFBCOLOM";
        if (symbolLocal.equals("EC")) return "ECOPETROL";

        //XTSE
        if (symbolLocal.equals("PRE")) return "PREC";
        if (symbolLocal.equals("CNE")) return "CNEC";

        //XSGO
        if (symbolLocal.equals("BCH")) return "CHILE";
        if (symbolLocal.equals("LFL")) return "LAN";
        if (symbolLocal.equals("BSAC")) return "BSANTANDER";
        if (symbolLocal.equals("CNCO")) return "CENCOSUD";
        if (symbolLocal.equals("CCU")) return "CCU";
        if (symbolLocal.equals("ITCB")) return "ITAUCORP";
        if (symbolLocal.equals("AKO.A")) return "ANDINA-A";
        if (symbolLocal.equals("AKO/A")) return "ANDINA-A";
        if (symbolLocal.equals("AKO/B")) return "ANDINA-B";
        if (symbolLocal.equals("AKO.B")) return "ANDINA-B";
        if (symbolLocal.equals("AKO A")) return "ANDINA-A";
        if (symbolLocal.equals("AKO B")) return "ANDINA-B";
        if (symbolLocal.equals("EOC")) return "ENDESA";
        if (symbolLocal.equals("ENI")) return "ENERSIS";
        if (symbolLocal.equals("SQM")) return "SQM-B";
        if (symbolLocal.equals("VCO")) return "CONCHATORO";

        if (symbolLocal.equals("EOCA")) return "ENDESA-AM";
        if (symbolLocal.equals("EOCC")) return "ENDESA-CH";

        if (symbolLocal.equals("ENIA")) return "ENERSIS-AM";
        if (symbolLocal.equals("ENIC")) return "ENERSIS-CH";



        return symbolLocal;
    }

    public synchronized int positions(String symbolLocal){

        //XBOG
        if (symbolLocal.equals("PFAVAL"))      return 0;
        if (symbolLocal.equals("ECOPETROL"))   return 1;
        if (symbolLocal.equals("PFAVH"))       return 2;
        if (symbolLocal.equals("PFBCOLOM"))    return 3;

        //XTSE
        if (symbolLocal.equals("PREC"))        return 0;
        if (symbolLocal.equals("CNEC"))        return 1;

        //XSGO
        if (symbolLocal.equals("CHILE"))       return 0;
        if (symbolLocal.equals("CONCHATORO"))  return 1;
        if (symbolLocal.equals("LAN"))         return 2;
        if (symbolLocal.equals("BSANTANDER"))  return 3;
        if (symbolLocal.equals("CENCOSUD"))    return 4;
        if (symbolLocal.equals("CCU"))         return 5;
        if (symbolLocal.equals("ITAUCORP"))    return 6;
        if (symbolLocal.equals("ANDINA-A"))    return 7;
        if (symbolLocal.equals("ANDINA-B"))    return 8;
        if (symbolLocal.equals("ENDESA"))      return 9;
        if (symbolLocal.equals("ENERSIS"))     return 10;
        if (symbolLocal.equals("SQM-B"))       return 11;

        if (symbolLocal.equals("ENDESA-AM"))   return 12;
        if (symbolLocal.equals("ENDESA-CH"))   return 13;
        if (symbolLocal.equals("ENERSIS-AM"))  return 14;
        if (symbolLocal.equals("ENERSIS-CH"))  return 15;

        return 0;
    }

    public synchronized Double ratio(String symbolLocal){

        //XBOG
        if (symbolLocal.equals("PFAVAL"))      return 20d;
        if (symbolLocal.equals("PFAVH"))       return 8d;
        if (symbolLocal.equals("PFBCOLOM"))    return 4d;
        if (symbolLocal.equals("ECOPETROL"))   return 20d;

        //XTSE
        if (symbolLocal.equals("PREC"))        return 1d;
        if (symbolLocal.equals("CNEC"))        return 1d;

        //XSGO
        if (symbolLocal.equals("CHILE"))       return 600d;
        if (symbolLocal.equals("LAN"))         return 1d;
        if (symbolLocal.equals("BSANTANDER"))  return 400d;
        if (symbolLocal.equals("CENCOSUD"))    return 3d;
        if (symbolLocal.equals("CCU"))         return 2d;
        if (symbolLocal.equals("ITAUCORP"))   return 1500d;
        if (symbolLocal.equals("ANDINA-A"))    return 6d;
        if (symbolLocal.equals("ANDINA-B"))    return 6d;
        if (symbolLocal.equals("ENDESA"))      return 30d;
        if (symbolLocal.equals("ENERSIS"))     return 50d;
        if (symbolLocal.equals("SQM-B"))       return 1d;
        if (symbolLocal.equals("CONCHATORO"))  return 50d;

        if (symbolLocal.equals("ENDESA-AM"))   return 30d;
        if (symbolLocal.equals("ENDESA-CH"))   return 30d;
        if (symbolLocal.equals("ENERSIS-AM"))  return 50d;
        if (symbolLocal.equals("ENERSIS-CH"))  return 50d;

        return 1d;
    }

    public synchronized static Integer position(String symbolLocal){

        //XBOG
        if (symbolLocal.equals("PFAVAL"))      return 1;
        if (symbolLocal.equals("PFAVH"))       return 2;
        if (symbolLocal.equals("PFBCOLOM"))    return 3;
        if (symbolLocal.equals("ECOPETROL"))   return 4;

        //XTSE
        if (symbolLocal.equals("PREC"))        return 1;
        if (symbolLocal.equals("CNEC"))        return 2;

        //XSGO
        if (symbolLocal.equals("CHILE"))       return 1;
        if (symbolLocal.equals("LAN"))         return 2;
        if (symbolLocal.equals("BSANTANDER"))  return 3;
        if (symbolLocal.equals("CENCOSUD"))    return 4;
        if (symbolLocal.equals("CCU"))         return 5;
        if (symbolLocal.equals("ITAUCORP"))   return 6;
        if (symbolLocal.equals("ANDINA-A"))    return 7;
        if (symbolLocal.equals("ANDINA-B"))    return 8;
        if (symbolLocal.equals("ENDESA"))      return 9;
        if (symbolLocal.equals("ENERSIS"))     return 10;
        if (symbolLocal.equals("SQM-B"))       return 11;
        if (symbolLocal.equals("CONCHATORO"))  return 12;

        return 1;
    }

    public synchronized boolean local(String symbolLocal){

        //XBOG
        if (symbolLocal.equals("PFAVAL"))      return true;
        if (symbolLocal.equals("PFAVH"))       return true;
        if (symbolLocal.equals("PFBCOLOM"))    return true;
        if (symbolLocal.equals("ECOPETROL"))   return true;

        //XTSE
        if (symbolLocal.equals("PREC"))        return true;
        if (symbolLocal.equals("CNEC"))        return true;

        //XSGO
        if (symbolLocal.equals("CHILE"))       return true;
        if (symbolLocal.equals("LAN"))         return true;
        if (symbolLocal.equals("BSANTANDER"))  return true;
        if (symbolLocal.equals("CENCOSUD"))    return true;
        if (symbolLocal.equals("CCU"))         return true;
        if (symbolLocal.equals("ITAUCORP"))   return true;
        if (symbolLocal.equals("ANDINA-A"))    return true;
        if (symbolLocal.equals("ANDINA-B"))    return true;
        if (symbolLocal.equals("ENDESA"))      return true;
        if (symbolLocal.equals("ENERSIS"))     return true;
        if (symbolLocal.equals("SQM-B"))       return true;
        if (symbolLocal.equals("CONCHATORO"))  return true;

        if (symbolLocal.equals("ENERSIS-CH"))  return true;
        if (symbolLocal.equals("ENERSIS-AM"))  return true;
        if (symbolLocal.equals("ENDESA-CH"))  return true;
        if (symbolLocal.equals("ENDESA-AM"))  return true;

        return false;
    }

    public static synchronized String side(String side){

        if(side.equals("1")) return "Buy";
        if(side.equals("2")) return "Sell";
        if(side.equals("5")) return "Sell Short";

        return side;
    }

    public static synchronized String orderStatus(String orderStatus){

        if(orderStatus.equals("0")) return "New";
        if(orderStatus.equals("1")) return "Partially filled";
        if(orderStatus.equals("2")) return "Filled";
        if(orderStatus.equals("3")) return "Done for day";
        if(orderStatus.equals("4")) return "Canceled";
        if(orderStatus.equals("5")) return "Replaced";
        if(orderStatus.equals("6")) return "Pending Cancel";
        if(orderStatus.equals("7")) return "Stopped";
        if(orderStatus.equals("8")) return "Rejected";
        if(orderStatus.equals("9")) return "Suspended";
        if(orderStatus.equals("A")) return "Pending New";
        if(orderStatus.equals("B")) return "Calculated";
        if(orderStatus.equals("C")) return "Expired";
        if(orderStatus.equals("C")) return "Accepted for Bidding";
        if(orderStatus.equals("E")) return "Pending Replace";

        return orderStatus;
    }

    public static synchronized String execType(String execType){

        if(execType.equals("0")) return "New";
        if(execType.equals("3")) return "Done for day";
        if(execType.equals("4")) return "Canceled";
        if(execType.equals("5")) return "Replaced";
        if(execType.equals("6")) return "Pending Cancel";
        if(execType.equals("E")) return "Pending Replace";
        if(execType.equals("7")) return "Stopped";
        if(execType.equals("8")) return "Rejected";
        if(execType.equals("9")) return "Suspended";
        if(execType.equals("A")) return "Pending New";
        if(execType.equals("B")) return "Calculated";
        if(execType.equals("C")) return "Expired";
        if(execType.equals("D")) return "Restated";
        if(execType.equals("F")) return "Trade";
        if(execType.equals("G")) return "Trade Correct";
        if(execType.equals("H")) return "Trade Cancel";
        if(execType.equals("I")) return "Order Status";
        if(execType.equals("J")) return "Trade in a Clearing Hold";
        if(execType.equals("K")) return "Trade has been released to Clearingl";
        if(execType.equals("L")) return "Trade Cancel";

        return execType;
    }

    public Map<Object, Object> getFixMessageParties(String fixMessageString) {

        try {

            Map<Object, Object> orderedFixMessage = new HashMap<Object, Object>();
            Map<Object, Object> mdParties = null;
            List<Map> parties = new ArrayList<Map>();

            Integer noPartyIDs = -1;
            Integer countId = 0;

            HashMap attributes = getHashWithAttribute();

            String[] valuesFixMessage = fixMessageString.split("\1");

            for (String tag : valuesFixMessage) {

                try {

                    String[] value = tag.split("=");

                    Attribute attribute = (Attribute) attributes.get(value[0]);

                    if (attribute != null) {

                        if (attribute.equals(FixConstants.NoPartyIDs)) noPartyIDs = Integer.valueOf(value[1]);

                        if (attribute.equals(FixConstants.PartyID)) {

                            mdParties = new HashMap<Object, Object>();
                            mdParties.put(attribute, value[1]);
                            parties.add(mdParties);

                            countId++;
                            continue;

                        } else if (countId <= noPartyIDs
                                && attribute.equals(FixConstants.PartyID)
                                || attribute.equals(FixConstants.PartyIDSource)
                                || attribute.equals(FixConstants.PartyRole)) {

                            if (mdParties != null) mdParties.put(attribute, value[1]);

                        } else {
                            orderedFixMessage.put(attribute, value[1]);
                        }

                    }

                } catch (Exception ex){
                    continue;
                }
            }

            if (!parties.isEmpty())
                orderedFixMessage.put(attributes.get(String.valueOf(FixConstants.NoPartyIDs.getId())), parties);

            return orderedFixMessage;

        } catch (Exception ex) {
            ex.printStackTrace();
            Helper.printerLog(ex.toString());
            Notifier.INSTANCE.notifyError("Error", ex.toString());
        }

        return null;
    }

    public HashMap getHashWithAttribute() {

        HashMap attributes = new HashMap();

        for (Object object : FixConstants.m_attributes.getAllAttributes()) {
            Attribute attribute = (Attribute) object;
            attributes.put(String.valueOf(attribute.getId()), attribute);
        }

        return attributes;
    }

    public Map<Object, Object> getFixMessageAttributeFull(String fixMessageString) throws Exception {

        Map<Object, Object> orderedFixMessage = new HashMap<Object, Object>();

        Map<Object, Object> mdEntries = null;
        List<Map> entries = new ArrayList<Map>();

        Integer noMDEntries = -1;
        Integer countId = 0;

        HashMap attributes = getHashWithAttribute();

        String[] valuesFixMessage = fixMessageString.split("\1");

        for(String tag : valuesFixMessage){

            String[] value = tag.split("=");

            Attribute attribute = (value[0].equals("1020")) ? (Attribute) attributes.get(String.valueOf(FixConstants.MDEntrySize.getId())) : (Attribute) attributes.get(value[0]);

            if(attribute != null){

                if(attribute.equals(FixConstants.NoMDEntries)) noMDEntries = Integer.valueOf(value[1]);

                if(attribute.equals(FixConstants.MDEntryType)){

                    mdEntries = new HashMap<Object, Object>();

                    mdEntries.put(attribute, value[1]);

                    entries.add(mdEntries);

                    countId++;
                    continue;

                }else if(countId <= noMDEntries){

                    if(mdEntries != null) mdEntries.put(attribute, value[1]);
                    orderedFixMessage.put(attribute, value[1]);

                }else{

                    orderedFixMessage.put(attribute, value[1]);
                }
            }
        }

        if(!entries.isEmpty()) orderedFixMessage.put(attributes.get(String.valueOf(FixConstants.NoMDEntries.getId())), entries);
        return orderedFixMessage;

    }

    public static Boolean isRouting(ModelRoutingData modelRoutingData){

        if (modelRoutingData.messageByType.equals("D")) return true;
        else if (modelRoutingData.messageByType.equals("G")) return true;
        else if (modelRoutingData.messageByType.equals("F")) return true;
        else if (modelRoutingData.messageByType.equals("8")) return true;
        else if (modelRoutingData.messageByType.equals("9")) return true;
        else return false;

    }

    public static Boolean isSolicitud(ModelRoutingData modelRoutingData){

        if (modelRoutingData.messageByType.equals("D")) return true;
        else if (modelRoutingData.messageByType.equals("G")) return true;
        else if (modelRoutingData.messageByType.equals("F")) return true;
        else return false;

    }

    public static Boolean isASK(ModelRoutingData modelRoutingData){

        if (modelRoutingData.execType.equals("F")) return true;
        else if (modelRoutingData.execType.equals("New")) return true;
        else if (modelRoutingData.execType.equals("Cancel")) return true;
        else if (modelRoutingData.execType.equals("Replaced")) return true;
        else if (modelRoutingData.execType.equals("Rejected")) return true;
        else if (modelRoutingData.execType.equals("Expired")) return true;
        else if (modelRoutingData.execType.equals("Restated")) return true;
        else if (modelRoutingData.execType.equals("Suspended")) return true;
        else if (modelRoutingData.execType.equals("Done for day")) return true;
        else return false;

    }

    public static void printLatency(ModelLatency latencyVO){
        logger.info("Latency superior a 400 mm");
        logger.info("clOrdID " + latencyVO.clOrdID);
        logger.info("nameAlgo " + latencyVO.nameAlgo);
        logger.info("timeStart " + latencyVO.timeStart);
        logger.info("timeEnd " + latencyVO.timeEnd);
        logger.info("timeLatency " + latencyVO.timeLatency);
        logger.info(latencyVO.routing);

    }

    public synchronized static void  printerLog(String msg){

        try {
            logger.info(msg + "\n");
        } catch (Exception ex){
            ex.printStackTrace();
            ex.printStackTrace();
        }

    }

    public static Boolean validateTime(String time){

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

    public static Double formatNumber(double number) {

        DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
        return new Double(df2.format(number)).doubleValue();
    }



}