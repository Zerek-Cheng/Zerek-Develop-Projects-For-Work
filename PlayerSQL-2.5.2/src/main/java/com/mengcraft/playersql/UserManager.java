/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  com.avaje.ebean.EbeanServer
 *  com.avaje.ebean.Update
 *  lombok.val
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.json.simple.JSONArray
 */
package com.mengcraft.playersql;

import com.avaje.ebean.Update;
import com.mengcraft.playersql.lib.ExpUtil;
import com.mengcraft.playersql.lib.ItemUtil;
import com.mengcraft.playersql.lib.JSONUtil;
//import com.mengcraft.playersql.lib.simpleorm.EbeanHandler;
import com.mengcraft.playersql.task.DailySaveTask;

import java.util.*;

import com.mengcraft.simpleorm.EbeanHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;

public enum UserManager {
    INSTANCE;

    public static final ItemStack AIR;
    private final Map<UUID, BukkitRunnable> scheduled = new HashMap<UUID, BukkitRunnable>();
    private final List<UUID> locked = new ArrayList<UUID>();
    private PluginMain main;
    private ItemUtil itemUtil;
    private ExpUtil expUtil;
    private EbeanHandler db;

    private UserManager() {
    }

    public void addFetched(User user) {
        this.main.run(() -> this.pend(user));
    }

    public User fetchUser(UUID uuid) {
        return this.db.find(User.class, uuid);
    }

    public void saveUser(Player p, boolean lock) {
        this.saveUser(this.getUserData(p, !lock), lock);
    }

    public void saveUser(User user, boolean lock) {
        user.setLocked(lock);
        this.db.update(user);
        if (Config.DEBUG) {
            this.main.log("Save user data " + user.getUuid() + " done!");
        }
    }

    public void updateDataLock(UUID who, boolean lock) {
        Update update = this.db.getServer().createUpdate(User.class, "update PLAYERSQL set locked = :locked where uuid = :uuid");
        update.set("locked", (Object)lock);
        update.set("uuid", (Object)who.toString());
        int result = update.execute();
        if (Config.DEBUG) {
            if (result == 1) {
                this.main.log("Update " + who + " lock to " + lock + " okay");
            } else {
                this.main.log(new PluginException("Update " + who + " lock to " + lock + " failed"));
            }
        }
    }

    private void closeInventory(Player p) {
        ItemStack cursor;
        InventoryView view = p.getOpenInventory();
        if (!PluginMain.nil((Object)view) && !PluginMain.nil((Object)(cursor = view.getCursor()))) {
            view.setCursor(null);
            HashMap<Integer,ItemStack> d = p.getInventory().addItem(new ItemStack[]{cursor});
            if (!d.isEmpty()) {
                for (ItemStack item : d.values()) {
                    view.getTopInventory().addItem(new ItemStack[]{item});
                }
            }
        }
    }

    public User getUserData(UUID id, boolean closeInventory) {
        Player p = this.main.getServer().getPlayer(id);
        if (!PluginMain.nil((Object)p)) {
            return this.getUserData(p, closeInventory);
        }
        return null;
    }

    public User getUserData(Player p, boolean closeInventory) {
        User user = new User();
        user.setUuid(p.getUniqueId());
        if (Config.SYN_HEALTH) {
            user.setHealth(p.getHealth());
        }
        if (Config.SYN_FOOD) {
            user.setFood(p.getFoodLevel());
        }
        if (Config.SYN_INVENTORY) {
            if (closeInventory) {
                this.closeInventory(p);
            }
            user.setInventory(this.toString(p.getInventory().getContents()));
            user.setArmor(this.toString(p.getInventory().getArmorContents()));
            user.setHand(p.getInventory().getHeldItemSlot());
        }
        if (Config.SYN_CHEST) {
            user.setChest(this.toString(p.getEnderChest().getContents()));
        }
        if (Config.SYN_EFFECT) {
            user.setEffect(this.toString(p.getActivePotionEffects()));
        }
        if (Config.SYN_EXP) {
            user.setExp(this.expUtil.getExp(p));
        }
        return user;
    }

    public boolean isLocked(UUID uuid) {
        return this.locked.indexOf(uuid) != -1;
    }

    public boolean isNotLocked(UUID uuid) {
        return this.locked.indexOf(uuid) == -1;
    }

    public void lockUser(UUID uuid) {
        this.locked.add(uuid);
    }

    public void unlockUser(UUID uuid) {
        while (this.isLocked(uuid)) {
            this.locked.remove(uuid);
        }
    }

    private void pend(User user) {
        block8 : {
            Player player = this.main.getPlayer(user.getUuid());
            if (!PluginMain.nil((Object)player) && player.isOnline()) {
                try {
                    this.pend(user, player);
                }
                catch (Exception e) {
                    if (Config.KICK_LOAD_FAILED) {
                        player.kickPlayer(Config.KICK_LOAD_MESSAGE);
                    } else {
                        this.unlockUser(player.getUniqueId());
                    }
                    if (Config.DEBUG) {
                        this.main.log(e);
                        break block8;
                    }
                    this.main.log(e.toString());
                }
            } else if (Config.DEBUG) {
                this.main.log(new PluginException("Player " + user.getUuid() + " not found"));
            }
        }
    }

