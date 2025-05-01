package dragonkk.rs2rsps;

import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.events.TaskManager;
import dragonkk.rs2rsps.model.GlobalDropManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.CommandManager;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.net.ServerChannelHandler;
import dragonkk.rs2rsps.rscache.Cache;
import dragonkk.rs2rsps.skills.combat.MagicManager;
import dragonkk.rs2rsps.update.GameLogic;
import dragonkk.rs2rsps.util.Logger;
import dragonkk.rs2rsps.util.MYSQL;
import dragonkk.rs2rsps.util.MapData;
import dragonkk.rs2rsps.util.Serializer;

import java.util.ArrayList;
import java.util.List;


public class Server {

    public static int updateTime = -1;
    private static final TaskManager entityExecutor = new TaskManager();
    private static final TaskManager worldExecutor = new TaskManager();
    public static MYSQL database = null;
    public static boolean autoMessageSet = true;
    public static int messageTimer = 250;
    public static int worldId = 1;
    public static boolean voteDisabled = false;
    public static List<String> onlinePlayers = new ArrayList<>();
    public static long tickTimer;
    public static Player lastMute = null;
    public static int serverMessage = 0;
    public static int message = 0;
    public static int muteSystem = 0;
    public static long UPTIME;

    @SuppressWarnings("unchecked")
    public Server() {
        UPTIME = System.currentTimeMillis();
        voteDisabled = true; //change to 1 to disable
        updateTime = 0;
        if (!voteDisabled) { // TODO
            Logger.log(this, "Attempting to connect to the Nostalgia Website Database.");
            database = new MYSQL();
        } else {
            Logger.log(this, "Vote system is currently disabled, please turn on (Server.java).");
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                for (Player p : World.getPlayers()) {
                    if (p != null) {
                        if (p.getTradeSession() != null) {
                            p.getTradeSession().tradeFailed();
                        } else if (p.getTradePartner() != null) {
                            p.getTradePartner().getTradeSession().tradeFailed();
                        }
                        Serializer.SaveAccount(p);
                        World.getClanManager().SaveClan();
                    }
                }
            }
        });
        Logger.log(this, "Loading commands");
        new CommandManager();
        Logger.log(this, "Starting loading Cache...");
        new Cache();
        Logger.log(this, "Starting loading MapData...");
        new MapData();
        Logger.log(this, "Starting loading Packets...");
        new Packets();
        Logger.log(this, "Loading magic spells...");
        new MagicManager();
        Logger.log(this, "Custom drops loaded...");
        new GlobalDropManager();
        Logger.log(this, "Starting loading World...");
        new World();
        worldId = 1;
        Logger.log(this, "Starting loading Game Logic TaskManager...");
        new GameLogicTaskManager();
        Logger.log(this, "Starting Game Logic Thread...");
        new Thread(new GameLogic()).start();
        Logger.log(this, "Starting Server Channel Handler...");
        new ServerChannelHandler();
        Logger.log(this, "Server Finished Loading.");
    }

    public static void main(String[] args) {
        new Server();
    }

    public static void close() {
        System.exit(0);
    }

    public static TaskManager getWorldExecutor() {
        return worldExecutor;
    }

    public static TaskManager getEntityExecutor() {
        return entityExecutor;
    }
}