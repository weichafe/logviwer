package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.controller.adrarbitragexsgo.AdrArbitrageXSGODolarController;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class SendToViewListener implements Listener {

    @Override
    public void eventOccurred(Event event){

        try {

            SendToViewEvent ev = (SendToViewEvent) event;

            if(ev.nameAlgo.equals(Repository.NameAdrArbitrageXSGO) & ev.typeMarket.equals(Repository.DOLAR)){

                AdrArbitrageXSGODolarController controller =  Repository.adrArbitrageXSGO_DOLAR_LOADER.getController();
                controller.getModelDolar().setItems(Repository.adrArbitrageXSGO_MKD_DOLAR);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
