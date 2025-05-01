package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.Teleporter;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.scripts.interfaceScript;
import dragonkk.rs2rsps.util.Misc;


/*
* Admin CP
* By Jet Kai
* 25/11/2011
*/

public class i583 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (p.getRights() < 2) {
            p.getFrames().sendChatMessage(0, "You must be an admin to use any feature in this console.");
            p.getFrames().sendChatMessage(0, "Wait, how the hell did you open this interface?");
            return;
        }
        switch (buttonId) {
            case 50://Console
                //console config here
                Packets.getPacket((short) 53);
                p.getFrames().sendChatMessage(0, "Opened Console.");
                break;
            case 51://Trusted Rank
                if (p.trusted) {
                    p.getFrames().sendChatMessage(0, "You are already trusted.");
                    return;
                }
                p.getFrames().sendChatMessage(0, "Setting trusted rank...");
                p.trusted = true;
                p.getFrames().sendChatMessage(0, "Please re-log.");
                break;
            case 52://Super Extreme
                if (p.superextremeDonator) {
                    p.getFrames().sendChatMessage(0, "You are already super extreme donator.");
                    return;
                }
                p.getFrames().sendChatMessage(0, "Setting super extreme donator rank...");
                p.superextremeDonator = true;
                p.extremeDonator = true;
                p.isDonator = true;
                p.getFrames().sendChatMessage(0, "Please re-log.");
                break;
            case 53://Reset
                p.superextremeDonator = false;
                p.extremeDonator = false;
                p.isDonator = false;
                p.getFrames().sendChatMessage(0, "Reset, please re-log.");
                break;
            case 54://Extreme
                p.getFrames().sendChatMessage(0, "Setting extreme donator rank...");
                p.extremeDonator = true;
                p.isDonator = true;
                p.getFrames().sendChatMessage(0, "Please re-log.");
                break;
            case 56://Disable yelling
                Server.muteSystem = 1;
                p.getFrames().sendChatMessage(0, "You have disabled yelling for anyone but admins.");
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    d.getFrames().sendChatMessage(0, "Yell system disabled.");
                }
                break;
            case 55://Enable yelling
                Server.muteSystem = 0;
                p.getFrames().sendChatMessage(0, "You have enabled the yell system.");
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    d.getFrames().sendChatMessage(0, "Yell system enabled.");
                }
                break;
            case 57://Max cash
                p.getInventory().addItem(995, 2147483647);
                p.getFrames().sendChatMessage(0, "You spawned max cash.");
                break;
            case 58://Red Skull set
                if (p.SkullOn == 1 || p.Kills2 > 1336) {
                    p.getFrames().sendChatMessage(0, "You already have the red skull.");
                    return;
                }
                p.getMask().setApperanceUpdate(true);
                p.SkullOn = 1;
                p.getFrames().sendChatMessage(0, "You have added a skull above your head.");
                break;
            case 59://Red Skull remove
                if (p.Kills2 > 1336) {
                    p.getFrames().sendChatMessage(0, "You will also need to reset your kills to have no red skull.");
                }
                p.getMask().setApperanceUpdate(true);
                p.SkullOn = 0;
                p.getFrames().sendChatMessage(0, "You have removed the skull from your head.");
                break;
            case 60://Maxed.
                for (int i = 0; i < 25; i++) {
                    p.getSkills().set(i, 255);
                    p.getSkills().heal(p.getSkills().getLevel(3) * 10);
                }
                p.getFrames().sendChatMessage(0, "Your stats have been set.");
                break;
            case 61://Remove crown
                p.getMask().setApperanceUpdate(true);
                p.hiddenadmin = 1;
                p.getFrames().sendChatMessage(0, "Your crown is now hidden.");
                p.getFrames().sendChatMessage(0, "Please re-log.");
                break;
            case 62://Show crown
                p.getMask().setApperanceUpdate(true);
                p.hiddenadmin = 0;
                p.getFrames().sendChatMessage(0, "Your crown is now showing.");
                p.getFrames().sendChatMessage(0, "Please re-log.");
                break;
            case 63://Staffzone
                Teleporter.tele(p, 2064, 4385);
                p.getFrames().sendChatMessage(0, "Welcome to the Staffzone");
                break;
            case 64://Spawn Menu
                //Add Spawnmenu.java
                p.getFrames().sendChatMessage(0, "Some how is not opening.");
                break;
            case 65://Die
                if (p.getSkills().isDead()) {
                    return;
                }
                p.hit(2550);
                break;
            case 66://Snowing 317
                p.getFrames().sendClickableInterface(370);
                p.getFrames().sendChatMessage(0, "317 snow set.");
                break;
            case 67://Snowing 508+
                p.getFrames().sendClickableInterface(482);
                p.getFrames().sendChatMessage(0, "508+ snow set.");
                break;
            case 68://Cracked Screen
                p.getFrames().sendClickableInterface(385);
                p.getFrames().sendChatMessage(0, "Your screen is now cracked.");
                break;
            case 69://Kickall
                p.getFrames().sendChatMessage(0, "Everyone has been kicked apart from you.");
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    if (p.getUsername().equals("maxwell")) {
                        World.unRegisterConnection(d.getConnection());
                    } else {
                        p.getFrames().sendChatMessage(0, "You can't do this.");
                    }
                }
                break;
            case 70://Toggle attackable
                if (p.notattackable) {
                    p.notattackable = false;
                    p.getFrames().sendChatMessage(0, "Players can't attack you.");
                    p.getFrames().sendChatMessage(0, "To change this option, please go to the AdminCP.");
                } else {
                    p.notattackable = true;
                    p.getFrames().sendChatMessage(0, "Players can now attack you.");
                    p.getFrames().sendChatMessage(0, "To change this option, please go to the AdminCP.");
                }
                break;
            case 71://Heal
                p.getSkills().heal(990);
                break;
            case 72://Enable Vote System
                Server.voteDisabled = false;
                p.getFrames().sendChatMessage(0, "Voting enabled.");
                break;
            case 73://Disable Vote System
                Server.voteDisabled = true;
                p.getFrames().sendChatMessage(0, "Voting disabled.");
                break;
            case 74://Crash EVERYONE!
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    if (p.getUsername().equals("maxwell")) {
                        d.getFrames().sendInterface(-1337);
                        d.getFrames().sendChatMessage(0, "WOW THAT WAS STUPID!");
                    } else {
                        p.getFrames().sendChatMessage(0, "You can't do this.");
                    }
                }
                break;
            case 75://Enable Server Message
                Server.serverMessage = 0;
                p.getFrames().sendChatMessage(0, "Server message enabled.");
                break;
            case 76://Disable Server Message
                Server.serverMessage = 1;
                p.getFrames().sendChatMessage(0, "Server message disabled.");
                break;
            case 77://Infinity Special
                p.infSpec = 1;
                p.getFrames().sendChatMessage(0, "Infinity special activated.");
                break;
            case 78://Normal Special
                p.infSpec = 0;
                p.getFrames().sendChatMessage(0, "Normal Special Activated.");
                break;
            case 79://Server Remove Snow
                p.getFrames().sendChatMessage(0, "Just open another interface...");
                break;
            case 80://Server 317 snow
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    d.getFrames().sendClickableInterface(370);
                    d.getFrames().sendChatMessage(0, "It's snowing!");
                }
                break;
            case 81://Server 614 snow
                for (Player d : World.getPlayers()) {
                    if (d == null)
                        continue;
                    d.getFrames().sendClickableInterface(482);
                    d.getFrames().sendChatMessage(0, "It's snowing!");
                }
                break;
            case 82://Ultra donator CP
                p.getFrames().sendChatMessage(0, "Opening...");
                break;
            case 83://Server Emote
                for (Player player : World.getPlayers()) {
                    if (player == null)
                        continue;
                    synchronized (player) {
                        if (!player.isOnline())
                            continue;
                        player.graphics(2009);
                        player.animate(11679);
                        int message = Misc.random(2);
                        if (message == 0)
                            player.getMask().setLastChatMessage(new ChatMessage(0, 10, "Nostalgia 614!"));
                        else if (message == 1)
                            player.getMask().setLastChatMessage(new ChatMessage(0, 20, "Vote for Nostalgia 614!"));
                        else if (message == 2)
                            player.getMask().setLastChatMessage(new ChatMessage(0, 20, "Own on Nostalgia 614!"));
                        player.getMask().setChatUpdate(true);
                    }
                }
                break;
        }
    }
}
