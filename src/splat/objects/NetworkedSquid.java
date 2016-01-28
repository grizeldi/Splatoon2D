package splat.objects;

import org.newdawn.slick.GameContainer;
import splat.Main;

import java.awt.*;

public class NetworkedSquid extends AISquid{

    public NetworkedSquid(float x, float y, Color c, Main main) {
        super(x, y, c, main);
    }

    @Override
    public void update(GameContainer container, float tpf) {
        //TODO if there is even going to be any of those...
    }

    public void move(float x, float y){
        this.x += x;
        this.y += y;
    }
}
