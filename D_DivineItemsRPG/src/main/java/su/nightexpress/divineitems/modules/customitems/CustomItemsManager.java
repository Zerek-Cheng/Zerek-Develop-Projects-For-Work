/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.inventory.ItemStack
 */
package su.nightexpress.divineitems.modules.customitems;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.CustomItemsCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.sets.SetManager;
import su.nightexpress.divineitems.nbt.NBTItem;

public class CustomItemsManager
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private HashMap<String, ItemStack> items;
    private final String NBT_KEY_CI = "DI_CI";

    public CustomItemsManager(DivineItems divineItems) {
        this.plugin = divineItems;
        this.items = new HashMap();
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.setupCustomItems();
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
        return "Custom Items";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        this.plugin.getCommander().registerCmd(this.label, new CustomItemsCommand(this.plugin));
        this.loadConfig();
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.items.clear();
            this.e = false;
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    private void setupCustomItems() {
        for (File file : this.listf("/modules/" + this.n + "/")) {
            ItemStack itemStack = this.loadFromConfig(file);
            this.items.put(file.getPath().split(this.n)[1].replaceAll("\\\\", "/").replaceFirst("\\/", "").replace(".yml", "").toLowerCase(), itemStack);
        }
    }

    public boolean isCustomItem(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DI_CI");
    }

    public ItemStack loadFromConfig(File file) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
        ItemStack itemStack = yamlConfiguration.getItemStack("Item");
        NBTItem nBTItem = new NBTItem(itemStack);
        nBTItem.setString("DI_CI", file.getName().replace(".yml", ""));
        return nBTItem.getItem();
    }

    private List<File> listf(String string) {
        File file = new File(this.plugin.getDataFolder() + string);
        ArrayList<File> arrayList = new ArrayList<File>();
        File[] arrfile = file.listFiles();
        if (arrfile == null) {
            return arrayList;
        }
        File[] arrfile2 = arrfile;
        int n = arrfile2.length;
        int n2 = 0;
        while (n2 < n) {
            File file2 = arrfile2[n2];
            if (file2.isFile()) {
                arrayList.add(file2);
            } else if (file2.isDirectory()) {
                arrayList.addAll(this.listf("/modules/" + this.n + "/" + file2.getName() + "/"));
            }
            ++n2;
        }
        return arrayList;
    }

    public boolean removeCustomItem(String string) {
        if (!this.items.containsKey(string = string.toLowerCase())) {
            return false;
        }
        this.items.remove(string);
        File file = new File(this.plugin.getDataFolder() + "/modules/" + this.n + "/", String.valueOf(string) + ".yml");
        file.delete();
        return true;
    }

    public void saveCustomItem(ItemStack itemStack, String string) {
        string = string.toLowerCase();
        File file = new File(this.plugin.getDataFolder() + "/modules/" + this.n + "/", String.valueOf(string) + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        file = new File(this.plugin.getDataFolder() + "/modules/" + this.n + "/", String.valueOf(string) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
        yamlConfiguration.set("Item", (Object)itemStack);
        try {
            yamlConfiguration.save(new File(this.plugin.getDataFolder() + "/modules/" + this.n + "/", String.valueOf(string) + ".yml"));
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.items.put(string, itemStack);
    }

    public ItemStack getCustomItem(String string) {
        if (string.equalsIgnoreCase("random")) {
            return this.items.get(this.getCustomItemsNames().get(this.r.nextInt(this.getCustomItemsNames().size())));
        }
        if (this.items.get(string.toLowerCase()) == null) {
            return new ItemStack(Material.AIR);
        }
        ItemStack itemStack = this.items.get(string.toLowerCase()).clone();
        if (this.plugin.getMM().getSetManager().isActive()) {
            itemStack = this.plugin.getMM().getSetManager().replaceLore(itemStack);
        }
        return itemStack;
    }

    public List<String> getCustomItemsNames() {
        return new ArrayList<String>(this.items.keySet());
    }
}

