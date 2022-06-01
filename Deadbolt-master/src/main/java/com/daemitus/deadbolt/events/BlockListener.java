package com.daemitus.deadbolt.events;

import com.daemitus.deadbolt.Deadbolt;
import com.daemitus.deadbolt.DeadboltPlugin;
import com.daemitus.deadbolt.Deadbolted;
import com.daemitus.deadbolt.Perm;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Iterator;

public class BlockListener implements Listener {

    private final DeadboltPlugin plugin = Deadbolt.getPlugin();

    public BlockListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Deadbolted db = Deadbolt.get(block);
        if (db.isProtected() && !db.isAutoExpired() && !db.isOwner(player)) {
            if (player.hasPermission(Perm.admin_break)) {
                Deadbolt.getConfig().sendBroadcast(Perm.admin_broadcast_break, ChatColor.RED, Deadbolt.getLanguage().msg_admin_break, player.getName(), db.getOwner());
            } else {
                Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_deny_block_break);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        Block against = event.getBlockAgainst();

        if (against.getType().equals(Material.WALL_SIGN) && Deadbolt.getLanguage().isValidWallSign((Sign) against.getState())) {
            event.setCancelled(true);
            return;
        }
        if (event.getBlock().getType() == Material.HOPPER) {
            Deadbolted d = Deadbolt.get(event.getBlock().getRelative(BlockFace.UP));
            if (d.isProtected() && !d.isOwner(player)) {
                Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_hopper);
                event.setCancelled(true);
            }
            return;
        }
        if (event.getBlock().getType() == Material.TRAPPED_CHEST) {
            Deadbolted UP = Deadbolt.get(event.getBlock().getRelative(BlockFace.UP));
            Deadbolted DOWN = Deadbolt.get(event.getBlock().getRelative(BlockFace.DOWN));

            Deadbolted NORTH = Deadbolt.get(event.getBlock().getRelative(BlockFace.NORTH));
            Deadbolted EAST = Deadbolt.get(event.getBlock().getRelative(BlockFace.EAST));
            Deadbolted SOUTH = Deadbolt.get(event.getBlock().getRelative(BlockFace.SOUTH));
            Deadbolted WEST = Deadbolt.get(event.getBlock().getRelative(BlockFace.WEST));
            if (UP.isProtected()) {
                Iterator<Block> iter = UP.getBlocks().iterator();
                if (!iter.hasNext()) return;
                if (iter.next().getType().equals(Material.CHEST) || iter.next().getType().equals(Material.TRAPPED_CHEST)) {
                    Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_hopper);
                    event.setCancelled(true);
                }
            }
            if (DOWN.isProtected()) {Iterator<Block> iter = UP.getBlocks().iterator();
                if (!iter.hasNext()) return;
                if (iter.next().getType().equals(Material.CHEST) || iter.next().getType().equals(Material.TRAPPED_CHEST)) {
                    Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_hopper);
                    event.setCancelled(true);
                }
            }
            if (NORTH.isProtected()) {Iterator<Block> iter = UP.getBlocks().iterator();
                if (!iter.hasNext()) return;
                if (iter.next().getType().equals(Material.CHEST) || iter.next().getType().equals(Material.TRAPPED_CHEST)) {
                    Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_hopper);
                    event.setCancelled(true);
                }
            }
            if (EAST.isProtected()) {Iterator<Block> iter = UP.getBlocks().iterator();
                if (!iter.hasNext()) return;
                if (iter.next().getType().equals(Material.CHEST) || iter.next().getType().equals(Material.TRAPPED_CHEST)) {
                    Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_hopper);
                    event.setCancelled(true);
                }
            }
            if (SOUTH.isProtected()) {Iterator<Block> iter = UP.getBlocks().iterator();
                if (!iter.hasNext()) return;
                if (iter.next().getType().equals(Material.CHEST) || iter.next().getType().equals(Material.TRAPPED_CHEST)) {
                    Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_hopper);
                    event.setCancelled(true);
                }
            }
            if (WEST.isProtected()) {Iterator<Block> iter = UP.getBlocks().iterator();
                if (!iter.hasNext()) return;
                if (iter.next().getType().equals(Material.CHEST) || iter.next().getType().equals(Material.TRAPPED_CHEST)) {
                    Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_hopper);
                    event.setCancelled(true);
                }
            }
            return;
        }
        Deadbolted db = Deadbolt.get(block);
        switch (block.getType()) {
            case CHEST:
            case FURNACE:
            case CAULDRON:
            case DISPENSER:
            case BREWING_STAND:
            case BURNING_FURNACE:
            case ENCHANTMENT_TABLE:
            case ANVIL:
            case ENDER_CHEST:
            case BEACON:
            case DROPPER:
            case TRAPPED_CHEST:
            case HOPPER:
                if (player.hasPermission(getPermission(block.getType())) && Deadbolt.getConfig().reminder.add(player)) {
                    //   Deadbolt.getConfig().sendMessage(player, ChatColor.GOLD, Deadbolt.getLanguage().msg_reminder_lock_your_chests);
                }
                if (db.isProtected() && !db.isOwner(player)) {
                    event.setCancelled(true);
                    //    Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_deny_container_expansion);
                }
                return;
            case IRON_DOOR_BLOCK:
            case WOODEN_DOOR:
            case SPRUCE_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR:
                if (player.hasPermission(getPermission(block.getType())) && Deadbolt.getConfig().reminder.add(player)) {
                    // Deadbolt.getConfig().sendMessage(player, ChatColor.GOLD, Deadbolt.getLanguage().msg_reminder_lock_your_chests);
                }
                if (db.isProtected() && !db.isOwner(player)) {
                    //    Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_deny_door_expansion);
                    Block upBlock = block.getRelative(BlockFace.UP);
                    block.setType(Material.STONE);
                    block.setType(Material.AIR);
                    upBlock.setType(Material.STONE);
                    upBlock.setType(Material.AIR);
                    event.setCancelled(true);
                }
                return;
            case TRAP_DOOR:
            case IRON_TRAPDOOR:
                if (player.hasPermission(getPermission(block.getType())) && Deadbolt.getConfig().reminder.add(player)) {
                    // Deadbolt.getConfig().sendMessage(player, ChatColor.GOLD, Deadbolt.getLanguage().msg_reminder_lock_your_chests);
                }
                if (db.isProtected() && !db.isOwner(player)) {
                    // Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_deny_trapdoor_expansion);
                    event.setCancelled(true);
                }
                return;
            case FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
                if (player.hasPermission(getPermission(block.getType())) && Deadbolt.getConfig().reminder.add(player)) {
                    //Deadbolt.getConfig().sendMessage(player, ChatColor.GOLD, Deadbolt.getLanguage().msg_reminder_lock_your_chests);
                }
                if (db.isProtected() && !db.isOwner(player)) {
                    //Deadbolt.getConfig().sendMessage(player, ChatColor.RED, Deadbolt.getLanguage().msg_deny_fencegate_expansion);
                    event.setCancelled(true);
                }
                return;
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent event) {
        Block block = event.getBlock();
        Deadbolted db = Deadbolt.get(block);
        if (db.isProtected()) {
            event.setCancelled(true);
        }
    }

    private String getPermission(Material type) {
        switch (type) {
            case CHEST:
                return Perm.user_create_chest;
            case FURNACE:
            case BURNING_FURNACE:
                return Perm.user_create_furnace;
            case CAULDRON:
                return Perm.user_create_cauldron;
            case DISPENSER:
                return Perm.user_create_dispenser;
            case BREWING_STAND:
                return Perm.user_create_brewery;
            case ENCHANTMENT_TABLE:
                return Perm.user_create_enchant;
            case WOODEN_DOOR:
            case IRON_DOOR_BLOCK:
            case SPRUCE_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR:
                return Perm.user_create_door;
            case TRAP_DOOR:
            case IRON_TRAPDOOR:
                return Perm.user_create_trapdoor;
            case FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
                return Perm.user_create_fencegate;
            case ANVIL:
                return Perm.user_create_anvil;
            case ENDER_CHEST:
                return Perm.user_create_ender;
            case BEACON:
                return Perm.user_create_beacon;
            case HOPPER:
                return Perm.user_create_hopper;
            case DROPPER:
                return Perm.user_create_dropper;
            case TRAPPED_CHEST:
                return Perm.user_create_trapped_chest;
            default:
                return null;
        }
    }
}
