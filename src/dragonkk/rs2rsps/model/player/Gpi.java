/*

Again, no retards.

*/

package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.update.GameLogic;
import dragonkk.rs2rsps.util.Misc;

public class Gpi {

    private Player player;
    private GpiPlayer[] localPlayers;
    private short inScreenCount;

    Gpi(Player player) {
        this.player = player;
        localPlayers = new GpiPlayer[2048];
    }

    private short needUpdateCount;

    void addPlayer(Player p) {
        localPlayers[p.getIndex()].player = p;
        String name = Misc.formatPlayerNameForDisplay(p.getUsername());
        if (player.getFriends().contains(name))
            player.UpdateFriendStatus(name, (short) 1, true);
    }

    public void loginData(OutStream stream) {
            stream.initBitAccess();
            short myplayerindex = (short) player.getIndex();
            GpiPlayer myplayer = localPlayers[myplayerindex] = new GpiPlayer();
            myplayer.inScreen = true;
            myplayer.player = player;
            inScreenCount++;
            stream.writeBits(30, player.getLocation().getY() | ((player.getLocation().getZ() << 28) | (player.getLocation().getX() << 14)));
            for (short index = 1; index < 2048; index++) {
                if (index == myplayerindex)
                    continue;
                GpiPlayer gp2 = localPlayers[index] = new GpiPlayer();
                Player p2 = World.getPlayers().get(index);
                if (p2 == null || !p2.isOnline()) { // null slot
                    stream.writeBits(18, 0);
                    continue;
                }
                gp2.player = p2;
                if (!player.getLocation().withinDistance(p2.getLocation())) {
                    stream.writeBits(18, 0);
                    continue;
                }
                stream.writeBits(18, gp2.player.getLocation().get18BitsHash());
            }
            stream.finishBitAccess();
    }


    public void sendUpdate() {
            player.getMusicmanager().playRegionMusic();
            if (player.getMask().getRegion().isDidMapRegionChange())
                if (player.getMask().getRegion().isUsingStaticRegion())
                    player.getFrames().sendStaticMapRegion();
                else
                    player.getFrames().sendMapRegion(true);
            player.getConnection().write(createPacket());
            needUpdateCount = 0;
    }

    private OutStream createPacket() {
            OutStream packet = new OutStream();
            OutStream updateBlock = new OutStream();
            packet.writePacketVarShort(53);
            processGlobalPlayerInform(packet);
            processUpdateBlock(updateBlock);
            packet.writeBytes(updateBlock.buffer(), 0, updateBlock.offset());
            packet.endPacketVarShort();
            return packet;
    }

    private void processGlobalPlayerInform(OutStream packet) {
            processInScreenPlayers(packet);
            processOutScreenPlayers(packet);
            resetInform();
    }

    private void resetInform() {
        inScreenCount = 0;
        for (short index = 1; index < 2048; index++) {
            GpiPlayer gp2 = localPlayers[index];
            if (gp2.status == 1)
                gp2.status = 0;
            if (gp2.inScreen)
                inScreenCount++;
        }
    }

    private void processOutScreenPlayers(OutStream packet) {
            short skipCost = 0;
            short addedPlayerCount = 0;
            packet.initBitAccess();
            for (short index = 1; index < 2048; index++) {
                GpiPlayer gp2 = localPlayers[index];
                if (gp2.inScreen || gp2.status == 1)
                    continue;
                if (skipCost > 0) {
                    skipCost--;
                    continue;
                }
                byte type = addedPlayerCount >= 50 ? -1 : outScreenUpdateType(gp2);
                packet.writeBits(1, type == -1 ? 0 : 1);
                if (type == -1) {
                    skipCost = processSkip(false, index, addedPlayerCount);
                    skip(packet, skipCost);
                } else if (type == 0) {
                    addLocalPlayer(packet, gp2);
                    addedPlayerCount++;
                }
            }
            packet.finishBitAccess();
    }


