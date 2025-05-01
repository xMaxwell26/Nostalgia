package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i459 extends interfaceScript {
    @Override
    public void actionButton(final Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {

            case 25:  //Ball of Wool
                switch (packetId) {
                    case 79: //One
                        if (p.getInventory().contains(1737, 1)) {
                            p.getInventory().deleteItem(1737, 1);
                            p.getInventory().addItem(1759, 1);
                            p.animate(894);
                            p.getSkills().addXp(12, 30);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                    case 24: //Five
                        if (p.getInventory().contains(1737, 5)) {
                            p.getInventory().deleteItem(1737, 5);
                            p.getInventory().addItem(1759, 5);
                            p.animate(894);
                            p.getSkills().addXp(12, 30 * 5);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                    case 48: //All
                        if (p.getInventory().contains(1737)) {
                            int i = p.getInventory().numberOf(1737);
                            p.getInventory().deleteItem(1737, i);
                            p.getInventory().addItem(1759, i);
                            p.animate(894);
                            p.getSkills().addXp(12, 30 * i);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                }
            case 22: //Bow String
                switch (packetId) {
                    case 79: //One
                        if (p.getInventory().contains(1779, 1)) {
                            p.getInventory().deleteItem(1779, 1);
                            p.getInventory().addItem(1777, 1);
                            p.animate(894);
                            p.getSkills().addXp(12, 90);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                    case 24: // Five
                        if (p.getInventory().contains(1779, 5)) {
                            p.getInventory().deleteItem(1779, 5);
                            p.getInventory().addItem(1777, 5);
                            p.animate(894);
                            p.getSkills().addXp(12, 90 * 5);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                    case 48: //All
                        if (p.getInventory().contains(1779)) {
                            int i = p.getInventory().numberOf(1779);
                            p.getInventory().deleteItem(1779, i);
                            p.getInventory().addItem(1777, i);
                            p.animate(894);
                            p.getSkills().addXp(12, 90 * i);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                }

            case 27://Magic Amulet String
                switch (packetId) {
                    case 79: //One
                        if (p.getInventory().contains(6051, 1)) {
                            p.getInventory().deleteItem(6051, 1);
                            p.getInventory().addItem(6038, 1);
                            p.animate(894);
                            p.getSkills().addXp(12, 120);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                    case 24: //Five
                        if (p.getInventory().contains(6051, 5)) {
                            p.getInventory().deleteItem(6051, 5);
                            p.getInventory().addItem(6038, 5);
                            p.animate(894);
                            p.getSkills().addXp(12, 120 * 5);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                    case 48: //All
                        if (p.getInventory().contains(6051)) {
                            int i = p.getInventory().numberOf(6051);
                            p.getInventory().deleteItem(6051, i);
                            p.getInventory().addItem(6038, i);
                            p.animate(894);
                            p.getSkills().addXp(12, 120 * i);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                }
            case 35: //C'bow String
                switch (packetId) {
                    case 79: //One
                        if (p.getInventory().contains(9436, 1)) {
                            p.getInventory().deleteItem(9436, 1);
                            p.getInventory().addItem(9438, 1);
                            p.animate(894);
                            p.getSkills().addXp(12, 150);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                    case 24: //Five
                        if (p.getInventory().contains(9436, 5)) {
                            p.getInventory().deleteItem(9436, 5);
                            p.getInventory().addItem(9438, 5);
                            p.animate(894);
                            p.getSkills().addXp(12, 150 * 5);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                    case 48: //All
                        if (p.getInventory().contains(9436)) {
                            int i = p.getInventory().numberOf(9436);
                            p.getInventory().deleteItem(9436, i);
                            p.getInventory().addItem(9438, i);
                            p.animate(894);
                            p.getSkills().addXp(12, 150 * i);
                            p.getFrames().CloseCInterface();
                        }
                        break;
                }
        }
    }
}
