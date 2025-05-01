package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.model.Container;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.rscache.Cache;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

import java.io.Serializable;

public class Banking implements Serializable {

    private static final long serialVersionUID = -9176611950582062556L;

    private Player player;

    public Container<Item> bank = new Container<Item>(468, false);
    public boolean withdrawNote = false;
    public boolean inserting = false;
    private final int[] tabStartSlot = new int[TAB_SIZE];
    private static int TAB_SIZE = 11;

    public void addItemFromInventoryBankAll(int slot, int itemID, int amount) {
        Item itemInInventory = player.getInventory().getContainer().get(slot);
        if (itemInInventory.getId() != itemID)
            return;
        int maxAmount = player.getInventory().numberOf(itemID);
        if (amount > maxAmount)
            amount = maxAmount;
        boolean isNoted = itemInInventory.getDefinition().isNoted();
        for (int i = 0; i < bank.getSize(); i++)
            if (bank.get(i) == null) {
                bank.set(i, new Item(isNoted ? itemID - 1 : itemID, amount));
                break;
            } else if (bank.get(i).getId() == itemID && bank.get(i).getAmount() == 2147483647) {
                player.getFrames().sendChatMessage(0, "You have max of that item in your inventory.");
                return;
            } else if (bank.get(i).getId() == (isNoted ? itemID - 1 : itemID)) {
                bank.set(i, new Item(isNoted ? itemID - 1 : itemID, bank.get(i)
                        .getAmount() + amount));
                break;
            }
        refresh();
    }

    public void setPlayer(Player player) {
        this.player = player;
        for (Item item : bank.getItems())
            if (item != null)
                item.setDefinition(ItemDefinitions.forID(item.getId()));
    }

    public void addItemFromInventory(int slot, int itemID, int amount) {
        Item itemInInventory = player.getInventory().getContainer().get(slot);
        if (itemInInventory.getId() != itemID)
            return;
        int maxAmount = player.getInventory().numberOf(itemID);
        if (amount > maxAmount)
            amount = maxAmount;
        boolean isNoted = itemInInventory.getDefinition().isNoted();
        for (int i = 0; i < bank.getSize(); i++)
            if (bank.get(i) == null) {
                bank.set(i, new Item(isNoted ? itemID - 1 : itemID, amount));
                break;
            } else if (bank.get(i).getId() == itemID && bank.get(i).getAmount() == 2147483647) {
                player.getFrames().sendChatMessage(0, "You have max of that item in your inventory.");
                return;
            } else if (bank.get(i).getId() == (isNoted ? itemID - 1 : itemID)) {
                bank.set(i, new Item(isNoted ? itemID - 1 : itemID, bank.get(i)
                        .getAmount() + amount));
                break;
            }
        player.getInventory().deleteItem(itemID, amount);
        refresh();
    }

    public void deleteItemToInventory(int slot, int itemID, int amount,
                                      boolean butOne) {
        if (bank.get(slot).getId() != itemID)
            return;
        if (player.getInventory().contains(itemID, 2147483647)) {
            player.getFrames().sendChatMessage(0, "You have max of that item in your inventory.");
            return;
        }
        int maxAmount = bank.getNumberOff(itemID);
        if (maxAmount == 0)// logic error
            return;
        if (amount > maxAmount) amount = !butOne ? maxAmount : maxAmount - 1;
        if (amount <= 0 || maxAmount <= 0) amount = 1;
        boolean shouldNote = (this.withdrawNote && ItemDefinitions.forID(itemID + 1).isNoted());
        boolean stackable = ItemDefinitions.forID(shouldNote ? itemID + 1 : itemID).isStackable();
        if ((player.getInventory().getFreeSlots() == 0 && !stackable)
                || (player.getInventory().getFreeSlots() == 0 && stackable && !player
                .getInventory().contains(shouldNote ? itemID + 1 : itemID))) {
            player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            return;
        }
        if ((!stackable || !player.getInventory().contains(itemID)) && (amount > player.getInventory().getFreeSlots()) && !withdrawNote)
            amount = player.getInventory().getFreeSlots();
        bank.remove(new Item(itemID, amount));
        player.getInventory().addItem(shouldNote ? itemID + 1 : itemID, amount);
        bank.shift();
        refresh();
    }

    public void sendTabConfig() {
        int config = 0;
        config += getItemsInTab(2);
        config += getItemsInTab(3) * 1024;
        config += getItemsInTab(4) * 1048576;
        player.getFrames().sendConfig2(1246, config);
        config = 0;
        config += getItemsInTab(5);
        config += getItemsInTab(6) * 1024;
        config += getItemsInTab(7) * 1048576;
        player.getFrames().sendConfig2(1247, config);
        int tab = player.getCurrentTab();
        config = -2013265920;
        config += (134217728 * (tab == 10 ? 0 : tab - 1));
        config += getItemsInTab(8);
        config += getItemsInTab(9) * 1024;
        player.getFrames().sendConfig2(1248, config);
    }

