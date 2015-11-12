package splat.multiplayer;

import splat.multiplayer.events.NetworkEvents;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class sends all changes to others.
 */
public final class NetworkNotifier {
    protected List<PrintWriter> notifieds = new ArrayList<PrintWriter>();

    public NetworkNotifier() {
        //DODO
    }

    public void notify(NetworkEvents event){
        switch (event){
            case CHAR_CHANGE:
                //TODO
                break;
            case CHAR_SHOOT:
                //TODO
                break;
        }
    }
}