    private void pend(User polled, Player player) {
        if (Config.SYN_INVENTORY) {
            ItemStack[] fetched = this.toStack(polled.getInventory());
            player.closeInventory();
            PlayerInventory pack = player.getInventory();
            if (fetched.length > pack.getSize()) {
                int size = pack.getSize();
                pack.setContents((ItemStack[])Arrays.copyOf(fetched, size));
                HashMap<Integer,ItemStack> out = pack.addItem((ItemStack[])Arrays.copyOfRange(fetched, size, fetched.length));
                if (!out.isEmpty()) {
                    Location location = player.getLocation();
                    out.forEach((o, item) -> player.getWorld().dropItem(location, item));
                }
            } else {
                pack.setContents(fetched);
            }
            pack.setArmorContents(this.toStack(polled.getArmor()));
            pack.setHeldItemSlot(polled.getHand());
            player.updateInventory();
        }
        if (Config.SYN_HEALTH && player.getMaxHealth() >= polled.getHealth()) {
            player.setHealth(polled.getHealth());
        }
        if (Config.SYN_EXP) {
            this.expUtil.setExp(player, polled.getExp());
        }
        if (Config.SYN_FOOD) {
            player.setFoodLevel(polled.getFood());
        }
        if (Config.SYN_EFFECT) {
            for (PotionEffect effect : this.toEffect(polled.getEffect())) {
                player.addPotionEffect(effect, true);
            }
        }
        if (Config.SYN_CHEST) {
            player.getEnderChest().setContents(this.toStack(polled.getChest()));
        }
        this.createTask(player.getUniqueId());
        this.unlockUser(player.getUniqueId());
    }

    private List<PotionEffect> toEffect(String input) {
        JSONArray parsed = JSONUtil.parseArray(input);
        ArrayList<PotionEffect> output = new ArrayList<PotionEffect>(parsed.size());
        for (Object entrya : parsed) {
            List entry  = Collections.singletonList(entrya);
            output.add(new PotionEffect(PotionEffectType.getById((int)((Number)entry.get(0)).intValue()), ((Number)entry.get(1)).intValue(), ((Number)entry.get(2)).intValue()));
        }
        return output;
    }

    private ItemStack[] toStack(String input) {
        JSONArray list = JSONUtil.parseArray(input);
        ArrayList<ItemStack> output = new ArrayList<ItemStack>(list.size());
        for (Object line : list) {
            if (PluginMain.nil(line)) {
                output.add(AIR);
                continue;
            }
            try {
                output.add(this.itemUtil.convert(String.valueOf(line)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (ItemStack[]) output.toArray();
    }

    private String toString(ItemStack[] stacks) {
        JSONArray array = new JSONArray();
        for (ItemStack stack : stacks) {
            if (stack == null || stack.getTypeId() == 0) {
                array.add(null);
                continue;
            }
            try {
                array.add((Object)this.itemUtil.convert(stack));
            }
            catch (Exception e) {
                this.main.log(e);
            }
        }
        return array.toString();
    }

    private String toString(Collection<PotionEffect> effects) {
        JSONArray out = new JSONArray();
        for (PotionEffect effect : effects) {
            JSONArray sub = new JSONArray();
            sub.add((Object)effect.getType().getId());
            sub.add((Object)effect.getDuration());
            sub.add((Object)effect.getAmplifier());
            out.add((Object)sub);
        }
        return out.toString();
    }

    public void cancelTask(UUID uuid) {
        BukkitRunnable task = this.scheduled.remove(uuid);
        if (task != null) {
            task.cancel();
        } else if (Config.DEBUG) {
            this.main.log("No task can be canceled for " + uuid + '!');
        }
    }

    public void createTask(UUID who) {
        if (Config.DEBUG) {
            this.main.log("Scheduling daily save task for user " + who + '.');
        }
        DailySaveTask task = new DailySaveTask();
        task.setWho(who);
        task.runTaskTimer((Plugin)this.main, 6000L, 6000L);
        BukkitRunnable old = this.scheduled.put(who, task);
        if (old != null) {
            old.cancel();
            if (Config.DEBUG) {
                this.main.log("Already scheduled task for user " + who + '!');
            }
        }
    }

    public void setItemUtil(ItemUtil itemUtil) {
        this.itemUtil = itemUtil;
    }

    public void setExpUtil(ExpUtil expUtil) {
        this.expUtil = expUtil;
    }

    public void setMain(PluginMain main) {
        this.main = main;
    }

    public PluginMain getMain() {
        return this.main;
    }

    public void setDb(EbeanHandler db) {
        this.db = db;
    }

    public void newUser(UUID uuid) {
        User user = new User();
        user.setUuid(uuid);
        user.setLocked(true);
        this.db.save(user);
    }

    static {
        AIR = new ItemStack(Material.AIR);
    }
}

