package dragonkk.rs2rsps.util;

import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Equipment;
import dragonkk.rs2rsps.model.player.Player;

public class CombatManager {

    public static byte getSpeedForWeapon(int weaponId) {
        if (weaponId == -1)
            return 3;
        Item w = new Item(weaponId);
        if (w.getDefinition().name.contains("Chaotic maul"))
            return 6;
        if (w.getDefinition().name.contains("Dark bow"))
            return 8;
        if (w.getDefinition().name.contains("shortbow"))
            return 3;
        if (w.getDefinition().name.contains("Hand cannon"))
            return 7;
        if (w.getDefinition().name.contains("claws"))
            return 4;
        if (w.getDefinition().name.contains("longsword"))
            return 5;
        if (w.getDefinition().name.contains("whip"))
            return 4;
        if (w.getDefinition().name.contains("godsword"))
            return 6;
        if (w.getDefinition().name.contains("crossbow"))
            return 5;
        if (w.getDefinition().name.contains("Dharok"))
            return 6;
        if (w.getDefinition().name.contains("staff"))
            return 5;
        if (w.getDefinition().name.contains("Bow-sword"))
            return 3;
        return 4;
    }

    public static boolean wearingDharok(Player p) {
        return !(p.getEquipment().get(Equipment.SLOT_WEAPON) == null || !p.getEquipment().get(Equipment.SLOT_WEAPON).getDefinition().name.equals("Dharok's greataxe")) && !(p.getEquipment().get(Equipment.SLOT_HAT) == null || !p.getEquipment().get(Equipment.SLOT_HAT).getDefinition().name.equals("Dharok's helm")) && !(p.getEquipment().get(Equipment.SLOT_CHEST) == null || !p.getEquipment().get(Equipment.SLOT_CHEST).getDefinition().name.equals("Dharok's platebody")) && !(p.getEquipment().get(Equipment.SLOT_LEGS) == null || !p.getEquipment().get(Equipment.SLOT_LEGS).getDefinition().name.equals("Dharok's platelegs"));
    }

    public static boolean wearingVoid(Player p, int helm) {
        return !(p.getEquipment().get(Equipment.SLOT_WEAPON) == null || !p.getEquipment().get(Equipment.SLOT_WEAPON).getDefinition().name.contains("Void")) && !(p.getEquipment().get(Equipment.SLOT_HAT) == null || !(p.getEquipment().get(Equipment.SLOT_HAT).getDefinition().getId() == helm)) && !(p.getEquipment().get(Equipment.SLOT_CHEST) == null || !p.getEquipment().get(Equipment.SLOT_CHEST).getDefinition().name.contains("Void")) && !(p.getEquipment().get(Equipment.SLOT_LEGS) == null || !p.getEquipment().get(Equipment.SLOT_LEGS).getDefinition().name.contains("Void"));
    }

    public static byte distForWeap(int weaponId) {
        switch (weaponId) {
            default:
                return 0;
        }
    }

    public static double getSpecDamageDoublePercentage(int weaponId) {
        switch (weaponId) {
            case 11694:
                return 1.34375;
            case 11696:
                return 1.1825;
            case 3204:
            case 3101:
                return 1.1;
            case 3105:
                return 1.15;
            case 1434:
                return 1.45;
            case 11698:
            case 11700:
                return 1.075;
            case 13902:
            case 13904:
                return 1.25;
            case 13899:
            case 13901:
                return 1.20;
        }
        String weaponName = weaponId == -1 ? "" : new Item(weaponId).getDefinition().name;
        if (weaponName.contains("Dragon dagger"))
            return 1.1;

        return 1;
    }

    public static short getSpecAmt(int weaponId) {
        switch (weaponId) {
            case 4151:
            case 4153:
            case 11694:
            case 14484:
                return 50;
            default:
                return -1;
        }
    }

    public static short getDefenceEmote(Entity entity) {
        if (entity instanceof Player) {
            Player target = (Player) entity;
            final short weaponId = target.getEquipment().get(3) == null ? -1 : target.getEquipment().get(3).getId();
            final short shieldId = target.getEquipment().get(5) == null ? -1 : target.getEquipment().get(5).getId();
            if (shieldId == -1 && weaponId == -1)
                return 424;
            String weaponName = weaponId == -1 ? "" : new Item(weaponId).getDefinition().name;
            String shieldName = shieldId == -1 ? "" : new Item(shieldId).getDefinition().name;
            if (shieldId != -1 && shieldName.contains("defender"))
                return 4177;
            if (shieldId != -1 && shieldName.contains("shield") || shieldId == 6524)
                return 1156;
            if (weaponId != -1 && (weaponName.contains("godsword") || weaponName.contains("2h sword")))
                return 7050;
            if (weaponId != -1 && (weaponName.contains("Keris") || weaponName.contains("dagger")))
                return 403;
            if (weaponId != -1 && (weaponName.contains("Staff of light")))
                return 12806;
            if (weaponId != -1 && (weaponName.contains("Dharok") && (weaponName.contains("axe"))))
                return 12004;
            if (weaponId != -1 && weaponName.contains("maul") && !weaponName.contains("Granite"))
                return 1666;
            if (weaponId != -1 && weaponName.contains("staff"))
                return 12030;
            if (weaponId != -1 && weaponName.contains("Bow-sword"))
                return 12030;
            if (weaponId == 15486) {
                return 13038;
            }

        }
        return 424;
    }

    public static boolean isRangingWeapon(int weaponId) {
        if (weaponId == -1)
            return false;
        String weaponName = new Item(weaponId).getDefinition().name;
        return weaponName.contains("bow") || weaponName.contains("dart") || weaponName.contains("knife");
    }

}
