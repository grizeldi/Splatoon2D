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
        System.out.println("Squid location: " + x + " " + y);
    }

    @Override
    public void render(int backX, int backY) {
        representation.draw(x + backX, y + backY);
        System.out.println("Drawn a networked squid at: " + (x+backX) + " " + (y+backY));
    }

    public void move(float x, float y){
        this.x += x;
        this.y += y;
    }
}
