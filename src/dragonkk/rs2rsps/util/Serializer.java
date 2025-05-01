package dragonkk.rs2rsps.util;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;

import java.io.*;


/**
 * This class defines utility routines that use Java serialization.
 */
public class Serializer {


    /**
     * Serialize the object o (and any Serializable objects it refers to) and
     * store its serialized state in File f.
     *
     * @param
     */
    public static Player loadBackup(String Username) throws Exception {
        File f = new File("./Data/backup/" + Username + ".ser");
        try {
            Object player = Serializer.load(f);
            if (player == null) return null;
            else return (Player) player;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Player loadBackup2(String Username) throws Exception {
        File f = new File("./data/backup2/" + Username + ".ser");
        try {
            Object player = Serializer.load(f);
            if (player == null) return null;
            else return (Player) player;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void store(Serializable o, File f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(o); // This method serializes an object graph
        out.close();
    }

    public static Object load(File f) {
        ObjectInputStream in;
        Object object;
        try {
            in = new ObjectInputStream(new FileInputStream(f));
            object = in.readObject();
        } catch (Exception e) {
            return null;
        }
        if (object == null) return null;
        try {
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static boolean backupExists(String Username) {
        return new File(
                "./data/backup/"
                        + Username + ".ser").exists();
    }

    public static void appendData(String file, String text) {
        BufferedWriter bw;
        try {
            FileWriter fileWriter = new FileWriter("./data/" + file, true);
            bw = new BufferedWriter(fileWriter);
            bw.write(text);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (Exception exception) {
            System.out.print("Critical error while writing data: " + file);
        }
    }

    public static Player LoadAccount(String Username) throws Exception {
        File f = new File("data/savedgames/" + Username + ".ser");
        try {
            Object player = Serializer.load(f);
            if (player == null) return null;
            else return (Player) player;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Player LoadAccount(final File f) {
        try {
            Object player = Serializer.load(f);
            if (player == null) return null;
            else return (Player) player;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveBanned(File f, Object array) {
        try {
            store(array, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void store(Object o, File f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(o);
        out.close();
    }

    public static void BackupAccount(Player account) {
        if (World.hours == 3 && World.minutes == 19 && World.seconds >= 40) {
            account.getFrames().sendChatMessage(0, "Account could not backup as the server is about to restart.");
            return;
        }
        File f = new File("./data/backup/" + account.getUsername() + ".ser");
        try {
            store(account, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Backup2Account(Player account) {
        if (World.hours == 3 && World.minutes == 19 && World.seconds >= 40) {
            account.getFrames().sendChatMessage(0, "Account could not backup as the server is about to restart.");
            return;
        }
        File f = new File("./data/backup2/" + account.getUsername() + ".ser");
        try {
            store(account, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SaveAccount(Player account) {
        if (account.getTradeSession() != null) {
            account.getFrames().sendChatMessage(0, "Account could not save as you are in a trade.");
            return;
        }
        if (Server.updateTime > 0) {
            account.getFrames().sendChatMessage(0, "Account could not save as the server is about to update");
            return;
        }
        if (World.hours == 3 && World.minutes == 19 && World.seconds >= 40) {
            account.getFrames().sendChatMessage(0, "Account could not save as the server is about to restart.");
            return;
        }
        File f = new File("./data/savedgames/" + account.getUsername() + ".ser");
        try {
            store(account, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
