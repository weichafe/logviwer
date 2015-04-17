package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.MainApp;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.controller.adrarbitragexsgo.AdrArbitrageXSGODolarController;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class SendToViewListener implements Listener {

    @Override
    public void eventOccurred(Event event){

        try {

            SendToViewEvent ev = (SendToViewEvent) event;

            if(ev.nameAlgo.equals(Repository.NameAdrArbitrageXSGO) & ev.typeMarket.equals(Repository.DOLAR)){
                FXMLLoader adrArbitrageXSGO_DOLAR_LOADER = new FXMLLoader();
                adrArbitrageXSGO_DOLAR_LOADER.setLocation(MainApp.class.getResource("view/ARDArbitrage_XSGO_MKD_DOLAR.fxml"));
                AdrArbitrageXSGODolarController controller =  adrArbitrageXSGO_DOLAR_LOADER.getController();
                controller.getModelDolar().setItems(Repository.adrArbitrageXSGO_DOLAR);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
