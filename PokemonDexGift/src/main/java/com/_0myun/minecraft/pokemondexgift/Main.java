package com._0myun.minecraft.pokemondexgift;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("缺少参数");
            return true;
        }
        Player p = (Player) sender;
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(p.getUniqueId());
        String name = args[0];
        if (name.equalsIgnoreCase("debug")) {
            p.sendMessage(String.valueOf(party.pokedex.countCaught()));
        }
        Integer total = getTotal(name);
        List<String> commands = getCommands(name);
        if (!exist(name)) {
            p.sendMessage(getLang("lang4"));
            return true;
        }
        if (party.pokedex.countCaught() < total) {
            p.sendMessage(getLang("lang1"));
            return true;
        }
        if (hasGet(p.getUniqueId(), name)) {
            p.sendMessage(getLang("lang2"));
            return true;
        }
        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            commands.forEach(commandStr -> {
                p.performCommand(commandStr.replace("<player>", p.getName()));
            });
        } finally {
            if (!op) p.setOp(false);
        }
        addGet(p.getUniqueId(), name);
        p.sendMessage(getLang("lang3"));
        saveConfig();
        return true;
    }

    public boolean exist(String name){
        return getConfig().isSet("gift."+name);
    }
    public Integer getTotal(String name) {
        return getConfig().getInt("gift." + name + ".total");
    }

    public List<String> getCommands(String name) {
        return getConfig().getStringList("gift." + name + ".command");
    }

    public String getLang(String name) {
        return getConfig().getString("lang." + name);
    }

    public boolean hasGet(UUID uuid, String name) {
        return getConfig().getStringList("data." + uuid.toString()).contains(name);
    }

    public void addGet(UUID uuid, String name) {
        List<String> get = getConfig().getStringList("data." + uuid.toString());
        get.add(name);
        getConfig().set("data." + uuid.toString(), get);
    }
}
