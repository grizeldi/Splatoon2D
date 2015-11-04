package com.grizeldi.splatoon.objects.mainChar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

public class InkTank {
    private static final int MAX_FULNESS = 100;
    public float fullness = MAX_FULNESS;
    private Image tankImage, orangeInkComponent, blueInkComponent, inkComponent;

    public InkTank(Color c) {
        try {
            tankImage = new Image("data/sprites/ink/inkTank.png");
            orangeInkComponent = new Image("data/sprites/ink/orangeInk.png");
            blueInkComponent = orangeInkComponent; //TODO fix this
        } catch (SlickException e) {
            System.exit(-1);
        }
        if (c == Color.ORANGE)
            inkComponent = orangeInkComponent;
        else if (c == Color.BLUE)
            inkComponent = blueInkComponent;
    }

    public void refillToMax(){
        fullness = MAX_FULNESS;
    }

    public void refill(float amount){
        fullness += amount;
        if (fullness > 100)
            fullness = 100;
    }

    public boolean isFull(){
        return fullness >= 100;
    }

    public boolean isEmpty(){
        return fullness < 1;
    }

    public void useUp(float inkAmount){
        fullness -= inkAmount;
    }

    //Render the tank and amount
    public void render(GameContainer c){
        renderInk(c);
        tankImage.draw(c.getWidth() - 51, 10);
    }

    private void renderInk(GameContainer c){
        if (fullness >= 1)
            inkComponent.draw(c.getWidth() - 49, 77);
        else
            return;
        if (fullness >= 26)
            inkComponent.draw(c.getWidth() - 49, 62);
        else
            return;
        if (fullness >= 51)
            inkComponent.draw(c.getWidth() - 49, 47);
        else
            return;
        if (fullness >= 76)
            inkComponent.draw(c.getWidth() - 49, 32);
    }
}
