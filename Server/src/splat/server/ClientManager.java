package splat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    void startGame() throws IOException{
        for (Socket s : clientConnections){
            OutputStream out = s.getOutputStream();
            out.write(2);
        }
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

//Used to listen to a single client;
class SingleClientListenerThread extends Thread{
    private final int clientID;
    private final InputStream in;
    private final MessageRelay relay;
    private boolean shouldExit;

    public SingleClientListenerThread(int clientID, Socket socket, MessageRelay relay) throws Exception{
        InputStream in1;
        this.clientID = clientID;
        this.relay = relay;
        try {
            in1 = socket.getInputStream();
        } catch (IOException e) {
            in1 = null;
            Logger.getLogger("splat.server.SingleClientListenerThread").warning("Unable to create client listener thread for client " + clientID);
            e.printStackTrace();
            throw new Exception();
        }
        in = in1;
        start();
    }

    @Override
    public void run() {
        try {
            while (!shouldExit) {
                if (in.available() > 0)
            }
        }catch (IOException e){
            Logger.getLogger("splat.server.SingleClientListenerThread").warning("Listener thread for client " + clientID + "failed.");
            e.printStackTrace();
        }
    }
}
