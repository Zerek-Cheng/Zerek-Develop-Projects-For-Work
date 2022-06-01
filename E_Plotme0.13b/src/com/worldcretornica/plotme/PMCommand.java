/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Biome
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.worldcretornica.plotme;

import com.worldcretornica.plotme.utils.MinecraftFontWidthCalculator;
import net.milkbowl.vault.economy.EconomyResponse;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Date;
import java.util.*;

public class PMCommand
        implements CommandExecutor {
    private PlotMe plugin;
    private final ChatColor BLUE = ChatColor.BLUE;
    private final ChatColor RED = ChatColor.RED;
    private final ChatColor RESET = ChatColor.RESET;
    private final ChatColor AQUA = ChatColor.AQUA;
    private final ChatColor GREEN = ChatColor.GREEN;
    private final ChatColor ITALIC = ChatColor.ITALIC;
    private final String PREFIX = PlotMe.PREFIX;
    private final String LOG = "[" + PlotMe.NAME + " Event] ";
    private final boolean isAdv = PlotMe.advancedlogging;

    public PMCommand(PlotMe instance) {
        this.plugin = instance;
    }

    private String C(String caption) {
        return PlotMe.caption(caption);
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if (l.equalsIgnoreCase("plotme") || l.equalsIgnoreCase("plot") || l.equalsIgnoreCase("p")) {
            if (!(s instanceof Player)) {
                if (args.length == 0 || args[0].equalsIgnoreCase("1")) {
                    s.sendMessage(this.C("ConsoleHelpMain"));
                    s.sendMessage(" - /plotme reload");
                    s.sendMessage(this.C("ConsoleHelpReload"));
                    return true;
                }
                String a0 = args[0].toString();
                if (!(s instanceof Player)) {
                    if (a0.equalsIgnoreCase("reload")) {
                        return this.reload(s, args);
                    }
                    if (a0.equalsIgnoreCase(this.C("CommandResetExpired"))) {
                        return this.resetexpired(s, args);
                    }
                }
            } else {
                Player p = (Player) s;
                if (args.length == 0) {
                    return this.showhelp(p, 1);
                }
                String a0 = args[0].toString();
                int ipage = -1;
                try {
                    ipage = Integer.parseInt(a0);
                } catch (Exception exception) {
                    // empty catch block
                }
                if (ipage != -1) {
                    return this.showhelp(p, ipage);
                }
                if (a0.equalsIgnoreCase(this.C("CommandHelp"))) {
                    ipage = -1;
                    if (args.length > 1) {
                        String a1 = args[1].toString();
                        ipage = -1;
                        try {
                            ipage = Integer.parseInt(a1);
                        } catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    if (ipage != -1) {
                        return this.showhelp(p, ipage);
                    }
                    return this.showhelp(p, 1);
                }
                if (a0.equalsIgnoreCase(this.C("CommandClaim"))) {
                    return this.claim(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandAuto"))) {
                    return this.auto(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandInfo")) || a0.equalsIgnoreCase("i")) {
                    return this.info(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandComment"))) {
                    return this.comment(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandComments")) || a0.equalsIgnoreCase("c")) {
                    return this.comments(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandBiome")) || a0.equalsIgnoreCase("b")) {
                    return this.biome(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandBiomelist"))) {
                    return this.biomelist((CommandSender) p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandId"))) {
                    return this.id(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandTp"))) {
                    return this.tp(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandClear"))) {
                    return this.clear(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandReset"))) {
                    return this.reset(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandAdd")) || a0.equalsIgnoreCase("+")) {
                    return this.add(p, args);
                }
                if (PlotMe.allowToDeny.booleanValue()) {
                    if (a0.equalsIgnoreCase(this.C("CommandDeny"))) {
                        return this.deny(p, args);
                    }
                    if (a0.equalsIgnoreCase(this.C("CommandUndeny"))) {
                        return this.undeny(p, args);
                    }
                }
                if (a0.equalsIgnoreCase(this.C("CommandRemove")) || a0.equalsIgnoreCase("-")) {
                    return this.remove(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandSetowner")) || a0.equalsIgnoreCase("o")) {
                    return this.setowner(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandMove")) || a0.equalsIgnoreCase("m")) {
                    return this.move(p, args);
                }
                if (a0.equalsIgnoreCase("reload")) {
                    return this.reload(s, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandWEAnywhere"))) {
                    return this.weanywhere(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandList"))) {
                    return this.plotlist(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandExpired"))) {
                    return this.expired(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandAddtime"))) {
                    return this.addtime(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandDone"))) {
                    return this.done(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandDoneList"))) {
                    return this.donelist(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandProtect"))) {
                    return this.protect(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandSell"))) {
                    return this.sell(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandDispose"))) {
                    return this.dispose(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandAuction"))) {
                    return this.auction(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandBuy"))) {
                    return this.buy(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandBid"))) {
                    return this.bid(p, args);
                }
                if (a0.startsWith(this.C("CommandHome")) || a0.startsWith("h")) {
                    return this.home(p, args);
                }
                if (a0.equalsIgnoreCase(this.C("CommandResetExpired"))) {
                    return this.resetexpired((CommandSender) p, args);
                }
            }
        }
        return false;
    }

    private boolean resetexpired(CommandSender s, String[] args) {
        if (PlotMe.cPerms(s, "PlotMe.admin.resetexpired")) {
            if (args.length <= 1) {
                this.Send(s, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandResetExpired") + " <" + this.C("WordWorld") + "> " + (Object) this.RESET + "Example: " + (Object) this.RED + "/plotme " + this.C("CommandResetExpired") + " plotworld ");
            } else if (PlotMe.worldcurrentlyprocessingexpired != null) {
                this.Send(s, String.valueOf(PlotMe.cscurrentlyprocessingexpired.getName()) + " " + this.C("MsgAlreadyProcessingPlots"));
            } else {
                World w = s.getServer().getWorld(args[1]);
                if (w == null) {
                    this.Send(s, (Object) this.RED + this.C("WordWorld") + " '" + args[1] + "' " + this.C("MsgDoesNotExistOrNotLoaded"));
                    return true;
                }
                if (!PlotManager.isPlotWorld(w)) {
                    this.Send(s, (Object) this.RED + this.C("MsgNotPlotWorld"));
                    return true;
                }
                PlotMe.worldcurrentlyprocessingexpired = w;
                PlotMe.cscurrentlyprocessingexpired = s;
                PlotMe.counterexpired = 50;
                PlotMe.nbperdeletionprocessingexpired = 5;
                this.plugin.scheduleTask(new PlotRunnableDeleteExpire(), 5, 50);
            }
        } else {
            this.Send(s, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean bid(Player p, String[] args) {
        if (PlotManager.isEconomyEnabled(p)) {
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.bid")) {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    Plot plot = PlotManager.getPlotById(p, id);
                    if (plot.auctionned) {
                        String bidder = p.getName();
                        if (plot.owner.equalsIgnoreCase(bidder)) {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgCannotBidOwnPlot"));
                        } else if (args.length == 2) {
                            double bid = 0.0;
                            double currentbid = plot.currentbid;
                            String currentbidder = plot.currentbidder;
                            try {
                                bid = Double.parseDouble(args[1]);
                            } catch (Exception exception) {
                                // empty catch block
                            }
                            if (bid < currentbid || bid == currentbid && !currentbidder.equals("")) {
                                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgInvalidBidMustBeAbove") + " " + (Object) this.RESET + this.f(plot.currentbid, false));
                            } else {
                                double balance = PlotMe.economy.getBalance(bidder);
                                if (bid >= balance && !currentbidder.equals(bidder) || currentbidder.equals(bidder) && bid > balance + currentbid) {
                                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughBid"));
                                } else {
                                    EconomyResponse er = PlotMe.economy.withdrawPlayer(bidder, bid);
                                    if (er.transactionSuccess()) {
                                        if (!currentbidder.equals("")) {
                                            EconomyResponse er2 = PlotMe.economy.depositPlayer(currentbidder, currentbid);
                                            if (!er2.transactionSuccess()) {
                                                this.Send((CommandSender) p, er2.errorMessage);
                                                this.warn(er2.errorMessage);
                                            } else {
                                                for (Player player : Bukkit.getOnlinePlayers()) {
                                                    if (player.getName().equalsIgnoreCase(currentbidder)) {
                                                        this.Send((CommandSender) player, String.valueOf(this.C("MsgOutbidOnPlot")) + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + ". " + this.f(bid));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        plot.currentbidder = bidder;
                                        plot.currentbid = bid;
                                        plot.updateField("currentbidder", bidder);
                                        plot.updateField("currentbid", bid);
                                        PlotManager.setSellSign(p.getWorld(), plot);
                                        this.Send((CommandSender) p, String.valueOf(this.C("MsgBidAccepted")) + " " + this.f(-bid));
                                        if (this.isAdv) {
                                            PlotMe.logger.info(String.valueOf(this.LOG) + bidder + " bid " + bid + " on plot " + id);
                                        }
                                    } else {
                                        this.Send((CommandSender) p, er.errorMessage);
                                        this.warn(er.errorMessage);
                                    }
                                }
                            }
                        } else {
                            this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandBid") + " <" + this.C("WordAmount") + "> " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandBid") + " 100");
                        }
                    } else {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPlotNotAuctionned"));
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            } else {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgEconomyDisabledWorld"));
        }
        return true;
    }

    private boolean buy(Player p, String[] args) {
        if (PlotManager.isEconomyEnabled(p)) {
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.buy") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.buy")) {
                Location l = p.getLocation();
                String id = PlotManager.getPlotId(l);
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    Plot plot = PlotManager.getPlotById(p, id);
                    if (!plot.forsale) {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPlotNotForSale"));
                    } else {
                        String buyer = p.getName();
                        if (plot.owner.equalsIgnoreCase(buyer)) {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgCannotBuyOwnPlot"));
                        } else {
                            int plotlimit = PlotMe.getPlotLimit(p);
                            if (plotlimit != -1 && PlotManager.getNbOwnedPlot(p) >= plotlimit) {
                                this.Send((CommandSender) p, String.valueOf(this.C("MsgAlreadyReachedMaxPlots")) + " (" + PlotManager.getNbOwnedPlot(p) + "/" + PlotMe.getPlotLimit(p) + "). " + this.C("WordUse") + " " + (Object) this.RED + "/plotme " + this.C("CommandHome") + (Object) this.RESET + " " + this.C("MsgToGetToIt"));
                            } else {
                                World w = p.getWorld();
                                double cost = plot.customprice;
                                if (PlotMe.economy.getBalance(buyer) < cost) {
                                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughBuy"));
                                } else {
                                    EconomyResponse er = PlotMe.economy.withdrawPlayer(buyer, cost);
                                    if (er.transactionSuccess()) {
                                        String oldowner = plot.owner;
                                        if (!oldowner.equalsIgnoreCase("$Bank$")) {
                                            EconomyResponse er2 = PlotMe.economy.depositPlayer(oldowner, cost);
                                            if (!er2.transactionSuccess()) {
                                                this.Send((CommandSender) p, (Object) this.RED + er2.errorMessage);
                                                this.warn(er2.errorMessage);
                                            } else {
                                                for (Player player : Bukkit.getOnlinePlayers()) {
                                                    if (player.getName().equalsIgnoreCase(oldowner)) {
                                                        this.Send((CommandSender) player, String.valueOf(this.C("WordPlot")) + " " + id + " " + this.C("MsgSoldTo") + " " + buyer + ". " + this.f(cost));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        plot.owner = buyer;
                                        plot.customprice = 0.0;
                                        plot.forsale = false;
                                        plot.updateField("owner", buyer);
                                        plot.updateField("customprice", 0);
                                        plot.updateField("forsale", false);
                                        PlotManager.adjustWall(l);
                                        PlotManager.setSellSign(w, plot);
                                        PlotManager.setOwnerSign(w, plot);
                                        this.Send((CommandSender) p, String.valueOf(this.C("MsgPlotBought")) + " " + this.f(-cost));
                                        if (this.isAdv) {
                                            PlotMe.logger.info(String.valueOf(this.LOG) + buyer + " " + this.C("MsgBoughtPlot") + " " + id + " " + this.C("WordFor") + " " + cost);
                                        }
                                    } else {
                                        this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                        this.warn(er.errorMessage);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            } else {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgEconomyDisabledWorld"));
        }
        return true;
    }

    private boolean auction(Player p, String[] args) {
        if (PlotManager.isEconomyEnabled(p)) {
            PlotMapInfo pmi = PlotManager.getMap(p);
            if (pmi.CanPutOnSale) {
                if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.auction") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.auction")) {
                    String id = PlotManager.getPlotId(p.getLocation());
                    if (id.equals("")) {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                    } else if (!PlotManager.isPlotAvailable(id, p)) {
                        Plot plot = PlotManager.getPlotById(p, id);
                        String name = p.getName();
                        if (plot.owner.equalsIgnoreCase(name) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.auction")) {
                            World w = p.getWorld();
                            if (plot.auctionned) {
                                if (!plot.currentbidder.equalsIgnoreCase("") && !PlotMe.cPerms((CommandSender) p, "PlotMe.admin.auction")) {
                                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPlotHasBidsAskAdmin"));
                                } else {
                                    if (!plot.currentbidder.equalsIgnoreCase("")) {
                                        EconomyResponse er = PlotMe.economy.depositPlayer(plot.currentbidder, plot.currentbid);
                                        if (!er.transactionSuccess()) {
                                            this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                            this.warn(er.errorMessage);
                                        } else {
                                            for (Player player : Bukkit.getOnlinePlayers()) {
                                                if (player.getName().equalsIgnoreCase(plot.currentbidder)) {
                                                    this.Send((CommandSender) player, String.valueOf(this.C("MsgAuctionCancelledOnPlot")) + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + ". " + this.f(plot.currentbid));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    plot.auctionned = false;
                                    PlotManager.adjustWall(p.getLocation());
                                    PlotManager.setSellSign(w, plot);
                                    plot.currentbid = 0.0;
                                    plot.currentbidder = "";
                                    plot.updateField("currentbid", 0);
                                    plot.updateField("currentbidder", "");
                                    plot.updateField("auctionned", false);
                                    this.Send((CommandSender) p, this.C("MsgAuctionCancelled"));
                                    if (this.isAdv) {
                                        PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgStoppedTheAuctionOnPlot") + " " + id);
                                    }
                                }
                            } else {
                                double bid = 1.0;
                                if (args.length == 2) {
                                    try {
                                        bid = Double.parseDouble(args[1]);
                                    } catch (Exception exception) {
                                        // empty catch block
                                    }
                                }
                                if (bid < 0.0) {
                                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgInvalidAmount"));
                                } else {
                                    plot.currentbid = bid;
                                    plot.auctionned = true;
                                    PlotManager.adjustWall(p.getLocation());
                                    PlotManager.setSellSign(w, plot);
                                    plot.updateField("currentbid", bid);
                                    plot.updateField("auctionned", true);
                                    this.Send((CommandSender) p, this.C("MsgAuctionStarted"));
                                    if (this.isAdv) {
                                        PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgStartedAuctionOnPlot") + " " + id + " " + this.C("WordAt") + " " + bid);
                                    }
                                }
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgDoNotOwnPlot"));
                        }
                    } else {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
                }
            } else {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgSellingPlotsIsDisabledWorld"));
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgEconomyDisabledWorld"));
        }
        return true;
    }

    private boolean dispose(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.dispose") || PlotMe.cPerms((CommandSender) p, "PlotMe.use.dispose")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    Plot plot = PlotManager.getPlotById(p, id);
                    if (plot.protect) {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPlotProtectedNotDisposed"));
                    } else {
                        String name = p.getName();
                        if (plot.owner.equalsIgnoreCase(name) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.dispose")) {
                            PlotMapInfo pmi = PlotManager.getMap(p);
                            double cost = pmi.DisposePrice;
                            if (PlotManager.isEconomyEnabled(p)) {
                                String currentbidder;
                                if (cost != 0.0 && PlotMe.economy.getBalance(name) < cost) {
                                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughDispose"));
                                    return true;
                                }
                                EconomyResponse er = PlotMe.economy.withdrawPlayer(name, cost);
                                if (!er.transactionSuccess()) {
                                    this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                    this.warn(er.errorMessage);
                                    return true;
                                }
                                if (plot.auctionned && !(currentbidder = plot.currentbidder).equals("")) {
                                    EconomyResponse er2 = PlotMe.economy.depositPlayer(currentbidder, plot.currentbid);
                                    if (!er2.transactionSuccess()) {
                                        this.Send((CommandSender) p, (Object) this.RED + er2.errorMessage);
                                        this.warn(er2.errorMessage);
                                    } else {
                                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                                            if (player.getName().equalsIgnoreCase(currentbidder)) {
                                                this.Send((CommandSender) player, String.valueOf(this.C("WordPlot")) + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + " " + this.C("MsgWasDisposed") + " " + this.f(cost));
                                                break;
                                            }
                                        }

                                    }
                                }
                            }
                            World w = p.getWorld();
                            if (!PlotManager.isPlotAvailable(id, p)) {
                                PlotManager.getPlots(p).remove(id);
                            }
                            PlotManager.removeOwnerSign(w, id);
                            PlotManager.removeSellSign(w, id);
                            SqlManager.deletePlot(PlotManager.getIdX(id), PlotManager.getIdZ(id), w.getName().toLowerCase());
                            this.Send((CommandSender) p, this.C("MsgPlotDisposedAnyoneClaim"));
                            if (this.isAdv) {
                                PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgDisposedPlot") + " " + id);
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursCannotDispose"));
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean sell(Player p, String[] args) {
        if (PlotManager.isEconomyEnabled(p)) {
            PlotMapInfo pmi = PlotManager.getMap(p);
            if (pmi.CanSellToBank || pmi.CanPutOnSale) {
                if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.sell") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.sell")) {
                    Location l = p.getLocation();
                    String id = PlotManager.getPlotId(l);
                    if (id.equals("")) {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                    } else if (!PlotManager.isPlotAvailable(id, p)) {
                        Plot plot = PlotManager.getPlotById(p, id);
                        if (plot.owner.equalsIgnoreCase(p.getName()) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.sell")) {
                            World w = p.getWorld();
                            String name = p.getName();
                            if (plot.forsale) {
                                plot.customprice = 0.0;
                                plot.forsale = false;
                                plot.updateField("customprice", 0);
                                plot.updateField("forsale", false);
                                PlotManager.adjustWall(l);
                                PlotManager.setSellSign(w, plot);
                                this.Send((CommandSender) p, this.C("MsgPlotNoLongerSale"));
                                if (this.isAdv) {
                                    PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgRemovedPlot") + " " + id + " " + this.C("MsgFromBeingSold"));
                                }
                            } else {
                                double price;
                                boolean bank;
                                block38:
                                {
                                    price = pmi.SellToPlayerPrice;
                                    bank = false;
                                    if (args.length == 2) {
                                        if (args[1].equalsIgnoreCase("bank")) {
                                            bank = true;
                                        } else if (pmi.CanCustomizeSellPrice) {
                                            try {
                                                price = Double.parseDouble(args[1]);
                                            } catch (Exception e) {
                                                if (pmi.CanSellToBank) {
                                                    this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + " /plotme " + this.C("CommandSellBank") + "|<" + this.C("WordAmount") + ">");
                                                    p.sendMessage("  " + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandSellBank") + " " + (Object) this.RESET + " or " + (Object) this.RED + " /plotme " + this.C("CommandSell") + " 200");
                                                    break block38;
                                                }
                                                this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + " /plotme " + this.C("CommandSell") + " <" + this.C("WordAmount") + ">" + (Object) this.RESET + " " + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandSell") + " 200");
                                            }
                                        } else {
                                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgCannotCustomPriceDefault") + " " + price);
                                            return true;
                                        }
                                    }
                                }
                                if (bank) {
                                    if (!pmi.CanSellToBank) {
                                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgCannotSellToBank"));
                                    } else {
                                        EconomyResponse er;
                                        double sellprice;
                                        String currentbidder = plot.currentbidder;
                                        if (!currentbidder.equals("")) {
                                            double bid = plot.currentbid;
                                            er = PlotMe.economy.depositPlayer(currentbidder, bid);
                                            if (!er.transactionSuccess()) {
                                                this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                                this.warn(er.errorMessage);
                                            } else {
                                                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                                                    if (player.getName().equalsIgnoreCase(currentbidder)) {
                                                        this.Send((CommandSender) player, String.valueOf(this.C("WordPlot")) + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + " " + this.C("MsgSoldToBank") + " " + this.f(bid));
                                                        break;
                                                    }
                                                }

                                            }
                                        }
                                        if ((er = PlotMe.economy.depositPlayer(name, sellprice = pmi.SellToBankPrice)).transactionSuccess()) {
                                            plot.owner = "$Bank$";
                                            plot.forsale = true;
                                            plot.customprice = pmi.BuyFromBankPrice;
                                            plot.auctionned = false;
                                            plot.currentbidder = "";
                                            plot.currentbid = 0.0;
                                            plot.removeAllAllowed();
                                            PlotManager.setOwnerSign(w, plot);
                                            PlotManager.setSellSign(w, plot);
                                            plot.updateField("owner", plot.owner);
                                            plot.updateField("forsale", true);
                                            plot.updateField("auctionned", true);
                                            plot.updateField("customprice", plot.customprice);
                                            plot.updateField("currentbidder", "");
                                            plot.updateField("currentbid", 0);
                                            this.Send((CommandSender) p, String.valueOf(this.C("MsgPlotSold")) + " " + this.f(sellprice));
                                            if (this.isAdv) {
                                                PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgSoldToBankPlot") + " " + id + " " + this.C("WordFor") + " " + sellprice);
                                            }
                                        } else {
                                            this.Send((CommandSender) p, " " + er.errorMessage);
                                            this.warn(er.errorMessage);
                                        }
                                    }
                                } else if (price < 0.0) {
                                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgInvalidAmount"));
                                } else {
                                    plot.customprice = price;
                                    plot.forsale = true;
                                    plot.updateField("customprice", price);
                                    plot.updateField("forsale", true);
                                    PlotManager.adjustWall(l);
                                    PlotManager.setSellSign(w, plot);
                                    this.Send((CommandSender) p, this.C("MsgPlotForSale"));
                                    if (this.isAdv) {
                                        PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgPutOnSalePlot") + " " + id + " " + this.C("WordFor") + " " + price);
                                    }
                                }
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgDoNotOwnPlot"));
                        }
                    } else {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
                }
            } else {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgSellingPlotsIsDisabledWorld"));
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgEconomyDisabledWorld"));
        }
        return true;
    }

    private boolean protect(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.protect") || PlotMe.cPerms((CommandSender) p, "PlotMe.use.protect")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            String id = PlotManager.getPlotId(p.getLocation());
            if (id.equals("")) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
            } else if (!PlotManager.isPlotAvailable(id, p)) {
                Plot plot = PlotManager.getPlotById(p, id);
                String name = p.getName();
                if (plot.owner.equalsIgnoreCase(name) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.protect")) {
                    if (plot.protect) {
                        plot.protect = false;
                        PlotManager.adjustWall(p.getLocation());
                        plot.updateField("protected", false);
                        this.Send((CommandSender) p, this.C("MsgPlotNoLongerProtected"));
                        if (this.isAdv) {
                            PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgUnprotectedPlot") + " " + id);
                        }
                    } else {
                        PlotMapInfo pmi = PlotManager.getMap(p);
                        double cost = 0.0;
                        if (PlotManager.isEconomyEnabled(p)) {
                            cost = pmi.ProtectPrice;
                            if (PlotMe.economy.getBalance(name) < cost) {
                                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughProtectPlot"));
                                return true;
                            }
                            EconomyResponse er = PlotMe.economy.withdrawPlayer(name, cost);
                            if (!er.transactionSuccess()) {
                                this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                this.warn(er.errorMessage);
                                return true;
                            }
                        }
                        plot.protect = true;
                        PlotManager.adjustWall(p.getLocation());
                        plot.updateField("protected", true);
                        this.Send((CommandSender) p, String.valueOf(this.C("MsgPlotNowProtected")) + " " + this.f(-cost));
                        if (this.isAdv) {
                            PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgProtectedPlot") + " " + id);
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgDoNotOwnPlot"));
                }
            } else {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean donelist(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.done")) {
            PlotMapInfo pmi = PlotManager.getMap(p);
            if (pmi == null) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            HashMap<String, Plot> plots = pmi.plots;
            ArrayList<Plot> finishedplots = new ArrayList<Plot>();
            int nbfinished = 0;
            int maxpage = 0;
            int pagesize = 8;
            int page = 1;
            if (args.length == 2) {
                try {
                    page = Integer.parseInt(args[1]);
                } catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            for (String id : plots.keySet()) {
                Plot plot = plots.get(id);
                if (!plot.finished) continue;
                finishedplots.add(plot);
                ++nbfinished;
            }
            Collections.sort(finishedplots, new PlotFinishedComparator());
            maxpage = (int) Math.ceil((double) nbfinished / (double) pagesize);
            if (finishedplots.size() == 0) {
                this.Send((CommandSender) p, this.C("MsgNoPlotsFinished"));
            } else {
                this.Send((CommandSender) p, String.valueOf(this.C("MsgFinishedPlotsPage")) + " " + page + "/" + maxpage);
                int i = (page - 1) * pagesize;
                while (i < finishedplots.size() && i < page * pagesize) {
                    Plot plot = (Plot) finishedplots.get(i);
                    String starttext = "  " + (Object) this.BLUE + plot.id + (Object) this.RESET + " -> " + plot.owner;
                    int textLength = MinecraftFontWidthCalculator.getStringWidth(starttext);
                    String line = String.valueOf(starttext) + this.whitespace(550 - textLength) + "@" + plot.finisheddate;
                    p.sendMessage(line);
                    ++i;
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean done(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.done") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.done")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            String id = PlotManager.getPlotId(p.getLocation());
            if (id.equals("")) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
            } else if (!PlotManager.isPlotAvailable(id, p)) {
                Plot plot = PlotManager.getPlotById(p, id);
                String name = p.getName();
                if (plot.owner.equalsIgnoreCase(name) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.done")) {
                    if (plot.finished) {
                        plot.setUnfinished();
                        this.Send((CommandSender) p, this.C("MsgUnmarkFinished"));
                        if (this.isAdv) {
                            PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("WordMarked") + " " + id + " " + this.C("WordFinished"));
                        }
                    } else {
                        plot.setFinished();
                        this.Send((CommandSender) p, this.C("MsgMarkFinished"));
                        if (this.isAdv) {
                            PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("WordMarked") + " " + id + " " + this.C("WordUnfinished"));
                        }
                    }
                }
            } else {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean addtime(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.addtime")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            String id = PlotManager.getPlotId(p.getLocation());
            if (id.equals("")) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
            } else if (!PlotManager.isPlotAvailable(id, p)) {
                Plot plot = PlotManager.getPlotById(p, id);
                if (plot != null) {
                    String name = p.getName();
                    plot.resetExpire(PlotManager.getMap((Player) p).DaysToExpiration);
                    this.Send((CommandSender) p, this.C("MsgPlotExpirationReset"));
                    if (this.isAdv) {
                        PlotMe.logger.info(String.valueOf(this.LOG) + name + " reset expiration on plot " + id);
                    }
                }
            } else {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean expired(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.expired")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            int pagesize = 8;
            int page = 1;
            int maxpage = 0;
            int nbexpiredplots = 0;
            World w = p.getWorld();
            ArrayList<Plot> expiredplots = new ArrayList<Plot>();
            HashMap<String, Plot> plots = PlotManager.getPlots(w);
            String date = PlotMe.getDate();
            if (args.length == 2) {
                try {
                    page = Integer.parseInt(args[1]);
                } catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            for (String id : plots.keySet()) {
                Plot plot = plots.get(id);
                if (plot.protect || plot.expireddate == null || PlotMe.getDate(plot.expireddate).compareTo(date.toString()) >= 0)
                    continue;
                ++nbexpiredplots;
                expiredplots.add(plot);
            }
            Collections.sort(expiredplots);
            maxpage = (int) Math.ceil((double) nbexpiredplots / (double) pagesize);
            if (expiredplots.size() == 0) {
                this.Send((CommandSender) p, this.C("MsgNoPlotExpired"));
            } else {
                this.Send((CommandSender) p, String.valueOf(this.C("MsgExpiredPlotsPage")) + " " + page + "/" + maxpage);
                int i = (page - 1) * pagesize;
                while (i < expiredplots.size() && i < page * pagesize) {
                    Plot plot = (Plot) expiredplots.get(i);
                    String starttext = "  " + (Object) this.BLUE + plot.id + (Object) this.RESET + " -> " + plot.owner;
                    int textLength = MinecraftFontWidthCalculator.getStringWidth(starttext);
                    String line = String.valueOf(starttext) + this.whitespace(550 - textLength) + "@" + plot.expireddate.toString();
                    p.sendMessage(line);
                    ++i;
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean plotlist(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.list")) {
            String name;
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
                return true;
            }
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.list") && args.length == 2) {
                name = args[1];
                this.Send((CommandSender) p, String.valueOf(this.C("MsgListOfPlotsWhere")) + " " + (Object) this.BLUE + name + (Object) this.RESET + " " + this.C("MsgCanBuild"));
            } else {
                name = p.getName();
                this.Send((CommandSender) p, this.C("MsgListOfPlotsWhereYou"));
            }
            for (Plot plot : PlotManager.getPlots(p).values()) {
                int i;
                StringBuilder helpers;
                StringBuilder addition = new StringBuilder();
                if (plot.expireddate != null) {
                    Date tempdate = plot.expireddate;
                    if (tempdate.compareTo(Calendar.getInstance().getTime()) < 0) {
                        addition.append((Object) this.RED + " @" + plot.expireddate.toString() + (Object) this.RESET);
                    } else {
                        addition.append(" @" + plot.expireddate.toString());
                    }
                }
                if (plot.auctionned) {
                    addition.append(" " + this.C("WordAuction") + ": " + (Object) this.GREEN + this.round(plot.currentbid) + (Object) this.RESET + (!plot.currentbidder.equals("") ? new StringBuilder(" ").append(plot.currentbidder).toString() : ""));
                }
                if (plot.forsale) {
                    addition.append(" " + this.C("WordSell") + ": " + (Object) this.GREEN + this.round(plot.customprice) + (Object) this.RESET);
                }
                if (plot.owner.equalsIgnoreCase(name)) {
                    if (plot.allowedcount() == 0) {
                        if (name.equalsIgnoreCase(p.getName())) {
                            p.sendMessage("  " + plot.id + " -> " + (Object) this.BLUE + (Object) this.ITALIC + this.C("WordYours") + (Object) this.RESET + addition);
                            continue;
                        }
                        p.sendMessage("  " + plot.id + " -> " + (Object) this.BLUE + (Object) this.ITALIC + plot.owner + (Object) this.RESET + addition);
                        continue;
                    }
                    helpers = new StringBuilder();
                    i = 0;
                    while (i < plot.allowedcount()) {
                        helpers.append((Object) this.BLUE).append(plot.allowed().toArray()[i]).append((Object) this.RESET).append(", ");
                        ++i;
                    }
                    if (helpers.length() > 2) {
                        helpers.delete(helpers.length() - 2, helpers.length());
                    }
                    if (name.equalsIgnoreCase(p.getName())) {
                        p.sendMessage("  " + plot.id + " -> " + (Object) this.BLUE + (Object) this.ITALIC + this.C("WordYours") + (Object) this.RESET + addition + ", " + this.C("WordHelpers") + ": " + helpers);
                        continue;
                    }
                    p.sendMessage("  " + plot.id + " -> " + (Object) this.BLUE + (Object) this.ITALIC + plot.owner + (Object) this.RESET + addition + ", " + this.C("WordHelpers") + ": " + helpers);
                    continue;
                }
                if (!plot.isAllowed(name)) continue;
                helpers = new StringBuilder();
                i = 0;
                while (i < plot.allowedcount()) {
                    if (p.getName().equalsIgnoreCase((String) plot.allowed().toArray()[i])) {
                        if (name.equalsIgnoreCase(p.getName())) {
                            helpers.append((Object) this.BLUE).append((Object) this.ITALIC).append("You").append((Object) this.RESET).append(", ");
                        } else {
                            helpers.append((Object) this.BLUE).append((Object) this.ITALIC).append(name).append((Object) this.RESET).append(", ");
                        }
                    } else {
                        helpers.append((Object) this.BLUE).append(plot.allowed().toArray()[i]).append((Object) this.RESET).append(", ");
                    }
                    ++i;
                }
                if (helpers.length() > 2) {
                    helpers.delete(helpers.length() - 2, helpers.length());
                }
                if (plot.owner.equalsIgnoreCase(p.getName())) {
                    p.sendMessage("  " + plot.id + " -> " + (Object) this.BLUE + this.C("WordYours") + (Object) this.RESET + addition + ", " + this.C("WordHelpers") + ": " + helpers);
                    continue;
                }
                p.sendMessage("  " + plot.id + " -> " + (Object) this.BLUE + plot.owner + this.C("WordPossessive") + (Object) this.RESET + addition + ", " + this.C("WordHelpers") + ": " + helpers);
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean weanywhere(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.weanywhere")) {
            String name = p.getName();
            if (PlotMe.isIgnoringWELimit(p) && !PlotMe.defaultWEAnywhere.booleanValue() || !PlotMe.isIgnoringWELimit(p) && PlotMe.defaultWEAnywhere.booleanValue()) {
                PlotMe.removeIgnoreWELimit(p);
            } else {
                PlotMe.addIgnoreWELimit(p);
            }
            if (PlotMe.isIgnoringWELimit(p)) {
                this.Send((CommandSender) p, this.C("MsgWorldEditAnywhere"));
                if (this.isAdv) {
                    PlotMe.logger.info(String.valueOf(this.LOG) + name + " enabled WorldEdit anywhere");
                }
            } else {
                this.Send((CommandSender) p, this.C("MsgWorldEditInYourPlots"));
                if (this.isAdv) {
                    PlotMe.logger.info(String.valueOf(this.LOG) + name + " disabled WorldEdit anywhere");
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean showhelp(Player p, int page) {
        int max = 4;
        int maxpage = 0;
        boolean ecoon = PlotManager.isEconomyEnabled(p);
        ArrayList<String> allowed_commands = new ArrayList<String>();
        allowed_commands.add("limit");
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.claim")) {
            allowed_commands.add("claim");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.claim.other")) {
            allowed_commands.add("claim.other");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.auto")) {
            allowed_commands.add("auto");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.home")) {
            allowed_commands.add("home");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.home.other")) {
            allowed_commands.add("home.other");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.info")) {
            allowed_commands.add("info");
            allowed_commands.add("biomeinfo");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.comment")) {
            allowed_commands.add("comment");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.comments")) {
            allowed_commands.add("comments");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.list")) {
            allowed_commands.add("list");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.biome")) {
            allowed_commands.add("biome");
            allowed_commands.add("biomelist");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.done") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.done")) {
            allowed_commands.add("done");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.done")) {
            allowed_commands.add("donelist");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.tp")) {
            allowed_commands.add("tp");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.id")) {
            allowed_commands.add("id");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.clear") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.clear")) {
            allowed_commands.add("clear");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.dispose") || PlotMe.cPerms((CommandSender) p, "PlotMe.use.dispose")) {
            allowed_commands.add("dispose");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.reset")) {
            allowed_commands.add("reset");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.add") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.add")) {
            allowed_commands.add("add");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.remove") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.remove")) {
            allowed_commands.add("remove");
        }
        if (PlotMe.allowToDeny.booleanValue()) {
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.deny") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.deny")) {
                allowed_commands.add("deny");
            }
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.undeny") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.undeny")) {
                allowed_commands.add("undeny");
            }
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.setowner")) {
            allowed_commands.add("setowner");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.move")) {
            allowed_commands.add("move");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.weanywhere")) {
            allowed_commands.add("weanywhere");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.reload")) {
            allowed_commands.add("reload");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.list")) {
            allowed_commands.add("listother");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.expired")) {
            allowed_commands.add("expired");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.addtime")) {
            allowed_commands.add("addtime");
        }
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.resetexpired")) {
            allowed_commands.add("resetexpired");
        }
        PlotMapInfo pmi = PlotManager.getMap(p);
        if (PlotManager.isPlotWorld(p) && ecoon) {
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.buy")) {
                allowed_commands.add("buy");
            }
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.sell")) {
                allowed_commands.add("sell");
                if (pmi.CanSellToBank) {
                    allowed_commands.add("sellbank");
                }
            }
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.auction")) {
                allowed_commands.add("auction");
            }
            if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.bid")) {
                allowed_commands.add("bid");
            }
        }
        if (page > (maxpage = (int) Math.ceil((double) allowed_commands.size() / (double) max))) {
            page = 1;
        }
        p.sendMessage((Object) this.RED + " ---==" + (Object) this.BLUE + this.C("HelpTitle") + " " + page + "/" + maxpage + (Object) this.RED + "==--- ");
        int ctr = (page - 1) * max;
        while (ctr < page * max && ctr < allowed_commands.size()) {
            String allowedcmd = (String) allowed_commands.get(ctr);
            if (allowedcmd.equalsIgnoreCase("limit")) {
                if (PlotManager.isPlotWorld(p) || PlotMe.allowWorldTeleport.booleanValue()) {
                    World w = null;
                    if (PlotManager.isPlotWorld(p)) {
                        w = p.getWorld();
                    } else if (PlotMe.allowWorldTeleport.booleanValue()) {
                        w = PlotManager.getFirstWorld();
                    }
                    int maxplots = PlotMe.getPlotLimit(p);
                    int ownedplots = PlotManager.getNbOwnedPlot(p, w);
                    if (maxplots == -1) {
                        p.sendMessage((Object) this.GREEN + this.C("HelpYourPlotLimitWorld") + " : " + (Object) this.AQUA + ownedplots + (Object) this.GREEN + " " + this.C("HelpUsedOf") + " " + (Object) this.AQUA + this.C("WordInfinite"));
                    } else {
                        p.sendMessage((Object) this.GREEN + this.C("HelpYourPlotLimitWorld") + " : " + (Object) this.AQUA + ownedplots + (Object) this.GREEN + " " + this.C("HelpUsedOf") + " " + (Object) this.AQUA + maxplots);
                    }
                } else {
                    p.sendMessage((Object) this.GREEN + this.C("HelpYourPlotLimitWorld") + " : " + (Object) this.AQUA + this.C("MsgNotPlotWorld"));
                }
            } else if (allowedcmd.equalsIgnoreCase("claim")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandClaim"));
                if (ecoon && pmi != null && pmi.ClaimPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpClaim") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.ClaimPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpClaim"));
                }
            } else if (allowedcmd.equalsIgnoreCase("claim.other")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandClaim") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.ClaimPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpClaimOther") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.ClaimPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpClaimOther"));
                }
            } else if (allowedcmd.equalsIgnoreCase("auto")) {
                if (PlotMe.allowWorldTeleport.booleanValue()) {
                    p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandAuto") + " [" + this.C("WordWorld") + "]");
                } else {
                    p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandAuto"));
                }
                if (ecoon && pmi != null && pmi.ClaimPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpAuto") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.ClaimPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpAuto"));
                }
            } else if (allowedcmd.equalsIgnoreCase("home")) {
                if (PlotMe.allowWorldTeleport.booleanValue()) {
                    p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandHome") + "[:#] [" + this.C("WordWorld") + "]");
                } else {
                    p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandHome") + "[:#]");
                }
                if (ecoon && pmi != null && pmi.PlotHomePrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpHome") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.PlotHomePrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpHome"));
                }
            } else if (allowedcmd.equalsIgnoreCase("home.other")) {
                if (PlotMe.allowWorldTeleport.booleanValue()) {
                    p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandHome") + "[:#] <" + this.C("WordPlayer") + "> [" + this.C("WordWorld") + "]");
                } else {
                    p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandHome") + "[:#] <" + this.C("WordPlayer") + ">");
                }
                if (ecoon && pmi != null && pmi.PlotHomePrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpHomeOther") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.PlotHomePrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpHomeOther"));
                }
            } else if (allowedcmd.equalsIgnoreCase("info")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandInfo"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpInfo"));
            } else if (allowedcmd.equalsIgnoreCase("comment")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandComment") + " <" + this.C("WordComment") + ">");
                if (ecoon && pmi != null && pmi.AddCommentPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpComment") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.AddCommentPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpComment"));
                }
            } else if (allowedcmd.equalsIgnoreCase("comments")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandComments"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpComments"));
            } else if (allowedcmd.equalsIgnoreCase("list")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandList"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpList"));
            } else if (allowedcmd.equalsIgnoreCase("listother")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandList") + " <" + this.C("WordPlayer") + ">");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpListOther"));
            } else if (allowedcmd.equalsIgnoreCase("biomeinfo")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandBiome"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpBiomeInfo"));
            } else if (allowedcmd.equalsIgnoreCase("biome")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandBiome") + " <" + this.C("WordBiome") + ">");
                if (ecoon && pmi != null && pmi.BiomeChangePrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpBiome") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.BiomeChangePrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpBiome"));
                }
            } else if (allowedcmd.equalsIgnoreCase("biomelist")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandBiomelist"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpBiomeList"));
            } else if (allowedcmd.equalsIgnoreCase("done")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandDone"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpDone"));
            } else if (allowedcmd.equalsIgnoreCase("tp")) {
                if (PlotMe.allowWorldTeleport.booleanValue()) {
                    p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> [" + this.C("WordWorld") + "]");
                } else {
                    p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandTp") + " <" + this.C("WordId") + ">");
                }
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpTp"));
            } else if (allowedcmd.equalsIgnoreCase("id")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandId"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpId"));
            } else if (allowedcmd.equalsIgnoreCase("clear")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandClear"));
                if (ecoon && pmi != null && pmi.ClearPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpId") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.ClearPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpClear"));
                }
            } else if (allowedcmd.equalsIgnoreCase("reset")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandReset"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpReset"));
            } else if (allowedcmd.equalsIgnoreCase("add")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandAdd") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.AddPlayerPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpAdd") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.AddPlayerPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpAdd"));
                }
            } else if (allowedcmd.equalsIgnoreCase("deny")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandDeny") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.DenyPlayerPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpDeny") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.DenyPlayerPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpDeny"));
                }
            } else if (allowedcmd.equalsIgnoreCase("remove")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandRemove") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.RemovePlayerPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpRemove") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.RemovePlayerPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpRemove"));
                }
            } else if (allowedcmd.equalsIgnoreCase("undeny")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandUndeny") + " <" + this.C("WordPlayer") + ">");
                if (ecoon && pmi != null && pmi.UndenyPlayerPrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpUndeny") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.UndenyPlayerPrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpUndeny"));
                }
            } else if (allowedcmd.equalsIgnoreCase("setowner")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandSetowner") + " <" + this.C("WordPlayer") + ">");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpSetowner"));
            } else if (allowedcmd.equalsIgnoreCase("move")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandMove") + " <" + this.C("WordIdFrom") + "> <" + this.C("WordIdTo") + ">");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpMove"));
            } else if (allowedcmd.equalsIgnoreCase("weanywhere")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandWEAnywhere"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpWEAnywhere"));
            } else if (allowedcmd.equalsIgnoreCase("expired")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandExpired") + " [page]");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpExpired"));
            } else if (allowedcmd.equalsIgnoreCase("donelist")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandDoneList") + " [page]");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpDoneList"));
            } else if (allowedcmd.equalsIgnoreCase("addtime")) {
                int days;
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandAddtime"));
                int n = days = pmi == null ? 0 : pmi.DaysToExpiration;
                if (days == 0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpAddTime1") + " " + (Object) this.RESET + this.C("WordNever"));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpAddTime1") + " " + (Object) this.RESET + days + (Object) this.AQUA + " " + this.C("HelpAddTime2"));
                }
            } else if (allowedcmd.equalsIgnoreCase("reload")) {
                p.sendMessage((Object) this.GREEN + " /plotme reload");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpReload"));
            } else if (allowedcmd.equalsIgnoreCase("dispose")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandDispose"));
                if (ecoon && pmi != null && pmi.DisposePrice != 0.0) {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpDispose") + " " + this.C("WordPrice") + " : " + (Object) this.RESET + this.round(pmi.DisposePrice));
                } else {
                    p.sendMessage((Object) this.AQUA + " " + this.C("HelpDispose"));
                }
            } else if (allowedcmd.equalsIgnoreCase("buy")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandBuy"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpBuy"));
            } else if (allowedcmd.equalsIgnoreCase("sell")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandSell") + " [" + this.C("WordAmount") + "]");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpSell") + " " + this.C("WordDefault") + " : " + (Object) this.RESET + this.round(pmi.SellToPlayerPrice));
            } else if (allowedcmd.equalsIgnoreCase("sellbank")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandSellBank"));
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpSellBank") + " " + (Object) this.RESET + this.round(pmi.SellToBankPrice));
            } else if (allowedcmd.equalsIgnoreCase("auction")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandAuction") + " [" + this.C("WordAmount") + "]");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpAuction") + " " + this.C("WordDefault") + " : " + (Object) this.RESET + "1");
            } else if (allowedcmd.equalsIgnoreCase("resetexpired")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandResetExpired") + " <" + this.C("WordWorld") + ">");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpResetExpired"));
            } else if (allowedcmd.equalsIgnoreCase("bid")) {
                p.sendMessage((Object) this.GREEN + " /plotme " + this.C("CommandBid") + " <" + this.C("WordAmount") + ">");
                p.sendMessage((Object) this.AQUA + " " + this.C("HelpBid"));
            }
            ++ctr;
        }
        return true;
    }

    private boolean tp(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.tp")) {
            if (!PlotManager.isPlotWorld(p) && !PlotMe.allowWorldTeleport.booleanValue()) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else if (args.length == 2 || args.length == 3 && PlotMe.allowWorldTeleport.booleanValue()) {
                World w;
                String id = args[1];
                if (!PlotManager.isValidId(id)) {
                    if (PlotMe.allowWorldTeleport.booleanValue()) {
                        this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> [" + this.C("WordWorld") + "] " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
                    } else {
                        this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
                    }
                    return true;
                }
                if (args.length == 3) {
                    String world = args[2];
                    w = Bukkit.getWorld((String) world);
                } else {
                    w = !PlotManager.isPlotWorld(p) ? PlotManager.getFirstWorld() : p.getWorld();
                }
                if (w == null || !PlotManager.isPlotWorld(w)) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotworldFound"));
                } else {
                    Location bottom = PlotManager.getPlotBottomLoc(w, id);
                    Location top = PlotManager.getPlotTopLoc(w, id);
                    p.teleport(new Location(w, bottom.getX() + (double) ((top.getBlockX() - bottom.getBlockX()) / 2), (double) (PlotManager.getMap((World) w).RoadHeight + 2), bottom.getZ() - 2.0));
                }
            } else if (PlotMe.allowWorldTeleport.booleanValue()) {
                this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> [" + this.C("WordWorld") + "] " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
            } else {
                this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandTp") + " <" + this.C("WordId") + "> " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandTp") + " 5;-1 ");
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean auto(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.auto")) {
            if (!PlotManager.isPlotWorld(p) && !PlotMe.allowWorldTeleport.booleanValue()) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                World w;
                if (!PlotManager.isPlotWorld(p) && PlotMe.allowWorldTeleport.booleanValue()) {
                    w = args.length == 2 ? Bukkit.getWorld((String) args[1]) : PlotManager.getFirstWorld();
                    if (w == null || !PlotManager.isPlotWorld(w)) {
                        this.Send((CommandSender) p, (Object) this.RED + args[1] + " " + this.C("MsgWorldNotPlot"));
                        return true;
                    }
                } else {
                    w = p.getWorld();
                }
                if (w == null) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotworldFound"));
                } else if (PlotManager.getNbOwnedPlot(p, w) >= PlotMe.getPlotLimit(p) && !PlotMe.cPerms((CommandSender) p, "PlotMe.admin")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgAlreadyReachedMaxPlots") + " (" + PlotManager.getNbOwnedPlot(p, w) + "/" + PlotMe.getPlotLimit(p) + "). " + this.C("WordUse") + " " + (Object) this.RED + "/plotme " + this.C("CommandHome") + (Object) this.RESET + " " + this.C("MsgToGetToIt"));
                } else {
                    PlotMapInfo pmi = PlotManager.getMap(w);
                    int limit = pmi.PlotAutoLimit;
                    int i = 0;
                    while (i < limit) {
                        int x = -i;
                        while (x <= i) {
                            int z = -i;
                            while (z <= i) {
                                String id = x + ";" + z;
                                if (PlotManager.isPlotAvailable(id, w)) {
                                    String name = p.getName();
                                    double price = 0.0;
                                    if (PlotManager.isEconomyEnabled(w)) {
                                        int plotAmount = 0;
                                        for (Plot plottmp : PlotManager.getPlots(p).values())
                                            if (plottmp.getOwner().equalsIgnoreCase(p.getName())) plotAmount++;

                                        price = pmi.ClaimPrice * (plotAmount + 1) * 2;
                                        if (plotAmount >= 10) price = Integer.MAX_VALUE;
                                        double balance = PlotMe.economy.getBalance(name);
                                        if (balance >= price) {
                                            EconomyResponse er = PlotMe.economy.withdrawPlayer(name, price);
                                            if (!er.transactionSuccess()) {
                                                this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                                this.warn(er.errorMessage);
                                                return true;
                                            }
                                        } else {
                                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughAuto") + " " + this.C("WordMissing") + " " + (Object) this.RESET + this.f(price - balance, false));
                                            return true;
                                        }
                                    }
                                    Plot plot = PlotManager.createPlot(w, id, name);
                                    p.teleport(new Location(w, (double) (PlotManager.bottomX(plot.id, w) + (PlotManager.topX(plot.id, w) - PlotManager.bottomX(plot.id, w)) / 2), (double) (pmi.RoadHeight + 2), (double) (PlotManager.bottomZ(plot.id, w) - 2)));
                                    this.Send((CommandSender) p, String.valueOf(this.C("MsgThisPlotYours")) + " " + this.C("WordUse") + " " + (Object) this.RED + "/plotme " + this.C("CommandHome") + (Object) this.RESET + " " + this.C("MsgToGetToIt") + " " + this.f(-price));
                                    if (this.isAdv) {
                                        PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgClaimedPlot") + " " + id + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                                    }
                                    return true;
                                }
                                ++z;
                            }
                            ++x;
                        }
                        ++i;
                    }
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound1") + " " + (limit ^ 2) + " " + this.C("MsgNoPlotFound2"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean claim(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.claim") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.claim.other")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgCannotClaimRoad"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlotOwned"));
                } else {
                    String playername = p.getName();
                    if (args.length == 2 && PlotMe.cPerms((CommandSender) p, "PlotMe.admin.claim.other")) {
                        playername = args[1];
                    }
                    int plotlimit = PlotMe.getPlotLimit(p);
                    if (playername == p.getName() && plotlimit != -1 && PlotManager.getNbOwnedPlot(p) >= plotlimit) {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgAlreadyReachedMaxPlots") + " (" + PlotManager.getNbOwnedPlot(p) + "/" + PlotMe.getPlotLimit(p) + "). " + this.C("WordUse") + " " + (Object) this.RED + "/plotme " + this.C("CommandHome") + (Object) this.RESET + " " + this.C("MsgToGetToIt"));
                    } else {
                        Plot plot;
                        World w = p.getWorld();
                        PlotMapInfo pmi = PlotManager.getMap(w);
                        double price = 0.0;
                        if (PlotManager.isEconomyEnabled(w)) {
                            int plotAmount = 0;
                            for (Plot plottmp : PlotManager.getPlots(p).values())
                                if (plottmp.getOwner().equalsIgnoreCase(p.getName())) plotAmount++;

                            price = pmi.ClaimPrice * (plotAmount + 1) * 2;
                            if (plotAmount >= 10) price = Integer.MAX_VALUE;
                            double balance = PlotMe.economy.getBalance(playername);
                            if (balance >= price) {
                                EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                                if (!er.transactionSuccess()) {
                                    this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                    this.warn(er.errorMessage);
                                    return true;
                                }
                            } else {
                                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughBuy") + " " + this.C("WordMissing") + " " + (Object) this.RESET + (price - balance) + (Object) this.RED + " " + PlotMe.economy.currencyNamePlural());
                                return true;
                            }
                        }
                        if ((plot = PlotManager.createPlot(w, id, playername)) == null) {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("ErrCreatingPlotAt") + " " + id);
                        } else {
                            if (playername.equalsIgnoreCase(p.getName())) {
                                this.Send((CommandSender) p, String.valueOf(this.C("MsgThisPlotYours")) + " " + this.C("WordUse") + " " + (Object) this.RED + "/plotme " + this.C("CommandHome") + (Object) this.RESET + " " + this.C("MsgToGetToIt") + " " + this.f(-price));
                            } else {
                                this.Send((CommandSender) p, String.valueOf(this.C("MsgThisPlotIsNow")) + " " + playername + this.C("WordPossessive") + ". " + this.C("WordUse") + " " + (Object) this.RED + "/plotme " + this.C("CommandHome") + (Object) this.RESET + " " + this.C("MsgToGetToIt") + " " + this.f(-price));
                            }
                            if (this.isAdv) {
                                PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgClaimedPlot") + " " + id + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                            }
                        }
                    }
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean home(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.home") || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.home.other")) {
            if (!PlotManager.isPlotWorld(p) && !PlotMe.allowWorldTeleport.booleanValue()) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                boolean found = false;
                String playername = p.getName();
                int nb = 1;
                World w = !PlotManager.isPlotWorld(p) && PlotMe.allowWorldTeleport != false ? PlotManager.getFirstWorld() : p.getWorld();
                if (args[0].contains(":")) {
                    block30:
                    {
                        try {
                            if (args[0].split(":").length != 1 && !args[0].split(":")[1].equals("")) break block30;
                            this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandHome") + ":# " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandHome") + ":1");
                            return true;
                        } catch (Exception ex) {
                            this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandHome") + ":# " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandHome") + ":1");
                            return true;
                        }
                    }
                    nb = Integer.parseInt(args[0].split(":")[1]);
                }
                if (args.length == 2) {
                    if (Bukkit.getWorld((String) args[1]) == null) {
                        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.home.other")) {
                            playername = args[1];
                        }
                    } else {
                        w = Bukkit.getWorld((String) args[1]);
                    }
                }
                if (args.length == 3) {
                    if (Bukkit.getWorld((String) args[2]) == null) {
                        this.Send((CommandSender) p, (Object) this.RED + args[2] + this.C("MsgWorldNotPlot"));
                        return true;
                    }
                    w = Bukkit.getWorld((String) args[2]);
                }
                if (!PlotManager.isPlotWorld(w)) {
                    this.Send((CommandSender) p, (Object) this.RED + w.getName() + this.C("MsgWorldNotPlot"));
                } else {
                    int i = nb - 1;
                    for (Plot plot : PlotManager.getPlots(w).values()) {
                        if (!plot.owner.equalsIgnoreCase(playername)) continue;
                        if (i == 0) {
                            PlotMapInfo pmi = PlotManager.getMap(w);
                            double price = 0.0;
                            if (PlotManager.isEconomyEnabled(w)) {
                                price = pmi.PlotHomePrice;
                                double balance = PlotMe.economy.getBalance(playername);
                                if (balance >= price) {
                                    EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                                    if (!er.transactionSuccess()) {
                                        this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                        return true;
                                    }
                                } else {
                                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughTp") + " " + this.C("WordMissing") + " " + (Object) this.RESET + this.f(price - balance, false));
                                    return true;
                                }
                            }
                            p.teleport(PlotManager.getPlotHome(w, plot));
                            if (price != 0.0) {
                                this.Send((CommandSender) p, this.f(-price));
                            }
                            return true;
                        }
                        --i;
                    }
                    if (!found) {
                        if (nb > 0) {
                            if (!playername.equalsIgnoreCase(p.getName())) {
                                this.Send((CommandSender) p, (Object) this.RED + playername + " " + this.C("MsgDoesNotHavePlot") + " #" + nb);
                            } else {
                                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPlotNotFound") + " #" + nb);
                            }
                        } else if (!playername.equalsIgnoreCase(p.getName())) {
                            this.Send((CommandSender) p, (Object) this.RED + playername + " " + this.C("MsgDoesNotHavePlot"));
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgYouHaveNoPlot"));
                        }
                    }
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean info(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.info")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    Plot plot = PlotManager.getPlotById(p, id);
                    p.sendMessage((Object) this.GREEN + this.C("InfoId") + ": " + (Object) this.AQUA + id + (Object) this.GREEN + " " + this.C("InfoOwner") + ": " + (Object) this.AQUA + plot.owner + (Object) this.GREEN + " " + this.C("InfoBiome") + ": " + (Object) this.AQUA + this.FormatBiome(plot.biome.name()));
                    p.sendMessage((Object) this.GREEN + this.C("InfoExpire") + ": " + (Object) this.AQUA + (plot.expireddate == null ? this.C("WordNever") : plot.expireddate.toString()) + (Object) this.GREEN + " " + this.C("InfoFinished") + ": " + (Object) this.AQUA + (plot.finished ? this.C("WordYes") : this.C("WordNo")) + (Object) this.GREEN + " " + this.C("InfoProtected") + ": " + (Object) this.AQUA + (plot.protect ? this.C("WordYes") : this.C("WordNo")));
                    if (plot.allowedcount() > 0) {
                        p.sendMessage((Object) this.GREEN + this.C("InfoHelpers") + ": " + (Object) this.AQUA + plot.getAllowed());
                    }
                    if (PlotMe.allowToDeny.booleanValue() && plot.deniedcount() > 0) {
                        p.sendMessage((Object) this.GREEN + this.C("InfoDenied") + ": " + (Object) this.AQUA + plot.getDenied());
                    }
                    if (PlotManager.isEconomyEnabled(p)) {
                        if (plot.currentbidder.equalsIgnoreCase("")) {
                            p.sendMessage((Object) this.GREEN + this.C("InfoAuctionned") + ": " + (Object) this.AQUA + (plot.auctionned ? new StringBuilder(String.valueOf(this.C("WordYes"))).append((Object) this.GREEN).append(" ").append(this.C("InfoMinimumBid")).append(": ").append((Object) this.AQUA).append(this.round(plot.currentbid)).toString() : this.C("WordNo")) + (Object) this.GREEN + " " + this.C("InfoForSale") + ": " + (Object) this.AQUA + (plot.forsale ? new StringBuilder().append((Object) this.AQUA).append(this.round(plot.customprice)).toString() : this.C("WordNo")));
                        } else {
                            p.sendMessage((Object) this.GREEN + this.C("InfoAuctionned") + ": " + (Object) this.AQUA + (plot.auctionned ? new StringBuilder(String.valueOf(this.C("WordYes"))).append((Object) this.GREEN).append(" ").append(this.C("InfoBidder")).append(": ").append((Object) this.AQUA).append(plot.currentbidder).append((Object) this.GREEN).append(" ").append(this.C("InfoBid")).append(": ").append((Object) this.AQUA).append(this.round(plot.currentbid)).toString() : this.C("WordNo")) + (Object) this.GREEN + " " + this.C("InfoForSale") + ": " + (Object) this.AQUA + (plot.forsale ? new StringBuilder().append((Object) this.AQUA).append(this.round(plot.customprice)).toString() : this.C("WordNo")));
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean comment(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.comment")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else if (args.length < 2) {
                this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandComment") + " <" + this.C("WordText") + ">");
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    World w = p.getWorld();
                    PlotMapInfo pmi = PlotManager.getMap(w);
                    String playername = p.getName();
                    double price = 0.0;
                    if (PlotManager.isEconomyEnabled(w)) {
                        price = pmi.AddCommentPrice;
                        double balance = PlotMe.economy.getBalance(playername);
                        if (balance >= price) {
                            EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                            if (!er.transactionSuccess()) {
                                this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                this.warn(er.errorMessage);
                                return true;
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughComment") + " " + this.C("WordMissing") + " " + (Object) this.RESET + this.f(price - balance, false));
                            return true;
                        }
                    }
                    Plot plot = PlotManager.getPlotById(p, id);
                    String text = StringUtils.join((Object[]) args, (String) " ");
                    text = text.substring(text.indexOf(" "));
                    String[] comment = new String[]{playername, text};
                    plot.comments.add(comment);
                    SqlManager.addPlotComment(comment, plot.comments.size(), PlotManager.getIdX(id), PlotManager.getIdZ(id), plot.world);
                    this.Send((CommandSender) p, String.valueOf(this.C("MsgCommentAdded")) + " " + this.f(-price));
                    if (this.isAdv) {
                        PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgCommentedPlot") + " " + id + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean comments(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.comments")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else if (args.length < 2) {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    Plot plot = PlotManager.getPlotById(p, id);
                    if (plot.owner.equalsIgnoreCase(p.getName()) || plot.isAllowed(p.getName()) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin")) {
                        if (plot.comments.size() == 0) {
                            this.Send((CommandSender) p, this.C("MsgNoComments"));
                        } else {
                            this.Send((CommandSender) p, String.valueOf(this.C("MsgYouHave")) + " " + (Object) this.BLUE + plot.comments.size() + (Object) this.RESET + " " + this.C("MsgComments"));
                            for (String[] comment : plot.comments) {
                                p.sendMessage((Object) ChatColor.BLUE + this.C("WordFrom") + " : " + (Object) this.RED + comment[0]);
                                p.sendMessage("§" + this.RESET.toString() + "§" + ChatColor.ITALIC + comment[1]);
                            }
                        }
                    } else {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedViewComments"));
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            p.sendMessage((Object) this.BLUE + this.PREFIX + (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean biome(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.use.biome")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    p.sendMessage((Object) this.BLUE + this.PREFIX + (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    World w = p.getWorld();
                    if (args.length == 2) {
                        Biome biome = null;
                        Biome[] arrbiome = Biome.values();
                        int n = arrbiome.length;
                        int n2 = 0;
                        while (n2 < n) {
                            Biome bio = arrbiome[n2];
                            if (bio.name().equalsIgnoreCase(args[1])) {
                                biome = bio;
                            }
                            ++n2;
                        }
                        if (biome == null) {
                            this.Send((CommandSender) p, (Object) this.RED + args[1] + (Object) this.RESET + " " + this.C("MsgIsInvalidBiome"));
                        } else {
                            Plot plot = PlotManager.getPlotById(p, id);
                            String playername = p.getName();
                            if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin")) {
                                PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.BiomeChangePrice;
                                    double balance = PlotMe.economy.getBalance(playername);
                                    if (balance >= price) {
                                        EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                                        if (!er.transactionSuccess()) {
                                            this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                            this.warn(er.errorMessage);
                                            return true;
                                        }
                                    } else {
                                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughBiome") + " " + this.C("WordMissing") + " " + (Object) this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                }
                                PlotManager.setBiome(w, id, plot, biome);
                                this.Send((CommandSender) p, String.valueOf(this.C("MsgBiomeSet")) + " " + (Object) ChatColor.BLUE + this.FormatBiome(biome.name()) + " " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgChangedBiome") + " " + id + " " + this.C("WordTo") + " " + this.FormatBiome(biome.name()) + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                                }
                            } else {
                                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedBiome"));
                            }
                        }
                    } else {
                        Plot plot = PlotMe.plotmaps.get((Object) w.getName().toLowerCase()).plots.get(id);
                        this.Send((CommandSender) p, String.valueOf(this.C("MsgPlotUsingBiome")) + " " + (Object) ChatColor.BLUE + this.FormatBiome(plot.biome.name()));
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean biomelist(CommandSender s, String[] args) {
        if (!(s instanceof Player) || PlotMe.cPerms((CommandSender) ((Player) s), "PlotMe.use.biome")) {
            this.Send(s, String.valueOf(this.C("WordBiomes")) + " : ");
            StringBuilder line = new StringBuilder();
            ArrayList<String> biomes = new ArrayList<String>();
            Biome[] arrbiome = Biome.values();
            int n = arrbiome.length;
            int n2 = 0;
            while (n2 < n) {
                Biome b = arrbiome[n2];
                biomes.add(b.name());
                ++n2;
            }
            Collections.sort(biomes);
            ArrayList<String> column1 = new ArrayList<String>();
            ArrayList<String> column2 = new ArrayList<String>();
            ArrayList<String> column3 = new ArrayList<String>();
            int ctr = 0;
            while (ctr < biomes.size()) {
                if (ctr < biomes.size() / 3) {
                    column1.add((String) biomes.get(ctr));
                } else if (ctr < biomes.size() * 2 / 3) {
                    column2.add((String) biomes.get(ctr));
                } else {
                    column3.add((String) biomes.get(ctr));
                }
                ++ctr;
            }
            ctr = 0;
            while (ctr < column1.size()) {
                String b = this.FormatBiome((String) column1.get(ctr));
                int nameLength = MinecraftFontWidthCalculator.getStringWidth(b);
                line.append(b).append(this.whitespace(432 - nameLength));
                if (ctr < column2.size()) {
                    b = this.FormatBiome((String) column2.get(ctr));
                    nameLength = MinecraftFontWidthCalculator.getStringWidth(b);
                    line.append(b).append(this.whitespace(432 - nameLength));
                }
                if (ctr < column3.size()) {
                    b = this.FormatBiome((String) column3.get(ctr));
                    line.append(b);
                }
                s.sendMessage("" + (Object) this.BLUE + line);
                line = new StringBuilder();
                ++ctr;
            }
        } else {
            this.Send(s, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean reset(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.reset")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                Plot plot = PlotManager.getPlotById(p.getLocation());
                if (plot == null) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (plot.protect) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPlotProtectedCannotReset"));
                } else {
                    String id = plot.id;
                    World w = p.getWorld();
                    PlotManager.setBiome(w, id, plot, Biome.PLAINS);
                    PlotManager.clear(w, plot);
                    if (PlotManager.isEconomyEnabled(p)) {
                        Player[] arrplayer;
                        int n;
                        EconomyResponse er;
                        Player player;
                        String currentbidder;
                        int n2;
                        if (plot.auctionned && !(currentbidder = plot.currentbidder).equals("")) {
                            er = PlotMe.economy.depositPlayer(currentbidder, plot.currentbid);
                            if (!er.transactionSuccess()) {
                                this.Send((CommandSender) p, er.errorMessage);
                                this.warn(er.errorMessage);
                            } else {
                                for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
                                    if (player1.getName().equalsIgnoreCase(currentbidder)) {
                                        this.Send((CommandSender) player1, String.valueOf(this.C("WordPlot")) + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + " " + this.C("MsgWasReset") + " " + this.f(plot.currentbid));
                                        break;
                                    }
                                }

                            }
                        }
                        PlotMapInfo pmi = PlotManager.getMap(p);
                        if (pmi.RefundClaimPriceOnReset) {
                            er = PlotMe.economy.depositPlayer(plot.owner, pmi.ClaimPrice);
                            if (!er.transactionSuccess()) {
                                this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                this.warn(er.errorMessage);
                                return true;
                            }
                            for (Player player2 : Bukkit.getServer().getOnlinePlayers()) {
                                if (player2.getName().equalsIgnoreCase(plot.owner)) {
                                    this.Send((CommandSender) player2, String.valueOf(this.C("WordPlot")) + " " + id + " " + this.C("MsgOwnedBy") + " " + plot.owner + " " + this.C("MsgWasReset") + " " + this.f(pmi.ClaimPrice));
                                    break;
                                }
                            }
                        }
                    }
                    if (!PlotManager.isPlotAvailable(id, p)) {
                        PlotManager.getPlots(p).remove(id);
                    }
                    String name = p.getName();
                    PlotManager.removeOwnerSign(w, id);
                    PlotManager.removeSellSign(w, id);
                    SqlManager.deletePlot(PlotManager.getIdX(id), PlotManager.getIdZ(id), w.getName().toLowerCase());
                    this.Send((CommandSender) p, this.C("MsgPlotReset"));
                    if (this.isAdv) {
                        PlotMe.logger.info(String.valueOf(this.LOG) + name + " " + this.C("MsgResetPlot") + " " + id);
                    }
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean clear(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.clear") || PlotMe.cPerms((CommandSender) p, "PlotMe.use.clear")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    Plot plot = PlotManager.getPlotById(p, id);
                    if (plot.protect) {
                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPlotProtectedCannotClear"));
                    } else {
                        String playername = p.getName();
                        if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.clear")) {
                            World w = p.getWorld();
                            PlotMapInfo pmi = PlotManager.getMap(w);
                            double price = 0.0;
                            if (PlotManager.isEconomyEnabled(w)) {
                                price = pmi.ClearPrice;
                                double balance = PlotMe.economy.getBalance(playername);
                                if (balance >= price) {
                                    EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                                    if (!er.transactionSuccess()) {
                                        this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                        this.warn(er.errorMessage);
                                        return true;
                                    }
                                } else {
                                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughClear") + " " + this.C("WordMissing") + " " + (Object) this.RESET + (price - balance) + (Object) this.RED + " " + PlotMe.economy.currencyNamePlural());
                                    return true;
                                }
                            }
                            PlotManager.clear(w, plot);
                            this.Send((CommandSender) p, String.valueOf(this.C("MsgPlotCleared")) + " " + this.f(-price));
                            if (this.isAdv) {
                                PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgClearedPlot") + " " + id + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedClear"));
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean add(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.add") || PlotMe.cPerms((CommandSender) p, "PlotMe.use.add")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    if (args.length < 2 || args[1].equalsIgnoreCase("")) {
                        this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + " " + (Object) this.RED + "/plotme " + this.C("CommandAdd") + " <" + this.C("WordPlayer") + ">");
                    } else {
                        Plot plot = PlotManager.getPlotById(p, id);
                        String playername = p.getName();
                        String allowed = args[1];
                        if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.add")) {
                            if (plot.isAllowed(allowed)) {
                                this.Send((CommandSender) p, String.valueOf(this.C("WordPlayer")) + " " + (Object) this.RED + args[1] + (Object) this.RESET + " " + this.C("MsgAlreadyAllowed"));
                            } else {
                                World w = p.getWorld();
                                PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.AddPlayerPrice;
                                    double balance = PlotMe.economy.getBalance(playername);
                                    if (balance >= price) {
                                        EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                                        if (!er.transactionSuccess()) {
                                            this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                            this.warn(er.errorMessage);
                                            return true;
                                        }
                                    } else {
                                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughAdd") + " " + this.C("WordMissing") + " " + (Object) this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                }
                                plot.addAllowed(args[1]);
                                this.Send((CommandSender) p, String.valueOf(this.C("WordPlayer")) + " " + (Object) this.RED + allowed + (Object) this.RESET + " " + this.C("MsgNowAllowed") + " " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgAddedPlayer") + " " + allowed + " " + this.C("MsgToPlot") + " " + id + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                                }
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedAdd"));
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean deny(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.deny") || PlotMe.cPerms((CommandSender) p, "PlotMe.use.deny")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    if (args.length < 2 || args[1].equalsIgnoreCase("")) {
                        this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + " " + (Object) this.RED + "/plotme " + this.C("CommandDeny") + " <" + this.C("WordPlayer") + ">");
                    } else {
                        Plot plot = PlotManager.getPlotById(p, id);
                        String playername = p.getName();
                        String denied = args[1];
                        if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.deny")) {
                            if (plot.isDenied(denied)) {
                                this.Send((CommandSender) p, String.valueOf(this.C("WordPlayer")) + " " + (Object) this.RED + args[1] + (Object) this.RESET + " " + this.C("MsgAlreadyDenied"));
                            } else {
                                World w = p.getWorld();
                                PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.DenyPlayerPrice;
                                    double balance = PlotMe.economy.getBalance(playername);
                                    if (balance >= price) {
                                        EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                                        if (!er.transactionSuccess()) {
                                            this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                            this.warn(er.errorMessage);
                                            return true;
                                        }
                                    } else {
                                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughDeny") + " " + this.C("WordMissing") + " " + (Object) this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                }
                                plot.addDenied(args[1]);
                                if (denied.equals("*")) {
                                    List<Player> deniedplayers = PlotManager.getPlayersInPlot(w, id);
                                    for (Player deniedplayer : deniedplayers) {
                                        if (plot.isAllowed(deniedplayer.getName())) continue;
                                        deniedplayer.teleport(PlotManager.getPlotHome(w, plot));
                                    }
                                } else {
                                    String deniedid;
                                    Player deniedplayer = Bukkit.getServer().getPlayer(denied);
                                    if (deniedplayer != null && deniedplayer.getWorld().equals((Object) w) && (deniedid = PlotManager.getPlotId(deniedplayer)).equalsIgnoreCase(id)) {
                                        deniedplayer.teleport(PlotManager.getPlotHome(w, plot));
                                    }
                                }
                                this.Send((CommandSender) p, String.valueOf(this.C("WordPlayer")) + " " + (Object) this.RED + denied + (Object) this.RESET + " " + this.C("MsgNowDenied") + " " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgDeniedPlayer") + " " + denied + " " + this.C("MsgToPlot") + " " + id + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                                }
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedDeny"));
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean remove(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.remove") || PlotMe.cPerms((CommandSender) p, "PlotMe.use.remove")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    if (args.length < 2 || args[1].equalsIgnoreCase("")) {
                        this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandRemove") + " <" + this.C("WordPlayer") + ">");
                    } else {
                        Plot plot = PlotManager.getPlotById(p, id);
                        String playername = p.getName();
                        String allowed = args[1];
                        if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.remove")) {
                            if (plot.isAllowed(allowed)) {
                                World w = p.getWorld();
                                PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.RemovePlayerPrice;
                                    double balance = PlotMe.economy.getBalance(playername);
                                    if (balance >= price) {
                                        EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                                        if (!er.transactionSuccess()) {
                                            this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                            this.warn(er.errorMessage);
                                            return true;
                                        }
                                    } else {
                                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughRemove") + " " + this.C("WordMissing") + " " + (Object) this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                }
                                plot.removeAllowed(allowed);
                                this.Send((CommandSender) p, String.valueOf(this.C("WordPlayer")) + " " + (Object) this.RED + allowed + (Object) this.RESET + " " + this.C("WorldRemoved") + ". " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgRemovedPlayer") + " " + allowed + " " + this.C("MsgFromPlot") + " " + id + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                                }
                            } else {
                                this.Send((CommandSender) p, String.valueOf(this.C("WordPlayer")) + " " + (Object) this.RED + args[1] + (Object) this.RESET + " " + this.C("MsgWasNotAllowed"));
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedRemove"));
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean undeny(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.undeny") || PlotMe.cPerms((CommandSender) p, "PlotMe.use.undeny")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (!PlotManager.isPlotAvailable(id, p)) {
                    if (args.length < 2 || args[1].equalsIgnoreCase("")) {
                        this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandUndeny") + " <" + this.C("WordPlayer") + ">");
                    } else {
                        Plot plot = PlotManager.getPlotById(p, id);
                        String playername = p.getName();
                        String denied = args[1];
                        if (plot.owner.equalsIgnoreCase(playername) || PlotMe.cPerms((CommandSender) p, "PlotMe.admin.undeny")) {
                            if (plot.isDenied(denied)) {
                                World w = p.getWorld();
                                PlotMapInfo pmi = PlotManager.getMap(w);
                                double price = 0.0;
                                if (PlotManager.isEconomyEnabled(w)) {
                                    price = pmi.UndenyPlayerPrice;
                                    double balance = PlotMe.economy.getBalance(playername);
                                    if (balance >= price) {
                                        EconomyResponse er = PlotMe.economy.withdrawPlayer(playername, price);
                                        if (!er.transactionSuccess()) {
                                            this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                            this.warn(er.errorMessage);
                                            return true;
                                        }
                                    } else {
                                        this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotEnoughUndeny") + " " + this.C("WordMissing") + " " + (Object) this.RESET + this.f(price - balance, false));
                                        return true;
                                    }
                                }
                                plot.removeDenied(denied);
                                this.Send((CommandSender) p, String.valueOf(this.C("WordPlayer")) + " " + (Object) this.RED + denied + (Object) this.RESET + " " + this.C("MsgNowUndenied") + " " + this.f(-price));
                                if (this.isAdv) {
                                    PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgUndeniedPlayer") + " " + denied + " " + this.C("MsgFromPlot") + " " + id + (price != 0.0 ? new StringBuilder(" ").append(this.C("WordFor")).append(" ").append(price).toString() : ""));
                                }
                            } else {
                                this.Send((CommandSender) p, String.valueOf(this.C("WordPlayer")) + " " + (Object) this.RED + args[1] + (Object) this.RESET + " " + this.C("MsgWasNotDenied"));
                            }
                        } else {
                            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgNotYoursNotAllowedUndeny"));
                        }
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgThisPlot") + "(" + id + ") " + this.C("MsgHasNoOwner"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean setowner(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.setowner")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String id = PlotManager.getPlotId(p.getLocation());
                if (id.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else if (args.length < 2 || args[1].equals("")) {
                    this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandSetowner") + " <" + this.C("WordPlayer") + ">");
                } else {
                    String newowner = args[1];
                    String oldowner = "<" + this.C("WordNotApplicable") + ">";
                    String playername = p.getName();
                    if (!PlotManager.isPlotAvailable(id, p)) {
                        Plot plot = PlotManager.getPlotById(p, id);
                        PlotMapInfo pmi = PlotManager.getMap(p);
                        oldowner = plot.owner;
                        if (PlotManager.isEconomyEnabled(p)) {
                            int n;
                            Player[] arrplayer;
                            Player player;
                            int n2;
                            EconomyResponse er;
                            if (pmi.RefundClaimPriceOnSetOwner && newowner != oldowner) {
                                er = PlotMe.economy.depositPlayer(oldowner, pmi.ClaimPrice);
                                if (!er.transactionSuccess()) {
                                    this.Send((CommandSender) p, (Object) this.RED + er.errorMessage);
                                    this.warn(er.errorMessage);
                                    return true;
                                }
                                arrplayer = Bukkit.getServer().getOnlinePlayers();
                                n2 = arrplayer.length;
                                n = 0;
                                while (n < n2) {
                                    player = arrplayer[n];
                                    if (player.getName().equalsIgnoreCase(oldowner)) {
                                        this.Send((CommandSender) player, String.valueOf(this.C("MsgYourPlot")) + " " + id + " " + this.C("MsgNowOwnedBy") + " " + newowner + ". " + this.f(pmi.ClaimPrice));
                                        break;
                                    }
                                    ++n;
                                }
                            }
                            if (!plot.currentbidder.equals("")) {
                                er = PlotMe.economy.depositPlayer(plot.currentbidder, plot.currentbid);
                                if (!er.transactionSuccess()) {
                                    this.Send((CommandSender) p, er.errorMessage);
                                    this.warn(er.errorMessage);
                                } else {
                                    arrplayer = Bukkit.getServer().getOnlinePlayers();
                                    n2 = arrplayer.length;
                                    n = 0;
                                    while (n < n2) {
                                        player = arrplayer[n];
                                        if (player.getName().equalsIgnoreCase(plot.currentbidder)) {
                                            this.Send((CommandSender) player, String.valueOf(this.C("WordPlot")) + " " + id + " " + this.C("MsgChangedOwnerFrom") + " " + oldowner + " " + this.C("WordTo") + " " + newowner + ". " + this.f(plot.currentbid));
                                            break;
                                        }
                                        ++n;
                                    }
                                }
                            }
                        }
                        plot.currentbidder = "";
                        plot.currentbid = 0.0;
                        plot.auctionned = false;
                        plot.forsale = false;
                        PlotManager.setSellSign(p.getWorld(), plot);
                        plot.updateField("currentbidder", "");
                        plot.updateField("currentbid", 0);
                        plot.updateField("auctionned", false);
                        plot.updateField("forsale", false);
                        plot.owner = newowner;
                        PlotManager.setOwnerSign(p.getWorld(), plot);
                        plot.updateField("owner", newowner);
                    } else {
                        PlotManager.createPlot(p.getWorld(), id, newowner);
                    }
                    this.Send((CommandSender) p, String.valueOf(this.C("MsgOwnerChangedTo")) + " " + (Object) this.RED + newowner);
                    if (this.isAdv) {
                        PlotMe.logger.info(String.valueOf(this.LOG) + playername + " " + this.C("MsgChangedOwnerOf") + " " + id + " " + this.C("WordFrom") + " " + oldowner + " " + this.C("WordTo") + " " + newowner);
                    }
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean id(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.id")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else {
                String plotid = PlotManager.getPlotId(p.getLocation());
                if (plotid.equals("")) {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNoPlotFound"));
                } else {
                    p.sendMessage((Object) this.BLUE + this.C("WordPlot") + " " + this.C("WordId") + ": " + (Object) this.RESET + plotid);
                    Location bottom = PlotManager.getPlotBottomLoc(p.getWorld(), plotid);
                    Location top = PlotManager.getPlotTopLoc(p.getWorld(), plotid);
                    p.sendMessage((Object) this.BLUE + this.C("WordBottom") + ": " + (Object) this.RESET + bottom.getBlockX() + (Object) ChatColor.BLUE + "," + (Object) this.RESET + bottom.getBlockZ());
                    p.sendMessage((Object) this.BLUE + this.C("WordTop") + ": " + (Object) this.RESET + top.getBlockX() + (Object) ChatColor.BLUE + "," + (Object) this.RESET + top.getBlockZ());
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean move(Player p, String[] args) {
        if (PlotMe.cPerms((CommandSender) p, "PlotMe.admin.move")) {
            if (!PlotManager.isPlotWorld(p)) {
                this.Send((CommandSender) p, (Object) this.RED + this.C("MsgNotPlotWorld"));
            } else if (args.length < 3 || args[1].equalsIgnoreCase("") || args[2].equalsIgnoreCase("")) {
                this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandMove") + " <" + this.C("WordIdFrom") + "> <" + this.C("WordIdTo") + "> " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandMove") + " 0;1 2;-1");
            } else {
                String plot1 = args[1];
                String plot2 = args[2];
                if (!PlotManager.isValidId(plot1) || !PlotManager.isValidId(plot2)) {
                    this.Send((CommandSender) p, String.valueOf(this.C("WordUsage")) + ": " + (Object) this.RED + "/plotme " + this.C("CommandMove") + " <" + this.C("WordIdFrom") + "> <" + this.C("WordIdTo") + "> " + (Object) this.RESET + this.C("WordExample") + ": " + (Object) this.RED + "/plotme " + this.C("CommandMove") + " 0;1 2;-1");
                    return true;
                }
                if (PlotManager.movePlot(p.getWorld(), plot1, plot2)) {
                    this.Send((CommandSender) p, this.C("MsgPlotMovedSuccess"));
                    if (this.isAdv) {
                        PlotMe.logger.info(String.valueOf(this.LOG) + p.getName() + " " + this.C("MsgExchangedPlot") + " " + plot1 + " " + this.C("MsgAndPlot") + " " + plot2);
                    }
                } else {
                    this.Send((CommandSender) p, (Object) this.RED + this.C("ErrMovingPlot"));
                }
            }
        } else {
            this.Send((CommandSender) p, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private boolean reload(CommandSender s, String[] args) {
        if (!(s instanceof Player) || PlotMe.cPerms((CommandSender) ((Player) s), "PlotMe.admin.reload")) {
            this.plugin.initialize();
            this.Send(s, this.C("MsgReloadedSuccess"));
            if (this.isAdv) {
                PlotMe.logger.info(String.valueOf(this.LOG) + s.getName() + " " + this.C("MsgReloadedConfigurations"));
            }
        } else {
            this.Send(s, (Object) this.RED + this.C("MsgPermissionDenied"));
        }
        return true;
    }

    private StringBuilder whitespace(int length) {
        int spaceWidth = MinecraftFontWidthCalculator.getStringWidth(" ");
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (i + spaceWidth < length) {
            ret.append(" ");
            i += spaceWidth;
        }
        return ret;
    }

    private String round(double money) {
        return money % 1.0 == 0.0 ? "" + Math.round(money) : "" + money;
    }

    private void warn(String msg) {
        PlotMe.logger.warning(String.valueOf(this.LOG) + msg);
    }

    private String f(double price) {
        return this.f(price, true);
    }

    private String f(double price, boolean showsign) {
        if (price == 0.0) {
            return "";
        }
        String format = this.round(Math.abs(price));
        if (PlotMe.economy != null) {
            String string = format = price <= 1.0 && price >= -1.0 ? String.valueOf(format) + " " + PlotMe.economy.currencyNameSingular() : String.valueOf(format) + " " + PlotMe.economy.currencyNamePlural();
        }
        if (showsign) {
            return (Object) this.GREEN + (price > 0.0 ? new StringBuilder("+").append(format).toString() : new StringBuilder("-").append(format).toString());
        }
        return (Object) this.GREEN + format;
    }

    private void Send(CommandSender cs, String text) {
        cs.sendMessage(String.valueOf(this.PREFIX) + text);
    }

    private String FormatBiome(String biome) {
        biome = biome.toLowerCase();
        String[] tokens = biome.split("_");
        biome = "";
        String[] arrstring = tokens;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String token = arrstring[n2];
            token = String.valueOf(token.substring(0, 1).toUpperCase()) + token.substring(1);
            biome = biome.equalsIgnoreCase("") ? token : String.valueOf(biome) + "_" + token;
            ++n2;
        }
        return biome;
    }
}

