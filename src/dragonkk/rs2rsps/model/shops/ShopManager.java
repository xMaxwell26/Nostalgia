package dragonkk.rs2rsps.model.shops;

import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class ShopManager {

    public static Shops shops;
    public static boolean Voteshop = false;

    public ShopManager() {
        shops = new Shops();
    }

    public static void initiateShop(Player player, int i) {
        player.getFrames().sendItems(3, shops.getShop(player.getShophandler().getShopId()).items, false);
        player.getFrames().sendConfig(118, 3);
        player.getFrames().sendConfig(1496, 553);
        player.getFrames().sendConfig(532, 13654);
        player.getFrames().sendBConfig(199, -1);
        player.getFrames().sendInterface(620);
        player.getFrames().sendInventoryInterface(621);
        for (int index = 0; index < 40; index++) {
            if (index == 0) {
                player.getFrames().sendBConfig(946 + index, 1000);
                continue;
            }
            player.getFrames().sendBConfig(946 + index, -1);
        }
        Object[] params = new Object[]{"Sell 50", "Sell 10", "Sell 5",
                "Sell 1", "Value", -1, 1, 7, 4, 93, 40697856};
        player.getFrames().sendClientScript(149, params, "IviiiIsssss");
        player.getFrames().sendAMask(0, 27, 621, 0, 36, 1086);
        player.getFrames().sendAMask(0, 12, 620, 26, 0, 1150);
        player.getFrames().sendAMask(0, 240, 620, 25, 0, 1150);
    }

    public void handleSellButtons(Player p, int packetid, int slot, int itemid) {
        int itemworth = shops.getShop(p.getShophandler().getShopId()).items
                .get(slot)[2];
        switch (packetid) {
            case 24:
                sellItem(p, slot, 1, itemid);
                break;

            case 48:
                sellItem(p, slot, 1, itemid);
                break;

            case 40:
                sellItem(p, slot, 1, itemid);
                break;

            case 13:
                sellItem(p, slot, 1, itemid);
                break;
        }
    }

    public void sellItem(Player p, int slot, int amount, int itemid) {
        try {
            if (Voteshop) {
                Shop shop = shops.getShop(p.getShophandler().getShopId());
                if (!shop.sellsItem(itemid)) {
                    p.getFrames().sendChatMessage(0, "You can only sell shop items to this shop.");
                    return;
                }
                int shopslot = shop.getSlotId(itemid);
                int value = shop.items.get(shopslot)[2];
                if (p.getInventory().numberOf(itemid) < amount) {
                    shop.sellItem(shopslot, p.getInventory().numberOf(itemid));
                    p.votePoints += value * p.getInventory().numberOf(itemid);
                } else {
                    shop.sellItem(shopslot, amount);
                    p.votePoints += value * amount;
                }
                p.getInventory().deleteItem(itemid, amount);
                p.getInventory().refresh();
                p.getFrames().sendChatMessage(0, "You sold the <col=3300ff>[" + ItemDefinitions.forID(itemid).name + "]</col> for <col=3300ff>[" + value + "]</col> vote points.");
                p.getFrames().sendItems(3, shops.getShop(p.getShophandler().getShopId()).items, false);
            }
            if (!Voteshop) {
                Shop shop = shops.getShop(p.getShophandler().getShopId());
                if (!shop.sellsItem(itemid)) {
                    p.getFrames().sendChatMessage(0, "You can only sell shop items to this shop.");
                    return;
                }
                int shopslot = shop.getSlotId(itemid);
                int value = shop.items.get(shopslot)[2];
                if (p.getInventory().numberOf(itemid) < amount) {
                    shop.sellItem(shopslot, p.getInventory().numberOf(itemid));
                    p.Points += value / 1.10 * p.getInventory().numberOf(itemid);
                } else {
                    shop.sellItem(shopslot, amount);
                    p.Points += value / 1.10 * amount;
                }
                p.getInventory().deleteItem(itemid, amount);
                p.getInventory().refresh();
                p.getFrames().sendChatMessage(0, "You sold the <col=3300ff>[" + ItemDefinitions.forID(itemid).name + "]</col> for <col=3300ff>[" + (int) (value / 1.10) + "]</col> points.");
                p.getFrames().sendItems(3, shops.getShop(p.getShophandler().getShopId()).items, false);
                for (Player d : World.getPlayers())
                    d.getFrames().sendItems(3, shops.getShop(p.getShophandler().getShopId()).items, false);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleShopButtons(Player p, int buttonid, int packetid, int slot) {
        int itemworth = shops.getShop(p.getShophandler().getShopId()).items
                .get(slot)[2];
        switch (buttonid) {
            case 25:
                switch (packetid) {
                    case 24:
                        buyItem(p, slot, 1);
                        break;
                    case 0:
                    case 1:
                    case 2:
                    case 79:
                        if (itemworth == 0) {
                            p.getFrames().sendChatMessage(0, "This item is <col=ff0000>FREE</col>!");
                            return;
                        }
                        p.getFrames().sendChatMessage(0, "This item costs <col=ff0000>[" + itemworth + "]</col> points.");
                        break;

                    case 48:
                        buyItem(p, slot, 1);
                        break;

                    case 40:
                        buyItem(p, slot, 1);
                        break;

                    case 13:
                        buyItem(p, slot, 1);
                        break;

                    case 55:
                        buyItem(p, slot, 1);
                        break;
                }
                break;
        }
    }

    public void buyItem(Player p, int slot, int amount) {
        try {
            if (Voteshop) {
                int itemid = shops.getShop(p.getShophandler().getShopId()).items
                        .get(slot)[0];
                Item item = new Item(itemid, amount);
                int itemworth = shops.getShop(p.getShophandler().getShopId()).items
                        .get(slot)[2];
                if (!p.getInventory().hasRoomFor(itemid, amount)) {
                    p.getFrames().sendChatMessage(0,
                            "You don't have room to buy this item.");
                    return;
                }
                if (shops.getShop(p.getShophandler().getShopId()).items.get(slot)[1]
                        - amount < 0) {
                    p.getFrames().sendChatMessage(0,
                            "Not enough stock to sell this item.");
                    return;
                }
                if (p.votePoints * amount >= itemworth) {
                    p.getInventory().addItem(itemid, amount);
                    p.votePoints -= amount * itemworth;
                    shops.getShop(p.getShophandler().getShopId()).buyItem(slot,
                            amount);
                    p.getFrames().sendChatMessage(0, "You purchase the <col=3300ff>[" + ItemDefinitions.forID(itemid).name + "]</col> for <col=3300ff>[" + itemworth + "]</col> vote points.");
                    p.getFrames().sendItems(3,
                            shops.getShop(p.getShophandler().getShopId()).items,
                            false);
                } else p.getFrames().sendChatMessage(
                        0,
                        "You need " + amount * itemworth
                                + " vote points to buy this.");

            }
            if (!Voteshop) {
                int itemid = shops.getShop(p.getShophandler().getShopId()).items
                        .get(slot)[0];
                Item item = new Item(itemid, amount);
                int itemworth = shops.getShop(p.getShophandler().getShopId()).items
                        .get(slot)[2];
                if (!p.getInventory().hasRoomFor(itemid, amount)) {
                    p.getFrames().sendChatMessage(0,
                            "You don't have room to buy this item.");
                    return;
                }
                if (shops.getShop(p.getShophandler().getShopId()).items.get(slot)[1]
                        - amount < 0) {
                    p.getFrames().sendChatMessage(0,
                            "Not enough stock to sell this item.");
                    return;
                }
                if (p.Points * amount >= itemworth) {
                    p.getInventory().addItem(itemid, amount);
                    p.Points -= amount * itemworth;
                    shops.getShop(p.getShophandler().getShopId()).buyItem(slot,
                            amount);
                    p.getFrames().sendChatMessage(0, "You purchase the <col=3300ff>[" + ItemDefinitions.forID(itemid).name + "]</col> for <col=3300ff>[" + itemworth + "]</col> points.");
                    p.getFrames().sendItems(3, shops.getShop(p.getShophandler().getShopId()).items, false);
                    for (Player d : World.getPlayers())
                        d.getFrames().sendItems(3, shops.getShop(p.getShophandler().getShopId()).items, false);
                } else p.getFrames().sendChatMessage(
                        0,
                        "You need " + amount * itemworth
                                + " points to buy this.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}