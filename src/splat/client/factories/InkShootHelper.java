package splat.client.factories;

import java.awt.*;
import java.util.Random;

public class InkShootHelper {
    private ColorSplatFactory splatFactory;
    private static final Random random = new Random();
    private static final float splatPadding = 10.14F;
    private static final int randomMovement = 5;

    public InkShootHelper(ColorSplatFactory splatFactory) {
        this.splatFactory = splatFactory;
    }

    /**
     * Generates a bunch of splats.
     * @param x Character x.
     * @param y Character y.
     * @param rotation Rotation of character.
     * @param x2 Mouse click x.
     * @param y2 Mouse click y.
     * @return Amount of ink used.
     */
    public int generateSplatPath(int x, int y, int x2, int y2, float rotation){
        System.out.println("SPLAT!");
        float xFloat = (float) x, yFloat = (float) y;
        int i = 0;
        if (xFloat < x2 && yFloat < y2) {
            while (xFloat < x2 && yFloat < y2) {
                splatFactory.createNewSplat((int) xFloat + random.nextInt(randomMovement), (int) yFloat + random.nextInt(randomMovement), Color.ORANGE);
                xFloat += splatPadding * Math.cos(Math.toRadians(rotation));
                yFloat += splatPadding * Math.sin(Math.toRadians(rotation));
                i++;
            }
            return i;
        }else if (xFloat > x2 && yFloat > y2){
            while (xFloat > x2 && yFloat > y2) {
                splatFactory.createNewSplat((int) xFloat + random.nextInt(randomMovement), (int) yFloat + random.nextInt(randomMovement), Color.ORANGE);
                xFloat += splatPadding * Math.cos(Math.toRadians(rotation));
                yFloat += splatPadding * Math.sin(Math.toRadians(rotation));
                i++;
            }
            return i;
        }else if (xFloat < x2 && yFloat > y2){
            while (xFloat < x2 && yFloat > y2) {
                splatFactory.createNewSplat((int) xFloat + random.nextInt(randomMovement), (int) yFloat + random.nextInt(randomMovement), Color.ORANGE);
                xFloat += splatPadding * Math.cos(Math.toRadians(rotation));
                yFloat += splatPadding * Math.sin(Math.toRadians(rotation));
                i++;
            }
            return i;
        }else if (xFloat > x2 && yFloat < y2){
            while (xFloat > x2 && yFloat < y2) {
                splatFactory.createNewSplat((int) xFloat + random.nextInt(randomMovement), (int) yFloat + random.nextInt(randomMovement), Color.ORANGE);
                xFloat += splatPadding * Math.cos(Math.toRadians(rotation));
                yFloat += splatPadding * Math.sin(Math.toRadians(rotation));
                i++;
            }
            return i;
        }
        return 0;
    }
}
