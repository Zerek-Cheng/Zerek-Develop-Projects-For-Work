package com._0myun.minecraft.eventmsg.chargerespawn;

import com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnData;
import com._0myun.minecraft.utils.ItemUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class RespawnManager {
    static Map<UUID, Long> deathPlayer = new HashMap<>();
    static Map<UUID, Location> deathLocation = new HashMap<>();
    static List<UUID> pointRespawner = new ArrayList<>();

    /**
     * 是否死亡
     *
     * @param p
     * @return
     */
    public static boolean isDeath(Player p) {
        return deathPlayer.containsKey(p.getUniqueId());
    }

    /**
     * 死亡
     *
     * @param p
     */
    public static void death(Player p) {
        deathPlayer.put(p.getUniqueId(), System.currentTimeMillis());
        deathLocation.put(p.getUniqueId(), p.getLocation().clone());
        RespawnData playerData = Config.getPlayerData(p);
        long lastDeath = playerData.getLastDeath();
        Date lastDate = new Date(lastDeath);
        Date today = new Date(System.currentTimeMillis());
        if (lastDate.getYear() != today.getYear() ||
                lastDate.getMonth() != today.getMonth() ||
                today.getDay() != today.getDay()) {//复活次数刷新
            playerData.setFree(0);
            playerData.setCoin(0);
        }
        playerData.setLastDeath(deathPlayer.get(p.getUniqueId()));
        Config.setPlayerData(p, playerData);
    }

    /**
     * 获得死亡时间
     *
     * @param p
     * @return
     */
    public static long getDeathTime(Player p) {
        if (!deathPlayer.containsKey(p.getUniqueId())) return 0;
        long time = deathPlayer.get(p.getUniqueId());
        return time;
    }

    /**
     * 免费复活需要等待的时间
     *
     * @param p
     * @return
     */
    public static long getFreeRespawnNeedWait(Player p) {
        long hasWait = System.currentTimeMillis() - getDeathTime(p);
        int interval = Config.getRespawnConfig().getInterval();
        long needWait = interval * 1000 - hasWait;
        needWait = needWait < 0 ? 0 : needWait;
        return needWait;
    }

    /**
     * 当前是否可以免费复活
     *
     * @param p
     * @return
     */
    public static boolean canFreeRespawn(Player p) {
        int dayFree = Config.getRespawnConfig().getDayFree();
        RespawnData data = Config.getPlayerData(p);
        if (data.getFree() >= dayFree) {//已经复活超过每日限制
            return false;
        }
        if (getFreeRespawnNeedWait(p) > 0) {//没到复活时间
            return false;
        }
        return true;
    }

    /**
     * 免费复活
     *
     * @param p
     */
    public static void freeRespawn(Player p) {
        if (!canFreeRespawn(p)) {
            return;
        }
        deathPlayer.remove(p.getUniqueId());
        RespawnData playerData = Config.getPlayerData(p);
        playerData.setFree(playerData.getFree() + 1);
        Config.setPlayerData(p, playerData);
    }

    /**
     * 搜索inv中的复活币,返回序号,没有就返回-1
     *
     * @param inv
     */
    public static int searchCoin(Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null || item.getType().equals(Material.AIR)) continue;
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta == null || itemMeta.getLore() == null) continue;
            List<String> lores = itemMeta.getLore();
            if (ItemUtils.LoreHas(lores, Config.getCoinLore())) return i;
        }
        return -1;
    }

    /**
     * 是否可以使用复活币复活
     *
     * @param p
     * @return
     */
    public static boolean canCoinRespawn(Player p) {
        int coin = Config.getRespawnConfig().getCoinCount();//每日能用复活币最大次数
        RespawnData data = Config.getPlayerData(p);//当前用了的次数
        return data.getCoin() < coin;
    }

    /**
     * 扣除复活币
     *
     * @param inv
     */
    public static void takeCoin(Inventory inv) {
        int coinIndex = searchCoin(inv);
        ItemStack item = inv.getItem(coinIndex);
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() <= 0) {
            item.setTypeId(0);
        }
    }

    /**
     * 复活币复活
     *
     * @param p
     */
    public static void coinRespawn(Player p) {
        deathPlayer.remove(p.getUniqueId());
        RespawnData playerData = Config.getPlayerData(p);
        playerData.setCoin(playerData.getCoin() + 1);
        Config.setPlayerData(p, playerData);
    }


    /**
     * 是否可以使用点券复活
     *
     * @param p
     * @return
     */
    public static boolean canPointRespawn(Player p) {
        int point = Main.points.look(p.getUniqueId());
        return point >= Config.getRespawnPointConfig().getCost();
    }

    /**
     * 扣除复活的点券
     *
     * @param p
     */
    public static void takePoint(Player p) {
        Main.points.take(p.getUniqueId(), Config.getRespawnPointConfig().getCost());
    }

    /**
     * 点券复活
     *
     * @param p
     */
    public static void pointRespawn(Player p) {
        deathPlayer.remove(p.getUniqueId());
        pointRespawner.add(p.getUniqueId());
        backWhereDead(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                RespawnManager.pointRespawner.remove(p.getUniqueId());
                p.sendMessage(LangUtils.get("lang11", p));
            }
        }.runTaskLater(Main.getPlugin(), 20 * Config.getRespawnPointConfig().getInvincible());
    }

    public static void backWhereDead(Player p) {
        Location whereDead = deathLocation.get(p.getUniqueId());
        p.teleport(whereDead);
    }

    public static boolean isPointRespawner(Player p) {
        return pointRespawner.contains(p);
    }
}
