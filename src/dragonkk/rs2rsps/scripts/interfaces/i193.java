package dragonkk.rs2rsps.scripts.interfaces;


import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i193 extends interfaceScript {
    @Override
    public void actionButton(final Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (p.isDead()) {
            return;
        }
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        if (p.teleblockDelay > 0) {
            p.getFrames().sendChatMessage(0, "You cannot tele while teleblocked.");
            return;
        }
        switch (buttonId) {
            case 21:// Ice blitz
                AutoCastOption(p, 85, 82);
                break;
            case 25://Blood blitz
                AutoCastOption(p, 83, 80);
                break;
            case 27:// Blood barrage
                AutoCastOption(p, 91, 92);
                break;
            case 23:// Ice barrage
                AutoCastOption(p, 93, 94);
                break;


            case 40: //Paddewwa teleport - goes to edge
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.WarningTeleport = 0;
                p.getFrames().sendInterface(574);
                p.getFrames().sendString("Teleport to Edgeville (Unsafe Multi)", 574, 17);
                p.getFrames().sendString("Stay here", 574, 18);
                break;
            case 41: //Senntisten teleport - goes to south digsite
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                        p.getMask().getRegion().teleport(3322, 3337, 0, 0);
                    }
                }, 1801);
                break;
            case 42: //Kharyrll teleport - goes to canifis
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                        p.getMask().getRegion().teleport(3491, 3471, 0, 0);
                    }
                }, 1801);
                break;
            case 43: //Lasser teleport - goes to white mountion
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                        p.getMask().getRegion().teleport(3006, 3480, 0, 0);
                    }
                }, 1801);
                break;
            case 44: //Dareeyak teleport - goes to wild
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.WildTeleport = 0;
                p.getFrames().sendInterface(382);
                break;
            case 45: //Carrallangar teleport - goes to bounty hunter - wild
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.WildTeleport = 1;
                p.getFrames().sendInterface(382);
                break;
            case 46: //Annakarl teleport
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.WildTeleport = 2;
                p.getFrames().sendInterface(382);
                break;
            case 47: //Ghorrock teleport - goes to west wilderness grave yard
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.WildTeleport = 3;
                p.getFrames().sendInterface(382);
                break;
            case 48: //home
                if (!p.getCombat().isSafe(p) && !p.getCombat().isSafe(p) && p.getCombat().dangerousPVP(p) && !p.getCombat().inWild(p)) {
                    p.getFrames().sendChatMessage(0, "You can only use tele tabs here.");
                    return;
                }
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getCombatDefinitions().doEmote(8939, 1681, 1800);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getCombatDefinitions().doEmote(8941, 1681, 2400);
                        p.getMask().getRegion().teleport(3186, 3440, 0, 0);
                    }
                }, 1801);
                break;
        }
    }

    public void AutoCastOption(Player p, int config, int magicLevel) {
        if (p.getSkills().getLevel(6) < magicLevel) {
            p.getFrames().sendChatMessage(0, "You need " + magicLevel + " magic level to cast this spell.");
            return;
        }
        if (!p.AutoCast) {
            p.getFrames().sendConfig(109, config);
            //p.AutoCastSpell = config;
            //p.AutoCast = true;
            System.out.println("Autocast on.");
        } else {
            p.getFrames().sendConfig(109, 0);
            //p.AutoCastSpell = 0;
            //p.AutoCast = false;
            System.out.println("Autocast off.");
        }
    }
}