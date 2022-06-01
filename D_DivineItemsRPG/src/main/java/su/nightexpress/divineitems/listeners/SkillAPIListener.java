/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.sucy.skill.api.event.PlayerCastSkillEvent
 *  com.sucy.skill.api.event.PlayerClassChangeEvent
 *  com.sucy.skill.api.player.PlayerData
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package su.nightexpress.divineitems.listeners;

import com.sucy.skill.api.event.PlayerCastSkillEvent;
import com.sucy.skill.api.event.PlayerClassChangeEvent;
import com.sucy.skill.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.config.Config;

public class SkillAPIListener
implements Listener {
    private DivineItems plugin;

    public SkillAPIListener(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @EventHandler
    public void onSkillCast(PlayerCastSkillEvent playerCastSkillEvent) {
        if (!this.plugin.getCFG().skillAPIReduceDurability()) {
            return;
        }
        Player player = playerCastSkillEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || ItemAPI.getDurability(itemStack, 0) <= 0) {
            return;
        }
        ItemAPI.reduceDurability(itemStack, 1);
    }

    @EventHandler
    public void onClass(PlayerClassChangeEvent playerClassChangeEvent) {
        Player player = playerClassChangeEvent.getPlayerData().getPlayer();
        EntityAPI.checkForLegitItems(player);
    }
}

