// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.command;

import com.github.shawhoi.nybattle.game.BattleArena;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import com.github.shawhoi.nybattle.config.BattleConfig;
import com.github.shawhoi.nybattle.util.LocationUtil;
import org.bukkit.entity.Player;
import java.util.Iterator;
import java.io.File;
import com.github.shawhoi.nybattle.Main;
import org.bukkit.Bukkit;
import com.github.shawhoi.nybattle.playerdata.PlayerData;
import com.github.shawhoi.nybattle.config.Message;
import com.github.shawhoi.nybattle.game.ArenaData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class battle implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            this.sendCommandHelp(sender);
            return true;
        }
        if (args[0].equalsIgnoreCase("join")) {
            this.JoinGame(sender, args);
        }
        else if (args[0].equalsIgnoreCase("leave")) {
            this.Leave(sender);
        }
        else if (args[0].equalsIgnoreCase("list") && sender.hasPermission("battle.list")) {
            this.List(sender);
        }
        else if (args[0].equalsIgnoreCase("create") && sender.hasPermission("battle.admin")) {
            this.createArena(sender, args);
        }
        else if (args[0].equalsIgnoreCase("name") && sender.hasPermission("battle.admin")) {
            this.setName(sender, args);
        }
        else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("battle.admin")) {
            this.reloadConfig(sender);
        }
        else if (args[0].equalsIgnoreCase("border") && sender.hasPermission("battle.admin")) {
            this.setBorder(sender, args);
        }
        else if ((args[0].equalsIgnoreCase("min") || args[0].equalsIgnoreCase("max")) && sender.hasPermission("battle.admin")) {
            this.setPlayerAmount(sender, args);
        }
        else if ((args[0].equalsIgnoreCase("setlobby") || args[0].equalsIgnoreCase("setend")) && sender.hasPermission("battle.admin")) {
            final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "setlobby": {
                    this.setLobby(sender, args, true);
                    break;
                }
                default: {
                    this.setLobby(sender, args, false);
                    break;
                }
            }
        }
        else if (args[0].equalsIgnoreCase("setitem") && sender.hasPermission("battle.admin")) {
            this.setItem(sender, args);
        }
        else if (args[0].equalsIgnoreCase("delete") && sender.hasPermission("battle.admin")) {
            this.delete(sender, args);
        }
        else if (args[0].equalsIgnoreCase("restart") && sender.hasPermission("battle.admin")) {
            this.restart(sender);
        }
        return true;
    }
    
    public void delete(final CommandSender sender, final String[] args) {
        if (args.length == 1 || !ArenaData.badata.containsKey(args[1])) {
            sender.sendMessage(Message.getPrefix() + Message.getString("Delete.FailArena"));
            return;
        }
        for (final String name : PlayerData.playerdata.keySet()) {
            if (PlayerData.playerdata.get(name).equals(args[1])) {
                final Player p = Bukkit.getPlayerExact(name);
                if (p != null && p.isOnline()) {
                    PlayerData.getPlayerGameArena(p).PlayerLeave(p, false);
                }
                else {
                    PlayerData.playerdata.remove(name);
                }
            }
        }
        ArenaData.removeArena(args[1]);
        final File f = new File(Main.getInstance().getDataFolder() + "\\Arena\\", args[1] + ".yml");
        if (f.exists()) {
            f.delete();
        }
        sender.sendMessage(Message.getPrefix() + Message.getString("Delete.Success"));
    }
    
    public void setLobby(final CommandSender sender, final String[] args, final boolean lobby) {
        if (args.length == 1 || !ArenaData.badata.containsKey(args[1])) {
            sender.sendMessage(Message.getPrefix() + (lobby ? Message.getString("SetLobby.FailArena") : Message.getString("SetEnd.FailArena")));
            return;
        }
        final File f = new File(Main.getInstance().getDataFolder() + "\\Arena\\", args[1] + ".yml");
        LocationUtil.setLocationConfiguration(((Player)sender).getLocation(), f, lobby ? "Lobby" : "End");
        sender.sendMessage(Message.getPrefix() + (lobby ? Message.getString("SetLobby.Success").replace("%arena%", args[1]) : Message.getString("SetEnd.Success").replace("%arena%", args[1])));
    }
    
    public void Leave(final CommandSender sender) {
        if (!PlayerData.PlayerInGame((Player)sender)) {
            sender.sendMessage(Message.getPrefix() + Message.getString("NotInGame"));
            return;
        }
        final Player p = (Player)sender;
        PlayerData.getPlayerGameArena(p).PlayerLeave(p, false);
    }
    
    public void restart(final CommandSender sender) {
        ArenaData.ResetAllArena();
        sender.sendMessage(Message.getPrefix() + Message.getString("Restart"));
    }
    
    public void setPlayerAmount(final CommandSender sender, final String[] args) {
        if (args.length == 1 || !ArenaData.badata.containsKey(args[1])) {
            sender.sendMessage(Message.getPrefix() + Message.getString("SetAmount.FailArena"));
            return;
        }
        if (args.length == 2) {
            sender.sendMessage(Message.getPrefix() + Message.getString("SetAmount.FailArg"));
            return;
        }
        final String type = args[0].equalsIgnoreCase("min") ? "Min" : "Max";
        int amount = 0;
        try {
            amount = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            sender.sendMessage(Message.getPrefix() + Message.getString("SetAmount.FailArg"));
            return;
        }
        this.setArenaConfiguration(args[1], type, amount);
        sender.sendMessage(Message.getPrefix() + Message.getString("SetAmount.Success"));
    }
    
    public void setBorder(final CommandSender sender, final String[] args) {
        if (args.length == 1 || !ArenaData.badata.containsKey(args[1])) {
            sender.sendMessage(Message.getPrefix() + Message.getString("Border.FailArena"));
            return;
        }
        if (args.length == 2) {
            sender.sendMessage(Message.getPrefix() + Message.getString("Border.FailArg"));
            return;
        }
        int radius = 0;
        try {
            radius = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            sender.sendMessage(Message.getPrefix() + Message.getString("Border.FailArg"));
            return;
        }
        this.setArenaConfiguration(args[1], "Border", radius);
        sender.sendMessage(Message.getPrefix() + Message.getString("Border.Success"));
    }
    
    public void reloadConfig(final CommandSender sender) {
        Main.getInstance().reloadConfig();
        BattleConfig.build(new File(Main.getInstance().getDataFolder(), "config.yml"));
        File language = new File(Main.getInstance().getDataFolder() + "\\lang\\", BattleConfig.getString("Settings.Language") + ".yml");
        if (!language.exists()) {
            try {
                Main.getInstance().saveResource("lang/" + BattleConfig.getString("Settings.Language") + ".yml", true);
            }
            catch (Exception e) {
                final File nl = new File(Main.getInstance().getDataFolder() + "\\lang\\", "zh_CN.yml");
                if (!nl.exists()) {
                    Main.getInstance().saveResource("lang/zh_CN.yml", true);
                }
                language = nl;
            }
        }
        Message.build(language);
        sender.sendMessage(Message.getPrefix() + Message.getString("Reload"));
    }
    
    public void createArena(final CommandSender sender, final String[] args) {
        if (args.length == 1) {
            sender.sendMessage(Message.getPrefix() + Message.getString("Create.FailArena"));
            return;
        }
        if (args.length == 2 || Bukkit.getWorld(args[2]) == null) {
            sender.sendMessage(Message.getPrefix() + Message.getString("Create.FailWorld"));
            return;
        }
        this.setArenaConfiguration(args[1], "World", args[2]);
        sender.sendMessage(Message.getPrefix() + Message.getString("Create.Success").replace("%arena_name%", args[1]));
    }
    
    public void setName(final CommandSender sender, final String[] args) {
        if (args.length == 1 || !ArenaData.badata.containsKey(args[1])) {
            sender.sendMessage(Message.getPrefix() + Message.getString("SetName.FailArena"));
            return;
        }
        if (args.length == 2) {
            sender.sendMessage(Message.getPrefix() + Message.getString("SetName.FailArg"));
            return;
        }
        this.setArenaConfiguration(args[1], "ArenaName", args[2]);
        sender.sendMessage(Message.getPrefix() + Message.getString("SetName.Success"));
    }
    
    public void setArenaConfiguration(final String arena, final String key, final Object value) {
        final File f = new File(Main.getInstance().getDataFolder() + "\\Arena\\", arena + ".yml");
        try {
            f.createNewFile();
            final FileConfiguration data = (FileConfiguration)YamlConfiguration.loadConfiguration(f);
            data.set(key, value);
            data.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void List(final CommandSender sender) {
        sender.sendMessage("¡ìe=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        if (ArenaData.badata.size() == 0) {
            sender.sendMessage(Message.getString("List.Fail"));
        }
        else {
            for (final String key : ArenaData.badata.keySet()) {
                final BattleArena ba = ArenaData.badata.get(key);
                sender.sendMessage(Message.getString("List.Format").replace("%arena_name%", ba.getArenaName()).replace("%arena_state%", ba.getSignState()).replace("%min%", ba.getMin() + "").replace("%max%", ba.getMax() + "").replace("%players%", ba.getPlayerAmount() + ""));
            }
        }
        sender.sendMessage("¡ìe=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }
    
    public void JoinGame(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (args.length == 1) {
                sender.sendMessage(Message.getPrefix() + Message.getString("Join.FailArg"));
                return;
            }
            if (!ArenaData.badata.containsKey(args[1]) || !ArenaData.badata.get(args[1]).canJoin()) {
                sender.sendMessage(Message.getPrefix() + Message.getString("Join.FailArena"));
                return;
            }
            if (PlayerData.PlayerInGame(p)) {
                sender.sendMessage(Message.getPrefix() + Message.getString("Join.InGame"));
                return;
            }
            ArenaData.badata.get(args[1]).PlayerJoin(p);
        }
        else {
            sender.sendMessage(Message.getPrefix() + Message.getString("FailSender"));
        }
    }
    
    public void setItem(final CommandSender sender, final String[] args) {
        if (args.length == 1 || !ArenaData.badata.containsKey(args[1])) {
            sender.sendMessage(Message.getPrefix() + Message.getString("SetItem.FailArena"));
            return;
        }
        if (args.length == 2) {
            sender.sendMessage(Message.getPrefix() + Message.getString("SetItem.FailArg"));
            return;
        }
        if (args[2].equalsIgnoreCase("D")) {
            ((Player)sender).openInventory(ArenaData.badata.get(args[1]).getAirDropInventory());
        }
        else if (args[2].equalsIgnoreCase("C")) {
            ((Player)sender).openInventory(ArenaData.badata.get(args[1]).getChestInventory());
        }
        else {
            sender.sendMessage(Message.getPrefix() + Message.getString("SetItem.FailArg"));
        }
        ((Player)sender).updateInventory();
    }
    
    public void sendCommandHelp(final CommandSender sender) {
        sender.sendMessage("¡ìe=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        sender.sendMessage("¡ìa/battle join <arena> ¡ìf\u52a0\u5165\u4e00\u4e2a\u7ade\u6280\u573a");
        sender.sendMessage("¡ìa/battle leave ¡ìf\u79bb\u5f00\u5f53\u524d\u7ade\u6280\u573a");
        if (sender.hasPermission("battle.admin")) {
            sender.sendMessage("¡ìa/battle create <arena> <world> ¡ìf\u521b\u5efa\u7ade\u6280\u573a");
            sender.sendMessage("¡ìa/battle name <arena> <arenaname> ¡ìf\u8bbe\u7f6e\u7ade\u6280\u573a\u522b\u540d");
            sender.sendMessage("¡ìa/battle border <arena> <size> ¡ìf\u8bbe\u7f6e\u8fb9\u754c\u5927\u5c0f");
            sender.sendMessage("¡ìa/battle setitem <arena> <D/C> ¡ìf\u8bbe\u7f6e\u7a7a\u6295/\u7bb1\u5b50\u7269\u54c1");
            sender.sendMessage("¡ìa/battle setlobby <arena> ¡ìf\u8bbe\u7f6e\u7b49\u5f85\u5927\u5385");
            sender.sendMessage("¡ìa/battle setend <arena> ¡ìf\u8bbe\u7f6e\u6e38\u620f\u7ed3\u675f\u70b9");
            sender.sendMessage("¡ìa/battle min <arena> <amount> ¡ìf\u8bbe\u7f6e\u6700\u5c11\u4eba\u6570");
            sender.sendMessage("¡ìa/battle max <arena> <amount> ¡ìf\u8bbe\u7f6e\u6700\u5927\u4eba\u6570");
            sender.sendMessage("¡ìa/battle list ¡ìf\u67e5\u770b\u7ade\u6280\u573a\u5217\u8868\u53ca\u72b6\u6001");
            sender.sendMessage("¡ìa/battle delete <arena> ¡ìf\u5220\u9664\u7ade\u6280\u573a");
            sender.sendMessage("¡ìa/battle reload ¡ìf\u91cd\u8f7d\u63d2\u4ef6\u914d\u7f6e");
            sender.sendMessage("¡ìa/battle restart ¡ìf\u91cd\u8f7d\u5168\u90e8\u7ade\u6280\u573a ¡ìc¡ìl\u614e\u7528\uff01");
        }
        sender.sendMessage("¡ìbVersion: ¡ìf" + Main.getInstance().getDescription().getVersion() + " ¡ìbAuthor: ¡ìfShawhoi");
        sender.sendMessage("¡ìe=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }
}
