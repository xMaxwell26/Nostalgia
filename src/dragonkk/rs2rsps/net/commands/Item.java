package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

public class Item implements Command {

    public void execute(String[] args, Player p) {
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        int[] UNSPAWNS = {6862, 12183, 18016, 15262, 8008, 8009, 8007, 8010, 8011, 8012, 8013, 10733, 4079, 15098, 15099, 2520, 2521, 2522, 2523, 2524, 2525, 2526, 2527, 2528, 4083, 4084, 18348, 4067, 18346, 10842, 7927, 10729, 12844, 18740, 18741, 18742, 18743, 18739, 4067, 18580, 18581, 18582, 18583, 13677, 13678, 13679, 13680, 13681, 13682, 13683, 13684, 1763, 1764, 1771, 1772, 1765, 1766, 1767, 1768, 11808, 15441, 15442, 15443, 15444, 15816, 16971, 16972, 17357, 17358, 16032, 16399, 16400, 16963, 16964, 16043, 16951, 16952, 16959, 16960, 16182, 16421, 16422, 16965, 16966, 8898, 8899, 8890, 995, 1004, 7806, 4178, 7142, 11604, 11603, 13907, 8871, 10734, 13906, 18350, 18352, 18354, 18356, 18358, 18360, 18362, 18364, 14661, 1003, 1002, 1001, 1000, 999, 998, 997, 996, 1046, 1047, 15349, 15347, 1205, 1206, 15345, 19710, 19709, 6570, 10566, 10637, 962, 963, 10551, 1037, 1050, 1419, 10735, 4079, 10733, 14595, 14600, 14601, 14602, 14603, 14604, 14605, 15825, 17273, 17274, 13263, 14636, 14637, 15492, 15496, 15497};
        int amount = Integer.parseInt(args[2]);
        int item = Integer.parseInt(args[1]);
        int itemid = Integer.parseInt(args[1]);
        if (ItemDefinitions.forID(itemid).name == "Null") {
            p.getFrames().sendChatMessage(0, "You can't spawn this item.");
            return;
        }
        if (ItemDefinitions.forID(itemid).name == "null") {
            p.getFrames().sendChatMessage(0, "You can't spawn this item.");
            return;
        }
        if (amount < 1) {
            amount = 1;
        }
        if (amount >= 2147483647) {
            amount = 1;
        }
        if (p.getInventory().contains(item, 2147483647)) {
            p.getFrames().sendChatMessage(0, "You have max of that item in your inventory.");
            return;
        }
        if (Integer.parseInt(args[1]) == 12852 || item == 13531 || item == 13532 || item == 13533 || item == 13534 || item == 13535 || item == 13536 && !p.isDonator) {
            return;
        }
/*
*
*Anti dupe - spawn 600 ags ect...
*
*/

		/*if(item >= 11694 && item <= 11700 && amount > 10)  {
        p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn over 10 of this item.");
		return;
		}*/



/*
*
*END OF ANTI DUPE
*
*/
        if (item == 12183 || item == 18016) {
            p.getFrames().sendChatMessage(0, "You must spawn Spirit Shards [12530].");
            return;
        }
        if (item == 712) {
            p.getFrames().sendChatMessage(0, "Type ::tea to spawn some tea.");
            return;
        }
        if (item >= 15069 && item <= 15072) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>This item is a RARE and can't be spawned.");
            return;
        }
        if (item >= 15300 && item <= 15303 || item >= 15332 && item <= 15335) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn this potion.");
            return;
        }
        if (item == 8890 || item == 617 || item == 17391 || item == 17392 || item == 18201 || item == 20923) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You can't spawn them coins.");
            return;
        }
        if (item >= 13650 && item <= 13654) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn these tokens.");
            return;
        }
        if (item >= 8839 && item <= 8842 || item == 10611 || item >= 11663 && item <= 11676 || item >= 19711 && item <= 19712 || item >= 19785 && item <= 19790 || item >= 19803 && item <= 19804) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn void.");
            return;
        }
        if (item >= 15009 && item <= 15020 || item == 15220) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn (i) rings, you can get them in safe pvp drops.");
            return;
        }
        if (item >= 13911 && item <= 13931) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn corrupted vesta's!");
            return;
        }
        if (item >= 13908 && item <= 13928) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn corrupted statius's!");
            return;
        }
        if (item >= 13944 && item <= 13957) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn corrupted morrigan's!");
            return;
        }
        if (item >= 13932 && item <= 13943) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn corrupted zuriel's!");
            return;
        }
		/*if(item >= 1038 && item <= 1049) {
		p.getFrames().sendChatMessage(0, "<col=00FF00>Get this item out of a christmas cracker!");
		return;
		}*/
        if (item == 2422) {
            p.getFrames().sendChatMessage(0, "<col=00FF00>No!");
            return;
        }
        if (item >= 13405 && item <= 13558 || item >= 13763 && item <= 13839 || item == 14486 || item == 15502 || item == 19310 || item == 19313 || item == 19316 || item == 19319 || item == 19322) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn lended items.");
            return;
        }
        if (item > 19709) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn that item.");
            return;
        }
        for (int[] data : PARTY_HATS) {
            if (data[0] == Integer.parseInt(args[1])) {
                if (p.getInventory().contains(962, data[1] * amount)) {
                    if (p.getInventory().hasRoomFor(Integer.parseInt(args[1]), amount)) {
                        if (amount > 1) {
                            p.getFrames().sendChatMessage(0, "<col=ff0000>You cannot spawn over 1 of this item.");
                            return;
                        }
                        p.getInventory().deleteItem(962, data[1] * amount);
                        p.getInventory().addItem(Integer.parseInt(args[1]), amount);
                        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have opened the cracker and got the [" + ItemDefinitions.forID(itemid).name + "]!"));
                        p.getMask().setChatUpdate(true);
                        p.getFrames().sendChatMessage(0, "You have opened the cracker and got the [" + ItemDefinitions.forID(itemid).name + "]!");
                        return;
                    } else {
                        p.getFrames().sendChatMessage(0, "<col=ff0000>You don't have enough space in your inventory to buy this.");
                        return;
                    }
                } else {
                    p.getFrames().sendChatMessage(0, "<col=ff0000>To spawn this item you need a christmas cracker.");
                    return;
                }
            }
        }
        for (int[] data : VOTE_ITEMS) {
            if (data[0] == Integer.parseInt(args[1])) {
                if (p.votePoints == data[1] || p.votePoints > data[1]) {
                    if (p.getInventory().hasRoomFor(Integer.parseInt(args[1]), amount)) {
                        if (amount > 1) {
                            p.getFrames().sendChatMessage(0, "You can only spawn 1 of this item at a time.");
                            return;
                        }
                        p.votePoints -= data[1] * amount;
                        p.getInventory().addItem(Integer.parseInt(args[1]), amount);
                        p.getFrames().sendChatMessage(0, "You have purchased the " + ItemDefinitions.forID(itemid).name + ".");
                        return;
                    } else {
                        p.getFrames().sendChatMessage(0, "<col=ff0000>You don't have enough space in your inventory to buy this.");
                        return;
                    }
                } else {
                    p.getFrames().sendChatMessage(0, "<col=ff0000>You need " + data[1] + " vote points to spawn this.");
                    return;
                }
            }
        }
        for (int[] data : ITEM_PRICES) {
            if (data[0] == Integer.parseInt(args[1])) {
                if (p.getInventory().contains(995, data[1] * amount)) {
                    if (amount > 10) {
                        p.getFrames().sendChatMessage(0, "<col=ff0000>You can't spawn 10+ of an item.");
                        return;
                    }
                    if (p.getInventory().hasRoomFor(itemid, amount)) {
                        if (data[1] * amount > 2147000000) {
                            p.getFrames().sendChatMessage(0, "<col=ff0000>You can't sell that for that much.");
                            return;
                        }
                        p.getInventory().deleteItem(995, data[1] * amount);
                        p.getInventory().addItem(Integer.parseInt(args[1]), amount);
                        //p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have [PURCHASED] ["+amount+"] of ["+ItemDefinitions.forID(itemid).name+"] for "+data[1] * amount+" coins!"));
                        //p.getMask().setChatUpdate(true);
                        p.getFrames().sendChatMessage(0, "You purchase " + amount + " of <col=3300ff>[" + ItemDefinitions.forID(itemid).name + "]</col> for <col=000000>" + data[1] * amount + "</col> coins.");
                        return;
                    } else {
                        p.getFrames().sendChatMessage(0, "<col=ff0000>You don't have enough space in your inventory to buy this.");
                        return;
                    }
                } else {
                    p.getFrames().sendChatMessage(0, "<col=ff0000>You don't have enough coins to purchase this items;");
                    p.getFrames().sendChatMessage(0, "<col=ff0000>To spawn this item you need " + data[1] * amount + " coins.");
                    return;
                }
            }
        }
        for (int[] data : SHARDS) {
            if (data[0] == Integer.parseInt(args[1])) {
                if (p.getInventory().contains(995, data[1] * amount)) {
                    if (amount > 1000) {
                        p.getFrames().sendChatMessage(0, "<col=ff0000>You can only spawn under 1000 at a time.");
                        return;
                    }
                    if (p.getInventory().hasRoomFor(itemid, amount)) {
                        if (data[1] * amount > 2147000000) {
                            p.getFrames().sendChatMessage(0, "<col=ff0000>You can't buy that for that much.");
                            return;
                        }
                        p.getInventory().deleteItem(995, data[1] * amount);
                        p.getInventory().addItem(Integer.parseInt(args[1]), amount);
                        //p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have [PURCHASED] ["+amount+"] of ["+ItemDefinitions.forID(itemid).name+"] for "+data[1] * amount+" coins!"));
                        //p.getMask().setChatUpdate(true);
                        p.getFrames().sendChatMessage(0, "You purchase " + amount + " of <col=3300ff>[" + ItemDefinitions.forID(itemid).name + "]</col> for <col=000000>" + data[1] * amount + "</col> coins.");
                        return;
                    } else {
                        p.getFrames().sendChatMessage(0, "<col=ff0000>You don't have enough space in your inventory to buy this.");
                        return;
                    }
                } else {
                    p.getFrames().sendChatMessage(0, "<col=ff0000>You don't have enough coins to purchase this items;");
                    p.getFrames().sendChatMessage(0, "<col=ff0000>To spawn this item you need " + data[1] * amount + " coins.");
                    return;
                }
            }
        }
        for (int i : UNSPAWNS) {
            if (Integer.parseInt(args[1]) == i) {
                p.getFrames().sendChatMessage(0, "You can't spawn " + ItemDefinitions.forID(itemid).name + ".");
                p.getFrames().sendChatMessage(0, "This item is either a rare or is just unspawnable.");
                return;
            }
        }
        p.getInventory().addItem(Integer.parseInt(args[1]), amount);
    }

    public final int[][] PARTY_HATS = {
            {1038, 1},
            {1037, 1},
            {1040, 1},
            {1042, 1},
            {1044, 1},
            {1048, 1},
            {1039, 1},
            {1041, 1},
            {1043, 1},
            {1045, 1},
            {1049, 1},
    };

    public final int[][] SHARDS = {
            {12530, 1000000},
    };
    public final int[][] VOTE_ITEMS = {
            {13905, 30},
    };

    public final int[][] ITEM_PRICES = {
            {13887, 7500000},
            {13888, 7500000},
            {13889, 7500000},
            {13894, 7500000},
            {13893, 7500000},
            {13895, 7500000},
            {13899, 43500000},
            {13900, 43500000},
            {13901, 7500000},
            //{13905, 5500000},
            //{13906, 5500000},

            {11235, 4300000}, //Dark Bow
            {11236, 4300000}, //Dark Bow
            {15241, 9700000}, //Hand cannon
            {15242, 9700000}, //Hand cannon
            {15243, 2400}, //Hand cannon shot
            {11212, 1600}, //Dragon Arrow
            {11217, 1600}, //Dragon Fire Arrow
            {11222, 1600}, //Dragon Fire Arrow (2)


            {13884, 7500000}, // stat
            {13885, 7500000}, // stat
            {13886, 7500000}, // stat
            {13890, 7500000}, // stat
            {13891, 7500000}, // stat
            {13892, 7500000}, // stat
            {13896, 7500000}, // stat
            {13897, 7500000}, // stat
            {13898, 7500000}, // stat
            {13902, 7500000}, // stat
            {13903, 7500000}, // stat
            {13904, 7500000}, // stat


            {13870, 7500000}, // morrigan
            {13871, 7500000}, // morrigan
            {13872, 7500000}, // morrigan
            {13873, 7500000}, // morrigan
            {13874, 7500000}, // morrigan
            {13875, 7500000}, // morrigan
            {13876, 7500000}, // morrigan
            {13877, 7500000}, // morrigan
            {13878, 7500000}, // morrigan
            {13879, 7500000}, // morrigan
            {13880, 7500000}, // morrigan
            {13881, 7500000}, // morrigan
            {13882, 7500000}, // morrigan
            {13883, 7500000}, // morrigan


            {13858, 7500000}, // zuriel
            {13859, 7500000}, // zuriel
            {13860, 7500000}, // zuriel
            {13861, 7500000}, // zuriel
            {13862, 7500000}, // zuriel
            {13863, 7500000}, // zuriel
            {13864, 7500000}, // zuriel
            {13865, 7500000}, // zuriel
            {13866, 7500000}, // zuriel
            {13867, 7500000}, // zuriel
            {13868, 7500000}, // zuriel
            {13869, 7500000}, // zuriel

            {6733, 2400000}, // archer ring
            {6734, 2400000}, // archer ring
            {6731, 1400000}, // seers ring
            {6732, 1400000}, // seers ring
            {6735, 400000}, // warrior ring
            {6736, 400000}, // warrior ring
            {6737, 3500000}, // berserker ring
            {6738, 3500000}, // berserker ring
            {1215, 60000}, // Dragon dagger
            {1216, 60000}, // Dragon dagger
            {1231, 60000}, // Dragon dagger
            {1232, 60000}, // Dragon dagger
            {5680, 60000}, // Dragon dagger
            {5681, 60000}, // Dragon dagger
            {5698, 60000}, // Dragon dagger
            {5699, 60000}, // Dragon dagger
            {4718, 782700}, // Dharok's Greataxe
            {4719, 782700}, // Dharok's Greataxe
            {4716, 732700}, // Dharok's Helm
            {4717, 732700}, // Dharok's Helm
            {4720, 1100000}, // Dharok's Platebody
            {4721, 1100000}, // Dharok's Platebody
            {4722, 1600000}, // Dharok's Platelegs
            {4723, 1600000}, // Dharok's Platelegs
            {4708, 92400}, // Ahrim's Hood
            {4709, 92400}, // Ahrim's Hood
            {4710, 105200}, // Ahrim's Staff
            {4711, 105200}, // Ahrim's Staff
            {4712, 1700000}, // Ahrim's Robetop
            {4713, 1700000}, // Ahrim's Robetop
            {4714, 4700000}, // Ahrim's Robeskirt
            {4715, 4700000}, // Ahrim's Robeskirt
            {4724, 2500000}, // Guthan's Helm
            {4725, 2500000}, // Guthan's Helm
            {4726, 1600000}, // Guthan's Warspear
            {4727, 1600000}, // Guthan's Warspear
            {4728, 607500}, // Guthan's Platebody
            {4729, 607500}, // Guthan's Platebody
            {4730, 744700}, // Guthan's Chainskirt
            {4731, 744700}, // Guthan's Chainskirt
            {4732, 101700}, // Karil's Coif
            {4733, 101700}, // Karil's Coif
            {4734, 532400}, // Karil's Crossbow
            {4735, 532400}, // Karil's Crossbow
            {4736, 4900000}, // Karil's Top
            {4737, 4900000}, // Karil's Top
            {4738, 628000}, // Karil's Skirt
            {4739, 628000}, // Karil's Skirt
            {4745, 866200}, // Torag's Helm
            {4746, 866200}, // Torag's Helm
            {4747, 105500}, // Torag's Hammers
            {4748, 105500}, // Torag's Hammers
            {4749, 672400}, // Torag's Platebody
            {4750, 672400}, // Torag's Platebody
            {4751, 1500000}, // Torag's Platelegs
            {4752, 1500000}, // Torag's Platelegs
            {4753, 3800000}, // Verac's Helm
            {4754, 3800000}, // Verac's Helm
            {4755, 256300}, // Verac's Flail
            {4756, 256300}, // Verac's Flail
            {4757, 435100}, // Verac's Brassard
            {4758, 435100}, // Verac's Brassard
            {4759, 1800000}, // Verac's Plateskirt
            {4760, 1800000}, // Verac's Plateskirt
            {3481, 1800000}, // Gilded Platebody
            {3482, 1800000}, // Gilded Platebody
            {3483, 1700000}, // Gilded Platelegs
            {3484, 1700000}, // Gilded Platelegs
            {3485, 335500}, // Gilded Plateskirt
            {3486, 649200}, // Gilded Full Helm
            {3487, 649200}, // Gilded Full Helm
            {3488, 515800}, // Gilded Kiteshield
            {3489, 515800}, // Gilded Kiteshield
            {10330, 44900000}, // Third-age Range Top
            {10331, 44900000}, // Third-age Range Top
            {10332, 34000000}, // Third-age Range Legs
            {10333, 34000000}, // Third-age Range Legs
            {10334, 12200000}, // Third-age Range Coif
            {10335, 12200000}, // Third-age Range Coif
            {10336, 12400000}, // Third-age Vambraces
            {10337, 12400000}, // Third-age Vambraces
            {10338, 60400000}, // Third-age Robe Top
            {10339, 60400000}, // Third-age Robe Top
            {10340, 22500000}, // Third-age Robe
            {10341, 22500000}, // Third-age Robe
            {10342, 34400000}, // Third-age Mage Hat
            {10343, 34400000}, // Third-age Mage Hat
            {10344, 23100000}, // Third-age Amulet
            {10345, 23100000}, // Third-age Amulet
            {10346, 127100000}, // Third-age Platelegs
            {10347, 127100000}, // Third-age Platelegs
            {10348, 116300000}, // Third-age Platebody
            {10349, 116300000}, // Third-age Platebody
            {10350, 36000000}, // Third-age Full Helmet
            {10351, 36000000}, // Third-age Full Helmet
            {10352, 73500000}, // Third-age Kiteshield
            {10353, 73500000}, // Third-age Kiteshield
            {19308, 16900000}, // Third-age druidic Staff
            {19309, 16900000}, // Third-age druidic Staff
            {19310, 16900000}, // Third-age druidic Staff
            {19311, 52300000}, // Third-age Druidic Cloak
            {19312, 52300000}, // Third-age Druidic Cloak
            {19313, 52300000}, // Third-age Druidic Cloak
            {19314, 47100000}, // Third-age Druidic Wreath
            {19315, 47100000}, // Third-age Druidic Wreath
            {19316, 47100000}, // Third-age Druidic Wreath
            {19317, 94200000}, // Third-age Druidic Robe Top
            {19318, 94200000}, // Third-age Druidic Robe Top
            {19319, 94200000}, // Third-age Druidic Robe Top
            {19320, 47100000}, // Third-age Druidic Robe
            {19321, 47100000}, // Third-age Druidic Robe
            {6916, 1700000}, // Infinity Top
            {6917, 1700000}, // Infinity Top
            {6918, 2300000}, // Infinity Hat
            {6919, 2300000}, // Infinity Hat
            {6920, 935200}, // Infinity Boots
            {6921, 935200}, // Infinity Boots
            {6922, 1500000}, // Infinity Gloves
            {6923, 1500000}, // Infinity Gloves
            {6924, 2700000}, // Infinity Bottoms
            {6925, 2700000}, // Infinity Bottoms
            {1079, 48400}, // Rune Platelegs
            {1080, 48400}, // Rune Platelegs
            {1127, 67400}, // Rune Platebody
            {1128, 67400}, // Rune Platebody
            {1163, 21000}, // Rune Full Helm
            {1164, 21000}, // Rune Full Helm
            {1201, 46700}, // Rune Kiteshield
            {1202, 46700}, // Rune Kiteshield
            {1093, 43200}, // Rune Plateskirt
            {1094, 43200}, // Rune Plateskirt
            {1127, 67400}, // Rune Platebody
            {1128, 67400}, // Rune Platebody
            {1163, 21000}, // Rune Full Helm
            {1164, 21000}, // Rune Full Helm
            {1201, 46700}, // Rune Kiteshield
            {1202, 46700}, // Rune Kiteshield
            {14484, 31600000}, // Dragon Claws
            {14485, 31600000}, // Dragon Claws
            {11694, 77600000}, // Armadyl Godsword
            {11695, 77600000}, // Armadyl Godsword
            {11696, 20100000}, // Bandos Godsword
            {11697, 20100000}, // Bandos Godsword
            {11698, 54700000}, // Saradomin Godsword
            {11699, 54700000}, // Saradomin Godsword
            {11730, 8500000}, // Saradomin Sword
            {11731, 8500000}, // Saradomin Sword
            {11700, 21900000}, // Zamorak Godsword
            {11701, 21900000}, // Zamorak Godsword
            {11716, 5300000}, // Zamorakian Spear
            {11717, 5300000}, // Zamorakian Spear
            {11718, 2900000}, // Armadyl Helmet
            {11719, 2900000}, // Armadyl Helmet
            {11720, 15500000}, // Armadyl Chestplate
            {11721, 15500000}, // Armadyl Chestplate
            {11722, 15400000}, // Armadyl Plateskirt
            {11723, 15400000}, // Armadyl Plateskirt
            {11724, 22300000}, // Bandos Chestplate
            {11725, 22300000}, // Bandos Chestplate
            {11726, 25000000}, // Bandos Tassets
            {11727, 25000000}, // Bandos Tassets
            {11728, 765300}, // Bandos Boots
            {11729, 765300}, // Bandos Boots
            {11732, 464400}, // Dragon Boots
            {11733, 464400}, // Dragon Boots
		/*{1038, 446300000}, // Red Partyhat
		{1039, 446300000}, // Red Partyhat
		{1040, 429400000}, // Yellow Partyhat
		{1041, 429400000}, // Yellow Partyhat
		{1042, 570000000}, // Blue Partyhat
		{1043, 570000000}, // Blue Partyhat
		{1044, 431800000}, // Green Partyhat
		{1045, 431800000}, // Green Partyhat
		{1046, 428100000}, // Purple Partyhat
		{1047, 428100000}, // Purple Partyhat
		{1048, 477300000}, // White Partyhat
		{1049, 477300000}, // White Partyhat*/
            {1050, 94000000}, // Santa Hat
            {1051, 94000000}, // Santa Hat
            {1053, 88800000}, // Green H'ween Mask
            {1054, 88800000}, // Green H'ween Mask
            {1055, 104800000}, // Blue H'ween Mask
            {1056, 104800000}, // Blue H'ween Mask
            {1057, 135600000}, // Red H'ween Mask
            {1058, 135600000}, // Red H'ween Mask
            {13736, 3900000}, // Blessed Spirit Shield
            {13737, 3900000}, // Blessed Spirit Shield
            {13738, 63600000}, // Arcane Spirit Shield
            {13739, 63600000}, // Arcane Spirit Shield
            {13740, 200000000}, // Divine Spirit Shield
            {13741, 200000000}, // Divine Spirit Shield
            {13742, 100000000}, // Elysian Spirit Shield
            {13743, 100000000}, // Elysian Spirit Shield
            {13744, 22600000}, // Spectral Spirit Shield
            {13745, 22600000}, // Spectral Spirit Shield
            {4151, 3400000}, // Abyssal Whip
            {4152, 3400000}, //Abyssal whip
            {4153, 237700}, // Granite Maul
            {4154, 237700}, // Granite Maul
            {6739, 1700000}, // Dragon Hatchet
            {6740, 1700000}, // Dragon Hatchet
            {11335, 33300000}, // Dragon Full Helm
            {11336, 33300000}, // Dragon Full Helm
            {1149, 60300}, // Dragon Med Helm
            {1150, 60300}, // Dragon Med Helm
            {14479, 11200000}, // Dragon Platebody
            {14480, 11200000}, // Dragon Platebody
            {2513, 6300000}, // Dragon Chainbody
            {2514, 6300000}, // Dragon Chainbody
            {4087, 668600}, // Dragon Platelegs
            {4088, 668600}, // Dragon Platelegs
            {4585, 197600}, // Dragon Plateskirt
            {4586, 197600}, // Dragon Plateskirt
            {13405, 985400}, // Dark Bow
            {13405, 985400}, // Dark Bow
            {15241, 900000}, // hand cannon
            {15242, 900000}, // hand cannon
            {18349, 200000000}, // Chaotic rapier
            {18351, 200000000}, // Chaotic longsword
            {18353, 200000000}, // Chaotic maul
            {18357, 200000000}, // Chaotic crossbow
            {18359, 200000000}, // Chaotic kiteshield
            {18355, 200000000}, // Chaotic staff
            {15486, 1400000}, // Staff of light
            {15487, 1400000}, // Staff of light
            {10828, 56000}, // nezzy helm
            {10843, 56000}, // nezzy helm
            {12680, 56000}, // nezzy helm
            {12681, 56000}, // nezzy helm
            {6585, 12000000}, // fury
            {6586, 12000000}, // fury
            {18335, 20000000}, // arcane stream
            {7462, 1000000}, // barrows gloves
            {7461, 500000}, // gloves
            {7460, 500000}, // gloves
            {7459, 500000}, // gloves
            {7458, 500000}, // gloves
            {7457, 500000}, // gloves
            {18336, 20000000}, // might be arcane stream
            {4675, 70000}, // ancient staff
            {4676, 70000}, // ancient staff
            {6914, 4170000}, // wand
            {6915, 4170000}, // wand
            {6889, 3470000}, // mages book
            {6890, 3470000}, // mages book
            {11283, 4170000}, // dfs
            {11284, 34700000}, // dfs
            {11285, 34700000}, // dfs
            {1725, 1200}, // ammy of str
            {1726, 1200}, // ammy of str
            {4587, 100000}, // dragon scim
            {4588, 100000}, // dragon scim
            {1305, 250000}, // dragon long/korasi
            {1306, 250000}, // dragon long/korasi

    };

}