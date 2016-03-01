package com.larrainvial.logviwer.model;


public class ModelHardDisk {

    public String tamano;
    public String usados;
    public String disponible;
    public String uso;
    public String montado;

    public String hostname;
    public String user;
    public String password;


    public ModelHardDisk(String hostname, String user, String password){
        this.hostname = hostname;
        this.user = user;
        this.password = password;
    }






}
