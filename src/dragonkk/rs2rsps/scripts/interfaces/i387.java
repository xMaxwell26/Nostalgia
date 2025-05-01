package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i387 extends interfaceScript {

    private int riskedWealth;
    private int totalWealth;

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (p.getUsername().equals("maxwell")) {
            p.getFrames().sendChatMessage(0, "Packetid: " + packetId + " ButtonId: " + buttonId + " BId2: " + buttonId2 + " BID3: " + buttonId3 + ".");
        }
        int slots[] = {0, 1, 2, 3, 4, 5, 7, 9, 10, 12, 13};
        int buttonIds[] = {8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38};
        for (int i = 0; i < buttonIds.length; i++) {
            if (buttonId == buttonIds[i]) {
                if (buttonId == 23) {
                    switch (packetId) {
                        case 79:
                            Scripts.invokeItemScript((short) buttonId3).option1(p, buttonId3, 387, slots[i]);
                            return;
                        case 0:
                            Scripts.invokeItemScript((short) buttonId3).examine(p, buttonId3, buttonId2);
                            return;
                        case 24: //Operate
                            Item item = p.getEquipment().get(slots[i]);
                            Scripts.invokeItemScript((short) buttonId3).operate(p, item);
                            return;
                    }
                    break;
                }
                Scripts.invokeItemScript((short) buttonId3).option1(p, buttonId3, 387, slots[i]);

            }
        }
        switch (buttonId) {
            case 39:
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getFrames().scriptRequest(
                        new String[]{"", "", "", "", "Wear<col=ff9040>"},
                        -1, 0, 7, 4, 93, 43909120, 149);
                p.getFrames().sendAMask();
                p.getFrames().sendAMask();
                p.getFrames().sendString(
                        "Stab: " + p.getCombatDefinitions().getBonus()[0], 667,
                        37);
                p.getFrames().sendString(
                        "Slash: " + p.getCombatDefinitions().getBonus()[1],
                        667, 38);
                p.getFrames().sendString(
                        "Crush: " + p.getCombatDefinitions().getBonus()[2],
                        667, 39);
                p.getFrames().sendString(
                        "Magic: " + p.getCombatDefinitions().getBonus()[3],
                        667, 40);
                p.getFrames().sendString(
                        "Ranged: " + p.getCombatDefinitions().getBonus()[4],
                        667, 41);
                p.getFrames().sendString(
                        "Stab: " + p.getCombatDefinitions().getBonus()[5], 667,
                        42);
                p.getFrames().sendString(
                        "Slash: " + p.getCombatDefinitions().getBonus()[6],
                        667, 43);
                p.getFrames().sendString(
                        "Crush: " + p.getCombatDefinitions().getBonus()[7],
                        667, 44);
                p.getFrames().sendString(
                        "Magic: " + p.getCombatDefinitions().getBonus()[8],
                        667, 45);
                p.getFrames().sendString(
                        "Ranged: " + p.getCombatDefinitions().getBonus()[9],
                        667, 46);
                p.getFrames().sendString(
                        "Summoning: " + p.getCombatDefinitions().getBonus()[10],
                        667, 47);
                p.getFrames().sendString(
                        "Strength: " + p.getCombatDefinitions().getBonus()[11],
                        667, 49);
                p.getFrames().sendString(
                        "Ranged Strength: "
                                + p.getCombatDefinitions().getBonus()[12], 667,
                        50);
                p.getFrames().sendString(
                        "Prayer: " + p.getCombatDefinitions().getBonus()[13],
                        667, 51);
                p.getFrames().sendString("Magic Damage: " + p.getCombatDefinitions().getBonus()[14] + "%", 667, 52);
                p.getFrames().sendString("", 667, 33);
                p.getFrames().sendInterface(667);
                p.getFrames().sendInventoryInterface(670);
                p.getCombatDefinitions().refreshBonuses();
                break;
            case 45:
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getFrames().sendAMask(0, 4, 102, 18, 0, 2);
                p.getFrames().sendAMask(0, 42, 102, 21, 0, 2);
                Object[] params = new Object[]{getRisk(), getTotal(), "You're marked with a <col=ff3333>skull<col=ff981f>.", 0, 0, -1, getItemOne(), getItemTwo(), getItemThree(), 3, 0};
                p.getFrames().sendClientScript(118, params, "iiooooiisii");
                p.getFrames().sendInterface(102);
                break;
            case 42:
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getFrames().sendItems(90, p.getInventory().getContainer(), false);
                p.getFrames().sendAMask(0, 54, 206, 18, 0, 1086);
                Object[] params1 = new Object[]{"", "", "", "", "Add-X", "Add-All", "Add-10", "Add-5", "Add", -1, 1, 7, 4, 93, 13565952};
                p.getFrames().sendClientScript(150, params1, "IviiiIsssssssss");
                p.getFrames().sendAMask(0, 27, 207, 0, 36, 1086);
                int i = 0;
                for (int i1 = 0; i1 < p.getInventory().getContainer().getSize(); i1++) {
                    Item item = p.getInventory().getContainer().get(i);
                    if (item == null) {
                    } else {
                        item.getDefinition().getId();
                        item.getAmount();
                    }
                    int value = 1;
                    p.getFrames().sendBConfig(700 + i1, value);
                    i1++;
                }
                for (int i2 = 700 + i; i2 >= 727; i2--)
                    p.getFrames().sendBConfig(i2, 0);
                p.getFrames().sendBConfig(728, 100);
                p.getFrames().sendInterface(206);
                p.getFrames().sendInventoryInterface(207);
                break;
        }
    }

    private int getItemOne() {
        return -1;
    }

    private int getItemTwo() {
        return -1;
    }

    private int getItemThree() {
        return -1;
    }

    private int getRisk() {
        return riskedWealth;
    }

    private int getTotal() {
        return totalWealth;
    }

}
