package dragonkk.rs2rsps.rscache;

import dragonkk.rs2rsps.util.CacheConstants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Cache {

    private static RandomAccessFile ItemDefinitionFile2;

    public Cache() {
        try {
            CacheManager.load(CacheConstants.PATH);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        LoadItemsEquipId();
    }

    private static void LoadItemsEquipId() {
        try {

            //	BufferedWriter out = new BufferedWriter(new FileWriter("itemList.txt", false));
            setItemDefinitionFile2(new RandomAccessFile(CacheConstants.ItemDefinitionsPart2, "rw"));
            if (ItemDefinitionFile2.length() == 0)
                ItemDefinitions.packItemPart2();
            int equipID = 0;
            for (int itemID = 0; itemID < getAmountOfItems(); itemID++) {
                ItemDefinitions item = ItemDefinitions.forID(itemID);
                if (item == null)
                    continue;
            /*out.write("id = "+itemID+", name = "+item.name);
			out.newLine();
			out.flush();*/
                if (item.maleEquip1 >= 0 || item.maleEquip2 >= 0)
                    item.equipId = equipID++;
            }
            getItemDefinitionFile2().close();
            setItemDefinitionFile2(null);
            //out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private static void setItemDefinitionFile2(RandomAccessFile itemDefinitionFile2) {
        ItemDefinitionFile2 = itemDefinitionFile2;
    }

    static RandomAccessFile getItemDefinitionFile2() {
        return ItemDefinitionFile2;
    }

    public static short getAmountOfItems() {
        return (short) CacheManager.cacheCFCount(CacheConstants.ITEMDEF_IDX_ID);
    }

    public static int getAmountOfObjects() {
        return CacheManager.cacheCFCount(CacheConstants.OBJECTDEF_IDX_ID);
    }

    static short getAmountOfInterfaces() {
        return (short) CacheManager.containerCount(CacheConstants.INTERFACEDEF_IDX_ID);
    }

    public static short getAmountOfNpcs() {
        return (short) CacheManager.cacheCFCount(CacheConstants.NPCDEF_IDX_ID);
    }

}
