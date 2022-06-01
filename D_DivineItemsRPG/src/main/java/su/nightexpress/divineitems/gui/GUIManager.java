/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package su.nightexpress.divineitems.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.gui.ContentType;
import su.nightexpress.divineitems.gui.GUI;
import su.nightexpress.divineitems.gui.GUIItem;
import su.nightexpress.divineitems.gui.GUIType;

public class GUIManager {
    private DivineItems plugin;
    private HashMap<GUIType, GUI> gui;
    private HashMap<Player, GUI> gui2;

    public GUIManager(DivineItems divineItems) {
        this.plugin = divineItems;
        this.gui = new HashMap();
        this.gui2 = new HashMap();
    }

    public void setup() {
        FileConfiguration fileConfiguration = this.plugin.getCM().configGUI.getConfig();
        GUIType[] arrgUIType = GUIType.values();
        int n = arrgUIType.length;
        int n2 = 0;
        while (n2 < n) {
            GUIType gUIType = arrgUIType[n2];
            if (fileConfiguration.contains(gUIType.name()) && fileConfiguration.contains(String.valueOf(gUIType.name()) + ".Content")) {
                Object object;
                String string = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(gUIType.name()) + ".Title"));
                int n3 = fileConfiguration.getInt(String.valueOf(gUIType.name()) + ".Size");
                HashMap<ContentType, GUIItem> hashMap = new HashMap<ContentType, GUIItem>();
                ContentType[] arrcontentType = ContentType.values();
                int n4 = arrcontentType.length;
                int n5 = 0;
                while (n5 < n4) {
                    object = arrcontentType[n5];
                    if (fileConfiguration.contains(String.valueOf(gUIType.name()) + ".Content." + object.name())) {
                        String string2 = String.valueOf(gUIType.name()) + ".Content." + object.name() + ".";
                        String[] arrstring = fileConfiguration.getString(String.valueOf(string2) + "Material").split(":");
                        String string3 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Display"));
                        List list = fileConfiguration.getStringList(String.valueOf(string2) + "Lore");
                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (String string4 : list) {
                            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string4));
                        }
                        boolean bl = fileConfiguration.getBoolean(String.valueOf(string2) + "Enchant");
                        String string5 = fileConfiguration.getString(String.valueOf(string2) + "Slots");
                        String[] arrstring2 = string5.replaceAll("\\s", "").split(",");
                        int[] arrn = new int[arrstring2.length];
                        int n6 = 0;
                        while (n6 < arrstring2.length) {
                            try {
                                arrn[n6] = Integer.parseInt(arrstring2[n6]);
                            }
                            catch (NumberFormatException numberFormatException) {
                                // empty catch block
                            }
                            ++n6;
                        }
                        ItemStack itemStack = new ItemStack(Material.getMaterial((String)arrstring[0]), Integer.parseInt(arrstring[2]), (short)Integer.parseInt(arrstring[1]));
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(string3);
                        itemMeta.setLore(arrayList);
                        if (bl) {
                            itemStack.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
                        }
                        itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS});
                        itemStack.setItemMeta(itemMeta);
                        GUIItem gUIItem = new GUIItem((ContentType)((Object)object), itemStack, arrn);
                        hashMap.put((ContentType)((Object)object), gUIItem);
                    }
                    ++n5;
                }
                object = new GUI(gUIType, string, n3, hashMap);
                this.gui.put(gUIType, (GUI)object);
            }
            ++n2;
        }
    }

    public boolean isValidGui(Inventory inventory, GUIType gUIType) {
        String string = ChatColor.stripColor((String)inventory.getTitle());
        String string2 = ChatColor.stripColor((String)new GUI(this.getGUIByType(gUIType)).getTitle());
        return string.equalsIgnoreCase(string2);
    }

    public Collection<GUI> getAllGUI() {
        return this.gui.values();
    }

    public GUI getGUIByType(GUIType gUIType) {
        return this.gui.get((Object)gUIType);
    }

    public void setGUI(Player player, GUI gUI) {
        this.gui2.put(player, gUI);
    }

    public GUI getPlayerGUI(Player player, GUIType gUIType) {
        if (this.gui2.get((Object)player).getType() == gUIType) {
            return this.gui2.get((Object)player);
        }
        return null;
    }

    public boolean valid(Player player, GUIType gUIType) {
        if (this.gui2.get((Object)player) != null && this.gui2.get((Object)player).getType() == gUIType) {
            return true;
        }
        return false;
    }

    public void reset(Player player) {
        this.gui2.remove((Object)player);
    }
}

