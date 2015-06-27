package com.larrainvial.logviwer;

import com.larrainvial.logviwer.event.readlog.*;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.*;

public class Algo implements Serializable {

    public static final long serialVersionUID = 8799656478674226639L;

    public String nameAlgo;
    public String mkd_dolar;
    public String mkd_local;
    public String mkd_adr;
    public String routing_local;
    public String routing_adr;

    public double time;

    public FXMLLoader mkd_dolar_loader = new FXMLLoader();
    public FXMLLoader mkd_local_loader = new FXMLLoader();
    public FXMLLoader mkd_adr_loader = new FXMLLoader();
    public FXMLLoader routing_adr_loader = new FXMLLoader();
    public FXMLLoader routing_local_loader = new FXMLLoader();
    public FXMLLoader panel_positions_loader = new FXMLLoader();

    public ObservableList<ModelMarketData> dolarMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelMarketData> dolarFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> mkdAdrMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelMarketData> mkdAdrFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelMarketData> mkdLocalMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelMarketData> mkdLocalFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> routingAdrMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelRoutingData> routingAdrFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> routingLocalMasterList = FXCollections.observableArrayList();
    public ObservableList<ModelRoutingData> routingLocalFilterList = FXCollections.observableArrayList();

    public ObservableList<ModelRoutingData> routingBlotterMasterLsit = FXCollections.observableArrayList();
    public ObservableList<ModelRoutingData> routingBlotterFilterLsit = FXCollections.observableArrayList();

    public ObservableList<ModelPositions> positionsMasterList = FXCollections.observableArrayList();
    public  Map<String,ModelPositions> positionsMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelPositions>());
    public  Map<String,Integer> positionsMap = Collections.synchronizedMap(new LinkedHashMap<String,Integer>());
    public int contPositions = 0;

    public TableView<ModelMarketData> mkd_dolar_tableView;
    public TableView<ModelMarketData> mkd_adr_tableView;
    public TableView<ModelMarketData> mkd_local_tableView;
    public TableView<ModelRoutingData> routing_adr_tableView;
    public TableView<ModelRoutingData> routing_local_tableView;
    public TableView<ModelPositions> panel_positions_tableView;

    public boolean mkd_dolar_toggle = false;
    public boolean mkd_local_toggle = false;
    public boolean mkd_adr_toggle = false;
    public boolean routing_local_toggle = false;
    public boolean routing_adr_toggle = false;
    public boolean alert = false;

    public TimerTask timerTask;

    public File file_mkd_dolar;
    public File file_mkd_local;
    public File file_mkd_adr;
    public File file_routing_local;
    public File file_routing_adr;

    public FileInputStream inputStream_mkd_dolar;
    public FileInputStream inputStream_mkd_local;
    public FileInputStream inputStream_mkd_adr;
    public FileInputStream inputStream_routing_local;
    public FileInputStream inputStream_routing_adr;


    public void fileReader(boolean dolar, boolean mLocal, boolean mAdr, boolean rLocal, boolean rAdr) {

        try {

            if(dolar) {
                inputStream_mkd_dolar = new FileInputStream(file_mkd_dolar);
            }

            if(mLocal) {
                inputStream_mkd_local = new FileInputStream(file_mkd_local);
            }

            if(mAdr) {
                inputStream_mkd_adr = new FileInputStream(file_mkd_adr);
            }

            if(rLocal) {
                inputStream_routing_local = new FileInputStream(file_routing_local);
            }

            if(rAdr) {
                inputStream_routing_adr = new FileInputStream(file_routing_adr);
            }

        }catch (Exception e){
            Helper.exception(e);
        }

    }

    public void iniziale(boolean dolar, boolean mLocal, boolean mAdr, boolean rLocal, boolean rAdr) throws Exception {

        final double finalTimer_initial = this.time;
        stopTimer();

        timerTask = new TimerTask(){

            public void run() {

                if(finalTimer_initial != time) {

                    try {
                        iniziale(dolar, mLocal, mAdr, rLocal, rAdr);
                    }catch (Exception e){
                        Helper.exception(e);
                    }
                }

                if (mkd_dolar_toggle && dolar) {
                    Controller.dispatchEvent(new ReadFromDolarEvent(this, nameAlgo, mkd_dolar, inputStream_mkd_dolar));
                }

                if (mkd_local_toggle && mLocal) {
                    Controller.dispatchEvent(new ReadLogMkdLocalEvent(this, nameAlgo, mkd_local, inputStream_mkd_local));
                }

                if (mkd_adr_toggle && mAdr) {
                    Controller.dispatchEvent(new ReadLogMkdAdrEvent(this, nameAlgo, mkd_adr, inputStream_mkd_adr));
                }

                if (routing_local_toggle && rLocal) {
                    Controller.dispatchEvent(new ReadLogRoutingLocalEvent(this, nameAlgo, routing_local, inputStream_routing_local));
                }

                if (routing_adr_toggle && rAdr) {
                    Controller.dispatchEvent(new ReadlogRoutingAdrEvent(this, nameAlgo, routing_adr, inputStream_routing_adr));
                }

            }

        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1, (int) this.time * 1000);

    }

    public void stopTimer() {

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

    }


    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}