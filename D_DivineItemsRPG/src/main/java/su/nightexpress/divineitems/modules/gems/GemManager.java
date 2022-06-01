/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.apache.commons.lang.ArrayUtils
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.HumanEntity
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
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.gems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
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
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.GemsCommand;
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
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.types.DamageType;
import su.nightexpress.divineitems.types.DestroyType;
import su.nightexpress.divineitems.types.SlotType;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class GemManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private MyConfig settingsCfg;
    private MyConfig gemsCfg;
    private GemSettings settings;
    private HashMap<String, Gem> gems;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_VAR_ATT = "DIVINE_VAR_ATT_";
    private final String NBT_KEY_ITEM_GEM = "GEM_";
    private final String NBT_KEY_GEM = "DIVINE_GEM_ID";
    private final String NBT_KEY_GEM_ILD = "DIVINE_GEM_ILD";
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType;

    public GemManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.gems = new HashMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.gemsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "gems.yml");
        this.setupSettings();
        this.setupGems();
        this.setupSlot();
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
        return "Gems";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new GemsCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.gems.clear();
            this.settings = null;
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
        SlotType slotType = SlotType.GEM;
        slotType.setModule(this);
        slotType.setHeader(this.getSettings().getHeader());
        slotType.setEmpty(this.getSettings().getEmptySlot());
        slotType.setFilled(this.getSettings().getFilledSlot());
    }

    private void setupSettings() {
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        if (!fileConfiguration.contains("General.AllowMultipleSameGemsInOneItem")) {
            fileConfiguration.set("General.AllowMultipleSameGemsInOneItem", (Object)true);
        }
        boolean bl = fileConfiguration.getBoolean("General.AllowMultipleSameGemsInOneItem");
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
        this.settings = new GemSettings(bl, string2, list, n, n2, destroyType, bl2, string3, string4, bl3, sound, sound2, string5, string6, string7);
        this.settingsCfg.save();
    }

    private void setupGems() {
        FileConfiguration fileConfiguration = this.gemsCfg.getConfig();
        if (!fileConfiguration.contains("Gems")) {
            return;
        }
        for (String string : fileConfiguration.getConfigurationSection("Gems").getKeys(false)) {
            Object object4;
            Object object22;
            Object object32;
            String string2 = "Gems." + string + ".";
            String string3 = string.toLowerCase();
            boolean bl = fileConfiguration.getBoolean(String.valueOf(string2) + "Enabled");
            if (!bl) continue;
            boolean bl2 = fileConfiguration.getBoolean(String.valueOf(string2) + "Enchanted");
            String string4 = fileConfiguration.getString(String.valueOf(string2) + "Material");
            String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Name"));
            if (!fileConfiguration.contains(String.valueOf(string2) + "ItemLoreDisplay")) {
                fileConfiguration.set(String.valueOf(string2) + "ItemLoreDisplay", (Object)string5);
            }
            String string6 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "ItemLoreDisplay"));
            List list = fileConfiguration.getStringList(String.valueOf(string2) + "Desc");
            HashMap<String, Double> hashMap = new HashMap<String, Double>();
            if (fileConfiguration.contains(String.valueOf(string2) + "Variables")) {
                for (Object object4 : fileConfiguration.getConfigurationSection(String.valueOf(string2) + "Variables").getKeys(false)) {
                    double d = fileConfiguration.getDouble(String.valueOf(string2) + "Variables." + (String)object4);
                    hashMap.put((String)object4, d);
                }
            }
            object4 = new HashMap();
            for (Object object22 : fileConfiguration.getConfigurationSection(String.valueOf(string2) + "VariablesPerLvl").getKeys(false)) {
                double d = fileConfiguration.getDouble(String.valueOf(string2) + "VariablesPerLvl." + (String)object22);
                object4.put(object22, d);
            }
            object22 = new HashMap();
            for (Object object32 : fileConfiguration.getConfigurationSection(String.valueOf(string2) + "VariablesAttributes").getKeys(false)) {
                String string7 = fileConfiguration.getString(String.valueOf(string2) + "VariablesAttributes." + (String)object32).toUpperCase();
                object22.put(object32, string7);
            }
            object32 = new HashMap();
            for (Object object5 : fileConfiguration.getConfigurationSection(String.valueOf(string2) + "VariablesPercentage").getKeys(false)) {
                boolean bl3 = fileConfiguration.getBoolean(String.valueOf(string2) + "VariablesPercentage." + (String)object5);
                object32.put(object5, bl3);
            }
            int n = fileConfiguration.getInt(String.valueOf(string2) + "LevelMin");
            int n2 = fileConfiguration.getInt(String.valueOf(string2) + "LevelMax");
            List list2 = fileConfiguration.getStringList(String.valueOf(string2) + "ItemTypes");
            Gem gem = new Gem(bl, bl2, string3, string4, string5, string6, list, hashMap, (HashMap<String, Double>)object4, (HashMap<String, String>)object22, (HashMap<String, Boolean>)object32, n, n2, list2);
            this.gems.put(string3, gem);
        }
        this.gemsCfg.save();
    }

    public HashMap<String, String> getGemValues(ItemStack itemStack) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (itemStack == null) {
            return hashMap;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("DIVINE_VAR_ATT_")) continue;
            String[] arrstring = nBTItem.getString(string).split("@");
            String string2 = arrstring[0];
            boolean bl = Boolean.valueOf(arrstring[1]);
            double d = Double.parseDouble(arrstring[2]);
            String string3 = String.valueOf(d);
            if (bl) {
                string3 = String.valueOf(string3) + "%";
            }
            if (hashMap.containsKey(string2)) {
                string3 = String.valueOf(hashMap.get(string2)) + "/" + string3;
            }
            hashMap.put(string2, string3);
        }
        return hashMap;
    }

    public HashMap<ItemStat, Double> getItemGemStats(ItemStack itemStack, boolean bl) {
        HashMap<ItemStat, Double> hashMap = new HashMap<ItemStat, Double>();
        if (itemStack == null) {
            return hashMap;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string.startsWith("GEM_")) continue;
            String string2 = nBTItem.getString(string);
            String[] arrstring2 = arrstring = string2.split("\\|");
            int n = arrstring2.length;
            int n2 = 0;
            while (n2 < n) {
                String string3 = arrstring2[n2];
                try {
                    String[] arrstring3;
                    ItemStat itemStat = ItemStat.valueOf(string3.split("@")[0]);
                    String[] arrstring4 = arrstring3 = string3.split("@")[1].split("\\/");
                    int n3 = arrstring4.length;
                    int n4 = 0;
                    while (n4 < n3) {
                        block11 : {
                            double d;
                            block12 : {
                                String string4;
                                block10 : {
                                    string4 = arrstring4[n4];
                                    d = 0.0;
                                    if (!string4.endsWith("%")) break block10;
                                    if (!bl) break block11;
                                    d = Double.parseDouble(string4.replace("%", ""));
                                    break block12;
                                }
                                if (bl) break block11;
                                d = Double.parseDouble(string4);
                            }
                            if (hashMap.containsKey((Object)itemStat)) {
                                d += hashMap.get((Object)itemStat).doubleValue();
                            }
                            hashMap.put(itemStat, d);
                        }
                        ++n4;
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
                ++n2;
            }
        }
        return hashMap;
    }

    public HashMap<DamageType, Double> getItemGemDamages(ItemStack itemStack, boolean bl) {
        HashMap<DamageType, Double> hashMap = new HashMap<DamageType, Double>();
        if (itemStack == null) {
            return hashMap;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string.startsWith("GEM_")) continue;
            String string2 = nBTItem.getString(string);
            String[] arrstring2 = arrstring = string2.split("\\|");
            int n = arrstring2.length;
            int n2 = 0;
            while (n2 < n) {
                block8 : {
                    String[] arrstring3;
                    String string3 = arrstring2[n2];
                    String string4 = string3.split("@")[0];
                    if (!string4.endsWith("_DAMAGE_TYPE")) break block8;
                    string4 = string4.replace("_DAMAGE_TYPE", "");
                    DamageType damageType = this.plugin.getCFG().getDamageTypeById(string4);
                    if (damageType == null) break block8;
                    String[] arrstring4 = arrstring3 = string3.split("@")[1].split("\\/");
                    int n3 = arrstring4.length;
                    int n4 = 0;
                    while (n4 < n3) {
                        block10 : {
                            double d;
                            block11 : {
                                String string5;
                                block9 : {
                                    string5 = arrstring4[n4];
                                    d = 0.0;
                                    if (!string5.endsWith("%")) break block9;
                                    if (!bl) break block10;
                                    d = Double.parseDouble(string5.replace("%", ""));
                                    break block11;
                                }
                                if (bl) break block10;
                                d = Double.parseDouble(string5);
                            }
                            if (hashMap.containsKey(damageType)) {
                                d += hashMap.get(damageType).doubleValue();
                            }
                            hashMap.put(damageType, d);
                        }
                        ++n4;
                    }
                }
                ++n2;
            }
        }
        return hashMap;
    }

    public HashMap<ArmorType, Double> getItemGemArmors(ItemStack itemStack, boolean bl) {
        HashMap<ArmorType, Double> hashMap = new HashMap<ArmorType, Double>();
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string.startsWith("GEM_")) continue;
            String string2 = nBTItem.getString(string);
            String[] arrstring2 = arrstring = string2.split("\\|");
            int n = arrstring2.length;
            int n2 = 0;
            while (n2 < n) {
                block7 : {
                    String[] arrstring3;
                    String string3 = arrstring2[n2];
                    String string4 = string3.split("@")[0];
                    if (!string4.endsWith("_ARMOR_TYPE") && !string4.endsWith("_DEFENSE_TYPE")) break block7;
                    string4 = string4.replace("_ARMOR_TYPE", "").replace("_DEFENSE_TYPE", "");
                    ArmorType armorType = this.plugin.getCFG().getArmorTypeById(string4);
                    if (armorType == null) break block7;
                    String[] arrstring4 = arrstring3 = string3.split("@")[1].split("\\/");
                    int n3 = arrstring4.length;
                    int n4 = 0;
                    while (n4 < n3) {
                        block9 : {
                            double d;
                            block10 : {
                                String string5;
                                block8 : {
                                    string5 = arrstring4[n4];
                                    d = 0.0;
                                    if (!string5.endsWith("%")) break block8;
                                    if (!bl) break block9;
                                    d = Double.parseDouble(string5.replace("%", ""));
                                    break block10;
                                }
                                if (bl) break block9;
                                d = Double.parseDouble(string5);
                            }
                            if (hashMap.containsKey(armorType)) {
                                d += hashMap.get(armorType).doubleValue();
                            }
                            hashMap.put(armorType, d);
                        }
                        ++n4;
                    }
                }
                ++n2;
            }
        }
        return hashMap;
    }

    public ItemStack insertGem(ItemStack itemStack, ItemStack itemStack2) {
        Object object;
        Object object22;
        String string;
        String string2 = "";
        for (String object22 : this.getGemValues(itemStack).keySet()) {
            String string3 = this.getGemValues(itemStack).get(object22);
            object = String.valueOf(object22) + "@" + string3;
            if (!string2.isEmpty()) {
                string2 = String.valueOf(string2) + "|";
            }
            string2 = String.valueOf(string2) + (String)object;
        }
        NBTItem nBTItem = new NBTItem(itemStack2);
        int n = 0;
        for (String string4 : nBTItem.getKeys()) {
            if (!string4.startsWith("GEM_")) continue;
            ++n;
        }
        NBTItem nBTItem2 = new NBTItem(itemStack);
        object = nBTItem2.getString("DIVINE_GEM_ILD");
        List list = itemStack2.getItemMeta().getLore();
        for (Object object22 : list) {
            String string5;
            string = ChatColor.stripColor((String)object22);
            if (!string.equals(string5 = ChatColor.stripColor((String)this.getSettings().getEmptySlot()))) continue;
            int n2 = list.indexOf(object22);
            list.remove(n2);
            list.add(n2, String.valueOf(this.getSettings().getFilledSlot()) + (String)object);
            break;
        }
        object22 = itemStack2.getItemMeta();
        object22.setLore(list);
        itemStack2.setItemMeta(object22);
        NBTItem nBTItem3 = new NBTItem(itemStack2);
        string = new NBTItem(itemStack).getString("DIVINE_GEM_ID");
        nBTItem3.setString("GEM_" + n + "_" + string, string2);
        return nBTItem3.getItem();
    }

    public int getItemGemsAmount(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        int n = 0;
        for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("GEM_")) continue;
            ++n;
        }
        return n;
    }

    public ItemStack removeGem(ItemStack itemStack, int n) {
        String string2;
        int n2;
        NBTItem nBTItem = new NBTItem(itemStack);
        int n3 = this.getItemGemsAmount(itemStack);
        int n4 = n - 1;
        int n5 = n2 = n3 - n4;
        if (n4 == 0) {
            --n5;
        }
        for (String string2 : nBTItem.getKeys()) {
            if (!string2.startsWith("GEM_" + n5)) continue;
            nBTItem.removeKey(string2);
            break;
        }
        itemStack = new ItemStack(nBTItem.getItem());
        string2 = itemStack.getItemMeta();
        ArrayList arrayList = new ArrayList(string2.getLore());
        int n6 = 0;
        int n7 = 0;
        for (String string3 : string2.getLore()) {
            if (string3.startsWith(this.getSettings().getFilledSlot())) {
                ++n6;
            }
            if (n6 == n2) {
                arrayList.remove(n7);
                arrayList.add(n7, this.settings.getEmptySlot());
                break;
            }
            ++n7;
        }
        string2.setLore((List)arrayList);
        itemStack.setItemMeta((ItemMeta)string2);
        return itemStack;
    }

    private ItemStack getResult(ItemStack itemStack, ItemStack itemStack2) {
        ItemStack itemStack3 = new ItemStack(this.insertGem(itemStack, itemStack2));
        ItemMeta itemMeta = itemStack3.getItemMeta();
        itemMeta.setDisplayName(Lang.Gems_Enchanting_Result.toMsg());
        itemStack3.setItemMeta(itemMeta);
        return itemStack3;
    }

    private void openEnchantGUI(Player player, ItemStack itemStack, ItemStack itemStack2) {
        GUI gUI = new GUI(this.plugin.getGUIManager().getGUIByType(GUIType.ENCHANT_GEM));
        gUI.getItems().get((Object)ContentType.TARGET).setItem(itemStack);
        gUI.getItems().get((Object)ContentType.SOURCE).setItem(itemStack2);
        gUI.getItems().get((Object)ContentType.RESULT).setItem(this.getResult(new ItemStack(itemStack2), new ItemStack(itemStack)));
        this.plugin.getGUIManager().setGUI(player, gUI);
        player.openInventory(gUI.build());
    }

    public boolean hasGem(ItemStack itemStack, String string) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string2 : nBTItem.getKeys()) {
            if (!string2.startsWith("GEM_")) continue;
            String[] arrstring = nBTItem.getString(string2).split("_");
            if (arrstring.length != 3) {
                return false;
            }
            if (!arrstring[2].equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }

    public GemSettings getSettings() {
        return this.settings;
    }

    public Collection<Gem> getGems() {
        return this.gems.values();
    }

    public List<String> getGemNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Gem gem : this.getGems()) {
            arrayList.add(gem.getId());
        }
        return arrayList;
    }

    public Gem getGemById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<Gem>(this.getGems()).get(this.r.nextInt(this.getGems().size()));
        }
        return this.gems.get(string.toLowerCase());
    }

    public boolean isGem(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_GEM_ID");
    }

    public String getGemId(ItemStack itemStack) {
        return new NBTItem(itemStack).getString("DIVINE_GEM_ID");
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent inventoryClickEvent) {
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
        if (!nBTItem.hasKey("DIVINE_GEM_ID").booleanValue()) {
            return;
        }
        String string = nBTItem.getString("DIVINE_GEM_ID");
        if (this.getGemById(string) == null) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_Internal.toMsg());
            return;
        }
        if (!this.settings.allowMultSameGems() && this.hasGem(itemStack2, string)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_MultipleNotAllowed.toMsg().replace("%gem%", string));
            return;
        }
        Gem gem = this.getGemById(string);
        boolean bl = false;
        for (String string2 : gem.getItemTypes()) {
            if (!ItemUtils.isValidItemType(string2, itemStack2)) continue;
            bl = true;
            break;
        }
        if (!bl) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_InvalidType.toMsg());
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_FullInventory.toMsg());
            return;
        }
        for (String string2 : itemStack2.getItemMeta().getLore()) {
            String string3;
            String string4 = ChatColor.stripColor((String)string2);
            if (!string4.equalsIgnoreCase(string3 = ChatColor.stripColor((String)this.getSettings().getEmptySlot()))) continue;
            inventoryClickEvent.setCursor(null);
            player.getInventory().addItem(new ItemStack[]{itemStack});
            inventoryClickEvent.setCancelled(true);
            this.openEnchantGUI(player, itemStack2, itemStack);
            return;
        }
        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_NoSlots.toMsg());
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (!this.plugin.getGUIManager().valid(player, GUIType.ENCHANT_GEM)) {
            return;
        }
        GUI gUI = this.plugin.getGUIManager().getPlayerGUI(player, GUIType.ENCHANT_GEM);
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
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_NoItem.toMsg());
                return;
            }
            int n2 = new Random().nextInt(100);
            NBTItem nBTItem = new NBTItem(itemStack3);
            int n3 = nBTItem.getInteger("DIVINE_CHANCE");
            GemSettings gemSettings = this.getSettings();
            if (n3 < n2) {
                switch (GemManager.$SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType()[gemSettings.getDestroyType().ordinal()]) {
                    case 1: {
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_Failure_Item.toMsg());
                        break;
                    }
                    case 2: {
                        player.getInventory().removeItem(new ItemStack[]{itemStack3});
                        itemStack3.setAmount(itemStack3.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack3});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_Failure_Source.toMsg());
                        break;
                    }
                    case 4: {
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        player.getInventory().removeItem(new ItemStack[]{itemStack3});
                        itemStack3.setAmount(itemStack3.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack3});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_Failure_Both.toMsg());
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
                            if (object2.startsWith(gemSettings.getFilledSlot())) {
                                arrayList.add(gemSettings.getEmptySlot());
                                continue;
                            }
                            arrayList.add((String)object2);
                        }
                        itemMeta.setLore(arrayList);
                        itemStack2.setItemMeta(itemMeta);
                        object2 = new NBTItem(itemStack2);
                        for (String string : object2.getKeys()) {
                            if (!string.startsWith("GEM_")) continue;
                            object2.removeKey(string);
                        }
                        player.getInventory().addItem(new ItemStack[]{object2.getItem()});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_Failure_Clear.toMsg());
                    }
                }
                player.closeInventory();
                this.plugin.getGUIManager().reset(player);
                if (gemSettings.useSound()) {
                    player.playSound(player.getLocation(), gemSettings.getDestroySound(), 0.5f, 0.5f);
                }
                if (gemSettings.useEffect()) {
                    Utils.playEffect(gemSettings.getDestroyEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
                }
                return;
            }
            player.getInventory().removeItem(new ItemStack[]{itemStack2});
            ItemStack itemStack4 = this.insertGem(itemStack3, itemStack2);
            player.getInventory().addItem(new ItemStack[]{itemStack4});
            player.getInventory().removeItem(new ItemStack[]{itemStack3});
            itemStack3.setAmount(itemStack3.getAmount() - 1);
            player.getInventory().addItem(new ItemStack[]{itemStack3});
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_Success.toMsg());
            player.closeInventory();
            if (gemSettings.useSound()) {
                player.playSound(player.getLocation(), gemSettings.getSuccessSound(), 0.5f, 0.5f);
            }
            if (gemSettings.useEffect()) {
                Utils.playEffect(gemSettings.getSuccessEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
            }
            player.updateInventory();
            this.plugin.getGUIManager().reset(player);
            return;
        }
        GUIItem gUIItem2 = gUI.getItems().get((Object)ContentType.DECLINE);
        if (itemStack.isSimilar(gUIItem2.getItem()) || ArrayUtils.contains((int[])gUIItem2.getSlots(), (int)n)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Gems_Enchanting_Cancel.toMsg());
            player.closeInventory();
            player.updateInventory();
            this.plugin.getGUIManager().reset(player);
        }
    }

    private String replaceVars(String string, HashMap<?, ?> hashMap) {
        for (Object obj : hashMap.keySet()) {
            String string2 = obj.toString();
            String string3 = "%var_" + string2 + "%";
            String string4 = hashMap.get(obj).toString();
            string = string.replace(string3, string4);
        }
        return string;
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

    public class Gem {
        private boolean enabled;
        private boolean enchant;
        private String id;
        private String material;
        private String name;
        private String ild;
        private List<String> desc;
        private HashMap<String, Double> vars;
        private HashMap<String, Double> vars_lvl;
        private HashMap<String, String> vars_att;
        private HashMap<String, Boolean> vars_perc;
        private int min_lvl;
        private int max_lvl;
        private List<String> item_types;

        public Gem(boolean bl, boolean bl2, String string, String string2, String string3, String string4, List<String> list, HashMap<String, Double> hashMap, HashMap<String, Double> hashMap2, HashMap<String, String> hashMap3, HashMap<String, Boolean> hashMap4, int n, int n2, List<String> list2) {
            this.setEnabled(bl);
            this.setEnchanted(bl2);
            this.setId(string);
            this.setName(string3);
            this.setItemLoreDispaly(string4);
            this.setMaterial(string2);
            this.setDesc(list);
            this.setVariables(hashMap);
            this.setVariablesLvl(hashMap2);
            this.setVariablesAttributes(hashMap3);
            this.setVariablesPercentage(hashMap4);
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

        public boolean isEnchanted() {
            return this.enchant;
        }

        public void setEnchanted(boolean bl) {
            this.enchant = bl;
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

        public String getItemLoreDisplay() {
            return this.ild;
        }

        public void setItemLoreDispaly(String string) {
            this.ild = string;
        }

        public List<String> getDesc() {
            return this.desc;
        }

        public void setDesc(List<String> list) {
            this.desc = list;
        }

        public HashMap<String, Double> getVariables() {
            return this.vars;
        }

        public void setVariables(HashMap<String, Double> hashMap) {
            this.vars = hashMap;
        }

        public HashMap<String, Double> getVariablesLvl() {
            return this.vars_lvl;
        }

        public void setVariablesLvl(HashMap<String, Double> hashMap) {
            this.vars_lvl = hashMap;
        }

        public HashMap<String, String> getVariablesAttributes() {
            return this.vars_att;
        }

        public void setVariablesAttributes(HashMap<String, String> hashMap) {
            this.vars_att = hashMap;
        }

        public HashMap<String, Boolean> getVariablesPercentage() {
            return this.vars_perc;
        }

        public void setVariablesPercentage(HashMap<String, Boolean> hashMap) {
            this.vars_perc = hashMap;
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
            String string22;
            if (!this.isEnabled()) {
                return new ItemStack(Material.AIR);
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
            HashMap<String, Double> hashMap = new HashMap<String, Double>(this.getVariables());
            if (n > 1) {
                int n2 = n - 1;
                for (String string22 : this.getVariablesLvl().keySet()) {
                    double d = Utils.round3(this.getVariablesLvl().get(string22) * (double)n2);
                    if (!hashMap.containsKey(string22)) continue;
                    double d2 = Utils.round3(hashMap.get(string22) + d);
                    hashMap.put(string22, d2);
                }
            }
            String string3 = GemManager.this.replaceVars(this.getName(), hashMap).replace("%level%", String.valueOf(n)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n));
            string22 = GemManager.this.replaceVars(this.getItemLoreDisplay(), hashMap).replace("%level%", String.valueOf(n)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n));
            String string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)GemManager.this.getSettings().getDisplay().replace("%s", string3));
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String string5 : this.getDesc()) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)GemManager.this.replaceVars(string5, hashMap)));
            }
            int n3 = Utils.randInt(GemManager.this.getSettings().getMinChance(), GemManager.this.getSettings().getMaxChance());
            int n4 = 100 - n3;
            String string6 = "";
            String string7 = GemManager.this.plugin.getCM().getCFG().getStrClassColor();
            String string8 = GemManager.this.plugin.getCM().getCFG().getStrClassSeparator();
            for (String string : this.getItemTypes()) {
                void var17_26;
                String string5 = string.replace("*", "");
                if (Lang.hasPath("ItemTypes." + string5)) {
                    String string9 = Lang.getCustom("ItemTypes." + string5);
                }
                string6 = String.valueOf(string6) + string7 + (String)var17_26 + string8;
            }
            if (string6.length() > 3) {
                string6 = string6.substring(0, string6.length() - 3);
            }
            ArrayList<String> arrayList2 = new ArrayList<String>();
            for (String string : GemManager.this.getSettings().getLore()) {
                if (string.equals("%desc%")) {
                    for (String string10 : arrayList) {
                        arrayList2.add(string10);
                    }
                    continue;
                }
                arrayList2.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string.replace("%d%", String.valueOf(n4)).replace("%s%", String.valueOf(n3)).replace("%level%", String.valueOf(n)).replace("%type%", string6).replace("%rlevel%", Utils.IntegerToRomanNumeral(n))));
            }
            itemMeta.setDisplayName(GemManager.this.replaceVars(string4, hashMap));
            itemMeta.setLore(arrayList2);
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(itemMeta);
            if (this.isEnchanted()) {
                itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
            }
            NBTItem nBTItem = new NBTItem(itemStack);
            for (String string : this.getVariablesAttributes().keySet()) {
                String string11 = this.getVariablesAttributes().get(string);
                boolean bl = this.getVariablesPercentage().get(string);
                nBTItem.setString("DIVINE_VAR_ATT_" + string, String.valueOf(string11) + "@" + bl + "@" + hashMap.get(string));
            }
            nBTItem.setString("DIVINE_GEM_ID", this.getId());
            nBTItem.setInteger("DIVINE_CHANCE", n3);
            nBTItem.setString("DIVINE_GEM_ILD", string22);
            ItemStack itemStack2 = nBTItem.getItem();
            return itemStack2;
        }
    }

    public class GemSettings
    extends MainSettings {
        private boolean same;

        public GemSettings(boolean bl, String string, List<String> list, int n, int n2, DestroyType destroyType, boolean bl2, String string2, String string3, boolean bl3, Sound sound, Sound sound2, String string4, String string5, String string6) {
            super(string, list, n, n2, destroyType, bl2, string2, string3, bl3, sound, sound2, string4, string5, string6);
            this.setAllowMultSameGems(bl);
        }

        public boolean allowMultSameGems() {
            return this.same;
        }

        public void setAllowMultSameGems(boolean bl) {
            this.same = bl;
        }
    }

}

