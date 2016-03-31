package splat.multiplayer;

import org.newdawn.slick.GameContainer;
import splat.Main;
import splat.objects.NetworkedSquid;
import splat.updating.UpdateAble;

import java.awt.*;
import java.util.*;
import java.util.List;

public class OtherSquidsManager implements UpdateAble{
    private Map<Integer, NetworkedSquid> squidMap;
    private Main main;
    private final Object sync = new Object();

    public List<Color> tempColorArray= new ArrayList<>();

    public OtherSquidsManager(Main main) {
        this.main = main;
        squidMap = new HashMap<>();
    }

    @Override
    public void update(GameContainer container, float tpf) {
        for (int i = 0; i < squidMap.size(); i++){
            synchronized (sync) {
                if (squidMap.get(i) != null)
                    squidMap.get(i).update(container, tpf);
            }
        }
    }

    public void updateSquidLoc(int squidId, int x, int y){
        NetworkedSquid squid = squidMap.get(squidId);
        squid.x = x;
        squid.y = y;
    }

    public void updateSquidRot(int squidId, int rot){
        NetworkedSquid squid = squidMap.get(squidId);
        squid.rotation = rot;
    }

    public void renderSquids(){
        for (int i = 0; i < squidMap.size(); i++){
            if (squidMap.get(i) != null)
                squidMap.get(i).render((int) main.mainChar.x, (int) main.mainChar.y);
        }
    }

    //--------------------------INIT STUFF----------------------------
    //Array is full. Create squids.
    public void finalizeSquidCreation(){
        for (int i = 0; i < tempColorArray.size(); i++){
            if (tempColorArray.get(i) != null && i != main.clientID){
                synchronized (sync) {
                    squidMap.put(i, new NetworkedSquid(0, 0, tempColorArray.get(i), main));
                }
            }
        }
    }
    //------------------------END OF INIT STUFF-----------------------
}