    private void addLocalPlayer(OutStream packet, GpiPlayer gp2) {
            if (gp2.player == player)
                return;
            packet.writeBits(2, 0);
            packet.writeBits(1, 1);
            packet.writeBits(2, 3);
            packet.writeBits(18, gp2.player.getLocation().get18BitsHash());
            int coordX = gp2.player.getLocation().getX();
            int coordY = gp2.player.getLocation().getY();
            packet.writeBits(6, coordX - ((coordX / 64) * 64));
            packet.writeBits(6, coordY - ((coordY / 64) * 64));
            packet.writeBits(1, 1);
            needUpdateCount++;
            gp2.inScreen = true;
            gp2.status = 2;
    }

    private byte outScreenUpdateType(GpiPlayer gp2) {
        if (gp2.player != null && player.getLocation().withinDistance(gp2.player.getLocation()))
            return 0;
        return -1;
    }

    private void processInScreenPlayers(OutStream packet) {
            short skipCost = 0;
            short sentInCount = 0;
            packet.initBitAccess();
            for (short index = 1; index < 2048; index++) {
                GpiPlayer gp2 = localPlayers[index];
                if (!gp2.inScreen)
                    continue;
                if (sentInCount >= inScreenCount)
                    break;
                sentInCount++;
                if (skipCost > 0) {
                    skipCost--;
                    continue;
                }
                byte type = inScreenUpdateType(gp2);
                packet.writeBits(1, type == -1 ? 0 : 1);
                if (type == -1) {
                    skipCost = processSkip(true, index, (short) 0);
                    skip(packet, skipCost);
                } else if (type == 0)
                    removeLocalPlayer(packet, gp2);
                else
                    sendLocalPlayerUpdate(packet, gp2);
            }
            packet.finishBitAccess();
    }

    private void sendLocalPlayerUpdate(OutStream packet, GpiPlayer gp2) {
            if (gp2.player.getMask().getRegion().isDidTeleport()) {
                sendLocalPlayerTeleport(packet, gp2);
                return;
            }
            byte walkDir = gp2.player.getWalk().getWalkDir();
            byte runDir = gp2.player.getWalk().getRunDir();
            sendLocalPlayerStatus(packet, walkDir > -1 ? 1 : runDir > -1 ? 2 : 0, true);
            if (walkDir < 0 && runDir < 0)
                return;
            packet.writeBits(walkDir > -1 ? 3 : 4, walkDir > -1 ? walkDir : runDir);
    }

    private void sendLocalPlayerTeleport(OutStream packet, GpiPlayer gp2) {
            sendLocalPlayerStatus(packet, 3, true);
            packet.writeBits(1, 1);
            packet.writeBits(30, gp2.player.getLocation().get30BitsHash());
    }

    @SuppressWarnings("unused")
    private boolean isLocalPlayerHashUpdated(GpiPlayer gp2) {
        return gp2.player.getLocation().get18BitsHash() == gp2.player.getMask().getRegion().getLastLocation().get18BitsHash();
    }

    private void sendLocalPlayerStatus(OutStream packet, int type, boolean status) {
            if (status)
                needUpdateCount++;
            packet.writeBits(1, status ? 1 : 0);
            packet.writeBits(2, type);
    }


    private void removeLocalPlayer(OutStream packet, GpiPlayer gp2) {
            if (gp2.player == player)
                return;
            sendLocalPlayerStatus(packet, 0, false);
            gp2.status = 1;
            gp2.inScreen = false;
            packet.writeBits(1, 0);
            if (!gp2.player.isOnline())
                gp2.player = null;
    }

    private short processSkip(boolean inScreen, short index, short addedPlayerCount) {
        short skipCost = 0;
            for (short index1 = (short) (index + 1); index1 < 2048; index1++) {
                GpiPlayer gp3 = localPlayers[index1];
                if (inScreen) {
                    if (!gp3.inScreen)
                        continue;
                    if (inScreenUpdateType(gp3) > -1)
                        break;
                } else {
                    if (gp3.inScreen || gp3.status == 1)
                        continue;
                    if (addedPlayerCount < 50 && outScreenUpdateType(gp3) > -1)
                        break;
                }
                skipCost++;
            }
        return skipCost;
    }

