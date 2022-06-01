/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.ArrayUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitScheduler
 */
package su.nightexpress.divineitems.modules.runes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.RunesCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.gui.ContentType;
import su.nightexpress.divineitems.gui.GUI;
import su.nightexpress.divineitems.gui.GUIItem;
import su.nightexpress.divineitems.gui.GUIManager;
import su.nightexpress.divineitems.gui.GUIType;
import su.nightexpress.divineitems.modules.MainSettings;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.types.DestroyType;
import su.nightexpress.divineitems.types.SlotType;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class RuneManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private MyConfig settingsCfg;
    private MyConfig runesCfg;
    private boolean e;
    private Random r;
    private int taskId;
    private RuneSettings rr;
    private HashMap<String, Rune> runes;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_RUNE = "DIVINE_RUNE_ID";
    private final String NBT_KEY_ITEM_RUNE = "RUNE_";
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType;

    public RuneManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.runes = new HashMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.runesCfg = new MyConfig(this.plugin, "/modules/" + this.n, "runes.yml");
        this.setupSettings();
        this.setupSlot();
        this.setupRunes();
    }

    @Override
    public boolean isActive() {
        return this.e;
    }

    @Override
    public boolean isDropable() {
        return true;
    }

    @Override
    public boolean isResolvable() {
        return true;
    }

    @Override
    public String name() {
        return "Runes";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new RunesCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
            this.startTask();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.stopTask();
            this.rr = null;
            this.runes.clear();
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    private void setupSlot() {
        SlotType slotType = SlotType.RUNE;
        slotType.setModule(this);
        slotType.setHeader(this.getSettings().getHeader());
        slotType.setEmpty(this.getSettings().getEmptySlot());
        slotType.setFilled(this.getSettings().getFilledSlot());
    }

    private void setupSettings() {
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        String string = "Item.";
        String string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "Display"));
        List list = fileConfiguration.getStringList(String.valueOf(string) + "Lore");
        string = "Enchant.";
        int n = fileConfiguration.getInt(String.valueOf(string) + "MinSuccessChance");
        int n2 = fileConfiguration.getInt(String.valueOf(string) + "MaxSuccessChance");
        DestroyType destroyType = DestroyType.CLEAR;
        try {
            destroyType = DestroyType.valueOf(fileConfiguration.getString(String.valueOf(string) + "DestroyType"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, String.valueOf(string) + "DestroyType", "Invalid Destroy Type!", true);
            fileConfiguration.set(String.valueOf(string) + "DestroyType", (Object)"CLEAR");
        }
        boolean bl = fileConfiguration.getBoolean(String.valueOf(string) + "StackLevels");
        string = "Effects.";
        boolean bl2 = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
        String string3 = fileConfiguration.getString(String.valueOf(string) + "Failure");
        String string4 = fileConfiguration.getString(String.valueOf(string) + "Success");
        string = "Sounds.";
        boolean bl3 = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
        Sound sound = Sound.BLOCK_ANVIL_BREAK;
        try {
            sound = Sound.valueOf((String)fileConfiguration.getString(String.valueOf(string) + "Failure"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, String.valueOf(string) + "Failure", "Invalid Sound Type!", true);
            fileConfiguration.set(String.valueOf(string) + "Failure", (Object)"BLOCK_ANVIL_BREAK");
        }
        Sound sound2 = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
        try {
            sound2 = Sound.valueOf((String)fileConfiguration.getString(String.valueOf(string) + "Success"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, String.valueOf(string) + "Success", "Invalid Sound Type!", true);
            fileConfiguration.set(String.valueOf(string) + "Success", (Object)"ENTITY_EXPERIENCE_ORB_PICKUP");
        }
        string = "Design.";
        String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "Header"));
        String string6 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "EmptySlot"));
        String string7 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "FilledSlot"));
        this.rr = new RuneSettings(string2, list, n, n2, destroyType, bl, bl2, string3, string4, bl3, sound, sound2, string5, string6, string7);
    }

    private void setupRunes() {
        FileConfiguration fileConfiguration = this.runesCfg.getConfig();
        for (String string : fileConfiguration.getConfigurationSection("Runes").getKeys(false)) {
            String string2 = "Runes." + string + ".";
            String string3 = string.toString().toLowerCase();
            boolean bl = fileConfiguration.getBoolean(String.valueOf(string2) + "Enabled");
            if (!bl) continue;
            String string4 = fileConfiguration.getString(String.valueOf(string2) + "Material");
            String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Name"));
            List list = fileConfiguration.getStringList(String.valueOf(string2) + "Desc");
            String string6 = fileConfiguration.getString(String.valueOf(string2) + "Effect");
            int n = fileConfiguration.getInt(String.valueOf(string2) + "LevelMin");
            int n2 = fileConfiguration.getInt(String.valueOf(string2) + "LevelMax");
            List list2 = fileConfiguration.getStringList(String.valueOf(string2) + "ItemTypes");
            Rune rune = new Rune(bl, string3, string4, string5, list, string6, n, n2, list2);
            this.runes.put(string3, rune);
        }
    }

    private void startTask() {
        this.taskId = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    RuneManager.this.setRuneEffects(player);
                }
            }
        }, 10L, 60L);
    }

    private void stopTask() {
        this.plugin.getServer().getScheduler().cancelTask(this.taskId);
    }

    public void setRuneEffects(Player player) {
        NBTItem nBTItem;
        if (player.getInventory().getArmorContents() == null) {
            return;
        }
        HashMap<PotionEffectType, Integer> hashMap = new HashMap<PotionEffectType, Integer>();
        ItemStack[] arritemStack = EntityAPI.getEquipment((LivingEntity)player, false);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack[n2];
            if (itemStack != null && itemStack.hasItemMeta()) {
                nBTItem = new NBTItem(itemStack);
                for (String string : nBTItem.getKeys()) {
                    PotionEffectType potionEffectType;
                    String[] arrstring;
                    if (!string.startsWith("RUNE_") || (potionEffectType = PotionEffectType.getByName((String)(arrstring = nBTItem.getString(string).split(":"))[2])) == null) continue;
                    int n3 = Integer.parseInt(arrstring[1]);
                    if (this.getSettings().isStackLevels() && hashMap.containsKey((Object)potionEffectType)) {
                        n3 += ((Integer)hashMap.get((Object)potionEffectType)).intValue();
                    }
                    hashMap.put(potionEffectType, n3);
                }
            }
            ++n2;
        }
        if (hashMap.isEmpty()) {
            return;
        }
        for (ItemStack itemStack : hashMap.keySet()) {
            n = (Integer)hashMap.get((Object)itemStack);
            int n4 = 100;
            if (itemStack.getName().equalsIgnoreCase("NIGHT_VISION")) {
                n4 = 1200;
            }
            nBTItem = new PotionEffect((PotionEffectType)itemStack, n4, n - 1);
            player.removePotionEffect((PotionEffectType)itemStack);
            player.addPotionEffect((PotionEffect)nBTItem);
        }
    }

    public boolean hasRune(String string, ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string2 : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string2.startsWith("RUNE_") || !(arrstring = nBTItem.getString(string2).split(":"))[0].equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }

    public int getItemRuneLvl(ItemStack itemStack, String string) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string2 : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string2.startsWith("RUNE_") || !(arrstring = nBTItem.getString(string2).split(":"))[0].equalsIgnoreCase(string)) continue;
            return Integer.parseInt(arrstring[1]);
        }
        return 0;
    }

    public String getRuneId(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String[] arrstring = nBTItem.getString("DIVINE_RUNE_ID").split(":");
        return arrstring[0];
    }

    public int getRuneLevel(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String[] arrstring = nBTItem.getString("DIVINE_RUNE_ID").split(":");
        return Integer.parseInt(arrstring[1]);
    }

    private String getRuneEffect(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String[] arrstring = nBTItem.getString("DIVINE_RUNE_ID").split(":");
        return arrstring[2];
    }

    public RuneSettings getSettings() {
        return this.rr;
    }

    public Collection<Rune> getRunes() {
        return this.runes.values();
    }

    public List<String> getRuneNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Rune rune : this.getRunes()) {
            arrayList.add(rune.getId());
        }
        return arrayList;
    }

    public Rune getRuneById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<Rune>(this.getRunes()).get(this.r.nextInt(this.getRunes().size()));
        }
        return this.runes.get(string.toLowerCase());
    }

    public int getItemRunesAmount(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        int n = 0;
        for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("RUNE_")) continue;
            ++n;
        }
        return n;
    }

    public ItemStack removeRune(ItemStack itemStack, String string) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string2 : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string2.startsWith("RUNE_") || !(arrstring = nBTItem.getString(string2).split(":"))[0].equalsIgnoreCase(string)) continue;
            nBTItem.removeKey(string2);
            break;
        }
        return nBTItem.getItem();
    }

    public ItemStack removeRune(ItemStack itemStack, int n) {
        int n2;
        NBTItem nBTItem = new NBTItem(itemStack);
        int n3 = this.getItemRunesAmount(itemStack);
        int n4 = n - 1;
        int n5 = n2 = n3 - n4;
        if (n4 == 0) {
            --n5;
        }
        nBTItem.removeKey("RUNE_" + n5);
        itemStack = new ItemStack(nBTItem.getItem());
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>(itemMeta.getLore());
        int n6 = 0;
        int n7 = 0;
        for (String string : itemMeta.getLore()) {
            if (string.startsWith(this.getSettings().getFilledSlot())) {
                ++n6;
            }
            if (n6 == n2) {
                arrayList.remove(n7);
                arrayList.add(n7, this.rr.getEmptySlot());
                break;
            }
            ++n7;
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private void openGUI(Player player, ItemStack itemStack, ItemStack itemStack2) {
        GUI gUI = new GUI(this.plugin.getGUIManager().getGUIByType(GUIType.ENCHANT_RUNE));
        gUI.getItems().get((Object)ContentType.TARGET).setItem(itemStack);
        gUI.getItems().get((Object)ContentType.SOURCE).setItem(itemStack2);
        gUI.getItems().get((Object)ContentType.RESULT).setItem(this.getResult(new ItemStack(itemStack), new ItemStack(itemStack2)));
        this.plugin.getGUIManager().setGUI(player, gUI);
        player.openInventory(gUI.build());
    }

    private ItemStack insertRune(ItemStack itemStack, ItemStack itemStack2) {
        Object object2;
        String string = this.getRuneId(itemStack2);
        int n = this.getRuneLevel(itemStack2);
        String string2 = this.getRuneEffect(itemStack2);
        Rune rune = this.getRuneById(string);
        String string3 = rune.getName().replace("%rlevel%", "").replace("%level%", "").trim();
        List list = itemStack.getItemMeta().getLore();
        String string4 = String.valueOf(this.getSettings().getFilledSlot()) + rune.getName().replace("%level%", new StringBuilder(String.valueOf(n)).toString()).replace("%rlevel%", Utils.IntegerToRomanNumeral(n));
        if (this.hasRune(string, itemStack)) {
            for (Object object2 : list) {
                if (!object2.contains(string3)) continue;
                int n2 = list.indexOf(object2);
                list.remove(n2);
                list.add(n2, string4);
                break;
            }
            itemStack = this.removeRune(itemStack, string);
        } else {
            for (Object object2 : list) {
                String string5;
                String string6 = ChatColor.stripColor((String)object2);
                if (!string6.equals(string5 = ChatColor.stripColor((String)this.getSettings().getEmptySlot()))) continue;
                int n3 = list.indexOf(object2);
                list.remove(n3);
                list.add(n3, string4);
                break;
            }
        }
        object2 = itemStack.getItemMeta();
        object2.setLore(list);
        itemStack.setItemMeta((ItemMeta)object2);
        NBTItem nBTItem = new NBTItem(itemStack);
        nBTItem.setString("RUNE_" + this.getItemRunesAmount(itemStack), String.valueOf(string) + ":" + n + ":" + string2);
        return nBTItem.getItem();
    }

    private ItemStack getResult(ItemStack itemStack, ItemStack itemStack2) {
        ItemStack itemStack3 = this.insertRune(itemStack, itemStack2);
        ItemMeta itemMeta = itemStack3.getItemMeta();
        itemMeta.setDisplayName(Lang.Runes_Enchanting_Result.toMsg());
        itemStack3.setItemMeta(itemMeta);
        return itemStack3;
    }

    public boolean isRune(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        if (nBTItem.hasKey("DIVINE_RUNE_ID").booleanValue() && nBTItem.getString("DIVINE_RUNE_ID").split(":").length == 3) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCursor();
        ItemStack itemStack2 = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || itemStack2 == null) {
            return;
        }
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (!itemStack2.hasItemMeta() || !itemStack2.getItemMeta().hasLore()) {
            return;
        }
        if (inventoryClickEvent.getInventory().getType() != InventoryType.CRAFTING) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.CRAFTING) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.ARMOR || inventoryClickEvent.getSlot() == 40) {
            return;
        }
        if (!this.isRune(itemStack)) {
            return;
        }
        String string = this.getRuneId(itemStack);
        Rune rune = this.getRuneById(string);
        if (rune == null) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_Internal.toMsg());
            return;
        }
        boolean bl = false;
        for (String string2 : rune.getItemTypes()) {
            if (!ItemUtils.isValidItemType(string2, itemStack2)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_InvalidType.toMsg());
            return;
        }
        int n = this.getRuneLevel(itemStack);
        if (this.hasRune(string, itemStack2) && this.getItemRuneLvl(itemStack2, string) >= n) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_AlreadyHave.toMsg());
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_FullInventory.toMsg());
            return;
        }
        for (Object object : itemStack2.getItemMeta().getLore()) {
            String string3;
            String string4 = ChatColor.stripColor((String)object);
            if (!string4.equalsIgnoreCase(string3 = ChatColor.stripColor((String)this.getSettings().getEmptySlot()))) continue;
            inventoryClickEvent.setCursor(null);
            player.getInventory().addItem(new ItemStack[]{itemStack});
            inventoryClickEvent.setCancelled(true);
            this.openGUI(player, itemStack2, itemStack);
            return;
        }
        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_NoSlots.toMsg());
    }

    @EventHandler
    public void onClickGUI(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (!this.plugin.getGUIManager().valid(player, GUIType.ENCHANT_RUNE)) {
            return;
        }
        GUI gUI = this.plugin.getGUIManager().getPlayerGUI(player, GUIType.ENCHANT_RUNE);
        if (gUI == null || !inventoryClickEvent.getInventory().getTitle().equals(gUI.getTitle())) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return;
        }
        GUIItem gUIItem = gUI.getItems().get((Object)ContentType.ACCEPT);
        int n = inventoryClickEvent.getRawSlot();
        if (itemStack.isSimilar(gUIItem.getItem()) || ArrayUtils.contains((int[])gUIItem.getSlots(), (int)n)) {
            ItemStack itemStack2 = new ItemStack(gUI.getItems().get((Object)ContentType.TARGET).getItem());
            ItemStack itemStack3 = new ItemStack(gUI.getItems().get((Object)ContentType.SOURCE).getItem());
            if (!player.getInventory().contains(itemStack2)) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_NoItem.toMsg());
                return;
            }
            int n2 = new Random().nextInt(100);
            NBTItem nBTItem = new NBTItem(itemStack3);
            int n3 = nBTItem.getInteger("DIVINE_CHANCE");
            RuneSettings runeSettings = this.getSettings();
            if (n3 < n2) {
                DestroyType destroyType = runeSettings.getDestroyType();
                switch (RuneManager.$SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType()[destroyType.ordinal()]) {
                    case 1: {
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_Failure_Item.toMsg());
                        break;
                    }
                    case 2: {
                        player.getInventory().removeItem(new ItemStack[]{itemStack3});
                        itemStack3.setAmount(itemStack3.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack3});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_Failure_Source.toMsg());
                        break;
                    }
                    case 4: {
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        player.getInventory().removeItem(new ItemStack[]{itemStack3});
                        itemStack3.setAmount(itemStack3.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack3});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_Failure_Both.toMsg());
                        break;
                    }
                    case 3: {
                        Object object2;
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        player.getInventory().removeItem(new ItemStack[]{itemStack3});
                        itemStack3.setAmount(itemStack3.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack3});
                        ItemMeta itemMeta = itemStack2.getItemMeta();
                        List list = itemMeta.getLore();
                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (Object object2 : list) {
                            if (object2.startsWith(runeSettings.getFilledSlot())) {
                                arrayList.add(runeSettings.getEmptySlot());
                                continue;
                            }
                            arrayList.add((String)object2);
                        }
                        itemMeta.setLore(arrayList);
                        itemStack2.setItemMeta(itemMeta);
                        object2 = new NBTItem(itemStack2);
                        for (String string : object2.getKeys()) {
                            if (!string.startsWith("RUNE_")) continue;
                            object2.removeKey(string);
                        }
                        player.getInventory().addItem(new ItemStack[]{object2.getItem()});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_Failure_Both.toMsg());
                    }
                }
                player.closeInventory();
                if (runeSettings.useSound()) {
                    player.playSound(player.getLocation(), runeSettings.getDestroySound(), 0.5f, 0.5f);
                }
                if (runeSettings.useEffect()) {
                    Utils.playEffect(runeSettings.getDestroyEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
                }
                return;
            }
            player.getInventory().removeItem(new ItemStack[]{itemStack2});
            ItemStack itemStack4 = this.insertRune(itemStack2, itemStack3);
            player.getInventory().addItem(new ItemStack[]{itemStack4});
            player.getInventory().removeItem(new ItemStack[]{itemStack3});
            itemStack3.setAmount(itemStack3.getAmount() - 1);
            player.getInventory().addItem(new ItemStack[]{itemStack3});
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_Success.toMsg());
            player.closeInventory();
            if (runeSettings.useSound()) {
                player.playSound(player.getLocation(), runeSettings.getSuccessSound(), 0.5f, 0.5f);
            }
            if (runeSettings.useEffect()) {
                Utils.playEffect(runeSettings.getSuccessEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
            }
            player.updateInventory();
            this.plugin.getGUIManager().reset(player);
            return;
        }
        GUIItem gUIItem2 = gUI.getItems().get((Object)ContentType.DECLINE);
        if (itemStack.isSimilar(gUIItem2.getItem()) || ArrayUtils.contains((int[])gUIItem2.getSlots(), (int)n)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Runes_Enchanting_Cancel.toMsg());
            player.closeInventory();
            this.plugin.getGUIManager().reset(player);
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[DestroyType.values().length];
        try {
            arrn[DestroyType.BOTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DestroyType.CLEAR.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DestroyType.ITEM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DestroyType.SOURCE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType;
    }

    public class Rune {
        private boolean enabled;
        private String id;
        private String material;
        private String name;
        private List<String> desc;
        private String effect;
        private int min_lvl;
        private int max_lvl;
        private List<String> item_types;

        public Rune(boolean bl, String string, String string2, String string3, List<String> list, String string4, int n, int n2, List<String> list2) {
            this.setEnabled(bl);
            this.setId(string);
            this.setMaterial(string2);
            this.setName(string3);
            this.setDesc(list);
            this.setEffect(string4);
            this.setMinLevel(n);
            this.setMaxLevel(n2);
            this.setItemTypes(list2);
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean bl) {
            this.enabled = bl;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public String getMaterial() {
            return this.material;
        }

        public void setMaterial(String string) {
            this.material = string;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String string) {
            this.name = string;
        }

        public List<String> getDesc() {
            return this.desc;
        }

        public void setDesc(List<String> list) {
            this.desc = list;
        }

        public String getEffect() {
            return this.effect;
        }

        public void setEffect(String string) {
            this.effect = string;
        }

        public int getMinLevel() {
            return this.min_lvl;
        }

        public void setMinLevel(int n) {
            this.min_lvl = n;
        }

        public int getMaxLevel() {
            return this.max_lvl;
        }

        public void setMaxLevel(int n) {
            this.max_lvl = n;
        }

        public List<String> getItemTypes() {
            return this.item_types;
        }

        public void setItemTypes(List<String> list) {
            this.item_types = list;
        }

        public ItemStack create(int n) {
            Object object2;
            if (!this.isEnabled()) {
                return null;
            }
            if (n == -1) {
                n = Utils.randInt(this.getMinLevel(), this.getMaxLevel());
            } else if (n > this.getMaxLevel()) {
                n = this.getMaxLevel();
            } else if (n < 1) {
                n = this.getMinLevel();
            }
            String[] arrstring = this.getMaterial().split(":");
            ItemStack itemStack = Utils.buildItem(arrstring, this.id);
            ItemMeta itemMeta = itemStack.getItemMeta();
            String string = this.getName().replace("%level%", String.valueOf(n)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n));
            String string2 = RuneManager.this.getSettings().getDisplay().replace("%s", string);
            int n2 = Utils.randInt(RuneManager.this.getSettings().getMinChance(), RuneManager.this.getSettings().getMaxChance());
            int n3 = 100 - n2;
            String string3 = "";
            String string4 = RuneManager.this.plugin.getCM().getCFG().getStrClassColor();
            String string5 = RuneManager.this.plugin.getCM().getCFG().getStrClassSeparator();
            for (String object3 : this.getItemTypes()) {
                object2 = object3.replace("*", "");
                if (Lang.hasPath("ItemTypes." + (String)object2)) {
                    object2 = Lang.getCustom("ItemTypes." + (String)object2);
                }
                string3 = String.valueOf(string3) + string4 + (String)object2 + string5;
            }
            if (string3.length() > 3) {
                string3 = string3.substring(0, string3.length() - 3);
            }
            List<String> list = RuneManager.this.getSettings().getLore();
            ArrayList arrayList = new ArrayList();
            for (Object object2 : list) {
                if (object2.equals("%desc%")) {
                    for (String string6 : this.getDesc()) {
                        arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string6.replace("%level%", String.valueOf(n)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n))));
                    }
                    continue;
                }
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2.replace("%d%", String.valueOf(n3)).replace("%s%", String.valueOf(n2)).replace("%type%", string3).replace("%level%", String.valueOf(n)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n))));
            }
            itemMeta.setDisplayName(string2);
            itemMeta.setLore((List)arrayList);
            itemMeta.spigot().setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.values());
            itemStack.setItemMeta(itemMeta);
            object2 = new NBTItem(itemStack);
            object2.setString("DIVINE_RUNE_ID", String.valueOf(this.getId().toLowerCase()) + ":" + n + ":" + this.getEffect());
            object2.setInteger("DIVINE_CHANCE", n2);
            return object2.getItem();
        }
    }

    public class RuneSettings
    extends MainSettings {
        private boolean stack;

        public RuneSettings(String string, List<String> list, int n, int n2, DestroyType destroyType, boolean bl, boolean bl2, String string2, String string3, boolean bl3, Sound sound, Sound sound2, String string4, String string5, String string6) {
            super(string, list, n, n2, destroyType, bl2, string2, string3, bl3, sound, sound2, string4, string5, string6);
            this.setStackLevels(bl);
        }

        public boolean isStackLevels() {
            return this.stack;
        }

        public void setStackLevels(boolean bl) {
            this.stack = bl;
        }
    }

}

