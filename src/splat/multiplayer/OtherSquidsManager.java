package splat.multiplayer;

import org.newdawn.slick.GameContainer;
import splat.Main;
import splat.objects.NetworkedSquid;
import splat.updating.UpdateAble;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherSquidsManager implements UpdateAble{
    private Map<Integer, NetworkedSquid> squidMap;
    private Main main;

    public Color [] tempColorArray;

    public OtherSquidsManager(Main main) {
        this.main = main;
        squidMap = new HashMap<>();
    }

    @Override
    public void update(GameContainer container, float tpf) {

    }

    //--------------------------INIT STUFF----------------------------
    //Used to flag that squid creation has started
    public void startSquidCreation(){
        tempColorArray = new Color[main.players];
    }

    //Array is full. Create squids.
    public void finalizeSquidCreation(){
        for (int i = 0; i < tempColorArray.length; i++){
            if (tempColorArray[i] != null){
                squidMap.put(i, new NetworkedSquid(0, 0, tempColorArray[i], main));
            }
        }
    }
    //------------------------END OF INIT STUFF-----------------------
}
