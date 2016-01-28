package splat.multiplayer.events;

public class CharChangeEvent extends NetworkEvent{
    private float newX, newY, newRotation;

    public CharChangeEvent(int peerID, float newX, float newY, float newRotation) {
        super(peerID);
        this.newX = newX;
        this.newY = newY;
        this.newRotation = newRotation;
    }

    public float getNewX() {
        return newX;
    }

    public float getNewY() {
        return newY;
    }

    public float getNewRotation() {
        return newRotation;
    }
}
