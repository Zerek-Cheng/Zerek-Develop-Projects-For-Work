package com._0myun.minecraft.dentallaboratories;

import com._0myun.minecraft.dentallaboratories.bin.Item;
import com._0myun.minecraft.dentallaboratories.listener.GuiListener;
import com._0myun.minecraft.dentallaboratories.listener.LevelUpListener;
import com._0myun.minecraft.dentallaboratories.utils.ColorSign;
import com.comphenix.protocol.utility.StreamSerializer;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    public static Main plugin;
    @Getter
    private ItemsManager itemsManager = new ItemsManager();
    @Getter
    private LangManager langManager = new LangManager();
    @Getter
    private GuiManager guiManager = new GuiManager();
    private File itemDir = new File(this.getDataFolder().getPath() + "/item/");
    public static Economy economy = null;

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

    @Override
    public void onEnable() {
        this.setupEconomy();
        plugin = this;
        this.regConfigurationSerializable();
        this.saveDefault();

        this.reloadAllConfig();

        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new LevelUpListener(), this);
    }

    private void regConfigurationSerializable() {
        ConfigurationSerialization.registerClass(com._0myun.minecraft.dentallaboratories.bin.Item.class, "com._0myun.minecraft.dentallaboratories.bin.Item");
        ConfigurationSerialization.registerClass(com._0myun.minecraft.dentallaboratories.bin.Attribute.class, "com._0myun.minecraft.dentallaboratories.bin.Attribute");
        ConfigurationSerialization.registerClass(com._0myun.minecraft.dentallaboratories.bin.Event.class, "com._0myun.minecraft.dentallaboratories.bin.Event");
        ConfigurationSerialization.registerClass(com._0myun.minecraft.dentallaboratories.bin.Material.class, "com._0myun.minecraft.dentallaboratories.bin.Material");

    }

    public void saveDefault() {
        this.saveDefaultConfig();
        this.saveResource("lang.yml", false);
        if (!this.itemDir.exists()) {
            this.itemDir.mkdir();
            saveResource("item/default.yml", false);
            saveResource("item/default2.yml", false);
            saveResource("item/default3.yml", false);
        }
    }

    public void reloadAllConfig() {
        this.reloadConfig();
        this.getItemsManager().cleanItems();
        this.loadItems();

        this.getLangManager().setLang(loadConfig("lang.yml"));
    }

    public void loadItems() {
        String[] files = this.itemDir.list();
        Arrays.asList(files).forEach(file -> {
            YamlConfiguration yml = loadConfig("item/" + file);
            com._0myun.minecraft.dentallaboratories.bin.Item item = (com._0myun.minecraft.dentallaboratories.bin.Item) yml.get("default");
            this.getItemsManager().addItem(item.getSign(), item);
        });
    }

    public YamlConfiguration loadConfig(String fileName) {
        File file = new File(this.getDataFolder().getPath() + "/" + fileName);
        //if (!fileName.startsWith("/") && !fileName.startsWith("\\")) saveResource(fileName, false);
        try {
            YamlConfiguration yml = new YamlConfiguration();
            yml.load(file);
            return yml;
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().log(Level.WARNING, "文件" + fileName + "加载错误...以上是报错信息");
        }
        return null;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            this.reloadAllConfig();
            sender.sendMessage("ok");
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("itemstr") && sender.isOp()) {
            Player p = (Player) sender;
            ItemStack itemInHand = p.getItemInHand();
            try {
                getLogger().log(Level.INFO,"序列化内容已保存到config.yml中的itemstr项:");
                getConfig().set("itemstr",StreamSerializer.getDefault().serializeItemStack(itemInHand));
                saveConfig();
            } catch (IOException e) {
                getLogger().log(Level.WARNING,"序列化失败......");
                e.printStackTrace();
            }
            return true;
        }
        Player p = (Player) sender;
        ItemStack itemInHand = p.getItemInHand();
        Item item = getItemsManager().getItem(itemInHand);
        if (args.length == 1 && args[0].equalsIgnoreCase("string")) {
            try {
                getLogger().log(Level.INFO, StreamSerializer.getDefault().serializeItemStack(itemInHand));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (item == null) {
            p.sendMessage(this.getLangManager().get("lang3"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("refresh")) {
            this.getItemsManager().updateLores(itemInHand);
            p.updateInventory();
            return true;
        }
        getGuiManager().openGui(p, itemInHand);
/*        try {
            System.out.println(StreamSerializer.getDefault().serializeItemStack(new ItemStack(160,1, (short) 1)));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return true;
    }

    public String getSignStr() {
        return ColorSign.parse(getConfig().getString("sign"));
    }
}
