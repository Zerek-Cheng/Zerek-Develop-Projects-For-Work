/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.kevinnovak.inventorypages;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.kevinnovak.inventorypages.InventoryPages;
import me.kevinnovak.inventorypages.InventoryStringDeSerializer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomInventory {
    public InventoryPages plugin;
    private Player player;
    private ItemStack prevItem;
    private ItemStack nextItem;
    private ItemStack noPageItem;
    private Integer page = 0;
    public Integer maxPage = 1;
    private Integer prevPos;
    private Integer nextPos;
    private Boolean hasUsedCreative = false;
    private HashMap<Integer, ArrayList<ItemStack>> items = new HashMap();
    private ArrayList<ItemStack> creativeItems = new ArrayList(27);

    CustomInventory(InventoryPages inventoryPages, Player player, int n, ItemStack itemStack, Integer n2, ItemStack itemStack2, Integer n3, ItemStack itemStack3, String string, String string2) {
        Serializable serializable;
        ItemStack itemStack4;
        HashMap object;
        this.plugin = inventoryPages;
        this.player = player;
        this.maxPage = n;
        this.prevItem = itemStack;
        this.prevPos = n2;
        this.nextItem = itemStack2;
        this.nextPos = n3;
        this.noPageItem = itemStack3;
        int n4 = 0;
        while (n4 < n + 1) {
            if (!this.pageExists(n4).booleanValue()) {
                this.createPage(n4);
            }
            ++n4;
        }
        n4 = 0;
        while (n4 < 27) {
            this.creativeItems.add(null);
            ++n4;
        }
        String string3 = player.getUniqueId().toString();
        File file = new File(this.plugin.getDataFolder() + "/inventories/" + string3.substring(0, 1) + "/" + string3 + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration((File) file);
        if (file.exists()) {
            object = new HashMap();
            int n5 = 0;
            while (n5 < n + 1) {
                serializable = new ArrayList(25);
                int n6 = 0;
                while (n6 < 25) {
                    itemStack4 = null;
                    if (yamlConfiguration.contains("items.main." + n5 + "." + n6) && yamlConfiguration.getString("items.main." + n5 + "." + n6) != null) {
                        try {
                            itemStack4 = InventoryStringDeSerializer.stacksFromBase64(yamlConfiguration.getString("items.main." + n5 + "." + n6))[0];
                        } catch (Exception iOException) {
                            iOException.printStackTrace();
                        }
                    }
                    ((ArrayList) serializable).add(itemStack4);
                    ++n6;
                }
                object.put(n5, serializable);
                ++n5;
            }
            this.setItems((HashMap<Integer, ArrayList<ItemStack>>) object);
            if (yamlConfiguration.contains("items.creative.0")) {
                ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(27);
                int n7 = 0;
                while (n7 < 27) {
                    ItemStack itemStack5 = null;
                    if (yamlConfiguration.contains("items.creative.0." + n7)) {
                        try {
                            itemStack5 = InventoryStringDeSerializer.stacksFromBase64(yamlConfiguration.getString("items.creative.0." + n7))[0];
                        } catch (Exception iOException) {
                            iOException.printStackTrace();
                        }
                    }
                    arrayList.add(itemStack5);
                    ++n7;
                }
                this.setCreativeItems(arrayList);
            }
            if (yamlConfiguration.contains("page")) {
                this.setPage(yamlConfiguration.getInt("page"));
            }
        }
        GameMode gameMode = player.getGameMode();
        Boolean bl = false;
        serializable = false;
        int n8 = 0;
        while (n8 < 27) {
            itemStack4 = player.getInventory().getItem(n8 + 9);
            if (itemStack4 != null) {
                if (this.storeOrDropItem(itemStack4, (GameMode) gameMode).booleanValue()) {
                    serializable = Boolean.valueOf(true);
                } else {
                    bl = true;
                }
            }
            ++n8;
        }
        if (file.exists()) {
            if (bl.booleanValue()) {
                player.sendMessage(string);
            }
            if (((Boolean) serializable).booleanValue()) {
                player.sendMessage(string2);
            }
        }
    }

    void saveCurrentPage() {
        if (this.player.getGameMode() != GameMode.CREATIVE) {
            ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(25);
            int n = 0;
            while (n < 27) {
                if (n != this.prevPos && n != this.nextPos) {
                    arrayList.add(this.player.getInventory().getItem(n + 9));
                }
                ++n;
            }
            this.items.put(this.page, arrayList);
        } else {
            int n = 0;
            while (n < 27) {
                this.creativeItems.set(n, this.player.getInventory().getItem(n + 9));
                ++n;
            }
        }
    }

    void clearPage(GameMode gameMode) {
        this.clearPage(this.page, gameMode);
    }

    void clearPage(int n, GameMode gameMode) {
        if (gameMode != GameMode.CREATIVE) {
            ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(25);
            int n2 = 0;
            while (n2 < 25) {
                arrayList.add(null);
                ++n2;
            }
            this.items.put(n, arrayList);
        } else {
            int n3 = 0;
            while (n3 < 27) {
                this.creativeItems.set(n3, null);
                ++n3;
            }
        }
    }

    void clearAllPages(GameMode gameMode) {
        if (gameMode != GameMode.CREATIVE) {
            int n = 0;
            while (n < this.maxPage + 1) {
                this.clearPage(n, gameMode);
                ++n;
            }
        } else {
            this.clearPage(gameMode);
        }
    }

    void dropPage(GameMode gameMode) {
        this.dropPage(this.page, gameMode);
    }

    void dropPage(int n, GameMode gameMode) {
        if (gameMode != GameMode.CREATIVE) {
            int n2 = 0;
            while (n2 < 25) {
                ItemStack itemStack = this.items.get(n).get(n2);
                if (itemStack != null) {
                    this.player.getWorld().dropItemNaturally(this.player.getLocation(), itemStack);
                    this.items.get(n).set(n2, null);
                }
                ++n2;
            }
        } else {
            int n3 = 0;
            while (n3 < 27) {
                ItemStack itemStack = this.creativeItems.get(n3);
                if (itemStack != null) {
                    this.player.getWorld().dropItemNaturally(this.player.getLocation(), itemStack);
                    this.creativeItems.set(n3, null);
                }
                ++n3;
            }
        }
    }

    void dropAllPages(GameMode gameMode) {
        if (gameMode != GameMode.CREATIVE) {
            int n = 0;
            while (n < this.maxPage + 1) {
                this.dropPage(n, gameMode);
                ++n;
            }
        } else {
            this.dropPage(gameMode);
        }
    }

    void showPage() {
        this.showPage(this.page);
    }

    void showPage(Integer n) {
        this.showPage(n, GameMode.SURVIVAL);
    }

    void showPage(GameMode gameMode) {
        this.showPage(this.page, gameMode);
    }

    void showPage(Integer n, GameMode gameMode) {
        this.page = n > this.maxPage ? this.maxPage : n;
        if (gameMode != GameMode.CREATIVE) {
            Boolean bl = false;
            Boolean bl2 = false;
            int n2 = 0;
            while (n2 < 27) {
                int n3 = n2;
                if (n2 == this.prevPos) {
                    if (this.page == 0) {
                        this.player.getInventory().setItem(n2 + 9, this.addPageNums(this.noPageItem));
                    } else {
                        this.player.getInventory().setItem(n2 + 9, this.addPageNums(this.prevItem));
                    }
                    bl = true;
                } else if (n2 == this.nextPos) {
                    if (this.page == this.maxPage) {
                        this.player.getInventory().setItem(n2 + 9, this.addPageNums(this.noPageItem));
                    } else {
                        this.player.getInventory().setItem(n2 + 9, this.addPageNums(this.nextItem));
                    }
                    bl2 = true;
                } else {
                    if (bl.booleanValue()) {
                        --n3;
                    }
                    if (bl2.booleanValue()) {
                        --n3;
                    }
                    this.player.getInventory().setItem(n2 + 9, this.items.get(this.page).get(n3));
                }
                ++n2;
            }
        } else {
            this.hasUsedCreative = true;
            int n4 = 0;
            while (n4 < 27) {
                this.player.getInventory().setItem(n4 + 9, this.creativeItems.get(n4));
                ++n4;
            }
        }
    }

    ItemStack addPageNums(ItemStack itemStack) {
        ItemStack itemStack2 = new ItemStack(itemStack);
        ItemMeta itemMeta = itemStack2.getItemMeta();
        List list = itemMeta.getLore();
        int n = 0;
        while (n < list.size()) {
            Integer n2 = this.page + 1;
            Integer n3 = this.maxPage + 1;
            list.set(n, ((String) list.get(n)).replace("{CURRENT}", n2.toString()).replace("{MAX}", n3.toString()));
            ++n;
        }
        itemMeta.setLore(list);
        itemStack2.setItemMeta(itemMeta);
        return itemStack2;
    }

    void prevPage() {
        if (this.page > 0) {
            this.saveCurrentPage();
            this.page = this.page - 1;
            this.showPage();
            this.saveCurrentPage();
        }
    }

    void nextPage() {
        if (this.page < this.maxPage) {
            this.saveCurrentPage();
            this.page = this.page + 1;
            this.showPage();
            this.saveCurrentPage();
        }
    }

    Boolean pageExists(Integer n) {
        if (this.items.containsKey(n)) {
            return true;
        }
        return false;
    }

    void createPage(Integer n) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(25);
        int n2 = 0;
        while (n2 < 25) {
            arrayList.add(null);
            ++n2;
        }
        this.items.put(n, arrayList);
    }

    HashMap<Integer, ArrayList<ItemStack>> getItems() {
        return this.items;
    }

    void setItems(HashMap<Integer, ArrayList<ItemStack>> hashMap) {
        this.items = hashMap;
    }

    ArrayList<ItemStack> getCreativeItems() {
        return this.creativeItems;
    }

    void setCreativeItems(ArrayList<ItemStack> arrayList) {
        this.creativeItems = arrayList;
    }

    Integer getPage() {
        return this.page;
    }

    void setPage(Integer n) {
        this.page = n;
    }

    Boolean hasUsedCreative() {
        return this.hasUsedCreative;
    }

    void setUsedCreative(Boolean bl) {
        this.hasUsedCreative = bl;
    }

    AbstractMap.SimpleEntry<Integer, Integer> nextFreeSpace() {
        Integer n = 0;
        while (n < this.maxPage + 1) {
            Integer n2 = 0;
            while (n2 < 25) {
                if (this.items.get(n).get(n2) == null) {
                    AbstractMap.SimpleEntry<Integer, Integer> simpleEntry = new AbstractMap.SimpleEntry<Integer, Integer>(n, n2);
                    return simpleEntry;
                }
                n2 = n2 + 1;
            }
            n = n + 1;
        }
        return null;
    }

    int nextCreativeFreeSpace() {
        Integer n = 0;
        while (n < 27) {
            if (this.creativeItems.get(n) == null) {
                return n;
            }
            n = n + 1;
        }
        return -1;
    }

    Boolean storeOrDropItem(ItemStack itemStack, GameMode gameMode) {
        if (gameMode != GameMode.CREATIVE) {
            AbstractMap.SimpleEntry<Integer, Integer> simpleEntry = this.nextFreeSpace();
            if (simpleEntry != null) {
                this.items.get(simpleEntry.getKey()).set(simpleEntry.getValue(), itemStack);
                return false;
            }
            this.player.getWorld().dropItem(this.player.getLocation(), itemStack);
            return true;
        }
        int n = this.nextCreativeFreeSpace();
        if (n != -1) {
            this.creativeItems.set(n, itemStack);
            return false;
        }
        this.player.getWorld().dropItem(this.player.getLocation(), itemStack);
        return true;
    }
}

