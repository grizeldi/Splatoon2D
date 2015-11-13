package splat.multiplayer;

import splat.multiplayer.events.CharChangeEvent;
import splat.multiplayer.events.NetworkEvent;
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

    public void notify(NetworkEvents eventType, NetworkEvent event){
        switch (eventType){
            case CHAR_CHANGE:
                CharChangeEvent exactType = (CharChangeEvent) event;

                break;
            case CHAR_SHOOT:
                //TODO
                break;
        }
    }
}
