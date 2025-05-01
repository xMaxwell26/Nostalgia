package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i763 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (packetId) {
            case 79: // Deposit-1
                p.getBank().addItemFromInventory(buttonId2, buttonId3, 1);
                break;
            case 24: // Deposit-5
                p.getBank().addItemFromInventory(buttonId2, buttonId3, 5);
                break;
            case 48: // Deposit-10
                p.getBank().addItemFromInventory(buttonId2, buttonId3, 10);
                break;
            case 13:
                p.getFrames().requestIntegerInput(4,
                        "Please enter the amount you would like to deposit:");
                p.slot = buttonId2;
                p.itemID = buttonId3;
                break;
            case 55: // Deposit-All
                p.getBank().addItemFromInventory(buttonId2, buttonId3, 2147483647);
                break;
            default:
                System.out.println(packetId);
                break;
        }
    }
}