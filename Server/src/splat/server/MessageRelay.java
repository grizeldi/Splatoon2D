package splat.server;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Used to relay messages to other clients.
 * @see splat.server.ClientManager
 */
public class MessageRelay {
    private Map<Integer, OutputStream> streamsMap = new HashMap<>();
    private List<DataPacket> packetsBuffer = new ArrayList<>();
    private int [] allClientIds;

    public boolean relayToAll(byte [] data){
        /*try {
            for (OutputStream out : streamsMap.values()) {
                out.write(data);
            }
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }*/
        DataPacket packet = new DataPacket(allClientIds, data);
        packetsBuffer.add(packet);
        return true;
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
            Logger.getLogger("splat.server.MessageRelay").warning("Write failed:");
            e.printStackTrace();
            return false;
        }
    }

    public void addClient(int id, OutputStream stream){
        streamsMap.put(id, stream);
        allClientIds = new int[streamsMap.size()];
        Set<Integer> set = streamsMap.keySet();
        int i = 0;
        while (set.iterator().hasNext()){
            int id2 = set.iterator().next();
            allClientIds[i] = id2;
            i++;
        }
    }

    private class DataPacket {
        int [] recieverIds;
        byte [] messasge;

        public DataPacket(int[] recieverIds, byte [] messasge) {
            this.recieverIds = recieverIds;
            this.messasge = messasge;
        }
    }
}
