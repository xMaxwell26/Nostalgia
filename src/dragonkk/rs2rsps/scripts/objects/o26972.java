package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o26972 extends objectScript {


    @Override
    public void option1(Player p, int coordX, int coordY, int height) {
        if (p.entered && p.hasPin) {
            p.getBank().openBank();
            p.getInventory().refresh();
        } else if (!p.hasPin) {
            p.getBank().openBank();
        } else {
            p.getFrames().requestIntegerInput(7, "Please enter your bank pin:");
        }
    }

    @Override
    public void option2(Player p, int coordX, int coordY, int height) {
        if (p.entered && p.hasPin) {
            p.getBank().openBank();
            p.getInventory().refresh();
        } else if (!p.hasPin) {
            p.getBank().openBank();
        } else {
            p.getFrames().requestIntegerInput(7, "Please enter your bank pin:");
        }
    }


    public void option3(Player p, int coordX, int coordY, int height) {
        if (p.entered) {
            p.getBank().openBank();
            p.getInventory().refresh();
        } else {
            p.getFrames().requestIntegerInput(7, "Please enter your bank pin:");
        }
    }


    @Override
    public void examine(Player p) {
        p.getFrames().sendChatMessage(0, "Nostalgia's Bank in Varrock.");

    }

}
