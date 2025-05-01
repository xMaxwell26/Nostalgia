package dragonkk.rs2rsps.rsobjects;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.rscache.*;
import dragonkk.rs2rsps.util.MapData;
import dragonkk.rs2rsps.util.RSObject;
import dragonkk.rs2rsps.util.RSTile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RSObjectsRegion {

    private static Map<Short, RSObjectsRegion> loadedRegionObjects = new HashMap<>();

    public static RSObjectsRegion getRegion(short regionId) {
        if (loadedRegionObjects.containsKey(regionId))
            return loadedRegionObjects.get(regionId);
        RSObjectsRegion region = new RSObjectsRegion(regionId);
        loadedRegionObjects.put(regionId, region);
        return region;
    }

    public static boolean objectExistsAt(int objectId, RSTile location) {
        return getRegion((short) (((location.getRegionX() / 8) << 8) + (location.getRegionY() / 8))).containsObjectAt(objectId, location);
    }

    public static void putObject(RSObject object, long expireTime) {
        getRegion((short) (((object.getLocation().getRegionX() / 8) << 8) + (object.getLocation().getRegionY() / 8))).addObject(object, expireTime);
    }

    public static void loadMapObjects(Player player) {
        boolean forceSend = true;
        if ((((player.getLocation().getRegionX() / 8) == 48) || ((player.getLocation().getRegionX() / 8) == 49)) && ((player.getLocation().getRegionY() / 8) == 48))
            forceSend = false;
        if (((player.getLocation().getRegionX() / 8) == 48) && ((player.getLocation().getRegionY() / 8) == 148))
            forceSend = false;
        for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player.getLocation().getRegionX() + 6) / 8); xCalc++)
            for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player.getLocation().getRegionY() + 6) / 8); yCalc++) {
                short regionId = (short) (yCalc + (xCalc << 8));
                if (forceSend || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47))))
                    getRegion(regionId).loadSpawnObjects(player);
            }
    }

    private void loadNewObjects() {
        //Portal at fally
        spawnobjects.add(new RSObject(13629, 3015, 3358, 0, 10, 0));
        spawnobjects.add(new RSObject(13629, 3015, 3358, 0, 10, 0));
        //Banks at New PK zone
        spawnobjects.add(new RSObject(11402, 3085, 3932, 0, 10, 1));
        spawnobjects.add(new RSObject(11402, 3085, 3935, 0, 10, 1));
        spawnobjects.add(new RSObject(11402, 3084, 3931, 0, 10, 0));
        spawnobjects.add(new RSObject(11402, 3085, 3931, 0, 10, 0));
        spawnobjects.add(new RSObject(11402, 3085, 3936, 0, 10, 2));
        spawnobjects.add(new RSObject(11402, 3084, 3936, 0, 10, 2));
        //Barrier
        spawnobjects.add(new RSObject(0, 3084, 3935, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3084, 3934, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3084, 3933, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3084, 3932, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3084, 3934, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3084, 3937, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3085, 3937, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3086, 3937, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3087, 3937, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3084, 3930, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3085, 3930, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3086, 3930, 0, 10, 0));
        spawnobjects.add(new RSObject(0, 3087, 3930, 0, 10, 0));
        //Cool fireplaces
        spawnobjects.add(new RSObject(20000, 3087, 3932, 0, 10, 0));
        spawnobjects.add(new RSObject(20000, 3087, 3935, 0, 10, 0));
        spawnobjects.add(new RSObject(20001, 3093, 3932, 0, 10, 0));
        spawnobjects.add(new RSObject(20001, 3093, 3935, 0, 10, 0));
        //Stuff at Home
        spawnobjects.add(new RSObject(45803, 3085, 3933, 0, 10, 0));
        spawnobjects.add(new RSObject(45803, 3085, 3934, 0, 10, 0));
        spawnobjects.add(new RSObject(16577, 3184, 3446, 0, 10, 0));
        spawnobjects.add(new RSObject(13631, 3188, 3446, 0, 10, 0));
        spawnobjects.add(new RSObject(46935, 3182, 3445, 0, 10, 1));
        spawnobjects.add(new RSObject(-1, 3194, 3442, 0, 10, 1));
        spawnobjects.add(new RSObject(20348, 3193, 3442, 0, 10, 1));
    }
    
    private List<RSObject> objects;
    private List<RSObject> spawnobjects;
    private short regionId;

    private RSObjectsRegion(short regionId) {
        this.regionId = regionId;
        objects = new ArrayList<>();
        spawnobjects = new ArrayList<>();
        loadNewObjects();
        try {
            loadRegion();
        } catch (Exception | Error ignored) {
        }
    }

    private void loadSpawnObjects(Player player) {
        for (RSObject object : spawnobjects) {
            player.getFrames().addMapObject(object);
            RSObject lastObject = getRealObjectAt(object.getLocation());
            if (lastObject != null && lastObject.getType() != object.getType())
                player.getFrames().removeMapObject(lastObject);
        }
    }

    private void addObject(final RSObject object, long expireTime) {
        final RSObject lastObject = getObjectAt(object.getLocation());
        if (lastObject != null)
            object.setRotation(lastObject.getRotation());
        spawnobjects.add(object);
        for (Player player : World.getPlayers()) {

            boolean forceSend = true;
            if ((((player.getLocation().getRegionX() / 8) == 48) || ((player.getLocation().getRegionX() / 8) == 49)) && ((player.getLocation().getRegionY() / 8) == 48))
                forceSend = false;
            if (((player.getLocation().getRegionX() / 8) == 48) && ((player.getLocation().getRegionY() / 8) == 148))
                forceSend = false;
            for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player.getLocation().getRegionX() + 6) / 8); xCalc++)
                for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player.getLocation().getRegionY() + 6) / 8); yCalc++) {
                    short regionId = (short) (yCalc + (xCalc << 8));
                    if (forceSend || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47))))
                        if (regionId == this.regionId) {
                            player.getFrames().addMapObject(object);
                            if (lastObject != null && lastObject.getType() != object.getType())
                                player.getFrames().removeMapObject(lastObject);
                            break;
                        }
                }
        }
        Server.getWorldExecutor().schedule(new Task() {
            @Override
            public void run() {
                spawnobjects.remove(object);
                for (Player player : World.getPlayers()) {
                    boolean forceSend = true;
                    if ((((player.getLocation().getRegionX() / 8) == 48) || ((player.getLocation().getRegionX() / 8) == 49)) && ((player.getLocation().getRegionY() / 8) == 48))
                        forceSend = false;
                    if (((player.getLocation().getRegionX() / 8) == 48) && ((player.getLocation().getRegionY() / 8) == 148))
                        forceSend = false;
                    for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player.getLocation().getRegionX() + 6) / 8); xCalc++)
                        for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player.getLocation().getRegionY() + 6) / 8); yCalc++) {
                            short thisregionId = (short) (yCalc + (xCalc << 8));
                            if (forceSend || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47))))
                                if (thisregionId == regionId) {
                                    if (lastObject != null)
                                        player.getFrames().addMapObject(lastObject);
                                    if (lastObject == null || lastObject.getType() != object.getType())
                                        player.getFrames().removeMapObject(object);
                                    break;
                                }
                        }
                }
            }
        }, expireTime);
    }

    private boolean containsObjectAt(int objectId, RSTile location) {
        for (RSObject object : spawnobjects)
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return objectId == object.getId();
        for (RSObject object : objects)
            if (objectId == object.getId() && object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return true;
        return false;
    }

    private RSObject getRealObjectAt(RSTile location) {
        for (RSObject object : objects)
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return object;
        return null;
    }

    private RSObject getObjectAt(RSTile location) {
        for (RSObject object : spawnobjects)
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return object;
        for (RSObject object : objects)
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return object;
        return null;
    }


    private void loadRegion() throws Exception, Error {
        int absX = (regionId >> 8) * 64;
        int absY = (regionId & 0xff) * 64;
        ByteInputStream objectmap = null;
        RSInputStream landscape = null;
        byte[] landscapebytes = CacheManager.getByName(5, "m" + ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
        byte[] objectmapbytes = CacheManager.getByName(5, "l" + ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));

        int[] xtea_keys = MapData.getMapData().get(regionId);
        byte[] raw_data_objectmap = xtea_keys != null ? objectmapbytes != null ? XTEADecrypter.decryptXTEA(xtea_keys, objectmapbytes, 5, objectmapbytes.length) : objectmapbytes : objectmapbytes;

        if (raw_data_objectmap != null)
            objectmap = new ByteInputStream(new CacheContainer(raw_data_objectmap).decompress());
        if (landscapebytes != null)
            landscape = new RSInputStream(new ByteArrayInputStream(new CacheContainer(landscapebytes).decompress()));
        byte[][][] someArray = new byte[4][64][64];
        if (landscape != null) for (int z = 0; z < 4; z++)
            for (int localX = 0; localX < 64; localX++)
                for (int localY = 0; localY < 64; localY++)
                    while (true) {
                        int v = landscape.readByte() & 0xff;
                        if (v == 0) break;
                        else if (v == 1) {
                            landscape.readByte();
                            break;
                        } else if (v <= 49) landscape.readByte();
                        else if (v <= 81) someArray[z][localX][localY] = (byte) (v - 49);
                    }
        if (objectmap != null) {
            int objectId = -1;
            int incr;
            while ((incr = objectmap.readSmart2()) != 0) {
                objectId += incr;
                int location = 0;
                int incr2;
                while ((incr2 = objectmap.readSmart()) != 0) {
                    location += incr2 - 1;
                    int localX = (location >> 6 & 0x3f);
                    int localY = (location & 0x3f);
                    int z = location >> 12;
                    int objectData = objectmap.readUByte();
                    int type = objectData >> 2;
                    int rotation = objectData & 0x3;
                    if (localX < 0 || localX >= 64 || localY < 0
                            || localY >= 64) continue;
                    if ((someArray[1][localX][localY] & 2) == 2) z--;
                    if (z >= 0 && z <= 3)
                        objects.add(new RSObject(objectId, localX + absX, localY + absY, z, type, rotation));
                }
            }
        }
    }
}
