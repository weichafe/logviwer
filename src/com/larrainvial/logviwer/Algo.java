package com.larrainvial.logviwer;

import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Timer;
import java.util.TimerTask;

public class Algo {

    private String nameAlgo;
    private String mkd_dolar;
    private String mkd_local;
    private String mkd_adr;
    private String routing_local;
    private String routing_adr;

    private String mkd_dolar_file = null;
    private String mkd_local_file = null;
    private String mkd_adr_file = null;
    private String routing_local_file = null;
    private String routing_adr_file = null;

    private double time;

    private FXMLLoader mkd_dolar_loader = new FXMLLoader();
    private FXMLLoader mkd_local_loader = new FXMLLoader();
    private FXMLLoader mkd_adr_loader = new FXMLLoader();
    private FXMLLoader routing_adr_loader = new FXMLLoader();
    private FXMLLoader routing_local_loader = new FXMLLoader();

    private ObservableList<ModelMarketData> dolarMasterList = FXCollections.observableArrayList();
    private ObservableList<ModelMarketData> dolarFilterList = FXCollections.observableArrayList();

    private ObservableList<ModelMarketData> mkdAdrMasterList = FXCollections.observableArrayList();
    private ObservableList<ModelMarketData> mkdAdrFilterList = FXCollections.observableArrayList();

    private ObservableList<ModelMarketData> mkdLocalMasterList = FXCollections.observableArrayList();
    private ObservableList<ModelMarketData> mkdLocalFilterList = FXCollections.observableArrayList();

    private ObservableList<ModelRoutingData> routingAdrMasterList = FXCollections.observableArrayList();
    private ObservableList<ModelRoutingData> routingAdrFilterList = FXCollections.observableArrayList();

    private ObservableList<ModelRoutingData> routingLocalMasterList = FXCollections.observableArrayList();
    private ObservableList<ModelRoutingData> routingLocalFilterList = FXCollections.observableArrayList();

    private ObservableList<ModelRoutingData> routingBlotterMasterLsit = FXCollections.observableArrayList();
    private ObservableList<ModelRoutingData> routingBlotterFilterLsit = FXCollections.observableArrayList();

    private TableView<ModelMarketData> mkd_dolar_tableView;
    private TableView<ModelMarketData> mkd_adr_tableView;
    private TableView<ModelMarketData> mkd_local_tableView;
    private TableView<ModelRoutingData> routing_adr_tableView;
    private TableView<ModelRoutingData> routing_local_tableView;

    private TimerTask timerTask;

    private File file_mkd_dolar;
    private File file_mkd_local;
    private File file_mkd_adr;
    private File file_routing_local;
    private File file_routing_adr;

    private BufferedReader file_routing_adrBufferedReader;
    private BufferedReader file_mkd_dolar_adrBufferedReader;
    private BufferedReader file_routing_localBufferedReader;
    private BufferedReader file_mkd_localBufferedReader;
    private BufferedReader file_mkd_adrBufferedReader;


    //ARRAY LIST DOLAR Y FILTROS



    public void fileReader() throws Exception {

        file_mkd_dolar_adrBufferedReader = new BufferedReader(new FileReader(file_mkd_dolar));
        file_mkd_localBufferedReader = new BufferedReader(new FileReader(file_mkd_local));
        file_mkd_adrBufferedReader = new BufferedReader(new FileReader(file_mkd_adr));
        file_routing_adrBufferedReader = new BufferedReader(new FileReader(file_routing_adr));
        file_routing_localBufferedReader = new BufferedReader(new FileReader(file_routing_local));

    }

