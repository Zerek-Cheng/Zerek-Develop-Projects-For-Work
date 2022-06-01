package com._0myun.minecraft.peacewarrior;

import com._0myun.minecraft.peacewarrior.data.BattleMap;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    public static final List<BattleMap> maps = new ArrayList<>();

    public static BattleMap getMapByName(String name) {
        for (BattleMap map : maps) {
            if (map.getName().equalsIgnoreCase(name)) return map;
        }
        return null;
    }
}
