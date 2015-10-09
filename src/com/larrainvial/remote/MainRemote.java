package com.larrainvial.remote;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MainRemote {

    public static void main(String[] args) {

        Socket s;
        PrintStream p;
        BufferedReader b;

        String host = "localhost";
        int port = 9999;
        String respuesta;


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {


            s = new Socket(host,port);

            p = new PrintStream(s.getOutputStream());
            b = new BufferedReader ( new InputStreamReader ( s.getInputStream() ) );

            while ( true ) {

                System.out.print("Mensaje a enviar: ");

                //Escribo en el canal de escritura del socket
                p.println( in.readLine() );

                //Espero la respuesta por el canal de lectura
                respuesta = b.readLine();
                System.out.println(respuesta);

                if ( respuesta.equals("by")) {
                    break;
                }
            }

            p.close();
            b.close();
            s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


