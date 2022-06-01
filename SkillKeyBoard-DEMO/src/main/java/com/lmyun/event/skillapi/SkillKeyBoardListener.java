package com.lmyun.event.skillapi;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerAccounts;
import com.sucy.skill.api.player.PlayerSkill;
import lk.vexview.event.KeyBoardPressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SkillKeyBoardListener implements Listener {

    private SkillKeyBoard plugin;

    public SkillKeyBoardListener(SkillKeyBoard plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void PlayerLoginEvent(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        this.plugin.sendPlayerHud(player);
        BukkitRunnable bukkitRunnable = new SkillKeyBoardHudSender(player, this.plugin);
        bukkitRunnable.runTaskTimerAsynchronously(this.plugin, 24, 12);
    }

    @EventHandler
    public void KeyBoardPressEvent(KeyBoardPressEvent e) {
        int key = e.getKey();
        Player player = e.getPlayer();
        PlayerAccounts playerAccount = SkillAPI.getPlayerAccountData(player);
        if (key != 17) {
            return;
        }
        String skill = ((PlayerSkill) playerAccount.getActiveData().getSkills().toArray()[0]).getData().getName();
        if (skill == null || skill == "" || !playerAccount.getActiveData().hasSkill(skill)) {
            return;
        }
        playerAccount.getActiveData().cast(skill);
        playerAccount.getPlayer().sendMessage("您正在使用的是SkillKeyBoard演示版,只支持固定图片固定技能固定按键,如需要无限制版本请下载商业版,演示版本功能键为W,支持的技能是获取到的第一个技能");
    }
}
