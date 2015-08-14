package com.larrainvial.logviwer.utils;

import com.javtech.javatoolkit.fix.FixConstants;
import com.larrainvial.logviwer.model.ModelMarketData;
import quickfix.field.MsgType;
import quickfix.fix44.Message;
import java.util.ArrayList;
import java.util.Map;


public class StringToMarketData {

    private ModelMarketData modelMarketData;

    public ModelMarketData marketData(String message) throws Exception {

            ArrayList<Map> mDEntryType;
            MsgType typeOfMessage = Message.identifyType(message);
            String[] date = message.split("8=")[0].split("-");

            modelMarketData = new ModelMarketData(date[0], date[1], typeOfMessage.getValue());

            Map<Object, Object> messageMap = new Helper().getFixMessageAttributeFull(message);

            modelMarketData.symbol = messageMap.containsKey(FixConstants.Symbol) ? messageMap.get(FixConstants.Symbol).toString() : "";
            modelMarketData.messageByType = typeOfMessage.getValue();

            if(typeOfMessage.getValue().equals("5") || typeOfMessage.getValue().equals("A") || typeOfMessage.getValue().equals("1") || typeOfMessage.getValue().equals("3")){
                return modelMarketData;
            }

            if(!messageMap.containsKey(FixConstants.NoMDEntries)){
                return modelMarketData;
            }


            mDEntryType = (ArrayList<Map>) messageMap.get(FixConstants.NoMDEntries);

            if (messageMap.containsKey(FixConstants.NoMDEntryTypes)) {
                return modelMarketData;
            }



            for (Map map : mDEntryType) {

                if (map.get(FixConstants.MDEntryType).equals("0")) {

                    if(map.containsKey(FixConstants.MDEntrySize)){
                        if (map.get(FixConstants.MDEntrySize).toString() != null) {
                            modelMarketData.buyQty = Double.valueOf(map.get(FixConstants.MDEntrySize).toString());
                    }
                    }

                    if (map.get(FixConstants.MDEntryPx).toString() != null) {
                        modelMarketData.buyPx = Double.valueOf(map.get(FixConstants.MDEntryPx).toString());
                    }
                }

                if (map.get(FixConstants.MDEntryType).equals("1")) {


                    if(map.containsKey(FixConstants.MDEntrySize)){
                        if (map.get(FixConstants.MDEntrySize).toString() != null) {
                            modelMarketData.sellQty = Double.valueOf(map.get(FixConstants.MDEntrySize).toString());
                        }
                    }

                    if (map.get(FixConstants.MDEntryPx).toString() != null) {
                        modelMarketData.sellPx = Double.valueOf(map.get(FixConstants.MDEntryPx).toString());
                    }

                }

                if (map.get(FixConstants.MDEntryType).equals("5")) {

                    if (map.get(FixConstants.MDEntryPx).toString() != null) {
                        modelMarketData.closePx = Double.valueOf(map.get(FixConstants.MDEntryPx).toString());
                    }
                }

                if (map.get(FixConstants.MDEntryType).equals("D")) {

                    if(map.containsKey(FixConstants.MDEntryPx)){
                        if (map.get(FixConstants.MDEntryPx).toString() != null) {
                            modelMarketData.tradeAmount = Double.valueOf(map.get(FixConstants.MDEntryPx).toString());
                        }
                    }

                    if(map.containsKey(FixConstants.MDEntrySize)){
                        if (map.get(FixConstants.MDEntrySize).toString() != null) {
                            modelMarketData.tradeAmount = Double.valueOf(map.get(FixConstants.MDEntrySize).toString());
                        }
                    }

                }
            }

        return modelMarketData;
    }
}
