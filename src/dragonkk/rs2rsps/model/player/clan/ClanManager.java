package dragonkk.rs2rsps.model.player.clan;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.xml.XMLHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 'Mystic Flow
 */
public class ClanManager {

    private int canJoin = 1;
    public int clantotal;


    // private ClanChatMessagePacket ClanMessage;

    private Map<String, Clan> clans;

    public ClanManager() {
        try {
            System.out.println("Loading clans....");
            try {
                clans = XMLHandler.fromXML("data/xml/clans.xml");
            } catch (Exception e) {
                //e.printStackTrace();
                clans = new HashMap<String, Clan>();
            }
            for (Map.Entry<String, Clan> entries : clans.entrySet()) {
                entries.getValue().setTransient();
            }
            System.out.println("Loaded " + clans.size() + " clans.");
            clantotal = clans.size();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void SaveClan() {
        try {
            XMLHandler.toXML("data/xml/clans.xml", World.getClanManager().getClans());
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public Clan getClans(String s) {
        return clans.get(Misc.formatPlayerNameForProtocol(s));
    }

    public Map<String, Clan> getClans() {
        return clans;
    }

    public void createClan(Player p, String name) {
        try {
            if (name.equals("")) {
                return;
            }
            String user = Misc.formatPlayerNameForProtocol(p.getUsername());
            if (!clans.containsKey(user)) {
                Clan clan = new Clan(user, name);
                clans.put(Misc.formatPlayerNameForProtocol(p.getUsername()), clan);
                refresh(clan);
                SaveClan();
            } else {
                Clan clan = clans.get(user);
                clan.setName(name);
                refresh(clan);
                SaveClan();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void joinClan(final Player p, final String user) {
        try {
            if (canJoin == 1) {
                p.getFrames().sendChatMessage(0, "Joining clans has been currently disabled until fix.");
                p.getFrames().sendChatMessage(0, "You can still register a clan on the clan setup menu.");
                return;
            }
            p.getFrames().sendChatMessage(0, "Attempting to join channel...");
            final Clan clan = clans.get(Misc.formatPlayerNameForProtocol(user));
            if (clan == null) {
                GameLogicTaskManager.schedule(new GameLogicTask() {
                    @Override
                    public void run() {
                        p.getFrames().sendChatMessage(0, "Then channel you tried to join does not exist.");
                        stop();
                    }
                }, 1, 0);
                return;
            }
            p.getFrames().sendChatMessage(0, "Fine and and now going to the gamelogic task.");
            GameLogicTaskManager.schedule(new GameLogicTask() {
                @Override
                public void run() {
                    stop();
                    if (clan.getMembers().size() >= 99) {
                        p.getFrames().sendChatMessage(0, "This clan chat is currently full.");
                        return;
                    }
                    if (clan.canJoin(p)) {
                        p.getFrames().sendChatMessage(0, "Fine your in the clan!");
                        //p.getClanSettings().setCurrentClan(clan);
                        p.getFrames().sendChatMessage(0, "past the setcurrentclan..");
                        clan.addMember(p);
                        refresh(clan);
                        p.getFrames().sendConfig(1083, clan.isLootsharing() ? 1 : 0);
                        p.getFrames().sendChatMessage(0, "Now talking in the clan channel " + clan.getName());
                        p.getFrames().sendChatMessage(0, "To talk, start each line of chat with the / symbol.");
                    } else {
                        p.getFrames().sendChatMessage(0, "You don't have a high enough rank to join this channel.");
                    }
                    //this.stop();
                }
            }, 1, 0);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void destroy(Player player, String username) {
        try {
            Clan c = clans.get(Misc.formatPlayerNameForProtocol(username));
            if (c != null) {
                for (Player p : c.getMembers()) {
                    if (p == null) {
                        continue;
                    }
                    ClanPacket.sendClanList(p, null);
                }
            }
            clans.remove(username);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void refresh(Clan clan) {
        try {
            for (Player p : clan.getMembers()) {
                if (p == null) {
                    continue;
                }
                ClanPacket.sendClanList(p, clan);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void leaveClan(Player player) {
        try {
            if (canJoin == 1) {
                player.getFrames().sendChatMessage(0, "Joining clans has been currently disabled until fix.");
                player.getFrames().sendChatMessage(0, "You can still register a clan on the clan setup menu.");
                return;
            }
            //Clan c = player.getClanSettings().getCurrentClan();
            Clan c = null;
            if (c != null) {
                c.removeMember(player);
                refresh(c);
                ClanPacket.sendClanList(player, null);
            }
            player.getFrames().sendConfig(1083, 0);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void rankMember(Player player, String user, int rank) {
        try {
            Clan c = clans.get(player.getUsername());
            if (c == null) {
                return;
            }
            c.rankUser(user, rank);
            refresh(c);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public String getClanName(String user) {
        try {
            Clan c = clans.get(user);
            if (c == null) {
                return "Chat disabled";
            }
            return c.getName();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return user;
    }

    public void sendClanMessage(Player player, String text) {
        //Clan c = player.getClanSettings().getCurrentClan();
        Clan c = null;
        if (c == null) {
            return;
        }
        for (Player pl : c.getMembers()) {
            if (pl.getIndex() == player.getIndex()) {
                continue;
            }
            ClanChatMessagePacket.sendClanChatMessage(player, pl, c.getName(), c.getOwner(), text);
        }
        ClanChatMessagePacket.sendClanChatMessage(player, null, c.getName(), c.getOwner(), text);
    }

    public void toggleLootshare(Player player) {
        try {
            Clan c = clans.get(player.getUsername());
            if (c != null) {
                c.toggleLootshare();
            } else {
                player.getFrames().sendChatMessage(0, "You don't have a clan to active lootshare with.");
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
