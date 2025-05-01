package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.Item;

import java.io.Serializable;

public class CombatDefinitions implements Serializable {

    private static final long serialVersionUID = 3485431224108912565L;

    private transient Player player;
    private transient boolean specialOn;
    public transient boolean infspecActive;
    private transient boolean gettingSpecialUp;
    private transient boolean autocasting;
    public transient short[] bonus;
    private transient boolean healing;
    private transient long lastEmote;
    private transient long lastFood;
    private transient long lastPot;
    private byte attackStyle;
    public byte specpercentage;


    public void switchSpecial() {
        specialOn = !specialOn;
        player.getFrames().sendConfig(301, specialOn ? 1 : 0);
        Item wep = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (wep != null) {
            switch (wep.getId()) {
                case 15486: //Staff of light
                    specialOn = false;
                    player.getFrames().sendConfig(301, 0);
                    if (specpercentage < 100) {
                        player.getFrames().sendChatMessage(0,
                                "You do not have enough special attack left.");
                        return;
                    }
                    player.animate(12804);
                    player.graphics(2319);
                    player.getCombat().solSpecWait = 100;
                    specpercentage = 0;
                    refreshSpecial();
                    break;
            }
        }
        if (player.isAttacking()) {
            if (wep != null) {
                switch (wep.getId()) {
                    //Gmaul ect in here..
                }
            }
        }
    }

    public void setSpecialOff() {
        specialOn = false;
        player.getFrames().sendConfig(301, specialOn ? 1 : 0);
    }

    public void refreshSpecial() {
        if (player.infSpec == 1) {
            gettingSpecialUp = true;
            specpercentage = 100;
            player.getFrames().sendConfig(300, specpercentage * 100);
            return;
        }
        player.getFrames().sendConfig(300, specpercentage * 10);
    }

    public void startGettingSpecialUp() {
        if (gettingSpecialUp)
            return;
        gettingSpecialUp = true;
        Server.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                if (player.infSpec == 1) {
                    gettingSpecialUp = true;
                    specpercentage = 100;
                    return;
                }
                if (!player.isOnline() || specpercentage == 100) {
                    gettingSpecialUp = false;
                    stop();
                    return;
                }
                specpercentage += (byte) ((100 - specpercentage) > 9 ? 10 : 100 - specpercentage);
                refreshSpecial();
                if (specpercentage == 100) {
                    gettingSpecialUp = false;
                    stop();
                }
            }
        }, 30000, 30000);
    }

    public void doEmote(int animId, int gfxId, int milliSecondDelay) {
        if (System.currentTimeMillis() < lastEmote) {
            player.getFrames().sendChatMessage(0,
                    "You're already doing an emote!");
            return;
        }
        if (player.getCombat().hasTarget()) {
            player.getFrames().sendChatMessage(0,
                    "You can't make an emote while attacking!");
            return;
        }
        if (animId > -1)
            player.animate(animId);
        if (gfxId > -1)
            player.graphics(gfxId);
        lastEmote = System.currentTimeMillis() + milliSecondDelay;
    }

    void startHealing() {
        if (healing)
            return;
        healing = true;
        Server.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                if (!player.isOnline()
                        || player.getSkills().getHitPoints() <= 0
                        || player.getSkills().getHitPoints() >= (player
                        .getSkills().getXPForLevel(3) * 10)) {
                    healing = false;
                    stop();
                    return;
                }
                player.getSkills().heal(1);
                if (player.getSkills().getHitPoints() >= (player.getSkills()
                        .getXPForLevel(3) * 10)) {
                    healing = false;
                    stop();
                }
            }
        }, 6000, 6000);
    }

    CombatDefinitions() {
    }

    public void refreshBonuses() {
        short[] bonuses = new short[15];
        for (int i = 0; i < 14; i++) {
            Item item = player.getEquipment().get(i);
            if (item == null)
                continue;
            for (int j = 0; j < 15; j++) {
                bonuses[j] += item.getDefinition().bonus[j];
            }
        }
        bonus = bonuses;
        // TODO
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSpecialOn(boolean specialOn) {
        this.specialOn = specialOn;
    }

    public boolean isSpecialOn() {
        return specialOn;
    }

    public void setAttackStyle(byte attackStyle) {
        this.attackStyle = attackStyle;
    }

    public byte getAttackStyle() {
        return attackStyle;
    }

    public void setBonus(short[] bonus) {
        this.bonus = bonus;
    }

    public short[] getBonus() {
        return bonus;
    }

    public void setAutocasting(boolean autocasting) {
        this.autocasting = autocasting;
    }

    public boolean isAutocasting() {
        return autocasting;
    }

    public void setSpecpercentage(byte specpercentage) {
        this.specpercentage = specpercentage;
    }

    public byte getSpecpercentage() {
        return specpercentage;
    }

    public void setHealing(boolean healing) {
        this.healing = healing;
    }

    public boolean isHealing() {
        return healing;
    }

    public void setLastEmote(long lastEmote) {
        this.lastEmote = lastEmote;
    }

    public long getLastEmote() {
        return lastEmote;
    }

    public void setLastFood(long lastFood) {
        this.lastFood = lastFood;
    }

    public long getLastFood() {
        return lastFood;
    }

    public void setLastPot(long lastPot) {
        this.lastPot = lastPot;
    }

    public long getLastPot() {
        return lastPot;
    }
}
