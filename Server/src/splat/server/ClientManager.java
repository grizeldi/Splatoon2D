package splat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
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

    void addClient(Socket s) throws IOException{
        clientConnections.add(s);
        notifyClientsNumberChanged();
    }

    private void notifyClientsNumberChanged() throws IOException{
        byte newNum = (byte)clientConnections.size();
        for (Socket s : clientConnections){
            OutputStream out = s.getOutputStream();
            out.write(0);
            out.write(newNum);
        }
    }
}
