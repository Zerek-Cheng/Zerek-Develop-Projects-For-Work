package com._0myun.bind;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com._0myun.bind.Utils.*;

public final class R extends JavaPlugin {

    public static R INSTANCE = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new Listener(), this);
        new InvCheckTask().runTaskTimerAsynchronously(this, 20l, 20l);
    }

    public String lang(String lang) {
        return getConfig().getString("lang." + lang);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            if (!hasBind(p.getItemInHand())) return true;
            p.sendMessage(getOwner(p.getItemInHand()));
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("bind") && sender.hasPermission("bind.bind")) {
            setOwner(p.getItemInHand(), p.getUniqueId());
            runCommand(p, "bind");
            p.sendMessage(lang("lang1"));
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("unbind") && sender.hasPermission("bind.unbind")) {
            if (checkMeta(p.getItemInHand(),true)){
                p.sendMessage(lang("lang6"));
                return true;
            }
            setOwner(p.getItemInHand(), null);
            runCommand(p, "unbind");
            p.sendMessage(lang("lang1"));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("bindmeta")
                && sender.hasPermission("bind.admin.bind")) {
            bindMeta(p.getItemInHand());
            p.sendMessage(lang("lang1"));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("bindid")
                && sender.hasPermission("bind.admin.bind")) {
            bindId(p.getItemInHand().getTypeId() + ":" + p.getItemInHand().getDurability());
            p.sendMessage(lang("lang1"));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("unbindmeta")
                && sender.hasPermission("bind.admin.bind")) {
            unbindId(p.getItemInHand().getTypeId() + ":" + p.getItemInHand().getDurability());
            p.sendMessage(lang("lang1"));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("unbindid")
                && sender.hasPermission("bind.admin.bind")) {
            unbindMeta(p.getItemInHand());
            p.sendMessage(lang("lang1"));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("isbind")
                && sender.hasPermission("bind.admin.bind")) {
            p.sendMessage(checkMeta(p.getItemInHand(), true) ? "预约绑定了" : "没预约绑定");
        }
        return true;
    }

    public void runCommand(Player p, String type) {
        List<String> cmds = getConfig().getStringList("cmd." + type);
        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            for (String cmd : cmds) {
                p.performCommand(cmd.replace("!player!", p.getName()));
            }
        } finally {
            if (!op) p.setOp(false);
        }
    }

    public void bindId(String ids) {
        List<String> list = getConfig().getStringList("bind.ids");
        if (list.contains(ids)) return;
        list.add(ids);
        getConfig().set("bind.ids", list);
        saveConfig();
    }

    public void bindMeta(ItemStack itemStack) {
        if (checkMeta(itemStack, false)) return;
        Material type = itemStack.getType();
        ItemMeta itemMeta = itemStack.getItemMeta();
        String display = itemMeta.getDisplayName();
        List<String> lore = itemMeta.getLore();
        List<Map<?, ?>> list = getConfig().getMapList("bind.items");
        HashMap map = new HashMap();
        /**
         * display: "名称xxxx"
         *       material: "MATERIAL"
         *       lores:
         *         - "lores"
         */
        map.put("display", display);
        map.put("material", type.toString());
        map.put("lores", itemMeta.getLore());
        list.add(map);
        getConfig().set("bind.items", list);
        saveConfig();
    }

    public void unbindId(String ids) {
        List<String> list = getConfig().getStringList("bind.ids");
        if (!list.contains(ids)) return;
        list.remove(ids);
        getConfig().set("bind.ids", list);
        saveConfig();
    }

    public boolean unbindMeta(ItemStack itemStack) {
        if (!checkMeta(itemStack, false)) return false;

        Material type = itemStack.getType();
        ItemMeta itemMeta = itemStack.getItemMeta();
        String display = itemMeta.getDisplayName();
        List<String> lore = itemMeta.getLore();

        List<Map<?, ?>> list = getConfig().getMapList("bind.items");
        Iterator<Map<?, ?>> iter = list.iterator();
        while (iter.hasNext()) {
            Map<?, ?> map = iter.next();
            Object odisplay = map.get("display");
            Object omaterial = map.get("material");
            Object olores = map.get("lores");
            if (!display.equalsIgnoreCase(String.valueOf(odisplay))) continue;
            if (!type.toString().equalsIgnoreCase(String.valueOf(omaterial))) continue;
            if (!lore.toString().equalsIgnoreCase(olores.toString())) continue;
            iter.remove();
            return true;
        }
        return false;
    }

    public boolean checkMeta(ItemStack itemStack, boolean checkId) {
        if (checkId && getConfig().getStringList("bind.ids").contains(itemStack.getTypeId() + ":" + itemStack.getDurability()))
            return true;
        Material type = itemStack.getType();
        ItemMeta itemMeta = itemStack.getItemMeta();
        String display = itemMeta.getDisplayName();
        List<String> lore = itemMeta.getLore();
        for (Map<?, ?> map : getConfig().getMapList("bind.items")) {
            Object odisplay = map.get("display");
            Object omaterial = map.get("material");
            Object olores = map.get("lores");
            if (display == null && odisplay != null) continue;
            if (display != null && odisplay == null) continue;
            if (type == null && omaterial != null) continue;
            if (type != null && omaterial == null) continue;
            if (lore == null && olores != null) continue;
            if (lore != null && olores == null) continue;
            if ((display != null && odisplay != null) && !display.equalsIgnoreCase(String.valueOf(odisplay))) continue;
            if ((type != null && omaterial != null) && !type.toString().equalsIgnoreCase(String.valueOf(omaterial)))
                continue;
            if ((lore != null && olores != null) && !lore.toString().equalsIgnoreCase(olores.toString())) continue;
            return true;
        }
        return false;
    }
}
