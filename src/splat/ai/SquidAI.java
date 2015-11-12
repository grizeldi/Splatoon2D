package splat.ai;

import org.newdawn.slick.GameContainer;
import splat.objects.AISquid;
import splat.updating.UpdateAble;

import java.util.Random;

public class SquidAI implements UpdateAble {
    private AISquid squid;
    private Random random = new Random();
    private static final float movementSpeed = 0.07F;
    private boolean rotated;
    private int count = 0;

    public SquidAI(AISquid squid) {
        this.squid = squid;
    }

    @Override
    public void update(GameContainer container, float tpf) {
        if (!squid.isOnSplat || rotated) {
            if (random.nextInt(10) > 7) {
                //Change direction
                if (random.nextBoolean()) {
                    squid.rotation += random.nextInt(40);
                } else {
                    squid.rotation -= random.nextInt(40);
                }
            }
        }else {
            squid.rotation += 180;
            rotated = true;
        }
        //Move the squid: check collisions first and then do shit.
        float absoluteX = squid.mainLign.x + squid.x, absoluteY = squid.mainLign.y + squid.y;
        if (squid.mapHelper.isBlocked(squid.x + tpf * movementSpeed * Math.cos(Math.toRadians(squid.rotation)), squid.y + tpf * movementSpeed * Math.sin(Math.toRadians(squid.rotation)))){
            squid.rotation += 180;
            rotated = true;
        }else {
            squid.x += tpf * movementSpeed * Math.cos(Math.toRadians(squid.rotation));
            squid.y += tpf * movementSpeed * Math.sin(Math.toRadians(squid.rotation));
        }
        if (rotated){
            count ++;
            if (count > 50){
                rotated = false;
                count = 0;
            }
        }
    }
}
