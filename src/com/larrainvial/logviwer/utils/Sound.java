package com.larrainvial.logviwer.utils;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {

    public Sound(){

        try {

            URL urlSound = ClassLoader.getSystemResource("resources/tos-redalert.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlSound);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();
            audioClip.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
