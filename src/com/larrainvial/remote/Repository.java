package com.larrainvial.remote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class Repository {

    public static Socket socket;
    public static DataOutputStream sendMessage;
    public static DataInputStream receivedMessage;

}
