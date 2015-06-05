package com.larrainvial;

import com.larrainvial.client.Client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {

    public static void main(String[] args) {

        try {

            Client cli = new Client(); //Se crea el cliente

            System.out.println("Iniciando cliente\n");
            cli.startClient(); //Se inicia el cliente

        } catch (UnknownHostException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
