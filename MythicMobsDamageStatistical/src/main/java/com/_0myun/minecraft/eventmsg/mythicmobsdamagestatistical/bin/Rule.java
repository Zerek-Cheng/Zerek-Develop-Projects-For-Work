package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin;

import lombok.Data;
import lombok.var;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@SerializableAs("com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Rule")
public class Rule implements ConfigurationSerializable {
    int timeOfRound;
    long endTime;
    Map<String, List<String>> rewards;

    public static Rule deserialize(Map<String, Object> map) {
        var tmp = new Rule();
        tmp.setTimeOfRound((Integer) map.get("timeOfRound"));
        tmp.setEndTime(Long.valueOf(String.valueOf(map.get("endTime"))));
        System.out.println(((Map)map.get("reward")).keySet().iterator().next().getClass().getName());
        tmp.setRewards((Map<String, List<String>>) map.get("reward"));
        return tmp;
    }

    @Override
    public Map<String, Object> serialize() {
        var tmp = new HashMap<String, Object>();
        tmp.put("timeOfRound", this.getTimeOfRound());
        tmp.put("endTime", this.getEndTime());
        tmp.put("reward", this.getRewards());
        return tmp;
    }

    public List<String> getReward(int index) {
        System.out.println(this.getRewards().get("1").getClass().toString());
        return this.getRewards().get(String.valueOf(index));
    }
}
