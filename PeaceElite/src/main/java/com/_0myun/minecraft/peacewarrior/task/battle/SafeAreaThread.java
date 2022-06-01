package com._0myun.minecraft.peacewarrior.task.battle;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.utils.Area;
import com._0myun.minecraft.peacewarrior.utils.LocationUtil;
import com._0myun.minecraft.peacewarrior.utils.LotteryUtil;
import com._0myun.minecraft.peacewarrior.utils.Replacer;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SafeAreaThread extends BukkitRunnable {
    public static HashMap<String, String> nextTime = new HashMap<>();
    Battle battle;
    BattleMap map;

    public SafeAreaThread(Battle battle) {
        this.battle = battle;
        this.map = MapManager.getMapByName(battle.getMap());
    }

    @Override
    public void run() {
        R.INSTANCE.getLogger().info("地图" + battle.getMap() + "缩圈线程开始运行...");
        Map narrows = map.getNarrow();
        Area pre = BattleManager.pres.get(battle.getMap());
        for (Object roundObj : narrows.keySet()) {
            try {
                DBManager.battleDao.refresh(battle);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (battle.getStat().equals(Battle.Stat.FINISH)) {
                return;
            }
            int round = Integer.valueOf(String.valueOf(roundObj));
            Map config = (Map) narrows.get(round);
            int wait = (int) config.get("wait");
            int narrow = (int) config.get("narrow");
            pre.narrow(narrow);
            try {
                nextTime.put(battle.getMap(), new SimpleDateFormat().format(new Date(System.currentTimeMillis() + wait * 1000)));
                Thread.sleep(wait * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Area area = BattleManager.areas.get(battle.getMap());
            area.setPos1(pre.getPos1().clone());
            area.setPos2(pre.getPos2().clone());

            BattleManager.pres.put(battle.getMap(), pre);
            BattleManager.areas.put(battle.getMap(), area);
            if (battle.getProgress() + 1 <= narrows.keySet().size()) {
                battle.setProgress(battle.getProgress() + 1);
                try {
                    DBManager.battleDao.update(battle);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            dropFlightItems();
            R.INSTANCE.getLogger().info("地图" + battle.getMap() + "第" + round + "次缩圈运行结束!" + area);

        }
    }

    /**
     * 丢空投
     */
    public void dropFlightItems() {
        int amount = map.getDrop_chest_item_amount();
        List<Map<?, ?>> items = map.getDrop_chest_item();


        try {
            List<LotteryUtil.Award> awards = new ArrayList<>();
            for (Map<?, ?> item : items) {
                double rand = (Double) item.get("rand");
                String itemStr = (String) item.get("item");
                awards.add(new LotteryUtil.Award(itemStr, rand));
            }

            Location loc = BattleManager.areas.get(battle.getMap()).randPosition().toBukkitLocation();
            loc.setY(244);
            for (int i = 0; i < amount; i++) {
                String itemStr = LotteryUtil.lottery(awards).getAwardId();
                ItemStack item = StreamSerializer.getDefault().deserializeItemStack(itemStr);

                loc.getWorld().dropItem(loc, item);
            }
            String lang = R.INSTANCE.lang("lang23");
            lang = Replacer.replace(lang, loc);
            lang = Replacer.replace(lang, battle);
            lang = Replacer.replace(lang, map);
            Bukkit.broadcastMessage(lang);


            Block block = loc.getWorld().getHighestBlockAt((int) loc.getX(), (int) loc.getZ());

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 60; i++) {
                        for (Location locI : LocationUtil.findOval(block.getLocation(), 2d, 2d)) {
                            locI.getWorld().playEffect(locI, Effect.getByName(R.INSTANCE.getConfig().getString("effect-flight")), 0);
                        }
                        try {
                            Thread.sleep(999);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }.runTaskAsynchronously(R.INSTANCE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