    private byte inScreenUpdateType(GpiPlayer gp2) {
        if (!gp2.player.isOnline()
                || !player.getLocation().withinDistance(
                gp2.player.getLocation()))
            return 0;
        if (GameLogic.getPlayerUpdates()[gp2.player.getIndex()])
            return 1;
        return -1;
    }

    private void skip(OutStream packet, int amount) {
            packet.writeBits(2, amount == 0 ? 0 : amount > 255 ? 3
                    : (amount > 31 ? 2 : 1));
            if (amount > 0)
                packet.writeBits(amount > 255 ? 11 : (amount > 31 ? 8 : 5),
                        amount);
    }


    private void processUpdateBlock(OutStream updateBlock) {
            short updateCount = 0;
            for (int index = 1; index < 2048; index++) {
                GpiPlayer gp2 = localPlayers[index];
                if (!gp2.inScreen || gp2.player == null || gp2.status == 1 || gp2.status == 2 || !GameLogic.getPlayerUpdates()[index])
                    continue;
                createPlayerUpdateBlock(updateBlock, gp2.player, false);
                updateCount++;
                if (updateCount >= needUpdateCount)
                    break;
            }
            if (updateCount < needUpdateCount) for (int index = 1; index < 2048; index++) {
                GpiPlayer gp2 = localPlayers[index];
                if (!gp2.inScreen || gp2.player == null || gp2.status != 2)
                    continue;
                createPlayerUpdateBlock(updateBlock, gp2.player, true);
                gp2.status = 0;
                updateCount++;
                if (updateCount >= needUpdateCount)
                    break;
            }
            if (player.getMask().isChatUpdate()) {
                for (int index = 1; index < 2048; index++) {
                    GpiPlayer gp2 = localPlayers[index];
                    if (!gp2.inScreen || gp2.player == null)
                        continue;
                    gp2.player.getFrames().sendPublicChatMessage(
                            player.getIndex(), player.getRights(),
                            player.getMask().getLastChatMessage());
                }
                player.getMask().setChatUpdate(false);
            }
    }

    private static void createPlayerUpdateBlock(OutStream stream, Player p, boolean forceAppearence) {
            int maskData = 0;
            if (p.getMask().isHit2Update()) maskData |= 0x10000;
            if (p.getMask().isAnimationUpdate()) maskData |= 0x80;
            if (p.getMask().isGraphicUpdate()) maskData |= 0x1000;
            if (p.getWalk().getWalkDir() != -1 || p.getWalk().getRunDir() != -1) maskData |= 0x10;
            if (p.getMask().isTurnToUpdate()) maskData |= 0x2;
            if (p.getMask().isApperanceUpdate() || forceAppearence) maskData |= 0x20;
            if (p.getMask().isHealUpdate()) maskData |= 0x100;
            if (p.getWalk().isDidTele()) maskData |= 0x2000;
            if (p.getMask().isHitUpdate()) maskData |= 0x40;
            if (p.getMask().isTurnToUpdate1()) maskData |= 0x4;
            if (p.getMask().isGraphic2Update()) maskData |= 0x8;
            if (maskData > 128)
                maskData |= 0x1;
            if (maskData > 32768)
                maskData |= 0x800;
            stream.writeByte(maskData);
            if (maskData > 128)
                stream.writeByte(maskData >> 8);
            if (maskData > 32768)
                stream.writeByte(maskData >> 16);
            if (p.getMask().isHit2Update()) applyHit2Mask(p, stream);
            if (p.getMask().isAnimationUpdate()) applyAnimationMask(p, stream);
            if (p.getMask().isGraphicUpdate()) applyGraphicMask(p, stream);
            if (p.getWalk().getWalkDir() != -1 || p.getWalk().getRunDir() != -1) applyMovementMask(p, stream);
            if (p.getMask().isTurnToUpdate()) applyTurnToMask(p, stream);
            if (p.getMask().isApperanceUpdate() || forceAppearence) applyAppearanceMask(p, stream);
            if (p.getMask().isHealUpdate()) applyHealMask(p, stream);
            if (p.getWalk().isDidTele()) applyTeleTypeMask(stream);
            if (p.getMask().isHitUpdate()) applyHitMask(p, stream);
            if (p.getMask().isTurnToUpdate1()) applyTurnTo1Mask(p, stream);
            if (p.getMask().isGraphic2Update()) applyGraphic2Mask(p, stream);
    }

