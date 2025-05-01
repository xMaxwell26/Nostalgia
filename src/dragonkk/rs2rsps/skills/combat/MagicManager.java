package dragonkk.rs2rsps.skills.combat;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.spells.*;
import dragonkk.rs2rsps.util.Logger;

import java.util.HashMap;
import java.util.Map;

//import dragonkk.rs2rsps.skills.combat.spells.MiasmicBarrage;

public class MagicManager {

    private static final Map<String, MagicInterface> SPELLS = new HashMap<String, MagicInterface>();

    public MagicManager() {
        SPELLS.put("IceBlitz", new IceBlitz());
        SPELLS.put("Entangle", new Entangle());
        SPELLS.put("TeleBlock", new TeleBlock());
        SPELLS.put("IceBarrage", new IceBarrage());
        SPELLS.put("BloodBlitz", new BloodBlitz());
        //SPELLS.put("MiasmicBarrage", new MiasmicBarrage());
        SPELLS.put("BloodBarrage", new BloodBarrage());
        Logger.log("MagicManager", "Sucessfully loaded: " + SPELLS.size() + ", magic spells.");
    }

    public static void executeSpell(Player p, Player opp, String Spell) {
        MagicInterface spell = SPELLS.get(Spell);
        spell.execute(p, opp);
    }

}

