/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.ItemStack
 */
package su.nightexpress.divineitems.listeners;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import su.nightexpress.divineitems.api.ItemAPI;

public class PickupListener2
implements Listener {
    @EventHandler
    public void onPickup(PlayerPickupItemEvent playerPickupItemEvent) {
        if (playerPickupItemEvent.getItem().hasMetadata("dont_pick_me")) {
            playerPickupItemEvent.setCancelled(true);
            return;
        }
        ItemStack itemStack = playerPickupItemEvent.getItem().getItemStack();
        Player player = playerPickupItemEvent.getPlayer();
        if (ItemAPI.isSoulBinded(itemStack) && !ItemAPI.isOwner(itemStack, player)) {
            playerPickupItemEvent.setCancelled(true);
            return;
        }
        if (ItemAPI.hasOwner(itemStack) && !ItemAPI.isOwner(itemStack, player)) {
            playerPickupItemEvent.setCancelled(true);
            return;
        }
    }
}