    private static void applyTeleTypeMask(OutStream outStream) {
        outStream.writeByteC(127);
    }

    private static void applyTurnToMask(Player p, OutStream outStream) {
        outStream.writeShort128(p.getMask().getTurnToIndex());
    }

    private static void applyTurnTo1Mask(Player p, OutStream outStream) {
        outStream.writeShortLE(Misc.getFacingDirection(p.getLocation().getX(), p.getLocation().getY(), p.getMask().getTurnToLocation().getX(), p.getMask().getTurnToLocation().getY()));
    }

    private static void applyHitMask(Player p, OutStream outStream) {
        outStream.writeSmart(p.getHits().getHitDamage1());
        outStream.writeByte(p.getHits().getHitType1());
        int Amthp = p.getSkills().getHitPoints();
        int Maxhp = p.getSkills().getLevelForXp(3) * 10;
        if (Amthp > Maxhp)
            Amthp = Maxhp;
        outStream.writeByte(Amthp * 255 / Maxhp);
    }

    private static void applyHit2Mask(Player p, OutStream outStream) {
        outStream.writeSmart(p.getHits().getHitDamage2());
        outStream.write128Byte(p.getHits().getHitType2());
    }

    private static void applyHealMask(Player p, OutStream outStream) {
        outStream.writeShort(p.getMask().getLastHeal().getHealDelay());
        outStream.write128Byte(p.getMask().getLastHeal().getBarDelay());
        outStream.write128Byte(p.getMask().getLastHeal().getHealSpeed());
    }

    private static void applyAnimationMask(Player p, OutStream outStream) {
        outStream.writeShort128(p.getMask().getLastAnimation().getId());
        outStream.writeByte128(p.getMask().getLastAnimation().getDelay());
    }

    private static void applyGraphicMask(Player p, OutStream outStream) {
        outStream.writeShort128(p.getMask().getLastGraphics().getId());
        outStream.writeInt(p.getMask().getLastGraphics().getDelay());
        outStream.writeByte(0);
    }

    private static void applyGraphic2Mask(Player p, OutStream outStream) {
        outStream.writeShortLE128(p.getMask().getLastGraphics2().getId());
        outStream.writeIntV1(100 << 16);
        outStream.write128Byte(0);
    }

    private static void applyMovementMask(Player p, OutStream outStream) {
        outStream.writeByteC(p.getWalk().getWalkDir() != -1 ? 1 : 2);
    }

