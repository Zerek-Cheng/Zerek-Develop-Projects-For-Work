package com._0myun.minecraft.denyunknowninvclick;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DenyUnknownInvClick extends JavaPlugin implements Listener {
    public static DenyUnknownInvClick INSTANCE;
    public static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new Checker());
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        System.out.println("检测到点击格子：" + e.getRawSlot());
    }
}
