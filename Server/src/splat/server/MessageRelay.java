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
    private List<ListenerThread> threads = new ArrayList<ListenerThread>();

    public MessageRelay(ClientManager clientManager, Main main) throws IOException{
        this.clientManager = clientManager;
        this.main = main;
        for (Socket s : clientManager.clientConnections){
            inputs.add(new BufferedReader(new InputStreamReader(s.getInputStream())));
            outputs.add(new PrintWriter(new OutputStreamWriter(s.getOutputStream())));
            threads.add(new ListenerThread(inputs.get(inputs.size() - 1)));
        }
    }

    public void relayToAll(String s) throws WriteFailedException{
        for (PrintWriter out : outputs){
            out.println(s);
            if (out.checkError())
                throw new WriteFailedException(out);
        }
    }

    public class ListenerThread extends Thread{
        private BufferedReader buff;

        public ListenerThread(BufferedReader reader) {
            buff = reader;
            this.start();
        }

        public ListenerThread(int index) {
            this(inputs.get(index));
        }

        @Override
        public void run() {
            super.run();
            try {
                while (!main.shouldExit) {
                    String s = buff.readLine();
                    relayToAll(s);
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
