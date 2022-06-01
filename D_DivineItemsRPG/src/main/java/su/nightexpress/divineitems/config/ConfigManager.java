/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.WordUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.config;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigUpdater;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.utils.ResourceExtractor;

public class ConfigManager {
    private DivineItems plugin;
    private ConfigUpdater cu;
    private Config cfg;
    public MyConfig configLang;
    public MyConfig configMain;
    public MyConfig configGUI;
    public MyConfig item_names;
    public MyConfig ench_names;
    public MyConfig temp_hash;

    public ConfigManager(DivineItems divineItems) {
        this.plugin = divineItems;
        this.cu = new ConfigUpdater(divineItems, this);
    }

    public void setup() {
        Object object;
        FileConfiguration fileConfiguration;
        this.extract("lang/en");
        this.extract("modules/drops");
        this.extract("modules/tiers");
        this.extract("modules/resolve");
        this.configMain = new MyConfig(this.plugin, "", "config.yml");
        this.cu.update();
        this.cfg = new Config(this.configMain);
        this.configLang = new MyConfig(this.plugin, "/lang/" + this.cfg.getLangCode(), "messages.yml");
        this.item_names = new MyConfig(this.plugin, "/lang/" + this.cfg.getLangCode(), "item_names.yml");
        this.ench_names = new MyConfig(this.plugin, "/lang/" + this.cfg.getLangCode(), "ench_names.yml");
        this.configGUI = new MyConfig(this.plugin, "", "gui.yml");
        this.temp_hash = new MyConfig(this.plugin, "", "temp_hash.yml");
        FileConfiguration fileConfiguration2 = this.item_names.getConfig();
        Material[] arrmaterial = Material.values();
        int n = arrmaterial.length;
        int n2 = 0;
        while (n2 < n) {
            fileConfiguration = arrmaterial[n2];
            if (!fileConfiguration2.contains("Material." + fileConfiguration.name())) {
                object = WordUtils.capitalizeFully((String)fileConfiguration.name().replace("_", " "));
                fileConfiguration2.set("Material." + fileConfiguration.name(), object);
            }
            ++n2;
        }
        fileConfiguration = this.ench_names.getConfig();
        object = Enchantment.values();
        int n3 = ((Enchantment[])object).length;
        n = 0;
        while (n < n3) {
            Enchantment enchantment = object[n];
            if (!fileConfiguration.contains("Enchant." + enchantment.getName())) {
                String string = WordUtils.capitalizeFully((String)enchantment.getName().replace("_", " "));
                fileConfiguration.set("Enchant." + enchantment.getName(), (Object)string);
            }
            ++n;
        }
        this.item_names.save();
        this.ench_names.save();
        Lang.setup(this.configLang);
    }

    private void extract(String string) {
        File file = new File(this.plugin.getDataFolder() + "/" + string + "/");
        if (!file.exists()) {
            ResourceExtractor resourceExtractor = new ResourceExtractor(this.plugin, new File(this.plugin.getDataFolder() + File.separator + string), string, ".*\\.(yml)$");
            try {
                resourceExtractor.extract(false, true);
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    public String getDefaultItemName(ItemStack itemStack) {
        if (this.item_names.getConfig().contains("Material." + itemStack.getType().name())) {
            return this.item_names.getConfig().getString("Material." + itemStack.getType().name());
        }
        return WordUtils.capitalizeFully((String)itemStack.getType().name().replace("_", " "));
    }

    public String getDefaultEnchantName(Enchantment enchantment) {
        if (this.ench_names.getConfig().contains("Enchant." + enchantment.getName())) {
            return ChatColor.translateAlternateColorCodes((char)'&', (String)this.ench_names.getConfig().getString("Enchant." + enchantment.getName()));
        }
        return WordUtils.capitalizeFully((String)enchantment.getName().replace("_", " "));
    }

    public void createItemHash(String string) {
        FileConfiguration fileConfiguration = this.plugin.getCM().temp_hash.getConfig();
        if (!fileConfiguration.contains(string)) {
            fileConfiguration.set(string, (Object)UUID.randomUUID().toString());
        }
        this.plugin.getCM().temp_hash.save();
    }

    public UUID getItemHash(String string) {
        FileConfiguration fileConfiguration = this.plugin.getCM().temp_hash.getConfig();
        if (!fileConfiguration.contains(string)) {
            this.createItemHash(string);
        }
        String string2 = fileConfiguration.getString(string);
        return UUID.fromString(string2);
    }

    public Config getCFG() {
        return this.cfg;
    }
}

