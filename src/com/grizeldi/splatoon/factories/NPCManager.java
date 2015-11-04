package com.grizeldi.splatoon.factories;

import com.grizeldi.splatoon.Main;
import org.newdawn.slick.GameContainer;
import com.grizeldi.splatoon.objects.AISquid;
import com.grizeldi.splatoon.updating.UpdateAble;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NPCManager implements UpdateAble, Runnable{
    private List<AISquid> squidsList = new ArrayList<AISquid>(), scheduled = new ArrayList<AISquid>();
    private Thread sleeperThread;
    private boolean spawnNewSquid;
    private Random random = new Random();
    private final static Object lock = new Object();
    private Main main;

    public NPCManager(Main main) {
        sleeperThread = new Thread(this);
        sleeperThread.setDaemon(true);
        sleeperThread.start();
        this.main = main;
    }

    @Override
    public void update(GameContainer container, float tpf) {
        synchronized (lock) {
            if (spawnNewSquid) {
                squidsList.add(new AISquid(350, 350, Color.BLUE, main));
                spawnNewSquid = false;
                System.out.println("A new NPC squid spawned.");
            }
        }
        for (AISquid squid : squidsList){
            squid.update(container, tpf);
            if (squid.opacity < 0)
                scheduled.add(squid);
        }
        squidsList.removeAll(scheduled);
        scheduled.removeAll(scheduled);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(30)*1000);
                synchronized (lock) {
                    spawnNewSquid = true;
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void renderSquids(int backX, int backY){
        for (AISquid squid:squidsList){
            squid.render(backX, backY);
        }
    }
}
