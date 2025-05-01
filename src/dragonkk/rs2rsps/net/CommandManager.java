package dragonkk.rs2rsps.net;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.commands.*;
import dragonkk.rs2rsps.util.Logger;

import java.util.HashMap;
import java.util.Map;


public class CommandManager {

    /*
     * A hashmap holding the commands
     */
    private static final Map<String, Command> COMMANDS = new HashMap<>();

    /*
     * Constructor
     */
    public CommandManager() {
        COMMANDS.put("gfx", new Gfx());
        COMMANDS.put("search", new Search());
        COMMANDS.put("scanip", new Scanip());
        COMMANDS.put("uptime", new Uptime());
        COMMANDS.put("emote", new Emote());
        COMMANDS.put("sendhome", new SendHome());
        COMMANDS.put("prefix", new Prefix());
        COMMANDS.put("rocktail", new Food());
        COMMANDS.put("food", new Food());
        COMMANDS.put("tea", new Tea());
        COMMANDS.put("togglename", new Togglename());
        COMMANDS.put("deletepin", new Deletepin());
        COMMANDS.put("removepin", new Deletepin());
        COMMANDS.put("resetpin", new Deletepin());
        COMMANDS.put("changepin", new Deletepin());
        COMMANDS.put("createpin", new Createpin());
        COMMANDS.put("makepin", new Createpin());
        COMMANDS.put("addpin", new Createpin());
        COMMANDS.put("pin", new Createpin());
        COMMANDS.put("changename", new Changename());
        COMMANDS.put("easts", new Easts());
        COMMANDS.put("donatorzone", new Donatorzone());
        COMMANDS.put("toggleskull", new Toggleskull());
        COMMANDS.put("info", new Info());
        COMMANDS.put("togglepray", new Togglepray());
        COMMANDS.put("toggleprayer", new Togglepray());
        COMMANDS.put("toggleprayers", new Togglepray());
        COMMANDS.put("receive", new Receive());
        COMMANDS.put("recieve", new Receive());
        COMMANDS.put("togglewelcome", new Togglewelcome());
        COMMANDS.put("resetcounter", new Resetcounter());
        COMMANDS.put("imsure", new Imsure());
        COMMANDS.put("uncurse", new Uncurse());
        COMMANDS.put("totalvotes", new Totalvotes());
        COMMANDS.put("disablevote", new Disablevote());
        COMMANDS.put("enablevote", new Enablevote());
        COMMANDS.put("deathemote", new Deathemote());
        COMMANDS.put("votepoints", new Votepoints());
        COMMANDS.put("claim", new Claim());
        COMMANDS.put("vote", new Claim());
        COMMANDS.put("banuser", new Banuser());
        COMMANDS.put("tele", new Tele());
        COMMANDS.put("menu", new Tele());
        COMMANDS.put("shop", new Shop());
        COMMANDS.put("dice", new Dice());
        COMMANDS.put("tradezone", new Tradezone());
        COMMANDS.put("dicezone", new Dicezone());
        COMMANDS.put("kick", new Kick());
        COMMANDS.put("removestarter", new Removestarter());
        COMMANDS.put("setgender", new SetGender());
        COMMANDS.put("changepassword", new Changepassword());
        COMMANDS.put("changepass", new Changepassword());
        COMMANDS.put("newpassword", new Changepassword());
        COMMANDS.put("newpass", new Changepassword());
        COMMANDS.put("pvp", new DangerousPvPTeleport());
        COMMANDS.put("starter", new Starter());
        COMMANDS.put("barragerunes", new Barragerunes());
        COMMANDS.put("teletome", new Teletome());
        COMMANDS.put("vengrunes", new Vengrunes());
        COMMANDS.put("points", new Points());
        COMMANDS.put("dung", new Dung());
        COMMANDS.put("magebank", new Magebank());
        COMMANDS.put("getip", new Getip());
        COMMANDS.put("multi", new Multi());
        COMMANDS.put("yell", new Yell());
        COMMANDS.put("resetkdr", new Resetkdr());
        COMMANDS.put("staffzone", new Staffzone());
        COMMANDS.put("pots", new Pots());
        COMMANDS.put("potions", new Pots());
        COMMANDS.put("bank", new Bank());
        COMMANDS.put("thiev", new Thiev());
        COMMANDS.put("thief", new Thiev());
        COMMANDS.put("save", new Save());
        COMMANDS.put("clanwars", new Clanwars());
        COMMANDS.put("players", new Players());
        COMMANDS.put("help", new Help());
        COMMANDS.put("rules", new Rules());
        COMMANDS.put("rule", new Rules());
        COMMANDS.put("help", new Help());
        COMMANDS.put("kdr", new Kdr());
        COMMANDS.put("reset", new Reset());
        COMMANDS.put("setlevel", new Setlevel());
        COMMANDS.put("moderns", new Moderns());
        COMMANDS.put("curses", new Curses());
        COMMANDS.put("ancients", new Ancients());
        COMMANDS.put("sellitem", new Sellitem());
        COMMANDS.put("lunars", new Lunars());
        COMMANDS.put("item", new Item());
        COMMANDS.put("pickup", new Item());
        COMMANDS.put("138", new Master());
        COMMANDS.put("maxlevel", new Master());
        COMMANDS.put("max", new Master());
        COMMANDS.put("master", new Master());
        COMMANDS.put("mute", new Mute());
        COMMANDS.put("unmute", new Unmute());
        COMMANDS.put("commands", new Commands());
        COMMANDS.put("command", new Commands());
        COMMANDS.put("teleto", new Teleto());
        COMMANDS.put("jail", new Jail());
        COMMANDS.put("unjail", new Unjail());
        COMMANDS.put("empty", new Empty());
        COMMANDS.put("home", new Home());
        Logger.log("CommandHandler", "Loaded: " + COMMANDS.size() + " commands.");
    }

    /*
     * Executes the command
     */
    public static void execute(String args[], Player p) {
        try {
            if (p.isDead()) {
                p.getFrames().sendChatMessage(0, "You are dead.");
                return;
            }
            if (p.getTradeSession() != null) {
                p.getFrames().sendChatMessage(0, "You can't do any commands while in trade.");
                return;
            }
            if (p.commandwait > 0) {
                p.getFrames().sendChatMessage(0, "Please wait a couple seconds to do this command.");
                return;
            }
            if (p.Jailed) {
                p.getMask().getRegion().teleport(2167, -20902, 0, 0);
                return;
            }
            if (p.teleblockDelay > 0) {
                p.getFrames().sendChatMessage(0, "You can't use commands while tele blocked.");
                return;
            }
            if (p.getCombat().CombatDelay > 0) {
                p.getFrames().sendChatMessage(0, "You must not hit your opponent for 5 seconds to do this.");
                return;
            }
            Command command = COMMANDS.get(args[0]);
            if (command != null) {
                command.execute(args, p);
                p.totalcommands += 1;
                p.commandwait = 3;
            } else {
                p.getFrames().sendChatMessage(0, "Command error, try the command again.");
            }
        } catch (Exception e) {
            p.getFrames().sendChatMessage(0, "Command null, try the command again or try again later.");
        }
    }

}
