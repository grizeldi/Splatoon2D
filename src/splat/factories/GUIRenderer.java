package splat.factories;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GUIRenderer {
    private Image healthBack, healthTop;
    private int healthDrawY;

    public GUIRenderer(GameContainer container) throws SlickException{
        healthBack = new Image("data/gui/healthBack.png").getScaledCopy(700*container.getWidth()/1920, 286*container.getHeight()/1080);
        healthTop = new Image("data/gui/health.png").getScaledCopy(656*container.getWidth()/1920, 152*container.getHeight()/1080);
        healthDrawY = 41*container.getHeight()/1080;
    }

    public void renderGui(int mainCharHealth, int mainCharMaxHealth){
        //render health stuff
        healthBack.draw(0, 30);
        int width = Math.round(healthTop.getWidth() * mainCharHealth / mainCharMaxHealth);

        healthTop.getSubImage(0, 0, width, healthTop.getHeight()).draw(0, 30+healthDrawY);
    }

}