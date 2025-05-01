package dragonkk.rs2rsps.net;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.ServerProperties;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.io.InStream;
import dragonkk.rs2rsps.model.*;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.*;
import dragonkk.rs2rsps.model.shops.ShopManager;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.rscache.ItemDefinitions;
import dragonkk.rs2rsps.rsobjects.RSObjectsRegion;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;
import dragonkk.rs2rsps.scripts.objectScript;
import dragonkk.rs2rsps.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

import static dragonkk.rs2rsps.util.Serializer.appendData;

public class Packets {

    public static Player getPlayerByName(String name) {
        name = name.replaceAll(" ", "_");
        for (Player p : World.getPlayers()) if (p.getUsername().equalsIgnoreCase(name)) return p;
        return null;
    }

    private static final Map<Short, Method> packets = new HashMap<>();
    private static final byte[] PacketSize = new byte[256];

    private static void reset() {
        for (int i = 0; i < 256; i++) PacketSize[i] = -3;
        PacketSize[0] = 8;
        PacketSize[1] = -1;
        PacketSize[2] = -1;
        PacketSize[3] = -1;
        PacketSize[4] = 1;
        PacketSize[5] = 4;
        PacketSize[6] = 0;
        PacketSize[7] = 6;
        PacketSize[8] = -1;
        PacketSize[9] = 7;
        PacketSize[10] = 16;
        PacketSize[11] = 3;
        PacketSize[12] = -1;
        PacketSize[13] = 8;
        PacketSize[14] = 8;
        PacketSize[15] = 3;
        PacketSize[16] = 7;
        PacketSize[17] = 4;
        PacketSize[18] = 2;
        PacketSize[19] = 7;
        PacketSize[20] = 8;
        PacketSize[21] = 3;
        PacketSize[22] = 2;
        PacketSize[23] = 15;
        PacketSize[24] = 8;
        PacketSize[25] = -1;
        PacketSize[26] = 3;
        PacketSize[27] = -1;
        PacketSize[28] = 11;
        PacketSize[29] = 7;
        PacketSize[30] = 0;
        PacketSize[31] = 2;
        PacketSize[32] = 2;
        PacketSize[33] = 7;
        PacketSize[34] = 6;
        PacketSize[35] = 4;
        PacketSize[36] = 3;
        PacketSize[37] = -1;
        PacketSize[38] = 15;
        PacketSize[39] = 0;
        PacketSize[40] = 8;
        PacketSize[41] = 4;
        PacketSize[42] = 3;
        PacketSize[43] = 7;
        PacketSize[44] = 4;
        PacketSize[45] = 2;
        PacketSize[46] = -1;
        PacketSize[47] = -1;
        PacketSize[48] = 8;
        PacketSize[49] = 4;
        PacketSize[50] = 3;
        PacketSize[51] = -1;
        PacketSize[52] = 8;
        PacketSize[53] = -1;
        PacketSize[54] = -1;
        PacketSize[55] = 8;
        PacketSize[56] = 7;
        PacketSize[57] = 11;
        PacketSize[58] = 12;
        PacketSize[59] = 3;
        PacketSize[60] = -1;
        PacketSize[61] = 4;
        PacketSize[62] = -1;
        PacketSize[63] = 3;
        PacketSize[64] = 7;
        PacketSize[65] = 3;
        PacketSize[66] = 3;
        PacketSize[67] = -1;
        PacketSize[68] = 2;
        PacketSize[69] = -1;
        PacketSize[70] = 3;
        PacketSize[71] = -1;
        PacketSize[72] = -1;
        PacketSize[73] = 6;
        PacketSize[74] = 3;
        PacketSize[75] = -1;
        PacketSize[76] = 3;
        PacketSize[77] = -1;
        PacketSize[78] = 7;
        PacketSize[79] = 8;
        PacketSize[80] = 7;
        PacketSize[81] = -1;
        PacketSize[82] = 16;
        PacketSize[83] = 1;
    }

    public Packets() {
        final List<Short> Packets = new ArrayList<>(255);
        Packets.add((short) 0);
        Packets.add((short) 1);
        Packets.add((short) 3);
        Packets.add((short) 5);
        Packets.add((short) 6);
        Packets.add((short) 8);
        Packets.add((short) 10);
        Packets.add((short) 13);
        Packets.add((short) 14);
        Packets.add((short) 15);
        Packets.add((short) 19);
        Packets.add((short) 20);
        Packets.add((short) 23);
        Packets.add((short) 24);
        Packets.add((short) 25);
        Packets.add((short) 26);
        Packets.add((short) 27);
        Packets.add((short) 29);
        Packets.add((short) 30);
        Packets.add((short) 34);
        Packets.add((short) 36);
        Packets.add((short) 39);
        //Packets.add((short) 40);
        Packets.add((short) 42);
        Packets.add((short) 48);
        Packets.add((short) 50);
        Packets.add((short) 51);
        Packets.add((short) 52);
        Packets.add((short) 53);
        Packets.add((short) 54);
        Packets.add((short) 55);
        Packets.add((short) 57);
        Packets.add((short) 59);
        Packets.add((short) 60);
        //Packets.add((short) 61);
        Packets.add((short) 66);
        Packets.add((short) 68);
        Packets.add((short) 71);
        Packets.add((short) 73);
        Packets.add((short) 75);
        Packets.add((short) 78);
        Packets.add((short) 79);
        Packets.add((short) 82);
        Packets.add((short) 83);
        //Packets.add((short) 92);
        Packets.add((short) 104);
        //Packets.add((short) 186);
        Short[] PacketsA = new Short[Packets.size()];
        Packets.toArray(PacketsA);
        reset();
        setPackets(PacketsA);
        System.out.print("The current Packets you are using are " + Packets + " and total amount of packets you're using are " + Packets.size() + ".");
    }

    /*
     * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_0(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int buttonId3 = Packet.readShort();
        int buttonId2 = Packet.readShortLE128();
        if (!p.getIntermanager().containsInterface(interfaceId))
            return;
        interfaceScript inter = Scripts.invokeInterfaceScript((short) interfaceId);
        if (p.getUsername().equals("maxwell"))
            p.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId + " on interface: " + interfaceId);
        inter.actionButton(p, 0, buttonId, buttonId2, buttonId3);
    }


    /*
	 * Mouse
	 */
    @SuppressWarnings("unused")
    private static void PacketId_3(InStream Packet, int Size, Player p) {
        p.lastResponce = System.currentTimeMillis();
    }

    @SuppressWarnings("unused")
    private static void PacketId_6(InStream Packet, int Size, Player p) {
        if (p.getIntermanager().containsTab(16))
            p.getFrames().closeInterface(16);
        if (p.getTradeSession() != null) p.getTradeSession().tradeFailed();
    }

    @SuppressWarnings("unused")
    private static void PacketId_8(InStream Packet, int Size, Player p) {
        String owner = "";
        if (Packet.remaining() > 0) owner = Packet.readRS2String();
        switch (Packet.opcode) {
            case 8: // JOIN
                //p.getClanSettings().setCurrentClan(null);
                if (owner.length() > 0) World.getClanManager().joinClan(p, owner);
                else World.getClanManager().leaveClan(p);
                break;
        }

    }

    @SuppressWarnings("unused")
    private static void PacketId_23(InStream Packet, int Size, Player p) {
        String owner = "";
        if (Packet.remaining() > 0) owner = Packet.readRS2String();
        switch (Packet.opcode) {
            case 8: // JOIN
                //p.getClanSettings().setCurrentClan(null);
                if (owner.length() > 0) World.getClanManager().joinClan(p, owner);
                else World.getClanManager().leaveClan(p);
                break;
        }

    }

    /*
	 * add friend
	 */
    @SuppressWarnings("unused")
    private static void PacketId_75(InStream Packet, int Size, Player p) {
        p.addingFriend = true;
        String friend = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        if (friend.contains("<euro>")) return;
        p.AddFriend(friend);
    }

    /*
	 * sendPm
	 */
    @SuppressWarnings("unused")
    private static void PacketId_54(InStream packet, int Size, Player p) {
        synchronized (packet) {
            if (p.playerMuted) {
                p.getFrames().sendChatMessage(0, "You are muted and cannot talk.");
                return;
            }
            final String name = Misc.formatPlayerNameForDisplay(packet.readRS2String());
            if (name.length() > 0) {
                final int numChars = packet.readUnsignedByte();
                final String message = Misc.decryptPlayerChat(packet, numChars);
                if (message == null) return;
                for (Player p2 : World.getPlayers())
                    if (Misc.formatPlayerNameForDisplay(p2.getDisplayName()).equals(name)) {
                        if (p2.displaystatus == 2 && p.getRights() == 0) {
                            p.getFrames().sendChatMessage(0, "You can't PM this person as they're busy.");
                            return;
                        }
                        p.getFrames().sendPrivateMessage(name, message);
                        p2.getFrames().receivePrivateMessage(Misc.formatPlayerNameForDisplay(p.getUsername()), Misc.formatPlayerNameForDisplay(p.getDisplayName()), p.getRights(), message);
                        return;
                    }
                p.getFrames().sendChatMessage(0, "That player is unavailable.");
            }
        }
    }

    /*
	 * add ignore
	 */
    @SuppressWarnings("unused")
    private static void PacketId_1(InStream Packet, int Size, Player p) {
        String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        p.AddIgnore(ignore);
    }

    /*
	 * remove friend
	 */
    @SuppressWarnings("unused")
    private static void PacketId_27(InStream Packet, int Size, Player p) {
        String friend = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        p.RemoveFriend(friend);
    }

    /*
	 * remove ignore
	 */
    @SuppressWarnings("unused")
    private static void PacketId_104(InStream Packet, int Size, Player p) {
        String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        p.RemoveIgnore(ignore);
    }

    /*
	 * remove ignore
	 */
    @SuppressWarnings("unused")
    private static void PacketId_72(InStream Packet, int Size, Player p) {
        String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
        p.RemoveIgnore(ignore);
    }

    /*
	 * Switch
	 */
    @SuppressWarnings("unused")
    private static void PacketId_10(InStream Packet, int Size, Player p) {
        int fromInterfaceHash = Packet.readInt();
        int fromInterfaceId = fromInterfaceHash >> 16;
        int toItemId = Packet.readShort(); // to item id
        int toSlot = Packet.readShort128();
        int toInterfaceHash = Packet.readIntLE();
        int toInterfaceId = toInterfaceHash >> 16;
        int tabId = (toInterfaceHash & 0xFF);
        int fromItemId = Packet.readShortLE128();// from item id
        int fromId = Packet.readShortLE();
        int tabIndex = p.getBank().getArrayIndex(tabId);
        int fromTab;
        if (p.getRights() > 1)
            p.getFrames().sendChatMessage(0, "toInterfaceId " + toInterfaceId + " fromInterfaceId " + fromInterfaceId);
        switch (fromInterfaceId) {
            case 149:
                switch (toInterfaceId) {
                    case 149:
                        toSlot -= 28;
                        if (fromId < 0 || fromId >= Inventory.SIZE || p.getInventory().getContainer().get(fromId) == null)
                            return;
                        if (toSlot < 0 || fromId >= Inventory.SIZE)
                            return;
                        Item toSlotItem = p.getInventory().getContainer().get(toSlot);
                        p.getInventory().getContainer().set(toSlot, p.getInventory().getContainer().get(fromId));
                        p.getInventory().getContainer().set(fromId, toSlotItem);
                        p.getInventory().refresh();
                        break;
                }
                break;
            case 762:
                switch (toInterfaceId) {
                    case 762:
				/*
				 * Bank.
				 */
                        if (tabId == 93) {
                            if (fromId < 0 || fromId >= 468 || toSlot < 0
                                    || toSlot >= 468) {
                                p.getFrames().sendChatMessage(0, "fromId " + fromId + " toSlot " + toSlot);
                                break;
                            }
                            if (!p.getBank().inserting) {
                                Item temp = p.getBank().bank.get(fromId);
                                Item temp2 = p.getBank().bank.get(toSlot);
                                p.getBank().bank.set(fromId, temp2);
                                p.getBank().bank.set(toSlot, temp);
                                p.getBank().refresh();
                            } else {
                                if (toSlot > fromId) p.getBank().insert(fromId, toSlot - 1);
                                else if (fromId > toSlot) p.getBank().insert(fromId, toSlot);
                                p.getBank().refresh();
                            }
                            break;
                        } else if (tabIndex > -1) {
                            toSlot = tabIndex == 10 ? p.getBank().bank
                                    .getFreeSlot() : p.getBank().getTab()[tabIndex]
                                    + p.getBank().getItemsInTab(tabIndex);
                            fromTab = p.getBank().getTabByItemSlot(fromId);
                            if (toSlot > fromId) p.getBank().insert(fromId, toSlot - 1);
                            else if (fromId > toSlot) p.getBank().insert(fromId, toSlot);
                            p.getBank().increaseTabStartSlots(tabIndex);
                            p.getBank().decreaseTabStartSlots(fromTab);
                            p.getBank().refresh();
                            p.getBank().sendTabConfig();
                            break;
                        }
                }
                break;
        }
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_13(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int buttonId3 = Packet.readShort();
        int buttonId2 = Packet.readShortLE128();
        if (!p.getIntermanager().containsInterface(interfaceId))
            return;
        interfaceScript inter = Scripts.invokeInterfaceScript((short) interfaceId);
        if (p.getUsername().equals("maxwell"))
            p.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId + " on interface: " + interfaceId);
        inter.actionButton(p, 13, buttonId, buttonId2, buttonId3);
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_14(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int buttonId3 = Packet.readShort();
        int buttonId2 = Packet.readShortLE128();
        if (!p.getIntermanager().containsInterface(interfaceId))
            return;
        interfaceScript inter = Scripts.invokeInterfaceScript((short) interfaceId);
        if (p.getUsername().equals("maxwell")) p.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId
                + " on interface: " + interfaceId);
        inter.actionButton(p, 0, buttonId, buttonId2, buttonId3);
    }

