package com.larrainvial.logviwer;

import com.larrainvial.logviwer.model.ModelHardDisk;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.PropertiesFile;
import com.larrainvial.logviwer.model.ModelProcess;
import com.larrainvial.sellside.orders.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;
import quickfix.SessionID;
import quickfix.SocketAcceptor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class  Repository {

    private static Logger logger = Logger.getLogger(Repository.class.getName());

    public static Stage primaryStage;
    public static Scene scene;
    public static TabPane tabPanePrincipalTabPanel;
    public static FXMLLoader rootLayout_Loader = new FXMLLoader();
    public static FXMLLoader principalTabPanel_Loader = new FXMLLoader();
    public static HashMap<String, Algo> strategy = new HashMap<String, Algo>();
    public static PropertiesFile logviewer;
    public static String locationPath;
    public static PropertiesFile killProcess;
    public static boolean exception = true;
    public static LinkedHashMap<String, ModelProcess> coreStrategy = new LinkedHashMap<String, ModelProcess>();
    public static String year = new SimpleDateFormat("yyyy/MM/dd").format(new Date()).replace("/", "");
    public static String nameFileQuickFix;
    public static String log4j;
    public static NodeList nodeList;

    public static com.larrainvial.sellside.utils.PropertiesFile buySide;
    public static SocketAcceptor socketAcceptor;
    public static SessionID sessionID;
    public static String socketAcceptPort;
    public static String senderCompID;
    public static String targetCompID;

    public static String date;
    public static String XPUS_NAME;
    public static String XPUS_UUID;
    public static Algo sellside;

    public static HashMap<String, String> UUID = new HashMap<String, String>();
    public static Map<String, Orders> executionWorkOrderBuy = Collections.synchronizedMap(new LinkedHashMap<String, Orders>());
    public static Map<String, Orders> executionWorkOrderSell = Collections.synchronizedMap(new LinkedHashMap<String, Orders>());

    public static Map<String, ModelRoutingData> sellSideMasterListHash = Collections.synchronizedMap(new LinkedHashMap<String, ModelRoutingData>());
    public static TableView<ModelHardDisk> hardDiskTableView;

    public static DecimalFormat formatNumber = new DecimalFormat( "#,###,###,##0");
    public static DecimalFormat formatNumber1 = new DecimalFormat( "#,###,###,##0.0");
    public static DecimalFormat formatNumber2 = new DecimalFormat( "#,###,###,##0.00");
    public static DecimalFormat formatNumber3 = new DecimalFormat( "#,###,###,##0.000");
    public static DecimalFormat formatNumber4 = new DecimalFormat( "#,###,###,##0.0000");

    public static String getID() {
        return Long.toString(java.util.UUID.randomUUID().getMostSignificantBits(), 16);
    }

    public static ObservableList<ModelHardDisk> listServerHardDisk = FXCollections.observableArrayList();

    public static void deleteOrder(){
        executionWorkOrderBuy.clear();
        executionWorkOrderSell.clear();

        if(sellside.sellsideTableView != null){
            sellside.sellsideTableView.getItems().clear();
        }

        logger.info(Constants.ORDERS_DELETED);
    }




}