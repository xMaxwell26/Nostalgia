package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.model.Container;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

import java.io.Serializable;

public class Equipment implements Serializable {

    private static final long serialVersionUID = -2934091862414178786L;

    private transient Player player;

    public static final byte SLOT_HAT = 0, SLOT_CAPE = 1, SLOT_AMULET = 2, SLOT_WEAPON = 3, SLOT_CHEST = 4, SLOT_SHIELD = 5, SLOT_LEGS = 7, SLOT_HANDS = 9, SLOT_FEET = 10, SLOT_RING = 12, SLOT_ARROWS = 13;

    private Container<Item> equipment = new Container<Item>(15, false);

    public Container<Item> getEquipment() {
        return equipment;
    }

    public void setPlayer(Player player) {
        for (Item item : getEquipment().getItems())
            if (item != null)
                item.setDefinition(ItemDefinitions.forID(item.getId()));
        this.player = player;
    }

    public boolean contains(int item) {
        return equipment.containsOne(new Item(item));
    }

    public void deleteItem(int item, int amount) {
        equipment.remove(new Item(item, amount));
        refresh();
    }

    public void deleteArrow(int item, int amount) {
        equipment.remove(new Item(item, amount));
        refreshEquipment();
    }

    public Item get(int slot) {
        return equipment.get(slot);
    }

    public void set(int slot, Item item) {
        equipment.set(slot, item);
        //if (slot == 3)
        //reset spec
        refresh();
    }

    public void clear() {
        equipment.clear();
        refresh();
    }

    private void refreshEquipment() {
        player.getFrames().sendItems(94, equipment, false);
    }

    public void refresh() {
        //if (player.isOnline())
        player.getFrames().sendSound(2248);
        player.getMask().setApperanceUpdate(true);
        player.getFrames().sendItems(94, equipment, false);
        player.getCombatDefinitions().refreshBonuses();
        player.getCombat().removeTarget();
        player.getCombatDefinitions().setSpecialOff();
    }

    private static String[] CAPES = {"druidic cloak", "Diving apparatus", "Grain", "Bonesack", "Bonesack(e)", "cloak", "cloak 3", "cape", "Cape", "Ava's accumulator"};
    private static String[] HATS = {"Dagon'hai hat", "goggles", "Goggles", "Woolly hat", "beret", "Voting", "voting", "Bunny ears", "mask", "Hat", "Pink hat", "Santa hat", "Top hat", "Infinity hat", "druidic wreath", "halo", "Royal", "crown", "sallet", "helm", "Bedsheet", "hood", "coif", "Coif", "partyhat", "Hat", "full helm (t)", "full helm (g)", "hat (t)", "hat (g)", "cav", "boater", "helmet", "Helm of neitiznot", "Fighter hat"};
    private static String[] BOOTS = {"boots", "Flippers", "Boots", "shoes", "Shoes"};
    private static String[] GLOVES = {"gloves", "Brawling", "gauntlets", "Gloves", "vambraces", "vamb", "bracers"};
    private static String[] SHIELDS = {"kiteshield", "Tome of frost", "Chicken", "sq shield", "Toktz-ket", "books", "book", "kiteshield (t)", "kiteshield (g)", "kiteshield(h)", "defender", "shield"};
    private static String[] AMULETS = {"amulet", "necklace", "Amulet of", "scarf"};
    private static String[] ARROWS = {"shot", "arrow", "arrows", "arrow(p)", "arrow(+)", "arrow(s)", "bolt", "Bolt rack", "Opal bolts", "Dragon bolts", "bolts (e)", "bolts"};
    private static String[] RINGS = {"ring", "Ring", "(i)", "(I)"};
    private static String[] BODY = {"armour", "apron", "hauberk", "platebody", "chainbody", "robetop", "leathertop", "platemail", "top", "brassard", "Robe top", "body", "platebody (t)", "platebody (g)", "body(g)", "body_(g)", "chestplate", "torso", "shirt"};
    private static String[] LEGS = {"druidic robe", "Shorts", "knight robe", "leggings", "Leggings", "cuisse", "pants", "platelegs", "plateskirt", "skirt", "bottoms", "chaps", "platelegs (t)", "platelegs (g)", "bottom", "skirt", "skirt (g)", "skirt (t)", "chaps (g)", "chaps (t)", "tassets", "legs", "trousers", "robe bottom"};
    private static String[] WEAPONS = {"chaotic", "Sled", "Meat tenderiser", "Flagstaff", "flag", "(broken)", "Stone of power", "stone of power", "Basket of eggs", "machete", "Red topaz machete", "Gnomecopter", "Toy kite", "toy kite", "gnomecopter", "Rapier", "Crate", "crate", "Fox", "chicken", "scythe", "Scythe", "dagger (p++)", "Dragon dagger (p++)", "rapier", "dagger", "hatchet", "bow", "Hand cannon", "Inferno adze", "Silverlight", "Darklight", "wand", "Statius's warhammer", "anchor", "spear", "Vesta's longsword.", "scimitar", "longsword", "sword", "longbow", "shortbow", "dagger", "mace", "halberd", "spear",
            "Abyssal whip", "axe", "flail", "crossbow", "Torags hammers", "dagger (p)", "dagger (p+)", "dagger(p++)", "spear(p)", "spear(+)",
            "spear(s)", "spear(kp)", "maul", "dart", "dart(p)", "javelin", "javelin(p)", "knife", "knife(p)", "Longbow", "Shortbow",
            "Crossbow", "Toktz-xil", "Toktz-mej", "Tzhaar-ket", "staff", "Staff", "godsword", "c'bow", "Crystal bow", "Dark bow", "claws", "warhammer", "adze", "hand"};
    private static String[] FULL_BODY = {"armour", "hauberk", "top", "shirt", "platebody", "Ahrims robetop", "Karils leathertop", "brassard", "Robe top", "robetop", "platebody (t)", "platebody (g)", "chestplate", "torso", "Morrigan's", "leather body", "chainbody", "Zuriel's", "robe top"};
    private static String[] FULL_HAT = {"sallet", "med helm", "coif", "Dharoks helm", "hood", "Initiate helm", "Slayer helmet", "Coif", "Helm of neitiznot"};
    private static String[] FULL_MASK = {"sallet", "full helm", "mask", "Veracs helm", "Guthans helm", "Torags helm", "Karils coif", "full helm (t)", "full helm (g)", "mask"};

