package splat.factories;

import splat.Main;

import java.awt.*;
import java.util.Random;

public class InkShootHelper {
    private ColorSplatFactory splatFactory;
    private static final Random random = new Random();
    private static final float splatPadding = 10.14F;
    private static final int randomMovement = 5;
    private final TileMapHelper mapHelper;

    public InkShootHelper(Main main) {
        splatFactory = main.splatFactory;
        mapHelper = main.mapHelper;
    }

    /**
     * Generates a bunch of splats.
     * Uses absolute coords.
     * @param x Character x.
     * @param y Character y.
     * @param rotation Rotation of character (direction).
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
                i++;
                if (mapHelper.isBlocked(xFloat + splatPadding * Math.cos(rotation), yFloat + splatPadding * Math.sin(Math.toRadians(rotation))))
                    return i;
                else {
                    xFloat += splatPadding * Math.cos(Math.toRadians(rotation));
                    yFloat += splatPadding * Math.sin(Math.toRadians(rotation));
                }
            }
            return i;
        }else if (xFloat > x2 && yFloat > y2){
            while (xFloat > x2 && yFloat > y2) {
                splatFactory.createNewSplat((int) xFloat + random.nextInt(randomMovement), (int) yFloat + random.nextInt(randomMovement), Color.ORANGE);
                i++;
                if (mapHelper.isBlocked(xFloat + splatPadding * Math.cos(rotation), yFloat + splatPadding * Math.sin(Math.toRadians(rotation))))
                    return i;
                else {
                    xFloat += splatPadding * Math.cos(Math.toRadians(rotation));
                    yFloat += splatPadding * Math.sin(Math.toRadians(rotation));
                }
            }
            return i;
        }else if (xFloat < x2 && yFloat > y2){
            while (xFloat < x2 && yFloat > y2) {
                splatFactory.createNewSplat((int) xFloat + random.nextInt(randomMovement), (int) yFloat + random.nextInt(randomMovement), Color.ORANGE);
                i++;
                if (mapHelper.isBlocked(xFloat + splatPadding * Math.cos(rotation), yFloat + splatPadding * Math.sin(Math.toRadians(rotation))))
                    return i;
                else {
                    xFloat += splatPadding * Math.cos(Math.toRadians(rotation));
                    yFloat += splatPadding * Math.sin(Math.toRadians(rotation));
                }
            }
            return i;
        }else if (xFloat > x2 && yFloat < y2){
            while (xFloat > x2 && yFloat < y2) {
                splatFactory.createNewSplat((int) xFloat + random.nextInt(randomMovement), (int) yFloat + random.nextInt(randomMovement), Color.ORANGE);
                i++;
                if (mapHelper.isBlocked(xFloat + splatPadding * Math.cos(rotation), yFloat + splatPadding * Math.sin(Math.toRadians(rotation))))
                    return i;
                else {
                    xFloat += splatPadding * Math.cos(Math.toRadians(rotation));
                    yFloat += splatPadding * Math.sin(Math.toRadians(rotation));
                }
            }
            return i;
        }
        return 0;
    }
}
