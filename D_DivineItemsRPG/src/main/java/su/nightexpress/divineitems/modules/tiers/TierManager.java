/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.block.Banner
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.banner.Pattern
 *  org.bukkit.block.banner.PatternType
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.BlockStateMeta
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 */
package su.nightexpress.divineitems.modules.tiers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.TiersCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.sets.SetManager;
import su.nightexpress.divineitems.modules.soulbound.SoulboundManager;
import su.nightexpress.divineitems.modules.tiers.resources.ResourceSubType;
import su.nightexpress.divineitems.modules.tiers.resources.ResourceType;
import su.nightexpress.divineitems.modules.tiers.resources.Resources;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.nms.NBTAttribute;
import su.nightexpress.divineitems.nms.NMS;
import su.nightexpress.divineitems.types.AmmoType;
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.types.DamageType;
import su.nightexpress.divineitems.types.SlotType;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.Files;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class TierManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private HashMap<String, Tier> tiers;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY = "TIER";

    public TierManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.tiers = new HashMap();
        this.r = new Random();
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        this.setupTiers();
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
    public boolean isActive() {
        return this.e;
    }

    @Override
    public String name() {
        return "Tiers";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new TiersCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.tiers.clear();
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    public void setupTiers() {
        for (String string : Files.getFilesFolder("tiers")) {
            Tier tier = this.loadFromConfig(string);
            this.tiers.put(tier.getId(), tier);
        }
    }

    private Tier loadFromConfig(String string) {
        double d;
        Object object;
        Object object2;
        Object object32;
        Object object4;
        Object bl11;
        Object object52;
        Object object62;
        ArrayList<ItemFlag> arrayList;
        File file = new File(this.plugin.getDataFolder() + "/modules/tiers/", string);
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
        String string2 = string.replace(".yml", "").toLowerCase();
        String string3 = yamlConfiguration.getString("Display");
        String string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)yamlConfiguration.getString("Color"));
        boolean bl = yamlConfiguration.getBoolean("FindBroadcast");
        boolean bl2 = yamlConfiguration.getBoolean("EquipOnEntity");
        String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)yamlConfiguration.getString("Item.Display"));
        List list2 = yamlConfiguration.getStringList("Item.Lore");
        ArrayList<String> arrayList2 = new ArrayList<String>();
        for (String string6 : list2) {
            arrayList2.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string6.replace("%c%", string4)));
        }
        boolean bl3 = yamlConfiguration.getBoolean("Item.Color.Random");
        String[] arrstring = yamlConfiguration.getString("Item.Color.Value").split(",");
        Color color = Color.fromRGB((int)Integer.parseInt(arrstring[0]), (int)Integer.parseInt(arrstring[1]), (int)Integer.parseInt(arrstring[2]));
        boolean bl4 = yamlConfiguration.getBoolean("Item.Materials.Reverse");
        List list3 = yamlConfiguration.getStringList("Item.Materials.BlackList");
        HashSet<Material> hashSet = new HashSet<Material>(Resources.getAllMaterials());
        ArrayList<Object> arrayList3 = new ArrayList();
        if (!bl4) {
            arrayList3 = new ArrayList<Material>(hashSet);
        }
        for (String string7 : list3) {
            for (Material list4 : hashSet) {
                if ((!string7.contains("*") || !list4.name().startsWith(string7.replace("*", "")) && !list4.name().endsWith(string7.replace("*", ""))) && (string7.contains("*") || !list4.name().equalsIgnoreCase(string7))) continue;
                if (bl4) {
                    arrayList3.add((Object)list4);
                    continue;
                }
                arrayList3.remove((Object)list4);
            }
        }
        boolean bl5 = yamlConfiguration.getBoolean("Item.MaterialData.Reverse");
        boolean bl6 = yamlConfiguration.getBoolean("Item.MaterialData.SetUnbreakable");
        List list = yamlConfiguration.getIntegerList("Item.MaterialData.BlackList");
        HashMap hashMap = new HashMap();
        Object[] arrobject = yamlConfiguration.getConfigurationSection("Item.MaterialData.Special").getKeys(false).toArray();
        Object object7 = arrobject;
        boolean bl7 = ((Object[])object7).length;
        boolean bl8 = false;
        while (bl8 < bl7) {
            Object n = object7[bl8];
            object62 = n.toString();
            arrayList = yamlConfiguration.getIntegerList("Item.MaterialData.Special." + (String)object62);
            hashMap.put(object62, arrayList);
            bl8 += 1;
        }
        int n = yamlConfiguration.getInt("Item.Enchants.Min");
        bl8 = yamlConfiguration.getInt("Item.Enchants.Max");
        bl7 = (boolean)(yamlConfiguration.getBoolean("Item.Enchants.SafeOnly") ? 1 : 0);
        object7 = new HashMap();
        for (Object object62 : yamlConfiguration.getStringList("Item.Enchants.List")) {
            object = Enchantment.getByName((String)object62.split(":")[0]);
            if (object == null) continue;
            object52 = String.valueOf(object62.split(":")[1]) + ":" + object62.split(":")[2];
            object7.put(object, object52);
        }
        object62 = yamlConfiguration.getStringList("Item.Flags");
        arrayList = new ArrayList<ItemFlag>();
        object52 = object62.iterator();
        while (object52.hasNext()) {
            object = (String)object52.next();
            try {
                arrayList.add(ItemFlag.valueOf((String)object));
            }
            catch (IllegalArgumentException object52) {
                ErrorLog.sendError(this, "Item.Flags." + (String)object, "Invalid Item Flag!", false);
            }
        }
        object = new HashMap();
        for (Object object52 : yamlConfiguration.getConfigurationSection("Item.DamageTypes").getKeys(false)) {
            bl11 = this.plugin.getCFG().getDamageTypes().get(object52.toLowerCase());
            if (bl11 == null) continue;
            double armorType = yamlConfiguration.getDouble("Item.DamageTypes." + (String)object52 + ".Chance");
            double arrammoType = yamlConfiguration.getDouble("Item.DamageTypes." + (String)object52 + ".Min");
            double list5 = yamlConfiguration.getDouble("Item.DamageTypes." + (String)object52 + ".Max");
            String n2 = String.valueOf(armorType) + ":" + arrammoType + ":" + list5;
            object.put(bl11, n2);
        }
        object52 = new HashMap();
        for (Object object32 : yamlConfiguration.getConfigurationSection("Item.ArmorTypes").getKeys(false)) {
            ArmorType bl10 = this.plugin.getCFG().getArmorTypes().get(object32.toLowerCase());
            if (bl10 == null) continue;
            double bl9 = yamlConfiguration.getDouble("Item.ArmorTypes." + (String)object32 + ".Chance");
            d = yamlConfiguration.getDouble("Item.ArmorTypes." + (String)object32 + ".Min");
            double list6 = yamlConfiguration.getDouble("Item.ArmorTypes." + (String)object32 + ".Max");
            String n3 = String.valueOf(bl9) + ":" + d + ":" + list6;
            object52.put(bl10, n3);
        }
        object32 = new HashMap();
        AmmoType[] d7 = AmmoType.values();
        boolean string10 = d7.length;
        boolean bl9 = false;
        while (bl9 < string10) {
            bl11 = d7[bl9];
            if (yamlConfiguration.contains("Item.AmmoTypes." + bl11.name())) {
                d = yamlConfiguration.getDouble("Item.AmmoTypes." + bl11.name());
                object32.put(bl11, d);
            }
            bl9 += 1;
        }
        object32 = (HashMap)Utils.sortByValue(object32);
        boolean bl10 = yamlConfiguration.getBoolean("Item.Restrictions.Soulbound");
        bl9 = (boolean)(yamlConfiguration.getBoolean("Item.Restrictions.Untradeable") ? 1 : 0);
        String string6 = yamlConfiguration.getString("Item.Restrictions.Levels");
        double d2 = yamlConfiguration.getDouble("Item.Restrictions.LevelScaleValues");
        List list4 = yamlConfiguration.getStringList("Item.Restrictions.LevelScaleBlackList");
        List list5 = yamlConfiguration.getStringList("Item.Restrictions.Classes");
        int n2 = yamlConfiguration.getInt("Item.Restrictions.MaxAttributes");
        int n3 = yamlConfiguration.getInt("Item.Restrictions.MaxBonuses");
        HashMap<ItemStat, String> hashMap2 = new HashMap<ItemStat, String>();
        ItemStat[] n6 = ItemStat.values();
        int tier = n6.length;
        int slotType = 0;
        while (slotType < tier) {
            object2 = n6[slotType];
            object4 = "Item.Attributes." + object2.name();
            if (yamlConfiguration.contains((String)object4)) {
                double string12 = yamlConfiguration.getDouble(String.valueOf(object4) + ".Default.Chance");
                double d3 = yamlConfiguration.getDouble(String.valueOf(object4) + ".Default.Min");
                double n8 = yamlConfiguration.getDouble(String.valueOf(object4) + ".Default.Max");
                double d4 = yamlConfiguration.getDouble(String.valueOf(object4) + ".Bonus.Chance");
                double d5 = yamlConfiguration.getDouble(String.valueOf(object4) + ".Bonus.Min");
                double d6 = yamlConfiguration.getDouble(String.valueOf(object4) + ".Bonus.Max");
                String string7 = String.valueOf(string12) + ":" + d3 + ":" + n8 + "!" + d4 + ":" + d5 + ":" + d6;
                hashMap2.put((ItemStat)((Object)object2), string7);
            }
            ++slotType;
        }
        object2 = new HashMap();
        object4 = SlotType.values();
        int n4 = ((SlotType[])object4).length;
        tier = 0;
        while (tier < n4) {
            SlotType hashMap3 = object4[tier];
            String string8 = "Item.Slots." + hashMap3.name();
            if (yamlConfiguration.contains(string8)) {
                double d8 = yamlConfiguration.getDouble(String.valueOf(string8) + ".Chance");
                int n5 = yamlConfiguration.getInt(String.valueOf(string8) + ".Min");
                int n7 = yamlConfiguration.getInt(String.valueOf(string8) + ".Max");
                String string9 = String.valueOf(d8) + ":" + n5 + ":" + n7;
                object2.put(hashMap3, string9);
            }
            ++tier;
        }
        HashMap<ResourceType, List<String>> hashMap3 = new HashMap<ResourceType, List<String>>();
        hashMap3.put(ResourceType.PREFIX, Resources.getSource(ResourceType.PREFIX, ResourceSubType.TIER, string2));
        hashMap3.put(ResourceType.SUFFIX, Resources.getSource(ResourceType.SUFFIX, ResourceSubType.TIER, string2));
        Tier tier2 = new Tier(string2, string3, string4, bl, bl2, string5, arrayList2, bl3, color, bl4, arrayList3, bl5, bl6, list, hashMap, n, bl8 ? 1 : 0, bl7, (HashMap<Enchantment, String>)object7, arrayList, (HashMap<DamageType, String>)object, (HashMap<ArmorType, String>)object52, (HashMap<AmmoType, Double>)object32, bl10, bl9, string6, d2, list4, list5, n2, n3, hashMap2, (HashMap<SlotType, String>)object2, hashMap3);
        return tier2;
    }

    public ItemStack replaceSet(ItemStack itemStack, Tier tier) {
        if (this.plugin.getMM().getSetManager() == null || !this.plugin.getMM().getSetManager().isActive()) {
            return this.replaceLore(itemStack, "SET", "delz");
        }
        int n = itemStack.getItemMeta().getLore().indexOf("%SET%");
        if (n < 0) {
            return itemStack;
        }
        itemStack = this.plugin.getMM().getSetManager().replaceLore(itemStack);
        return this.replaceLore(itemStack, "SET", "delz");
    }

    public ItemStack replaceEnchants(ItemStack itemStack) {
        int n = itemStack.getItemMeta().getLore().indexOf("%ENCHANTS%");
        if (n < 0) {
            return itemStack;
        }
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasEnchants()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            List list = itemMeta.getLore();
            for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                String string = String.valueOf(this.plugin.getCM().getDefaultEnchantName(enchantment)) + " " + Utils.IntegerToRomanNumeral(itemMeta.getEnchantLevel(enchantment));
                list.add(n, string);
            }
            itemMeta.setLore(list);
            itemStack.setItemMeta(itemMeta);
        }
        itemStack = this.replaceLore(itemStack, "ENCHANTS", "delz");
        return itemStack;
    }

    public ItemStack replaceDamageTypes(ItemStack itemStack, Tier tier, double d) {
        int n = itemStack.getItemMeta().getLore().indexOf("%DAMAGE_TYPES%");
        if (n < 0) {
            return itemStack;
        }
        if (!ItemUtils.isWeapon(itemStack) || tier.getDamageTypes().isEmpty()) {
            return this.replaceLore(itemStack, "DAMAGE_TYPES", "delz");
        }
        for (DamageType damageType : tier.getDamageTypes().keySet()) {
            String string = tier.getDamageTypes().get(damageType);
            double d2 = Double.parseDouble(string.split(":")[0]);
            if (Utils.getRandDouble(0.0, 100.0) > d2) continue;
            if (tier.getLevelScaleBlack().contains(damageType.getId())) {
                d = 1.0;
            }
            double d3 = Double.parseDouble(string.split(":")[1]) * d;
            double d4 = Double.parseDouble(string.split(":")[2]) * d;
            double d5 = Utils.round3(Utils.getRandDouble(d3, d4));
            double d6 = Utils.round3(Utils.getRandDouble(d3, d4));
            itemStack = ItemAPI.addDamageType(itemStack, damageType, d5, d6, n);
        }
        itemStack = this.replaceLore(itemStack, "DAMAGE_TYPES", "delz");
        return itemStack;
    }

    public ItemStack replaceArmorTypes(ItemStack itemStack, Tier tier, double d) {
        int n = itemStack.getItemMeta().getLore().indexOf("%ARMOR_TYPES%");
        if (n < 0) {
            this.replaceLore(itemStack, "ARMOR_TYPES", "delz");
        }
        if (!ItemUtils.isArmor(itemStack) || tier.getArmorTypes().isEmpty()) {
            return this.replaceLore(itemStack, "ARMOR_TYPES", "delz");
        }
        for (ArmorType armorType : tier.getArmorTypes().keySet()) {
            String string = tier.getArmorTypes().get(armorType);
            double d2 = Double.parseDouble(string.split(":")[0]);
            if (Utils.getRandDouble(0.0, 100.0) > d2) continue;
            if (tier.getLevelScaleBlack().contains(armorType.getId())) {
                d = 1.0;
            }
            double d3 = Double.parseDouble(string.split(":")[1]) * d;
            double d4 = Double.parseDouble(string.split(":")[2]) * d;
            double d5 = Utils.round3(Utils.getRandDouble(d3, d4));
            itemStack = ItemAPI.addDefenseType(itemStack, armorType, d5, n);
        }
        itemStack = this.replaceLore(itemStack, "ARMOR_TYPES", "delz");
        return itemStack;
    }

    public ItemStack replaceAmmoTypes(ItemStack itemStack, Tier tier) {
        int n = itemStack.getItemMeta().getLore().indexOf("%AMMO_TYPE%");
        if (n < 0) {
            return itemStack;
        }
        if (itemStack.getType() != Material.BOW || tier.getDamageTypes().isEmpty()) {
            return this.replaceLore(itemStack, "AMMO_TYPE", "delz");
        }
        AmmoType ammoType = AmmoType.ARROW;
        for (AmmoType ammoType2 : tier.getAmmoTypes().keySet()) {
            double d = tier.getAmmoTypes().get((Object)ammoType2);
            if (Utils.getRandDouble(0.0, 100.0) > d) continue;
            ammoType = ammoType2;
            break;
        }
        itemStack = ItemAPI.setAmmoType(itemStack, ammoType, n);
        itemStack = this.replaceLore(itemStack, "AMMO_TYPE", "delz");
        return itemStack;
    }

    public ItemStack replaceClass(ItemStack itemStack, Tier tier) {
        if (this.plugin.getHM().getClassPlugin() != Hook.NONE) {
            String string = "";
            String string2 = this.plugin.getCM().getCFG().getStrClassColor();
            String string3 = this.plugin.getCM().getCFG().getStrClassSeparator();
            for (String string4 : tier.getClasses()) {
                string = String.valueOf(string) + string2 + string4 + string3;
            }
            if (string.length() > 3) {
                string = string.substring(0, string.length() - 3);
            }
            if (!string.isEmpty()) {
                return this.replaceLore(itemStack, "CLASS", String.valueOf(this.plugin.getCM().getCFG().getStrClass()) + string);
            }
        }
        return this.replaceLore(itemStack, "CLASS", "delz");
    }

    public ItemStack replaceLevel(ItemStack itemStack, int n) {
        String string = this.plugin.getCM().getCFG().getStrLevel().replace("%n", String.valueOf(n));
        return this.replaceLore(itemStack, "LEVEL", string);
    }

    public ItemStack replaceSoul(ItemStack itemStack) {
        return this.replaceLore(itemStack, "SOULBOUND", this.plugin.getMM().getSoulboundManager().getSoulString());
    }

    public ItemStack replaceUntrade(ItemStack itemStack) {
        return this.replaceLore(itemStack, "SOULBOUND", this.plugin.getMM().getSoulboundManager().getUntradeString());
    }

    public ItemStack replaceSlots(ItemStack itemStack, Tier tier) {
        for (SlotType slotType : tier.getSlots().keySet()) {
            int n;
            String string = tier.getSlots().get((Object)slotType);
            double d = Double.parseDouble(string.split(":")[0]);
            int n2 = 0;
            if (Utils.getRandDouble(0.0, 100.0) <= d) {
                int n3 = Integer.parseInt(string.split(":")[1]);
                n = Integer.parseInt(string.split(":")[2]);
                n2 = Utils.randInt(n3, n);
            }
            String string2 = "delz";
            if (n2 > 0 && slotType.getModule() != null && slotType.getModule().isActive()) {
                string2 = slotType.getHeader();
            }
            if ((n = itemStack.getItemMeta().getLore().indexOf("%" + slotType.name() + "%") + 1) == -1) continue;
            itemStack = this.replaceLore(itemStack, slotType.name(), string2);
            int n4 = 0;
            while (n4 < n2) {
                itemStack = ItemAPI.addDivineSlot(itemStack, slotType, n);
                ++n4;
            }
        }
        return itemStack;
    }

    public ItemStack setMetaFlags(ItemStack itemStack, Tier tier) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return itemStack;
        }
        itemMeta.addItemFlags(tier.getFlags().toArray((T[])new ItemFlag[tier.getFlags().size()]));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack replaceAttributes(ItemStack itemStack, Tier tier, double d) {
        HashSet<ItemStat> hashSet = new HashSet<ItemStat>(tier.getAttributes().keySet());
        int n = 0;
        int n2 = 0;
        for (ItemStat itemStat : hashSet) {
            double d2;
            double d3;
            double d4;
            double d5;
            boolean bl = true;
            if (itemStat.getType() == ItemStat.ItemType.BOTH) {
                if (!Resources.getAllMaterials().contains((Object)itemStack.getType())) {
                    itemStack = this.replaceLore(itemStack, itemStat.name(), "delz");
                    bl = false;
                }
            } else if (itemStat.getType() == ItemStat.ItemType.ARMOR) {
                if (!this.plugin.getCM().getCFG().getArmors().contains((Object)itemStack.getType())) {
                    itemStack = this.replaceLore(itemStack, itemStat.name(), "delz");
                    bl = false;
                }
            } else if (itemStat.getType() == ItemStat.ItemType.WEAPON && !ItemUtils.isWeapon(itemStack)) {
                itemStack = this.replaceLore(itemStack, itemStat.name(), "delz");
                bl = false;
            }
            double d6 = d;
            if (tier.getLevelScaleBlack().contains(itemStat.name())) {
                d6 = 1.0;
            }
            String string = tier.getAttributes().get((Object)itemStat);
            if ((tier.getMaxAttributes() != 0 && n < tier.getMaxAttributes() || tier.getMaxAttributes() == -1) && bl && !ItemAPI.hasAttribute(itemStack, itemStat)) {
                int n3 = itemStack.getItemMeta().getLore().indexOf("%" + itemStat.name() + "%");
                if (n3 < 0) continue;
                double d7 = Double.parseDouble(string.split("!")[0].split(":")[0]);
                if (Utils.getRandDouble(0.0, 100.0) <= d7) {
                    d2 = Double.parseDouble(string.split("!")[0].split(":")[1]) * d6;
                    d5 = Double.parseDouble(string.split("!")[0].split(":")[2]) * d6;
                    d4 = Utils.round3(Utils.getRandDouble(d2, d5));
                    d3 = Utils.round3(Utils.getRandDouble(d2, d5));
                    itemStack = ItemAPI.addAttribute(itemStack, itemStat, false, String.valueOf(d4), String.valueOf(d3), n3);
                    if (tier.getMaxAttributes() > 0) {
                        ++n;
                    }
                }
            }
            ItemStat itemStat2 = null;
            if (ItemAPI.hasAttribute(itemStack, ItemStat.CRITICAL_DAMAGE) && !ItemAPI.hasAttribute(itemStack, ItemStat.CRITICAL_RATE)) {
                itemStat2 = ItemStat.CRITICAL_RATE;
            } else if (ItemAPI.hasAttribute(itemStack, ItemStat.CRITICAL_RATE) && !ItemAPI.hasAttribute(itemStack, ItemStat.CRITICAL_DAMAGE)) {
                itemStat2 = ItemStat.CRITICAL_DAMAGE;
            } else if (ItemAPI.hasAttribute(itemStack, ItemStat.BLOCK_DAMAGE) && !ItemAPI.hasAttribute(itemStack, ItemStat.BLOCK_RATE)) {
                itemStat2 = ItemStat.BLOCK_RATE;
            } else if (ItemAPI.hasAttribute(itemStack, ItemStat.BLOCK_RATE) && !ItemAPI.hasAttribute(itemStack, ItemStat.BLOCK_DAMAGE)) {
                itemStat2 = ItemStat.BLOCK_DAMAGE;
            }
            if (itemStat2 == null) continue;
            String string2 = tier.getAttributes().get((Object)itemStat2);
            int n4 = itemStack.getItemMeta().getLore().indexOf("%" + itemStat2.name() + "%");
            d2 = Double.parseDouble(string2.split("!")[0].split(":")[1]) * d6;
            d5 = Double.parseDouble(string2.split("!")[0].split(":")[2]) * d6;
            d4 = Utils.round3(Utils.getRandDouble(d2, d5));
            d3 = Utils.round3(Utils.getRandDouble(d2, d5));
            itemStack = ItemAPI.addAttribute(itemStack, itemStat2, false, String.valueOf(d4), String.valueOf(d3), n4);
        }
        for (ItemStat itemStat : hashSet) {
            itemStack = this.replaceLore(itemStack, itemStat.name(), "delz");
        }
        int n5 = itemStack.getItemMeta().getLore().indexOf("%BONUS_STATS%");
        for (ItemStat itemStat : hashSet) {
            if ((tier.getMaxBonuses() == 0 || n2 >= tier.getMaxBonuses()) && tier.getMaxBonuses() != -1 || n5 == -1) continue;
            String string = tier.getAttributes().get((Object)itemStat);
            double d8 = Double.parseDouble(string.split("!")[1].split(":")[0]);
            if (Utils.getRandDouble(0.0, 100.0) > d8) continue;
            double d9 = d;
            if (tier.getLevelScaleBlack().contains(itemStat.name())) {
                d9 = 1.0;
            }
            double d10 = Double.parseDouble(string.split("!")[1].split(":")[1]) * d9;
            double d11 = Double.parseDouble(string.split("!")[1].split(":")[2]) * d9;
            double d12 = Utils.round3(Utils.getRandDoubleNega(d10, d11));
            double d13 = Utils.round3(Utils.getRandDoubleNega(d10, d11));
            itemStack = ItemAPI.addAttribute(itemStack, itemStat, true, String.valueOf(d12), String.valueOf(d13), n5);
            if (tier.getMaxBonuses() <= 0) continue;
            ++n2;
        }
        itemStack = this.replaceLore(itemStack, "BONUS_STATS", "delz");
        return itemStack;
    }

    public ItemStack replaceLore(ItemStack itemStack, String string, String string2) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        if (list == null) {
            return itemStack;
        }
        String string3 = "%" + string.toUpperCase() + "%";
        int n = 0;
        for (String string4 : list) {
            if (!string4.contains(string3)) continue;
            n = list.indexOf(string4);
            list.remove(n);
            if (string2.equals("delz") || string2.equals("")) break;
            list.add(n, string4.replace(string3, string2));
            break;
        }
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Collection<Tier> getTiers() {
        return this.tiers.values();
    }

    public List<String> getTierNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Tier tier : this.getTiers()) {
            arrayList.add(tier.getId());
        }
        return arrayList;
    }

    public Tier getTierById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<Tier>(this.getTiers()).get(this.r.nextInt(this.getTiers().size()));
        }
        return this.tiers.get(string.toLowerCase());
    }

    public boolean isTiered(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("TIER");
    }

    public String getTierId(ItemStack itemStack) {
        return new NBTItem(itemStack).getString("TIER");
    }

    public class Tier {
        private String t_id;
        private String t_name;
        private String t_color;
        private boolean bc;
        private boolean equip;
        private String i_metaname;
        private List<String> i_lore;
        private boolean i_c_rand;
        private Color i_c_value;
        private boolean mat_reverse;
        private List<Material> mat_list;
        private boolean data_reverse;
        private boolean data_unbreak;
        private List<Integer> data_list;
        private HashMap<String, List<Integer>> data_spec;
        private int ench_min;
        private int ench_max;
        private boolean ench_safe;
        private HashMap<Enchantment, String> ench_list;
        private List<ItemFlag> flags;
        private HashMap<DamageType, String> dtypes;
        private HashMap<ArmorType, String> armtypes;
        private HashMap<AmmoType, Double> atypes;
        private boolean rest_soul;
        private boolean rest_untrade;
        private String rest_level;
        private double rest_level_scale;
        private List<String> rest_level_scale_black;
        private List<String> rest_class;
        private int max_att;
        private int max_bonus;
        private HashMap<ItemStat, String> att;
        private HashMap<SlotType, String> slots;
        private HashMap<ResourceType, List<String>> source;

        public Tier(String string, String string2, String string3, boolean bl, boolean bl2, String string4, List<String> list, boolean bl3, Color color, boolean bl4, List<Material> list2, boolean bl5, boolean bl6, List<Integer> list3, HashMap<String, List<Integer>> hashMap, int n, int n2, boolean bl7, HashMap<Enchantment, String> hashMap2, List<ItemFlag> list4, HashMap<DamageType, String> hashMap3, HashMap<ArmorType, String> hashMap4, HashMap<AmmoType, Double> hashMap5, boolean bl8, boolean bl9, String string5, double d, List<String> list5, List<String> list6, int n3, int n4, HashMap<ItemStat, String> hashMap6, HashMap<SlotType, String> hashMap7, HashMap<ResourceType, List<String>> hashMap8) {
            this.setId(string);
            this.setName(string2);
            this.setColor(string3);
            this.setBroadcast(bl);
            this.setEquipOnEntity(bl2);
            this.setMetaName(string4);
            this.setLore(list);
            this.setRandomLeatherColor(bl3);
            this.setLeatherColor(color);
            this.setMaterialReversed(bl4);
            this.setMaterials(list2);
            this.setDataReversed(bl5);
            this.setDataUnbreak(bl6);
            this.setDatas(list3);
            this.setDataSpecial(hashMap);
            this.setMinEnchantments(n);
            this.setMaxEnchantments(n2);
            this.setSafeEnchant(bl7);
            this.setEnchantments(hashMap2);
            this.setFlags(list4);
            this.setDamageTypes(hashMap3);
            this.setArmorTypes(hashMap4);
            this.setAmmoTypes(hashMap5);
            this.setNeedSoul(bl8);
            this.setNonTrade(bl9);
            this.setLevels(string5);
            this.setLevelScale(d);
            this.setLevelScaleBlack(list5);
            this.setClasses(list6);
            this.setMaxAttributes(n3);
            this.setMaxBonuses(n4);
            this.setAttributes(hashMap6);
            this.setSlots(hashMap7);
            this.setSource(hashMap8);
        }

        public String getId() {
            return this.t_id;
        }

        public void setId(String string) {
            this.t_id = string;
        }

        public String getName() {
            return this.t_name;
        }

        public void setName(String string) {
            this.t_name = string;
        }

        public String getColor() {
            return this.t_color;
        }

        public void setColor(String string) {
            this.t_color = string;
        }

        public boolean isBroadcast() {
            return this.bc;
        }

        public void setBroadcast(boolean bl) {
            this.bc = bl;
        }

        public boolean isEquipOnEntity() {
            return this.equip;
        }

        public void setEquipOnEntity(boolean bl) {
            this.equip = bl;
        }

        public String getMetaName() {
            return this.i_metaname;
        }

        public void setMetaName(String string) {
            this.i_metaname = string;
        }

        public List<String> getLore() {
            return this.i_lore;
        }

        public void setLore(List<String> list) {
            this.i_lore = list;
        }

        public boolean isRandomLeatherColor() {
            return this.i_c_rand;
        }

        public void setRandomLeatherColor(boolean bl) {
            this.i_c_rand = bl;
        }

        public Color getLeatherColor() {
            return this.i_c_value;
        }

        public void setLeatherColor(Color color) {
            this.i_c_value = color;
        }

        public boolean isMaterialReversed() {
            return this.mat_reverse;
        }

        public void setMaterialReversed(boolean bl) {
            this.mat_reverse = bl;
        }

        public List<Material> getMaterials() {
            return this.mat_list;
        }

        public void setMaterials(List<Material> list) {
            this.mat_list = list;
        }

        public boolean isDataReversed() {
            return this.data_reverse;
        }

        public void setDataReversed(boolean bl) {
            this.data_reverse = bl;
        }

        public boolean isDataUnbreak() {
            return this.data_unbreak;
        }

        public void setDataUnbreak(boolean bl) {
            this.data_unbreak = bl;
        }

        public List<Integer> getDatas() {
            return this.data_list;
        }

        public void setDatas(List<Integer> list) {
            this.data_list = list;
        }

        public HashMap<String, List<Integer>> getDataSpecial() {
            return this.data_spec;
        }

        public void setDataSpecial(HashMap<String, List<Integer>> hashMap) {
            this.data_spec = hashMap;
        }

        public int getMinEnchantments() {
            return this.ench_min;
        }

        public void setMinEnchantments(int n) {
            this.ench_min = n;
        }

        public int getMaxEnchantments() {
            return this.ench_max;
        }

        public void setMaxEnchantments(int n) {
            this.ench_max = n;
        }

        public boolean isSafeEnchant() {
            return this.ench_safe;
        }

        public void setSafeEnchant(boolean bl) {
            this.ench_safe = bl;
        }

        public HashMap<Enchantment, String> getEnchantments() {
            return this.ench_list;
        }

        public void setEnchantments(HashMap<Enchantment, String> hashMap) {
            this.ench_list = hashMap;
        }

        public List<ItemFlag> getFlags() {
            return this.flags;
        }

        public void setFlags(List<ItemFlag> list) {
            this.flags = list;
        }

        public HashMap<DamageType, String> getDamageTypes() {
            return this.dtypes;
        }

        public void setDamageTypes(HashMap<DamageType, String> hashMap) {
            this.dtypes = hashMap;
        }

        public HashMap<ArmorType, String> getArmorTypes() {
            return this.armtypes;
        }

        public void setArmorTypes(HashMap<ArmorType, String> hashMap) {
            this.armtypes = hashMap;
        }

        public HashMap<AmmoType, Double> getAmmoTypes() {
            return this.atypes;
        }

        public void setAmmoTypes(HashMap<AmmoType, Double> hashMap) {
            this.atypes = hashMap;
        }

        public boolean isNeedSoul() {
            return this.rest_soul;
        }

        public void setNeedSoul(boolean bl) {
            this.rest_soul = bl;
        }

        public boolean isNonTrade() {
            return this.rest_untrade;
        }

        public void setNonTrade(boolean bl) {
            this.rest_untrade = bl;
        }

        public String getLevels() {
            return this.rest_level;
        }

        public void setLevels(String string) {
            this.rest_level = string;
        }

        public double getLevelScale() {
            return this.rest_level_scale;
        }

        public void setLevelScale(double d) {
            this.rest_level_scale = d;
        }

        public List<String> getLevelScaleBlack() {
            return this.rest_level_scale_black;
        }

        public void setLevelScaleBlack(List<String> list) {
            this.rest_level_scale_black = list;
        }

        public List<String> getClasses() {
            return this.rest_class;
        }

        public void setClasses(List<String> list) {
            this.rest_class = list;
        }

        public int getMaxAttributes() {
            return this.max_att;
        }

        public void setMaxAttributes(int n) {
            this.max_att = n;
        }

        public int getMaxBonuses() {
            return this.max_bonus;
        }

        public void setMaxBonuses(int n) {
            this.max_bonus = n;
        }

        public HashMap<ItemStat, String> getAttributes() {
            return this.att;
        }

        public void setAttributes(HashMap<ItemStat, String> hashMap) {
            this.att = hashMap;
        }

        public HashMap<SlotType, String> getSlots() {
            return this.slots;
        }

        public void setSlots(HashMap<SlotType, String> hashMap) {
            this.slots = hashMap;
        }

        public HashMap<ResourceType, List<String>> getSource() {
            return this.source;
        }

        public void setSource(HashMap<ResourceType, List<String>> hashMap) {
            this.source = hashMap;
        }

        public ItemStack create(int n, Material material) {
            List<String> list;
            Object object;
            BlockStateMeta blockStateMeta;
            ItemStack itemStack = new ItemStack(Material.STONE_SWORD);
            if (this.getMaterials().size() <= 0) {
                return itemStack;
            }
            if (material != null && this.getMaterials().contains((Object)material)) {
                itemStack.setType(material);
            } else {
                itemStack.setType(this.getMaterials().get(TierManager.this.r.nextInt(this.getMaterials().size())));
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<Integer> list2 = this.getDatas();
            if (this.getDataSpecial().containsKey(itemStack.getType().name())) {
                list2 = this.getDataSpecial().get(itemStack.getType().name());
            }
            if (!list2.isEmpty()) {
                if (this.isDataUnbreak()) {
                    itemMeta.spigot().setUnbreakable(true);
                }
                if (this.isDataReversed()) {
                    itemStack.setDurability((short)list2.get(TierManager.this.r.nextInt(list2.size())).intValue());
                } else {
                    int n2 = Utils.randInt(1, itemStack.getType().getMaxDurability());
                    while (list2.contains(n2)) {
                        n2 = Utils.randInt(1, itemStack.getType().getMaxDurability());
                    }
                    itemStack.setDurability((short)n2);
                }
            }
            itemMeta.setLore(this.getLore());
            String string = itemStack.getType().name();
            String string2 = "";
            String string3 = "";
            String string4 = "";
            String string5 = "";
            String string6 = "";
            String string7 = "";
            String string8 = "";
            List<String> list3 = this.getSource().get((Object)ResourceType.PREFIX);
            if (!list3.isEmpty()) {
                string2 = list3.get(TierManager.this.r.nextInt(list3.size()));
            }
            if (!(list = this.getSource().get((Object)ResourceType.SUFFIX)).isEmpty()) {
                string3 = list.get(TierManager.this.r.nextInt(list.size()));
            }
            List<String> list4 = Resources.getSourceByFullType(ResourceType.PREFIX, string);
            List<String> list5 = Resources.getSourceByFullType(ResourceType.SUFFIX, string);
            if (list4.size() > 0) {
                string5 = list4.get(TierManager.this.r.nextInt(list4.size()));
            }
            if (list5.size() > 0) {
                string6 = list5.get(TierManager.this.r.nextInt(list5.size()));
            }
            List<String> list6 = Resources.getSourceByHalfType(ResourceType.PREFIX, string);
            List<String> list7 = Resources.getSourceByHalfType(ResourceType.SUFFIX, string);
            if (list6 != null && list6.size() > 0) {
                string7 = list6.get(TierManager.this.r.nextInt(list6.size()));
            }
            if (list7 != null && list7.size() > 0) {
                string8 = list7.get(TierManager.this.r.nextInt(list7.size()));
            }
            string4 = string.split("_").length == 2 ? Lang.getHalfType(itemStack.getType()) : TierManager.this.plugin.getCM().getDefaultItemName(itemStack);
            String string9 = this.getMetaName().replace("%itemtype%", string4).replace("%suffix_tier%", string3).replace("%prefix_tier%", string2).replace("%prefix_type%", string7).replace("%suffix_type%", string8).replace("%prefix_material%", string5).replace("%suffix_material%", string6).replace("%c%", "");
            string9 = string9.trim().replaceAll("\\s+", " ");
            string9 = String.valueOf(this.getColor()) + string9;
            itemMeta.setDisplayName(string9);
            itemStack.setItemMeta(itemMeta);
            if (itemStack.getType().name().startsWith("LEATHER_")) {
                blockStateMeta = (BlockStateMeta)itemStack.getItemMeta();
                if (this.isRandomLeatherColor()) {
                    blockStateMeta.setColor(Color.fromRGB((int)TierManager.this.r.nextInt(255), (int)TierManager.this.r.nextInt(255), (int)TierManager.this.r.nextInt(255)));
                } else {
                    blockStateMeta.setColor(this.getLeatherColor());
                }
                itemStack.setItemMeta((ItemMeta)blockStateMeta);
            } else if (itemStack.getType() == Material.SHIELD && (blockStateMeta = (BlockStateMeta)(itemMeta = itemStack.getItemMeta())) != null && blockStateMeta.hasBlockState() && blockStateMeta.getBlockState() != null) {
                Banner banner = (Banner)blockStateMeta.getBlockState();
                DyeColor[] arrdyeColor = DyeColor.values();
                DyeColor dyeColor = arrdyeColor[TierManager.this.r.nextInt(arrdyeColor.length - 1)];
                banner.setBaseColor(dyeColor);
                PatternType[] arrpatternType = PatternType.values();
                PatternType patternType = arrpatternType[TierManager.this.r.nextInt(arrpatternType.length - 1)];
                DyeColor[] arrdyeColor2 = DyeColor.values();
                object = arrdyeColor2[TierManager.this.r.nextInt(arrdyeColor2.length - 1)];
                banner.addPattern(new Pattern(object, patternType));
                banner.update();
                blockStateMeta.setBlockState((BlockState)banner);
                itemStack.setItemMeta((ItemMeta)blockStateMeta);
            }
            blockStateMeta = this.getLevels().split("-");
            int n3 = 1;
            int n4 = 1;
            try {
                n3 = Integer.parseInt((String)blockStateMeta[0]);
                n4 = Integer.parseInt((String)blockStateMeta[1]);
            }
            catch (ArrayIndexOutOfBoundsException | NumberFormatException runtimeException) {
                // empty catch block
            }
            if (n > 0 && n > n4) {
                n = n4;
            }
            if (n > 0 && n < n3) {
                n = n3;
            }
            if (n <= 0) {
                n = TierManager.this.r.nextInt(n4 - n3 + 1) + n3;
            }
            double d = (this.getLevelScale() * 100.0 - 100.0) * (double)n / 100.0 + 1.0;
            int n5 = this.getMinEnchantments();
            int n6 = this.getMaxEnchantments();
            if (n5 >= 0 && n6 >= 0) {
                int n7 = TierManager.this.r.nextInt(n6 - n5 + 1) + n5;
                ArrayList<Enchantment> arrayList = new ArrayList<Enchantment>();
                for (Enchantment enchantment : this.getEnchantments().keySet()) {
                    arrayList.add(enchantment);
                }
                int n8 = 0;
                while (n8 < n7) {
                    if (arrayList.isEmpty()) break;
                    Enchantment enchantment = (Enchantment)arrayList.get(TierManager.this.r.nextInt(arrayList.size()));
                    double d2 = Math.max(1, Integer.parseInt(this.getEnchantments().get((Object)enchantment).split(":")[0]));
                    double d3 = Math.max(1, Integer.parseInt(this.getEnchantments().get((Object)enchantment).split(":")[1]));
                    int n9 = Utils.randInt((int)d2, (int)d3) - 1;
                    if (this.isSafeEnchant()) {
                        if (enchantment.canEnchantItem(itemStack)) {
                            itemStack.addUnsafeEnchantment(enchantment, n9);
                        } else {
                            --n8;
                        }
                    } else {
                        itemStack.addUnsafeEnchantment(enchantment, n9);
                    }
                    arrayList.remove((Object)enchantment);
                    ++n8;
                }
            }
            itemStack = TierManager.this.replaceDamageTypes(itemStack, this, d);
            itemStack = TierManager.this.replaceArmorTypes(itemStack, this, d);
            itemStack = TierManager.this.replaceAmmoTypes(itemStack, this);
            itemStack = TierManager.this.replaceLevel(itemStack, n);
            itemStack = TierManager.this.replaceClass(itemStack, this);
            itemStack = TierManager.this.replaceEnchants(itemStack);
            if (TierManager.this.plugin.getMM().getSoulboundManager().isActive()) {
                object = this.isNeedSoul();
                boolean bl = this.isNonTrade();
                if (object != false && bl) {
                    object = false;
                    bl = false;
                }
                itemStack = object != false ? new ItemStack(TierManager.this.replaceSoul(itemStack)) : (bl ? new ItemStack(TierManager.this.replaceUntrade(itemStack)) : new ItemStack(TierManager.this.replaceLore(itemStack, "SOULBOUND", "delz")));
            } else {
                itemStack = new ItemStack(TierManager.this.replaceLore(itemStack, "SOULBOUND", "delz"));
            }
            itemStack = new ItemStack(TierManager.this.setMetaFlags(itemStack, this));
            itemStack = new ItemStack(TierManager.this.replaceAttributes(itemStack, this, d));
            itemStack = new ItemStack(TierManager.this.replaceSlots(itemStack, this));
            String string10 = itemStack.getType().name().split("_").length == 2 ? Lang.getHalfType(itemStack.getType()) : TierManager.this.plugin.getCM().getDefaultItemName(itemStack);
            itemStack = TierManager.this.replaceSet(itemStack, this);
            itemStack = TierManager.this.replaceLore(itemStack, "TYPE", string10);
            itemStack = TierManager.this.replaceLore(itemStack, "TIER", this.getName());
            itemStack = TierManager.this.replaceLore(itemStack, "DEFENSE", "delz");
            itemStack = TierManager.this.replaceLore(itemStack, "DAMAGE", "delz");
            itemStack = TierManager.this.replaceLore(itemStack, "POISON_DEFENSE", "delz");
            itemStack = TierManager.this.replaceLore(itemStack, "MAGIC_DEFENSE", "delz");
            itemStack = TierManager.this.replaceLore(itemStack, "FIRE_DEFENSE", "delz");
            itemStack = TierManager.this.replaceLore(itemStack, "WATER_DEFENSE", "delz");
            itemStack = TierManager.this.replaceLore(itemStack, "WIND_DEFENSE", "delz");
            itemStack = ItemAPI.addNBTTag(itemStack, "TIER", this.getId());
            if (ItemUtils.isArmor(itemStack)) {
                itemStack = TierManager.this.plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.attackDamage, 0.0);
                itemStack = TierManager.this.plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.armor, ItemAPI.getDefaultDefense(itemStack));
                itemStack = TierManager.this.plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.armorToughness, ItemAPI.getDefaultToughness(itemStack));
            }
            return itemStack;
        }
    }

}

