/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.World$Environment
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Chest
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Animals
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Monster
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package clear;

import clear.Main;
import clear.Names;
import clear.ServerManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Clear
        implements Listener {
    private static final int DELAY_SHOW = 35;
    private static final int CHEST_ID = 54;
    private Random r = new Random();
    private Main main;
    private Server server;
    private ServerManager serverManager;
    private boolean tip;
    private HashMap<String, Boolean> ignoreWorlds;
    private int checkInterval;
    private int startClearEntitys;
    private int mustClearAmount;
    private int mustClearLevel;
    private List<Level> levelList;
    private int gridSize;
    private HashMap<Integer, Boolean> clearList;
    private int ticksLived;
    private int clearMode;
    private HashMap<Integer, Boolean> clearWhite;
    private HashMap<Integer, Boolean> clearBlack;
    private boolean ske;
    private HashMap<Short, Boolean> clearMonsterList;
    private int maxPerGrid;
    private boolean firstAll;
    private int heightMax;
    private int heightMin;
    private HashMap<Integer, Integer> clearTypes;
    private ClearTimer clearTimer;
    private HashMap<Integer, Boolean> airBlocks;

    public Clear(Main main) {
        this.main = main;
        this.server = main.getServer();
        this.serverManager = main.getServerManager();
        this.clearTimer = new ClearTimer();
        main.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) main, (Runnable) this.clearTimer, (long) (this.checkInterval * 20));
    }

    public void info(CommandSender sender) {
        HashMap<String, Integer> entityHash = new HashMap<String, Integer>();
        int total = 0;
        for (World w : this.server.getWorlds()) {
            List<Entity> list = w.getEntities();
            total += list.size();
            for (Entity e : list) {
                if (!entityHash.containsKey(e.getType().getName())) {
                    entityHash.put(e.getType().getName(), 0);
                }
                entityHash.put(e.getType().getName(), (Integer) entityHash.get(e.getType().getName()) + 1);
            }
        }
        sender.sendMessage(this.main.format("success", this.get(1200)));
        for (String s : entityHash.keySet()) {
            String name = "";
            try {
                name = Names.getEntityName(EntityType.fromName((String) s).getTypeId());
            } catch (Exception exception) {
                // empty catch block
            }
            sender.sendMessage(this.main.format("broadcastInfo", name, entityHash.get(s)));
        }
        sender.sendMessage(this.main.format("clearInfo2", total));
    }

    public void clear(boolean force, int clearLevel) {
        Iterator it;
        HashMap<Short, Integer> startHash = new HashMap<Short, Integer>();
        int startTotal = 0;
        for (World w : this.server.getWorlds()) {
            List<Entity> list = w.getEntities();
            startTotal += list.size();
            for (Entity e : list) {
                if (!startHash.containsKey(e.getType().getTypeId())) {
                    startHash.put(e.getType().getTypeId(), 0);
                }
                startHash.put(e.getType().getTypeId(), (Integer) startHash.get(e.getType().getTypeId()) + 1);
            }
        }
        if (startTotal >= this.mustClearAmount) {
            clearLevel = this.mustClearLevel;
        } else {
            if (!force && this.serverManager.getServerStatus() == 0) {
                return;
            }
            if (!force && startTotal < this.startClearEntitys) {
                return;
            }
        }
        if (clearLevel == -1) {
            clearLevel = this.serverManager.getServerStatus();
        } else if (clearLevel < 0) {
            clearLevel = 0;
        } else if (clearLevel > 3) {
            clearLevel = 3;
        }
        this.server.broadcastMessage(this.main.format("success", this.get(1205)));
        this.server.broadcastMessage(this.main.format("clearLevel", this.levelList.get(clearLevel).getShow()));
        if (this.levelList.get(clearLevel).isEntity()) {
            if (this.tip) {
                this.server.broadcastMessage(String.valueOf(this.get(1295)) + this.get(1890));
            } else {
                Main.sendConsoleMessage(String.valueOf(this.get(1295)) + this.get(1890));
            }
            for (World w : this.server.getWorlds()) {
                if (this.ignoreWorlds.containsKey(w.getName())) continue;
                it = w.getEntities().iterator();
                while (it.hasNext()) {
                    Entity e = (Entity) it.next();
                    try {
                        short id = e.getType().getTypeId();
                        if (id == 1) {
                            Item item = (Item) e;
                            if (item.getTicksLived() < this.ticksLived) continue;
                            ItemStack is = item.getItemStack();
                            int itemId = is.getTypeId();
                            if ((this.clearMode != 1 || this.clearWhite.containsKey(itemId)) && (this.clearMode != 2 || !this.clearBlack.containsKey(itemId)))
                                continue;
                            e.remove();
                            it.remove();
                            continue;
                        }
                        if (!this.clearList.containsKey(Integer.valueOf(id))) continue;
                        e.remove();
                        it.remove();
                    } catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
        } else if (this.tip) {
            this.server.broadcastMessage(String.valueOf(this.get(1295)) + this.get(1891));
        } else {
            Main.sendConsoleMessage(String.valueOf(this.get(1295)) + this.get(1891));
        }
        if (this.levelList.get(clearLevel).isMonster()) {
            if (this.tip) {
                this.server.broadcastMessage(String.valueOf(this.get(1305)) + this.get(1890));
            } else {
                Main.sendConsoleMessage(String.valueOf(this.get(1305)) + this.get(1890));
            }
            for (World w : this.server.getWorlds()) {
                if (this.ignoreWorlds.containsKey(w.getName())) continue;
                it = w.getEntitiesByClass(Monster.class).iterator();
                while (it.hasNext()) {
                    Monster mon = (Monster) it.next();
                    if (!this.clearMonsterList.containsKey(mon.getType().getTypeId()) || this.ske && mon.getType().equals((Object) EntityType.SKELETON) && w.getEnvironment().equals((Object) World.Environment.NETHER))
                        continue;
                    mon.remove();
                    it.remove();
                }
            }
        } else if (this.tip) {
            this.server.broadcastMessage(String.valueOf(this.get(1305)) + this.get(1891));
        } else {
            Main.sendConsoleMessage(String.valueOf(this.get(1305)) + this.get(1891));
        }
        if (this.levelList.get(clearLevel).isAnimal()) {
            if (this.tip) {
                this.server.broadcastMessage(String.valueOf(this.get(1310)) + this.get(1890));
            } else {
                Main.sendConsoleMessage(String.valueOf(this.get(1310)) + this.get(1890));
            }
            for (World w : this.server.getWorlds()) {
                HashMap amountHash = new HashMap();
                HashMap generateHash = new HashMap();
                HashMap locHash = new HashMap();
                if (this.ignoreWorlds.containsKey(w.getName())) continue;
                Iterator it2 = w.getEntitiesByClass(Animals.class).iterator();
                while (it2.hasNext()) {
                    int current;
                    Animals animals = (Animals) it2.next();
                    short id = animals.getType().getTypeId();
                    if (!this.clearTypes.containsKey(Integer.valueOf(id))) continue;
                    int x = animals.getLocation().getBlockX() / this.gridSize;
                    int z = animals.getLocation().getBlockZ() / this.gridSize;
                    if (!amountHash.containsKey(x)) {
                        amountHash.put(x, new HashMap());
                    }
                    if (!((HashMap) amountHash.get(x)).containsKey(z)) {
                        ((HashMap) amountHash.get(x)).put(z, 0);
                    }
                    if ((current = ((Integer) ((HashMap) amountHash.get(x)).get(z)).intValue()) >= this.maxPerGrid) {
                        animals.remove();
                        it2.remove();
                        if (this.r.nextInt(1000) >= this.clearTypes.get(Integer.valueOf(id))) continue;
                        if (!generateHash.containsKey(x)) {
                            generateHash.put(x, new HashMap());
                        }
                        if (!((HashMap) generateHash.get(x)).containsKey(z)) {
                            ((HashMap) generateHash.get(x)).put(z, new HashMap());
                        }
                        if (!((HashMap) ((HashMap) generateHash.get(x)).get(z)).containsKey(Integer.valueOf(id))) {
                            ((HashMap) ((HashMap) generateHash.get(x)).get(z)).put(Integer.valueOf(id), 0);
                        }
                        ((HashMap) ((HashMap) generateHash.get(x)).get(z)).put(Integer.valueOf(id), (Integer) ((HashMap) ((HashMap) generateHash.get(x)).get(z)).get(Integer.valueOf(id)) + 1);
                        if (!locHash.containsKey(x)) {
                            locHash.put(x, new HashMap());
                        }
                        ((HashMap) locHash.get(x)).put(z, animals.getLocation());
                        continue;
                    }
                    ((HashMap) amountHash.get(x)).put(z, current + 1);
                }
                Iterator iterator = generateHash.keySet().iterator();
                while (iterator.hasNext()) {
                    int x2 = (Integer) iterator.next();
                    Iterator iterator2 = ((HashMap) generateHash.get(x2)).keySet().iterator();
                    while (iterator2.hasNext()) {
                        int z2 = (Integer) iterator2.next();
                        try {
                            this.checkGenerateChest(w, x2, z2, (HashMap) ((HashMap) generateHash.get(x2)).get(z2), (Location) ((HashMap) locHash.get(x2)).get(z2));
                        } catch (Exception exception) {
                            // empty catch block
                        }
                    }
                }
            }
        } else if (this.tip) {
            this.server.broadcastMessage(String.valueOf(this.get(1310)) + this.get(1891));
        } else {
            Main.sendConsoleMessage(String.valueOf(this.get(1310)) + this.get(1891));
        }
        this.server.getScheduler().scheduleSyncDelayedTask((Plugin) this.main, (Runnable) new DelayShow(startHash, startTotal), 35L);
    }

    public void loadConfig(YamlConfiguration config) {
        this.tip = config.getBoolean("clear.tip");
        this.ignoreWorlds = new HashMap();
        for (String s : config.getStringList("clear.ignoreWorlds")) {
            this.ignoreWorlds.put(s, true);
        }
        this.checkInterval = config.getInt("clear.checkInterval");
        this.startClearEntitys = config.getInt("clear.startClearEntitys");
        this.mustClearAmount = config.getInt("clear.mustClear.amount");
        this.mustClearLevel = config.getInt("clear.mustClear.level");
        this.levelList = new ArrayList<Level>();
        String[] arrstring = new String[]{"unknown", "good", "fine", "bad"};
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String s = arrstring[n2];
            String show = Main.convert(config.getString("clear.clear." + s + ".show"));
            boolean entity = config.getBoolean("clear.clear." + s + ".entity");
            boolean monster = config.getBoolean("clear.clear." + s + ".monster");
            boolean animal = config.getBoolean("clear.clear." + s + ".animal");
            Level level = new Level(show, entity, monster, animal);
            this.levelList.add(level);
            ++n2;
        }
        this.clearList = new HashMap();
        Iterator iterator = config.getIntegerList("clear.entity.clear").iterator();
        while (iterator.hasNext()) {
            int i = (Integer) iterator.next();
            this.clearList.put(i, true);
        }
        this.ticksLived = config.getInt("clear.entity.items.ticksLived");
        this.clearMode = config.getInt("clear.entity.items.mode");
        this.clearWhite = new HashMap();
        this.clearBlack = new HashMap();
        iterator = config.getIntegerList("clear.entity.items.white").iterator();
        while (iterator.hasNext()) {
            int i = (Integer) iterator.next();
            this.clearWhite.put(i, true);
        }
        iterator = config.getIntegerList("clear.entity.items.black").iterator();
        while (iterator.hasNext()) {
            int i = (Integer) iterator.next();
            this.clearBlack.put(i, true);
        }
        this.ske = config.getBoolean("clear.monster.ske");
        this.clearMonsterList = new HashMap();
        iterator = config.getIntegerList("clear.monster.clear").iterator();
        while (iterator.hasNext()) {
            int i = (Integer) iterator.next();
            this.clearMonsterList.put((short) i, true);
        }
        this.gridSize = config.getInt("clear.animal.gridSize");
        this.maxPerGrid = config.getInt("clear.animal.maxPerGrid");
        this.firstAll = config.getBoolean("clear.animal.firstAll");
        this.heightMax = config.getInt("clear.animal.heightMax");
        this.heightMin = config.getInt("clear.animal.heightMin");
        this.clearTypes = new HashMap();
        for (String s : config.getStringList("clear.animal.clearTypes")) {
            int id = Integer.parseInt(s.split(" ")[0]);
            int chance = Integer.parseInt(s.split(" ")[1]);
            this.clearTypes.put(id, chance);
        }
        this.airBlocks = new HashMap();
        iterator = config.getIntegerList("clear.animal.airBlocks").iterator();
        while (iterator.hasNext()) {
            int i = (Integer) iterator.next();
            this.airBlocks.put(i, true);
        }
    }

    private void checkGenerateChest(World w, int x, int z, HashMap<Integer, Integer> hash, Location l) {
        int yy;
        if (this.firstAll) {
            int y2 = l.getBlockY() - this.heightMin;
            while (y2 <= l.getBlockY() + this.heightMax) {
                int x2 = x * this.gridSize;
                while (x2 < x * (this.gridSize + 1)) {
                    int z2 = z * this.gridSize;
                    while (z2 < z * (this.gridSize + 1)) {
                        if (w.getBlockTypeIdAt(x2, y2, z2) == 54) {
                            Chest chest = (Chest) w.getBlockAt(x2, y2, z2).getState();
                            Inventory inventory = chest.getBlockInventory();
                            Iterator<Integer> iterator = hash.keySet().iterator();
                            while (iterator.hasNext()) {
                                int id = iterator.next();
                                short type = (short) id;
                                inventory.addItem(new ItemStack[]{new ItemStack(383, hash.get(id).intValue(), type)});
                            }
                            return;
                        }
                        ++z2;
                    }
                    ++x2;
                }
                ++y2;
            }
        }
        int xx = l.getBlockX();
        int zz = l.getBlockZ();
        if (this.airBlocks.containsKey(w.getBlockAt(xx, l.getBlockY(), zz).getTypeId())) {
            yy = l.getBlockY() - 1;
            while (yy > 0) {
                if (!this.airBlocks.containsKey(w.getBlockAt(xx, yy, zz).getTypeId())) {
                    if (w.getBlockAt(xx, yy, zz).getTypeId() != 54) {
                        w.getBlockAt(xx, ++yy, zz).setTypeId(54);
                    }
                    Chest chest = (Chest) w.getBlockAt(xx, yy, zz).getState();
                    Inventory inventory = chest.getBlockInventory();
                    Iterator<Integer> iterator = hash.keySet().iterator();
                    while (iterator.hasNext()) {
                        int id = iterator.next();
                        short type = (short) id;
                        inventory.addItem(new ItemStack[]{new ItemStack(383, hash.get(id).intValue(), type)});
                    }
                    return;
                }
                --yy;
            }
            yy = 254;
            while (yy > l.getBlockY()) {
                if (!this.airBlocks.containsKey(w.getBlockAt(xx, yy, zz).getTypeId())) {
                    if (w.getBlockAt(xx, yy, zz).getTypeId() != 54) {
                        w.getBlockAt(xx, ++yy, zz).setTypeId(54);
                    }
                    Chest chest = (Chest) w.getBlockAt(xx, yy, zz).getState();
                    Inventory inventory = chest.getBlockInventory();
                    Iterator<Integer> iterator = hash.keySet().iterator();
                    while (iterator.hasNext()) {
                        int id = iterator.next();
                        short type = (short) id;
                        inventory.addItem(new ItemStack[]{new ItemStack(383, hash.get(id).intValue(), type)});
                    }
                    return;
                }
                --yy;
            }
        } else {
            yy = l.getBlockY() + 1;
            while (yy < 255) {
                if (this.airBlocks.containsKey(w.getBlockAt(xx, yy, zz).getTypeId())) {
                    if (w.getBlockAt(xx, yy - 1, zz).getTypeId() == 54) {
                        --yy;
                    } else {
                        w.getBlockAt(xx, yy, zz).setTypeId(54);
                    }
                    Chest chest = (Chest) w.getBlockAt(xx, yy, zz).getState();
                    Inventory inventory = chest.getBlockInventory();
                    Iterator<Integer> iterator = hash.keySet().iterator();
                    while (iterator.hasNext()) {
                        int id = iterator.next();
                        short type = (short) id;
                        inventory.addItem(new ItemStack[]{new ItemStack(383, hash.get(id).intValue(), type)});
                    }
                    return;
                }
                ++yy;
            }
            yy = l.getBlockY() - 1;
            while (yy > 0) {
                if (this.airBlocks.containsKey(w.getBlockAt(xx, yy, zz).getTypeId())) {
                    int yyy = yy - 1;
                    while (yyy > 0) {
                        if (!this.airBlocks.containsKey(w.getBlockAt(xx, yyy, zz).getTypeId())) {
                            if (w.getBlockAt(xx, yyy, zz).getTypeId() != 54) {
                                w.getBlockAt(xx, ++yyy, zz).setTypeId(54);
                            }
                            Chest chest = (Chest) w.getBlockAt(xx, yyy, zz).getState();
                            Inventory inventory = chest.getBlockInventory();
                            Iterator<Integer> type = hash.keySet().iterator();
                            while (type.hasNext()) {
                                int id = type.next();
                                short type2 = (short) id;
                                inventory.addItem(new ItemStack[]{new ItemStack(383, hash.get(id).intValue(), type2)});
                            }
                            return;
                        }
                        --yyy;
                    }
                }
                --yy;
            }
        }
    }

    private String get(int id) {
        return this.main.get(id);
    }

    class ClearTimer
            implements Runnable {
        ClearTimer() {
        }

        @Override
        public void run() {
            Clear.this.clear(false, -1);
            Clear.this.main.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) Clear.this.main, (Runnable) Clear.this.clearTimer, (long) (Clear.this.checkInterval * 20));
        }
    }

    class DelayShow
            implements Runnable {
        HashMap<Short, Integer> startHash;
        int startTotal;

        public DelayShow(HashMap<Short, Integer> startHash, int startTotal) {
            this.startHash = startHash;
            this.startTotal = startTotal;
        }

        @Override
        public void run() {
            String show;
            Iterator e2iter;
            HashMap<Short, Integer> endHash = new HashMap<Short, Integer>();
            int endTotal = 0;
            for (World w : Clear.this.server.getWorlds()) {
                List<Entity> list2 = w.getEntities();
                endTotal += list2.size();
                for (Entity e2 : list2) {
                    if (!endHash.containsKey(e2.getType().getTypeId())) {
                        endHash.put(e2.getType().getTypeId(), 0);
                    }
                    endHash.put(e2.getType().getTypeId(), (Integer) endHash.get(e2.getType().getTypeId()) + 1);
                }
            }
            Clear.this.server.broadcastMessage(Clear.this.main.format("success", Clear.this.get(1210)));
            e2iter = this.startHash.keySet().iterator();
            while (e2iter.hasNext()) {
                short s = (Short) e2iter.next();
                int end = endHash.containsKey(s) ? (Integer) endHash.get(s) : 0;
                show = Names.getEntityName(s);
                if (Clear.this.tip) {
                    Clear.this.server.broadcastMessage(Clear.this.main.format("clearInfo", show, this.startHash.get(s), end));
                } else {
                    Main.sendConsoleMessage(Clear.this.main.format("clearInfo", show, this.startHash.get(s), end));
                }
                endHash.remove(s);
            }
            for (Short s : endHash.keySet()) {
                show = Names.getEntityName(s.shortValue());
                if (Clear.this.tip) {
                    Clear.this.server.broadcastMessage(Clear.this.main.format("clearInfo", show, 0, endHash.get(s)));
                    continue;
                }
                Main.sendConsoleMessage(Clear.this.main.format("clearInfo", show, 0, endHash.get(s)));
            }
            Clear.this.server.broadcastMessage(Clear.this.main.format("clearInfo3", this.startTotal, endTotal));
        }
    }

    class Level {
        private String show;
        private boolean entity;
        private boolean monster;
        private boolean animal;

        public Level(String show, boolean entity, boolean monster, boolean animal) {
            this.show = show;
            this.entity = entity;
            this.monster = monster;
            this.animal = animal;
        }

        public String getShow() {
            return this.show;
        }

        public boolean isEntity() {
            return this.entity;
        }

        public boolean isMonster() {
            return this.monster;
        }

        public boolean isAnimal() {
            return this.animal;
        }
    }

}