    private static void applyAppearanceMask(Player p, OutStream outStream) {
        OutStream playerUpdate = new OutStream();
        playerUpdate.writeByte(p.getAppearence().getGender() & 0xFF);
        if (p.Skull && p.Skullon) {
            playerUpdate.writeByte(-1);
            playerUpdate.writeByte(1);
        } else if (p.Kills2 >= 2500 && p.Skullon && !p.Skull) {
            playerUpdate.writeByte(-1);
            playerUpdate.writeByte(1);
        } else if (p.Kills2 >= 1337 || p.superextremeDonator && p.Skullon && !p.Skull) {
            playerUpdate.writeByte(-1);
            playerUpdate.writeByte(2);
        } else if (p.Skull && p.Skullon && p.Kills2 <= 1337 && !p.Skull) {
            playerUpdate.writeByte(-1);
            playerUpdate.writeByte(1);
        } else if (p.Kills2 >= 500 && p.Skullon && !p.Skull) {
            playerUpdate.writeByte(-1);
            playerUpdate.writeByte(3);
        } else if (p.Kills2 >= 200 && p.Skullon && !p.Skull) {
            playerUpdate.writeByte(-1);
            playerUpdate.writeByte(4);
        } else if (p.Kills2 >= 100 && p.Skullon && !p.Skull) {
            playerUpdate.writeByte(-1);
            playerUpdate.writeByte(5);
        } else if (p.Kills2 >= 100 && p.Skullon && !p.Skull) {
            playerUpdate.writeByte(-1);
            playerUpdate.writeByte(6);
        } else {
            playerUpdate.writeByte(0);
            playerUpdate.writeByte(p.getAppearence().getSkullIcon());//pk icon
        }
        playerUpdate.writeByte(p.getPrayer().getHeadIcon());
        if (p.getAppearence().getNpcType() == -1) {
            for (int i = 0; i < 4; i++)
                if (p.getEquipment().get(i) == null)
                    playerUpdate.writeByte(0);
                else
                    playerUpdate.writeShort(32768 + p.getEquipment().get(i).getDefinition().equipId);
            if (p.getEquipment().get(Equipment.SLOT_CHEST) != null)
                playerUpdate.writeShort(32768 + p.getEquipment().get(
                        Equipment.SLOT_CHEST).getDefinition().equipId);
            else playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[2]);
            if (p.getEquipment().get(Equipment.SLOT_SHIELD) != null)
                playerUpdate.writeShort(32768 + p.getEquipment().get(Equipment.SLOT_SHIELD).getDefinition().equipId);
            else playerUpdate.writeByte((byte) 0);
            Item chest = p.getEquipment().get(Equipment.SLOT_CHEST);
            if (chest != null) if (!Equipment.isFullBody(chest.getDefinition()))
                playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[3]);
            else playerUpdate.writeByte((byte) 0);
            else playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[3]);
            if (p.getEquipment().get(Equipment.SLOT_LEGS) != null)
                playerUpdate.writeShort(32768 + p.getEquipment().get(Equipment.SLOT_LEGS).getDefinition().equipId);
            else playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[5]);
            Item hat = p.getEquipment().get(Equipment.SLOT_HAT);
            if (hat != null)
                if (!Equipment.isFullHat(hat.getDefinition()) && !Equipment.isFullMask(hat.getDefinition()))
                    playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[0]);
                else playerUpdate.writeByte((byte) 0);
            else playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[0]);
            if (p.getEquipment().get(Equipment.SLOT_HANDS) != null)
                playerUpdate.writeShort(32768 + p.getEquipment().get(Equipment.SLOT_HANDS).getDefinition().equipId);
            else playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[4]);
            if (p.getEquipment().get(Equipment.SLOT_FEET) != null)
                playerUpdate.writeShort(32768 + p.getEquipment().get(Equipment.SLOT_FEET).getDefinition().equipId);
            else playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[6]);
            if (hat != null) if (!Equipment.isFullMask(hat.getDefinition()))
                playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[1]);
            else playerUpdate.writeByte((byte) 0);
            else playerUpdate.writeShort(0x100 + p.getAppearence().getLook()[1]);
        } else {
            playerUpdate.writeShort(-1);
            playerUpdate.writeShort(p.getAppearence().getNpcType());
            playerUpdate.writeByte(0);
        }
        for (int j = 0; j < 5; j++) playerUpdate.writeByte(p.getAppearence().getColour()[j]);
        if (p.superextremeDonator && !p.SolidGold && !p.trusted && !p.getUsername().equals("maxwell")) {
            switch (p.stanceEmote) {
                case 0:
                    playerUpdate.writeShort(p.getEquipment().getRenderAnim());
                    break;
                case 1:
                    playerUpdate.writeShort(182);
                    break;
                case 2:
                    playerUpdate.writeShort(208);
                    break;
            }
            playerUpdate.writeString(!p.addingFriend ? p.superextremeDonator ? "<col=00A1B3><shad=002896>" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()) : p.superextremeDonator ? "" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()));
            playerUpdate.writeShort(p.getSkills().getCombatLevel());
            playerUpdate.writeShort(0);
            playerUpdate.writeByte(0);
            outStream.write128Byte(playerUpdate.offset());
            outStream.addBytes128(playerUpdate.buffer(), 0, playerUpdate.offset());
        } else if (p.trusted && p.superextremeDonator && !p.SolidGold && !p.getUsername().equals("maxwell")) {
            playerUpdate.writeShort(p.getEquipment().getRenderAnim());
            playerUpdate.writeString(!p.addingFriend ? p.trusted ? "<col=FF450A><shad=FF7000>" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()) : p.trusted ? "" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()));
            playerUpdate.writeShort(p.getSkills().getCombatLevel());
            playerUpdate.writeShort(0);
            playerUpdate.writeByte(0);
            outStream.write128Byte(playerUpdate.offset());
            outStream.addBytes128(playerUpdate.buffer(), 0, playerUpdate.offset());
        } else if (p.trusted && !p.SolidGold && !p.getUsername().equals("maxwell")) {
            playerUpdate.writeShort(p.getEquipment().getRenderAnim());
            playerUpdate.writeString(!p.addingFriend ? p.trusted ? "<col=800080>" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()) : p.trusted ? "" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()));
            playerUpdate.writeShort(p.getSkills().getCombatLevel());
            playerUpdate.writeShort(0);
            playerUpdate.writeByte(0);
            outStream.write128Byte(playerUpdate.offset());
            outStream.addBytes128(playerUpdate.buffer(), 0, playerUpdate.offset());
        } else if (p.extremeDonator && !p.SolidGold && !p.superextremeDonator && !p.trusted && !p.getUsername().equals("maxwell")) {
            playerUpdate.writeShort(p.getEquipment().getRenderAnim());
            playerUpdate.writeString(!p.addingFriend ? p.extremeDonator ? "<col=088A08>" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()) : p.extremeDonator ? "" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()));
            playerUpdate.writeShort(p.getSkills().getCombatLevel());
            playerUpdate.writeShort(0);
            playerUpdate.writeByte(0);
            outStream.write128Byte(playerUpdate.offset());
            outStream.addBytes128(playerUpdate.buffer(), 0, playerUpdate.offset());
        } else if (p.getUsername().equals("maxwell")) {
            playerUpdate.writeShort(p.renderEmote);
            playerUpdate.writeString(!p.addingFriend ? "<shad=cc0ff><col=9900CC>" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()));
            playerUpdate.writeShort(p.levelset);
            playerUpdate.writeShort(p.skillset);
            playerUpdate.writeByte(0);
            outStream.write128Byte(playerUpdate.offset());
            outStream.addBytes128(playerUpdate.buffer(), 0, playerUpdate.offset());
        } else if (p.SolidGold) {
            playerUpdate.writeShort(p.getEquipment().getRenderAnim());
            playerUpdate.writeString(!p.addingFriend ? p.SolidGold ? "<col=FACC2E><shad=FACC2E>" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()) : p.SolidGold ? "" + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "" : Misc.formatPlayerNameForDisplay(p.getDisplayName()));
            playerUpdate.writeShort(p.SolidGold ? 150 : p.getSkills().getCombatLevel());
            playerUpdate.writeShort(p.SolidGold ? 150 : 0);
            playerUpdate.writeShort(0);
            playerUpdate.writeByte(0);
            outStream.write128Byte(playerUpdate.offset());
            outStream.addBytes128(playerUpdate.buffer(), 0, playerUpdate.offset());
        } else {
            playerUpdate.writeShort(p.getEquipment().getRenderAnim());
            playerUpdate.writeString(Misc.formatPlayerNameForDisplay(p.getDisplayName()));
            playerUpdate.writeShort(p.getSkills().getCombatLevel());
            playerUpdate.writeShort(0);
            playerUpdate.writeByte(0);
            outStream.write128Byte(playerUpdate.offset());
            outStream.addBytes128(playerUpdate.buffer(), 0, playerUpdate.offset());
        }
    }


    public class GpiPlayer {
        private Player player;
        private boolean inScreen;
        byte status;
    }
}
