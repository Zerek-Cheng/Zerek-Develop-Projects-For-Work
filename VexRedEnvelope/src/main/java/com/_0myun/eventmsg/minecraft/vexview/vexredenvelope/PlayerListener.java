package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import lk.vexview.event.KeyBoardPressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {
    @EventHandler
    public void onClick(KeyBoardPressEvent e) {
        if (DataManager.getLastWord() == null) return;
        if (!ConfigManager.getConfig().getBoolean("ui.keyBoard.receive_enable")) return;
        if (e.getEventKeyState()) return;
        if (!(e.getKey() == ConfigManager.getConfig().getInt("ui.keyBoard.receive"))) return;
        Player p = e.getPlayer();
        p.performCommand("vred get " + DataManager.getLastWord());
        p.sendMessage(LangUtil.get("lang18"));
    }
}
