package com.larrainvial.logviwer.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.model.ModelXml;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class CopyFileXbog {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final String hostname = "catedral.larrainvial.com";
    private final String username = "usrlv_etrading";
    private final String password = "79.etlv.31";
    private final String dir = "C:\\Logviewer\\";
    private Algo algo;
    private String copyFrom;
    private String copyTo;


    public CopyFileXbog(Algo algo) {

        try {

            this.algo = algo;
            copyFrom = algo.modelXml.location;
            copyTo = dir + "\\" + algo.modelXml.nameAlgo + "\\";
            algo.modelXml.location = copyTo;

            File folder = new File(copyTo);

            if(!folder.exists()){
                folder.mkdir();
            }

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }
    }


    public  void copyFile(String copyFrom, String copyTo) {

        try {

            JSch jsch = new JSch();
            Session session = null;

            session = jsch.getSession(username, hostname, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            sftpChannel.get(copyFrom, copyTo);

            sftpChannel.exit();
            session.disconnect();

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }
    }

    public void copyDolarFile() {

        try {

            String copyFrom = this.copyFrom + algo.modelXml.mkd_dolar + Repository.year + ".log";
            String copyTo = this.copyTo + algo.modelXml.mkd_dolar + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
            algo.blokedDolar = true;

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }
    }

    public void copyMkdLocalFile() {

        try {

            String copyFrom = this.copyFrom + algo.modelXml.mkd_local + Repository.year + ".log";
            String copyTo = this.copyTo + algo.modelXml.mkd_local + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
            algo.blokedMkdLocal = true;

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }
    }

    public void copyMkdAdrFile() {

        try {

            String copyFrom = this.copyFrom + algo.modelXml.mkd_nyse + Repository.year + ".log";
            String copyTo = this.copyTo + algo.modelXml.mkd_nyse + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
            algo.blokedMkdAdr = true;

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }
    }

    public void copyRoutingLocalFile() {

        try {

            String copyFrom = this.copyFrom + algo.modelXml.routing_local + Repository.year + ".log";
            String copyTo = this.copyTo + algo.modelXml.routing_local + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
            algo.blokedRoutingLocal = true;

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }
    }

    public void copyRoutingAdrFile() {

        try {

            String copyFrom = this.copyFrom + algo.modelXml.routing_nyse + Repository.year + ".log";
            String copyTo = this.copyTo + algo.modelXml.routing_nyse + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
            algo.blokedRoutingAdr = true;

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }


}
