package com._0myun.minecraft.dentallaboratories.listener;

import com._0myun.minecraft.dentallaboratories.Main;
import com.comphenix.packetwrapper.WrapperPlayServerSetSlot;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ProtocolItemShowListener extends PacketAdapter {
    public ProtocolItemShowListener() {
        super(Main.plugin, ListenerPriority.NORMAL, PacketType.Play.Server.SET_SLOT);
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.getPacketType() !=
                PacketType.Play.Server.SET_SLOT) {
            return;
        }
        Player p = event.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE)) return;

        WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot(event.getPacket());

        ItemStack itemStack = packet.getSlotData();
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        if (Main.plugin.getItemsManager().getItem(itemStack) == null) return;
        int level = Main.plugin.getItemsManager().getLevel(itemStack);
        if (level <= 0) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasDisplayName()) return;
        itemMeta.setDisplayName(itemMeta.getDisplayName() + " " + Main.plugin.getConfig().getString("level-show-color", "§b§l") + "+" + level);
        itemStack.setItemMeta(itemMeta);

        packet.setSlotData(itemStack);

        event.setCancelled(false);
    }

}
