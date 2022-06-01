package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class VexRedEnvelope extends JavaPlugin {
    public static VexRedEnvelope plugin;
    public static PlayerPointsAPI points;
    public static Economy economy;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        VexRedEnvelope.plugin = this;
        VexRedEnvelope.points = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();
        VexRedEnvelope.economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();

        new Loader().run();
        if (!this.isEnabled()) return;
        new Souter().runTaskLater(this, 24 * 10);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        if (args.length >= 4 && args[0].equalsIgnoreCase("send")) {//c send word type amount <total>
            sender.sendMessage("傻逼盗版用户你妈死了");
            return true;
        }
        sender.sendMessage(LangUtil.get("lang1"));
        return true;
    }
}
