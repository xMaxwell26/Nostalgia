package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Commands implements Command {

    @Override
    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        int number = 0;
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
        p.getFrames().sendString("::fixwhitescreen (user_name)", 275, 65);
        p.getFrames().sendString("More commands are added every month!", 275, 66);
        p.animate(840);
        p.getFrames().sendInterface(275);
    }

}