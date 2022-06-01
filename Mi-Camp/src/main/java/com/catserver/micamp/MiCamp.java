package com.catserver.micamp;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MiCamp extends JavaPlugin implements Listener {

    private Chat chat;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupChat();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
            createCamp(args[1]);
            p.sendMessage("创建请求已提交");
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("hz")) {
            setCampHz(args[1], args[2]);
            p.sendMessage("后缀已设置");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            deleteCamp(args[1]);
            p.sendMessage("删除请求已提交");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            joinCamp(p.getUniqueId(), args[1]);
            p.sendMessage("已加入成功");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("quit")) {
            quitCamp(p.getUniqueId(), args[1]);
            p.sendMessage("已退出成功");
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            p.sendMessage(getPlayerCamps(p.getUniqueId()).toString());
        }
        saveConfig();
        return true;
    }

    public boolean existCamp(String name) {
        return getConfig().isSet("camp." + name);
    }

    public void createCamp(String name) {
        if (existCamp(name)) return;
        getConfig().set("camp." + name, "[未设置]");
    }

    public void deleteCamp(String name) {
        if (!existCamp(name)) return;
        getConfig().set("camp." + name, null);
    }

    public void setCampHz(String name, String hz) {
        if (!existCamp(name)) return;
        getConfig().set("camp." + name, hz);

        getConfig().getStringList("data." + name).forEach(uuid ->
                Bukkit.getWorlds().forEach(world ->
                        chat.setPlayerSuffix(world.getName(), Bukkit.getOfflinePlayer(uuid), getCampHz(name))));
    }

    public String getCampHz(String name) {
        return getConfig().getString("camp." + name);
    }

    public boolean isJoin(UUID uuid, String name) {
        return getConfig().getStringList("data." + name).contains(uuid.toString());
    }

    public void joinCamp(UUID uuid, String name) {
        if (!existCamp(name)) return;
        if (isJoin(uuid, name)) return;
        List<String> list = getConfig().getStringList("data." + name);
        list.add(uuid.toString());
        getConfig().set("data." + name, list);
        Bukkit.getWorlds().forEach(world -> chat.setPlayerSuffix(world.getName(), Bukkit.getOfflinePlayer(uuid), getCampHz(name)));
    }

    public void quitCamp(UUID uuid, String name) {
        if (!isJoin(uuid, name)) return;
        List<String> list = getConfig().getStringList("data." + name);
        list.remove(uuid.toString());
        getConfig().set("data." + name, list);
    }

    public List<String> getPlayerCamps(UUID uuid) {
        List<String> camps = new ArrayList<>();
        ConfigurationSection data = getConfig().getConfigurationSection("data");
        data.getKeys(false).forEach(key -> {
            List<String> uuids = data.getStringList(key);
            if (uuids.contains(uuid.toString())) camps.add(key);
        });
        return camps;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        Player eP = (Player) e.getDamager();
        List<String> pCamps = getPlayerCamps(p.getUniqueId());
        List<String> dPCamps = getPlayerCamps(eP.getUniqueId());
        pCamps.retainAll(dPCamps);
        if (pCamps.size() > 0) {
            e.setCancelled(true);
            p.sendMessage("队友免伤");
            eP.sendMessage("队友免伤");
        }

    }
}
