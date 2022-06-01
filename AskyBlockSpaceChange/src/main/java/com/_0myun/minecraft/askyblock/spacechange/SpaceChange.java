package com._0myun.minecraft.askyblock.spacechange;

import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class SpaceChange extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logi
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ASkyBlockAPI api = ASkyBlockAPI.getInstance();
        Player p = (Player) sender;
        Location loc = p.getLocation();
        if (!p.hasPermission("SpaceChange.admin." + args[0])) {
            p.sendMessage("没有权限");
            return true;
        }
        if (!api.islandAtLocation(loc)) {
            p.sendMessage("所处区域不在空岛内");
            return true;
        }
        int add = new Integer(args[0]).intValue();
        Island land = api.getIslandAt(loc);
        UUID owner = land.getOwner();
        land = ASkyBlock.getPlugin().getGrid().getIsland(owner);

        p.sendMessage("原大小：" + land.getProtectionSize());
        land.setIslandDistance(add * 2);
        land.setProtectionSize(add);
        /*land.setIslandDistance(land.getIslandDistance() + (add * 2));
        land.setProtectionSize(land.getProtectionSize() + (add * 2));*/
        land.save();
        p.sendMessage("新大小：" + land.getProtectionSize());
        return true;
    }
}
