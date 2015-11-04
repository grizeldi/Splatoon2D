package com.grizeldi.splatoon;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class MusicPlayer {
    private Music splatAttak;

    public MusicPlayer() {
        try {
            splatAttak = new Music("data/inkattack.ogg");
        } catch (SlickException e) {
            System.exit(-2);
        }
    }

    public void start(){
        splatAttak.loop(1.0F, 0.4F);
    }

    public void pause(){
        splatAttak.pause();
    }

    public void resume(){
        splatAttak.resume();
    }

    public void stop(){
        splatAttak.stop();
    }
}
