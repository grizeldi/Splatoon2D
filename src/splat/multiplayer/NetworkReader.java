package splat.multiplayer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkReader implements Runnable{
    private List<BufferedReader> inputs;
    private List<ListenerThread> listenerThreads;
    private boolean shouldExit = false;
    private int idCount = 1;

    public NetworkReader() {
        inputs = new ArrayList<BufferedReader>();
        listenerThreads = new ArrayList<ListenerThread>();
        new Thread(this).start();
    }

    public void addToInputs(Socket s) throws IOException{
        PipedInputStream pipe = new PipedInputStream();
        inputs.add(new BufferedReader(new InputStreamReader(pipe)));
        listenerThreads.add(new ListenerThread(s, pipe, idCount));
    }

    @Override
    public void run() {
        while (!shouldExit){
            try {
                for (int i = 0; i < inputs.size(); i++) {
                    if (listenerThreads.get(i).shouldRead) {
                        String in = inputs.get(i).readLine();
                        //TODO handle the text
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static class ListenerThread extends Thread{
        private BufferedReader in;
        private boolean exit = false;
        public boolean shouldRead = false;
        private PrintWriter out;
        int peerID;

        public ListenerThread(Socket sock, PipedInputStream str, int clientID) throws IOException{
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(new PipedOutputStream(str)));
            this.peerID = clientID;
            start();
        }

        @Override
        public void run() {
            super.run();
            while (!exit){
                try {
                    String in = this.in.readLine();
                    shouldRead = true;
                    out.println(in);
                } catch (IOException e) {
                    e.printStackTrace();
                    exit = true;
                }
            }
            System.out.println("Exiting communication.");
        }

        public void shutdown(){
            exit = true;
        }
    }
}
