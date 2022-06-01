package com._0myun.minecraft.netease.shopgodsender;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class NeteaseMessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        //System.out.println("onStoreMessageReceived:" + channel + ", " + player.getName());
    }

}
