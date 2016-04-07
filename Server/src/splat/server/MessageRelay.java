package splat.server;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Used to relay messages to other clients.
 * @see splat.server.ClientManager
 */
public class MessageRelay implements Runnable{
    private Map<Integer, OutputStream> streamsMap = new HashMap<>();
    private List<DataPacket> packetsBuffer = new ArrayList<>();
    private int [] allClientIds;
    private Thread dispatcherThread;

    public MessageRelay() {
        dispatcherThread = new Thread(this);
        dispatcherThread.start();
    }

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
        //try {
            int [] clients = new int[streamsMap.size() - 1];
            int i = 0;
            for (Map.Entry<Integer, OutputStream> set : streamsMap.entrySet()) {
                if (set.getKey() != exceptClientID) {
                    //set.getValue().write(data);
                    clients[i] = set.getKey();
                    i++;
                }
            }
            DataPacket packet = new DataPacket(clients, data);
            packetsBuffer.add(packet);
            return true;
        /*}catch (IOException e){
            Logger.getLogger("splat.server.MessageRelay").warning("Write failed:");
            e.printStackTrace();
            return false;
        }*/
    }

    public void addClient(int id, OutputStream stream){
        streamsMap.put(id, stream);
        allClientIds = new int[streamsMap.size()];
        Set<Integer> set = streamsMap.keySet();
        int i = 0;
        while (i < set.size()){
            allClientIds[i] = i;
            i++;
        }
        System.out.println("Client ids array: " + Arrays.toString(allClientIds));
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(300);
                for (Map.Entry<Integer, OutputStream> set : streamsMap.entrySet()) {
                    for (DataPacket packet : packetsBuffer) {
                        //Dispatch
                        if (packet.recieverIds.contains(set.getKey())){
                            set.getValue().write(packet.messasge);
                        }
                    }
                }
                packetsBuffer.clear();
                for (DataPacket packet : packetsBuffer){
                    System.out.println("Packet clients: " + Arrays.toString(packet.recieverIds.toArray()));
                    System.out.println("Packet message: " + Arrays.toString(packet.messasge));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            } catch (IOException e) {
                System.err.println("Client dispatcher thread had problems: ");
                e.printStackTrace();
            }
        }
    }

    private class DataPacket {
        List<Integer> recieverIds = new ArrayList<>();
        byte [] messasge;

        public DataPacket(int[] recieverIds, byte [] messasge) {
            for (int i : recieverIds){
                this.recieverIds.add(i);
            }
            this.messasge = messasge;
        }
    }
}
