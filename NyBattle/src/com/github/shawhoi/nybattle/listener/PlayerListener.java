// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.block.Chest;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryCloseEvent;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Sign;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import com.github.shawhoi.nybattle.config.Message;
import com.github.shawhoi.nybattle.playerdata.PlayerData;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.EventHandler;
import com.github.shawhoi.nybattle.game.BattleArena;
import com.github.shawhoi.nybattle.game.ArenaData;
import com.github.shawhoi.nybattle.Main;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.ArrayList;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener
{
    private ArrayList<String> playername;
    
    public PlayerListener() {
        this.playername = new ArrayList<String>();
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        if (Main.getInstance().isBungee()) {
            e.setJoinMessage((String)null);
            if (ArenaData.badata.values().size() > 0) {
                final Object[] ks = ArenaData.badata.keySet().toArray();
                final BattleArena ba = ArenaData.badata.get(String.valueOf(ks[0]));
                if (ba.canJoin()) {
                    ba.PlayerJoin(e.getPlayer());
                }
                else {
                    e.getPlayer().kickPlayer("��cGame Start");
                }
            }
        }
    }
    
    @EventHandler
    public void onSignChange(final SignChangeEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer()) || !e.getPlayer().hasPermission("battle.admin")) {
            return;
        }
        if (e.getLine(0).equals("[nybattle]")) {
            if (ArenaData.badata.containsKey(e.getLine(1))) {
                final String arena = e.getLine(1);
                ArenaData.badata.get(arena).setSignLocation(e.getBlock().getLocation());
                ArenaData.badata.get(arena).upSign();
                e.getPlayer().sendMessage(Message.getPrefix() + Message.getString("CreateSign.Success"));
            }
            else {
                e.getPlayer().sendMessage(Message.getPrefix() + Message.getString("CreateSign.Fail"));
            }
        }
    }
    
    @EventHandler
    public void onPlayerDropItem(final PlayerDropItemEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer()) && !PlayerData.getPlayerGameArena(e.getPlayer()).getArenaState().equals("START")) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerToggleSneak(final PlayerToggleSneakEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer()) && PlayerData.getPlayerGameArena(e.getPlayer()).getArenaState().equals("START")) {
            PlayerData.getPlayerGameArena(e.getPlayer()).PlayerJump(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer())) {
            PlayerData.setPlayerGameData(e.getPlayer());
            PlayerData.getPlayerGameArena(e.getPlayer()).PlayerLeave(e.getPlayer(), true);
        }
    }
    
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (PlayerData.PlayerInGame(e.getPlayer())) {
                final ItemStack item = e.getPlayer().getInventory().getItemInHand();
                if (item != null && item.getType() == Material.PAPER && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(Message.getString("Help.ItemName")) && !this.playername.contains(e.getPlayer().getName())) {
                    for (final String mes : Message.getStringList("Help.Message")) {
                        e.getPlayer().sendMessage(mes);
                    }
                    this.playername.add(e.getPlayer().getName());
                    new BukkitRunnable() {
                        public void run() {
                            if (e.getPlayer() != null && e.getPlayer().isOnline()) {
                                PlayerListener.this.playername.remove(e.getPlayer().getName());
                            }
                        }
                    }.runTaskLaterAsynchronously((Plugin)Main.getInstance(), 100L);
                }
            }
            else if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getState() instanceof Sign) {
                final String[] re = this.isGameSign(e.getClickedBlock().getLocation()).split(":");
                if (Boolean.parseBoolean(re[0])) {
                    if (!ArenaData.badata.get(re[1]).canJoin()) {
                        e.getPlayer().sendMessage(Message.getPrefix() + Message.getString("Join.FailArena"));
                        return;
                    }
                    ArenaData.badata.get(re[1]).PlayerJoin(e.getPlayer());
                }
            }
        }
    }
    
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (e.getInventory().getTitle().startsWith("C: ")) {
            final String sp = e.getInventory().getTitle().split(": ")[1];
            if (ArenaData.badata.containsKey(sp)) {
                ArenaData.badata.get(sp).setChestInventory(e.getInventory());
                ((Player)e.getPlayer()).sendMessage(Message.getPrefix() + Message.getString("SetItem.Success"));
            }
        }
        else if (e.getInventory().getTitle().startsWith("D: ")) {
            final String sp = e.getInventory().getTitle().split(": ")[1];
            if (ArenaData.badata.containsKey(sp)) {
                ArenaData.badata.get(sp).setAirDropInventory(e.getInventory());
                ((Player)e.getPlayer()).sendMessage(Message.getPrefix() + Message.getString("SetItem.Success"));
            }
        }
    }
    
    public String isGameSign(final Location loc) {
        String is = "false";
        String is2 = "";
        for (final BattleArena ba : ArenaData.badata.values()) {
            if (ba.getSignLocation().distance(loc) == 0.0) {
                is = "true";
                is2 = ba.getArenaFileName();
                break;
            }
        }
        return is + ":" + is2;
    }
    
    @EventHandler
    public void onPlayerInteractChest(final PlayerInteractEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer()) && e.getAction() == Action.RIGHT_CLICK_BLOCK && PlayerData.getPlayerGameArena(e.getPlayer()).getArenaState().equals("START") && e.getClickedBlock().getType() == Material.CHEST && !PlayerData.getPlayerGameArena(e.getPlayer()).isOpen(e.getClickedBlock().getLocation())) {
            if (!PlayerData.getPlayerGameArena(e.getPlayer()).isAirDropChest(e.getClickedBlock().getLocation())) {
                final Chest chest = (Chest)e.getClickedBlock().getState();
                final Inventory bi = chest.getBlockInventory();
                final int[] randomslot = { (int)(0.0 + Math.random() * (bi.getSize() - 0)), (int)(0.0 + Math.random() * (bi.getSize() - 0)), (int)(0.0 + Math.random() * (bi.getSize() - 0)) };
                final ArrayList<ItemStack> items = getItemList(PlayerData.getPlayerGameArena(e.getPlayer()).getChestInventory());
                if (items.size() > 0) {
                    chest.getBlockInventory().setItem(randomslot[0], (ItemStack)items.get((int)(0.0 + Math.random() * (items.size() - 0))));
                    chest.getBlockInventory().setItem(randomslot[1], (ItemStack)items.get((int)(0.0 + Math.random() * (items.size() - 0))));
                    chest.getBlockInventory().setItem(randomslot[2], (ItemStack)items.get((int)(0.0 + Math.random() * (items.size() - 0))));
                }
                PlayerData.getPlayerGameArena(e.getPlayer()).addOpenChest(e.getClickedBlock().getLocation());
            }
            else if (!PlayerData.getPlayerGameArena(e.getPlayer()).AirDropIsOpen(e.getClickedBlock().getLocation())) {
                final Chest chest = (Chest)e.getClickedBlock().getState();
                final Inventory bi = chest.getBlockInventory();
                final int[] randomslot = { (int)(0.0 + Math.random() * (bi.getSize() - 0)), (int)(0.0 + Math.random() * (bi.getSize() - 0)), (int)(0.0 + Math.random() * (bi.getSize() - 0)) };
                final ArrayList<ItemStack> items = getItemList(PlayerData.getPlayerGameArena(e.getPlayer()).getAirDropInventory());
                if (items.size() > 0) {
                    chest.getBlockInventory().setItem(randomslot[0], (ItemStack)items.get((int)(0.0 + Math.random() * (items.size() - 0))));
                    chest.getBlockInventory().setItem(randomslot[1], (ItemStack)items.get((int)(0.0 + Math.random() * (items.size() - 0))));
                    chest.getBlockInventory().setItem(randomslot[2], (ItemStack)items.get((int)(0.0 + Math.random() * (items.size() - 0))));
                }
                PlayerData.getPlayerGameArena(e.getPlayer()).addAriDopChest(e.getClickedBlock().getLocation());
            }
        }
    }
    
    public static ArrayList<ItemStack> getItemList(final Inventory inv) {
        final ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        for (int i = 0; i < inv.getSize(); ++i) {
            if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR) {
                items.add(inv.getItem(i));
            }
        }
        return items;
    }
    
    @EventHandler
    public void onServerMotd(final ServerListPingEvent e) {
        if (Main.getInstance().isBungee() && ArenaData.badata.values().size() > 0) {
            String motd = e.getMotd();
            final Object[] ks = ArenaData.badata.keySet().toArray();
            final String arenaState = ArenaData.badata.get(String.valueOf(ks[0])).getArenaState();
            switch (arenaState) {
                case "WAIT": {
                    motd = Main.getInstance().motd_wait;
                    break;
                }
                case "START": {
                    motd = Main.getInstance().motd_start;
                    break;
                }
                case "END": {
                    motd = Main.getInstance().motd_end;
                    break;
                }
                case "WSTART": {
                    motd = Main.getInstance().motd_wait;
                    break;
                }
            }
            e.setMotd(motd);
        }
    }
}
