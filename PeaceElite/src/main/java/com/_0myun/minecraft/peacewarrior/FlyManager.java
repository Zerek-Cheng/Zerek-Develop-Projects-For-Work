package com._0myun.minecraft.peacewarrior;

import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.data.FlyLine;
import com._0myun.minecraft.peacewarrior.data.Position;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleFlyEvent;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FlyManager {
    /**
     * 飞行线路
     */
    public static Map<String, FlyLine> lines = new HashMap<>();
    /**
     * 飞行进度
     */
    public static Map<String, Integer> precent = new ConcurrentHashMap<>();
    /**
     * 记录已经跳伞的玩家
     */
    public static List<UUID> fly = new ArrayList<>();

    public static FlyLine randLine(Battle battle) {
        BattleMap map = MapManager.getMapByName(battle.getMap());
        Position min = map.getPosition_min();
        Position max = map.getPosition_max();
        FlyLine line = new FlyLine();
        switch (new Random().nextInt(2)) {
            case 0:
                line.setPos1(new Position(min.getWorld(), new Random().nextInt(max.getX() - min.getX()), 255, 0));
                line.setPos2(new Position(min.getWorld(), new Random().nextInt(max.getX() - min.getX()), 255, max.getZ() - min.getZ()));
                line.setMode(0);
                break;
            case 1:
                line.setPos1(new Position(min.getWorld(), 0, 255, new Random().nextInt(max.getZ() - min.getZ())));
                line.setPos2(new Position(min.getWorld(), max.getX() - min.getX(), 255, new Random().nextInt(max.getZ() - min.getZ())));
                line.setMode(1);
                break;
        }
        FlyManager.lines.put(battle.getMap(), line);
        return line;
    }

    public static Position calc(String map, double percent) {
        /*x=[x1＋λx2]/(1＋λ)
        y=[y1＋λy2]/(1＋λ)*/
        /*Cx=X1+1/3*(X2-X1)
        Cy=Y1+1/3*(Y2-Y1)*/
        if (!lines.containsKey(map)) return null;
        FlyLine line = lines.get(map);
        Position pos1 = line.getPos1();
        Position pos2 = line.getPos2();
        int x = Double.valueOf(pos1.getX() + (percent * (pos2.getX() - pos1.getX()))).intValue();
        int y = Double.valueOf(pos1.getZ() + (percent * (pos2.getZ() - pos1.getZ()))).intValue();
        return new Position(pos1.getWorld(), x, 255, y);
    }

    public synchronized static void fly(Player p) {
        if (FlyManager.fly.contains(p.getUniqueId())) return;
        PlayerData playerData = null;
        try {
            playerData = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (!playerData.getStat().equals(PlayerData.Stat.PLAY)) {
            return;
        }
        Position posMin = MapManager.getMapByName(playerData.getMap()).getPosition_min();
        Integer percent = FlyManager.precent.get(playerData.getMap());
        if (percent == null) return;
        p.getInventory().clear();
        MapManager.getMapByName(playerData.getMap()).getJump_item().forEach(itemstr -> {
            try {
                p.getInventory().addItem(StreamSerializer.getDefault().deserializeItemStack(itemstr));
                p.updateInventory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Position pos = FlyManager.calc(playerData.getMap(), percent / 100d);
        Location loc = posMin.toBukkitLocation();
        loc.setX(loc.getX() + pos.getX());
        loc.setY(255);
        loc.setZ(loc.getZ() + pos.getZ());
        p.teleport(loc);
        FlyManager.fly.add(p.getUniqueId());
        R.INSTANCE.getLogger().info("玩家" + p.getName() + "在" + playerData.getMap() + "跳伞,坐标信息:" + loc.toString());

        Bukkit.getPluginManager().callEvent(new PWBattleFlyEvent(p));
    }
}
