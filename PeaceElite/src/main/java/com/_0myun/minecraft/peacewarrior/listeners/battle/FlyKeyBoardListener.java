package com._0myun.minecraft.peacewarrior.listeners.battle;

import com._0myun.minecraft.peacewarrior.FlyManager;
import com._0myun.minecraft.peacewarrior.R;
import lk.vexview.event.KeyBoardPressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FlyKeyBoardListener implements Listener {
    @EventHandler
    public void onFly(KeyBoardPressEvent e) {
        Player p = e.getPlayer();
        int key = e.getKey();
        if (e.getEventKeyState() == false) return;
        if (!(key == R.INSTANCE.getConfig().getInt("vv-key-flight", 1))) return;
        FlyManager.fly(p);
    }
}
