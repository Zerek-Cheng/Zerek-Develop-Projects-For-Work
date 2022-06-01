package com._0myun.minecraft.peacewarrior;

import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.data.Position;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleFinishEvent;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleQuitEvent;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleStartEvent;
import com._0myun.minecraft.peacewarrior.utils.Area;
import com._0myun.minecraft.peacewarrior.utils.LotteryUtil;
import com._0myun.minecraft.peacewarrior.utils.Replacer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.utility.StreamSerializer;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class BattleManager {

    public static HashMap<String, Area> areas = new HashMap<>();
    public static HashMap<String, Area> pres = new HashMap<>();

    public static void updateBattlePlayer(Battle battle) {
        if (battle.getStat().equals(Battle.Stat.WAIT) || battle.getStat().equals(Battle.Stat.READY)) {
            battle.setPlayer_amount(DBManager.playerDataDao.queryForMap(battle.getMap(), PlayerData.Stat.WAIT, PlayerData.Stat.PLAY).size());
            battle.setAlive(DBManager.playerDataDao.queryForMap(battle.getMap(), PlayerData.Stat.WAIT, PlayerData.Stat.PLAY).size());
        } else if (battle.getStat().equals(Battle.Stat.PLAY) || battle.getStat().equals(Battle.Stat.FINISH)) {
            battle.setAlive(DBManager.playerDataDao.queryForMap(battle.getMap(), PlayerData.Stat.PLAY).size());
        }
        try {
            DBManager.battleDao.update(battle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void join(Player p, Battle battle) {
        if (battle.getStat() != Battle.Stat.WAIT && battle.getStat() != Battle.Stat.READY) {
            p.sendMessage(R.INSTANCE.lang("lang3"));
            return;
        }
        if (battle.getPlayer_amount() >= MapManager.getMapByName(battle.getMap()).getPlayer_max()) {
            p.sendMessage(R.INSTANCE.lang("lang4"));
            return;
        }
        PlayerData data = null;
        try {
            data = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!data.getStat().equals(PlayerData.Stat.NONE) || data.getStat().equals(PlayerData.Stat.BANNED)) {
            p.sendMessage(R.INSTANCE.lang("lang5"));
            return;
        }
        data.setStat(PlayerData.Stat.WAIT);
        data.setMap(battle.getMap());
        try {
            DBManager.playerDataDao.update(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PlayerInvManager.saveInv(p);
        p.teleport(MapManager.getMapByName(battle.getMap()).getWait_hall().toBukkitLocation());
        List<String> watingItems = R.INSTANCE.getConfig().getStringList("battle-wait-item");
        for (int i = 0; i < watingItems.size(); i++) {
            String itemStr = watingItems.get(i);
            try {
                ItemStack itemStack = StreamSerializer.getDefault().deserializeItemStack(itemStr);
                String cmd = R.INSTANCE.getConfig().getString("battle-wait-item-command." + i);
                if (cmd != null && !cmd.isEmpty()) {
                    itemStack = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(itemStack));
                    NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(itemStack));
                    nbt.put("com._0myun.minecraft.peacewarrior.R.command", cmd);
                }
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta.hasLore()) {
                    List<String> lore = itemMeta.getLore();
                    lore = Replacer.replace(lore, data);
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                }
                p.getInventory().setItem(i, itemStack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TeamManager.captain.put(p.getUniqueId(), p.getUniqueId());
        p.sendMessage(R.INSTANCE.lang("lang6"));

        R.INSTANCE.getLogger().info("玩家" + p.getName() + "加入" + battle.getMap() + "战场");
    }

    public static void quit(Player p) {
        PlayerData sqlData = null;
        try {
            sqlData = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
            String map = sqlData.getMap();
            if (!sqlData.getStat().equals(PlayerData.Stat.WAIT) && !sqlData.getStat().equals(PlayerData.Stat.PLAY))
                return;
            TeamManager.quitTeam(p.getUniqueId());
            FlyManager.fly.remove(p.getUniqueId());

            sqlData.setStat(PlayerData.Stat.NONE);
            sqlData.setMap(null);
            //sqlData.setMap("");
            DBManager.playerDataDao.update(sqlData);

            PlayerInvManager.loadInv(p);

            R.INSTANCE.getLogger().info("玩家" + p.getName() + "离开" + map + "的战斗");
            Bukkit.getPluginManager().callEvent(new PWBattleQuitEvent(p, DBManager.battleDao.queryForMap(map)));
            Battle battle = DBManager.battleDao.queryForMap(map);

            checkWin(battle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 奖励
     *
     * @param p   玩家
     * @param map 地图
     * @param win 是否胜利
     */
    public static void reward(Player p, BattleMap map, boolean win) {
        Map reward = win ? map.getWin_reward() : map.getLose_reward();
        reward.forEach((k, v) -> {
            if (k.equals("item")) {
                List<String> items = (List<String>) v;
                items.forEach(itemStr -> {
                    try {
                        p.getInventory().addItem(StreamSerializer.getDefault().deserializeItemStack(itemStr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else if (k.equals("command")) {
                List<String> cmds = (List<String>) v;
                boolean op = p.isOp();
                try {
                    if (!op) p.setOp(true);
                    cmds.forEach(cmd -> p.performCommand(cmd.replace("!player!", p.getName())));
                } finally {
                    if (!op) p.setOp(false);
                }
            }
        });
        R.INSTANCE.getLogger().info("奖励发放——" + p.getName() + "获得" + map.getName() + "地图的" + (win ? "胜利" : "失败") + "奖励");
    }

    public static synchronized void start(Battle battle) {
        BattleMap map = MapManager.getMapByName(battle.getMap());
        areas.put(battle.getMap(), Area.builder().pos1(map.getPosition_min().clone()).pos2(map.getPosition_max().clone()).build());
        pres.put(battle.getMap(), Area.builder().pos1(map.getPosition_min().clone()).pos2(map.getPosition_max().clone()).build());

        R.INSTANCE.getLogger().info(battle.getMap() + "地图安全范围初始化..");
        try {
            DBManager.battleDao.refresh(battle);
            if (battle.getStat().equals(Battle.Stat.PLAY)) return;
            battle.setStat(Battle.Stat.PLAY);
            DBManager.battleDao.update(battle);

            FlyManager.randLine(battle);
            FlyManager.precent.put(battle.getMap(), 0);

            List<PlayerData> playerDatas = DBManager.playerDataDao.queryForMap(battle.getMap(), PlayerData.Stat.WAIT, PlayerData.Stat.PLAY);
            for (PlayerData playerData : playerDatas) {
                playerData.setStat(PlayerData.Stat.PLAY);
                DBManager.playerDataDao.update(playerData);
            }

            Bukkit.getPluginManager().callEvent(new PWBattleStartEvent(battle));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void randChest(BattleMap map, Inventory inv) {
        try {
            inv.clear();
            int amount = map.getWild_chest_item_amount();
            List<Map<?, ?>> items = map.getWild_chest_item();
            List<LotteryUtil.Award> awards = new ArrayList<>();
            for (Map<?, ?> item : items) {
                double rand = (Double) item.get("rand");
                String itemStr = (String) item.get("item");
                awards.add(new LotteryUtil.Award(itemStr, rand));
            }
            for (int i = 0; i < amount; i++) {
                String itemStr = LotteryUtil.lottery(awards).getAwardId();
                inv.addItem(StreamSerializer.getDefault().deserializeItemStack(itemStr));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static synchronized void checkWin(Battle battle) {
        if (battle == null || !battle.getStat().equals(Battle.Stat.PLAY)) return;
        try {
            List<PlayerData> pDs = DBManager.playerDataDao.queryForMap(battle.getMap(), PlayerData.Stat.PLAY);
            UUID lastCaptain = null;
            for (int i = 0; i < pDs.size(); i++) {
                PlayerData pD = pDs.get(i);
                if (pDs.size() <= 1) break;
                UUID captain = TeamManager.captain.get(UUID.fromString(pD.getUuid()));
                if (i == 0) {
                    lastCaptain = captain;
                    continue;
                }
                if (!captain.equals(lastCaptain)) return;
                continue;
            }

            for (PlayerData pD : pDs) {
                Player p = Bukkit.getPlayer(pD.getPlayer());
                p.getInventory().clear();
                BattleManager.quit(Bukkit.getPlayer(pD.getPlayer()));
                BattleManager.reward(p, MapManager.getMapByName(pD.getMap()), true);
                p.updateInventory();

                DBManager.playerDataDao.update(pD);
                pD.setWin(pD.getWin() + 1);
                pD.setTotal(pD.getTotal() + 1);
                pD.setStat(PlayerData.Stat.NONE);
                pD.setMap(null);
                DBManager.playerDataDao.update(pD);
                p.teleport(((Position) R.INSTANCE.getConfig().get("hall")).toBukkitLocation());
            }
            battle.setStat(Battle.Stat.FINISH);
            DBManager.battleDao.update(battle);
            Bukkit.getPluginManager().callEvent(new PWBattleFinishEvent(battle));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        R.INSTANCE.getLogger().info("战场" + battle.getMap() + "结束了");
    }
}
