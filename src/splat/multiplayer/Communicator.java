package splat.multiplayer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Communicator implements Runnable{
    private Socket serverConnection;
    public OutputStream out;
    public DataOutputStream dataOut;
    public InputStream in;

    public Communicator(String serverIP) {
        try {
            serverConnection = new Socket(serverIP, 7878);
            out = serverConnection.getOutputStream();
            in = serverConnection.getInputStream();
            dataOut = new DataOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //Converter thread
    @Override
    public void run() {
        while (true){

        }
    }
}
