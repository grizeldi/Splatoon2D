package splat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to relay messages to other clients.
 * @see splat.server.ClientManager
 */
public class MessageRelay {
    private Main main;
    private ClientManager clientManager;
    private List<BufferedReader> inputs = new ArrayList<BufferedReader>();
    private List<PrintWriter> outputs = new ArrayList<PrintWriter>();

    public MessageRelay(ClientManager clientManager, Main main) throws IOException{
        this.clientManager = clientManager;
        this.main = main;
        for (Socket s : clientManager.clientConnections){
            inputs.add(new BufferedReader(new InputStreamReader(s.getInputStream())));
            outputs.add(new PrintWriter(new OutputStreamWriter(s.getOutputStream())));
        }
    }

    public class ListenerThread extends Thread{
        private MessageRelay relay;
        private BufferedReader buff;

        public ListenerThread(MessageRelay relay, BufferedReader reader) {
            this.relay = relay;
            buff = reader;
            this.start();
        }

        public ListenerThread(MessageRelay relay, int index) {
            this(relay, relay.inputs.get(index));
        }

        @Override
        public void run() {
            super.run();
            try {
                while (!this.relay.main.shouldExit) {

                }
            }catch (Exception e){
                System.err.println("Listener thread failed. Shutting this client down.");
                Socket sock = clientManager.clientConnections.get(inputs.indexOf(buff));
                try {
                    sock.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                outputs.remove(inputs.indexOf(buff));
                inputs.remove(buff);
            }
        }
    }
}
