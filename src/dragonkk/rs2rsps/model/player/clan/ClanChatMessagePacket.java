package dragonkk.rs2rsps.model.player.clan;

import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;

import java.util.Random;

/**
 * ClanChatMessagePacket.java
 *
 * @author David + Harry Andreas - actualy leech from mystic flow.
 *         Legacy614
 *         Apr 21, 2011
 */
public class ClanChatMessagePacket {

    public static int messageCounter = 1;
    public static final Random r = new Random();
    public static int id = 0;

	/*public static void sendClanChatMessage(Player from, Player pl, String roomName, String user, String message) {
        try {
		int messageCounter = getNextUniqueId();
		OutStream bldr = new OutStream();
		bldr.writePacketVarByte(64);
		bldr.writeByte(0);
		bldr.writeString(Misc.formatPlayerNameForDisplay((from.getDisplayName())));
		bldr.writeLong(Misc.stringToLong(roomName));
		bldr.writeShort(r.nextInt());
		byte[] bytes = new byte[256];
		bytes[0] = (byte) message.length();
		int len = 1 + Misc.huffmanCompress(message, bytes, 1);
		bldr.writeMediumInt(messageCounter);
		bldr.writeByte((byte) from.getRights());
		bldr.writeBytes(bytes, 0, len);
		bldr.endPacketVarByte();
		if (pl != null)
			pl.getConnection().write(bldr);
		else 
			from.getConnection().write(bldr);
		//TODO
	} catch(Exception e) {
            //e.printStackTrace();
        }
        } */

    public static void sendClanChatMessage(Player from, Player pl, String roomName, String user, String message) {
        try {
            int messageCounter = getNextUniqueId();
            OutStream bldr = new OutStream();
            bldr.writePacketVarByte(64);
            bldr.writeByte(0);
            bldr.writeString(Misc.formatPlayerNameForDisplay((from.getUsername())));
            bldr.writeLong(Misc.stringToLong(roomName));
            bldr.writeShort(r.nextInt());
            byte[] bytes = new byte[256];
            bytes[0] = (byte) message.length();
            int len = 1 + Misc.huffmanCompress(message, bytes, 1);
            bldr.writeMediumInt(messageCounter);
            bldr.writeByte(from.getRights());
            bldr.writeBytes(bytes, 0, len);
            if (pl != null)
                pl.getConnection().write(bldr);
            else
                from.getConnection().write(bldr);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static int getNextUniqueId() {
        try {
            if (messageCounter >= 16000000) {
                messageCounter = 0;
            }
            return messageCounter++;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }

}
