package com._0myun.minecraft.treasuremap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SerializableAs("com._0myun.minecraft.treasuremap.TreasureMap")
@Data
@AllArgsConstructor
@ToString
public class TreasureMap implements ConfigurationSerializable {

    String item;
    Position loc_min;
    Position loc_max;
    String title;
    String subtitle;
    String actionbar;
    double reward_1;
    double reward_2;
    double reward_3;
    List<RandCommand> command;
    String reward_none;
    String reward_more;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("item", getItem());
        result.put("loc-min", getLoc_min());
        result.put("loc-max", getLoc_max());
        result.put("title", getTitle());
        result.put("subtitle", getSubtitle());
        result.put("actionbar", getActionbar());
        result.put("reward-1", getReward_1());
        result.put("reward-2", getReward_2());
        result.put("reward-3", getReward_3());
        result.put("command", getCommand());
        result.put("reward-none", getReward_none());
        result.put("reward-more", getReward_more());

        return result;
    }

    public static TreasureMap deserialize(Map<String, Object> args) {
        return new TreasureMap(
                (String) args.get("item")
                , (Position) args.get("loc-min")
                , (Position) args.get("loc-max")
                , (String) args.get("title")
                , (String) args.get("subtitle")
                , (String) args.get("actionbar")
                , (Double) args.get("reward-1")
                , (Double) args.get("reward-2")
                , (Double) args.get("reward-3")
                , (List<RandCommand>) args.get("command")
                , (String) args.get("reward-none")
                , (String) args.get("reward-more")
        );
    }

    public Position randPosition() {
        Position min = this.getLoc_min();
        Position max = this.getLoc_max();
        return new Position(min.getWorld(), new Random().nextInt(max.getX() - min.getX()) + min.getX()
                , new Random().nextInt(max.getY() - min.getY()) + min.getY()
                , new Random().nextInt(max.getZ() - min.getZ()) + min.getZ());
    }
}