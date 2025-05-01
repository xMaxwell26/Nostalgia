package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.xml.XMLHandler;

import java.io.IOException;

/**
 * @author 'Mystic Flow <Steven@rune-server.org> (Converted to dragonkk's source by jet kai)
 */
public class ClanSave implements Runnable {

    @Override
    public void run() {
        GameLogicTaskManager.schedule(new GameLogicTask() {
            public void run() {
                try {
                    XMLHandler.toXML("data/xml/clans.xml", World.getClanManager().getClans());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 0);
    }

}
