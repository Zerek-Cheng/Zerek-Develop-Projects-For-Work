package com._0myun.minecraft.eventmsg.chargerespawn;

import com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnConfig;
import com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnData;
import com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnPointConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Config {

    public static RespawnConfig getRespawnConfig() {
        return (RespawnConfig) Main.getPlugin().getConfig().get("respawn");
    }

    public static RespawnPointConfig getRespawnPointConfig() {
        return getRespawnConfig().getPointConfig();
    }

    public static String getCoinLore() {
        return Main.getPlugin().getConfig().getString("coin.lore");
    }

    public static FileConfiguration getData() {
        return Main.getPlugin().getData();
    }

    public static void setPlayerData(Player p, RespawnData data) {
        getData().set(p.getUniqueId().toString(), data);
    }

    public static RespawnData getPlayerData(Player p) {
        RespawnData data = (RespawnData) getData().get(p.getUniqueId().toString());
        data = data == null ? new RespawnData() : data;
        return data;
    }

}
