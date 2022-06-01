//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intellectualcrafters.plot.object;

import com.google.common.base.Optional;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.intellectualcrafters.jnbt.CompoundTag;
import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.config.Configuration;
import com.intellectualcrafters.plot.config.Settings.Auto_Clear;
import com.intellectualcrafters.plot.config.Settings.Paths;
import com.intellectualcrafters.plot.config.Settings.Schematics;
import com.intellectualcrafters.plot.config.Settings.Teleport;
import com.intellectualcrafters.plot.database.DBFunc;
import com.intellectualcrafters.plot.flag.Flag;
import com.intellectualcrafters.plot.flag.FlagManager;
import com.intellectualcrafters.plot.flag.Flags;
import com.intellectualcrafters.plot.generator.SquarePlotWorld;
import com.intellectualcrafters.plot.util.*;
import com.intellectualcrafters.plot.util.SchematicHandler.Schematic;
import com.intellectualcrafters.plot.util.block.GlobalBlockQueue;
import com.intellectualcrafters.plot.util.block.LocalBlockQueue;
import com.intellectualcrafters.plot.util.expiry.ExpireManager;
import com.intellectualcrafters.plot.util.expiry.PlotAnalysis;
import com.plotsquared.listener.PlotListener;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Plot {
    /**
     * @deprecated
     */
    @Deprecated
    private static HashSet<Plot> connected_cache;
    private static HashSet<RegionWrapper> regions_cache;
    private final PlotId id;
    /**
     * @deprecated
     */
    @Deprecated
    public UUID owner;
    public boolean countsTowardsMax = true;
    /**
     * @deprecated
     */
    @Deprecated
    public int temp;
    private long timestamp;
    private HashSet<UUID> trusted;
    private HashSet<UUID> members;
    private HashSet<UUID> denied;
    private PlotSettings settings;
    private PlotArea area;
    private ConcurrentHashMap<String, Object> meta;
    private Plot origin;

    public Plot(PlotArea area, PlotId id, UUID owner) {
        this.area = area;
        this.id = id;
        this.owner = owner;
    }

    public Plot(PlotArea area, PlotId id) {
        this.area = area;
        this.id = id;
    }

    public Plot(PlotArea area, PlotId id, UUID owner, int temp) {
        this.area = area;
        this.id = id;
        this.owner = owner;
        this.temp = temp;
    }

    public Plot(PlotId id, UUID owner, HashSet<UUID> trusted, HashSet<UUID> members, HashSet<UUID> denied, String alias, BlockLoc position, Collection<Flag> flags, PlotArea area, boolean[] merged, long timestamp, int temp) {
        this.id = id;
        this.area = area;
        this.owner = owner;
        this.settings = new PlotSettings();
        this.members = members;
        this.trusted = trusted;
        this.denied = denied;
        this.settings.setAlias(alias);
        this.settings.setPosition(position);
        this.settings.setMerged(merged);
        if (flags != null) {
            Iterator var14 = flags.iterator();

            while (var14.hasNext()) {
                Flag flag = (Flag) var14.next();
                this.settings.flags.put(flag, flag);
            }
        }

        this.timestamp = timestamp;
        this.temp = temp;
    }

    public String getWorldName() {
        return this.area.worldname;
    }

    public static Plot fromString(PlotArea defaultArea, String string) {
        String[] split = string.split(";|,");
        if (split.length == 2) {
            if (defaultArea != null) {
                PlotId id = PlotId.fromString(split[0] + ';' + split[1]);
                return id != null ? defaultArea.getPlotAbs(id) : null;
            }
        } else {
            PlotId id;
            PlotArea pa;
            if (split.length == 3) {
                pa = PS.get().getPlotArea(split[0], (String) null);
                if (pa != null) {
                    id = PlotId.fromString(split[1] + ';' + split[2]);
                    return pa.getPlotAbs(id);
                }
            } else if (split.length == 4) {
                pa = PS.get().getPlotArea(split[0], split[1]);
                if (pa != null) {
                    id = PlotId.fromString(split[1] + ';' + split[2]);
                    return pa.getPlotAbs(id);
                }
            }
        }

        return null;
    }

    public static Plot getPlot(Location location) {
        PlotArea pa = PS.get().getPlotAreaAbs(location);
        return pa != null ? pa.getPlot(location) : null;
    }

    public void setMeta(String key, Object value) {
        if (this.meta == null) {
            this.meta = new ConcurrentHashMap();
        }

        this.meta.put(key, value);
    }

    public Object getMeta(String key) {
        return this.meta != null ? this.meta.get(key) : null;
    }

    public void deleteMeta(String key) {
        if (this.meta != null) {
            this.meta.remove(key);
        }

    }

    public PlotCluster getCluster() {
        return this.getArea().getCluster(this.id);
    }

    public List<PlotPlayer> getPlayersInPlot() {
        ArrayList<PlotPlayer> players = new ArrayList();
        Iterator var2 = UUIDHandler.getPlayers().entrySet().iterator();

        while (var2.hasNext()) {
            Entry<String, PlotPlayer> entry = (Entry) var2.next();
            PlotPlayer pp = (PlotPlayer) entry.getValue();
            if (this.equals(pp.getCurrentPlot())) {
                players.add(pp);
            }
        }

        return players;
    }

    public boolean hasOwner() {
        return this.owner != null;
    }

    public boolean isOwner(UUID uuid) {
        if (uuid.equals(this.owner)) {
            return true;
        } else if (!this.isMerged()) {
            return false;
        } else {
            Set<Plot> connected = this.getConnectedPlots();
            Iterator var3 = connected.iterator();

            Plot current;
            do {
                if (!var3.hasNext()) {
                    return false;
                }

                current = (Plot) var3.next();
            } while (!uuid.equals(current.owner));

            return true;
        }
    }

    public boolean isOwnerAbs(UUID uuid) {
        return uuid.equals(this.owner);
    }

    public Set<UUID> getOwners() {
        if (this.owner == null) {
            return ImmutableSet.of();
        } else if (!this.isMerged()) {
            return ImmutableSet.of(this.owner);
        } else {
            Set<Plot> plots = this.getConnectedPlots();
            Plot[] array = (Plot[]) plots.toArray(new Plot[plots.size()]);
            Builder<UUID> owners = ImmutableSet.builder();
            UUID last = this.owner;
            owners.add(this.owner);
            Plot[] var5 = array;
            int var6 = array.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Plot current = var5[var7];
                if (last == null || current.owner.getMostSignificantBits() != last.getMostSignificantBits()) {
                    owners.add(current.owner);
                    last = current.owner;
                }
            }

            return owners.build();
        }
    }

    public boolean isAdded(UUID uuid) {
        if (this.owner != null && !this.getDenied().contains(uuid)) {
            if (this.isOwner(uuid)) {
                return true;
            } else if (this.getMembers().contains(uuid)) {
                return this.isOnline();
            } else if (!this.getTrusted().contains(uuid) && !this.getTrusted().contains(DBFunc.everyone)) {
                return this.getMembers().contains(DBFunc.everyone) ? this.isOnline() : false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean isDenied(UUID uuid) {
        return this.denied != null && (this.denied.contains(DBFunc.everyone) && !this.isAdded(uuid) || !this.isAdded(uuid) && this.denied.contains(uuid));
    }

    public PlotId getId() {
        return this.id;
    }

    public PlotArea getArea() {
        return this.area;
    }

    public void setArea(PlotArea area) {
        if (this.getArea() != area) {
            if (this.getArea() != null) {
                this.area.removePlot(this.id);
            }

            this.area = area;
            area.addPlot(this);
        }
    }

    public PlotManager getManager() {
        return this.area.getPlotManager();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public PlotSettings getSettings() {
        if (this.settings == null) {
            this.settings = new PlotSettings();
        }

        return this.settings;
    }

    public boolean isBasePlot() {
        return !this.isMerged() || this.equals(this.getBasePlot(false));
    }

    public Plot getBasePlot(boolean recalculate) {
        if (this.origin != null && !recalculate) {
            return this.equals(this.origin) ? this : this.origin.getBasePlot(false);
        } else if (!this.isMerged()) {
            this.origin = this;
            return this.origin;
        } else {
            this.origin = this;
            PlotId min = this.id;
            Iterator var3 = this.getConnectedPlots().iterator();

            while (true) {
                Plot plot;
                do {
                    if (!var3.hasNext()) {
                        for (var3 = this.getConnectedPlots().iterator(); var3.hasNext(); plot.origin = this.origin) {
                            plot = (Plot) var3.next();
                        }

                        return this.origin;
                    }

                    plot = (Plot) var3.next();
                } while (plot.id.y >= min.y && (plot.id.y != min.y || plot.id.x >= min.x));

                this.origin = plot;
                min = plot.id;
            }
        }
    }

    public boolean isMerged() {
        return this.getSettings().getMerged(0) || this.getSettings().getMerged(2) || this.getSettings().getMerged(1) || this.getSettings().getMerged(3);
    }

    public long getTimestamp() {
        if (this.timestamp == 0L) {
            this.timestamp = System.currentTimeMillis();
        }

        return this.timestamp;
    }

    public boolean getMerged(int direction) {
        if (this.settings == null) {
            return false;
        } else {
            int i;
            switch (direction) {
                case 0:
                case 1:
                case 2:
                case 3:
                    return this.getSettings().getMerged(direction);
                case 4:
                case 5:
                case 6:
                    i = direction - 4;
                    int i2 = direction - 3;
                    return this.getSettings().getMerged(i2) && this.getSettings().getMerged(i) && this.area.getPlotAbs(this.id.getRelative(i)).getMerged(i2) && this.area.getPlotAbs(this.id.getRelative(i2)).getMerged(i);
                case 7:
                    i = direction - 4;
                    i2 = 0;
                    if (this.getSettings().getMerged(i2) && this.getSettings().getMerged(i) && this.area.getPlotAbs(this.id.getRelative(i)).getMerged(i2) && this.area.getPlotAbs(this.id.getRelative(i2)).getMerged(i)) {
                        return true;
                    }

                    return false;
                default:
                    return false;
            }
        }
    }

    public HashSet<UUID> getDenied() {
        if (this.denied == null) {
            this.denied = new HashSet();
        }

        return this.denied;
    }

    public void setDenied(Set<UUID> uuids) {
        boolean larger = uuids.size() > this.getDenied().size();
        HashSet<UUID> intersection = new HashSet((Collection) (larger ? this.getDenied() : uuids));
        intersection.retainAll((Collection) (larger ? uuids : this.getDenied()));
        uuids.removeAll(intersection);
        HashSet<UUID> toRemove = new HashSet(this.getDenied());
        toRemove.removeAll(intersection);
        Iterator var5 = toRemove.iterator();

        UUID uuid;
        while (var5.hasNext()) {
            uuid = (UUID) var5.next();
            this.removeDenied(uuid);
        }

        var5 = uuids.iterator();

        while (var5.hasNext()) {
            uuid = (UUID) var5.next();
            this.addDenied(uuid);
        }

    }

    public HashSet<UUID> getTrusted() {
        if (this.trusted == null) {
            this.trusted = new HashSet();
        }

        return this.trusted;
    }

    public void setTrusted(Set<UUID> uuids) {
        boolean larger = uuids.size() > this.getTrusted().size();
        HashSet<UUID> intersection = new HashSet((Collection) (larger ? this.getTrusted() : uuids));
        intersection.retainAll((Collection) (larger ? uuids : this.getTrusted()));
        uuids.removeAll(intersection);
        HashSet<UUID> toRemove = new HashSet(this.getTrusted());
        toRemove.removeAll(intersection);
        Iterator var5 = toRemove.iterator();

        UUID uuid;
        while (var5.hasNext()) {
            uuid = (UUID) var5.next();
            this.removeTrusted(uuid);
        }

        var5 = uuids.iterator();

        while (var5.hasNext()) {
            uuid = (UUID) var5.next();
            this.addTrusted(uuid);
        }

    }

    public HashSet<UUID> getMembers() {
        if (this.members == null) {
            this.members = new HashSet();
        }

        return this.members;
    }

    public void setMembers(Set<UUID> uuids) {
        boolean larger = uuids.size() > this.getMembers().size();
        HashSet<UUID> intersection = new HashSet((Collection) (larger ? this.getMembers() : uuids));
        intersection.retainAll((Collection) (larger ? uuids : this.getMembers()));
        uuids.removeAll(intersection);
        HashSet<UUID> toRemove = new HashSet(this.getMembers());
        toRemove.removeAll(intersection);
        Iterator var5 = toRemove.iterator();

        UUID uuid;
        while (var5.hasNext()) {
            uuid = (UUID) var5.next();
            this.removeMember(uuid);
        }

        var5 = uuids.iterator();

        while (var5.hasNext()) {
            uuid = (UUID) var5.next();
            this.addMember(uuid);
        }

    }

    public void addDenied(UUID uuid) {
        Iterator var2 = this.getConnectedPlots().iterator();

        while (var2.hasNext()) {
            Plot current = (Plot) var2.next();
            if (current.getDenied().add(uuid)) {
                DBFunc.setDenied(current, uuid);
            }
        }

    }

    public void addTrusted(UUID uuid) {
        Iterator var2 = this.getConnectedPlots().iterator();

        while (var2.hasNext()) {
            Plot current = (Plot) var2.next();
            if (current.getTrusted().add(uuid)) {
                DBFunc.setTrusted(current, uuid);
            }
        }

    }

    public void addMember(UUID uuid) {
        Iterator var2 = this.getConnectedPlots().iterator();

        while (var2.hasNext()) {
            Plot current = (Plot) var2.next();
            if (current.getMembers().add(uuid)) {
                DBFunc.setMember(current, uuid);
            }
        }

    }

    public void setOwner(UUID owner) {
        if (!this.hasOwner()) {
            this.owner = owner;
            this.create();
        } else if (!this.isMerged()) {
            if (!this.owner.equals(owner)) {
                this.owner = owner;
                DBFunc.setOwner(this, owner);
            }

        } else {
            Iterator var2 = this.getConnectedPlots().iterator();

            while (var2.hasNext()) {
                Plot current = (Plot) var2.next();
                if (!owner.equals(current.owner)) {
                    current.owner = owner;
                    DBFunc.setOwner(current, owner);
                }
            }

        }
    }

    public boolean setOwner(UUID owner, PlotPlayer initiator) {
        boolean result = EventUtil.manager.callOwnerChange(initiator, this, owner, this.hasOwner() ? this.owner : null, this.hasOwner());
        if (!result) {
            return false;
        } else if (!this.hasOwner()) {
            this.owner = owner;
            this.create();
            return true;
        } else if (!this.isMerged()) {
            if (!this.owner.equals(owner)) {
                this.owner = owner;
                DBFunc.setOwner(this, owner);
            }

            return true;
        } else {
            Iterator var4 = this.getConnectedPlots().iterator();

            while (var4.hasNext()) {
                Plot current = (Plot) var4.next();
                if (!owner.equals(current.owner)) {
                    current.owner = owner;
                    DBFunc.setOwner(current, owner);
                }
            }

            return true;
        }
    }

    public void clear(Runnable whenDone) {
        this.clear(false, false, whenDone);
    }

    public boolean clear(boolean checkRunning, final boolean isDelete, final Runnable whenDone) {
        if (checkRunning && this.getRunning() != 0) {
            return false;
        } else {
            if (isDelete) {
                if (!EventUtil.manager.callDelete(this)) {
                    return false;
                }
            } else if (!EventUtil.manager.callClear(this)) {
                return false;
            }

            final HashSet<RegionWrapper> regions = this.getRegions();
            final Set<Plot> plots = this.getConnectedPlots();
            final ArrayDeque<Plot> queue = new ArrayDeque(plots);
            if (isDelete) {
                this.removeSign();
            }

            this.unlinkPlot(true, !isDelete);
            final PlotManager manager = this.area.getPlotManager();
            Runnable run = new Runnable() {
                public void run() {
                    if (!queue.isEmpty()) {
                        Plot currentx = (Plot) queue.poll();
                        if (Plot.this.area.TERRAIN != 0) {
                            ChunkManager.manager.regenerateRegion(currentx.getBottomAbs(), currentx.getTopAbs(), false, this);
                        } else {
                            manager.clearPlot(Plot.this.area, currentx, this);
                        }
                    } else {
                        Runnable run = new Runnable() {
                            public void run() {
                                Iterator var1 = regions.iterator();

                                while (var1.hasNext()) {
                                    RegionWrapper region = (RegionWrapper) var1.next();
                                    Location[] corners = region.getCorners(Plot.this.getWorldName());
                                    ChunkManager.manager.clearAllEntities(corners[0], corners[1]);
                                }

                                TaskManager.runTask(whenDone);
                            }
                        };
                        Iterator var2 = plots.iterator();

                        while (true) {
                            while (var2.hasNext()) {
                                Plot current = (Plot) var2.next();
                                if (!isDelete && current.owner != null) {
                                    manager.claimPlot(Plot.this.area, current);
                                } else {
                                    manager.unclaimPlot(Plot.this.area, current, (Runnable) null);
                                }
                            }

                            GlobalBlockQueue.IMP.addTask(run);
                            return;
                        }
                    }
                }
            };
            run.run();
            return true;
        }
    }

    public void setBiome(final String biome, final Runnable whenDone) {
        final ArrayDeque<RegionWrapper> regions = new ArrayDeque(this.getRegions());
        final int extendBiome;
        if (this.area instanceof SquarePlotWorld) {
            extendBiome = ((SquarePlotWorld) this.area).ROAD_WIDTH > 0 ? 1 : 0;
        } else {
            extendBiome = 0;
        }

        Runnable run = new Runnable() {
            public void run() {
                if (regions.isEmpty()) {
                    Plot.this.refreshChunks();
                    TaskManager.runTask(whenDone);
                } else {
                    RegionWrapper region = (RegionWrapper) regions.poll();
                    Location pos1 = new Location(Plot.this.getWorldName(), region.minX - extendBiome, region.minY, region.minZ - extendBiome);
                    Location pos2 = new Location(Plot.this.getWorldName(), region.maxX + extendBiome, region.maxY, region.maxZ + extendBiome);
                    ChunkManager.chunkTask(pos1, pos2, new RunnableVal<int[]>() {
                        public void run(int[] value) {
                            ChunkLoc loc = new ChunkLoc(value[0], value[1]);
                            ChunkManager.manager.loadChunk(Plot.this.getWorldName(), loc, false);
                            MainUtil.setBiome(Plot.this.getWorldName(), value[2], value[3], value[4], value[5], biome);
                            ChunkManager.manager.unloadChunk(Plot.this.getWorldName(), loc, true, true);
                        }
                    }, this, 5);
                }
            }
        };
        run.run();
    }

    public boolean unlinkPlot(boolean createRoad, boolean createSign) {
        if (!this.isMerged()) {
            return false;
        } else {
            final Set<Plot> plots = this.getConnectedPlots();
            ArrayList<PlotId> ids = new ArrayList(plots.size());
            Iterator var5 = plots.iterator();

            while (var5.hasNext()) {
                Plot current = (Plot) var5.next();
                current.setHome((BlockLoc) null);
                ids.add(current.getId());
            }

            boolean result = EventUtil.manager.callUnlink(this.area, ids);
            if (!result) {
                return false;
            } else {
                this.clearRatings();
                if (createSign) {
                    this.removeSign();
                }

                PlotManager manager = this.area.getPlotManager();
                if (createRoad) {
                    manager.startPlotUnlink(this.area, ids);
                }

                Iterator var7;
                Plot current;
                if (this.area.TERRAIN != 3 && createRoad) {
                    var7 = plots.iterator();

                    while (var7.hasNext()) {
                        current = (Plot) var7.next();
                        if (current.getMerged(1)) {
                            manager.createRoadEast(current.area, current);
                            if (current.getMerged(2)) {
                                manager.createRoadSouth(current.area, current);
                                if (current.getMerged(5)) {
                                    manager.createRoadSouthEast(current.area, current);
                                }
                            }
                        } else if (current.getMerged(2)) {
                            manager.createRoadSouth(current.area, current);
                        }
                    }
                }

                var7 = plots.iterator();

                while (var7.hasNext()) {
                    current = (Plot) var7.next();
                    boolean[] merged = new boolean[]{false, false, false, false};
                    current.setMerged(merged);
                }

                if (createSign) {
                    GlobalBlockQueue.IMP.addTask(new Runnable() {
                        public void run() {
                            Iterator var1 = plots.iterator();

                            while (var1.hasNext()) {
                                Plot current = (Plot) var1.next();
                                current.setSign(MainUtil.getName(current.owner));
                            }

                        }
                    });
                }

                if (createRoad) {
                    manager.finishPlotUnlink(this.area, ids);
                }

                return true;
            }
        }
    }

    public void setSign(final String name) {
        if (this.isLoaded()) {
            if (!PS.get().isMainThread(Thread.currentThread())) {
                TaskManager.runTask(new Runnable() {
                    public void run() {
                        Plot.this.setSign(name);
                    }
                });
            } else {
                PlotManager manager = this.area.getPlotManager();
                if (this.area.ALLOW_SIGNS) {
                    Location loc = manager.getSignLoc(this.area, this);
                    String id = this.id.x + ";" + this.id.y;
                    String[] lines = new String[]{C.OWNER_SIGN_LINE_1.formatted().replaceAll("%id%", id), C.OWNER_SIGN_LINE_2.formatted().replaceAll("%id%", id).replaceAll("%plr%", name), C.OWNER_SIGN_LINE_3.formatted().replaceAll("%id%", id).replaceAll("%plr%", name), C.OWNER_SIGN_LINE_4.formatted().replaceAll("%id%", id).replaceAll("%plr%", name)};
                    WorldUtil.IMP.setSign(this.getWorldName(), loc.getX(), loc.getY(), loc.getZ(), lines);
                }

            }
        }
    }

    protected boolean isLoaded() {
        return WorldUtil.IMP.isWorld(this.getWorldName());
    }

    public PlotAnalysis getComplexity(Auto_Clear settings) {
        return PlotAnalysis.getAnalysis(this, settings);
    }

    public void analyze(RunnableVal<PlotAnalysis> whenDone) {
        PlotAnalysis.analyzePlot(this, whenDone);
    }

    public <V> boolean setFlag(Flag<V> flag, Object value) {
        if (flag == Flags.KEEP && ExpireManager.IMP != null) {
            ExpireManager.IMP.updateExpired(this);
        }

        return FlagManager.addPlotFlag(this, flag, value);
    }

    public boolean removeFlag(Flag<?> flag) {
        return FlagManager.removePlotFlag(this, flag);
    }

    public <V> Optional<V> getFlag(Flag<V> key) {
        return FlagManager.getPlotFlag(this, key);
    }

    public <V> V getFlag(Flag<V> key, V defaultValue) {
        V value = FlagManager.getPlotFlagRaw(this, key);
        return value == null ? defaultValue : value;
    }

    public boolean deletePlot(final Runnable whenDone) {
        if (!this.hasOwner()) {
            return false;
        } else {
            final Set<Plot> plots = this.getConnectedPlots();
            this.clear(false, true, new Runnable() {
                public void run() {
                    Iterator var1 = plots.iterator();

                    while (var1.hasNext()) {
                        Plot current = (Plot) var1.next();
                        current.unclaim();
                    }

                    TaskManager.runTask(whenDone);
                }
            });
            return true;
        }
    }

    public int[] countEntities() {
        int[] count = new int[6];

        int[] result;
        for (Iterator var2 = this.getConnectedPlots().iterator(); var2.hasNext(); count[5] += result[5]) {
            Plot current = (Plot) var2.next();
            result = ChunkManager.manager.countEntities(current);
            count[0] += result[0];
            count[1] += result[1];
            count[2] += result[2];
            count[3] += result[3];
            count[4] += result[4];
        }

        return count;
    }

    public int addRunning() {
        int value = this.getRunning();
        Iterator var2 = this.getConnectedPlots().iterator();

        while (var2.hasNext()) {
            Plot plot = (Plot) var2.next();
            plot.setMeta("running", value + 1);
        }

        return value;
    }

    public int removeRunning() {
        int value = this.getRunning();
        Iterator var2;
        Plot plot;
        if (value < 2) {
            var2 = this.getConnectedPlots().iterator();

            while (var2.hasNext()) {
                plot = (Plot) var2.next();
                plot.deleteMeta("running");
            }
        } else {
            var2 = this.getConnectedPlots().iterator();

            while (var2.hasNext()) {
                plot = (Plot) var2.next();
                plot.setMeta("running", value - 1);
            }
        }

        return value;
    }

    public int getRunning() {
        Integer value = (Integer) this.getMeta("running");
        return value == null ? 0 : value;
    }

    public boolean unclaim() {
        if (this.owner == null) {
            return false;
        } else {
            Iterator var1 = this.getConnectedPlots().iterator();

            while (var1.hasNext()) {
                Plot current = (Plot) var1.next();
                List<PlotPlayer> players = current.getPlayersInPlot();
                Iterator var4 = players.iterator();

                PlotPlayer pp;
                while (var4.hasNext()) {
                    pp = (PlotPlayer) var4.next();
                    PlotListener.plotExit(pp, current);
                }

                this.getArea().removePlot(this.getId());
                DBFunc.delete(current);
                current.owner = null;
                current.settings = null;
                var4 = players.iterator();

                while (var4.hasNext()) {
                    pp = (PlotPlayer) var4.next();
                    PlotListener.plotEntry(pp, current);
                }
            }

            return true;
        }
    }

    public boolean unlink() {
        return this.unlinkPlot(true, true);
    }

    public Location getCenter() {
        Location[] corners = this.getCorners();
        Location top = corners[0];
        Location bot = corners[1];
        Location loc = new Location(this.getWorldName(), MathMan.average(bot.getX(), top.getX()), MathMan.average(bot.getY(), top.getY()), MathMan.average(bot.getZ(), top.getZ()));
        if (!this.isLoaded()) {
            return loc;
        } else {
            int y = this.isLoaded() ? WorldUtil.IMP.getHighestBlock(this.getWorldName(), loc.getX(), loc.getZ()) : 64;
            if (this.area.ALLOW_SIGNS) {
                y = Math.max(y, this.getManager().getSignLoc(this.area, this).getY());
            }

            loc.setY(1 + y);
            return loc;
        }
    }

    public Location getSide() {
        RegionWrapper largest = this.getLargestRegion();
        int x = (largest.maxX >> 1) - (largest.minX >> 1) + largest.minX;
        int z = largest.minZ - 1;
        PlotManager manager = this.getManager();
        int y = this.isLoaded() ? WorldUtil.IMP.getHighestBlock(this.getWorldName(), x, z) : 64;
        if (this.area.ALLOW_SIGNS && (y <= 0 || y >= 255)) {
            y = Math.max(y, manager.getSignLoc(this.area, this).getY() - 1);
        }

        return new Location(this.getWorldName(), x, y + 1, z);
    }

    public Location getHome() {
        BlockLoc home = this.getPosition();
        if (home == null || home.x == 0 && home.z == 0) {
            return this.getDefaultHome(true);
        } else {
            Location bot = this.getBottomAbs();
            Location loc = new Location(bot.getWorld(), bot.getX() + home.x, bot.getY() + home.y, bot.getZ() + home.z, home.yaw, home.pitch);
            if (!this.isLoaded()) {
                return loc;
            } else {
                if (WorldUtil.IMP.getBlock(loc).id != 0) {
                    loc.setY(Math.max(1 + WorldUtil.IMP.getHighestBlock(this.getWorldName(), loc.getX(), loc.getZ()), bot.getY()));
                }

                return loc;
            }
        }
    }

    public void setHome(BlockLoc location) {
        Plot plot = this.getBasePlot(false);
        if (location == null || !(new BlockLoc(0, 0, 0)).equals(location)) {
            plot.getSettings().setPosition(location);
            if (location != null) {
                DBFunc.setPosition(plot, plot.getSettings().getPosition().toString());
            } else {
                DBFunc.setPosition(plot, (String) null);
            }
        }
    }

    public Location getDefaultHome() {
        return this.getDefaultHome(false);
    }

    public Location getDefaultHome(boolean member) {
        Plot plot = this.getBasePlot(false);
        PlotLoc loc = member ? this.area.DEFAULT_HOME : this.area.NONMEMBER_HOME;
        if (loc == null) {
            return plot.getSide();
        } else {
            int x;
            int z;
            if (loc.x == 2147483647 && loc.z == 2147483647) {
                RegionWrapper largest = plot.getLargestRegion();
                x = (largest.maxX >> 1) - (largest.minX >> 1) + largest.minX;
                z = (largest.maxZ >> 1) - (largest.minZ >> 1) + largest.minZ;
            } else {
                Location bot = plot.getBottomAbs();
                x = bot.getX() + loc.x;
                z = bot.getZ() + loc.z;
            }

            int y = loc.y < 1 ? (this.isLoaded() ? WorldUtil.IMP.getHighestBlock(plot.getWorldName(), x, z) + 1 : 63) : loc.y;
            return new Location(plot.getWorldName(), x, y, z);
        }
    }

    public double getVolume() {
        double count = 0.0D;

        RegionWrapper region;
        for (Iterator var3 = this.getRegions().iterator(); var3.hasNext(); count += ((double) region.maxX - (double) region.minX + 1.0D) * ((double) region.maxZ - (double) region.minZ + 1.0D) * 256.0D) {
            region = (RegionWrapper) var3.next();
        }

        return count;
    }

    public double getAverageRating() {
        double sum = 0.0D;
        Collection<Rating> ratings = this.getRatings().values();

        Rating rating;
        for (Iterator var4 = ratings.iterator(); var4.hasNext(); sum += rating.getAverageRating()) {
            rating = (Rating) var4.next();
        }

        return sum / (double) ratings.size();
    }

    public boolean addRating(UUID uuid, Rating rating) {
        Plot base = this.getBasePlot(false);
        PlotSettings baseSettings = base.getSettings();
        if (baseSettings.getRatings().containsKey(uuid)) {
            return false;
        } else {
            int aggregate = rating.getAggregate();
            baseSettings.getRatings().put(uuid, aggregate);
            DBFunc.setRating(base, uuid, aggregate);
            return true;
        }
    }

    public void clearRatings() {
        Plot base = this.getBasePlot(false);
        PlotSettings baseSettings = base.getSettings();
        if (baseSettings.ratings != null && !baseSettings.getRatings().isEmpty()) {
            DBFunc.deleteRatings(base);
            baseSettings.ratings = null;
        }

    }

    public HashMap<UUID, Rating> getRatings() {
        Plot base = this.getBasePlot(false);
        HashMap<UUID, Rating> map = new HashMap();
        if (!base.hasRatings()) {
            return map;
        } else {
            Iterator var3 = base.getSettings().getRatings().entrySet().iterator();

            while (var3.hasNext()) {
                Entry<UUID, Integer> entry = (Entry) var3.next();
                map.put(entry.getKey(), new Rating((Integer) entry.getValue()));
            }

            return map;
        }
    }

    public boolean hasRatings() {
        Plot base = this.getBasePlot(false);
        return base.settings != null && base.settings.ratings != null;
    }

    public void refreshChunks() {
        LocalBlockQueue queue = GlobalBlockQueue.IMP.getNewQueue(this.getWorldName(), false);
        HashSet<ChunkLoc> chunks = new HashSet();
        Iterator var3 = this.getRegions().iterator();

        while (var3.hasNext()) {
            RegionWrapper region = (RegionWrapper) var3.next();

            for (int x = region.minX >> 4; x <= region.maxX >> 4; ++x) {
                for (int z = region.minZ >> 4; z <= region.maxZ >> 4; ++z) {
                    if (chunks.add(new ChunkLoc(x, z))) {
                        queue.refreshChunk(x, z);
                    }
                }
            }
        }

    }

    public void removeSign() {
        PlotManager manager = this.area.getPlotManager();
        if (this.area.ALLOW_SIGNS) {
            Location loc = manager.getSignLoc(this.area, this);
            LocalBlockQueue queue = GlobalBlockQueue.IMP.getNewQueue(this.getWorldName(), false);
            queue.setBlock(loc.getX(), loc.getY(), loc.getZ(), 0);
            queue.flush();
        }
    }

    public void setSign() {
        if (this.owner == null) {
            this.setSign("unknown");
        } else {
            this.setSign(UUIDHandler.getName(this.owner));
        }
    }

    public boolean create() {
        return this.create(this.owner, true);
    }

    public boolean claim(PlotPlayer player, boolean teleport, String schematic) {
        return !this.canClaim(player) ? false : this.claim(player, teleport, schematic, true);
    }

    public boolean claim(final PlotPlayer player, boolean teleport, String schematic, boolean updateDB) {
        boolean result = EventUtil.manager.callClaim(player, this, false);
        if (updateDB) {
            if (!result || !this.create(player.getUUID(), true)) {
                return false;
            }
        } else {
            this.area.addPlot(this);
        }

        this.setSign(player.getName());
        MainUtil.sendMessage(player, C.CLAIMED, new String[0]);
        if (teleport) {
            this.teleportPlayer(player);
        }

        PlotArea plotworld = this.getArea();
        if (plotworld.SCHEMATIC_ON_CLAIM) {
            Schematic sch;
            if (schematic != null && !schematic.isEmpty()) {
                sch = SchematicHandler.manager.getSchematic(schematic);
                if (sch == null) {
                    sch = SchematicHandler.manager.getSchematic(plotworld.SCHEMATIC_FILE);
                }
            } else {
                sch = SchematicHandler.manager.getSchematic(plotworld.SCHEMATIC_FILE);
            }

            SchematicHandler.manager.paste(sch, this, 0, 0, 0, Schematics.PASTE_ON_TOP, new RunnableVal<Boolean>() {
                public void run(Boolean value) {
                    if (value) {
                        MainUtil.sendMessage(player, C.SCHEMATIC_PASTE_SUCCESS, new String[0]);
                    } else {
                        MainUtil.sendMessage(player, C.SCHEMATIC_PASTE_FAILED, new String[0]);
                    }

                }
            });
        }

        plotworld.getPlotManager().claimPlot(plotworld, this);
        return true;
    }

    public boolean create(final UUID uuid, final boolean notify) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        } else {
            this.owner = uuid;
            Plot existing = this.area.getOwnedPlotAbs(this.id);
            if (existing != null) {
                throw new IllegalStateException("Plot already exists!");
            } else {
                if (notify) {
                    Integer meta = (Integer) this.area.getMeta("worldBorder");
                    if (meta != null) {
                        this.updateWorldBorder();
                    }
                }

                connected_cache = null;
                regions_cache = null;
                this.getTrusted().clear();
                this.getMembers().clear();
                this.getDenied().clear();
                this.settings = new PlotSettings();
                if (this.area.addPlot(this)) {
                    DBFunc.createPlotAndSettings(this, new Runnable() {
                        public void run() {
                            PlotArea plotworld = Plot.this.area;
                            if (notify && plotworld.AUTO_MERGE) {
                                Plot.this.autoMerge(-1, 2147483647, uuid, true);
                            }

                        }
                    });
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public boolean setComponent(String component, String blocks) {
        PlotBlock[] parsed = (PlotBlock[]) Configuration.BLOCKLIST.parseString(blocks);
        return parsed != null && parsed.length != 0 && this.setComponent(component, parsed);
    }

    public String getBiome() {
        Location loc = this.getCenter();
        return WorldUtil.IMP.getBiome(loc.getWorld(), loc.getX(), loc.getZ());
    }

    public Location getTopAbs() {
        Location top = this.area.getPlotManager().getPlotTopLocAbs(this.area, this.id);
        top.setWorld(this.getWorldName());
        return top;
    }

    public Location getBottomAbs() {
        Location loc = this.area.getPlotManager().getPlotBottomLocAbs(this.area, this.id);
        loc.setWorld(this.getWorldName());
        return loc;
    }

    public boolean swapData(Plot plot, Runnable whenDone) {
        if (this.owner == null) {
            if (plot != null && plot.hasOwner()) {
                plot.moveData(this, whenDone);
                return true;
            } else {
                return false;
            }
        } else if (plot != null && plot.owner != null) {
            PlotId temp = new PlotId(this.getId().x, this.getId().y);
            this.getId().x = plot.getId().x;
            this.getId().y = plot.getId().y;
            plot.getId().x = temp.x;
            plot.getId().y = temp.y;
            this.area.removePlot(this.getId());
            plot.area.removePlot(plot.getId());
            this.getId().recalculateHash();
            plot.getId().recalculateHash();
            this.area.addPlotAbs(this);
            plot.area.addPlotAbs(plot);
            DBFunc.swapPlots(plot, this);
            TaskManager.runTaskLater(whenDone, 1);
            return true;
        } else {
            this.moveData(plot, whenDone);
            return true;
        }
    }

    public boolean moveData(Plot plot, Runnable whenDone) {
        if (this.owner == null) {
            PS.debug(plot + " is unowned (single)");
            TaskManager.runTask(whenDone);
            return false;
        } else if (plot.hasOwner()) {
            PS.debug(plot + " is unowned (multi)");
            TaskManager.runTask(whenDone);
            return false;
        } else {
            this.area.removePlot(this.id);
            this.getId().x = plot.getId().x;
            this.getId().y = plot.getId().y;
            this.getId().recalculateHash();
            this.area.addPlotAbs(this);
            DBFunc.movePlot(this, plot);
            TaskManager.runTaskLater(whenDone, 1);
            return true;
        }
    }

    public Location getExtendedTopAbs() {
        Location top = this.getTopAbs();
        if (!this.isMerged()) {
            return top;
        } else {
            if (this.getMerged(2)) {
                top.setZ(this.getRelative(2).getBottomAbs().getZ() - 1);
            }

            if (this.getMerged(1)) {
                top.setX(this.getRelative(1).getBottomAbs().getX() - 1);
            }

            return top;
        }
    }

    public Location getExtendedBottomAbs() {
        Location bot = this.getBottomAbs();
        if (!this.isMerged()) {
            return bot;
        } else {
            if (this.getMerged(0)) {
                bot.setZ(this.getRelative(0).getTopAbs().getZ() + 1);
            }

            if (this.getMerged(3)) {
                bot.setX(this.getRelative(3).getTopAbs().getX() + 1);
            }

            return bot;
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Location[] getCorners() {
        return !this.isMerged() ? new Location[]{this.getBottomAbs(), this.getTopAbs()} : MainUtil.getCorners(this.getWorldName(), this.getRegions());
    }

    public void removeRoadEast() {
        if (this.area.TYPE != 0 && this.area.TERRAIN > 1) {
            if (this.area.TERRAIN == 3) {
                return;
            }

            Plot other = this.getRelative(1);
            Location bot = other.getBottomAbs();
            Location top = this.getTopAbs();
            Location pos1 = new Location(this.getWorldName(), top.getX(), 0, bot.getZ());
            Location pos2 = new Location(this.getWorldName(), bot.getX(), 256, top.getZ());
            ChunkManager.manager.regenerateRegion(pos1, pos2, true, (Runnable) null);
        } else {
            this.area.getPlotManager().removeRoadEast(this.area, this);
        }

    }

    /**
     * @deprecated
     */
    @Deprecated
    public PlotId[] getCornerIds() {
        if (!this.isMerged()) {
            return new PlotId[]{this.getId(), this.getId()};
        } else {
            PlotId min = new PlotId(this.getId().x, this.getId().y);
            PlotId max = new PlotId(this.getId().x, this.getId().y);
            Iterator var3 = this.getConnectedPlots().iterator();

            while (var3.hasNext()) {
                Plot current = (Plot) var3.next();
                if (current.getId().x < min.x) {
                    min.x = current.getId().x;
                } else if (current.getId().x > max.x) {
                    max.x = current.getId().x;
                }

                if (current.getId().y < min.y) {
                    min.y = current.getId().y;
                } else if (current.getId().y > max.y) {
                    max.y = current.getId().y;
                }
            }

            return new PlotId[]{min, max};
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Location getBottom() {
        return this.getCorners()[0];
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Location getTop() {
        return this.getCorners()[1];
    }

    public boolean swap(Plot destination, Runnable whenDone) {
        return this.move(destination, whenDone, true);
    }

    public boolean move(Plot destination, Runnable whenDone) {
        return this.move(destination, whenDone, false);
    }

    public String toString() {
        return this.settings != null && this.settings.getAlias().length() > 1 ? this.settings.getAlias() : this.area + ";" + this.id.x + ";" + this.id.y;
    }

    public boolean removeDenied(UUID uuid) {
        if (uuid == DBFunc.everyone && !this.denied.contains(uuid)) {
            boolean result = false;

            UUID other;
            for (Iterator var3 = (new HashSet(this.getDenied())).iterator(); var3.hasNext(); result = this.rmvDenied(other) || result) {
                other = (UUID) var3.next();
            }

            return result;
        } else {
            return this.rmvDenied(uuid);
        }
    }

    private boolean rmvDenied(UUID uuid) {
        Iterator var2 = this.getConnectedPlots().iterator();

        while (var2.hasNext()) {
            Plot current = (Plot) var2.next();
            if (!current.getDenied().remove(uuid)) {
                return false;
            }

            DBFunc.removeDenied(current, uuid);
        }

        return true;
    }

    public boolean removeTrusted(UUID uuid) {
        if (uuid == DBFunc.everyone && !this.trusted.contains(uuid)) {
            boolean result = false;

            UUID other;
            for (Iterator var3 = (new HashSet(this.getTrusted())).iterator(); var3.hasNext(); result = this.rmvTrusted(other) || result) {
                other = (UUID) var3.next();
            }

            return result;
        } else {
            return this.rmvTrusted(uuid);
        }
    }

    private boolean rmvTrusted(UUID uuid) {
        Iterator var2 = this.getConnectedPlots().iterator();

        while (var2.hasNext()) {
            Plot plot = (Plot) var2.next();
            if (!plot.getTrusted().remove(uuid)) {
                return false;
            }

            DBFunc.removeTrusted(plot, uuid);
        }

        return true;
    }

    public boolean removeMember(UUID uuid) {
        if (this.members == null) {
            return false;
        } else if (uuid == DBFunc.everyone && !this.members.contains(uuid)) {
            boolean result = false;

            UUID other;
            for (Iterator var3 = (new HashSet(this.members)).iterator(); var3.hasNext(); result = this.rmvMember(other) || result) {
                other = (UUID) var3.next();
            }

            return result;
        } else {
            return this.rmvMember(uuid);
        }
    }

    private boolean rmvMember(UUID uuid) {
        Iterator var2 = this.getConnectedPlots().iterator();

        while (var2.hasNext()) {
            Plot current = (Plot) var2.next();
            if (!current.getMembers().remove(uuid)) {
                return false;
            }

            DBFunc.removeMember(current, uuid);
        }

        return true;
    }

    public void export(final RunnableVal<Boolean> whenDone) {
        SchematicHandler.manager.getCompoundTag(this, new RunnableVal<CompoundTag>() {
            public void run(final CompoundTag value) {
                if (value == null) {
                    if (whenDone != null) {
                        whenDone.value = false;
                        TaskManager.runTask(whenDone);
                    }
                } else {
                    TaskManager.runTaskAsync(new Runnable() {
                        public void run() {
                            String name = Plot.this.id + "," + Plot.this.area + ',' + MainUtil.getName(Plot.this.owner);
                            boolean result = SchematicHandler.manager.save(value, Paths.SCHEMATICS + File.separator + name + ".schematic");
                            if (whenDone != null) {
                                whenDone.value = result;
                                TaskManager.runTask(whenDone);
                            }

                        }
                    });
                }

            }
        });
    }

    public void exportBO3(RunnableVal<Boolean> whenDone) {
        boolean result = BO3Handler.saveBO3(this);
        if (whenDone != null) {
            whenDone.value = result;
        }

        TaskManager.runTask(whenDone);
    }

    public void upload(final RunnableVal<URL> whenDone) {
        SchematicHandler.manager.getCompoundTag(this, new RunnableVal<CompoundTag>() {
            public void run(CompoundTag value) {
                SchematicHandler.manager.upload(value, (UUID) null, (String) null, whenDone);
            }
        });
    }

    public void uploadWorld(RunnableVal<URL> whenDone) {
        WorldUtil.IMP.upload(this, (UUID) null, (String) null, whenDone);
    }

    public void uploadBO3(RunnableVal<URL> whenDone) {
        BO3Handler.upload(this, (UUID) null, (String) null, whenDone);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Plot other = (Plot) obj;
            return this.hashCode() == other.hashCode() && this.id.equals(other.id) && this.area == other.area;
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public HashMap<Flag<?>, Object> getFlags() {
        return this.getSettings().flags;
    }

    public void setFlags(HashMap<Flag<?>, Object> flags) {
        FlagManager.setPlotFlags(this, flags);
    }

    public String getAlias() {
        return this.settings == null ? "" : this.settings.getAlias();
    }

    public void setAlias(String alias) {
        Iterator var2 = this.getConnectedPlots().iterator();

        while (var2.hasNext()) {
            Plot current = (Plot) var2.next();
            String name = this.getSettings().getAlias();
            if (alias == null) {
                alias = "";
            }

            if (name.equals(alias)) {
                return;
            }

            current.getSettings().setAlias(alias);
            DBFunc.setAlias(current, alias);
        }

    }

    public void setMerged(int direction, boolean value) {
        if (this.getSettings().setMerged(direction, value)) {
            if (value) {
                Plot other = this.getRelative(direction).getBasePlot(false);
                if (!other.equals(this.getBasePlot(false))) {
                    Plot base = other.id.y >= this.id.y && (other.id.y != this.id.y || other.id.x >= this.id.x) ? this.origin : other;
                    this.origin.origin = base;
                    other.origin = base;
                    this.origin = base;
                    connected_cache = null;
                }
            } else {
                if (this.origin != null) {
                    this.origin.origin = null;
                    this.origin = null;
                }

                connected_cache = null;
            }
            DBFunc.setMerged(this, this.getSettings().getMerged());
            regions_cache = null;
        }

    }

    public boolean[] getMerged() {
        return this.getSettings().getMerged();
    }

    public void setMerged(boolean[] merged) {
        this.getSettings().setMerged(merged);
        DBFunc.setMerged(this, merged);
        this.clearCache();
    }

    public void clearCache() {
        connected_cache = null;
        regions_cache = null;
        if (this.origin != null) {
            this.origin.origin = null;
            this.origin = null;
        }

    }

    public BlockLoc getPosition() {
        return this.getSettings().getPosition();
    }

    public boolean canClaim(PlotPlayer player) {
        PlotCluster cluster = this.getCluster();
        if (cluster != null && player != null && !cluster.isAdded(player.getUUID()) && !Permissions.hasPermission(player, "plots.admin.command.claim")) {
            return false;
        } else {
            return this.guessOwner() == null && !this.isMerged();
        }
    }

    public UUID guessOwner() {
        if (this.hasOwner()) {
            return this.owner;
        } else if (!this.area.ALLOW_SIGNS) {
            return null;
        } else {
            try {
                final Location loc = this.getManager().getSignLoc(this.area, this);
                String[] lines = (String[]) TaskManager.IMP.sync(new RunnableVal<String[]>() {
                    public void run(String[] value) {
                        ChunkManager.manager.loadChunk(loc.getWorld(), loc.getChunkLoc(), false);
                        this.value = WorldUtil.IMP.getSign(loc);
                    }
                });
                if (lines == null) {
                    return null;
                } else {
                    label67:
                    for (int i = 4; i > 0; --i) {
                        String caption = C.valueOf("OWNER_SIGN_LINE_" + i).s();
                        int index = caption.indexOf("%plr%");
                        if (index >= 0) {
                            String line = lines[i - 1];
                            if (line.length() <= index) {
                                return null;
                            }

                            String name = line.substring(index);
                            if (name.isEmpty()) {
                                return null;
                            }

                            UUID owner = UUIDHandler.getUUID(name, (RunnableVal) null);
                            if (owner != null) {
                                this.owner = owner;
                            } else {
                                if (lines[i - 1].length() == 15) {
                                    BiMap<StringWrapper, UUID> map = UUIDHandler.getUuidMap();
                                    Iterator var10 = map.entrySet().iterator();

                                    while (var10.hasNext()) {
                                        Entry<StringWrapper, UUID> entry = (Entry) var10.next();
                                        String key = ((StringWrapper) entry.getKey()).value;
                                        if (key.length() > name.length() && key.startsWith(name)) {
                                            this.owner = (UUID) entry.getValue();
                                            break label67;
                                        }
                                    }
                                }

                                this.owner = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
                            }
                            break;
                        }
                    }

                    if (this.hasOwner()) {
                        this.create();
                    }

                    return this.owner;
                }
            } catch (IllegalArgumentException var13) {
                return null;
            }
        }
    }

    public void removeRoadSouth() {
        if (this.area.TYPE != 0 && this.area.TERRAIN > 1) {
            if (this.area.TERRAIN == 3) {
                return;
            }

            Plot other = this.getRelative(2);
            Location bot = other.getBottomAbs();
            Location top = this.getTopAbs();
            Location pos1 = new Location(this.getWorldName(), bot.getX(), 0, top.getZ());
            Location pos2 = new Location(this.getWorldName(), top.getX(), 256, bot.getZ());
            ChunkManager.manager.regenerateRegion(pos1, pos2, true, (Runnable) null);
        } else {
            this.getManager().removeRoadSouth(this.area, this);
        }

    }

    public boolean autoMerge(int dir, int max, UUID uuid, boolean removeRoads) {
        if (this.owner == null) {
            return false;
        } else if (!EventUtil.manager.callMerge(this, dir, max)) {
            return false;
        } else {
            HashSet<Plot> visited = new HashSet();
            HashSet<PlotId> merged = new HashSet();
            Set<Plot> connected = this.getConnectedPlots();
            Iterator var8 = connected.iterator();

            Plot current;
            while (var8.hasNext()) {
                current = (Plot) var8.next();
                merged.add(current.getId());
            }

            ArrayDeque<Plot> frontier = new ArrayDeque(connected);
            boolean toReturn = false;

            while ((current = (Plot) frontier.poll()) != null && max >= 0) {
                if (!visited.contains(current)) {
                    visited.add(current);
                    Plot other;
                    Set plots;
                    if ((dir == -1 || dir == 0) && !current.getMerged(0)) {
                        other = current.getRelative(0);
                        if (other != null && other.isOwner(uuid) && (other.getBasePlot(false).equals(current.getBasePlot(false)) || (plots = other.getConnectedPlots()).size() <= max && frontier.addAll(plots) && (max -= plots.size()) != -1)) {
                            current.mergePlot(other, removeRoads);
                            merged.add(current.getId());
                            merged.add(other.getId());
                            toReturn = true;
                        }
                    }

                    if (max >= 0 && (dir == -1 || dir == 1) && !current.getMerged(1)) {
                        other = current.getRelative(1);
                        if (other != null && other.isOwner(uuid) && (other.getBasePlot(false).equals(current.getBasePlot(false)) || (plots = other.getConnectedPlots()).size() <= max && frontier.addAll(plots) && (max -= plots.size()) != -1)) {
                            current.mergePlot(other, removeRoads);
                            merged.add(current.getId());
                            merged.add(other.getId());
                            toReturn = true;
                        }
                    }

                    if (max >= 0 && (dir == -1 || dir == 2) && !current.getMerged(2)) {
                        other = current.getRelative(2);
                        if (other != null && other.isOwner(uuid) && (other.getBasePlot(false).equals(current.getBasePlot(false)) || (plots = other.getConnectedPlots()).size() <= max && frontier.addAll(plots) && (max -= plots.size()) != -1)) {
                            current.mergePlot(other, removeRoads);
                            merged.add(current.getId());
                            merged.add(other.getId());
                            toReturn = true;
                        }
                    }

                    if (max >= 0 && (dir == -1 || dir == 3) && !current.getMerged(3)) {
                        other = current.getRelative(3);
                        if (other != null && other.isOwner(uuid) && (other.getBasePlot(false).equals(current.getBasePlot(false)) || (plots = other.getConnectedPlots()).size() <= max && frontier.addAll(plots) && (max -= plots.size()) != -1)) {
                            current.mergePlot(other, removeRoads);
                            merged.add(current.getId());
                            merged.add(other.getId());
                            toReturn = true;
                        }
                    }
                }
            }

            if (removeRoads && toReturn) {
                ArrayList<PlotId> ids = new ArrayList(merged);
                this.getManager().finishPlotMerge(this.area, ids);
            }

            return toReturn;
        }
    }

    public void mergeData(Plot b) {
        HashMap<Flag<?>, Object> flags1 = this.getFlags();
        HashMap<Flag<?>, Object> flags2 = b.getFlags();
        if ((!flags1.isEmpty() || !flags2.isEmpty()) && !flags1.equals(flags2)) {
            boolean greater = flags1.size() > flags2.size();
            if (greater) {
                flags1.putAll(flags2);
            } else {
                flags2.putAll(flags1);
            }

            HashMap<Flag<?>, Object> net = greater ? flags1 : flags2;
            this.setFlags(net);
            b.setFlags(net);
        }

        if (!this.getAlias().isEmpty()) {
            b.setAlias(this.getAlias());
        } else if (!b.getAlias().isEmpty()) {
            this.setAlias(b.getAlias());
        }

        Iterator var6 = this.getTrusted().iterator();

        UUID uuid;
        while (var6.hasNext()) {
            uuid = (UUID) var6.next();
            b.addTrusted(uuid);
        }

        var6 = b.getTrusted().iterator();

        while (var6.hasNext()) {
            uuid = (UUID) var6.next();
            this.addTrusted(uuid);
        }

        var6 = this.getMembers().iterator();

        while (var6.hasNext()) {
            uuid = (UUID) var6.next();
            b.addMember(uuid);
        }

        var6 = b.getMembers().iterator();

        while (var6.hasNext()) {
            uuid = (UUID) var6.next();
            this.addMember(uuid);
        }

        var6 = this.getDenied().iterator();

        while (var6.hasNext()) {
            uuid = (UUID) var6.next();
            b.addDenied(uuid);
        }

        var6 = b.getDenied().iterator();

        while (var6.hasNext()) {
            uuid = (UUID) var6.next();
            this.addDenied(uuid);
        }

    }

    public void removeRoadSouthEast() {
        if (this.area.TYPE != 0 && this.area.TERRAIN > 1) {
            if (this.area.TERRAIN == 3) {
                return;
            }

            Plot other = this.getRelative(1, 1);
            Location pos1 = this.getTopAbs().add(1, 0, 1);
            Location pos2 = other.getBottomAbs().subtract(1, 0, 1);
            pos1.setY(0);
            pos2.setY(256);
            ChunkManager.manager.regenerateRegion(pos1, pos2, true, (Runnable) null);
        } else {
            this.area.getPlotManager().removeRoadSouthEast(this.area, this);
        }

    }

    public Plot getRelative(int x, int y) {
        return this.area.getPlotAbs(this.id.getRelative(x, y));
    }

    public Plot getRelative(PlotArea area, int x, int y) {
        return area.getPlotAbs(this.id.getRelative(x, y));
    }

    public Plot getRelative(int direction) {
        return this.area.getPlotAbs(this.id.getRelative(direction));
    }

    public Set<Plot> getConnectedPlots() {
        if (this.settings == null) {
            return Collections.singleton(this);
        } else {
            boolean[] merged = this.getMerged();
            int hash = MainUtil.hash(merged);
            if (hash == 0) {
                return Collections.singleton(this);
            } else if (connected_cache != null && connected_cache.contains(this)) {
                return connected_cache;
            } else {
                regions_cache = null;
                HashSet<Plot> tmpSet = new HashSet();
                ArrayDeque<Plot> frontier = new ArrayDeque();
                HashSet<Object> queuecache = new HashSet();
                tmpSet.add(this);
                Plot tmp;
                if (merged[0]) {
                    tmp = this.area.getPlotAbs(this.id.getRelative(0));
                    if (!tmp.getMerged(2)) {
                        PS.debug("Fixing invalid merge: " + this);
                        if (tmp.isOwnerAbs(this.owner)) {
                            tmp.getSettings().setMerged(2, true);
                            DBFunc.setMerged(tmp, tmp.getSettings().getMerged());
                        } else {
                            this.getSettings().setMerged(0, false);
                            DBFunc.setMerged(this, this.getSettings().getMerged());
                        }
                    }

                    queuecache.add(tmp);
                    frontier.add(tmp);
                }

                if (merged[1]) {
                    tmp = this.area.getPlotAbs(this.id.getRelative(1));
                    if (!tmp.getMerged(3)) {
                        PS.debug("Fixing invalid merge: " + this);
                        if (tmp.isOwnerAbs(this.owner)) {
                            tmp.getSettings().setMerged(3, true);
                            DBFunc.setMerged(tmp, tmp.getSettings().getMerged());
                        } else {
                            this.getSettings().setMerged(1, false);
                            DBFunc.setMerged(this, this.getSettings().getMerged());
                        }
                    }

                    queuecache.add(tmp);
                    frontier.add(tmp);
                }

                if (merged[2]) {
                    tmp = this.area.getPlotAbs(this.id.getRelative(2));
                    if (!tmp.getMerged(0)) {
                        PS.debug("Fixing invalid merge: " + this);
                        if (tmp.isOwnerAbs(this.owner)) {
                            tmp.getSettings().setMerged(0, true);
                            DBFunc.setMerged(tmp, tmp.getSettings().getMerged());
                        } else {
                            this.getSettings().setMerged(2, false);
                            DBFunc.setMerged(this, this.getSettings().getMerged());
                        }
                    }

                    queuecache.add(tmp);
                    frontier.add(tmp);
                }

                if (merged[3]) {
                    tmp = this.area.getPlotAbs(this.id.getRelative(3));
                    if (!tmp.getMerged(1)) {
                        PS.debug("Fixing invalid merge: " + this);
                        if (tmp.isOwnerAbs(this.owner)) {
                            tmp.getSettings().setMerged(1, true);
                            DBFunc.setMerged(tmp, tmp.getSettings().getMerged());
                        } else {
                            this.getSettings().setMerged(3, false);
                            DBFunc.setMerged(this, this.getSettings().getMerged());
                        }
                    }

                    queuecache.add(tmp);
                    frontier.add(tmp);
                }

                while (true) {
                    Plot current;
                    while ((current = (Plot) frontier.poll()) != null) {
                        if (current.owner != null && current.settings != null) {
                            tmpSet.add(current);
                            queuecache.remove(current);
                            merged = current.getMerged();
                            if (merged[0]) {
                                tmp = current.area.getPlotAbs(current.id.getRelative(0));
                                if (tmp != null && !queuecache.contains(tmp) && !tmpSet.contains(tmp)) {
                                    queuecache.add(tmp);
                                    frontier.add(tmp);
                                }
                            }

                            if (merged[1]) {
                                tmp = current.area.getPlotAbs(current.id.getRelative(1));
                                if (tmp != null && !queuecache.contains(tmp) && !tmpSet.contains(tmp)) {
                                    queuecache.add(tmp);
                                    frontier.add(tmp);
                                }
                            }

                            if (merged[2]) {
                                tmp = current.area.getPlotAbs(current.id.getRelative(2));
                                if (tmp != null && !queuecache.contains(tmp) && !tmpSet.contains(tmp)) {
                                    queuecache.add(tmp);
                                    frontier.add(tmp);
                                }
                            }

                            if (merged[3]) {
                                tmp = current.area.getPlotAbs(current.id.getRelative(3));
                                if (tmp != null && !queuecache.contains(tmp) && !tmpSet.contains(tmp)) {
                                    queuecache.add(tmp);
                                    frontier.add(tmp);
                                }
                            }
                        } else {
                            PS.debug("Ignoring invalid merged plot: " + current + " | " + current.owner);
                        }
                    }

                    connected_cache = tmpSet;
                    return tmpSet;
                }
            }
        }
    }

    public HashSet<RegionWrapper> getRegions() {
        if (regions_cache != null && connected_cache != null && connected_cache.contains(this)) {
            return regions_cache;
        } else if (!this.isMerged()) {
            Location pos1 = this.getBottomAbs();
            Location pos2 = this.getTopAbs();
            connected_cache = new HashSet(Collections.singletonList(this));
            regions_cache = new HashSet(1);
            regions_cache.add(new RegionWrapper(pos1.getX(), pos2.getX(), pos1.getY(), pos2.getY(), pos1.getZ(), pos2.getZ()));
            return regions_cache;
        } else {
            Set<Plot> plots = this.getConnectedPlots();
            HashSet<RegionWrapper> regions = regions_cache = new HashSet();
            HashSet<PlotId> visited = new HashSet();
            Iterator var4 = plots.iterator();

            while (true) {
                Plot current;
                do {
                    if (!var4.hasNext()) {
                        return regions;
                    }

                    current = (Plot) var4.next();
                } while (visited.contains(current.getId()));

                boolean merge = true;
                PlotId bot = new PlotId(current.getId().x, current.getId().y);
                PlotId top = new PlotId(current.getId().x, current.getId().y);

                Iterator var11;
                PlotId id;
                label154:
                while (merge) {
                    merge = false;
                    ArrayList<PlotId> ids = MainUtil.getPlotSelectionIds(new PlotId(bot.x, bot.y - 1), new PlotId(top.x, bot.y - 1));
                    boolean tmp = true;
                    var11 = ids.iterator();

                    while (true) {
                        Plot plot;
                        do {
                            if (!var11.hasNext()) {
                                if (tmp) {
                                    merge = true;
                                    --bot.y;
                                }

                                ids = MainUtil.getPlotSelectionIds(new PlotId(top.x + 1, bot.y), new PlotId(top.x + 1, top.y));
                                tmp = true;
                                var11 = ids.iterator();

                                while (true) {
                                    do {
                                        if (!var11.hasNext()) {
                                            if (tmp) {
                                                merge = true;
                                                ++top.x;
                                            }

                                            ids = MainUtil.getPlotSelectionIds(new PlotId(bot.x, top.y + 1), new PlotId(top.x, top.y + 1));
                                            tmp = true;
                                            var11 = ids.iterator();

                                            while (true) {
                                                do {
                                                    if (!var11.hasNext()) {
                                                        if (tmp) {
                                                            merge = true;
                                                            ++top.y;
                                                        }

                                                        ids = MainUtil.getPlotSelectionIds(new PlotId(bot.x - 1, bot.y), new PlotId(bot.x - 1, top.y));
                                                        tmp = true;
                                                        var11 = ids.iterator();

                                                        while (true) {
                                                            do {
                                                                if (!var11.hasNext()) {
                                                                    if (tmp) {
                                                                        merge = true;
                                                                        --bot.x;
                                                                    }
                                                                    continue label154;
                                                                }

                                                                id = (PlotId) var11.next();
                                                                plot = this.area.getPlotAbs(id);
                                                            } while (plot != null && plot.getMerged(1) && !visited.contains(plot.getId()));

                                                            tmp = false;
                                                        }
                                                    }

                                                    id = (PlotId) var11.next();
                                                    plot = this.area.getPlotAbs(id);
                                                } while (plot != null && plot.getMerged(0) && !visited.contains(plot.getId()));

                                                tmp = false;
                                            }
                                        }

                                        id = (PlotId) var11.next();
                                        plot = this.area.getPlotAbs(id);
                                    } while (plot != null && plot.getMerged(3) && !visited.contains(plot.getId()));

                                    tmp = false;
                                }
                            }

                            id = (PlotId) var11.next();
                            plot = this.area.getPlotAbs(id);
                        } while (plot != null && plot.getMerged(2) && !visited.contains(plot.getId()));

                        tmp = false;
                    }
                }

                Location gtopabs = this.area.getPlotAbs(top).getTopAbs();
                Location gbotabs = this.area.getPlotAbs(bot).getBottomAbs();
                var11 = MainUtil.getPlotSelectionIds(bot, top).iterator();

                while (var11.hasNext()) {
                    id = (PlotId) var11.next();
                    visited.add(id);
                }

                Location botabs;
                Location topabs;
                int y;
                Plot plot;
                Location toploc;
                for (y = bot.x; y <= top.x; ++y) {
                    plot = this.area.getPlotAbs(new PlotId(y, top.y));
                    if (plot.getMerged(2)) {
                        toploc = plot.getExtendedTopAbs();
                        botabs = plot.getBottomAbs();
                        topabs = plot.getTopAbs();
                        regions.add(new RegionWrapper(botabs.getX(), topabs.getX(), topabs.getZ() + 1, toploc.getZ()));
                        if (plot.getMerged(5)) {
                            regions.add(new RegionWrapper(topabs.getX() + 1, toploc.getX(), topabs.getZ() + 1, toploc.getZ()));
                        }
                    }
                }

                for (y = bot.y; y <= top.y; ++y) {
                    plot = this.area.getPlotAbs(new PlotId(top.x, y));
                    if (plot.getMerged(1)) {
                        toploc = plot.getExtendedTopAbs();
                        botabs = plot.getBottomAbs();
                        topabs = plot.getTopAbs();
                        regions.add(new RegionWrapper(topabs.getX() + 1, toploc.getX(), botabs.getZ(), topabs.getZ()));
                        if (plot.getMerged(5)) {
                            regions.add(new RegionWrapper(topabs.getX() + 1, toploc.getX(), topabs.getZ() + 1, toploc.getZ()));
                        }
                    }
                }

                regions.add(new RegionWrapper(gbotabs.getX(), gtopabs.getX(), gbotabs.getZ(), gtopabs.getZ()));
            }
        }
    }

    public RegionWrapper getLargestRegion() {
        HashSet<RegionWrapper> regions = this.getRegions();
        RegionWrapper max = null;
        double area = -1.0D / 0.0;
        Iterator var5 = regions.iterator();

        while (var5.hasNext()) {
            RegionWrapper region = (RegionWrapper) var5.next();
            double current = ((double) region.maxX - (double) region.minX + 1.0D) * ((double) region.maxZ - (double) region.minZ + 1.0D);
            if (current > area) {
                max = region;
                area = current;
            }
        }

        return max;
    }

    public void reEnter() {
        TaskManager.runTaskLater(new Runnable() {
            public void run() {
                Iterator var1 = Plot.this.getPlayersInPlot().iterator();

                while (var1.hasNext()) {
                    PlotPlayer pp = (PlotPlayer) var1.next();
                    PlotListener.plotExit(pp, Plot.this);
                    PlotListener.plotEntry(pp, Plot.this);
                }

            }
        }, 1);
    }

    public List<Location> getAllCorners() {
        Area area = new Area();
        Iterator var2 = this.getRegions().iterator();

        while (var2.hasNext()) {
            RegionWrapper region = (RegionWrapper) var2.next();
            Rectangle2D rect = new Double((double) region.minX - 0.6D, (double) region.minZ - 0.6D, (double) (region.maxX - region.minX) + 1.2D, (double) (region.maxZ - region.minZ) + 1.2D);
            Area rectArea = new Area(rect);
            area.add(rectArea);
        }

        List<Location> locs = new ArrayList();
        double[] coords = new double[6];

        for (PathIterator pi = area.getPathIterator((AffineTransform) null); !pi.isDone(); pi.next()) {
            int type = pi.currentSegment(coords);
            int x = (int) MathMan.inverseRound(coords[0]);
            int z = (int) MathMan.inverseRound(coords[1]);
            if (type != 4) {
                locs.add(new Location(this.getWorldName(), x, 0, z));
            }
        }

        return locs;
    }

    public boolean teleportPlayer(final PlotPlayer player) {
        Plot plot = this.getBasePlot(false);
        boolean result = EventUtil.manager.callTeleport(player, player.getLocation(), plot);
        if (!result) {
            return false;
        } else {
            final Location location;
            if (!this.area.HOME_ALLOW_NONMEMBER && !plot.isAdded(player.getUUID())) {
                location = this.getDefaultHome(false);
            } else {
                location = this.getHome();
            }

            if (Teleport.DELAY != 0 && !Permissions.hasPermission(player, "plots.teleport.delay.bypass")) {
                MainUtil.sendMessage(player, C.TELEPORT_IN_SECONDS, new String[]{Teleport.DELAY + ""});
                final String name = player.getName();
                TaskManager.TELEPORT_QUEUE.add(name);
                TaskManager.runTaskLater(new Runnable() {
                    public void run() {
                        if (!TaskManager.TELEPORT_QUEUE.contains(name)) {
                            MainUtil.sendMessage(player, C.TELEPORT_FAILED, new String[0]);
                        } else {
                            TaskManager.TELEPORT_QUEUE.remove(name);
                            if (player.isOnline()) {
                                MainUtil.sendMessage(player, C.TELEPORTED_TO_PLOT, new String[0]);
                                player.teleport(location);
                            }

                        }
                    }
                }, Teleport.DELAY * 20);
                return true;
            } else {
                MainUtil.sendMessage(player, C.TELEPORTED_TO_PLOT, new String[0]);
                player.teleport(location);
                return true;
            }
        }
    }

    public boolean isOnline() {
        if (this.owner == null) {
            return false;
        } else if (!this.isMerged()) {
            return UUIDHandler.getPlayer(this.owner) != null;
        } else {
            Iterator var1 = this.getConnectedPlots().iterator();

            Plot current;
            do {
                if (!var1.hasNext()) {
                    return false;
                }

                current = (Plot) var1.next();
            } while (!current.hasOwner() || UUIDHandler.getPlayer(current.owner) == null);

            return true;
        }
    }

    public boolean setComponent(String component, PlotBlock[] blocks) {
        if (StringMan.isEqualToAny(component, this.getManager().getPlotComponents(this.area, this.getId()))) {
            EventUtil.manager.callComponentSet(this, component);
        }

        return this.getManager().setComponent(this.area, this.getId(), component, blocks);
    }

    public int getDistanceFromOrigin() {
        Location bot = this.getManager().getPlotBottomLocAbs(this.area, this.id);
        Location top = this.getManager().getPlotTopLocAbs(this.area, this.id);
        return Math.max(Math.max(Math.abs(bot.getX()), Math.abs(bot.getZ())), Math.max(Math.abs(top.getX()), Math.abs(top.getZ())));
    }

    public void updateWorldBorder() {
        if (this.owner != null) {
            int border = this.area.getBorder();
            if (border != 2147483647) {
                int max = this.getDistanceFromOrigin();
                if (max > border) {
                    this.area.setMeta("worldBorder", max);
                }

            }
        }
    }

    public void mergePlot(Plot lesserPlot, boolean removeRoads) {
        removeRoads = false;
        Plot greaterPlot = this;
        Plot below;
        if (lesserPlot.getId().x == this.getId().x) {
            if (lesserPlot.getId().y > this.getId().y) {
                below = lesserPlot;
                lesserPlot = this;
                greaterPlot = below;
            }

            if (!lesserPlot.getMerged(2)) {
                lesserPlot.clearRatings();
                greaterPlot.clearRatings();
                lesserPlot.setMerged(2, true);
                greaterPlot.setMerged(0, true);
                lesserPlot.mergeData(greaterPlot);
                if (removeRoads) {
                    lesserPlot.removeRoadSouth();
                    below = greaterPlot.getRelative(1);
                    if (below.getMerged(7)) {
                        lesserPlot.removeRoadSouthEast();
                    }

                    below = greaterPlot.getRelative(3);
                    if (below.getMerged(4)) {
                        below.getRelative(0).removeRoadSouthEast();
                    }
                }
            }
        } else {
            if (lesserPlot.getId().x > this.getId().x) {
                below = lesserPlot;
                lesserPlot = this;
                greaterPlot = below;
            }

            if (!lesserPlot.getMerged(1)) {
                lesserPlot.clearRatings();
                greaterPlot.clearRatings();
                lesserPlot.setMerged(1, true);
                greaterPlot.setMerged(3, true);
                lesserPlot.mergeData(greaterPlot);
                if (removeRoads) {
                    below = greaterPlot.getRelative(2);
                    if (below.getMerged(7)) {
                        lesserPlot.removeRoadSouthEast();
                    }

                    lesserPlot.removeRoadEast();
                }

                below = greaterPlot.getRelative(0);
                if (below.getMerged(6)) {
                    below.getRelative(3).removeRoadSouthEast();
                }
            }
        }

    }

    public boolean move(final Plot destination, final Runnable whenDone, boolean allowSwap) {
        final PlotId offset = new PlotId(destination.getId().x - this.getId().x, destination.getId().y - this.getId().y);
        Location db = destination.getBottomAbs();
        Location ob = this.getBottomAbs();
        final int offsetX = db.getX() - ob.getX();
        final int offsetZ = db.getZ() - ob.getZ();
        if (this.owner == null) {
            TaskManager.runTaskLater(whenDone, 1);
            return false;
        } else {
            boolean occupied = false;
            Set<Plot> plots = this.getConnectedPlots();
            Iterator var11 = plots.iterator();

            while (var11.hasNext()) {
                Plot plot = (Plot) var11.next();
                Plot other = plot.getRelative(destination.getArea(), offset.x, offset.y);
                if (other.hasOwner()) {
                    if (!allowSwap) {
                        TaskManager.runTaskLater(whenDone, 1);
                        return false;
                    }

                    occupied = true;
                } else {
                    plot.removeSign();
                }
            }

            destination.updateWorldBorder();
            final ArrayDeque<RegionWrapper> regions = new ArrayDeque(this.getRegions());
            final PlotArea originArea = this.getArea();
            Iterator var18 = plots.iterator();

            while (var18.hasNext()) {
                Plot plot = (Plot) var18.next();
                Plot other = plot.getRelative(destination.getArea(), offset.x, offset.y);
                plot.swapData(other, (Runnable) null);
            }

            Runnable move = new Runnable() {
                public void run() {
                    if (!regions.isEmpty()) {
                        RegionWrapper region = (RegionWrapper) regions.poll();
                        Location[] corners = region.getCorners(Plot.this.getWorldName());
                        final Location pos1 = corners[0];
                        final Location pos2 = corners[1];
                        Location newPos = pos1.clone().add(offsetX, 0, offsetZ);
                        newPos.setWorld(destination.getWorldName());
                        ChunkManager.manager.copyRegion(pos1, pos2, newPos, new Runnable() {
                            public void run() {
                                ChunkManager.manager.regenerateRegion(pos1, pos2, true, null);
                            }
                        });
                    } else {
                        Plot plot = destination.getRelative(0, 0);
                        Iterator var2 = plot.getConnectedPlots().iterator();

                        while (var2.hasNext()) {
                            Plot current = (Plot) var2.next();
                            Plot.this.getManager().claimPlot(current.getArea(), current);
                            Plot originPlot = originArea.getPlotAbs(new PlotId(current.id.x - offset.x, current.id.y - offset.y));
                            originPlot.getManager().unclaimPlot(originArea, originPlot, (Runnable) null);
                        }

                        plot.setSign();
                        TaskManager.runTask(whenDone);
                    }
                }
            };
            Runnable swap = new Runnable() {
                public void run() {
                    if (regions.isEmpty()) {
                        TaskManager.runTask(whenDone);
                    } else {
                        RegionWrapper region = (RegionWrapper) regions.poll();
                        Location[] corners = region.getCorners(Plot.this.getWorldName());
                        Location pos1 = corners[0];
                        Location pos2 = corners[1];
                        Location pos3 = pos1.clone().add(offsetX, 0, offsetZ);
                        Location pos4 = pos2.clone().add(offsetX, 0, offsetZ);
                        pos3.setWorld(destination.getWorldName());
                        pos4.setWorld(destination.getWorldName());
                        ChunkManager.manager.swap(pos1, pos2, pos3, pos4, this);
                    }
                }
            };
            if (occupied) {
                swap.run();
            } else {
                move.run();
            }

            return true;
        }
    }

    public boolean copy(final Plot destination, final Runnable whenDone) {
        PlotId offset = new PlotId(destination.getId().x - this.getId().x, destination.getId().y - this.getId().y);
        Location db = destination.getBottomAbs();
        Location ob = this.getBottomAbs();
        final int offsetX = db.getX() - ob.getX();
        final int offsetZ = db.getZ() - ob.getZ();
        if (this.owner == null) {
            TaskManager.runTaskLater(whenDone, 1);
            return false;
        } else {
            Set<Plot> plots = this.getConnectedPlots();
            Iterator var9 = plots.iterator();

            Plot plot;
            Plot other;
            while (var9.hasNext()) {
                plot = (Plot) var9.next();
                other = plot.getRelative(destination.getArea(), offset.x, offset.y);
                if (other.hasOwner()) {
                    TaskManager.runTaskLater(whenDone, 1);
                    return false;
                }
            }

            destination.updateWorldBorder();
            var9 = plots.iterator();

            while (true) {
                Iterator var12;
                UUID denied;
                do {
                    do {
                        if (!var9.hasNext()) {
                            final ArrayDeque<RegionWrapper> regions = new ArrayDeque(this.getRegions());
                            Runnable run = new Runnable() {
                                public void run() {
                                    if (!regions.isEmpty()) {
                                        RegionWrapper region = (RegionWrapper) regions.poll();
                                        Location[] corners = region.getCorners(Plot.this.getWorldName());
                                        Location pos1 = corners[0];
                                        Location pos2 = corners[1];
                                        Location newPos = pos1.clone().add(offsetX, 0, offsetZ);
                                        newPos.setWorld(destination.getWorldName());
                                        ChunkManager.manager.copyRegion(pos1, pos2, newPos, this);
                                    } else {
                                        Iterator var1 = Plot.this.getConnectedPlots().iterator();

                                        while (var1.hasNext()) {
                                            Plot current = (Plot) var1.next();
                                            destination.getManager().claimPlot(destination.getArea(), destination);
                                        }

                                        destination.setSign();
                                        TaskManager.runTask(whenDone);
                                    }
                                }
                            };
                            run.run();
                            return true;
                        }

                        plot = (Plot) var9.next();
                        other = plot.getRelative(destination.getArea(), offset.x, offset.y);
                        other.create(plot.owner, false);
                        if (!plot.getFlags().isEmpty()) {
                            other.getSettings().flags = plot.getFlags();
                            DBFunc.setFlags(other, plot.getFlags());
                        }

                        if (plot.isMerged()) {
                            other.setMerged(plot.getMerged());
                        }

                        if (plot.members != null && !plot.members.isEmpty()) {
                            other.members = plot.members;
                            var12 = plot.members.iterator();

                            while (var12.hasNext()) {
                                denied = (UUID) var12.next();
                                DBFunc.setMember(other, denied);
                            }
                        }

                        if (plot.trusted != null && !plot.trusted.isEmpty()) {
                            other.trusted = plot.trusted;
                            var12 = plot.trusted.iterator();

                            while (var12.hasNext()) {
                                denied = (UUID) var12.next();
                                DBFunc.setTrusted(other, denied);
                            }
                        }
                    } while (plot.denied == null);
                } while (plot.denied.isEmpty());

                other.denied = plot.denied;
                var12 = plot.denied.iterator();

                while (var12.hasNext()) {
                    denied = (UUID) var12.next();
                    DBFunc.setDenied(other, denied);
                }
            }
        }
    }

    public boolean hasFlag(Flag<?> flag) {
        return this.getFlags().containsKey(flag);
    }
}
