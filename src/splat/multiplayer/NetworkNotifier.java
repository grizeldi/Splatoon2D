package splat.multiplayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public final class NetworkNotifier {
    protected List<PrintWriter> notifieds = new ArrayList<PrintWriter>();
    private ServerSocket connecter;

    public NetworkNotifier() {
        try {
            connecter = new ServerSocket(78877);
        } catch (BindException e) {
            System.out.println("Failed to bind port...");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
