//
// Decompiled by Procyon v0.5.30
//

package me.libraryaddict.disguise;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.libraryaddict.disguise.disguisetypes.TargetedDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import me.libraryaddict.disguise.events.DisguiseEvent;
import me.libraryaddict.disguise.utilities.DisguiseUtilities;
import me.libraryaddict.disguise.utilities.LibsProfileLookup;
import me.libraryaddict.disguise.utilities.ReflectionManager;
import me.libraryaddict.disguise.utilities.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class DisguiseListener implements Listener {
    private String currentVersion;
    private HashMap<String, Boolean[]> disguiseClone;
    private HashMap<String, Disguise> disguiseEntity;
    private HashMap<String, BukkitRunnable> disguiseRunnable;
    private String latestVersion;
    private LibsDisguises plugin;
    private BukkitTask updaterTask;

    public DisguiseListener(final LibsDisguises libsDisguises) {
        this.disguiseClone = new HashMap<String, Boolean[]>();
        this.disguiseEntity = new HashMap<String, Disguise>();
        this.disguiseRunnable = new HashMap<String, BukkitRunnable>();
        this.plugin = libsDisguises;
        if (this.plugin.getConfig().getBoolean("NotifyUpdate")) {
            this.currentVersion = this.plugin.getDescription().getVersion();
            this.updaterTask = Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin) this.plugin, (Runnable) new Runnable() {
                @Override
                public void run() {
                    try {
                        final UpdateChecker updateChecker = new UpdateChecker();
                        updateChecker.checkUpdate("v" + DisguiseListener.this.currentVersion);
                        DisguiseListener.this.latestVersion = updateChecker.getLatestVersion();
                        if (DisguiseListener.this.latestVersion == null) {
                            return;
                        }
                        DisguiseListener.this.latestVersion = "v" + DisguiseListener.this.latestVersion;
                        Bukkit.getScheduler().runTask((Plugin) DisguiseListener.this.plugin, (Runnable) new Runnable() {
                            @Override
                            public void run() {
                                for (final Player p : Bukkit.getOnlinePlayers()) {
                                    if (!p.hasPermission(DisguiseConfig.getUpdateNotificationPermission())) {
                                        continue;
                                    }
                                    p.sendMessage(String.format(DisguiseConfig.getUpdateMessage(), DisguiseListener.this.currentVersion, DisguiseListener.this.latestVersion));
                                }
                            }
                        });
                    } catch (Exception ex) {
                        System.out.print(String.format("[LibsDisguises] Failed to check for update: %s", ex.getMessage()));
                    }
                }
            }, 0L, 432000L);
        }
    }

    public void cleanup() {
        for (final BukkitRunnable r : this.disguiseRunnable.values()) {
            r.cancel();
        }
        for (final Disguise d : this.disguiseEntity.values()) {
            d.removeDisguise();
        }
        this.disguiseClone.clear();
        this.updaterTask.cancel();
    }

    private void checkPlayerCanBlowDisguise(final Player entity) {
        final Disguise[] disguises = DisguiseAPI.getDisguises((Entity) entity);
        if (disguises.length > 0) {
            DisguiseAPI.undisguiseToAll((Entity) entity);
            if (DisguiseConfig.getDisguiseBlownMessage().length() > 0) {
                entity.sendMessage(DisguiseConfig.getDisguiseBlownMessage());
            }
        }
    }

    private void chunkMove(final Player player, final Location newLoc, final Location oldLoc) {
        try {
            for (final PacketContainer packet : DisguiseUtilities.getBedChunkPacket(newLoc, oldLoc)) {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet, false);
            }
            if (newLoc != null) {
                for (final HashSet<TargetedDisguise> list : DisguiseUtilities.getDisguises().values()) {
                    for (final TargetedDisguise disguise : list) {
                        if (disguise.getEntity() == null) {
                            continue;
                        }
                        if (!disguise.isPlayerDisguise()) {
                            continue;
                        }
                        if (!disguise.canSee(player)) {
                            continue;
                        }
                        if (!((PlayerDisguise) disguise).getWatcher().isSleeping()) {
                            continue;
                        }
                        if (!DisguiseUtilities.getPerverts(disguise).contains(player)) {
                            continue;
                        }
                        final PacketContainer[] packets = DisguiseUtilities.getBedPackets((disguise.getEntity() == player) ? newLoc : disguise.getEntity().getLocation(), newLoc, (PlayerDisguise) disguise);
                        if (disguise.getEntity() == player) {
                            for (final PacketContainer packet2 : packets) {
                                packet2.getIntegers().write(0, DisguiseAPI.getSelfDisguiseId());
                            }
                        }
                        for (final PacketContainer packet2 : packets) {
                            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet2);
                        }
                    }
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAttack(final EntityDamageByEntityEvent event) {
        if (DisguiseConfig.isDisguiseBlownOnAttack()) {
            if (event.getEntity() instanceof Player) {
                this.checkPlayerCanBlowDisguise((Player) event.getEntity());
            }
            if (event.getDamager() instanceof Player) {
                this.checkPlayerCanBlowDisguise((Player) event.getDamager());
            }
        }
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player p = event.getPlayer();
        if (this.latestVersion != null && p.hasPermission(DisguiseConfig.getUpdateNotificationPermission())) {
            p.sendMessage(String.format(DisguiseConfig.getUpdateMessage(), this.currentVersion, this.latestVersion));
        }
        if (DisguiseConfig.isBedPacketsEnabled()) {
            this.chunkMove(p, p.getLocation(), null);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(final PlayerMoveEvent event) {
        if (DisguiseConfig.isBedPacketsEnabled()) {
            final Location to = event.getTo();
            final Location from = event.getFrom();
            if (DisguiseUtilities.getChunkCord(to.getBlockX()) != DisguiseUtilities.getChunkCord(from.getBlockX()) || DisguiseUtilities.getChunkCord(to.getBlockZ()) != DisguiseUtilities.getChunkCord(from.getBlockZ())) {
                this.chunkMove(event.getPlayer(), to, from);
            }
        }
        final Disguise disguise;
        if (DisguiseConfig.isStopShulkerDisguisesFromMoving() && (disguise = DisguiseAPI.getDisguise((Entity) event.getPlayer())) != null && disguise.getType() == DisguiseType.SHULKER) {
            final Location from = event.getFrom();
            final Location to2 = event.getTo();
            to2.setX(from.getX());
            to2.setZ(from.getZ());
            event.setTo(to2);
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        ReflectionManager.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        final Disguise[] arr$;
        final Disguise[] disguises = arr$ = DisguiseAPI.getDisguises((Entity) event.getPlayer());
        for (final Disguise disguise : arr$) {
            if (disguise.isRemoveDisguiseOnDeath()) {
                disguise.removeDisguise();
            }
        }
        if (DisguiseConfig.isBedPacketsEnabled()) {
            final Player player = event.getPlayer();
            this.chunkMove(event.getPlayer(), null, player.getLocation());
            Bukkit.getScheduler().runTask((Plugin) this.plugin, (Runnable) new Runnable() {
                @Override
                public void run() {
                    DisguiseListener.this.chunkMove(player, player.getLocation(), null);
                }
            });
        }
    }

    @EventHandler
    public void onRightClick(final PlayerInteractEntityEvent event) {
        if (this.disguiseEntity.containsKey(event.getPlayer().getName()) || this.disguiseClone.containsKey(event.getPlayer().getName())) {
            final Player p = event.getPlayer();
            event.setCancelled(true);
            this.disguiseRunnable.remove(p.getName()).cancel();
            final Entity entity = event.getRightClicked();
            String entityName;
            if (entity instanceof Player && !this.disguiseClone.containsKey(p.getName())) {
                entityName = entity.getName();
            } else {
                entityName = DisguiseType.getType(entity).toReadable();
            }
            if (this.disguiseClone.containsKey(p.getName())) {
                final Boolean[] options = this.disguiseClone.remove(p.getName());
                Disguise disguise = DisguiseAPI.getDisguise(p, entity);
                if (disguise == null) {
                    disguise = DisguiseAPI.constructDisguise(entity, options[0], options[1], options[2]);
                } else {
                    disguise = disguise.clone();
                }
                final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                String reference = null;
                final int referenceLength = Math.max(2, (int) Math.ceil((0.1 + DisguiseConfig.getMaxClonedDisguises()) / 26.0));
                for (int attempts = 0; reference == null && attempts++ < 1000; reference = null) {
                    reference = "@";
                    for (int i = 0; i < referenceLength; ++i) {
                        reference += alphabet[DisguiseUtilities.random.nextInt(alphabet.length)];
                    }
                    if (DisguiseUtilities.getClonedDisguise(reference) != null) {
                    }
                }
                if (reference != null && DisguiseUtilities.addClonedDisguise(reference, disguise)) {
                    p.sendMessage(ChatColor.RED + "Constructed a " + entityName + " disguise! Your reference is " + reference);
                    p.sendMessage(ChatColor.RED + "Example usage: /disguise " + reference);
                } else {
                    p.sendMessage(ChatColor.RED + "Failed to store the reference due to lack of size. Please set this in the config");
                }
            } else if (this.disguiseEntity.containsKey(p.getName())) {
                final Disguise disguise2 = this.disguiseEntity.remove(p.getName());
                if (disguise2 != null) {
                    if (disguise2.isMiscDisguise() && !DisguiseConfig.isMiscDisguisesForLivingEnabled() && entity instanceof LivingEntity) {
                        p.sendMessage(ChatColor.RED + "Can't disguise a living entity as a misc disguise. This has been disabled in the config!");
                    } else {
                        if (entity instanceof Player && DisguiseConfig.isNameOfPlayerShownAboveDisguise() && disguise2.getWatcher() instanceof LivingWatcher) {
                            disguise2.getWatcher().setCustomName(((Player) entity).getDisplayName());
                            if (DisguiseConfig.isNameAboveHeadAlwaysVisible()) {
                                disguise2.getWatcher().setCustomNameVisible(true);
                            }
                        }
                        DisguiseAPI.disguiseToAll(entity, disguise2);
                        String disguiseName = "a ";
                        if (disguise2 instanceof PlayerDisguise) {
                            disguiseName = "the player " + ((PlayerDisguise) disguise2).getName();
                        } else {
                            disguiseName += disguise2.getType().toReadable();
                        }
                        if (disguise2.isDisguiseInUse()) {
                            p.sendMessage(ChatColor.RED + "Disguised " + ((entity instanceof Player) ? "" : "the ") + entityName + " as " + disguiseName + "!");
                        } else {
                            p.sendMessage(ChatColor.RED + "Failed to disguise " + ((entity instanceof Player) ? "" : "the ") + entityName + " as " + disguiseName + "!");
                        }
                    }
                } else if (DisguiseAPI.isDisguised(entity)) {
                    DisguiseAPI.undisguiseToAll(entity);
                    p.sendMessage(ChatColor.RED + "Undisguised " + ((entity instanceof Player) ? "" : "the ") + entityName);
                } else {
                    p.sendMessage(ChatColor.RED + ((entity instanceof Player) ? "" : "the") + entityName + " isn't disguised!");
                }
            }
        }
    }

    @EventHandler
    public void onTarget(final EntityTargetEvent event) {
        if (DisguiseConfig.isMonstersIgnoreDisguises() && event.getTarget() != null && event.getTarget() instanceof Player && DisguiseAPI.isDisguised(event.getTarget())) {
            switch (event.getReason()) {
                case TARGET_ATTACKED_ENTITY:
                case TARGET_ATTACKED_OWNER:
                case OWNER_ATTACKED_TARGET:
                case CUSTOM: {
                    break;
                }
                default: {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        final Location to = event.getTo();
        final Location from = event.getFrom();
        if (DisguiseConfig.isBedPacketsEnabled() && (DisguiseUtilities.getChunkCord(to.getBlockX()) != DisguiseUtilities.getChunkCord(from.getBlockX()) || DisguiseUtilities.getChunkCord(to.getBlockZ()) != DisguiseUtilities.getChunkCord(from.getBlockZ()))) {
            this.chunkMove(player, null, from);
            Bukkit.getScheduler().runTask((Plugin) this.plugin, (Runnable) new Runnable() {
                @Override
                public void run() {
                    DisguiseListener.this.chunkMove(player, player.getLocation(), null);
                }
            });
        }
        if (!DisguiseAPI.isDisguised((Entity) player)) {
            return;
        }
        if (DisguiseConfig.isUndisguiseOnWorldChange() && to.getWorld() != null && from.getWorld() != null && to.getWorld() != from.getWorld()) {
            for (final Disguise disguise : DisguiseAPI.getDisguises((Entity) event.getPlayer())) {
                disguise.removeDisguise();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onVehicleEnter(final VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player && DisguiseAPI.isDisguised((Player) event.getEntered(), event.getEntered())) {
            DisguiseUtilities.removeSelfDisguise((Player) event.getEntered());
            ((Player) event.getEntered()).updateInventory();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onVehicleLeave(final VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            final Disguise disguise = DisguiseAPI.getDisguise((Player) event.getExited(), (Entity) event.getExited());
            if (disguise != null) {
                Bukkit.getScheduler().runTask((Plugin) this.plugin, (Runnable) new Runnable() {
                    @Override
                    public void run() {
                        DisguiseUtilities.setupFakeDisguise(disguise);
                        ((Player) disguise.getEntity()).updateInventory();
                    }
                });
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldSwitch(final PlayerChangedWorldEvent event) {
        if (DisguiseConfig.isBedPacketsEnabled()) {
            this.chunkMove(event.getPlayer(), event.getPlayer().getLocation(), null);
        }
        if (!DisguiseAPI.isDisguised((Entity) event.getPlayer())) {
            return;
        }
        if (DisguiseConfig.isUndisguiseOnWorldChange()) {
            for (final Disguise disguise : DisguiseAPI.getDisguises((Entity) event.getPlayer())) {
                disguise.removeDisguise();
            }
        } else {
            final boolean viewSelfToggled = DisguiseAPI.isViewSelfToggled((Entity) event.getPlayer());
            if (viewSelfToggled) {
                final Disguise disguise2 = DisguiseAPI.getDisguise((Entity) event.getPlayer());
                disguise2.setViewSelfDisguise(false);
                Bukkit.getScheduler().runTaskLater((Plugin) this.plugin, (Runnable) new Runnable() {
                    @Override
                    public void run() {
                        disguise2.setViewSelfDisguise(true);
                    }
                }, 20L);
            }
        }
    }

    public void setDisguiseClone(final String player, final Boolean[] options) {
        if (this.disguiseRunnable.containsKey(player)) {
            final BukkitRunnable run = this.disguiseRunnable.remove(player);
            run.cancel();
            run.run();
        }
        final BukkitRunnable runnable = new BukkitRunnable() {
            public void run() {
                DisguiseListener.this.disguiseClone.remove(player);
                DisguiseListener.this.disguiseRunnable.remove(player);
            }
        };
        runnable.runTaskLater((Plugin) this.plugin, (long) (20 * DisguiseConfig.getDisguiseCloneExpire()));
        this.disguiseRunnable.put(player, runnable);
        this.disguiseClone.put(player, options);
    }

    public void setDisguiseEntity(final String player, final Disguise disguise) {
        if (this.disguiseRunnable.containsKey(player)) {
            final BukkitRunnable run = this.disguiseRunnable.remove(player);
            run.cancel();
            run.run();
        }
        final BukkitRunnable runnable = new BukkitRunnable() {
            public void run() {
                DisguiseListener.this.disguiseEntity.remove(player);
                DisguiseListener.this.disguiseRunnable.remove(player);
            }
        };
        runnable.runTaskLater((Plugin) this.plugin, (long) (20 * DisguiseConfig.getDisguiseEntityExpire()));
        this.disguiseRunnable.put(player, runnable);
        this.disguiseEntity.put(player, disguise);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisguise(DisguiseEvent e) {
        //System.out.println(11111);
        if (e.getDisguise().isPlayerDisguise()) {
            PlayerDisguise pd = (PlayerDisguise) e.getDisguise();
            String skin = pd.getSkin();
            if (skin == null || skin.isEmpty()) skin = pd.getName();
            UUID uuid = UUID.nameUUIDFromBytes(skin.getBytes());
            try {
                WrappedGameProfile gp = pd.getGameProfile();
                Field puid = WrappedGameProfile.class.getDeclaredField("parsedUUID");
                puid.setAccessible(true);
                puid.set(gp, uuid);

                Field currentLookupField = PlayerDisguise.class.getDeclaredField("currentLookup");
                currentLookupField.setAccessible(true);
                final WrappedGameProfile profileFromMojang = DisguiseUtilities.getProfileFromMojang(skin,
                        (LibsProfileLookup) currentLookupField.get(pd));
                // System.out.println("profileFromMojang = " + profileFromMojang.getProperties());
                //  System.out.println("pd.getSkin() = " + skin);

                if (profileFromMojang != null) {
                    pd.setSkin(profileFromMojang, true);
                }
                //  System.out.println("pd.getSkin() = " + skin);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
    }
}
