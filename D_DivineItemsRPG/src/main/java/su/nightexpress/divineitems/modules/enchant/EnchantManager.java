/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.apache.commons.lang.ArrayUtils
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LightningStrike
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPistonRetractEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.enchantment.EnchantItemEvent
 *  org.bukkit.event.enchantment.PrepareItemEnchantEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.BlockStateMeta
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.inventory.meta.SkullMeta
 *  org.bukkit.material.MaterialData
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package su.nightexpress.divineitems.modules.enchant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.EnchantsCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.gui.ContentType;
import su.nightexpress.divineitems.gui.GUI;
import su.nightexpress.divineitems.gui.GUIItem;
import su.nightexpress.divineitems.gui.GUIManager;
import su.nightexpress.divineitems.gui.GUIType;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.hooks.external.VaultHook;
import su.nightexpress.divineitems.hooks.external.WorldGuardHook;
import su.nightexpress.divineitems.hooks.external.citizens.CitizensHook;
import su.nightexpress.divineitems.modules.MainSettings;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.types.DestroyType;
import su.nightexpress.divineitems.types.SlotType;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.ParticleUtils;
import su.nightexpress.divineitems.utils.Utils;

public class EnchantManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private Random r;
    private boolean e;
    private MyConfig settingsCfg;
    private MyConfig enchantsCfg;
    private EnchantSettings es;
    private HashMap<EnchantType, Enchant> enchants;
    private TreeMap<String, Double> table_rate;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_BOOK = "DIVINE_ENCHANT";
    private final String NBT_KEY_ITEM_ENCHANT = "ENCHANT_";
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType;
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType;

    public EnchantManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.enchants = new HashMap();
        this.table_rate = new TreeMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.enchantsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "enchants.yml");
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
        return true;
    }

    @Override
    public String name() {
        return "Enchants";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new EnchantsCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.enchants.clear();
            this.table_rate.clear();
            this.es = null;
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    private void setup() {
        this.setupSettings();
        this.setupSlot();
        this.setupEnchants();
        this.setupTableRates();
    }

    private void setupSlot() {
        SlotType slotType = SlotType.ENCHANT;
        slotType.setModule(this);
        slotType.setHeader(this.getSettings().getHeader());
        slotType.setEmpty(this.getSettings().getEmptySlot());
        slotType.setFilled(this.getSettings().getFilledSlot());
    }

    private void setupSettings() {
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        String string = "Item.";
        List list = fileConfiguration.getStringList(String.valueOf(string) + "Materials");
        String string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "Display"));
        List list2 = fileConfiguration.getStringList(String.valueOf(string) + "Lore");
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
        string = "Effects.";
        boolean bl = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
        String string3 = fileConfiguration.getString(String.valueOf(string) + "Failure");
        String string4 = fileConfiguration.getString(String.valueOf(string) + "Success");
        string = "Sounds.";
        boolean bl2 = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
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
        string = "EnchantmentTable.";
        boolean bl3 = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
        double d = fileConfiguration.getDouble(String.valueOf(string) + "ChanceToGet");
        this.es = new EnchantSettings(list, string2, list2, n, n2, destroyType, bl, string3, string4, bl2, sound, sound2, string5, string6, string7, bl3, d);
    }

    private void setupEnchants() {
        FileConfiguration fileConfiguration = this.enchantsCfg.getConfig();
        for (String string : fileConfiguration.getConfigurationSection("Enchants").getKeys(false)) {
            EnchantType enchantType;
            String string2 = "Enchants." + string + ".";
            boolean bl = fileConfiguration.getBoolean(String.valueOf(string2) + "Enabled");
            if (!bl) continue;
            String string3 = string;
            try {
                enchantType = EnchantType.valueOf(string3.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                ErrorLog.sendError(this, String.valueOf(string2) + string3, "Invalid Enchantment Type!", false);
                continue;
            }
            String string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Name"));
            List list = fileConfiguration.getStringList(String.valueOf(string2) + "Desc");
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String string5 : list) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string5));
            }
            double d = fileConfiguration.getDouble(String.valueOf(string2) + "Value");
            double d2 = fileConfiguration.getDouble(String.valueOf(string2) + "ValuePerLvl");
            int n = fileConfiguration.getInt(String.valueOf(string2) + "PlayerTableLevel");
            int n2 = fileConfiguration.getInt(String.valueOf(string2) + "LevelMin");
            int n3 = fileConfiguration.getInt(String.valueOf(string2) + "LevelMax");
            double d3 = fileConfiguration.getDouble(String.valueOf(string2) + "TableChance");
            ArrayList<String> arrayList2 = new ArrayList<String>(fileConfiguration.getStringList(String.valueOf(string2) + "ItemTypes"));
            ArrayList<String> arrayList3 = new ArrayList<String>();
            Enchant enchant = new Enchant(bl, string3.toUpperCase(), string4, arrayList, d, d2, n, n2, n3, d3, arrayList2, arrayList3);
            this.enchants.put(enchantType, enchant);
        }
    }

    private void setupTableRates() {
        TreeMap<String, Double> treeMap = new TreeMap<String, Double>();
        for (Enchant enchant : this.getEnchants()) {
            if (!enchant.isEnabled()) continue;
            treeMap.put(enchant.getId(), enchant.getTableRate());
        }
        this.table_rate = treeMap;
    }

    public boolean haveEnchant(ItemStack itemStack, EnchantType enchantType) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            EnchantType enchantType2;
            String[] arrstring;
            if (!string.startsWith("ENCHANT_") || enchantType != (enchantType2 = EnchantType.valueOf((arrstring = nBTItem.getString(string).split(":"))[0]))) continue;
            return true;
        }
        return false;
    }

    public int getItemEnchantLevel(ItemStack itemStack, EnchantType enchantType) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string.startsWith("ENCHANT_") || !(arrstring = nBTItem.getString(string).split(":"))[0].equalsIgnoreCase(enchantType.name())) continue;
            return Integer.parseInt(arrstring[1]);
        }
        return 0;
    }

    public String getEnchantBookId(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String[] arrstring = nBTItem.getString("DIVINE_ENCHANT").split(":");
        return arrstring[0];
    }

    public int getEnchantBookLevel(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String[] arrstring = nBTItem.getString("DIVINE_ENCHANT").split(":");
        return Integer.parseInt(arrstring[1]);
    }

    private double getEnchantBookValue(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String[] arrstring = nBTItem.getString("DIVINE_ENCHANT").split(":");
        return Double.parseDouble(arrstring[2]);
    }

    public EnchantSettings getSettings() {
        return this.es;
    }

    public Collection<Enchant> getEnchants() {
        return this.enchants.values();
    }

    public List<String> getEnchantNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Enchant enchant : this.getEnchants()) {
            arrayList.add(enchant.getId());
        }
        return arrayList;
    }

    public Enchant getEnchantByType(EnchantType enchantType) {
        return this.enchants.get((Object)enchantType);
    }

    public Enchant getRandomEnchant() {
        return new ArrayList<Enchant>(this.getEnchants()).get(this.r.nextInt(this.getEnchants().size()));
    }

    private ItemStack getResult(ItemStack itemStack, ItemStack itemStack2) {
        ItemStack itemStack3 = this.enchantItem(itemStack, itemStack2);
        ItemMeta itemMeta = itemStack3.getItemMeta();
        itemMeta.setDisplayName(Lang.Enchants_Enchanting_Result.toMsg());
        itemStack3.setItemMeta(itemMeta);
        return itemStack3;
    }

    private void openGUI(Player player, ItemStack itemStack, ItemStack itemStack2) {
        GUI gUI = new GUI(this.plugin.getGUIManager().getGUIByType(GUIType.ENCHANT_BOOK));
        gUI.getItems().get((Object)ContentType.TARGET).setItem(itemStack);
        gUI.getItems().get((Object)ContentType.SOURCE).setItem(itemStack2);
        gUI.getItems().get((Object)ContentType.RESULT).setItem(this.getResult(new ItemStack(itemStack), new ItemStack(itemStack2)));
        this.plugin.getGUIManager().setGUI(player, gUI);
        player.openInventory(gUI.build());
    }

    private ItemStack enchantItem(ItemStack itemStack, ItemStack itemStack2) {
        Object object2;
        String string = this.getEnchantBookId(itemStack2);
        int n = this.getEnchantBookLevel(itemStack2);
        double d = this.getEnchantBookValue(itemStack2);
        EnchantType enchantType = EnchantType.valueOf(string);
        Enchant enchant = this.getEnchantByType(enchantType);
        List list = itemStack.getItemMeta().getLore();
        String string2 = String.valueOf(this.getSettings().getFilledSlot()) + enchant.getName().replace("%level%", new StringBuilder(String.valueOf(n)).toString()).replace("%rlevel%", Utils.IntegerToRomanNumeral(n));
        if (this.haveEnchant(itemStack, enchantType)) {
            for (Object object2 : list) {
                if (!object2.contains(string)) continue;
                int n2 = list.indexOf(object2);
                list.remove(n2);
                list.add(n2, string2);
                break;
            }
            itemStack = this.removeEnchant(itemStack, string);
        } else {
            for (Object object2 : list) {
                String string3;
                String string4 = ChatColor.stripColor((String)object2);
                if (!string4.equals(string3 = ChatColor.stripColor((String)this.getSettings().getEmptySlot()))) continue;
                int n3 = list.indexOf(object2);
                list.remove(n3);
                list.add(n3, string2);
                break;
            }
        }
        object2 = itemStack.getItemMeta();
        object2.setLore(list);
        itemStack.setItemMeta((ItemMeta)object2);
        NBTItem nBTItem = new NBTItem(itemStack);
        nBTItem.setString("ENCHANT_" + this.getItemEnchantAmount(itemStack), String.valueOf(string) + ":" + n + ":" + d);
        return nBTItem.getItem();
    }

    public int getItemEnchantAmount(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        int n = 0;
        for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("ENCHANT_")) continue;
            ++n;
        }
        return n;
    }

    public ItemStack removeEnchant(ItemStack itemStack, String string) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string2 : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string2.startsWith("ENCHANT_") || !(arrstring = nBTItem.getString(string2).split(":"))[0].equalsIgnoreCase(string)) continue;
            nBTItem.removeKey(string2);
            break;
        }
        return nBTItem.getItem();
    }

    public ItemStack removeEnchant(ItemStack itemStack, int n) {
        int n2;
        NBTItem nBTItem = new NBTItem(itemStack);
        int n3 = this.getItemEnchantAmount(itemStack);
        int n4 = n - 1;
        int n5 = n2 = n3 - n4;
        if (n4 == 0) {
            --n5;
        }
        nBTItem.removeKey("ENCHANT_" + n5);
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
                arrayList.add(n7, this.es.getEmptySlot());
                break;
            }
            ++n7;
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public boolean isEnchant(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_ENCHANT");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        String string2;
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCursor();
        ItemStack itemStack2 = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || itemStack2 == null) {
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
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (!itemStack2.hasItemMeta() || !itemStack2.getItemMeta().hasLore()) {
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        if (!nBTItem.hasKey("DIVINE_ENCHANT").booleanValue()) {
            return;
        }
        String[] arrstring = nBTItem.getString("DIVINE_ENCHANT").split(":");
        EnchantType enchantType = EnchantType.valueOf(arrstring[0]);
        int n = Integer.parseInt(arrstring[1]);
        Enchant enchant = this.getEnchantByType(enchantType);
        if (enchant == null) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_Internal.toMsg());
            return;
        }
        boolean bl = false;
        for (String string2 : enchant.getItemTypes()) {
            if (!ItemUtils.isValidItemType(string2, itemStack2)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_InvalidType.toMsg());
            return;
        }
        if (this.haveEnchant(itemStack2, enchantType) && this.getItemEnchantLevel(itemStack2, enchantType) >= n) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_AlreadyHave.toMsg());
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_FullInventory.toMsg());
            return;
        }
        string2 = enchant.getName().replace("%level%", "").trim();
        for (Object object : itemStack2.getItemMeta().getLore()) {
            String string3;
            String string4 = ChatColor.stripColor((String)object);
            if (!string4.equalsIgnoreCase(string3 = ChatColor.stripColor((String)this.getSettings().getEmptySlot())) && !string4.contains(string2)) continue;
            inventoryClickEvent.setCursor(null);
            player.getInventory().addItem(new ItemStack[]{itemStack});
            inventoryClickEvent.setCancelled(true);
            this.openGUI(player, itemStack2, itemStack);
            return;
        }
        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_NoSlots.toMsg());
    }

    @EventHandler
    public void onPreEnchant(PrepareItemEnchantEvent prepareItemEnchantEvent) {
        if (!this.getSettings().isTableEnabled()) {
            return;
        }
        ItemStack itemStack = prepareItemEnchantEvent.getItem();
        if (itemStack == null) {
            return;
        }
        String string = String.valueOf(itemStack.getType().name()) + ":" + itemStack.getData().getData();
        if (!this.getSettings().getMaterials().contains(string)) {
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        if (nBTItem.hasKey("DIVINE_ENCHANT").booleanValue()) {
            prepareItemEnchantEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent enchantItemEvent) {
        if (!this.getSettings().isTableEnabled()) {
            return;
        }
        Player player = enchantItemEvent.getEnchanter();
        ItemStack itemStack = enchantItemEvent.getItem();
        if (itemStack == null) {
            return;
        }
        String string = String.valueOf(itemStack.getType().name()) + ":" + itemStack.getData().getData();
        if (!this.getSettings().getMaterials().contains(string)) {
            return;
        }
        ArrayList<Enchant> arrayList = new ArrayList<Enchant>();
        double d = 0.0;
        double d2 = Utils.getRandDouble(0.0, 100.0) / 100.0;
        if (d2 <= this.getSettings().getTableChance()) {
            Enchant enchant;
            double d3 = Utils.getRandDouble(0.0, 100.0) / 100.0;
            for (String object2 : this.table_rate.keySet()) {
                enchant = this.getEnchantByType(EnchantType.valueOf(object2));
                if (player.getLevel() < enchant.getEnchantLevel() || d3 > enchant.getTableRate()) continue;
                d = enchant.getTableRate();
                break;
            }
            for (String string2 : this.table_rate.keySet()) {
                enchant = this.getEnchantByType(EnchantType.valueOf(string2));
                if (player.getLevel() < enchant.getEnchantLevel() || enchant.getTableRate() != d) continue;
                arrayList.add(enchant);
            }
            if (arrayList.isEmpty()) {
                return;
            }
            Enchant enchant2 = (Enchant)arrayList.get(new Random().nextInt(arrayList.size()));
            player.setLevel(player.getLevel() - enchantItemEvent.getExpLevelCost());
            int n = enchant2.getMinLevel();
            int n2 = enchant2.getMaxLevel();
            int n3 = Utils.randInt(n, n2);
            enchantItemEvent.getInventory().setItem(0, new ItemStack(enchant2.create(n3)));
            enchantItemEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onClickGUI(InventoryClickEvent inventoryClickEvent) {
        Object object;
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (!this.plugin.getGUIManager().valid(player, GUIType.ENCHANT_BOOK)) {
            return;
        }
        GUI gUI = this.plugin.getGUIManager().getPlayerGUI(player, GUIType.ENCHANT_BOOK);
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
            object = new ItemStack(gUI.getItems().get((Object)ContentType.TARGET).getItem());
            ItemStack itemStack2 = new ItemStack(gUI.getItems().get((Object)ContentType.SOURCE).getItem());
            if (!player.getInventory().contains((ItemStack)object)) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_NoItem.toMsg());
                return;
            }
            int n2 = new Random().nextInt(100);
            NBTItem nBTItem = new NBTItem(itemStack2);
            int n3 = nBTItem.getInteger("DIVINE_CHANCE");
            EnchantSettings enchantSettings = this.getSettings();
            if (n3 < n2) {
                switch (EnchantManager.$SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType()[enchantSettings.getDestroyType().ordinal()]) {
                    case 1: {
                        player.getInventory().removeItem(new ItemStack[]{object});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_Failure_Item.toMsg());
                        break;
                    }
                    case 2: {
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        itemStack2.setAmount(itemStack2.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack2});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_Failure_Source.toMsg());
                        break;
                    }
                    case 4: {
                        player.getInventory().removeItem(new ItemStack[]{object});
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        itemStack2.setAmount(itemStack2.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack2});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_Failure_Both.toMsg());
                        break;
                    }
                    case 3: {
                        Object object22;
                        player.getInventory().removeItem(new ItemStack[]{object});
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        itemStack2.setAmount(itemStack2.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack2});
                        ItemMeta itemMeta = object.getItemMeta();
                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (Object object22 : itemMeta.getLore()) {
                            if (object22.startsWith(enchantSettings.getFilledSlot())) {
                                arrayList.add(enchantSettings.getEmptySlot());
                                continue;
                            }
                            arrayList.add((String)object22);
                        }
                        itemMeta.setLore(arrayList);
                        object.setItemMeta(itemMeta);
                        object22 = new NBTItem((ItemStack)object);
                        for (String string : object22.getKeys()) {
                            if (!string.startsWith("ENCHANT_")) continue;
                            object22.removeKey(string);
                        }
                        player.getInventory().addItem(new ItemStack[]{object22.getItem()});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_Failure_Clear.toMsg());
                    }
                }
                player.closeInventory();
                this.plugin.getGUIManager().reset(player);
                if (enchantSettings.useSound()) {
                    player.playSound(player.getLocation(), enchantSettings.getDestroySound(), 0.5f, 0.5f);
                }
                if (enchantSettings.useEffect()) {
                    Utils.playEffect(enchantSettings.getDestroyEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
                }
                return;
            }
            player.getInventory().removeItem(new ItemStack[]{object});
            ItemStack itemStack3 = this.enchantItem((ItemStack)object, itemStack2);
            player.getInventory().addItem(new ItemStack[]{itemStack3});
            player.getInventory().removeItem(new ItemStack[]{itemStack2});
            itemStack2.setAmount(itemStack2.getAmount() - 1);
            player.getInventory().addItem(new ItemStack[]{itemStack2});
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_Success.toMsg());
            player.closeInventory();
            this.plugin.getGUIManager().reset(player);
            if (enchantSettings.useSound()) {
                player.playSound(player.getLocation(), enchantSettings.getSuccessSound(), 0.5f, 0.5f);
            }
            if (enchantSettings.useEffect()) {
                Utils.playEffect(enchantSettings.getSuccessEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
            }
            player.updateInventory();
        }
        if (itemStack.isSimilar((object = gUI.getItems().get((Object)ContentType.DECLINE)).getItem()) || ArrayUtils.contains((int[])object.getSlots(), (int)n)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Enchanting_Cancel.toMsg());
            player.closeInventory();
            player.updateInventory();
            this.plugin.getGUIManager().reset(player);
        }
    }

    @EventHandler
    public void onEDeath(EntityDeathEvent entityDeathEvent) {
        Player player = entityDeathEvent.getEntity().getKiller();
        if (player == null) {
            return;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("ENCHANT_") || !this.isValidEnchant(string, nBTItem)) continue;
            EnchantType enchantType = EnchantType.valueOf(nBTItem.getString(string).split(":")[0]);
            int n = Integer.parseInt(nBTItem.getString(string).split(":")[1]);
            double d = Double.parseDouble(nBTItem.getString(string).split(":")[2]);
            switch (EnchantManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType()[enchantType.ordinal()]) {
                case 1: {
                    this.getExecute(d, (Entity)entityDeathEvent.getEntity());
                    break;
                }
                case 9: {
                    entityDeathEvent.setDroppedExp(this.getExpHunter(n, d, entityDeathEvent.getDroppedExp()));
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onArmorEnch2(PlayerMoveEvent playerMoveEvent) {
        ItemStack[] arritemStack;
        Player player = playerMoveEvent.getPlayer();
        Location location = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1.0, player.getLocation().getZ());
        Block block = location.getBlock();
        if (!block.getType().name().contains("LAVA")) {
            return;
        }
        ItemStack[] arritemStack2 = arritemStack = EntityAPI.getEquipment((LivingEntity)player, true);
        int n = arritemStack2.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack2[n2];
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                NBTItem nBTItem = new NBTItem(itemStack);
                for (String string : nBTItem.getKeys()) {
                    if (!string.startsWith("ENCHANT_") || !this.isValidEnchant(string, nBTItem)) continue;
                    EnchantType enchantType = EnchantType.valueOf(nBTItem.getString(string).split(":")[0]);
                    switch (EnchantManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType()[enchantType.ordinal()]) {
                        case 17: {
                            this.getLavaWalk(block, player);
                            break;
                        }
                    }
                }
            }
            ++n2;
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onArmorEnch3(EntityDamageEvent entityDamageEvent) {
        ItemStack[] arritemStack;
        if (!(entityDamageEvent.getEntity() instanceof Player)) {
            return;
        }
        if (entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.FIRE && entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK && entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.LAVA) {
            return;
        }
        Player player = (Player)entityDamageEvent.getEntity();
        ItemStack[] arritemStack2 = arritemStack = EntityAPI.getEquipment((LivingEntity)player, true);
        int n = arritemStack2.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack2[n2];
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                NBTItem nBTItem = new NBTItem(itemStack);
                for (String string : nBTItem.getKeys()) {
                    if (!string.startsWith("ENCHANT_") || !this.isValidEnchant(string, nBTItem)) continue;
                    EnchantType enchantType = EnchantType.valueOf(nBTItem.getString(string).split(":")[0]);
                    double d = Double.parseDouble(nBTItem.getString(string).split(":")[2]);
                    switch (EnchantManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType()[enchantType.ordinal()]) {
                        case 17: {
                            if ((double)this.r.nextInt(100) >= d) break;
                            entityDamageEvent.setCancelled(true);
                            break;
                        }
                    }
                }
            }
            ++n2;
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onArmorEnch(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        Arrow arrow;
        ItemStack[] arritemStack;
        if (entityDamageByEntityEvent.isCancelled()) {
            return;
        }
        Entity entity = entityDamageByEntityEvent.getEntity();
        Entity entity2 = entityDamageByEntityEvent.getDamager();
        if (entity2 instanceof Arrow) {
            arrow = (Arrow)entity2;
            if (!(arrow.getShooter() instanceof LivingEntity)) {
                return;
            }
            entity2 = (Entity)arrow.getShooter();
        }
        if (!(entity instanceof Player)) {
            return;
        }
        if (!(entity2 instanceof LivingEntity)) {
            return;
        }
        arrow = (Player)entity;
        LivingEntity livingEntity = (LivingEntity)entity2;
        ItemStack[] arritemStack2 = arritemStack = EntityAPI.getEquipment((LivingEntity)arrow, true);
        int n = arritemStack2.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack2[n2];
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                NBTItem nBTItem = new NBTItem(itemStack);
                for (String string : nBTItem.getKeys()) {
                    if (!string.startsWith("ENCHANT_") || !this.isValidEnchant(string, nBTItem)) continue;
                    EnchantType enchantType = EnchantType.valueOf(nBTItem.getString(string).split(":")[0]);
                    int n3 = Integer.parseInt(nBTItem.getString(string).split(":")[1]);
                    double d = Double.parseDouble(nBTItem.getString(string).split(":")[2]);
                    switch (EnchantManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType()[enchantType.ordinal()]) {
                        case 12: {
                            this.getFireSpray(n3, d, livingEntity);
                            break;
                        }
                        case 13: {
                            this.getPunishWave(n3, d, (LivingEntity)arrow);
                            break;
                        }
                        case 14: {
                            this.getPrayVictor(d, (LivingEntity)arrow);
                            break;
                        }
                        case 15: {
                            this.getAmbush(n3, d, (LivingEntity)arrow, livingEntity);
                            break;
                        }
                        case 16: {
                            this.getEternalDenial(n3, d, livingEntity);
                            break;
                        }
                    }
                }
            }
            ++n2;
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onEDamage(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        Arrow arrow;
        Entity entity = entityDamageByEntityEvent.getEntity();
        Entity entity2 = entityDamageByEntityEvent.getDamager();
        if (entity2 instanceof Arrow) {
            arrow = (Arrow)entity2;
            if (!(arrow.getShooter() instanceof LivingEntity)) {
                return;
            }
            entity2 = (Entity)arrow.getShooter();
        }
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        if (!(entity2 instanceof LivingEntity)) {
            return;
        }
        arrow = (LivingEntity)entity2;
        LivingEntity livingEntity = (LivingEntity)entity;
        ItemStack itemStack = arrow.getEquipment().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        block11 : for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("ENCHANT_") || !this.isValidEnchant(string, nBTItem)) continue;
            EnchantType enchantType = EnchantType.valueOf(nBTItem.getString(string).split(":")[0]);
            int n = Integer.parseInt(nBTItem.getString(string).split(":")[1]);
            double d = Double.parseDouble(nBTItem.getString(string).split(":")[2]);
            switch (EnchantManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType()[enchantType.ordinal()]) {
                case 2: {
                    this.getLegGrab(n, d, livingEntity);
                    break;
                }
                case 3: {
                    this.getVenom(n, d, livingEntity);
                    break;
                }
                case 4: {
                    this.getWither(n, d, livingEntity);
                    break;
                }
                case 5: {
                    this.getEyeBurn(n, d, livingEntity);
                    break;
                }
                case 8: {
                    this.getJustice(n, d, livingEntity);
                    break;
                }
                case 6: {
                    this.getExhaust(n, d, livingEntity);
                    break;
                }
                case 7: {
                    this.getParalyze(n, d, livingEntity);
                    break;
                }
                case 10: {
                    this.getMagicImp(d, livingEntity);
                    break;
                }
                case 11: {
                    if (!(arrow instanceof Player) || !(livingEntity instanceof Player)) continue block11;
                    Player player = (Player)livingEntity;
                    Player player2 = (Player)arrow;
                    this.getGTM(n, d, player, player2);
                    break;
                }
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onRicDamage(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        Projectile projectile;
        Entity entity = entityDamageByEntityEvent.getEntity();
        Entity entity2 = entityDamageByEntityEvent.getDamager();
        if (entity2 instanceof Projectile) {
            projectile = (Projectile)entity2;
            if (!(projectile.getShooter() instanceof LivingEntity)) {
                return;
            }
        } else {
            return;
        }
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        projectile = (LivingEntity)entity;
        this.onRicochet((Projectile)entity2, (LivingEntity)projectile, entityDamageByEntityEvent.getFinalDamage(), entityDamageByEntityEvent);
    }

    @EventHandler(ignoreCancelled=true)
    public void onBowEDamage(EntityShootBowEvent entityShootBowEvent) {
        Entity entity = entityShootBowEvent.getProjectile();
        LivingEntity livingEntity = entityShootBowEvent.getEntity();
        ItemStack itemStack = livingEntity.getEquipment().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("ENCHANT_") || !this.isValidEnchant(string, nBTItem)) continue;
            EnchantType enchantType = EnchantType.valueOf(nBTItem.getString(string).split(":")[0]);
            int n = Integer.parseInt(nBTItem.getString(string).split(":")[1]);
            double d = Double.parseDouble(nBTItem.getString(string).split(":")[2]);
            switch (EnchantManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType()[enchantType.ordinal()]) {
                case 18: {
                    entityShootBowEvent.setProjectile(this.getMalice(entityShootBowEvent.getForce(), d, livingEntity, entity));
                    break;
                }
                case 19: {
                    this.getMinigun(entityShootBowEvent.getForce(), n, d, livingEntity, entity);
                    break;
                }
                case 22: {
                    entityShootBowEvent.getProjectile().setGravity(false);
                    break;
                }
                case 23: {
                    entityShootBowEvent.setProjectile((Entity)this.getRicochet((Projectile)entity, livingEntity, n, d));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onPickE(BlockBreakEvent blockBreakEvent) {
        if (blockBreakEvent.isCancelled()) {
            return;
        }
        if (blockBreakEvent.getBlock().hasMetadata("Divine")) {
            return;
        }
        Player player = blockBreakEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        block4 : for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("ENCHANT_") || !this.isValidEnchant(string, nBTItem)) continue;
            EnchantType enchantType = EnchantType.valueOf(nBTItem.getString(string).split(":")[0]);
            int n = Integer.parseInt(nBTItem.getString(string).split(":")[1]);
            double d = Double.parseDouble(nBTItem.getString(string).split(":")[2]);
            switch (EnchantManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType()[enchantType.ordinal()]) {
                case 20: {
                    if (!blockBreakEvent.getBlock().getType().name().contains("_ORE")) continue block4;
                    this.getLuckyMiner(n, d, blockBreakEvent.getBlock(), itemStack);
                    break;
                }
                case 21: {
                    if (blockBreakEvent.getBlock().getType() != Material.MOB_SPAWNER) continue block4;
                    this.getDivineTouch(d, blockBreakEvent.getBlock());
                    break;
                }
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onPlaceB(BlockPlaceEvent blockPlaceEvent) {
        Block block = blockPlaceEvent.getBlock();
        if (block.getType().name().contains("_ORE")) {
            block.setMetadata("Divine", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)"yes"));
        }
    }

    @EventHandler
    public void onPiston(BlockPistonExtendEvent blockPistonExtendEvent) {
        for (Block block : blockPistonExtendEvent.getBlocks()) {
            if (!block.hasMetadata("Divine")) continue;
            blockPistonExtendEvent.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onPiston2(BlockPistonRetractEvent blockPistonRetractEvent) {
        for (Block block : blockPistonRetractEvent.getBlocks()) {
            if (!block.hasMetadata("Divine")) continue;
            blockPistonRetractEvent.setCancelled(true);
            return;
        }
    }

    public void getExecute(double d, Entity entity) {
        if ((double)this.r.nextInt(100) <= d) {
            ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, 3);
            SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
            if (!(entity instanceof Player)) {
                skullMeta.setDisplayName(Lang.Enchants_Misc_SkullName.toMsg().replace("%name%", Lang.getCustom("EntityNames." + entity.getType().name())));
                skullMeta.setOwner(ItemUtils.getValidSkullName(entity));
            } else {
                skullMeta.setDisplayName(Lang.Enchants_Misc_SkullName.toMsg().replace("%name%", entity.getName()));
                skullMeta.setOwner(entity.getName());
            }
            itemStack.setItemMeta((ItemMeta)skullMeta);
            entity.getLocation().getWorld().dropItem(entity.getLocation(), itemStack);
        }
    }

    public int getExpHunter(int n, double d, int n2) {
        ++n;
        if ((double)this.r.nextInt(100) <= d) {
            n2 *= n;
        }
        return n2;
    }

    public void getLegGrab(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, n - 1));
            ParticleUtils.doParticle((Entity)livingEntity, "SPELL_MOB", "VILLAGER_ANGRY");
        }
    }

    public void getVenom(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, n - 1));
            ParticleUtils.doParticle((Entity)livingEntity, "SPELL_MOB_AMBIENT", "SLIME");
        }
    }

    public void getWither(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, n - 1));
            ParticleUtils.doParticle((Entity)livingEntity, "SPELL_WITCH", "CLOUD");
        }
    }

    public void getEyeBurn(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, n - 1));
            ParticleUtils.doParticle((Entity)livingEntity, "TOWN_AURA", "LAVA");
        }
    }

    public void getExhaust(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, n - 1));
            ParticleUtils.doParticle((Entity)livingEntity, "FIREWORKS_SPARK", "CRIT");
        }
    }

    public void getParalyze(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, n + 5));
            ParticleUtils.doParticle((Entity)livingEntity, "CRIT_MAGIC", "PORTAL");
        }
    }

    public void getJustice(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            Location location = livingEntity.getLocation();
            location.getWorld().strikeLightning(location);
            livingEntity.damage((double)(n * 2));
        }
    }

    public void getMagicImp(double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            for (PotionEffect potionEffect : livingEntity.getActivePotionEffects()) {
                livingEntity.removePotionEffect(potionEffect.getType());
            }
            ParticleUtils.doParticle((Entity)livingEntity, "CRIT_MAGIC", "VILLAGER_ANGRY");
        }
    }

    public void getGTM(int n, double d, Player player, Player player2) {
        if ((double)this.r.nextInt(100) <= d) {
            double d2 = this.plugin.getHM().getVault().getBalans(player);
            double d3 = d2 * (double)(1 - n / 100);
            double d4 = d2 - d3;
            if (this.plugin.getHM().getVault().take(player, d4)) {
                player.sendMessage(Lang.Enchants_Misc_GTM_Robbed.toMsg().replace("%s", String.valueOf(d4)).replace("%p", player2.getName()));
                player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 0.3f, 0.3f);
            }
            if (this.plugin.getHM().getVault().give(player2, d4)) {
                player2.sendMessage(Lang.Enchants_Misc_GTM_Steal.toMsg().replace("%s", String.valueOf(d4)).replace("%p", player.getName()));
                player2.playSound(player2.getLocation(), Sound.ENTITY_WITCH_AMBIENT, 0.3f, 0.3f);
            }
        }
    }

    public void getFireSpray(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            livingEntity.setFireTicks(20 * n);
        }
    }

    public void getLavaWalk(Block block, Player player) {
        if (Hook.WORLD_GUARD.isEnabled() && this.plugin.getHM().getWorldGuard().canBuilds(player)) {
            block.setType(Material.STAINED_GLASS);
            player.playSound(block.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 0.5f, 0.5f);
        }
    }

    public void getPrayVictor(double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            for (PotionEffect potionEffect : livingEntity.getActivePotionEffects()) {
                if (potionEffect.getType() != PotionEffectType.BLINDNESS && potionEffect.getType() != PotionEffectType.CONFUSION && potionEffect.getType() != PotionEffectType.HUNGER && potionEffect.getType() != PotionEffectType.LEVITATION && potionEffect.getType() != PotionEffectType.POISON && potionEffect.getType() != PotionEffectType.SLOW && potionEffect.getType() != PotionEffectType.SLOW_DIGGING && potionEffect.getType() != PotionEffectType.UNLUCK && potionEffect.getType() != PotionEffectType.WEAKNESS && potionEffect.getType() != PotionEffectType.WITHER) continue;
                livingEntity.removePotionEffect(potionEffect.getType());
            }
            ParticleUtils.doParticle((Entity)livingEntity, "CRIT_MAGIC", "VILLAGER_HAPPY");
        }
    }

    public void getEternalDenial(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            Location location = livingEntity.getLocation();
            location.getWorld().strikeLightning(location);
            livingEntity.damage((double)n);
        }
    }

    public void getAmbush(int n, double d, LivingEntity livingEntity, LivingEntity livingEntity2) {
        if ((double)this.r.nextInt(100) <= d) {
            Utils.playEffect("EXPLOSION_NORMAL", 0.0, 0.0, 0.0, 0.15000000596046448, 15, livingEntity.getLocation());
            livingEntity.teleport(livingEntity2.getLocation().add(livingEntity2.getLocation().getDirection().multiply(-2.0)));
            livingEntity2.damage((double)n);
            Utils.playEffect("EXPLOSION_NORMAL", 0.0, 0.0, 0.0, 0.15000000596046448, 15, livingEntity.getLocation());
        }
    }

    public void getPunishWave(int n, double d, LivingEntity livingEntity) {
        if ((double)this.r.nextInt(100) <= d) {
            for (Entity entity : livingEntity.getLocation().getWorld().getNearbyEntities(livingEntity.getLocation(), 5.0, 5.0, 5.0)) {
                if (!(entity instanceof LivingEntity) || entity.equals((Object)livingEntity) || Hook.WORLD_GUARD.isEnabled() && !this.plugin.getHM().getWorldGuard().canFights((Entity)livingEntity, entity)) continue;
                Location location = entity.getLocation();
                Location location2 = location.subtract(livingEntity.getLocation());
                Vector vector = location2.getDirection().normalize().multiply(-1.4);
                if (vector.getY() >= 1.15) {
                    vector.setY(vector.getY() * 0.45);
                } else if (vector.getY() >= 1.0) {
                    vector.setY(vector.getY() * 0.6);
                } else if (vector.getY() >= 0.8) {
                    vector.setY(vector.getY() * 0.85);
                }
                if (vector.getY() <= 0.0) {
                    vector.setY(- vector.getY() + 0.3);
                }
                if (Math.abs(location2.getX()) <= 1.0) {
                    vector.setX(vector.getX() * 1.2);
                }
                if (Math.abs(location2.getZ()) <= 1.0) {
                    vector.setZ(vector.getZ() * 1.2);
                }
                double d2 = vector.getX() * 2.0;
                double d3 = vector.getY() * 2.0;
                double d4 = vector.getZ() * 2.0;
                if (d2 >= 3.0) {
                    d2 *= 0.5;
                }
                if (d3 >= 3.0) {
                    d3 *= 0.5;
                }
                if (d4 >= 3.0) {
                    d4 *= 0.5;
                }
                vector.setX(d2);
                vector.setY(d3);
                vector.setZ(d4);
                entity.setVelocity(vector);
                ((LivingEntity)entity).damage((double)n);
            }
            ParticleUtils.wave(livingEntity.getLocation());
        }
    }

    public Entity getMalice(double d, double d2, LivingEntity livingEntity, Entity entity) {
        if ((double)this.r.nextInt(100) <= d2) {
            TNTPrimed tNTPrimed = (TNTPrimed)entity.getWorld().spawn(entity.getLocation(), TNTPrimed.class);
            tNTPrimed.setVelocity(livingEntity.getEyeLocation().getDirection().multiply(d * 2.0));
            return tNTPrimed;
        }
        return entity;
    }

    private Projectile getRicochet(Projectile projectile, LivingEntity livingEntity, int n, double d) {
        projectile.setMetadata("RICOCHET-L", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)livingEntity));
        projectile.setMetadata("RICOCHET-LVL", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)n));
        projectile.setMetadata("RICOCHET-VAL", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)d));
        return projectile;
    }

    private void onRicochet(Projectile projectile, LivingEntity livingEntity, double d, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!projectile.hasMetadata("RICOCHET-L")) {
            return;
        }
        entityDamageByEntityEvent.setCancelled(true);
        LivingEntity livingEntity2 = (LivingEntity)((MetadataValue)projectile.getMetadata("RICOCHET-L").get(0)).value();
        livingEntity.damage(d, (Entity)livingEntity2);
        double d2 = ((MetadataValue)projectile.getMetadata("RICOCHET-LVL").get(0)).asDouble();
        double d3 = ((MetadataValue)projectile.getMetadata("RICOCHET-VAL").get(0)).asDouble();
        for (Entity entity : livingEntity.getNearbyEntities(d3, 2.5, d3)) {
            if (!(entity instanceof LivingEntity) || entity.equals((Object)livingEntity) || entity.getUniqueId().toString().equals(livingEntity2.getUniqueId().toString())) continue;
            LivingEntity livingEntity3 = (LivingEntity)entity;
            if (Hook.CITIZENS.isEnabled() && this.plugin.getHM().getCitizens().isNPC((Entity)livingEntity3)) continue;
            Location location = livingEntity3.getEyeLocation();
            Location location2 = livingEntity.getEyeLocation();
            Vector vector = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()).toVector();
            location2.setDirection(vector.subtract(location2.toVector()));
            Vector vector2 = location2.getDirection();
            projectile.setVelocity(vector2.multiply((- d2) * 1.5));
            break;
        }
    }

    public void getMinigun(double d, final int n, double d2, final LivingEntity livingEntity, Entity entity) {
        if ((double)this.r.nextInt(100) <= d2) {
            final Vector vector = entity.getVelocity();
            new BukkitRunnable(){
                int i = 0;

                public void run() {
                    if (this.i == n + 3) {
                        this.cancel();
                    }
                    ((Arrow)livingEntity.launchProjectile(Arrow.class)).setVelocity(vector);
                    ++this.i;
                }
            }.runTaskTimer((Plugin)this.plugin, 1L, 2L);
        }
    }

    public void getLuckyMiner(int n, double d, Block block, ItemStack itemStack) {
        if ((double)this.r.nextInt(100) <= d) {
            Location location = block.getLocation();
            ItemStack itemStack2 = new ItemStack(block.getType());
            if (!itemStack.containsEnchantment(Enchantment.SILK_TOUCH)) {
                String[] arrstring = block.getType().toString().split("_");
                int n2 = 0;
                while (n2 < arrstring.length) {
                    Material material = Material.getMaterial((String)arrstring[n2]);
                    if (material != null) {
                        itemStack2 = new ItemStack(material);
                        break;
                    }
                    ++n2;
                }
            }
            int n3 = 0;
            while (n3 < n - 1) {
                location.getWorld().dropItem(location, itemStack2);
                ++n3;
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onBlockP(BlockPlaceEvent blockPlaceEvent) {
        Block block = blockPlaceEvent.getBlock();
        if (block.getType() != Material.MOB_SPAWNER) {
            return;
        }
        ItemStack itemStack = blockPlaceEvent.getPlayer().getInventory().getItemInMainHand();
        if (itemStack != null && itemStack.getType() == Material.MOB_SPAWNER) {
            BlockStateMeta blockStateMeta = (BlockStateMeta)itemStack.getItemMeta();
            BlockState blockState = blockStateMeta.getBlockState();
            CreatureSpawner creatureSpawner = (CreatureSpawner)blockState;
            BlockState blockState2 = block.getState();
            CreatureSpawner creatureSpawner2 = (CreatureSpawner)blockState2;
            creatureSpawner2.setSpawnedType(creatureSpawner.getSpawnedType());
            blockState2.update();
        }
    }

    private void getDivineTouch(double d, Block block) {
        if ((double)this.r.nextInt(100) <= d) {
            Location location = block.getLocation();
            CreatureSpawner creatureSpawner = (CreatureSpawner)block.getState();
            ItemStack itemStack = new ItemStack(Material.MOB_SPAWNER);
            BlockStateMeta blockStateMeta = (BlockStateMeta)itemStack.getItemMeta();
            BlockState blockState = blockStateMeta.getBlockState();
            CreatureSpawner creatureSpawner2 = (CreatureSpawner)blockState;
            creatureSpawner2.setSpawnedType(creatureSpawner.getSpawnedType());
            blockState.update(true);
            blockStateMeta.setDisplayName(Lang.Enchants_Misc_SpawnerName.toMsg().replace("%type%", Lang.getCustom("EntityNames." + creatureSpawner.getSpawnedType().name())));
            blockStateMeta.setBlockState(blockState);
            itemStack.setItemMeta((ItemMeta)blockStateMeta);
            location.getWorld().dropItem(location, itemStack);
        }
    }

    private boolean isValidEnchant(String string, NBTItem nBTItem) {
        EnchantType enchantType;
        try {
            enchantType = EnchantType.valueOf(nBTItem.getString(string).split(":")[0]);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
        Enchant enchant = this.getEnchantByType(enchantType);
        if (enchant != null && enchant.isEnabled()) {
            return true;
        }
        return false;
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

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[EnchantType.values().length];
        try {
            arrn[EnchantType.AMBUSH.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.DIVINE_TOUCH.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.ETERNAL_DENIAL.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.EXECUTIONER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.EXHAUST.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.EXP_HUNTER.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.EYE_BURN.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.FLAME_SPRAY.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.GTM.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.JUSTICE.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.LAVA_WALKER.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.LEG_GRAB.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.LUCKY_MINER.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.MAGIC_IMPLOSION.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.MALICE_JOKE.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.MINIGUN.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.NO_GRAVITY.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.PARALYZE.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.PRAYER_OF_VICTORY.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.PUNISHING_WAVE.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.RICOCHET.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.VENOM.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[EnchantType.WITHER.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$modules$enchant$EnchantManager$EnchantType;
    }

    public class Enchant {
        private boolean enabled;
        private String id;
        private String name;
        private List<String> desc;
        private double value;
        private double value_lvl;
        private int lvl_get;
        private int min_lvl;
        private int max_lvl;
        private double table;
        private List<String> item_types;
        private List<String> conflict;

        public Enchant(boolean bl, String string, String string2, List<String> list, double d, double d2, int n, int n2, int n3, double d3, List<String> list2, List<String> list3) {
            this.setEnabled(bl);
            this.setId(string);
            this.setName(string2);
            this.setDesc(list);
            this.setValue(d);
            this.setValueLvl(d2);
            this.setEnchantLevel(n);
            this.setMinLevel(n2);
            this.setMaxLevel(n3);
            this.setTableRate(d3);
            this.setItemTypes(list2);
            this.setConflicts(list3);
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

        public double getValue() {
            return this.value;
        }

        public void setValue(double d) {
            this.value = d;
        }

        public double getValueLvl() {
            return this.value_lvl;
        }

        public void setValueLvl(double d) {
            this.value_lvl = d;
        }

        public int getEnchantLevel() {
            return this.lvl_get;
        }

        public void setEnchantLevel(int n) {
            this.lvl_get = n;
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

        public double getTableRate() {
            return this.table;
        }

        public void setTableRate(double d) {
            this.table = d;
        }

        public List<String> getItemTypes() {
            return this.item_types;
        }

        public void setItemTypes(List<String> list) {
            this.item_types = list;
        }

        public List<String> getConflicts() {
            return this.conflict;
        }

        public void setConflicts(List<String> list) {
            this.conflict = list;
        }

        public ItemStack create(int n) {
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
            String string = EnchantManager.this.getSettings().getMaterials().get(new Random().nextInt(EnchantManager.this.getSettings().getMaterials().size()));
            String[] arrstring = string.split(":");
            ItemStack itemStack = Utils.buildItem(arrstring, this.id);
            ItemMeta itemMeta = itemStack.getItemMeta();
            double d = Utils.round3(this.getValue() + this.getValueLvl() * (double)(n - 1));
            String string2 = this.getName().replace("%level%", String.valueOf(n)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n));
            String string3 = EnchantManager.this.getSettings().getDisplay().replace("%s", string2);
            int n2 = Utils.randInt(EnchantManager.this.getSettings().getMinChance(), EnchantManager.this.getSettings().getMaxChance());
            int n3 = 100 - n2;
            String string4 = "";
            String string5 = EnchantManager.this.plugin.getCM().getCFG().getStrClassColor();
            String string6 = EnchantManager.this.plugin.getCM().getCFG().getStrClassSeparator();
            for (String string7 : this.getItemTypes()) {
                Object object = string7.replace("*", "");
                if (Lang.hasPath("ItemTypes." + (String)object)) {
                    object = Lang.getCustom("ItemTypes." + (String)object);
                }
                string4 = String.valueOf(string4) + string5 + (String)object + string6;
            }
            if (string4.length() > 3) {
                string4 = string4.substring(0, string4.length() - 3);
            }
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String string8 : EnchantManager.this.getSettings().getLore()) {
                if (string8.equals("%desc%")) {
                    for (String string9 : this.getDesc()) {
                        arrayList.add(string9.replace("%value%", "" + d).replace("%rlevel%", Utils.IntegerToRomanNumeral(n)).replace("%level%", String.valueOf(n)));
                    }
                    continue;
                }
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string8.replace("%d%", String.valueOf(n3)).replace("%s%", String.valueOf(n2)).replace("%type%", string4).replace("%value%", "" + d).replace("%rlevel%", Utils.IntegerToRomanNumeral(n)).replace("%level%", String.valueOf(n))));
            }
            itemMeta.setDisplayName(string3);
            itemMeta.setLore(arrayList);
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(itemMeta);
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_ENCHANT", String.valueOf(this.getId().toUpperCase()) + ":" + n + ":" + d);
            nBTItem.setInteger("DIVINE_CHANCE", n2);
            return nBTItem.getItem();
        }
    }

    public class EnchantSettings
    extends MainSettings {
        private List<String> material;
        private boolean table;
        private double table_chance;

        public EnchantSettings(List<String> list, String string, List<String> list2, int n, int n2, DestroyType destroyType, boolean bl, String string2, String string3, boolean bl2, Sound sound, Sound sound2, String string4, String string5, String string6, boolean bl3, double d) {
            super(string, list2, n, n2, destroyType, bl, string2, string3, bl2, sound, sound2, string4, string5, string6);
            this.setMaterials(list);
            this.setTableEnabled(bl3);
            this.setTableChance(d);
        }

        public List<String> getMaterials() {
            return this.material;
        }

        public void setMaterials(List<String> list) {
            this.material = list;
        }

        public boolean isTableEnabled() {
            return this.table;
        }

        public void setTableEnabled(boolean bl) {
            this.table = bl;
        }

        public double getTableChance() {
            return this.table_chance;
        }

        public void setTableChance(double d) {
            this.table_chance = d;
        }
    }

    public static enum EnchantType {
        EXECUTIONER,
        LEG_GRAB,
        VENOM,
        WITHER,
        EYE_BURN,
        EXHAUST,
        PARALYZE,
        JUSTICE,
        EXP_HUNTER,
        MAGIC_IMPLOSION,
        GTM,
        FLAME_SPRAY,
        PUNISHING_WAVE,
        PRAYER_OF_VICTORY,
        AMBUSH,
        ETERNAL_DENIAL,
        LAVA_WALKER,
        MALICE_JOKE,
        MINIGUN,
        LUCKY_MINER,
        DIVINE_TOUCH,
        NO_GRAVITY,
        RICOCHET;
        

        private EnchantType(String string2, int n2) {
        }
    }

}

