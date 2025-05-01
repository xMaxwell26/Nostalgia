package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i320 extends interfaceScript {

    @Override
    public void actionButton(final Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        try {
            switch (buttonId) {
        /*case 200:
		p.getFrames().requestIntegerInput(8, "(Attack) Set your level:");
		break;
		case 28:
		p.getFrames().requestIntegerInput(9, "(Defence) Set your level:");
		break;
		case 11:
		p.getFrames().requestIntegerInput(10, "(Strength) Set your level:");
		break;
		case 193:
		p.getFrames().requestIntegerInput(11, "(Hitpoints) Set your level:");
		break;
		case 52:
		p.getFrames().requestIntegerInput(12, "(Ranged) Set your level:");
		break;
		case 76:
		p.getFrames().requestIntegerInput(13, "(Prayer) Set your level:");
		break;
		case 93:
		p.getFrames().requestIntegerInput(14, "(Magic) Set your level:");
		break;
		case 150:
		p.getFrames().requestIntegerInput(15, "(Summoning) Set your level:");
		break;*/
                case 158:
                    p.getMask().setLastChatMessage(new ChatMessage(0, 0, "My Dungeoneering level is " + p.getSkills().getLevel(24) + "."));
                    p.getMask().setChatUpdate(true);
                    p.getFrames().sendChatMessage(0, "Your Dungeoneering level is " + p.getSkills().getLevel(24) + ".");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}