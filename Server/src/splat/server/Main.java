package splat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main implements Runnable{
    boolean shouldExit = false;
    private ServerSocket serverSocket;
    private Thread socketAccepter;

    public static void main(String [] args){
        new Main();
    }

    public Main() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    while (!shouldExit) {
                        if (!in.readLine().toLowerCase().equals("stop")) {
                            in.close();
                            shouldExit = true;
                            socketAccepter.interrupt();
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
        socketAccepter = new Thread(this);
        socketAccepter.start();
    }

    @Override
    public void run() {
        try {
            while (!shouldExit) {
                Socket sock = serverSocket.accept();
            }
        }catch (Exception e){
            System.err.println("Socket accepter thread has failed, exiting.");
            e.printStackTrace();
            shouldExit = true;
        }
    }
}
