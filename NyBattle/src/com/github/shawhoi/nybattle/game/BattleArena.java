// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.github.shawhoi.nybattle.Main;
import com.github.shawhoi.nybattle.bungee.Teleport;
import com.github.shawhoi.nybattle.config.BattleConfig;
import com.github.shawhoi.nybattle.config.Message;
import com.github.shawhoi.nybattle.listener.PlayerTouch;
import com.github.shawhoi.nybattle.playerdata.PlayerData;
import com.github.shawhoi.nybattle.playerdata.PlayerGameData;
import com.github.shawhoi.nybattle.util.BossBarUtil;
import com.github.shawhoi.nybattle.util.LocationUtil;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import com.gmail.filoghost.holographicdisplays.api.line.TouchableLine;
import com.gmail.filoghost.holographicdisplays.object.line.CraftHologramLine;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.Config;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BattleArena {
    private String arenaname;
    private int min;
    private int max;
    private Location signloc;
    private List<String> allplayers;
    private String arenastate;
    private int waittime;
    private String winplayer;
    private Location waitloc;
    private Location endloc;
    private HashMap<String, Inventory> playerinventory;
    private String arena_name;
    private File df;
    private FileConfiguration data;
    private Location protect;
    private World world;
    private double damage;
    private double size;
    private BossBar bb;
    private ArrayList<String> jump;
    private org.inventivetalent.bossbar.BossBar hxbb;
    private int hxjd;
    private Location startloc;
    private Location lastloc;
    private Location nowloc;
    private HashMap<String, Integer> kills;
    private HashMap<Hologram, Inventory> holograms;
    private Inventory ci;
    private Inventory airdrop;
    private ArrayList<Location> openchestlocs;
    private ArrayList<Location> airdroplocs;
    private ArrayList<Location> airdropblock;
    private ArrayList<BukkitTask> runnables;
    private int bordertime;
    private boolean ssborder;

    public BattleArena(final File f) {
        this.arenaname = "";
        this.min = 0;
        this.max = 0;
        this.signloc = null;
        this.allplayers = new ArrayList<String>();
        this.arenastate = "WAIT";
        this.waittime = 60;
        this.winplayer = "";
        this.waitloc = null;
        this.endloc = null;
        this.playerinventory = new HashMap<String, Inventory>();
        this.arena_name = null;
        this.damage = 0.0;
        this.size = 0.0;
        this.jump = new ArrayList<String>();
        this.kills = new HashMap<String, Integer>();
        this.holograms = new HashMap<Hologram, Inventory>();
        this.openchestlocs = new ArrayList<Location>();
        this.airdroplocs = new ArrayList<Location>();
        this.airdropblock = new ArrayList<Location>();
        this.runnables = new ArrayList<BukkitTask>();
        this.bordertime = 180;
        this.ssborder = false;
        this.data = (FileConfiguration) YamlConfiguration.loadConfiguration(f);
        this.df = f;
        this.arena_name = f.getName().split(".yml")[0];
        this.arenaname = ((this.data.getString("ArenaName") == null) ? f.getName().split(".yml")[0] : this.data.getString("ArenaName").replace("&", "��"));
        this.min = ((this.data.getInt("Min") > 0) ? this.data.getInt("Min") : 0);
        this.max = ((this.data.getInt("Max") > 0) ? this.data.getInt("Max") : 0);
        this.waitloc = (this.data.getKeys(false).contains("Lobby") ? LocationUtil.getLocation(this.data.getString("Lobby")) : null);
        this.endloc = (this.data.getKeys(false).contains("End") ? LocationUtil.getLocation(this.data.getString("End")) : null);
        this.signloc = (this.data.getKeys(false).contains("Sign") ? LocationUtil.getLocation(this.data.getString("Sign")) : null);
        this.size = this.data.getDouble("Border");
        this.world = Bukkit.getWorld(this.data.getString("World"));
        this.world.setSpawnLocation(0, 0, 0);
        BorderData border = Config.Border(this.world.getName());
        Config.setBorder(this.world.getName(), Double.valueOf(this.size).intValue(), this.world.getSpawnLocation().getX(), this.world.getSpawnLocation().getZ());
/*        border.setX(this.world.getSpawnLocation().getX());
        border.setZ(this.world.getSpawnLocation().getZ());
        border.setRadius(Double.valueOf(this.size).intValue());*/

        this.bb = BossBarAPI.addBar(new ArrayList<>(), Message.getString("BossBar.Main"), BossBarAPI.Color.YELLOW, BossBarAPI.Style.PROGRESS, 1F);
        this.hxbb = BossBarAPI.addBar(new ArrayList<>(), Message.getString("Route.Main"), BossBarAPI.Color.WHITE, BossBarAPI.Style.PROGRESS, 0F);
        this.ci = Bukkit.createInventory((InventoryHolder) null, 54, "C: " + this.arena_name);
        this.airdrop = Bukkit.createInventory((InventoryHolder) null, 54, "D: " + this.arena_name);
        if (this.data.getKeys(false).contains("ChestItems")) {
            for (final String key : this.data.getConfigurationSection("ChestItems").getKeys(false)) {
                if (this.data.getItemStack("ChestItems.." + key) != null && this.data.getItemStack("ChestItems.." + key).getType() != Material.AIR) {
                    this.ci.addItem(new ItemStack[]{this.data.getItemStack("ChestItems.." + key)});
                }
            }
        }
        if (this.data.getKeys(false).contains("DropItems")) {
            for (final String key : this.data.getConfigurationSection("DropItems").getKeys(false)) {
                if (this.data.getItemStack("DropItems.." + key) != null && this.data.getItemStack("DropItems.." + key).getType() != Material.AIR) {
                    this.airdrop.addItem(new ItemStack[]{this.data.getItemStack("DropItems.." + key)});
                }
            }
        }
        this.checkPlayer();
        this.upSign();
        this.sendScoreboard();
        BorderData config = Config.Border(this.world.getName());
        final Location spawnloc =
                new Location(this.world, config.getX(), this.world.getSpawnLocation().getY(), config.getZ());
        final int slx = (int) (spawnloc.getBlockX() - this.size / 3.0 + Math.random() * (spawnloc.getBlockX() + this.size / 3.0 - (spawnloc.getBlockX() - this.size / 3.0)));
        final int slz = (int) (spawnloc.getBlockZ() - this.size / 3.0 + Math.random() * (spawnloc.getBlockZ() + this.size / 3.0 - (spawnloc.getBlockZ() - this.size / 3.0)));
        this.startloc = new Location(this.world, (double) slx, 150.0, (double) slz);
        final double distance = this.startloc.distance(spawnloc);
        this.lastloc = new Location(this.world, (slx < spawnloc.getBlockX()) ? (spawnloc.getBlockX() + distance / 3.0) : (spawnloc.getBlockX() - distance / 3.0), 150.0, (slz < spawnloc.getBlockZ()) ? (spawnloc.getBlockZ() + distance / 3.0) : (spawnloc.getBlockZ() - distance / 3.0));
        new BukkitRunnable() {
            public void run() {
                for (final Hologram h : BattleArena.this.holograms.keySet()) {
                    for (int i = 0; i < h.size(); ++i) {
                        final CraftHologramLine cl = (CraftHologramLine) h.getLine(i);
                        if (cl instanceof TouchableLine) {
                            final TouchableLine tl = (TouchableLine) cl;
                            if (((TouchableLine) cl).getTouchHandler() != null) {
                                ((TouchableLine) cl).setTouchHandler((TouchHandler) null);
                            }
                            ((TouchableLine) cl).setTouchHandler((TouchHandler) new PlayerTouch(Main.getInstance(), h));
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin) Main.getInstance(), 10L, 10L);
    }

    public String getTimeFormat() {
        String format = "";
        if (this.ssborder) {
            return Message.getString("StartSendBorderFormat");
        }
        format = this.bordertime / 60 + ":" + this.bordertime % 60;
        return format;
    }

    public void startSendBorder() {
        this.size /= 2.0;
        this.ChangeProtectLocation();
        this.ChangeBorderTime();
    }

    public void ChangeBorderTime() {
        this.runnables.add(new BukkitRunnable() {
            public void run() {
                if (BattleArena.this.ssborder) {
                    BattleArena.this.bb.setMessage(Message.getString("BossBar.Run"));
                    BorderData border = Config.Border(BattleArena.this.world.getName());
                    final double prog = BattleArena.this.size / border.getRadius();
                    BattleArena.this.bb.setProgress(Float.valueOf(String.valueOf(prog)));
                    return;
                }
                if (BattleArena.this.bordertime == 0) {
                    BattleArena.this.ssborder = true;
                    BattleArena.this.SendBorder(BattleArena.this.world, BattleArena.this.protect, BattleArena.this.size, (int) (BattleArena.this.size / 10.0));
                    return;
                }
                BattleArena.this.bb.setMessage(Message.getString("BossBar.WaitRun").replace("%time_format%", BattleArena.this.getTimeFormat()));
                BattleArena.this.bordertime--;
            }
        }.runTaskTimerAsynchronously((Plugin) Main.getInstance(), 20L, 20L));
    }

    public boolean isOpen(final Location loc) {
        return this.openchestlocs.contains(loc);
    }

    public void addOpenChest(final Location loc) {
        this.openchestlocs.add(loc);
    }

    public boolean isAirDropChest(final Location loc) {
        return this.airdropblock.contains(loc);
    }

    public boolean AirDropIsOpen(final Location loc) {
        return !this.airdroplocs.contains(loc);
    }

    public void setSignLocation(final Location loc) {
        LocationUtil.setLocationConfiguration(loc, this.df, "Sign");
        this.data = (FileConfiguration) YamlConfiguration.loadConfiguration(this.df);
        this.signloc = (this.data.getKeys(false).contains("Sign") ? LocationUtil.getLocation(this.data.getString("Sign")) : null);
    }

    public boolean isInside(final Player p) {
        return p.getLocation().distance(this.protect) > this.size / 2.0;
    }

    public double getDamage() {
        return this.damage;
    }

    public String getArenaState() {
        return this.arenastate;
    }

    public String getArenaFileName() {
        return this.df.getName().split(".yml")[0];
    }

    public Location getSignLocation() {
        return this.signloc;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public String getArenaName() {
        return this.arenaname;
    }

    public void PlayerJoin(final Player p) {
        final PlayerGameData pgd = new PlayerGameData(p);
        PlayerData.playergamedata.put(p.getName(), pgd);
        BossBarUtil.setPlayerViewBossBar(p);
        this.bb.addPlayer(p);
        this.hxbb.addPlayer(p);
        this.kills.put(p.getName(), 0);
        PlayerData.playerdata.put(p.getName(), this.df.getName().split(".yml")[0]);
        if (!this.arenastate.equals("START")) {
            this.allplayers.add(p.getName());
            for (final PotionEffect pe : p.getActivePotionEffects()) {
                p.removePotionEffect(pe.getType());
            }
            p.getInventory().setArmorContents(new ItemStack[4]);
            p.getInventory().setContents(new ItemStack[0]);
            p.getInventory().setItem(0, Message.getHelpItem());
            p.setHealth(20.0);
            p.setFoodLevel(20);
            p.setGameMode(GameMode.ADVENTURE);
            p.setFlying(false);
            p.setAllowFlight(false);
            p.setExp(0.0f);
            p.setLevel(this.waittime);
            p.sendMessage(Message.getPrefix() + Message.getString("JoinMes.Player").replace("%arena_name%", this.getArenaName()));
            this.sendBroadcast(Message.getPrefix() + Message.getString("JoinMes.All").replace("%player%", p.getName()).replace("%players%", this.allplayers.size() + "").replace("%max%", this.max + ""));
            new BukkitRunnable() {
                public void run() {
                    p.teleport(BattleArena.this.waitloc);
                }
            }.runTaskLater((Plugin) Main.getInstance(), 5L);
            this.upSign();
        }
    }

    public void PlayerDeath(final Player p) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 36, Message.getString("PlayerInventoryTitle").replace("%player%", p.getName()));
        for (final ItemStack item : p.getInventory().getContents()) {
            inv.addItem(new ItemStack[]{(item == null) ? new ItemStack(Material.AIR) : item});
        }
        for (final ItemStack item : p.getInventory().getArmorContents()) {
            inv.addItem(new ItemStack[]{(item == null) ? new ItemStack(Material.AIR) : item});
        }
        final Hologram holo = HologramsAPI.createHologram((Plugin) Main.getInstance(), p.getLocation().add(0.0, 1.5, 0.0));
        final String killname = (p.getKiller() == null) ? "\u672a\u77e5" : p.getKiller().getName();
        for (final String text : Message.getStringList("Hologram")) {
            holo.appendTextLine(text.replace("%player%", p.getName()).replace("%killer%", killname));
        }
        holo.appendItemLine(new ItemStack(Material.CHEST));
        this.holograms.put(holo, inv);
    }

    public void PlayerLeave(final Player p, final boolean quit) {
        if (!Main.getInstance().isBungee()) {
            new BukkitRunnable() {
                public void run() {
                    p.teleport(BattleArena.this.endloc);
                }
            }.runTaskLater((Plugin) Main.getInstance(), 3L);
        } else {
            new BukkitRunnable() {
                public void run() {
                    p.teleport(BattleArena.this.waitloc);
                }
            }.runTaskLater((Plugin) Main.getInstance(), 3L);
        }
        BossBarUtil.removePlayerViewBossBar(p);
        PlayerData.setPlayerGameData(p);
        this.bb.removePlayer(p);
        this.hxbb.removePlayer(p);
        this.kills.remove(p.getName());
        p.sendMessage(Message.getPrefix() + Message.getString("Quit.Player").replace("%arena_name%", this.arenaname));
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        if (this.arenastate.equals("START") || this.arenastate.equals("END")) {
            this.sendBroadcast(Message.getPrefix() + Message.getString("Quit.All").replace("%player%", p.getName()).replace("%players%", this.allplayers.size() - 1 + "").replace("%max%", this.max + ""));
            if (!quit) {
                final boolean isop = p.isOp();
                try {
                    p.setOp(true);
                    for (final String command : BattleConfig.getStringList("Commands.play")) {
                        p.performCommand(command.replace("%player%", p.getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    p.setOp(isop);
                }
                new Teleport(p);
            } else {
                this.PlayerDeath(p);
            }
        } else {
            this.sendBroadcast(Message.getPrefix() + Message.getString("Quit.All").replace("%player%", p.getName()).replace("%players%", this.allplayers.size() - 1 + "").replace("%max%", this.max + ""));
        }
        this.allplayers.remove(p.getName());
        PlayerData.playerdata.remove(p.getName());
        this.upSign();
    }

    public void FailStop() {
        this.arenastate = "END";
        for (final BukkitTask bt : this.runnables) {
            bt.cancel();
        }
        this.resetArena();
    }

    public void checkPlayer() {
        new BukkitRunnable() {
            public void run() {
                if (BattleArena.this.arenastate.equals("WAIT")) {
                    if (BattleArena.this.allplayers.size() >= BattleArena.this.min) {
                        BattleArena.this.arenastate = "WSTART";
                    }
                } else if (BattleArena.this.arenastate.equals("START")) {
                    if (BattleArena.this.allplayers.size() <= 1) {
                        BattleArena.this.endGame();
                        this.cancel();
                        return;
                    }
                } else if (BattleArena.this.arenastate.equals("WSTART") && BattleArena.this.waittime == 60) {
                    BattleArena.this.setWaitTime();
                }
                BattleArena.this.setWaitTimeLevel();
            }
        }.runTaskTimer((Plugin) Main.getInstance(), 20L, 20L);
    }

    public boolean JumpListContainPlayer(final String name) {
        return this.jump.contains(name);
    }

    public int getPlayerAmount() {
        return this.allplayers.size();
    }

    public boolean canJoin() {
        boolean can = true;
        if (this.allplayers.size() >= this.max || this.arenastate.equals("START") || this.arenastate.equals("END")) {
            can = false;
        }
        return can;
    }

    public void ChangeProtectLocation() {
        if (this.protect == null) {
            final Location centerloc = this.world.getSpawnLocation();
            final int rx = (int) (centerloc.getBlockX() - this.size / 2.0 + Math.random() * (centerloc.getBlockX() + this.size / 2.0 - (centerloc.getBlockX() - this.size / 2.0)));
            final int rz = (int) (centerloc.getBlockZ() - this.size / 2.0 + Math.random() * (centerloc.getBlockZ() + this.size / 2.0 - (centerloc.getBlockZ() - this.size / 2.0)));
            this.protect = new Location(this.world, (double) rx, (double) centerloc.getBlockY(), (double) rz);
        } else {
            final int rx2 = (int) (this.protect.getBlockX() - this.size / 2.0 + Math.random() * (this.protect.getBlockX() + this.size / 2.0 - (this.protect.getBlockX() - this.size / 2.0)));
            final int rz2 = (int) (this.protect.getBlockZ() - this.size / 2.0 + Math.random() * (this.protect.getBlockZ() + this.size / 2.0 - (this.protect.getBlockZ() - this.size / 2.0)));
            this.protect = new Location(this.world, (double) rx2, (double) this.protect.getBlockY(), (double) rz2);
        }
    }

    public void sendScoreboard() {
        new BukkitRunnable() {
            public void run() {
                for (final String name : BattleArena.this.allplayers) {
                    final Player p = Bukkit.getPlayerExact(name);
                    if (p != null && p.isOnline()) {
                        final Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
                        final Objective obj = sb.registerNewObjective("ScoreBoard", "dummy");
                        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                        obj.setDisplayName(BattleConfig.getString("Scoreboard.Title"));
                        if (BattleArena.this.arenastate.equals("START") || BattleArena.this.arenastate.equals("END")) {
                            final List<String> sl = BattleConfig.getStringList("Scoreboard.Play");
                            for (int i = 0; i < sl.size(); ++i) {
                                final String text = BattleArena.this.buildScore(sl.get(i), p);
                                obj.getScore(text).setScore(15 - i);
                            }
                        } else {
                            final List<String> sl = BattleConfig.getStringList("Scoreboard.Wait");
                            for (int i = 0; i < sl.size(); ++i) {
                                final String text = BattleArena.this.buildScore(sl.get(i), p);
                                obj.getScore(text).setScore(15 - i);
                            }
                        }
                        p.setScoreboard(sb);
                    }
                }
            }
        }.runTaskTimer((Plugin) Main.getInstance(), 15L, 15L);
    }

    public void startTNTDrop() {
        final BukkitTask bt = new BukkitRunnable() {
            public void run() {
                final int x = (int) (BattleArena.this.protect.getBlockX() - (BattleArena.this.size / 2.0 - 10.0) + Math.random() * (BattleArena.this.protect.getBlockX() + (BattleArena.this.size / 2.0 - 10.0) - (BattleArena.this.protect.getBlockX() - (BattleArena.this.size / 2.0 - 10.0))));
                final int z = (int) (BattleArena.this.protect.getBlockZ() - (BattleArena.this.size / 2.0 - 10.0) + Math.random() * (BattleArena.this.protect.getBlockZ() + (BattleArena.this.size / 2.0 - 10.0) - (BattleArena.this.protect.getBlockZ() - (BattleArena.this.size / 2.0 - 10.0))));
                final Location td1 = new Location(BattleArena.this.world, (double) x, 0.0, (double) z);
                BattleArena.this.sendBroadcast(Message.getPrefix() + Message.getString("TNTDrop").replace("%x%", x + "").replace("%z%", z + ""));
                new BukkitRunnable() {
                    int i = 0;

                    public void run() {
                        if (this.i == 5) {
                            this.cancel();
                            return;
                        }
                        final int x = (int) (td1.getBlockX() - 5 + Math.random() * (td1.getBlockX() + 5 - (td1.getBlockX() - 5)));
                        final int z = (int) (td1.getBlockZ() - 5 + Math.random() * (td1.getBlockZ() + 5 - (td1.getBlockZ() - 5)));
                        for (int i = 256; i > 0; --i) {
                            final Location tz = new Location(BattleArena.this.world, (double) x, (double) i, (double) z);
                            if (tz.getBlock() != null && tz.getBlock().getType() != Material.AIR) {
                                BattleArena.this.world.createExplosion((double) x, (double) i, (double) z, 3.0f, false, false);
                                break;
                            }
                        }
                        ++this.i;
                    }
                }.runTaskTimer((Plugin) Main.getInstance(), 20L, 20L);
            }
        }.runTaskTimer((Plugin) Main.getInstance(), 2400L, 2400L);
    }

    public String buildScore(final String text, final Player p) {
        String distance = "";
        if (this.protect != null) {
            final Location pclone = this.protect.clone();
            pclone.setY(p.getLocation().getY());
            final int pd = (int) p.getLocation().distance(pclone);
            distance = ((pd > this.size / 2.0) ? ((int) (pd - this.size / 2.0) + "") : Message.getString("InProtect"));
        }
        return text.replace("%players%", this.allplayers.size() + "").replace("%player%", p.getName()).replace("%wait_time%", this.waittime + "").replace("%arena_name%", this.arenaname).replace("%%", "��").replace("%kills%", this.kills.get(p.getName()) + "").replace("%sc_time%", this.getTimeFormat()).replace("%distance%", distance);
    }

    public void setWaitTime() {
        new BukkitRunnable() {
            public void run() {
                if (BattleArena.this.waittime == 0) {
                    BattleArena.this.sendBroadcast(Message.getPrefix() + Message.getString("WaitMes.Play"));
                    BattleArena.this.startGame();
                    this.cancel();
                    return;
                }
                if (BattleArena.this.allplayers.size() < BattleArena.this.min) {
                    BattleArena.this.arenastate = "WAIT";
                    BattleArena.this.waittime = 60;
                    this.cancel();
                    return;
                }
                if (BattleArena.this.waittime <= 10) {
                    BattleArena.this.sendBroadcast(Message.getPrefix() + Message.getString("WaitMes.Delay").replace("%time%", BattleArena.this.waittime + ""));
                }
                BattleArena.this.waittime--;
            }
        }.runTaskTimerAsynchronously((Plugin) Main.getInstance(), 0L, 20L);
    }

    public void setWaitTimeLevel() {
        new BukkitRunnable() {
            public void run() {
                for (final String playername : BattleArena.this.allplayers) {
                    final Player p = Bukkit.getPlayerExact(playername);
                    if (p != null && p.isOnline()) {
                        p.setLevel(BattleArena.this.waittime);
                    }
                }
            }
        }.runTask((Plugin) Main.getInstance());
    }

    public void startGame() {
        this.arenastate = "START";
        this.upSign();
        this.sendBroadcast(Message.getPrefix() + Message.getString("Game.Start"));
        for (final String name : this.allplayers) {
            this.jump.add(name);
            Bukkit.getPlayerExact(name).sendMessage(new String[]{Message.getString("Route.MT"), Message.getString("Route.ST")});
            Bukkit.getPlayerExact(name).getInventory().clear();
        }
        this.startSendBorder();
        this.StartAirDrop();
        this.startTNTDrop();
        new BukkitRunnable() {
            public void run() {
                if (BattleArena.this.arenastate.equals("END")) {
                    this.cancel();
                    return;
                }
                BattleArena.this.setNowLocation();
                if (BattleArena.this.hxjd == 100) {
                    BattleArena.this.hxbb.getPlayers().clear();
                    for (final String name : BattleArena.this.jump) {
                        BattleArena.this.PlayerJump(Bukkit.getPlayerExact(name));
                    }
                    this.cancel();
                    return;
                }
                BattleArena.this.hxbb.setMessage(Message.getString("Route.Bar").replace("%scale%", BattleArena.this.hxjd + ""));
                BattleArena.this.hxbb.setProgress(BattleArena.this.hxjd * 0.01f);
                BattleArena.this.hxjd++;
            }
        }.runTaskTimerAsynchronously((Plugin) Main.getInstance(), 10L, 10L);
    }

    public void setNowLocation() {
        final Vector vector = this.lastloc.clone().subtract(this.startloc).toVector();
        final double vl = vector.length();
        final double vp = vl * (this.hxjd * 0.01);
        vector.normalize();
        vector.multiply(vp);
        this.nowloc = this.startloc.clone().add(vector);
    }

    public void addKill(final String name) {
        this.kills.replace(name, this.kills.get(name) + 1);
    }

    public void PlayerJump(final Player p) {
        if (this.jump.contains(p.getName())) {
            new BukkitRunnable() {
                public void run() {
                    BattleArena.this.jump.remove(p.getName());
                    p.teleport(BattleArena.this.nowloc);
/*                    final ItemStack item = new ItemStack(Material.ELYTRA);
                    final ItemMeta im = item.getItemMeta();
                    im.setDisplayName("��a\u6ed1\u7fd4\u4f1e");
                    item.setItemMeta(im);
                    p.getInventory().setItem(38, item);
                    p.updateInventory();
                    p.sendTitle(Message.getString("Game.Jump.M"), Message.getString("Game.Jump.S"));*/
                }
            }.runTaskLater((Plugin) Main.getInstance(), 5L);
        }
    }

    public void endGame() {
        this.winplayer = ((this.allplayers.size() != 1) ? "\u65e0" : this.allplayers.get(0));
        final Player p = Bukkit.getPlayerExact(this.winplayer);
        this.arenastate = "END";
        new BukkitRunnable() {
            public void run() {
                for (final Location zb : BattleArena.this.airdropblock) {
                    if (!zb.getChunk().isLoaded()) {
                        zb.getChunk().load();
                    }
                    zb.getBlock().setType(Material.AIR);
                }
                for (final Location ol : BattleArena.this.openchestlocs) {
                    if (!ol.getChunk().isLoaded()) {
                        ol.getChunk().load();
                    }
                    ((Chest) ol.getBlock().getState()).getBlockInventory().clear();
                }
            }
        }.runTaskLater((Plugin) Main.getInstance(), 3L);
        if (p != null && p.isOnline()) {
            p.teleport(this.endloc);
            for (final String mes : Message.getStringList("WinMessage")) {
                p.sendMessage(mes.replace("%kills%", this.kills.get(p.getName()) + ""));
            }
            this.PlayerLeave(p, true);
            final boolean isop = p.isOp();
            try {
                p.setOp(true);
                for (final String command : BattleConfig.getStringList("Commands.win")) {
                    p.performCommand(command.replace("%player%", p.getName()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                p.setOp(isop);
            }
            p.sendMessage(new String[]{Message.getString("Title.Win.Main"), Message.getString("Title.Win.Sub")});
        }
        for (final Hologram hg : this.holograms.keySet()) {
            hg.delete();
        }
        for (final BukkitTask br : this.runnables) {
            br.cancel();
        }
        new BukkitRunnable() {
            public void run() {
                BattleArena.this.resetArena();
            }
        }.runTaskLaterAsynchronously((Plugin) Main.getInstance(), 60L);
    }

    public boolean hasHologramInventory(final Hologram hg) {
        boolean has = false;
        for (final Hologram hologram : this.holograms.keySet()) {
            if (hg.getLocation().distance(hologram.getLocation()) == 0.0) {
                has = true;
                break;
            }
        }
        return has;
    }

    public Inventory getHologramInventory(final Hologram hl) {
        Inventory inv = null;
        for (final Hologram hologram : this.holograms.keySet()) {
            if (hl.getLocation().distance(hologram.getLocation()) == 0.0) {
                inv = this.holograms.get(hologram);
                break;
            }
        }
        return inv;
    }

    public void sendBroadcast(final String mes) {
        for (final String name : this.allplayers) {
            final Player p = Bukkit.getPlayerExact(name);
            if (p != null && p.isOnline()) {
                p.sendMessage(mes);
            }
        }
    }

    public void resetArena() {
        if (Main.getInstance().isBungee()) {
            Bukkit.shutdown();
        } else {
            this.damage = 0.0;
            this.allplayers.clear();
            this.playerinventory.clear();
            this.airdroplocs.clear();
            this.runnables.clear();
            this.airdropblock.clear();
            this.openchestlocs.clear();
            this.jump.clear();
            this.holograms.clear();
            this.kills.clear();
            this.bordertime = 180;
            this.ssborder = false;
            this.hxjd = 0;
            this.waittime = 60;
            this.size = this.data.getDouble("Border");

            BorderData border = Config.Border(this.world.getName());
            border.setX(this.world.getSpawnLocation().getX());
            border.setZ(this.world.getSpawnLocation().getZ());
            border.setRadius(Double.valueOf(this.size).intValue());

            this.bb = BossBarAPI.addBar(new ArrayList<Player>(), Message.getString("BossBar.Main"), BossBarAPI.Color.YELLOW, BossBarAPI.Style.PROGRESS, 1f);
            this.hxbb = BossBarAPI.addBar(new ArrayList<Player>(), Message.getString("BossBar.Main"), BossBarAPI.Color.WHITE, BossBarAPI.Style.PROGRESS, 0f);
            for (final Player p : this.hxbb.getPlayers()) {
                this.hxbb.removePlayer(p);
            }
            for (final Player p : this.bb.getPlayers()) {
                this.bb.removePlayer(p);
            }
            this.ci = Bukkit.createInventory((InventoryHolder) null, 54, "C: " + this.arena_name);
            this.airdrop = Bukkit.createInventory((InventoryHolder) null, 54, "D: " + this.arena_name);
            if (this.data.getKeys(false).contains("ChestItems")) {
                for (final String key : this.data.getConfigurationSection("ChestItems").getKeys(false)) {
                    if (this.data.getItemStack("ChestItems.." + key) != null && this.data.getItemStack("ChestItems.." + key).getType() != Material.AIR) {
                        this.ci.addItem(new ItemStack[]{this.data.getItemStack("ChestItems.." + key)});
                    }
                }
            }
            if (this.data.getKeys(false).contains("DropItems")) {
                for (final String key : this.data.getConfigurationSection("DropItems").getKeys(false)) {
                    if (this.data.getItemStack("DropItems.." + key) != null && this.data.getItemStack("DropItems.." + key).getType() != Material.AIR) {
                        this.airdrop.addItem(new ItemStack[]{this.data.getItemStack("DropItems.." + key)});
                    }
                }
            }
            BorderData config = Config.Border(this.world.getName());
            final Location spawnloc =
                    new Location(this.world, config.getX(), this.world.getSpawnLocation().getY(), config.getZ());
            final int slx = (int) (spawnloc.getBlockX() - this.size / 3.0 + Math.random() * (spawnloc.getBlockX() + this.size / 3.0 - (spawnloc.getBlockX() - this.size / 3.0)));
            final int slz = (int) (spawnloc.getBlockZ() - this.size / 3.0 + Math.random() * (spawnloc.getBlockZ() + this.size / 3.0 - (spawnloc.getBlockZ() - this.size / 3.0)));
            this.startloc = new Location(this.world, (double) slx, 150.0, (double) slz);
            final double distance = this.startloc.distance(spawnloc);
            this.lastloc = new Location(this.world, (slx < spawnloc.getBlockX()) ? (spawnloc.getBlockX() + distance / 3.0) : (spawnloc.getBlockX() - distance / 3.0), 150.0, (slz < spawnloc.getBlockZ()) ? (spawnloc.getBlockZ() + distance / 3.0) : (spawnloc.getBlockZ() - distance / 3.0));
            this.arenastate = "WAIT";
            this.checkPlayer();
            this.upSign();
        }
    }

    public void upSign() {
        new BukkitRunnable() {
            public void run() {
                if (!Main.getInstance().isBungee() && BattleArena.this.signloc != null) {
                    if (!BattleArena.this.signloc.getChunk().isLoaded()) {
                        BattleArena.this.signloc.getChunk().load();
                    }
                    final Block block = BattleArena.this.signloc.getBlock();
                    if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
                        final Sign sign = (Sign) block.getState();
                        final List<String> signlines = BattleConfig.getStringList("GameSign");
                        for (int i = 0; i < signlines.size(); ++i) {
                            sign.setLine(i, BattleArena.this.buildSignLine(signlines.get(i)));
                        }
                        sign.update(true, true);
                    }
                }
            }
        }.runTaskLater((Plugin) Main.getInstance(), 10L);
    }

    public String buildSignLine(final String line) {
        return line.replace("&", "��").replace("%players%", this.allplayers.size() + "").replace("%max%", this.max + "").replace("%arena_name%", this.arenaname).replace("%state%", this.getSignState());
    }

    public String getSignState() {
        String restate = "";
        final String arenastate = this.arenastate;
        switch (arenastate) {
            case "WAIT": {
                restate = BattleConfig.getString("SignState.Wait");
                break;
            }
            case "START": {
                restate = BattleConfig.getString("SignState.Start");
                break;
            }
            case "END": {
                restate = BattleConfig.getString("SignState.Reset");
                break;
            }
            case "WSTART": {
                restate = BattleConfig.getString("SignState.WStart");
                break;
            }
            default: {
                restate = BattleConfig.getString("SignState.Error");
                break;
            }
        }
        return restate.replace("&", "��");
    }

    public Inventory getAirDropInventory() {
        return this.airdrop;
    }

    public Inventory getChestInventory() {
        return this.ci;
    }

    public void setAirDropInventory(final Inventory inv) {
        for (int i = 0; i < inv.getSize(); ++i) {
            this.data.set("DropItems.." + i, (Object) inv.getItem(i));
        }
        try {
            this.data.save(this.df);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.airdrop = inv;
    }

    public void addAriDopChest(final Location loc) {
        this.airdroplocs.remove(loc);
    }

    public void StartAirDrop() {
        final BukkitTask rb = new BukkitRunnable() {
            public void run() {
                final int x = (int) (BattleArena.this.protect.getBlockX() - BattleArena.this.size / 2.0 + Math.random() * (BattleArena.this.protect.getBlockX() + BattleArena.this.size / 2.0 - (BattleArena.this.protect.getBlockX() - BattleArena.this.size / 2.0)));
                final int z = (int) (BattleArena.this.protect.getBlockZ() - BattleArena.this.size / 2.0 + Math.random() * (BattleArena.this.protect.getBlockZ() + BattleArena.this.size / 2.0 - (BattleArena.this.protect.getBlockZ() - BattleArena.this.size / 2.0)));
                final Location droploc = new Location(BattleArena.this.world, (double) x, 150.0, (double) z);
                BattleArena.this.updataAirDrop(droploc);
            }
        }.runTaskTimer((Plugin) Main.getInstance(), 6000L, 6000L);
        this.runnables.add(rb);
    }

    public void updataAirDrop(final Location loc) {
        this.sendBroadcast(Message.getPrefix() + Message.getString("AirDrop").replace("%x%", loc.getBlockX() + "").replace("%y%", loc.getBlockY() + "").replace("%z%", loc.getBlockZ() + ""));
        new BukkitRunnable() {
            Location last = loc;

            public void run() {
                if (!loc.getChunk().isLoaded()) {
                    loc.getChunk().load();
                }
                if (this.last.clone().add(0.0, -1.0, 0.0).getBlock() != null && this.last.clone().add(0.0, -1.0, 0.0).getBlock().getType() != Material.AIR) {
                    this.last.getBlock().setType(Material.CHEST);
                    this.last.clone().add(0.0, 1.0, 0.0).getBlock().setType(Material.AIR);
                    BattleArena.this.airdroplocs.add(this.last);
                    BattleArena.this.airdropblock.add(this.last);
                    BattleArena.this.sendAirDropEffect(this.last);
                    this.cancel();
                    return;
                }
                this.last.clone().add(0.0, 1.0, 0.0).getBlock().setType(Material.AIR);
                this.last.getBlock().setType(Material.MELON_BLOCK);
                this.last = loc.add(0.0, -1.0, 0.0);
            }
        }.runTaskTimer((Plugin) Main.getInstance(), 20L, 20L);
    }

    public void sendAirDropEffect(final Location last) {
        final PacketContainer packet = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);
        //packet.getParticles().write(0, (Object)EnumWrappers.Particle.DRAGON_BREATH);
        packet.getFloat().write(0, (float) last.getBlockX());
        packet.getFloat().write(1, (float) (last.getBlockY() + 1));
        packet.getFloat().write(2, (float) last.getBlockZ());
        packet.getFloat().write(3, 0.0f);
        packet.getFloat().write(4, 0.0f);
        packet.getFloat().write(5, 0.0f);
        packet.getFloat().write(6, 1.0f);
        packet.getIntegers().write(0, 11);
        packet.getIntegerArrays().write(0, new int[0]);
        new BukkitRunnable() {
            int amount = 120;

            public void run() {
                if (this.amount <= 0) {
                    this.cancel();
                    return;
                }
                Main.getProtocolManager().broadcastServerPacket(packet);
                --this.amount;
            }
        }.runTaskTimerAsynchronously((Plugin) Main.getInstance(), 5L, 5L);
    }

    public void setChestInventory(final Inventory inv) {
        for (int i = 0; i < inv.getSize(); ++i) {
            this.data.set("ChestItems.." + i, (Object) inv.getItem(i));
        }
        try {
            this.data.save(this.df);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ci = inv;
    }

    public void SendBorder(final World world, final Location center, final double distance, final int time) {
        ++this.damage;

        BorderData border = Config.Border(this.world.getName());
        border.setX(this.world.getSpawnLocation().getX());
        border.setZ(this.world.getSpawnLocation().getZ());
        border.setRadius(Double.valueOf(distance).intValue());

        final int cgx = center.getBlockX();
        final int cgz = center.getBlockZ();
        final int rspeed = (int) distance / time;
        new BukkitRunnable() {
            public void run() {
                if (border.getRadiusX() - BattleArena.this.size <= 0.0) {

                    border.setX(center.getX());
                    border.setZ(center.getZ());

                    BattleArena.this.size /= 2.0;
                    BattleArena.this.ChangeProtectLocation();
                    BattleArena.this.bordertime = 180;
                    BattleArena.this.ssborder = false;
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously((Plugin) Main.getInstance(), 20L, 20L);
    }
}
