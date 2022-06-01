package com._0myun.minecraft.killmmmobsreward;

import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {
    static Main INSTANCE;
    BukkitAPIHelper mmApi;

    public static Permission permission = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        mmApi = new BukkitAPIHelper();
        setupPermissions();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp() && !sender.hasPermission("mr.admin")) return true;
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        if (args.length == 4 && args[0].equalsIgnoreCase("group")) {
            String pName = args[1];
            String gName = args[2];
            setGroup(gName, Bukkit.getOfflinePlayer(pName).getUniqueId());
            removeValidate(Bukkit.getOfflinePlayer(pName).getUniqueId());
            sender.sendMessage(getConfig().getString("lang1"));
            onCommand(sender, command, label, new String[]{"validate", args[3]});
            return true;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("group")) {
            String pName = args[1];
            String gName = args[2];
            setGroup(gName, Bukkit.getOfflinePlayer(pName).getUniqueId());
            removeValidate(Bukkit.getOfflinePlayer(pName).getUniqueId());
            sender.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("group")) {
            String pName = sender.getName();
            String gName = args[1];
            setGroup(gName, Bukkit.getOfflinePlayer(pName).getUniqueId());
            removeValidate(Bukkit.getOfflinePlayer(pName).getUniqueId());
            sender.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            String pName = sender.getName();
            String gName = args[1];
            setGroup(gName, Bukkit.getOfflinePlayer(pName).getUniqueId());
            removeValidate(Bukkit.getOfflinePlayer(pName).getUniqueId());
            sender.sendMessage(getConfig().getString("lang1"));
            onCommand(sender, command, label, new String[]{"validate", args[2]});
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
            String pName = sender.getName();
            //String gName = getGroup(Bukkit.getOfflinePlayer(sender.getName()).getUniqueId());
            long validate = getValidate(Bukkit.getOfflinePlayer(sender.getName()).getUniqueId());

            removeValidate(Bukkit.getOfflinePlayer(pName).getUniqueId());
            sender.sendMessage(getConfig().getString("lang1"));
            onCommand(sender, command, label, new String[]{"validate", String.valueOf(validate + Long.valueOf(args[2]))});
            return true;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("validate")) {
            String pName = args[1];
            int validate = Integer.valueOf(args[2]);
            setValidate(Bukkit.getOfflinePlayer(pName).getUniqueId(), System.currentTimeMillis() + (validate * 1000));
            sender.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("validate")) {
            int validate = Integer.valueOf(args[1]);
            setValidate(Bukkit.getOfflinePlayer(sender.getName()).getUniqueId(), System.currentTimeMillis() + (validate * 1000));
            sender.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("time")) {
            Player p = (Player) sender;
            p.sendMessage(String.format(getConfig().getString("lang2"), getGroup(p.getUniqueId())
                    , new SimpleDateFormat("yyyy-MM-dd h:m:s").format(new Date(getValidate(p.getUniqueId())))))
            ;
            return true;
        }
        sender.sendMessage("/mr reload\n" +
                "/mr group <player> <group> <time>\n" +
                "/mr group <player> <group>\n" +
                "/mr group <group>\n" +
                "/mr add <time>" +
                "/mr add <group> <time>" +
                "/mr validate <player> <time>\n" +
                "/mr validate <time>" +
                "/mr time");
        return true;
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (!(e.getEntity().getKiller() instanceof Player) || !mmApi.isMythicMob(e.getEntity())) return;
        Player killer = e.getEntity().getKiller();
        ActiveMob mob = mmApi.getMythicMobInstance(e.getEntity());
        String key = mob.getType().getConfig().getKey();
        if (!existMobConfig(key)) return;
        String groupName = getGroup(killer.getUniqueId());

        List<Map<?, ?>> mobConfig = getMobConfig(key);
        mobConfig.forEach(map -> {
            String type = (String) map.get("type");
            String group = null;
            String permission = null;
            double rand = Double.valueOf(String.valueOf(map.get("rand")));
            String msg = (String) map.get("msg");
            if (type.equalsIgnoreCase("group")) group = (String) map.get("group");
            if (type.equalsIgnoreCase("permission")) permission = (String) map.get("permission");
            List<String> cmds = (List<String>) map.get("cmd");
            if (Math.random() > rand) return;
            if (group == null && permission == null) return;
            if (group != null && !group.equalsIgnoreCase(groupName)) return;
            if (permission != null && !Main.permission.has(killer, permission)) return;
            killer.sendMessage(msg);
            boolean op = killer.isOp();
            try {
                if (!op) killer.setOp(true);
                cmds.forEach(cmd -> killer.performCommand(cmd.replace("<player>", killer.getName())));
            } finally {
                if (!op) killer.setOp(false);
            }
        });
    }

    public boolean existMobConfig(String name) {
        return getMobConfig(name) != null;
    }

    public List<Map<?, ?>> getMobConfig(String name) {
        return getConfig().getMapList("mobs." + name);
    }

    public void setGroup(String group, UUID uuid) {
        getConfig().set("data.group." + uuid.toString(), group);
        saveConfig();
    }

    public String getGroup(UUID uuid) {
        if (getValidate(uuid) < System.currentTimeMillis()) return "Default";
        return getConfig().getString("data.group." + uuid.toString(), "Default");
    }

    public long getValidate(UUID uuid) {
        return getConfig().getLong("data.validate." + uuid.toString(), System.currentTimeMillis() + 31536000l);
    }

    public void setValidate(UUID uuid, long validate) {
        getConfig().set("data.validate." + uuid.toString(), validate);
    }

    public void removeValidate(UUID uuid) {
        getConfig().set("data.validate." + uuid.toString(), null);
    }
}
