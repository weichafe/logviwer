package com.larrainvial.logviwer.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.readlog.*;
import com.larrainvial.logviwer.model.ModelHardDisk;
import com.larrainvial.trading.emp.Controller;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class HardDishCheckLinux {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public HardDishCheckLinux(){

        try {

            setServerData();

        } catch (Exception ex){
            ex.printStackTrace();
            logger.error(Level.SEVERE, ex);
        }

    }


    public void setServerData(){

        try {

            Repository.listServerHardDisk.add(0, new ModelHardDisk("everest.larrainvial.com", "root", "larralinuxvial"));
            Repository.listServerHardDisk.add(1, new ModelHardDisk("aconcagua.larrainvial.com", "root", "larralinuxvial"));
            Repository.listServerHardDisk.add(2, new ModelHardDisk("plomo.larrainvial.com", "root", "plomo2014"));
            Repository.listServerHardDisk.add(3, new ModelHardDisk("marmolejo.larrainvial.com", "root", "marmolejo2014"));
            Repository.listServerHardDisk.add(4, new ModelHardDisk("puntasantiago.larrainvial.com", "root", "79.etlv.31"));
            Repository.listServerHardDisk.add(5, new ModelHardDisk("putumayo.larrainvial.com", "root", "79.etlv.31"));
            Repository.listServerHardDisk.add(6, new ModelHardDisk("esmeralda.larrainvial.com", "root", "79.etlv.31"));
            Repository.listServerHardDisk.add(7, new ModelHardDisk("desconsuelo.larrainvial.com", "root", "79.etlv.31"));
            Repository.listServerHardDisk.add(8, new ModelHardDisk("catedral.larrainvial.com", "root", "79.etlv.31"));
            Repository.listServerHardDisk.add(9, new ModelHardDisk("parsifal.larrainvial.com", "root", "79.etlv.31"));

            for (ModelHardDisk modelHardDisk : Repository.listServerHardDisk) {

                JSch jsch = new JSch();
                Session session = null;

                session = jsch.getSession(modelHardDisk.user, modelHardDisk.hostname, 22);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(modelHardDisk.password);
                session.connect();

                ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

                InputStream in = channelExec.getInputStream();

                channelExec.setCommand("df -hT /");
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String linea = null;

                while ((linea = reader.readLine()) != null) {

                    String aux = linea.replace(" ", "|");
                    String aux5 = aux.replace("|||||", "|");
                    String aux4 = aux5.replace("||||", "|");
                    String aux3 = aux4.replace("|||", "|");
                    String aux2 = aux3.replace("||", "|");
                    String[] resultado = aux2.split("\\|");

                    try {

                        modelHardDisk.disponible = resultado[4].trim();
                        modelHardDisk.uso = resultado[5].trim();
                        modelHardDisk.usados = resultado[3].trim();
                        modelHardDisk.montado = resultado[6].trim();
                        modelHardDisk.tamano = resultado[2].trim();

                    } catch (Exception ex) {

                    }

                }

                channelExec.disconnect();
                session.disconnect();
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
