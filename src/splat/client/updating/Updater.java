package splat.client.updating;

import org.newdawn.slick.GameContainer;

import java.util.ArrayList;
import java.util.List;

public class Updater implements UpdateAble{
    private List<UpdateAble> updateAbles = new ArrayList<UpdateAble>();

    public void addUpdateAble(UpdateAble a) {
        updateAbles.add(a);
    }

    public void removeUpdateAble(UpdateAble a){
        updateAbles.remove(a);
    }

    @Override
    public void update(GameContainer c, float tpf) {
        for (UpdateAble updateAble : updateAbles)
            updateAble.update(c, tpf);
    }
}
