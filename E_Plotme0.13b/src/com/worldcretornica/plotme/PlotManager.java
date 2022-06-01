/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  com.griefcraft.lwc.LWC
 *  com.griefcraft.model.Protection
 *  com.griefcraft.sql.PhysDB
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Biome
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Jukebox
 *  org.bukkit.block.Sign
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package com.worldcretornica.plotme;

import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import java.util.*;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PlotManager {
    public static String getPlotId(Location loc) {
        PlotMapInfo pmi = PlotManager.getMap(loc);
        if (pmi != null) {
            double n3;
            int valx = loc.getBlockX();
            int valz = loc.getBlockZ();
            int size = pmi.PlotSize + pmi.PathWidth;
            int pathsize = pmi.PathWidth;
            boolean road = false;
            int mod2 = 0;
            double mod1 = 1;
            int x = (int) Math.ceil((double) valx / (double) size);
            int z = (int) Math.ceil((double) valz / (double) size);
            if (pathsize % 2 == 1) {
                n3 = Math.ceil((double) pathsize / 2.0);
                mod2 = -1;
            } else {
                n3 = Math.floor((double) pathsize / 2.0);
            }
            double i = n3;
            while (i >= 0.0) {
                if (((double) valx - i + (double) mod1) % (double) size == 0.0 || ((double) valx + i + (double) mod2) % (double) size == 0.0) {
                    road = true;
                    x = (int) Math.ceil(((double) valx - n3) / (double) size);
                }
                if (((double) valz - i + (double) mod1) % (double) size == 0.0 || ((double) valz + i + (double) mod2) % (double) size == 0.0) {
                    road = true;
                    z = (int) Math.ceil(((double) valz - n3) / (double) size);
                }
                i -= 1.0;
            }
            if (road) {
                return "";
            }
            return x + ";" + z;
        }
        return "";
    }

    public static String getPlotId(Player player) {
        return PlotManager.getPlotId(player.getLocation());
    }

    public static List<Player> getPlayersInPlot(World w, String id) {
        ArrayList<Player> playersInPlot = new ArrayList<Player>();
        for (Player p : w.getPlayers()) {
            if (!PlotManager.getPlotId(p).equals(id)) continue;
            playersInPlot.add(p);
        }
        return playersInPlot;
    }

    public static void adjustLinkedPlots(String id, World world) {
        PlotMapInfo pmi = PlotManager.getMap(world);
        if (pmi != null) {
            HashMap<String, Plot> plots = pmi.plots;
            int x = PlotManager.getIdX(id);
            int z = PlotManager.getIdZ(id);
            Plot p11 = plots.get(id);
            if (p11 != null) {
                Plot p01 = plots.get(String.valueOf(x - 1) + ";" + z);
                Plot p10 = plots.get(String.valueOf(x) + ";" + (z - 1));
                Plot p12 = plots.get(String.valueOf(x) + ";" + (z + 1));
                Plot p21 = plots.get(String.valueOf(x + 1) + ";" + z);
                Plot p00 = plots.get(String.valueOf(x - 1) + ";" + (z - 1));
                Plot p02 = plots.get(String.valueOf(x - 1) + ";" + (z + 1));
                Plot p20 = plots.get(String.valueOf(x + 1) + ";" + (z - 1));
                Plot p22 = plots.get(String.valueOf(x + 1) + ";" + (z + 1));
                if (p01 != null && p01.owner.equalsIgnoreCase(p11.owner)) {
                    PlotManager.fillroad(p01, p11, world);
                }
                if (p10 != null && p10.owner.equalsIgnoreCase(p11.owner)) {
                    PlotManager.fillroad(p10, p11, world);
                }
                if (p12 != null && p12.owner.equalsIgnoreCase(p11.owner)) {
                    PlotManager.fillroad(p12, p11, world);
                }
                if (p21 != null && p21.owner.equalsIgnoreCase(p11.owner)) {
                    PlotManager.fillroad(p21, p11, world);
                }
                if (p00 != null && p10 != null && p01 != null && p00.owner.equalsIgnoreCase(p11.owner) && p11.owner.equalsIgnoreCase(p10.owner) && p10.owner.equalsIgnoreCase(p01.owner)) {
                    PlotManager.fillmiddleroad(p00, p11, world);
                }
                if (p10 != null && p20 != null && p21 != null && p10.owner.equalsIgnoreCase(p11.owner) && p11.owner.equalsIgnoreCase(p20.owner) && p20.owner.equalsIgnoreCase(p21.owner)) {
                    PlotManager.fillmiddleroad(p20, p11, world);
                }
                if (p01 != null && p02 != null && p12 != null && p01.owner.equalsIgnoreCase(p11.owner) && p11.owner.equalsIgnoreCase(p02.owner) && p02.owner.equalsIgnoreCase(p12.owner)) {
                    PlotManager.fillmiddleroad(p02, p11, world);
                }
                if (p12 != null && p21 != null && p22 != null && p12.owner.equalsIgnoreCase(p11.owner) && p11.owner.equalsIgnoreCase(p21.owner) && p21.owner.equalsIgnoreCase(p22.owner)) {
                    PlotManager.fillmiddleroad(p22, p11, world);
                }
            }
        }
    }

    private static void fillroad(Plot plot1, Plot plot2, World w) {
        int maxX;
        int minZ;
        boolean isWallX;
        int maxZ;
        int minX;
        Location bottomPlot1 = PlotManager.getPlotBottomLoc(w, plot1.id);
        Location topPlot1 = PlotManager.getPlotTopLoc(w, plot1.id);
        Location bottomPlot2 = PlotManager.getPlotBottomLoc(w, plot2.id);
        Location topPlot2 = PlotManager.getPlotTopLoc(w, plot2.id);
        PlotMapInfo pmi = PlotManager.getMap(w);
        int h = pmi.RoadHeight;
        short wallId = pmi.WallBlockId;
        byte wallValue = pmi.WallBlockValue;
        short fillId = pmi.PlotFloorBlockId;
        byte fillValue = pmi.PlotFloorBlockValue;
        if (bottomPlot1.getBlockX() == bottomPlot2.getBlockX()) {
            minX = bottomPlot1.getBlockX();
            maxX = topPlot1.getBlockX();
            minZ = Math.min(bottomPlot1.getBlockZ(), bottomPlot2.getBlockZ()) + pmi.PlotSize;
            maxZ = Math.max(topPlot1.getBlockZ(), topPlot2.getBlockZ()) - pmi.PlotSize;
        } else {
            minZ = bottomPlot1.getBlockZ();
            maxZ = topPlot1.getBlockZ();
            minX = Math.min(bottomPlot1.getBlockX(), bottomPlot2.getBlockX()) + pmi.PlotSize;
            maxX = Math.max(topPlot1.getBlockX(), topPlot2.getBlockX()) - pmi.PlotSize;
        }
        boolean bl = isWallX = maxX - minX > maxZ - minZ;
        if (isWallX) {
            --minX;
            ++maxX;
        } else {
            --minZ;
            ++maxZ;
        }
        int x = minX;
        while (x <= maxX) {
            int z = minZ;
            while (z <= maxZ) {
                int y = h;
                while (y < w.getMaxHeight()) {
                    if (y >= h + 2) {
                        w.getBlockAt(x, y, z).setType(Material.AIR);
                    } else if (y == h + 1) {
                        if (isWallX && (x == minX || x == maxX)) {
                            w.getBlockAt(x, y, z).setTypeIdAndData((int) wallId, wallValue, true);
                        } else if (!(isWallX || z != minZ && z != maxZ)) {
                            w.getBlockAt(x, y, z).setTypeIdAndData((int) wallId, wallValue, true);
                        } else {
                            w.getBlockAt(x, y, z).setType(Material.AIR);
                        }
                    } else {
                        w.getBlockAt(x, y, z).setTypeIdAndData((int) fillId, fillValue, true);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
    }

    private static void fillmiddleroad(Plot plot1, Plot plot2, World w) {
        Location bottomPlot1 = PlotManager.getPlotBottomLoc(w, plot1.id);
        Location topPlot1 = PlotManager.getPlotTopLoc(w, plot1.id);
        Location bottomPlot2 = PlotManager.getPlotBottomLoc(w, plot2.id);
        Location topPlot2 = PlotManager.getPlotTopLoc(w, plot2.id);
        PlotMapInfo pmi = PlotManager.getMap(w);
        int h = pmi.RoadHeight;
        short fillId = pmi.PlotFloorBlockId;
        int minX = Math.min(topPlot1.getBlockX(), topPlot2.getBlockX());
        int maxX = Math.max(bottomPlot1.getBlockX(), bottomPlot2.getBlockX());
        int minZ = Math.min(topPlot1.getBlockZ(), topPlot2.getBlockZ());
        int maxZ = Math.max(bottomPlot1.getBlockZ(), bottomPlot2.getBlockZ());
        int x = minX;
        while (x <= maxX) {
            int z = minZ;
            while (z <= maxZ) {
                int y = h;
                while (y < w.getMaxHeight()) {
                    if (y >= h + 1) {
                        w.getBlockAt(x, y, z).setType(Material.AIR);
                    } else {
                        w.getBlockAt(x, y, z).setTypeId((int) fillId);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
    }

    public static boolean isPlotAvailable(String id, World world) {
        return PlotManager.isPlotAvailable(id, world.getName().toLowerCase());
    }

    public static boolean isPlotAvailable(String id, Player p) {
        return PlotManager.isPlotAvailable(id, p.getWorld().getName().toLowerCase());
    }

    public static boolean isPlotAvailable(String id, String world) {
        if (PlotManager.isPlotWorld(world)) {
            return !PlotManager.getPlots(world).containsKey(id);
        }
        return false;
    }

    public static Plot createPlot(World world, String id, String owner) {
        if (PlotManager.isPlotAvailable(id, world) && id != "") {
            Plot plot = new Plot(owner, PlotManager.getPlotTopLoc(world, id), PlotManager.getPlotBottomLoc(world, id), id, PlotManager.getMap((World) world).DaysToExpiration);
            PlotManager.setOwnerSign(world, plot);
            PlotManager.getPlots(world).put(id, plot);
            SqlManager.addPlot(plot, PlotManager.getIdX(id), PlotManager.getIdZ(id), world);
            return plot;
        }
        return null;
    }

    public static void setOwnerSign(World world, Plot plot) {
        Location pillar = new Location(world, (double) (PlotManager.bottomX(plot.id, world) - 1), (double) (PlotManager.getMap((World) world).RoadHeight + 1), (double) (PlotManager.bottomZ(plot.id, world) - 1));
        Block bsign = pillar.add(0.0, 0.0, -1.0).getBlock();
        bsign.setType(Material.AIR);
        bsign.setTypeIdAndData(Material.WALL_SIGN.getId(), (byte) 2, false);
        String id = PlotManager.getPlotId(new Location(world, (double) PlotManager.bottomX(plot.id, world), 0.0, (double) PlotManager.bottomZ(plot.id, world)));
        Sign sign = (Sign) bsign.getState();
        if ((String.valueOf(PlotMe.caption("SignId")) + id).length() > 16) {
            sign.setLine(0, (String.valueOf(PlotMe.caption("SignId")) + id).substring(0, 16));
            if ((String.valueOf(PlotMe.caption("SignId")) + id).length() > 32) {
                sign.setLine(1, (String.valueOf(PlotMe.caption("SignId")) + id).substring(16, 32));
            } else {
                sign.setLine(1, (String.valueOf(PlotMe.caption("SignId")) + id).substring(16));
            }
        } else {
            sign.setLine(0, String.valueOf(PlotMe.caption("SignId")) + id);
        }
        if ((String.valueOf(PlotMe.caption("SignOwner")) + plot.owner).length() > 16) {
            sign.setLine(2, (String.valueOf(PlotMe.caption("SignOwner")) + plot.owner).substring(0, 16));
            if ((String.valueOf(PlotMe.caption("SignOwner")) + plot.owner).length() > 32) {
                sign.setLine(3, (String.valueOf(PlotMe.caption("SignOwner")) + plot.owner).substring(16, 32));
            } else {
                sign.setLine(3, (String.valueOf(PlotMe.caption("SignOwner")) + plot.owner).substring(16));
            }
        } else {
            sign.setLine(2, String.valueOf(PlotMe.caption("SignOwner")) + plot.owner);
            sign.setLine(3, "");
        }
        sign.update(true);
    }

    public static void setSellSign(World world, Plot plot) {
        PlotManager.removeSellSign(world, plot.id);
        if (plot.forsale || plot.auctionned) {
            Location pillar = new Location(world, (double) (PlotManager.bottomX(plot.id, world) - 1), (double) (PlotManager.getMap((World) world).RoadHeight + 1), (double) (PlotManager.bottomZ(plot.id, world) - 1));
            Block bsign = pillar.clone().add(-1.0, 0.0, 0.0).getBlock();
            bsign.setType(Material.AIR);
            bsign.setTypeIdAndData(Material.WALL_SIGN.getId(), (byte) 4, false);
            Sign sign = (Sign) bsign.getState();
            if (plot.forsale) {
                sign.setLine(0, PlotMe.caption("SignForSale"));
                sign.setLine(1, PlotMe.caption("SignPrice"));
                if (plot.customprice % 1.0 == 0.0) {
                    sign.setLine(2, String.valueOf(PlotMe.caption("SignPriceColor")) + Math.round(plot.customprice));
                } else {
                    sign.setLine(2, String.valueOf(PlotMe.caption("SignPriceColor")) + plot.customprice);
                }
                sign.setLine(3, "/plotme " + PlotMe.caption("CommandBuy"));
                sign.update(true);
            }
            if (plot.auctionned) {
                if (plot.forsale) {
                    bsign = pillar.clone().add(-1.0, 0.0, 1.0).getBlock();
                    bsign.setType(Material.AIR);
                    bsign.setTypeIdAndData(Material.WALL_SIGN.getId(), (byte) 4, false);
                    sign = (Sign) bsign.getState();
                }
                sign.setLine(0, PlotMe.caption("SignOnAuction"));
                if (plot.currentbidder.equals("")) {
                    sign.setLine(1, PlotMe.caption("SignMinimumBid"));
                } else {
                    sign.setLine(1, PlotMe.caption("SignCurrentBid"));
                }
                if (plot.currentbid % 1.0 == 0.0) {
                    sign.setLine(2, String.valueOf(PlotMe.caption("SignCurrentBidColor")) + Math.round(plot.currentbid));
                } else {
                    sign.setLine(2, String.valueOf(PlotMe.caption("SignCurrentBidColor")) + plot.currentbid);
                }
                sign.setLine(3, "/plotme " + PlotMe.caption("CommandBid") + " <x>");
                sign.update(true);
            }
        }
    }

    public static void removeOwnerSign(World world, String id) {
        Location bottom = PlotManager.getPlotBottomLoc(world, id);
        Location pillar = new Location(world, bottom.getX() - 1.0, (double) (PlotManager.getMap((World) world).RoadHeight + 1), bottom.getZ() - 1.0);
        Block bsign = pillar.add(0.0, 0.0, -1.0).getBlock();
        bsign.setType(Material.AIR);
    }

    public static void removeSellSign(World world, String id) {
        Location bottom = PlotManager.getPlotBottomLoc(world, id);
        Location pillar = new Location(world, bottom.getX() - 1.0, (double) (PlotManager.getMap((World) world).RoadHeight + 1), bottom.getZ() - 1.0);
        Block bsign = pillar.clone().add(-1.0, 0.0, 0.0).getBlock();
        bsign.setType(Material.AIR);
        bsign = pillar.clone().add(-1.0, 0.0, 1.0).getBlock();
        bsign.setType(Material.AIR);
    }

    public static int getIdX(String id) {
        return Integer.parseInt(id.substring(0, id.indexOf(";")));
    }

    public static int getIdZ(String id) {
        return Integer.parseInt(id.substring(id.indexOf(";") + 1));
    }

    public static Location getPlotBottomLoc(World world, String id) {
        int px = PlotManager.getIdX(id);
        int pz = PlotManager.getIdZ(id);
        PlotMapInfo pmi = PlotManager.getMap(world);
        int x = px * (pmi.PlotSize + pmi.PathWidth) - pmi.PlotSize - (int) Math.floor(pmi.PathWidth / 2);
        int z = pz * (pmi.PlotSize + pmi.PathWidth) - pmi.PlotSize - (int) Math.floor(pmi.PathWidth / 2);
        return new Location(world, (double) x, 1.0, (double) z);
    }

    public static Location getPlotTopLoc(World world, String id) {
        int px = PlotManager.getIdX(id);
        int pz = PlotManager.getIdZ(id);
        PlotMapInfo pmi = PlotManager.getMap(world);
        int x = px * (pmi.PlotSize + pmi.PathWidth) - (int) Math.floor(pmi.PathWidth / 2) - 1;
        int z = pz * (pmi.PlotSize + pmi.PathWidth) - (int) Math.floor(pmi.PathWidth / 2) - 1;
        return new Location(world, (double) x, 255.0, (double) z);
    }

    public static void setBiome(World w, String id, Plot plot, Biome b) {
        int bottomX = PlotManager.bottomX(plot.id, w) - 1;
        int topX = PlotManager.topX(plot.id, w) + 1;
        int bottomZ = PlotManager.bottomZ(plot.id, w) - 1;
        int topZ = PlotManager.topZ(plot.id, w) + 1;
        int x = bottomX;
        while (x <= topX) {
            int z = bottomZ;
            while (z <= topZ) {
                w.getBlockAt(x, 0, z).setBiome(b);
                ++z;
            }
            ++x;
        }
        plot.biome = b;
        PlotManager.refreshPlotChunks(w, plot);
        SqlManager.updatePlot(PlotManager.getIdX(id), PlotManager.getIdZ(id), plot.world, "biome", b.name());
    }

    public static void refreshPlotChunks(World w, Plot plot) {
        int bottomX = PlotManager.bottomX(plot.id, w);
        int topX = PlotManager.topX(plot.id, w);
        int bottomZ = PlotManager.bottomZ(plot.id, w);
        int topZ = PlotManager.topZ(plot.id, w);
        int minChunkX = (int) Math.floor((double) bottomX / 16.0);
        int maxChunkX = (int) Math.floor((double) topX / 16.0);
        int minChunkZ = (int) Math.floor((double) bottomZ / 16.0);
        int maxChunkZ = (int) Math.floor((double) topZ / 16.0);
        int x = minChunkX;
        while (x <= maxChunkX) {
            int z = minChunkZ;
            while (z <= maxChunkZ) {
                w.refreshChunk(x, z);
                ++z;
            }
            ++x;
        }
    }

    public static Location getTop(World w, Plot plot) {
        return new Location(w, (double) PlotManager.topX(plot.id, w), (double) w.getMaxHeight(), (double) PlotManager.topZ(plot.id, w));
    }

    public static Location getBottom(World w, Plot plot) {
        return new Location(w, (double) PlotManager.bottomX(plot.id, w), 0.0, (double) PlotManager.bottomZ(plot.id, w));
    }

    public static void clear(World w, Plot plot) {
        PlotManager.clear(PlotManager.getBottom(w, plot), PlotManager.getTop(w, plot));
        PlotManager.RemoveLWC(w, plot);
    }

    public static void clear(Location bottom, Location top) {
        PlotMapInfo pmi = PlotManager.getMap(bottom);
        int bottomX = bottom.getBlockX();
        int topX = top.getBlockX();
        int bottomZ = bottom.getBlockZ();
        int topZ = top.getBlockZ();
        int minChunkX = (int) Math.floor((double) bottomX / 16.0);
        int maxChunkX = (int) Math.floor((double) topX / 16.0);
        int minChunkZ = (int) Math.floor((double) bottomZ / 16.0);
        int maxChunkZ = (int) Math.floor((double) topZ / 16.0);
        World w = bottom.getWorld();
        int cx = minChunkX;
        while (cx <= maxChunkX) {
            int cz = minChunkZ;
            while (cz <= maxChunkZ) {
                Chunk chunk = w.getChunkAt(cx, cz);
                Entity[] arrentity = chunk.getEntities();
                int n = arrentity.length;
                int n2 = 0;
                while (n2 < n) {
                    Entity e = arrentity[n2];
                    Location eloc = e.getLocation();
                    if (!(e instanceof Player) && eloc.getBlockX() >= bottom.getBlockX() && eloc.getBlockX() <= top.getBlockX() && eloc.getBlockZ() >= bottom.getBlockZ() && eloc.getBlockZ() <= top.getBlockZ()) {
                        e.remove();
                    }
                    ++n2;
                }
                ++cz;
            }
            ++cx;
        }
        int x = bottomX;
        while (x <= topX) {
            int z = bottomZ;
            while (z <= topZ) {
                Block block = new Location(w, (double) x, 0.0, (double) z).getBlock();
                block.setBiome(Biome.PLAINS);
                int y = w.getMaxHeight();
                while (y >= 0) {
                    block = new Location(w, (double) x, (double) y, (double) z).getBlock();
                    BlockState state = block.getState();
                    if (state instanceof InventoryHolder) {
                        InventoryHolder holder = (InventoryHolder) state;
                        holder.getInventory().clear();
                    }
                    if (state instanceof Jukebox) {
                        Jukebox jukebox = (Jukebox) state;
                        try {
                            jukebox.setPlaying(Material.AIR);
                        } catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    if (y == 0) {
                        block.setTypeId((int) pmi.BottomBlockId);
                    } else if (y < pmi.RoadHeight) {
                        block.setTypeId((int) pmi.PlotFillingBlockId);
                    } else if (y == pmi.RoadHeight) {
                        block.setTypeId((int) pmi.PlotFloorBlockId);
                    } else if (y != pmi.RoadHeight + 1 || x != bottomX - 1 && x != topX + 1 && z != bottomZ - 1 && z != topZ + 1) {
                        block.setTypeIdAndData(0, (byte) 0, false);
                    }
                    --y;
                }
                ++z;
            }
            ++x;
        }
        PlotManager.adjustWall(bottom);
    }

    public static void adjustWall(Location l) {
        Block block;
        String currentblockid;
        int z;
        Plot plot = PlotManager.getPlotById(l);
        World w = l.getWorld();
        PlotMapInfo pmi = PlotManager.getMap(w);
        ArrayList<String> wallids = new ArrayList<String>();
        String auctionwallid = pmi.AuctionWallBlockId;
        String forsalewallid = pmi.ForSaleWallBlockId;
        if (plot.protect) {
            wallids.add(pmi.ProtectedWallBlockId);
        }
        if (plot.auctionned && !wallids.contains(auctionwallid)) {
            wallids.add(auctionwallid);
        }
        if (plot.forsale && !wallids.contains(forsalewallid)) {
            wallids.add(forsalewallid);
        }
        if (wallids.size() == 0) {
            wallids.add(pmi.WallBlockId + ":" + pmi.WallBlockValue);
        }
        int ctr = 0;
        Location bottom = PlotManager.getPlotBottomLoc(w, plot.id);
        Location top = PlotManager.getPlotTopLoc(w, plot.id);
        int x = bottom.getBlockX() - 1;
        while (x < top.getBlockX() + 1) {
            z = bottom.getBlockZ() - 1;
            currentblockid = (String) wallids.get(ctr);
            ctr = ctr == wallids.size() - 1 ? 0 : ctr + 1;
            block = w.getBlockAt(x, pmi.RoadHeight + 1, z);
            PlotManager.setWall(block, currentblockid);
            ++x;
        }
        z = bottom.getBlockZ() - 1;
        while (z < top.getBlockZ() + 1) {
            x = top.getBlockX() + 1;
            currentblockid = (String) wallids.get(ctr);
            ctr = ctr == wallids.size() - 1 ? 0 : ctr + 1;
            block = w.getBlockAt(x, pmi.RoadHeight + 1, z);
            PlotManager.setWall(block, currentblockid);
            ++z;
        }
        x = top.getBlockX() + 1;
        while (x > bottom.getBlockX() - 1) {
            z = top.getBlockZ() + 1;
            currentblockid = (String) wallids.get(ctr);
            ctr = ctr == wallids.size() - 1 ? 0 : ctr + 1;
            block = w.getBlockAt(x, pmi.RoadHeight + 1, z);
            PlotManager.setWall(block, currentblockid);
            --x;
        }
        z = top.getBlockZ() + 1;
        while (z > bottom.getBlockZ() - 1) {
            x = bottom.getBlockX() - 1;
            currentblockid = (String) wallids.get(ctr);
            ctr = ctr == wallids.size() - 1 ? 0 : ctr + 1;
            block = w.getBlockAt(x, pmi.RoadHeight + 1, z);
            PlotManager.setWall(block, currentblockid);
            --z;
        }
    }

    private static void setWall(Block block, String currentblockid) {
        int blockId;
        byte blockData = 0;
        PlotMapInfo pmi = PlotManager.getMap(block);
        if (currentblockid.contains(":")) {
            try {
                blockId = Integer.parseInt(currentblockid.substring(0, currentblockid.indexOf(":")));
                blockData = Byte.parseByte(currentblockid.substring(currentblockid.indexOf(":") + 1));
            } catch (NumberFormatException e) {
                blockId = pmi.WallBlockId;
                blockData = pmi.WallBlockValue;
            }
        } else {
            try {
                blockId = Integer.parseInt(currentblockid);
            } catch (NumberFormatException e) {
                blockId = pmi.WallBlockId;
            }
        }
        block.setTypeIdAndData(blockId, blockData, true);
    }

    public static boolean isBlockInPlot(Plot plot, Location blocklocation) {
        World w = blocklocation.getWorld();
        int lowestX = Math.min(PlotManager.bottomX(plot.id, w), PlotManager.topX(plot.id, w));
        int highestX = Math.max(PlotManager.bottomX(plot.id, w), PlotManager.topX(plot.id, w));
        int lowestZ = Math.min(PlotManager.bottomZ(plot.id, w), PlotManager.topZ(plot.id, w));
        int highestZ = Math.max(PlotManager.bottomZ(plot.id, w), PlotManager.topZ(plot.id, w));
        if (blocklocation.getBlockX() >= lowestX && blocklocation.getBlockX() <= highestX && blocklocation.getBlockZ() >= lowestZ && blocklocation.getBlockZ() <= highestZ) {
            return true;
        }
        return false;
    }

    public static boolean movePlot(World w, String idFrom, String idTo) {
        Location plot1Bottom = PlotManager.getPlotBottomLoc(w, idFrom);
        Location plot2Bottom = PlotManager.getPlotBottomLoc(w, idTo);
        Location plot1Top = PlotManager.getPlotTopLoc(w, idFrom);
        int distanceX = plot1Bottom.getBlockX() - plot2Bottom.getBlockX();
        int distanceZ = plot1Bottom.getBlockZ() - plot2Bottom.getBlockZ();
        int x = plot1Bottom.getBlockX();
        while (x <= plot1Top.getBlockX()) {
            int z = plot1Bottom.getBlockZ();
            while (z <= plot1Top.getBlockZ()) {
                Block plot1Block = w.getBlockAt(new Location(w, (double) x, 0.0, (double) z));
                Block plot2Block = w.getBlockAt(new Location(w, (double) (x - distanceX), 0.0, (double) (z - distanceZ)));
                String plot1Biome = plot1Block.getBiome().name();
                String plot2Biome = plot2Block.getBiome().name();
                plot1Block.setBiome(Biome.valueOf((String) plot2Biome));
                plot2Block.setBiome(Biome.valueOf((String) plot1Biome));
                int y = 0;
                while (y < w.getMaxHeight()) {
                    plot1Block = w.getBlockAt(new Location(w, (double) x, (double) y, (double) z));
                    int plot1Type = plot1Block.getTypeId();
                    byte plot1Data = plot1Block.getData();
                    plot2Block = w.getBlockAt(new Location(w, (double) (x - distanceX), (double) y, (double) (z - distanceZ)));
                    int plot2Type = plot2Block.getTypeId();
                    byte plot2Data = plot2Block.getData();
                    plot1Block.setTypeIdAndData(plot2Type, plot2Data, false);
                    plot1Block.setData(plot2Data);
                    plot2Block.setTypeIdAndData(plot1Type, plot1Data, false);
                    plot2Block.setData(plot1Data);
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        HashMap<String, Plot> plots = PlotManager.getPlots(w);
        if (plots.containsKey(idFrom)) {
            if (plots.containsKey(idTo)) {
                Plot plot1 = plots.get(idFrom);
                Plot plot2 = plots.get(idTo);
                int idX = PlotManager.getIdX(idTo);
                int idZ = PlotManager.getIdZ(idTo);
                SqlManager.deletePlot(idX, idZ, plot2.world);
                plots.remove(idFrom);
                plots.remove(idTo);
                idX = PlotManager.getIdX(idFrom);
                idZ = PlotManager.getIdZ(idFrom);
                SqlManager.deletePlot(idX, idZ, plot1.world);
                plot2.id = idX + ";" + idZ;
                SqlManager.addPlot(plot2, idX, idZ, w);
                plots.put(idFrom, plot2);
                int i = 0;
                while (i < plot2.comments.size()) {
                    SqlManager.addPlotComment(plot2.comments.get(i), i, idX, idZ, plot2.world);
                    ++i;
                }
                for (String player : plot2.allowed()) {
                    SqlManager.addPlotAllowed(player, idX, idZ, plot2.world);
                }
                idX = PlotManager.getIdX(idTo);
                idZ = PlotManager.getIdZ(idTo);
                plot1.id = idX + ";" + idZ;
                SqlManager.addPlot(plot1, idX, idZ, w);
                plots.put(idTo, plot1);
                int i2 = 0;
                while (i2 < plot1.comments.size()) {
                    SqlManager.addPlotComment(plot1.comments.get(i2), i2, idX, idZ, plot1.world);
                    ++i2;
                }
                for (String player : plot1.allowed()) {
                    SqlManager.addPlotAllowed(player, idX, idZ, plot1.world);
                }
                PlotManager.setOwnerSign(w, plot1);
                PlotManager.setSellSign(w, plot1);
                PlotManager.setOwnerSign(w, plot2);
                PlotManager.setSellSign(w, plot2);
            } else {
                Plot plot = plots.get(idFrom);
                int idX = PlotManager.getIdX(idFrom);
                int idZ = PlotManager.getIdZ(idFrom);
                SqlManager.deletePlot(idX, idZ, plot.world);
                plots.remove(idFrom);
                idX = PlotManager.getIdX(idTo);
                idZ = PlotManager.getIdZ(idTo);
                plot.id = idX + ";" + idZ;
                SqlManager.addPlot(plot, idX, idZ, w);
                plots.put(idTo, plot);
                int i = 0;
                while (i < plot.comments.size()) {
                    SqlManager.addPlotComment(plot.comments.get(i), i, idX, idZ, plot.world);
                    ++i;
                }
                for (String player : plot.allowed()) {
                    SqlManager.addPlotAllowed(player, idX, idZ, plot.world);
                }
                PlotManager.setOwnerSign(w, plot);
                PlotManager.setSellSign(w, plot);
                PlotManager.removeOwnerSign(w, idFrom);
                PlotManager.removeSellSign(w, idFrom);
            }
        } else if (plots.containsKey(idTo)) {
            Plot plot = plots.get(idTo);
            int idX = PlotManager.getIdX(idTo);
            int idZ = PlotManager.getIdZ(idTo);
            SqlManager.deletePlot(idX, idZ, plot.world);
            plots.remove(idTo);
            idX = PlotManager.getIdX(idFrom);
            idZ = PlotManager.getIdZ(idFrom);
            plot.id = idX + ";" + idZ;
            SqlManager.addPlot(plot, idX, idZ, w);
            plots.put(idFrom, plot);
            int i = 0;
            while (i < plot.comments.size()) {
                SqlManager.addPlotComment(plot.comments.get(i), i, idX, idZ, plot.world);
                ++i;
            }
            for (String player : plot.allowed()) {
                SqlManager.addPlotAllowed(player, idX, idZ, plot.world);
            }
            PlotManager.setOwnerSign(w, plot);
            PlotManager.setSellSign(w, plot);
            PlotManager.removeOwnerSign(w, idTo);
            PlotManager.removeSellSign(w, idTo);
        }
        return true;
    }

    public static int getNbOwnedPlot(Player p) {
        return PlotManager.getNbOwnedPlot(p.getName(), p.getWorld());
    }

    public static int getNbOwnedPlot(Player p, World w) {
        return PlotManager.getNbOwnedPlot(p.getName(), w);
    }

    public static int getNbOwnedPlot(String name, World w) {
        int nbfound = 0;
        if (PlotManager.getPlots(w) != null) {
            for (Plot plot : PlotManager.getPlots(w).values()) {
                if (!plot.owner.equalsIgnoreCase(name)) continue;
                ++nbfound;
            }
        }
        return nbfound;
    }

    public static int bottomX(String id, World w) {
        return PlotManager.getPlotBottomLoc(w, id).getBlockX();
    }

    public static int bottomZ(String id, World w) {
        return PlotManager.getPlotBottomLoc(w, id).getBlockZ();
    }

    public static int topX(String id, World w) {
        return PlotManager.getPlotTopLoc(w, id).getBlockX();
    }

    public static int topZ(String id, World w) {
        return PlotManager.getPlotTopLoc(w, id).getBlockZ();
    }

    public static boolean isPlotWorld(World w) {
        if (w == null) {
            return false;
        }
        return PlotMe.plotmaps.containsKey(w.getName().toLowerCase());
    }

    public static boolean isPlotWorld(String name) {
        return PlotMe.plotmaps.containsKey(name.toLowerCase());
    }

    public static boolean isPlotWorld(Location l) {
        if (l == null) {
            return false;
        }
        return PlotMe.plotmaps.containsKey(l.getWorld().getName().toLowerCase());
    }

    public static boolean isPlotWorld(Player p) {
        if (p == null) {
            return false;
        }
        return PlotMe.plotmaps.containsKey(p.getWorld().getName().toLowerCase());
    }

    public static boolean isPlotWorld(Block b) {
        if (b == null) {
            return false;
        }
        return PlotMe.plotmaps.containsKey(b.getWorld().getName().toLowerCase());
    }

    public static boolean isPlotWorld(BlockState b) {
        if (b == null) {
            return false;
        }
        return PlotMe.plotmaps.containsKey(b.getWorld().getName().toLowerCase());
    }

    public static boolean isEconomyEnabled(World w) {
        PlotMapInfo pmi = PlotManager.getMap(w);
        if (pmi == null) {
            return false;
        }
        if (pmi.UseEconomy && PlotMe.globalUseEconomy.booleanValue() && PlotMe.economy != null) {
            return true;
        }
        return false;
    }

    public static boolean isEconomyEnabled(String name) {
        PlotMapInfo pmi = PlotManager.getMap(name);
        if (pmi == null) {
            return false;
        }
        if (pmi.UseEconomy && PlotMe.globalUseEconomy.booleanValue()) {
            return true;
        }
        return false;
    }

    public static boolean isEconomyEnabled(Player p) {
        if (PlotMe.economy == null) {
            return false;
        }
        PlotMapInfo pmi = PlotManager.getMap(p);
        if (pmi == null) {
            return false;
        }
        if (pmi.UseEconomy && PlotMe.globalUseEconomy.booleanValue()) {
            return true;
        }
        return false;
    }

    public static boolean isEconomyEnabled(Block b) {
        PlotMapInfo pmi = PlotManager.getMap(b);
        if (pmi == null) {
            return false;
        }
        if (pmi.UseEconomy && PlotMe.globalUseEconomy.booleanValue()) {
            return true;
        }
        return false;
    }

    public static PlotMapInfo getMap(World w) {
        if (w == null) {
            return null;
        }
        String worldname = w.getName().toLowerCase();
        if (PlotMe.plotmaps.containsKey(worldname)) {
            return PlotMe.plotmaps.get(worldname);
        }
        return null;
    }

    public static PlotMapInfo getMap(String name) {
        String worldname = name.toLowerCase();
        if (PlotMe.plotmaps.containsKey(worldname)) {
            return PlotMe.plotmaps.get(worldname);
        }
        return null;
    }

    public static PlotMapInfo getMap(Location l) {
        if (l == null) {
            return null;
        }
        String worldname = l.getWorld().getName().toLowerCase();
        if (PlotMe.plotmaps.containsKey(worldname)) {
            return PlotMe.plotmaps.get(worldname);
        }
        return null;
    }

    public static PlotMapInfo getMap(Player p) {
        if (p == null) {
            return null;
        }
        String worldname = p.getWorld().getName().toLowerCase();
        if (PlotMe.plotmaps.containsKey(worldname)) {
            return PlotMe.plotmaps.get(worldname);
        }
        return null;
    }

    public static PlotMapInfo getMap(Block b) {
        if (b == null) {
            return null;
        }
        String worldname = b.getWorld().getName().toLowerCase();
        if (PlotMe.plotmaps.containsKey(worldname)) {
            return PlotMe.plotmaps.get(worldname);
        }
        return null;
    }

    public static HashMap<String, Plot> getPlots(World w) {
        PlotMapInfo pmi = PlotManager.getMap(w);
        if (pmi == null) {
            return null;
        }
        return pmi.plots;
    }

    public static HashMap<String, Plot> getPlots(String name) {
        PlotMapInfo pmi = PlotManager.getMap(name);
        if (pmi == null) {
            return null;
        }
        return pmi.plots;
    }

    public static HashMap<String, Plot> getPlots(Player p) {
        PlotMapInfo pmi = PlotManager.getMap(p);
        if (pmi == null) {
            return null;
        }
        return pmi.plots;
    }

    public static HashMap<String, Plot> getPlots(Block b) {
        PlotMapInfo pmi = PlotManager.getMap(b);
        if (pmi == null) {
            return null;
        }
        return pmi.plots;
    }

    public static HashMap<String, Plot> getPlots(Location l) {
        PlotMapInfo pmi = PlotManager.getMap(l);
        if (pmi == null) {
            return null;
        }
        return pmi.plots;
    }

    public static Plot getPlotById(World w, String id) {
        HashMap<String, Plot> plots = PlotManager.getPlots(w);
        if (plots == null) {
            return null;
        }
        return plots.get(id);
    }

    public static Plot getPlotById(String name, String id) {
        HashMap<String, Plot> plots = PlotManager.getPlots(name);
        if (plots == null) {
            return null;
        }
        return plots.get(id);
    }

    public static Plot getPlotById(Player p, String id) {
        HashMap<String, Plot> plots = PlotManager.getPlots(p);
        if (plots == null) {
            return null;
        }
        return plots.get(id);
    }

    public static Plot getPlotById(Player p) {
        HashMap<String, Plot> plots = PlotManager.getPlots(p);
        String plotid = PlotManager.getPlotId(p.getLocation());
        if (plots == null || plotid == "") {
            return null;
        }
        return plots.get(plotid);
    }

    public static Plot getPlotById(Location l) {
        HashMap<String, Plot> plots = PlotManager.getPlots(l);
        String plotid = PlotManager.getPlotId(l);
        if (plots == null || plotid == "") {
            return null;
        }
        return plots.get(plotid);
    }

    public static Plot getPlotById(Block b, String id) {
        HashMap<String, Plot> plots = PlotManager.getPlots(b);
        if (plots == null) {
            return null;
        }
        return plots.get(id);
    }

    public static Plot getPlotById(Block b) {
        HashMap<String, Plot> plots = PlotManager.getPlots(b);
        String plotid = PlotManager.getPlotId(b.getLocation());
        if (plots == null || plotid == "") {
            return null;
        }
        return plots.get(plotid);
    }

    public static void deleteNextExpired(World w, CommandSender sender) {
        String id2 = null;
        ArrayList<Plot> expiredplots = new ArrayList<Plot>();
        HashMap<String, Plot> plots = PlotManager.getPlots(w);
        String date = PlotMe.getDate();
        Iterator<String> iter = plots.keySet().iterator();
        while (iter.hasNext()) {
            id2 = iter.next();
            Plot plot = plots.get(id2);
            if (plot.protect || plot.finished || plot.expireddate == null || PlotMe.getDate(plot.expireddate).compareTo(date.toString()) >= 0)
                continue;
            expiredplots.add(plot);
        }
        plots = null;
        Collections.sort(expiredplots);
        Plot expiredplot = (Plot) expiredplots.get(0);
        expiredplots = null;
        PlotManager.clear(w, expiredplot);
        id2 = expiredplot.id;
        PlotManager.getPlots(w).remove(id2);
        PlotManager.removeOwnerSign(w, id2);
        PlotManager.removeSellSign(w, id2);
        SqlManager.deletePlot(PlotManager.getIdX(id2), PlotManager.getIdZ(id2), w.getName().toLowerCase());
    }

    public static World getFirstWorld() {
        if (PlotMe.plotmaps != null && PlotMe.plotmaps.keySet() != null && PlotMe.plotmaps.keySet().toArray().length > 0) {
            return Bukkit.getWorld((String) ((String) PlotMe.plotmaps.keySet().toArray()[0]));
        }
        return null;
    }

    public static World getFirstWorld(String player) {
        if (PlotMe.plotmaps != null && PlotMe.plotmaps.keySet() != null && PlotMe.plotmaps.keySet().toArray().length > 0) {
            for (String mapkey : PlotMe.plotmaps.keySet()) {
                for (String id : PlotMe.plotmaps.get((Object) mapkey).plots.keySet()) {
                    if (!PlotMe.plotmaps.get((Object) mapkey).plots.get((Object) id).owner.equalsIgnoreCase(player))
                        continue;
                    return Bukkit.getWorld((String) mapkey);
                }
            }
        }
        return null;
    }

    public static Plot getFirstPlot(String player) {
        if (PlotMe.plotmaps != null && PlotMe.plotmaps.keySet() != null && PlotMe.plotmaps.keySet().toArray().length > 0) {
            for (String mapkey : PlotMe.plotmaps.keySet()) {
                for (String id : PlotMe.plotmaps.get((Object) mapkey).plots.keySet()) {
                    if (!PlotMe.plotmaps.get((Object) mapkey).plots.get((Object) id).owner.equalsIgnoreCase(player))
                        continue;
                    return PlotMe.plotmaps.get((Object) mapkey).plots.get(id);
                }
            }
        }
        return null;
    }

    public static boolean isValidId(String id) {
        String[] coords = id.split(";");
        if (coords.length != 2) {
            return false;
        }
        try {
            Integer.parseInt(coords[0]);
            Integer.parseInt(coords[1]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void regen(World w, Plot plot, CommandSender sender) {
        int bottomX = PlotManager.bottomX(plot.id, w);
        int topX = PlotManager.topX(plot.id, w);
        int bottomZ = PlotManager.bottomZ(plot.id, w);
        int topZ = PlotManager.topZ(plot.id, w);
        int minChunkX = (int) Math.floor((double) bottomX / 16.0);
        int maxChunkX = (int) Math.floor((double) topX / 16.0);
        int minChunkZ = (int) Math.floor((double) bottomZ / 16.0);
        int maxChunkZ = (int) Math.floor((double) topZ / 16.0);
        HashMap<Location, Biome> biomes = new HashMap<Location, Biome>();
        int cx = minChunkX;
        while (cx <= maxChunkX) {
            int xx = cx << 4;
            int cz = minChunkZ;
            while (cz <= maxChunkZ) {
                int z;
                int y;
                int zz = cz << 4;
                BlockState[][][] blocks = new BlockState[16][16][w.getMaxHeight()];
                int x = 0;
                while (x < 16) {
                    z = 0;
                    while (z < 16) {
                        biomes.put(new Location(w, (double) (x + xx), 0.0, (double) (z + zz)), w.getBiome(x + xx, z + zz));
                        y = 0;
                        while (y < w.getMaxHeight()) {
                            boolean ignoreBlockDestruction;
                            Protection protection;
                            Material material;
                            LWC lwc;
                            Block block = w.getBlockAt(x + xx, y, z + zz);
                            blocks[x][z][y] = block.getState();
                            if (PlotMe.usinglwc.booleanValue() && !(ignoreBlockDestruction = Boolean.parseBoolean((lwc = LWC.getInstance()).resolveProtectionConfiguration(material = block.getType(), "ignoreBlockDestruction"))) && (protection = lwc.findProtection(block)) != null) {
                                protection.remove();
                            }
                            ++y;
                        }
                        ++z;
                    }
                    ++x;
                }
                try {
                    w.regenerateChunk(cx, cz);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                x = 0;
                while (x < 16) {
                    z = 0;
                    while (z < 16) {
                        y = 0;
                        while (y < w.getMaxHeight()) {
                            if (x + xx < bottomX || x + xx > topX || z + zz < bottomZ || z + zz > topZ) {
                                Block newblock = w.getBlockAt(x + xx, y, z + zz);
                                BlockState oldblock = blocks[x][z][y];
                                newblock.setTypeIdAndData(oldblock.getTypeId(), oldblock.getRawData(), false);
                                oldblock.update();
                            }
                            ++y;
                        }
                        ++z;
                    }
                    ++x;
                }
                ++cz;
            }
            ++cx;
        }
        for (Location loc : biomes.keySet()) {
            int x = loc.getBlockX();
            int z = loc.getBlockX();
            w.setBiome(x, z, (Biome) biomes.get((Object) loc));
        }
    }

    public static Location getPlotHome(World w, Plot plot) {
        PlotMapInfo pmi = PlotManager.getMap(w);
        if (pmi != null) {
            return new Location(w, (double) (PlotManager.bottomX(plot.id, w) + (PlotManager.topX(plot.id, w) - PlotManager.bottomX(plot.id, w)) / 2), (double) (pmi.RoadHeight + 2), (double) (PlotManager.bottomZ(plot.id, w) - 2));
        }
        return w.getSpawnLocation();
    }

    public static void RemoveLWC(World w, Plot plot) {
        if (PlotMe.usinglwc.booleanValue()) {
            Location bottom = PlotManager.getBottom(w, plot);
            Location top = PlotManager.getTop(w, plot);
            final int x1 = bottom.getBlockX();
            final int y1 = bottom.getBlockY();
            final int z1 = bottom.getBlockZ();
            final int x2 = top.getBlockX();
            final int y2 = top.getBlockY();
            final int z2 = top.getBlockZ();
            String wname = w.getName();
            Bukkit.getScheduler().runTaskAsynchronously((Plugin) PlotMe.self, new Runnable() {

                public void run() {
                    LWC lwc = LWC.getInstance();
                    List<Protection> protections = lwc.getPhysicalDatabase().loadProtections(w.getName(), x1, x2, y1, y2, z1, z2);
                    for (Protection protection : protections) {
                        protection.remove();
                    }
                }
            });
        }
    }

}

