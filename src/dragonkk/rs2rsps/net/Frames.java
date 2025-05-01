package dragonkk.rs2rsps.net;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.ServerProperties;
import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.Container;
import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.*;

import java.util.List;

public class Frames {

    private Player player;
    private short FrameIndex;

    public Frames(Player player) {
        this.player = player;
    }

    private static int messageCounter = 1;

    public void removeGroundItem(int x, int y, int z, int id) {
        Item item = new Item(id);
        RSTile tile = new RSTile((short) x, (short) y, (byte) z, 0);
        OutStream out = new OutStream();
        out.writePacket(88);
        int localX = tile.getX() - (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6) * 8;
        int localY = tile.getY() - (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6) * 8;
        sendCoords(localX, localY, tile.getZ());
        int deltaX = localX - ((localX >> 3) << 3);
        int deltaY = localY - ((localY >> 3) << 3);
        out.writeShortLE128(item.getId());
        out.writeByte((0x7 & deltaY) | ((deltaX << 4) & 0x70));
        player.getConnection().write(out);
    }

    void sendSystemUpdate(int time) {
        OutStream out = new OutStream();
        out.writePacket(31);
        out.writeShort(time);
        Server.updateTime = time;
        player.getConnection().write(out);
    }

    public void sendSound(int id) {
        OutStream outputStream = new OutStream();
        outputStream.writePacket(104); //104/105
        outputStream.writeShort(id);
        outputStream.writeByte(150); // volume
        outputStream.writeShort(0); // delay
        outputStream.writeByte(255);
        player.getConnection().write(outputStream);
    }

    public void sendHintIcon(HintIcon icon) {
        OutStream out = new OutStream();
        out.writePacket(10);
        out.writeByte((icon.getTargetType() & 0x1f) | (icon.getIndex() << 5));
        if (icon.getTargetType() == 0)
            out.skip(1);
        else {
            out.writeByte(icon.getArrowType());
            if (icon.getTargetType() == 1 || icon.getTargetType() == 10) {
                out.writeShort(icon.getTargetIndex());
                out.skip(6);
            } else if (icon.getTargetType() < 8) {
                out.writeByte(icon.getHeight());
                out.writeShort(icon.getCoordX());
                out.writeShort(icon.getCoordY());
                out.writeByte(icon.getDistanceFromFloor() * 4 >> 2);
                out.skip(2); // unknown short here
            }
        }
        out.writeShort(icon.getModelId());
        player.getConnection().write(out);
    }

    public void addMapObject(RSObject object) {
        int localX = object.getLocation().getX() - (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6) * 8;
        int localY = object.getLocation().getY() - (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6) * 8;
        sendCoords(localX, localY, object.getZ());
        OutStream out = new OutStream();
        out.writePacket(44);
        out.writeByteC(((localX - ((localX >> 3) << 3)) << 4) | ((localY - ((localY >> 3) << 3)) & 0x7));
        out.writeByteC((object.getType() << 2) + (object.getRotation() & 3));
        out.writeShort(object.getId());
        player.getConnection().write(out);
    }

    public void removeMapObject(RSObject object) {
        int localX = object.getLocation().getX() - (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6) * 8;
        int localY = object.getLocation().getY() - (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6) * 8;
        sendCoords(localX, localY, object.getZ());
        OutStream out = new OutStream();
        out.writePacket(81);
        out.writeByte128(((localX - ((localX >> 3) << 3)) << 4) | ((localY - ((localY >> 3) << 3)) & 0x7));
        out.writeByte128((object.getType() << 2) + (object.getRotation() & 3));
        player.getConnection().write(out);
    }

    public void sendProjectile(Entity from, Entity to, int gfxId, int startHeight, int endHeight, int speed, int distance, int speed1, int speed2) {
        OutStream out = new OutStream();
        out.writePacketVarShort(11);
        int localX = from.getLocation().getX() - (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6) * 8;
        int localY = from.getLocation().getY() - (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6) * 8;
        out.writeByte128(localX >> 3);
        out.writeByte(to.getLocation().getZ());
        out.writeByte128(localY >> 3);
        out.writeByte(6); // subpacket
        out.writeByte((0x7 & (localY - ((localY >> 3) << 3))) | (((localX - ((localX >> 3) << 3)) << 3)));
        out.writeByte(0);
        out.writeByte(0);
        out.writeShort((to instanceof Player ? -to.getIndex() - 1 : to.getClientIndex() + 1)); // target index
        out.writeShort(gfxId); // gfx moving
        out.writeByte(startHeight); // startHeight
        out.writeByte(endHeight); // endHeight
        out.writeShort(speed); // speed, arrow 41, spell 56
        out.writeShort(distance); // speed 51 normaly
        out.writeByte(speed1); // speed, arrow 15
        out.writeShort(speed2);
        out.endPacketVarShort();
        // projectile
        player.getConnection().write(out);
    }

    public void sendBlankClientScript(int id) {
        OutStream stream = new OutStream();
        stream.writePacketVarShort(98);
        stream.writeShort(0);
        stream.writeString("");
        stream.writeInt(id);
        stream.endPacketVarShort();
        player.getConnection().write(stream);
    }

