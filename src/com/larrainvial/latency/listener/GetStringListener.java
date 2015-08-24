package com.larrainvial.latency.listener;

import com.larrainvial.latency.Algo;
import com.larrainvial.latency.event.GetStringEvent;
import com.larrainvial.latency.utils.Helper;
import com.larrainvial.latency.vo.LatencyVO;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class GetStringListener implements Listener {

    public Algo algo;

    public GetStringListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event){

        try {

            GetStringEvent ev = (GetStringEvent) event;

            if (!ev.algo.nameFile.equals(algo.nameFile)) return;
            this.getVo(ev.getLineString, ev.algo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void getVo(String line,Algo algoTemp) {

        try {

            LatencyVO latency = Helper.returnLatencyVO(line, algoTemp);

            if (latency != null ){

                System.out.println("Enviando desde el BAM -> " + algoTemp.nameFile + "  Orden ->"  + latency.clOrdID);
                algo.hashLatencyVO.remove(latency.clOrdID);
            }


        } catch (Exception e){
            e.printStackTrace();
        }





    }

}
