package dragonkk.rs2rsps.model.npc;

import dragonkk.rs2rsps.model.Animation;
import dragonkk.rs2rsps.model.Graphics;


public class Mask {
    private transient int turnToIndex;
    private transient Npc npc;
    private transient boolean Teleport;
    private transient boolean HitUpdate;
    private transient boolean AnimationUpdate;
    private transient boolean turnToUpdate;
    private transient Animation lastAnimation;
    private transient Graphics lastGraphics;
    private transient Graphics lastGraphics2;
    private transient String chatMessage;

    public void setChatMessage(String message) {
        this.chatMessage = message;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public boolean isTurnToUpdate() {
        return turnToUpdate;
    }

    public int getTurnToIndex() {
        return turnToIndex;
    }

    private transient boolean graphicUpdate, graphic2Update, hit1Update, hit2Update, chatUpdate;

    public Mask(Npc npc) {
        this.setNpc(npc);
        this.setTeleport(true);
    }

    public void setTurnToIndex(int index) {
        this.turnToIndex = index;
    }

    public void setTurnToUpdate(boolean val) {
        this.turnToUpdate = val;
    }

    public void reset() {
        this.setTeleport(false);
        this.setHitUpdate(false);
        this.setAnimationUpdate(false);
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }

    public Npc getNpc() {
        return npc;
    }

    public boolean isUpdateNeeded() {
        return HitUpdate || AnimationUpdate;
    }

    public void setTeleport(boolean teleport) {
        Teleport = teleport;
    }

    public boolean isTeleport() {
        return Teleport;
    }

    public void setHitUpdate(boolean hitUpdate) {
        HitUpdate = hitUpdate;
    }

    public boolean isHitUpdate() {
        return HitUpdate;
    }

    public void setAnimationUpdate(boolean animationUpdate) {
        AnimationUpdate = animationUpdate;
    }

    public boolean isAnimationUpdate() {
        return AnimationUpdate;
    }

    public void setLastAnimation(Animation lastAnimation) {
        this.lastAnimation = lastAnimation;
    }

    public Animation getLastAnimation() {
        return lastAnimation;
    }

    /**
     * @param graphicUpdate the graphicUpdate to set
     */
    public void setGraphicUpdate(boolean graphicUpdate) {
        this.graphicUpdate = graphicUpdate;
    }

    /**
     * @return the graphicUpdate
     */
    public boolean isGraphicUpdate() {
        return graphicUpdate;
    }

    /**
     * @param graphic2Update the graphic2Update to set
     */
    public void setGraphic2Update(boolean graphic2Update) {
        this.graphic2Update = graphic2Update;
    }

    /**
     * @return the graphic2Update
     */
    public boolean isGraphic2Update() {
        return graphic2Update;
    }

    /**
     * @param hit1Update the hit1Update to set
     */
    public void setHit1Update(boolean hit1Update) {
        this.hit1Update = hit1Update;
    }

    /**
     * @return the hit1Update
     */
    public boolean isHit1Update() {
        return hit1Update;
    }

    /**
     * @param hit2Update the hit2Update to set
     */
    public void setHit2Update(boolean hit2Update) {
        this.hit2Update = hit2Update;
    }

    /**
     * @return the hit2Update
     */
    public boolean isHit2Update() {
        return hit2Update;
    }

    /**
     * @param chatUpdate the chatUpdate to set
     */
    public void setChatUpdate(boolean chatUpdate) {
        this.chatUpdate = chatUpdate;
    }

    /**
     * @return the chatUpdate
     */
    public boolean isChatUpdate() {
        return chatUpdate;
    }

    /**
     * @param lastGraphics the lastGraphics to set
     */
    public void setLastGraphics(Graphics lastGraphics) {
        this.lastGraphics = lastGraphics;
    }

    /**
     * @return the lastGraphics
     */
    public Graphics getLastGraphics() {
        return lastGraphics;
    }

    /**
     * @param lastGraphics2 the lastGraphics2 to set
     */
    public void setLastGraphics2(Graphics lastGraphics2) {
        this.lastGraphics2 = lastGraphics2;
    }

    /**
     * @return the lastGraphics2
     */
    public Graphics getLastGraphics2() {
        return lastGraphics2;
    }
}
