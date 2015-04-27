package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class SendToViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event){

        try {

            SendToViewEvent ev = (SendToViewEvent) event;

            algo = Repository.strategy.get(ev.nameAlgo);

            if(ev.typeMarket.equals(algo.getMkd_dolar())){
                algo.getDolarList().add(ev.modelMarketData);
                algo.getMkd_dolar_tableView().setItems(algo.getDolarList());
            }

            if(ev.typeMarket.equals(algo.getMkd_adr())){
                algo.getMkd_adr_list().add(ev.modelMarketData);
                algo.getMkd_adr_tableView().setItems(algo.getMkd_adr_list());
            }

            if(ev.typeMarket.equals(algo.getMkd_local())){
                algo.getMkd_local_list().add(ev.modelMarketData);
                algo.getMkd_local_tableView().setItems(algo.getMkd_local_list());
            }

            if(ev.typeMarket.equals(algo.getRouting_adr())){
                algo.getRouting_adr_list().add(ev.modelRoutingData);
                algo.getRouting_adr_tableView().setItems(algo.getRouting_adr_list());
            }

            if(ev.typeMarket.equals(algo.getRouting_local())){
                algo.getRouting_local_list().add(ev.modelRoutingData);
                algo.getRouting_local_tableView().setItems(algo.getRouting_local_list());
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
