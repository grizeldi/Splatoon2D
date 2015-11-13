package splat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Runnable{
    List<Socket> clientConnections = new ArrayList<Socket>();
    private Main main;

    public ClientManager(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        while (!main.shouldExit){}
        for (Socket s : clientConnections){
            try {
                s.close();
            } catch (IOException e) {
                System.err.println("Failed to close socket???");
                e.printStackTrace();
            }
        }
    }
}
