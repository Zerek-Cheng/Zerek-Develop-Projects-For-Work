package com.lmyun.event.skillapi;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerAccounts;
import lk.vexview.event.KeyBoardPressEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
        if (!e.getEventKeyState()) {
            return;
        }
        int key = e.getKey();
        Player player = e.getPlayer();
        PlayerAccounts playerAccount = SkillAPI.getPlayerAccountData(player);
        String skill = this.plugin.keyBoardToSkill(player, key);
        if (skill == null || skill == "" || !playerAccount.getActiveData().hasSkill(skill)) {
            return;
        }
        playerAccount.getActiveData().cast(skill);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void KeyBoardPressEvent_Bind(KeyBoardPressEvent e) {
        if (!e.getEventKeyState()) {
            return;
        }
        Player player = e.getPlayer();
        if (!this.plugin.isInBind(player.getName())) {
            return;
        }
        this.plugin.bindKeyBoard(player, this.plugin.getInBind(player.getName()), e.getKey());
        this.plugin.setInBind(player.getName(), null);
        this.plugin.saveKeyBoard();
        player.sendMessage("热键绑定成功");
    }
}
