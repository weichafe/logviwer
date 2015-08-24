package com.larrainvial.latency.utils;

import com.larrainvial.latency.Algo;
import com.larrainvial.latency.vo.LatencyVO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    private static DateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
    private static SimpleDateFormat QFfmt = new SimpleDateFormat("yyyyMMdd-HH:mm:ss.SSS");    
    public static LatencyVO returnLatencyVO(String line, Algo algoTemp) {
    
        try {

            LatencyVO latencyVO = new LatencyVO();

            String tempclOrdID = "";
            String temptransactionTime = "";
            String tempsymbol = "";
            String tempside = "";
            String tempmsgtype = "";

            if (line.indexOf("11=") == -1) return null;
            else tempclOrdID = line.split("11=")[1];

            tempmsgtype = line.split("35=")[1];
            latencyVO.msgType = tempmsgtype.substring(0, tempmsgtype.indexOf("\u0001"));

            if (algoTemp.typeEngine == "QF")
                temptransactionTime =  line.substring(0,21);
            else
                temptransactionTime = line.split("\\|")[1];



            if (latencyVO.msgType.equals("D") || latencyVO.msgType.equals("G") || latencyVO.msgType.equals("F")) {

                tempsymbol = line.split("55=")[1];
                tempside = line.split("54=")[1];

                latencyVO.nameFile = algoTemp.nameFile;

                if (algoTemp.typeEngine == "QF")
                    latencyVO.transactionTimeStart = getTransactionTime(temptransactionTime);
                if (algoTemp.typeEngine == "TSM")
                    latencyVO.transactionTimeStart = Long.valueOf(temptransactionTime);

                latencyVO.clOrdID = tempclOrdID.substring(0, tempclOrdID.indexOf("\u0001"));
                latencyVO.symbol = tempsymbol.substring(0, tempsymbol.indexOf("\u0001"));
                latencyVO.side = tempside.substring(0, tempside.indexOf("\u0001"));
                latencyVO.engine = algoTemp.typeEngine;

                algoTemp.hashLatencyVO.put(latencyVO.clOrdID, latencyVO);

            } else {

                tempclOrdID = tempclOrdID.substring(0, tempclOrdID.indexOf("\u0001"));

                if (tempclOrdID.equals("")) return null;

                LatencyVO latency = algoTemp.hashLatencyVO.get(tempclOrdID);

                if (latency == null) return null;

                if (latency.transactionTimeEnd > 0) return null;

                if (algoTemp.typeEngine == "QF")
                    latency.transactionTimeEnd = getTransactionTime(temptransactionTime);

                if (algoTemp.typeEngine == "TSM")
                    latency.transactionTimeEnd = Long.valueOf(temptransactionTime);


                latency.timeLatency = latency.transactionTimeEnd - latency.transactionTimeStart;

                return latency;
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }



    public static Long getTransactionTime(String times) {

        try {

            SimpleDateFormat QFfmtHelper = new SimpleDateFormat("yyyyMMdd-HH:mm:ss.SSS");
            return Long.valueOf(QFfmtHelper.parse(times).getTime());

        } catch(Exception e) {
            System.out.print("Error fecha->" + times);
        }

        return 0L;
    }

    public static String getTimestampStringFormat(long time){        
        return formater.format(new Date(time));
    }

    
    public static void main(String[] args) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

        try {

            Date hoy = new Date();

            String fechaDesde = fmt.format(hoy) + "-00:00:00.000";
            String fechaHasta = fmt.format(hoy) + "-23:59:59.999";
            
            System.out.println("fechaDesde: " + fechaDesde + " fechaHasta: " + fechaHasta);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

   }
}