    public static int getItemType(int wearId) {
        try {
            String weapon = ItemDefinitions.forID(wearId).name;
            if (wearId == 6317) {
                return 3;
            }
            if (weapon.contains("hat") && !weapon.contains("hatchet")) {
                return 0;
            }
            for (String CAPE : CAPES) {
                if (weapon.contains(CAPE))
                    return 1;
            }
            for (String HAT : HATS) {
                if (weapon.contains(HAT))
                    return 0;
            }
            for (String BOOT : BOOTS) {
                if (weapon.endsWith(BOOT) || weapon.startsWith(BOOT))
                    return 10;
            }
            for (String GLOVE : GLOVES) {
                if (weapon.endsWith(GLOVE) || weapon.startsWith(GLOVE))
                    return 9;
            }
            for (String SHIELD : SHIELDS) {
                if (weapon.contains(SHIELD))
                    return 5;
            }
            for (String AMULET : AMULETS) {
                if (weapon.endsWith(AMULET) || weapon.startsWith(AMULET))
                    return 2;
            }
            for (String ARROW : ARROWS) {
                if (weapon.endsWith(ARROW) || weapon.startsWith(ARROW))
                    return 13;
            }
            for (String RING : RINGS) {
                if (weapon.endsWith(RING) || weapon.startsWith(RING))
                    return 12;
            }
            for (String aBODY : BODY) {
                if (weapon.contains(aBODY))
                    return 4;
            }
            if (wearId == 6107) {
                return 4;
            }
            if (wearId == 6108) {
                return 7;
            }
            for (String LEG : LEGS) {
                if (weapon.contains(LEG))
                    return 7;
            }
            for (String WEAPON : WEAPONS) {
                if (weapon.endsWith(WEAPON) || weapon.startsWith(WEAPON))
                    return 3;
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    static boolean isFullBody(ItemDefinitions def) {
        String weapon = def.name;
        if (def.getId() == 6107) {
            return true;
        }
        for (String aFULL_BODY : FULL_BODY) {
            if (weapon.contains(aFULL_BODY)) {
                return true;
            }
        }
        return false;
    }


    static boolean isFullHat(ItemDefinitions def) {
        String weapon = def.name;
        for (String aFULL_HAT : FULL_HAT) {
            if (weapon.endsWith(aFULL_HAT)) {
                return true;
            }
        }
        return false;
    }

    static boolean isFullMask(ItemDefinitions def) {
        String weapon = def.name;
        for (String aFULL_MASK : FULL_MASK) {
            if (weapon.endsWith(aFULL_MASK)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTwoHanded(ItemDefinitions def) {
        String wepEquiped = def.name;
        int itemId = def.getId();
        return itemId == 4212 || itemId == 4214 || wepEquiped.endsWith("claws") || wepEquiped.endsWith("Scythe") ||
                wepEquiped.endsWith("scythe") || wepEquiped.endsWith("cannon") || wepEquiped.endsWith("maul") ||
                wepEquiped.endsWith("greataxe") || wepEquiped.endsWith("anchor") || wepEquiped.endsWith("2h sword") ||
                wepEquiped.endsWith("longbow") || wepEquiped.equals("Seercull") || wepEquiped.endsWith("shortbow") ||
                wepEquiped.endsWith("Longbow") || wepEquiped.endsWith("Shortbow") || wepEquiped.endsWith("bow full") ||
                wepEquiped.equals("Dark bow") || wepEquiped.endsWith("halberd") || wepEquiped.equals("Granite maul") ||
                wepEquiped.equals("Vesta's spear") || wepEquiped.equals("spear") || wepEquiped.equals("Karils crossbow") ||
                wepEquiped.equals("Torags hammers") || wepEquiped.equals("Veracs flail") || wepEquiped.equals("Dharoks greataxe") ||
                wepEquiped.equals("Meat tenderiser") || wepEquiped.equals("Guthans warspear") || wepEquiped.equals("Tzhaar-ket-om") ||
                wepEquiped.endsWith("godsword") || wepEquiped.equals("Saradomin sword");
    }

    int getRenderAnim() { //TODO cache sided
        if (get(3) == null)
            return 1426;
        int renderEmote = get(3).getDefinition().renderEmote;
        if (renderEmote != 0)
            return renderEmote;
        return 1426;
    }

}
