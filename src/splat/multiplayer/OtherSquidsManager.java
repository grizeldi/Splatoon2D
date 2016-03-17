package splat.multiplayer;

import org.newdawn.slick.GameContainer;
import splat.objects.NetworkedSquid;
import splat.updating.UpdateAble;

import java.util.List;
import java.util.Map;

public class OtherSquidsManager implements UpdateAble{
    private Map<Integer, NetworkedSquid> squidMap;

    @Override
    public void update(GameContainer container, float tpf) {

    }
}
