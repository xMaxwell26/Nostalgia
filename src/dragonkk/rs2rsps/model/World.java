package dragonkk.rs2rsps.model;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.clan.Clan;
import dragonkk.rs2rsps.model.player.clan.ClanManager;
import dragonkk.rs2rsps.model.shops.ShopManager;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.util.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static dragonkk.rs2rsps.util.Serializer.appendData;


public class World {

    private static EntityList<Player> players;
    private static EntityList<Npc> npcs;
    private static HashMap<Integer, Long> ips;
    private static ShopManager shopmanager;
    public static ClanManager clanManager;
    public static int serverKills;
    public static long before, after;
    private Clan currentClan;

    public static EntityList<Player> getPlayers() {
        synchronized (players) {
            return players;
        }
    }


    public World() {
        clanManager = new ClanManager();
        shopmanager = new ShopManager();
        players = new EntityList<>(Constants.MAX_AMT_OF_PLAYERS);
        npcs = new EntityList<>(Constants.MAX_AMT_OF_NPCS);
        ips = new HashMap<>(Constants.MAX_AMT_OF_IPS);
        getNpcs().add(new Npc((short) 660, RSTile.createRSTile(3184, 3432), 0, 0, 0, 0));
        getNpcs().add(new Npc((short) 661, RSTile.createRSTile(3187, 3432), 0, 0, 0, 0));
        getNpcs().add(new Npc((short) 12180, RSTile.createRSTile(2443, 5519), 0, 0, 0, 0));
    }


    public static void registerConnection(ConnectionHandler p) {
        if (players.add(p.getPlayer())) {
            p.getPlayer().LoadPlayer(p);
            MYSQL.updatePlayers();
            //Region.reset();
            String name = Misc.formatPlayerNameForDisplay(p.getPlayer().getUsername().replaceAll("_", " "));
            System.out.println("[" + name + "] has logged in.");
            // DatabaseFunctions.online();
            String ip = "" + p.getPlayer().getConnection().getChannel().getRemoteAddress();
            ip = ip.replaceAll("/", "");
            ip = ip.replaceAll(" ", "");
            ip = ip.substring(0, ip.indexOf(":"));
            appendData("logs/ips/" + p.getPlayer().getUsername() + ".txt", new GregorianCalendar().getTime() + ": " + p.getPlayer().getUsername() + " Logged in on IP:" + p.getPlayer().getConnection().getChannel().getRemoteAddress());
            appendData("logs/ips2/" + ip + ".txt", new GregorianCalendar().getTime() + ": " + p.getPlayer().getUsername() + " Logged in on IP:" + p.getPlayer().getConnection().getChannel().getRemoteAddress());
        }
    }

    public void setCurrentClan(Clan currentClan) {
        this.currentClan = currentClan;
    }

    public Clan getCurrentClan() {
        return currentClan;
    }

    public static int seconds, minutes, hours, days;

    public static void updateUptime() {
        long milliseconds = System.currentTimeMillis() - Server.UPTIME;
        seconds = (int) (milliseconds / 1000) % 60;
        minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        //System.out.println("Uptime" +seconds+ "second" +minutes+ "minutes" +hours+ "hours"); // for debugging purposes
    }


    private static String EventipList = "./data/eventips.txt";

