package com._0myun.minecraft.trashanddrop;

import cn.hamster3.trash.InventoryClear;
import com.oraman.drd.listener.DeathListener;
import com.sun.istack.internal.NotNull;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TrashAndDrop extends JavaPlugin implements Listener {
    public static TrashAndDrop plugin;
    private Inventory inventory;
    private String Title;
    private InventoryClear clear;
    private String CantPut;
    private String CantGet;
    private String Reload;
    private int row = 1;

    @Override
    public void onEnable() {
        plugin = this;
        onEnable_Drop();
        onEnbale_Trash();
    }

    public void onEnable_Drop() {
        this.getServer().getConsoleSender().sendMessage("[DeathRandomDrop]死亡随机掉落插件开始启动......");
        this.getServer().getConsoleSender().sendMessage("[DeathRandomDrop]By Oraman");
        this.getServer().getConsoleSender().sendMessage("[DeathRandomDrop]QQ:418844961 如有问题请与我联系");
        this.saveDefaultConfig();
        this.reloadConfig();
        this.saveResource("items.yml", false);
        File f = new File(this.getDataFolder(), "items.yml");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration((File) f);
        Economy economy = null;
        RegisteredServiceProvider economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy) economyProvider.getProvider();
        }
        this.getServer().getPluginManager().registerEvents(new DeathListener(this.getConfig(), conf, economy), this);
        this.getServer().getConsoleSender().sendMessage("[DeathRandomDrop]死亡随机掉落插件启动完毕......");
    }


    public void onEnbale_Trash() {
        Material material;
        if (this.clear != null) {
            this.clear.cancel();
            this.inventory.clear();
            ArrayList<HumanEntity> list = new ArrayList(this.inventory.getViewers());
            for (HumanEntity player : list) {
                if (!(player instanceof Player)) continue;
                player.closeInventory();
                Player p = (Player) player;
                p.sendMessage(this.Reload);
            }
        }
        this.reloadConfig();
        FileConfiguration config = this.getConfig();
        this.Title = replaceColor(config.getString("Title", "&c公共垃圾桶"));
        String materialName = config.getString("Material", "FIREBALL");
        int cooldown = config.getInt("Cooldown", 60);
        String disPlayName = replaceColor(config.getString("DisplayName", "剩余时间: "));
        this.CantPut = replaceColor(config.getString("Message.CantPut", "&c你没有向垃圾桶里放入物品的权限!"));
        this.CantGet = replaceColor(config.getString("Message.CantGet", "&c你没有从垃圾桶里拿出物品的权限!"));
        this.Reload = replaceColor(config.getString("Message.Reload", "&c由于插件重载，你的界面被强制关闭!"));
        try {
            material = Material.valueOf((String) materialName);
        } catch (Exception e) {
            material = Material.FIRE;
            sendConsoleMessage("§c未找到材料 §b" + materialName + " §c，已启用默认材料 FIRE !");
        }
        ItemStack barrier = new ItemStack(material);
        ItemMeta meta = barrier.getItemMeta();
        meta.setDisplayName(disPlayName + cooldown);
        meta.setLore(replaceColor(config.getStringList("Lore")));
        barrier.setItemMeta(meta);
        this.inventory = Bukkit.createInventory(null, (int) 54, this.Title);
        this.inventory.setItem(0, barrier.clone());
        this.clear = new InventoryClear(this.inventory, cooldown, barrier, disPlayName);
        this.clear.runTaskTimerAsynchronously(this, 20L, 20L);
        Bukkit.getPluginManager().registerEvents((Listener) this, this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();
        if (!commandName.equalsIgnoreCase("trash")) {
            if (!commandName.equalsIgnoreCase("trashReload")) return false;
            this.onEnbale_Trash();
            sender.sendMessage("§e[§bTrash§e] §a插件已重载!");
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(this.inventory);
            return false;
        }
        sender.sendMessage("§c只有玩家能使用这个命令!");
        return true;
    }

    @EventHandler
    public void onPlayerClickInv(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if (inv != null && inv.getTitle().equalsIgnoreCase(this.Title)) {
            ItemStack stack = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            if (stack != null) {
                if (!player.hasPermission("trash.get")) {
                    player.sendMessage(this.CantGet);
                    event.setCancelled(true);
                    return;
                }
                if (event.getRawSlot() == 0) {
                    event.setCancelled(true);
                    return;
                }
            }
            if ((stack = event.getCursor()) != null && !player.hasPermission("trash.put")) {
                player.sendMessage(this.CantPut);
                event.setCancelled(true);
            }
        }
    }

    private static String replaceColor(@NotNull String string) {
        return string.replace("&", "§");
    }

    private static List<String> replaceColor(@NotNull List<String> strings) {
        ArrayList<String> list = new ArrayList<String>();
        for (String str : strings) {
            list.add(replaceColor(str));
        }
        return list;
    }

    private static void sendConsoleMessage(@NotNull String message) {
        Bukkit.getConsoleSender().sendMessage("§e[§bTrash§e] " + message);
    }

    public void dropItem(ItemStack itemStack) {
        Inventory inv = this.inventory;
        if (inv.firstEmpty() != -1) {
            inv.addItem(itemStack);
            return;
        }
        if (row == 0) row = 1;
        inv.setItem(row, itemStack);
        row++;
        if (row > 53) row = 1;
    }

}
