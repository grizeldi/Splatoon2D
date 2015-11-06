package splat.client.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HealthBar {
    public int health, maxHealth;
    private Image onePixel;

    public HealthBar(int health) {
        this.health = health;
        maxHealth = health;
        try {
            onePixel = new Image("data/sprites/healthPart.png");
        } catch (SlickException e) {
            System.exit(-1);
        }
    }

    public void heal(int amount){
        health += amount;
        if (health > maxHealth)
            health = maxHealth;
    }

    public void damage(int amount){
        health -= amount;
    }

    public boolean isDead(){
        return health < 0;
    }

    public void render(int x, int y){
        //Calculate number of pixels
        //37 is the pixel width of squid
        int pixels = Math.round(37 * health / maxHealth);
        for (int i = 0; i < pixels; i++){
            onePixel.draw(x + i, y);
        }
    }
}