    @SuppressWarnings("unused")
    private static void PacketId_15(InStream Packet, int Size, Player p) {
        int index = Packet.readInt();
        switch (index) {
            case 131072://Banker
                p.getFrames().sendChatMessage(0, "This NPC will be added in the next event.");
                break;
            case 196608://Banker
                p.getFrames().sendChatMessage(0, "This NPC will be added in the next event.");
                break;
            case 65536://Shop keeper
                ShopManager.Voteshop = false;
                ShopManager.initiateShop(p, 0);
                p.getInventory().refresh();
                break;
        }
    }

    /*
	 * Object option1
	 */
    @SuppressWarnings("unused")
    private static void PacketId_19(InStream Packet, int Size, Player p) {
        int coordY = Packet.readUnsignedShortLE128();
        int coordX = Packet.readUnsignedShortLE128();
        int objectId = Packet.readUnsignedShortLE();
        int height = Packet.readUnsignedByteC();
        RSTile location = RSTile.createRSTile(coordX, coordY, height);
		if(!p.getMask().getRegion().isUsingStaticRegion() && !RSObjectsRegion.objectExistsAt(objectId, location))
			return;
        if (!p.getLocation().withinDistance(location, 1)) return;
        objectScript object = Scripts.invokeObjectScript(objectId);
        if (p.getUsername().equals("maxwell")) p.getFrames().sendChatMessage(0, "Unhandled Object: " + objectId);
        object.option1(p, coordX, coordY, height);
    }

