/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.md_5.bungee.api.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.ItemSpawnEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package su.nightexpress.divineitems.modules.sets;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.SetsCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.utils.ErrorLog;

public class SetManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private MyConfig settingsCfg;
    private MyConfig setsCfg;
    private boolean e;
    private int taskId;
    private SetSettings ss;
    private HashMap<String, ItemSet> sets;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");

    public SetManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        this.sets = new HashMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.setsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "sets.yml");
        this.setup();
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
        return false;
    }

    @Override
    public String name() {
        return "Sets";
    }

    @Override
    public String version() {
        return "0.1";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new SetsCommand(this.plugin));
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
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    public void setup() {
        this.setupSettings();
        this.setupSets();
    }

    private void setupSettings() {
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        boolean bl = fileConfiguration.getBoolean("DynamicLore");
        String string = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("ItemHave"));
        String string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("ItemMiss"));
        String string3 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("LoreHeader"));
        String string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("LoreFooter"));
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string5 : fileConfiguration.getStringList("Lore")) {
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string5));
        }
        this.ss = new SetSettings(bl, string, string2, string3, string4, arrayList);
    }

    private void setupSets() {
        FileConfiguration fileConfiguration = this.setsCfg.getConfig();
        if (!fileConfiguration.contains("ItemSets")) {
            return;
        }
        for (String string : fileConfiguration.getConfigurationSection("ItemSets").getKeys(false)) {
            Object object5;
            Object object22;
            Object object32;
            ArrayList<EffectAttribute> arrayList;
            Object object42;
            String string2;
            String string3 = string.toString().toLowerCase();
            String string4 = "ItemSets." + string + ".";
            String string5 = ChatColor.stripColor((String)ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string4) + "Name")));
            String string6 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string4) + "Prefix"));
            String string7 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string4) + "Suffix"));
            String string8 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string4) + "Color.Have"));
            String string9 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string4) + "Color.Miss"));
            HashMap<PartType, SetPart> hashMap = new HashMap<PartType, SetPart>();
            for (Object object22 : fileConfiguration.getConfigurationSection(String.valueOf(string4) + "Parts").getKeys(false)) {
                PartType partType;
                String string10 = object22.toString().toUpperCase();
                try {
                    partType = PartType.valueOf(string10);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    ErrorLog.sendError(this, String.valueOf(string4) + "Parts." + (String)string10, "Invalid Part Type!", false);
                    continue;
                }
                string2 = String.valueOf(string4) + "Parts." + (String)string10 + ".";
                boolean bl = fileConfiguration.getBoolean(String.valueOf(string2) + "Enabled");
                object32 = fileConfiguration.getString(String.valueOf(string2) + "Material");
                arrayList = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Name"));
                object42 = new SetPart(bl, (String)object32, (String)((Object)arrayList));
                hashMap.put(partType, (SetPart)object42);
            }
            object22 = new TreeMap();
            for (Object object5 : fileConfiguration.getConfigurationSection(String.valueOf(string4) + "Effects.Parts").getKeys(false)) {
                int n = Integer.parseInt(object5.toString());
                string2 = String.valueOf(string4) + "Effects.Parts." + n + ".";
                ArrayList<String> arrayList2 = new ArrayList<String>();
                for (Object object32 : fileConfiguration.getStringList(String.valueOf(string2) + "Lore")) {
                    arrayList2.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object32));
                }
                object32 = fileConfiguration.getStringList(String.valueOf(string2) + "Effects");
                arrayList = new ArrayList<EffectAttribute>();
                for (Object object42 : fileConfiguration.getConfigurationSection(String.valueOf(string2) + "Attributes").getKeys(false)) {
                    ItemStat itemStat;
                    EffectAction effectAction;
                    String string11 = object42.toString();
                    String string12 = String.valueOf(string2) + "Attributes." + string11 + ".";
                    try {
                        itemStat = ItemStat.valueOf(string11.toUpperCase());
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        ErrorLog.sendError(this, string12, "Invalid Attribute Type!", false);
                        continue;
                    }
                    try {
                        effectAction = EffectAction.valueOf(fileConfiguration.getString(String.valueOf(string12) + "Action"));
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        ErrorLog.sendError(this, string12, "Invalid Action Type!", false);
                        continue;
                    }
                    String string13 = fileConfiguration.getString(String.valueOf(string12) + "Value");
                    EffectAttribute effectAttribute = new EffectAttribute(itemStat, effectAction, string13);
                    arrayList.add(effectAttribute);
                }
                object42 = new SetPartEffect(n, arrayList2, (List<String>)object32, arrayList);
                object22.put(n, object42);
            }
            object5 = new ItemSet(string3, string5, string6, string7, string8, string9, hashMap, (TreeMap<Integer, SetPartEffect>)object22);
            this.sets.put(string3, (ItemSet)object5);
        }
    }

    private void startTask() {
        this.taskId = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                for (Player player : SetManager.this.plugin.getServer().getOnlinePlayers()) {
                    SetManager.this.applySetPotions(player);
                }
            }
        }, 10L, 80L);
    }

    private void stopTask() {
        this.plugin.getServer().getScheduler().cancelTask(this.taskId);
    }

    public void applySetPotions(Player player) {
        for (ItemSet itemSet : this.getSets()) {
            for (SetPartEffect setPartEffect : itemSet.getPartsEffects().values()) {
                int n = setPartEffect.getPartsAmount();
                int n2 = this.getPartsOf((LivingEntity)player, itemSet);
                if (n2 < n) continue;
                for (String string : setPartEffect.getEffects()) {
                    String[] arrstring = string.split(":");
                    PotionEffectType potionEffectType = PotionEffectType.getByName((String)arrstring[0].toUpperCase());
                    if (potionEffectType == null) continue;
                    if (player.hasPotionEffect(potionEffectType)) {
                        player.removePotionEffect(potionEffectType);
                    }
                    int n3 = Math.max(0, Integer.parseInt(arrstring[1]) - 1);
                    PotionEffect potionEffect = new PotionEffect(potionEffectType, 100, n3);
                    player.addPotionEffect(potionEffect);
                }
            }
        }
    }

    public HashMap<ItemStat, Double> getSetAttributes(LivingEntity livingEntity, ItemSet itemSet, boolean bl) {
        HashMap<ItemStat, Double> hashMap = new HashMap<ItemStat, Double>();
        for (SetPartEffect setPartEffect : itemSet.getPartsEffects().values()) {
            if (setPartEffect.getAttributes().isEmpty()) continue;
            int n = setPartEffect.getPartsAmount();
            int n2 = this.getPartsOf(livingEntity, itemSet);
            if (n2 < n) continue;
            for (EffectAttribute effectAttribute : setPartEffect.getAttributes()) {
                if (bl && !effectAttribute.getValue().endsWith("%") || !bl && effectAttribute.getValue().endsWith("%")) continue;
                double d = Double.parseDouble(effectAttribute.getValue().replace("%", ""));
                if (effectAttribute.getAction() == EffectAction.MINUS) {
                    d = - d;
                }
                if (hashMap.containsKey((Object)effectAttribute.getType())) {
                    d += hashMap.get((Object)effectAttribute.getType()).doubleValue();
                }
                hashMap.put(effectAttribute.getType(), d);
            }
        }
        return hashMap;
    }

    public boolean isItemOfSet(ItemStack itemStack) {
        if (!(itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().hasLore())) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        String string = itemMeta.getDisplayName();
        PartType partType = this.getItemType(itemStack);
        for (ItemSet itemSet : this.getSets()) {
            if (!itemSet.getParts().containsKey((Object)partType) || !itemSet.getParts().get((Object)partType).isEnabled() || !itemSet.getParts().get((Object)partType).getMaterial().equalsIgnoreCase(itemStack.getType().name())) continue;
            String string2 = itemSet.getPrefix();
            String string3 = itemSet.getSuffix();
            String string4 = itemSet.getParts().get((Object)partType).getName().replace("%suffix%", string3).replace("%prefix%", string2);
            string4.trim().replaceAll("\\s+", " ");
            if (!string.equalsIgnoreCase(string4)) continue;
            return true;
        }
        return false;
    }

    public ItemSet getItemSet(ItemStack itemStack) {
        if (!(itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().hasLore())) {
            return null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        String string = itemMeta.getDisplayName();
        for (ItemSet itemSet : this.getSets()) {
            String string2 = itemSet.getPrefix();
            String string3 = itemSet.getSuffix();
            if (!string.startsWith(string2) || !string.endsWith(string3)) continue;
            return itemSet;
        }
        return null;
    }

    public ItemStack replaceLore(ItemStack itemStack) {
        if (!(itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().hasLore())) {
            return itemStack;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>(itemMeta.getLore());
        for (String string : itemMeta.getLore()) {
            if (!string.contains("%SET%") || !this.isItemOfSet(itemStack)) continue;
            ItemSet itemSet = this.getItemSet(itemStack);
            if (itemSet == null) {
                return itemStack;
            }
            int n = itemMeta.getLore().indexOf(string);
            arrayList.remove(n);
            arrayList.add(n, this.ss.getLoreFooter());
            List list = Lists.reverse(new ArrayList<String>(this.ss.getLore()));
            for (String string2 : list) {
                Object object;
                Object object2;
                String string3;
                if (string2.contains("%parts%")) {
                    Object object3 = Lists.reverse(new ArrayList<SetPart>(itemSet.getParts().values()));
                    object = object3.iterator();
                    while (object.hasNext()) {
                        SetPart setPart = (SetPart)object.next();
                        string3 = setPart.getName().replace("%suffix%", itemSet.getSuffix()).replace("%prefix%", itemSet.getPrefix());
                        object2 = ChatColor.stripColor((String)string3);
                        String string4 = this.getSettings().getItemMissStr().replace("%c%", itemSet.getColorMiss()).replace("%name%", (CharSequence)object2);
                        arrayList.add(n, string4);
                    }
                    continue;
                }
                if (string2.contains("%effects%")) {
                    for (Object object3 : Lists.reverse(new ArrayList<SetPartEffect>(itemSet.getPartsEffects().values()))) {
                        object = Lists.reverse(new ArrayList<String>(object3.getLore()));
                        object2 = object.iterator();
                        while (object2.hasNext()) {
                            string3 = (String)object2.next();
                            arrayList.add(n, string3.replace("%c%", itemSet.getColorMiss()));
                        }
                    }
                    continue;
                }
                arrayList.add(n, string2.replace("%set%", itemSet.getName()));
            }
            arrayList.add(n, this.ss.getLoreHeader());
            itemMeta.setLore(arrayList);
            itemStack.setItemMeta(itemMeta);
            break;
        }
        return itemStack;
    }

    public void updateSets(Player player) {
        ItemStack[] arritemStack = EntityAPI.getEquipment((LivingEntity)player, false);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack[n2];
            this.updateLore(player, itemStack);
            ++n2;
        }
        player.updateInventory();
    }

    public void updateLore(Player player, ItemStack itemStack) {
        if (!(itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().hasLore())) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>(itemMeta.getLore());
        if (!arrayList.contains(this.ss.getLoreHeader())) {
            return;
        }
        if (!arrayList.contains(this.ss.getLoreFooter())) {
            return;
        }
        if (!this.isItemOfSet(itemStack)) {
            return;
        }
        ItemSet itemSet = this.getItemSet(itemStack);
        int n = arrayList.indexOf(this.ss.getLoreHeader());
        int n2 = arrayList.indexOf(this.ss.getLoreFooter());
        int n3 = n2 - n + 1;
        while (n3 > 0) {
            arrayList.remove(n);
            --n3;
        }
        arrayList.add(n, this.ss.getLoreFooter());
        List list = Lists.reverse(new ArrayList<String>(this.ss.getLore()));
        for (String string : list) {
            String string2;
            Object object;
            String string3;
            Object object2;
            Iterator<SetPart> iterator;
            if (string.contains("%parts%")) {
                Object object3 = new ArrayList<SetPart>(itemSet.getParts().values());
                object3 = Lists.reverse(object3);
                iterator = object3.iterator();
                while (iterator.hasNext()) {
                    SetPart setPart = iterator.next();
                    object2 = setPart.getName().replace("%suffix%", itemSet.getSuffix()).replace("%prefix%", itemSet.getPrefix());
                    if (this.hasSetItem((LivingEntity)player, (String)object2)) {
                        object = this.ss.getItemHaveStr();
                        string2 = itemSet.getColorHave();
                    } else {
                        object = this.ss.getItemMissStr();
                        string2 = itemSet.getColorMiss();
                    }
                    string3 = object.replace("%c%", string2).replace("%name%", ChatColor.stripColor((String)object2));
                    arrayList.add(n, string3);
                }
                continue;
            }
            if (string.contains("%effects%")) {
                for (Object object3 : Lists.reverse(new ArrayList<SetPartEffect>(itemSet.getPartsEffects().values()))) {
                    iterator = (Iterator<SetPart>)object3.getPartsAmount();
                    object2 = this.getPartsOf((LivingEntity)player, itemSet);
                    string2 = object2 >= iterator ? itemSet.getColorHave() : itemSet.getColorMiss();
                    object = Lists.reverse(new ArrayList<String>(object3.getLore()));
                    Iterator iterator2 = object.iterator();
                    while (iterator2.hasNext()) {
                        string3 = (String)iterator2.next();
                        arrayList.add(n, string3.replace("%c%", string2));
                    }
                }
                continue;
            }
            arrayList.add(n, string.replace("%set%", itemSet.getName()));
        }
        arrayList.add(n, this.ss.getLoreHeader());
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
    }

    public ItemStack resetSet(ItemStack itemStack) {
        if (!(itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().hasLore())) {
            return itemStack;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>(itemMeta.getLore());
        if (!arrayList.contains(this.ss.getLoreHeader())) {
            return itemStack;
        }
        if (!arrayList.contains(this.ss.getLoreFooter())) {
            return itemStack;
        }
        if (!this.isItemOfSet(itemStack)) {
            return itemStack;
        }
        ItemSet itemSet = this.getItemSet(itemStack);
        int n = arrayList.indexOf(this.ss.getLoreHeader());
        int n2 = arrayList.indexOf(this.ss.getLoreFooter());
        int n3 = n2 - n + 1;
        while (n3 > 0) {
            arrayList.remove(n);
            --n3;
        }
        arrayList.add(n, this.ss.getLoreFooter());
        List list = Lists.reverse(new ArrayList<String>(this.ss.getLore()));
        for (String string : list) {
            Object object;
            Object object2;
            String string2;
            if (string.contains("%parts%")) {
                Object object3 = Lists.reverse(new ArrayList<SetPart>(itemSet.getParts().values()));
                object = object3.iterator();
                while (object.hasNext()) {
                    SetPart setPart = (SetPart)object.next();
                    string2 = setPart.getName().replace("%suffix%", itemSet.getSuffix()).replace("%prefix%", itemSet.getPrefix());
                    object2 = itemSet.getColorMiss();
                    String string3 = this.ss.getItemMissStr();
                    String string4 = string3.replace("%c%", (CharSequence)object2).replace("%name%", ChatColor.stripColor((String)string2));
                    arrayList.add(n, string4);
                }
                continue;
            }
            if (string.contains("%effects%")) {
                for (Object object3 : Lists.reverse(new ArrayList<SetPartEffect>(itemSet.getPartsEffects().values()))) {
                    object = Lists.reverse(new ArrayList<String>(object3.getLore()));
                    object2 = object.iterator();
                    while (object2.hasNext()) {
                        string2 = (String)object2.next();
                        arrayList.add(n, string2.replace("%c%", itemSet.getColorMiss()));
                    }
                }
                continue;
            }
            arrayList.add(n, string.replace("%set%", itemSet.getName()));
        }
        arrayList.add(n, this.ss.getLoreHeader());
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public int getPartsOf(LivingEntity livingEntity, ItemSet itemSet) {
        int n = 0;
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, false);
        int n2 = arritemStack.length;
        int n3 = 0;
        while (n3 < n2) {
            ItemSet itemSet2;
            ItemStack itemStack = arritemStack[n3];
            if (this.isItemOfSet(itemStack) && (itemSet2 = this.getItemSet(itemStack)) != null && itemSet2.getId().equalsIgnoreCase(itemSet.getId())) {
                ++n;
            }
            ++n3;
        }
        return n;
    }

    public boolean hasSetItem(LivingEntity livingEntity, String string) {
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, false);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemMeta itemMeta;
            ItemStack itemStack = arritemStack[n2];
            if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().hasLore() && (itemMeta = itemStack.getItemMeta()).getDisplayName().equalsIgnoreCase(string)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public PartType getItemType(ItemStack itemStack) {
        String string = itemStack.getType().name();
        if (string.endsWith("_CHESTPLATE") || string.equals("ELYTRA")) {
            return PartType.CHESTPLATE;
        }
        if (string.endsWith("_LEGGINGS")) {
            return PartType.LEGGINGS;
        }
        if (string.endsWith("_BOOTS")) {
            return PartType.BOOTS;
        }
        if (string.endsWith("_HELMET") || string.equals("SKULL_ITEM")) {
            return PartType.HELMET;
        }
        if (string.equals("SHIELD")) {
            return PartType.OFF_HAND;
        }
        return PartType.MAIN_HAND;
    }

    public SetSettings getSettings() {
        return this.ss;
    }

    public Collection<ItemSet> getSets() {
        if (!this.isActive()) {
            return new ArrayList<ItemSet>();
        }
        return this.sets.values();
    }

    public ItemSet getSetById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<ItemSet>(this.getSets()).get(this.getSets().size() - 1);
        }
        return this.sets.get(string.toLowerCase());
    }

    public List<String> getSetNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (ItemSet itemSet : this.getSets()) {
            arrayList.add(itemSet.getId());
        }
        return arrayList;
    }

    @EventHandler(ignoreCancelled=true)
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        final Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (inventoryClickEvent.getInventory().getType() != InventoryType.CRAFTING) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.CRAFTING) {
            return;
        }
        if (itemStack != null && itemStack.getType() != Material.AIR) {
            inventoryClickEvent.setCurrentItem(this.resetSet(itemStack));
        }
        new BukkitRunnable(){

            public void run() {
                SetManager.this.updateSets(player);
            }
        }.runTaskLater((Plugin)this.plugin, 1L);
    }

    @EventHandler(ignoreCancelled=true)
    public void onSpawn(ItemSpawnEvent itemSpawnEvent) {
        Item item = itemSpawnEvent.getEntity();
        ItemStack itemStack = item.getItemStack();
        itemStack = this.replaceLore(itemStack);
        itemStack = this.resetSet(itemStack);
        item.setItemStack(itemStack);
    }

    public static enum EffectAction {
        PLUS,
        MINUS,
        MULTIPLICATION,
        DIVISION;
        

        private EffectAction(String string2, int n2) {
        }
    }

    public class EffectAttribute {
        private ItemStat type;
        private EffectAction act;
        private String value;

        public EffectAttribute(ItemStat itemStat, EffectAction effectAction, String string) {
            this.setType(itemStat);
            this.setAction(effectAction);
            this.setValue(string);
        }

        public ItemStat getType() {
            return this.type;
        }

        public void setType(ItemStat itemStat) {
            this.type = itemStat;
        }

        public EffectAction getAction() {
            return this.act;
        }

        public void setAction(EffectAction effectAction) {
            this.act = effectAction;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String string) {
            this.value = string;
        }
    }

    public class ItemSet {
        private String id;
        private String name;
        private String prefix;
        private String suffix;
        private String color_have;
        private String color_miss;
        private HashMap<PartType, SetPart> parts;
        private TreeMap<Integer, SetPartEffect> parts_eff;

        public ItemSet(String string, String string2, String string3, String string4, String string5, String string6, HashMap<PartType, SetPart> hashMap, TreeMap<Integer, SetPartEffect> treeMap) {
            this.setId(string);
            this.setName(string2);
            this.setPrefix(string3);
            this.setSuffix(string4);
            this.setColorHave(string5);
            this.setColorMiss(string6);
            this.setParts(hashMap);
            this.setPartsEffects(treeMap);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String string) {
            this.name = string;
        }

        public String getPrefix() {
            return this.prefix;
        }

        public void setPrefix(String string) {
            this.prefix = string;
        }

        public String getSuffix() {
            return this.suffix;
        }

        public void setSuffix(String string) {
            this.suffix = string;
        }

        public String getColorHave() {
            return this.color_have;
        }

        public void setColorHave(String string) {
            this.color_have = string;
        }

        public String getColorMiss() {
            return this.color_miss;
        }

        public void setColorMiss(String string) {
            this.color_miss = string;
        }

        public HashMap<PartType, SetPart> getParts() {
            return this.parts;
        }

        public void setParts(HashMap<PartType, SetPart> hashMap) {
            this.parts = hashMap;
        }

        public TreeMap<Integer, SetPartEffect> getPartsEffects() {
            return this.parts_eff;
        }

        public void setPartsEffects(TreeMap<Integer, SetPartEffect> treeMap) {
            this.parts_eff = treeMap;
        }

        public ItemStack create(String string) {
            PartType partType;
            Object object;
            if (string.equalsIgnoreCase("random")) {
                object = new ArrayList<PartType>(this.getParts().keySet());
                partType = object.get(new Random().nextInt(object.size()));
            } else {
                partType = PartType.valueOf(string.toUpperCase());
            }
            object = this.getParts().get((Object)partType);
            ItemStack itemStack = new ItemStack(Material.getMaterial((String)object.getMaterial()));
            ItemMeta itemMeta = itemStack.getItemMeta();
            String string2 = object.getName().replace("%suffix%", this.getSuffix()).replace("%prefix%", this.getPrefix());
            string2 = string2.trim().replaceAll("\\s+", " ");
            itemMeta.setDisplayName(string2);
            itemMeta.setLore(Arrays.asList("%SET%"));
            itemStack.setItemMeta(itemMeta);
            itemStack = SetManager.this.replaceLore(itemStack);
            return itemStack;
        }
    }

    public static enum PartType {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        MAIN_HAND,
        OFF_HAND;
        

        private PartType(String string2, int n2) {
        }
    }

    public class SetPart {
        private boolean enabled;
        private String mat;
        private String name;

        public SetPart(boolean bl, String string, String string2) {
            this.setEnabled(bl);
            this.setMaterial(string);
            this.setName(string2);
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean bl) {
            this.enabled = bl;
        }

        public String getMaterial() {
            return this.mat;
        }

        public void setMaterial(String string) {
            this.mat = string;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String string) {
            this.name = string;
        }
    }

    public class SetPartEffect {
        private int amount;
        private List<String> lore;
        private List<String> eff;
        private List<EffectAttribute> att;

        public SetPartEffect(int n, List<String> list, List<String> list2, List<EffectAttribute> list3) {
            this.setPartsAmount(n);
            this.setLore(list);
            this.setEffects(list2);
            this.setAttributes(list3);
        }

        public int getPartsAmount() {
            return this.amount;
        }

        public void setPartsAmount(int n) {
            this.amount = n;
        }

        public List<String> getLore() {
            return this.lore;
        }

        public void setLore(List<String> list) {
            this.lore = list;
        }

        public List<String> getEffects() {
            return this.eff;
        }

        public void setEffects(List<String> list) {
            this.eff = list;
        }

        public List<EffectAttribute> getAttributes() {
            return this.att;
        }

        public void setAttributes(List<EffectAttribute> list) {
            this.att = list;
        }
    }

    public class SetSettings {
        private boolean dynamic;
        private String i_have;
        private String i_miss;
        private String l_head;
        private String l_foot;
        private List<String> lore;

        public SetSettings(boolean bl, String string, String string2, String string3, String string4, List<String> list) {
            this.setDynamic(bl);
            this.setItemHaveStr(string);
            this.setItemMissStr(string2);
            this.setLoreHeader(string3);
            this.setLoreFooter(string4);
            this.setLore(list);
        }

        public boolean isDynamic() {
            return this.dynamic;
        }

        public void setDynamic(boolean bl) {
            this.dynamic = bl;
        }

        public String getItemHaveStr() {
            return this.i_have;
        }

        public void setItemHaveStr(String string) {
            this.i_have = string;
        }

        public String getItemMissStr() {
            return this.i_miss;
        }

        public void setItemMissStr(String string) {
            this.i_miss = string;
        }

        public String getLoreHeader() {
            return this.l_head;
        }

        public void setLoreHeader(String string) {
            this.l_head = string;
        }

        public String getLoreFooter() {
            return this.l_foot;
        }

        public void setLoreFooter(String string) {
            this.l_foot = string;
        }

        public List<String> getLore() {
            return this.lore;
        }

        public void setLore(List<String> list) {
            this.lore = list;
        }
    }

}

