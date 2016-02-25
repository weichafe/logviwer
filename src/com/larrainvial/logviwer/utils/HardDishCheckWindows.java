package com.larrainvial.logviwer.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.model.ModelHardDisk;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class HardDishCheckWindows {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public HardDishCheckWindows(){

        try {

            Repository.listServerHardDisk.add(new ModelHardDisk("172.16.240.85", "vnazar", "1020304050"));


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

                    String aux = linea.replace(" ","|");
                    String aux5 = aux.replace("|||||","|");
                    String aux4 = aux5.replace("||||","|");
                    String aux3 = aux4.replace("|||","|");
                    String aux2 = aux3.replace("||", "|");
                    String[] resultado = aux2.split("\\|");

                    try {

                        modelHardDisk.disponible = resultado[4].trim();
                        modelHardDisk.uso = resultado[5].trim();
                        modelHardDisk.usados = resultado[3].trim();
                        modelHardDisk.montado = resultado[6].trim();
                        modelHardDisk.tamano = resultado[2].trim();

                    } catch (Exception ex){
                        logger.info("Error controlado");
                        logger.error(Level.SEVERE, ex);
                    }

                }

                channelExec.disconnect();
                session.disconnect();
            }

        } catch (Exception ex){
            ex.printStackTrace();
            logger.error(Level.SEVERE, ex);
        }

    }


}
