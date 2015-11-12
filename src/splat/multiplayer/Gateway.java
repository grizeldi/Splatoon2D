package splat.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;

public class Gateway {
    private ServerSocket inGateway;
    private NetworkReader reader = new NetworkReader();
    private NetworkNotifier notifier = new NetworkNotifier();

    public Gateway() {
        try {
            inGateway = new ServerSocket(78778);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
