package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelPositions;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;

public class ExportExcel {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    public String userHomeFolder;
    public File textFile;


    public void positions(Algo algo){

        try {

            userHomeFolder = System.getProperty("user.home")+ "/Desktop";
            textFile = new File(userHomeFolder, algo.nameAlgo + " Positions" + ".csv");

            FileWriter escribir = new FileWriter(textFile, true);

            for (int i = 0; i < algo.panelPositionsTableView.getColumns().size(); i++) {
                escribir.write(algo.panelPositionsTableView.getColumns().get(i).getText() + ",");
            }

            escribir.write("\n");

            for(String x : algo.positionsMasterListHash.keySet()){

                ModelPositions modelPositions = algo.positionsMasterListHash.get(x);

                escribir.write(modelPositions.symbolLocal      + ","  + modelPositions.symbolAdr          + "," +
                               modelPositions.qtyBuyLocalRatio + ", " + modelPositions.qtySellAdr         + "," +
                               modelPositions.differenceInflow + ","  + modelPositions.qtySellLocalRatio  + "," +
                               modelPositions.qtyBuyAdr        + ","  + modelPositions.differenceflowback + "," +
                               modelPositions.qtyBuyLocal      + ","  + modelPositions.qtySellLocal       + "," +
                               modelPositions.ratio +
                               "\n"
                );

            }

            escribir.close();

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }


    }

    public void lastPrice(Algo algo){

        try {

            userHomeFolder = System.getProperty("user.home")+ "/Desktop";
            textFile = new File(userHomeFolder, algo.nameAlgo + " Last Price" + ".csv");

            FileWriter escribir = new FileWriter(textFile, true);

            for (int i = 0; i < algo.lastPriceTableView.getColumns().size(); i++) {
                escribir.write(algo.lastPriceTableView.getColumns().get(i).getText() + ",");
            }

            escribir.write("\n");

            for(String x : algo.lastPriceMasterListHash.keySet()){

                ModelMarketData modelMarketData = algo.lastPriceMasterListHash.get(x);

                escribir.write(modelMarketData.hour        + ","  + modelMarketData.messageByType + "," +
                               modelMarketData.symbol      + "," + modelMarketData.buyPx          + "," +
                               modelMarketData.buyQty      + ","  + modelMarketData.sellPx        + "," +
                               modelMarketData.sellQty     + ","  + modelMarketData.closePx       + "," +
                               modelMarketData.tradeAmount + "\n"
                );

            }

            escribir.close();

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }


    }
}
