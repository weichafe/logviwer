package com.larrainvial.logviwer.utils;

import java.io.*;

public class FileRepository extends File {

    public FileRepository(String name, String uri) {
        super(uri + name + ".bkp");
    }

    public synchronized void writeFile(Object object) throws IOException {

        FileOutputStream f = new FileOutputStream(this);
        ObjectOutputStream s = new ObjectOutputStream(f);
        s.writeObject(object);
        s.close();
    }

    public synchronized Object readFile() throws IOException, ClassNotFoundException {

        FileInputStream f = new FileInputStream(this);
        ObjectInputStream s = new ObjectInputStream(f);
        Object object = s.readObject();
        s.close();
        return object;
    }

    public Boolean existFile() {
        return this.exists() && !this.isDirectory();
    }
}
