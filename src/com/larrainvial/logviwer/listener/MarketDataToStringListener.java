package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.MarketDataToStringEvent;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.DataDictionary;
import quickfix.field.*;
import quickfix.fix42.MarketDataSnapshotFullRefresh;
import quickfix.fix44.MarketDataIncrementalRefresh;
import quickfix.fix44.Message;


public class MarketDataToStringListener implements Listener {

    private  DataDictionary dictionary;

    private  String symbol;
    private  String messageByType;
    private  String hour;
    private  String anio;

    private  Double buyPx;
    private  Double buyQty;
    private  Double sellPx;
    private  Double sellQty;
    private  Double closePx;

    public MarketDataToStringListener() {

        try {
            dictionary = new DataDictionary("resources/FIX44.xml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void eventOccurred(Event event) {

        try {

            MarketDataToStringEvent ev = (MarketDataToStringEvent) event;

            String mesage = ev.lineFromLog;
            buyPx = buyQty = sellPx = sellQty = closePx = 0d;

            Message mktDataInc = stringToFix(mesage);
            MsgType msgType = Message.identifyType(mesage);

            String[] date = mesage.split("8=")[0].split("-");
            messageByType = msgType.getValue();
            symbol = (mktDataInc.isSetField(Symbol.FIELD)) ? mktDataInc.getString(Symbol.FIELD) : symbol;
            anio = date[0];
            hour = date[1];


            if(msgType.valueEquals(MarketDataSnapshotFullRefresh.MSGTYPE) || msgType.valueEquals(MarketDataIncrementalRefresh.MSGTYPE)) {
               this.setMarketData(mktDataInc);
            }

            if(ev.nameAlgo.equals(Repository.NameAdrArbitrageXSGO)) {
                Repository.adrArbitrageXSGO_MKD_DOLAR.add(new ModelMarketData(anio, hour, messageByType, symbol,  buyPx, buyQty, sellPx, sellQty, closePx));
            }

            Controller.dispatchEvent(new SendToViewEvent(this, ev.nameAlgo, ev.typeMarket));


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Message stringToFix(String fixMsg) throws Exception {

        Message fixMessage = new Message();
        fixMessage.fromString(fixMsg.split("FIX.4.4" + "\u0001")[1], dictionary, false);

        return fixMessage;
    }


    public void setMarketData(Message mktDataInc){

        MarketDataIncrementalRefresh.NoMDEntries noMDEntries;

        try {

            for (int i = 1; i <= mktDataInc.getInt(NoMDEntries.FIELD); i++) {
                noMDEntries = new MarketDataIncrementalRefresh.NoMDEntries();

                mktDataInc.getGroup(i, noMDEntries);

                if (noMDEntries.getMDEntryType().valueEquals(MDEntryType.BID)) {
                    this.buyPx = noMDEntries.getMDEntryPx().getValue();
                    this.buyQty = noMDEntries.getMDEntrySize().getValue();

                } else if (noMDEntries.getMDEntryType().valueEquals(MDEntryType.OFFER)) {
                    this.sellPx = noMDEntries.getMDEntryPx().getValue();
                    this.sellQty = noMDEntries.getMDEntrySize().getValue();

                } else if (noMDEntries.getMDEntryType().valueEquals(MDEntryType.CLOSING_PRICE)) {
                    this.closePx = noMDEntries.getMDEntryPx().getValue();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.out);

        }


    }
}