    public static boolean EventIpsContain(String host) {
        BufferedReader list;
        try {
            list = new BufferedReader(new FileReader(EventipList));
        } catch (Exception e) {
            System.out.println("Event IP list error.");
            return false;
        }
        String line;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading Event IP list.");
        }
        return false;
    }

    public static boolean Eventip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(EventipList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    private static String GlitchipList = "./data/glitchlist.txt";

    public static boolean GlitchIpsContain(String username) {
        BufferedReader list;
        try {
            list = new BufferedReader(new FileReader(GlitchipList));
        } catch (Exception e) {
            System.out.println("Glitch IP list error.");
            return false;
        }
        String line;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(username)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading Glitch IP list.");
        }
        return false;
    }

    public static boolean Glitchip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(GlitchipList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    private static String StarteripList = "./data/starterips.txt";

    public static boolean StarterIpsContain(String host) {
        BufferedReader list;
        try {
            list = new BufferedReader(new FileReader(StarteripList));
        } catch (Exception e) {
            System.out.println("Starter IP list error.");
            return false;
        }
        String line;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading Starter IP list.");
        }
        return false;
    }

    public static boolean Starterip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(StarteripList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }


    private static String BannedipList = "./data/bannedips.txt";

    public static boolean BannedIpsContain(String host) {
        BufferedReader list;
        try {
            list = new BufferedReader(new FileReader(BannedipList));
        } catch (Exception e) {
            System.out.println("[Banned ips]: Failed to load bannedips.txt. File might be missing.");
            return false;
        }
        String line;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Banned ips]: Error with loading banned ips!");
        }
        return false;
    }

    public static boolean Banip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(BannedipList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    private static String PermbannedipList = "./data/PermipedList.txt";

    public static boolean PermbannedIpsContain(String host) {
        BufferedReader list;
        try {
            list = new BufferedReader(new FileReader(PermbannedipList));
        } catch (Exception e) {
            System.out.println("[Banned ips]: Failed to load bannedips.txt. File might be missing.");
            return false;
        }
        String line;
        try {
            while ((line = list.readLine()) != null) {
                if (line.equals(host)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Banned ips]: Error with loading banned ips!");
        }
        return false;
    }

    public static boolean Destroyip(String ip) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(PermbannedipList, true));
            bw.write(ip);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignored) {
                }
            }

        }
        return false;
    }

    public static void unRegisterConnection(final ConnectionHandler p) {
        String name = Misc.formatPlayerNameForDisplay(p.getPlayer().getUsername().replaceAll("_", " "));
        System.out.println("[" + name + "] has logged out.");
        appendData("logs/ips/" + p.getPlayer().getUsername() + ".txt", new GregorianCalendar().getTime() + ": " + p.getPlayer().getUsername() + " Logged out on IP:" + p.getPlayer().getConnection().getChannel().getRemoteAddress());
        final Player player = p.getPlayer();
        if (player.getTradeSession() != null) {
            player.getTradeSession().tradeFailed();
        } else if (player.getTradePartner() != null) {
            player.getTradePartner().getTradeSession().tradeFailed();
        }
        if (player == null) {
            player.getConnection().getChannel().close();
            MYSQL.updatePlayers();
            return;
        }
        if (player.getSkills().xLogProtection
                || player.getSkills().getHitPoints() < 1
                || player.getCombat().combatWithDelay > 0
                || player.getCombat().delay > 0) {
            GameLogicTaskManager.schedule(new GameLogicTask() {

                public void run() {
                    removePlayer(player);
                    this.stop();

                }

            }, 60, 0);
        } else {
            removePlayer(player);
        }
        MYSQL.updatePlayers();
    }

    public static void removePlayer(Player p) {
        Serializer.SaveAccount(p);
        //p.extraAutoSave();
        p.getFrames().updateFriendsList(false);
        Server.onlinePlayers.remove(p.getUsername());
        p.setOnline(false);
        players.remove(p);
        for (Player p2 : players) {
            if (p2.getFriends().contains(
                    Misc.formatPlayerNameForDisplay(p.getUsername()))) {
                p2.UpdateFriendStatus(
                        Misc.formatPlayerNameForDisplay(p.getUsername()),
                        (short) 0, false);
            }
        }
        if (p.getConnection().getChannel() != null
                || !p.getConnection().isDisconnected()) {
            p.getConnection().getChannel().close();
        }
        p = null;
    }


    public static boolean isOnline(String Username) {
        for (Player p : players)
            if (p.getUsername().equals(Username))
                if (p.isOnline())
                    return true;

        return false;
    }

    public static boolean isOnList(String Username) {
        for (Player p : players)
            if (p.getUsername().equals(Username))
                return true;

        return false;
    }

    public static ClanManager getClanManager() {
        return clanManager;
    }

    public static boolean isOnList(Player player) {
        return players.contains(player);
    }


    //public static boolean isOnList(Player player) {
    //return players.contains(player);
    // }


    public static EntityList<Npc> getNpcs() {
        synchronized (npcs) {
            return npcs;
        }
    }

    public static HashMap<Integer, Long> getIps() {
        synchronized (ips) {
            return ips;
        }
    }

    /**
     * @return the shopmanager
     */
    public static ShopManager getShopmanager() {
        return shopmanager;
    }

}