/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package su.nightexpress.divineitems.config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.types.AmmoType;
import su.nightexpress.divineitems.utils.Files;

public class ConfigUpdater {
    private DivineItems plugin;
    private ConfigManager cm;

    public ConfigUpdater(DivineItems divineItems, ConfigManager configManager) {
        this.plugin = divineItems;
        this.cm = configManager;
    }

    public void update() {
        this.updateConfig();
        this.updateDrops();
        this.updateTiers();
    }

    private void updateConfig() {
        Object object2;
        FileConfiguration fileConfiguration = this.cm.configMain.getConfig();
        fileConfiguration.set("DamageFormula", null);
        if (!fileConfiguration.contains("Global.DamageFormula.damage")) {
            fileConfiguration.set("Global.DamageFormula", null);
            fileConfiguration.set("Global.DamageFormula.other", (Object)"(1 + (%pvpe_dmg% - %pvpe_def%) / 100) * (1 - %block% / 100)");
            fileConfiguration.set("Global.DamageFormula.damage", (Object)"(%dmg% * %other%)");
        }
        if (!fileConfiguration.contains("Global.AllowAttributesInOffHand")) {
            fileConfiguration.set("Global.AllowAttributesInOffHand", (Object)false);
        }
        if (!fileConfiguration.contains("Global.AmmoTypeFormat")) {
            fileConfiguration.set("Global.AmmoTypeFormat", (Object)"&7Ammo Type: %type_prefix% %type_name%");
        }
        if (!fileConfiguration.contains("Global.ArmorTypeFormat")) {
            fileConfiguration.set("Global.ArmorTypeFormat", (Object)"%type_prefix% %type_name%: %type_value%");
        }
        if (!fileConfiguration.contains("Global.SkillAPISkillsReduceDurability")) {
            fileConfiguration.set("Global.SkillAPISkillsReduceDurability", (Object)true);
        }
        Enum[] arrenum = AmmoType.values();
        int n = arrenum.length;
        int n2 = 0;
        while (n2 < n) {
            object2 = arrenum[n2];
            if (!fileConfiguration.contains("AmmoTypes." + object2.name())) {
                fileConfiguration.set("AmmoTypes." + object2.name() + ".Enabled", (Object)object2.isEnabled());
                fileConfiguration.set("AmmoTypes." + object2.name() + ".Name", (Object)object2.getName());
                fileConfiguration.set("AmmoTypes." + object2.name() + ".Prefix", (Object)object2.getPrefix());
            }
            ++n2;
        }
        if (!fileConfiguration.contains("Modules.Arrows")) {
            fileConfiguration.set("Modules.Arrows", (Object)true);
        }
        if (!fileConfiguration.contains("Modules.Consumables")) {
            fileConfiguration.set("Modules.Consumables", (Object)true);
        }
        if (!fileConfiguration.contains("Modules.Resolve")) {
            fileConfiguration.set("Modules.Resolve", (Object)true);
        }
        if (!fileConfiguration.contains("ArmorTypes")) {
            object2 = Arrays.asList("Physical", "Magical", "Poison", "Fire", "Water", "Wind");
            Iterator iterator = object2.iterator();
            while (iterator.hasNext()) {
                String string = (String)iterator.next();
                fileConfiguration.set("ArmorTypes." + string + ".Prefix", (Object)"&2\u25b8");
                fileConfiguration.set("ArmorTypes." + string + ".Name", (Object)(String.valueOf(string) + " Defense"));
                fileConfiguration.set("ArmorTypes." + string + ".Value", (Object)"&f");
                fileConfiguration.set("ArmorTypes." + string + ".Percent", (Object)false);
                fileConfiguration.set("ArmorTypes." + string + ".BlockDamageSource", Arrays.asList("NONE"));
                fileConfiguration.set("ArmorTypes." + string + ".BlockDamageTypes", Arrays.asList(string));
                fileConfiguration.set("ArmorTypes." + string + ".Formula", (Object)"(((%dmg% * (1 + %penetrate% / 100)) - (%def% / 10)))");
            }
        }
        for (Object object2 : fileConfiguration.getConfigurationSection("DamageTypes").getKeys(false)) {
            String string = "DamageTypes." + (String)object2 + ".";
            fileConfiguration.set(String.valueOf(string) + "Enabled", null);
            if (!fileConfiguration.contains(String.valueOf(string) + "Default")) {
                if (object2.equalsIgnoreCase("Physical")) {
                    fileConfiguration.set(String.valueOf(string) + "Default", (Object)true);
                } else {
                    fileConfiguration.set(String.valueOf(string) + "Default", (Object)false);
                }
            }
            if (fileConfiguration.contains(String.valueOf(string) + "BiomeDamageModifier")) continue;
            fileConfiguration.set(String.valueOf(string) + "BiomeDamageModifier.PLAINS", (Object)1.0);
        }
        if (!fileConfiguration.contains("AttributeSettings.DISARM_RATE")) {
            object2 = "AttributeSettings.DISARM_RATE.";
            fileConfiguration.set(String.valueOf(object2) + "Effect", (Object)"ITEM_CRACK:IRON_SWORD");
            fileConfiguration.set(String.valueOf(object2) + "Message.Damager", (Object)"&2*** &aYou disarmed &7%s%&a! &2***");
            fileConfiguration.set(String.valueOf(object2) + "Message.Entity", (Object)"&4*** &cYou have been disarmed by &7%s%&c! &4***");
        }
        if (!fileConfiguration.contains("AttributeSettings.BLEED_RATE")) {
            object2 = "AttributeSettings.BLEED_RATE.";
            fileConfiguration.set(String.valueOf(object2) + "Time", (Object)5);
            fileConfiguration.set(String.valueOf(object2) + "Formula", (Object)"%dmg% * 0.25");
            fileConfiguration.set(String.valueOf(object2) + "Effect", (Object)"ITEM_CRACK:IRON_SWORD");
        }
        arrenum = ItemStat.values();
        n = arrenum.length;
        int n3 = 0;
        while (n3 < n) {
            object2 = arrenum[n3];
            if (!fileConfiguration.contains("Attributes." + object2.name())) {
                fileConfiguration.set("Attributes." + object2.name() + ".Name", (Object)object2.getName());
                fileConfiguration.set("Attributes." + object2.name() + ".Prefix", (Object)object2.getPrefix());
                fileConfiguration.set("Attributes." + object2.name() + ".Value", (Object)object2.getValue());
                fileConfiguration.set("Attributes." + object2.name() + ".Bonus", (Object)object2.getBonus());
                fileConfiguration.set("Attributes." + object2.name() + ".Capability", (Object)object2.getCapability());
            }
            ++n3;
        }
        this.cm.configMain.save();
    }

