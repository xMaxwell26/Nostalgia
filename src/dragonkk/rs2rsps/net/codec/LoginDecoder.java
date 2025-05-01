package dragonkk.rs2rsps.net.codec;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.io.InStream;
import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Constants;
import dragonkk.rs2rsps.util.Logger;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.Serializer;
import org.jboss.netty.channel.ChannelFutureListener;

import java.io.File;
import java.util.GregorianCalendar;

//import dragonkk.rs2rsps.HostList;

public class LoginDecoder {

    private static File AccountFile(String name) {
        return new File("./data/savedgames/" + name + ".ser");
    }

    @SuppressWarnings("unused")
    public static void decode(ConnectionHandler p, InStream in) {
        String before = "" + p.getChannel().getRemoteAddress();
        String[] ipString = before.replaceAll("/", "").replaceAll(":", " ").split(" ");
        if (p.getConnectionStage() == Constants.LOGIN_START) {
            OutStream outstream = new OutStream();
            outstream.writeByte(0);
            outstream.writeLong(p.getSessionKey());
            p.write(outstream);
            p.setConnectionStage(Constants.LOGIN_CYPTION);
        } else if (p.getConnectionStage() == Constants.LOGIN_CYPTION) {
            if (3 > in.remaining()) return;
            int loginType = in.readUnsignedByte();
            if (loginType != 16 && loginType != 18) {
                p.setConnectionStage(Constants.DISCONNECT);
                return;
            }
            int loginPacketSize = in.readUnsignedShort();
            if (loginPacketSize > in.remaining()) return;
            int clientVersion = in.readInt();
            if (clientVersion != Constants.REVISION) {
                p.setConnectionStage(Constants.DISCONNECT);
                return;
            }
            int unknown0 = in.readUnsignedByte();
            int displayMode = in.readUnsignedByte();
            p.setDisplayMode(displayMode);
            int screenSizeX = in.readUnsignedShort();
            int screenSizeY = in.readUnsignedShort();
            int unknown3 = in.readUnsignedByte();
            InStream inStream1 = new InStream(24); // new dunno
            inStream1.addBytes(in.buffer(), in.offset(), 24);
            in.skip(24);
            String settings = in.readRS2String();
            int unknown4 = in.readInt();
            int size = in.readUnsignedByte();
            InStream inStream2 = new InStream(size); // options etc
            inStream2.addBytes(in.buffer(), in.offset(), size);
            in.skip(size);
            InStream inStream3 = new InStream(14); // new dunno
            inStream1.addBytes(in.buffer(), in.offset(), 14);
            in.skip(14);
            in.readShort(); // new dunno
            in.readLong(); // new dunno
            int[] idxSizes = new int[33];
            for (int index = 0; index < idxSizes.length; index++) idxSizes[index] = in.readInt();
            in.skip(6);
            if (in.readUnsignedByte() != 10) {
                p.setConnectionStage(Constants.DISCONNECT);
                return;
            }
            int sessionKey[] = new int[4];
            for (int i = 0; i < 4; i++) sessionKey[i] = in.readInt();
            long l = in.readLong();
            int hash = (int) (31 & l >> 16);
            if (hash != p.getNameHash()) {
                p.setConnectionStage(Constants.DISCONNECT);
                return;
            }
            String Username = Misc.formatPlayerNameForProtocol(Misc.longToString(l));
            String Password655 = in.readRS2String();
            for (int i = 0; i < 4; i++) sessionKey[i] += 50;
            byte OpCode = 0;
            p.nulled = false;
            File account = AccountFile(Username);
            String host = p.getChannel().getRemoteAddress().toString();
            host = host.substring(1, host.indexOf(':'));
            if (account == null || Username == null || Password655 == null) OpCode = Constants.INVALID_PASSWORD;
            else if (World.isOnList(Username)) OpCode = Constants.ALREADY_ONLINE;
            int returnCode = Constants.LOGIN_OK;
            String details[] = new String[]{Username, Password655};
            Object serializedJavaObject = null;
            serializedJavaObject = Serializer.LoadAccount(account);
            if (!account.exists())
                serializedJavaObject = new Player(Username, Password655, new GregorianCalendar(), new GregorianCalendar(), (short) 1, "", (byte) 1);
            else if (account.exists()) {
                serializedJavaObject = Serializer.LoadAccount(account);
                if (serializedJavaObject == null) {
                    Logger.log("Nulled account", "Account " + details[0] + " is nulled.");
                    if (Serializer.backupExists(Username)) {
                        Logger.log("Backup", "A backup of this player exists. Attempting to load");
                        try {
                            serializedJavaObject = Serializer.loadBackup(Username);
                            p.nulled = true;
                        } catch (Exception e) {
                            Logger.log("BackupService", "Restore from backup failed. An IO Error occoured while reading the file. Attempting to load 2nd backup!");
                            try {
                                serializedJavaObject = Serializer.loadBackup2(Username);
                                p.nulled = true;
                            } catch (Exception e1) {
                                Logger.log("BackupService", "Restore failed.");
                            }
                        }
                    } else if (serializedJavaObject == null) {
                        Logger.log("BackupService", "Restore from backup failed. The file was corrupted. Deleting and creating a new character!");
                        serializedJavaObject = new Player(Username, Password655, new GregorianCalendar(), new GregorianCalendar(), (short) 1, "", (byte) 1);
                        p.nulled = true;
                    } else {
                        Logger.log("BackupService", "A backup file did not exist for the nulled player. Deleting and creating a new character!");
                        serializedJavaObject = new Player(Username, Password655, new GregorianCalendar(), new GregorianCalendar(), (short) 1, "", (byte) 1);
                        p.nulled = true;
                    }

                }
            }
            Player loadedPlayer = (Player) serializedJavaObject;
            if (p.nulled) {
                OpCode = Constants.LOGIN_OK;
                System.out.println("Bypassing login messages...");
            }
            if (!p.nulled) {
                loadedPlayer = Serializer.LoadAccount(account);
                if (!account.exists()) OpCode = Constants.LOGIN_OK;
                else if (World.isOnList(Username)) OpCode = Constants.ALREADY_ONLINE;
                else if (World.BannedIpsContain(host)) {
                    System.out.println("IP: " + host + " tried to connect but was blocked.");
                    OpCode = Constants.IPED;
                } else if (World.isOnline(details[0])) OpCode = Constants.ALREADY_ONLINE;
                else if (!Password655.equals(loadedPlayer.getPassword())) OpCode = Constants.INVALID_PASSWORD;
                else if (loadedPlayer.isBanned()) OpCode = Constants.BANNED;
                else if (loadedPlayer.isLOCKED()) OpCode = Constants.LOCKED;
                else if (Server.updateTime > 0) OpCode = Constants.UPDATE;
                else OpCode = Constants.LOGIN_OK;
            }
            if (account.exists())
                if (!Password655.equals(loadedPlayer.getPassword())) OpCode = Constants.INVALID_PASSWORD;
                else if (World.isOnline(details[0])) OpCode = Constants.ALREADY_ONLINE;
            OutStream outstream = new OutStream();
            outstream.writeByte(OpCode);
            loadedPlayer = (Player) serializedJavaObject;
            if (OpCode != 2) {
                System.out.println("Writing out connection.");
                p.writeInstant(outstream).addListener(ChannelFutureListener.CLOSE);
                return;
            } else {
                System.out.println("Writing Instant connection.");
                p.writeInstant(outstream);
            }
            if (World.isOnList(Username)) {
                OpCode = Constants.ALREADY_ONLINE;
                return;
            }
            p.setConnectionStage(Constants.REMOVE_ID);
            p.setPlayer(loadedPlayer);
            World.registerConnection(p);
            System.out.println("Registering Player.");
            p.nulled = false;
        } else {
            System.out.println("Disconnecting player.");
            p.setConnectionStage(Constants.DISCONNECT);
        }
    }
}

