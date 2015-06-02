package com.larrainvial.logviwer.utils;


import com.javtech.javatoolkit.fix.FixConstants;
import com.javtech.javatoolkit.message.Attribute;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {

    public static synchronized void exception(Exception e) {

        try {

            e.printStackTrace();

            Platform.runLater(new Runnable() {

                public void run() {

                    Alert alertException = new Alert(Alert.AlertType.ERROR);
                    alertException.setTitle("Exception Dialog");
                    alertException.setHeaderText("Look, an Exception Dialog");
                    alertException.setContentText(e.toString());

                    Exception ex = new FileNotFoundException(e.toString());

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String exceptionText = sw.toString();

                    Label label = new Label("The exception stacktrace was:");

                    TextArea textArea = new TextArea(exceptionText);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);

                    textArea.setMaxWidth(Double.MAX_VALUE);
                    textArea.setMaxHeight(Double.MAX_VALUE);
                    GridPane.setVgrow(textArea, Priority.ALWAYS);
                    GridPane.setHgrow(textArea, Priority.ALWAYS);

                    GridPane expContent = new GridPane();
                    expContent.setMaxWidth(Double.MAX_VALUE);
                    expContent.add(label, 0, 0);
                    expContent.add(textArea, 0, 1);

                    alertException.getDialogPane().setExpandableContent(expContent);
                    alertException.showAndWait();

                }
            });


        }catch (Exception ex){
            ex.printStackTrace();

        }
    }

    public static synchronized void  alert(String headerText, String contentText1){


            Platform.runLater(new Runnable() {
                public void run() {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText(headerText);
                    alert.setContentText(contentText1);
                    alert.showAndWait();
                }
            });

    }


    public synchronized static String adrToLocal(String symbolLocal){

        if(symbolLocal.equals("AVAL")) return "PFAVAL";
        if(symbolLocal.equals("AVH")) return "PFAVH";
        if(symbolLocal.equals("CIB")) return "PFBCOLOM";
        if(symbolLocal.equals("EC")) return "ECOPETROL";
        if(symbolLocal.equals("PRE")) return "PREC";
        if(symbolLocal.equals("CNE")) return "CNEC";
        if(symbolLocal.equals("BCH")) return "CHILE";
        if(symbolLocal.equals("LFL")) return "LAN";
        if(symbolLocal.equals("BSAC")) return "BSANTANDER";
        if(symbolLocal.equals("CNCO")) return "CENCOSUD";
        if(symbolLocal.equals("CCU")) return "CCU";
        if(symbolLocal.equals("BCA")) return "CORPBANCA";
        if(symbolLocal.equals("AKO.A")) return "ANDINA-A";
        if(symbolLocal.equals("AKO/A")) return "ANDINA-A";
        if(symbolLocal.equals("AKO/B")) return "ANDINA-B";
        if(symbolLocal.equals("AKO.B")) return "ANDINA-B";
        if(symbolLocal.equals("AKO A")) return "ANDINA-A";
        if(symbolLocal.equals("AKO B")) return "ANDINA-B";
        if(symbolLocal.equals("EOC")) return "ENDESA";
        if(symbolLocal.equals("ENI")) return "ENERSIS";
        if(symbolLocal.equals("SQM")) return "SQM-B";
        if(symbolLocal.equals("VCO")) return "CONCHATORO";

        return symbolLocal;
    }

    public synchronized Double ratio(String symbolLocal){

        if(symbolLocal.equals("PFAVAL"))      return 20d;
        if(symbolLocal.equals("PFAVH"))       return 8d;
        if(symbolLocal.equals("PFBCOLOM"))    return 4d;
        if(symbolLocal.equals("ECOPETROL"))   return 20d;

        if(symbolLocal.equals("PREC"))        return 1d;
        if(symbolLocal.equals("CNEC"))        return 1d;
        if(symbolLocal.equals("CHILE"))       return 600d;
        if(symbolLocal.equals("LAN"))         return 1d;
        if(symbolLocal.equals("BSANTANDER"))  return 400d;
        if(symbolLocal.equals("CENCOSUD"))    return 3d;
        if(symbolLocal.equals("CCU"))         return 2d;
        if(symbolLocal.equals("CORPBANCA"))   return 1500d;
        if(symbolLocal.equals("ANDINA-A"))    return 6d;
        if(symbolLocal.equals("ANDINA-B"))    return 6d;
        if(symbolLocal.equals("ENDESA"))      return 30d;
        if(symbolLocal.equals("ENERSIS"))     return 50d;
        if(symbolLocal.equals("SQM-B"))       return 1d;
        if(symbolLocal.equals("CONCHATORO"))  return 50d;

        return 1d;
    }


    public synchronized boolean local(String symbolLocal){

        if(symbolLocal.equals("PFAVAL"))      return true;
        if(symbolLocal.equals("PFAVH"))       return true;
        if(symbolLocal.equals("PFBCOLOM"))    return true;
        if(symbolLocal.equals("ECOPETROL"))   return true;

        if(symbolLocal.equals("PREC"))        return true;
        if(symbolLocal.equals("CNEC"))        return true;
        if(symbolLocal.equals("CHILE"))       return true;
        if(symbolLocal.equals("LAN"))         return true;
        if(symbolLocal.equals("BSANTANDER"))  return true;
        if(symbolLocal.equals("CENCOSUD"))    return true;
        if(symbolLocal.equals("CCU"))         return true;
        if(symbolLocal.equals("CORPBANCA"))   return true;
        if(symbolLocal.equals("ANDINA-A"))    return true;
        if(symbolLocal.equals("ANDINA-B"))    return true;
        if(symbolLocal.equals("ENDESA"))      return true;
        if(symbolLocal.equals("ENERSIS"))     return true;
        if(symbolLocal.equals("SQM-B"))       return true;
        if(symbolLocal.equals("CONCHAYTORO")) return true;

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


    public synchronized static Map<Object, Object> getFixMessageParties(String fixMessageString) {

        try {

            Map<Object, Object> orderedFixMessage = new HashMap<Object, Object>();
            Map<Object, Object> mdParties = null;
            List<Map> parties = new ArrayList<Map>();

            Integer noPartyIDs = -1;
            Integer countId = 0;

            HashMap attributes = getHashWithAttribute();

            String[] valuesFixMessage = fixMessageString.split("\1");
            for (String tag : valuesFixMessage) {

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
            }

            if (!parties.isEmpty())
                orderedFixMessage.put(attributes.get(String.valueOf(FixConstants.NoPartyIDs.getId())), parties);

            return orderedFixMessage;

        } catch (Exception e) {
            //new Algo().exception(e);
        }

        return null;
    }

    public static synchronized HashMap getHashWithAttribute() {

        HashMap attributes = new HashMap();

        for (Object object : FixConstants.m_attributes.getAllAttributes()) {
            Attribute attribute = (Attribute) object;
            attributes.put(String.valueOf(attribute.getId()), attribute);
        }

        return attributes;
    }

    public static synchronized Map<Object, Object> getFixMessageAttributeFull(String fixMessageString)throws Exception {

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

}
