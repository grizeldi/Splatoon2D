package splat.multiplayer;

import splat.Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class InGameListenerThread extends Thread {
    private Main main;
    private DataInputStream dataIn;

    public InGameListenerThread(Main main) {
        this.main = main;
        dataIn = new DataInputStream(main.communicator.in);
    }

    @Override
    public void run() {
        while (true) {
            try {
                int in = main.communicator.in.read();
                int squidId;
                switch (in) {
                    case 10:
                        int x = dataIn.readInt();
                        int y = dataIn.readInt();
                        squidId = main.communicator.in.read();
                        main.networkedSquidManager.updateSquidLoc(squidId, x, y);
                        break;
                    case 11:
                        int rot = main.communicator.in.read();
                        squidId = main.communicator.in.read();
                        main.networkedSquidManager.updateSquidRot(squidId, rot);
                        break;
                }
            }catch (SocketException e){
                System.err.println("Server shutdown. Exiting.");
                System.exit(2);
            }catch (IOException e) {
                System.err.println("Network listener failed:");
                e.printStackTrace();
                return;
            }
        }
    }
}
