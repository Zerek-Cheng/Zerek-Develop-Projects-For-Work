package com._0myun.minecraft.vexview.npcdialogue;

import lk.vexview.event.KeyBoardPressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.betoncraft.betonquest.conversation.Conversation;

public class PlayerListener implements Listener {

    /*@EventHandler
    public void onCloseGUI(VexGuiCloseEvent e) {
        System.out.println(e.getStage());
        Player p = e.getPlayer();
        HashMap<UUID, Conversation> playerConMap = NPCDialogue.plugin.getPlayerConMap();
        Conversation con = playerConMap.get(p.getUniqueId());
        if (con == null) return;
        con.endConversation();
        playerConMap.remove(p.getUniqueId());
        NPCDialogue.plugin.getLogger().log(Level.INFO, "玩家" + p.getName() + "强制关闭NPC聊天,结束对话.")
    }*/
    @EventHandler
    public void onKeyBoardClose(KeyBoardPressEvent e) {
        int key = e.getKey();
        boolean eventKeyState = e.getEventKeyState();
        if (!(key == 1)) return;
        if (!eventKeyState) return;
        Player p = e.getPlayer();
        Conversation con = Conversation.getConversation(p.getUniqueId().toString());
        if (con == null) return;
        con.endConversation();
        //NPCDialogue.plugin.getLogger().log(Level.INFO, "玩家" + p.getName() + "强制关闭NPC聊天,结束对话.");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
    }

}
