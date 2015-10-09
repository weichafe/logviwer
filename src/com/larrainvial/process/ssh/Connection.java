package com.larrainvial.process.ssh;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.larrainvial.process.vo.ServerVO;
import org.apache.log4j.Logger;

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



*/



