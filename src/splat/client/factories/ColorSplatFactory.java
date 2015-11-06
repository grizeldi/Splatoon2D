package splat.client.factories;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import splat.client.updating.UpdateAble;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColorSplatFactory implements UpdateAble {
    private List<Splat> splats = new ArrayList<Splat>(), scheduled = new ArrayList<Splat>();
    private Image orangeSplat;

    public ColorSplatFactory() {
        try {
            orangeSplat = new Image("data/sprites/splats/orange.png");
        } catch (SlickException e) {
            System.exit(-1);
        }
    }

    @Override
    public void update(GameContainer container, float tpf) {
        for (Splat s : splats) {
            s.update(container, tpf);
            if (s.opacity <= 0)
                scheduled.add(s);
        }
        splats.removeAll(scheduled);
        scheduled.removeAll(scheduled);
    }

    public void renderSplats(int backX, int backY){
        for (Splat splat : splats){
            if (splat.color == Color.ORANGE){
                orangeSplat.setAlpha(splat.opacity);
                orangeSplat.draw(splat.x + backX, splat.y + backY);
            }
        }
    }

    public boolean createNewSplat(int x, int y, Color c){
        for (Splat s : splats){
            if (s.x == x && s.y == y)
                return false;
        }
        splats.add(new Splat(x, y, c));
        return true;
    }

    public boolean intersectsWithSplat(Rectangle rec, Color c, int backX, int backY){
        for (Splat s : splats){
            if (c == s.color && rec.intersects(new Rectangle(s.x + backX, s.y + backY, 25, 25)))
                return true;
        }
        return false;
    }
}

class Splat implements UpdateAble{
    int x, y, timeToRemoval;
    float opacity = 1F;
    static final int maxTimeAlive = 600;
    Color color;

    public Splat(int x, int y, Color c){
        this.x = x;
        this.y = y;
        color = c;
        timeToRemoval = maxTimeAlive;
    }

    @Override
    public void update(GameContainer container, float tpf) {
        timeToRemoval --;
        if (timeToRemoval < 1)
            opacity -= 0.03F;
    }
}
