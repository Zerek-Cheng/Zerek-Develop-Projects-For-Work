/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 *  org.bukkit.plugin.Plugin
 */
package yo;

import java.io.Closeable;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.ad_0;
import yo.ah_1;
import yo.ai_1;
import yo.aj_1;
import yo.ak_1;
import yo.am_0;
import yo.ao_0;
import yo.aq_0;
import yo.as_0;
import yo.bc_1;
import yo.bd_0;
import yo.bf_1;
import yo.bg_0;
import yo.bg_1;
import yo.bi_0;
import yo.bi_1;
import yo.bk_0;
import yo.bm_0;
import yo.by_0;
import yo.cd_0;
import yo.cv;
import yo.y_0;
import yo.z_0;

public class af_0
implements ad_0 {
    @ak_1(a="rpgitem list")
    @ah_1(a="$command.rpgitem.list")
    @aj_1(a="list")
    public void a(CommandSender sender) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        sender.sendMessage((Object)ChatColor.GREEN + "RPGItems:");
        for (RPGItem item : by_0.b.values()) {
            sender.sendMessage((Object)ChatColor.GREEN + item.getName() + " - " + item.getDisplay());
        }
    }

    @ak_1(a="rpgitem list $integer:i[]")
    @ah_1(a="$command.rpgitem.list")
    @aj_1(a="list")
    public void a(CommandSender sender, int page) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        int lines = Plugin.d.getInt("linesPerPage", 15);
        List<String> contents = this.a();
        int maxPage = contents.size() / lines + ((contents.size() & lines) != 0 ? 1 : 0);
        if (!contents.isEmpty()) {
            String locale = ao_0.a((Object)sender);
            int fromIndex = (page - 1) * lines;
            if (page > 0 && page <= maxPage) {
                int toIndex = fromIndex + lines;
                if (toIndex > contents.size()) {
                    toIndex = contents.size();
                }
                sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.listpage.top", locale), page, maxPage));
                sender.sendMessage(contents.subList(fromIndex, toIndex).toArray(new String[0]));
                sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.listpage.bottom", locale), page, maxPage));
            } else {
                sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.listpage.error", locale), maxPage));
            }
        } else {
            sender.sendMessage((Object)ChatColor.GREEN + "RPGItems: []");
        }
    }

    private List<String> a() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        ArrayList<String> list = new ArrayList<String>();
        for (RPGItem item : by_0.b.values()) {
            list.add((Object)ChatColor.GREEN + item.getName() + " - " + item.getDisplay());
        }
        return list;
    }

    @ak_1(a="rpgitem import $file:s[]")
    @ah_1(a="$command.rpgitem.import")
    @aj_1(a="import")
    public void a(CommandSender sender, String fileName) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        String locale = ao_0.a((Object)sender);
        fileName = Plugin.c.getDataFolder().getAbsolutePath() + File.separator + fileName;
        sender.sendMessage(by_0.a(fileName, locale).toArray(new String[0]));
    }

    @ak_1(a="rpgitem reload $type:o[conf,lang,plug]")
    @ah_1(a="$command.rpgitem.reloadplugin")
    @aj_1(a="reload")
    public void b(CommandSender sender, String type) {
        String locale = ao_0.a((Object)sender);
        switch (type.toLowerCase()) {
            case "plug": {
                try {
                    bc_1.b(Plugin.c.getName(), sender);
                }
                catch (Exception e2) {
                    Bukkit.getPluginManager().disablePlugin((org.bukkit.plugin.Plugin)Plugin.c);
                    Bukkit.getPluginManager().enablePlugin((org.bukkit.plugin.Plugin)Plugin.c);
                }
                sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.reload.plugin", locale));
                break;
            }
            case "conf": {
                Plugin.c.reloadConfig();
                Plugin.c.b();
                sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.reload.config", locale));
                break;
            }
            case "lang": {
                ao_0.a(Plugin.c);
                sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.reload.language", locale));
            }
        }
    }

    @ak_1(a="rpgitem option support $plugin:s[]")
    @ah_1(a="$command.rpgitem.support")
    @aj_1(a="option_support")
    public void c(CommandSender sender, String plugin) {
        String locale = ao_0.a((Object)sender);
        cv ips = cd_0.a(plugin);
        if (ips != null) {
            if (!ips.g()) {
                sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.support.error", locale), ips.toString()));
                return;
            }
            if (ips.d()) {
                sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.support.disable", locale), ips.toString()));
            } else {
                sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.support.enable", locale), ips.toString()));
            }
            ips.e();
            Plugin.c.c().set("support." + plugin.toLowerCase(), ips.d());
            Plugin.c.saveConfig();
        } else {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.support.notfound", locale), plugin, cd_0.c().toString()));
        }
    }

    @ak_1(a="rpgitem $n[] support $plugin:s[]")
    @ah_1(a="$command.rpgitem.item.support")
    @aj_1(a="item_support")
    public void a(CommandSender sender, RPGItem item, String plugin) {
        String locale = ao_0.a((Object)sender);
        cv ips = cd_0.a(plugin);
        if (ips != null) {
            if (!ips.g()) {
                sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.support.error", locale), ips.toString()));
                return;
            }
            item.a(ips);
            by_0.c(Plugin.c);
            if (item.d(ips.toString())) {
                sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.support.override.active", locale), ips.toString()));
            } else {
                sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.support.override.disabled", locale), ips.toString()));
            }
        } else {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.support.notfound", locale), plugin, cd_0.c().toString()));
        }
    }

    @ak_1(a="rpgitem $n[] keep $type:o[lore,enchant]")
    @ah_1(a="$command.rpgitem.keep")
    @aj_1(a="item_keep")
    public void b(CommandSender sender, RPGItem item, String type) {
        String locale = ao_0.a((Object)sender);
        type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        try {
            Field keepField = item.getClass().getDeclaredField("keep" + type);
            keepField.setAccessible(true);
            keepField.set(item, null);
            by_0.c(Plugin.c);
            sender.sendMessage(String.format(ao_0.a("message.keep.success.clear", locale), item.getDisplay(), type));
        }
        catch (Exception e2) {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.keep.fail", locale));
        }
    }

    @ak_1(a="rpgitem $n[] keep $type:o[lore,enchant] $string:o[true,false]")
    @ah_1(a="$command.rpgitem.keep")
    @aj_1(a="item_keep")
    public void a(CommandSender sender, RPGItem item, String type, String string) {
        String locale = ao_0.a((Object)sender);
        boolean enabled = string.equalsIgnoreCase("true");
        type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        try {
            Field keepField = item.getClass().getDeclaredField("keep" + type);
            keepField.setAccessible(true);
            keepField.set(item, enabled);
            by_0.c(Plugin.c);
            Object[] arrobject = new Object[3];
            arrobject[0] = item.getDisplay();
            arrobject[1] = ao_0.a("command.info." + (enabled ? "allow" : "refuse"), locale);
            arrobject[2] = type;
            sender.sendMessage(String.format(ao_0.a("message.keep.success", locale), arrobject));
        }
        catch (Exception e2) {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.keep.fail", locale));
        }
    }

    @ak_1(a="rpgitem $n[] level")
    @ah_1(a="$command.rpgitem.level.set")
    @aj_1(a="item_level")
    public void a(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        item.c = -1;
        by_0.c(Plugin.c);
        sender.sendMessage(String.format((Object)ChatColor.AQUA + ao_0.a("message.level.remove", locale), item.getDisplay() + (Object)ChatColor.AQUA));
    }

    @ak_1(a="rpgitem $n[] level $integer:i[1,2147483647]")
    @ah_1(a="$command.rpgitem.level.set")
    @aj_1(a="item_level")
    public void a(CommandSender sender, RPGItem item, int level) {
        String locale = ao_0.a((Object)sender);
        item.c = level - 1;
        by_0.c(Plugin.c);
        sender.sendMessage(String.format((Object)ChatColor.AQUA + ao_0.a("message.level.set", locale), item.getDisplay() + (Object)ChatColor.AQUA, level));
    }

    @ak_1(a="rpgitem $n[] costexp $integer:i[]")
    @ah_1(a="$command.rpgitem.cost.exp")
    @aj_1(a="item_cost_exp")
    public void b(CommandSender sender, RPGItem item, int exp) {
        String locale = ao_0.a((Object)sender);
        if (exp < 0) {
            exp = 0;
        }
        item.d = exp;
        by_0.c(Plugin.c);
        sender.sendMessage(String.format(ao_0.a("message.exp.set", locale), exp));
    }

    @ak_1(a="rpgitem $n[] costmoney $double:f[]")
    @ah_1(a="$command.rpgitem.cost.money")
    @aj_1(a="item_cost_exp")
    public void a(CommandSender sender, RPGItem item, double money) {
        String locale = ao_0.a((Object)sender);
        if (money < 0.0) {
            money = 0.0;
        }
        item.e = money;
        by_0.c(Plugin.c);
        sender.sendMessage(String.format(ao_0.a("message.money.set", locale), money));
    }

    @ak_1(a="rpgitem $n[]")
    @ah_1(a="$command.rpgitem.print")
    @aj_1(a="item")
    public void b(CommandSender sender, RPGItem item) {
        item.a(sender);
    }

    @ak_1(a="rpgitem $name:s[] create")
    @ah_1(a="$command.rpgitem.create")
    @aj_1(a="item")
    public void d(CommandSender sender, String itemName) {
        String locale = ao_0.a((Object)sender);
        RPGItem rItem = by_0.b(itemName);
        if (rItem != null) {
            sender.sendMessage(String.format((Object)ChatColor.GREEN + ao_0.a("message.create.ok", locale), itemName));
            by_0.c(Plugin.c);
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.create.fail", locale));
        }
    }

    @ak_1(a="rpgitem $name:s[] create $string:s[]")
    @ah_1(a="$command.rpgitem.create.full")
    @aj_1(a="item")
    public void a(CommandSender sender, String itemName, String fileName) {
        String locale = ao_0.a((Object)sender);
        fileName = Plugin.c.getDataFolder().getAbsolutePath() + File.separator + "items" + File.separator + fileName;
        RPGItem rItem = by_0.b(itemName, fileName);
        if (rItem != null) {
            sender.sendMessage(String.format((Object)ChatColor.GREEN + ao_0.a("message.create.ok", locale), itemName));
            by_0.c(Plugin.c);
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.create.fail", locale));
        }
    }

    @ak_1(a="rpgitem option giveperms")
    @ah_1(a="$command.rpgitem.giveperms")
    @aj_1(a="option_giveperms")
    public void b(CommandSender sender) {
        String locale = ao_0.a((Object)sender);
        Plugin.c.c().set("give-perms", !Plugin.c.c().getBoolean("give-perms", false));
        if (Plugin.c.c().getBoolean("give-perms", false)) {
            sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.giveperms.true", locale));
        } else {
            sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.giveperms.false", locale));
        }
        Plugin.c.saveConfig();
    }

    @ak_1(a="rpgitem $n[] give", b=true)
    @ah_1(a="$command.rpgitem.give")
    @aj_1(a="item_give")
    public void c(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        if (sender instanceof Player) {
            if (!Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem") || Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem.give." + item.getName())) {
                item.y((Player)sender);
                sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.give.ok", locale), item.getDisplay()));
            } else {
                sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
            }
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.give.console", locale));
        }
    }

    @ak_1(a="rpgitem $n[] give $p[]")
    @ah_1(a="$command.rpgitem.give.player")
    @aj_1(a="item_give")
    public void a(CommandSender sender, RPGItem item, Player player) {
        String locale = ao_0.a((Object)sender);
        if (!Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem") || Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem.give." + item.getName())) {
            item.y(player);
            sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.give.to", locale), new StringBuilder().append(item.getDisplay()).append((Object)ChatColor.AQUA).toString(), player.getName()));
            player.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.give.ok", locale), item.getDisplay()));
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
        }
    }

    @ak_1(a="rpgitem $n[] give $p[] $count:i[]")
    @ah_1(a="$command.rpgitem.give.player.count")
    @aj_1(a="item_give")
    public void a(CommandSender sender, RPGItem item, Player player, int count) {
        String locale = ao_0.a((Object)sender);
        if (!Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem") || Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem.give." + item.getName())) {
            for (int i = 0; i < count; ++i) {
                item.y(player);
            }
            sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.give.to", locale), new StringBuilder().append(item.getDisplay()).append((Object)ChatColor.AQUA).toString(), player.getName()));
            player.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.give.ok", locale), item.getDisplay()));
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
        }
    }

    @ak_1(a="rpgitem $n[] giveall", b=true)
    @ah_1(a="$command.rpgitem.giveall")
    @aj_1(a="item_give")
    public void d(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        if (!Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem") || Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem.give." + item.getName())) {
            int loop = 0;
            for (Player player : bg_1.c()) {
                item.y(player);
                player.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.giveall.ok", locale), item.getDisplay()));
                ++loop;
            }
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.giveall.to", locale), item.getDisplay(), loop));
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
        }
    }

    @ak_1(a="rpgitem $n[] giveall $count:i[]")
    @ah_1(a="$command.rpgitem.givell.count")
    @aj_1(a="item_give")
    public void c(CommandSender sender, RPGItem item, int count) {
        String locale = ao_0.a((Object)sender);
        if (!Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem") || Plugin.c.c().getBoolean("give-perms", false) && sender.hasPermission("rpgitem.give." + item.getName())) {
            int loop = 0;
            for (Player player : bg_1.c()) {
                for (int i = 0; i < loop; ++i) {
                    item.y(player);
                }
                player.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.giveall.ok", locale), item.getDisplay()));
                ++loop;
            }
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.giveall.to", locale), item.getDisplay(), loop));
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
        }
    }

    @ak_1(a="rpgitem $n[] damagetype")
    @ah_1(a="$command.rpgitem.toggledamagetype")
    @aj_1(a="item_damagetype")
    public void e(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        item.a = !item.a;
        Object[] arrobject = new Object[2];
        arrobject[0] = item.getName();
        arrobject[1] = ao_0.a("display.damagetype." + (item.a ? "" : "in") + "direct", locale);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.toggledamagetype.ok", locale), arrobject));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] remove")
    @ah_1(a="$command.rpgitem.remove")
    @aj_1(a="item_remove")
    public void f(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        by_0.b(item);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.remove.ok", locale), item.getName()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] display")
    @ah_1(a="$command.rpgitem.display")
    @aj_1(a="item_display")
    public void g(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.display.get", locale), item.getName(), item.getDisplay()));
    }

    @ak_1(a="rpgitem $n[] display $display:s[]")
    @ah_1(a="$command.rpgitem.display.set")
    @aj_1(a="item_display")
    public void c(CommandSender sender, RPGItem item, String display) {
        String locale = ao_0.a((Object)sender);
        item.e(display);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.display.set", locale), item.getName(), item.getDisplay()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] quality")
    @ah_1(a="$command.rpgitem.quality")
    @aj_1(a="item_quality")
    public void h(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.quality.get", locale), item.getName(), item.n().toString().toLowerCase()));
    }

    @ak_1(a="rpgitem $n[] quality $e[#Quality]")
    @ah_1(a="$command.rpgitem.quality.set")
    @aj_1(a="item_quality")
    public void a(CommandSender sender, RPGItem item, bd_0 quality) {
        String locale = ao_0.a((Object)sender);
        item.a(quality);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.quality.set", locale), item.getName(), item.n().toString().toLowerCase()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] damage")
    @ah_1(a="$command.rpgitem.damage")
    @aj_1(a="item_damage")
    public void i(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.damage.get", locale), item.getName(), item.f(), item.g()));
    }

    @ak_1(a="rpgitem $n[] damage $damage:i[]")
    @ah_1(a="$command.rpgitem.damage.set")
    @aj_1(a="item_damage")
    public void d(CommandSender sender, RPGItem item, int damage) {
        String locale = ao_0.a((Object)sender);
        item.a(damage, damage);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.damage.set", locale), item.getName(), item.f()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] damage $min:i[] $max:i[]")
    @ah_1(a="$command.rpgitem.damage.set.range")
    @aj_1(a="item_damage")
    public void a(CommandSender sender, RPGItem item, int min, int max) {
        String locale = ao_0.a((Object)sender);
        item.a(min, max);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.damage.set.range", locale), item.getName(), item.f(), item.g()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] armour")
    @ah_1(a="$command.rpgitem.armour")
    @aj_1(a="item_armour")
    public void j(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.armour.get", locale), item.getName(), item.k()));
    }

    @ak_1(a="rpgitem $n[] armour $armour:i[0,100]")
    @ah_1(a="$command.rpgitem.armour.set")
    @aj_1(a="item_armour")
    public void e(CommandSender sender, RPGItem item, int armour) {
        String locale = ao_0.a((Object)sender);
        item.d(armour);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.armour.set", locale), item.getName(), item.k()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] armournum")
    @ah_1(a="$command.rpgitem.armour")
    @aj_1(a="item_armour")
    public void k(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.armour.get", locale), item.getName(), item.l()));
    }

    @ak_1(a="rpgitem $n[] armournum $armour:i[0,2147483647]")
    @ah_1(a="$command.rpgitem.armour.set")
    @aj_1(a="item_armour")
    public void f(CommandSender sender, RPGItem item, int armour) {
        String locale = ao_0.a((Object)sender);
        item.e(armour);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.armour.set", locale), item.getName(), item.l()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] type")
    @ah_1(a="$command.rpgitem.type")
    @aj_1(a="item_type")
    public void l(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.type.get", locale), item.getName(), item.d()));
    }

    @ak_1(a="rpgitem $n[] type $type:s[]")
    @ah_1(a="$command.rpgitem.type.set")
    @aj_1(a="item_type")
    public void d(CommandSender sender, RPGItem item, String type) {
        String locale = ao_0.a((Object)sender);
        item.f(type);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.type.set", locale), item.getName(), item.d()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] hand")
    @ah_1(a="$command.rpgitem.hand")
    @aj_1(a="item_hand")
    public void m(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.hand.get", locale), item.getName(), item.e()));
    }

    @ak_1(a="rpgitem $n[] hand $hand:s[]")
    @ah_1(a="$command.rpgitem.hand.set")
    @aj_1(a="item_hand")
    public void e(CommandSender sender, RPGItem item, String hand) {
        String locale = ao_0.a((Object)sender);
        item.g(hand);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.hand.set", locale), item.getName(), item.e()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] lore")
    @ah_1(a="$command.rpgitem.lore")
    @aj_1(a="item_lore")
    public void n(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.lore.get", locale), item.getName(), item.m()));
    }

    @ak_1(a="rpgitem $n[] lore $lore:s[]")
    @ah_1(a="$command.rpgitem.lore.set")
    @aj_1(a="item_lore")
    public void f(CommandSender sender, RPGItem item, String lore) {
        String locale = ao_0.a((Object)sender);
        item.i(lore);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.lore.set", locale), item.getName(), item.m()));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] item")
    @ah_1(a="$command.rpgitem.item")
    @aj_1(a="item_item")
    public void o(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.item.get", locale), item.getName(), item.p().toString()));
    }

    @ak_1(a="rpgitem $n[] item $m[]")
    @ah_1(a="$command.rpgitem.item.set")
    @aj_1(a="item_item")
    public void a(CommandSender sender, RPGItem item, Material material) {
        String locale = ao_0.a((Object)sender);
        item.a(material);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.item.set", locale), new Object[]{item.getName(), item.p(), item.o()}));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] item $m[] $data:i[]")
    @ah_1(a="$command.rpgitem.item.set.data")
    @aj_1(a="item_item")
    public void a(CommandSender sender, RPGItem item, Material material, int data) {
        String locale = ao_0.a((Object)sender);
        item.a(material, false);
        ItemMeta meta = item.b(locale);
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)meta).setColor(Color.fromRGB((int)data));
        } else {
            item.a((short)data);
        }
        for (String locales : ao_0.a()) {
            item.a(locales, meta.clone());
        }
        item.a();
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.item.set", locale), new Object[]{item.getName(), item.p(), item.o()}));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] item $m[] hex $hexcolour:s[]")
    @ah_1(a="$command.rpgitem.item.set.data.hex")
    @aj_1(a="item_item")
    public void a(CommandSender sender, RPGItem item, Material material, String hexColour) {
        int dam;
        String locale = ao_0.a((Object)sender);
        try {
            dam = Integer.parseInt(hexColour, 16);
        }
        catch (NumberFormatException e2) {
            sender.sendMessage((Object)ChatColor.RED + "Failed to parse " + hexColour);
            return;
        }
        item.a(material, true);
        ItemMeta meta = item.b(locale);
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)meta).setColor(Color.fromRGB((int)dam));
        } else {
            item.a((short)dam);
        }
        for (String locales : ao_0.a()) {
            item.a(locales, meta.clone());
        }
        item.a();
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.item.set", locale), new Object[]{item.getName(), item.p(), item.o()}));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] item $itemid:i[]")
    @ah_1(a="$command.rpgitem.item.set.id")
    @aj_1(a="item_item")
    public void g(CommandSender sender, RPGItem item, int id) {
        String locale = ao_0.a((Object)sender);
        Material mat = Material.getMaterial((int)id);
        if (mat == null) {
            sender.sendMessage((Object)ChatColor.RED + "Cannot find item");
            return;
        }
        item.a(mat);
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.item.set", locale), new Object[]{item.getName(), item.p(), item.o()}));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] item $itemid:i[] $data:i[]")
    @ah_1(a="$command.rpgitem.item.set.id.data")
    @aj_1(a="item_item")
    public void b(CommandSender sender, RPGItem item, int id, int data) {
        String locale = ao_0.a((Object)sender);
        Material mat = Material.getMaterial((int)id);
        if (mat == null) {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.item.cant.find", locale));
            return;
        }
        item.a(mat, true);
        ItemMeta meta = item.toItemStack(locale).getItemMeta();
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)meta).setColor(Color.fromRGB((int)data));
        } else {
            item.a((short)data);
        }
        for (String locales : ao_0.a()) {
            item.a(locales, meta);
        }
        item.a();
        sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.item.set", locale), new Object[]{item.getName(), item.p(), item.o()}));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] removepower $power:s[]")
    @ah_1(a="$command.rpgitem.removepower")
    @aj_1(a="item_removepower")
    public void g(CommandSender sender, RPGItem item, String power) {
        String locale = ao_0.a((Object)sender);
        if (power.equalsIgnoreCase("all")) {
            item.s();
            sender.sendMessage((Object)ChatColor.GREEN + ao_0.a("message.power.removedall", locale));
            by_0.c(Plugin.c);
        } else if (item.j(power)) {
            bi_1.b.a(power, bi_1.b.b(power) - 1);
            sender.sendMessage((Object)ChatColor.GREEN + String.format(ao_0.a("message.power.removed", locale), power));
            by_0.c(Plugin.c);
        } else {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.power.unknown", locale), power));
        }
    }

    @ak_1(a="rpgitem $n[] removepower $power:s[] $e[#EventType]")
    @ah_1(a="$command.rpgitem.removepower")
    @aj_1(a="item_removepower")
    public void a(CommandSender sender, RPGItem item, String power, am_0 eventType) {
        String locale = ao_0.a((Object)sender);
        if (power.equalsIgnoreCase("all")) {
            item.b(eventType);
            sender.sendMessage((Object)ChatColor.GREEN + String.format(ao_0.a("message.power.removedall.eventtype", locale), eventType.getName().toLowerCase()));
            by_0.c(Plugin.c);
        } else if (item.a(power, eventType)) {
            bi_1.b.a(power, bi_1.b.b(power) - 1);
            sender.sendMessage((Object)ChatColor.GREEN + String.format(ao_0.a("message.power.removed.eventtype", locale), eventType.getName().toLowerCase(), power));
            by_0.c(Plugin.c);
        } else {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.power.unknown", locale), power));
        }
    }

    @ak_1(a="rpgitem $n[] description $string:o[desc,lore] add $descriptionline:s[]")
    @ah_1(a="$command.rpgitem.description.add")
    @aj_1(a="item_description")
    public void b(CommandSender sender, RPGItem item, String type, String line) {
        String locale = ao_0.a((Object)sender);
        boolean desc = type.equalsIgnoreCase("desc");
        if (desc) {
            item.k(line);
        } else {
            item.l(line);
        }
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.description.ok", locale));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] description $string:o[desc,lore] set $lineno:i[] $descriptionline:s[]")
    @ah_1(a="$command.rpgitem.description.set")
    @aj_1(a="item_description")
    public void a(CommandSender sender, RPGItem item, String type, int lineNo, String line) {
        String locale = ao_0.a((Object)sender);
        if (lineNo < 0 || lineNo >= item.f.size()) {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.description.out.of.range", locale), lineNo));
            return;
        }
        boolean desc = type.equalsIgnoreCase("desc");
        if (desc) {
            item.f.set(lineNo, bg_1.a(line));
            item.a();
        } else {
            item.g.set(lineNo, bg_1.a(line));
        }
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.description.change", locale));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] description $string:o[desc,lore] remove $lineno:i[]")
    @ah_1(a="$command.rpgitem.description.remove")
    @aj_1(a="item_description")
    public void a(CommandSender sender, RPGItem item, String type, int lineNo) {
        String locale = ao_0.a((Object)sender);
        if (lineNo < 0 || lineNo >= item.f.size()) {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.description.out.of.range", locale), lineNo));
            return;
        }
        boolean desc = type.equalsIgnoreCase("desc");
        if (desc) {
            item.f.remove(lineNo);
            item.a();
        } else {
            item.g.remove(lineNo);
        }
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.description.remove", locale));
        by_0.c(Plugin.c);
    }

    @ak_1(a="rpgitem $n[] onlyshowdesc $string:o[true,false]")
    @ah_1(a="$command.rpgitem.description.onlyshow")
    @aj_1(a="item_description_onlyshow")
    public void h(CommandSender sender, RPGItem item, String string) {
        boolean enabled;
        String locale = ao_0.a((Object)sender);
        item.h = enabled = string.equalsIgnoreCase("true");
        item.a();
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.description.onlyshow", locale), string));
    }

    @ak_1(a="rpgitem $n[] removerecipe")
    @ah_1(a="$command.rpgitem.removerecipe")
    @aj_1(a="item_recipe")
    public void p(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        item.j = false;
        item.a(true);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.recipe.removed", locale));
    }

    @ak_1(a="rpgitem $n[] recipe $chance:i[]")
    @ah_1(a="$command.rpgitem.recipe")
    @aj_1(a="item_recipe")
    public void h(CommandSender sender, RPGItem item, int chance) {
        String locale = ao_0.a((Object)sender);
        if (sender instanceof Player) {
            Player player = (Player)sender;
            String title = "RPGItems - " + item.getDisplay();
            if (title.length() > 32) {
                title = title.substring(0, 32);
            }
            Inventory recipeInventory = Bukkit.createInventory((InventoryHolder)player, (int)27, (String)title);
            if (item.j) {
                ItemStack blank = new ItemStack(Material.WALL_SIGN);
                ItemMeta meta = blank.getItemMeta();
                meta.setDisplayName((Object)ChatColor.RED + ao_0.a("message.recipe.1", locale));
                ArrayList<String> lore = new ArrayList<String>();
                lore.add((Object)ChatColor.WHITE + ao_0.a("message.recipe.2", locale));
                lore.add((Object)ChatColor.WHITE + ao_0.a("message.recipe.3", locale));
                lore.add((Object)ChatColor.WHITE + ao_0.a("message.recipe.4", locale));
                lore.add((Object)ChatColor.WHITE + ao_0.a("message.recipe.5", locale));
                meta.setLore(lore);
                blank.setItemMeta(meta);
                for (int i = 0; i < 27; ++i) {
                    recipeInventory.setItem(i, blank);
                }
                for (int x = 0; x < 3; ++x) {
                    for (int y = 0; y < 3; ++y) {
                        int i = x + y * 9;
                        ItemStack it = item.k.get(x + y * 3);
                        if (it != null) {
                            recipeInventory.setItem(i, it);
                            continue;
                        }
                        recipeInventory.setItem(i, null);
                    }
                }
            }
            item.c(chance);
            player.openInventory(recipeInventory);
            bg_0.b.a(player.getName(), item.getID());
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.only.player", locale));
        }
    }

    @ak_1(a="rpgitem $n[] drop $e[org.bukkit.entity.EntityType]")
    @ah_1(a="$command.rpgitem.drop")
    @aj_1(a="item_drop")
    public void a(CommandSender sender, RPGItem item, EntityType type) {
        String locale = ao_0.a((Object)sender);
        sender.sendMessage(String.format((Object)ChatColor.AQUA + ao_0.a("message.drop.get", locale), item.getDisplay() + (Object)ChatColor.AQUA, type.toString().toLowerCase(), item.l.b(type.toString())));
    }

    @ak_1(a="rpgitem $n[] drop $e[org.bukkit.entity.EntityType] $chance:f[]")
    @ah_1(a="$command.rpgitem.drop.set")
    @aj_1(a="item_drop")
    public void a(CommandSender sender, RPGItem item, EntityType type, double chance) {
        String locale = ao_0.a((Object)sender);
        chance = Math.min(chance, 100.0);
        String typeS = type.toString();
        if (chance > 0.0) {
            item.l.a(typeS, chance);
            if (!bf_1.c.containsKey(typeS)) {
                bf_1.c.put(typeS, new HashSet());
            }
            Set<Integer> set = bf_1.c.get(typeS);
            set.add(item.getID());
        } else {
            item.l.b_(typeS);
            if (bf_1.c.containsKey(typeS)) {
                Set<Integer> set = bf_1.c.get(typeS);
                set.remove(item.getID());
            }
        }
        by_0.c(Plugin.c);
        sender.sendMessage(String.format((Object)ChatColor.AQUA + ao_0.a("message.drop.set", locale), item.getDisplay() + (Object)ChatColor.AQUA, typeS.toLowerCase(), item.l.b(typeS)));
    }

    @ak_1(a="rpgitem $n[] disappear $chance:i[0,2147483647]")
    @ah_1(a="$command.rpgitem.disappear.set")
    @aj_1(a="item_disappear")
    public void i(CommandSender sender, RPGItem item, int chance) {
        String locale = ao_0.a((Object)sender);
        item.m = chance;
        by_0.c(Plugin.c);
        sender.sendMessage(String.format((Object)ChatColor.AQUA + ao_0.a("message.disappear.set", locale), item.getDisplay() + (Object)ChatColor.AQUA, chance));
    }

    @ak_1(a="rpgitem $n[] repairprice $price:f[0.0d,10000000.0d]")
    @ah_1(a="$command.rpgitem.repairprice.set")
    @aj_1(a="item_repair")
    public void b(CommandSender sender, RPGItem item, double price) {
        String locale = ao_0.a((Object)sender);
        item.b = price;
        by_0.c(Plugin.c);
        sender.sendMessage(String.format((Object)ChatColor.AQUA + ao_0.a("message.repairprice.set", locale), item.getDisplay() + (Object)ChatColor.AQUA, price));
    }

    @ak_1(a="rpgitem repair", c="rpgitem.command.repair")
    @ah_1(a="$command.rpgitem.repair")
    @aj_1(a="use_player_repair")
    @ai_1(a=ai_1.a.PLAYER)
    public void c(CommandSender sender) {
        RPGItem rItem;
        String locale = ao_0.a((Object)sender);
        Player player = (Player)sender;
        ItemStack item = player.getItemInHand();
        if (bg_1.a(item) && (rItem = by_0.a(item)) != null && rItem.b > 0.0 && rItem.q() != -1) {
            as_0 meta = RPGItem.b(item);
            int durability = meta.c(0) ? ((Number)meta.j_(0)).intValue() : rItem.q();
            int needRepair = rItem.q() - durability;
            if (needRepair > 0) {
                if (bi_0.a(bm_0.class).c(player, rItem.b * (double)needRepair)) {
                    meta.a(0, Integer.valueOf(durability + needRepair));
                    RPGItem.a(item, locale, meta);
                    player.updateInventory();
                    sender.sendMessage(String.format((Object)ChatColor.AQUA + ao_0.a("message.repair.ok", locale), rItem.getDisplay() + (Object)ChatColor.AQUA));
                }
                return;
            }
        }
        sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.cantrepair", locale));
    }

    @ak_1(a="rpgitem $n[] savenbt")
    @ah_1(a="$command.rpgitem.savenbt")
    @aj_1(a="item_keep")
    @ai_1(a=ai_1.a.PLAYER)
    public void q(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        bk_0 protocolLib = bi_0.a(bk_0.class);
        if (!protocolLib.b()) {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.clonenbt.protocollib", locale));
            return;
        }
        Player player = (Player)sender;
        ItemStack itemHold = player.getItemInHand();
        if (bg_1.a(itemHold)) {
            try {
                item.a(itemHold);
            }
            catch (Exception ex) {
                sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.clonenbt.error", locale));
                return;
            }
            by_0.c(Plugin.c);
            sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.clonenbt.ok", locale));
        }
    }

    @ak_1(a="rpgitem $n[] durability $durability:i[]")
    @ah_1(a="$command.rpgitem.durability")
    @aj_1(a="item_durability")
    public void j(CommandSender sender, RPGItem item, int newValue) {
        String locale = ao_0.a((Object)sender);
        item.f(newValue);
        by_0.c(Plugin.c);
        sender.sendMessage(ao_0.a("message.durability.change", locale));
    }

    @ak_1(a="rpgitem $n[] durability infinite")
    @ah_1(a="$command.rpgitem.durability.infinite")
    @aj_1(a="item_durability")
    public void r(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        item.f(-1);
        by_0.c(Plugin.c);
        sender.sendMessage(ao_0.a("message.durability.change", locale));
    }

    @ak_1(a="rpgitem $n[] durability togglebar")
    @ah_1(a="$command.rpgitem.durability.togglebar")
    @aj_1(a="item_durability")
    public void s(CommandSender sender, RPGItem item) {
        String locale = ao_0.a((Object)sender);
        item.u();
        by_0.c(Plugin.c);
        sender.sendMessage(ao_0.a("message.durability.toggle", locale));
    }

    @ak_1(a="rpgitem $n[] permission $permission:s[] $haspermission:s[]")
    @ah_1(a="$command.rpgitem.permission")
    @aj_1(a="item_permission")
    public void c(CommandSender sender, RPGItem item, String permission, String haspermission) {
        String locale = ao_0.a((Object)sender);
        boolean enabled = false;
        if (haspermission.equalsIgnoreCase("true")) {
            enabled = true;
        } else if (haspermission.equalsIgnoreCase("false")) {
            enabled = false;
        } else {
            sender.sendMessage(ao_0.a("message.permission.booleanerror", locale));
        }
        item.h(permission);
        item.b(enabled);
        by_0.c(Plugin.c);
        sender.sendMessage(ao_0.a("message.permission.success", locale));
    }
}