    private void updateDrops() {
        for (String string : Files.getFilesFolder("drops")) {
            File file = new File(this.plugin.getDataFolder() + "/modules/drops/", string);
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
            if (!yamlConfiguration.contains("General.BiomesWhitelist")) {
                yamlConfiguration.set("General.BiomesWhitelist.Reverse", (Object)false);
                yamlConfiguration.set("General.BiomesWhitelist.List", Arrays.asList("ANY", "PLAINS", "BIOME_NAME"));
            }
            if (!yamlConfiguration.contains("General.DropLevelPenalty")) {
                yamlConfiguration.set("General.DropLevelPenalty.Enabled", (Object)false);
                yamlConfiguration.set("General.DropLevelPenalty.Variance", (Object)10);
            }
            try {
                yamlConfiguration.save(file);
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    private void updateTiers() {
        for (String string : Files.getFilesFolder("tiers")) {
            File file = new File(this.plugin.getDataFolder() + "/modules/tiers/", string);
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
            if (!yamlConfiguration.contains("Item.MaterialData.Special")) {
                yamlConfiguration.set("Item.MaterialData.Special.DIAMOND_SWORD", Arrays.asList(5, 6, 7));
                yamlConfiguration.set("Item.MaterialData.Special.GOLD_SWORD", Arrays.asList(9, 10, 11));
            }
            if (!yamlConfiguration.contains("Item.Restrictions.LevelScaleBlackList")) {
                yamlConfiguration.set("Item.Restrictions.LevelScaleBlackList", Arrays.asList("CRITICAL_DAMAGE"));
            }
            if (!yamlConfiguration.contains("Item.AmmoTypes")) {
                yamlConfiguration.set("Item.AmmoTypes.ARROW", (Object)100.0);
            }
            if (!yamlConfiguration.contains("Item.ArmorTypes")) {
                yamlConfiguration.set("Item.ArmorTypes.Physical.Chance", (Object)100.0);
                yamlConfiguration.set("Item.ArmorTypes.Physical.Min", (Object)10.0);
                yamlConfiguration.set("Item.ArmorTypes.Physical.Max", (Object)25.0);
            }
            ItemStat[] arritemStat = ItemStat.values();
            int n = arritemStat.length;
            int n2 = 0;
            while (n2 < n) {
                ItemStat itemStat = arritemStat[n2];
                if (!yamlConfiguration.contains("Item.Attributes." + itemStat.name())) {
                    yamlConfiguration.set("Item.Attributes." + itemStat.name() + ".Default.Chance", (Object)20.0);
                    yamlConfiguration.set("Item.Attributes." + itemStat.name() + ".Default.Min", (Object)5.0);
                    yamlConfiguration.set("Item.Attributes." + itemStat.name() + ".Default.Max", (Object)25.0);
                    yamlConfiguration.set("Item.Attributes." + itemStat.name() + ".Bonus.Chance", (Object)20.0);
                    yamlConfiguration.set("Item.Attributes." + itemStat.name() + ".Bonus.Min", (Object)5.0);
                    yamlConfiguration.set("Item.Attributes." + itemStat.name() + ".Bonus.Max", (Object)25.0);
                }
                ++n2;
            }
            try {
                yamlConfiguration.save(file);
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }
}

