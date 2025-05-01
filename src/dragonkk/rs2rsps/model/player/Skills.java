package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.GlobalDropManager;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSTile;
import dragonkk.rs2rsps.util.Serializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the player's skills.
 *
 * @author Graham
 */
public class Skills implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5844927980818218635L;

    private static final int SKILL_COUNT = 25;

    private transient Player player;

    public static Player killer;

    public static final double MAXIMUM_EXP = 200000000;

    public static final double SMALL_EXP = 13034431;

    public short level[] = new short[SKILL_COUNT];
    private double xp[] = new double[SKILL_COUNT];
    private short HitPoints;
    private transient boolean goingUp;


    void startBoostingSkill() {
        if (goingUp)
            return;
        goingUp = true;
        Server.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                boolean canContinue = false;
                if (!player.isOnline() || isDead()) {
                    goingUp = false;
                    stop();
                    return;
                }
                for (int skillId = 0; skillId < SKILL_COUNT; skillId++) {
                    int lvlForXp = getLevelForXp(skillId);
                    if (skillId != 5 && level[skillId] != lvlForXp) {
                        if (level[skillId] < lvlForXp)
                            level[skillId] = (short) (level[skillId] + 1);
                        else
                            level[skillId] = (short) (level[skillId] - 1);
                        canContinue = true;
                        player.getFrames().sendSkillLevel(skillId);
                    }

                }
                if (!canContinue) {
                    goingUp = false;
                    stop();
                }
            }

        }, 60000, 60000);
    }

    public static final String[] SKILL_NAME = {"Attack", "Defence",
            "Strength", "Hitpoints", "Range", "Prayer", "Magic", "Cooking",
            "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting",
            "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer",
            "Farming", "Runecrafting", "Construction", "Hunter", "Summoning",
            "Dungeoneering"};

    public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2,
            HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6, COOKING = 7,
            WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
            CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15,
            AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19,
            RUNECRAFTING = 20, CONSTRUCTION = 21, HUNTER = 22, SUMMONING = 23,
            DUNGEONEERING = 23;

    public Skills() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            level[i] = 1;
            xp[i] = 0;
        }
        level[3] = 10;
        xp[3] = 1184;
        HitPoints = 100;
    }

    private static int[] PVP_DROP = {4151, 10828, 3751, 3753, 3755, 6737, 3749, 1079, 1093, 1114, 1127, 1163, 1185, 1201, 1213, 1347, 1289, 1319, 1333, 6733, 1347, 1359, 1373, 1432, 4131, 1187, 1215, 1231, 1305, 6731, 1377, 1434, 1645, 4087, 4586, 5680, 5681, 5698, 4587, 7158, 11732, 1725, 1704, 6585, 4724, 4726, 4728, 4730, 6735, 4753, 8007, 4755, 4759, 15349, 4757, 4745, 4747, 4749, 4751, 4732, 4734, 4736, 4738, 4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 2412, 1052, 2413, 2414, 10828, 3105, 7462, 7461, 7460, 4675, 2497, 2491, 2503, 2587, 2583, 2510, 1401, 1403, 1405, 1407, 15300, 3054, 4091, 8008, 4093, 4095, 4097, 3839, 3840, 3842, 3844, 4089, 7403, 7402, 7401, 7400, 10380, 10382, 10442, 10448, 10454, 10462, 10466, 10472, 10788, 10384, 10386, 10388, 10390, 10440, 10446, 10458, 10459, 10470, 10471, 11730, 1033, 1035, 2655, 10372, 10374, 10444, 10450, 10460, 10468, 15486, 10786, 11235, 13734, 13736, 3122, 4153, 6809, 6705, 15272, 15266, 2440, 2436, 3024, 2444, 6685
    };

    public static int PVPDROP() {
        return PVP_DROP[(int) (Math.random() * PVP_DROP.length)];
    }

    public void hit(int hitDiff) {
        if (hitDiff > HitPoints)
            hitDiff = HitPoints;
        HitPoints -= hitDiff;
        if (HitPoints == 0 || HitPoints < 0)
            sendDead();
        player.getFrames().sendConfig(1240, HitPoints * 2);
        player.getCombatDefinitions().startHealing();
    }

    String killerName;

    public void setKiller(String name) {
        killerName = name;
    }

    public Player getPlayer() {
        for (Player p : World.getPlayers()) if (p.getUsername().equalsIgnoreCase(killerName)) return p;
        return null;
    }


    public void sendCounter(int hit, boolean mage) {
        if (hit < 1)
            hit = 0;
        if (!mage) {
            player.xpGained += hit;
            addXp(6, hit);
        } else {
            player.xpGained += hit;
            addXp(0, hit);
        }
    }

    private void deathMessage(int x, int y) {
        Player p = getPlayer();
        if (p == null)
            return;
        int tokenAmount = player.getCombat().dangerousPVP(player) ? 6 : 3;
        int finalAmount = p.superextremeDonator || p.DoublePoints || p.getUsername().equals("im_tha_brid") || p.doublePoints2 == 1 ? tokenAmount * 2 : tokenAmount;
        p.getFrames().sendChatMessage(0, "You have defeated " + player.getUsername().replace("_", " ").toLowerCase() + ".");
        p.getFrames().sendChatMessage(0, "You are given " + finalAmount + " points for shop items!");
        Serializer.SaveAccount(player);
        p.getMask().setApperanceUpdate(true);
        if (player.getCombat().dangerousPVP(player)) {
            p.unsafeKills++;
            World.serverKills++;
        }
        if (p.getSkills().getXp(24) == 200000000) {
        } else if (p.doublePoints2 == 1) {
            p.getFrames().sendChatMessage(0, "You are rewarded 400k dung exp for that kill.");
            p.getSkills().addXp(24, 400000);
        } else {
            p.getFrames().sendChatMessage(0, "You are rewarded 200k dung exp for that kill.");
            p.getSkills().addXp(24, 200000);
        }
        p.Points += (finalAmount);
        p.Kills2++;
        RSTile tile = new RSTile((short) x, (short) y, (byte) 0, 0);
        Item item;
        double baseDrop = 0.10 + p.epAmount * 1.75;
        double ratio = Misc.random(251213) / 1457000;
        double overall = baseDrop / ratio;
        if (overall > 1.0) overall = 1.0;
        if (!player.getCombat().dangerousPVP(player)) {
            if (player == null) return;
            int amountToDrop = Misc.random(8);
            for (int i = 0; i < amountToDrop; i++) {
                item = new Item(PVP_DROP[(int) Math.floor(Math.random() * PVP_DROP.length)]);
                GlobalDropManager.dropPvpItem(p, tile, item, false);

            }
        } else {
            if (player.getRights() > 1)
                return;
            if (player == null) return;
            for (int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
                item = player.getInventory().getContainer().get(i);
                if (item == null)
                    continue;
                if (p.getRights() == 2)
                    player.getInventory().deleteItem(item.getId(), item.getAmount());
                GlobalDropManager.dropPvpItem(p, tile, item, false);
            }
            for (int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
                item = player.getEquipment().getEquipment().get(i);
                if (item == null)
                    continue;
                player.getEquipment().set(i, null);
                GlobalDropManager.dropPvpItem(p, tile, item, false);
            }
        }
    }

    public boolean isDead() {
        return HitPoints == 0;
    }

    public boolean playerDead;
    public transient boolean xLogProtection = false;

    public void setSkilll(int id, int level) {
        int hp = (short) level * 10;
        if (id == 3) this.HitPoints = (short) hp;
        this.level[(short) id] = (short) level;
    }

    public void setXpp(int id, double xp) {
        this.xp[id] = xp;
    }

    void sendDead() {
        if (xLogProtection)
            return;
        xLogProtection = true;
        if (player == null) return;
        player.getWalk().reset(true);
        playerDead = true;
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                if (player.getRights() > 0 && !player.getUsername().equals("maxwell")) {
                    player.animate(6994);
                    player.graphics(2755);
                    for (Player p2 : World.getPlayers()) {
                        if (p2 == null)
                            continue;
                        if (Math.random() * 100 == 1)
                            p2.getFrames().sendChatMessage(0, "<col=ff0000>Oh dear, one of Nostalgia's staff has been executed.");
                        else if (Math.random() * 100 >= 80 && Math.random() * 100 <= 100)
                            p2.getFrames().sendChatMessage(0, "<col=ff0000>Uh oh, looks like one of Nostalgia's staff has been killed, they must be mad.");
                        else if (Math.random() * 100 <= 0 && Math.random() * 100 <= 20)
                            p2.getFrames().sendChatMessage(0, "<col=ff0000>Looks like you will see this member of staff at home, dead.");
                        else if (Math.random() * 100 >= 20 && Math.random() * 100 <= 40)
                            p2.getFrames().sendChatMessage(0, "<col=ff0000>Why do we have so many bad pkers as staff?");
                        else if (Math.random() * 100 >= 40 && Math.random() * 100 <= 60) {
                            p2.getFrames().sendChatMessage(0, "<col=ff0000>What's going on, is it the end of the world?");
                            p2.getFrames().sendChatMessage(0, "<col=ff0000>Nope, a mod just died.");
                        } else if (Math.random() * 100 >= 60 && Math.random() * 100 <= 80)
                            p2.getFrames().sendChatMessage(0, "<col=ff0000>Okay, dead, we get it (Staff death is EPIC)!");
                        else
                            p2.getFrames().sendChatMessage(0, "<col=ff0000>Oh dear, one of Nostalgia's staff has been executed.");
                    }
                    this.stop();
                } /*/else if (player.getUsername().equals("maxwell")) {
                   // player.animate(4200);
                    //this.stop();
                }*/ else if (player.deathemote == 1 && player.getRights() == 0) {
                    player.animate(4200);
                    this.stop();
                } else if (player.deathemote == 0 && player.getRights() == 0) {
                    player.animate(9055);
                    this.stop();
                } else if (player.deathemote == 2 && player.getRights() == 0) {
                    player.animate(7197);
                    this.stop();
                } else if (player.deathemote == 3 && player.getRights() == 0) {
                    player.animate(2241);
                    this.stop();
                } else if (player.deathemote == 4 && player.getRights() == 0) {
                    player.animate(2242);
                    this.stop();
                } else if (player.deathemote < 0 || player.deathemote > 4 && player.getRights() == 0) {
                    player.animate(7197);
                    this.stop();
                }
                GameLogicTaskManager.schedule(new GameLogicTask() {
                    @Override
                    public void run() {
                        player.getCombat().logoutDelay = 20;
                        final int x = player.getLocation().getX();
                        final int y = player.getLocation().getY();
                        player.getCombatDefinitions().refreshSpecial();
                        player.getFrames().sendClickableInterface(778);
                        deathMessage(x, y);
                        player.Deaths2++;
                        player.lastDFS = 0;
                        player.getFrames().sendChatMessage(0, "You have been defeated by " + player.target + ".");
                        if (player.getCombat().donatorzone(player))
                            if (Math.random() * 100 >= 50) player.getMask().getRegion().teleport(2444, 5533, 0, 0);
                            else player.getMask().getRegion().teleport(2444, 5522, 0, 0);
                        else if (player.getCombat().mageArena(player))
                            if (Math.random() * 100 >= 50) player.getMask().getRegion().teleport(3084, 3933, 0, 0);
                            else player.getMask().getRegion().teleport(3084, 3934, 0, 0);
                        else if (Math.random() * 100 >= 0 && Math.random() * 100 <= 25)
                            player.getMask().getRegion().teleport(3192, 3435, 0, 0);
                        else if (Math.random() * 100 >= 25 && Math.random() * 100 <= 50)
                            player.getMask().getRegion().teleport(3192, 3445, 0, 0);
                        else if (Math.random() * 100 >= 50 && Math.random() * 100 <= 75)
                            player.getMask().getRegion().teleport(3180, 3445, 0, 0);
                        else if (Math.random() * 100 >= 75 && Math.random() * 100 <= 100)
                            player.getMask().getRegion().teleport(3180, 3435, 0, 0);
                        else player.getMask().getRegion().teleport(3192, 3435, 0, 0);
                        HitPoints = (short) (getLevelForXp(3) * 10);
                        player.getFrames().sendConfig(1240, HitPoints * 2);
                        player.getCombat().freezeDelay = 0;
                        player.getCombat().immuneDelay = 0;
                        player.overload = 0;
                        player.curseDelay = 0;
                        player.overloadstats = 0;
                        player.magicresist = 0;
                        player.teleblockimmuneDelay = 0;
                        player.teleblockDelay = 0;
                        player.getCombat().CombatDelay = 0;
                        player.getCombat().delay = 0;
                        xLogProtection = false;
                        player.getCombat().vengDelay = 0;
                        player.getCombat().vengeance = false;
                        for (int i = 0; i < SKILL_COUNT; i++)
                            set(i, getLevelForXp(i));
                        player.getPrayer().closeAllPrayers();
                        player.getCombatDefinitions().setSpecpercentage(
                                (byte) 100);
                        player.getCombatDefinitions().refreshSpecial();
                        player.animate(-1);
                        player.getCombat().removeTarget();
                        player.getCombat().clear();
                        playerDead = false;
                        Serializer.SaveAccount(player);
                        this.stop();
                    }
                }, 3, 0);
            }
        }, 1, 0);
    }

    public void heal(int hitDiff) {
        if (isDead())
            return;
        HitPoints += hitDiff;
        short max = (short) (getLevel(3) * 10);
        if (HitPoints > max) HitPoints = max;
        player.getFrames().sendConfig(1240, HitPoints * 2);
    }

    public void healOverLevel(int hitDiff, int over) {
        if (isDead()) return;
        HitPoints += hitDiff;
        int max = (getLevelForXp(3) + over) * 10;
        if (HitPoints > max) HitPoints = (short) max;
        player.getFrames().sendConfig(1240, HitPoints * 2);
    }

    public void healBrew(int hitDiff) {
        if (isDead())
            return;
        HitPoints += hitDiff;
        short max = (short) ((getLevel(3) * 10) * 1.15);
        if (HitPoints > max) HitPoints = max;
        player.getFrames().sendConfig(1240, HitPoints * 2);
    }

    public void heal(int hitDiff, short max) {
        HitPoints += hitDiff;
        if (HitPoints > max) HitPoints = max;
        player.getFrames().sendConfig(1240, HitPoints * 2);
    }

    public void RestorePray(int hitDiff) {
        level[5] += hitDiff;
        short max = (short) getLevelForXp(5);
        if (level[5] > max) level[5] = max;
        player.getFrames().sendSkillLevel(5);
    }

    public void drainPray(int drain) {
        level[5] -= drain;
        if (level[5] < 0) level[5] = 0;
        player.getFrames().sendSkillLevel(5);
    }

    public void drain(int skill, int drain) {
        level[skill] -= drain;
        if (level[skill] < 0) level[skill] = 0;
        player.getFrames().sendSkillLevel(skill);
        startBoostingSkill();
    }

    public void Reset() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            level[i] = 1;
            xp[i] = 0;
        }
        refresh();
    }

    public int getCombatLevel() {
        int attack = getLevelForXp(0);
        int defence = getLevelForXp(1);
        int strength = getLevelForXp(2);
        int hp = getLevelForXp(3);
        int prayer = getLevelForXp(5);
        int ranged = getLevelForXp(4);
        int magic = getLevelForXp(6);
        int combatLevel = 3;
        combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.25) + 1;
        double melee = (attack + strength) * 0.325;
        double ranger = Math.floor(ranged * 1.5) * 0.325;
        double mage = Math.floor(magic * 1.5) * 0.325;
        if (melee >= ranger && melee >= mage) combatLevel += melee;
        else if (ranger >= melee && ranger >= mage) combatLevel += ranger;
        else if (mage >= melee && mage >= ranger) combatLevel += mage;
        int summoning = getLevelForXp(Skills.SUMMONING);
        summoning /= 8;
        return combatLevel + summoning;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getLevel(int skill) {
        return level[skill];
    }

    public double getXp(int skill) {
        return xp[skill];
    }

    public int getXPForLevel(int level) {
        int points = 0;
        int output = 0;
        for (int lvl = 1; lvl <= level; lvl++) {
            points += Math.floor((double) lvl + 300.0
                    * Math.pow(2.0, (double) lvl / 7.0));
            if (lvl >= level) return output;
            output = (int) Math.floor(points / 4);
        }
        return 0;
    }

    public int getLevelForXp(int skill) {
        double exp = xp[skill];
        int points = 0;
        int output = 0;
        for (int lvl = 1; lvl < (skill == 24 ? 121 : 100); lvl++) {
            points += Math.floor((double) lvl + 300.0
                    * Math.pow(2.0, (double) lvl / 7.0));
            output = (int) Math.floor(points / 4);
            if ((output - 1) >= exp) return lvl;
        }
        return player.getUsername().equals("") ? 255
                : (skill == 24 ? 120 : 99);
    }

    public void setXp(int skill, double exp) {
        xp[skill] = exp;
        player.getFrames().sendSkillLevel(skill);
    }

    public void addXp(int skill, double exp) {
        int oldLevel = getLevelForXp(skill);
        xp[skill] += exp;
        if (xp[skill] > MAXIMUM_EXP) xp[skill] = MAXIMUM_EXP;
        int newLevel = getLevelForXp(skill);
        int levelDiff = newLevel - oldLevel;
        if (newLevel > oldLevel) {
            level[skill] += levelDiff;
            if (skill == 3)
                heal(100 * levelDiff);
            //LevelUp.levelUp(player, skill);
            player.getMask().setApperanceUpdate(true);
        }
        player.getFrames().sendSkillLevel(skill);
    }

    public void set(int skill, int val) {
        level[skill] = (short) val;
        player.getFrames().sendSkillLevel(skill);
        startBoostingSkill();
    }

    public void sendSkillLevels() {
        for (int i = 0; i < Skills.SKILL_COUNT; i++)
            player.getFrames().sendSkillLevel(i);
    }

    public void refresh() {
        sendSkillLevels();
        player.getFrames().sendConfig(1240, HitPoints * 2);
        this.player.getMask().setApperanceUpdate(true);
    }

    public void setHitPoints(short hitPoints) {
        HitPoints = hitPoints;
    }

    public short getHitPoints() {
        return HitPoints;
    }

}
