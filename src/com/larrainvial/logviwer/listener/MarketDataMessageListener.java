package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.MarketDataMessageEvent;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.field.MDEntryType;
import quickfix.field.NoMDEntries;
import quickfix.fix42.MarketDataSnapshotFullRefresh;
import quickfix.fix44.MarketDataIncrementalRefresh;
import quickfix.fix44.Message;

public class MarketDataMessageListener implements Listener {

    public ModelMarketData modelMarketData;
    private Algo algo;


    @Override
    public void eventOccurred(Event event){

        try {

            MarketDataMessageEvent ev = (MarketDataMessageEvent) event;

            modelMarketData = ev.modelMarketData;

            this.setMarketData(ev.messageFix);
            Controller.dispatchEvent(new SendToViewEvent(this, ev.nameAlgo, ev.typeMarket, modelMarketData));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setMarketData(Message mktDataInc){

        MarketDataIncrementalRefresh.NoMDEntries noMDEntries;

        try {

            for (int i = 1; i <= mktDataInc.getInt(NoMDEntries.FIELD); i++) {
                noMDEntries = new MarketDataIncrementalRefresh.NoMDEntries();

                mktDataInc.getGroup(i, noMDEntries);

                if (noMDEntries.getMDEntryType().valueEquals(MDEntryType.BID)) {
                    modelMarketData.setBuyPx(noMDEntries.getMDEntryPx().getValue());
                    modelMarketData.setBuyQty(noMDEntries.getMDEntrySize().getValue());

                } else if (noMDEntries.getMDEntryType().valueEquals(MDEntryType.OFFER)) {
                    modelMarketData.setSellPx(noMDEntries.getMDEntryPx().getValue());
                    modelMarketData.setSellQty(noMDEntries.getMDEntrySize().getValue());

                } else if (noMDEntries.getMDEntryType().valueEquals(MDEntryType.CLOSING_PRICE)) {
                    modelMarketData.setClosePx(noMDEntries.getMDEntryPx().getValue());
                }
            }

        } catch (Exception ex) {
            //System.out.println(mktDataInc);
        }


    }
}
