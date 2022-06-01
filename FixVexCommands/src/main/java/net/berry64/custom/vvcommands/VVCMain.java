// 
// Decompiled by Procyon v0.5.30
// 

package net.berry64.custom.vvcommands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class VVCMain extends JavaPlugin implements Listener {
    Map<String, VVCItem> names;
    Map<ItemStack, VVCItem> items;
    static VVCMain instance;

    public void onEnable() {
        (VVCMain.instance = this).loadFiles();
        this.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
        new g().runTaskLater(this, 1);
    }

    public void loadFiles() {
        File f = new File(this.getDataFolder(), "config.yml");
        if (!f.exists()) {
            this.saveDefaultConfig();
        }
        this.reloadConfig();
        f = new File(this.getDataFolder() + "/items/");
        if (!f.exists()) {
            f.mkdirs();
        }
        this.names = new HashMap<String, VVCItem>();
        this.items = new HashMap<ItemStack, VVCItem>();
        File[] listFiles;
        for (int length = (listFiles = f.listFiles()).length, i = 0; i < length; ++i) {
            final File file = listFiles[i];
            this.colour("&a正在加载界面: &e" + file.getName());
            final String name = file.getName().replace(".yml", "");
            VVCItem item;
            try {
                item = new VVCItem(file);
            } catch (UnsupportedEncodingException | FileNotFoundException ex2) {
                this.colour("&c无法加载文件: &e" + file.getName());
                ex2.printStackTrace();
                continue;
            }
            this.names.put(name, item);
            this.items.put(item.getItem(), item);
            this.getServer().getPluginManager().registerEvents((Listener) item, (Plugin) this);
        }
        System.gc();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("vvc")) {
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage("§c使用方法: §e/vvc reload/list/set/create");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            this.loadFiles();
            sender.sendMessage("§a成功重载");
        } else if (args[0].equalsIgnoreCase("list") && sender.isOp()) {
            sender.sendMessage("§a=======已加载的界面列表=========");
            this.names.keySet().forEach(name -> sender.sendMessage("§e- " + name));
        } else if (args[0].equalsIgnoreCase("create") && sender.isOp()) {
            if (args.length < 2) {
                sender.sendMessage("§c使用方法: /vvc create [id]");
                return true;
            }
            final String id = args[1];
            if (!this.names.keySet().contains(id)) {
                final int btnid = (int) (Math.random() * 250.0);
                final int txtid = btnid + 3;
                final File f = new File(this.getDataFolder() + "/items/", String.valueOf(id) + ".yml");
                if (f.exists()) {
                    sender.sendMessage("§c该菜单已经存在");
                    return true;
                }
                try {
                    Files.copy(this.getResource("default.yml"), f.toPath(), new CopyOption[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final ItemStack i = new ItemStack(Material.IRON_INGOT);
                VVCItem item;
                try {
                    item = new VVCItem(f);
                } catch (UnsupportedEncodingException | FileNotFoundException ex2) {
                    sender.sendMessage("§c创建失败，详情请查看后台报错");
                    ex2.printStackTrace();
                    return true;
                }
                item.set("item", i);
                item.set("button.id", btnid);
                item.set("txt.id", txtid);
                item.save();
                sender.sendMessage("§a创建成功，请使用/vvc reload 使生效");
            } else {
                sender.sendMessage("§c该菜单已经存在");
            }
        } else if (args[0].equalsIgnoreCase("set") && sender instanceof Player && sender.isOp()) {
            if (args.length < 2) {
                sender.sendMessage("§c使用方法: /vvc set [id]");
                return true;
            }
            final String id = args[1];
            if (this.names.keySet().contains(id)) {
                final ItemStack j = ((Player) sender).getItemInHand();
                final VVCItem item2 = this.names.get(id);
                item2.set("item", j);
                item2.save();
                sender.sendMessage("§a设置成功，请使用/vvc reload 使生效");
            } else {
                sender.sendMessage("§c无法找到菜单: " + id);
            }
        } else {
            sender.sendMessage("§c未知命令");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("open") && sender.isOp()) {
            this.names.get(args[1]).openGui((Player) sender);
            return true;
        }
        return true;
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent evt) {
        if (evt.getItem() == null) {
            return;
        }
/*        for (final ItemStack i : this.items.keySet()) {
            if (evt.getItem().isSimilar(i)) {
                if (evt.getItem().getAmount() >= i.getAmount()) {
                    evt.setCancelled(true);
                    this.items.get(i).openGui(evt.getPlayer());
                    return;
                }
                evt.getPlayer().sendMessage("§c这个物品需要至少" + i.getAmount() + "§c个才能发动");
            }
        }*/
    }

    public void colour(final String msg) {
        this.getServer().getConsoleSender().sendMessage("[VVC] " + msg.replace('&', '§'));
    }
}