    public void iniziale() throws Exception {

        final double finalTimer_initial = this.getTime();
        stopTimer();

        timerTask = new TimerTask(){

            public void run() {

                if(finalTimer_initial != getTime()) {
                    try {
                        iniziale();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


                Controller.dispatchEvent(new ReadLogEvent(this, nameAlgo, mkd_dolar, file_mkd_dolar_adrBufferedReader));
                Controller.dispatchEvent(new ReadLogEvent(this, nameAlgo, mkd_local, file_mkd_localBufferedReader));
                Controller.dispatchEvent(new ReadLogEvent(this, nameAlgo, mkd_adr, file_mkd_adrBufferedReader));
                Controller.dispatchEvent(new ReadLogEvent(this, nameAlgo, routing_local, file_routing_localBufferedReader));
                Controller.dispatchEvent(new ReadLogEvent(this, nameAlgo, routing_adr, file_routing_adrBufferedReader));

            }


        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1, (int) this.getTime() * 1000);
    }

    public void stopTimer() {

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

    }



    /*
    Getter and Setters
     */


    public ObservableList<ModelMarketData> getMkdAdrFilterList() {
        return mkdAdrFilterList;
    }

    public void setMkdAdrFilterList(ObservableList<ModelMarketData> mkdAdrFilterList) {
        this.mkdAdrFilterList = mkdAdrFilterList;
    }

    public ObservableList<ModelMarketData> getMkdLocalFilterList() {
        return mkdLocalFilterList;
    }

    public void setMkdLocalFilterList(ObservableList<ModelMarketData> mkdLocalFilterList) {
        this.mkdLocalFilterList = mkdLocalFilterList;
    }

    public ObservableList<ModelRoutingData> getRoutingAdrFilterList() {
        return routingAdrFilterList;
    }

    public void setRoutingAdrFilterList(ObservableList<ModelRoutingData> routingAdrFilterList) {
        this.routingAdrFilterList = routingAdrFilterList;
    }

    public ObservableList<ModelRoutingData> getRoutingLocalFilterList() {
        return routingLocalFilterList;
    }

    public void setRoutingLocalFilterList(ObservableList<ModelRoutingData> routingLocalFilterList) {
        this.routingLocalFilterList = routingLocalFilterList;
    }

    public ObservableList<ModelRoutingData> getRoutingBlotterMasterLsit() {
        return routingBlotterMasterLsit;
    }

    public void setRoutingBlotterMasterLsit(ObservableList<ModelRoutingData> routingBlotterMasterLsit) {
        this.routingBlotterMasterLsit = routingBlotterMasterLsit;
    }

    public ObservableList<ModelRoutingData> getRoutingBlotterFilterLsit() {
        return routingBlotterFilterLsit;
    }

    public void setRoutingBlotterFilterLsit(ObservableList<ModelRoutingData> routingBlotterFilterLsit) {
        this.routingBlotterFilterLsit = routingBlotterFilterLsit;
    }

    public ObservableList<ModelMarketData> getDolarFilterList() {
        return dolarFilterList;
    }

    public void setDolarFilterList(ObservableList<ModelMarketData> dolarFilterList) {
        this.dolarFilterList = dolarFilterList;
    }


    public File getFile_mkd_dolar() {
        return file_mkd_dolar;
    }

    public void setFile_mkd_dolar(File file_mkd_dolar) {
        this.file_mkd_dolar = file_mkd_dolar;
    }

    public File getFile_mkd_local() {
        return file_mkd_local;
    }

    public void setFile_mkd_local(File file_mkd_local) {
        this.file_mkd_local = file_mkd_local;
    }

    public File getFile_mkd_adr() {
        return file_mkd_adr;
    }

    public void setFile_mkd_adr(File file_mkd_adr) {
        this.file_mkd_adr = file_mkd_adr;
    }

    public File getFile_routing_local() {
        return file_routing_local;
    }

    public void setFile_routing_local(File file_routing_local) {
        this.file_routing_local = file_routing_local;
    }

    public File getFile_routing_adr() {
        return file_routing_adr;
    }

    public void setFile_routing_adr(File file_routing_adr) {
        this.file_routing_adr = file_routing_adr;
    }

    public TableView<ModelMarketData> getMkd_dolar_tableView() {
        return mkd_dolar_tableView;
    }

    public void setMkd_dolar_tableView(TableView<ModelMarketData> mkd_dolar_tableView) {
        this.mkd_dolar_tableView = mkd_dolar_tableView;
    }

    public TableView<ModelMarketData> getMkd_adr_tableView() {
        return mkd_adr_tableView;
    }

    public void setMkd_adr_tableView(TableView<ModelMarketData> mkd_adr_tableView) {
        this.mkd_adr_tableView = mkd_adr_tableView;
    }

    public TableView<ModelMarketData> getMkd_local_tableView() {
        return mkd_local_tableView;
    }

    public void setMkd_local_tableView(TableView<ModelMarketData> mkd_local_tableView) {
        this.mkd_local_tableView = mkd_local_tableView;
    }

    public TableView<ModelRoutingData> getRouting_adr_tableView() {
        return routing_adr_tableView;
    }

    public void setRouting_adr_tableView(TableView<ModelRoutingData> routing_adr_tableView) {
        this.routing_adr_tableView = routing_adr_tableView;
    }

    public TableView<ModelRoutingData> getRouting_local_tableView() {
        return routing_local_tableView;
    }

    public void setRouting_local_tableView(TableView<ModelRoutingData> routing_local_tableView) {
        this.routing_local_tableView = routing_local_tableView;
    }

    public ObservableList<ModelMarketData> getDolarMasterList() {
        return dolarMasterList;
    }

    public void setDolarMasterList(ObservableList<ModelMarketData> dolarMasterList) {
        this.dolarMasterList = dolarMasterList;
    }

    public ObservableList<ModelMarketData> getMkdAdrMasterList() {
        return mkdAdrMasterList;
    }

    public void setMkdAdrMasterList(ObservableList<ModelMarketData> mkdAdrMasterList) {
        this.mkdAdrMasterList = mkdAdrMasterList;
    }

    public ObservableList<ModelMarketData> getMkdLocalMasterList() {
        return mkdLocalMasterList;
    }

    public void setMkdLocalMasterList(ObservableList<ModelMarketData> mkdLocalMasterList) {
        this.mkdLocalMasterList = mkdLocalMasterList;
    }

    public ObservableList<ModelRoutingData> getRoutingAdrMasterList() {
        return routingAdrMasterList;
    }

    public void setRoutingAdrMasterList(ObservableList<ModelRoutingData> routingAdrMasterList) {
        this.routingAdrMasterList = routingAdrMasterList;
    }

    public ObservableList<ModelRoutingData> getRoutingLocalMasterList() {
        return routingLocalMasterList;
    }

    public void setRoutingLocalMasterList(ObservableList<ModelRoutingData> routingLocalMasterList) {
        this.routingLocalMasterList = routingLocalMasterList;
    }

    public String getNameAlgo() {
        return nameAlgo;
    }

    public void setNameAlgo(String nameAlgo) {
        this.nameAlgo = nameAlgo;
    }

    public String getMkd_dolar() {
        return mkd_dolar;
    }

    public void setMkd_dolar(String mkd_dolar) {
        this.mkd_dolar = mkd_dolar;
    }

    public String getMkd_local() {
        return mkd_local;
    }

    public void setMkd_local(String mkd_local) {
        this.mkd_local = mkd_local;
    }

    public String getMkd_adr() {
        return mkd_adr;
    }

    public void setMkd_adr(String mkd_adr) {
        this.mkd_adr = mkd_adr;
    }

    public String getRouting_local() {
        return routing_local;
    }

    public void setRouting_local(String routing_local) {
        this.routing_local = routing_local;
    }

    public String getRouting_adr() {
        return routing_adr;
    }

    public void setRouting_adr(String routing_adr) {
        this.routing_adr = routing_adr;
    }

    public String getMkd_dolar_file() {
        return mkd_dolar_file;
    }

    public void setMkd_dolar_file(String mkd_dolar_file) {
        this.mkd_dolar_file = mkd_dolar_file;
    }

    public String getMkd_local_file() {
        return mkd_local_file;
    }

    public void setMkd_local_file(String mkd_local_file) {
        this.mkd_local_file = mkd_local_file;
    }

    public String getMkd_adr_file() {
        return mkd_adr_file;
    }

    public void setMkd_adr_file(String mkd_adr_file) {
        this.mkd_adr_file = mkd_adr_file;
    }

    public String getRouting_local_file() {
        return routing_local_file;
    }

    public void setRouting_local_file(String routing_local_file) {
        this.routing_local_file = routing_local_file;
    }

    public String getRouting_adr_file() {
        return routing_adr_file;
    }

    public void setRouting_adr_file(String routing_adr_file) {
        this.routing_adr_file = routing_adr_file;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public FXMLLoader getMkd_dolar_loader() {
        return mkd_dolar_loader;
    }

    public void setMkd_dolar_loader(FXMLLoader mkd_dolar_loader) {
        this.mkd_dolar_loader = mkd_dolar_loader;
    }

    public FXMLLoader getMkd_local_loader() {
        return mkd_local_loader;
    }

    public void setMkd_local_loader(FXMLLoader mkd_local_loader) {
        this.mkd_local_loader = mkd_local_loader;
    }

    public FXMLLoader getMkd_adr_loader() {
        return mkd_adr_loader;
    }

    public void setMkd_adr_loader(FXMLLoader mkd_adr_loader) {
        this.mkd_adr_loader = mkd_adr_loader;
    }

    public FXMLLoader getRouting_adr_loader() {
        return routing_adr_loader;
    }

    public void setRouting_adr_loader(FXMLLoader routing_adr_loader) {
        this.routing_adr_loader = routing_adr_loader;
    }

    public FXMLLoader getRouting_local_loader() {
        return routing_local_loader;
    }

    public void setRouting_local_loader(FXMLLoader routing_local_loader) {
        this.routing_local_loader = routing_local_loader;
    }

}