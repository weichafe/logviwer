package com.larrainvial.process.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class ModelProcess {

    public SimpleStringProperty name;
    public SimpleStringProperty processName;
    public SimpleStringProperty pathbin;
    public SimpleStringProperty comentary;
    public Boolean proccesUp = false;
    public String algo;
    public Button button;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getProcessName() {
        return processName.get();
    }

    public SimpleStringProperty processNameProperty() {

        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = new SimpleStringProperty(processName);
    }

    public String getPathbin() {

        return pathbin.get();
    }

    public SimpleStringProperty pathbinProperty() {
        return pathbin;
    }

    public void setPathbin(String pathbin) {
        this.pathbin = new SimpleStringProperty(pathbin);
    }

    public String getComentary() {
        return comentary.get();
    }

    public SimpleStringProperty comentaryProperty() {
        return comentary;
    }

    public void setComentary(String comentary) {
        this.comentary = new SimpleStringProperty(comentary);
    }

    public Boolean isProccesUp() {
        return proccesUp;
    }

    public void setProccesUp(Boolean proccesUp) {
        this.proccesUp = proccesUp;
    }
}
