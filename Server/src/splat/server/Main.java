package splat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main implements Runnable{
    boolean shouldExit = false;
    private int maxClients;
    private ServerSocket serverSocket;
    private Thread socketAccepter;
    private ClientManager clientManager;

    public static void main(String [] args){
        new Main(Integer.parseInt(args[0]));
    }

    public Main(int i) {
        maxClients = i;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    while (!shouldExit) {
                        if (in.readLine().toLowerCase().equals("stop")) {
                            in.close();
                            shouldExit = true;
                            socketAccepter.interrupt();
                            System.exit(1);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    shouldExit = true;
                }
            }
        }).start();
        try {
            serverSocket = new ServerSocket(7878);
        } catch (IOException e) {
            e.printStackTrace();
            shouldExit = true;
        }
        clientManager = new ClientManager(this);
        socketAccepter = new Thread(this);
        socketAccepter.start();
    }

    @Override
    public void run() {
        try {
            while (!shouldExit) {
                Socket sock = serverSocket.accept();
                clientManager.addClient(sock);
                if (clientManager.clientConnections.size() == maxClients)
                    break;
            }
            if (clientManager.clientConnections.size() == maxClients){
                //We are good to go. Let's rock!
            }
        }catch (Exception e){
            System.err.println("Socket accepter thread has failed, exiting.");
            e.printStackTrace();
            shouldExit = true;
        }
    }
}
