package com._0myun.minecraft.treasuremap;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack itemInHand = p.getItemInHand();

        if (itemInHand == null || itemInHand.getType().equals(Material.AIR)) return;
        NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(itemInHand));
        String mapName = "";
        try {
            mapName = nbt.getString("com._0myun.minecraft.treasuremap.R.map");
        } catch (Throwable ex) {
            return;
        }
        if (mapName.isEmpty()) return;
        TreasureMap map = (TreasureMap) R.INSTANCE.getConfig().get("map." + mapName);
        itemInHand.setAmount(itemInHand.getAmount() - 1);
        R.INSTANCE.getSearching().put(p.getUniqueId(), mapName);
        R.INSTANCE.getPosition().put(p.getUniqueId(), map.randPosition());

        p.sendMessage(R.INSTANCE.lang("lang1").replace("{map}", mapName));
        R.INSTANCE.sendTitle(p);

        new TreasureTask(p, R.INSTANCE.getConfig().getInt("wait", 5)).runTaskTimer(R.INSTANCE, 0, 1);
    }

}
