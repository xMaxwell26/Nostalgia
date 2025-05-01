package dragonkk.rs2rsps.scripts.interfaces;


import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.OpenCB;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i496 extends interfaceScript {
    @Override
    public void actionButton(final Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (p.isDead()) {
            return;
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
            return;
        }
        if (p.getCombat().CombatDelay > 0) {
            p.getFrames().sendChatMessage(0, "You must not hit your opponent for 10 seconds to do this.");
            return;
        }
        if (p.page == 0) {
            p.getFrames().sendString("Page 1: Select a location", 496, 3);
            p.getFrames().sendString("<col=33ff00>Home (Safe)", 496, 4);
            p.getFrames().sendString("Shilo Quest PvP (Unsafe)", 496, 5);
            p.getFrames().sendString("<col=01DFD7>[New] MageArena (Unsafe)", 496, 6);
            p.getFrames().sendString("<col=33ff00>DiceZone (Safe)", 496, 7);
            p.getFrames().sendString("<col=33ff00>ChillZone (Safe)", 496, 8);
            p.getFrames().sendString("<col=33ff00>StaffZone (Safe)", 496, 9);
            p.getFrames().sendString("<col=33ff00>Thiev (Safe)", 496, 10);
            p.getFrames().sendString("Shilo Bank PvP (Unsafe)", 496, 11);
            p.getFrames().sendString("", 496, 12);
            p.getFrames().sendString("<col=ffffff><shad=ff0000>Next page", 496, 13);
            switch (buttonId) {
                case 14:
                    p.animate(-1);
                    break;
                case 4:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0) {
                                p.getMask().getRegion().teleport(3186, 3440, 0, 0);
                            } else {
                                this.stop();
                            }
                        }
                    }, 6, 0, 0);
                    break;
                case 5:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0)
                                p.getMask().getRegion().teleport(2835, 2983, 0, 0);
                            else {
                                this.stop();
                            }
                        }

                    }, 6, 0, 0);
                    break;
                case 6:
                    OpenCB.close(p);
                    if (Math.random() * 100 >= 50) {
                        p.getMask().getRegion().teleport(3085, 3934, 0, 0);
                    } else {
                        p.getMask().getRegion().teleport(3085, 3933, 0, 0);
                    }
                    p.animate(-1);
                    break;
                case 7:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0) {
                                p.getMask().getRegion().teleport(3164, 3484, 0, 0);
                            } else {
                                this.stop();
                            }
                        }
                    }, 6, 0, 0);
                    break;
                case 8:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0) {
                                p.getMask().getRegion().teleport(2845, 5221, 0, 0);
                            } else {
                                this.stop();
                            }
                        }
                    }, 6, 0, 0);
                    break;
                case 9:
                    if (p.getRights() == 0 && !p.isForumMod) {
                        p.getFrames().sendChatMessage(0, "You need to be a staff member to go to the staff zone.");
                        return;
                    }
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0) {
                                p.getMask().getRegion().teleport(2064, 4385, 0, 0);
                            } else {
                                this.stop();
                            }
                        }
                    }, 6, 0, 0);
                    break;
                case 10:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0) {
                                p.getMask().getRegion().teleport(3194, 3441, 0, 0);
                            } else {
                                this.stop();
                            }
                        }
                    }, 6, 0, 0);
                    break;
                case 11:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0) {
                                p.getMask().getRegion().teleport(2852, 2952, 0, 0);
                            } else {
                                this.stop();
                            }
                        }
                    }, 6, 0, 0);
                    break;
                case 13:
                    p.page = 1;
                    OpenCB.open(p);
                    break;
            }
        } else if (p.page == 1) {
            p.getFrames().sendString("Page 2: Select a location", 496, 3);
            p.getFrames().sendString("<col=33ff00>DonatorZone (Multi/safe)", 496, 4);
            p.getFrames().sendString("Easts (Unsafe)", 496, 5);
            p.getFrames().sendString("Clanwars (Unsafe/multi)", 496, 6);
            p.getFrames().sendString("Edgeville (Unsafe/multi)", 496, 7);
            p.getFrames().sendString("Falador PvP (Unsafe)", 496, 8);
            p.getFrames().sendString("Suggest more", 496, 9);
            p.getFrames().sendString("", 496, 10);
            p.getFrames().sendString("", 496, 11);
            p.getFrames().sendString("", 496, 12);
            p.getFrames().sendString("<col=ffffff><shad=ff0000>Back", 496, 13);
            switch (buttonId) {
                case 4:
                    OpenCB.close(p);
                    if (!p.isDonator) {
                        p.getFrames().sendChatMessage(0, "You are not a donator so you can't do this command.");
                    } else {
                        if (Math.random() * 100 >= 50) {
                            p.getMask().getRegion().teleport(2443, 5529, 0, 0);
                            p.getFrames().sendChatMessage(0, "You were teleported randomly to the north train.");
                        } else {
                            p.getMask().getRegion().teleport(2443, 5526, 0, 0);
                            p.getFrames().sendChatMessage(0, "You were teleported randomly to the south train.");
                        }
                        p.animate(-1);
                    }
                    break;
                case 14:
                    p.animate(-1);
                    break;
                case 5:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0)
                                p.getMask().getRegion().teleport(3355, 3682, 0, 0);
                            else {
                                this.stop();
                            }
                        }

                    }, 6, 0, 0);
                    break;
                case 6:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0)
                                p.getMask().getRegion().teleport(3275, 3681, 0, 0);
                            else {
                                this.stop();
                            }
                        }

                    }, 6, 0, 0);
                    break;
                case 7:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0)
                                p.getMask().getRegion().teleport(3092, 3494, 0, 0);
                            else {
                                this.stop();
                            }
                        }

                    }, 6, 0, 0);
                    break;
                case 8:
                    OpenCB.close(p);
                    p.getCombatDefinitions().doEmote(6064, 1034, 5600);
                    GameLogicTaskManager.schedule(new GameLogicTask() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (!p.isOnline()) {
                                this.stop();
                                return;
                            }
                            if (count++ == 0)
                                p.getMask().getRegion().teleport(3013, 3356, 0, 0);
                            else {
                                this.stop();
                            }
                        }

                    }, 6, 0, 0);
                    break;
                case 9:
                    //p.getFrames().sendChatMessage(0, "Please go to www.spawnscape.org and suggest new updates.");
                    p.getFrames().sendChatMessage(0, "Website down"); // TODO
                    break;
                case 13:
                    p.page = 0;
                    OpenCB.open(p);
                    break;
            }
        }
    }
}