    public int getItemsInTab(int tabId) {
        return tabStartSlot[tabId + 1] - tabStartSlot[tabId];
    }

    public void openBank() {
        inserting = false;
        player.setCurrentTab(10);
        player.getFrames().sendConfig2(563, 4194304);
        player.getFrames().sendConfig(1248, -2013265920);
        player.getFrames().sendInterface(762);
        player.getFrames().sendString(bank.getSize() + "", 762, 31);
        player.getFrames().sendString(bank.getSize() + "", 762, 32);
        player.getFrames().sendString("Bank of Nostalgia 614", 762, 45);
        player.getFrames().sendInventoryInterface(763);
        player.getFrames().sendAMask(0, 516, 762, 93, 40, 1278);
        player.getFrames().sendAMask(0, 27, 763, 0, 36, 1150);
        player.getFrames().sendBlankClientScript(1451);
        player.getFrames().sendItems(95, bank, false);
        player.getFrames().sendItems(31, player.getInventory().getContainer(), false);
    }

    public boolean addItem(int itemId, int amount) {
        int currentTab = player.getCurrentTab();
        if (itemId < 0 || itemId > Cache.getAmountOfItems()) return false;
        boolean b = bank.getSize() >= 468;
        if (!b) {
            player.getFrames().sendChatMessage(0,
                    "Your bank is full.");
            return false;
        }
        Item item = new Item(itemId);
        if (!player.getInventory().contains(itemId))
            return false;
        if (bank.containsOne(item)) for (int i = 0; i > bank.getSize(); i++) {
            Item bankItem = bank.get(i);
            if (bankItem.getId() == item.getId()) {
                Item toBank = new Item(item.getId(), bankItem.getAmount()
                        + item.getAmount());
                bank.set(i, toBank);
            }
        }
        else {
            int freeSlot;
            if (currentTab == 10) freeSlot = bank.getFreeSlot();
            else freeSlot = tabStartSlot[currentTab] + getItemsInTab(currentTab);
            if (item.getAmount() > 0) if (currentTab != 10) {
                insert(bank.getFreeSlot(), freeSlot);
                increaseTabStartSlots(currentTab);
            }
            bank.set(freeSlot, new Item(item.getId(), amount));
        }
        player.getInventory().deleteItem(itemId, amount);
        refresh();
        return true;
    }

    public void refresh() {
        player.getFrames().sendItems(95, bank, false);
    }

    public boolean deleteItem(int item, int amount) {
        if (item < 0 || item > Cache.getAmountOfItems()) return false;
        bank.remove(new Item(item, amount));
        player.getInventory().getContainer().add(new Item(item, amount));
        refresh();
        return true;
    }

    public void set(int slot, Item item) {
        bank.set(slot, item);
    }

    public void increaseTabStartSlots(int startId) {
        for (int i = startId + 1; i < tabStartSlot.length; i++) tabStartSlot[i]++;
    }

    public void decreaseTabStartSlots(int startId) {
        if (startId == 10)
            return;
        for (int i = startId + 1; i < tabStartSlot.length; i++) tabStartSlot[i]--;
        if (getItemsInTab(startId) == 0) collapseTab(startId);
    }

    public void insert(int fromId, int toId) {
        Item temp = bank.getItems()[fromId];
        if (toId > fromId) for (int i = fromId; i < toId; i++) set(i, get(i + 1));
        else if (fromId > toId) for (int i = fromId; i > toId; i--) set(i, get(i - 1));
        set(toId, temp);
    }

    public int getTabByItemSlot(int itemSlot) {
        int tabId = 0;
        for (int i = 0; i < tabStartSlot.length; i++) if (itemSlot >= tabStartSlot[i]) tabId = i;
        return tabId;
    }

    private void collapseTab(int tabId) {
        int size = getItemsInTab(tabId);
        Item[] tempTabItems = new Item[size];
        for (int i = 0; i < size; i++) {
            tempTabItems[i] = get(tabStartSlot[tabId] + i);
            set(tabStartSlot[tabId] + i, null);
        }
        bank.shift();
        for (int i = tabId; i < tabStartSlot.length - 1; i++) tabStartSlot[i] = tabStartSlot[i + 1] - size;
        tabStartSlot[10] = tabStartSlot[10] - size;
        for (int i = 0; i < size; i++) {
            int slot = bank.getFreeSlot();
            set(slot, tempTabItems[i]);
        }
    }

    public int getArrayIndex(int tabId) {
        if (tabId == 61 || tabId == 73) return 10;
        int base = 59;
        for (int i = 2; i < 10; i++) {
            if (tabId == base) return i;
            base -= 2;
        }
        base = 74;
        for (int i = 2; i < 10; i++) {
            if (tabId == base) return i;
            base++;
        }
        return -1;
    }

    public int[] getTab() {
        return tabStartSlot;
    }

    public Item get(int slot) {
        return bank.get(slot);
    }

}
