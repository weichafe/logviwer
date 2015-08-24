package com.larrainvial.latency;


import com.larrainvial.latency.utils.FileRepository;
import com.larrainvial.latency.utils.PropertiesFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Latency {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        try
        {
            PropertiesFile propertiesFile = new PropertiesFile(args[0]);
            Repository.nameFile = new FileRepository("nameDirectory", propertiesFile.getPropertiesString("RUTATEMP") );
            Repository.FileLog  = propertiesFile.getPropertiesString("RUTALOG");

            if (Repository.nameFile.existFile()){
                Repository.directoryFile = (HashMap<String, String>) Repository.nameFile.readFile();
            }

            String[] DIRECORY_NAMEQF = propertiesFile.getPropertiesString("DIRECTORYQF").toString().split(";");
            String[] DIRECORY_NAMETSM = propertiesFile.getPropertiesString("DIRECTORYTSM").toString().split(";");

            String fechaDesde = propertiesFile.getPropertiesString("FECHA_DESDE");
            String fechaHasta = propertiesFile.getPropertiesString("FECHA_HASTA");

            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

            if(fechaDesde==null || fechaDesde.equals("") || fechaHasta==null || fechaHasta.equals(""))
            {
                Date hoy = new Date();
                fechaDesde = fmt.format(hoy) + "-00:00:00.000";
                fechaHasta = fmt.format(hoy) + "-23:59:59.999";
            }
            else
            {
                fechaDesde = fmt.format(fmt.parse(fechaDesde)) + "-00:00:00.000";
                fechaHasta = fmt.format(fmt.parse(fechaHasta)) + "-23:59:59.999";
            }



            System.out.println("Iniciando ReadFileAdapter con fechaDesde: " + fechaDesde + " fechaHasta: " + fechaHasta);

            // Log QuickFIX
            for (int i=0; i < DIRECORY_NAMEQF.length; i++) {
                new Algo(DIRECORY_NAMEQF[i].toString(),"QF", fechaDesde, fechaHasta);
            }

            // Log TSM
            for (int x=0; x < DIRECORY_NAMETSM.length; x++) {
                new Algo(DIRECORY_NAMETSM[x].toString(),"TSM", fechaDesde, fechaHasta);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
