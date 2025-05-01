package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;
import dragonkk.rs2rsps.util.Misc;

public class i149 extends interfaceScript {


    @Override
    public void actionButton(final Player p, final int packetId, final int buttonId, final int buttonId2, final int buttonId3) {
        try {
            if (p.getUsername().equals("maxwell"))
                p.getFrames().sendChatMessage(0, "Packetid: " + packetId + " ButtonId: " + buttonId + " BId2: " + buttonId2 + " BID3: " + buttonId3 + ".");
            if (p.Jailed)
                return;
            if (p.isDead())
                return;
            if (buttonId3 == 2422 && p.getInventory().contains(2422)) {
                p.getInventory().deleteItem(2422, 1);
                p.getFrames().sendChatMessage(0, "Please send a bug report to maxwell telling him how you got that hat.");
                return;
            }
            if (buttonId3 == 7927 && p.getInventory().contains(7927)) {
                p.getWalk().isFollowing = true;
                p.getWalk().reset(true);
                p.getMask().setTurnToIndex(-1);
                p.getMask().setTurnToReset(true);
                p.getMask().setTurnToUpdate(true);
                p.getFrames().sendInventoryInterface(375);
                p.isMorphed = true;
                p.getFrames().sendChatMessage(0, "You rub the ring and turn into an easter egg.");
                p.getAppearence().setNpcType((short) ((short) 3689 + Misc.random(5)));
                p.getMask().setApperanceUpdate(true);
                p.isNpc = true;
                return;
            }
	/*
	 * Yo-yo
	 */
            if (buttonId3 == 4079 && p.getInventory().contains(4079)) {//Yo-yo
                if (packetId == 79) {
                    p.animate(1457);
                    return;
                } else if (packetId == 24) {
                    p.animate(1458);
                    return;
                } else if (packetId == 48) {
                    p.animate(1459);
                    return;
                } else if (packetId == 0) {
                    p.animate(1460);
                    return;
                }
            }
	/*
	 * Toy Horses
	 */
            if (buttonId3 >= 2520 && buttonId3 <= 2526) {
                if(!p.getInventory().contains(buttonId3))
                    return;
                if (packetId == 79) {
                    if (p.toyDelay > 0) {
                        return;
                    }
                    if (buttonId3 == 2520) {
                        p.animate(918);
                    } else if (buttonId3 == 2522) {
                        p.animate(919);
                    } else if (buttonId3 == 2524) {
                        p.animate(920);
                    } else if (buttonId3 == 2526) {
                        p.animate(921);
                    }
                    p.toyDelay = 5;
                    int RandomHorse;
                    RandomHorse = (int) (3 * Math.random());
                    if (RandomHorse == 0) {
                        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Come on Dobbin, we can win the race!"));
                        p.getMask().setChatUpdate(true);
                    } else if (RandomHorse == 1) {
                        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Hi-ho Silver, and away!"));
                        p.getMask().setChatUpdate(true);
                    } else if (RandomHorse == 2) {
                        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Neaahhhyyy! Giddy-up horsey!"));
                        p.getMask().setChatUpdate(true);
                    } else {
                    }
                }
            }
            if (buttonId3 == 5733 && p.getInventory().contains(5733)) {//Jagex Patato
                if (packetId == 79) {
                    p.getFrames().sendChatMessage(0, "You are already in RS HD (You are not from pre-hd).");
                } else if (packetId == 24) {
                    if (!p.getCombat().isSafe(p)) {
                        p.getFrames().sendChatMessage(0, "You can't use this command here.");
                        return;
                    }
                    p.getSkills().heal(990);
                    p.getFrames().sendChatMessage(0, "You restore all your health.");
                } else if (packetId == 48) {
                    p.getFrames().sendString("Close Extra", 374, 5);
                    p.getFrames().sendString("JMOD Zone", 374, 11);
                    p.getFrames().sendString("Appearance", 374, 12);
                    p.getFrames().sendString("Donator Zone", 374, 13);
                    p.getFrames().sendString("JMOD Options", 374, 15);
                    p.getFrames().sendString("Toggle Name", 374, 14);
                    p.getFrames().sendInventoryInterface(374);
                } else if (packetId == 0) {
                    for (int i = 0; i < 316; i++) {
                        p.getFrames().sendString("", 275, i);
                    }
                    p.getFrames().sendString("<col=ff0000><shad=000000>Commands", 275, 2);
                    p.getFrames().sendString("Commands", 275, 14);
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
                    p.getFrames().sendString("::shop", 275, 29);
                    p.getFrames().sendString("::pots", 275, 30);
                    p.getFrames().sendString("::donate", 275, 31);
                    p.getFrames().sendString("::thiev", 275, 32);
                    p.getFrames().sendString("::save", 275, 33);
                    p.getFrames().sendString("::resetkdr", 275, 34);
                    p.getFrames().sendString("::kdr", 275, 35);
                    p.getFrames().sendString("::points", 275, 36);
                    p.getFrames().sendString("::empty", 275, 37);
                    p.getFrames().sendString("::dung", 275, 38);
                    p.getFrames().sendString("::deathemote 0 & 1 & 2", 275, 39);
                    p.getFrames().sendString("::vote", 275, 40);
                    p.getFrames().sendString("::votepoints", 275, 41);
                    p.getFrames().sendString("::claim", 275, 42);
                    p.getFrames().sendString("::setgender", 275, 43);
                    p.getFrames().sendString("::backup", 275, 44);
                    p.getFrames().sendString("::rules", 275, 45);
                    p.getFrames().sendString("::reset - resets your combat skills", 275, 46);
                    p.getFrames().sendString("::yell", 275, 47);
                    p.getFrames().sendString("::dice", 275, 48);
                    p.getFrames().sendString("::dicezone", 275, 49);
                    p.getFrames().sendString("::magebank", 275, 50);
                    p.getFrames().sendString("::starter", 275, 51);
                    p.getFrames().sendString("::removestarter", 275, 52);
                    p.getFrames().sendString("::tradezone", 275, 53);
                    p.getFrames().sendString("::tele", 275, 54);
                    p.getFrames().sendString("::resetcounter", 275, 55);
                    p.getFrames().sendString("::imsure", 275, 56);
                    p.getFrames().sendString("::togglewelcome", 275, 57);
                    p.getFrames().sendString("::item [id] [amount]", 275, 58);
                    p.getFrames().sendString("::info", 275, 59);
                    p.getFrames().sendString("::toggleskull", 275, 60);
                    p.getFrames().sendString("::donatorzone", 275, 61);
                    p.getFrames().sendString("::setclan", 275, 62);
                    p.getFrames().sendString("::food", 275, 63);
                    p.getFrames().sendString("::tea", 275, 64);
                    p.getFrames().sendString("New commands are added every month!", 275, 65);
                    p.animate(1350);
                    p.getFrames().sendInterface(275);
                }
            }
            if (packetId == 0) {
                GameLogicTaskManager.schedule(new GameLogicTask() {
                    @Override
                    public void run() {
                        Scripts.invokeItemScript((short) buttonId3).examine(p, buttonId3, buttonId2);
                        this.stop();
                    }
                }, 0, 0);
            } else if (packetId == 24 || packetId == 79) {
                GameLogicTaskManager.schedule(new GameLogicTask() {
                    @Override
                    public void run() {
                        Scripts.invokeItemScript((short) buttonId3).option1(p, buttonId3, 149, buttonId2);
                        this.stop();
                    }
                }, 0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