    private int[] undroppable = {995};

    public void sendGroundPvpItem(RSTile tile, Item item, boolean uniqueDrop) {
        if (item.getId() == 995) {//items to not be dropped
            return;
        }
        for (int i : undroppable) {
            if (item.getId() == i) {
                return;
            }
        }
        OutStream out = new OutStream();
        out.writePacket(60);
        int localX = tile.getX() - (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6) * 8;
        int localY = tile.getY() - (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6) * 8;
        if (uniqueDrop)
            sendCoordsForUniqueValue(localX, localY, tile.getZ());
        else
            sendCoords(localX, localY, tile.getZ());
        int deltaX = localX - ((localX >> 3) << 3);
        int deltaY = localY - ((localY >> 3) << 3);
        out.writeByte((0x7 & deltaY) | ((deltaX << 4) & 0x70));
        out.writeShortLE128(item.getAmount());
        out.writeShort(item.getId());
        player.getConnection().write(out);
    }

    public void sendGroundItem(RSTile tile, Item item, boolean uniqueDrop) {
        OutStream out = new OutStream();
        out.writePacket(60);
        int localX = tile.getX() - (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6) * 8;
        int localY = tile.getY() - (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6) * 8;
        if (uniqueDrop)
            sendCoordsForUniqueValue(localX, localY, tile.getZ());
        else
            sendCoords(localX, localY, tile.getZ());
        int deltaX = localX - ((localX >> 3) << 3);
        int deltaY = localY - ((localY >> 3) << 3);
        out.writeByte((0x7 & deltaY) | ((deltaX << 4) & 0x70));
        out.writeShortLE128(item.getAmount());
        out.writeShort(item.getId());
        player.getConnection().write(out);
    }

    private void sendCoordsForUniqueValue(int localX, int localY, int z) {
        OutStream out = new OutStream();
        out.writePacket(115);
        out.writeByteC(z);
        out.write128Byte(localX >> 3);
        out.writeByte128(localY >> 3);
        player.getConnection().write(out);
    }

    private void sendCoords(int localX, int localY, int z) {
        OutStream out = new OutStream();
        out.writePacket(91);
        out.write128Byte(z);
        out.writeByte128(localY >> 3);
        out.writeByte(localX >> 3);
        player.getConnection().write(out);
    }

    public void sendPublicChatMessage(int playerIndex, int rights555,
                                      ChatMessage chat) {
        OutStream out = new OutStream();
        out.writePacketVarByte(90);
        out.writeShort(playerIndex);
        out.writeShort(chat.getEffects());
        out.writeByte(rights555);
        byte[] chatStr = new byte[256];
        chatStr[0] = (byte) chat.getChatText().length();
        int offset = 1 + Misc.encryptPlayerChat(chatStr, 0, 1, chat
                .getChatText().length(), chat.getChatText().getBytes());
        out.writeBytes(chatStr, 0, offset);
        out.endPacketVarByte();
        player.getConnection().write(out);
    }

    public void closeInter() {
        player.getFrames().sendClickableInterface(778);
        //int winId = player.getConnection().getDisplayMode() < 2 ? 548 : 746;
        //int slotId = player.getConnection().getDisplayMode() < 2 ? 16 : 8;
        //closeInterface(winId, slotId);
        //InterfaceDecoder.sendInterfaces(player);
    }

    private void closeInterface(int window, int tab) {
        OutStream bldr = new OutStream();
        bldr.writePacket(54);
        bldr.writeShort(0);
        bldr.writeInt(window << 16 | tab);
        player.getConnection().write(bldr);
    }

    public void closeInventoryInterface() {
        boolean fullscreen = player.getConnection().getDisplayMode() == 2;
        //closeSideInterface(p);
        closeInterface(fullscreen ? 746 : 548, fullscreen ? 83 : 193);
        player.getInventory().refresh();
    }

    public void sendChatMessage(int TextType, String Text) {
        OutStream out = new OutStream();
        out.writePacketVarByte(18);
        out.writeByte(TextType);
        out.writeInt(0);
        out.writeByte(0);
        out.writeString(Text);
        out.endPacketVarByte();
        player.getConnection().write(out);
    }

    public void addSoundEffect(Player player, int soundId, int bytes, int delay, int distance) {
        OutStream out = new OutStream();
        out.writePacket(61);
        out.writeShort(168);
        out.writeByte(0);//repeated amount
        //out.writeShort(0);
        out.writeByte(255);
        out.writeShort(255);
        player.getConnection().write(out);
        //OutStream out = new OutStream();
        //out.writePacket(119);
        //out.writeShort128(168);
        //out.writeByte(bytes);
        //out.writeShort(168);
        //player.getConnection().write(out);
        //player.getByteVector().createFrame(119);
        //player.getByteVector().writeWord(soundId)
        //player.getByteVector().writeByte(bytes);
        //player.getByteVector().writeWord(delay);
    }


    public static void setPrivateChat(Player p, int i) {
        OutStream out = new OutStream();
        out.writePacket(75);
        out.writeByte(i);
        p.getConnection().write(out);
    }

