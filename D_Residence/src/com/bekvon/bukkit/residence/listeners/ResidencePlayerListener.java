/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.WeatherType
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Sign
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Boat
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Hanging
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Player$Spigot
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.SignChangeEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerLoginEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerShearEntityEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package com.bekvon.bukkit.residence.listeners;

import cmiLib.ActionBarTitleMessages;
import cmiLib.ItemManager;
import cmiLib.VersionChecker;
import com.bekvon.bukkit.residence.ConfigManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.chat.ChatChannel;
import com.bekvon.bukkit.residence.chat.ChatManager;
import com.bekvon.bukkit.residence.containers.AutoSelector;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.NMS;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.containers.StuckInfo;
import com.bekvon.bukkit.residence.containers.Visualizer;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.economy.TransactionManager;
import com.bekvon.bukkit.residence.economy.rent.RentManager;
import com.bekvon.bukkit.residence.economy.rent.RentableLand;
import com.bekvon.bukkit.residence.economy.rent.RentedLand;
import com.bekvon.bukkit.residence.event.ResidenceChangedEvent;
import com.bekvon.bukkit.residence.event.ResidenceDeleteEvent;
import com.bekvon.bukkit.residence.event.ResidenceFlagChangeEvent;
import com.bekvon.bukkit.residence.event.ResidenceRenameEvent;
import com.bekvon.bukkit.residence.gui.SetFlag;
import com.bekvon.bukkit.residence.itemlist.WorldItemManager;
import com.bekvon.bukkit.residence.listeners.ResidenceBlockListener;
import com.bekvon.bukkit.residence.listeners.ResidenceEntityListener;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.permissions.PermissionManager;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.PlayerManager;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.bekvon.bukkit.residence.selection.AutoSelection;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import com.bekvon.bukkit.residence.selection.WESchematicManager;
import com.bekvon.bukkit.residence.signsStuff.SignInfo;
import com.bekvon.bukkit.residence.signsStuff.SignUtil;
import com.bekvon.bukkit.residence.signsStuff.Signs;
import com.bekvon.bukkit.residence.utils.GetTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class ResidencePlayerListener
        implements Listener {
    protected Map<UUID, ClaimedResidence> currentRes = new HashMap<UUID, ClaimedResidence>();
    protected Map<UUID, Long> lastUpdate = new HashMap<UUID, Long>();
    protected Map<UUID, Location> lastOutsideLoc = new HashMap<UUID, Location>();
    protected Map<UUID, StuckInfo> stuckTeleportCounter = new HashMap<UUID, StuckInfo>();
    protected int minUpdateTime;
    protected boolean chatenabled;
    protected Set<UUID> playerToggleChat = new HashSet<UUID>();
    public Map<UUID, SetFlag> GUI = new HashMap<UUID, SetFlag>();
    private Residence plugin;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState;
    private static /* synthetic */ int[] $SWITCH_TABLE$cmiLib$ItemManager$CMIMaterial;

    public ResidencePlayerListener(Residence plugin) {
        this.playerToggleChat.clear();
        this.minUpdateTime = plugin.getConfigManager().getMinMoveUpdateInterval();
        this.chatenabled = plugin.getConfigManager().chatEnabled();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.lastUpdate.put(player.getUniqueId(), System.currentTimeMillis());
        }
        this.plugin = plugin;
    }

    public Map<UUID, SetFlag> getGUImap() {
        return this.GUI;
    }

    public void reload() {
        this.currentRes = new HashMap<UUID, ClaimedResidence>();
        this.lastUpdate = new HashMap<UUID, Long>();
        this.lastOutsideLoc = new HashMap<UUID, Location>();
        this.stuckTeleportCounter = new HashMap<UUID, StuckInfo>();
        this.playerToggleChat.clear();
        this.minUpdateTime = this.plugin.getConfigManager().getMinMoveUpdateInterval();
        this.chatenabled = this.plugin.getConfigManager().chatEnabled();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.lastUpdate.put(player.getUniqueId(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        if (!Flags.jump3.isGlobalyEnabled() && !Flags.jump2.isGlobalyEnabled()) {
            return;
        }
        Player player = event.getPlayer();
        if (player.isFlying()) {
            return;
        }
        if (event.getTo().getY() - event.getFrom().getY() != 0.41999998688697815) {
            return;
        }
        if (player.hasMetadata("NPC")) {
            return;
        }
        FlagPermissions perms = this.plugin.getPermsByLoc(player.getLocation());
        if (Flags.jump2.isGlobalyEnabled() && perms.has(Flags.jump2, FlagPermissions.FlagCombo.OnlyTrue)) {
            player.setVelocity(player.getVelocity().add(player.getVelocity().multiply(0.3)));
        } else if (Flags.jump3.isGlobalyEnabled() && perms.has(Flags.jump3, FlagPermissions.FlagCombo.OnlyTrue)) {
            player.setVelocity(player.getVelocity().add(player.getVelocity().multiply(0.6)));
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        if (!Flags.itempickup.isGlobalyEnabled()) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(event.getItem().getLocation());
        if (res == null) {
            return;
        }
        if (event.getPlayer().hasMetadata("NPC")) {
            return;
        }
        if (!res.getPermissions().playerHas(event.getPlayer(), Flags.itempickup, FlagPermissions.FlagCombo.OnlyFalse)) {
            return;
        }
        if (event.getPlayer().hasPermission("residence.flag.itempickup.bypass")) {
            return;
        }
        event.setCancelled(true);
        event.getItem().setPickupDelay(this.plugin.getConfigManager().getItemPickUpDelay() * 20);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        if (!Flags.itemdrop.isGlobalyEnabled()) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(event.getPlayer().getLocation());
        if (res == null) {
            return;
        }
        if (event.getPlayer().hasMetadata("NPC")) {
            return;
        }
        if (!res.getPermissions().playerHas(event.getPlayer(), Flags.itemdrop, FlagPermissions.FlagCombo.OnlyFalse)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerGlobalChat(AsyncPlayerChatEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (!this.plugin.getConfigManager().isGlobalChatEnabled()) {
            return;
        }
        if (!this.plugin.getConfigManager().isGlobalChatSelfModify()) {
            return;
        }
        Player player = event.getPlayer();
        ResidencePlayer rPlayer = this.plugin.getPlayerManager().getResidencePlayer(player);
        if (rPlayer == null) {
            return;
        }
        if (rPlayer.getResList().size() == 0) {
            return;
        }
        ClaimedResidence res = rPlayer.getMainResidence();
        if (res == null) {
            return;
        }
        String honorific = this.plugin.getConfigManager().getGlobalChatFormat().replace("%1", res.getTopParentName());
        String format = event.getFormat();
        format = format.replace("%1$s", String.valueOf(honorific) + "%1$s");
        event.setFormat(format);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerChatGlobalLow(AsyncPlayerChatEvent event) {
        String format;
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (!this.plugin.getConfigManager().isGlobalChatEnabled()) {
            return;
        }
        if (this.plugin.getConfigManager().isGlobalChatSelfModify()) {
            return;
        }
        Player player = event.getPlayer();
        ResidencePlayer rPlayer = this.plugin.getPlayerManager().getResidencePlayer(player);
        if (rPlayer == null) {
            return;
        }
        if (rPlayer.getResList().size() == 0) {
            return;
        }
        ClaimedResidence res = rPlayer.getMainResidence();
        if (res == null) {
            return;
        }
        String honorific = this.plugin.getConfigManager().getGlobalChatFormat().replace("%1", res.getTopParentName());
        if (honorific.equalsIgnoreCase(" ")) {
            honorific = "";
        }
        if (!(format = event.getFormat()).contains("{residence}")) {
            return;
        }
        format = format.replace("{residence}", honorific);
        event.setFormat(format);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onResidenceBackup(ResidenceFlagChangeEvent event) {
        if (!event.getFlag().equalsIgnoreCase(Flags.backup.getName())) {
            return;
        }
        Player player = event.getPlayer();
        if (!this.plugin.getConfigManager().RestoreAfterRentEnds) {
            return;
        }
        if (!this.plugin.getConfigManager().SchematicsSaveOnFlagChange) {
            return;
        }
        if (this.plugin.getSchematicManager() == null) {
            return;
        }
        if (player != null && !player.hasPermission("residence.backup")) {
            event.setCancelled(true);
        } else {
            this.plugin.getSchematicManager().save(event.getResidence());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onResidenceBackupRename(ResidenceRenameEvent event) {
        if (this.plugin.getSchematicManager() == null) {
            return;
        }
        this.plugin.getSchematicManager().rename(event.getResidence(), event.getNewResidenceName());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onResidenceDelete(ResidenceDeleteEvent event) {
        if (this.plugin.getSchematicManager() == null) {
            return;
        }
        this.plugin.getSchematicManager().delete(event.getResidence());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerFirstLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            ResidenceBlockListener.newPlayers.add(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!this.plugin.getConfigManager().isRentInformOnEnding()) {
            return;
        }
        final Player player = event.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this.plugin, new Runnable() {

            @Override
            public void run() {
                if (!player.isOnline()) {
                    return;
                }
                List<String> list2 = ResidencePlayerListener.this.plugin.getRentManager().getRentedLandsList(player.getName());
                if (list2.isEmpty()) {
                    return;
                }
                for (String one : list2) {
                    RentedLand rentedland = ResidencePlayerListener.this.plugin.getRentManager().getRentedLand(one);
                    if (rentedland == null || rentedland.AutoPay || rentedland.endTime - System.currentTimeMillis() >= (long) (ResidencePlayerListener.this.plugin.getConfigManager().getRentInformBefore() * 60 * 24 * 7))
                        continue;
                    ResidencePlayerListener.this.plugin.msg((CommandSender) player, lm.Residence_EndingRent, one, GetTime.getTime(rentedland.endTime));
                }
            }
        }, (long) this.plugin.getConfigManager().getRentInformDelay() * 20L);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onFishingRodUse(PlayerFishEvent event) {
        if (!Flags.hook.isGlobalyEnabled()) {
            return;
        }
        if (event == null) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (event.getCaught() == null) {
            return;
        }
        if (this.plugin.getNms().isArmorStandEntity(event.getCaught().getType()) || event.getCaught() instanceof Boat || event.getCaught() instanceof LivingEntity) {
            FlagPermissions perm = this.plugin.getPermsByLoc(event.getCaught().getLocation());
            ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(event.getCaught().getLocation());
            if (perm.has(Flags.hook, FlagPermissions.FlagCombo.OnlyFalse)) {
                event.setCancelled(true);
                if (res != null) {
                    this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.hook.getName(), res.getName());
                }
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFlagChangeDayNight(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.day.getName()) && !event.getFlag().equalsIgnoreCase(Flags.night.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2:
            case 3: {
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.resetPlayerTime();
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                if (event.getFlag().equalsIgnoreCase(Flags.day.getName())) {
                    for (Player one : event.getResidence().getPlayersInResidence()) {
                        one.setPlayerTime(6000L, false);
                    }
                }
                if (!event.getFlag().equalsIgnoreCase(Flags.night.getName())) break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setPlayerTime(14000L, false);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFlagChangeGlow(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.glow.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2:
            case 3: {
                if (!VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_9_R1) || !event.getFlag().equalsIgnoreCase(Flags.glow.getName()))
                    break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setGlowing(false);
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                if (!event.getFlag().equalsIgnoreCase(Flags.glow.getName()) || !VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_9_R1))
                    break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setGlowing(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onResidenceDeleteEvent(ResidenceDeleteEvent event) {
        if (event.isCancelled()) {
            return;
        }
        ClaimedResidence res = event.getResidence();
        if (res.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue) || res.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
            for (Player one : event.getResidence().getPlayersInResidence()) {
                one.setWalkSpeed(0.2f);
            }
        }
        if (res.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue) || res.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
            for (Player one : event.getResidence().getPlayersInResidence()) {
                one.resetPlayerWeather();
            }
        }
        if (event.getPlayer() != null && res.getPermissions().playerHas(event.getPlayer(), Flags.fly, FlagPermissions.FlagCombo.OnlyTrue)) {
            for (Player one : event.getResidence().getPlayersInResidence()) {
                this.fly(one, false);
            }
        }
        if (res.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue) && VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_9_R1)) {
            for (Player one : event.getResidence().getPlayersInResidence()) {
                one.setGlowing(false);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(player.getLocation());
        if (res == null) {
            return;
        }
        if (res.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue) || res.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
            player.setWalkSpeed(0.2f);
        }
        if (res.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue) || res.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
            player.resetPlayerWeather();
        }
        if (event.getPlayer() != null && res.getPermissions().playerHas(event.getPlayer(), Flags.fly, FlagPermissions.FlagCombo.OnlyTrue)) {
            this.fly(player, false);
        }
        if (res.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue) && VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_9_R1)) {
            player.setGlowing(false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFlagChangeWSpeed(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.wspeed1.getName()) && !event.getFlag().equalsIgnoreCase(Flags.wspeed2.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2:
            case 3: {
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setWalkSpeed(0.2f);
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                if (event.getFlag().equalsIgnoreCase(Flags.wspeed1.getName())) {
                    for (Player one : event.getResidence().getPlayersInResidence()) {
                        one.setWalkSpeed(this.plugin.getConfigManager().getWalkSpeed1().floatValue());
                    }
                    if (!event.getResidence().getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue))
                        break;
                    event.getResidence().getPermissions().setFlag(Flags.wspeed2.getName(), FlagPermissions.FlagState.NEITHER);
                    break;
                }
                if (!event.getFlag().equalsIgnoreCase(Flags.wspeed2.getName())) break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setWalkSpeed(this.plugin.getConfigManager().getWalkSpeed2().floatValue());
                }
                if (!event.getResidence().getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue))
                    break;
                event.getResidence().getPermissions().setFlag(Flags.wspeed1.getName(), FlagPermissions.FlagState.NEITHER);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFlagChangeJump(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.jump2.getName()) && !event.getFlag().equalsIgnoreCase(Flags.jump3.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2:
            case 3:
            case 4: {
                break;
            }
            case 1: {
                if (event.getFlag().equalsIgnoreCase(Flags.jump2.getName())) {
                    if (!event.getResidence().getPermissions().has(Flags.jump3, FlagPermissions.FlagCombo.OnlyTrue))
                        break;
                    event.getResidence().getPermissions().setFlag(Flags.jump3.getName(), FlagPermissions.FlagState.NEITHER);
                    break;
                }
                if (!event.getFlag().equalsIgnoreCase(Flags.jump3.getName()) || !event.getResidence().getPermissions().has(Flags.jump2, FlagPermissions.FlagCombo.OnlyTrue))
                    break;
                event.getResidence().getPermissions().setFlag(Flags.jump2.getName(), FlagPermissions.FlagState.NEITHER);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFlagChangeSunRain(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.sun.getName()) && !event.getFlag().equalsIgnoreCase(Flags.rain.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2:
            case 3: {
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.resetPlayerWeather();
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                if (event.getFlag().equalsIgnoreCase(Flags.sun.getName())) {
                    for (Player one : event.getResidence().getPlayersInResidence()) {
                        one.setPlayerWeather(WeatherType.CLEAR);
                    }
                }
                if (!event.getFlag().equalsIgnoreCase(Flags.rain.getName())) break;
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    one.setPlayerWeather(WeatherType.DOWNFALL);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFlagChangeFly(ResidenceFlagChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getFlag().equalsIgnoreCase(Flags.fly.getName())) {
            return;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState()[event.getNewState().ordinal()]) {
            case 2:
            case 3: {
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    this.fly(one, false);
                }
                break;
            }
            case 4: {
                break;
            }
            case 1: {
                for (Player one : event.getResidence().getPlayersInResidence()) {
                    this.fly(one, true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String t;
        if (!Flags.command.isGlobalyEnabled()) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        ClaimedResidence res = this.getCurrentResidence(player.getUniqueId());
        if (res == null) {
            return;
        }
        if (!res.getPermissions().playerHas(player, Flags.command, FlagPermissions.FlagCombo.OnlyFalse)) {
            return;
        }
        if (this.plugin.getPermissionManager().isResidenceAdmin((CommandSender) player)) {
            return;
        }
        if (player.hasPermission("residence.flag.command.bypass")) {
            return;
        }
        String msg = event.getMessage().replace(" ", "_").toLowerCase();
        int white = 0;
        int black = 0;
        for (String oneWhite : res.getCmdWhiteList()) {
            t = oneWhite.toLowerCase();
            if (!msg.startsWith("/" + t)) continue;
            if (t.contains("_") && t.split("_").length > white) {
                white = t.split("_").length;
                continue;
            }
            if (white != 0) continue;
            white = 1;
        }
        for (String oneBlack : res.getCmdBlackList()) {
            t = oneBlack.toLowerCase();
            if (!msg.startsWith("/" + t)) continue;
            if (msg.contains("_")) {
                black = t.split("_").length;
                break;
            }
            black = 1;
            break;
        }
        if (black == 0) {
            for (String oneBlack : res.getCmdBlackList()) {
                t = oneBlack.toLowerCase();
                if (!t.equalsIgnoreCase("*")) continue;
                if (!msg.contains("_")) break;
                black = msg.split("_").length;
                break;
            }
        }
        if (white != 0 && white >= black || black == 0) {
            return;
        }
        event.setCancelled(true);
        this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.command.getName(), res.getName());
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onFlagGuiClick(InventoryClickEvent event) {
        if (this.getGUImap().isEmpty()) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (!this.getGUImap().containsKey(player.getUniqueId())) {
            return;
        }
        event.setCancelled(true);
        int slot = event.getRawSlot();
        if (slot > 53 || slot < 0) {
            return;
        }
        SetFlag setFlag = this.getGUImap().get(player.getUniqueId());
        ClickType click = event.getClick();
        InventoryAction action = event.getAction();
        setFlag.toggleFlag(slot, click, action);
        setFlag.recalculateInv();
        if (!player.getOpenInventory().getTopInventory().getType().equals((Object) InventoryType.CHEST)) {
            return;
        }
        player.getOpenInventory().getTopInventory().setContents(setFlag.getInventory().getContents());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLowestClick(InventoryClickEvent event) {
        if (this.getGUImap().isEmpty()) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (!this.getGUImap().containsKey(player.getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onFlagGuiClose(InventoryCloseEvent event) {
        if (this.getGUImap().isEmpty()) {
            return;
        }
        HumanEntity player = event.getPlayer();
        this.getGUImap().remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onSignInteract(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null || block.getState() == null) {
            return;
        }
        if (!(block.getState() instanceof Sign)) {
            return;
        }
        Player player = event.getPlayer();
        if (player.hasMetadata("NPC")) {
            return;
        }
        Location loc = block.getLocation();
        for (Signs one : this.plugin.getSignUtil().getSigns().GetAllSigns()) {
            if (!one.GetLocation().getWorld().getName().equalsIgnoreCase(loc.getWorld().getName()) || one.GetLocation().getBlockX() != loc.getBlockX() || one.GetLocation().getBlockY() != loc.getBlockY() || one.GetLocation().getBlockZ() != loc.getBlockZ())
                continue;
            ClaimedResidence res = one.GetResidence();
            boolean ForSale = res.isForSell();
            boolean ForRent = res.isForRent();
            String landName = res.getName();
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (ForSale) {
                    Bukkit.dispatchCommand((CommandSender) player, (String) ("res market buy " + landName));
                    break;
                }
                if (!ForRent) continue;
                if (res.isRented() && player.isSneaking()) {
                    Bukkit.dispatchCommand((CommandSender) player, (String) ("res market release " + landName));
                    break;
                }
                boolean stage = true;
                if (player.isSneaking()) {
                    stage = false;
                }
                Bukkit.dispatchCommand((CommandSender) player, (String) ("res market rent " + landName + " " + stage));
                break;
            }
            if (event.getAction() != Action.LEFT_CLICK_BLOCK || !ForRent || !res.isRented() || !this.plugin.getRentManager().getRentingPlayer(res).equals(player.getName()))
                continue;
            this.plugin.getRentManager().payRent(player, res, false);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onSignCreate(SignChangeEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Block block = event.getBlock();
        if (!(block.getState() instanceof Sign)) {
            return;
        }
        Sign sign = (Sign) block.getState();
        if (!ChatColor.stripColor((String) event.getLine(0)).equalsIgnoreCase(this.plugin.msg(lm.Sign_TopLine, new Object[0]))) {
            return;
        }
        Signs signInfo = new Signs();
        Location loc = sign.getLocation();
        String landName = null;
        Player player = event.getPlayer();
        if (player.hasMetadata("NPC")) {
            return;
        }
        ClaimedResidence res = null;
        if (!event.getLine(1).equalsIgnoreCase("")) {
            String resname = event.getLine(1);
            if (!event.getLine(2).equalsIgnoreCase("")) {
                resname = String.valueOf(resname) + "." + event.getLine(2);
            }
            if (!event.getLine(3).equalsIgnoreCase("")) {
                resname = String.valueOf(resname) + "." + event.getLine(3);
            }
            if ((res = this.plugin.getResidenceManager().getByName(resname)) == null) {
                this.plugin.msg((CommandSender) player, lm.Invalid_Residence, new Object[0]);
                return;
            }
            landName = res.getName();
        } else {
            res = this.plugin.getResidenceManager().getByLoc(loc);
            if (res != null) {
                landName = res.getName();
            }
        }
        if (res == null) {
            this.plugin.msg((CommandSender) player, lm.Invalid_Residence, new Object[0]);
            return;
        }
        final ClaimedResidence residence = res;
        int category = 1;
        if (this.plugin.getSignUtil().getSigns().GetAllSigns().size() > 0) {
            category = this.plugin.getSignUtil().getSigns().GetAllSigns().get(this.plugin.getSignUtil().getSigns().GetAllSigns().size() - 1).GetCategory() + 1;
        }
        signInfo.setCategory(category);
        signInfo.setResidence(res);
        signInfo.setLocation(loc);
        this.plugin.getSignUtil().getSigns().addSign(signInfo);
        this.plugin.getSignUtil().saveSigns();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this.plugin, new Runnable() {

            @Override
            public void run() {
                ResidencePlayerListener.this.plugin.getSignUtil().CheckSign(residence);
            }
        }, 5L);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onSignDestroy(BlockBreakEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getBlock();
        if (block == null) {
            return;
        }
        if (!(block.getState() instanceof Sign)) {
            return;
        }
        Location loc = block.getLocation();
        if (event.getPlayer().hasMetadata("NPC")) {
            return;
        }
        for (Signs one : this.plugin.getSignUtil().getSigns().GetAllSigns()) {
            if (!one.GetLocation().getWorld().getName().equalsIgnoreCase(loc.getWorld().getName()) || one.GetLocation().getBlockX() != loc.getBlockX() || one.GetLocation().getBlockY() != loc.getBlockY() || one.GetLocation().getBlockZ() != loc.getBlockZ())
                continue;
            this.plugin.getSignUtil().getSigns().removeSign(one);
            if (one.GetResidence() != null) {
                one.GetResidence().getSignsInResidence().remove(one);
            }
            this.plugin.getSignUtil().saveSigns();
            break;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String pname = event.getPlayer().getName();
        this.currentRes.remove(event.getPlayer().getUniqueId());
        this.lastUpdate.remove(event.getPlayer().getUniqueId());
        this.lastOutsideLoc.remove(event.getPlayer().getUniqueId());
        this.plugin.getChatManager().removeFromChannel(pname);
        this.plugin.getPlayerListener().removePlayerResidenceChat(event.getPlayer());
        this.plugin.addOfflinePlayerToChache((OfflinePlayer) event.getPlayer());
        this.plugin.getAutoSelectionManager().getList().remove(pname.toLowerCase());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        ClaimedResidence res;
        if (!Flags.nofly.isGlobalyEnabled()) {
            return;
        }
        Player player = event.getPlayer();
        if (player.hasMetadata("NPC")) {
            return;
        }
        FlagPermissions perms = this.plugin.getPermsByLocForPlayer(player.getLocation(), player);
        if (!(!player.getAllowFlight() && !player.isFlying() || !perms.has(Flags.nofly, false) || this.plugin.isResAdminOn(player) || player.hasPermission("residence.nofly.bypass") || (res = this.plugin.getResidenceManager().getByLoc(player.getLocation())) != null && res.isOwner(player))) {
            Location lc = player.getLocation();
            Location location = new Location(lc.getWorld(), lc.getX(), (double) lc.getBlockY(), lc.getZ());
            location.setPitch(lc.getPitch());
            location.setYaw(lc.getYaw());
            int from = location.getBlockY();
            int maxH = location.getWorld().getMaxHeight() - 1;
            int i = 0;
            while (i < maxH) {
                location.setY((double) (from - i));
                Block block = location.getBlock();
                if (!ResidencePlayerListener.isEmptyBlock(block)) {
                    location.setY((double) (from - i + 1));
                    break;
                }
                if (location.getBlockY() <= 0) {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.nofly.getName(), location.getWorld().getName());
                    return;
                }
                ++i;
            }
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.nofly.getName(), location.getWorld().getName());
            player.closeInventory();
            player.teleport(location);
            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.lastUpdate.put(player.getUniqueId(), 0L);
        if (this.plugin.getPermissionManager().isResidenceAdmin((CommandSender) player)) {
            this.plugin.turnResAdminOn(player);
        }
        this.handleNewLocation(player, player.getLocation(), true);
        this.plugin.getPlayerManager().playerJoin(player, false);
        if (player.hasPermission("residence.versioncheck")) {
            this.plugin.getVersionChecker().VersionCheck(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerSpawn(PlayerRespawnEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getRespawnLocation().getWorld())) {
            return;
        }
        Location loc = event.getRespawnLocation();
        Boolean bed = event.isBedSpawn();
        Player player = event.getPlayer();
        if (player.hasMetadata("NPC")) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(loc);
        if (res == null) {
            return;
        }
        if (!res.getPermissions().playerHas(player, Flags.move, FlagPermissions.FlagCombo.OnlyFalse)) {
            return;
        }
        if (bed.booleanValue()) {
            loc = player.getWorld().getSpawnLocation();
        }
        if ((res = this.plugin.getResidenceManager().getByLoc(loc)) != null && res.getPermissions().playerHas(player, Flags.move, FlagPermissions.FlagCombo.OnlyFalse)) {
            loc = res.getOutsideFreeLoc(loc, player);
        }
        this.plugin.msg((CommandSender) player, lm.General_NoSpawn, new Object[0]);
        event.setRespawnLocation(loc);
    }

    private boolean isContainer(Material mat, Block block) {
        if (!(FlagPermissions.getMaterialUseFlagList().containsKey((Object) mat) && FlagPermissions.getMaterialUseFlagList().get((Object) mat).equals((Object) Flags.container) || this.plugin.getConfigManager().getCustomContainers().contains(block.getType().getId()))) {
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isCanUseEntity_RClickOnly(Material mat, Block block) {
        String string = mat.name();
        switch (string.hashCode()) {
            case -2063836287: {
                if (string.equals("REDSTONE_COMPARATOR")) return true;
                break;
            }
            case -1892221527: {
                if (string.equals("DIODE_BLOCK_OFF")) return true;
                break;
            }
            case -1629786261: {
                if (string.equals("DAYLIGHT_DETECTOR")) return true;
                break;
            }
            case -1331883119: {
                if (string.equals("REDSTONE_COMPARATOR_OFF")) return true;
                break;
            }
            case -1289889955: {
                if (string.equals("REDSTONE_COMPARATOR_ON")) return true;
                break;
            }
            case -753776059: {
                if (string.equals("DIODE_BLOCK_ON")) return true;
                break;
            }
            case -562844383: {
                if (string.equals("ITEM_FRAME")) return true;
                break;
            }
            case -415523425: {
                if (string.equals("WORKBENCH")) return true;
                break;
            }
            case -343861338: {
                if (string.equals("CAKE_BLOCK")) return true;
                break;
            }
            case -329276657: {
                if (string.equals("BED_BLOCK")) return true;
                break;
            }
            case 62437548: {
                if (string.equals("ANVIL")) return true;
                break;
            }
            case 65052267: {
                if (string.equals("DIODE")) return true;
                break;
            }
            case 867723593: {
                if (string.equals("DAYLIGHT_DETECTOR_INVERTED")) return true;
                break;
            }
            case 1401892433: {
                if (string.equals("FLOWER_POT")) return true;
                break;
            }
            case 1545025079: {
                if (string.equals("BREWING_STAND")) return true;
                break;
            }
            case 1593989766: {
                if (string.equals("ENCHANTMENT_TABLE")) return true;
                break;
            }
            case 1668377387: {
                if (string.equals("COMMAND")) return true;
                break;
            }
            case 1955250244: {
                if (!string.equals("BEACON")) break;
                return true;
            }
        }
        ItemManager.CMIMaterial cmat = ItemManager.CMIMaterial.get(mat);
        if (cmat == null || !cmat.isPottedFlower())
            return this.plugin.getConfigManager().getCustomRightClick().contains(block.getType().getId());
        return true;
    }

    public boolean isCanUseEntity_BothClick(Material mat, Block block) {
        ItemManager.CMIMaterial m = ItemManager.CMIMaterial.get(mat);
        if (m.isDoor()) {
            return true;
        }
        if (m.isButton()) {
            return true;
        }
        if (m.isGate()) {
            return true;
        }
        if (m.isTrapDoor()) {
            return true;
        }
        switch (ResidencePlayerListener.$SWITCH_TABLE$cmiLib$ItemManager$CMIMaterial()[ItemManager.CMIMaterial.get(mat).ordinal()]) {
            case 259:
            case 434:
            case 537:
            case 593:
            case 800: {
                return true;
            }
        }
        return Residence.getInstance().getConfigManager().getCustomBothClick().contains(block.getType().getId());
    }

    public static boolean isEmptyBlock(Block block) {
        switch (ResidencePlayerListener.$SWITCH_TABLE$cmiLib$ItemManager$CMIMaterial()[ItemManager.CMIMaterial.get(block).ordinal()]) {
            case 156:
            case 401:
            case 571:
            case 814:
            case 842:
            case 843:
            case 857:
            case 859: {
                return true;
            }
        }
        if (ItemManager.CMIMaterial.get(block).isSapling()) {
            return true;
        }
        if (ItemManager.CMIMaterial.get(block).isAir()) {
            return true;
        }
        if (ItemManager.CMIMaterial.get(block).isButton()) {
            return true;
        }
        return false;
    }

    private boolean isCanUseEntity(Material mat, Block block) {
        if (!this.isCanUseEntity_BothClick(mat, block) && !this.isCanUseEntity_RClickOnly(mat, block)) {
            return false;
        }
        return true;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerFireInteract(PlayerInteractEvent event) {
        boolean hasplace;
        if (event.getPlayer() == null) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Block relativeBlock = block.getRelative(event.getBlockFace());
        if (relativeBlock == null) {
            return;
        }
        Player player = event.getPlayer();
        if (player.hasMetadata("NPC")) {
            return;
        }
        FlagPermissions perms = this.plugin.getPermsByLocForPlayer(block.getLocation(), player);
        if (relativeBlock.getType() == Material.FIRE && !(hasplace = perms.playerHas(player, Flags.place, perms.playerHas(player, Flags.build, true)))) {
            event.setCancelled(true);
            this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.build.getName());
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlatePress(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        ItemManager.CMIMaterial mat = ItemManager.CMIMaterial.get(block);
        Player player = event.getPlayer();
        if (player.hasMetadata("NPC")) {
            return;
        }
        FlagPermissions perms = this.plugin.getPermsByLocForPlayer(block.getLocation(), player);
        boolean resadmin2 = this.plugin.isResAdminOn(player);
        if (!resadmin2) {
            boolean hasuse = perms.playerHas(player, Flags.use, true);
            boolean haspressure = perms.playerHas(player, Flags.pressure, hasuse);
            if ((!hasuse && !haspressure || !haspressure) && mat.isPlate()) {
                event.setCancelled(true);
                return;
            }
        }
        if (!perms.playerHas(player, Flags.trample, perms.playerHas(player, Flags.build, true)) && (mat.equals((Object) ItemManager.CMIMaterial.FARMLAND) || mat.equals((Object) ItemManager.CMIMaterial.SOUL_SAND))) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSelection(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player player = event.getPlayer();
        ItemManager.CMIMaterial heldItem = ItemManager.CMIMaterial.get(player.getItemInHand());
        if (!heldItem.equals((Object) this.plugin.getConfigManager().getSelectionTool())) {
            return;
        }
        if (this.plugin.getWorldEditTool().equals((Object) this.plugin.getConfigManager().getSelectionTool())) {
            return;
        }
        if (player.getGameMode() == GameMode.CREATIVE) {
            event.setCancelled(true);
        }
        boolean resadmin2 = this.plugin.isResAdminOn(player);
        if (player.hasMetadata("NPC")) {
            return;
        }
        ResidencePlayer rPlayer = this.plugin.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = rPlayer.getGroup();
        if (player.hasPermission("residence.select") || player.hasPermission("residence.create") && !player.isPermissionSet("residence.select") || group.canCreateResidences() && !player.isPermissionSet("residence.create") && !player.isPermissionSet("residence.select") || resadmin2) {
            Block block = event.getClickedBlock();
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                Location loc = block.getLocation();
                this.plugin.getSelectionManager().placeLoc1(player, loc, true);
                this.plugin.msg((CommandSender) player, lm.Select_PrimaryPoint, this.plugin.msg(lm.General_CoordsTop, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                event.setCancelled(true);
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && this.plugin.getNms().isMainHand(event)) {
                Location loc = block.getLocation();
                this.plugin.getSelectionManager().placeLoc2(player, loc, true);
                this.plugin.msg((CommandSender) player, lm.Select_SecondaryPoint, this.plugin.msg(lm.General_CoordsBottom, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                event.setCancelled(true);
            }
            if (this.plugin.getSelectionManager().hasPlacedBoth(player)) {
                this.plugin.getSelectionManager().showSelectionInfoInActionBar(player);
                this.plugin.getSelectionManager().updateLocations(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInfoCheck(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        ItemManager.CMIMaterial heldItem = ItemManager.CMIMaterial.get(item);
        if (!heldItem.equals((Object) this.plugin.getConfigManager().getInfoTool())) {
            return;
        }
        if (this.isContainer(block.getType(), block)) {
            return;
        }
        if (player.hasMetadata("NPC")) {
            return;
        }
        Location loc = block.getLocation();
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(loc);
        if (res != null) {
            this.plugin.getResidenceManager().printAreaInfo(res.getName(), (CommandSender) player, false);
        } else {
            this.plugin.msg((CommandSender) player, lm.Residence_NoResHere, new Object[0]);
        }
        event.setCancelled(true);
    }

    private static boolean placingMinecart(Block block, ItemStack item) {
        if (block.getType().name().contains("RAIL") && item.getType().name().contains("MINECART")) {
            return true;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (!this.plugin.getNms().isMainHand(event)) {
            return;
        }
        ItemStack iih = this.plugin.getNms().itemInMainHand(player);
        ItemManager.CMIMaterial heldItem = ItemManager.CMIMaterial.get(iih);
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Material mat = block.getType();
        if (!(event.getAction() == Action.PHYSICAL || (this.isContainer(mat, block) || this.isCanUseEntity_RClickOnly(mat, block)) && event.getAction() == Action.RIGHT_CLICK_BLOCK || this.isCanUseEntity_BothClick(mat, block) || heldItem.equals((Object) this.plugin.getConfigManager().getSelectionTool()) || heldItem.equals((Object) this.plugin.getConfigManager().getInfoTool()) || heldItem.isDye() || heldItem.equals((Object) ItemManager.CMIMaterial.ARMOR_STAND) || heldItem.isBoat() || ResidencePlayerListener.placingMinecart(block, iih))) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        String world = player.getWorld().getName();
        ResidencePlayer resPlayer = this.plugin.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = resPlayer.getGroup();
        boolean resadmin2 = this.plugin.isResAdminOn(player);
        if (!resadmin2 && !this.plugin.getItemManager().isAllowed(heldItem.getMaterial(), group, world)) {
            this.plugin.msg((CommandSender) player, lm.General_ItemBlacklisted, new Object[0]);
            event.setCancelled(true);
            return;
        }
        if (resadmin2) {
            return;
        }
        ItemManager.CMIMaterial blockM = ItemManager.CMIMaterial.get(block);
        FlagPermissions perms = this.plugin.getPermsByLocForPlayer(block.getLocation(), player);
        if (heldItem != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (heldItem.isDye() && (heldItem.equals((Object) ItemManager.CMIMaterial.BONE_MEAL) && block.getType() == Material.GRASS || heldItem.equals((Object) ItemManager.CMIMaterial.COCOA_BEANS) && blockM.equals((Object) ItemManager.CMIMaterial.JUNGLE_WOOD)) && !(perms = this.plugin.getPermsByLocForPlayer(block.getRelative(event.getBlockFace()).getLocation(), player)).playerHas(player, Flags.build, true)) {
                this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.build.getName());
                event.setCancelled(true);
                return;
            }
            if ((heldItem.equals((Object) ItemManager.CMIMaterial.ARMOR_STAND) || heldItem.isBoat()) && !(perms = this.plugin.getPermsByLocForPlayer(block.getRelative(event.getBlockFace()).getLocation(), player)).playerHas(player, Flags.build, true)) {
                this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.build.getName());
                event.setCancelled(true);
                return;
            }
            if (ResidencePlayerListener.placingMinecart(block, iih) && !(perms = this.plugin.getPermsByLocForPlayer(block.getLocation(), player)).playerHas(player, Flags.build, true)) {
                this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.build.getName());
                event.setCancelled(true);
                return;
            }
        }
        if (this.isContainer(mat, block) || this.isCanUseEntity(mat, block)) {
            boolean hasuse = perms.playerHas(player, Flags.use, true);
            ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(block.getLocation());
            if (res == null || !res.isOwner(player)) {
                for (Map.Entry<Material, Flags> checkMat : FlagPermissions.getMaterialUseFlagList().entrySet()) {
                    if (mat != checkMat.getKey() || perms.playerHas(player, checkMat.getValue(), hasuse)) continue;
                    if (hasuse || checkMat.getValue().equals((Object) Flags.container)) {
                        event.setCancelled(true);
                        this.plugin.msg((CommandSender) player, lm.Flag_Deny, new Object[]{checkMat.getValue()});
                        return;
                    }
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        event.setCancelled(true);
                        this.plugin.msg((CommandSender) player, lm.Flag_Deny, new Object[]{checkMat.getValue()});
                        return;
                    }
                    if (this.isCanUseEntity_BothClick(mat, block)) {
                        event.setCancelled(true);
                        this.plugin.msg((CommandSender) player, lm.Flag_Deny, new Object[]{checkMat.getValue()});
                    }
                    return;
                }
            }
            if (this.plugin.getConfigManager().getCustomContainers().contains(blockM.getId()) && !perms.playerHas(player, Flags.container, hasuse)) {
                event.setCancelled(true);
                this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.container.getName());
                return;
            }
            if (this.plugin.getConfigManager().getCustomBothClick().contains(blockM.getId()) && !hasuse) {
                event.setCancelled(true);
                this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.use.getName());
                return;
            }
            if (this.plugin.getConfigManager().getCustomRightClick().contains(blockM.getId()) && event.getAction() == Action.RIGHT_CLICK_BLOCK && !hasuse) {
                event.setCancelled(true);
                this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.use.getName());
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerTradeEntity(PlayerInteractEntityEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.VILLAGER) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(ent.getLocation());
        if (res != null && res.getPermissions().playerHas(player, Flags.trade, FlagPermissions.FlagCombo.OnlyFalse)) {
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.trade.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean canRide(EntityType type) {
        String string = type.name().toLowerCase();
        switch (string.hashCode()) {
            case -1326158276: {
                if (string.equals("donkey")) return true;
                return false;
            }
            case 110990: {
                if (string.equals("pig")) return true;
                return false;
            }
            case 99466205: {
                if (string.equals("horse")) return true;
                return false;
            }
            case 103054389: {
                if (!string.equals("llama")) return false;
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean canHaveContainer(EntityType type) {
        String string = type.name().toLowerCase();
        switch (string.hashCode()) {
            case -1326158276: {
                if (string.equals("donkey")) return true;
                return false;
            }
            case 99466205: {
                if (string.equals("horse")) return true;
                return false;
            }
            case 103054389: {
                if (!string.equals("llama")) return false;
                return true;
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractWithHorse(PlayerInteractEntityEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (!Flags.container.isGlobalyEnabled()) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (!ResidencePlayerListener.canHaveContainer(ent.getType())) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.container, FlagPermissions.FlagCombo.OnlyFalse) && player.isSneaking()) {
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.container.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractWithRidable(PlayerInteractEntityEvent event) {
        if (!Flags.riding.isGlobalyEnabled()) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (!ResidencePlayerListener.canRide(ent.getType())) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && !res.getPermissions().playerHas(player, Flags.riding, FlagPermissions.FlagCombo.TrueOrNone)) {
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.riding.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractWithMinecartStorage(PlayerInteractEntityEvent event) {
        if (!Flags.container.isGlobalyEnabled()) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.MINECART_CHEST && ent.getType() != EntityType.MINECART_HOPPER) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.container, FlagPermissions.FlagCombo.OnlyFalse)) {
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.container.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractWithMinecart(PlayerInteractEntityEvent event) {
        if (!Flags.riding.isGlobalyEnabled()) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.MINECART && ent.getType() != EntityType.BOAT) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.riding, FlagPermissions.FlagCombo.OnlyFalse)) {
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.riding.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDyeSheep(PlayerInteractEntityEvent event) {
        if (!Flags.dye.isGlobalyEnabled()) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (ent.getType() != EntityType.SHEEP) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.dye, FlagPermissions.FlagCombo.OnlyFalse)) {
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.dye.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerShearEntity(PlayerShearEntityEvent event) {
        if (!Flags.shear.isGlobalyEnabled()) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getEntity();
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(ent.getLocation());
        if (res == null) {
            return;
        }
        if (!res.isOwner(player) && res.getPermissions().playerHas(player, Flags.shear, FlagPermissions.FlagCombo.OnlyFalse)) {
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.shear.getName(), res.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerItemFrameInteract(PlayerInteractEntityEvent event) {
        if (!Flags.container.isGlobalyEnabled()) {
            return;
        }
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Entity ent = event.getRightClicked();
        if (!(ent instanceof Hanging)) {
            return;
        }
        Hanging hanging = (Hanging) ent;
        if (hanging.getType() != EntityType.ITEM_FRAME) {
            return;
        }
        Material heldItem = this.plugin.getNms().itemInMainHand(player).getType();
        FlagPermissions perms = this.plugin.getPermsByLocForPlayer(ent.getLocation(), player);
        String world = player.getWorld().getName();
        ResidencePlayer resPlayer = this.plugin.getPlayerManager().getResidencePlayer(player);
        PermissionGroup group = resPlayer.getGroup();
        if (!this.plugin.getItemManager().isAllowed(heldItem, group, world)) {
            this.plugin.msg((CommandSender) player, lm.General_ItemBlacklisted, new Object[0]);
            event.setCancelled(true);
            return;
        }
        if (!perms.playerHas(player, Flags.container, perms.playerHas(player, Flags.use, true))) {
            event.setCancelled(true);
            this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.container.getName());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        FlagPermissions perms;
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        Location loc = event.getBlockClicked().getLocation();
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(loc);
        if (res != null) {
            if (this.plugin.getConfigManager().preventRentModify() && this.plugin.getConfigManager().enabledRentSystem() && this.plugin.getRentManager().isRented(res.getName())) {
                this.plugin.msg((CommandSender) player, lm.Rent_ModifyDeny, new Object[0]);
                event.setCancelled(true);
                return;
            }
            Material mat = event.getBucket();
            if (res.getPermissions().playerHas(player, Flags.build, FlagPermissions.FlagCombo.OnlyFalse) && this.plugin.getConfigManager().getNoPlaceWorlds().contains(loc.getWorld().getName()) && (mat == Material.LAVA_BUCKET || mat == Material.WATER_BUCKET)) {
                this.plugin.msg((CommandSender) player, lm.Flag_Deny, new Object[]{Flags.build});
                event.setCancelled(true);
                return;
            }
        }
        if (!(perms = this.plugin.getPermsByLocForPlayer(loc, player)).playerHas(player, Flags.build, true)) {
            this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.build.getName());
            event.setCancelled(true);
            return;
        }
        Material mat = event.getBucket();
        int level = this.plugin.getConfigManager().getPlaceLevel();
        if (res == null && this.plugin.getConfigManager().isNoLavaPlace() && loc.getBlockY() >= level - 1 && this.plugin.getConfigManager().getNoPlaceWorlds().contains(loc.getWorld().getName()) && mat == Material.LAVA_BUCKET) {
            if (!this.plugin.msg(lm.General_CantPlaceLava, new Object[0]).equalsIgnoreCase("")) {
                this.plugin.msg((CommandSender) player, lm.General_CantPlaceLava, level);
            }
            event.setCancelled(true);
            return;
        }
        if (res == null && this.plugin.getConfigManager().isNoWaterPlace() && loc.getBlockY() >= level - 1 && this.plugin.getConfigManager().getNoPlaceWorlds().contains(loc.getWorld().getName()) && mat == Material.WATER_BUCKET) {
            if (!this.plugin.msg(lm.General_CantPlaceWater, new Object[0]).equalsIgnoreCase("")) {
                this.plugin.msg((CommandSender) player, lm.General_CantPlaceWater, level);
            }
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (this.plugin.isResAdminOn(player)) {
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(event.getBlockClicked().getLocation());
        if (res != null && this.plugin.getConfigManager().preventRentModify() && this.plugin.getConfigManager().enabledRentSystem() && this.plugin.getRentManager().isRented(res.getName())) {
            this.plugin.msg((CommandSender) player, lm.Rent_ModifyDeny, new Object[0]);
            event.setCancelled(true);
            return;
        }
        FlagPermissions perms = this.plugin.getPermsByLocForPlayer(event.getBlockClicked().getLocation(), player);
        boolean hasdestroy = perms.playerHas(player, Flags.destroy, perms.playerHas(player, Flags.build, true));
        if (!hasdestroy) {
            this.plugin.msg((CommandSender) player, lm.Flag_Deny, Flags.destroy.getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (player.hasMetadata("NPC")) {
            return;
        }
        Location loc = event.getTo();
        if (this.plugin.isResAdminOn(player)) {
            this.handleNewLocation(player, loc, false);
            return;
        }
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(loc);
        if (res == null) {
            return;
        }
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND || event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL || event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) {
            if (res.getPermissions().playerHas(player, Flags.move, FlagPermissions.FlagCombo.OnlyFalse) && !res.isOwner(player) && !player.hasPermission("residence.tpbypass")) {
                event.setCancelled(true);
                this.plugin.msg((CommandSender) player, lm.Residence_MoveDeny, res.getName());
                return;
            }
        } else if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && res.getPermissions().playerHas(player, Flags.enderpearl, FlagPermissions.FlagCombo.OnlyFalse)) {
            event.setCancelled(true);
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.enderpearl.getName(), res.getName());
            return;
        }
        if ((event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN || event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) && this.plugin.getConfigManager().isBlockAnyTeleportation() && !res.isOwner(player) && res.getPermissions().playerHas(player, Flags.tp, FlagPermissions.FlagCombo.OnlyFalse) && !player.hasPermission("residence.admin.tp")) {
            event.setCancelled(true);
            this.plugin.msg((CommandSender) player, lm.General_TeleportDeny, res.getName());
            return;
        }
        if (this.plugin.getNms().isChorusTeleport(event.getCause()) && !res.isOwner(player) && res.getPermissions().playerHas(player, Flags.chorustp, FlagPermissions.FlagCombo.OnlyFalse) && !player.hasPermission("residence.admin.tp")) {
            event.setCancelled(true);
            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.chorustp.getName(), res.getName());
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getEntity().getWorld())) {
            return;
        }
        Player player = event.getEntity();
        if (player == null) {
            return;
        }
        if (player.hasMetadata("NPC")) {
            return;
        }
        Location loc = player.getLocation();
        ClaimedResidence res = this.plugin.getResidenceManager().getByLoc(loc);
        if (res == null) {
            return;
        }
        if (res.getPermissions().has(Flags.keepinv, false)) {
            event.setKeepInventory(true);
        }
        if (res.getPermissions().has(Flags.keepexp, false)) {
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
        if (res.getPermissions().has(Flags.respawn, false) && Bukkit.getVersion().toString().contains("Spigot")) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this.plugin, new Runnable() {

                @Override
                public void run() {
                    try {
                        event.getEntity().spigot().respawn();
                    } catch (Exception exception) {
                        // empty catch block
                    }
                }
            }, 1L);
        }
    }

    private static Location getSafeLocation(Location loc) {
        Block block;
        int curY = loc.getBlockY();
        int i = 0;
        while (i <= curY) {
            block = loc.clone().add(0.0, (double) (-i), 0.0).getBlock();
            if (!block.isEmpty() && block.getLocation().clone().add(0.0, 1.0, 0.0).getBlock().isEmpty() && block.getLocation().clone().add(0.0, 2.0, 0.0).getBlock().isEmpty()) {
                return loc.clone().add(0.0, (double) (-i + 1), 0.0);
            }
            ++i;
        }
        i = 0;
        while (i <= loc.getWorld().getMaxHeight() - curY) {
            block = loc.clone().add(0.0, (double) i, 0.0).getBlock();
            if (!block.isEmpty() && block.getLocation().clone().add(0.0, 1.0, 0.0).getBlock().isEmpty() && block.getLocation().clone().add(0.0, 2.0, 0.0).getBlock().isEmpty()) {
                return loc.clone().add(0.0, (double) (i + 1), 0.0);
            }
            ++i;
        }
        return null;
    }

    private void fly(Player player, boolean state) {
        if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) {
            return;
        }
        if (player.hasPermission("residence.flybypass")) {
            return;
        }
        if (!state) {
            boolean land = player.isFlying();
            player.setFlying(state);
            player.setAllowFlight(state);
            if (land) {
                Location loc = ResidencePlayerListener.getSafeLocation(player.getLocation());
                if (loc == null && (loc = this.plugin.getConfigManager().getFlyLandLocation()) == null) {
                    loc = ((World) Bukkit.getWorlds().get(0)).getSpawnLocation();
                }
                if (loc != null) {
                    player.closeInventory();
                    player.teleport(loc);
                }
            }
        } else {
            player.setAllowFlight(state);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onResidenceChange(ResidenceChangedEvent event) {
        ClaimedResidence res = event.getTo();
        ClaimedResidence ResOld = event.getFrom();
        Player player = event.getPlayer();
        if (res == null && ResOld != null) {
            if (ResOld.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue) || ResOld.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerTime();
            }
            if (ResOld.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue) || ResOld.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(0.2f);
            }
            if (ResOld.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue) || ResOld.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerWeather();
            }
            if (ResOld.getPermissions().playerHas(player, Flags.fly, FlagPermissions.FlagCombo.OnlyTrue)) {
                this.fly(player, false);
            }
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_9_R1) && ResOld.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setGlowing(false);
            }
        }
        if (res != null && ResOld != null && !res.equals(ResOld)) {
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_9_R1)) {
                if (res.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue)) {
                    player.setGlowing(true);
                } else if (ResOld.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue)) {
                    player.setGlowing(false);
                }
            }
            if (res.getPermissions().playerHas(player, Flags.fly, FlagPermissions.FlagCombo.OnlyTrue)) {
                this.fly(player, true);
            } else if (ResOld.getPermissions().playerHas(player, Flags.fly, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().playerHas(player, Flags.fly, FlagPermissions.FlagCombo.OnlyTrue)) {
                this.fly(player, false);
            }
            if (res.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerTime(6000L, false);
            } else if (ResOld.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerTime();
            }
            if (res.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerTime(14000L, false);
            } else if (ResOld.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerTime();
            }
            if (res.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(this.plugin.getConfigManager().getWalkSpeed1().floatValue());
            } else if (ResOld.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(0.2f);
            }
            if (res.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(this.plugin.getConfigManager().getWalkSpeed2().floatValue());
            } else if (ResOld.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(0.2f);
            }
            if (res.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerWeather(WeatherType.CLEAR);
            } else if (ResOld.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerWeather();
            }
            if (res.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            } else if (ResOld.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue) && !res.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.resetPlayerWeather();
            }
        }
        if (res != null && ResOld == null) {
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_9_R1) && res.getPermissions().has(Flags.glow, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setGlowing(true);
            }
            if (res.getPermissions().playerHas(player, Flags.fly, FlagPermissions.FlagCombo.OnlyTrue)) {
                this.fly(player, true);
            }
            if (res.getPermissions().has(Flags.day, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerTime(6000L, false);
            }
            if (res.getPermissions().has(Flags.night, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerTime(14000L, false);
            }
            if (res.getPermissions().has(Flags.wspeed1, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(this.plugin.getConfigManager().getWalkSpeed1().floatValue());
            }
            if (res.getPermissions().has(Flags.wspeed2, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setWalkSpeed(this.plugin.getConfigManager().getWalkSpeed2().floatValue());
            }
            if (res.getPermissions().has(Flags.sun, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerWeather(WeatherType.CLEAR);
            }
            if (res.getPermissions().has(Flags.rain, FlagPermissions.FlagCombo.OnlyTrue)) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        if (player.hasMetadata("NPC")) {
            return;
        }
        Location locfrom = event.getFrom();
        Location locto = event.getTo();
        if (locfrom.getBlockX() == locto.getBlockX() && locfrom.getBlockY() == locto.getBlockY() && locfrom.getBlockZ() == locto.getBlockZ()) {
            return;
        }
        String name = player.getName();
        if (name == null) {
            return;
        }
        Long last = this.lastUpdate.get(player.getUniqueId());
        long now = System.currentTimeMillis();
        if (last != null && now - last < (long) this.plugin.getConfigManager().getMinMoveUpdateInterval()) {
            return;
        }
        this.lastUpdate.put(player.getUniqueId(), now);
        boolean handled = this.handleNewLocation(player, locto, true);
        if (!handled) {
            event.setCancelled(true);
        }
        if (!this.plugin.getTeleportDelayMap().isEmpty() && this.plugin.getConfigManager().getTeleportDelay() > 0 && this.plugin.getTeleportDelayMap().contains(player.getName())) {
            this.plugin.getTeleportDelayMap().remove(player.getName());
            this.plugin.msg((CommandSender) player, lm.General_TeleportCanceled, new Object[0]);
            if (this.plugin.getConfigManager().isTeleportTitleMessage()) {
                ActionBarTitleMessages.sendTitle(player, "", "");
            }
        }
    }

    private static boolean teleport(Player player, Location loc) {
        if (player == null || !player.isOnline() || loc == null) {
            return false;
        }
        if (!player.teleport(loc)) {
            return false;
        }
        return true;
    }

    public boolean handleNewLocation(final Player player, Location loc, boolean move) {
        ClaimedResidence res;
        ClaimedResidence orres = res = this.plugin.getResidenceManager().getByLoc(loc);
        String areaname = null;
        String subzone2 = null;
        if (res != null) {
            areaname = res.getName();
            while (res.getSubzoneByLoc(loc) != null) {
                res = res.getSubzoneByLoc(player.getLocation());
                subzone2 = res.getName();
                areaname = String.valueOf(areaname) + "." + subzone2;
            }
        }
        UUID uuid = player.getUniqueId();
        ClaimedResidence ResOld = null;
        if (this.currentRes.containsKey(uuid)) {
            ResOld = this.currentRes.get(uuid);
            if (ResOld == null) {
                this.currentRes.remove(uuid);
            } else if (res != null && ResOld.getName().equals(res.getName())) {
                if (Flags.nofly.isGlobalyEnabled() && player.isFlying() && res.getPermissions().playerHas(player, Flags.nofly, FlagPermissions.FlagCombo.OnlyTrue) && !this.plugin.isResAdminOn(player) && !player.hasPermission("residence.nofly.bypass") && !res.isOwner(player)) {
                    Location lc = player.getLocation();
                    Location location = new Location(lc.getWorld(), lc.getX(), (double) lc.getBlockY(), lc.getZ());
                    location.setPitch(lc.getPitch());
                    location.setYaw(lc.getYaw());
                    int from = location.getBlockY();
                    int maxH = location.getWorld().getMaxHeight() - 1;
                    int i = 0;
                    while (i < maxH) {
                        location.setY((double) (from - i));
                        Block block = location.getBlock();
                        if (!ResidencePlayerListener.isEmptyBlock(block)) {
                            location.setY((double) (from - i + 1));
                            break;
                        }
                        if (location.getBlockY() <= 0) {
                            Location lastLoc = this.lastOutsideLoc.get(uuid);
                            player.closeInventory();
                            boolean teleported = false;
                            teleported = lastLoc != null ? ResidencePlayerListener.teleport(player, lastLoc) : ResidencePlayerListener.teleport(player, res.getOutsideFreeLoc(loc, player));
                            this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.nofly.getName(), orres.getName());
                            if (!teleported) {
                                return false;
                            }
                            return true;
                        }
                        ++i;
                    }
                    this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.nofly.getName(), orres.getName());
                    player.closeInventory();
                    boolean teleported = ResidencePlayerListener.teleport(player, location);
                    if (!teleported) {
                        return false;
                    }
                    player.setFlying(false);
                    player.setAllowFlight(false);
                }
                this.lastOutsideLoc.put(uuid, loc);
                return true;
            }
        }
        if (!this.plugin.getAutoSelectionManager().getList().isEmpty()) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin) this.plugin, new Runnable() {

                @Override
                public void run() {
                    ResidencePlayerListener.this.plugin.getAutoSelectionManager().UpdateSelection(player);
                }
            });
        }
        if (res == null) {
            this.lastOutsideLoc.put(uuid, loc);
            if (ResOld != null) {
                ResidenceChangedEvent chgEvent = new ResidenceChangedEvent(ResOld, null, player);
                this.plugin.getServ().getPluginManager().callEvent((Event) chgEvent);
                this.currentRes.remove(uuid);
            }
            return true;
        }
        if (move) {
            if (res.getPermissions().playerHas(player, Flags.move, FlagPermissions.FlagCombo.OnlyFalse) && !this.plugin.isResAdminOn(player) && !res.isOwner(player) && !player.hasPermission("residence.admin.move")) {
                Location lastLoc = this.lastOutsideLoc.get(uuid);
                if (this.plugin.getConfigManager().BounceAnimation()) {
                    Visualizer v = new Visualizer(player);
                    v.setErrorAreas(res);
                    v.setOnce(true);
                    this.plugin.getSelectionManager().showBounds(player, v);
                }
                ClaimedResidence preRes = this.plugin.getResidenceManager().getByLoc(lastLoc);
                boolean teleported = false;
                if (preRes != null && preRes.getPermissions().playerHas(player, Flags.tp, FlagPermissions.FlagCombo.OnlyFalse) && !player.hasPermission("residence.admin.tp")) {
                    Location newLoc = res.getOutsideFreeLoc(loc, player);
                    player.closeInventory();
                    teleported = ResidencePlayerListener.teleport(player, newLoc);
                } else if (lastLoc != null) {
                    StuckInfo info2 = this.updateStuckTeleport(player, loc);
                    player.closeInventory();
                    if (info2 != null && info2.getTimesTeleported() > 5) {
                        Location newLoc = res.getOutsideFreeLoc(loc, player);
                        teleported = ResidencePlayerListener.teleport(player, newLoc);
                    } else {
                        teleported = ResidencePlayerListener.teleport(player, lastLoc);
                    }
                } else {
                    Location newLoc = res.getOutsideFreeLoc(loc, player);
                    player.closeInventory();
                    teleported = ResidencePlayerListener.teleport(player, newLoc);
                }
                if (this.plugin.getConfigManager().useActionBar()) {
                    ActionBarTitleMessages.send(player, this.plugin.msg(lm.Residence_MoveDeny, orres.getName()));
                } else {
                    this.plugin.msg((CommandSender) player, lm.Residence_MoveDeny, orres.getName());
                }
                return teleported;
            }
            if (Flags.nofly.isGlobalyEnabled() && player.isFlying() && res.getPermissions().playerHas(player, Flags.nofly, FlagPermissions.FlagCombo.OnlyTrue) && !this.plugin.isResAdminOn(player) && !player.hasPermission("residence.nofly.bypass") && !res.isOwner(player)) {
                Location lc = player.getLocation();
                Location location = new Location(lc.getWorld(), lc.getX(), (double) lc.getBlockY(), lc.getZ());
                location.setPitch(lc.getPitch());
                location.setYaw(lc.getYaw());
                int from = location.getBlockY();
                int maxH = location.getWorld().getMaxHeight() - 1;
                boolean teleported = false;
                int i = 0;
                while (i < maxH) {
                    location.setY((double) (from - i));
                    Block block = location.getBlock();
                    if (!ResidencePlayerListener.isEmptyBlock(block)) {
                        location.setY((double) (from - i + 1));
                        break;
                    }
                    if (location.getBlockY() <= 0) {
                        Location lastLoc = this.lastOutsideLoc.get(uuid);
                        player.closeInventory();
                        teleported = lastLoc != null ? ResidencePlayerListener.teleport(player, lastLoc) : ResidencePlayerListener.teleport(player, res.getOutsideFreeLoc(loc, player));
                        this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.nofly.getName(), orres.getName());
                        return teleported;
                    }
                    ++i;
                }
                this.plugin.msg((CommandSender) player, lm.Residence_FlagDeny, Flags.nofly.getName(), orres.getName());
                player.closeInventory();
                teleported = ResidencePlayerListener.teleport(player, location);
                if (!teleported) {
                    return false;
                }
                player.setFlying(false);
                player.setAllowFlight(false);
            }
        }
        this.lastOutsideLoc.put(uuid, loc);
        if (!this.currentRes.containsKey(uuid) || ResOld != res) {
            this.currentRes.put(uuid, res);
            ResidenceChangedEvent chgEvent = new ResidenceChangedEvent(ResOld, res, player);
            this.plugin.getServ().getPluginManager().callEvent((Event) chgEvent);
        }
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        ClaimedResidence fromRes = this.plugin.getResidenceManager().getByLoc(from);
        ClaimedResidence toRes = this.plugin.getResidenceManager().getByLoc(to);
        if (fromRes != null && toRes != null && fromRes.equals(toRes)) {
            return;
        }
        if (event.getPlayer().hasMetadata("NPC")) {
            return;
        }
        ResidenceChangedEvent chgEvent = new ResidenceChangedEvent(fromRes, toRes, event.getPlayer());
        this.plugin.getServ().getPluginManager().callEvent((Event) chgEvent);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onResidenceChangeMessagePrint(ResidenceChangedEvent event) {
        ClaimedResidence res;
        Player player;
        ClaimedResidence from = event.getFrom();
        ClaimedResidence to = event.getTo();
        String message2 = null;
        ClaimedResidence claimedResidence = res = from == null ? to : from;
        if (from == null && to != null) {
            message2 = to.getEnterMessage();
            res = to;
        }
        if (from != null && to == null) {
            message2 = from.getLeaveMessage();
            res = from;
        }
        if (from != null && to != null) {
            message2 = to.getEnterMessage();
            res = to;
        }
        if ((player = event.getPlayer()).hasMetadata("NPC")) {
            return;
        }
        if (message2 != null) {
            if (this.plugin.getConfigManager().useTitleMessage()) {
                ActionBarTitleMessages.sendTitle(player, (Object) ChatColor.YELLOW + this.insertMessages(player, res, message2));
            }
            if (this.plugin.getConfigManager().useActionBar()) {
                ActionBarTitleMessages.send(player, (Object) ChatColor.YELLOW + this.insertMessages(player, res, message2));
            } else {
                this.plugin.msg(player, (Object) ChatColor.YELLOW + this.insertMessages(player, res, message2));
            }
        }
        if (from == null || res == null) {
            return;
        }
        if (res != from.getParent() && this.plugin.getConfigManager().isExtraEnterMessage() && !res.isOwner(player) && (this.plugin.getRentManager().isForRent(from) || this.plugin.getTransactionManager().isForSale(from))) {
            if (this.plugin.getRentManager().isForRent(from) && !this.plugin.getRentManager().isRented(from)) {
                RentableLand rentable = this.plugin.getRentManager().getRentableLand(from);
                if (rentable != null) {
                    ActionBarTitleMessages.send(player, this.plugin.msg(lm.Residence_CanBeRented, from.getName(), rentable.cost, rentable.days));
                }
            } else if (this.plugin.getTransactionManager().isForSale(from) && !res.isOwner(player)) {
                int sale = this.plugin.getTransactionManager().getSaleAmount(from);
                ActionBarTitleMessages.send(player, this.plugin.msg(lm.Residence_CanBeBought, from.getName(), sale));
            }
        }
    }

    private StuckInfo updateStuckTeleport(Player player, Location loc) {
        if (loc.getY() >= player.getLocation().getY()) {
            return null;
        }
        StuckInfo info2 = this.stuckTeleportCounter.get(player.getUniqueId());
        if (info2 == null) {
            info2 = new StuckInfo(player);
            this.stuckTeleportCounter.put(player.getUniqueId(), info2);
        }
        info2.updateLastTp();
        return info2;
    }

    public String insertMessages(Player player, ClaimedResidence res, String message2) {
        try {
            message2 = message2.replaceAll("%player", player.getName());
            message2 = message2.replaceAll("%owner", res.getPermissions().getOwner());
            message2 = message2.replaceAll("%residence", res.getName());
        } catch (Exception ex) {
            return "";
        }
        return message2;
    }

    public void doHeals() {
        if (!Flags.healing.isGlobalyEnabled()) {
            return;
        }
        try {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                double health;
                Player damage;
                ClaimedResidence res = this.getCurrentResidence(player.getUniqueId());
                if (res == null || !res.getPermissions().has(Flags.healing, false) || (health = (damage = player).getHealth()) >= damage.getMaxHealth() || player.isDead())
                    continue;
                player.setHealth(health + 1.0);
            }
        } catch (Exception player) {
            // empty catch block
        }
    }

    public void feed() {
        if (!Flags.feed.isGlobalyEnabled()) {
            return;
        }
        try {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                int food;
                ClaimedResidence res = this.getCurrentResidence(player.getUniqueId());
                if (res == null || !res.getPermissions().has(Flags.feed, false) || (food = player.getFoodLevel()) >= 20 || player.isDead())
                    continue;
                player.setFoodLevel(food + 1);
            }
        } catch (Exception player) {
            // empty catch block
        }
    }

    public void DespawnMobs() {
        if (!Flags.nomobs.isGlobalyEnabled()) {
            return;
        }
        try {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                ClaimedResidence res = this.getCurrentResidence(player.getUniqueId());
                if (res == null || !res.getPermissions().has(Flags.nomobs, false)) continue;
                List<Entity> entities = Bukkit.getServer().getWorld(res.getWorld()).getEntities();
                for (Entity ent : entities) {
                    if (!ResidenceEntityListener.isMonster(ent) || !res.containsLoc(ent.getLocation())) continue;
                    ent.remove();
                }
            }
        } catch (Exception player) {
            // empty catch block
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (this.plugin.isDisabledWorldListener(event.getPlayer().getWorld())) {
            return;
        }
        String pname = event.getPlayer().getName();
        if (!this.chatenabled || !this.playerToggleChat.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        ChatChannel channel = this.plugin.getChatManager().getPlayerChannel(pname);
        if (channel != null) {
            channel.chat(pname, event.getMessage());
        }
        event.setCancelled(true);
    }

    public void tooglePlayerResidenceChat(Player player, String residence) {
        this.playerToggleChat.add(player.getUniqueId());
        this.plugin.msg((CommandSender) player, lm.Chat_ChatChannelChange, residence);
    }

    @Deprecated
    public void removePlayerResidenceChat(String pname) {
        this.removePlayerResidenceChat(Bukkit.getPlayer((String) pname));
    }

    public void removePlayerResidenceChat(Player player) {
        if (player == null) {
            return;
        }
        this.playerToggleChat.remove(player.getUniqueId());
        this.plugin.msg((CommandSender) player, lm.Chat_ChatChannelLeave, new Object[0]);
    }

    public ClaimedResidence getCurrentResidence(UUID uuid) {
        return this.currentRes.get(uuid);
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[FlagPermissions.FlagState.values().length];
        try {
            arrn[FlagPermissions.FlagState.FALSE.ordinal()] = 2;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[FlagPermissions.FlagState.INVALID.ordinal()] = 4;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[FlagPermissions.FlagState.NEITHER.ordinal()] = 3;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[FlagPermissions.FlagState.TRUE.ordinal()] = 1;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState = arrn;
        return $SWITCH_TABLE$com$bekvon$bukkit$residence$protection$FlagPermissions$FlagState;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$cmiLib$ItemManager$CMIMaterial() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$cmiLib$ItemManager$CMIMaterial;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[ItemManager.CMIMaterial.values().length];
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_BOAT.ordinal()] = 2;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_BUTTON.ordinal()] = 3;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_DOOR.ordinal()] = 4;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_FENCE.ordinal()] = 5;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_FENCE_GATE.ordinal()] = 6;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_LEAVES.ordinal()] = 7;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_LOG.ordinal()] = 8;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_PLANKS.ordinal()] = 9;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_PRESSURE_PLATE.ordinal()] = 10;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_SAPLING.ordinal()] = 11;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_SLAB.ordinal()] = 12;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_STAIRS.ordinal()] = 13;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_TRAPDOOR.ordinal()] = 14;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACACIA_WOOD.ordinal()] = 15;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ACTIVATOR_RAIL.ordinal()] = 16;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.AIR.ordinal()] = 17;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ALLIUM.ordinal()] = 18;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ANDESITE.ordinal()] = 19;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ANVIL.ordinal()] = 20;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.APPLE.ordinal()] = 21;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ARMOR_STAND.ordinal()] = 22;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ARROW.ordinal()] = 23;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ATTACHED_MELON_STEM.ordinal()] = 24;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ATTACHED_PUMPKIN_STEM.ordinal()] = 25;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.AWKWARD_POTION.ordinal()] = 609;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.AZURE_BLUET.ordinal()] = 26;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BAKED_POTATO.ordinal()] = 27;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BARRIER.ordinal()] = 28;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BAT_SPAWN_EGG.ordinal()] = 29;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BEACON.ordinal()] = 30;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BEDROCK.ordinal()] = 31;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BEEF.ordinal()] = 32;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BEETROOT.ordinal()] = 33;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BEETROOTS.ordinal()] = 34;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BEETROOT_SEEDS.ordinal()] = 35;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BEETROOT_SOUP.ordinal()] = 36;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_BOAT.ordinal()] = 37;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_BUTTON.ordinal()] = 38;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_DOOR.ordinal()] = 39;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_FENCE.ordinal()] = 40;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_FENCE_GATE.ordinal()] = 41;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_LEAVES.ordinal()] = 42;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_LOG.ordinal()] = 43;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_PLANKS.ordinal()] = 44;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_PRESSURE_PLATE.ordinal()] = 45;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_SAPLING.ordinal()] = 46;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_SLAB.ordinal()] = 47;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_STAIRS.ordinal()] = 48;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_TRAPDOOR.ordinal()] = 49;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BIRCH_WOOD.ordinal()] = 50;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_BANNER.ordinal()] = 51;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_BED.ordinal()] = 52;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_CARPET.ordinal()] = 53;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_CONCRETE.ordinal()] = 54;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_CONCRETE_POWDER.ordinal()] = 55;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_GLAZED_TERRACOTTA.ordinal()] = 56;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_SHULKER_BOX.ordinal()] = 57;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_STAINED_GLASS.ordinal()] = 58;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_STAINED_GLASS_PANE.ordinal()] = 59;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_TERRACOTTA.ordinal()] = 60;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_WALL_BANNER.ordinal()] = 61;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLACK_WOOL.ordinal()] = 62;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLAZE_POWDER.ordinal()] = 63;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLAZE_ROD.ordinal()] = 64;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLAZE_SPAWN_EGG.ordinal()] = 65;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_BANNER.ordinal()] = 66;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_BED.ordinal()] = 67;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_CARPET.ordinal()] = 68;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_CONCRETE.ordinal()] = 69;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_CONCRETE_POWDER.ordinal()] = 70;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_GLAZED_TERRACOTTA.ordinal()] = 71;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_ICE.ordinal()] = 72;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_ORCHID.ordinal()] = 73;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_SHULKER_BOX.ordinal()] = 74;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_STAINED_GLASS.ordinal()] = 75;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_STAINED_GLASS_PANE.ordinal()] = 76;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_TERRACOTTA.ordinal()] = 77;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_WALL_BANNER.ordinal()] = 78;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BLUE_WOOL.ordinal()] = 79;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BONE.ordinal()] = 80;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BONE_BLOCK.ordinal()] = 81;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BONE_MEAL.ordinal()] = 82;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BOOK.ordinal()] = 83;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BOOKSHELF.ordinal()] = 84;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BOW.ordinal()] = 85;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BOWL.ordinal()] = 86;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BRAIN_CORAL.ordinal()] = 87;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BRAIN_CORAL_BLOCK.ordinal()] = 88;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BRAIN_CORAL_FAN.ordinal()] = 89;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BRAIN_CORAL_WALL_FAN.ordinal()] = 90;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BREAD.ordinal()] = 91;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BREWING_STAND.ordinal()] = 92;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BRICK.ordinal()] = 93;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BRICKS.ordinal()] = 94;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BRICK_SLAB.ordinal()] = 95;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BRICK_STAIRS.ordinal()] = 96;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_BANNER.ordinal()] = 97;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_BED.ordinal()] = 98;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_CARPET.ordinal()] = 99;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_CONCRETE.ordinal()] = 100;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_CONCRETE_POWDER.ordinal()] = 101;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_GLAZED_TERRACOTTA.ordinal()] = 102;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_MUSHROOM.ordinal()] = 103;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_MUSHROOM_BLOCK.ordinal()] = 104;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_SHULKER_BOX.ordinal()] = 105;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_STAINED_GLASS.ordinal()] = 106;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_STAINED_GLASS_PANE.ordinal()] = 107;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_TERRACOTTA.ordinal()] = 108;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_WALL_BANNER.ordinal()] = 109;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BROWN_WOOL.ordinal()] = 110;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BUBBLE_COLUMN.ordinal()] = 111;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BUBBLE_CORAL.ordinal()] = 112;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BUBBLE_CORAL_BLOCK.ordinal()] = 113;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BUBBLE_CORAL_FAN.ordinal()] = 114;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BUBBLE_CORAL_WALL_FAN.ordinal()] = 115;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.BUCKET.ordinal()] = 116;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CACTUS.ordinal()] = 117;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CACTUS_GREEN.ordinal()] = 118;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CAKE.ordinal()] = 119;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CARROT.ordinal()] = 120;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CARROTS.ordinal()] = 121;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CARROT_ON_A_STICK.ordinal()] = 122;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CARVED_PUMPKIN.ordinal()] = 123;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CAULDRON.ordinal()] = 124;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CAVE_AIR.ordinal()] = 125;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CAVE_SPIDER_SPAWN_EGG.ordinal()] = 126;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHAINMAIL_BOOTS.ordinal()] = 127;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHAINMAIL_CHESTPLATE.ordinal()] = 128;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHAINMAIL_HELMET.ordinal()] = 129;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHAINMAIL_LEGGINGS.ordinal()] = 130;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHAIN_COMMAND_BLOCK.ordinal()] = 131;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHARCOAL.ordinal()] = 132;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHEST.ordinal()] = 133;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHEST_MINECART.ordinal()] = 134;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHICKEN.ordinal()] = 135;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHICKEN_SPAWN_EGG.ordinal()] = 136;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHIPPED_ANVIL.ordinal()] = 137;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHISELED_QUARTZ_BLOCK.ordinal()] = 138;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHISELED_RED_SANDSTONE.ordinal()] = 139;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHISELED_SANDSTONE.ordinal()] = 140;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHISELED_STONE_BRICKS.ordinal()] = 141;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHORUS_FLOWER.ordinal()] = 142;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHORUS_FRUIT.ordinal()] = 143;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CHORUS_PLANT.ordinal()] = 144;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CLAY.ordinal()] = 145;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CLAY_BALL.ordinal()] = 146;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CLOCK.ordinal()] = 147;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COAL.ordinal()] = 148;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COAL_BLOCK.ordinal()] = 149;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COAL_ORE.ordinal()] = 150;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COARSE_DIRT.ordinal()] = 151;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COBBLESTONE.ordinal()] = 152;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COBBLESTONE_SLAB.ordinal()] = 153;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COBBLESTONE_STAIRS.ordinal()] = 154;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COBBLESTONE_WALL.ordinal()] = 155;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COBWEB.ordinal()] = 156;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COCOA.ordinal()] = 157;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COCOA_BEANS.ordinal()] = 158;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COD.ordinal()] = 159;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COD_BUCKET.ordinal()] = 160;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COD_SPAWN_EGG.ordinal()] = 161;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COMMAND_BLOCK.ordinal()] = 162;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COMMAND_BLOCK_MINECART.ordinal()] = 163;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COMPARATOR.ordinal()] = 164;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COMPASS.ordinal()] = 165;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CONDUIT.ordinal()] = 166;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COOKED_BEEF.ordinal()] = 167;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COOKED_CHICKEN.ordinal()] = 168;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COOKED_COD.ordinal()] = 169;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COOKED_MUTTON.ordinal()] = 170;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COOKED_PORKCHOP.ordinal()] = 171;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COOKED_RABBIT.ordinal()] = 172;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COOKED_SALMON.ordinal()] = 173;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COOKIE.ordinal()] = 174;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.COW_SPAWN_EGG.ordinal()] = 175;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CRACKED_STONE_BRICKS.ordinal()] = 176;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CRAFTING_TABLE.ordinal()] = 177;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CREEPER_HEAD.ordinal()] = 178;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CREEPER_SPAWN_EGG.ordinal()] = 179;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CREEPER_WALL_HEAD.ordinal()] = 180;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CUT_RED_SANDSTONE.ordinal()] = 181;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CUT_SANDSTONE.ordinal()] = 182;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_BANNER.ordinal()] = 183;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_BED.ordinal()] = 184;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_CARPET.ordinal()] = 185;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_CONCRETE.ordinal()] = 186;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_CONCRETE_POWDER.ordinal()] = 187;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_DYE.ordinal()] = 188;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_GLAZED_TERRACOTTA.ordinal()] = 189;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_SHULKER_BOX.ordinal()] = 190;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_STAINED_GLASS.ordinal()] = 191;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_STAINED_GLASS_PANE.ordinal()] = 192;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_TERRACOTTA.ordinal()] = 193;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_WALL_BANNER.ordinal()] = 194;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.CYAN_WOOL.ordinal()] = 195;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DAMAGED_ANVIL.ordinal()] = 196;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DANDELION.ordinal()] = 197;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DANDELION_YELLOW.ordinal()] = 198;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_BOAT.ordinal()] = 199;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_BUTTON.ordinal()] = 200;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_DOOR.ordinal()] = 201;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_FENCE.ordinal()] = 202;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_FENCE_GATE.ordinal()] = 203;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_LEAVES.ordinal()] = 204;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_LOG.ordinal()] = 205;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_PLANKS.ordinal()] = 206;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_PRESSURE_PLATE.ordinal()] = 207;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_SAPLING.ordinal()] = 208;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_SLAB.ordinal()] = 209;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_STAIRS.ordinal()] = 210;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_TRAPDOOR.ordinal()] = 211;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_OAK_WOOD.ordinal()] = 212;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_PRISMARINE.ordinal()] = 213;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_PRISMARINE_SLAB.ordinal()] = 214;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DARK_PRISMARINE_STAIRS.ordinal()] = 215;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DAYLIGHT_DETECTOR.ordinal()] = 216;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BRAIN_CORAL.ordinal()] = 217;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BRAIN_CORAL_BLOCK.ordinal()] = 218;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BRAIN_CORAL_FAN.ordinal()] = 219;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BRAIN_CORAL_WALL_FAN.ordinal()] = 220;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BUBBLE_CORAL.ordinal()] = 221;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BUBBLE_CORAL_BLOCK.ordinal()] = 222;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BUBBLE_CORAL_FAN.ordinal()] = 223;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BUBBLE_CORAL_WALL_FAN.ordinal()] = 224;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_BUSH.ordinal()] = 225;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_FIRE_CORAL.ordinal()] = 226;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_FIRE_CORAL_BLOCK.ordinal()] = 227;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_FIRE_CORAL_FAN.ordinal()] = 228;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_FIRE_CORAL_WALL_FAN.ordinal()] = 229;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_HORN_CORAL.ordinal()] = 230;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_HORN_CORAL_BLOCK.ordinal()] = 231;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_HORN_CORAL_FAN.ordinal()] = 232;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_HORN_CORAL_WALL_FAN.ordinal()] = 233;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_TUBE_CORAL.ordinal()] = 234;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_TUBE_CORAL_BLOCK.ordinal()] = 235;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_TUBE_CORAL_FAN.ordinal()] = 236;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEAD_TUBE_CORAL_WALL_FAN.ordinal()] = 237;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DEBUG_STICK.ordinal()] = 238;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DETECTOR_RAIL.ordinal()] = 239;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND.ordinal()] = 240;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_AXE.ordinal()] = 241;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_BLOCK.ordinal()] = 242;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_BOOTS.ordinal()] = 243;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_CHESTPLATE.ordinal()] = 244;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_HELMET.ordinal()] = 245;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_HOE.ordinal()] = 246;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_HORSE_ARMOR.ordinal()] = 247;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_LEGGINGS.ordinal()] = 248;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_ORE.ordinal()] = 249;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_PICKAXE.ordinal()] = 250;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_SHOVEL.ordinal()] = 251;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIAMOND_SWORD.ordinal()] = 252;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIORITE.ordinal()] = 253;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DIRT.ordinal()] = 254;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DISPENSER.ordinal()] = 255;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DOLPHIN_SPAWN_EGG.ordinal()] = 256;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DONKEY_SPAWN_EGG.ordinal()] = 257;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DRAGON_BREATH.ordinal()] = 258;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DRAGON_EGG.ordinal()] = 259;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DRAGON_HEAD.ordinal()] = 260;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DRAGON_WALL_HEAD.ordinal()] = 261;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DRIED_KELP.ordinal()] = 262;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DRIED_KELP_BLOCK.ordinal()] = 263;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DROPPER.ordinal()] = 264;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.DROWNED_SPAWN_EGG.ordinal()] = 265;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.EGG.ordinal()] = 266;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ELDER_GUARDIAN_SPAWN_EGG.ordinal()] = 267;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ELYTRA.ordinal()] = 268;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.EMERALD.ordinal()] = 269;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.EMERALD_BLOCK.ordinal()] = 270;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.EMERALD_ORE.ordinal()] = 271;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ENCHANTED_BOOK.ordinal()] = 272;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ENCHANTED_GOLDEN_APPLE.ordinal()] = 273;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ENCHANTING_TABLE.ordinal()] = 274;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ENDERMAN_SPAWN_EGG.ordinal()] = 275;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ENDERMITE_SPAWN_EGG.ordinal()] = 276;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ENDER_CHEST.ordinal()] = 277;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ENDER_EYE.ordinal()] = 278;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ENDER_PEARL.ordinal()] = 279;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.END_CRYSTAL.ordinal()] = 280;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.END_GATEWAY.ordinal()] = 281;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.END_PORTAL.ordinal()] = 282;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.END_PORTAL_FRAME.ordinal()] = 283;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.END_ROD.ordinal()] = 284;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.END_STONE.ordinal()] = 285;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.END_STONE_BRICKS.ordinal()] = 286;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.EVOKER_SPAWN_EGG.ordinal()] = 287;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.EXPERIENCE_BOTTLE.ordinal()] = 288;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FARMLAND.ordinal()] = 289;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FEATHER.ordinal()] = 290;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FERMENTED_SPIDER_EYE.ordinal()] = 291;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FERN.ordinal()] = 292;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FILLED_MAP.ordinal()] = 293;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIRE.ordinal()] = 294;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIREWORK_ROCKET.ordinal()] = 295;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIREWORK_STAR.ordinal()] = 296;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIRE_CHARGE.ordinal()] = 297;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIRE_CORAL.ordinal()] = 298;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIRE_CORAL_BLOCK.ordinal()] = 299;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIRE_CORAL_FAN.ordinal()] = 300;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIRE_CORAL_WALL_FAN.ordinal()] = 301;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIRE_RESISTANCE_POTION.ordinal()] = 614;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FIRE_RESISTANCE_POTION3.ordinal()] = 633;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FISHING_ROD.ordinal()] = 302;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FLINT.ordinal()] = 303;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FLINT_AND_STEEL.ordinal()] = 304;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FLOWER_POT.ordinal()] = 305;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FROSTED_ICE.ordinal()] = 306;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FURNACE.ordinal()] = 307;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.FURNACE_MINECART.ordinal()] = 308;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GHAST_SPAWN_EGG.ordinal()] = 309;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GHAST_TEAR.ordinal()] = 310;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GLASS.ordinal()] = 311;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GLASS_BOTTLE.ordinal()] = 312;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GLASS_PANE.ordinal()] = 313;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GLISTERING_MELON_SLICE.ordinal()] = 314;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GLOWSTONE.ordinal()] = 315;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GLOWSTONE_DUST.ordinal()] = 316;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_APPLE.ordinal()] = 317;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_AXE.ordinal()] = 318;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_BOOTS.ordinal()] = 319;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_CARROT.ordinal()] = 320;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_CHESTPLATE.ordinal()] = 321;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_HELMET.ordinal()] = 322;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_HOE.ordinal()] = 323;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_HORSE_ARMOR.ordinal()] = 324;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_LEGGINGS.ordinal()] = 325;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_PICKAXE.ordinal()] = 326;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_SHOVEL.ordinal()] = 327;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLDEN_SWORD.ordinal()] = 328;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLD_BLOCK.ordinal()] = 329;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLD_INGOT.ordinal()] = 330;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLD_NUGGET.ordinal()] = 331;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GOLD_ORE.ordinal()] = 332;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRANITE.ordinal()] = 333;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRASS.ordinal()] = 334;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRASS_BLOCK.ordinal()] = 335;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRASS_PATH.ordinal()] = 336;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAVEL.ordinal()] = 337;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_BANNER.ordinal()] = 338;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_BED.ordinal()] = 339;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_CARPET.ordinal()] = 340;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_CONCRETE.ordinal()] = 341;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_CONCRETE_POWDER.ordinal()] = 342;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_DYE.ordinal()] = 343;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_GLAZED_TERRACOTTA.ordinal()] = 344;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_SHULKER_BOX.ordinal()] = 345;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_STAINED_GLASS.ordinal()] = 346;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_STAINED_GLASS_PANE.ordinal()] = 347;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_TERRACOTTA.ordinal()] = 348;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_WALL_BANNER.ordinal()] = 349;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GRAY_WOOL.ordinal()] = 350;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_BANNER.ordinal()] = 351;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_BED.ordinal()] = 352;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_CARPET.ordinal()] = 353;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_CONCRETE.ordinal()] = 354;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_CONCRETE_POWDER.ordinal()] = 355;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_GLAZED_TERRACOTTA.ordinal()] = 356;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_SHULKER_BOX.ordinal()] = 357;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_STAINED_GLASS.ordinal()] = 358;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_STAINED_GLASS_PANE.ordinal()] = 359;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_TERRACOTTA.ordinal()] = 360;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_WALL_BANNER.ordinal()] = 361;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GREEN_WOOL.ordinal()] = 362;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GUARDIAN_SPAWN_EGG.ordinal()] = 363;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.GUNPOWDER.ordinal()] = 364;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HARMING_POTION.ordinal()] = 621;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HARMING_POTION2.ordinal()] = 630;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HAY_BLOCK.ordinal()] = 365;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HEALING_POTION.ordinal()] = 616;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HEALING_POTION2.ordinal()] = 627;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HEART_OF_THE_SEA.ordinal()] = 366;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.ordinal()] = 367;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HOPPER.ordinal()] = 368;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HOPPER_MINECART.ordinal()] = 369;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HORN_CORAL.ordinal()] = 370;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HORN_CORAL_BLOCK.ordinal()] = 371;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HORN_CORAL_FAN.ordinal()] = 372;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HORN_CORAL_WALL_FAN.ordinal()] = 373;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HORSE_SPAWN_EGG.ordinal()] = 374;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.HUSK_SPAWN_EGG.ordinal()] = 375;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ICE.ordinal()] = 376;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INFESTED_CHISELED_STONE_BRICKS.ordinal()] = 377;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INFESTED_COBBLESTONE.ordinal()] = 378;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INFESTED_CRACKED_STONE_BRICKS.ordinal()] = 379;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INFESTED_MOSSY_STONE_BRICKS.ordinal()] = 380;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INFESTED_STONE.ordinal()] = 381;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INFESTED_STONE_BRICKS.ordinal()] = 382;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INK_SAC.ordinal()] = 383;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INVISIBILITY_POTION.ordinal()] = 623;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.INVISIBILITY_POTION2.ordinal()] = 641;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_AXE.ordinal()] = 384;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_BARS.ordinal()] = 385;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_BLOCK.ordinal()] = 386;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_BOOTS.ordinal()] = 387;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_CHESTPLATE.ordinal()] = 388;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_DOOR.ordinal()] = 389;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_HELMET.ordinal()] = 390;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_HOE.ordinal()] = 391;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_HORSE_ARMOR.ordinal()] = 392;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_INGOT.ordinal()] = 393;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_LEGGINGS.ordinal()] = 394;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_NUGGET.ordinal()] = 395;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_ORE.ordinal()] = 396;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_PICKAXE.ordinal()] = 397;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_SHOVEL.ordinal()] = 398;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_SWORD.ordinal()] = 399;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.IRON_TRAPDOOR.ordinal()] = 400;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ITEM_FRAME.ordinal()] = 401;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JACK_O_LANTERN.ordinal()] = 402;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUKEBOX.ordinal()] = 403;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_BOAT.ordinal()] = 404;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_BUTTON.ordinal()] = 405;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_DOOR.ordinal()] = 406;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_FENCE.ordinal()] = 407;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_FENCE_GATE.ordinal()] = 408;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_LEAVES.ordinal()] = 409;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_LOG.ordinal()] = 410;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_PLANKS.ordinal()] = 411;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_PRESSURE_PLATE.ordinal()] = 412;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_SAPLING.ordinal()] = 413;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_SLAB.ordinal()] = 414;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_STAIRS.ordinal()] = 415;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_TRAPDOOR.ordinal()] = 416;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.JUNGLE_WOOD.ordinal()] = 417;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.KELP.ordinal()] = 418;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.KELP_PLANT.ordinal()] = 419;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.KNOWLEDGE_BOOK.ordinal()] = 420;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LADDER.ordinal()] = 421;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LAPIS_BLOCK.ordinal()] = 422;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LAPIS_LAZULI.ordinal()] = 423;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LAPIS_ORE.ordinal()] = 424;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LARGE_FERN.ordinal()] = 425;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LAVA.ordinal()] = 426;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LAVA_BUCKET.ordinal()] = 427;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEAD.ordinal()] = 428;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEAPING_POTION2.ordinal()] = 629;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEAPING_POTION3.ordinal()] = 639;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEATHER.ordinal()] = 429;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEATHER_BOOTS.ordinal()] = 430;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEATHER_CHESTPLATE.ordinal()] = 431;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEATHER_HELMET.ordinal()] = 432;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEATHER_LEGGINGS.ordinal()] = 433;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_ACACIA_DOOR.ordinal()] = 922;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_BIRCH_DOOR.ordinal()] = 920;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_BURNING_FURNACE.ordinal()] = 911;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_DARK_OAK_DOOR.ordinal()] = 923;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_GLOWING_REDSTON_ORE.ordinal()] = 913;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_IRON_DOOR_BLOCK.ordinal()] = 917;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_JUNGLE_DOOR.ordinal()] = 921;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_NETHER_WARTS.ordinal()] = 912;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_RAW_FISH.ordinal()] = 915;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_SKULL.ordinal()] = 916;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_SPRUCE_DOOR.ordinal()] = 919;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_STATIONARY_LAVA.ordinal()] = 910;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_STATIONARY_WATER.ordinal()] = 909;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_SUGAR_CANE_BLOCK.ordinal()] = 914;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_WHEAT.ordinal()] = 924;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEGACY_WOODEN_DOOR.ordinal()] = 918;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LEVER.ordinal()] = 434;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_BANNER.ordinal()] = 435;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_BED.ordinal()] = 436;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_CARPET.ordinal()] = 437;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_CONCRETE.ordinal()] = 438;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_CONCRETE_POWDER.ordinal()] = 439;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_DYE.ordinal()] = 440;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_GLAZED_TERRACOTTA.ordinal()] = 441;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_SHULKER_BOX.ordinal()] = 442;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_STAINED_GLASS.ordinal()] = 443;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_STAINED_GLASS_PANE.ordinal()] = 444;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_TERRACOTTA.ordinal()] = 445;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_WALL_BANNER.ordinal()] = 446;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_BLUE_WOOL.ordinal()] = 447;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_BANNER.ordinal()] = 448;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_BED.ordinal()] = 449;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_CARPET.ordinal()] = 450;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_CONCRETE.ordinal()] = 451;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_CONCRETE_POWDER.ordinal()] = 452;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_DYE.ordinal()] = 453;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_GLAZED_TERRACOTTA.ordinal()] = 454;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_SHULKER_BOX.ordinal()] = 455;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_STAINED_GLASS.ordinal()] = 456;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_STAINED_GLASS_PANE.ordinal()] = 457;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_TERRACOTTA.ordinal()] = 458;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_WALL_BANNER.ordinal()] = 459;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_GRAY_WOOL.ordinal()] = 460;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE.ordinal()] = 461;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LILAC.ordinal()] = 462;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LILY_PAD.ordinal()] = 463;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_BANNER.ordinal()] = 464;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_BED.ordinal()] = 465;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_CARPET.ordinal()] = 466;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_CONCRETE.ordinal()] = 467;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_CONCRETE_POWDER.ordinal()] = 468;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_DYE.ordinal()] = 469;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_GLAZED_TERRACOTTA.ordinal()] = 470;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_SHULKER_BOX.ordinal()] = 471;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_STAINED_GLASS.ordinal()] = 472;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_STAINED_GLASS_PANE.ordinal()] = 473;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_TERRACOTTA.ordinal()] = 474;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_WALL_BANNER.ordinal()] = 475;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LIME_WOOL.ordinal()] = 476;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LINGERING_POTION.ordinal()] = 477;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.LLAMA_SPAWN_EGG.ordinal()] = 478;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_BANNER.ordinal()] = 479;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_BED.ordinal()] = 480;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_CARPET.ordinal()] = 481;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_CONCRETE.ordinal()] = 482;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_CONCRETE_POWDER.ordinal()] = 483;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_DYE.ordinal()] = 484;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_GLAZED_TERRACOTTA.ordinal()] = 485;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_SHULKER_BOX.ordinal()] = 486;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_STAINED_GLASS.ordinal()] = 487;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_STAINED_GLASS_PANE.ordinal()] = 488;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_TERRACOTTA.ordinal()] = 489;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_WALL_BANNER.ordinal()] = 490;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGENTA_WOOL.ordinal()] = 491;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGMA_BLOCK.ordinal()] = 492;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGMA_CREAM.ordinal()] = 493;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAGMA_CUBE_SPAWN_EGG.ordinal()] = 494;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MAP.ordinal()] = 495;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MELON.ordinal()] = 496;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MELON_SEEDS.ordinal()] = 497;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MELON_SLICE.ordinal()] = 498;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MELON_STEM.ordinal()] = 499;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MILK_BUCKET.ordinal()] = 500;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MINECART.ordinal()] = 501;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MOOSHROOM_SPAWN_EGG.ordinal()] = 502;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MOSSY_COBBLESTONE.ordinal()] = 503;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MOSSY_COBBLESTONE_WALL.ordinal()] = 504;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MOSSY_STONE_BRICKS.ordinal()] = 505;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MOVING_PISTON.ordinal()] = 506;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MULE_SPAWN_EGG.ordinal()] = 507;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUNDANE_POTION.ordinal()] = 611;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSHROOM_STEM.ordinal()] = 508;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSHROOM_STEW.ordinal()] = 509;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_11.ordinal()] = 510;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_13.ordinal()] = 511;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_BLOCKS.ordinal()] = 512;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_CAT.ordinal()] = 513;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_CHIRP.ordinal()] = 514;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_FAR.ordinal()] = 515;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_MALL.ordinal()] = 516;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_MELLOHI.ordinal()] = 517;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_STAL.ordinal()] = 518;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_STRAD.ordinal()] = 519;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_WAIT.ordinal()] = 520;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUSIC_DISC_WARD.ordinal()] = 521;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MUTTON.ordinal()] = 522;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.MYCELIUM.ordinal()] = 523;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NAME_TAG.ordinal()] = 524;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NAUTILUS_SHELL.ordinal()] = 525;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHERRACK.ordinal()] = 526;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_BRICK.ordinal()] = 527;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_BRICKS.ordinal()] = 528;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_BRICK_FENCE.ordinal()] = 529;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_BRICK_SLAB.ordinal()] = 530;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_BRICK_STAIRS.ordinal()] = 531;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_PORTAL.ordinal()] = 532;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_QUARTZ_ORE.ordinal()] = 533;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_STAR.ordinal()] = 534;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_WART.ordinal()] = 535;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NETHER_WART_BLOCK.ordinal()] = 536;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NIGHT_VISION_POTION.ordinal()] = 617;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NIGHT_VISION_POTION2.ordinal()] = 635;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NONE.ordinal()] = 1;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.NOTE_BLOCK.ordinal()] = 537;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_BOAT.ordinal()] = 538;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_BUTTON.ordinal()] = 539;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_DOOR.ordinal()] = 540;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_FENCE.ordinal()] = 541;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_FENCE_GATE.ordinal()] = 542;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_LEAVES.ordinal()] = 543;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_LOG.ordinal()] = 544;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_PLANKS.ordinal()] = 545;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_PRESSURE_PLATE.ordinal()] = 546;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_SAPLING.ordinal()] = 547;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_SLAB.ordinal()] = 548;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_STAIRS.ordinal()] = 549;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_TRAPDOOR.ordinal()] = 550;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OAK_WOOD.ordinal()] = 551;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OBSERVER.ordinal()] = 552;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OBSIDIAN.ordinal()] = 553;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OCELOT_SPAWN_EGG.ordinal()] = 554;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_BANNER.ordinal()] = 555;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_BED.ordinal()] = 556;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_CARPET.ordinal()] = 557;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_CONCRETE.ordinal()] = 558;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_CONCRETE_POWDER.ordinal()] = 559;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_DYE.ordinal()] = 560;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_GLAZED_TERRACOTTA.ordinal()] = 561;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_SHULKER_BOX.ordinal()] = 562;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_STAINED_GLASS.ordinal()] = 563;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_STAINED_GLASS_PANE.ordinal()] = 564;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_TERRACOTTA.ordinal()] = 565;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_TULIP.ordinal()] = 566;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_WALL_BANNER.ordinal()] = 567;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ORANGE_WOOL.ordinal()] = 568;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.OXEYE_DAISY.ordinal()] = 569;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PACKED_ICE.ordinal()] = 570;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PAINTING.ordinal()] = 571;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PAPER.ordinal()] = 572;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PARROT_SPAWN_EGG.ordinal()] = 573;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PEONY.ordinal()] = 574;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PETRIFIED_OAK_SLAB.ordinal()] = 575;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PHANTOM_MEMBRANE.ordinal()] = 576;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PHANTOM_SPAWN_EGG.ordinal()] = 577;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PIG_SPAWN_EGG.ordinal()] = 578;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_BANNER.ordinal()] = 579;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_BED.ordinal()] = 580;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_CARPET.ordinal()] = 581;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_CONCRETE.ordinal()] = 582;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_CONCRETE_POWDER.ordinal()] = 583;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_DYE.ordinal()] = 584;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_GLAZED_TERRACOTTA.ordinal()] = 585;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_SHULKER_BOX.ordinal()] = 586;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_STAINED_GLASS.ordinal()] = 587;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_STAINED_GLASS_PANE.ordinal()] = 588;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_TERRACOTTA.ordinal()] = 589;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_TULIP.ordinal()] = 590;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_WALL_BANNER.ordinal()] = 591;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PINK_WOOL.ordinal()] = 592;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PISTON.ordinal()] = 593;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PISTON_HEAD.ordinal()] = 594;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PLAYER_HEAD.ordinal()] = 595;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PLAYER_WALL_HEAD.ordinal()] = 596;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PODZOL.ordinal()] = 597;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POISONOUS_POTATO.ordinal()] = 598;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POISON_POTION.ordinal()] = 615;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POISON_POTION2.ordinal()] = 626;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POISON_POTION3.ordinal()] = 634;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POISON_POTION4.ordinal()] = 644;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POLAR_BEAR_SPAWN_EGG.ordinal()] = 599;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POLISHED_ANDESITE.ordinal()] = 600;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POLISHED_DIORITE.ordinal()] = 601;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POLISHED_GRANITE.ordinal()] = 602;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POPPED_CHORUS_FRUIT.ordinal()] = 603;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POPPY.ordinal()] = 604;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PORKCHOP.ordinal()] = 605;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTATO.ordinal()] = 606;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTATOES.ordinal()] = 607;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTION.ordinal()] = 608;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_ACACIA_SAPLING.ordinal()] = 646;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_ALLIUM.ordinal()] = 647;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_AZURE_BLUET.ordinal()] = 648;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_BIRCH_SAPLING.ordinal()] = 649;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_BLUE_ORCHID.ordinal()] = 650;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_BROWN_MUSHROOM.ordinal()] = 651;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_CACTUS.ordinal()] = 652;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_DANDELION.ordinal()] = 653;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_DARK_OAK_SAPLING.ordinal()] = 654;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_DEAD_BUSH.ordinal()] = 655;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_FERN.ordinal()] = 656;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_JUNGLE_SAPLING.ordinal()] = 657;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_OAK_SAPLING.ordinal()] = 658;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_ORANGE_TULIP.ordinal()] = 659;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_OXEYE_DAISY.ordinal()] = 660;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_PINK_TULIP.ordinal()] = 661;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_POPPY.ordinal()] = 662;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_RED_MUSHROOM.ordinal()] = 663;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_RED_TULIP.ordinal()] = 664;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_SPRUCE_SAPLING.ordinal()] = 665;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POTTED_WHITE_TULIP.ordinal()] = 666;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.POWERED_RAIL.ordinal()] = 667;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PRISMARINE.ordinal()] = 668;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PRISMARINE_BRICKS.ordinal()] = 669;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PRISMARINE_BRICK_SLAB.ordinal()] = 670;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PRISMARINE_BRICK_STAIRS.ordinal()] = 671;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PRISMARINE_CRYSTALS.ordinal()] = 672;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PRISMARINE_SHARD.ordinal()] = 673;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PRISMARINE_SLAB.ordinal()] = 674;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PRISMARINE_STAIRS.ordinal()] = 675;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PUFFERFISH.ordinal()] = 676;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PUFFERFISH_BUCKET.ordinal()] = 677;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PUFFERFISH_SPAWN_EGG.ordinal()] = 678;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PUMPKIN.ordinal()] = 679;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PUMPKIN_PIE.ordinal()] = 680;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PUMPKIN_SEEDS.ordinal()] = 681;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PUMPKIN_STEM.ordinal()] = 682;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_BANNER.ordinal()] = 683;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_BED.ordinal()] = 684;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_CARPET.ordinal()] = 685;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_CONCRETE.ordinal()] = 686;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_CONCRETE_POWDER.ordinal()] = 687;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_DYE.ordinal()] = 688;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_GLAZED_TERRACOTTA.ordinal()] = 689;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_SHULKER_BOX.ordinal()] = 690;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_STAINED_GLASS.ordinal()] = 691;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_STAINED_GLASS_PANE.ordinal()] = 692;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_TERRACOTTA.ordinal()] = 693;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_WALL_BANNER.ordinal()] = 694;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPLE_WOOL.ordinal()] = 695;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPUR_BLOCK.ordinal()] = 696;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPUR_PILLAR.ordinal()] = 697;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPUR_SLAB.ordinal()] = 698;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.PURPUR_STAIRS.ordinal()] = 699;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.QUARTZ.ordinal()] = 700;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.QUARTZ_BLOCK.ordinal()] = 701;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.QUARTZ_PILLAR.ordinal()] = 702;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.QUARTZ_SLAB.ordinal()] = 703;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.QUARTZ_STAIRS.ordinal()] = 704;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RABBIT.ordinal()] = 705;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RABBIT_FOOT.ordinal()] = 706;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RABBIT_HIDE.ordinal()] = 707;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RABBIT_SPAWN_EGG.ordinal()] = 708;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RABBIT_STEW.ordinal()] = 709;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RAIL.ordinal()] = 710;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REDSTONE.ordinal()] = 711;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REDSTONE_BLOCK.ordinal()] = 712;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REDSTONE_LAMP.ordinal()] = 713;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REDSTONE_ORE.ordinal()] = 714;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REDSTONE_TORCH.ordinal()] = 715;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REDSTONE_WALL_TORCH.ordinal()] = 716;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REDSTONE_WIRE.ordinal()] = 717;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_BANNER.ordinal()] = 718;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_BED.ordinal()] = 719;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_CARPET.ordinal()] = 720;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_CONCRETE.ordinal()] = 721;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_CONCRETE_POWDER.ordinal()] = 722;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_GLAZED_TERRACOTTA.ordinal()] = 723;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_MUSHROOM.ordinal()] = 724;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_MUSHROOM_BLOCK.ordinal()] = 725;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_NETHER_BRICKS.ordinal()] = 726;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_SAND.ordinal()] = 727;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_SANDSTONE.ordinal()] = 728;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_SANDSTONE_SLAB.ordinal()] = 729;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_SANDSTONE_STAIRS.ordinal()] = 730;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_SHULKER_BOX.ordinal()] = 731;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_STAINED_GLASS.ordinal()] = 732;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_STAINED_GLASS_PANE.ordinal()] = 733;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_TERRACOTTA.ordinal()] = 734;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_TULIP.ordinal()] = 735;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_WALL_BANNER.ordinal()] = 736;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.RED_WOOL.ordinal()] = 737;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REGENERATION_POTION.ordinal()] = 612;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REGENERATION_POTION2.ordinal()] = 624;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REGENERATION_POTION3.ordinal()] = 631;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REGENERATION_POTION4.ordinal()] = 642;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REPEATER.ordinal()] = 738;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.REPEATING_COMMAND_BLOCK.ordinal()] = 739;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ROSE_BUSH.ordinal()] = 740;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ROSE_RED.ordinal()] = 741;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ROTTEN_FLESH.ordinal()] = 742;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SADDLE.ordinal()] = 743;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SALMON.ordinal()] = 744;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SALMON_BUCKET.ordinal()] = 745;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SALMON_SPAWN_EGG.ordinal()] = 746;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SAND.ordinal()] = 747;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SANDSTONE.ordinal()] = 748;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SANDSTONE_SLAB.ordinal()] = 749;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SANDSTONE_STAIRS.ordinal()] = 750;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SCUTE.ordinal()] = 751;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SEAGRASS.ordinal()] = 752;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SEA_LANTERN.ordinal()] = 753;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SEA_PICKLE.ordinal()] = 754;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SHEARS.ordinal()] = 755;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SHEEP_SPAWN_EGG.ordinal()] = 756;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SHIELD.ordinal()] = 757;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SHULKER_BOX.ordinal()] = 758;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SHULKER_SHELL.ordinal()] = 759;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SHULKER_SPAWN_EGG.ordinal()] = 760;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SIGN.ordinal()] = 761;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SILVERFISH_SPAWN_EGG.ordinal()] = 762;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SKELETON_HORSE_SPAWN_EGG.ordinal()] = 763;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SKELETON_SKULL.ordinal()] = 764;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SKELETON_SPAWN_EGG.ordinal()] = 765;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SKELETON_WALL_SKULL.ordinal()] = 766;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SLIME_BALL.ordinal()] = 767;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SLIME_BLOCK.ordinal()] = 768;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SLIME_SPAWN_EGG.ordinal()] = 769;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SLOWNESS_POTION.ordinal()] = 620;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SLOWNESS_POTION2.ordinal()] = 638;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SMOOTH_QUARTZ.ordinal()] = 770;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SMOOTH_RED_SANDSTONE.ordinal()] = 771;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SMOOTH_SANDSTONE.ordinal()] = 772;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SMOOTH_STONE.ordinal()] = 773;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SNOW.ordinal()] = 774;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SNOWBALL.ordinal()] = 775;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SNOW_BLOCK.ordinal()] = 776;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SOUL_SAND.ordinal()] = 777;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPAWNER.ordinal()] = 778;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPECTRAL_ARROW.ordinal()] = 779;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPIDER_EYE.ordinal()] = 780;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPIDER_SPAWN_EGG.ordinal()] = 781;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPLASH_POTION.ordinal()] = 782;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPONGE.ordinal()] = 783;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_BOAT.ordinal()] = 784;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_BUTTON.ordinal()] = 785;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_DOOR.ordinal()] = 786;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_FENCE.ordinal()] = 787;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_FENCE_GATE.ordinal()] = 788;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_LEAVES.ordinal()] = 789;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_LOG.ordinal()] = 790;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_PLANKS.ordinal()] = 791;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_PRESSURE_PLATE.ordinal()] = 792;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_SAPLING.ordinal()] = 793;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_SLAB.ordinal()] = 794;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_STAIRS.ordinal()] = 795;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_TRAPDOOR.ordinal()] = 796;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SPRUCE_WOOD.ordinal()] = 797;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SQUID_SPAWN_EGG.ordinal()] = 798;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STICK.ordinal()] = 799;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STICKY_PISTON.ordinal()] = 800;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE.ordinal()] = 801;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_AXE.ordinal()] = 802;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_BRICKS.ordinal()] = 803;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_BRICK_SLAB.ordinal()] = 804;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_BRICK_STAIRS.ordinal()] = 805;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_BUTTON.ordinal()] = 806;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_HOE.ordinal()] = 807;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_PICKAXE.ordinal()] = 808;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_PRESSURE_PLATE.ordinal()] = 809;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_SHOVEL.ordinal()] = 810;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_SLAB.ordinal()] = 811;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STONE_SWORD.ordinal()] = 812;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRAY_SPAWN_EGG.ordinal()] = 813;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRENGTH_POTION.ordinal()] = 619;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRENGTH_POTION2.ordinal()] = 628;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRENGTH_POTION3.ordinal()] = 637;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRENGTH_POTION4.ordinal()] = 645;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRING.ordinal()] = 814;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_ACACIA_LOG.ordinal()] = 815;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_ACACIA_WOOD.ordinal()] = 816;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_BIRCH_LOG.ordinal()] = 817;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_BIRCH_WOOD.ordinal()] = 818;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_DARK_OAK_LOG.ordinal()] = 819;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_DARK_OAK_WOOD.ordinal()] = 820;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_JUNGLE_LOG.ordinal()] = 821;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_JUNGLE_WOOD.ordinal()] = 822;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_OAK_LOG.ordinal()] = 823;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_OAK_WOOD.ordinal()] = 824;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_SPRUCE_LOG.ordinal()] = 825;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRIPPED_SPRUCE_WOOD.ordinal()] = 826;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRUCTURE_BLOCK.ordinal()] = 827;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.STRUCTURE_VOID.ordinal()] = 828;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SUGAR.ordinal()] = 829;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SUGAR_CANE.ordinal()] = 830;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SUNFLOWER.ordinal()] = 831;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SWIFTNESS_POTION.ordinal()] = 613;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SWIFTNESS_POTION2.ordinal()] = 625;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SWIFTNESS_POTION3.ordinal()] = 632;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.SWIFTNESS_POTION4.ordinal()] = 643;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TALL_GRASS.ordinal()] = 832;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TALL_SEAGRASS.ordinal()] = 833;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TERRACOTTA.ordinal()] = 834;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.THICK_POTION.ordinal()] = 610;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TIPPED_ARROW.ordinal()] = 835;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TNT.ordinal()] = 836;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TNT_MINECART.ordinal()] = 837;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TORCH.ordinal()] = 838;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TOTEM_OF_UNDYING.ordinal()] = 839;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TRAPPED_CHEST.ordinal()] = 840;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TRIDENT.ordinal()] = 841;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TRIPWIRE.ordinal()] = 842;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TRIPWIRE_HOOK.ordinal()] = 843;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TROPICAL_FISH.ordinal()] = 844;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TROPICAL_FISH_BUCKET.ordinal()] = 845;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TROPICAL_FISH_SPAWN_EGG.ordinal()] = 846;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TUBE_CORAL.ordinal()] = 847;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TUBE_CORAL_BLOCK.ordinal()] = 848;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TUBE_CORAL_FAN.ordinal()] = 849;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TUBE_CORAL_WALL_FAN.ordinal()] = 850;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TURTLE_EGG.ordinal()] = 851;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TURTLE_HELMET.ordinal()] = 852;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.TURTLE_SPAWN_EGG.ordinal()] = 853;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.VEX_SPAWN_EGG.ordinal()] = 854;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.VILLAGER_SPAWN_EGG.ordinal()] = 855;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.VINDICATOR_SPAWN_EGG.ordinal()] = 856;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.VINE.ordinal()] = 857;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.VOID_AIR.ordinal()] = 858;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WALL_SIGN.ordinal()] = 859;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WALL_TORCH.ordinal()] = 860;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WATER.ordinal()] = 861;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WATER_BREATHING_POTION.ordinal()] = 622;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WATER_BREATHING_POTION2.ordinal()] = 640;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WATER_BUCKET.ordinal()] = 862;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WEAKNESS_POTION.ordinal()] = 618;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WEAKNESS_POTION2.ordinal()] = 636;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WET_SPONGE.ordinal()] = 863;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHEAT.ordinal()] = 864;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHEAT_SEEDS.ordinal()] = 865;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_BANNER.ordinal()] = 866;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_BED.ordinal()] = 867;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_CARPET.ordinal()] = 868;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_CONCRETE.ordinal()] = 869;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_CONCRETE_POWDER.ordinal()] = 870;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_GLAZED_TERRACOTTA.ordinal()] = 871;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_SHULKER_BOX.ordinal()] = 872;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_STAINED_GLASS.ordinal()] = 873;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_STAINED_GLASS_PANE.ordinal()] = 874;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_TERRACOTTA.ordinal()] = 875;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_TULIP.ordinal()] = 876;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_WALL_BANNER.ordinal()] = 877;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WHITE_WOOL.ordinal()] = 878;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WITCH_SPAWN_EGG.ordinal()] = 879;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WITHER_SKELETON_SKULL.ordinal()] = 880;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WITHER_SKELETON_SPAWN_EGG.ordinal()] = 881;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WITHER_SKELETON_WALL_SKULL.ordinal()] = 882;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WOLF_SPAWN_EGG.ordinal()] = 883;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WOODEN_AXE.ordinal()] = 884;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WOODEN_HOE.ordinal()] = 885;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WOODEN_PICKAXE.ordinal()] = 886;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WOODEN_SHOVEL.ordinal()] = 887;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WOODEN_SWORD.ordinal()] = 888;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WRITABLE_BOOK.ordinal()] = 889;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.WRITTEN_BOOK.ordinal()] = 890;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_BANNER.ordinal()] = 891;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_BED.ordinal()] = 892;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_CARPET.ordinal()] = 893;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_CONCRETE.ordinal()] = 894;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_CONCRETE_POWDER.ordinal()] = 895;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_GLAZED_TERRACOTTA.ordinal()] = 896;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_SHULKER_BOX.ordinal()] = 897;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_STAINED_GLASS.ordinal()] = 898;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_STAINED_GLASS_PANE.ordinal()] = 899;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_TERRACOTTA.ordinal()] = 900;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_WALL_BANNER.ordinal()] = 901;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.YELLOW_WOOL.ordinal()] = 902;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ZOMBIE_HEAD.ordinal()] = 903;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ZOMBIE_HORSE_SPAWN_EGG.ordinal()] = 904;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ZOMBIE_PIGMAN_SPAWN_EGG.ordinal()] = 905;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ZOMBIE_SPAWN_EGG.ordinal()] = 906;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ZOMBIE_VILLAGER_SPAWN_EGG.ordinal()] = 907;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[ItemManager.CMIMaterial.ZOMBIE_WALL_HEAD.ordinal()] = 908;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        $SWITCH_TABLE$cmiLib$ItemManager$CMIMaterial = arrn;
        return $SWITCH_TABLE$cmiLib$ItemManager$CMIMaterial;
    }

}

