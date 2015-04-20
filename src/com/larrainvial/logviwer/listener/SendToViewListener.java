package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.controller.adrarbitragexsgo.DolarController;
import com.larrainvial.logviwer.controller.adrarbitragexsgo.NyseMKDController;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class SendToViewListener implements Listener {

    @Override
    public void eventOccurred(Event event){

        try {

            SendToViewEvent ev = (SendToViewEvent) event;

            if(ev.nameAlgo.equals(Repository.NameAdrArbitrageXSGO) & ev.typeMarket.equals(Repository.DOLAR)){

                DolarController controller =  Repository.adrArbitrageXSGO_MKD_DOLAR_LOADER.getController();
                Repository.adrArbitrageXSGO_MKD_DOLAR.add(ev.modelMarketData);
                controller.getDolar().setItems(Repository.adrArbitrageXSGO_MKD_DOLAR);
            }

            if(ev.nameAlgo.equals(Repository.NameAdrArbitrageXSGO) & ev.typeMarket.equals(Repository.MKD_NYSE)){
                Repository.adrArbitrageXSGO_MKD_NYSE.add(ev.modelMarketData);
                NyseMKDController controller =  Repository.adrArbitrageXSGO_MKD_NYSE_LOADER.getController();
                controller.getModelNyseMkd().setItems(Repository.adrArbitrageXSGO_MKD_DOLAR);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