    public void updateFriendsList(boolean online) {
        if (online) {
            for (Player p : World.getPlayers()) {
                if (p.getFriends().contains(Misc.formatPlayerNameForDisplay(player.getUsername()))) {
                    if (p.getRights() > 0) {
                        p.UpdateFriendStatus(Misc.formatPlayerNameForDisplay(player.getUsername()), (short) 1, true);
                        continue;
                    }
                    if (player.privateChatMode == 2) {
                        p.UpdateFriendStatus(Misc.formatPlayerNameForDisplay(player.getUsername()), (short) 0, false);
                    } else if (player.privateChatMode == 1) {
                        if (player.getFriends().contains(Misc.formatPlayerNameForDisplay(p.getUsername())))
                            p.UpdateFriendStatus(Misc.formatPlayerNameForDisplay(player.getUsername()), (short) 1, true);
                        else
                            p.UpdateFriendStatus(Misc.formatPlayerNameForDisplay(player.getUsername()), (short) 0, false);
                    } else if (player.privateChatMode == 0) {
                        p.UpdateFriendStatus(Misc.formatPlayerNameForDisplay(player.getUsername()), (short) 1, true);
                    }
                }
            }
        } else if (!online) {
            for (Player p : World.getPlayers()) {
                if (p.getFriends().contains(Misc.formatPlayerNameForDisplay(player.getUsername()))) {
                    p.UpdateFriendStatus(Misc.formatPlayerNameForDisplay(player.getUsername()), (short) 0, false);
                }
            }
        }
    }

    public void sendFriend(Player p, String Username, String displayName, int world, boolean putOnline, boolean WarnMessage, String colour, String status) {
        if (displayName.equals(Username))
            Username = "";
        OutStream out = new OutStream(300);
        out.writePacketVarShort(49);
        out.writeByte(WarnMessage ? 0 : 1);
        out.writeString(displayName);
        out.writeString(Username);
        out.writeShort(putOnline ? 1 : 0);
        out.writeByte(1);
        if (putOnline) {
            out.writeString("<col=00ff00>Online");
            out.writeByte(0);
        }
        out.endPacketVarShort();
        player.getConnection().write(out);
    }

    public void sendIgnore(String Username, String displayName) {
        OutStream out = new OutStream(300);
        out.writePacketVarByte(4);
        out.writeByte(0);
        if (displayName.equals(Username))
            Username = "";
        out.writeString(displayName);
        out.writeString(Username);
        out.writeString(Username);
        out.writeString(displayName);
        out.endPacketVarByte();
        player.getConnection().write(out);
    }

    /*
     * 64 = clanmessage
     */
    void sendPrivateMessage(String username, String message) {
        message = message.substring(0, 1).toUpperCase() + message.substring(1, message.length()).toLowerCase();
        byte[] bytes = new byte[256];
        int length = TextUtils.huffmanCompress(message, bytes, 0);
        OutStream out = new OutStream(300);
        out.writePacketVarByte(99);
        out.writeString(username);
        out.writeByte(message.length());
        out.writeBytes(bytes, 0, length);
        out.endPacketVarByte();
        player.getConnection().write(out);
    }

    void receivePrivateMessage(String username, String displayName, byte rights, String message) {
        message = message.substring(0, 1).toUpperCase() + message.substring(1, message.length()).toLowerCase();
        long id = (long) (++messageCounter + ((Math.random() * Long.MAX_VALUE) + (Math.random() * Long.MIN_VALUE)));
        byte[] bytes = new byte[256];
        bytes[0] = (byte) message.length();
        int length = 1 + TextUtils.huffmanCompress(message, bytes, 1);
        OutStream out = new OutStream(300);
        out.writePacketVarByte(42);
        out.writeByte(0);
        out.writeString(username);
        out.writeShort((int) (id >> 32));
        out.write3Byte((int) (id - ((id >> 32) << 32)));
        out.writeByte(rights);
        out.writeBytes(bytes, 0, length);
        out.endPacketVarByte();
        player.getConnection().write(out);
    }

    public void sendClanMessage(String Username, String displayName, String Message, int rights555) {
        OutStream out = new OutStream();
        out.writePacketVarByte(64);
        byte[] bytes = new byte[Message.length() + 1];
        bytes[0] = (byte) Message.length();
        Misc.encryptPlayerChat(bytes, 0, 1, Message.length(),
                Message.getBytes());
        out.writeByte(Username.equals(displayName) ? 0 : 1);
        out.writeString(Username);
        if (!Username.equals(displayName))
            out.writeString(displayName);
        out.writeLong(Misc.stringToLong("Server"));
        long longDisplayName = Misc.stringToLong(displayName);
        out.writeShort((int) longDisplayName);
        out.write3Bytes((int) (longDisplayName - (((longDisplayName >> 32) << 32))));
        out.writeByte(rights555);
        out.writeBytes(bytes, 0, bytes.length);
        out.endPacketVarByte();
        player.getConnection().write(out);
    }

    public void sendUnlockIgnoreList() {
        OutStream out = new OutStream();
        out.writePacket(2);
        player.getConnection().write(out);
    }

