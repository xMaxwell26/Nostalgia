package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.model.Animation;
import dragonkk.rs2rsps.model.Graphics;
import dragonkk.rs2rsps.model.Heal;
import dragonkk.rs2rsps.util.RSTile;

public class Mask {
    private transient Player player;

    private transient ChatMessage lastChatMessage;
    private transient Graphics lastGraphics;
    private transient Graphics lastGraphics2;
    private transient Animation lastAnimation;
    private transient Heal lastHeal;
    private transient boolean ApperanceUpdate;
    private transient boolean HitUpdate;
    private transient boolean Hit2Update;
    private transient boolean HealUpdate;
    private transient boolean ChatUpdate;
    private transient boolean GraphicUpdate;
    private transient boolean Graphic2Update;
    private transient boolean AnimationUpdate;
    private transient boolean turnToUpdate;
    private transient boolean turnToUpdate1;
    private transient boolean turnToReset;
    private transient RSTile turnToLocation;
    private transient int turnToIndex;
    private transient Region region;

    Mask(Player player) {
        this.setwPlayer(player);
        this.setRegion(new Region(this.player));
        this.setApperanceUpdate(true);
    }

    public void reset() {
        this.getRegion().reset();
        this.setApperanceUpdate(false);
        this.setHitUpdate(false);
        this.setHit2Update(false);
        this.setHealUpdate(false);
        this.setChatUpdate(false);
        this.setGraphicUpdate(false);
        this.setGraphic2Update(false);
        this.setAnimationUpdate(false);
        if (this.turnToReset)
            this.setTurnToUpdate(false);
        this.setTurnToUpdate1(false);
    }

    private void setwPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isUpdateNeeded() {
        return ApperanceUpdate || HitUpdate || Hit2Update || HealUpdate
                || GraphicUpdate || Graphic2Update || AnimationUpdate || turnToUpdate || player.getWalk().getWalkDir() != -1 || player.getWalk().getRunDir() != -1 || player.getWalk().isDidTele();
    }

    public void setApperanceUpdate(boolean apperanceUpdate) {
        ApperanceUpdate = apperanceUpdate;
    }

    boolean isApperanceUpdate() {
        return ApperanceUpdate;
    }

    void setHitUpdate(boolean hitUpdate) {
        HitUpdate = hitUpdate;
    }

    boolean isHitUpdate() {
        return HitUpdate;
    }

    public void setChatUpdate(boolean chatUpdate) {
        ChatUpdate = chatUpdate;
    }

    boolean isChatUpdate() {
        return ChatUpdate;
    }

    public void setLastChatMessage(ChatMessage lastChatMessage) {
        this.lastChatMessage = lastChatMessage;
    }

    ChatMessage getLastChatMessage() {
        return lastChatMessage;
    }

    void setGraphicUpdate(boolean graphicUpdate) {
        GraphicUpdate = graphicUpdate;
    }

    boolean isGraphicUpdate() {
        return GraphicUpdate;
    }

    void setLastGraphics(Graphics lastGraphics) {
        this.lastGraphics = lastGraphics;
    }

    Graphics getLastGraphics() {
        return lastGraphics;
    }

    void setLastAnimation(Animation lastAnimation) {
        this.lastAnimation = lastAnimation;
    }

    Animation getLastAnimation() {
        return lastAnimation;
    }

    void setAnimationUpdate(boolean animationUpdate) {
        AnimationUpdate = animationUpdate;
    }

    boolean isAnimationUpdate() {
        return AnimationUpdate;
    }

    private void setRegion(Region region) {
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    void setHit2Update(boolean hit2Update) {
        Hit2Update = hit2Update;
    }

    boolean isHit2Update() {
        return Hit2Update;
    }

    public void setTurnToUpdate(boolean turnToUpdate) {
        this.turnToUpdate = turnToUpdate;
    }

    boolean isTurnToUpdate() {
        return turnToUpdate;
    }

    public void setTurnToReset(boolean turnToReset) {
        this.turnToReset = turnToReset;
    }

    public boolean isTurnToReset() {
        return turnToReset;
    }

    void setGraphic2Update(boolean Graphic2Update) {
        this.Graphic2Update = Graphic2Update;
    }

    boolean isGraphic2Update() {
        return Graphic2Update;
    }

    void setLastGraphics2(Graphics lastGraphics2) {
        this.lastGraphics2 = lastGraphics2;
    }

    Graphics getLastGraphics2() {
        return lastGraphics2;
    }

    void setTurnToLocation(RSTile turnToLocation) {
        this.turnToLocation = turnToLocation;
    }

    RSTile getTurnToLocation() {
        return turnToLocation;
    }

    void setHealUpdate(boolean hit3Update) {
        HealUpdate = hit3Update;
    }

    boolean isHealUpdate() {
        return HealUpdate;
    }

    void setLastHeal(Heal lastHeal) {
        this.lastHeal = lastHeal;
    }

    Heal getLastHeal() {
        return lastHeal;
    }

    public void setTurnToIndex(int turnToIndex) {
        this.turnToIndex = turnToIndex;
    }

    int getTurnToIndex() {
        return turnToIndex;
    }

    void setTurnToUpdate1(boolean turnToUpdate1) {
        this.turnToUpdate1 = turnToUpdate1;
    }

    boolean isTurnToUpdate1() {
        return turnToUpdate1;
    }
}
