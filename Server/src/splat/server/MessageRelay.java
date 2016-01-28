package splat.server;

import java.io.*;
import java.util.Map;

/**
 * Used to relay messages to other clients.
 * @see splat.server.ClientManager
 */
public class MessageRelay {
    private Map<Integer, OutputStream> streamsMap;

    public boolean relayToAll(byte [] data){
        try {
            for (OutputStream out : streamsMap.values()) {
                out.write(data);
            }
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean relayToAllExcept(byte [] data, int exceptClientID){
        try {
            for (Map.Entry<Integer, OutputStream> set : streamsMap.entrySet()) {
                if (set.getKey() != exceptClientID) {
                    set.getValue().write(data);
                }
            }
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
