package dragonkk.rs2rsps.model.player.clan;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 'Mystic Flow <- all flow (converted from dementhium by jet kai)
 */
public class Clan {

    private String roomName;
    private String roomOwner;
    private int joinReq = 0;
    private int talkReq = 0;
    private int kickReq = 7;
    private HashMap<String, Byte> ranks;
    private transient List<Player> members;
    private transient boolean lootsharing;

    public Clan(String owner, String name) {
        try {
            this.roomName = name;
            this.roomOwner = owner;
            setTransient();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void setTransient() {
        try {
            setLootsharing(false);
            if (kickReq == 0) {
                kickReq = 7;
            }
            if (members == null) {
                this.members = new ArrayList<Player>();
            }
            if (ranks == null) {
                this.ranks = new HashMap<String, Byte>();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public String getName() {
        return roomName;
    }

    public String getOwner() {
        return roomOwner;
    }

    public void rankUser(String name, int rank) {
        try {
            if (!ranks.containsKey(name)) {
                ranks.put(name, (byte) rank);
            } else {
                ranks.remove(name);
                ranks.put(name, (byte) rank);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public Byte getRank(Player player) {
        try {
            if (Misc.formatPlayerNameForProtocol(player.getUsername()).equals(roomOwner)) {
                return 7;
            } else if (player.getRights() == 2) {
                return 127;
            } else if (ranks.containsKey(player.getUsername())) {
                return ranks.get(player.getUsername());
            }
            return -1;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    public boolean canJoin(Player player) {
        try {
            byte rank = 0;
            if (ranks.containsKey(player.getUsername())) {
                rank = ranks.get(player.getUsername());
            }
            return rank >= joinReq;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }

    public boolean canTalk(Player player) {
        try {
            byte rank = 0;
            if (ranks.containsKey(player.getUsername())) {
                rank = ranks.get(player.getUsername());
            }
            return rank >= talkReq;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }

    public void toggleLootshare() {
        try {
            lootsharing = !lootsharing;
            String message = "";
            if (lootsharing) {
                message = "Lootshare has been enabled.";
            } else {
                message = "Lootshare has been disabled.";
            }
            for (Player pl : members) {
                pl.getFrames().sendChatMessage(0, message);
                pl.getFrames().sendConfig(1083, lootsharing ? 1 : 0);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void addMember(Player member) {
        members.add(member);
    }

    public void setName(String name) {
        this.roomName = name;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void removeMember(Player player) {
        members.remove(player);
    }

    public HashMap<String, Byte> getRanks() {
        return ranks;
    }

    public void setLootsharing(boolean lootsharing) {
        this.lootsharing = lootsharing;
    }

    public boolean isLootsharing() {
        return lootsharing;
    }

    public void setTalkReq(int talkReq) {
        this.talkReq = talkReq;
    }

    public int getTalkReq() {
        return talkReq;
    }

    public void setJoinReq(int joinReq) {
        this.joinReq = joinReq;
    }

    public int getJoinReq() {
        return joinReq;
    }

    public int getKickReq() {
        return kickReq;
    }
}
