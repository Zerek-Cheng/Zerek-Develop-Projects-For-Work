/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 */
package su.nightexpress.divineitems.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.entity.EntityDamageEvent;
import su.nightexpress.divineitems.attributes.BleedRateSettings;
import su.nightexpress.divineitems.attributes.DisarmRateSettings;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.attributes.StatSettings;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.types.AmmoType;
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.types.DamageType;

public class Config {
    private MyConfig config;
    private String lang;
    private Hook g_LevelPlugin;
    private Hook g_ClassPlugin;
    private String global_dformula_dmg;
    private String global_dformula_other;
    private double g_dmgReduce;
    private boolean g_itemsBreak;
    private int g_targetDist;
    private String g_attFormat;
    private String g_dtFormat;
    private String g_atFormat;
    private String g_ammoFormat;
    private boolean g_mobAtt;
    private boolean g_fishHookDmg;
    private boolean g_offAtt;
    private boolean g_sapiDur;
    private HashMap<String, Boolean> modules;
    private HashMap<EntityDamageEvent.DamageCause, Double> damage_values_p;
    private HashMap<EntityDamageEvent.DamageCause, Double> damage_values_m;
    private HashMap<String, DamageType> damage_types;
    private HashMap<String, ArmorType> armor_types;
    private String str_dmgSep;
    private String str_durSep;
    private String str_pc;
    private String str_neg;
    private String str_pos;
    private String str_mod;
    private String str_lvl;
    private String str_cls;
    private String str_clsSep;
    private String str_clsCol;
    private Set<Material> wpn;
    private Set<Material> tool;
    private Set<Material> arm;

    public Config(MyConfig myConfig) {
        this.config = myConfig;
        this.setup();
    }

