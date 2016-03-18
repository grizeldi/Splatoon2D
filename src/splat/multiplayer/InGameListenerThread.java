package splat.multiplayer;

import splat.Main;

import java.io.IOException;
import java.net.SocketException;

public class InGameListenerThread extends Thread {
    private Main main;

    public InGameListenerThread(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int in = main.communicator.in.read();
                int squidId;
                switch (in) {
                    case 10:
                        int x = main.communicator.in.read();
                        int y = main.communicator.in.read();
                        squidId = main.communicator.in.read();
                        //TODO update stuff
                        System.out.println("Squid " + squidId + " update recieved");
                        break;
                    case 11:
                        int rot = main.communicator.in.read();
                        squidId = main.communicator.in.read();
                        //TODO update stuff
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
