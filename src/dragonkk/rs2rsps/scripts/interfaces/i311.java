package dragonkk.rs2rsps.scripts.interfaces;


import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

/**
 * Handles all of Smelting
 * Needs to be Rewritten through loops, so each bar make = 1 emote time(also add X make)
 *
 * @author Emily
 */

public class i311 extends interfaceScript {
    @Override
    public void actionButton(final Player player, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 15://Start of Bronze
                smelting(player, 436, 1, 438, 1, 2349, 1, 1, 500);
                break;
            case 14:
                smelting(player, 436, 5, 438, 5, 2349, 5, 1, 500 * 5);
                break;
            case 13:
                smelting(player, 436, 10, 438, 10, 2349, 10, 1, 500 * 10);
                break;
            case 27://Start of Silver
                singleSmelt(player, 442, 1, 2355, 1, 300);
                break;
            case 26:
                singleSmelt(player, 442, 5, 2355, 5, 300 * 5);
                break;
            case 25:
                singleSmelt(player, 442, 10, 2355, 10, 300 * 10);
                break;
            case 31://Start of Steel
                smelting(player, 453, 2, 440, 1, 2353, 1, 1, 500);
                break;
            case 30:
                smelting(player, 453, 10, 440, 5, 2353, 5, 1, 500 * 5);
                break;
            case 29:
                smelting(player, 453, 20, 440, 10, 2353, 10, 1, 500 * 10);
                break;
            case 35://Start of Gold
                singleSmelt(player, 444, 1, 2357, 1, 300);
                break;
            case 34:
                singleSmelt(player, 444, 5, 2357, 5, 300 * 5);
                break;
            case 33:
                singleSmelt(player, 444, 10, 2357, 10, 300 * 10);
                break;
            case 39://Start of Mithril
                smelting(player, 453, 4, 447, 1, 2349, 1, 1, 500);
                break;
            case 38:
                smelting(player, 453, 4 * 5, 447, 5, 2349, 5, 1, 500 * 5);
                break;
            case 37:
                smelting(player, 453, 4 * 10, 447, 10, 2349, 10, 1, 500 * 10);
                break;
            case 43://Start of Adamant
                smelting(player, 453, 6, 449, 1, 2361, 1, 1, 500);
                break;
            case 42:
                smelting(player, 453, 30, 449, 5, 2361, 5, 1, 500 * 5);
                break;
            case 41:
                smelting(player, 453, 60, 449, 10, 2361, 10, 1, 500 * 10);
                break;
            case 47://Start of Runite
                smelting(player, 453, 10, 451, 1, 2363, 1, 1, 500);
                break;
            case 46://Start of Runite
                smelting(player, 453, 10 * 5, 451, 5, 2363, 5, 1, 500 * 5);
                break;
            case 45:
                smelting(player, 453, 10 * 10, 451, 10, 2363, 10, 1, 500 * 10);
                break;
        }
    }

    public void smelting(final Player player, final int ore1id, final int ore1amnt, final int ore2id, final int ore2amnt, final int barid, final int baramount, final int lvl, final int xp) {
        if (player.getSkills().getLevel(13) < lvl) {
            player.getFrames().sendChatMessage(0, "You need " + lvl + " smelting level to smelt this!");
            return;
        }
        if (player.getInventory().contains(ore1id, ore1amnt) && player.getInventory().contains(ore2id, ore2amnt)) {
            player.animate(899);
            player.getFrames().CloseCInterface();
            player.getInventory().deleteItem(ore1id, ore1amnt);
            player.getInventory().deleteItem(ore2id, ore2amnt);
            player.getInventory().addItem(barid, baramount);
            player.getSkills().addXp(13, xp);
            player.getFrames().sendChatMessage(0, "You have smelted a bar.");
        } else {
            player.getFrames().sendChatMessage(0, "You do not have the required items to make this bar");
        }
    }

    public void singleSmelt(final Player player, final int ore1id, final int ore1amnt, final int barid, final int baramount, final int xp) {
        if (player.getInventory().contains(ore1id, ore1amnt)) {
            player.animate(899);
            player.getFrames().CloseCInterface();
            player.getInventory().deleteItem(ore1id, ore1amnt);
            player.getInventory().addItem(barid, baramount);
            player.getSkills().addXp(13, xp);
            player.getFrames().sendChatMessage(0, "You have smelted a bar.");
        } else {
            player.getFrames().sendChatMessage(0, "You do not have the required items to make this bar");
        }
    }
}