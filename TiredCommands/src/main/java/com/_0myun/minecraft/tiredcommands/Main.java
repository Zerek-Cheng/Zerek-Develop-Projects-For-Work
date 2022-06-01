package com._0myun.minecraft.tiredcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        new PlayerChecker().runTaskTimerAsynchronously(this, 20, 20);
        new souter().runTaskLater(this, 1);
        new Saver().runTaskTimer(this, 20 * 10, 20 * 10);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            this.reloadConfig();
            sender.sendMessage(LangManager.get("lang2"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            showInfo((Player) sender);
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("run")) {
            String name = args[1];
            if (CommandsManager.getCommandConfig(name) == null) {
                sender.sendMessage(LangManager.get("lang7"));
                return true;
            }
            String commandStr = CommandsManager.getCommand(name);
            String cost = CommandsManager.getCommandCost(name);

            Player p = (Player) sender;
            if (PhysicalManager.getPhysical(p.getUniqueId()) < Integer.parseInt(cost)) {
                p.sendMessage(LangManager.get("lang4"));
                return true;
            }
            boolean op = p.isOp();
            try {
                if (!op) p.setOp(true);
                if (!op) PhysicalManager.takePhysical(p.getUniqueId(), Integer.parseInt(cost));
                p.performCommand(commandStr.replace("<player>", p.getName()));
            } finally {
                if (!op) p.setOp(false);
            }
            p.sendMessage(LangManager.get("lang3"));
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("get") && sender.isOp()) {
            String name = args[1];
            if (PotionManager.getPotionConfig(name) == null) {
                sender.sendMessage(LangManager.get("lang7"));
                return true;
            }
            ItemStack item = PotionManager.getPotionItem(name);
            Player p = (Player) sender;
            p.getInventory().addItem(item);
            p.updateInventory();
            p.sendMessage(LangManager.get("lang8"));
            return true;
        }
        sender.sendMessage(LangManager.get("lang1"));
        return true;
    }


    public void showInfo(Player p) {
        int physical = PhysicalManager.getPhysical(p.getUniqueId());
        p.sendMessage(String.format(LangManager.get("lang5"), String.valueOf(physical), String.valueOf(PhysicalManager.getMax())));
    }
}
