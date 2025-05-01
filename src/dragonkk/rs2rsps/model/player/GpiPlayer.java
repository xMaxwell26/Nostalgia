package dragonkk.rs2rsps.model.player;

public class GpiPlayer {
    Player player;
    boolean inScreen;
    private boolean forceAppearence;
    byte status;
    private int locationHash;
    private boolean needUpdate;

    public GpiPlayer() {
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setLocation(int location) {
        this.locationHash = location;
    }

    public int getLocation() {
        return locationHash;
    }

    public void setForceAppearence(boolean forceAppearence) {
        this.forceAppearence = forceAppearence;
    }

    public boolean isForceAppearence() {
        return forceAppearence;
    }

    public void setInScreen(boolean inScreen) {
        this.inScreen = inScreen;
    }

    public boolean isInScreen() {
        return inScreen;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getStatus() {
        return status;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }
}