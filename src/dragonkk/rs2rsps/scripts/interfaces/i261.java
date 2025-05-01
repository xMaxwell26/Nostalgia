package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.AdminCP;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i261 extends interfaceScript {

    private boolean chatEffects;

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        switch (buttonId) {
            case 3:
                p.getFrames().sendConfig(173, p.getWalk().isRunToggled() ? 0 : 1);
                p.getWalk().setRunToggled(!p.getWalk().isRunToggled());
                break;
            case 4: //Chat effects
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                if (!isChatEffectsEnabled()) {
                    setChatEffectsEnabled(true);
                    p.getFrames().sendConfig(171, 0);
                } else {
                    setChatEffectsEnabled(false);
                    p.getFrames().sendConfig(171, 1);
                }
                break;
            case 5: //Split chat
                p.getFrames().sendInventoryInterface(982);
                        /*if(!isPrivateChatSplit()) {
						setPrivateChatSplit(true);
						p.getFrames().sendConfig(287, 1);
						} else {
						setPrivateChatSplit(false);
						p.getFrames().sendConfig(287, 0);
						}*/
                break;
            case 6: //Mouse Button config
                if (!isMouseTwoButtons()) {
                    setMouseTwoButtons(true);
                    p.getFrames().sendConfig(170, 0);
                } else {
                    setMouseTwoButtons(false);
                    p.getFrames().sendConfig(170, 1);
                }
                break;
            case 7: //Accept aid config
                if (!isAcceptAidEnabled()) {
                    setAcceptAidEnabled(true);
                    p.getFrames().sendConfig(427, 1);
                } else {
                    setAcceptAidEnabled(false);
                    p.getFrames().sendConfig(427, 0);
                }
                break;
            case 8: //House Building Options
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getFrames().sendChatMessage(0, "You are not in a player owned house.");
                //p.getFrames().sendInventoryInterface(398);
                break;
            case 16:
                if (p.getRights() < 2) {
                    p.getFrames().sendChatMessage(0, "You must be an admin to open the AdminCP.");
                    p.getFrames().sendChatMessage(0, "To change your graphcis option, please logout and click the tools icon.");
                    return;
                }
                AdminCP.open(p);
                p.getFrames().sendChatMessage(0, "Admin Control Panel Opened.");
                break;
            case 18:
                if (p.curseDelay > 0) {
                    p.getFrames().sendChatMessage(0, "You are cursed, please wait 20 seconds and try again.");
                    return;
                }
                p.getFrames().sendInterface(743);
                break;
            default:
                System.out.println("buttonId: " + buttonId);
                break;
        }
    }

    private boolean chat = true, split = true, mouse = true, aid = false;
    private boolean autoRetaliate = true;
    private transient Player player;

    public void setPlayer(Player player) {
        try {
            this.player = player;
        } catch (Exception e) {
        }
    }

    public void refresh() {
        try {
            player.getFrames().sendConfig(171, !chat ? 1 : 0);
            player.getFrames().sendConfig(287, !split ? 1 : 0);
            player.getFrames().sendConfig(170, !mouse ? 1 : 0);
            player.getFrames().sendConfig(427, aid ? 1 : 0);
            player.getFrames().sendConfig(172, !autoRetaliate ? 1 : 0);
        } catch (Exception e) {
        }
    }

    public void setDefaultSettings() {
        try {
            chat = true;
            split = true;
            mouse = true;
            aid = false;
        } catch (Exception e) {
        }
    }

    public boolean isMouseTwoButtons() {
        return mouse;
    }

    public boolean isChatEffectsEnabled() {
        return chat;
    }

    public boolean isPrivateChatSplit() {
        return split;
    }

    public boolean isAcceptAidEnabled() {
        return aid;
    }

    public void setMouseTwoButtons(boolean mouse) {
        try {
            this.mouse = mouse;
        } catch (Exception e) {
        }
    }

    public void setChatEffectsEnabled(boolean chat) {
        try {
            this.chat = chat;
        } catch (Exception e) {
        }
    }

    public void setPrivateChatSplit(boolean split) {
        try {
            this.split = split;
        } catch (Exception e) {
        }
    }

    public void setAcceptAidEnabled(boolean aid) {
        try {
            this.aid = aid;
        } catch (Exception e) {
        }
    }

    public boolean isAutoRetaliate() {
        return this.autoRetaliate;
    }

    public void setAutoRetaliate(boolean retaliate) {
        try {
            this.autoRetaliate = retaliate;
            refresh();
        } catch (Exception e) {
        }
    }

    public boolean isChatEffects() {
        return chatEffects;
    }

    public static enum Size {Fixed, VariableByte, VariableShort}

    ;
}
