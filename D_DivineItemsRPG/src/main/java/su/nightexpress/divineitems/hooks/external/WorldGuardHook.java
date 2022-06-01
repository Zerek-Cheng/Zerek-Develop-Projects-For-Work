/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  com.sk89q.worldguard.protection.ApplicableRegionSet
 *  com.sk89q.worldguard.protection.association.RegionAssociable
 *  com.sk89q.worldguard.protection.flags.DefaultFlag
 *  com.sk89q.worldguard.protection.flags.StateFlag
 *  com.sk89q.worldguard.protection.flags.StateFlag$State
 *  com.sk89q.worldguard.protection.managers.RegionManager
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package su.nightexpress.divineitems.hooks.external;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import su.nightexpress.divineitems.DivineItems;

public class WorldGuardHook {
    private DivineItems plugin;

    public WorldGuardHook(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    public boolean canFights(Entity entity, Entity entity2) {
        if (!(entity instanceof Player) || !(entity2 instanceof Player)) {
            return true;
        }
        WorldGuardPlugin worldGuardPlugin = (WorldGuardPlugin)this.plugin.getPluginManager().getPlugin("WorldGuard");
        RegionManager regionManager = worldGuardPlugin.getRegionManager(entity.getLocation().getWorld());
        if (regionManager == null) {
            return true;
        }
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions((Vector)this.convertToSk89qBV(entity.getLocation()));
        ApplicableRegionSet applicableRegionSet2 = regionManager.getApplicableRegions((Vector)this.convertToSk89qBV(entity2.getLocation()));
        if (!(applicableRegionSet == null && applicableRegionSet2 == null || applicableRegionSet.queryState(null, new StateFlag[]{DefaultFlag.PVP}) != StateFlag.State.DENY && applicableRegionSet2.queryState(null, new StateFlag[]{DefaultFlag.PVP}) != StateFlag.State.DENY)) {
            return false;
        }
        return true;
    }

    private BlockVector convertToSk89qBV(Location location) {
        return new BlockVector(location.getX(), location.getY(), location.getZ());
    }

    public boolean canBuilds(Player player) {
        WorldGuardPlugin worldGuardPlugin = (WorldGuardPlugin)this.plugin.getPluginManager().getPlugin("WorldGuard");
        return worldGuardPlugin.canBuild(player, player.getLocation().getBlock().getRelative(0, -1, 0));
    }

    public boolean isInRegion(LivingEntity livingEntity, String string) {
        return this.getRegion(livingEntity).equalsIgnoreCase(string);
    }

    public String getRegion(LivingEntity livingEntity) {
        WorldGuardPlugin worldGuardPlugin = (WorldGuardPlugin)this.plugin.getPluginManager().getPlugin("WorldGuard");
        RegionManager regionManager = worldGuardPlugin.getRegionManager(livingEntity.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(livingEntity.getLocation());
        String string = "";
        int n = -1;
        for (ProtectedRegion protectedRegion : applicableRegionSet) {
            if (protectedRegion.getPriority() <= n) continue;
            n = protectedRegion.getPriority();
            string = protectedRegion.getId();
        }
        return string;
    }
}

