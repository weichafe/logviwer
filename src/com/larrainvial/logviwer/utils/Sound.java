package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Repository;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;

public class Sound {

    public Sound(){

        try {

            File urlSound = new File(Repository.locationPath + "tos-redalert.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlSound);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();
            audioClip.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