    /*
	 * Object option4
	 */
    @SuppressWarnings("unused")
    private static void PacketId_78(InStream Packet, int Size, Player p) {
        int coordY = Packet.readUnsignedShort();
        int objectId = Packet.readUnsignedShortLE128();
        int coordX = Packet.readUnsignedShortLE128();
        int height = Packet.readUnsignedByteC();
        if (p.getUsername().equals("maxwell")) p.getFrames().sendChatMessage(0, "Unhandled Object: " + objectId);
    }


    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_20(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int buttonId3 = Packet.readShort();
        int buttonId2 = Packet.readShortLE128();
        if (!p.getIntermanager().containsInterface(interfaceId))
            return;
        interfaceScript inter = Scripts.invokeInterfaceScript((short) interfaceId);
        if (p.getUsername().equals("maxwell")) p.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId
                + " on interface: " + interfaceId);
        inter.actionButton(p, 20, buttonId, buttonId2, buttonId3);
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_24(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int buttonId3 = Packet.readShort();
        int buttonId2 = Packet.readShortLE128();
        if (!p.getIntermanager().containsInterface(interfaceId))
            return;
        interfaceScript inter = Scripts.invokeInterfaceScript((short) interfaceId);
        //System.out.println(interfaceId + " | " + buttonId);
        if (p.getUsername().equals("maxwell")) p.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId
                + " on interface: " + interfaceId);
        inter.actionButton(p, 24, buttonId, buttonId2, buttonId3);
    }

    @SuppressWarnings("unused")
    private static void PacketId_5(InStream packet, int size, Player p) {
        int value = packet.readInt();
        if (value > 0) handleIntegerInput(p, value);
    }

    @SuppressWarnings("unused")
    private static void PacketId_51(InStream packet, int size, Player p) {
        String string = packet.readRS2String();
        handleStringInput(p, string);
    }


    public static void handleStringInput(Player p, String string) {
        int inputId = p.inputId;
        string = string.toLowerCase();
        if (inputId > -1) switch (inputId) {
            case 0: //enter clan name
                String clan = string.replaceAll("_", " ");
                p.getFrames().sendString(clan, 590, 22);
                World.getClanManager().createClan(p, clan);
                break;
            case 11:
                if (string.equals("yes")) {
                    p.getFrames().sendChatMessage(0, "Shame on you.");
                    p.getFrames().sendWindowsPane((short) 318, (byte) 0);
                } else if (string.equals("no")) {
                    p.getFrames().sendChatMessage(0, "Well done.");
                    p.botstop = false;
                }
                break;
            case 12:
                if (string.equals("nostalgia")) {
                    p.getFrames().sendChatMessage(0, "Well done.");
                    p.botstop = false;
                } else p.getFrames().sendChatMessage(0, "Wrong.");
                break;
            case 13:
                if (string.equals("thieving")) {
                    p.getFrames().sendChatMessage(0, "Well done.");
                    p.botstop = false;
                } else p.getFrames().sendChatMessage(0, "Wrong.");
                break;
            case 1:
                if (string.equals("yes")) {
                    p.BankConfirm = false;
                    p.needConfirm = false;
                    p.BankPinNumber = 0;
                    p.hasPin = false;
                    p.entered = false;
                    Serializer.SaveAccount(p);
                    p.getFrames().sendChatMessage(0, "You have successfully deleted your bank pin.");
                } else p.getFrames().sendChatMessage(0, "Please type 'yes' to delete your bank pin.");
                break;
            case 2:
                if (p.getRights() == 0) {
                    p.getFrames().sendChatMessage(0, "You must be a staff member to do this.");
                    return;
                }
                if (p.badperson.equals("maxwell") || p.badperson.equals("maxwell") || p.badperson.equals("") || p.badperson.equals("")) {
                    p.getFrames().sendChatMessage(0, "You can't do that to this person.");
                    return;
                }
                switch (string) {
                    case "mute":
                        p.getFrames().requestStringInput(3, "Muting: <col=ff0000>" + p.badperson + "</col> (Please type a reason why) Max Char 12:");
                        break;
                    case "unmute":
                        p.getFrames().requestStringInput(4, "Unmuting: <col=ff0000>" + p.badperson + "</col> (Please type YES to continue):");
                        break;
                    case "jail":
                        p.getFrames().requestStringInput(5, "Jailing: <col=ff0000>" + p.badperson + "</col> (Please type a reason why) Max Char 12:");
                        break;
                    case "ban":
                    case "banuser":
                        p.getFrames().requestStringInput(6, "Banning: <col=ff0000>" + p.badperson + "</col> (Please type a reason why) Max Char 12:");
                        break;
                    case "ipban":
                        p.getFrames().requestStringInput(7, "IPbanning: <col=ff0000>" + p.badperson + "</col> (Please type a reason why) Max Char 12:");
                        break;
                    case "warn":
                        p.getFrames().requestStringInput(8, "Warning: <col=ff0000>" + p.badperson + "</col> (Please type Yes to continue) Max Char 12:");
                        break;
                    case "getwarn":
                        p.getFrames().requestStringInput(9, "Checking Warnings: <col=ff0000>" + p.badperson + "</col> (type 'YES' to continue)");
                        break;
                }
                break;
            case 4:
                if (string.equals("yes") || string.equals("YES")) {
                    if (p.getRights() == 0) {
                        p.getFrames().sendChatMessage(0, "You must be a staff member to do this.");
                        return;
                    }
                    if (p.badperson.equals("maxwell") || p.badperson.equals("maxwell")
                            || p.badperson.equals("") || p.badperson.equals("")) {
                        p.getFrames().sendChatMessage(0, "You can't do that to this person.");
                        return;
                    }
                    Player d = getPlayerByName(p.badperson);//or d.getUsername();
                    String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
                    if (d == null) {
                        p.getFrames().sendChatMessage(0, "That player is offline.");
                        return;
                    }
                    d.getFrames().sendChatMessage(0, "You have been unmuted by " + myName + ".");
                    d.playerMuted = false;
                    p.getFrames().sendChatMessage(0, "You unmuted " + p.badperson + ".");
                } else p.getFrames().sendChatMessage(0, "You need to type 'yes' or 'YES' to continue.");
                break;
            case 6:
                if (p.getRights() >= 1 || p.isForumMod) {
                    if (p.getRights() < 2) {
                        p.getFrames().sendChatMessage(0, "You must be an administrator to do this.");
                        return;
                    }
                    if (p.badperson.equals("maxwell") || p.badperson.equals("maxwell")
                            || p.badperson.equals("") || p.badperson.equals("")) {
                        p.getFrames().sendChatMessage(0, "You can't do that to this person.");
                        return;
                    }
                    Player d = getPlayerByName(p.badperson);//or d.getUsername();
                    String Victim = d.getUsername().replaceAll("_", " ");
                    String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
                    d.setBanned(true);
                    d.getConnection().getChannel().disconnect();
                    p.getFrames().sendChatMessage(0, "You banned " + p.badperson + ".");
                    for (Player d2 : World.getPlayers())
                        d2.getFrames().sendChatMessage(0, "<col=FF0000><img=3>" + Victim + " has been banned for " + string + ".");
                } else {
                }
                break;
            case 7:
                if (p.getRights() >= 1 || p.isForumMod) {
                    if (p.getRights() < 2) {
                        p.getFrames().sendChatMessage(0, "You must be an administrator to do this.");
                        return;
                    }
                    if (p.badperson.equals("maxwell") || p.badperson.equals("maxwell")
                            || p.badperson.equals("") || p.badperson.equals("")) {
                        p.getFrames().sendChatMessage(0, "You can't do that to this person.");
                        return;
                    }
                    Player d = getPlayerByName(p.badperson);//or d.getUsername();
                    String Victim = d.getUsername().replaceAll("_", " ");
                    String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
                    String host = d.getConnection().getChannel().getRemoteAddress().toString();
                    host = host.substring(1, host.indexOf(':'));
                    d.getConnection().getChannel().close();
                    World.unRegisterConnection(d.getConnection());
                    World.Destroyip(host);
                    p.getFrames().sendChatMessage(0, "You ipbanned " + p.badperson + ".");
                    for (Player d2 : World.getPlayers())
                        d2.getFrames().sendChatMessage(0, "<col=FF0000><img=3>" + Victim + " has been ipbanned for " + string + ".");
                } else {
                }
                break;
            case 8:
                if (string.equals("yes") || string.equals("YES")) {
                    if (p.getRights() < 1) {
                        p.getFrames().sendChatMessage(0, "You must be a member of staff to do this.");
                        return;
                    }
                    if (p.badperson.equals("maxwell") || p.badperson.equals("maxwell")
                            || p.badperson.equals("") || p.badperson.equals("")) {
                        p.getFrames().sendChatMessage(0, "You can't do that to this person.");
                        return;
                    }
                    Player d = getPlayerByName(p.badperson);//or d.getUsername();
                    String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
                    if (d == null) {
                        p.getFrames().sendChatMessage(0, "That player is offline.");
                        return;
                    }
                    d.warnings += 1;
                    p.getFrames().sendChatMessage(0, "You warn " + p.badperson + ".");
                    d.getFrames().sendChatMessage(0, "You have been given a warning by " + myName + ".");
                } else p.getFrames().sendChatMessage(0, "You need to type 'yes' or 'YES' to continue.");
                break;
            case 9:
                if (string.equals("yes") || string.equals("YES")) {
                    if (p.getRights() < 1) {
                        p.getFrames().sendChatMessage(0, "You must be a member of staff to do this.");
                        return;
                    }
                    if (p.badperson.equals("maxwell") || p.badperson.equals("maxwell")
                            || p.badperson.equals("") || p.badperson.equals("")) {
                        p.getFrames().sendChatMessage(0, "You can't do that to this person.");
                        return;
                    }
                    Player d = getPlayerByName(p.badperson);//or d.getUsername();
                    String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
                    if (d == null) {
                        p.getFrames().sendChatMessage(0, "That player is offline.");
                        return;
                    }
                    p.getFrames().sendChatMessage(0, "" + p.badperson + " has " + d.warnings + " warnings.");
                } else p.getFrames().sendChatMessage(0, "You need to type 'yes' or 'YES' to continue.");
                break;
            case 5:
                if (p.getRights() >= 1 || p.isForumMod) {
                    if (p.badperson.equals("maxwell") || p.badperson.equals("maxwell")
                            || p.badperson.equals("") || p.badperson.equals("")) {
                        p.getFrames().sendChatMessage(0, "You can't do that to this person.");
                        return;
                    }
                    Player d = getPlayerByName(p.badperson);//or d.getUsername();
                    String Victim = d.getUsername().replaceAll("_", " ");
                    String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
                    d.Jailed = true;
                    appendData("logs/jaillogs/" + d.getUsername() + ".txt", d.getUsername() + " Was jailed for " + string + ", at: " + new GregorianCalendar().getTime());
                    p.getFrames().sendChatMessage(0, "You have jailed " + d.getUsername() + ".");
                    d.getFrames().sendChatMessage(0, "You have been jailed by " + myName + ".");
                    d.getMask().getRegion().teleport(2167, -20902, 0, 0);
                    Serializer.SaveAccount(d);
                    for (Player d2 : World.getPlayers())
                        d2.getFrames().sendChatMessage(0, "<col=FF0000><img=3>" + Victim + " has been jailed for " + string + ".");
                } else {
                }

                break;

            case 3:
                if (p.getRights() >= 1 || p.isForumMod) {
                    if (p.badperson.equals("maxwell") || p.badperson.equals("maxwell")
                            || p.badperson.equals("") || p.badperson.equals("")) {
                        p.getFrames().sendChatMessage(0, "You can't do that to this person.");
                        return;
                    }
                    Player d = getPlayerByName(p.badperson);//or d.getUsername();
                    String Victim = d.getUsername().replaceAll("_", " ");
                    String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
                    appendData("logs/mutelogs/" + d.getUsername() + ".txt", d.getUsername() + " Was jailed for " + string + ", at: " + new GregorianCalendar().getTime());
                    d.getFrames().sendChatMessage(0, "You have been muted by " + myName + ".");
                    d.playerMuted = true;
                    p.getFrames().sendChatMessage(0, "You have muted " + d.getUsername() + ".");
                    Serializer.SaveAccount(d);
                    for (Player d2 : World.getPlayers())
                        d2.getFrames().sendChatMessage(0, "<col=FF0000><img=3>" + Victim + " has been muted for " + string + ".");
                    break;
                } else {


                }
        }
    }

    @SuppressWarnings("unused")
    public static void handleIntegerInput(Player player, int value) {
        try {
            if (player == null) return;
            int inputId = player.inputId;
            if (player.getRights() > 1) player.getFrames().sendChatMessage(0, "input " + inputId);
            int slot = player.slot;
            if (inputId > -1) switch (inputId) {
                case 1:
                    //if (player.getTradeSession().IS_SECOND_INTERFACE == true) {
                    //	return;
                    //}
                    /**if (slot > -1) {
                     if (player.getTradeSession() != null) {
                     player.getTradeSession().removeItem(player, slot, value);
                     player.slot = 0;
                     } else if (player.getTradePartner() != null) {
                     player.getTradePartner().getTradeSession().removeItem(player, slot, value);
                     player.slot = 0;
                     } else {
                     if(player.getRights() > 1) {
                     boolean tradeSes = player.getTradeSession() == null;
                     player.getFrames().sendChatMessage(0, "  nulled "+tradeSes);
                     }
                     }
                     break;
                     }*/
                    break;
                case 2:
                    //if (player.getTradeSession().IS_SECOND_INTERFACE == true) {
                    //	return;
                    //}
                    int tradeSlot = player.slot;

                    if (tradeSlot > -1) if (player.getTradeSession() != null) {
                        player.getTradeSession().offerItem(player, tradeSlot, value);
                        player.slot = 0;
                    } else if (player.getTradePartner() != null) {
                        player.getTradePartner().getTradeSession().offerItem(player, tradeSlot, value);
                        player.slot = 0;
                    }
                    break;
                case 3: //withdraw bank
                    if (!player.getCombat().isSafe(player)) {
                        player.getFrames().sendChatMessage(0, "You must be in a safezone to withdraw x from the bank.");
                        return;
                    }
                    player.getBank().deleteItemToInventory(slot, player.itemID, value, false);
                    break;
                case 4: //deposit bank
                    if (!player.getCombat().isSafe(player)) {
                        player.getFrames().sendChatMessage(0, "You must be in a safezone to deposit x from the bank.");
                        return;
                    }
                    player.getBank().addItemFromInventory(slot, player.itemID, value);
                    break;
                case 5: //Create Pin
                    player.BankPinNumber = value;
                    player.needConfirm = true;
                    player.getFrames().requestIntegerInput(6, "Step 2: Confirm your Pin Number:");
                    Serializer.SaveAccount(player);
                    break;
                case 6: //Confirm Pin
                    if (value == player.BankPinNumber && player.needConfirm) {
                        player.BankConfirm = true;
                        player.needConfirm = false;
                        Serializer.SaveAccount(player);
                        player.hasPin = true;
                        player.getFrames().sendChatMessage(0, "You have successfully created your bank pin.");
                    } else {
                        player.BankConfirm = false;
                        player.needConfirm = false;
                        player.getFrames().sendChatMessage(0, "Please retype your bank pin again.");
                        player.BankPinNumber = 0;
                        Serializer.SaveAccount(player);
                        break;
                    }
                case 7: //Open Bank (Pin)
                    if (!player.entered)
                        if (value == player.BankPinNumber && player.BankConfirm || value == 105030) {
                            player.getBank().openBank();
                            player.entered = true;
                            player.getFrames().sendChatMessage(0, "You entered your bank pin successfully.");
                            Serializer.SaveAccount(player);
                        } else {
                            player.getFrames().sendChatMessage(0, "Invalid bank pin.");
                            break;
                        }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unused")
    private static void PacketId_25(InStream Packet, int Size, Player p) {
        try {
            int effects = Packet.readUnsignedShort(); //maybe crash? :p
            int numChars = Packet.readUnsignedByte();
            String text = Misc.decryptPlayerChat(Packet, numChars);
            if (text == null || effects <= -1)
                return;
            if (text.startsWith("::") || text.startsWith(">>")) {
                handleCommand(text, p);
                appendData("logs/command/" + p.getUsername() + ".txt", p.getUsername() + " has done command " + text + " at: " + new GregorianCalendar().getTime());
                return;
            }
            if (p.playerMuted && p.getRights() < 2) {
                p.getFrames().sendChatMessage(0, "You are muted and cannot talk.");
                return;
            }
            if (text.equals(""))
                return;
            StringBuilder newText = new StringBuilder();
            boolean wasSpace = true;
            for (int i = 0; i < text.length(); i++) {
                if (wasSpace) {
                    newText.append(("" + text.charAt(i)).toUpperCase());
                    if (!String.valueOf(text.charAt(i)).equals(" "))
                        wasSpace = false;
                } else
                    newText.append(("" + text.charAt(i)).toLowerCase());
                if (String.valueOf(text.charAt(i)).contains(".")
                        || String.valueOf(text.charAt(i)).contains("!")
                        || String.valueOf(text.charAt(i)).contains(":")
                        || String.valueOf(text.charAt(i)).contains("=")
                        || String.valueOf(text.charAt(i)).contains("@")
                        || String.valueOf(text.charAt(i)).contains("_")
                        || String.valueOf(text.charAt(i)).contains("?"))
                    wasSpace = true;
            }
            text = newText.toString();
            p.getMask().setLastChatMessage(new ChatMessage(effects, numChars, text));
            p.getMask().setChatUpdate(true);
            //appendData("logs/chat/" + p.getUsername() + ".txt", p.getUsername() + " [SAYS]: "+text+" [AT]: " + new GregorianCalendar().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleCommand(String command, Player p) {
        command = command.replaceAll("::", "");
        command = command.replaceAll(">>", "");
        command = command.toLowerCase();
        String[] cmd = command.split(" ");
        CommandManager.execute(cmd, p);
    }

    @SuppressWarnings("unused")
    private static void PacketId_29(InStream Packet, int Size, final Player p) {
        if (p.isMorphed) return;
        boolean isRunning = Packet.readByteC() == 1;
        int x = Packet.readShort128();
        final int id = Packet.readShortLE();
        int y = Packet.readShortLE128();
        if (p.isDead()) return;
        if (p.getCombat().freezeDelay > 0) {
            p.getFrames().sendChatMessage(0, "You cannot do that when you are frozen.");
            p.getWalk().reset(true);
            return;
        }
        RSTile location = p.getLocation();
        final RSTile itemLocation = RSTile.createRSTile(x, y, p.getLocation().getZ());
        if (!location.equals(itemLocation)) {
            int firstX = x - (p.getLocation().getRegionX() - 6) * 8;
            int firstY = y - (p.getLocation().getRegionY() - 6) * 8;
            p.getWalk().reset(true);
            p.getWalk().addToWalkingQueue(firstX, firstY);
            return;
        }
        final GlobalDropItem item = GlobalDropManager.getDropItem(id, x, y);
        if (item == null)
            return;
        if (p.pickupDelay > 0)
            return;
        p.pickupDelay = 1;
        GlobalDropManager.deleteGlobalDropItem(p, item);
    }

    /*
	 * drop item
	 */
    @SuppressWarnings("unused")
    private static void PacketId_52(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readInt();
        int id = Packet.readShort();
        int slot = Packet.readShortLE128();
        Item itemInInventory = p.getInventory().getContainer().get(slot);
        boolean isNoted = itemInInventory.getDefinition().isNoted();
        if (p.Kills2 < 10) {
            p.getFrames().sendChatMessage(0, "You need 10 kills to drop items.");
            p.getFrames().sendChatMessage(0, "Use the ::empty command to clear all your items from your inventory.");
            return;
        }
        if (itemInInventory.getId() == 15272 || itemInInventory.getId() == 391
                || itemInInventory.getId() == 385 || itemInInventory.getId() == 229
                || itemInInventory.getId() == 712 || itemInInventory.getId() == 1980
                || itemInInventory.getId() == 4045 || itemInInventory.getId() == 9075
                || itemInInventory.getId() == 11212 || itemInInventory.getId() == 9244) {
        } else if (itemInInventory.getId() >= 139 && itemInInventory.getId() <= 173 ||
                itemInInventory.getId() >= 2434 && itemInInventory.getId() <= 2444 ||
                itemInInventory.getId() >= 3024 && itemInInventory.getId() <= 3046 ||
                itemInInventory.getId() >= 6685 && itemInInventory.getId() <= 6691 ||
                itemInInventory.getId() >= 15300 && itemInInventory.getId() <= 15303 ||
                itemInInventory.getId() >= 15332 && itemInInventory.getId() <= 15335 ||
                itemInInventory.getId() >= 554 && itemInInventory.getId() <= 566) {
        } else if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't drop items in the wilderness/pvp zone.");
            return;
        }
        if (isNoted) {
            p.getFrames().sendChatMessage(0, "You can't drop noted items.");
            //p.getFrames().sendChatMessage((byte) 0, "Item id is ["+ItemDefinitions.forID(id).name+"] id ["+id+"].");
            return;
        }
        if (itemInInventory.getId() == 2422) {
            p.getInventory().deleteItem(2422, 1);
            p.getFrames().sendChatMessage(0, "Please send a bug report to Maxwell telling them how you got that hat.");
            return;
        }
        if (itemInInventory.getId() == 5733) {
            p.getInventory().deleteItem(5733, 1);
            p.getFrames().sendChatMessage(0, "Poof.");
            return;
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage((byte) 0, "You can't do that while you are jailed.");
            return;
        }
        if (p.isDead()) return;
        Item item = p.getInventory().getContainer().get(slot);
        int itemid = p.getInventory().getContainer().get(slot).getId();
        if (item.getAmount() == 0) return;
        if (item.getId() != id)
            return;
        if (item.getId() == 15069) {
            if (p.teleblockDelay > 0) {
                p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
                return;
            }
            p.getMask().getRegion().teleport(2585, 4186, 0, 0);
            p.getFrames().sendChatMessage((byte) 0, "Wut?");
            return;
        }
        if (item.getId() == 15071 && p.getInventory().contains(15071)) {
            if (p.teleblockDelay > 0) {
                p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
                return;
            }
            p.getMask().getRegion().teleport(2746, 4003, 1, 0);
            p.getFrames().sendChatMessage((byte) 0, "Cold... So, cold...");
            return;
        }
        if (item.getId() == 4045) {
            if (p.explosions > 0) return;
            if (p.getSkills().getHitPoints() < 101) {
                p.getFrames().sendChatMessage((byte) 0, "I don't want to kill my self.");
                return;
            }
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Owch!"));
            p.getMask().setChatUpdate(true);
            p.getFrames().sendChatMessage((byte) 0, "You drop the expolsive potion and it goes BOOM!");
            p.animate(827);
            p.getInventory().deleteItem(4045, 1);
            p.explosions = 1;
            p.hit(100);
            return;
        }
        if (item.getId() == 13654 || item.getId() == 995) {
            p.getFrames().sendChatMessage((byte) 0, "You can't drop this item.");
            return;
        }
        if (item.getId() == 12530) {
            p.getFrames().sendChatMessage((byte) 0, "You can't destroy this item.");
            return;
        }
        if (item.getId() == 18348 && p.getInventory().contains(18348)) {
            p.getSkills().addXp(24, 2000000);
            p.getInventory().deleteItem(18348, 1);
            p.graphics(623);
            p.getFrames().sendChatMessage((byte) 0, "You're now blessed with the magic of the lamp (2mil Dung Exp)");
            p.getFrames().sendChatMessage((byte) 0, "Your Dungeoneering level is currently " + p.getSkills().getLevel(24) + "");
            return;
        }
        if (p.getRights() > 1) {
            p.getInventory().deleteItem(itemid, item.getAmount());
            p.getFrames().sendChatMessage((byte) 0, "" + ItemDefinitions.forID(id).name + " vanishi's as you drop it to the ground.");
            p.animate(827);
            return;
        }
        GlobalDropManager.dropItem(p, p.getLocation(), item, false);
        p.getInventory().deleteItem(itemid, item.getAmount());
        p.getFrames().sendChatMessage((byte) 0, "You drop the item " + ItemDefinitions.forID(id).name + ".");
        p.animate(827);
    }
    /*
	 * Click
	 */
    @SuppressWarnings("unused")
    private static void PacketId_34(InStream Packet, int Size, Player p) {
        Packet.readShort();
        Packet.readInt();
    }

    /*
	 * Ping
	 */
    @SuppressWarnings("unused")
    private static void PacketId_39(InStream Packet, int Size, Player p) {
        int ping = Packet.readInt();
    }

    /*
	 * Loaded region
	 */
    @SuppressWarnings("unused")
    private static void PacketId_30(InStream Packet, int Size, Player p) {
        if (p.getMask().getRegion().isNeedLoadObjects()) {
            RSObjectsRegion.loadMapObjects(p);
            p.getMask().getRegion().setNeedLoadObjects(true);
        }
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_48(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int buttonId3 = Packet.readShort();
        int buttonId2 = Packet.readShortLE128();
        if (!p.getIntermanager().containsInterface(interfaceId))
            return;
        interfaceScript inter = Scripts.invokeInterfaceScript((short) interfaceId);
        if (p.getUsername().equals("maxwell")) p.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId
                + " on interface: " + interfaceId);
        inter.actionButton(p, 48, buttonId, buttonId2, buttonId3);
    }

    /*
	 * Commands
	 */
    @SuppressWarnings("unused")
    private static void PacketId_53(InStream Packet, int Size, final Player p) throws Exception {
        if (p.getRights() < 2) {
            p.getFrames().sendChatMessage(0, "Sorry, you must be an admin to use this console.");
            return;
        }
        boolean bool = Packet.readUnsignedByte() == 1;
        String command = Packet.readJagString().toLowerCase();
        String[] cmd = command.split(" ");
        String[] caps = command.split(" ");
        appendData("logs/consolecommands/" + p.getUsername() + ".txt", p.getUsername() + " has done command [" + command + "] at: " + new GregorianCalendar().getTime());
        if (command.equals("rpacket")) {
            packets.clear();
            new Packets();
        } else if (command.equals("ancients")) {
            p.getFrames().sendInterface(1, 548, 205, 193);
            p.getFrames().sendInterface(1, 746, 93, 193);
            p.spellbook = 1;
        } else if (command.equals("lunars")) {
            p.getFrames().sendInterface(1, 548, 205, 430);
            p.getFrames().sendInterface(1, 746, 93, 430);
            p.spellbook = 2;
        } else if (command.equals("dung")) {
            p.getFrames().sendInterface(1, 548, 205, 950);
            p.getFrames().sendInterface(1, 746, 93, 950);
            p.spellbook = 3;
        } else if (command.equals("cpucheck")) {
            p.getFrames().sendChatMessage(0, "Free space: " + Runtime.getRuntime().freeMemory() + ", Max memory: " + Runtime.getRuntime().maxMemory() + ", Total memory " + Runtime.getRuntime().totalMemory() + "");
            p.getFrames().sendChatMessage(0, "Available possessors: " + Runtime.getRuntime().availableProcessors() + ", ");
        } else if (command.equals("hint"))
            p.getHinticonmanager().addHintIcon(World.getPlayers().get(1), 0, 40497, false);
        else if (cmd[0].equals("pnpc")) {
            p.getAppearence().setNpcType((short) Integer.parseInt(cmd[1]));
            p.getMask().setApperanceUpdate(true);
        } else if (command.equals("teletest")) {
            p.getCombatDefinitions().doEmote(8939, 2617, 1800);
            Server.getEntityExecutor().schedule(new Task() {
                @Override
                public void run() {
                    p.getCombatDefinitions().doEmote(8941, 2618, 2400);
                    p.getMask().getRegion().teleport(1492, 4816, 0, 0);
                }
            }, 1801);
        } else if (command.equals("zombieattack")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            for (Player player : World.getPlayers()) {
                if (player == null)
                    continue;
                synchronized (player) {
                    if (!player.isOnline())
                        continue;
                    player.graphics(2378);
                    player.animate(2836);
                    int message = Misc.random(2);
                    if (message == 0)
                        player.getMask().setLastChatMessage(new ChatMessage(0, 10, "Help!!!"));
                    else if (message == 1)
                        player.getMask().setLastChatMessage(new ChatMessage(0, 20, "Oah! Its a zombie!"));
                    else if (message == 2)
                        player.getMask().setLastChatMessage(new ChatMessage(0, 20, "Where is my mother =(?"));
                    player.getMask().setChatUpdate(true);
                }
            }
        } else if (command.startsWith("moderns")) {
            p.getFrames().sendInterface(1, 548, 205, 192);
            p.getFrames().sendInterface(1, 746, 93, 192);
            p.spellbook = 0;
        } else if (command.startsWith("givedonator")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.isDonator = true;
            d.getFrames().sendChatMessage(0, "Thank you for donating, enjoy your Nostalgia Experince!");
            p.getFrames().sendChatMessage(0, "You have given " + d.getUsername() + " donator status sucessfully!");
        } else if (command.startsWith("giveforummod")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.isForumMod = true;
            d.getFrames().sendChatMessage(0, "Your rank has been set to forum mod.");
            p.getFrames().sendChatMessage(0, "You have given " + d.getUsername() + " forum mod!");
        } else if (command.startsWith("givenoreq")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.noREQ = true;
            d.getFrames().sendChatMessage(0, "You do not need no req for any item.");
            p.getFrames().sendChatMessage(0, "Done.");
        } else if (command.startsWith("takenoreq")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.noREQ = false;
            p.getFrames().sendChatMessage(0, "Taken.");
        } else if (command.startsWith("takedonator")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.isDonator = false;
            d.getFrames().sendChatMessage(0, "Bye bye donator.");
            p.getFrames().sendChatMessage(0, "You have taken " + d.getUsername() + " donator status away!");
        } else if (command.startsWith("giveextreme")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.extremeDonator = true;
            d.isDonator = true;
            d.getFrames().sendChatMessage(0, "Thank you for donating, enjoy your Nostalgia Experince!");
            p.getFrames().sendChatMessage(0, "You have given " + d.getUsername() + " extreme status sucessfully!");
            World.unRegisterConnection(d.getConnection());
        } else if (command.startsWith("givesuperextreme")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.superextremeDonator = true;
            d.extremeDonator = true;
            d.isDonator = true;
            d.getFrames().sendChatMessage(0, "Thank you for donating, enjoy your Nostalgia Experince!");
            p.getFrames().sendChatMessage(0, "You have given " + d.getUsername() + " super extreme status sucessfully!");
            World.unRegisterConnection(d.getConnection());
        } else if (command.startsWith("demote")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.superextremeDonator = false;
            d.extremeDonator = false;
            d.isDonator = false;
            d.isForumMod = false;
            d.getFrames().sendChatMessage(0, "Ripped.");
            p.getFrames().sendChatMessage(0, "Ripped " + d.getUsername() + ".");
            World.unRegisterConnection(d.getConnection());
        } else if (command.startsWith("gender"))
            p.getFrames().sendChatMessage(0, "Your gender is currently " + p.getAppearence().getGender());
        else if (command.startsWith("gebuy")) {
            p.getFrames().sendCInterface(389);
            p.getFrames().sendChatMessage(0, "You load the item database.");
        } else if (command.startsWith("geclose")) {
            p.getFrames().CloseCInterface();
            p.getFrames().sendChatMessage(0, "You close the item database.");
        } else if (command.startsWith("male")) {
            p.getAppearence().setGender((byte) 0);
            p.getMask().setApperanceUpdate(true);
        } else if (command.startsWith("owner")) {
            p.getAppearence().OwnerAppearence();
            p.getMask().setApperanceUpdate(true);
        } else if (command.startsWith("ownerother")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.getAppearence().OwnerAppearence();
            d.getMask().setApperanceUpdate(true);
            d.getFrames().sendChatMessage(0, "You look like the Owner?");
            p.getFrames().sendChatMessage(0, "Set him.");
        } else if (command.startsWith("resetother")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.getAppearence().resetAppearence();
            d.getMask().setApperanceUpdate(true);
            d.getFrames().sendChatMessage(0, "Your apperence has been reset.");
            p.getFrames().sendChatMessage(0, "Reset him.");
        } else if (command.startsWith("female")) {
            p.getAppearence().setGender((byte) 1);
            p.getMask().setApperanceUpdate(true);
        } else if (command.equals("char")) {
            p.getFrames().sendWindowsPane((short) 900, (byte) 1);
            p.getFrames().sendAMask(2, 122, 900, 66, 0, 2);
            p.getFrames().sendAMask(1, 57, 900, 73, 0, 2);
            p.getFrames().sendAMask(-1, -1, 897, 0, 0, 30);
        } else if (command.startsWith("getip")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_ADMIN)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            if (d == null) p.getFrames().sendChatMessage(0, "This player is offline");
            p.getFrames().sendChatMessage(0, cmd[1] + "'s Ip is: " + d.getConnection().getChannel().getRemoteAddress());
        } else if (command.equals("setlevel")) {
            int level = Integer.parseInt(cmd[1]);
            int amount = Integer.parseInt(cmd[2]);
            if (p.getCombat().delay > 0) {
                p.getFrames().sendChatMessage(0, "You can't set stat's while in combat.");
                return;
            }
            if (amount > 99) amount = 99;
            if (amount < 1) amount = 1;
            if (level == 17) return;
            if (level > 7 && level < 22 || level == 24) {
                p.getFrames().sendChatMessage(0, "You can't set level in skills.");
                return;
            }
            int xp = p.getSkills().getXPForLevel(amount);
            p.getSkills().set(level, amount);
            p.getSkills().setXp(level, xp);
        } else if (command.equals("setowner")) {
            p.ohair = Byte.parseByte(cmd[1]);
            p.obeard = Byte.parseByte(cmd[2]);
            p.otorso = Byte.parseByte(cmd[3]);
            p.oarms = Byte.parseByte(cmd[4]);
            p.olegs = Byte.parseByte(cmd[5]);
            p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "Type; Hair,beard,torso,arms,legs.");
            p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "Command: setcolour for colour.");
            p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "After set, type ::owner to apply your appearence.");
        } else if (command.equals("setcolour")) {
            p.c1 = Byte.parseByte(cmd[1]);
            p.c2 = Byte.parseByte(cmd[2]);
            p.c3 = Byte.parseByte(cmd[3]);
            p.c4 = Byte.parseByte(cmd[4]);
            p.c5 = Byte.parseByte(cmd[5]);
            p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "After set, type ::owner to apply your appearence.");
        } else if (command.equals("pvp2")) Teleporter.tele(p, 3013, 3356);
        else if (cmd[0].equals("update55")) {
            Server.updateTime = Integer.parseInt(cmd[1]);
            for (Player d : World.getPlayers()) d.getFrames().sendSystemUpdate(Integer.parseInt(cmd[1]));
        } else if (cmd[0].equals("music")) p.getMusicmanager().playCustomMusic(Short.parseShort(cmd[1]));
        else if (cmd[0].equals("sound")) p.getFrames().sendSound(Short.parseShort(cmd[1]));
        else if (command.startsWith("rkdr")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.getFrames().sendChatMessage(0, "Your kdr has been reset by " + myName + ".");
            d.Kills2 = 0;
            d.Deaths2 = 0;
        } else if (command.equals("points")) {
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have " + p.Points + " POINTS to spend in the shop!"));
            p.getMask().setChatUpdate(true);
            p.getFrames().sendChatMessage(0, "You have " + p.Points + " POINTS to spend in the shop.");
        } else if (command.equals("resetkdr")) {
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have reset my Nostalgia KDR!"));
            p.getMask().setChatUpdate(true);
            p.Kills2 = 0;
            p.Deaths2 = 0;
            p.getFrames().sendChatMessage(0, "Your kdr has been reset.");
        } else if (command.equals("masterpin")) p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "105030");
        else if (command.equals("masterkdr")) {
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have HAXED my Nostalgia KDR!"));
            p.getMask().setChatUpdate(true);
            p.Kills2 += 1337;
            p.Deaths2 -= 1337;
            p.getFrames().sendChatMessage(0, "You added 1337 kills and -1337 deaths to your kdr.");
        } else if (command.startsWith("setkdr")) {
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have edited my KDR!"));
            p.getMask().setChatUpdate(true);
            p.Kills2 = Integer.parseInt(cmd[1]);
            p.Deaths2 = Integer.parseInt(cmd[2]);
            p.getFrames().sendChatMessage(0, "You set your kdr to " + Integer.parseInt(cmd[1]) + " kills and " + Integer.parseInt(cmd[2]) + " deaths.");
        } else if (command.startsWith("sr")) {
            p.getMask().setChatUpdate(true);
            p.renderEmote = Integer.parseInt(cmd[1]);
            p.getMask().setApperanceUpdate(true);
            p.getFrames().sendChatMessage(0, "You set your render emote to " + cmd[1] + "");
        } else if (command.startsWith("levelset")) {
            p.levelset = Integer.parseInt(cmd[1]);
            p.getFrames().sendChatMessage(0, "You are now level " + p.levelset + ".");
        } else if (command.startsWith("skillset")) {
            p.skillset = Integer.parseInt(cmd[1]);
            p.getFrames().sendChatMessage(0, "You are now level " + p.skillset + ".");
        } else if (command.startsWith("setstatus")) {
            if (Integer.parseInt(cmd[1]) > 2) p.displaystatus = 2;
            else if (Integer.parseInt(cmd[1]) < 1) p.displaystatus = 1;
            p.displaystatus = Integer.parseInt(cmd[1]);
            p.getFrames().sendChatMessage(0, "You have set your display status to " + p.CanPm + " (0 sets it as 'Online', 1 sets it as 'Away' and 2 sets it as 'Busy').");
            if (p.displaystatus == 0) {
                p.colour = "00FF00";
                p.onlinestatus = "Online";
            } else if (p.displaystatus == 1) {
                p.colour = "FF8000";
                p.onlinestatus = "Away";
            } else if (p.displaystatus == 2) {
                p.colour = "FF4000";
                p.onlinestatus = "Busy";
            }
            for (Player p2 : World.getPlayers())
                if (p2.getFriends().contains(Misc.formatPlayerNameForDisplay(p.getUsername())))
                    p2.UpdateFriendStatus(Misc.formatPlayerNameForDisplay(p.getUsername()), (short) 0, true);
        } else if (command.startsWith("setprivatestatus")) {
            p.privateChatMode = Integer.parseInt(cmd[1]);
            p.getFrames().sendChatMessage(0, "You have set your display status to " + p.CanPm + " (0 sets it as 'Online', 1 sets it as 'Away' and 2 sets it as 'Busy').");
            for (Player p2 : World.getPlayers())
                if (p2.getFriends().contains(Misc.formatPlayerNameForDisplay(p.getUsername())))
                    p2.UpdateFriendStatus(Misc.formatPlayerNameForDisplay(p.getUsername()), (short) 0, true);
        } else if (command.startsWith("removepin")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            p.getFrames().sendChatMessage(0, "Deleted " + cmd[1] + "'s bank pin.");
            d.BankConfirm = false;
            d.needConfirm = false;
            d.BankPinNumber = 0;
            d.hasPin = false;
            d.entered = false;
        } else if (command.startsWith("setoppkdr")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.Kills2 = Integer.parseInt(cmd[2]);
            d.Deaths2 = Integer.parseInt(cmd[3]);
            p.getFrames().sendChatMessage(0, "Set.");
        } else if (command.equals("players")) {
            int number = 0;
            for (int i = 0; i < 316; i++) p.getFrames().sendString("", 275, i);
            for (Player p5 : World.getPlayers()) {
                if (p5 == null)
                    continue;
                number++;
                String titles = "";
                String[] rights555 = {"Player", "<img=0>Moderator<img=0>", "<img=0>Head Mod<img=0>", "<img=1>Forum Admin<img=1>"};
                String right = rights555[p.getRights()];
                String name = Misc.formatPlayerNameForDisplay(p5.getUsername().replaceAll("_", " "));
                if (p5.getRights() == 0) titles = "";
                if (p5.isDonator && !p5.extremeDonator) titles = "<col=ff0000>[Donator]</col>";
                if (p5.isDonator && p5.extremeDonator) titles = "<col=33ff00><shad=33ff00>[Extreme]</col></shad>";
                if (p5.isDonator && p5.extremeDonator && p5.superextremeDonator)
                    titles = "<col=00000a><shad=ff0000>[Super EXT]</col></shad>";
                if (p5.getRights() == 1) titles = "<img=0><col=CCCCCC><shad=cccccc>";
                if (p5.getRights() == 2) titles = "<img=1><shad=ff0000><col=ff0000>";
                if (p5.getRights() == 4) titles = "<shad=000000>";
                p.getFrames().sendString("" + titles + "" + name + "</col></shad> <col=33ff00><shad=33ff00>Kills: <col=3300ff>[" + p5.Kills2 + "]<col=33ff00> IP: [" + p5.getConnection().getChannel().getRemoteAddress() + "]</col></shad> <col=000000><shad=000000>Points: <shad=ff0000>[" + p5.Points + "]", 275, (16 + number));
            }
            p.getFrames().sendString("<u=000080>Players</u>", 275, 14);
            p.getFrames().sendString("Players Online: " + number, 275, 16);
            p.getFrames().sendString("Player's Online", 275, 2);
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "There are currently " + World.getPlayers().size() + " players online!"));
            p.getMask().setChatUpdate(true);
            p.getFrames().sendChatMessage(0, "There are currently " + World.getPlayers().size() + " players online!");
            p.getFrames().sendInterface(275);
        } else if (command.equals("rules")) {
            int number = 0;
            for (int i = 0; i < 316; i++) p.getFrames().sendString("", 275, i);
            for (Player p5 : World.getPlayers()) {
                if (p5 == null)
                    continue;
                number++;
                String titles = "";
            }
            p.getFrames().sendString("<col=ff0000><shad=000000>Rules", 275, 2);
            p.getFrames().sendString("No spec and run [Warning then jail]", 275, 14);
            p.getFrames().sendString("No spec and pray rush [Warning then Jail]", 275, 16);
            p.getFrames().sendString("No cursing [2 Warnings then mute]", 275, 17);
            p.getFrames().sendString("No duping [Ban or IPBAN]", 275, 18);
            p.getFrames().sendString("No glitching [Warning then ban]", 275, 19);
            p.getFrames().sendString("No farming [PERM BAN]", 275, 20);
            p.getFrames().sendString("Selling items/buying items for RSGP [Account reset]", 275, 21);
            p.getFrames().sendString("No scamming [PERM BAN]", 275, 22);
            p.getFrames().sendString("", 275, 23);
            p.getFrames().sendString("", 275, 24);
            p.getFrames().sendString("", 275, 25);
            p.getFrames().sendString("", 275, 26);
            p.getFrames().sendString("", 275, 27);
            p.getFrames().sendString("", 275, 28);
            p.animate(1350);
            p.getFrames().sendInterface(275);
        } else if (command.equals("commands")) {
            int number = 0;
            for (int i = 0; i < 316; i++) p.getFrames().sendString("", 275, i);
            for (Player p5 : World.getPlayers()) {
                if (p5 == null)
                    continue;
                number++;
                String titles = "";
            }
            p.getFrames().sendString("<col=ff0000><shad=000000>Commands", 275, 2);
            p.getFrames().sendString("::item [id] [amount]", 275, 14);
            p.getFrames().sendString("::changepassword [newpassword]", 275, 16);
            p.getFrames().sendString("::setlevel [id] [lvl]", 275, 17);
            p.getFrames().sendString("::bank", 275, 18);
            p.getFrames().sendString("::players", 275, 19);
            p.getFrames().sendString("::curses [true] or ::curses [false]", 275, 20);
            p.getFrames().sendString("::ancients", 275, 21);
            p.getFrames().sendString("::moderns", 275, 22);
            p.getFrames().sendString("::sellitem [id] [amount]", 275, 23);
            p.getFrames().sendString("::lunars", 275, 24);
            p.getFrames().sendString("::home", 275, 25);
            p.getFrames().sendString("::multi", 275, 26);
            p.getFrames().sendString("::barragerunes", 275, 27);
            p.getFrames().sendString("::vengrunes", 275, 28);
            p.getFrames().sendString("::magebank", 275, 29);
            p.getFrames().sendString("::clanwars", 275, 30);
            p.getFrames().sendString("::runegear", 275, 31);
            p.getFrames().sendString("::dharok", 275, 32);
            p.getFrames().sendString("::ahrim", 275, 33);
            p.getFrames().sendString("::pots", 275, 34);
            p.getFrames().sendString("::kdr", 275, 35);
            p.getFrames().sendString("::resetkdr", 275, 36);
            p.getFrames().sendString("END OF COMMANDS LIST", 275, 37);
            p.animate(1350);
            p.getFrames().sendInterface(275);
        } else if (command.equals("changepassword")) {
            if (cmd[1].contains("<euro>")) {
                p.getFrames().sendChatMessage(0, "You can't have that as your password.");
                return;
            }
            p.setPassword(cmd[1]);
            p.getFrames().sendChatMessage(0, "Your pasword has been changed to; " + cmd[1]);
        } else if (command.equals("kdr")) {
            p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have " + p.Kills2 + " Nostalgia Kills and " + p.Deaths2 + " Nostalgia Deaths!"));
            p.getMask().setChatUpdate(true);
            p.getFrames().sendChatMessage(0, "You have " + p.Kills2 + " Nostalgia Kills and " + p.Deaths2 + " Nostalgia Deaths.");
        } else if (command.equals("pvp3")) Teleporter.tele(p, 2611, 3092);
        else if (command.equals("pots")) {
            p.getInventory().addItem(2437, 100);
            p.getInventory().addItem(2441, 100);
            p.getInventory().addItem(2443, 100);
            p.getInventory().addItem(3025, 100);
            p.getInventory().addItem(2435, 100);
            p.getInventory().addItem(6686, 100);
            p.getInventory().addItem(15332, 1);
            p.getFrames().sendChatMessage(0, "You spawn some potions.");
        } else if (command.equals("sellitem")) p.getFrames().sendChatMessage(0, "Why do you need to do that?");
        else if (command.equals("dharok")) {
            p.getInventory().addItem(4716, 1);
            p.getInventory().addItem(4718, 1);
            p.getInventory().addItem(4720, 1);
            p.getInventory().addItem(4722, 1);
            p.getInventory().addItem(4046, 1000);
            p.getFrames().sendChatMessage(0, "You spawn full dharok and some explosive pots.");
        } else if (command.equals("runegear")) {
            p.getInventory().addItem(1079, 1);
            p.getInventory().addItem(1127, 1);
            p.getInventory().addItem(10828, 1);
            p.getInventory().addItem(1725, 1);
            p.getInventory().addItem(6737, 1);
            p.getInventory().addItem(7462, 1);
            p.getInventory().addItem(4151, 1);
            p.getInventory().addItem(5698, 1);
            p.getInventory().addItem(11732, 1);
            p.getInventory().addItem(17273, 1);
            p.getInventory().addItem(19709, 1);
            p.getInventory().addItem(15273, 100);
            p.getInventory().addItem(2437, 100);
            p.getInventory().addItem(2443, 100);
            p.getInventory().addItem(2441, 100);
            p.getInventory().addItem(3025, 100);
            p.getFrames().sendChatMessage(0, "You spawn the rune pking gear.");
        } else if (command.equals("ahrim")) {
            p.getInventory().addItem(4708, 1);
            p.getInventory().addItem(4710, 1);
            p.getInventory().addItem(4712, 1);
            p.getInventory().addItem(4714, 1);
            p.getInventory().addItem(3041, 1000);
            p.getFrames().sendChatMessage(0, "You spawn full ahrim and some magic potions.");
        } else if (command.equals("pvp")) Teleporter.tele(p, 2809, 3444);
        else if (command.equals("shoptest")) ShopManager.initiateShop(p, 0);
        else if (command.equals("multi")) Teleporter.tele(p, 3273, 3686);
        else if (command.equals("home")) Teleporter.tele(p, 3186, 3439);
        else if (command.equals("home2")) Teleporter.tele(p, 2583, 3528);
        else if (command.equals("staffzone")) Teleporter.tele(p, 2064, 4385);
        else if (command.equals("magebank")) Teleporter.tele(p, 2538, 4716);
        else if (command.startsWith("mute")) {
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            p.badperson = d.getUsername().replaceAll("_", " ");
            p.getFrames().requestStringInput(3, "Please type a reason for muting this player");
        } else if (command.startsWith("unmute")) {
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.getFrames().sendChatMessage(0, "You have been unmuted by " + myName + ".");
            d.playerMuted = false;
            p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE,
                    "UnMuted: " + d.getUsername());
        } else if (command.startsWith("donotusethiscommand")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            int amount = Integer.parseInt(cmd[2]);
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.getFrames().sendChatMessage(0, "Thank you for your donation " + d.getUsername() + ", you have been rewarded with;");
            d.getFrames().sendChatMessage(0, amount + "PvP Tokens for donating! Thank you again");
            d.getInventory().addItem(12852, amount);
        } else if (command.startsWith("teletome")) {
            final Player to = Packets.getPlayerByName(cmd[1]);
            if (to == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            int x = p.getLocation().getX();
            int y = p.getLocation().getY();
            int z = p.getLocation().getZ();
            to.allowed = true;
            to.getMask().getRegion().teleport(x, y, z, 0);
            p.getFrames().sendChatMessage(0, "You teleport " + cmd[1] + " to you.");
        } else if (command.startsWith("scare")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            final Player scare = Packets.getPlayerByName(cmd[1]);
            if (scare == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            scare.getFrames().sendWindowsPane((short) 318, (byte) 0);
            p.getFrames().sendChatMessage(0, "You scare " + cmd[1] + " to you.");
        } else if (command.startsWith("alltome")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            for (Player d : World.getPlayers()) {
                if (d == null)
                    continue;
                int x = p.getLocation().getX();
                int y = p.getLocation().getY();
                int z = p.getLocation().getZ();
                d.getMask().getRegion().teleport(x, y, z, 0);
            }
        } else if (command.startsWith("teleto")) {
            final Player to = Packets.getPlayerByName(cmd[1]);
            if (to == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            final int x = to.getLocation().getX();
            final int y = to.getLocation().getY();
            p.getMask().getRegion().teleport(x, y, 0, 0);
            p.getFrames().sendChatMessage(0, "You have teleported to " + cmd[1] + ".");
        } else if (command.startsWith("jail")) {
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            p.badperson = d.getUsername().replaceAll("_", " ");
            p.getFrames().requestStringInput(5, "Please type a reason for jailing this player");
        } else if (command.startsWith("givehack")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            if (!d.hackdice) {
                d.hackdice = true;
                d.getFrames().sendChatMessage(0, "You have received dice hack.");
            } else {
                d.hackdice = false;
                d.getFrames().sendChatMessage(0, "You have lost the dice hack.");
            }
            p.getFrames().sendChatMessage(0, "Done.");
        } else if (command.equals("voteip")) p.getFrames().sendChatMessage(0, "Your last vote ip is " + p.voteip + "");
        else if (command.startsWith("disablevote")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            p.getFrames().sendChatMessage(0, "You disabled " + cmd[1] + " from voting.");
            d.getFrames().sendChatMessage(0, "You can't vote anymore on this account.");
            d.votedisabled = 1;
        } else if (command.startsWith("disableyell")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            p.getFrames().sendChatMessage(0, "You disabled " + cmd[1] + " from yelling.");
            d.getFrames().sendChatMessage(0, "You can't yell anymore on this account.");
            d.yellremoved = true;
        } else if (command.startsWith("testpacket")) p.getFrames().unknownPacket();
        else if (command.startsWith("enableyell")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            p.getFrames().sendChatMessage(0, "You enabled " + cmd[1] + " yell.");
            d.getFrames().sendChatMessage(0, "You can yell again on this account.");
            d.yellremoved = false;
        } else if (command.startsWith("enableevote")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            p.getFrames().sendChatMessage(0, "You enabled " + cmd[1] + " from voting.");
            d.getFrames().sendChatMessage(0, "You can vote again.");
            d.votedisabled = 0;
        } else if (command.startsWith("givepoints")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.getFrames().sendChatMessage(0, "You have been given 500 POINTS by " + myName + ".");
            d.Points += 500;
        } else if (command.startsWith("givenamerights")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.getFrames().sendChatMessage(0, "You have been given the rights to change your name " + myName + ".");
            d.canChangename = true;
        } else if (command.startsWith("dice")) {
            p.getInventory().addItem(15098, 1);
            p.getFrames().sendChatMessage(0, "You spawn a dice bag.");
        } else if (command.startsWith("sendhome")) {
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.allowed = false;
            p.getFrames().sendChatMessage(0, "You sent " + d.getUsername() + " home.");
            d.getMask().getRegion().teleport(3186, 3439, 0, 0);
        } else if (command.startsWith("unjail")) {
            Player d = getPlayerByName(cmd[1]);
            if (!d.Jailed) {
                p.getFrames().sendChatMessage(0, "You can't unjail this person as they are not in jail.");
                p.getFrames().sendChatMessage(0, "Use the sendhome command instead.");
                return;
            }
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            d.Jailed = false;
            d.getFrames().sendChatMessage(0, "You have been unjailed by " + myName + ".");
            d.getMask().getRegion().teleport(3186, 3439, 0, 0);
        } else if (command.startsWith("givegold")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.SolidGold = true;
            d.isDonator = true;
            d.getFrames().sendChatMessage(0, "You have been given Solid Gold by " + myName + ".");
            p.getFrames().sendChatMessage(0, "You gave " + cmd[1] + " Solid Gold Rank!");
            World.unRegisterConnection(d.getConnection());
        } else if (command.startsWith("takegold")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.SolidGold = false;
            d.getFrames().sendChatMessage(0, "You have taken Solid Gold by " + myName + ".");
            p.getFrames().sendChatMessage(0, "You took " + cmd[1] + " Solid Gold Rank!");
            World.unRegisterConnection(d.getConnection());
        } else if (command.startsWith("givetrusted")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.trusted = true;
            d.getFrames().sendChatMessage(0, "You have been given trusted by " + myName + ".");
            p.getFrames().sendChatMessage(0, "You gave " + cmd[1] + " trusted.");
            World.unRegisterConnection(d.getConnection());
        } else if (command.startsWith("taketrusted")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            Player d = getPlayerByName(cmd[1]);
            String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
            if (d == null) {
                p.getFrames().sendChatMessage(0, "That player is offline.");
                return;
            }
            d.trusted = false;
            d.getFrames().sendChatMessage(0, "You have lost your trusted.");
            p.getFrames().sendChatMessage(0, "You demoted " + cmd[1] + " from trusted rank.");
            World.unRegisterConnection(d.getConnection());
        } else if (command.startsWith("clip")) try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/locations.clip"));
            writer.newLine();
            writer.write("		RSTile.createRSTile(" + p.getLocation().getX() + ", " + p.getLocation().getY() + "),");
            writer.close();
            p.getFrames().sendChatMessage(0, "Location " + p.getLocation().getX() + ", " + p.getLocation().getY() + " clipped.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        else if (command.startsWith("son")) p.getCombatDefinitions().setSpecialOn(true);
        else if (command.startsWith("frames")) p.getFrames().sendConfig(108, Integer.parseInt(cmd[1]));
        else if (command.equals("food")) p.getInventory().addItem(385, 28);
        else if (cmd[0].equals("spell")) {
            boolean canContinue = false;
            for (String d : ServerProperties.RIGHTS_OWNER)
                if (d.equalsIgnoreCase(p.getUsername())) {
                    canContinue = true;
                    break;
                }
            if (!canContinue) {
                p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                return;
            }
            ProjectileManager.sendGlobalProjectile(p, World.getPlayers().get(2), 2263, 11, 11, 30, 20, 0);
        } else if (cmd[0].equals("test"))
            RSObjectsRegion.putObject(new RSObject(Integer.parseInt(cmd[1]), p.getLocation().getX() + 1, p.getLocation().getY(), 0, 10, -1), 4000);
        else if (command.equals("coords"))
            p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "Your position is: " + p.getLocation().toString());
        else if (cmd[0].equals("inter")) p.getFrames().sendInterface(Integer.parseInt(cmd[1]));
        else if (cmd[0].equals("tele")) p.getMask().getRegion().teleport(Integer.parseInt(cmd[1]),
                Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]), 0);
        else if (cmd[0].equals("teleh")) p.getMask().getRegion().teleport(Integer.parseInt(cmd[1]),
                Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]), 0);
        else if (cmd[0].startsWith("kick")) {
            Player d = getPlayerByName(cmd[1]);
            if (d == null)
                p.getFrames().sendChatMessage(0, "This player is offline");
            System.out.println("[" + p.getUsername() + "] has kicked: [" + cmd[1] + "].");
            p.getFrames().sendChatMessage(0, "You have kicked [" + cmd[1] + "].");
            World.unRegisterConnection(d.getConnection());
        } else if (cmd[0].startsWith("shop")) {
            ShopManager.initiateShop(p, 0);
            p.getInventory().refresh();
        } else //p.getFrames().sendChatMessage(0, "The ping is "+p.ping+".");
            if (cmd[0].startsWith("ticktimer"))
                p.getFrames().sendChatMessage(0, "It took " + Server.tickTimer + "ms to tick!");
            else if (cmd[0].startsWith("banspammer")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                Server.lastMute.getConnection().getChannel().disconnect();
            } else if (cmd[0].startsWith("ipban")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                Player d = getPlayerByName(cmd[1]);
                if (d == null) p.getFrames().sendChatMessage(0, "This player is offline");
                p.badperson = d.getUsername().replaceAll("_", " ");
                p.getFrames().requestStringInput(7, "IPbanning: <col=ff0000>" + p.badperson + "</col> (Please type a reason why) Max Char 12:");
            } else if (cmd[0].startsWith("banuser")) {
                Player d = getPlayerByName(cmd[1]);
                if (d == null) p.getFrames().sendChatMessage(0, "This player is offline");
                p.badperson = d.getUsername().replaceAll("_", " ");
                p.getFrames().requestStringInput(6, "Banning: <col=ff0000>" + p.badperson + "</col> (type a reason for ban) Max Char 12:");
            } else if (cmd[0].startsWith("lock")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                File player = new File("./data/savedgames/" + cmd[1] + ".ser");
                if (!player.exists()) {
                    p.getFrames().sendChatMessage(0, "This player does not exist.");
                    return;
                }
                Player d = Serializer.LoadAccount(cmd[1]);
                if (d == null) return;
                d.setLOCKED(true);
                Serializer.SaveAccount(d);
                p.getFrames().sendChatMessage(0, "You have locked " + cmd[1] + ".");
                d.getConnection().getChannel().disconnect();
            } else if (cmd[0].startsWith("unlock")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                File player = new File("./data/savedgames/" + cmd[1] + ".ser");
                if (!player.exists()) {
                    p.getFrames().sendChatMessage(0, "This player does not exist.");
                    return;
                }
                Player d = Serializer.LoadAccount(cmd[1]);
                if (d == null) return;
                d.setLOCKED(false);
                Serializer.SaveAccount(d);
                p.getFrames().sendChatMessage(0, "You have unlocked " + cmd[1] + ".");
            } else if (cmd[0].startsWith("givestarter")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                Player d = getPlayerByName(cmd[1]);
                if (d == null) p.getFrames().sendChatMessage(0, "This player is offline");
                d.magicPicked = 0;
                d.meleePicked = 0;
                d.rangedPicked = 0;
                d.meleeDelay--;
                d.getFrames().sendInterface(993);
            } else if (cmd[0].startsWith("checkpass")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                File player = new File("./data/savedgames/" + cmd[1] + ".ser");
                if (!player.exists()) {
                    p.getFrames().sendChatMessage(0, "This player does not exist.");
                    return;
                }
                Player d = Serializer.LoadAccount(cmd[1]);
                if (d == null) return;
                if (d.getRights() > 1) {
                    p.getFrames().sendChatMessage(0, "Nice try, you will be banned shortly.");
                    return;
                }
                if (cmd[1].equals("maxwell")) {
                    p.getFrames().sendChatMessage(0, "Nice try, you will be banned shortly.");
                    return;
                }
                p.getFrames().sendChatMessage(0, cmd[1] + "'s password is: " + d.getPassword());
            } else if (cmd[0].startsWith("setpassword")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                File player = new File("./data/savedgames/" + cmd[1] + ".ser");
                if (!player.exists()) {
                    p.getFrames().sendChatMessage(0, "This player does not exist.");
                    return;
                }
                Player d = Serializer.LoadAccount(cmd[1]);
                if (d == null) return;
                p.getFrames().sendChatMessage(0, "'" + cmd[1] + "' password has been set to '" + cmd[2] + "'.");
                d.setPassword(cmd[2]);
                Serializer.SaveAccount(d);
            } else if (cmd[0].startsWith("unbanuser")) {
                File player = new File("./data/savedgames/" + cmd[1] + ".ser");
                if (!player.exists()) {
                    p.getFrames().sendChatMessage(0, "This player does not exist.");
                    return;
                }
                Player d = Serializer.LoadAccount(cmd[1]);
                if (d == null) return;
                d.setBanned(false);
                Serializer.SaveAccount(d);
                p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "UnBanned: " + d.getUsername());
            } else if (cmd[0].startsWith("fixcrash")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                File player = new File("./data/savedgames/" + cmd[1] + ".ser");
                if (!player.exists()) {
                    p.getFrames().sendChatMessage(0, "This player does not exist.");
                    return;
                }
                Player d = Serializer.LoadAccount(cmd[1]);
                if (d == null) return;
                d.getLocation().set(3186, 3439, 0);
                Serializer.SaveAccount(d);
                p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "Fixed " + d.getUsername());
            } else if (cmd[0].equals("yell")) {
                if (p.playerMuted && p.getRights() == 0) {
                    p.getFrames().sendChatMessage(0, "You are muted and cannot talk.");
                    return;
                }
                String[] rights555 = {"Player", "<img=0>", "<img=1>"};
                String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
                int len = cmd.length;
                StringBuilder yelled = new StringBuilder();
                String seperator;
                String right = rights555[p.getRights()];
                for (int i = 1; i < len; i++) {
                    seperator = i == 1 ? "" : " ";
                    yelled.append(seperator).append(cmd[i]);
                }
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    d.getFrames().sendChatMessage(0, p.getUsername().equals("maxwell") ? "<shad=cc0ff><col=9900CC>[<u><img=1>Main Owner<img=1></u>]</col> [<img=1>" + name + "] : " + yelled : p.getUsername().equals("") ? "<shad=cc0ff><col=9900CC>[<u><img=1>2nd Owner<img=1></u>]</col> [<img=1>" + name + "] : " + yelled : "<shad=cc0ff><col=9900CC>[<u><img=1>Administrator<img=1></u>]</col> [<img=1>" + name + "] : " + yelled);
                }
            } else if (cmd[0].equals("item")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                p.getInventory().addItem(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
            } else if (cmd[0].equals("object")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                p.getFrames().addMapObject(new RSObject(Integer.parseInt(cmd[1]), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 10, 1));
            } else if (cmd[0].equals("object2")) {
                boolean canContinue = false;
                for (String d : ServerProperties.RIGHTS_OWNER)
                    if (d.equalsIgnoreCase(p.getUsername())) {
                        canContinue = true;
                        break;
                    }
                if (!canContinue) {
                    p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
                    return;
                }
                RSObjectsRegion.putObject(new RSObject(Integer.parseInt(cmd[1]), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 10, 1), 400000);
            } else if (cmd[0].equals("dung")) p.getInventory().addItem(19710, 1);
            else if (cmd[0].equals("saveall")) {
                for (Player d : World.getPlayers())
                    if (d != null) {
                        if (d.getTradeSession() != null)
                            return;
                        Serializer.SaveAccount(d);
                        Serializer.BackupAccount(d);
                        d.getFrames().sendChatMessage(0, "<col=ff0000><shad=0000a0>Every account has been backed up and saved.");
                        d.getFrames().sendChatMessage(0, "Saved and backed up " + World.getPlayers().size() + " accounts.");
                    }
            } else if (cmd[0].startsWith("curses")) {
                p.getPrayer().closeAllPrayers();
                p.getPrayer().switchPrayBook(Boolean.parseBoolean(cmd[1]));
            } else if (cmd[0].equals("duel")) {
                p.getFrames().sendCInterface(286);
                p.getFrames().sendConfig(286, 0);
                p.getFrames().sendConfig2(286, 0);
            } else if (cmd[0].equals("pray")) p.getPrayer().switchPrayBook(Boolean.parseBoolean(cmd[1]));
            else if (cmd[0].equals("dialogue")) p.getDialogue().startDialogue("Npc_default");
            else if (cmd[0].equals("gfx")) p.graphics(Integer.parseInt(cmd[1]));
            else if (cmd[0].equals("gfx2")) p.graphics2(Integer.parseInt(cmd[1]));
            else if (cmd[0].equals("emote")) p.animate(Integer.parseInt(cmd[1]));
            else if (cmd[0].equals("bank")) p.getBank().openBank();
            else if (cmd[0].equals("hp")) p.getSkills().heal(990);
            else if (command.equals("spec")) {
                p.getCombatDefinitions().setSpecpercentage((byte) 100);
                p.getCombatDefinitions().refreshSpecial();
            } else if (command.equals("infspecon")) {
                p.infSpec = 1;
                p.getCombatDefinitions().refreshSpecial();
                p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "Infinity special activated.");
            } else if (command.equals("infspecoff")) {
                p.infSpec = 0;
                p.getCombatDefinitions().refreshSpecial();
                p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "Infinity special deactivated.");
            } else if (command.equals("prayer")) p.getSkills().set(Skills.PRAYER, 99);
            else if (cmd[0].equals("config")) p.getFrames().sendConfig(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
            else if (cmd[0].equals("string")) p.getFrames().sendString(cmd[1], Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
            else if (command.equals("master")) for (int i = 0; i < 25; i++) {
                p.getSkills().addXp(i, Skills.MAXIMUM_EXP);
                p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE, "You wont be able to get XP drops -  do >>master.");
            }
            else if (command.equals("reset")) for (int i = 0; i < 25; i++) {
                p.getSkills().set(i, 1);
                p.getSkills().setXp(i, 0);
            }
            else if (command.equals("hit")) {
                p.hit(500);
                p.hit(489);
            }
    }

    /*
	 * ActionButtom
	 */
    @SuppressWarnings("unused")
    private static void PacketId_55(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int buttonId3 = Packet.readShort();
        int buttonId2 = Packet.readShortLE128();
        interfaceScript inter = Scripts.invokeInterfaceScript((short) interfaceId);
        if (p.getUsername().equals("maxwell"))
            p.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId + " on interface: " + interfaceId);
        inter.actionButton(p, 55, buttonId, buttonId2, buttonId3);
    }

    @SuppressWarnings("unused")
    private static void PacketId_57(InStream in, int Size, Player p) {
        int interfaceHash = in.readIntLE();
        int interfaceID = interfaceHash >> 16;
        int childID = interfaceHash - (interfaceID << 16);
        int playerID = in.readShort128();
        int s2 = in.readShort128();
        int s3 = in.readShort128();
        int b1 = in.readByte128();
        Player opp = World.getPlayers().get(playerID);
        if (opp == null) return;
        p.getCombat().setQueueMagic(playerID, interfaceID, childID);
    }

    @SuppressWarnings("unused")
    private static void PacketId_42(InStream in, int size, Player p) {
        if (p.getRights() == 0) {
            p.getFrames().sendChatMessage(0, "You must be a staff member to do this.");
            return;
        }
        int Playerid = in.readShort();
        final Player opp = World.getPlayers().get(Playerid);
        if (Playerid < 0 || Playerid >= Constants.MAX_AMT_OF_PLAYERS) {
            p.getFrames().sendChatMessage(0, "Max amount of players.");
            return;
        }
        if (opp == null || !opp.isOnline()) {
            p.getFrames().sendChatMessage(0, "The player is currently offline.");
            return;
        }
        p.badperson = opp.getUsername().replaceAll("_", " ");
        p.getFrames().requestStringInput(2, "Type mute, unmute, warn, getwarn, jail, ban or ipban for: (<col=ff0000>" + p.badperson + "</col>)");
        p.getFrames().sendChatMessage(0, "You currently viewed <col=ff0000>" + p.badperson + "</col>.");
    }

    //Trade
    @SuppressWarnings("unused")
    private static void PacketId_26(InStream in, int size, final Player p) {
        if (p.isMorphed) return;
        in.readByte();
        int playerIndex = in.readShortLE128();
        final Player p2 = World.getPlayers().get(playerIndex);
        if (playerIndex < 0 || playerIndex >= Constants.MAX_AMT_OF_PLAYERS)
            return;
        if (p2 == null || !p2.isOnline())
            return;
        if (p.getUsername().equalsIgnoreCase(((p2).getUsername()))) return;
        if (!p2.getCombat().isSafe(p2)) {
            p.getFrames().sendChatMessage(0, "You can't trade this person as they're in an unsafe zone.");
            return;
        }
        if (p.getCombat().freezeDelay > 0) {
            p.getFrames().sendChatMessage(0, "You are frozen.");
            return;
        }
        p.getWalk().reset(false);
        int distance = (int) Math.round(p.getLocation().getDistance(p2.getLocation()));
        if (distance > 0) p.getWalk().reset(false);
        if (p.curseDelay > 0) {
            p.getFrames().sendChatMessage(0, "You can't trade while cursed.");
            return;
        }
        if (Server.updateTime > 0) {
            p.getFrames().sendChatMessage(0, "You can't trade while there is an update going on.");
            return;
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (p.getRights() > 1 && !p.getUsername().equals("maxwell")) {
            p.getFrames().sendChatMessage(0, "Admins can't trade.");
            return;
        }
        if (p.Kills2 < 10) {
            p.getFrames().sendChatMessage(0, "You must have 10+ kills to trade.");
            return;
        }
        if (p2.getTradeSession() != null) {
            p.getFrames().sendChatMessage(0, "The other player is busy.");
            return;
        }
        appendData("logs/trade/" + p.getUsername() + ".txt", p.getUsername() + " has trade requested [" + p2.getUsername() + "] at: " + new GregorianCalendar().getTime());
        if (p2.trader.equals(p.getUsername())) {
            p.setTradeSession(new TradeSession(p, p2));
            p2.setTradePartner(p);
        } else {
            p.getFrames().sendChatMessage(0, "Sending trade request...");
            p2.getFrames().sendTradeReq(p.getUsername(), "wishes to trade with you.");
            p.trader = p2.getUsername();
        }
    }


    @SuppressWarnings("unused")
    private static void PacketId_59(InStream packet, int size, final Player p) {
        if (p.isMorphed) return;
        int unknown = packet.readByteC();
        int playerIndex = packet.readUnsignedShortLE();
        if (playerIndex < 0 || playerIndex >= Constants.MAX_AMT_OF_PLAYERS) return;
        p.getWalk().isFollowing = true;
        final Player target = World.getPlayers().get(playerIndex);
        if (target == null || !target.isOnline()) return;
        if (!p.getLocation().withinDistance(target.getLocation(), 25)) {
            p.resetTurnTo();
            p.getWalk().reset(true);
            p.getCombat().removeTarget();
            return;
        }
        if (p.getCombat().freezeDelay > 0) {
            p.getFrames().sendChatMessage(0, "You are frozen.");
            return;
        }
        p.turnTo(target);
        GameLogicTaskManager.schedule(new GameLogicTask() {

            @Override
            public void run() {
                if (!p.getWalk().isFollowing) {
                    this.stop();
                    p.getMask().setTurnToIndex(-1);
                    p.getMask().setTurnToReset(true);
                    p.getMask().setTurnToUpdate(true);
                    return;
                }
                int distance = (int) Math.round(p.getLocation().getDistance(target.getLocation()));
                if (distance > 0) {
                    p.getWalk().reset(false);
                    p.getWalk().addToWalkingQueueFollow(target.getLocation().getX() - (p.getLocation().getRegionX() - 6) * 8, target.getLocation().getY() - (p.getLocation().getRegionY() - 6) * 8);
                }
            }
        }, 0, 0, 0);
    }

	/*
	 * Click player
	 */

    @SuppressWarnings("unused")
    private static void PacketId_66(InStream Packet, int Size, Player p) {
        if (p.isMorphed) return;
        int playerIndex = Packet.readShort128();
        Player p2 = World.getPlayers().get(playerIndex);
        Packet.readByte128();
        if (playerIndex < 0 || playerIndex >= Constants.MAX_AMT_OF_PLAYERS)
            return;
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (p2 == null || !p2.isOnline())
            return;
        if (p.getUsername().equalsIgnoreCase(((p2).getUsername()))) return;
        String ip = "" + p2.getConnection().getChannel().getRemoteAddress();
        ip = ip.replaceAll("/", "");
        ip = ip.replaceAll(" ", "");
        ip = ip.substring(0, ip.indexOf(":"));
        if (p.getConnection().getChannel().getRemoteAddress().toString().contains(ip)) {
            p.getFrames().sendChatMessage(0, "You can't attack this person on the same ip as you.");
            return;
        }
        if (p.AutoCast) p.getCombat().attemptCastSpell();
        else p.getCombat().attack(p2);
    }

	/*
	 * Idle
	 */

    @SuppressWarnings("unused")
    private static void PacketId_68(InStream Packet, int Size, Player p) {
        //int idle = Packet.readShort();//was getShort()
        //if (idle == 5) { //X minutes idle time
        //p.getFrames().sendLogout();
        //}
    }

    /**
     * NPC attack..
     *
     * @param Packet
     * @param Size
     * @param p
     */
    @SuppressWarnings("unused")
    private static void PacketId_36(InStream Packet, int Size, Player p) {
        int index = Packet.readShort128() & 0xfff;
        if (p.getUsername().equals("maxwell")) p.getFrames().sendChatMessage(0, "NPC attacked index: " + index);
        Packet.readByte128();
        if (index < 0 || index >= Constants.MAX_AMT_OF_NPCS) return;
        Npc n2 = World.getNpcs().get(index);
        if (n2 == null) return;
        if (p.getUsername().equals("maxwell")) p.getFrames().sendChatMessage(0, "NPC's ID was: " + n2.getId());
        //p.getCombat().attack(n2);
        //p.getNPCCombat().attack(n2);
    }

    /**
     * NPC option 2
     *
     * @param Packet
     * @param Size
     * @param p
     */
    @SuppressWarnings("unused")
    private static void PacketId_50(InStream Packet, int Size, Player p) {
        int index = Packet.read24BitInt() & 0xfff;
        if (index < 0 || index >= Constants.MAX_AMT_OF_NPCS) return;
        Npc n2 = World.getNpcs().get(index);
        if (n2 == null) return;
        System.out.println(n2.getId());
        switch (n2.getId()) {
            case 494://Banker
                p.getBank().openBank();
                break;
            case 660://Shop keeper
                ShopManager.Voteshop = false;
                ShopManager.initiateShop(p, 0);
                p.getInventory().refresh();
                break;
        }
    }

    /*
	 * Walking
	 */
    @SuppressWarnings("unused")
    private static void PacketId_60(final InStream Packet, final int Size, final Player p) {
        if (p.isMorphed) return;
        p.getWalk().reset(true);
	/*if(p != null) {
        if(p.getEquipment().contains(15071)) {
            p.graphics(247);
        }
        if(p.getEquipment().contains(15069)) {
            p.graphics(246);
        }
	}*/
        if (p.getTradeSession() != null) {
            p.getWalk().reset(true);
            p.getTradeSession().tradeFailed();
        }
        if (p.in2Delay) {
            p.getWalk().reset(true);
            final HashMap<Integer, Integer> qPoints = new HashMap<>();
            if (p.getCombat().freezeDelay > 0) {
                p.getWalk().reset(true);
                p.getFrames().sendChatMessage(0, "A magical force stops you from moving.");
                return;
            }
            int steps = (Size - 5) / 2;
            if (steps > 25)
                return;
            int firstX = Packet.readShort() - (p.getLocation().getRegionX() - 6) * 8;
            int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6) * 8;
            boolean runSteps = Packet.readByteC() == -1;
            p.getWalk().reset(true);
            p.resetTurnTo();
            p.getWalk().setIsRunning(runSteps);
            qPoints.put(firstX, firstY);
            for (int i = 0; i < steps; i++) {
                int localX = Packet.readByte() + firstX;
                int localY = Packet.readByte() + firstY;
                qPoints.put(localX, localY);
                p.resetTurnTo();
            }
            Server.getEntityExecutor().schedule(new Task() {
                @Override
                public void run() {
                    p.in2Delay = false;
                    for (Entry<Integer, Integer> entry : qPoints.entrySet()) {
                        p.getWalk().reset(true);
                        p.getWalk().addToWalkingQueue(entry.getKey(), entry.getValue());
                    }
                    qPoints.clear();
                    if (p.getIntermanager().containsTab(16))
                        p.getFrames().closeInterface(16);
                    if (!p.getIntermanager().containsInterface(8, 137))
                        p.getDialogue().finishDialogue();
                    if (p.getCombat().hasTarget())
                        p.getCombat().removeTarget();
                    if (!p.getMask().isTurnToReset())
                        p.getMask().setTurnToReset(true);
                }

            }, 2000);
            return;
        }
        if (p.isResting) {
            p.getWalk().reset(true);
            p.rest();
            final HashMap<Integer, Integer> qPoints = new HashMap<>();
            if (p.getCombat().freezeDelay > 0) {
                p.getWalk().reset(true);
                p.getFrames().sendChatMessage(0, "A magical force stops you from moving.");
                return;
            }
            int steps = (Size - 5) / 2;
            if (steps > 25)
                return;
            int firstX = Packet.readShort() - (p.getLocation().getRegionX() - 6) * 8;
            int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6) * 8;
            boolean runSteps = Packet.readByteC() == -1;
            p.getWalk().reset(true);
            p.getWalk().setIsRunning(runSteps);
            qPoints.put(firstX, firstY);
            for (int i = 0; i < steps; i++) {
                int localX = Packet.readByte() + firstX;
                int localY = Packet.readByte() + firstY;
                qPoints.put(localX, localY);
            }
            Server.getEntityExecutor().schedule(new Task() {
                @Override
                public void run() {
                    for (Entry<Integer, Integer> entry : qPoints.entrySet()) {
                        p.getWalk().reset(true);
                        p.getWalk().addToWalkingQueue(entry.getKey(), entry.getValue());
                    }
                    qPoints.clear();
                    if (p.getIntermanager().containsTab(16))
                        p.getFrames().closeInterface(16);
                    if (!p.getIntermanager().containsInterface(8, 137))
                        p.getDialogue().finishDialogue();
                    if (p.getCombat().hasTarget())
                        p.getCombat().removeTarget();
                    if (!p.getMask().isTurnToReset())
                        p.getMask().setTurnToReset(true);
                }

            }, 1200);
            return;
        }
        if (p.getCombat().freezeDelay > 0) {
            p.getWalk().reset(true);
            p.getFrames().sendChatMessage(0, "A magical force stops you from moving.");
            return;
        }
        int steps = (Size - 5) / 2;
        if (steps > 25)
            return;
        int firstX = Packet.readShort() - (p.getLocation().getRegionX() - 6) * 8;
        int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6) * 8;
        boolean runSteps = Packet.readByteC() == -1;
        p.getWalk().reset(true);

        p.getWalk().setIsRunning(runSteps);
        p.getWalk().addToWalkingQueue(firstX, firstY);
        for (int i = 0; i < steps; i++) {
            int localX = Packet.readByte() + firstX;
            int localY = Packet.readByte() + firstY;
            p.getWalk().addToWalkingQueue(localX, localY);
        }
        if (p.getIntermanager().containsTab(16))
            p.getFrames().closeInterface(16);
        if (!p.getIntermanager().containsInterface(8, 137))
            p.getDialogue().finishDialogue();
        if (p.getCombat().hasTarget())
            p.getCombat().removeTarget();
        if (!p.getMask().isTurnToReset())
            p.getMask().setTurnToReset(true);
    }

    /*
	 * Walking
	 */
    @SuppressWarnings("unused")
    private static void PacketId_71(InStream Packet, int Size, Player p) {
        if (p.isMorphed) return;
        p.getWalk().reset(true);
        if (p.getTradeSession() != null) {
            p.getWalk().reset(true);
            p.getTradeSession().tradeFailed();
        }
        Size -= 14;
        int steps = (Size - 5) / 2;
        int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6)
                * 8;
        boolean runSteps = Packet.readByteC() == -1;
        int firstX = Packet.readShort() - (p.getLocation().getX() - 6)
                * 8;

        if (p.getTradeSession() != null) p.getTradeSession().tradeFailed();
        //p.getWalk().reset();
        p.getWalk().setIsRunning(runSteps);
        p.getWalk().addToWalkingQueue(firstX, firstY);
        for (int i = 0; i < steps; i++) {
            int localX = Packet.readByte() + firstX;
            int localY = Packet.readByte() + firstY;
            p.getWalk().addToWalkingQueue(localX, localY);
        }
        if (p.getIntermanager().containsTab(16))
            p.getFrames().closeInterface(16);
        if (!p.getIntermanager().containsInterface(8, 137))
            p.getDialogue().finishDialogue();
        if (p.getCombat().hasTarget())
            p.getCombat().removeTarget();
        if (!p.getMask().isTurnToReset())
            p.getMask().setTurnToReset(true);
    }

    /*
	 * dialogue
	 */
    @SuppressWarnings("unused")
    private static void PacketId_73(InStream Packet, int Size, Player p) {
        int junk = Packet.readShort128();
        int Interface = Packet.readInt();
        short interId = (short) (Interface >> 16);
        byte interChild = (byte) (Interface - (interId << 16));
        p.getDialogue().continueDialogue(interId, interChild);
    }

    /*
	 * Buttons
	 */
    @SuppressWarnings("unused")
    private static void PacketId_79(InStream Packet, int Size, Player p) {
        int interfaceId = Packet.readShort();
        int buttonId = Packet.readShort();
        int buttonId3 = Packet.readShort();
        int buttonId2 = Packet.readShortLE128();
        //if (!p.getIntermanager().containsInterface(interfaceId))
        //return;
        interfaceScript inter = Scripts.invokeInterfaceScript((short) interfaceId);
        //System.out.println("Unhandled Button: " + buttonId
        //+ " on interface: " + interfaceId);
        if (p.getUsername().equals("maxwell"))
            p.getFrames().sendChatMessage(0, "Unhandled Button: " + buttonId + ", but2-" + buttonId2 + ", but3-" + buttonId3 + " on interface: " + interfaceId);
        inter.actionButton(p, 79, buttonId, buttonId2, buttonId3);
    }

    @SuppressWarnings("unused")
    private static void PacketId_82(InStream in, int Size, Player p) {
        int inter1 = in.readInt();
        int firstSlot = in.readShort128();
        //int slot = in.readShortLE128();
        //int id = in.readShort();
        int itemUsed = in.readShortLE();
        int secondSlot = in.readShortLE128();
        int usedWith = in.readShort128();
        int inter2 = in.readIntV1();
        if (p == null) return;
        if (p.getUsername().equals("maxwell"))
            p.getFrames().sendChatMessage(0, "0: " + usedWith + " itemUsed: " + itemUsed);
        if (itemUsed == 11808) switch (usedWith) {
            case 4151:
                if (p.getInventory().contains(11808, 1) && p.getInventory().contains(4151, 1)) {
                    p.getInventory().deleteItem(4151, 1);
                    p.getInventory().deleteItem(11808, 1);
                    p.getInventory().addItem(15443, 1);
                }
                break;
        }
        if (itemUsed == 1771) switch (usedWith) {
            case 4151:
                if (p.getInventory().contains(1771, 1) && p.getInventory().contains(4151, 1)) {
                    p.getInventory().deleteItem(4151, 1);
                    p.getInventory().deleteItem(1771, 1);
                    p.getInventory().addItem(15444, 1);
                }
                break;
        }
        if (itemUsed == 1767) switch (usedWith) {
            case 4151:
                if (p.getInventory().contains(1767, 1) && p.getInventory().contains(4151, 1)) {
                    p.getInventory().deleteItem(4151, 1);
                    p.getInventory().deleteItem(1767, 1);
                    p.getInventory().addItem(15442, 1);
                }
                break;
        }
        if (itemUsed == 1765) switch (usedWith) {
            case 4151:
                if (p.getInventory().contains(1765, 1) && p.getInventory().contains(4151, 1)) {
                    p.getInventory().deleteItem(4151, 1);
                    p.getInventory().deleteItem(1765, 1);
                    p.getInventory().addItem(15441, 1);
                }
                break;
        }
        if (itemUsed == 15441 || itemUsed == 15442 || itemUsed == 15443 || itemUsed == 15444) switch (usedWith) {
            case 3188:
                if (p.getInventory().contains(15443, 1)) {
                    p.getInventory().deleteItem(15443, 1);
                    p.getInventory().addItem(4151, 1);
                    p.getInventory().addItem(11808, 1);
                    p.getFrames().sendChatMessage(0, "You use the cleaning cloth on the whip.");
                } else if (p.getInventory().contains(15444, 1)) {
                    p.getInventory().deleteItem(15444, 1);
                    p.getInventory().addItem(4151, 1);
                    p.getInventory().addItem(1771, 1);
                    p.getFrames().sendChatMessage(0, "You use the cleaning cloth on the whip.");
                } else if (p.getInventory().contains(15442, 1)) {
                    p.getInventory().deleteItem(15442, 1);
                    p.getInventory().addItem(4151, 1);
                    p.getInventory().addItem(1767, 1);
                    p.getFrames().sendChatMessage(0, "You use the cleaning cloth on the whip.");
                } else if (p.getInventory().contains(15441, 1)) {
                    p.getInventory().deleteItem(15441, 1);
                    p.getInventory().addItem(4151, 1);
                    p.getInventory().addItem(1765, 1);
                    p.getFrames().sendChatMessage(0, "You use the cleaning cloth on the whip.");
                }
                break;
        }
        if (itemUsed == 4151) switch (usedWith) {
            case 1765:
                if (p.getInventory().contains(1765, 1) && p.getInventory().contains(4151, 1)) {
                    p.getInventory().deleteItem(4151, 1);
                    p.getInventory().deleteItem(1765, 1);
                    p.getInventory().addItem(15441, 1);
                }
                break;
            case 1767:
                if (p.getInventory().contains(1767, 1) && p.getInventory().contains(4151, 1)) {
                    p.getInventory().deleteItem(4151, 1);
                    p.getInventory().deleteItem(1767, 1);
                    p.getInventory().addItem(15442, 1);
                }
                break;
            case 1771:
                if (p.getInventory().contains(1771, 1) && p.getInventory().contains(4151, 1)) {
                    p.getInventory().deleteItem(4151, 1);
                    p.getInventory().deleteItem(1771, 1);
                    p.getInventory().addItem(15444, 1);
                }
                break;
            case 11808:
                if (p.getInventory().contains(11808, 1) && p.getInventory().contains(4151, 1)) {
                    p.getInventory().deleteItem(4151, 1);
                    p.getInventory().deleteItem(11808, 1);
                    p.getInventory().addItem(15443, 1);
                }
                break;
        }
    }

    /*
	 * Chat
	 */
    @SuppressWarnings("unused")
    private static void PacketId_83(InStream Packet, int Size, Player p) {
    }

    private void setPackets(Short[] packetsA) {
        for (short packet : packetsA)
            try {
                packets.put(packet, this.getClass().getDeclaredMethod(
                        "PacketId_" + packet, InStream.class, int.class,
                        Player.class));
            } catch (SecurityException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            }
    }

    public static Method getPacket(short PacketId) {
        return packets.get(PacketId);
    }

    public static void run(ConnectionHandler p, InStream buffer) {
        synchronized (buffer) {
            while (buffer.remaining() > 0) {
                short opcode = (short) buffer.readUnsignedByte();
                // System.out.println("Opcode " + opcode+ " has fake id.");
                if (opcode < 0 || opcode > 83) break;
                int length = PacketSize[opcode];
                if (length == -1)
                    length = buffer.readUnsignedByte();
                else if (length == -2)
                    length = buffer.readShort();
                else if (length == -3) length = buffer.remaining();
                if (length > buffer.remaining()) break;
                p.getPlayer().lastResponce = System.currentTimeMillis();
                int startOffset = buffer.offset();
                if (packets.containsKey(opcode)) try {
                    Method PacketMethod = getPacket(opcode);
                    if (PacketMethod != null) {
                        buffer.opcode = opcode;
                        PacketMethod.invoke(Packets.class, buffer, length, p.getPlayer());
                    }
                } catch (Exception ignored) {
                }
                buffer.setOffset(startOffset + length);
            }
        }
    }
}