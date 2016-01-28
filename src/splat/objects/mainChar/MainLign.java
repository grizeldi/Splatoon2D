package splat.objects.mainChar;

import splat.Main;
import splat.factories.ColorSplatFactory;
import splat.objects.GameObject;
import splat.updating.UpdateAble;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import splat.factories.TileMapHelper;

import java.awt.*;

/**
 * Represents the player squid/inkling in space
 */
public class MainLign extends GameObject implements UpdateAble {
    private Image representation;
    private ColorSplatFactory splatFactory;
    public InkTank inkTank;
    public Rectangle collisionRectangle;
    private TileMapHelper mapHelper;
    public float health = 100;
    private Main main;

    public MainLign(Color c, Main main) {
        if (c == Color.ORANGE){
            try {
                representation = new Image("data/sprites/oranzenLign.png");
            } catch (SlickException e) {
                System.exit(-1);
            }
        }else if (c == Color.BLUE){
            try {
                representation = new Image("data/sprites/modrLign.png");
            } catch (SlickException e) {
                System.exit(-1);
            }
        }
        x = -100;
        y = -400;
        representation.setCenterOfRotation(18, 18);
        collisionRectangle = new Rectangle(33, 33);
        splatFactory = main.splatFactory;
        mapHelper = main.mapHelper;
        inkTank = new InkTank(c);
        this.main = main;
    }

    @Override
    public void update(GameContainer gameContainer, float tpf) {
        //Movement
        Input input = gameContainer.getInput();
        float movementSpeed = 0.3F;
        float absoluteX = x * -1 + gameContainer.getWidth() / 2 - 18, absoluteY = y * -1 + gameContainer.getHeight() / 2 - 18;
        if (((input.isKeyDown(Input.KEY_A) && !main.controllerUsed) || input.isControllerLeft(0)) && !mapHelper.isBlocked(absoluteX - tpf * movementSpeed * Math.cos(Math.toRadians(rotation + 90)), absoluteY - tpf * movementSpeed * Math.sin(Math.toRadians(rotation + 90)))){
            x += tpf / 2 * movementSpeed * Math.cos(Math.toRadians(rotation + 90));
            y += tpf / 2 * movementSpeed * Math.sin(Math.toRadians(rotation + 90));
        }
        if (((input.isKeyDown(Input.KEY_D) && !main.controllerUsed) || input.isControllerRight(0)) && !mapHelper.isBlocked(absoluteX + tpf * movementSpeed * Math.cos(Math.toRadians(rotation + 90)), absoluteY + tpf * movementSpeed * Math.sin(Math.toRadians(rotation + 90)))){
            x -= tpf / 2 * movementSpeed * Math.cos(Math.toRadians(rotation + 90));
            y -= tpf / 2 * movementSpeed * Math.sin(Math.toRadians(rotation + 90));
        }
        if(((input.isKeyDown(Input.KEY_W) && !main.controllerUsed) || input.isControllerUp(0)) && !mapHelper.isBlocked(absoluteX + tpf * movementSpeed * Math.cos(Math.toRadians(rotation)), absoluteY + tpf * movementSpeed * Math.sin(Math.toRadians(rotation)))){
            x -= tpf * movementSpeed * Math.cos(Math.toRadians(rotation));
            y -= tpf * movementSpeed * Math.sin(Math.toRadians(rotation));
        }
        if (((input.isKeyDown(Input.KEY_S) && !main.controllerUsed) || input.isControllerDown(0)) && !mapHelper.isBlocked(absoluteX - tpf * movementSpeed * Math.cos(Math.toRadians(rotation)), absoluteY - tpf * movementSpeed * Math.sin(Math.toRadians(rotation)))){
            x += tpf * movementSpeed * Math.cos(Math.toRadians(rotation));
            y += tpf * movementSpeed * Math.sin(Math.toRadians(rotation));
        }
        //Rotate to mouse
        float mouseX = input.getMouseX() - 18, mouseY = input.getMouseY() - 18;
        float lignX = (gameContainer.getWidth() / 2) - (representation.getWidth() / 2), lignY = (gameContainer.getHeight() / 2) - (representation.getHeight() / 2);

        if (main.controllerUsed){
            if (input.getAxisValue(0, 4) != 0.0F) {
                mouseX = input.getAxisValue(0, 4) * 1000 + (gameContainer.getWidth() / 2 );
            }else {
                mouseX = gameContainer.getWidth() / 2;
            }
            if (input.getAxisValue(0, 5) != 0.0F) {
                mouseY = input.getAxisValue(0, 5) * 1000 + (gameContainer.getHeight() / 2 );
            }else {
                mouseY = gameContainer.getHeight() / 2;
            }
        }
        float xDistance = mouseX - lignX, yDistance = mouseY - lignY;
        rotation = (float) Math.toDegrees(Math.atan2(yDistance, xDistance));

        //Apply changes
        representation.setRotation(rotation);
        collisionRectangle.setLocation((int)lignX, (int)lignY);

        //Harm the squid or recharge ink tank
        if (splatFactory.intersectsWithSplat(collisionRectangle, Color.BLUE, (int) x, (int) y)){
            health -= 0.2F;
        }else if (splatFactory.intersectsWithSplat(collisionRectangle, Color.ORANGE, (int)x, (int)y)) {
            inkTank.refill(0.2F);
        }
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Image getRepresentation() {
        return representation;
    }
}
