package com._0myun.minecraft.eventmsg.chargerespawn;

import com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnConfig;
import com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnData;
import com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnPointConfig;
import lombok.Getter;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {
    @Getter
    public static Main plugin;
    public FileConfiguration data = new YamlConfiguration();
    public static PlayerPointsAPI points;

    @Override
    public void onEnable() {
        Main.plugin = this;
        Main.points = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();

        ConfigurationSerialization.registerClass(RespawnConfig.class, "com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnConfig");
        ConfigurationSerialization.registerClass(RespawnPointConfig.class, "com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnPointConfig");
        ConfigurationSerialization.registerClass(RespawnData.class, "com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnData");

        saveDefaultConfig();
        saveResource("data.yml", false);
        try {
            this.data.load(new File(this.getDataFolder() + "/data.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        new FreeRespawnChecker().runTaskTimer(this, 20, 20);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    public FileConfiguration getData() {
        return this.data;
    }

    @Override
    public void saveConfig() {
        super.saveConfig();
        try {
            this.data.save(new File(this.getDataFolder() + "/data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LangUtils.get("lang4"));
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("coin")) {//复活币复活
            this.respawnByCoin(p);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("point")) {//复活币复活
            this.respawnByPoint(p);
            return true;
        }
        return true;
    }

    public void respawnByCoin(Player p) {
        PlayerInventory inv = p.getInventory();
        int coinIndex = RespawnManager.searchCoin(inv);
        if (!RespawnManager.isDeath(p)) {
            p.sendMessage(LangUtils.get("lang8", p));
            return;
        }
        if (coinIndex == -1) {//没币
            p.sendMessage(LangUtils.get("lang5", p));
            return;
        }
        if (!RespawnManager.canCoinRespawn(p)) {//上限
            p.sendMessage(LangUtils.get("lang7", p));
            return;
        }
        RespawnManager.takeCoin(inv);
        RespawnManager.coinRespawn(p);
        p.sendMessage(LangUtils.get("lang6", p));
    }

    public void respawnByPoint(Player p) {
        if (!RespawnManager.isDeath(p)) {
            p.sendMessage(LangUtils.get("lang8", p));
            return;
        }
        if (!RespawnManager.canPointRespawn(p)) {
            p.sendMessage(LangUtils.get("lang9", p));
            return;
        }
        RespawnManager.takePoint(p);
        RespawnManager.pointRespawn(p);
        p.sendMessage(LangUtils.get("lang10", p));
    }
}
