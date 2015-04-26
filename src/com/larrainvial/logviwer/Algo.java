package com.larrainvial.logviwer;


import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class Algo {

    private String nameAlgo;
    private String mkd_dolar;
    private String mkd_local;
    private String mkd_adr;
    private String routing_local;
    private String routing_adr;

    private String mkd_dolar_file;
    private String mkd_local_file;
    private String mkd_adr_file;
    private String routing_local_file;
    private String routing_adr_file;

    private double time;

    private FXMLLoader mkd_dolar_loader = new FXMLLoader();
    private FXMLLoader mkd_local_loader = new FXMLLoader();
    private FXMLLoader mkd_adr_loader = new FXMLLoader();
    private FXMLLoader routing_adr_loader = new FXMLLoader();
    private FXMLLoader routing_local_loader = new FXMLLoader();

    private ObservableList<ModelMarketData> mkd_dolar_arrayList = FXCollections.observableArrayList();
    private ObservableList<ModelMarketData> mkd_local_arrayList = FXCollections.observableArrayList();
    private ObservableList<ModelMarketData> mkd_adr_arrayList = FXCollections.observableArrayList();
    private ObservableList<ModelMarketData> routing_local_arrayList = FXCollections.observableArrayList();
    private ObservableList<ModelRoutingData> routing_adr_arrayList = FXCollections.observableArrayList();

    private ObservableList<ModelMarketData> dolarList = FXCollections.observableArrayList();
    private ObservableList<ModelMarketData> mkd_adr_list = FXCollections.observableArrayList();
    private ObservableList<ModelMarketData> mkd_local_list = FXCollections.observableArrayList();
    private ObservableList<ModelRoutingData> routing_local_list = FXCollections.observableArrayList();
    private ObservableList<ModelRoutingData> routing_adr_list = FXCollections.observableArrayList();
    private ObservableList<ModelRoutingData> routing_blotter_list = FXCollections.observableArrayList();

    private TableView<ModelMarketData> mkd_dolar_tableView;
    private TableView<ModelMarketData> mkd_adr_tableView;
    private TableView<ModelMarketData> mkd_local_tableView;
    private TableView<ModelRoutingData> routing_adr_tableView;
    private TableView<ModelRoutingData> routing_local_tableView;

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

    public ObservableList<ModelMarketData> getDolarList() {
        return dolarList;
    }

    public void setDolarList(ObservableList<ModelMarketData> dolarList) {
        this.dolarList = dolarList;
    }

    public ObservableList<ModelMarketData> getMkd_adr_list() {
        return mkd_adr_list;
    }

    public void setMkd_adr_list(ObservableList<ModelMarketData> mkd_adr_list) {
        this.mkd_adr_list = mkd_adr_list;
    }

    public ObservableList<ModelMarketData> getMkd_local_list() {
        return mkd_local_list;
    }

    public void setMkd_local_list(ObservableList<ModelMarketData> mkd_local_list) {
        this.mkd_local_list = mkd_local_list;
    }

    public ObservableList<ModelRoutingData> getRouting_local_list() {
        return routing_local_list;
    }

    public void setRouting_local_list(ObservableList<ModelRoutingData> routing_local_list) {
        this.routing_local_list = routing_local_list;
    }

    public ObservableList<ModelRoutingData> getRouting_adr_list() {
        return routing_adr_list;
    }

    public void setRouting_adr_list(ObservableList<ModelRoutingData> routing_adr_list) {
        this.routing_adr_list = routing_adr_list;
    }

    public ObservableList<ModelRoutingData> getRouting_blotter_list() {
        return routing_blotter_list;
    }

    public void setRouting_blotter_list(ObservableList<ModelRoutingData> routing_blotter_list) {
        this.routing_blotter_list = routing_blotter_list;
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

    public ObservableList<ModelMarketData> getMkd_dolar_arrayList() {
        return mkd_dolar_arrayList;
    }

    public void setMkd_dolar_arrayList(ObservableList<ModelMarketData> mkd_dolar_arrayList) {
        this.mkd_dolar_arrayList = mkd_dolar_arrayList;
    }

    public ObservableList<ModelMarketData> getMkd_local_arrayList() {
        return mkd_local_arrayList;
    }

    public void setMkd_local_arrayList(ObservableList<ModelMarketData> mkd_local_arrayList) {
        this.mkd_local_arrayList = mkd_local_arrayList;
    }

    public ObservableList<ModelMarketData> getMkd_adr_arrayList() {
        return mkd_adr_arrayList;
    }

    public void setMkd_adr_arrayList(ObservableList<ModelMarketData> mkd_adr_arrayList) {
        this.mkd_adr_arrayList = mkd_adr_arrayList;
    }

    public ObservableList<ModelMarketData> getRouting_local_arrayList() {
        return routing_local_arrayList;
    }

    public void setRouting_local_arrayList(ObservableList<ModelMarketData> routing_local_arrayList) {
        this.routing_local_arrayList = routing_local_arrayList;
    }

    public ObservableList<ModelRoutingData> getRouting_adr_arrayList() {
        return routing_adr_arrayList;
    }

    public void setRouting_adr_arrayList(ObservableList<ModelRoutingData> routing_adr_arrayList) {
        this.routing_adr_arrayList = routing_adr_arrayList;
    }
}
