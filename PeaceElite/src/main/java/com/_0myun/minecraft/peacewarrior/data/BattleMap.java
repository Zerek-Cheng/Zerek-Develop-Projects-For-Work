package com._0myun.minecraft.peacewarrior.data;

import lombok.Data;
import lombok.ToString;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.List;
import java.util.Map;

@Data
@ToString
@SerializableAs("com._0myun.minecraft.peacewarrior.data.BattleMap")
public class BattleMap extends DataBase {
    String name;
    String display;
    int player_min;
    int player_max;
    int team_max;
    Position wait_hall;
    Position position_min;
    Position position_max;
    Map narrow;
    List<String> jump_item;
    int wild_chest_item_amount;
    List<Map<?, ?>> wild_chest_item;
    int drop_chest_item_amount;
    List<Map<?, ?>> drop_chest_item;
    Map win_reward;
    Map lose_reward;


    public BattleMap(Map<String, Object> args) {
        super(args);
    }
}
