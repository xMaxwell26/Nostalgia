package dragonkk.rs2rsps.skills.combat;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.Hits.Hit;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Equipment;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.util.CombatManager;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.ProjectileManager;
import dragonkk.rs2rsps.util.RSTile;

import java.util.Random;

public class Combat {

    private boolean DFS = false;
    public int solSpecWait = 0;
    public boolean queuedSet;
    private int queuedSpellID;
    private int queuedSpellbookId;
    private int queuedPlayer;
    private int magicDelay;
    private int shieldDelay;
    private double BGSDrain;
    private double SWHDrain;
    private int[] dealtDamage = new int[2048];

    private boolean boltEffect = false;
    private boolean bowSpec = false;

    private int calculateRange() {
        Player p = (Player) entity;
        double strength = p.getSkills().getLevel(Skills.RANGE);
        double bonusMultiplier = (getRangedStrength(p) * 0.00175) + 0.21;
        double maxHit = (int) Math.floor(strength * bonusMultiplier) + 1.08;
        if (CombatManager.wearingVoid(p, 11664)) maxHit *= 1.6;
        return ((int) maxHit * 10);
    }

    private int getRangedStrength(Player p) {
        short arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
        short weapon = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
        switch (weapon) {
            case 4214:
                return 70;
            case 806:
                return 1;
            case 807:
                return 3;
            case 808:
                return 4;
            case 809:
                return 7;
            case 810:
                return 10;
            case 811:
                return 14;
        }
        switch (arrows) {
            case 15243:
                return 150;
            case 11212:
                return 60;
            case 11217:
                return 60;
            case 11222:
                return 60;
            case 9243:
                return 105;
            case 9244:
                return 117;
            case 9144:
                return 115;
            case 9143:
                return 100;
            case 9142:
                return 82;
            case 4740:
                return 55;
            case 882:
                return 7;
            case 884:
                return 10;
            case 886:
                return 16;
            case 888:
                return 22;
            case 890:
                return 31;
            case 892:
                return 49;
        }
        return -1;
    }


    /*
     * Returns the arrows allowed to be used by the bow
     */
    private int[] arrowsAllowed(int bow) {
        switch (bow) {
            case 839:
            case 841:
                return new int[]{882, 884};
            case 843:
            case 845:
                return new int[]{882, 884, 886};
            case 847:
            case 849:
                return new int[]{882, 884, 886, 888};
            case 851:
            case 853:
                return new int[]{882, 884, 886, 888, 890};
            case 859:
            case 861:
                return new int[]{882, 884, 886, 888, 890, 892};
            case 9185:
            case 18357:
                return new int[]{9243, 9244};
            case 11235:
                return new int[]{882, 884, 886, 888, 890, 892, 11212, 11217, 11222};
            case 4734:
                return new int[]{4740};
            case 15241:
                return new int[]{15243};
        }
        return null;
    }

    /*
     * Checks to see if the player can use the arrows with the bow
     */
    private boolean canUseArrows(Player p) {
        int wep = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
        int arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
        int[] allowed = arrowsAllowed(wep);
        for (int i : allowed)
            if (i == arrows)
                return true;
        return false;
    }

    private boolean checkArrows(Player p) {
        int wep = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
        int arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
        if (wep >= 4210 && wep <= 4225) return true;
        if (arrows < 1 && arrows > 19710 || p.getEquipment().get(13) == null) {
            p.getFrames().sendChatMessage(0, "You don't have any arrows equipt to use this bow/cannon.");
            return false;
        }
        if (!canUseArrows(p)) {
            p.getFrames().sendChatMessage(0, "You can't use these arrows in your bow/cannon.");
            return false;
        }
        return true;
    }

    private void skullPlayer(Player skulledOn) {

        Player us = ((Player) entity);
        boolean skulled = us.skullTimer > 0;
        if (!skulledOn.skulledOn.equalsIgnoreCase(((Player) entity)
                .getUsername())) {
            us.skulledOn = skulledOn.getUsername();
            us.skullTimer = 1500;
        } else if (skulled) us.skullTimer = 1500;

    }