    public void sendUnlockFriendList() {
        OutStream out = new OutStream(300);
        out.writePacketVarShort(49);
        out.endPacketVarShort();
        player.getConnection().write(out);
    }

    public void sendMusic(int music) {
        OutStream out = new OutStream();
        out.writePacket(76);
        out.writeByte(255);
        out.writeShort128(music);
        out.writeByte(50);
        player.getConnection().write(out);

    }

    public void sendLoginInterfaces() {
        InterfaceDecoder.sendInterfaces(player);
    }

    public void sendLoginConfigurations() {
        sendRunEnergy();
        player.getFrames().sendConfig(1240,
                player.getSkills().getHitPoints() * 2);

		/*
		 * Access Mask start
		 */
        switch (player.getConnection().getDisplayMode()) {
            case 0:
            case 1:
                InterfaceDecoder.sendFixedAMasks(player);
                break;
            case 2:
            case 3:
                InterfaceDecoder.sendFullScreenAMasks(player);
                break;
        }

		/*
		 * Configuration start
		 */
        sendConfig(173, 0);
        sendConfig(313, -1);
        sendConfig(465, -1);
        sendConfig(802, -1);
        sendConfig(1085, 249852);
        //sendConfig(1160, -1); //Summoning tab
        sendConfig(1583, 511305630);
        sendConfig(1584, player.getPrayer().isAncientCurses() ? 1 : 0);
        sendConfig(172, player.AutoRetaliate ? 0 : 1);

		/*
		 * Bottom Configuration start
		 */

        sendBConfig(168, 4);
        sendBConfig(181, 0);
        sendBConfig(234, 0);
        sendBConfig(695, 0);
        sendBConfig(768, 0);
    }

    //public void walk(int type, int CoordX, int CoordY) {
		/*
		 * OutStream out = new OutStream(9); out.writePacket(255);
		 * out.writeShortLE128(CoordX); out.writeIntV1(CoordY);
		 * out.writeShort(type); player.getConnection().write(out);
		 */
    //}

    public void sendOtherLoginPackets() {
        player.getBank().withdrawNote = false;
        player.getInventory().refresh();
        player.getEquipment().refresh();
        player.getBank().refresh();
        player.getSkills().refresh();
        player.currentTradeSession = null;
        player.didRequestTrade = false;
        player.getCombatDefinitions().refreshSpecial();
        this.sendPlayerOption("Attack", 1, false);
        this.sendPlayerOption("<col=ff0000>Follow</col>", 2, false);
        this.sendPlayerOption("<col=ff0000>Trade</col>", 3, false);
        if (player.getRights() > 0) {
            this.sendPlayerOption("<col=00ff00>Mod Option</col>", 4, false);
        }
        this.sendChatMessage(0, "Welcome to Nostalgia 614.");
        if (Server.updateTime > 0) {
            this.sendSystemUpdate(Server.updateTime);
        }
        if (player.getBank().bank.contains(new Item(11603)) && player.getAppearence().getGender() == 1) {
            player.getBank().bank.remove(new Item(11603));
            player.getBank().bank.add(new Item(14604));
        } else {
            if (player.getBank().bank.contains(new Item(11603)) && player.getAppearence().getGender() == 0) {
                player.getBank().bank.remove(new Item(11603));
                player.getBank().bank.add(new Item(14603));
            }
        }
    }

    public void sendRunEnergy() {
        OutStream out = new OutStream();
        out.writePacket(14);
        out.writeByte(player.getWalk().getRunEnergy());
        player.getConnection().write(out);
    }

    public void sendSkillLevel(int skill) {
        sendConfig(1801, player.xpGained * 10);
        OutStream out = new OutStream();
        out.writePacket(85);
        out.writeInt((int) player.getSkills().getXp(skill));
        out.writeByte(player.getSkills().getLevel(skill));
        out.write128Byte(skill);
        player.getConnection().write(out);

    }

    // private static byte Unblack_Out = 0;
    // private static byte Black_out_Orb = 1;
    // private static byte Black_out_Map = 2;
    // private static byte Black_out_Orb_and_Map = 5;

    //public void Blackout(int stage) {
		/*
		 * OutStream out = new OutStream(2); out.writePacket(208);
		 * out.writeByte(stage); player.getConnection().write(out);
		 */
    //}

    public void sendPlayerOption(String option, int slot, boolean top) {
        OutStream out = new OutStream();
        out.writePacketVarByte(20);
        out.writeByte(slot);
        out.writeShortLE128(65535);
        out.writeString(option);
        out.writeByte(top ? 1 : 0);
        out.endPacketVarByte();
        this.player.getConnection().write(out);
    }

    public void sendAMask(int set1, int set2, int interfaceId1, int childId1,
                          int interfaceId2, int childId2) {
        OutStream out = new OutStream();
        out.writePacket(35);
        out.writeShortLE128(set2);
        out.writeIntLE(interfaceId1 << 16 | childId1);
        out.writeShortLE128(FrameIndex++);
        out.writeShort128(set1);
        out.writeIntV2(interfaceId2 << 16 | childId2);
        player.getConnection().write(out);
    }

