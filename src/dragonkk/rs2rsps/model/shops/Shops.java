package dragonkk.rs2rsps.model.shops;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
class Shops {

    private ArrayList<Shop> shops = new ArrayList<Shop>(10);

    Shops() {
        setShops();
    }

    private void setShops() {
        shops.add(!ShopManager.Voteshop ? new Shop(0, Shop0Items(), Shop0Items().size()) : new Shop(1, Shop1Items(), Shop1Items().size()));
    }

    Shop getShop(int shopid) {
        if (ShopManager.Voteshop) {
            return shops.get(1);
        } else {
            return shops.get(0);
        }
    }

    private static List<int[]> Shop1Items() {
        List<int[]> items = new ArrayList<int[]>(40);
        return items;
    }

    private static List<int[]> Shop0Items() {
        List<int[]> items = new ArrayList<int[]>(40);
        // items.add(new int[]{2422, 2147483647, 0}); //Fake Blue Partyhat
        //items.add(new int[]{1205, 1, -1}); //Bronze dagger
        items.add(new int[]{15262, 1000, 1000}); //Shard Pack
        items.add(new int[]{8007, 1000, 10}); //V Teletab
        items.add(new int[]{8008, 1000, 10}); //L Teletab
        //items.add(new int[]{4084, 5, 10000}); //Sled
        items.add(new int[]{2520, 1000, 150}); //Toy horsey
        items.add(new int[]{2526, 1000, 150}); //Toy horsey
        items.add(new int[]{2524, 1000, 150}); //Toy horsey
        items.add(new int[]{2522, 1000, 150}); //Toy horsey	
        items.add(new int[]{15098, 1000, 150}); //Dice bag
        items.add(new int[]{18346, 15, 1000}); //Magic frost book	
        items.add(new int[]{3188, 2147483647, 0}); //cleaning cloth
        //items.add(new int[]{5733, 2147483647, 0}); //Jagex Patato
        items.add(new int[]{6570, 100, 250}); //Firecape
        items.add(new int[]{10551, 100, 125}); //Fighter torso
        items.add(new int[]{17273, 1000, 50}); //Flameburst Defender
        items.add(new int[]{15825, 1000, 125}); //Flameburst Defender (B)
        items.add(new int[]{8839, 100, 130}); // Void
        items.add(new int[]{8840, 100, 130}); // Void
        items.add(new int[]{8841, 100, 130}); // Void
        items.add(new int[]{8842, 100, 130}); // Void
        items.add(new int[]{11663, 100, 100}); // Void
        items.add(new int[]{11664, 100, 100});// Void
        items.add(new int[]{11665, 100, 100});  // Void
        items.add(new int[]{15018, 100, 250}); //Seers(i)
        items.add(new int[]{15019, 100, 250}); //Archers (i)
        items.add(new int[]{15020, 100, 250}); //Warrior (i)
        items.add(new int[]{15220, 100, 250}); //Berserker (i)
        items.add(new int[]{15017, 1000, 100}); //Onyx (i)
        items.add(new int[]{13263, 100, 200}); //Slayer helm
        items.add(new int[]{15300, 100000, 8}); //Recover special
        items.add(new int[]{15349, 1000, 20});  //Ardy Cloak
        items.add(new int[]{19710, 1000000, 350}); //Dung mastercape
        items.add(new int[]{15332, 1000000, 5}); //Overload
        //Whip Upgrades
        items.add(new int[]{1771, 100, 350}); //Green dye
        items.add(new int[]{1765, 100, 600}); //Yellow dye
        items.add(new int[]{1767, 100, 850}); //Blue dye
        items.add(new int[]{11808, 100, 1250}); //Whitefish
        //Castlewars capes
        items.add(new int[]{18739, 10000000, 200}); //Flag cape
        items.add(new int[]{18741, 10000000, 200}); //Hobbyist cape
        items.add(new int[]{18740, 10000000, 200}); //Kill cape
        items.add(new int[]{18742, 10000000, 250}); //enthusiast cape
        items.add(new int[]{18743, 10000000, 350}); //professional cape.
        return items;
    }
}
