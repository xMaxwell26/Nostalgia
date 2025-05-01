package dragonkk.rs2rsps.update;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.Player;

public class GameLogic implements Runnable {

    private final static boolean[] playerUpdates = new boolean[2048];

    public void run() {
        while (true) {
            long startTick = System.currentTimeMillis();
            Server.getWorldExecutor().purge();
            Server.getEntityExecutor().purge();
            World.updateUptime();
            //World.getGlobaldropmanager().processGlobalItemTimers();
            long startLoopTime = System.currentTimeMillis();
            for (Npc npc : World.getNpcs()) {
                if (npc == null) continue;
                synchronized (npc) {
                    npc.getWalk().getNextEntityMovement();
                }
            }
            GameLogicTaskManager.processTasks();
            for (Player player : World.getPlayers()) {
                if (player == null) continue;
                synchronized (player) {
                    if (player.isOnline()) player.getWalk().getNextEntityMovement();
                }
            }
            try {
                for (int index = 0; index < 2048; index++) {
                    Player player = World.getPlayers().get(index);
                    if (player == null) continue;
                    synchronized (player) {

                        if (player.isOnline()) {
                            try {
                                player.getCombat().tick();
                            } catch (Exception ignored) {
                            }
                            player.tick();
                            player.processQueuedHits();
                            playerUpdates[index] = player.getMask().isUpdateNeeded();
                        }
                    }
                }
                for (Player player : World.getPlayers()) {
                    if (player == null)
                        continue;
                    synchronized (player) {
                        if (player.isOnline()) {
                            player.getGpi().sendUpdate();
                            player.getGni().sendUpdate();
                            checkAttackOption(player);
                            donatorzone(player);
                            staffzone(player);
                        }
                    }
                }
                long endTick = System.currentTimeMillis();
                Server.tickTimer = endTick - startTick;
                for (Npc npc : World.getNpcs()) {
                    if (npc == null)
                        continue;
                    synchronized (npc) {
                        npc.getMask().reset();
                    }
                }
                for (int index = 0; index < 2048; index++) {
                    if (!playerUpdates[index])
                        continue;
                    playerUpdates[index] = false;
                    Player player = World.getPlayers().get(index);
                    if (player == null)
                        continue;
                    synchronized (player) {
                        if (player.isOnline()) player.getMask().reset();
                    }
                }
                if (World.hours == 2 && World.minutes == 49 && World.seconds == 50) for (Player d : World.getPlayers())
                    d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 30 minutes.");
                else if (World.hours == 3 && World.minutes == 9 && World.seconds == 50)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 10 minutes.");
                else if (World.hours == 3 && World.minutes == 14 && World.seconds == 50)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 5 minutes.");
                else if (World.hours == 3 && World.minutes == 18 && World.seconds == 20)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 1 minute and 30 seconds.");
                else if (World.hours == 3 && World.minutes == 18 && World.seconds == 50)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 1 minute.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 20)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 30 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 40)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 10 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 41)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 9 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 52)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 8 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 43)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 7 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 44)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 6 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 45)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 5 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 46)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 4 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 47)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 3 seconds.");
                else if (World.hours == 3 && World.minutes == 19 && World.seconds == 48)
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 2 seconds.");
                else //System.exit(0);
                    if (World.hours == 3 && World.minutes == 19 && World.seconds == 49)
                        for (Player d : World.getPlayers())
                            d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server auto restart in 1 seconds.");
                    else if (World.hours == 3 && World.minutes == 19 && World.seconds == 50) {
                    for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=3>Server is restarting...");
                    System.exit(0);
                }
                if (Server.autoMessageSet && Server.serverMessage == 0) {
                    Server.messageTimer--;
                    if (Server.messageTimer == 0) {
                        for (Player d : World.getPlayers())
                            d.getFrames().sendChatMessage(0, "<col=ff0000><img=2>Server messages are in Update -> GameLogic.java!");
                        Server.messageTimer = 1200;
                    } else if (Server.messageTimer == 200) for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=2>Server messages are in Update -> GameLogic.java!");
                    else if (Server.messageTimer == 400) for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=2>Server messages are in Update -> GameLogic.java!");
                    else if (Server.messageTimer == 600) for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=2>Server messages are in Update -> GameLogic.java!");
                    else if (Server.messageTimer == 800) for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=2>Server messages are in Update -> GameLogic.java!");
                    else if (Server.messageTimer == 1000) for (Player d : World.getPlayers())
                        d.getFrames().sendChatMessage(0, "<col=ff0000><img=2>Server messages are in Update -> GameLogic.java!");
                }
                long sleepTime = startLoopTime + 600 - System.currentTimeMillis();
                if (sleepTime > 0) try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Error e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean[] getPlayerUpdates() {
        return playerUpdates;
    }

    public void donatorzone(final Player player) {
        int absX = player.getLocation().getX();
        int absY = player.getLocation().getY();
        if (!player.isDonator && absX >= 2433 && absX <= 2451 && absY >= 5511 && absY <= 5560) {
            player.getCombatDefinitions().doEmote(6064, 1034, 5600);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                int count = 0;

                public void run() {
                    if (!player.isOnline()) {
                        this.stop();
                        return;
                    }
                    if (count++ == 0) {
                        player.getFrames().sendChatMessage(0, "You are not allowed in this area.");
                        player.getMask().getRegion().teleport(3186, 3440, 0, 0);
                    } else this.stop();
                }
            }, 6, 0, 0);
            return;
        }
        if (absX >= 2433 && absX <= 2451 && absY >= 5511 && absY <= 5560)
            player.getCombatDefinitions().specpercentage = 100;
    }

    private void staffzone(final Player player) {
        int absX = player.getLocation().getX();
        int absY = player.getLocation().getY();
        if (player.getRights() == 0 && !player.isForumMod &&
                absX >= 2054 && absX <= 2084 && absY >= 4366 && absY <= 4396) {
            player.getCombatDefinitions().doEmote(6064, 1034, 5600);
            GameLogicTaskManager.schedule(new GameLogicTask() {
                int count = 0;

                public void run() {
                    if (!player.isOnline()) {
                        this.stop();
                        return;
                    }
                    if (count++ == 0) {
                        player.getFrames().sendChatMessage(0, "You are not allowed in this area.");
                        player.getMask().getRegion().teleport(3186, 3440, 0, 0);
                    } else this.stop();
                }
            }, 6, 0, 0);
        }
    }


    private void checkAttackOption(Player player) {
        if (player.getCombat().isSafe(player) && player.getCombat().Multi(player)) {
            player.getFrames().sendPlayerOption("Null", 1, false);
            player.getFrames().sendPlayerOption("<col=ff0000><shad=ff0000>Follow", 2, false);
            player.getFrames().sendPlayerOption("<col=ff0000><shad=ffffff>Trade", 3, false);
            player.getFrames().sendInterfaceConfig(745, 1, true);
            player.getFrames().sendInterfaceConfig(745, 3, false);
            player.getFrames().sendInterfaceConfig(745, 6, true);
        } else if (player.getCombat().Multi(player)) {
            player.getFrames().sendInterfaceConfig(745, 1, false);
            player.getFrames().sendInterfaceConfig(745, 3, true);
            player.getFrames().sendInterfaceConfig(745, 6, false);
            player.getFrames().sendPlayerOption("Attack", 1, false);
            player.getFrames().sendPlayerOption("Follow", 2, false);
            player.getFrames().sendPlayerOption("Null", 3, false);
        } else if (!player.getCombat().isSafe(player)) {
            player.getFrames().sendInterfaceConfig(745, 1, true);
            player.getFrames().sendInterfaceConfig(745, 3, true);
            player.getFrames().sendInterfaceConfig(745, 6, false);
            player.getFrames().sendPlayerOption("Attack", 1, false);
            player.getFrames().sendPlayerOption("Follow", 2, false);
            player.getFrames().sendPlayerOption("Null", 3, false);
        } else {
            player.getFrames().sendPlayerOption("Null", 1, false);
            player.getFrames().sendPlayerOption("<col=ff0000><shad=ff0000>Follow", 2, false);
            player.getFrames().sendPlayerOption("<col=ff0000><shad=ffffff>Trade", 3, false);
            player.getFrames().sendInterfaceConfig(745, 1, true);
            player.getFrames().sendInterfaceConfig(745, 3, false);
            player.getFrames().sendInterfaceConfig(745, 6, true);
        }
    }
}
