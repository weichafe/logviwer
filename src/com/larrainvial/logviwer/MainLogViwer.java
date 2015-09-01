package com.larrainvial.logviwer;

import com.larrainvial.logviwer.model.StrategyListWrapper;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.model.Dolar;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Latency;
import com.larrainvial.logviwer.utils.PropertiesFile;
import com.larrainvial.logviwer.vo.StrategyVO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.controlsfx.dialog.Dialogs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.prefs.Preferences;


public class MainLogViwer extends Application {

    private ObservableList<StrategyVO> strategyData = FXCollections.observableArrayList();
    private static Logger log = Logger.getLogger(MainLogViwer.class.getName());
    private String log4jConfPath = "C:\\Program Files (x86)\\LarrainVial\\Logviewer\\Resources\\log4j.properties";



    public void start(Stage primaryStage) {

        try {

            PropertyConfigurator.configure(log4jConfPath);
            Repository.logviewer  = new PropertiesFile("C:\\Program Files (x86)\\LarrainVial\\Logviewer\\Resources\\logviewer.properties");


            Latency.LATENCY_MIN = Integer.valueOf(Repository.logviewer.getPropertiesString("LATENCY_MIN"));
            Latency.LATENCY_MAX = Integer.valueOf(Repository.logviewer.getPropertiesString("LATENCY_MAX"));

            Dolar.VARIACION_CLP = Double.valueOf(Repository.logviewer.getPropertiesString("VARIACION_CLP"));
            Dolar.VARIACION_CAD = Double.valueOf(Repository.logviewer.getPropertiesString("VARIACION_CAD"));
            Dolar.VARIACION_COFX = Double.valueOf(Repository.logviewer.getPropertiesString("VARIACION_COFX"));

            Dolar.CLP = Repository.logviewer.getPropertiesString("CLP");
            Dolar.CAD = Repository.logviewer.getPropertiesString("CAD");
            Dolar.COFX = Repository.logviewer.getPropertiesString("COFX");

            log.info("Inicializando servidor... ");

            Repository.primaryStage = primaryStage;
            Repository.primaryStage.setTitle("Log Viewer");

            Repository.rootLayout_Loader.setLocation(MainLogViwer.class.getResource("view/rootLayout.fxml"));
            BorderPane rootLayout_Loader = (BorderPane) Repository.rootLayout_Loader.load();

            Repository.principalTabPanel_Loader.setLocation(MainLogViwer.class.getResource("view/PrincipalTabPanel.fxml"));
            Repository.tabPanePrincipalTabPanel = (TabPane) Repository.principalTabPanel_Loader.load();
            rootLayout_Loader.setCenter(Repository.tabPanePrincipalTabPanel);

            Repository.scene = new Scene(rootLayout_Loader);
            primaryStage.setScene(Repository.scene);

            primaryStage.show();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.exit(0);
                }
            });

            Helper helper = new Helper();
            helper.createStrategy();

        } catch (Exception e){
            e.printStackTrace();
            Dialog.exception(e);
        }

    }

    public File getPersonFilePath() {

        Preferences prefs = Preferences.userNodeForPackage(MainLogViwer.class);
        String filePath = prefs.get("filePath", null);

        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {

        Preferences prefs = Preferences.userNodeForPackage(MainLogViwer.class);

        if (file != null) {
            prefs.put("filePath", file.getPath());
            Repository.primaryStage.setTitle("AddressApp - " + file.getName());

        } else {
            prefs.remove("filePath");
            Repository.primaryStage.setTitle("AddressApp");
        }
    }

    public void savePersonDataToFile(File file) {

        try {

            JAXBContext context = JAXBContext.newInstance(StrategyListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            StrategyListWrapper wrapper = new StrategyListWrapper();
            wrapper.setPersons(strategyData);

            m.marshal(wrapper, file);
            setPersonFilePath(file);

        } catch (Exception e) {
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);

        }
    }

    public void loadPersonDataFromFile(File file) {

        try {

            JAXBContext context = JAXBContext.newInstance(StrategyListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();


            StrategyListWrapper wrapper = (StrategyListWrapper) um.unmarshal(file);

            strategyData.clear();
            strategyData.addAll(wrapper.getPersons());
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + file.getPath())
                    .showException(e);

        }
    }

    public ObservableList<StrategyVO> getPersonData() {
        return strategyData;
    }


}