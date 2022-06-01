package com._0myun.minecraft.skillapi.vexskillkey;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.classes.RPGClass;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;
import org.bukkit.entity.Player;

public class SkillAPIUtils {
    public static String getMainClassName(Player p) {
        PlayerData pd = SkillAPI.getPlayerData(p);
        if (!pd.hasClass()) return "default";
        PlayerClass mainClass = pd.getMainClass();
        RPGClass data = mainClass.getData();
        return data.getName();
    }
}
