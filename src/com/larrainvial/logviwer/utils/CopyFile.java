package com.larrainvial.logviwer.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.model.ModelXml;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class CopyFile {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public final String hostname = "catedral.larrainvial.com";
    public final String username = "usrlv_etrading";
    public final String password = "79.etlv.31";
    public final String dir = "C:\\Logviewer\\";
    public TimerTask timerTask;


    public CopyFile(ModelXml xmlVO) {
        startFile(xmlVO);
        copyFile(xmlVO);
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

            logger.info("from " + copyFrom + " to" + copyTo);

        } catch (JSchException ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        } catch (SftpException ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }


    public synchronized  void copyFile(ModelXml xmlVO) {

        try {

            timerTask = new TimerTask() {

                public void run() {
                    startFile(xmlVO);
                }

            };

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 300000, 1);

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void startFile(ModelXml xmlVO){

        xmlVO.locationLinux = xmlVO.location;
        xmlVO.location = dir + "\\" + xmlVO.nameAlgo + "\\";


        if (xmlVO.booleanDolar){
            String copyFrom = xmlVO.locationLinux + xmlVO.mkd_dolar + Repository.year + ".log";
            String copyTo = xmlVO.location + xmlVO.mkd_dolar + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
        }

        if (xmlVO.booleanMLocal){
            String copyFrom = xmlVO.locationLinux + xmlVO.mkd_local + Repository.year + ".log";
            String copyTo = xmlVO.location + xmlVO.mkd_local + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
        }

        if (xmlVO.booleanMAdr){
            String copyFrom = xmlVO.locationLinux + xmlVO.mkd_nyse + Repository.year + ".log";
            String copyTo = xmlVO.location + xmlVO.mkd_nyse + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
        }

        if (xmlVO.booleanRLocal){
            String copyFrom = xmlVO.locationLinux + xmlVO.routing_local + Repository.year + ".log";
            String copyTo = xmlVO.location + xmlVO.routing_local + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
        }

        if (xmlVO.booleanRAdr){
            String copyFrom = xmlVO.locationLinux + xmlVO.routing_nyse + Repository.year + ".log";
            String copyTo = xmlVO.location + xmlVO.routing_nyse + Repository.year + ".log";
            copyFile(copyFrom, copyTo);
        }

    }


}
