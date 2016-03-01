package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Repository;
import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;

public class Sound {

    public final String SOUND_NAME = "tos-redalert.wav";
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public Sound(){

        try {

            File urlSound = new File(Repository.locationPath + SOUND_NAME);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlSound);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();
            audioClip.close();

        } catch (Exception ex){
            ex.printStackTrace();
            logger.error(Level.SEVERE, ex);
        }

    }


}
