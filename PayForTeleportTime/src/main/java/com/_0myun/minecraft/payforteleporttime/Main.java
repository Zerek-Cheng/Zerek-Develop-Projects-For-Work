package com._0myun.minecraft.payforteleporttime;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Level;

public final class Main extends JavaPlugin implements Listener {
    public static Main plugin;
    public static Economy economy = null;

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        setupEconomy();
        Bukkit.getPluginManager().registerEvents(this, this);
        new PlayerChecker().runTaskTimer(this, 20, 20);
        getLogger().log(Level.WARNING,"灵梦云科技定制插件+q2025255093");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("reloadok");
        return true;
    }

    @EventHandler
    public void onClickRight(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = e.getClickedBlock();
        if (block == null || block.getType().equals(Material.AIR)) return;
        if (!block.getType().equals(Material.SIGN) && !block.getType().equals(Material.SIGN_POST) && !block.getType().equals(Material.WALL_SIGN))
            return;
        if (!(block.getState() instanceof Sign)) return;
       /* [付费传送]
        1000金币
        1000,60,-500,世界名
        60
        */
        try {
            Sign sign = (Sign) block.getState();
            if (!sign.getLine(0).contains("[付费传送]")) return;
            int price = Integer.valueOf(sign.getLine(1).replace("金币", ""));
            String[] locationNums = sign.getLine(2).split(",");
            Location toLoc = new Location(Bukkit.getWorld(locationNums[3]),
                    Integer.valueOf(locationNums[0]),
                    Integer.valueOf(locationNums[1]),
                    Integer.valueOf(locationNums[2]));
            int time = Integer.valueOf(sign.getLine(3));
            if (!economy.has(p, price)) {
                p.sendMessage(getConfig().getString("lang1"));
                return;
            }
            economy.withdrawPlayer(p, price);
            p.teleport(toLoc);
            this.setTimeOut(p.getUniqueId(), time);
            p.sendMessage(getConfig().getString("lang3"));
        } catch (Exception ex) {
            p.sendMessage("数据异常...无法传送,报错已经发送到后台");
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (getTimeOut(p.getUniqueId()) > 0) {
            p.sendMessage(getConfig().getString("lang4"));
        }
        this.delTimeOut(p.getUniqueId());
    }

    public void setTimeOut(UUID p, long time) {
        getConfig().set("data." + p.toString(), System.currentTimeMillis() + (time * 1000l));
    }

    public long getTimeOut(UUID p) {
        return getConfig().getLong("data." + p.toString());
    }

    public void delTimeOut(UUID p) {
        getConfig().set("data." + p.toString(), null);
    }

    public Location getDefaultLocation() {
        return new Location(Bukkit.getWorld(getConfig().getString("default.world")),
                getConfig().getInt("default.x"),
                getConfig().getInt("default.y"),
                getConfig().getInt("default.z"));
    }
}
