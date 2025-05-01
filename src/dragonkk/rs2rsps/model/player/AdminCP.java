package dragonkk.rs2rsps.model.player;

import java.io.Serializable;

    /*
    * Admin CP
    * By Jet Kai
    * 25/11/2011
     */

public class AdminCP implements Serializable {

    public static void open(Player p) {
        p.getFrames().sendInterface(583);
        p.getFrames().sendString("<col=ff0000><shad=330fff>Admin CP", 583, 3);
        p.getFrames().sendString("Open Console", 583, 50);
        p.getFrames().sendString("Trusted Rank", 583, 51);
        p.getFrames().sendString("Super Extreme Rank", 583, 52);
        p.getFrames().sendString("Reset Ranks.", 583, 53);
        p.getFrames().sendString("Extreme Rank", 583, 54);
        p.getFrames().sendString("Enable Yell", 583, 55);
        p.getFrames().sendString("Disable Yell", 583, 56);
        p.getFrames().sendString("Max cash", 583, 57);
        p.getFrames().sendString("Red Skull", 583, 58);
        p.getFrames().sendString("Remove Skull", 583, 59);
        p.getFrames().sendString("Maxed 255 Everything", 583, 60);
        p.getFrames().sendString("Hide Crown", 583, 61);
        p.getFrames().sendString("Show Crown", 583, 62);
        p.getFrames().sendString("Staffzone", 583, 63);
        p.getFrames().sendString("Spawn Menu", 583, 64);
        p.getFrames().sendString("Die", 583, 65);
        p.getFrames().sendString("317 Snow Mode", 583, 66);
        p.getFrames().sendString("508+ Snow Mode", 583, 67);
        p.getFrames().sendString("Cracked Screen Mode", 583, 68);
        p.getFrames().sendString("Kickall", 583, 69);
        p.getFrames().sendString("Toggle Attackable", 583, 70);
        p.getFrames().sendString("Heal", 583, 71);
        p.getFrames().sendString("Enable Vote System", 583, 72);
        p.getFrames().sendString("Disable Vote System", 583, 73);
        p.getFrames().sendString("Crash Everyone [DON'T DO IT]", 583, 74);
        p.getFrames().sendString("Enable Server Message", 583, 75);
        p.getFrames().sendString("Disable Server Message", 583, 76);
        p.getFrames().sendString("Infinity Special", 583, 77);
        p.getFrames().sendString("Normal Special", 583, 78);
        p.getFrames().sendString("Server Remove Snow", 583, 79);
        p.getFrames().sendString("Server 317 Snow", 583, 80);
        p.getFrames().sendString("Server 508+ Snow", 583, 81);
        p.getFrames().sendString("Ultra Donator Interface", 583, 82);
        p.getFrames().sendString("Server Emote", 583, 83);

    }

    public static void close(Player p) {
        p.getFrames().closeInterface(583);
    }

}
