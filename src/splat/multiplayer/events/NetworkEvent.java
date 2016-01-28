package splat.multiplayer.events;

public abstract class NetworkEvent {
    private int id;

    public NetworkEvent(int peerID){
        id = peerID;
    }

    public int getSourcePeerID(){
        return id;
    }
}
