package dragonkk.rs2rsps.model.player.clan;

import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;


/**
 * @author 'Mystic Flow  Converted to dragonkk by jet kai
 */
public class ClanPacket {
    public static void sendClanList(Player p, Clan clan) {
        try {
            OutStream bldr = new OutStream();
            bldr.writePacketVarByte(23);
            if (clan != null) {
                bldr.writeString(Misc.formatPlayerNameForDisplay(clan.getOwner()));
                bldr.writeByte(0);
                bldr.writeLong(Misc.stringToLong(clan.getName()));
                bldr.writeByte(clan.getKickReq());
                bldr.writeByte(clan.getMembers().size());
                for (Player pl : clan.getMembers()) {
                    bldr.writeString(Misc.formatPlayerNameForDisplay(pl.getUsername()));
                    bldr.writeByte(p.getConnection().isDisconnected() ? 1 : 0);
                    if (p.getConnection().isDisconnected()) {
                        bldr.writeString(pl.getConnection().isDisconnected() ? "Null" : "Null");
                    } else {
                        bldr.writeString("");
                    }
                    bldr.writeShort(clan.getRank(pl));
                    bldr.writeByte(clan.getRank(pl));
                    bldr.writeString(pl.getConnection().isDisconnected() ? "Null" : "Null");
                }
            }
            p.getConnection().write(bldr);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
