package dragonkk.rs2rsps.model.player;

import java.io.Serializable;

    /*
    * OpenCB
    * By Jet Kai
    * 25/11/2011
     */

public class OpenCB implements Serializable {

    public static void open(Player p) {
        p.getFrames().sendInterface(496);
        if (p.page == 0) {
            p.getFrames().sendString("Page 1: Select a location", 496, 3);
            p.getFrames().sendString("<col=33ff00>Home (Safe)", 496, 4);
            p.getFrames().sendString("Shilo Quest PvP (Unsafe)", 496, 5);
            p.getFrames().sendString("<col=01DFD7>[New] MageArena (Unsafe)", 496, 6);
            p.getFrames().sendString("<col=33ff00>DiceZone (Safe)", 496, 7);
            p.getFrames().sendString("<col=33ff00>ChillZone (Safe)", 496, 8);
            p.getFrames().sendString("<col=33ff00>StaffZone (Safe)", 496, 9);
            p.getFrames().sendString("<col=33ff00>Thiev (Safe)", 496, 10);
            p.getFrames().sendString("Shilo Bank PvP (Unsafe)", 496, 11);
            p.getFrames().sendString("", 496, 12);
            p.getFrames().sendString("<col=ffffff><shad=ff0000>Next page", 496, 13);

        } else if (p.page == 1) {
            p.getFrames().sendString("Page 2: Select a location", 496, 3);
            p.getFrames().sendString("<col=33ff00>DonatorZone (Multi/safe)", 496, 4);
            p.getFrames().sendString("Easts (Unsafe)", 496, 5);
            p.getFrames().sendString("Clanwars (Unsafe/multi)", 496, 6);
            p.getFrames().sendString("Edgeville (Unsafe/multi)", 496, 7);
            p.getFrames().sendString("Falador PvP (Unsafe)", 496, 8);
            p.getFrames().sendString("Suggest more", 496, 9);
            p.getFrames().sendString("", 496, 10);
            p.getFrames().sendString("", 496, 11);
            p.getFrames().sendString("", 496, 12);
            p.getFrames().sendString("<col=ffffff><shad=ff0000>Back", 496, 13);
        }
    }

    public static void close(Player p) {
        p.getFrames().sendClickableInterface(778);
    }

}
