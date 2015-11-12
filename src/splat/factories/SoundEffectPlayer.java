package splat.factories;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.Random;

public class SoundEffectPlayer {
    public static enum sounds {SQUID_DIE, SHOOT}
    private Sound squidDie;
    private Sound shoot1, shoot2, shoot3;
    private Random random = new Random();

    public SoundEffectPlayer() throws SlickException {
        squidDie = new Sound("data/sound/squidDie.ogg");
        shoot1 = new Sound("data/sound/shoot.ogg");
        shoot2 = new Sound("data/sound/shoot2.ogg");
        shoot3 = new Sound("data/sound/shoot3.ogg");
    }

    public void playSound(sounds sound, float vol){
        switch (sound){
            case SQUID_DIE:
                squidDie.play(1.0F, vol);
                break;
            case SHOOT:
                switch (random.nextInt(3) + 1){
                    case 1:
                        shoot1.play(1.0F, vol);
                        break;
                    case 2:
                        shoot2.play(1.0F, vol);
                        break;
                    case 3:
                        shoot3.play(1.0F, vol);
                        break;
                }
                break;
        }
    }

    public void playSound(sounds sound){
        playSound(sound, 1.0F);
    }
}
