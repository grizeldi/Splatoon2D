package com.grizeldi.splatoon.objects.mainChar;

import com.grizeldi.splatoon.Main;
import com.grizeldi.splatoon.factories.ColorSplatFactory;
import com.grizeldi.splatoon.objects.GameObject;
import com.grizeldi.splatoon.updating.UpdateAble;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import com.grizeldi.splatoon.factories.TileMapHelper;

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
        y = -80;
        representation.setCenterOfRotation(18, 18);
        collisionRectangle = new Rectangle(33, 33);
        splatFactory = main.splatFactory;
        mapHelper = main.mapHelper;
        inkTank = new InkTank(c);
    }

    @Override
    public void update(GameContainer gameContainer, float tpf) {
        //Movement
        Input input = gameContainer.getInput();
        float movementSpeed = 0.3F;
        float absoluteX = x * -1 + gameContainer.getWidth() / 2 - 18, absoluteY = y * -1 + gameContainer.getHeight() / 2 - 18;
        if (input.isKeyDown(Input.KEY_A) && !mapHelper.isBlocked(absoluteX - tpf * movementSpeed * Math.cos(Math.toRadians(rotation + 90)), absoluteY - tpf * movementSpeed * Math.sin(Math.toRadians(rotation + 90)))){
            x += tpf / 2 * movementSpeed * Math.cos(Math.toRadians(rotation + 90));
            y += tpf / 2 * movementSpeed * Math.sin(Math.toRadians(rotation + 90));
        }
        if (input.isKeyDown(Input.KEY_D) && !mapHelper.isBlocked(absoluteX + tpf * movementSpeed * Math.cos(Math.toRadians(rotation + 90)), absoluteY + tpf * movementSpeed * Math.sin(Math.toRadians(rotation + 90)))){
            x -= tpf / 2 * movementSpeed * Math.cos(Math.toRadians(rotation + 90));
            y -= tpf / 2 * movementSpeed * Math.sin(Math.toRadians(rotation + 90));
        }
        if(input.isKeyDown(Input.KEY_W) && !mapHelper.isBlocked(absoluteX + tpf * movementSpeed * Math.cos(Math.toRadians(rotation)), absoluteY + tpf * movementSpeed * Math.sin(Math.toRadians(rotation)))){
            x -= tpf * movementSpeed * Math.cos(Math.toRadians(rotation));
            y -= tpf * movementSpeed * Math.sin(Math.toRadians(rotation));
        }
        if (input.isKeyDown(Input.KEY_S) && !mapHelper.isBlocked(absoluteX - tpf * movementSpeed * Math.cos(Math.toRadians(rotation)), absoluteY - tpf * movementSpeed * Math.sin(Math.toRadians(rotation)))){
            x += tpf * movementSpeed * Math.cos(Math.toRadians(rotation));
            y += tpf * movementSpeed * Math.sin(Math.toRadians(rotation));
        }
        //Rotate to mouse
        int mouseX = input.getMouseX() - 18, mouseY = input.getMouseY() - 18;
        int lignX = (gameContainer.getWidth() / 2) - (representation.getWidth() / 2), lignY = (gameContainer.getHeight() / 2) - (representation.getHeight() / 2);
        float xDistance = mouseX - lignX, yDistance = mouseY - lignY;
        rotation = (float) Math.toDegrees(Math.atan2(yDistance, xDistance));

        //Apply changes
        representation.setRotation(rotation);
        collisionRectangle.setLocation(lignX, lignY);

        //Recharge Ink tank
        if (splatFactory.intersectsWithSplat(collisionRectangle, Color.ORANGE, (int)x, (int)y)) {
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
