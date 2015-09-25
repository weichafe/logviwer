package com.larrainvial.killprocess.ssh;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.larrainvial.killprocess.vo.ServerVO;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Connection {

    private static Logger logger = Logger.getLogger(Connection.class.getName());

    public Session connectServer(ServerVO serverVO) {

        Session session = null;

        try {

            JSch jSSH = new JSch();
            session = jSSH.getSession(serverVO.usuario, serverVO.url, 22);
            UserInfo ui = new SesionUsuario(serverVO.pass, null);
            session.setUserInfo(ui);
            session.setPassword(serverVO.pass);

        } catch (Exception e) {
            logger.error(e);
        }

        return session;

    }
}
/*
            session.connect();

            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

            InputStream in = channelExec.getInputStream();

            channelExec.setCommand("top -bn 1");
            channelExec.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String linea = null;
            int index = 0;

            while ((linea = reader.readLine()) != null) {
                System.out.println(++index + " : " + linea);
            }

            channelExec.disconnect();


*/



