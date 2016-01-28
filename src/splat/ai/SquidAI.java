package splat.ai;

import org.newdawn.slick.GameContainer;
import splat.objects.AISquid;
import splat.updating.UpdateAble;

import java.util.Random;

/**
 * Superclass for all squid AIs.
 */
public abstract class SquidAI implements UpdateAble{
    protected AISquid squid;
    protected Random random = new Random();

    @Override
    public abstract void update(GameContainer container, float tpf);
}
