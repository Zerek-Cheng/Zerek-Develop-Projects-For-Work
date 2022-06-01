package me.alessiodp.parties;

import java.util.ArrayList;

import me.alessiodp.parties.configuration.Messages;
import me.alessiodp.parties.configuration.Variables;
import me.alessiodp.parties.handlers.Party;
import me.alessiodp.parties.handlers.PartyHandler;
import me.alessiodp.parties.handlers.PlayerHandler;
import me.alessiodp.parties.handlers.ThePlayer;
import me.alessiodp.parties.utils.LogHandler;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerListener
        implements Listener {
    Parties plugin;

    public PlayerListener(Parties instance) {
        this.plugin = instance;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(player);

        this.plugin.getPlayerHandler().initSpy(player.getUniqueId());
        if (!tp.haveParty()) {
            return;
        }
        Party party = this.plugin.getPartyHandler().loadParty(tp.getPartyName());
        if (party == null) {
            if ((player.hasPermission("parties.admin.updates")) && (Variables.warnupdates) &&
                    (this.plugin.updateavailable)) {
                tp.sendMessage(Messages.availableupdate.replace("%version%", this.plugin.newversion).replace("%thisversion%", this.plugin.getDescription().getVersion()));
            }
            return;
        }
        if (!party.onlinePlayers.contains(player)) {
            party.onlinePlayers.add(player);
        }
        this.plugin.getPartyHandler().scoreboard_addPlayer(player, tp.getPartyName());
        if (!party.motd.isEmpty()) {
            tp.sendMessage(Messages.motd_header);
            String[] arrayOfString;
            int i = (arrayOfString = party.motd.split(Variables.motd_newline)).length;
            for (int j = 0; j < i; j++) {
                String str = arrayOfString[j];
                tp.sendMessage(Messages.motd_color.concat(str));
            }
            tp.sendMessage(Messages.motd_footer);
        }
        if ((player.hasPermission("parties.admin.updates")) && (Variables.warnupdates) && (this.plugin.updateavailable)) {
            tp.sendMessage(Messages.availableupdate.replace("%version%", this.plugin.newversion).replace("%thisversion%", this.plugin.getDescription().getVersion()));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(p);
        if (tp.haveParty()) {
            if (tp.getHomeTask() != -1) {
                this.plugin.getPlayerHandler().remHomeCount();
            }
            Party party = this.plugin.getPartyHandler().loadParty(tp.getPartyName());
            if (party != null) {
                if (party.onlinePlayers.contains(p)) {
                    party.onlinePlayers.remove(p);
                }
                if (Variables.database_type.equalsIgnoreCase("none")) {
                    if (party.onlinePlayers.size() == 0) {
                        party.removeParty();
                        this.plugin.getPartyHandler().removePartyList(tp.getPartyName());
                    } else if (Variables.database_none_leaderleft) {
                        boolean flag = false;
                        for (Player player : party.onlinePlayers) {
                            if (party.leaders.contains(player.getUniqueId())) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            party.sendBroadcastParty(p, Messages.leave_disbanded);
                            party.removeParty();
                            this.plugin.getPartyHandler().removePartyList(tp.getPartyName());
                        }
                    }
                }
            }
            this.plugin.getPartyHandler().scoreboard_removePlayer(p);
        }
        this.plugin.getPlayerHandler().removePlayer(p);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        Player p = event.getPlayer();
        ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(p);
        if (tp.haveParty()) {
            if (tp.getHomeTask() != -1) {
                this.plugin.getPlayerHandler().remHomeCount();
            }
            Party party = this.plugin.getPartyHandler().loadParty(tp.getPartyName());
            if (party != null) {
                if (party.onlinePlayers.contains(p)) {
                    party.onlinePlayers.remove(p);
                }
                if (Variables.database_type.equalsIgnoreCase("none")) {
                    if (party.onlinePlayers.size() == 0) {
                        party.removeParty();
                        this.plugin.getPartyHandler().removePartyList(tp.getPartyName());
                    } else if (Variables.database_none_leaderleft) {
                        boolean flag = false;
                        for (Player player : party.onlinePlayers) {
                            if (party.leaders.contains(player.getUniqueId())) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            party.sendBroadcastParty(p, Messages.leave_disbanded);
                            party.removeParty();
                            this.plugin.getPartyHandler().removePartyList(tp.getPartyName());
                        }
                    }
                }
            }
            this.plugin.getPartyHandler().scoreboard_removePlayer(p);
        }
        this.plugin.getPlayerHandler().removePlayer(p);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
            if (!Variables.preventdamage) {
                return;
            }
            Player p = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(p);
            if (tp.haveParty()) {
                Party party = this.plugin.getPartyHandler().loadParty(tp.getPartyName());
                if (party.onlinePlayers.contains(attacker)) {
                    this.plugin.getPlayerHandler().getThePlayer(attacker).sendMessage(Messages.canthitmates);
                    if (Variables.warnondamage) {
                        for (Player onlineP : party.onlinePlayers) {
                            if (onlineP != attacker) {
                                if (party.leaders.contains(onlineP.getName())) {
                                    this.plugin.getPlayerHandler().getThePlayer(onlineP).sendMessage(Messages.warnondamage.replace("%player%", attacker.getName()).replace("%victim%", p.getName()));
                                }
                            }
                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
        if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Arrow)) &&
                ((((Arrow) event.getDamager()).getShooter() instanceof Player))) {
            if (!Variables.preventdamage) {
                return;
            }
            Player p = (Player) event.getEntity();
            Player attacker = (Player) ((Arrow) event.getDamager()).getShooter();

            ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(p);
            if (tp.haveParty()) {
                Party party = this.plugin.getPartyHandler().loadParty(tp.getPartyName());
                if (party.onlinePlayers.contains(attacker)) {
                    if ((!Variables.preventdamagefromleader) && (party.leaders.contains(attacker.getUniqueId()))) {
                        return;
                    }
                    this.plugin.getPlayerHandler().getThePlayer(attacker).sendMessage(Messages.canthitmates);
                    if (Variables.warnondamage) {
                        for (Player onlineP : party.onlinePlayers) {
                            if (onlineP != attacker) {
                                if (party.leaders.contains(onlineP.getName())) {
                                    this.plugin.getPlayerHandler().getThePlayer(onlineP).sendMessage(Messages.warnondamage.replace("%player%", attacker.getName()).replace("%victim%", p.getName()));
                                }
                            }
                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player p = event.getPlayer();
        ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(p);
        if ((tp.haveParty()) && (tp.chatParty())) {
            Party party = this.plugin.getPartyHandler().loadParty(tp.getPartyName());

            party.sendPlayerMessage(p, event.getMessage());
            party.sendSpyMessage(p, event.getMessage());
            if (Variables.log_chat) {
                LogHandler.log("[%time%] Chat of " + party.name + ":" + p.getName() + "[" + p.getUniqueId() + "]:" + event.getMessage());
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDie(EntityDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player)) {
            return;
        }
        if (!Variables.divideexp) {
            return;
        }
        int exp = event.getDroppedExp();
        int exptotal = exp;
        event.setDroppedExp(0);
        ArrayList<Player> list = new ArrayList();

        Player killer = event.getEntity().getKiller();
        ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(killer);

        list.add(killer);
        if (tp.haveParty()) {
            Party party = this.plugin.getPartyHandler().loadParty(tp.getPartyName());
            for (Player p : party.onlinePlayers) {
                if (!killer.getLocation().getWorld().getName().equalsIgnoreCase(p.getLocation().getWorld().getName()))
                    continue;
                if ((killer.getLocation().distance(p.getLocation()) < Variables.exprange) && (p != killer)) {
                    list.add(p);
                }
            }
        }
        if (list.size() < 2) {
            event.setDroppedExp(exp);
            return;
        }
        if (exp < 1) {
            return;
        }
        if (exp == 1) {
            killer.giveExp(exp);
            tp.sendMessage(Messages.expgain.replace("%exp%", String.valueOf(exp)).replace("%exptotal%", String.valueOf(exptotal)).replace("%mob%", event.getEntity().getType().getName()));
            return;
        }
        exp /= list.size();
        for (int c = 0; c < list.size(); c++) {
            ((Player) list.get(c)).giveExp(exp);
            if (list.get(c) == killer) {
                tp.sendMessage(Messages.expgain.replace("%exp%", String.valueOf(exp)).replace("%exptotal%", String.valueOf(exptotal)).replace("%mob%", event.getEntity().getType().getName()));
            } else {
                this.plugin.getPlayerHandler().getThePlayer((Player) list.get(c)).sendMessage(Messages.expgainother.replace("%exp%", String.valueOf(exp)).replace("%exptotal%", String.valueOf(exptotal)).replace("%mob%", event.getEntity().getType().getName()), killer);
            }
        }
    }

    @EventHandler
    public void onEntityDieKills(EntityDeathEvent event) {
        if ((!(event.getEntity().getKiller() instanceof Player)) || (!Variables.kill_enable)) {
            return;
        }
        ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(event.getEntity().getKiller());
        if (!tp.haveParty()) {
            return;
        }
        Party party = this.plugin.getPartyHandler().loadParty(tp.getPartyName());
        if (((event.getEntity() instanceof Monster)) && (Variables.kill_save_mobshostile)) {
            party.kills += 1;
            party.updateParty();
        } else if (((event.getEntity() instanceof Animals)) && (Variables.kill_save_mobsneutral)) {
            party.kills += 1;
            party.updateParty();
        } else if (((event.getEntity() instanceof Player)) && (Variables.kill_save_players)) {
            party.kills += 1;
            party.updateParty();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if ((event.getFrom().getBlockX() == event.getTo().getBlockX()) &&
                (event.getFrom().getBlockY() == event.getTo().getBlockY()) &&
                (event.getFrom().getBlockZ() == event.getTo().getBlockZ())) {
            return;
        }
        if (!Variables.homecancelmove) {
            return;
        }
        if (this.plugin.getPlayerHandler().getHomeCount() == 0) {
            return;
        }
        ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer(event.getPlayer());
        if ((tp.getHomeTask() != -1) &&
                (tp.getHomeFrom().distance(event.getTo()) > Variables.homedistance)) {
            this.plugin.getServer().getScheduler().cancelTask(tp.getHomeTask());
            tp.setHomeTask(-1);
            this.plugin.getPlayerHandler().remHomeCount();
            tp.sendMessage(Messages.home_denied);
        }
    }

    @EventHandler
    public void onPlayerHittedHome(EntityDamageByEntityEvent event) {
        if ((event.getEntity() instanceof Player)) {
            if (!Variables.homecancelmove) {
                return;
            }
            if (this.plugin.getPlayerHandler().getHomeCount() == 0) {
                return;
            }
            ThePlayer tp = this.plugin.getPlayerHandler().getThePlayer((Player) event.getEntity());
            if (tp.getHomeTask() != -1) {
                this.plugin.getServer().getScheduler().cancelTask(tp.getHomeTask());
                tp.setHomeTask(-1);
                this.plugin.getPlayerHandler().remHomeCount();
                tp.sendMessage(Messages.home_denied);
            }
        }
    }
}
