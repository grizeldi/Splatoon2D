package splat.objects;

import splat.Main;
import splat.ai.SquidAI;
import splat.factories.ColorSplatFactory;
import org.newdawn.slick.*;
import org.newdawn.slick.Image;
import splat.factories.SoundEffectPlayer;
import splat.factories.TileMapHelper;
import splat.objects.mainChar.MainLign;
import splat.updating.UpdateAble;

import java.awt.*;
import java.awt.Color;

//These's x and y are absolute.
public class AISquid extends GameObject implements UpdateAble {
    protected Color color;
    private SquidAI ai;
    protected Image representation;
    protected HealthBar health;
    public boolean isOnSplat = false;
    private boolean deadReported = false;
    protected ColorSplatFactory splatFactory;
    public TileMapHelper mapHelper;
    protected Rectangle collisionRectangle;
    public MainLign mainLign;
    protected SoundEffectPlayer soundEffectPlayer;
    public float opacity = 1.0F;
    private int splatCount = 0;

    public AISquid(float x, float y, Color c, Main main) {
        color = c;
        splatFactory = main.splatFactory;
        mainLign = main.mainChar;
        mapHelper = main.mapHelper;
        ai = new SquidAI(this);
        health = new HealthBar(333);
        this.x = x;
        this.y = y;
        rotation = -5;
        soundEffectPlayer = main.soundPlayer;
        try {
            if (c == Color.ORANGE)
                representation = new Image("data/sprites/oranzenLign.png");
            else if (c == Color.BLUE)
                representation = new Image("data/sprites/modrLign.png");
            else
                throw new RuntimeException("Wrong squid color.");
        }catch (Exception e){
            System.exit(-1);
        }
        collisionRectangle = new Rectangle(33, 33);
        representation.setCenterOfRotation(18, 18);
    }

    @Override
    public void update(GameContainer container, float tpf) {
        collisionRectangle.setLocation((int)(x + mainLign.x), (int)(y + mainLign.y));
        isOnSplat = splatFactory.intersectsWithSplat(collisionRectangle, Color.ORANGE, (int)mainLign.x, (int)mainLign.y);
        ai.update(container, tpf);
        representation.setRotation(rotation);
        if (isOnSplat){
            health.damage(1);
        }
        if (health.isDead() && !deadReported){
            soundEffectPlayer.playSound(SoundEffectPlayer.sounds.SQUID_DIE);
            Main.squidsKilled ++;

            deadReported = true;
        }
        if (splatCount > 10){
            splatFactory.createNewSplat((int)x, (int)y, Color.BLUE);
            splatCount = 0;
            soundEffectPlayer.playSound(SoundEffectPlayer.sounds.SHOOT, 0.1F);
        }else {
            splatCount++;
        }
    }

    public void render(int backX, int backY){
        if (health.isDead()){
            opacity -= 0.05F;
            representation.setAlpha(opacity);
        }
        representation.draw(x + backX, y + backY);
        health.render((int)x + backX, (int)y + backY - 5);
    }

    public Color getColor() {
        return color;
    }

    public Image getRepresentation() {
        return representation;
    }
}