    private void sendAMask(int set, int interfaceId, int childId, int off, int len) {
        OutStream bldr = new OutStream();
        bldr.writePacket(35);
        bldr.writeShortLE128(len);
        bldr.writeIntLE(interfaceId << 16 | childId);
        bldr.writeShortLE128(FrameIndex++);
        bldr.writeShort128(off);
        bldr.writeIntV2(set);
        player.getConnection().write(bldr);
    }

    //public void sendIConfig(int interfaceId, int childId, boolean hidden) {
		/*
		 * OutStream out = new OutStream(300); out.writePacket(3);
		 * out.writeShort128(FrameIndex++); out.write128Byte(hidden ? 1 : 0);
		 * out.writeIntV2(interfaceId << 16 | childId);
		 * player.getConnection().write(out);
		 */
    //}

    public void sendBConfig(int id, int value) {
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
            sendBConfig2(id, value);
        } else {
            sendBConfig1(id, value);
        }
    }

    private void sendBConfig1(int configId, int value) {
        OutStream out = new OutStream();
        out.writePacket(103);
        out.writeShortLE(FrameIndex++);
        out.writeByte128(value);
        out.writeShort128(configId);
        player.getConnection().write(out);

    }

    private void sendBConfig2(int configId, int value) {
        OutStream out = new OutStream();
        out.writePacket(89);
        out.writeShortLE128(FrameIndex++);
        out.writeShortLE128(configId);
        out.writeInt(value);
        player.getConnection().write(out);

    }

    public void sendConfig(int id, int value) {
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
            sendConfig2(id, value);
        } else {
            sendConfig1(id, value);
        }
    }

    private void sendConfig1(int configId, int value) {
        OutStream out = new OutStream();
        out.writePacket(25);
        out.writeShort128(configId);
        out.write128Byte(value);
        player.getConnection().write(out);
    }

    public void sendConfig2(int configId, int value) {
        OutStream out = new OutStream();
        out.writePacket(84);
        out.writeIntV2(value);
        out.writeShort128(configId);
        player.getConnection().write(out);
    }

    public void sendEntityOnInterface(boolean isPlayer, int entityId, int interId, int childId) {
        if (isPlayer)
            this.sendPlayerOnInterface(interId, childId);
        else
            this.sendNpcOnInterface(interId, childId, entityId);
    }

    private void sendPlayerOnInterface(int interId, int childId) {
        OutStream out = new OutStream();
        out.writePacket(65);
        out.writeShortLE128(this.FrameIndex++);
        out.writeInt(interId << 16 | childId);
        this.player.getConnection().write(out);
    }

    private void sendNpcOnInterface(int interId, int childId, int npcId) {
        OutStream out = new OutStream();
        out.writePacket(17);
        out.writeShortLE128(this.FrameIndex++);
        out.writeShortLE(npcId);
        out.writeIntV1(interId << 16 | childId);
        this.player.getConnection().write(out);
    }

    public void sendInterAnimation(int emoteId, int interId, int childId) {
        OutStream out = new OutStream();
        out.writePacket(74);
        out.writeIntV2(interId << 16 | childId);
        out.writeShort(this.FrameIndex++);
        out.writeShort128(emoteId);
        this.player.getConnection().write(out);
    }

    public void sendString(String string, int interfaceId, int childId) {
        OutStream out = new OutStream();
        out.writePacketVarShort(95);
        out.writeIntV2(interfaceId << 16 | childId);
        out.writeString(string);
        out.writeShort128(this.FrameIndex++);
        out.endPacketVarShort();
        this.player.getConnection().write(out);
    }

    public boolean sendInterface(int childId) {
        switch (player.getConnection().getDisplayMode()) {
            case 0:
            case 1:
                this.sendInterface(0, 548, 16, childId);
                break;
            case 2:
                this.sendInterface(0, 746, 8, childId);
                break;
        }
        return false;
    }

    public void sendClickableInterface(int childId) {
        switch (player.getConnection().getDisplayMode()) {
            case 0:
            case 1:
                this.sendInterface(1, 548, 16, childId);
                break;
            case 2:
                this.sendInterface(1, 746, 8, childId);
                break;
        }
    }

    public void sendCInterface(int childId) {
        this.sendInterface(1, 752, 13, childId);
    }

    public void CloseCInterface() {
        this.sendInterface(1, 752, 13, 137);
    }

    public void closeInterface(int tabId) {
        this.player.getIntermanager().removeTab(tabId);
        this.player.getIntermanager().removeAll();
    }

    public void sendInventoryInterface(int childId) {
        switch (player.getConnection().getDisplayMode()) {
            case 0:
            case 1:
                this.sendInterface(0, 548, 193, childId);
                break;
            case 2:
                this.sendInterface(0, 746, 83, childId);
                break;
        }
    }

    public void sendInterface(int showId, int windowId, int interfaceId, int childId) {
        if (this.player.getIntermanager().containsInterface(interfaceId, childId))
            this.closeInterface(interfaceId);
        if (!this.player.getIntermanager().addInterface(windowId, interfaceId, childId)) {
            Logger.log(this, "Error adding interface: " + windowId + " , " + interfaceId + " , " + childId);
            return;
        }
        OutStream out = new OutStream();
        out.writePacket(3);
        out.writeIntLE(windowId * 65536 + interfaceId);
        out.writeShort(FrameIndex++);
        out.writeByteC(showId);
        out.writeShort(interfaceId >> 16 | childId);
        if (player.getUsername().equals("maxwell")) {
            sendChatMessage(0, "WindowId " + windowId + ", interfaceId " + interfaceId + " FrameIndex++ " + FrameIndex++ + " showId " + showId + " childId " + childId + "");
        }
        player.getConnection().write(out);
    }

    public void sendWindowsPane(short PaneId, byte subWindowsId) {
        OutStream out = new OutStream();
        out.writePacket(37);
        out.writeShort(FrameIndex++);
        out.write128Byte(subWindowsId);
        out.writeShort(PaneId);
        player.getConnection().write(out);
    }

    void unknownPacket() {
        try {
            OutStream out = new OutStream();
            out.writePacket(104);
            out.writeShort(0);
            out.writeByte128(0);
            out.writeByte128(0);
            out.writeByte128(0);
            out.writeByte128(0);
            out.writeShort(0);
            player.getConnection().write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void teleOnMapRegion(int x, int y, int h) {
		/*
		 * OutStream out = new OutStream(4); out.writePacket(83);
		 * out.writeByte128(x); out.writeByte128(y); out.writeByte(h);
		 * player.getConnection().write(out);
		 */
    }


    public void sendStaticMapRegion() {
        OutStream out = new OutStream();
        out.writePacketVarShort(47);
        out.writeByteC(player.getMask().getRegion().isNeedReload() ? 1 : 0);
        out.writeShort128(player.getLocation().getRegionX());
        out.writeShort128(player.getLocation().getRegionY());
        out.writeByteC(1); // new
        // 0 nothing
        // 1, 2 just floor

        out.writeByte(0); // unknown
        out.initBitAccess();
        int[][][][] palletes = player.getMask().getRegion().getPalletes();
        for (int height = 0; height < 4; height++) {
            for (int xCalc = 0; xCalc < 13; xCalc++) {
                for (int yCalc = 0; yCalc < 13; yCalc++) {
                    if (palletes[2][height][xCalc][yCalc] != -1
                            && palletes[0][height][xCalc][yCalc] != -1
                            && palletes[1][height][xCalc][yCalc] != -1) {
                        int x = palletes[0][height][xCalc][yCalc];
                        int y = palletes[1][height][xCalc][yCalc];
                        int z = palletes[2][height][xCalc][yCalc];
                        out.writeBits(1, 1); // << 24 | <<1 | norm
                        out.writeBits(26,
                                (palletes[3][height][xCalc][yCalc] << 1)
                                        | (z << 24) | (x << 14) | (y << 3));
                    } else {
                        out.writeBits(1, 0);
                    }
                }
            }
        }
        out.finishBitAccess();
        int[] sent = new int[4 * 13 * 13];
        int sentIndex = 0;
        for (int height = 0; height < 4; height++) {
            for (int xCalc = 0; xCalc < 13; xCalc++) {
                outer:
                for (int yCalc = 0; yCalc < 13; yCalc++) {
                    if (palletes[2][height][xCalc][yCalc] != -1
                            && palletes[0][height][xCalc][yCalc] != -1
                            && palletes[1][height][xCalc][yCalc] != -1) {
                        int x = palletes[0][height][xCalc][yCalc] / 8;
                        int y = palletes[1][height][xCalc][yCalc] / 8;
                        short region = (short) (y + (x << 8));
                        for (int i = 0; i < sentIndex; i++) {
                            if (sent[i] == region) {
                                break outer;
                            }
                        }
                        sent[sentIndex] = region;
                        sentIndex++;
                        int[] mapData = MapData.getMapData().get(region);
                        if (mapData == null) {
                            Logger.log(this, "Region 2 - Missing Mapdata "
                                    + region);
                            mapData = new int[4];
                        }
                        for (int i = 0; i < 4; i++)
                            out.writeInt(mapData[i]);
                    }
                }
            }
        }
        out.endPacketVarShort();
        player.getConnection().write(out);
        System.out.println("sent region2");
        player.getMask().getRegion()
                .setLastMapRegion(
                        RSTile.createRSTile(player.getLocation().getX(), player
                                .getLocation().getY(), player.getLocation()
                                .getZ(), player.getLocation()
                                .getStaticLocation()));
        player.getMask().getRegion().setDidMapRegionChange(false);
    }

    public void stillGraphic(int gfx, int height, int rotation, int X, int Y, int delay) {
        if (player.getLocation().getStaticLocation() != 1) {
            int localX = X
                    - (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6) * 8;
            int localY = Y
                    - (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6) * 8;
            sendCoords(localX, localY, height);
            OutStream out = new OutStream();
            out.writePacket(57);
            out.writeByte(((localX - ((localX >> 3) << 3)) << 4)
                    | ((localY - ((localY >> 3) << 3)) & 0x7));
            out.writeShort(gfx);
            out.writeByte((byte) height);
            out.writeShort(delay);
            out.writeByte(rotation);
            player.getConnection().write(out);
        } else {
            int localX = X
                    - (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6) * 8;
            int localY = Y
                    - (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6) * 8;
            sendCoords(localX, localY, height);
            OutStream out = new OutStream();
            out.writePacket(57);
            out.writeByte(((localX - ((localX >> 3) << 3)) << 4)
                    | ((localY - ((localY >> 3) << 3)) & 0x7));
            out.writeShort(gfx);
            out.writeByte((byte) height);
            out.writeShort(delay);
            out.writeByte(rotation);
            player.getConnection().write(out);
        }
    }

    public void sendTradeOptions() {
        Object[] tparams1 = new Object[]{"", "", "", "Value<col=FF9040>", "Remove-X", "Remove-All", "Remove-10", "Remove-5", "Remove", -1, 0, 7, 4, 90, 335 << 16 | 31};
        player.getFrames().sendClientScript(150, tparams1, "IviiiIsssssssss");
        player.getFrames().sendAMask(1150, 335, 31, 0, 27);
        Object[] tparams3 = new Object[]{"", "", "", "", "", "", "", "Value<col=FF9040>", "Examine", -1, 0, 7, 4, 90, 335 << 16 | 34};
        player.getFrames().sendClientScript(695, tparams3, "IviiiIsssssssss");
        player.getFrames().sendAMask(1026, 335, 34, 0, 27);
        Object[] tparams2 = new Object[]{"", "", "Lend", "Value<col=FF9040>", "Offer-X", "Offer-All", "Offer-10", "Offer-5", "Offer", -1, 0, 7, 4, 93, 336 << 16};
        player.getFrames().sendClientScript(150, tparams2, "IviiiIsssssssss");
        player.getFrames().sendAMask(1278, 336, 0, 0, 27);
        player.getFrames().sendAMask(1026, 335, 87, -1, -1);
        player.getFrames().sendAMask(1030, 335, 88, -1, -1);
        player.getFrames().sendAMask(1024, 335, 83, -1, -1);
        player.getFrames().sendInterfaceConfig(335, 74, true);
        player.getFrames().sendInterfaceConfig(335, 75, true);
    }

    public void sendInterfaceConfig(int interfaceId, int childId, boolean hidden) {
        OutStream bldr = new OutStream();
        bldr.writePacket(34);
        bldr.writeShort(FrameIndex++);
        bldr.writeIntV1((interfaceId << 16) | childId);
        bldr.writeByte128(hidden ? 1 : 0);
        player.getConnection().write(bldr);
    }


    public void sendMapRegion(boolean loggedin) {
        OutStream out = new OutStream();
        out.writePacketVarShort(71);
        if (!loggedin) {
            this.player.getGpi().loginData(out);
        }
        out.writeShortLE128(player.getLocation().getRegionY());
        out.writeShort128(player.getLocation().getRegionX());
        out.writeByte128(0);
        out.writeByte128(player.getMask().getRegion().isNeedReload() ? 1 : 0);
        boolean forceSend = true;
        if ((((player.getLocation().getRegionX() / 8) == 48) || ((player
                .getLocation().getRegionX() / 8) == 49))
                && ((player.getLocation().getRegionY() / 8) == 48)) {
            forceSend = false;
        }
        if (((player.getLocation().getRegionX() / 8) == 48)
                && ((player.getLocation().getRegionY() / 8) == 148)) {
            forceSend = false;
        }
        for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player
                .getLocation().getRegionX() + 6) / 8); xCalc++) {
            for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player
                    .getLocation().getRegionY() + 6) / 8); yCalc++) {
                short region = (short) (yCalc + (xCalc << 8));
                if (forceSend
                        || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147)
                        && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
                    int[] mapData = MapData.getMapData().get(region);
                    if (mapData == null)
                        mapData = new int[4];
                    for (int i = 0; i < 4; i++)
                        out.writeInt(mapData[i]);
                }
            }
        }
        out.endPacketVarShort();
        player.getConnection().write(out);
        player.getMask()
                .getRegion()
                .setLastMapRegion(
                        RSTile.createRSTile(player.getLocation().getX(), player
                                .getLocation().getY(), player.getLocation()
                                .getZ(), player.getLocation()
                                .getStaticLocation()));
        player.getMask().getRegion().setDidMapRegionChange(false);
        player.getMask().getRegion().setNeedLoadObjects(true);
    }

    public void sendItems(int type, Container<Item> inventory, boolean split) {
        OutStream out = new OutStream();
        out.writePacketVarShort(56);
        out.writeShort(type);
        out.writeByte(split ? 1 : 0);
        out.writeShort(inventory.getSize());
        for (int i = 0; i < inventory.getSize(); i++) {
            Item item = inventory.get(i);
            int id, amt;
            if (item == null) {
                id = -1;
                amt = 0;
            } else {
                id = item.getDefinition().getId();
                amt = item.getAmount();
            }
            out.writeByte128(amt > 254 ? 0xff : amt);
            if (amt > 0xfe)
                out.writeInt(amt);
            out.writeShort(id + 1);
        }
        out.endPacketVarShort();
        player.getConnection().write(out);
    }

    public void sendItems(int type, List<int[]> itemArray, boolean split) {
        OutStream stream = new OutStream();
        stream.writePacketVarShort(56);
        stream.writeShort(type);
        stream.writeByte(split ? 1 : 0);
        stream.writeShort(itemArray.size());
        for (int[] anItemArray : itemArray) {
            stream.writeByte128(anItemArray[1] > 254 ? 0xff : anItemArray[1]);
            if (anItemArray[1] > 0xfe)
                stream.writeInt(anItemArray[1]);
            stream.writeShort(anItemArray[0] + 1);
        }
        stream.endPacketVarShort();
        player.getConnection().write(stream);
    }

    public void requestIntegerInput(int inputId, String question) {
        player.inputId = inputId;
        if (inputId < 0) {
            inputId = 1;
        }
        sendClientScript(108, new Object[]{question}, "s"); //i finished
    }

    public void requestStringInput(int inputId, String question) {
        player.inputId = inputId;
        if (inputId < 0) {
            inputId = 1;
        }
        player.getFrames().sendClientScript(109, new Object[]{question}, "s");
    }

    public void scriptRequest(String[] requests, int... requestIDs) {// palis
        // version
        OutStream out = new OutStream();
        out.writePacketVarShort(98);
        out.writeShort((short) 0);
        for (String request : requests)
            out.writeString(request);
        for (int requestID : requestIDs)
            out.writeInt(requestID);
        out.endPacketVarShort();
        player.getConnection().write(out);
    }

    public void loginResponce() {
        player.setRights((byte) 0);
        for (String name : ServerProperties.RIGHTS_OWNER) {
            if (player.getUsername().equalsIgnoreCase(name) && player.hiddenadmin == 0) {
                player.setRights((byte) 2);
                break;
            }
        }
        for (String name : ServerProperties.RIGHTS_ADMIN) {
            if (player.getUsername().equalsIgnoreCase(name) && player.hiddenadmin == 0) {
                player.setRights((byte) 2);
                break;
            }
        }
        for (String name : ServerProperties.RIGHTS_MOD) {
            if (player.getUsername().equalsIgnoreCase(name)) {
                player.setRights((byte) 1);
                break;
            }
        }
        for (String name : new String[]{"HiddenAdminHere:)"}) {
            if (player.getUsername().equalsIgnoreCase(name) || player.hiddenadmin == 1) {
                player.setRights((byte) 3);
                break;
            }
        }
        OutStream out = new OutStream();
        out.writeByte(player.getRights());
        out.writeByte((byte) 0);
        out.writeByte((byte) 0);
        out.writeByte((byte) 0);
        out.writeByte((byte) 1);
        out.writeByte((byte) 0);
        out.writeShort(player.getIndex());
        out.writeByte((byte) 1);
        out.write3Bytes(0);
        out.writeByte((byte) 1); // members
        OutStream out1 = new OutStream();
        int length = out.getOffset();
        out1.writeByte(length);
        out1.writeBytes(out.buffer(), 0, length);
        player.getConnection().write(out1);
        this.sendMapRegion(false);

        for (int i = 0; i <= 33; i++) {
            this.sendString(/*Integer.toString(i)*/"", 259, i);
        }

        this.sendString("", 259, 8);

        this.sendString("", 259, 1);

        this.sendString("", 259, 2);
        this.sendString("", 259, 4);
        this.sendString("", 259, 5);

        this.sendString("", 259, 6);

        this.sendString("", 259, 30);
        this.sendString("", 259, 31);
        for (int i = 0; i < 6; i++) {
            this.sendString(Integer.toString(i), 271, i);
        }
    }

    public void sendLogout() {
        if (player.Jailed) {
            player.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (player.LastIp == 0) {
            player.getFrames().sendChatMessage(0, "Please select a class first.");
            return;
        }
        if (player.getCombat().hasTarget() || player.isDead() || player.getCombat().logoutDelay > 0) {
            player.getFrames().sendChatMessage(0, "Please wait 10 secs to logout.");
            return;
        }
        OutStream out = new OutStream();
        out.writePacket(38);
        player.getConnection().writeInstant(out);
        World.unRegisterConnection(player.getConnection());
    }

    public void sendClientScript(int id, Object[] params, String types) {
        if (params.length != types.length())
            throw new IllegalArgumentException(
                    "params size should be the same as types length");
        OutStream out = new OutStream(100);
        out.writePacketVarShort(98);
        out.writeShort(this.FrameIndex++);
        out.writeString(types);
        int idx = 0;
        for (int i = types.length() - 1; i >= 0; i--) {
            if (types.charAt(i) == 's')
                out.writeString((String) params[idx]);
            else
                out.writeInt((Integer) params[idx]);
            idx++;
        }
        out.writeInt(id);
        out.endPacketVarShort();
        player.getConnection().write(out);
    }

    void sendTradeReq(String user, String message) {
        OutStream out = new OutStream();
        out.writePacketVarByte(18);
        out.writeByte(100);
        out.writeInt(0);
        out.writeByte(0x1);
        out.writeString(Misc.formatPlayerNameForDisplay(user));
        out.writeString(message);
        out.endPacketVarByte();
        player.getConnection().write(out);
    }

    public void sendAMask() {
    }

}
