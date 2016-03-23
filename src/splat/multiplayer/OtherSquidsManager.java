package splat.multiplayer;

import org.newdawn.slick.GameContainer;
import splat.Main;
import splat.objects.NetworkedSquid;
import splat.updating.UpdateAble;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherSquidsManager implements UpdateAble{
    private Map<Integer, NetworkedSquid> squidMap;
    private Main main;

    public List<Color> tempColorArray= new ArrayList<>();

    public OtherSquidsManager(Main main) {
        this.main = main;
        squidMap = new HashMap<>();
    }

    @Override
    public void update(GameContainer container, float tpf) {
        for (Color c : tempColorArray){
            if (c == Color.BLUE){
                System.out.print("Blue ");
            }else {
                System.out.print("Orange ");
            }
            System.out.println();
        }
    }

    //--------------------------INIT STUFF----------------------------
    //Array is full. Create squids.
    public void finalizeSquidCreation(){
        for (int i = 0; i < tempColorArray.size(); i++){
            if (tempColorArray.get(i) != null && i != main.clientID){
                squidMap.put(i, new NetworkedSquid(0, 0, tempColorArray.get(i), main));
            }
        }
    }
    //------------------------END OF INIT STUFF-----------------------
}
