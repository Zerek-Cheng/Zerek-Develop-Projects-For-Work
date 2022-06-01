/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.gmail.filoghost.chestcommands.listener;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.filoghost.chestcommands.ChestCommands;
import com.gmail.filoghost.chestcommands.api.Icon;
import com.gmail.filoghost.chestcommands.api.IconMenu;
import com.gmail.filoghost.chestcommands.internal.BoundItem;
import com.gmail.filoghost.chestcommands.internal.MenuInventoryHolder;
import com.gmail.filoghost.chestcommands.task.ExecuteCommandsTask;
import com.gmail.filoghost.chestcommands.util.Utils;

public class InventoryListener implements Listener {
	
	private static Map<Player, Long> antiClickSpam = Utils.newHashMap();
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onInteract(PlayerInteractEvent event) {
		if (event.hasItem() && event.getAction() != Action.PHYSICAL) {
			for (BoundItem boundItem : ChestCommands.getBoundItems()) {
				if (boundItem.isValidTrigger(event.getItem(), event.getAction())) {
					if (event.getPlayer().hasPermission(boundItem.getMenu().getPermission())) {
						boundItem.getMenu().open(event.getPlayer());
					} else {
						boundItem.getMenu().sendNoPermissionMessage(event.getPlayer());
					}
				}
			}
		}
	}

	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getHolder() instanceof MenuInventoryHolder) {
			
			event.setCancelled(true); // First thing to do, if an exception is thrown at least the player doesn't take the item
			
			IconMenu menu = ((MenuInventoryHolder) event.getInventory().getHolder()).getIconMenu();
			int slot = event.getRawSlot();
			
			if (slot >= 0 && slot < menu.getSize()) {
				
				Icon icon = menu.getIconRaw(slot);
				
				if (icon != null && event.getInventory().getItem(slot) != null) {
					Player clicker = (Player) event.getWhoClicked();
					
					Long cooldownUntil = antiClickSpam.get(clicker);
					long now = System.currentTimeMillis();
					int minDelay = ChestCommands.getSettings().anti_click_spam_delay;
					
					if (minDelay > 0) {
						if (cooldownUntil != null && cooldownUntil > now) {
							return;
						} else {
							antiClickSpam.put(clicker, now + minDelay);
						}
					}
					
					// Closes the inventory and executes commands AFTER the event
					Bukkit.getScheduler().scheduleSyncDelayedTask(ChestCommands.getInstance(), new ExecuteCommandsTask(clicker, icon));
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		antiClickSpam.remove(event.getPlayer());
	}
	
}