    private void leechStats(final Player p, final Player opp) {
        boolean don = false;
        if (leechDelay > 0)
            return;
        if (opp == null || opp.getSkills().playerDead)
            return;
        // atk = 2231
        // range = 2236 defence 2244 mage = 2248
        boolean leechingAttack = p.getPrayer().usingPrayer(1, 1) || p.getPrayer().usingPrayer(1, 10);
        boolean leechingStrength = p.getPrayer().usingPrayer(1, 14);
        boolean leechingDef = p.getPrayer().usingPrayer(1, 13);
        boolean leechingRange = p.getPrayer().usingPrayer(1, 11);
        boolean leechingMagic = p.getPrayer().usingPrayer(1, 12);
        if (leechingAttack) {
            if (this.shieldDelay < 1) p.animate(12575);
            ProjectileManager.sendGlobalProjectile(((Entity) p), (Entity) opp,
                    2231, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(0) >
                            opp.getSkills().getLevelForXp(0)) {
                        double minLevel =
                                opp.getSkills().getLevelForXp(0) * 0.80;
                        if (!(opp.getSkills().getLevel(0) < minLevel)) {
                            opp.getSkills().set(0, opp.getSkills().getLevel(0) - 1);
                            opp.getFrames().sendChatMessage(0,
                                    "The enemy's curse has drained your attack level!");
                        } else opp.getFrames().sendChatMessage(0,
                                "The enemy's curse has no effect on your attack level.");
                    }

                    opp.graphics(2232);
                    ProjectileManager.sendGlobalProjectile((Entity) opp,
                            (Entity) p, 2231, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(0)
                                    * 1.25;
                            if (p.getSkills().getLevel(0) >= maxLevel) p.getFrames().sendChatMessage(0,
                                    "Your curse has no effect on your attack as it is too high."
                            );
                            else {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse boosts your attack.");
                                p.getSkills().set(0, p.getSkills().getLevel(0) +
                                        1);
                            }

                            p.graphics(2233);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
        if (leechingStrength) {
            if (this.shieldDelay < 1) p.animate(12575);
            ProjectileManager.sendGlobalProjectile(((Entity) p), (Entity) opp,
                    2231, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(2) >
                            opp.getSkills().getLevelForXp(2)) {
                        double minLevel =
                                opp.getSkills().getLevelForXp(2) * 0.80;
                        if (!(opp.getSkills().getLevel(0) < minLevel)) {
                            opp.getSkills().set(2, opp.getSkills().getLevel(2) - 1);
                            opp.getFrames().sendChatMessage(0,
                                    "The enemy's curse has drained your strength level!");
                        } else opp.getFrames().sendChatMessage(0,
                                "The enemy's curse has no effect on your strength level."
                        );
                    }

                    opp.graphics(2232);
                    ProjectileManager.sendGlobalProjectile((Entity) opp,
                            (Entity) p, 2231, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(2)
                                    * 1.25;
                            if (p.getSkills().getLevel(2) >= maxLevel) p.getFrames().sendChatMessage(0,
                                    "Your curse has no effect on your strength as it is too high."
                            );
                            else {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse boosts your strength.");
                                p.getSkills().set(2, p.getSkills().getLevel(2) +
                                        1);
                            }

                            p.graphics(2233);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
        if (leechingMagic) {
            if (this.shieldDelay < 1) p.animate(12575);
            ProjectileManager.sendGlobalProjectile(((Entity) p), (Entity) opp,
                    2240, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(6) >
                            opp.getSkills().getLevelForXp(6)) {
                        double minLevel =
                                opp.getSkills().getLevelForXp(6) * 0.80;
                        if (!(opp.getSkills().getLevel(0) < minLevel)) {
                            opp.getSkills().set(6, opp.getSkills().getLevel(6) - 1);
                            opp.getFrames().sendChatMessage(0,
                                    "The enemy's curse has drained your magic level!");
                        } else opp.getFrames().sendChatMessage(0,
                                "The enemy's curse has no effect on your magic level."
                        );
                    }

                    opp.graphics(2242);
                    ProjectileManager.sendGlobalProjectile((Entity) opp,
                            (Entity) p, 2240, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(6)
                                    * 1.25;
                            if (p.getSkills().getLevel(6) >= maxLevel) p.getFrames().sendChatMessage(0,
                                    "Your curse has no effect on your magic as it is too high."
                            );
                            else {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse boosts your magic.");
                                p.getSkills().set(6, p.getSkills().getLevel(6) +
                                        1);
                            }

                            p.graphics(2241);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
        if (leechingRange) {
            if (this.shieldDelay < 1) p.animate(12575);
            ProjectileManager.sendGlobalProjectile(((Entity) p), (Entity) opp,
                    2236, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(4) >
                            opp.getSkills().getLevelForXp(4)) {
                        double minLevel =
                                opp.getSkills().getLevelForXp(4) * 0.80;
                        if (!(opp.getSkills().getLevel(0) < minLevel)) {
                            opp.getSkills().set(4, opp.getSkills().getLevel(4) - 1);
                            opp.getFrames().sendChatMessage(0,
                                    "The enemy's curse has drained your range level!");
                        } else opp.getFrames().sendChatMessage(0,
                                "The enemy's curse has no effect on your range level."
                        );
                    }

                    opp.graphics(2238);
                    ProjectileManager.sendGlobalProjectile((Entity) opp,
                            (Entity) p, 2236, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(4)
                                    * 1.25;
                            if (p.getSkills().getLevel(4) >= maxLevel) p.getFrames().sendChatMessage(0,
                                    "Your curse has no effect on your range as it is too high."
                            );
                            else {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse boosts your range.");
                                p.getSkills().set(4, p.getSkills().getLevel(4) +
                                        1);
                            }

                            p.graphics(2237);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
        /*if (leechingRun) {
			if (this.shieldDelay < 1) {
				p.animate(12575);
			}
			ProjectileManager.sendGlobalProjectile(((Entity) p), (Entity) opp,
					2256, 11, 11, 30, 20, 0);
			GameLogicTaskManager.schedule(new GameLogicTask() {
				@Override
				public void run() {
					
					  if(opp.getSkills().getLevel(4) >
					  opp.getSkills().getLevelForXp(4)) { double minLevel =
					  opp.getSkills().getLevelForXp(4) * 0.80;
					  if(!(opp.getSkills().getLevel(0) < minLevel)) {
					  opp.getSkills().set(4, opp.getSkills().getLevel(4) - 1);
					  opp.getFrames().sendChatMessage(0,
					  "The enemy's curse has drained your range level!"); }
					  else { opp.getFrames().sendChatMessage(0,
					  "The enemy's curse has no effect on your range level."
					  ); } }
					 
					opp.graphics(2238);
					ProjectileManager.sendGlobalProjectile((Entity) opp,
							(Entity) p, 2236, 11, 11, 30, 20, 0);
					GameLogicTaskManager.schedule(new GameLogicTask() {
						@Override
						public void run() {
							
							  double maxLevel = p.getSkills().getLevelForXp(4)
							  * 1.25; if(p.getSkills().getLevel(4) >= maxLevel)
							  { p.getFrames().sendChatMessage(0,
							  "Your curse has no effect on your range as it is too high."
							  ); } else { p.getFrames().sendChatMessage(0,
							  "Your curse boosts your range.");
							  p.getSkills().set(4, p.getSkills().getLevel(4) +
							  1); }
							 
							p.graphics(2237);
							this.stop();
						}
					}, 1, 1);
					this.stop();
				}
			}, 2, 1);
		}*/
        if (leechingDef) {
            if (this.shieldDelay < 1) p.animate(12575);
            ProjectileManager.sendGlobalProjectile(((Entity) p), (Entity) opp,
                    2244, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {

                    if (opp.getSkills().getLevel(1) >
                            opp.getSkills().getLevelForXp(1)) {
                        double minLevel =
                                opp.getSkills().getLevelForXp(1) * 0.80;
                        if (!(opp.getSkills().getLevel(1) < minLevel)) {
                            opp.getSkills().set(1, opp.getSkills().getLevel(1) - 1);
                            opp.getFrames().sendChatMessage(0,
                                    "The enemy's curse has drained your strength level!");
                        } else opp.getFrames().sendChatMessage(0,
                                "The enemy's curse has no effect on your strength level."
                        );
                    }

                    opp.graphics(2245);
                    ProjectileManager.sendGlobalProjectile((Entity) opp,
                            (Entity) p, 2244, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {

                            double maxLevel = p.getSkills().getLevelForXp(1)
                                    * 1.25;
                            if (p.getSkills().getLevel(1) >= maxLevel) p.getFrames().sendChatMessage(0,
                                    "Your curse has no effect on your strength as it is too high."
                            );
                            else {
                                p.getFrames().sendChatMessage(0,
                                        "Your curse boosts your defence.");
                                p.getSkills().set(1, p.getSkills().getLevel(1) +
                                        1);
                            }

                            p.graphics(2246);
                            this.stop();
                        }
                    }, 1, 1);
                    this.stop();
                }
            }, 2, 1);
        }
    }

    public void resetDamage() {
        for (int i = 0; i < dealtDamage.length; i++) dealtDamage[i] = -1;
    }

    public void addDamage(int damage, int slot) {
        dealtDamage[slot] += damage;
    }

    public boolean dangerousPVP(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 2919 && absX <= 3029 && absY >= 3307 && absY <= 3438)
                || (absX >= 3029 && absX <= 3068 && absY >= 3329 && absY <= 3385)
                || (absX >= 3093 && absX <= 3117 && absY >= 3919 && absY <= 3946)
                || (absX >= 2940 && absX <= 3395 && absY >= 3520 && absY <= 4000)
                || (absX >= 2816 && absX <= 2884 && absY >= 2940 && absY <= 3008)
                || (absX >= 3048 && absX <= 3135 && absY >= 3468 && absY <= 3522);
    }

    public int getKiller() {
        int killer = -1;
        int killerDamage = -1;
        for (int i = 0; i < dealtDamage.length; i++) if (dealtDamage[i] > killerDamage) killer = i;
        return killer;
    }

    public boolean Multi(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
		/*if(p.getUsername().equals("teh_cops")) {
			return (absX >= 0 && absX <= 9999 && absY >= 0 && absY <= 9999);
		}*/
        return (absX >= 3136 && absX <= 3327 && absY >= 3520 && absY <= 3607)
                || (absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839)

				/*shilo half multi
				|| (absX >= 2817 && absX <= 2881 && absY >= 2943 && absY <= 2969)
				*/
                //edge multi
                || (absX >= 3048 && absX <= 3138 && absY >= 3448 && absY <= 3522)
                // end
                || (absX >= 3095 && absX <= 3314 && absY >= 3583 && absY <= 3708)
                || (absX >= 2951 && absX <= 2976 && absY >= 3362 && absY <= 3391)
                || (absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967)
                || (absX >= 2433 && absX <= 2451 && absY >= 5511 && absY <= 5560)
                || (absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967)
                || (absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831)
                || (absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903)
                || (absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711)
                || (absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647);
    }

    public boolean donatorzone(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 2433 && absX <= 2451 && absY >= 5511 && absY <= 5560);
    }

    public boolean mageArena(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        return (absX >= 3093 && absX <= 3117 && absY >= 3919 && absY <= 3946);
    }


    public boolean isSafe(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        //p.getFrames().sendClickableInterface(381);
        if (absX >= 3093 && absX <= 3117 && absY >= 3919 && absY <= 3946) return false;
        return (absX >= 3201 && absX <= 3227 && absY >= 2301 && absY <= 3235)
                || (absX >= 3080 && absX <= 3128 && absY >= 3912 && absY <= 3955)
                //falador pvp fountin crash fix (currently)
                || (absX >= 3035 && absX <= 3042 && absY >= 3350 && absY <= 3357)
                //Thiev safe
                || (absX >= 3273 && absX <= 3320 && absY >= 2752 && absY <= 2809)
                //end
                //shilo village
                //fally bank safe spot
                || (absX >= 3019 && absX <= 3021 && absY >= 3353 && absY <= 3356)
                //Bank in shilo
                || (absX >= 2843 && absX <= 2861 && absY >= 2953 && absY <= 2955)
                || (absX >= 2851 && absX <= 2853 && absY >= 2951 && absY <= 2952) || (absX == 2845 && absY == 2952) || (absX == 2860 && absY == 2952) || (absX == 2850 && absY == 2952)
                || (absX == 2854 && absY == 2956) || (absX == 2853 && absY == 2957) || (absX == 2852 && absY == 2957) || (absX == 2850 && absY == 2953) || (absX == 2851 && absY == 2952)
                || (absX == 2854 && absY == 2952) || (absX == 2844 && absY == 2952) || (absX == 2844 && absY == 2956) || (absX == 2845 && absY == 2956) || (absX == 2850 && absY == 2956)
                || (absX == 2852 && absY == 2956) || (absX == 2851 && absY == 2956) || (absX == 2859 && absY == 2956) || (absX == 2860 && absY == 2952)
                || (absX == 2860 && absY == 2956) || (absX == 2851 && absY == 2957) || (absX == 2853 && absY == 2956) || (absX == 2853 && absY == 2951)
                //entrance to ladder to slayer master.
                || (absX >= 2869 && absX <= 2872 && absY >= 2966 && absY <= 2972) || (absX == 2868 && absY == 2967) || (absX == 2873 && absY == 2968)
                //Shilo general store
                || (absX >= 2824 && absX <= 2825 && absY >= 2956 && absY <= 2961) || (absX == 2823 && absY == 2960) || (absX == 2823 && absY == 2959) || (absX == 2823 && absY == 2958)
                || (absX == 2823 && absY == 2957) || (absX == 2826 && absY == 2960) || (absX == 2826 && absY == 2959) || (absX == 2826 && absY == 2958) || (absX == 2826 && absY == 2957)
                //PvP safe main area - Quest.
                || (absX >= 2834 && absX <= 2837 && absY >= 2980 && absY <= 2993) || (absX == 2838 && absY == 2981) || (absX == 2839 && absY == 2982) || (absX == 2839 && absY == 2983)
                || (absX == 2839 && absY == 2984) || (absX == 2838 && absY == 2985) || (absX == 2833 && absY == 2982)
                || (absX == 2833 && absY == 2983) || (absX == 2833 && absY == 2984) || (absX == 2838 && absY == 2982) || (absX == 2838 && absY == 2983) || (absX == 2838 && absY == 2984)
                || (absX == 2853 && absY == 2957) || (absX == 2833 && absY == 2985) || (absX == 2832 && absY == 2984)
                || (absX == 2832 && absY == 2983) || (absX == 2832 && absY == 2982) || (absX == 2833 && absY == 2981) || (absX == 2833 && absY == 2990) || (absX == 2833 && absY == 2991) || (absX == 2833 && absY == 2992) || (absX == 2826 && absY == 2957)
                || (absX == 2838 && absY == 2992) || (absX == 2838 && absY == 2991) || (absX == 2838 && absY == 2990)
                //Done
                //end of shilo
                || (absX >= 3091 && absX <= 3098 && absY >= 3488 && absY <= 3499)
                || (absX >= 3147 && absX <= 3182 && absY >= 3473 && absY <= 3505)
                || (absX == 0 && absY == 0)
                || (absX >= 2053 && absX <= 2081 && absY >= 4369 && absY <= 4391)
                || (absX >= 2839 && absX <= 2853 && absY >= 5205 && absY <= 5224)
                || (absX >= 3227 && absX <= 3320 && absY >= 2752 && absY <= 2789)
                || (absX >= 2529 && absX <= 2549 && absY >= 4708 && absY <= 4725)//magebank
                || (absX >= 3264 && absX <= 3279 && absY >= 3672 && absY <= 3695)
                || (absX >= 2943 && absX <= 2950 && absY >= 3368 && absY <= 3373)
                || (absX >= 3264 && absX <= 2950 && absY >= 3672 && absY <= 3373)
                || (absX >= 3009 && absX <= 3018 && absY >= 3353 && absY <= 3358)
                || (absX >= 3179 && absX <= 3194 && absY >= 3432 && absY <= 3446)
                || (absX >= 2606 && absX <= 2616 && absY >= 3088 && absY <= 3097)
//Donatorzone safebits
                || (absX >= 2442 && absX <= 2444 && absY >= 5518 && absY <= 5526)
                || (absX >= 2442 && absX <= 2444 && absY >= 5529 && absY <= 5537);
    }

    public boolean inWild(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        p.getCombatDefinitions().refreshSpecial();
        p.getCombatDefinitions().startGettingSpecialUp();
        return (absX >= 2940 && absX <= 3395 && absY >= 3520 && absY <= 4000);
    }

    public Player killer = null;

    public void setQueueMagic(int opp, int book, int id) {
        this.queuedPlayer = opp;
        this.queuedSpellID = id;
        this.queuedSpellbookId = book;
        this.queuedSet = true;
    }

    private int combatWith;
    public int combatWithDelay;
    private int leechDelay = 0;
    public int logoutDelay = 0;
    public int CombatDelay = 0;
    //public int curseDelay = 0;
    private int ddsDelay = 0;
    public int thievDelay = 0;
    public int DFSdelay = 0;

    public void tick() {
        if (thievDelay > 0) thievDelay--;
        if (DFSdelay > 0) DFSdelay--;
        if (solSpecWait > 0) solSpecWait--;
        if (leechDelay > 0) leechDelay--;
        if (logoutDelay > 0) logoutDelay--;
        if (CombatDelay > 0) CombatDelay--;
        if (ddsDelay > 0) leechDelay--;
        if (DFSdelay == 0) {
            DFS = true;
            DFSdelay = -1;
        }
        if (leechDelay == 0) {
            leechStats((Player) entity, (Player) target);
            leechDelay = 30;
        }
        if (combatWithDelay > 0) combatWithDelay--;
        if (combatWithDelay == 0) combatWith = -1;
        if (vengDelay > 0) vengDelay--;
        if (shieldDelay > 0) shieldDelay--;
        if (freezeDelay > 0) freezeDelay--;
        if (immuneDelay > 0) immuneDelay--;
        if (delay > 0) delay--;
        if (magicDelay > 0) magicDelay--;
        if (queuedSet) attemptCastSpell();
    }

    public void attemptCastSpell() {
        Player p = (Player) entity;
        Player opp = World.getPlayers().get(queuedPlayer);
        String ip = "" + opp.getConnection().getChannel().getRemoteAddress();
        ip = ip.replaceAll("/", "");
        ip = ip.replaceAll(" ", "");
        ip = ip.substring(0, ip.indexOf(":"));
        if (p.getConnection().getChannel().getRemoteAddress().toString().contains(ip)) {
            p.getFrames().sendChatMessage(0, "You can't attack this person on the same ip as you.");
            this.removeTarget();
            queuedSet = false;
            return;
        }
        if (getPlayer().getCombat().delay > 0
                || getPlayer().getCombat().magicDelay > 0) return;
        if (getPlayer() == null || opp == null) return;
        if (p.isDead() || opp.isDead()) return;
        /*if(MagicManager.executeSpell(getPlayer(), opp, "VengOther")) {
		    if (isSafe(getPlayer()) || isSafe(opp) && MagicManager.executeSpell(getPlayer(), opp, "VengOther")) {
            }
        } else {
	        getPlayer().getFrames().sendChatMessage(0, "You can't attack this player in the safezone.");
            p.turnTemporarilyTo(opp);
			this.removeTarget();
			queuedSet = false;
			return;
		}*/
        if (isSafe(getPlayer()) || isSafe(opp)) {
            getPlayer().getFrames().sendChatMessage(0, "You can't attack this player in the safezone.");
            p.turnTo(opp);
            this.removeTarget();
            queuedSet = false;
            return;
        }
        int difference = Math.abs(getPlayer().getSkills().getCombatLevel() - opp.getSkills().getCombatLevel());
        if (difference > 15 || difference < -15) {
            getPlayer().getFrames().sendChatMessage(0, "You can't attack this player at that combat level.");
            this.removeTarget();
            queuedSet = false;
            return;
        }
        if (!Multi(getPlayer()) || !Multi(opp)) {
            if (combatWith != opp.getClientIndex() && combatWith > 0) {
                getPlayer().getFrames().sendChatMessage(0,
                        "I'm already under attack.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
            if (opp.getCombat().combatWith != getPlayer().getClientIndex()
                    && opp.getCombat().combatWith > 0) {
                getPlayer().getFrames().sendChatMessage(0,
                        "This player is already in combat.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
        }
        leechStats(getPlayer(), opp);
        skullPlayer(opp);
        this.target = (Entity) opp;
        opp.getCombat().combatWith = getPlayer().getClientIndex();
        opp.getCombat().combatWithDelay = 12;
        if (getPlayer().AutoCast) castSpell();
        castSpell();
        this.queuedSet = false;
        getPlayer().getCombat().delay = 4;
        magicDelay = 6;
    }

    public Player getPlayer() {
        return (Player) entity;
    }

    /*public void teleBlock() {
    getPlayer().turnTemporarilyTo((Entity) World.getPlayers().get(queuedPlayer));
    Player opp = World.getPlayers().get(queuedPlayer);
    MagicManager.executeSpell(getPlayer(), opp, "TeleBlock");
    }*/
    private void castSpell() {
        getPlayer().turnTemporarilyTo((Entity) World.getPlayers().get(queuedPlayer));
        Player opp = World.getPlayers().get(queuedPlayer);
        Player player = (Player) entity;
        if (opp.isDead() || player.isDead()) return;
        switch (queuedSpellbookId) {
            case 192: // moderns
                switch (queuedSpellID) {
                    case 81:
                    case 79:
                    case 36:
                        MagicManager.executeSpell(getPlayer(), opp, "Entangle");
                        break;
                    case 86:
                        MagicManager.executeSpell(getPlayer(), opp, "TeleBlock");
                        break;
                }
                break;
        }
	/*	switch (queuedSpellbookId) {
		case 430: // moderns
			switch (queuedSpellID) {
		        case 41:
			        MagicManager.executeSpell(getPlayer(), opp, "VengOther");
				break;
		}
		break;
		}  */
        switch (queuedSpellbookId) {
            case 193: // Ancients
                switch (queuedSpellID) {
                    case 21:
                        MagicManager.executeSpell(getPlayer(), opp, "IceBlitz");
                        break;
                    case 23:
                        MagicManager.executeSpell(getPlayer(), opp, "IceBarrage");
                        break;
                    case 25:
                        MagicManager.executeSpell(getPlayer(), opp, "BloodBlitz");
                        break;
                    case 27:
                        MagicManager.executeSpell(getPlayer(), opp, "BloodBarrage");
                        break;
                    case 39:
                        MagicManager.executeSpell(getPlayer(), opp, "MiasmicBarrage");
                        break;
                }
                break;
        }
        //IceBlitz
        if (getPlayer().AutoCastSpell == 85) MagicManager.executeSpell(getPlayer(), opp, "IceBlitz");
        else //BloodBlitz
            if (getPlayer().AutoCastSpell == 83) MagicManager.executeSpell(getPlayer(), opp, "BloodBlitz");
            else //BloodBarrage
                if (getPlayer().AutoCastSpell == 91) MagicManager.executeSpell(getPlayer(), opp, "BloodBarrage");
                else //IceBarrage
                    if (getPlayer().AutoCastSpell == 93) MagicManager.executeSpell(getPlayer(), opp, "IceBarrage");
    }

    public int freezeDelay;
    public int prayerDelay;
    public int immuneDelay;
    private Entity entity;
    private Entity target;

    public Entity getTarget() {
        return target;
    }

    public boolean targetAvailable() {
        return target != null;
    }

    public int delay;
    public boolean vengeance;
    public int vengDelay;
    private byte prayerBeforeHitDelay;
    private byte hitDelay;
    private byte prayerAfterHitDelay;
    private long lastAttackedTime;
    private boolean isProcessing;
    private CombatHitDefinitions combatHitDefinitions;

    private double getMagicAccuracy(Player p, Player opp) {
        final double A = 0.705;
        double atkBonus = p.getCombatDefinitions().bonus[3];
        double defBonus = opp.getCombatDefinitions().bonus[3 + 5];
        if (atkBonus < 1) atkBonus = 0;
        if (defBonus < 1) defBonus = 0;
        double atk = (atkBonus * p.getSkills().level[6]);
        double def = (defBonus * opp.getSkills().level[1]);
        if (opp.getPrayer().usingPrayer(0, 0)) def *= 1.05;
        if (opp.getPrayer().usingPrayer(0, 5)) def *= 1.10;
        if (opp.getPrayer().usingPrayer(0, 13)) def *= 1.15;
        if (opp.getPrayer().usingPrayer(0, 25)) def *= 1.20;
        if (opp.getPrayer().usingPrayer(0, 27)) def *= 1.25;
        if (p.getPrayer().usingPrayer(0, 4)) atk *= 1.05;
        if (p.getPrayer().usingPrayer(0, 12)) atk *= 1.10;
        if (p.getPrayer().usingPrayer(0, 21)) atk *= 1.15;
        if (CombatManager.wearingVoid(p, 11663)) atk *= 1.15;
        return A * (atk / def);
    }

    public int getMagicHit(Player p, Player opp, int hit) {
        double accuracy = getMagicAccuracy(p, opp);
        Random random = new Random();
        int hitBefore = hit;
        p.bonusmagicdmg = opp.magicresist * 2;
        p.target = opp.getUsername().replace("_", " ").toLowerCase();
        if (p != null || opp != null)
            if (p.getEquipment().contains(18346)) if (!opp.getCombat().isSafe(opp)) if (p.TomeTimer == 0) {
                if (opp.magicresist >= 100)
                    p.getFrames().sendChatMessage(0, "You have reached your max bonus damage for magic at " + p.bonusmagicdmg + ".");
                else {
                    opp.magicresist += 5;
                    p.getFrames().sendChatMessage(0, "You reduce your opponents magic resist by " + opp.magicresist + "%.");
                    p.getFrames().sendChatMessage(0, "Your magic damage is increased by " + p.bonusmagicdmg + ".");
                    opp.getFrames().sendChatMessage(0, "Your magic resist has been reduced by " + opp.magicresist + "%.");
                }
                p.TomeTimer = 50;
                p.animate(400);
                opp.animate(399);
            }
        if (opp.getPrayer().usingPrayer(1, 7)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics(2228);
            p.hit(Misc.random(100));
        }
        if (opp.getEquipment().contains(13740)) {
            int prayerLost = (int) Math.ceil(hit * 0.3 * .05);
            if (opp.getSkills().level[5] >= prayerLost) {
                opp.getSkills().level[5] -= prayerLost;
                opp.getFrames().sendSkillLevel(5);
                hit *= 0.7;
            } else opp.getSkills().level[5] = 0;
        }
        if (opp.getEquipment().contains(13742)) if (Misc.random(9) <= 6) hit *= 0.75;
        hit = hitBefore;
        if (opp.magicresist > 90) accuracy = 1;
        if (accuracy > 1.0) accuracy = 1;
        if (hit < 0) hit = 0;
        if (opp.tabbing > 0) hit = 0;
        if (accuracy < random.nextDouble()) return 0;
        else return hit;
    }

    public Combat(Entity entity) {
        this.entity = entity;
        prayerBeforeHitDelay = -1;
        hitDelay = -1;
        prayerAfterHitDelay = -1;
    }

    public void removeTarget() {
        target = null;
        entity.resetTurnTo();
    }

    public void clear() {
        prayerBeforeHitDelay = -1;
        hitDelay = -1;
        prayerAfterHitDelay = -1;
    }

    public boolean hasTarget() {
        return target != null;
    }

    private void processDelays() {
        if (hitDelay > 0) hitDelay--;
        if (entity instanceof Player) {
            Player p = (Player) entity;
            if (prayerBeforeHitDelay > 0)
                prayerBeforeHitDelay--;
            if (prayerAfterHitDelay > 0)
                prayerAfterHitDelay--;
        }
    }

    public void targetEquals() {
        Player opp = (Player) target;
        Player p = (Player) entity;
        if (target == null) {
            p.getFrames().sendChatMessage(0, "Target name was unreachable.");
            return;
        }
        p.getFrames().sendChatMessage(0, "You have been defeated by " + opp.getUsername() + ".");
    }

    private RSTile locMod() {
        int i = Misc.random(3);
        RSTile loc = null;
        if (i == 0) loc = RSTile.createRSTile(entity.getLocation().getX() - 1, entity.getLocation().getY(), 0);
        else if (i == 1) loc = RSTile.createRSTile(entity.getLocation().getX() + 1, entity.getLocation().getY(), 0);
        else if (i == 2) loc = RSTile.createRSTile(entity.getLocation().getX(), entity.getLocation().getY() + 1, 0);
        else if (i == 3) loc = RSTile.createRSTile(entity.getLocation().getX(), entity.getLocation().getY() - 1, 0);
        return loc;
    }

    public void attack(Entity t) {
        this.target = t;
        this.entity.turnTo(this.target);
        if (entity.isDead()) {
            entity.resetTurnTo();
            entity.getWalk().reset(true);
            return;
        }
        if (target.isDead()) {
            entity.resetTurnTo();
            entity.getWalk().reset(true);
            return;
        }
		/*if(entity.getLocation().equals(target.getLocation())) {
			RSTile tile = locMod();
			if(tile != null) {
				if(!tile.isSafe()) {
					entity.resetTurnTo();
					entity.getWalk().reset(true);
					entity.getWalk().addToWalkingQueue(tile.getX(), tile.getY());
				}
			}
		}*/
        if (!isProcessing) {
            isProcessing = true;
            GameLogicTaskManager.schedule(new GameLogicTask() {
                long time;

                @Override
                public void run() {
                    processDelays();
                    processAttack();
                    if (entity instanceof Player)
                        processPrayerBeforeHit();
                    processHit();
                    logoutDelay = 20;
                    CombatDelay = 8;
                    if (entity instanceof Player)
                        processPrayerAfterHit();
                    if (delay == 0 && hitDelay == -1
                            && prayerBeforeHitDelay == -1
                            && prayerAfterHitDelay == -1 && target == null) {
                        this.stop();
                        isProcessing = false;
                    }
                    time = System.currentTimeMillis();
                }

            }, 0, 0, 0);
        }
    }

    public int totalSoul;
    private boolean soulSplitting = false;

    @SuppressWarnings("static-access")
    public void soulSplit(final Player p, final Player opp, final int hit) {
        if (soulSplitting) return;
        if (hit < 1) return;
        Skills.killer = opp;
        if (!p.getPrayer().usingPrayer(1, 18)) return;
        soulSplitting = true;
        try {
            ProjectileManager.sendGlobalProjectile(((Entity) p), (Entity) opp,
                    2263, 11, 11, 30, 20, 0);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {
                    opp.graphics(2264);
                    ProjectileManager.sendGlobalProjectile((Entity) opp,
                            (Entity) p, 2263, 11, 11, 30, 20, 0);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        @Override
                        public void run() {
                            p.getSkills().heal((int) Math.floor(hit / 5));
                            p.getSkills().RestorePray(-1);
                            this.stop();
                        }
                    }, 1, 1);
                    soulSplitting = false;
                    this.stop();
                }
            }, 1, 1);
        } catch (Exception ignored) {

        }

    }

    private void processAttack() {
        Player opp = (Player) target;
        if (target == null) {
            entity.resetTurnTo();
            return;
        }
        Player ptarget = null;
        Npc ntarget = null;
        if (target instanceof Player) ptarget = ((Player) target);
        else ntarget = ((Npc) target);
        if ((ptarget != null && (!ptarget.isOnline()) || target.isDead())) {
            entity.resetTurnTo();
            removeTarget();
            return;
        }
        if (!entity.getLocation().withinDistance(target.getLocation(), 25)) {
            entity.resetTurnTo();
            removeTarget();
            return;
        }
        if (ptarget != null
                && (ptarget.isDead() || System.currentTimeMillis() < (ptarget
                .getCombatDefinitions().getLastEmote() - 600))) {
            entity.resetTurnTo();
            removeTarget();
            return;
        }

        int distance = (int) Math.round(entity.getLocation().getDistance(
                target.getLocation()));
        int entityDistance = ptarget == null ? (byte) ((Npc) target)
                .getNpcdefinition().size : 1;

        if (entity instanceof Player) {
            final Player p = (Player) entity;
            if (p.isDead()
                    || System.currentTimeMillis() < (p.getCombatDefinitions()
                    .getLastEmote() - 600)) {
                removeTarget();
                entity.resetTurnTo();
                return;
            }
            if (p.isMorphed) return;
            int difference = getPlayer().getSkills().getCombatLevel() - ((Player) target).getSkills().getCombatLevel();
            if (difference > 15 || difference < -15) {
                getPlayer().getFrames().sendChatMessage(0, "The difference between your Combat Level and the Combat Level of your opponent is");
                getPlayer().getFrames().sendChatMessage(0, "too great.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
            int weaponId = p.getEquipment().get(3) == null ? -1 : p
                    .getEquipment().get(3).getId();
            if (weaponId > -1)
                entityDistance += CombatManager.distForWeap(weaponId);

            if ((distance == 0 || distance > entityDistance
                    && !rangedWeapon(((Player) entity)))
                    || (distance == 0 || distance > 8
                    && rangedWeapon((Player) entity))) {
                if (this.freezeDelay > 0) return;
                entity.getWalk().reset(true);
                entity.getWalk().addToWalkingQueueFollow(
                        target.getLocation().getX()
                                - (entity.getLocation().getRegionX() - 6) * 8,
                        target.getLocation().getY()
                                - (entity.getLocation().getRegionY() - 6) * 8);
                // removeTarget();
                if ((entity.getWalk().getRunDir() == -1 || distance > 2)
                        && (entity.getWalk().getWalkDir() == -1 || distance > 1))
                    return;
            }
            if (weaponId < 0 || weaponId == -1) return;
            if (p != null || opp != null) if (p.getEquipment().contains(18346)) if (p.TomeTimer == 0) {
                if (opp.magicresist >= 100)
                    p.getFrames().sendChatMessage(0, "You have reached your max bonus damage for magic at " + p.bonusmagicdmg + ".");
                else {
                    opp.magicresist += 5;
                    p.getFrames().sendChatMessage(0, "You reduce your opponents magic resist by " + opp.magicresist + "%.");
                    p.getFrames().sendChatMessage(0, "Your magic damage is increased by " + p.bonusmagicdmg + ".");
                    opp.getFrames().sendChatMessage(0, "Your magic resist has been reduced by " + opp.magicresist + "%.");
                }
                p.TomeTimer = 50;
                p.animate(400);
                opp.animate(399);
            }
            if (p != null || opp != null) if (p.DFSSpecial && p.getEquipment().contains(11284)) {
                if (p.getCombat().isSafe(opp) || p.getCombat().isSafe(p) || opp.getCombat().isSafe(opp) || opp.getCombat().isSafe(p))
                    return;
                if (target.getCombat().combatWith != getPlayer().getClientIndex()
                        && target.getCombat().combatWith > 0) {
                    getPlayer().getFrames().sendChatMessage(0,
                            "This player is already in combat.");
                    this.removeTarget();
                    queuedSet = false;
                    return;
                }

                if (p.DFShit == 0) {
                    p.animate(6696, 0);
                    p.graphics(1165, 0);
                    this.DFSdelay = 2;
                    p.DFShit = 60;
                } else
                    p.getFrames().sendChatMessage(0, "You will need to wait " + p.DFShit + " seconds to use this again.");
            }
            p.DFSSpecial = false;
            if (p != null || opp != null) if (DFS && p.getEquipment().contains(11284)) {
                if (p.getCombat().isSafe(opp) || p.getCombat().isSafe(p) || opp.getCombat().isSafe(opp) || opp.getCombat().isSafe(p))
                    return;
                if (target.getCombat().combatWith != getPlayer().getClientIndex()
                        && target.getCombat().combatWith > 0) {
                    getPlayer().getFrames().sendChatMessage(0,
                            "This player is already in combat.");
                    this.removeTarget();
                    queuedSet = false;
                    return;
                }
                // int weaponID = p.getEquipment().get(3).getId();
                int max = getPlayerMaxHit(0, 4151, CombatManager.isRangingWeapon(4151), false);
                p.graphics(1167, 100);
                ProjectileManager.sendGlobalProjectile(p, target, 1166, 46, 31, 60, 55, 0);
                p.getCombat().appendMeleeDamage(p, opp, Misc.random(max), true);
                p.DFShit = 60;
                DFS = false;
            }
            if (delay > 0)
                return;

            if (entity instanceof Player)
                processPlayer();
        }
    }

    private final int[] RANGED_WEAPONS = {861, 9185, 18357, 15241, 11235, 4214};

    private boolean rangedWeapon(Player p) {
        for (int RANGED_WEAPON : RANGED_WEAPONS) if (p.getEquipment().contains(RANGED_WEAPON)) return true;
        return false;
    }

    private boolean canSpec(Player p, int weapon) {
        int specNeeded = 0;
        int specAmount = p.getCombatDefinitions().getSpecpercentage();
        switch (weapon) {
            case 11696:
                specNeeded = 100;
                break;
            case 6818:
                specNeeded = 0;
                break;
            case 11235:
            case 4587:
            case 11700:
                specNeeded = 60;
                break;
            case 14484:
            case 15241:
            case 11694:
            case 13902:
            case 11698:
            case 4153:
            case 13905:
                specNeeded = 50;
                break;
            case 13899:
            case 1215:
            case 5698:
            case 1305:
                specNeeded = 25;
                break;
        }
        if (specNeeded <= specAmount) {
            p.getCombatDefinitions().specpercentage -= specNeeded;
            p.getCombatDefinitions().setSpecialOff();
            p.getCombatDefinitions().refreshSpecial();
            return true;
        }
        return false;
    }

    private void specialAttack(final Player p, Player opp) {
        int weaponID = p.getEquipment().get(3).getId();
        if (rangedWeapon(p)) {
            if (!this.checkArrows(p)) {
                p.getCombatDefinitions().setSpecialOff();
                p.getCombatDefinitions().refreshSpecial();
                this.removeTarget();
                return;
            }
            int arrows = p.getEquipment().getEquipment().get(13).getId();
            //p.getEquipment().getEquipment().remove(new Item(arrows, 13));
            if (p.getEquipment().getEquipment().contains(new Item(11235))) p.getEquipment().deleteArrow(arrows, 2);
            else p.getEquipment().deleteArrow(arrows, 1);
        }
        if (!canSpec(p, weaponID)) {
            p.getCombatDefinitions().setSpecialOff();
            p.getFrames().sendChatMessage(0,
                    "You don't have enough power left.");
            return;
        }
        int maxHit = getPlayerMaxHit(0, weaponID,
                CombatManager.isRangingWeapon(weaponID), false);
        int hit = Misc.random(450);
        int distanceDelay = getDelayDistance(p, (Player) opp);
        switch (weaponID) {
            case 15241: // 2143 projectile handcannon
                if (this.mageArena(p)) {
                    p.getFrames().sendChatMessage(0, "You can't use range in this arena.");
                    return;
                }
                hit = Misc.random(this.calculateRange());
                p.animate(12175);
                p.graphics(2138);
                ProjectileManager.sendGlobalProjectile(entity, target, 2143, 31,
                        35, 45, 23, 0);
                appendRangeDamage(p, opp, hit, distanceDelay);
                this.delay = 2;
                break;
            case 11235:
                if (this.mageArena(p)) {
                    p.getFrames().sendChatMessage(0, "You can't use range in this arena.");
                    return;
                }
                p.animate(426);
                p.graphics2(1111);
                ProjectileManager.sendGlobalProjectile(entity, target, 1099, 42, 35, 45, 23, 0);
                ProjectileManager.sendGlobalProjectile(entity, target, 1099, 42, 35, 55, 23, 0);
                int max = this.calculateRange();
                bowSpec = true;
                appendDBowSpec(p, opp, hit, distanceDelay, 1100);
                int afterBoost = max + (max / 2) + 5;
                hit = Misc.random(afterBoost);
                appendDBowSpec(p, opp, hit, distanceDelay + 1, 1100);
                break;
            case 14484:
                maxHit *= 1.07;
                if (p.getUsername().equals("maxwell") || p.getUsername().equals("tester_kai")) {
                    p.animate(352);
                    p.graphics(-1);
                    int calcedHit = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    int hit2 = calcedHit / 2;
                    int hit3 = hit2 / 2;
                    int hit4 = hit3 + 10;
                    boolean failed = false;
                    if (calcedHit == 0) {
                        hit2 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit3 = hit2 / 2;
                        hit4 = hit3 + 10;
                    }
                    if (hit2 == 0) {
                        hit3 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit4 = hit3 + 10;
                    }
                    if (hit3 == 0) hit4 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    if (hit2 + hit3 + hit4 == 0) {
                        hit2 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit3 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit4 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    }
                    failed = hit2 + hit3 + hit4 > 470;
                    if (failed && !CombatManager.wearingVoid(p, 11665)) {
                        appendMeleeDamageNoDef(p, opp, 0, true);
                        appendMeleeDamageNoDef(p, opp, 10, true);
                        p.getSkills().sendCounter(10, false);
                    } else {
                        appendMeleeDamageNoDef(p, opp, calcedHit, true);
                        appendMeleeDamageNoDef(p, opp, hit2, true);
                        appendMeleeDamageNoDef(p, opp, hit3, true);
                        appendMeleeDamageNoDef(p, opp, hit4, true);
                        double gained = 0;
                        gained = hit2 + hit3 + hit4;
                        p.getSkills().sendCounter((int) gained, false);
                    }
                } else {
                    p.animate(10961);
                    p.graphics(1950);
                    int calcedHit = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    int hit2 = calcedHit / 2;
                    int hit3 = hit2 / 2;
                    int hit4 = hit3 + 10;
                    boolean failed = false;
                    if (calcedHit == 0) {
                        hit2 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit3 = hit2 / 2;
                        hit4 = hit3 + 10;
                    }
                    if (hit2 == 0) {
                        hit3 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                        hit4 = hit3 + 10;
                    }
                    if (hit3 == 0) hit4 = getMeleeHit(p, opp, Misc.random(maxHit), true);
                    if (hit4 == 0) failed = true;
                    if (failed) {
                        appendMeleeDamageNoDef(p, opp, 0, true);
                        appendMeleeDamageNoDef(p, opp, 10, true);
                    } else {
                        appendMeleeDamageNoDef(p, opp, calcedHit, true);
                        appendMeleeDamageNoDef(p, opp, hit2, true);
                        appendMeleeDamageNoDef(p, opp, hit3, true);
                        appendMeleeDamageNoDef(p, opp, hit4, true);
                    }
                    double gained = 0;
                    gained = hit2 + hit3 + hit4;
                    p.getSkills().sendCounter((int) gained, false);
                }
                break;
            case 13899:
                p.animate(10502);
                maxHit *= 1.20;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 11694:
                if (hit > 700) {
                    p.animate(7074);
                    p.graphics(1222);
                    maxHit *= 1.33;
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                    return;
                }
                p.animate(7074);
                p.graphics(1222);
                maxHit *= 1.33;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 11698:
                maxHit *= 1.25;
                p.animate(7071);
                p.graphics(1220);
                p.getSkills().heal(Misc.random(maxHit) / 2);
                p.getSkills().RestorePray(Misc.random(maxHit) / 4);
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 1305:
                p.animate(1058);
                p.graphics2(248);
                maxHit *= 1.25;
                this.appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 4587:
                p.animate(1872);
                p.graphics2(347);
                maxHit *= 1.35;
                opp.getPrayer().closeAllPrayers();
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 13902:
                maxHit *= 1.30;
                SWHDrain = Misc.random(maxHit) * 0.03;
                p.animate(10505);
                p.graphics(1840);
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                if (SWHDrain < opp.getSkills().getLevel(Skills.DEFENCE))
                    opp.getSkills().set(Skills.DEFENCE, (int) (opp.getSkills().getLevel(Skills.DEFENCE) - SWHDrain));
                else opp.getSkills().set(Skills.DEFENCE, 0);
                break;
            case 11696:
                maxHit *= 1.20;
                p.animate(7073);
                p.graphics(1223);
                BGSDrain = Misc.random(maxHit) * 0.01;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                if (BGSDrain < opp.getSkills().getLevel(Skills.DEFENCE)) opp.getSkills()
                        .set(Skills.DEFENCE,
                                (int) (opp.getSkills().getLevel(Skills.DEFENCE) - BGSDrain));
                else {
                    BGSDrain = BGSDrain - opp.getSkills().getLevel(Skills.DEFENCE);
                    opp.getSkills().set(Skills.DEFENCE, 0);
                    opp.getSkills()
                            .set(Skills.STRENGTH,
                                    (int) (opp.getSkills()
                                            .getLevel(Skills.STRENGTH) - BGSDrain));
                }
                break;
            case 11700:
                maxHit *= 1.20;
                if (hit == 0) {
                    p.animate(7070);
                    opp.graphics(2112);
                    p.getFrames().sendChatMessage(0, "Your special failed.");
                    return;
                }
                p.animate(7070);
                opp.graphics(2111);
                opp.getCombat().freezeDelay = 40;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
            case 13905:
                maxHit *= 1.13;
                p.animate(10499);
                p.graphics2(1835);
                if (hit < 300) {
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                } else {
                    appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                    opp.getFrames().sendClickableInterface(824);
                    p.graphics(1740);
                    opp.graphics(1739);
                    opp.curseDelay = 50;
                    opp.getFrames().sendChatMessage(0, "You have been cursed for 30 seconds.");
                }
                break;
            case 1215:
            case 5698:
                p.animate(0x426);
                p.graphics2(252);
                maxHit *= 1.20;
                appendMeleeDamage(p, opp, Misc.random(maxHit), true);

                appendMeleeDamage(p, opp, Misc.random(maxHit), true);
                break;
        }
    }

    private void processPlayer() {
        try {
            Player opp = (Player) target;
            final Player p = (Player) entity;
            final byte attackStyle = p.getCombatDefinitions().getAttackStyle();
            final int weaponId = p.getEquipment().get(3) == null ? -1 : p
                    .getEquipment().get(3).getId();
            final int ammoId = p.getEquipment().get(13) == null ? -1 : p
                    .getEquipment().get(13).getId();
            int specAmt = CombatManager.getSpecAmt(weaponId);
            final boolean specialOn = p.getCombatDefinitions().isSpecialOn()
                    && p.getCombatDefinitions().getSpecpercentage() >= specAmt;
            int maxHit = getPlayerMaxHit(attackStyle, weaponId,
                    CombatManager.isRangingWeapon(weaponId), specialOn);
            int speed = CombatManager.getSpeedForWeapon(weaponId);
            int distance = Math.round(entity.getLocation().getDistance(
                    target.getLocation())) > 3 ? 2 : 1;
            if (target.getLocation().equals(entity.getLocation()))
                entity.getWalk().addStepToWalkingQueue(target.getLocation().getX() + 1, target.getLocation().getY());
            if (isSafe(p) || isSafe((Player) target)) {
                p.getFrames()
                        .sendChatMessage(0,
                                "This person is the the SafeZone.");
                this.removeTarget();
                return;
            }
            if (!Multi(getPlayer()) || !Multi((Player) target)) {
                if (combatWith != target.getClientIndex() && combatWith > 0) {
                    getPlayer().getFrames().sendChatMessage(0,
                            "I'm already under attack.");
                    this.removeTarget();
                    queuedSet = false;
                    return;
                }
                if (target.getCombat().combatWith != getPlayer().getClientIndex()
                        && target.getCombat().combatWith > 0) {
                    getPlayer().getFrames().sendChatMessage(0,
                            "This player is already in combat.");
                    this.removeTarget();
                    queuedSet = false;
                    return;
                }
            }
            //Player opp = World.getPlayers().get(queuedPlayer);
            int difference = getPlayer().getSkills().getCombatLevel() - ((Player) target).getSkills().getCombatLevel();
		/*if (difference < 0) {
			difference = +difference;
		}*/
            if (difference > 15 || difference < -15) {
                getPlayer().getFrames().sendChatMessage(0, "The difference between your Combat Level and the Combat Level of your opponent is");
                getPlayer().getFrames().sendChatMessage(0, "too great.");
                this.removeTarget();
                queuedSet = false;
                return;
            }
            target.getCombat().combatWith = getPlayer().getClientIndex();
            target.getCombat().combatWithDelay = 12;
            skullPlayer((Player) target);
            this.delay += (byte) speed;
            leechStats(getPlayer(), (Player) target);
            if (p.getCombatDefinitions().isSpecialOn()) {
                specialAttack(p, (Player) target);
                return;
            }
            if (rangedWeapon(p)) {
                int arrows = p.getEquipment().getEquipment().get(13).getId();
                if (!checkArrows(p)) return;
                p.getWalk().reset(true);
                //p.getEquipment().getEquipment().remove(new Item(arrows, 13));
                if (p.getEquipment().getEquipment().contains(new Item(11235))) p.getEquipment().deleteArrow(arrows, 2);
                else p.getEquipment().deleteArrow(arrows, 1);
                rangedAttack(p, target, specialOn);
                return;
            }
            sendPlayerAttackEmote(specialOn, weaponId);
            sendPlayerAttackGraphic(specialOn, weaponId, ammoId, target);
            appendMeleeDamage(p, (Player) target, Misc.random(maxHit), false);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private int getDrawbackID(Player p) {
        int arrowId = p.getEquipment().get(3).getId();
        switch (arrowId) {
            case 882:
            case 883:
                return 19;
            case 884:
            case 885:
                return 18;
            case 886:
            case 887:
                return 20;
            case 888:
            case 889:
                return 21;
            case 890:
            case 891:
                return 22;
            case 892:
            case 893:
                return 24;
            default:
                return 18;
        }
    }

    public int getProjectileID(Player p) {
        int arrowId = p.getEquipment().get(3).getId();
        switch (arrowId) {
            case 882:
            case 883:
                return 11;
            case 884:
            case 885:
                return 10;
            case 886:
            case 887:
                return 12;
            case 888:
            case 889:
                return 13;
            case 890:
            case 891:
                return 14;
            case 892:
            case 893:
                return 17;
            case 9051:
                return 17;
            default:
                return 18;
        }
    }

    private int getDelayDistance(Player p, Player opp) {
        int dist = Misc.getDistance(p.getLocation().getX(), p.getLocation()
                .getY(), opp.getLocation().getX(), opp.getLocation().getY());
        int delay = 2;
        if (dist > 2) delay += 1;
        return delay;
    }

    private void appendRangeDamage(final Player p, final Player opp,
                                   int hitBefore, int cycleDelay) {
        if (opp.getPrayer().usingPrayer(0, 18)) hitBefore = (int) (hitBefore * 0.4);
        p.getCombat().shieldDelay = 1;
        final int hit = getRangeHit(p, opp, hitBefore, false);
        if (hit > opp.getSkills().getHitPoints()) {
            opp.getCombat().removeTarget();
            opp.getCombat().delay = 15;
            opp.getCombat().shieldDelay = 0;
        }
        if (hit > 0)
            p.getSkills().sendCounter(hit, false);
        soulSplit(p, opp, hit);
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                if (opp.getCombat().shieldDelay == 0) opp.animate(CombatManager.getDefenceEmote(opp));
                opp.hit(hit, p);
                if (opp.getCombat().vengeance) if (hit > 0) {
                    applyVengeance(
                            p.getSkills().isDead() ? 0 : Math
                                    .floor(hit * 0.75), p, opp);
                    p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Taste vengeance!"));
                    p.getMask().setChatUpdate(true);
                }
                this.stop();
            }
        }, cycleDelay - 1, 0);
    }

    private void appendDBowSpec(final Player p, final Player opp,
                                int hitBefore, int cycleDelay, final int end) {
        if (opp.getPrayer().usingPrayer(0, 18)) hitBefore = (int) (hitBefore * 0.4);
        p.getCombat().shieldDelay = 1;
        int hit = getRangeHit(p, opp, hitBefore, false);
        final int hit2 = hit;
        if (hit < 80) hit = 80;
        soulSplit(p, opp, hit);
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                if (opp.getCombat().shieldDelay == 0) opp.animate(CombatManager.getDefenceEmote(opp));
                opp.hit(hit2, p);
                opp.graphics2(end);
                if (opp.getCombat().vengeance) if (hit2 > 0) applyVengeance(
                        p.getSkills().isDead() ? 0 : Math
                                .floor(hit2 * 0.75), p, opp);
                this.stop();
            }
        }, cycleDelay - 1, 0);
    }

    public void applyVengeance(final double hit, final Player p,
                               final Player opp) {
        opp.getMask().setLastChatMessage(
                new ChatMessage(0, 0, "Taste vengeance!"));
        opp.getMask().setChatUpdate(true);
        opp.getCombat().vengeance = false;
        //opp.getCombat().removeTarget();
        if (hit > opp.getSkills().getHitPoints()) opp.getCombat().shieldDelay = 0;
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                p.hit((int) hit, opp);
                this.stop();
            }
        }, 0, 0);
    }

    private void appendMeleeDamage(final Player p, final Player opp,
                                   int hitBefore, boolean spec) {
        if (opp.getPrayer().usingPrayer(0, 19)) hitBefore = (int) (hitBefore * 0.4);
        else if (opp.getPrayer().usingPrayer(1, 9)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics2(2230);
            //hitBefore(Misc.random(100));
        }
        p.getCombat().shieldDelay = 1;
        final int hit = getMeleeHit(p, opp, hitBefore, spec);
        if (hit > 0)
            p.getSkills().sendCounter(hit, false);
        if (opp.getCombat().pendingDamage() + hit > opp.getSkills()
                .getHitPoints()) {
            opp.getCombat().removeTarget();
            opp.getCombat().shieldDelay = 0;
        }
        soulSplit(p, opp, hit);
        if (opp.getCombat().shieldDelay == 0) opp.animate(CombatManager.getDefenceEmote(opp));
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.hit(hit, p);
                if (opp.getCombat().vengeance) if (hit > 0) applyVengeance(
                        p.getSkills().isDead() ? 0 : Math
                                .floor(hit * 0.75), p, opp);
                this.stop();
            }
        }, 0, 0);
    }

    private int pendingDamage() {
        Player p = (Player) entity;
        int totalDamage = 0;
        for (Hit h : p.getQueuedHits()) totalDamage += h.getDamage();
        return totalDamage;
    }

    private void appendMeleeDamageNoDef(final Player p, final Player opp,
                                        int hitBefore, boolean spec) {
        if (opp.getPrayer().usingPrayer(0, 19)) hitBefore = (int) (hitBefore * 0.4);
        else if (opp.getPrayer().usingPrayer(1, 9)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics2(2230);
        }
        p.getCombat().shieldDelay = 1;
        final int hit = hitBefore;
        if (opp.getCombat().pendingDamage() + hit > opp.getSkills()
                .getHitPoints()) {
            opp.getCombat().removeTarget();
            opp.getCombat().shieldDelay = 0;
        }
        soulSplit(p, opp, hit);
        if (opp.getCombat().shieldDelay == 0) opp.animate(CombatManager.getDefenceEmote(opp));
        GameLogicTaskManager.schedule(new GameLogicTask() {
            @Override
            public void run() {
                opp.hit(hit);
                if (opp.getCombat().vengeance) if (hit > 0) applyVengeance(
                        p.getSkills().isDead() ? 0 : Math
                                .floor(hit * 0.75), p, opp);
                this.stop();
            }
        }, 0, 0);
    }

    private double getRangeAccuracy(Player p, Player opp, boolean spec) {
        final double A = 0.705;
        double atkBonus = p.getCombatDefinitions().bonus[4];
        double defBonus = opp.getCombatDefinitions().bonus[4 + 5];
        if (atkBonus < 1) atkBonus = 0;
        if (defBonus < 1) defBonus = 0;
        double atk = (atkBonus * p.getSkills().level[4]);
        double def = (defBonus * opp.getSkills().level[1]);
        if (CombatManager.wearingVoid(p, 11664)) atk *= 1.15;
        if (p.getPrayer().usingPrayer(0, 3)) atk *= 1.05;
        if (p.getPrayer().usingPrayer(0, 11)) atk *= 1.10;
        if (p.getPrayer().usingPrayer(0, 20)) atk *= 1.15;
		/*if (p.getPrayer().usingPrayer(0, 28)) {
			atk *= 1.20;
			def *= 1.25;
		}*/
        if (opp.getPrayer().usingPrayer(0, 0)) def *= 1.05;
        if (opp.getPrayer().usingPrayer(0, 5)) def *= 1.10;
        if (opp.getPrayer().usingPrayer(0, 13)) def *= 1.15;
        if (opp.getPrayer().usingPrayer(0, 25)) def *= 1.20;
        if (opp.getPrayer().usingPrayer(0, 27)) def *= 1.25;
        if (boltEffect) {
            atk *= 1.25;
            boltEffect = false;
        }
        if (bowSpec) {
            atk *= 1.40;
            bowSpec = false;
        }
        return A * (atk / def);
    }

    private int getRangeHit(Player p, Player opp, int hit, boolean spec) {
        double accuracy = getRangeAccuracy(p, opp, spec);
        Random random = new Random();
        int hitBefore = hit;
        if (opp.getPrayer().usingPrayer(1, 8)) {
            hitBefore = (int) (hitBefore * 0.4);
            if (opp.getCombat().shieldDelay == 0)
                opp.animate(12573);
            opp.graphics(2229);
            p.hit(Misc.random(70));
        }
        if (opp.getEquipment().contains(13740)) {
            int prayerLost = (int) Math.ceil(hit * 0.3 * .05);
            if (opp.getSkills().level[5] >= prayerLost) {
                opp.getSkills().level[5] -= prayerLost;
                opp.getFrames().sendSkillLevel(5);
                hit *= 0.7;
            } else opp.getSkills().level[5] = 0;
        }
        if (opp.getEquipment().contains(13742)) if (Misc.random(9) <= 6) hit *= 0.75;
        hit = hitBefore;
        if (accuracy > 1.0) accuracy = 1;
        if (opp.tabbing > 0) hit = 0;
        if (accuracy < random.nextDouble()) return 0;
        else return hit;
    }

    private int getMeleeHit(Player p, Player opp, int hit, boolean spec) {
        double accuracy = getMeleeAccuracy(p, opp, spec);
        Random random = new Random();
		/*int hitBefore = hit;
		if (opp.getPrayer().usingPrayer(1, 8)) {
			hitBefore = (int) (hitBefore * 0.4);
			if (opp.getCombat().shieldDelay == 0)
			opp.animate(12573);
			opp.graphics(2227);
			p.hit(Misc.random(100));
		}*/
        if (accuracy > 1.0) accuracy = 1;
        if (hit < 0) hit = 0;
        if (opp.getEquipment().contains(13740)) {
            int prayerLost = (int) Math.ceil(hit * 0.3 * .05);
            if (opp.getSkills().level[5] >= prayerLost) {
                opp.getSkills().level[5] -= prayerLost;
                opp.getFrames().sendSkillLevel(5);
                hit *= 0.7;
            } else opp.getSkills().level[5] = 0;
        }
        if (opp.getEquipment().contains(13742)) if (Misc.random(9) <= 6) hit *= 0.75;
        if (opp.tabbing > 0) hit = 0;
        if (accuracy < random.nextDouble()) return 0;
        else return hit;
    }

    private double getMeleeAccuracy(Player p, Player opp, boolean spec) {
        try {
            final double A = 0.755;
            int bonus = 1;
            double atkBonus = p.getCombatDefinitions().bonus[bonus];
            double defBonus = opp.getCombatDefinitions().bonus[bonus + 5];
            if (atkBonus < 1) atkBonus = 0;
            if (defBonus < 1) defBonus = 0;
            double atk = (atkBonus * p.getSkills().level[0]);
            double def = (defBonus * opp.getSkills().level[1]);
            atk += 2;
            if (CombatManager.wearingVoid(p, 11665)) atk *= 1.45;

            if (spec) switch (p.getEquipment().get(3).getId()) {
                case 4153:
                    atk *= 1.05;
                    break;
                case 13902:
                    atk *= 1.65;
                    break;
                case 11235:
                    atk *= 1.55;
                    break;
                case 13905:
                case 1419:
                case 7806:
                    atk *= 1.25;
                    break;
                case 11694:
                    atk *= 1.60;
                    break;
                case 14484:
                    atk *= 1.18;
                    break;
                case 11700:
                case 11698:
                case 11696:
                    atk *= 1.60;
                    break;
                case 1215:
                case 5698:
                    atk *= 1.80;
                    break;
                case 4587:
                case 1305:
                    atk *= 1.60;
                    break;
                case 13899:
                    atk *= 1.50;
                    break;
            }
            else switch (p.getEquipment().get(3).getId()) {
                case 18349:
                    atk *= 1.35;
            }
            if (p.getPrayer().usingPrayer(0, 2)) atk *= 1.05;
            if (p.getPrayer().usingPrayer(0, 7)) atk *= 1.10;
            if (opp.getPrayer().usingPrayer(0, 0)) def *= 1.05;
            if (opp.getPrayer().usingPrayer(0, 5)) def *= 1.10;
            if (opp.getPrayer().usingPrayer(0, 13)) def *= 1.15;
            if (opp.getPrayer().usingPrayer(0, 25)) def *= 1.20;
            if (opp.getPrayer().usingPrayer(0, 27)) def *= 1.25;

            return A * (atk / def);
        } catch (Exception ignored) {

        }
        return -1;
    }

    private void rangedAttack(Player p, Entity opp, boolean usingSpec) {
        int weaponID = p.getEquipment().get(3) == null ? -1 : p.getEquipment()
                .get(3).getId();
        Player opponant = (Player) opp;
        int hit = Misc.random(this.calculateRange());
        int distanceDelay = getDelayDistance(p, (Player) opp);
        if (!this.checkArrows(p)) return;
        if (this.mageArena(p)) {
            p.getFrames().sendChatMessage(0, "You can't use range in this arena.");
            return;
        }
        switch (weaponID) {
            case 11235:
                p.animate(426);
                p.graphics2(1111);
                ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35,
                        45, 23, 0);
                ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35,
                        20, 23, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);
                appendRangeDamage(p, opponant, Misc.random(this.calculateRange()), distanceDelay + 1);
                break;
            case 15241:
                p.animate(12174);
                p.graphics(2138);
                ProjectileManager.sendGlobalProjectile(entity, target, 2143, 31,
                        35, 45, 23, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);
                break;
            case 839:
            case 841:
            case 843:
            case 845:
            case 847:
            case 849:
            case 851:
            case 853:
            case 859:
            case 861:
                p.animate(426);
                p.graphics2(getDrawbackID(p));
                ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35,
                        45, 23, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);
                break;
            case 9185:
            case 18357:
                if (Misc.random(25) == 17) {
                    int arrows = p.getEquipment().get(13) == null ? -1 : p
                            .getEquipment().get(13).getId();
                    switch (arrows) {
                        case 9244:
                            opp.graphics(756);
                            boltEffect = true;
                            hit *= 1.40;
                    }
                }
                p.animate(4230);
                ProjectileManager.sendGlobalProjectile(entity, target, 27, 42, 35,
                        48, 18, 0);
                appendRangeDamage(p, opponant, hit, distanceDelay);
                break;
        }
    }

    private void processHit() {
        if (combatHitDefinitions == null)
            return;
        if (hitDelay != 0)
            return;
        Player p = (Player) entity;
        Entity target = combatHitDefinitions.getTarget();
        if (target instanceof Player) {
            if (!((Player) target).isOnline() || target.isDead()) {
                if (prayerAfterHitDelay == -1)
                    combatHitDefinitions = null;
                return;
            }
            ((Player) target).getCombat().setLastAttackedTime(
                    System.currentTimeMillis());
        }
        if (target.isDead()) return;
		
		/*
		 * Prayer while hitting
		 */
        int hit1 = PlayerProbOfHiting(target,
                combatHitDefinitions.getMaxDamage(),
                combatHitDefinitions.getBonuses(),
                combatHitDefinitions.getWeaponId(),
                combatHitDefinitions.isSpecialOn()) ? combatHitDefinitions
                .getMaxDamage() : 0;
        if (combatHitDefinitions.isMeleeDeflectPray()
                || combatHitDefinitions.isRangeDeflectPray()) {
            if (hit1 * 10 / 100 > 0)
                entity.hit(hit1 * 10 / 100);
            target.animate(12573);
            entity.graphics(combatHitDefinitions.isMeleeDeflectPray() ? 2230
                    : 2229); //target
        } else if (combatHitDefinitions.isSoulSplitPray())
            ProjectileManager.sendGlobalProjectile(entity, target, 2263, 11,
                    11, 30, 20, 0);
        if (combatHitDefinitions.isLeechAttack()
                || combatHitDefinitions.isLeechRanged()
                || combatHitDefinitions.isLeechDefence()
                || combatHitDefinitions.isLeechStrength())
            ProjectileManager
                    // atk = 2231
                    // range = 2236 defence 2244 mage = 2248
                    .sendGlobalProjectile(
                            target,
                            entity,
                            combatHitDefinitions.isLeechAttack() ? 2231
                                    : (combatHitDefinitions.isLeechRanged() ? 2236
                                    : (combatHitDefinitions
                                    .isLeechDefence() ? 2244
                                    : 2248)), 30, 30, 30, 0, 0);
        else if (combatHitDefinitions.isTurmoilPray())
            ((Player) target).getPrayer().setBoost(8, true);
		
		
		/*
		 * Defence Animation
		 */
        if (!combatHitDefinitions.isMeleeDeflectPray()
                && !combatHitDefinitions.isRangeDeflectPray())
            target.animate(CombatManager.getDefenceEmote(target));

		/*
		 * Hit
		 */
		
		/*
		 * Divine and Ely Effect
		 */

        if (combatHitDefinitions.isSpecialOn()
                && combatHitDefinitions.getWeaponId() == 14484) {
            int totalDamage = 0;
            int hit2 = hit1 == 0 ? (PlayerProbOfHiting(target,
                    combatHitDefinitions.getMaxDamage(),
                    combatHitDefinitions.getBonuses(),
                    combatHitDefinitions.getWeaponId(),
                    combatHitDefinitions.isSpecialOn()) ? combatHitDefinitions
                    .getMaxDamage() : 0) : hit1;
            if (hit1 == 0 && hit2 == 0) {
                int hit3 = PlayerProbOfHiting(target,
                        combatHitDefinitions.getMaxDamage(),
                        combatHitDefinitions.getBonuses(),
                        combatHitDefinitions.getWeaponId(),
                        combatHitDefinitions.isSpecialOn()) ? combatHitDefinitions
                        .getMaxDamage() : 0;
                if (hit3 == 0) {
                    int hit4 = PlayerProbOfHiting(target,
                            combatHitDefinitions.getMaxDamage(),
                            combatHitDefinitions.getBonuses(),
                            combatHitDefinitions.getWeaponId(),
                            combatHitDefinitions.isSpecialOn()) ? combatHitDefinitions
                            .getMaxDamage() : 0;
                    if (hit4 == 0) {
                        target.hit(hit2);
                        target.hit(hit1);
                        target.hit(1);
                        target.hit(hit3);
                        totalDamage = 1;
                    } else {
                        target.hit(hit2);
                        target.hit(hit1);
                        target.hit((int) (hit4 * 1.5));
                        target.hit(hit3);
                        totalDamage = (int) (hit4 * 1.5);
                    }
                } else {
                    target.hit(hit2);
                    target.hit(hit1);
                    target.hit(hit3);
                    target.hit(hit3);
                    totalDamage = hit3 * 2;
                }
            } else {
                target.hit(hit1 == 0 ? hit2 : hit2 / 2);
                target.hit(hit1);
                target.hit(hit2 / 4);
                target.hit(hit1 == 0 ? hit2 / 2 : hit2 / 4);
                totalDamage = (hit1 == 0 ? hit2 : hit2 / 2) + hit1
                        + (hit1 == 0 ? hit2 / 2 : hit2 / 4) + (hit2 / 4);
            }
            combatHitDefinitions.setMaxDamage(totalDamage);
        } else {
            target.hit(hit1);
            combatHitDefinitions.setMaxDamage(hit1);
        }
        hitDelay = -1;
        if (prayerAfterHitDelay == -1)
            combatHitDefinitions = null;
    }

    private boolean PlayerProbOfHiting(Entity target, int attackStyle,
                                       short[] bonus, int weaponId, boolean specialOn) {
        Player p = (Player) entity;
        if (target instanceof Player) { // TODO
            Player enemy = (Player) target;
            double att = bonus[1] + p.getSkills().getLevel(Skills.ATTACK);
            if (specialOn) {
                double multiplier = 0.25;
                multiplier += CombatManager
                        .getSpecDamageDoublePercentage(weaponId) / 2;
                att = att * multiplier;
            }
            double def = enemy.getCombatDefinitions().getBonus()[6]
                    + enemy.getSkills().getLevel(Skills.DEFENCE);
            double prob = att / def;
            if (prob > 0.70)
                prob = 0.70;
            return prob >= Math.random();
        }
        return true;
    }

    private void processPrayerBeforeHit() {
        if (combatHitDefinitions == null)
            return;
        if (prayerBeforeHitDelay != 0)
            return;
        Entity target = combatHitDefinitions.getTarget();
        if (target instanceof Player)
            if (!((Player) target).isOnline() || target.isDead()) {
            }

    }

    private void processPrayerAfterHit() {
        if (combatHitDefinitions == null)
            return;
        if (prayerAfterHitDelay != 0)
            return;
        Entity target = combatHitDefinitions.getTarget();
        if (target instanceof Player) if (!((Player) target).isOnline() || target.isDead()) {
            combatHitDefinitions = null;
            return;
        }

        if (combatHitDefinitions.isSoulSplitPray()) {
            int gain = combatHitDefinitions.getMaxDamage() / 50;
            ((Player) entity).getSkills().heal(gain * 10);
            if (target instanceof Player)
                ((Player) target).getSkills().drainPray(gain);
            target.graphics(2264);
            ProjectileManager.sendGlobalProjectile(target, entity, 2263, 11,
                    11, 30, 0, 0);
        }
        if (combatHitDefinitions.isSapWarrior()) {
			/*boolean bost = ((Player) entity).getPrayer().usingBoost(0);
			((Player) target).getSkills().set(
					0,
					((Player) target).getSkills().getLevelForXp(0)
							* (bost ? 80 : 90) / 100);
			((Player) target).getSkills().set(
					2,
					((Player) target).getSkills().getLevelForXp(2)
							* (bost ? 80 : 90) / 100);
			((Player) target).getSkills().set(
					1,
					((Player) target).getSkills().getLevelForXp(1)
							* (bost ? 80 : 90) / 100);
			target.graphics(2216);
			((Player) entity).getPrayer().setBoost(0, true);*/
        }
        if (combatHitDefinitions.isSapRanger()) {
			/*boolean bost = ((Player) entity).getPrayer().usingBoost(1);
			((Player) target).getSkills().set(
					4,
					((Player) target).getSkills().getLevelForXp(4)
							* (bost ? 80 : 90) / 100);
			((Player) target).getSkills().set(
					1,
					((Player) target).getSkills().getLevelForXp(1)
							* (bost ? 80 : 90) / 100);
			target.graphics(2219);
			((Player) entity).getPrayer().setBoost(1, true);*/
        }
        if (combatHitDefinitions.isSapSpirit()) target.graphics(2225);
        if (combatHitDefinitions.isLeechAttack()) {
            boolean bost = ((Player) entity).getPrayer().usingBoost(3);
            ((Player) target).getSkills().set(
                    0,
                    ((Player) target).getSkills().getLevelForXp(0)
                            * (bost ? 75 : 90) / 100);
            target.graphics(2232);
            ((Player) entity).getPrayer().setBoost(3, true);
        }
        if (combatHitDefinitions.isLeechRanged()) {
            boolean bost = ((Player) entity).getPrayer().usingBoost(5);
            ((Player) target).getSkills().set(
                    4,
                    ((Player) target).getSkills().getLevelForXp(4)
                            * (bost ? 75 : 90) / 100);
            target.graphics(2238);
            ((Player) entity).getPrayer().setBoost(5, true);
        }
        if (combatHitDefinitions.isLeechDefence()) {
            boolean bost = ((Player) entity).getPrayer().usingBoost(6);
            ((Player) target).getSkills().set(
                    1,
                    ((Player) target).getSkills().getLevelForXp(1)
                            * (bost ? 75 : 90) / 100);
            target.graphics(2246);
            ((Player) entity).getPrayer().setBoost(6, true);
        }
        if (combatHitDefinitions.isLeechStrength()) {
            boolean bost = ((Player) entity).getPrayer().usingBoost(7);
            ((Player) target).getSkills().set(
                    2,
                    ((Player) target).getSkills().getLevelForXp(2)
                            * (bost ? 75 : 90) / 100);
            target.graphics(2250);
            ((Player) entity).getPrayer().setBoost(7, true);
        }

        prayerAfterHitDelay = -1;
        combatHitDefinitions = null;
    }

    private void sendPlayerHit(boolean specialon, final int weaponId,
                               final int maxHit, final int distance, final Entity target) {
        int randomDamage = Misc.random(maxHit);
        if (specialon) {
            int p1 = maxHit * 5 / 10;
            double m1 = 1.25 + CombatManager
                    .getSpecDamageDoublePercentage(weaponId);
            if (p1 > randomDamage && m1 > Math.random()) {
                int d1 = maxHit - p1;
                randomDamage = p1 + Misc.random(d1);
            }
        }
        final boolean protectPray = randomDamage > 0
                && (target instanceof Player && (CombatManager
                .isRangingWeapon(weaponId)
                && (((Player) target).getPrayer().usingPrayer(0, 18)) || ((Player) target)
                .getPrayer().usingPrayer(0, 19)));
        final boolean sapWarrior = randomDamage > 0
                && !CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player && ((Player) entity)
                .getPrayer().usingPrayer(1, 1)) && Misc.random(14) == 1;
        if (protectPray)
            randomDamage = target instanceof Player ? randomDamage * 60 / 100
                    : 0;
        final boolean sapRanger = randomDamage > 0
                && CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player && ((Player) entity)
                .getPrayer().usingPrayer(1, 2)) && Misc.random(14) == 1;
        final boolean sapSpirit = !sapRanger
                && !sapWarrior
                && randomDamage > 0
                && (target instanceof Player && entity instanceof Player && ((Player) entity)
                .getPrayer().usingPrayer(1, 4)) && Misc.random(14) == 1;
        final boolean leechAttack = randomDamage > 0
                && !CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player && ((Player) entity)
                .getPrayer().usingPrayer(1, 10))
                && Misc.random(14) == 1;
        final boolean leechRanged = randomDamage > 0
                && CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player && ((Player) entity)
                .getPrayer().usingPrayer(1, 11))
                && Misc.random(14) == 1;
        final boolean leechDefence = !leechAttack
                && !leechRanged
                && randomDamage > 0
                && (target instanceof Player && entity instanceof Player && ((Player) entity)
                .getPrayer().usingPrayer(1, 13))
                && Misc.random(14) == 1;
        final boolean leechStrength = !leechAttack
                && !leechDefence
                && randomDamage > 0
                && !CombatManager.isRangingWeapon(weaponId)
                && (target instanceof Player && entity instanceof Player && ((Player) entity)
                .getPrayer().usingPrayer(1, 14))
                && Misc.random(14) == 1;
        final boolean rangeDeflectPray = randomDamage > 0
                && (target instanceof Player
                && CombatManager.isRangingWeapon(weaponId) && ((Player) target)
                .getPrayer().usingPrayer(1, 8));
        final boolean meleeDeflectPray = randomDamage > 0
                && (target instanceof Player
                && !CombatManager.isRangingWeapon(weaponId) && ((Player) target)
                .getPrayer().usingPrayer(1, 9));
        if (rangeDeflectPray || meleeDeflectPray)
            randomDamage = target instanceof Player ? randomDamage * 60 / 100
                    : 0;
        final boolean soulSplitPray = (entity instanceof Player && ((Player) entity)
                .getPrayer().usingPrayer(1, 18));
        final boolean turmoilPray = (target instanceof Player
                && ((Player) target).getPrayer().usingPrayer(1, 19) && !((Player) target)
                .getPrayer().usingBoost(8));


        if (sapWarrior || sapRanger || sapSpirit) {
            if (sapWarrior) {
                entity.animate(12569);
                target.graphics(2214);
            }
            if (sapRanger) {
                entity.animate(12569);
                target.graphics(2217);
            }
            if (sapSpirit) {
                entity.animate(12569);
                target.graphics(2223);
            }
        }
        combatHitDefinitions = new CombatHitDefinitions(target, weaponId,
                randomDamage, specialon, ((Player) entity)
                .getCombatDefinitions().getBonus(), meleeDeflectPray,
                rangeDeflectPray, protectPray, soulSplitPray, sapWarrior,
                sapRanger, sapSpirit, leechAttack, leechRanged, leechDefence,
                leechStrength, turmoilPray);
        hitDelay = (weaponId == 4153 && specialon) ? 0 : (byte) distance;

    }

    public CombatHitDefinitions getHitDefinitions() {
        return combatHitDefinitions;
    }


    private int getPlayerMaxHit(int attackStyle, int weaponId,
                                boolean isRanging, boolean specialOn) {
        Player p = (Player) entity;
        if (!isRanging) {
            int StrengthLvl = p.getSkills().getLevel(Skills.STRENGTH);
            double PrayerBonus = 1;
            if (p.getPrayer().usingPrayer(0, 1))
                PrayerBonus = 1.05;
            else if (p.getPrayer().usingPrayer(0, 6))
                PrayerBonus = 1.1;
            else if (p.getPrayer().usingPrayer(0, 14))
                PrayerBonus = 1.15;
            else if (p.getPrayer().usingPrayer(0, 25))
                PrayerBonus = 1.18;
            else if (p.getPrayer().usingPrayer(0, 26))
                PrayerBonus = 1.23;
            else if (p.getPrayer().usingPrayer(1, 19))
                PrayerBonus = (p.getPrayer().usingBoost(8) && target instanceof Player) ? 1.26 + (((Player) target)
                        .getSkills().getLevelForXp(Skills.STRENGTH) / 1000)
                        : 1.23;
            double OtherBonus = 1;
            int StyleBonus = 0;
            if (attackStyle == 0)
                StyleBonus = 0;
            else if (attackStyle == 2)
                StyleBonus = 1;
            else if (attackStyle == 3)
                StyleBonus = 3;
            double EffectiveStrenght = Math.round(StrengthLvl * PrayerBonus
                    * OtherBonus)
                    + StyleBonus;
            int StrengthBonus = p.getCombatDefinitions().getBonus()[11];
            double BaseDamage = 15 + EffectiveStrenght + (StrengthBonus / 8)
                    + (EffectiveStrenght * StrengthBonus / 64);
            double finaldamage;
            if (specialOn)
                finaldamage = Math.floor(BaseDamage * CombatManager.getSpecDamageDoublePercentage(weaponId));
            else
                finaldamage = Math.round(BaseDamage);
            if (CombatManager.wearingDharok(p))
                finaldamage += ((p.getSkills().getLevelForXp(3) * 10) - p
                        .getSkills().getHitPoints()) / 2;
            //Sol stuff - Canownueasy
            if (target != null) if (target instanceof Player) {
                Player defender = (Player) target;
                Item wep = defender.getEquipment().get(Equipment.SLOT_WEAPON);
                if (wep != null) if (wep.getId() == 15486 && target.getCombat().solSpecWait > 0) {
                    finaldamage = (finaldamage / 2);
                    target.graphics(2320);
                }
            }
            return (int) finaldamage;
        }
        return 0;
    }

    private void sendPlayerAttackEmote(boolean specialon, int weaponId) {
        Player p = (Player) entity;
        byte attackStyle = p.getCombatDefinitions().getAttackStyle();
        if (weaponId == -1) {
            this.entity.animate(attackStyle == 2 ? 422 : 422);
            return;
        }
        String itemName = new Item(weaponId).getDefinition().name;
        if (!specialon) if (itemName.contains("scimitar"))
            this.entity.animate(12029);
        else if (itemName.contains("Chaotic rapier"))
            this.entity.animate(12310);
        else if (itemName.contains("Rapier"))
            this.entity.animate(417);
        else if (itemName.contains("longsword"))
            this.entity.animate(12029);
        else if (itemName.contains("whip"))
            this.entity.animate(1658);
        else if (itemName.contains("Anger sword"))
            this.entity.animate(1655);
        else if (itemName.contains("Statius's warhammer"))
            this.entity.animate(401);
        else if (itemName.contains("2h sword"))
            this.entity.animate(418);
        else if (itemName.contains("claws"))
            this.entity.animate(393);
        else if (weaponId == 6528 || itemName.contains("Chaotic maul"))
            this.entity.animate(2661);
        else if (itemName.contains("pickaxe"))
            this.entity.animate(attackStyle == 2 ? 402 : 401);
        else if (itemName.contains("Dharok"))
            this.entity.animate(attackStyle == 2 ? 2067 : 2066);
        else if (itemName.contains("Verac"))
            this.entity.animate(2062);
        else if (itemName.contains("Guthan"))
            this.entity.animate(attackStyle == 2 ? 2081 : 2080);
        else if (itemName.contains("godsword") || itemName.contains("Saradomin sword"))
            this.entity.animate(attackStyle == 2 ? 7048 : 7041);
        else if (itemName.contains("Keris") || itemName.contains("dagger"))
            this.entity.animate(attackStyle == 2 ? 401 : 402);
        else if (itemName.contains("Karil"))
            this.entity.animate(2075);
        else if (itemName.contains("Scythe") || itemName.contains("scythe"))
            this.entity.animate(437);
        else if (itemName.contains("halberd"))
            this.entity.animate(440);
        else if (itemName.contains("Staff of light") || itemName.contains("spear") || itemName.contains("Staff") || itemName.contains("staff"))
            this.entity.animate(419);
        else if (itemName.contains("Bow-sword"))
            this.entity.animate(12028);
        else if (!itemName.contains("Karil")
                && itemName.contains("crossbow"))
            this.entity.animate(4230);
        else if (itemName.contains("dart"))
            this.entity.animate(582);
        else if (itemName.contains("knife"))
            this.entity.animate(806);
        else if (itemName.contains("bow"))
            this.entity.animate(426);
        else {
            if (weaponId == 14484) {
                this.entity.animate(10961);
                this.entity.graphics(1950);
            } else if (weaponId == 11694) {
                this.entity.animate(7074);
                this.entity.graphics(1222);
            } else if (weaponId == 4153) this.entity.animate(1667);
            this.entity.animate(426);
        }
    }

    private void sendPlayerAttackGraphic(boolean specialon, int weaponId,
                                         int ammoId, Entity target) {
        if (weaponId == -1)
            return;
        String itemName = new Item(weaponId).getDefinition().name;
        if (!specialon) if (weaponId == 868) entity.graphics(225, 100 << 16);
        else if (weaponId == 867) entity.graphics(224, 100 << 16);
        else if (weaponId == 866) entity.graphics(223, 100 << 16);
        else if (weaponId == 865) entity.graphics(222, 100 << 16);
        else if (weaponId == 864) entity.graphics(221, 100 << 16);
        else if (weaponId == 863) entity.graphics(220, 100 << 16);
        else if (weaponId == 4214) {
            entity.graphics(250, 100 << 16);
            ProjectileManager.sendGlobalProjectile(entity, target, 249, 36, 40, 41, 15, 0);
        } else if (itemName.contains("bow")) if (ammoId == 882) {
            entity.graphics(19, 100 << 16);
            ProjectileManager.sendGlobalProjectile(entity, target, 10, 36, 40, 41, 15, 0);
        } else if (ammoId == 884) {
            entity.graphics(18, 100 << 16);
            ProjectileManager.sendGlobalProjectile(entity, target, 11,
                    36, 40, 41, 15, 0);
        } else if (ammoId == 886) {
            entity.graphics(20, 100 << 16);
            ProjectileManager.sendGlobalProjectile(entity, target, 12, 36, 40, 41, 15, 0);
        } else if (ammoId == 9706) {
            entity.graphics(25, 100 << 16);
            ProjectileManager.sendGlobalProjectile(entity, target, 25, 36, 40, 41, 15, 0);
        } else if (ammoId == 888) {
            entity.graphics(21, 100 << 16);
            ProjectileManager.sendGlobalProjectile(entity, target, 13, 36, 40, 41, 15, 0);
        } else if (ammoId == 890) {
            entity.graphics(22, 100 << 16);
            ProjectileManager.sendGlobalProjectile(entity, target, 14, 36, 40, 41, 15, 0);
        } else if (ammoId == 892) {
            entity.graphics(24, 100 << 16);
            ProjectileManager.sendGlobalProjectile(entity, target, 15, 36, 40, 41, 15, 0);
        }
        else if (weaponId == 14484) this.entity.graphics(1950);
        else if (weaponId == 11694) this.entity.graphics(1222);
        else if (weaponId == 4153) this.entity.graphics(340);
    }

    private void setLastAttackedTime(long lastAttackedTime) {
        this.lastAttackedTime = lastAttackedTime;
    }

    public long getLastAttackedTime() {
        return lastAttackedTime;
    }

}
