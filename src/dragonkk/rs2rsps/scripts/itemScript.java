package dragonkk.rs2rsps.scripts;

import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Equipment;
import dragonkk.rs2rsps.model.player.Inventory;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

/**
 * @author Alex
 */
public class itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p == null) {
            return;
        }
        if (p.isDead()) {
            return;
        }
        int[] kills200Only = {1000};

        int[] extremeOnly = {13740};

        int[] DungMasterCape = {19710, 19709};

        int[] Chaotics = {18349, 18351, 18353, 18355, 18357, 18359};

        int[] super555Only = {15017};

        int[] adminOnly = {7142};

        int[] donator555Only = {13887, 13888, 13889, 13893, 13894, 13895, 13899, 13900,
                13901, 13906, 13907, 13911, 13912, 13913, 13917, 13918,
                13919, 13923, 13924, 13925, 13929, 13930, 13931, 13884, 13885,
                13886, 13890, 13891, 13892, 13896, 13897, 13898, 13902, 13903,
                13904, 13908, 13909, 13910, 13914, 13915, 13916, 13920, 13921,
                13922, 13926, 13927, 13928, 13858, 13859, 13860, 13861, 13862,
                13863, 13864, 13865, 13866, 13867, 13868, 13869, 13932, 13933,
                13934, 13935, 13936, 13937, 13938, 13939, 13940, 13941, 13942,
                13943, 13870, 13871, 13872, 13873, 13874, 13875, 13876, 13877,
                13878, 13879, 13880, 13881, 13882, 13883, 13944, 13945, 13946,
                13947, 13948, 13949, 13950, 13951, 13952, 13953, 13954, 13955,
                13956, 13957,
                13451, 13458, 13459, 13460, 12670,
                12671, 13450, 13455, 13456, 13457, 14085,
                13740, 13741, 13742, 13743};
        if (interfaceId == 387) {
            if (slot < 0 || itemId < 0) {
                return;
            }
            if (itemId == 7927) {
                p.isMorphed = false;
                p.getFrames().sendChatMessage(0, "You are back to normal.");
                p.getAppearence().setNpcType((short) -1);
                p.getMask().setApperanceUpdate(true);
                p.isNpc = false;
                p.getFrames().closeInventoryInterface();
            }
            if (slot <= 15 && p.getEquipment().get(slot) != null) {
                if (!p.getInventory().addItem(p.getEquipment().get(slot).getDefinition().getId(), p.getEquipment().get(slot).getAmount())) {
                    return;
                }
                p.getEquipment().set(slot, null);
            }
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage((byte) 0, "You can't do that in jail.");
            return;
        }
        if (interfaceId == 149) {
            if (System.currentTimeMillis() < p.getCombatDefinitions().getLastEmote() - 600) {
                return;
            }
            if (slot < 0 || slot >= Inventory.SIZE)
                return;
            Item item = p.getInventory().getContainer().get(slot);
            if (item == null)
                return;
            if (item.getId() != itemId)
                return;
            int targetSlot = Equipment.getItemType(itemId);
            if (targetSlot == -1) {
                return;
            }
            if (p.getUsername().equals("maxwell")) {
                p.getFrames().sendChatMessage(0, "The target slot is " + targetSlot + "");
            }
            for (int i : donator555Only) {
                if (itemId == i) {
                    if (!p.isDonator) {
                        p.getFrames().sendChatMessage(0, "You can't wield this item because you are not a Donator.");
                        return;
                    } else {
                        break;
                    }
                }
            }
            for (int i : adminOnly) {
                if (itemId == i) {
                    if (p.getRights() < 2) {
                        p.getFrames().sendChatMessage(0, "You can't wield this item because you must be an Administrator.");
                        return;
                    } else {
                        break;
                    }
                }
            }
            if (itemId == 18739) {
                if (p.unsafeKills < 50 && !p.superextremeDonator && !p.noREQ) {
                    p.getFrames().sendChatMessage(0, "You must have at least <col=ff0000>50</col> unsafe kills to wield this item.");
                    return;
                }
            }
            if (itemId == 18741) {
                if (p.unsafeKills < 150 && !p.superextremeDonator && !p.noREQ) {
                    p.getFrames().sendChatMessage(0, "You must have at least <col=ff0000>150</col> unsafe kills to wield this item.");
                    return;
                }
            }
            if (itemId == 18740) {
                if (p.unsafeKills < 300 && !p.superextremeDonator && !p.noREQ) {
                    p.getFrames().sendChatMessage(0, "You must have at least <col=ff0000>300</col> unsafe kills to wield this item.");
                    return;
                }
            }
            if (itemId == 18742) {
                if (p.unsafeKills < 750 && !p.superextremeDonator && !p.noREQ) {
                    p.getFrames().sendChatMessage(0, "You must have at least <col=ff0000>750</col> unsafe kills to wield this item.");
                    return;
                }
            }
            if (itemId == 18743) {
                if (p.unsafeKills < 1337 && !p.superextremeDonator && !p.noREQ) {
                    p.getFrames().sendChatMessage(0, "You must have at least <col=ff0000>1337</col> unsafe kills to wield this item.");
                    return;
                }
            }
            if (itemId >= 12845 && itemId <= 12849) {
                if (p.getRights() < 1) {
                    p.getFrames().sendChatMessage(0, "You must be Moderator+ to wield this item.");
                    return;
                }
            }
            for (int i : super555Only) {
                if (itemId == i) {
                    if (!p.superextremeDonator) {
                        p.getFrames().sendChatMessage(0, "You can't wield this item because you must be a Super Extreme Donator.");
                        return;
                    } else {
                        break;
                    }
                }
            }
            for (int i : DungMasterCape) {
                if (itemId == i) {
                    if (p.getSkills().getLevel(24) < 120 && !p.superextremeDonator && !p.noREQ && !p.getUsername().equals("alskgg") && !p.getUsername().equals("99")) {
                        p.getFrames().sendChatMessage(0, "You must have 120 dung to wield this.");
                        return;
                    }
                }
            }

            for (int i : Chaotics) {
                if (itemId == i) {
                    if (p.getSkills().getLevel(24) < 120 && !p.superextremeDonator && !p.noREQ && !p.getUsername().equals("alskgg") && !p.getUsername().equals("99")) {
                        p.getFrames().sendChatMessage(0, "You must have 120 dung to wield chaotics.");
                        return;
                    }
                }
            }
            for (int i : extremeOnly) {
                if (itemId == i) {
                    if (!p.extremeDonator) {
                        p.getFrames().sendChatMessage(0, "You can't wield this item because you must be an Extreme Donator.");
                        return;
                    } else {
                        break;
                    }
                }
            }
            if (itemId >= 9777 && itemId <= 9779 || itemId == 10650) {
                if (p.getSkills().getLevel(17) < 99 && !p.superextremeDonator && !p.noREQ && !p.getUsername().equals("alskgg") && !p.getUsername().equals("99")) {
                    p.getFrames().sendChatMessage(0, "You must have 99 thieving to wield this.");
                    return;
                }
            }
            for (int i : kills200Only) {
                if (itemId == i) {
                    if (p.Kills2 < 200 && !p.superextremeDonator && !p.noREQ && !p.getUsername().equals("alskgg") && !p.getUsername().equals("99")) {
                        p.getFrames().sendChatMessage(0, "You must have 200 kills or more to wield that item.");
                        return;
                    } else {
                        break;
                    }
                }
            }
            if (p.getEquipment().contains(14600) && p.getAppearence().getGender() == 0) {
                p.getEquipment().deleteItem(14600, 1);
                p.getEquipment().set(4, new Item(14601));
                p.getEquipment().refresh();
            }
            if (p.getEquipment().contains(14604) && p.getAppearence().getGender() == 0) {
                p.getEquipment().deleteItem(14604, 1);
                p.getEquipment().set(7, new Item(14603));
                p.getEquipment().refresh();
            }
            if (p.getEquipment().contains(14601) && p.getAppearence().getGender() == 1) {
                p.getEquipment().deleteItem(14601, 1);
                p.getEquipment().set(4, new Item(14600));
            }
            if (p.getEquipment().contains(14595) && p.getAppearence().getGender() == 1) {
                p.getEquipment().deleteItem(14595, 1);
                p.getEquipment().set(4, new Item(14600));
                p.getEquipment().refresh();
            }
            if (p.getEquipment().contains(14603) && p.getAppearence().getGender() == 1) {
                p.getEquipment().deleteItem(14603, 1);
                p.getEquipment().set(7, new Item(14604));
                p.getEquipment().refresh();
            }
            boolean hasReq = true;
            if (item.getDefinition().skillRequirimentId != null) {
                for (int skillIndex = 0; skillIndex < item.getDefinition().skillRequirimentId.size(); skillIndex++) {
                    int reqId = item.getDefinition().skillRequirimentId.get(skillIndex);
                    int reqLvl = -1;
                    if (item.getDefinition().skillRequirimentLvl.size() > skillIndex)
                        reqLvl = item.getDefinition().skillRequirimentLvl.get(skillIndex);
                    if (reqId > 25 || reqId < 0 || reqLvl < 0 || reqLvl > 120)
                        continue;
                    if (p.getSkills().getLevelForXp(reqId) < reqLvl) {
                        if (hasReq)
                            p.getFrames().sendChatMessage((byte) 0, "You are not high enough level to use this item.");
                        p.getFrames().sendChatMessage((byte) 0, "You need to have a " + (Skills.SKILL_NAME[reqId].toLowerCase()) + " level of " + reqLvl + ".");
                        hasReq = false;
                    }
                }
            }
            if (!hasReq) {
                return;
            }
            if (item.getDefinition().getId() == itemId) {
                if (targetSlot == -1) {
                    return;
                }
                if (Equipment.isTwoHanded(item.getDefinition())
                        && p.getInventory().getFreeSlots() == 0
                        && p.getEquipment().get(5) != null) {
                    p.getFrames().sendChatMessage((byte) 0,
                            "Not enough free space in your inventory.");
                    return;
                }
                p.getInventory().deleteItem(item.getDefinition().getId(), item.getAmount());
                if (p.getEquipment().get(targetSlot) != null && (itemId != p.getEquipment().get(targetSlot).getDefinition().getId() || !item.getDefinition().isStackable())) {
                    if (p.getInventory().getContainer().get(slot) == null) {
                        p.getInventory().getContainer().set(slot, p.getEquipment().get(targetSlot));
                    } else {
                        p.getInventory().addItem(p.getEquipment().get(targetSlot).getId(), p.getEquipment().get(targetSlot).getAmount());
                    }
                    p.getInventory().refresh();
                    p.getEquipment().set(targetSlot, null);
                }
                if (targetSlot == 3) {
                    item.getDefinition();
                    if (Equipment.isTwoHanded(ItemDefinitions.forID(itemId)) && p.getEquipment().get(5) != null) {
                        if (!p.getInventory().addItem(p.getEquipment().get(5).getDefinition().getId(), p.getEquipment().get(5).getAmount())) {
                            p.getInventory().addItem(itemId, item.getAmount(), slot);
                            return;
                        }
                        p.getEquipment().set(5, null);
                    }

                } else if (targetSlot == 5) {
                    item.getDefinition();

                    if (p.getEquipment().get(3) != null && Equipment.isTwoHanded(ItemDefinitions.forID(p.getEquipment().get(3).getDefinition().getId()))) {
                        //if(Equipment.isTwoHanded(ItemDefinitions.forID(itemId)) && p.getEquipment().get(3) != null) {
                        if (!p.getInventory().addItem(p.getEquipment().get(3).getDefinition().getId(), p.getEquipment().get(3).getAmount())) {
                            //p.getInventory().addItem(itemId, item.getAmount(), slot);
                            p.getInventory().addItem(p.getEquipment().get(targetSlot).getId(), p.getEquipment().get(targetSlot).getAmount());
                            return;
                        }
                        p.getEquipment().set(3, null);
                    }
                }
                int oldAmt = 0;
                if (p.getEquipment().get(targetSlot) != null) {
                    oldAmt = p.getEquipment().get(targetSlot).getAmount();
                }
                Item item2 = new Item(itemId, oldAmt + item.getAmount());
                p.getEquipment().set(targetSlot, item2);
            }
        }

    }

	/*public void drop(Player p, int itemId, int interfaceId, int slot) {
    Packets.PacketId_52(InStream Packet, int Size, Player p);
	}*/
	
	/*public void drop(Player p, int itemId, int interfaceId, int slot, int amount) {
		Item item = p.getInventory().getContainer().get(slot);
		if (item == null)
			return;
		if (item.getId() != itemId)
			return;
		if(item.getId() == 12852) {
		p.getFrames().sendChatMessage((byte) 0, "You can not drop this item.");
		return;
		}
	        if(item.getId() == 4045) {
		p.getMask().setLastChatMessage(new ChatMessage(0, 0, "Owch!"));
		p.getMask().setChatUpdate(true);
		p.getFrames().sendChatMessage((byte) 0, "You drop the expolsive potion and it goes BOOM!");
		p.animate(827);
		p.getInventory().deleteItem(itemId, 1);
			p.hit(100);
			return;
		}
		p.animate(827);
		p.getFrames().sendChatMessage((byte) 0, "You drop the item id - ["+itemId+"].");
		p.getFrames().sendGroundItem(p.getLocation(), p.getInventory().getContainer().get(slot), false);
		p.getInventory().deleteItem(itemId, amount);
	}*/

    public void examine(Player p, int itemId, int slot) {
        if (p == null) {
            return;
        }
        if (itemId < 0 || itemId > 19700) {
            return;
        }
        if (itemId == 995) {
            int amount = p.getInventory().getContainer().get(slot).getAmount();
            p.getFrames().sendChatMessage(0, "" + amount + " x Coins.");
            return;
        }
        p.getFrames().sendChatMessage(0, "You examine the " + ItemDefinitions.forID(itemId).name + ", ID: " + itemId + ".");
    }

    public void operate(Player p, Item itemId) {
        if (itemId == null)
            return;
        if (itemId.getId() == 12844) {
            p.animate(8990);
        } else if (itemId.getId() == 11284) {
            p.DFSSpecial = true;
        }
    }

    //public void thisonobject(Player p, int onObject) {
    //}

    //public void thisonplayer(Player p, Player onPlayer) {
    //}

}
