package com._0myun.minecraft.skillapi.vexskillkey;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerSkill;
import lk.vexview.event.KeyBoardPressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {

                Player p = e.getPlayer();
                LinkedHashMap<String, Integer> playerSkillImageIdMap = R.INSTANCE.getPlayerSkillImageIdMap();

                String name = SkillAPIUtils.getMainClassName(p);
                for (String index : R.INSTANCE.getConfig().getConfigurationSection("class." + name).getKeys(false)) {
                    playerSkillImageIdMap.put(p.getName() + "-" + index, 0);
                }

                R.INSTANCE.playerInit(p);
            }
        }.runTaskLater(R.INSTANCE, 10);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        R.INSTANCE.playerUnInit(e.getPlayer());
    }

    @EventHandler
    public void onKey(KeyBoardPressEvent e) {
        Player p = e.getPlayer();
        int key = e.getKey();
        if (R.DEBUG) System.out.println("key = " + key);
        if (e.getEventKeyState() == true) return;
        String className = SkillAPIUtils.getMainClassName(p);
        if (className.equalsIgnoreCase("default")) return;
        int index = R.INSTANCE.keyToIndex(key);
        if (index == -1) return;
        if (!R.INSTANCE.isSkillOpen(p.getUniqueId(), index)) return;

        String skillName = R.INSTANCE.getSkillName(className, index);
        PlayerSkill skill = SkillAPI.getPlayerData(p).getSkill(skillName);
        if (skill.isOnCooldown()) return;
        if (R.DEBUG) {
            System.out.println("p.getName() = " + p.getName());
            System.out.println("key = " + key);
            System.out.println("index = " + index);
            System.out.println("className = " + className);
            System.out.println("skillName = " + skillName);
            System.out.println("skill.getCooldown() = " + skill.getCooldown());
        }
        if (R.DEBUG) {
            System.out.println("玩家" + p.getName() + "使用技能" + skillName);
        }
        SkillAPI.getPlayerData(p).cast(skillName);
        R.INSTANCE.sendPlayerSkillCooldownShow(p, R.INSTANCE.getSkillIndex(className, skillName), skill.getCooldown());

    }

}