    private void setup() {
        String string;
        Object object;
        Object object22;
        ArrayList<String> arrayList;
        String string2;
        Object object32;
        FileConfiguration fileConfiguration = this.config.getConfig();
        this.lang = fileConfiguration.getString("Lang");
        String string3 = fileConfiguration.getString("Global.LevelPlugin");
        String string4 = fileConfiguration.getString("Global.ClassPlugin");
        Object object4 = Hook.values();
        boolean bl = ((Hook[])object4).length;
        boolean bl2 = false;
        while (bl2 < bl) {
            Object object5 = object4[bl2];
            if (object5.isLevelPlugin() && object5.getPluginName().equalsIgnoreCase(string3)) {
                this.g_LevelPlugin = object5;
            }
            if (object5.isClassPlugin() && object5.getPluginName().equalsIgnoreCase(string4)) {
                this.g_ClassPlugin = object5;
            }
            bl2 += 1;
        }
        if (this.g_LevelPlugin == null) {
            try {
                this.g_LevelPlugin = Hook.valueOf(string3.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                this.g_LevelPlugin = Hook.NONE;
            }
        }
        if (this.g_ClassPlugin == null) {
            try {
                this.g_ClassPlugin = Hook.valueOf(string4.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                this.g_ClassPlugin = Hook.NONE;
            }
        }
        if (!this.g_LevelPlugin.isLevelPlugin() && this.g_LevelPlugin != Hook.NONE) {
            Bukkit.getConsoleSender().sendMessage("Warning: \u00a7c" + this.g_LevelPlugin.getPluginName() + "\u00a77 is not a level plugin!");
        }
        if (!this.g_ClassPlugin.isClassPlugin() && this.g_ClassPlugin != Hook.NONE) {
            Bukkit.getConsoleSender().sendMessage("Warning: \u00a7c" + this.g_LevelPlugin.getPluginName() + "\u00a77 is not a class plugin!");
        }
        this.global_dformula_dmg = fileConfiguration.getString("Global.DamageFormula.damage");
        this.global_dformula_other = fileConfiguration.getString("Global.DamageFormula.other");
        this.g_dmgReduce = fileConfiguration.getDouble("Global.DamageReduceCooldown");
        this.g_itemsBreak = fileConfiguration.getBoolean("Global.BreakItems");
        this.g_targetDist = fileConfiguration.getInt("Global.MaxGetTargetDistance");
        this.g_attFormat = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Global.AttributeFormat"));
        this.g_dtFormat = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Global.DamageTypeFormat"));
        this.g_atFormat = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Global.ArmorTypeFormat"));
        this.g_ammoFormat = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Global.AmmoTypeFormat"));
        this.g_mobAtt = fileConfiguration.getBoolean("Global.AllowAttributesToMobs");
        this.g_fishHookDmg = fileConfiguration.getBoolean("Global.AllowFishHookDamage");
        this.g_offAtt = fileConfiguration.getBoolean("Global.AllowAttributesInOffHand");
        this.g_sapiDur = fileConfiguration.getBoolean("Global.SkillAPISkillsReduceDurability");
        this.modules = new HashMap();
        for (Object object5 : fileConfiguration.getConfigurationSection("Modules").getKeys(false)) {
            bl = (boolean)(fileConfiguration.getBoolean("Modules." + (String)object5) ? 1 : 0);
            this.modules.put((String)object5, bl);
        }
        this.damage_values_p = new HashMap();
        this.damage_values_m = new HashMap();
        for (Object object5 : fileConfiguration.getConfigurationSection("DamageModifiers").getKeys(false)) {
            try {
                EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.valueOf((String)object5.toUpperCase());
                double d = fileConfiguration.getDouble("DamageModifiers." + (String)object5 + ".PLAYER");
                double d2 = fileConfiguration.getDouble("DamageModifiers." + (String)object5 + ".MOB");
                this.damage_values_p.put(damageCause, d);
                this.damage_values_m.put(damageCause, d2);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        this.damage_types = new HashMap();
        for (Object object5 : fileConfiguration.getConfigurationSection("DamageTypes").getKeys(false)) {
            String string5 = "DamageTypes." + (String)object5 + ".";
            boolean bl3 = fileConfiguration.getBoolean(String.valueOf(string5) + "Default");
            string = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string5) + "Prefix"));
            String string6 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string5) + "Name"));
            string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string5) + "Value"));
            arrayList = fileConfiguration.getStringList(String.valueOf(string5) + "HitActions");
            object32 = new HashMap<String, Double>();
            if (fileConfiguration.contains(String.valueOf(string5) + "BiomeDamageModifier")) {
                for (Object object22 : fileConfiguration.getConfigurationSection(String.valueOf(string5) + "BiomeDamageModifier").getKeys(false)) {
                    double d = fileConfiguration.getDouble(String.valueOf(string5) + "BiomeDamageModifier." + (String)object22);
                    object32.put(object22.toUpperCase(), d);
                }
            }
            object22 = new DamageType((String)object5, bl3, string, string6, string2, (List<String>)arrayList, (HashMap<String, Double>)object32);
            this.damage_types.put(object22.getId(), (DamageType)object22);
        }
        this.armor_types = new HashMap();
        for (Object object5 : fileConfiguration.getConfigurationSection("ArmorTypes").getKeys(false)) {
            String string7 = "ArmorTypes." + (String)object5 + ".";
            object4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string7) + "Prefix"));
            string = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string7) + "Name"));
            String string8 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string7) + "Value"));
            boolean bl4 = fileConfiguration.getBoolean(String.valueOf(string7) + "Percent");
            arrayList = new ArrayList<String>();
            for (Object object32 : fileConfiguration.getStringList(String.valueOf(string7) + "BlockDamageSource")) {
                arrayList.add(object32.toLowerCase());
            }
            object32 = new ArrayList();
            for (Object object22 : fileConfiguration.getStringList(String.valueOf(string7) + "BlockDamageTypes")) {
                object32.add(object22.toLowerCase());
            }
            object22 = fileConfiguration.getString(String.valueOf(string7) + "Formula");
            object = new ArmorType((String)object5, (String)object4, string, string8, bl4, arrayList, (List<String>)object32, (String)object22);
            this.armor_types.put(object.getId(), (ArmorType)object);
        }
        for (Object object5 : fileConfiguration.getConfigurationSection("Attributes").getKeys(false)) {
            StatSettings statSettings;
            String string9;
            ItemStat itemStat = null;
            try {
                itemStat = ItemStat.valueOf(object5.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                continue;
            }
            object4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Attributes." + itemStat.name() + ".Name"));
            string = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Attributes." + itemStat.name() + ".Prefix"));
            String string10 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Attributes." + itemStat.name() + ".Value"));
            string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Attributes." + itemStat.name() + ".Bonus"));
            double d = fileConfiguration.getDouble("Attributes." + itemStat.name() + ".Capability");
            itemStat.setName((String)object4);
            itemStat.setPrefix(string);
            itemStat.setValue(string10);
            itemStat.setBonus(string2);
            itemStat.setCapability(d);
            if (itemStat == ItemStat.DISARM_RATE) {
                object22 = "AttributeSettings." + itemStat.name() + ".";
                object = fileConfiguration.getString(String.valueOf(object22) + "Effect");
                String string11 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(object22) + "Message.Damager"));
                string9 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(object22) + "Message.Entity"));
                statSettings = new DisarmRateSettings(itemStat, (String)object, string11, string9);
                itemStat.setSettings(statSettings);
                continue;
            }
            if (itemStat != ItemStat.BLEED_RATE) continue;
            object22 = "AttributeSettings." + itemStat.name() + ".";
            int n = fileConfiguration.getInt(String.valueOf(object22) + "Time");
            String string12 = fileConfiguration.getString(String.valueOf(object22) + "Formula");
            string9 = fileConfiguration.getString(String.valueOf(object22) + "Effect");
            statSettings = new BleedRateSettings(itemStat, n, string12, string9);
            itemStat.setSettings(statSettings);
        }
        for (Object object5 : fileConfiguration.getConfigurationSection("AmmoTypes").getKeys(false)) {
            AmmoType ammoType = null;
            try {
                ammoType = AmmoType.valueOf(object5.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                continue;
            }
            boolean bl5 = fileConfiguration.getBoolean("AmmoTypes." + ammoType.name() + ".Enabled");
            string = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("AmmoTypes." + ammoType.name() + ".Name"));
            String string13 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("AmmoTypes." + ammoType.name() + ".Prefix"));
            ammoType.setEnabled(bl5);
            ammoType.setName(string);
            ammoType.setPrefix(string13);
        }
        this.str_dmgSep = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.DamageSeparator"));
        this.str_durSep = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.DurabilitySeparator"));
        this.str_pc = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.Percent"));
        this.str_neg = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.Negative"));
        this.str_pos = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.Positive"));
        this.str_mod = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.Modifier"));
        this.str_lvl = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.Level"));
        this.str_cls = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.Class"));
        this.str_clsSep = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.ClassSeparator"));
        this.str_clsCol = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Strings.ClassColor"));
        this.wpn = new HashSet<Material>();
        for (Object object5 : fileConfiguration.getStringList("ItemGroups.Weapons")) {
            if (Material.getMaterial((String)object5) == null) continue;
            this.wpn.add(Material.getMaterial((String)object5));
        }
        this.tool = new HashSet<Material>();
        for (Object object5 : fileConfiguration.getStringList("ItemGroups.Tools")) {
            if (Material.getMaterial((String)object5) == null) continue;
            this.tool.add(Material.getMaterial((String)object5));
        }
        this.arm = new HashSet<Material>();
        for (Object object5 : fileConfiguration.getStringList("ItemGroups.Armors")) {
            if (Material.getMaterial((String)object5) == null) continue;
            this.arm.add(Material.getMaterial((String)object5));
        }
    }

    public String getLangCode() {
        return this.lang;
    }

    public Hook getLevelPlugin() {
        return this.g_LevelPlugin;
    }

    public Hook getClassPlugin() {
        return this.g_ClassPlugin;
    }

    public String getFormulaOther() {
        return this.global_dformula_other;
    }

    public String getFormulaDamage() {
        return this.global_dformula_dmg;
    }

    public double getDamageCDReduce() {
        return this.g_dmgReduce;
    }

    public boolean breakItems() {
        return this.g_itemsBreak;
    }

    public int getMaxTargetDistance() {
        return this.g_targetDist;
    }

    public String getAttributeFormat() {
        return this.g_attFormat;
    }

    public String getDamageTypeFormat() {
        return this.g_dtFormat;
    }

    public String getArmorTypeFormat() {
        return this.g_atFormat;
    }

    public String getAmmoTypeFormat() {
        return this.g_ammoFormat;
    }

    public boolean allowAttributesToMobs() {
        return this.g_mobAtt;
    }

    public boolean allowAttributesToOffHand() {
        return this.g_offAtt;
    }

    public boolean allowFishHookDamage() {
        return this.g_fishHookDmg;
    }

    public boolean skillAPIReduceDurability() {
        return this.g_sapiDur;
    }

    public HashMap<String, Boolean> getModules() {
        return this.modules;
    }

    public boolean isModuleEnabled(String string) {
        return this.modules.get(string);
    }

    public HashMap<EntityDamageEvent.DamageCause, Double> getPlayerDmgModifiers() {
        return this.damage_values_p;
    }

    public HashMap<EntityDamageEvent.DamageCause, Double> getMobDmgModifiers() {
        return this.damage_values_m;
    }

    public HashMap<String, DamageType> getDamageTypes() {
        return this.damage_types;
    }

    public HashMap<String, ArmorType> getArmorTypes() {
        return this.armor_types;
    }

    public DamageType getDamageTypeById(String string) {
        return this.damage_types.get(string.toLowerCase());
    }

    public ArmorType getArmorTypeById(String string) {
        return this.armor_types.get(string.toLowerCase());
    }

    public String getStrDamageSeparator() {
        return this.str_dmgSep;
    }

    public String getStrDurabilitySeparator() {
        return this.str_durSep;
    }

    public String getStrPercent() {
        return this.str_pc;
    }

    public String getStrPositive() {
        return this.str_pos;
    }

    public String getStrNegative() {
        return this.str_neg;
    }

    public String getStrModifier() {
        return this.str_mod;
    }

    public String getStrLevel() {
        return this.str_lvl;
    }

    public String getStrClass() {
        return this.str_cls;
    }

    public String getStrClassSeparator() {
        return this.str_clsSep;
    }

    public String getStrClassColor() {
        return this.str_clsCol;
    }

    public Set<Material> getArmors() {
        return this.arm;
    }

    public Set<Material> getTools() {
        return this.tool;
    }

    public Set<Material> getWeapons() {
        return this.wpn;
    }
}

